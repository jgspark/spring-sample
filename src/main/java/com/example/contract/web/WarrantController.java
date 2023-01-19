package com.example.contract.web;

import com.example.contract.service.WarrantService;
import com.example.contract.web.dto.WarrantResponse;
import com.example.contract.web.dto.WarrantSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class WarrantController {

    private final WarrantService warrantService;

    @PostMapping("warrant")
    @ResponseStatus(HttpStatus.CREATED)
    public WarrantResponse write(@RequestBody @Valid WarrantSaveRequest dto) {
        return new WarrantResponse(warrantService.created(dto));
    }
}
