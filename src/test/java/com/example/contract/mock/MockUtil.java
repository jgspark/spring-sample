package com.example.contract.mock;

import com.example.contract.doamin.Contract;
import com.example.contract.doamin.Product;
import com.example.contract.doamin.Warrant;
import com.example.contract.doamin.embeddable.ProductTerm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class MockUtil {

    public static <T> T readJson(String path, Class<T> type) {

        File jsonFile = null;

        try {
            jsonFile = new ClassPathResource(path).getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String fileString = null;

        try {
            fileString = new String(Files.readAllBytes(jsonFile.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        try {
            return objectMapper.readValue(fileString, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Warrant convertWarrant(Map map) {
        return Warrant.createBuilder().title((String) map.get("title")).subscriptionAmount(new BigDecimal((Integer) map.get("subscriptionAmount"))).standardAmount(new BigDecimal((Integer) map.get("standardAmount"))).build();
    }

    public static Product convert(Product mock, Warrant warrant) {
        return Product.createBuilder().title(mock.getTitle()).term(mock.getTerm()).warrants(Collections.singleton(warrant)).build();
    }

    public static Contract convert(Contract contract, Product product, Warrant warrant) {
        return Contract.createBuilder().product(product).warrants(Collections.singleton(warrant)).term(contract.getTerm()).startDate(contract.getStartDate()).endDate(contract.getEndDate()).premium(contract.getPremium()).build();
    }

    public static Product convertProduct(Map map) {
        Map term = (Map) map.get("term");
        return Product.createBuilder().title((String) map.get("title")).term(new ProductTerm((Integer) term.get("startMonth"), (Integer) term.get("endMonth"))).build();
    }

    public static String convert(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(date);
    }

    public static BigDecimal convert(BigDecimal num) {
        return num.stripTrailingZeros();
    }
}
