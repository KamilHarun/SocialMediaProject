package com.example.messagems.Config;

import com.example.messagems.Dto.MessageResponseDto;
import com.example.messagems.Model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
@Component
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageResponseDto messageToResponse(Message message);
    Message responseToMessage(MessageResponseDto messageResponseDto);
}
