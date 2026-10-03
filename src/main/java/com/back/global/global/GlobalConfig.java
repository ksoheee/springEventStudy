package com.back.global.global;

import com.back.global.evnetpublisher.EventPublisher;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * spring been인 eventPublisher를 엔티티에서도 가져올 수 있도록
 */
@Configuration
public class GlobalConfig {
    @Getter
    private static EventPublisher eventPublisher;

    @Autowired
    public void setEventPublisher(EventPublisher eventPublisher) {
        GlobalConfig.eventPublisher = eventPublisher;
    }
}
