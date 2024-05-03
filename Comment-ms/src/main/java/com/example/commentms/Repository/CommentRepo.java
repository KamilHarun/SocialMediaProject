package com.example.commentms.Repository;

import com.example.commentms.Dto.Request.CommentRequestDto;
import com.example.commentms.Model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE LOWER(c.content) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Comment> findByContentContainingIgnoreCase(@Param("query") String query, Pageable pageable);
    @Query("SELECT c FROM Comment c WHERE c.id = :commentId ORDER BY c.updateTime DESC")
    List<CommentRequestDto> findByCommentIdOrderByUpdateTimeDesc(Long id);

}
