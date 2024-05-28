package com.example.messagems.Config;

import com.example.messagems.Dto.MessageResponseDto;
import com.example.messagems.Model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
@Component
public interface MessageMapper {
    MessageResponseDto messageToResponse(Message message);
    Message responseToMessage(MessageResponseDto messageResponseDto);
}
