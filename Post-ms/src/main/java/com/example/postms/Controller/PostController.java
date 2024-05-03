package com.example.postms.Controller;

import com.example.postms.Dto.PostRequestDto;
import com.example.postms.Dto.Response.PostResponseDto;
import com.example.postms.Service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping
    @Operation(summary = "Create a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Long> createPost(@Valid @RequestHeader("Authorization") String authorizationHeader, @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity<>(postService.create(authorizationHeader, postRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the post"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PostResponseDto> findByID(@PathVariable Long id){
        return new ResponseEntity<>(postService.findById(id) , HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all posts"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Page<PostResponseDto> getAll (Pageable pageable){
        return postService.getAll(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PostResponseDto> updatePost(@Valid @RequestHeader String authorizationHeader ,  @RequestBody PostRequestDto postRequestDto ,
                                                      @PathVariable Long id){
        return new ResponseEntity<>(postService.update(authorizationHeader,postRequestDto,id) , HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Delete a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deletePost (@RequestParam Long id){
        postService.delete(id);
    }

    @PostMapping("/{postId}/share")
    @Operation(summary = "Share a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post shared successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<Void> sharePost(@PathVariable Long postId) {
        postService.sharePost(postId);
        return ResponseEntity.ok().build();
    }

}

