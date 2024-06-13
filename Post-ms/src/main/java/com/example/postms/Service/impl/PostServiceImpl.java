package com.example.postms.Service.impl;

import com.example.commonsms.Dto.UserResponseDto;
import com.example.commonsms.Exceptions.ErrorMessage;
import com.example.commonsms.Exceptions.NotFoundException;
import com.example.commonsms.Exceptions.UnauthorizedException;
import com.example.postms.Config.PostMapper;
import com.example.postms.Dto.PostRequestDto;
import com.example.postms.Dto.Response.PostResponseDto;
import com.example.postms.Feign.UserFeign;
import com.example.postms.Model.Post;
import com.example.postms.Repository.PostRepo;
import com.example.postms.Service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.commonsms.Exceptions.ErrorMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final UserFeign userFeign;
    private final PostMapper mapper;


    @Override
    public Long create(String authorizationHeader, PostRequestDto postRequestDto) {
        log.info("Create a new Post");
        UserResponseDto userById = userFeign.findById(authorizationHeader, postRequestDto.getUserId());
        if (userById == null) {
            log.warn("User with ID : {} not found" , postRequestDto.getUserId());
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }
        Post post = Post.builder()
                .userId(postRequestDto.getUserId())
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .createdAt(new Date())
                .build();
        Post save = postRepo.save(post);
        return save.getId();
    }

    @Override
    public Page<PostResponseDto> getAll(Pageable pageable) {
        log.info("Getting all Posts with pagination");
        Page<Post> posts = postRepo.findAll(pageable);

        if (posts.isEmpty()) {
            log.warn("Post not found");
            throw new NotFoundException(POST_NOT_FOUND_EXCEPTION);
        }
        List<PostResponseDto> responses = posts.getContent().stream()
                .map(post -> PostResponseDto.builder()
                        .userId(post.getUserId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build())
                .toList();

        return new PageImpl<>(responses , pageable , posts.getTotalElements());

    }

    @Override
    public PostResponseDto findById(Long id) {
        log.info("Fetching post By ID : {}" , id);
        Optional<Post> postOptional = postRepo.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            return mapper.postToResponse(post);
        } else {
            throw new NotFoundException(POST_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    public PostResponseDto update(String authorizationHeader ,  PostRequestDto postRequestDto, Long id) {
        log.info("Updating post with ID: {}", id);
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND_EXCEPTION));

        UserResponseDto userDto = userFeign.findById(authorizationHeader,postRequestDto.getUserId() );
        if (userDto == null) {
            throw new NotFoundException(USER_NOT_FOUND_EXCEPTION);
        }

        if (post.getUserId() != postRequestDto.getUserId()) {
            throw new UnauthorizedException(UNAUTHORIZED_EXCEPTION);
        }

        post.setContent(postRequestDto.getContent());
        post.setTitle(postRequestDto.getTitle());

        Post updatedPost = postRepo.save(post);
        return mapper.postToResponse(updatedPost);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting post with ID: {}", id);
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND_EXCEPTION));
        postRepo.delete(post);
    }

    @Override
    public void sharePost(Long postId) {
        log.info("Sharing post with ID: {}", postId);
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND_WITH_ID_EXCEPTION, postId));

        if (!post.isShared()) {
            post.setShared(true);
            postRepo.save(post);
        } else {
            throw new IllegalStateException(POST_ALREADY_SHARED.format(postId));
        }
    }

}




