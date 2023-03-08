package com.example.contract.controller.api;

import com.example.contract.controller.request.ContractSaveRequest;
import com.example.contract.controller.request.ContractUpdateRequest;
import com.example.contract.domain.mapper.ContractDetail;
import com.example.contract.dto.model.contract.ContractSaveModel;
import com.example.contract.dto.model.contract.ContractUpdateModel;
import com.example.contract.dto.response.ContractResponse;
import com.example.contract.service.contract.ContractService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 계약 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    /**
     * 계약 생성 API
     * Http 상태의 경우 201 로 반환 합니다.
     *
     * @param req 계약 생성에 필요한 데이터 {@link ContractSaveRequest}
     *            not null 체크를 합니다.
     * @return 가공된  계약 데이터
     */
    @PostMapping("contract")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse write(@RequestBody @Valid ContractSaveRequest req) {
        ContractSaveModel dto = new ContractSaveModel(req);
        return new ContractResponse(contractService.created(dto));
    }

    /**
     * 계약 상세 데이터 조회 API
     * <p>
     * 만약 데이터가 없으면 204 상태를 하지만 데이터가 있으면 200 으로 보냅니다.
     *
     * @param id 계약 아이디
     * @return 계약 상세 데이터
     */
    @GetMapping("contracts/{id}")
    public ResponseEntity<ContractDetail> selectOne(@PathVariable Long id) {
        Optional<ContractDetail> data = contractService.getOne(id);
        return data.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    /**
     * 계약 수정 API
     *
     * @param id  계약 아이디
     * @param req 계약 변경 데이터 {@link ContractUpdateRequest}
     *            not null 을 체크 합니다.
     * @return 가공된  계약 데이터
     */
    @PatchMapping("contracts/{id}")
    public ContractResponse update(@PathVariable Long id, @RequestBody @Valid ContractUpdateRequest req) {
        ContractUpdateModel dto = new ContractUpdateModel(id, req);
        return new ContractResponse(contractService.update(dto));
    }
}
