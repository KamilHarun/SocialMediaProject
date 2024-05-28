package com.example.postms.Config;

import com.example.postms.Dto.Response.PostResponseDto;
import com.example.postms.Model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    PostResponseDto postToResponse(Post post);
}