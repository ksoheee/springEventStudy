package com.back.global.jpa.entity;

import com.back.global.evnetpublisher.EventPublisher;
import com.back.global.global.GlobalConfig;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass //테이블로 만들 클래스는 아니지만, 공통 필드와 JPA 매핑 정보를 자식 Entity의 테이블에 상속시키기 위한 부모 클래스
@Getter
public abstract class BaseEntity {
    abstract public Long getId();
    abstract public LocalDateTime getCreatedDate();
    abstract public LocalDateTime getModifiedDate();


    protected void publishEvent(Object event){
        GlobalConfig.getEventPublisher().publish(event);
    }
}
