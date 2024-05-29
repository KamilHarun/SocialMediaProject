package com.example.messagems.Service.Impl;

import com.example.commonsms.Dto.UserResponseDto;
import com.example.commonsms.Exceptions.NotFoundException;
import com.example.messagems.Config.MessageMapper;
import com.example.messagems.Dto.MessageRequestDto;
import com.example.messagems.Dto.MessageResponseDto;
import com.example.messagems.Feign.UserFeign;
import com.example.messagems.Model.Message;
import com.example.messagems.Repository.MessageRepository;
import com.example.messagems.Service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserFeign userFeign;
    private final MessageMapper messageMapper;

    @Override
    public MessageResponseDto send(String authorizationHeader, MessageRequestDto messageRequestDto) {
        UserResponseDto sender = userFeign.findById(authorizationHeader, messageRequestDto.getSenderId());
        if (sender == null) {
            throw new NotFoundException("Sender not found");
        }

        UserResponseDto receiver = userFeign.findById(authorizationHeader, messageRequestDto.getReceiverId());
        if (receiver == null) {
            throw new NotFoundException("Receiver not found");
        }

        String content = messageRequestDto.getContent();
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }

        Message message = Message.builder()
                .senderId(sender.getId())
                .receiverId(receiver.getId())
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();

        Message saveMessage = messageRepository.save(message);

        return messageMapper.messageToResponse(saveMessage);

    }
    @Override
    public Page<MessageResponseDto> getAllMessages(Pageable pageable) {
        return null;
    }
}
