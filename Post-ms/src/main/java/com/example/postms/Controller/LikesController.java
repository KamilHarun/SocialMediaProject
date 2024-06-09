package com.example.postms.Controller;

import com.example.postms.Service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Like a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post liked successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<Void> likePost(@RequestHeader("Authorization") String authorizationHeader ,  @PathVariable Long postId, @RequestParam Long userId, @RequestParam boolean like) {
        likesService.likeOrDislikePost(authorizationHeader,postId, like, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/dislike")
    @Operation(summary = "Dislike a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post disliked successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<Void> dislikePost(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long postId, @RequestParam Long userId, @RequestParam boolean dislike) {
        likesService.likeOrDislikePost( authorizationHeader, postId, dislike, userId);
        return ResponseEntity.ok().build();
    }

}

