package com.example.userms.API;

import com.example.userms.Dto.Request.AddressRequestDto;
import com.example.userms.Dto.Response.AddressResponseDto;
import com.example.userms.Service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Create a new address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format")
    })
    @PostMapping()
    public ResponseEntity<Long> create(@RequestBody AddressRequestDto addressRequestDto){
        return new ResponseEntity<>(addressService.create(addressRequestDto) , CREATED);
    }


    @Operation(summary = "Get address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address found"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> findById(@RequestParam("id") Long id ) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @Operation(summary = "Get all addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addresses found")
    })

    @GetMapping()
    public ResponseEntity<Page<AddressResponseDto>> getAll(Pageable pageable) {
        Page<AddressResponseDto> addressPage = addressService.getAll(pageable);
        return new ResponseEntity<>(addressPage, OK);
    }
}
