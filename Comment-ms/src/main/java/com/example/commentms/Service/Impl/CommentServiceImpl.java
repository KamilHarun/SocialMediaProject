package com.example.commentms.Service.Impl;

import com.example.commentms.Config.CommentMapper;
import com.example.commentms.Dto.Request.CommentRequestDto;
import com.example.commentms.Dto.Response.CommentResponseDto;
import com.example.commentms.Model.Comment;
import com.example.commentms.Repository.CommentRepo;
import com.example.commentms.Service.CommentService;
import com.example.commonms.Exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    @Override
    public Long createComment(CommentRequestDto commentRequestDto) {
        Comment comment = Comment.builder()
                .commentedPostId(commentRequestDto.getCommentedPostId())
                .text(commentRequestDto.getText())
                .build();
        Comment save = commentRepo.save(comment);
        return save.getId();
    }

    @Override
    public CommentResponseDto findById(Long id) {
        Optional<Comment> byId = commentRepo.findById(id);
        if (byId.isPresent()) {
            Comment comment = byId.get();
            return commentMapper.commentToResponse(comment);
        }
        else {
            throw new NotFoundException("Comment not found");
        }
    }

    @Override
    public Page<CommentResponseDto> getAllComments(Pageable pageable) {
        Page<Comment> all = commentRepo.findAll(pageable);
        List<CommentResponseDto> commentResponseDtoList = all.getContent().stream()
                .map(comment -> CommentResponseDto.builder()
                        .commentedPostId(comment.getCommentedPostId())
                        .text(comment.getText())
                        .build())
                .toList();
        return new PageImpl<>(commentResponseDtoList,pageable, all.getTotalElements());
    }

    @Override
    public CommentResponseDto update(CommentRequestDto commentRequestDto, Long id) {
        Comment existingComment = commentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        existingComment.setCommentedPostId(commentRequestDto.getCommentedPostId());
        existingComment.setText(commentRequestDto.getText());
        Comment updatedComment = commentRepo.save(existingComment);
        return commentMapper.commentToResponse(updatedComment);
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Comment not found"));
        commentRepo.delete(comment);

    }

    @Override
    public Page<CommentResponseDto> searchCommentsByContent(String query, Pageable pageable) {
        Page<Comment> commentsPage = commentRepo.findByContentContainingIgnoreCase(query, pageable);

        List<CommentResponseDto> commentResponseDtos = commentsPage.getContent()
                .stream()
                .map(CommentMapper.INSTANCE::commentToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(commentResponseDtos, pageable, commentsPage.getTotalElements());
    }


    @Override
    public List<Comment> getCommentEditHistory(Long id) {
        List<CommentRequestDto> editHistoryList = commentRepo.findByCommentIdOrderByUpdateTimeDesc(id);

        return commentMapper.toDtoList(editHistoryList);
    }
}
