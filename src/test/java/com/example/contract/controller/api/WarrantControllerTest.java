package com.example.contract.controller.api;

import com.example.contract.exception.AppErrorHandler;
import com.example.contract.domain.warrant.Warrant;
import com.example.contract.service.warrant.WarrantServiceImpl;
import com.example.contract.controller.request.WarrantSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.example.contract.mock.MockUtil.asJsonString;
import static com.example.contract.mock.MockUtil.readJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("담보 컨틀롤러 레이어 에서")
@ExtendWith(SpringExtension.class)
@WebMvcTest(WarrantController.class)
class WarrantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WarrantServiceImpl warrantService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new WarrantController(warrantService)).setControllerAdvice(new AppErrorHandler()).addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
    }

    @Nested
    @DisplayName("생성 API 는")
    class CreatedAPI {

        @Test
        @DisplayName("성공적으로 수행을 하게 됩니다.")
        public void write_ok() throws Exception {

            Warrant mock = readJson("json/warrant/web/save_ok.json", Warrant.class);

            given(warrantService.created(any())).willReturn(mock);

            String uri = "/warrant";

            WarrantSaveRequest dto = readJson("json/warrant/web/warrant_save_request.json", WarrantSaveRequest.class);

            ResultActions action = mockMvc.perform(post(uri).content(asJsonString(dto)).contentType(MediaType.APPLICATION_JSON)).andDo(print());

            then(warrantService).should().created(any());

            action.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(mock.getId())).andExpect(jsonPath("$.title").value(mock.getTitle())).andExpect(jsonPath("$.subscriptionAmount").value(mock.getSubscriptionAmount())).andExpect(jsonPath("$.standardAmount").value(mock.getStandardAmount()));
        }
    }
}
