package com.back.boundcontext.post.domain;

import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "POST_MEMBER")
@NoArgsConstructor
public class PostMember extends ReplicaMember {

    public PostMember(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate,
                      String username, String password, String nickname, int activityScore) {
        super(id, createdDate, modifiedDate, username, password, nickname, activityScore);
    }
}
