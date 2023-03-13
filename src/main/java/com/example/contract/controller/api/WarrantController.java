package com.example.contract.controller.api;

import com.example.contract.dto.request.WarrantSaveRequest;
import com.example.contract.dto.model.warrant.WarrantSaveModel;
import com.example.contract.dto.response.WarrantResponse;
import com.example.contract.service.warrant.WarrantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WarrantController {

    private final WarrantService warrantService;

    /**
     * 담보 생성 API
     *
     * @param req 담보생성에 필요한 데이터 {@link WarrantSaveRequest}
     * @return 담보 데이터
     */
    @PostMapping("warrant")
    @ResponseStatus(HttpStatus.CREATED)
    public WarrantResponse write(@RequestBody @Valid WarrantSaveRequest req) {
        WarrantSaveModel dto = new WarrantSaveModel(req);
        return new WarrantResponse(warrantService.created(dto));
    }
}
