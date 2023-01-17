package com.example.contract.service;

import com.example.contract.doamin.Warrant;
import com.example.contract.repository.WarrantRepository;
import com.example.contract.web.dto.WarrantSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.contract.mock.MockUtil.readJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("담보 생성 서비스 레이어")
@ExtendWith(MockitoExtension.class)
class WarrantServiceTest {

    private WarrantService warrantService;

    @Mock
    private WarrantRepository warrantRepository;

    @BeforeEach
    public void init() {
        warrantService = new WarrantService(warrantRepository);
    }

    @Test
    @DisplayName("담보 생성 성공 테스트 케이스")
    public void save_ok() {

        Warrant mock = readJson("json/warrant/service/save_ok.json", Warrant.class);

        given(warrantRepository.save(any())).willReturn(mock);

        WarrantSaveRequest dto = readJson("json/warrant/service/warrant_save_request.json", WarrantSaveRequest.class);

        Warrant entity = warrantService.created(dto);

        then(warrantRepository).should().save(any());

        assertEquals(entity.getTitle(), mock.getTitle());
        assertEquals(entity.getStandardAmount(), mock.getStandardAmount());
        assertEquals(entity.getSubscriptionAmount(), mock.getSubscriptionAmount());
    }
}
