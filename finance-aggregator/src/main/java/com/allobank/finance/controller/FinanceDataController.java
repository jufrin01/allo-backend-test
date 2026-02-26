package com.allobank.finance.controller;

import com.allobank.finance.service.FinanceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
public class FinanceDataController {

    private final FinanceDataService financeService;

    @Autowired
    public FinanceDataController(FinanceDataService financeService) {
        this.financeService = financeService;
    }


    @GetMapping("/data/{resourceType}")
    public ResponseEntity<Object> getFinanceData(@PathVariable String resourceType) {

        Object responseData = financeService.getFinanceData(resourceType);

        if (responseData == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Resource tidak ditemukan");
            errorResponse.put("message", "Tipe resource '" + resourceType + "' tidak didukung.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        return ResponseEntity.ok(responseData);
    }
}