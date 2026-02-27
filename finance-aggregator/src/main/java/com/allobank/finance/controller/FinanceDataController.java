package com.allobank.finance.controller;

import com.allobank.finance.model.Finance;
import com.allobank.finance.service.FinanceDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/finance")
public class FinanceDataController {

    private final FinanceDataService financeService;

    @Value("${allobank.developer.username}")
    private String separator;


    @GetMapping("/data/{resourceType}")
    public ResponseEntity<Finance> getFinanceData(@PathVariable String resourceType) {
         System.out.println(separator + " (Access Log)");
        Object responseData = financeService.getFinanceData(resourceType);
        return ResponseEntity.ok(new Finance(responseData, separator));
    }
}