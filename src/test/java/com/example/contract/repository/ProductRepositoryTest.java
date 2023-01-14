package com.example.contract.repository;

import com.example.contract.doamin.Product;
import com.example.contract.mock.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    @DisplayName("상품 생성")
    public void save_ok() {

        Product mock = MockUtil.readJson("json/product/save_ok.json", Product.class);

        Product entity = productRepository.save(mock);

        assertNotNull(entity.getId());
        assertEquals(mock.getTitle(), entity.getTitle());
        assertEquals(mock.getTerm(), entity.getTerm());
    }

//    @Test
//    @DisplayName("계약 기간이 조건에 맞지 않는 케이스")
//    public void save_fail_case1() {
//
//        Product mock = MockUtil.readJson("json/product/save_fail_case1.json", Product.class);
//
//        assertThrows(RuntimeException.class, () -> {
//            productRepository.save(mock);
//        });
//    }
}
