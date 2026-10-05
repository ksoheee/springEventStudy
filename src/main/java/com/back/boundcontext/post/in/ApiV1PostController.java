package com.back.boundcontext.post.in;

import com.back.boundcontext.post.app.PostFacade;
import com.back.shared.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostFacade postFacade;

    @GetMapping
    @Transactional(readOnly = true)
    public List<PostDto> getPosts(){
        return postFacade.findByOrderByIdDesc()
                .stream()
                .map(post ->new PostDto(
                        post.getId(),
                        post.getCreatedDate(),
                        post.getModifiedDate(),
                        post.getAuthor().getId(),
                        post.getAuthor().getUsername(),
                        post.getTitle(),
                        post.getContent()
                ))
                .toList();
    }


    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public PostDto getPost(@PathVariable Long id){
        return postFacade.findById(id)
                .map(post ->new PostDto(
                        post.getId(),
                        post.getCreatedDate(),
                        post.getModifiedDate(),
                        post.getAuthor().getId(),
                        post.getAuthor().getUsername(),
                        post.getTitle(),
                        post.getContent()
                ))
                .get();
    }
}
