package com.example.userms.Service.Impl;

import com.example.commonsms.Exceptions.AddressExistException;
import com.example.commonsms.Exceptions.AddressNotFound;
import com.example.commonsms.Exceptions.ErrorMessage;
import com.example.commonsms.Exceptions.NotFoundException;
import com.example.userms.Config.AddressMapper;
import com.example.userms.Dto.Request.AddressRequestDto;
import com.example.userms.Dto.Response.AddressResponseDto;
import com.example.userms.Model.Address;
import com.example.userms.Repository.AddressRepo;
import com.example.userms.Service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.commonsms.Exceptions.ErrorMessage.ADDRESS_ALREADY_EXIST_EXCEPTION;
import static com.example.commonsms.Exceptions.ErrorMessage.ADDRESS_NOT_FOUND_EXCEPTION;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepo addressRepo;
    private final AddressMapper addressMapper;

    @Override
    public Long create(AddressRequestDto addressRequestDto) {
        Optional<Address> existingAddress = addressRepo.findById(addressRequestDto.getId()
        );
        if (existingAddress.isPresent()) {
            throw new AddressExistException(ADDRESS_ALREADY_EXIST_EXCEPTION);
        }
        Address address = Address.builder()
                .city(addressRequestDto.getCity())
                .country(addressRequestDto.getCountry())
                .street(addressRequestDto.getStreet())
                .postalCode(addressRequestDto.getPostalCode())
                .build();

        Address savedAddress = addressRepo.save(address);
        return savedAddress.getId();
    }
    @Override
    @Cacheable(value = "findById" , key = "#id")
    public AddressResponseDto findById(Long id) {
        Address address = addressRepo.findById(id).orElseThrow(() ->
                new NotFoundException(ADDRESS_NOT_FOUND_EXCEPTION));
        return addressMapper.addressToResponseDto(address);
    }

    @Override
    @Cacheable(value = "getAll" , key = "pageable.pageNumber + '-' + pageable.pageSize + '-' + pageable.sort.toString()")
    public Page<AddressResponseDto> getAll(Pageable pageable) {
        Page<Address> addresses = addressRepo.findAll(pageable);
        if (addresses.isEmpty()
        ) {
            throw new AddressNotFound(ADDRESS_NOT_FOUND_EXCEPTION);
        }
        List<AddressResponseDto> addressResponseDto = addresses.getContent().stream()
                .map(addressMapper::addressToResponseDto)
                .collect(Collectors.toList());
        return new PageImpl<>(addressResponseDto, pageable, addresses.getTotalElements());
    }
}
