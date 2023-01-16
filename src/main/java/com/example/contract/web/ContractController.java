package com.example.contract.web;

import com.example.contract.service.ContractService;
import com.example.contract.web.dto.ContractDetail;
import com.example.contract.web.dto.ContractResponse;
import com.example.contract.web.dto.ContractSaveRequest;
import com.example.contract.web.dto.ContractUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping("contract")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse write(@RequestBody ContractSaveRequest dto) {
        return new ContractResponse(contractService.created(dto));
    }

    @GetMapping("contracts/{id}")
    public ResponseEntity<ContractDetail> selectOne(@PathVariable Long id) {
        Optional<ContractDetail> data = contractService.getOne(id);
        return data.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PatchMapping("contracts/{id}")
    public ContractResponse update(@PathVariable Long id, @RequestBody ContractUpdateRequest dto) {
        return new ContractResponse(contractService.update(id, dto));
    }
}
