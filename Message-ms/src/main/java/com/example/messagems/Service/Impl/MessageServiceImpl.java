package com.example.messagems.Service.Impl;

import com.example.commonsms.Dto.UserResponseDto;
import com.example.commonsms.Exceptions.MessageNotFoundException;
import com.example.commonsms.Exceptions.NotFoundException;
import com.example.commonsms.Exceptions.UserNotFoundException;
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
import org.springframework.data.domain.PageImpl;
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

import static com.example.commonsms.Exceptions.ErrorMessage.*;

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
            throw new UserNotFoundException(SENDER_NOT_FOUND_EXCEPTION);
        }

        UserResponseDto receiver = userFeign.findById(authorizationHeader, messageRequestDto.getReceiverId());
        if (receiver == null) {
            throw new UserNotFoundException(RECEIVER_NOT_F0UND_EXCEPTION);
        }

        String content = messageRequestDto.getContent();
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException(String.valueOf(MESSAGE_CONTENT_EXCEPTION));
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
        log.info("Getting all message with pagination");
        Page<Message> messages = messageRepository.findAll(pageable);
        if (messages.isEmpty()) {
            throw new MessageNotFoundException(MESSAGE_NOT_FOUND_EXCEPTION);
        }
        return new PageImpl<>(messages.getContent().stream()
                .map(message -> MessageResponseDto.builder()
                        .senderId(message.getSenderId())
                        .receiverId(message.getReceiverId())
                        .content(message.getContent())
                        .build())
                .collect(Collectors.toList()), pageable, messages.getTotalElements());
    }


    @Override
    public MessageResponseDto update(Long id, MessageRequestDto messageRequestDto) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(MESSAGE_NOT_FOUND_WITH_ID_EXCEPTION , id));

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
                new MessageNotFoundException(MESSAGE_NOT_FOUND_WITH_ID_EXCEPTION , id));

        return messageMapper.messageToResponse(message);
    }

    @Override
    public List<MessageResponseDto> getMessagesByUser(String authorizationHeader, Long userId) {

        UserResponseDto byId = userFeign.findById(authorizationHeader, userId);
        if (byId == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_WITH_ID_EXCEPTION , userId);
        }

        List<MessageResponseDto> messages = messageRepository.findBySenderIdOrReceiverId(userId, userId);

        if (messages.isEmpty()) {
            throw new MessageNotFoundException(MESSAGE_NOT_FOUND_WITH_ID_EXCEPTION , userId);
        }

        return messages;
    }

}





