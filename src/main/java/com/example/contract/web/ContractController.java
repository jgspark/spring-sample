package com.example.contract.web;

import com.example.contract.service.ContractService;
import com.example.contract.web.dto.ContractResponse;
import com.example.contract.web.dto.ContractSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse write(@RequestBody ContractSaveRequest dto) {
        return contractService.created(dto);
    }
}
