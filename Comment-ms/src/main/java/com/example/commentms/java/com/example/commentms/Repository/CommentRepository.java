package com.example.commentms.java.com.example.commentms.Repository;

import com.example.commentms.java.com.example.commentms.Dto.Request.CommentRequestDto;
import com.example.commentms.java.com.example.commentms.Model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Long> {
    Page<Comment> findByContentContainingIgnoreCase(String query, Pageable pageable);

    List<CommentRequestDto> findByCommentIdOrderByUpdateTimeDesc(Long id);


}

