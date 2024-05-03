package com.example.postms.Service;

import com.example.postms.Dto.PostRequestDto;
import com.example.postms.Dto.Response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Long create(String authorizationHeader,PostRequestDto postRequestDto);

    Page<PostResponseDto> getAll(Pageable pageable);

    PostResponseDto findById(Long id);

    PostResponseDto update(String authorizationHeader ,  PostRequestDto postRequestDto, Long id);

    void delete(Long id);


    void sharePost(Long postId);

}

