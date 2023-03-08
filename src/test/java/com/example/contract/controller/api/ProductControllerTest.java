package com.example.contract.controller.api;

import com.example.contract.exception.AppErrorHandler;
import com.example.contract.exception.DataNotFoundException;
import com.example.contract.domain.entity.product.Product;
import com.example.contract.domain.entity.warrant.Warrant;
import com.example.contract.exception.ErrorCode;
import com.example.contract.mock.product.EstimatedPremiumImpl;
import com.example.contract.service.product.ProductServiceImpl;
import com.example.contract.domain.mapper.EstimatedPremium;
import com.example.contract.controller.request.ProductSaveRequest;
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

import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.example.contract.mock.ConvertUtil.convert;
import static com.example.contract.mock.ConvertUtil.convertProduct;
import static com.example.contract.mock.MockUtil.asJsonString;
import static com.example.contract.mock.MockUtil.readJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("상품 컨틀롤러 레이어에서")
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService))
                .setControllerAdvice(new AppErrorHandler())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Nested
    @DisplayName("총 보험로 계산 API 는")
    class SelectEstimatedPremiumAPI {

        @Test
        @DisplayName("성공적으로 실행을 하게 됩니다.")
        public void selectEstimatedPremium_ok() throws Exception {

            Optional<EstimatedPremium> mockOptional =
                    Optional.of(new EstimatedPremiumImpl(readJson("json/product/service/getEstimatedPremium_ok1.json", Product.class)));

            given(productService.getEstimatedPremium(any())).willReturn(mockOptional);

            Map<String, Object> dto = readJson("json/product/web/getEstimatedPremium_ok_dto.json", Map.class);

            Integer productId = (Integer) dto.get("productId");

            String uri = "/products/" + productId + "/premium";

            ResultActions action = mockMvc.perform(
                            get(uri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("warrantIds", (String) dto.get("warrantIds"))
                    )
                    .andDo(print());

            EstimatedPremium mock = mockOptional.get();

            then(productService).should().getEstimatedPremium(any());

            action.andExpect(status().isOk())
                    .andExpect(jsonPath("$.term").value(mock.getTerm()))
                    .andExpect(jsonPath("$.premium").value(mock.getPremium().setScale(1, RoundingMode.FLOOR)))
                    .andExpect(jsonPath("$.productTitle").value(mock.getProductTitle()));
        }

        @Test
        @DisplayName("데이터가 없다면, Http Status를 204 (No Content)를 반환을 하게 됩니다.")
        public void selectEstimatedPremium_fail1() throws Exception {

            given(productService.getEstimatedPremium(any())).willReturn(Optional.empty());

            Map<String, Object> dto = readJson("json/product/web/getEstimatedPremium_ok_dto.json", Map.class);

            Integer productId = (Integer) dto.get("productId");

            String uri = "/products/" + productId + "/premium";

            ResultActions action = mockMvc.perform(
                            get(uri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("warrantIds", (String) dto.get("warrantIds"))
                    )
                    .andDo(print());

            then(productService).should().getEstimatedPremium(any());

            action.andExpect(status().isNoContent());
        }

    }

    @Nested
    @DisplayName("생성 API 는")
    class CreatedAPI {

        @Test
        @DisplayName("성공적으로 실행을 하게 됩니다.")
        public void write_ok() throws Exception {

            Map map = readJson("json/product/web/write_ok.json", Map.class);

            Set<Warrant> warrantSet = convert(map.get("warrant"));

            Product mock = convert(convertProduct((Map) map.get("product")), warrantSet);

            given(productService.created(any())).willReturn(mock);

            String uri = "/product";

            ProductSaveRequest dto = readJson("json/product/web/product_save_request.json", ProductSaveRequest.class);

            ResultActions action = mockMvc.perform(
                            post(uri)
                                    .content(asJsonString(dto))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print());

            then(productService).should().created(any());

            action.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(mock.getId()))
                    .andExpect(jsonPath("$.title").value(mock.getTitle()))
                    .andExpect(jsonPath("$.term.startMonth").value(mock.getTerm().getStartMonth()))
                    .andExpect(jsonPath("$.term.endMonth").value(mock.getTerm().getEndMonth()))
                    .andExpect(jsonPath("$.term.range").value(mock.getTerm().getRange()))
                    .andExpect(jsonPath("$.warrantIds.size()").value(mock.getWarrants().size()));
        }

        @Test
        @DisplayName("담보 데이터가 없을 때, Not_Found_Data 를 반환을 하게 됩니다.")
        public void write_fail1() throws Exception {

            String msg = "Data Not Found";

            given(productService.created(any())).willThrow(new DataNotFoundException(msg));

            String uri = "/product";

            ProductSaveRequest dto = readJson("json/product/web/product_save_request.json", ProductSaveRequest.class);

            ResultActions action = mockMvc.perform(
                            post(uri)
                                    .content(asJsonString(dto))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print());

            then(productService).should().created(any());

            ErrorCode errorCode = ErrorCode.NOT_FOUND_DATA;

            action.andExpect(status().isNoContent())
                    .andExpect(jsonPath("$.code").value(errorCode.getCode()))
                    .andExpect(jsonPath("$.message").value(errorCode.convertMessage(msg)));
        }
    }
}
