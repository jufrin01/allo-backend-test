package com.allobank.finance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class FrankfurterRateDTO {
    private Double amount;
    private String base;
    private String date;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    private Map<String, Object> rates;
}