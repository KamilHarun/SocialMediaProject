package com.example.messagems.Service;

import com.example.messagems.Dto.MessageRequestDto;
import com.example.messagems.Dto.MessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    MessageResponseDto send(String authorizationHeader ,  MessageRequestDto messageRequestDto);

    Page<MessageResponseDto> getAllMessages(Pageable pageable);

    MessageResponseDto update(Long id, MessageRequestDto messageRequestDto);

    MessageResponseDto findById(Long id);

    List<MessageResponseDto> getMessagesByUser(String authorizationHeader , Long userId);
}
