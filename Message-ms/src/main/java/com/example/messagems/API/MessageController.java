package com.example.messagems.API;

import com.example.messagems.Dto.MessageRequestDto;
import com.example.messagems.Dto.MessageResponseDto;
import com.example.messagems.Service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor

public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<MessageResponseDto> send ( @Valid @RequestHeader("Authorization") String authorizationHeader ,
                                                     @RequestBody MessageRequestDto messageRequestDto){
        return new ResponseEntity<>(messageService.send(authorizationHeader, messageRequestDto) , OK);
    }

    @GetMapping()
    public ResponseEntity<Page< MessageResponseDto>> getAllMessages(Pageable pageable){
        return new ResponseEntity<>(messageService.getAllMessages(pageable) , OK);
    }


}
