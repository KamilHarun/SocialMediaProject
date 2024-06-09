package com.example.postms.Service.impl;

import com.example.commonsms.Exceptions.NotFoundException;
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

        Likes likesEntity = likesRepo.findByPostAndUserId(post, userId);
        boolean isNewLike = (likesEntity == null);

        if (isNewLike) {
            likesEntity = new Likes();
            likesEntity.setPost(post);
            likesEntity.setUserId(userId);
            post.setLikeCount(post.getLikeCount() + (like ? 1 : 0));
        } else {
            if (likesEntity.isLikes() != like) {
                post.setLikeCount(post.getLikeCount() + (like ? 1 : -1));
            }
        }

        likesEntity.setLikes(like);
        likesRepo.save(likesEntity);
        postRepo.save(post);
    }
}

