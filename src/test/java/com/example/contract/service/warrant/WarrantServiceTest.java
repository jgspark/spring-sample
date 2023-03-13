package com.example.contract.service.warrant;

import com.example.contract.domain.entity.warrant.Warrant;
import com.example.contract.dto.model.warrant.WarrantSaveModel;
import com.example.contract.repository.WarrantRepository;
import com.example.contract.dto.request.WarrantSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.contract.mock.MockUtil.readJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("담보 생성 서비스 에서")
@ExtendWith(MockitoExtension.class)
class WarrantServiceTest {

    private WarrantService warrantService;

    @Mock
    private WarrantRepository warrantRepository;

    @BeforeEach
    public void init() {
        warrantService = new WarrantServiceImpl(warrantRepository);
    }

    @Nested
    @DisplayName("저장로직은")
    class CreatedMethod {

        @Test
        @DisplayName("성공적으로 실행이 된다.")
        public void save_ok() {

            Warrant mock = readJson("json/warrant/service/save_ok.json", Warrant.class);

            given(warrantRepository.save(any())).willReturn(mock);

            WarrantSaveRequest req = readJson("json/warrant/service/warrant_save_request.json", WarrantSaveRequest.class);

            WarrantSaveModel dto = new WarrantSaveModel(req);

            Warrant entity = warrantService.created(dto);

            then(warrantRepository).should().save(any());

            assertEquals(entity.getTitle(), mock.getTitle());
            assertEquals(entity.getStandardAmount(), mock.getStandardAmount());
            assertEquals(entity.getSubscriptionAmount(), mock.getSubscriptionAmount());
        }
    }


}
