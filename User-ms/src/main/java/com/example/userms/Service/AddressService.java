package com.example.userms.Service;

import com.example.userms.Dto.Request.AddressRequestDto;
import com.example.userms.Dto.Response.AddressResponseDto;
import com.example.userms.Model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    Long createAddress(AddressRequestDto addressRequestDto);

    AddressResponseDto findById(Long id);

    Page<AddressResponseDto> getAll(Pageable pageable);
}
