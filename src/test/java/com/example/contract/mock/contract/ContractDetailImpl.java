package com.example.contract.mock.contract;

import com.example.contract.domain.contract.Contract;
import com.example.contract.domain.product.Product;
import com.example.contract.domain.warrant.Warrant;
import com.example.contract.domain.contract.ContractState;
import com.example.contract.dto.mapper.ContractDetail;
import com.example.contract.dto.mapper.ProductInfo;
import com.example.contract.dto.mapper.WarrantInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ContractDetailImpl implements ContractDetail {

    private final Contract contract;

    public ContractDetailImpl(Contract contract) {
        this.contract = contract;
    }

    public ContractDetailImpl(Contract contract, ContractState state) {
        this.contract = contract;
        this.contract.update(contract.getWarrants(), contract.getTerm(), state, contract.getPremium());
    }

    @Override
    public Long getId() {
        return contract.getId();
    }

    @Override
    public Integer getTerm() {
        return contract.getTerm();
    }

    @Override
    public Date getStartDate() {
        return contract.getStartDate();
    }

    @Override
    public Date getEndDate() {
        return contract.getEndDate();
    }

    @Override
    public BigDecimal getPremium() {
        return contract.getPremium();
    }

    @Override
    public ContractState getState() {
        return contract.getState();
    }

    @Override
    public ProductInfo getProduct() {
        return new ProductInfoTest(contract.getProduct());
    }

    @Override
    public Set<WarrantInfo> getWarrants() {
        return contract.getWarrants().stream().map(WarrantInfoTest::new).collect(Collectors.toSet());
    }

    static class ProductInfoTest implements ProductInfo {

        private Product product;


        ProductInfoTest(Product product) {
            this.product = product;
        }

        @Override
        public Long getId() {
            return product.getId();
        }

        @Override
        public String getTitle() {
            return product.getTitle();
        }

        @Override
        public Integer getRange() {
            return product.getTerm().getRange();
        }
    }

    static class WarrantInfoTest implements WarrantInfo {

        private Warrant warrant;

        public WarrantInfoTest(Warrant warrant) {
            this.warrant = warrant;
        }

        @Override
        public Long getId() {
            return warrant.getId();
        }

        @Override
        public String getTitle() {
            return warrant.getTitle();
        }

        @Override
        public BigDecimal getSubscriptionAmount() {
            return warrant.getSubscriptionAmount();
        }

        @Override
        public BigDecimal getStandardAmount() {
            return warrant.getStandardAmount();
        }
    }
}
