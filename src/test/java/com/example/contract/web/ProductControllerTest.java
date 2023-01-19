package com.example.contract.web;

import com.example.contract.config.exception.AppErrorHandler;
import com.example.contract.doamin.Product;
import com.example.contract.mock.EstimatedPremiumImpl;
import com.example.contract.service.ProductService;
import com.example.contract.web.dto.EstimatedPremium;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import static com.example.contract.mock.MockUtil.readJson;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("상품 컨틀롤러 레이어")
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService))
                .setControllerAdvice(new AppErrorHandler())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("예상 총 보험 계약 API 테스트 케이스")
    public void selectEstimatedPremium_ok () throws Exception {

        Optional<EstimatedPremium> mockOptional =
                Optional.of(new EstimatedPremiumImpl(readJson("json/product/service/getEstimatedPremium_ok1.json", Product.class)));

        given(productService.getEstimatedPremium(any() , any())).willReturn(mockOptional);

        Map<String, Object> dto = readJson("json/product/web/getEstimatedPremium_ok_dto.json", Map.class);

        Integer productId = (Integer) dto.get("productId");

        String uri = "/products/"+productId+"/premium";

        ResultActions action = mockMvc.perform(
                        get(uri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("warrantIds" , (String) dto.get("warrantIds"))
                )
                .andDo(print());

        EstimatedPremium mock = mockOptional.get();

        then(productService).should().getEstimatedPremium(any() , any());

        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.term").value(mock.getTerm()))
                .andExpect(jsonPath("$.premium").value(mock.getPremium().setScale(1 , RoundingMode.FLOOR)))
                .andExpect(jsonPath("$.productTitle").value(mock.getProductTitle()));
    }
}
