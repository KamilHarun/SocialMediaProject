package com.example.userms.Service;

import com.example.userms.Dto.Request.AddressRequestDto;
import com.example.userms.Dto.Response.AddressResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface AddressService {
    Long create(AddressRequestDto addressRequestDto);

    AddressResponseDto findById(Long id);

    Page<AddressResponseDto> getAll(Pageable pageable);
}
