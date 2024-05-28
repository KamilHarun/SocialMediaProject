package com.example.userms.Config;

import com.example.userms.Dto.Response.UserResponseDto;
import com.example.userms.Model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE , componentModel = "spring")
public interface UserMapper {

    UserResponseDto UserToDto (Users users );
    Users DtoToUsers(UserResponseDto userResponseDto);


}