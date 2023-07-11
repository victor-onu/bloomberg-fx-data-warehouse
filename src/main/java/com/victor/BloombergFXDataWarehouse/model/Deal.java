package com.victor.BloombergFXDataWarehouse.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deal")
@Data
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Deal unique ID is required")
    private String dealUniqueId;

    @NotBlank(message = "From currency ISO code is required")
    private String fromCurrencyISOCode;

    @NotBlank(message = "To currency ISO code is required")
    private String toCurrencyISOCode;

    @NotNull(message = "Deal timestamp is required")
    private LocalDateTime dealTimestamp;

    @NotNull(message = "Deal amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Deal amount must be greater than zero")
    private BigDecimal dealAmount;

}

