package com.example.messagems.API;

import com.example.messagems.Dto.MessageRequestDto;
import com.example.messagems.Dto.MessageResponseDto;
import com.example.messagems.Service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor

public class MessageController {
    private final MessageService messageService;
    @Operation(
            summary = "Send a message",
            description = "Send a new message with the given details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully sent message"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping("/send")
    public ResponseEntity<MessageResponseDto> send(
            @Parameter(description = "Authorization token", required = true) @Valid @RequestHeader("Authorization") String authorizationHeader,
            @Parameter(description = "Message details", required = true) @RequestBody MessageRequestDto messageRequestDto) {
        return new ResponseEntity<>(messageService.send(authorizationHeader, messageRequestDto), OK);
    }

    @Operation(
            summary = "Get all messages",
            description = "Retrieve a paginated list of all messages",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved messages")
            }
    )
    @GetMapping()
    public ResponseEntity<Page<MessageResponseDto>> getAllMessages(Pageable pageable) {
        return new ResponseEntity<>(messageService.getAllMessages(pageable), OK);
    }
    @Operation(
            summary = "Update a message",
            description = "Update the details of an existing message",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated message"),
                    @ApiResponse(responseCode = "404", description = "Message not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDto> updateMessage(@RequestParam("id") Long id ,
                                                            @RequestBody MessageRequestDto messageRequestDto){
        return new ResponseEntity<>(messageService.update(id ,messageRequestDto) , OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<MessageResponseDto> getMessageById(@RequestParam Long id){
        return new ResponseEntity<>(messageService.findById(id) ,OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MessageResponseDto>> getMessageByUser(@RequestHeader("Authorization") String authorizationHeader ,
                                                                     @RequestParam Long userId){
        return new ResponseEntity<>(messageService.getMessagesByUser( authorizationHeader, userId) , OK);

    }


}


