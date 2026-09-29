package com.back.shared.member.domain;


import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public abstract class ReplicaMember extends BaseMember{
    @Id
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ReplicaMember(long id, LocalDateTime createdDate, LocalDateTime modifiedDate,
                         String username, String password, String nickname, int activityScore) {
        super(username, password, nickname,activityScore );
        this.id = id;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}
