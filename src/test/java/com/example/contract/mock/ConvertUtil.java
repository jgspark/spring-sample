package com.example.contract.mock;

import com.example.contract.domain.contract.Contract;
import com.example.contract.domain.product.Product;
import com.example.contract.domain.warrant.Warrant;
import com.example.contract.domain.product.ProductTerm;
import com.example.contract.domain.contract.ContractState;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ConvertUtil {

    public static Warrant convertWarrant(Map map) {
        return Warrant.createBuilder()
                .id(Long.valueOf((Integer) map.get("id")))
                .title((String) map.get("title")).subscriptionAmount(
                        new BigDecimal((Integer) map.get("subscriptionAmount"))).standardAmount(
                        new BigDecimal((Integer) map.get("standardAmount"))).build();
    }

    public static Product convert(Product mock, Warrant warrant) {
        return Product.createBuilder().title(mock.getTitle()).term(mock.getTerm()).warrants(Collections.singleton(warrant)).build();
    }

    public static Product convert(Product mock, Set<Warrant> warrants) {
        return Product.createBuilder().id(mock.getId()).title(mock.getTitle()).term(mock.getTerm()).warrants(warrants).build();
    }

    public static Contract convert(Contract contract, Product product, Warrant warrant) {
        return Contract.createBuilder().product(product).warrants(Collections.singleton(warrant)).term(contract.getTerm()).startDate(
                contract.getStartDate()).endDate(contract.getEndDate()).premium(contract.getPremium()).build();
    }

    public static Product convertProduct(Map map) {

        Map term = (Map) map.get("term");

        return Product.createBuilder()
                .id(Long.valueOf((Integer) map.get("id")))
                .title((String) map.get("title")).term(
                        new ProductTerm((Integer) term.get("startMonth"), (Integer) term.get("endMonth"))).build();
    }

    public static String convert(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(date);
    }

    public static BigDecimal convert(BigDecimal num) {
        return num.stripTrailingZeros();
    }


    public static Contract convertContractByUpdate(Map map) {
        Contract entity = Contract.createBuilder()
                .term((Integer) map.get("term"))
                .endDate(convert((String) map.get("endDate")))
                .startDate(convert((String) map.get("startDate")))
                .product(convertProduct((Map) map.get("product")))
                .build();
        entity.update(entity.getWarrants(), entity.getTerm(), ContractState.EXPIRATION, entity.getPremium());
        return entity;
    }

    public static Date convert(String dateStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            return formatter.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<Warrant> convert(Object map) {
        return ((ArrayList<Map>) map).stream().map(ConvertUtil::convertWarrant).collect(Collectors.toSet());
    }

    public static Contract convertContract(Map map) {
        return Contract.createBuilder()
                .id(Long.valueOf((Integer) map.get("id")))
                .term((Integer) map.get("term"))
                .endDate(convert((String) map.get("endDate")))
                .startDate(convert((String) map.get("startDate")))
                .product(convertProduct((Map) map.get("product")))
                .premium(new BigDecimal((Integer) map.get("premium")))
                .build();
    }

    public static Contract convertContract(Map contractMap, Map warrantMap, ContractState contractState) {

        Contract contract = convertContract(contractMap);

        Warrant warrant = convertWarrant(warrantMap);

        contract.update(Collections.singleton(warrant), contract.getTerm(), contractState, contract.getPremium());

        return contract;
    }
}
