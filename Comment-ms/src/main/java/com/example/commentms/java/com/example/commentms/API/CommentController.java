package com.example.commentms.java.com.example.commentms.API;

import com.example.commentms.java.com.example.commentms.Dto.Request.CommentRequestDto;
import com.example.commentms.java.com.example.commentms.Dto.Response.CommentResponseDto;
import com.example.commentms.java.com.example.commentms.Model.Comment;
import com.example.commentms.java.com.example.commentms.Service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

        @PostMapping
        @Operation(summary = "Create a new comment")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Comment created successfully", content = @Content(schema = @Schema(implementation = Long.class))),
                @ApiResponse(responseCode = "400", description = "Invalid input"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        public ResponseEntity<Long> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {
            return new ResponseEntity<>(commentService.createComment(commentRequestDto), HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Find a comment by ID")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Found the comment", content = @Content(schema = @Schema(implementation = CommentResponseDto.class))),
                @ApiResponse(responseCode = "404", description = "Comment not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        public ResponseEntity<CommentResponseDto> getCommentFindById(@PathVariable Long id){
            return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
        }

        @GetMapping
        @Operation(summary = "Get all comments")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Retrieved all comments", content = @Content(schema = @Schema(implementation = Page.class))),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        public Page<CommentResponseDto> getAllComments(Pageable pageable){
            return commentService.getAllComments(pageable);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Update a comment")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Comment updated successfully", content = @Content(schema = @Schema(implementation = CommentResponseDto.class))),
                @ApiResponse(responseCode = "400", description = "Invalid input"),
                @ApiResponse(responseCode = "404", description = "Comment not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        public ResponseEntity<CommentResponseDto> updateComment(@RequestBody CommentRequestDto commentRequestDto ,
                                                                @PathVariable Long id){
            return new ResponseEntity<>(commentService.update(commentRequestDto , id), HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete a comment by ID")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        public void deleteComment(@PathVariable Long id ){
            commentService.delete(id);
        }

    @GetMapping("/comments/search")
    @Operation(summary = "Search comments by content")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found", content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "Comments not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Page<CommentResponseDto> searchCommentsByContent(@RequestParam String query, Pageable pageable) {
        return commentService.searchCommentsByContent(query, pageable);
    }


    @GetMapping("/comments/{id}/history")
    @Operation(summary = "Get comment edit history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved comment edit history"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Comment> getCommentEditHistory(@PathVariable Long id) {
        return commentService.getCommentEditHistory(id);
    }
    }

