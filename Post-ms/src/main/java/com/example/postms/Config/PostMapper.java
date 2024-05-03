package com.example.postms.Config;

import com.example.postms.Dto.PostRequestDto;
import com.example.postms.Dto.Response.PostResponseDto;
import com.example.postms.Model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE , componentModel = "spring")
@Component
public interface PostMapper {

     PostResponseDto postToResponse(Post post);
     Post requestToPost(PostRequestDto postRequestDto);
}
