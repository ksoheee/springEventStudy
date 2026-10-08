package com.back.shared.post.dto;

import com.back.standard.modeltype.HasModelTypeCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(
        onConstructor_ = @JsonCreator(mode= JsonCreator.Mode.PROPERTIES)
)
public class PostDto implements HasModelTypeCode {
    private final Long id;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final Long authorId;
    private final String authorName;
    private final String title;
    private final String content;

    @Override
    public String getModelTypeCode() {
        return "Post";
    }
}
