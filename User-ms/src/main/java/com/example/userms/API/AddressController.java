package com.example.userms.API;

import com.example.userms.Config.AddressMapper;
import com.example.userms.Dto.Request.AddressRequestDto;
import com.example.userms.Dto.Response.AddressResponseDto;
import com.example.userms.Model.Address;
import com.example.userms.Service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Create a new address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format")
    })
    @PostMapping
    public ResponseEntity<Long> createAddress(@RequestBody AddressRequestDto addressRequestDto) {
        Long addressId = addressService.createAddress(addressRequestDto);
        return new ResponseEntity<>(addressId, HttpStatus.CREATED);
    }

    @Operation(summary = "Get address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address found"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getAddressById(@RequestParam("id") Long id ) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @Operation(summary = "Get all addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addresses found")
    })
    @GetMapping()
    public ResponseEntity<Page<AddressResponseDto>> getAllAddresses(Pageable pageable) {
        Page<AddressResponseDto> addressPage = addressService.getAll(pageable);
        return new ResponseEntity<>(addressPage, HttpStatus.OK);
    }
}