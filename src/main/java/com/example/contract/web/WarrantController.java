package com.example.contract.web;

import com.example.contract.service.WarrantService;
import com.example.contract.data.dto.WarrantResponse;
import com.example.contract.data.dto.WarrantSaveRequest;
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

    /**
     * 담보 생성 API
     *
     * @param dto 담보생성에 필요한 데이터 {@link WarrantSaveRequest}
     * @return 담보 데이터
     */
    @PostMapping("warrant")
    @ResponseStatus(HttpStatus.CREATED)
    public WarrantResponse write(@RequestBody @Valid WarrantSaveRequest dto) {
        return new WarrantResponse(warrantService.created(dto));
    }
}
