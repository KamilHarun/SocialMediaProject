package com.example.commentms.java.com.example.commentms.Service;

import com.example.commentms.java.com.example.commentms.Dto.Request.CommentRequestDto;
import com.example.commentms.java.com.example.commentms.Dto.Response.CommentResponseDto;
import com.example.commentms.java.com.example.commentms.Model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Long createComment(CommentRequestDto commentRequestDto);

    CommentResponseDto findById(Long id);

    Page<CommentResponseDto> getAllComments(Pageable pageable);

    CommentResponseDto update(CommentRequestDto commentRequestDto, Long id);

    void delete(Long id);

    Page<CommentResponseDto> searchCommentsByContent(String query, Pageable pageable);

    List<Comment> getCommentEditHistory(Long id);
}
