package com.example.postms.Service.impl;

import com.example.commonsms.Exceptions.NotFoundException;
import com.example.postms.Dto.Response.UserResponseDto;
import com.example.postms.Feign.UserFeign;
import com.example.postms.Model.Likes;
import com.example.postms.Model.Post;
import com.example.postms.Repository.LikesRepo;
import com.example.postms.Repository.PostRepo;
import com.example.postms.Service.LikesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final PostRepo postRepo;
    private final LikesRepo likesRepo;
    private final UserFeign userFeign;

    @Transactional
    @Override
    public void likeOrDislikePost(String authorizationHeader, Long postId, boolean like, Long userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));

       userFeign.findById(authorizationHeader, userId);

        Likes likesEntity = likesRepo.findByPostAndUserId(post, userId);
        if (likesEntity == null) {
            likesEntity = new Likes();
        }

        likesEntity.setPost(post);
        likesEntity.setUserId(userId);
        likesEntity.setLikes(like);

        likesRepo.save(likesEntity);
    }
}
