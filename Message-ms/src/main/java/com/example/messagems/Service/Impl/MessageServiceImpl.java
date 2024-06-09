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
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return messageRepository.findAll(pageable).map(MessageResponseDto::new);
    }

    @Override
    public MessageResponseDto update(Long id, MessageRequestDto messageRequestDto) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));

        message.setSenderId(messageRequestDto.getSenderId());
        message.setReceiverId(messageRequestDto.getReceiverId());
        message.setContent(messageRequestDto.getContent());
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);

        return messageMapper.messageToResponse(message);
    }

    @Override
    public MessageResponseDto findById(Long id) {
        Optional<Message> byId = messageRepository.findById(id);
        Message message = byId.orElseThrow(() ->
                new NotFoundException("Message not found wtih this ID : " + id));

        return messageMapper.messageToResponse(message);
    }

    @Override
    public MessageResponseDto getMessagesByUser(String authorizationHeader, Long userId) {

        UserResponseDto byId = userFeign.findById(authorizationHeader, userId);
        if (byId == null) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        List<Message> messages = messageRepository.findBySenderIdOrReceiverId(userId, userId);

        if (messages.isEmpty()) {
            throw new ResourceNotFoundException("Messages not found for user with id: " + userId);
        }

        List<MessageResponseDto> responseDtos = new ArrayList<>();
        for (Message message : messages) {
            MessageResponseDto responseDto = new MessageResponseDto();
            responseDto.setId(message.getId());
            responseDto.setSenderId(message.getSenderId());
            responseDto.setReceiverId(message.getReceiverId());
            responseDto.setContent(message.getContent());
            // Diğer gerekli alanları set et
            responseDtos.add(responseDto);
        }

        return responseDtos.get(0); // İlk mesajı döndür
    }
}





