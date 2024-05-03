package com.example.postms.Service.impl;

import com.example.commonsms.Exceptions.NotFoundException;
import com.example.commonsms.Exceptions.UnauthorizedException;
import com.example.postms.Config.PostMapper;
import com.example.postms.Dto.PostRequestDto;
import com.example.postms.Dto.Response.PostResponseDto;
import com.example.postms.Dto.Response.UserResponseDto;
import com.example.postms.Feign.UserFeign;
import com.example.postms.Model.Post;
import com.example.postms.Repository.PostRepo;
import com.example.postms.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final UserFeign userFeign;
    private final PostMapper mapper;


    @Override
    public Long create(String authorizationHeader, PostRequestDto postRequestDto) {
        UserResponseDto userById = userFeign.findById(authorizationHeader, postRequestDto.getUserId());
        if (userById == null) {
            throw new NotFoundException("User not found");
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
        Page<Post> posts = postRepo.findAll(pageable);
        List<PostResponseDto> responses = posts.getContent().stream()
                .map(post -> PostResponseDto.builder()
                        .userId(post.getUserId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build())
                .toList();

        return new PageImpl<>(responses , pageable , posts.getTotalElements());

    }

    public PostResponseDto findById(Long id) {
        Optional<Post> postOptional = postRepo.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            return mapper.postToResponse(post);
        } else {
            throw new NotFoundException("Post not found with id: " + id);
        }
    }

    @Override
    public PostResponseDto update(String authorizationHeader ,  PostRequestDto postRequestDto, Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        UserResponseDto userDto = userFeign.findById(authorizationHeader,postRequestDto.getUserId() );
        if (userDto == null) {
            throw new NotFoundException("User not found");
        }

        if (post.getUserId() != postRequestDto.getUserId()) {
            throw new UnauthorizedException("You are not authorized to update this post");
        }

        post.setContent(postRequestDto.getContent());
        post.setTitle(postRequestDto.getTitle());

        Post updatedPost = postRepo.save(post);
        return mapper.postToResponse(updatedPost);
    }

    @Override
    public void delete(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        postRepo.delete(post);
    }

    @Override
    public void sharePost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));

        if (!post.isShared()) {
            post.setShared(true);
            postRepo.save(post);
        } else {
            throw new IllegalStateException("Post with id " + postId + " is already shared.");
        }
    }

}




