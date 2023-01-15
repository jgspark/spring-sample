package com.example.contract.web;

import com.example.contract.service.PremiumService;
import com.example.contract.web.dto.EstimatedPremium;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PremiumController {

    private final PremiumService premiumService;

    @GetMapping("products/{id}/premium")
    public ResponseEntity<EstimatedPremium> getEstimatedPremium(@PathVariable Long id) {
        Optional<EstimatedPremium> data = premiumService.getEstimatedPremium(id);
        return data.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
