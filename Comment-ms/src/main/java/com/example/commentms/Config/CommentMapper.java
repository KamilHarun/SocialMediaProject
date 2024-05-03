package com.example.commentms.Config;

import com.example.commentms.Dto.Request.CommentRequestDto;
import com.example.commentms.Dto.Response.CommentResponseDto;
import com.example.commentms.Model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "userId", source = "user.id")
    CommentResponseDto commentToResponse(Comment comment);

    @Mapping(target = "user.id", source = "userId")
    Comment responseToComment(CommentResponseDto commentResponseDto);

    @Mapping(target = "userId", source = "user.id")
    CommentRequestDto commentToRequest(Comment comment);

    CommentResponseDto commentToDto(Comment comment);

    List<Comment> toDtoList(List editHistoryList);
}