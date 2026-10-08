package com.back.shared.market.out;

import com.back.global.exception.DomainException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class TossPaymentsService {

    private static final String TOSS_BASE_URL = "https://api.tosspayments.com";
    private static final String CONFIRM_PATH = "/v1/payments/confirm";

    private final RestClient tossRestClient;
    private final ObjectMapper objectMapper;

    @Value("${custom.market.toss.payments.secretKey:}")
    private String tossSecretKey;

    public TossPaymentsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.tossRestClient = RestClient.builder()
                .baseUrl(TOSS_BASE_URL)
                .build();
    }

    public Map<String, Object> confirmCardPayment(String paymentKey, String orderId, long amount) {
        //요청 dto 생성
        TossPaymentsConfirmRequest requestBody = new TossPaymentsConfirmRequest(
                paymentKey,
                orderId,
                amount
        );

        try {
            //http요청을 구성하여 retrieve로 api 반환하고 toEntity로 JSON응답을 Map으로 변환
            ResponseEntity<Map> responseEntity = createConfirmRequest(requestBody)
                    .retrieve()
                    .toEntity(Map.class);

            //HTTP 응답 상태 코드
            int httpStatus = responseEntity.getStatusCode().value();
            //TOSS가 반환한 JSON데이터
            Map<String, Object> responseBody = responseEntity.getBody();

            if (httpStatus != 200) {
                throw createDomainExceptionFromNon200(httpStatus, responseBody);
            }

            //응답이 성공이여도 응답 body가 비어있으면 비정상적인 상황으로 판단
            if (responseBody == null) {
                throw new DomainException("400-EMPTY_RESPONSE", "토스 결제 승인 응답 바디가 비었습니다.");
            }

            @SuppressWarnings("unchecked")  //제네릭 형변환에 관한 컴파일러 경고를 숨기는
            Map<String, Object> casted = (Map<String, Object>) responseBody;
            return casted;

        } catch (RestClientResponseException e) {
            throw createDomainExceptionFromHttpError(e); //오류 구조 맞추기 위해 변환
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) { //예상지 못한 기타 오류
            throw new DomainException("400-TOSS_CALL_EXCEPTION", "토스 결제 승인 호출 중 예외: " + e.getMessage());
        }
    }

    //http 요청 구성
    private RestClient.RequestHeadersSpec<?> createConfirmRequest(TossPaymentsConfirmRequest requestBody) {
        return tossRestClient.post()    //post요청 구성 시작
                .uri(CONFIRM_PATH)      //경로지정
                .contentType(MediaType.APPLICATION_JSON)    //요청 body가 JSON임을 지정
                .accept(MediaType.APPLICATION_JSON)         //JSON응답을 원한다고 지정
                .headers(headers -> headers.setBasicAuth(tossSecretKey, "")) //인증 정보를 Header에 설정
                .body(requestBody);     //승인요청 dto를 body에 설정
    }

    //HTTP응답은 정상적으로 받았지만 상태가 200이 아닐때
    private DomainException createDomainExceptionFromNon200(int httpStatus, Map responseBody) {
        if (responseBody == null) {
            return new DomainException("400-HTTP_" + httpStatus, "토스 결제 승인 실패(응답 바디 없음), HTTP " + httpStatus);
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) responseBody;

        String tossCode = extractStringOrDefault(body, "code", "HTTP_" + httpStatus);
        String tossMessage = extractStringOrDefault(body, "message", "토스 결제 승인 실패, HTTP " + httpStatus);

        return new DomainException("400-" + tossCode, tossMessage);
    }

    private DomainException createDomainExceptionFromHttpError(RestClientResponseException e) {
        int httpStatus = e.getStatusCode().value();
        String rawBody = e.getResponseBodyAsString(StandardCharsets.UTF_8); //오류 응답 body가져오기

        if (rawBody == null || rawBody.isBlank()) {
            return new DomainException("400-HTTP_" + httpStatus, "토스 결제 승인 실패(빈 바디), HTTP " + httpStatus);
        }

        try {
            //rawBody가 아직 JSON이므로 Map으로 변환(ObjectMapper)/ TypeReference는 Jackson에 변환할 Java타입의 제네릭 정보를 전달하기 위해
            Map<String, Object> errorBody = objectMapper.readValue(rawBody, new TypeReference<>() {
            });
            String tossCode = extractStringOrDefault(errorBody, "code", "HTTP_" + httpStatus);
            String tossMessage = extractStringOrDefault(errorBody, "message", "토스 결제 승인 실패, HTTP " + httpStatus);
            return new DomainException("400-" + tossCode, tossMessage); //프로젝트 예외로 변환
        } catch (Exception parseFail) {
            return new DomainException("400-HTTP_" + httpStatus, "토스 결제 승인 실패, HTTP " + httpStatus + " / body=" + rawBody);
        }
    }

    //Map에서 특정 값을 가져오되, 값이 없거나 유효하지 않으면 기본값 반환
    private String extractStringOrDefault(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        if (value instanceof String s && !s.isBlank()) return s;
        return defaultValue;
    }

    public record TossPaymentsConfirmRequest(String paymentKey, String orderId, long amount) {
    }
}