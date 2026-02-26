package com.allobank.finance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class FrankfurterRateDTO {

    private Double amount;
    private String base;
    private String date;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    private Map<String, Object> rates;


    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public Map<String, Object> getRates() { return rates; }
    public void setRates(Map<String, Object> rates) { this.rates = rates; }
}