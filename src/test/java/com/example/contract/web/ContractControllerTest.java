package com.example.contract.web;

import com.example.contract.doamin.Contract;
import com.example.contract.enums.ContractState;
import com.example.contract.service.ContractService;
import com.example.contract.web.dto.ContractSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Map;

import static com.example.contract.mock.ConvertUtil.convertContract;
import static com.example.contract.mock.MockUtil.asJsonString;
import static com.example.contract.mock.MockUtil.readJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("거래 데이터 컨트롤러 레이어")
@ExtendWith(SpringExtension.class)
@WebMvcTest(ContractControllerTest.class)
class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ContractController(contractService))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    public void write_ok() throws Exception {

        Map mockMap = readJson("json/contract/web/write_ok.json", Map.class);

        Contract mock = convertContract((Map) mockMap.get("contract"), (Map) mockMap.get("warrant"), ContractState.NORMAL);

        given(contractService.created(any())).willReturn(mock);

        String uri = "/contract";

        ContractSaveRequest dto = readJson("json/contract/web/contract_save_request.json", ContractSaveRequest.class);

        ResultActions action = mockMvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print());

        then(contractService).should().created(any());

        action
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mock.getId()))
                .andExpect(jsonPath("$.productId").value(mock.getProduct().getId()))
                .andExpect(jsonPath("$.warrantIds.size()").value(mock.getWarrants().size()))
                        .andExpect(jsonPath("$.term").value(mock.getTerm()))
                        .andExpect(jsonPath("$.startDate").value(mock.getStartDate()))
                        .andExpect(jsonPath("$.endDate").value(mock.getEndDate()))
                        .andExpect(jsonPath("$.premium").value(mock.getPremium()))
                        .andExpect(jsonPath("$.state").value(mock.getState().name()));

    }
}
