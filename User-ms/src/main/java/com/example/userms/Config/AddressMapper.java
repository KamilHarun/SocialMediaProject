package com.example.userms.Config;

import com.example.userms.Dto.Response.AddressResponseDto;
import com.example.userms.Model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE , componentModel = "spring")
@Component
public interface AddressMapper {
    AddressResponseDto addressToResponseDto(Address address);

}
