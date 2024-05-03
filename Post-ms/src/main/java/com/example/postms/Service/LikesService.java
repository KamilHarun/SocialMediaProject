package com.example.postms.Service;

public interface LikesService {
    void likeOrDislikePost(String authorizationHeader ,  Long postId, boolean like, Long userId);
}
