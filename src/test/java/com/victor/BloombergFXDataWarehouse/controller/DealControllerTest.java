package com.victor.BloombergFXDataWarehouse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.victor.BloombergFXDataWarehouse.config.CustomizedResponseEntityExceptionHandler;
import com.victor.BloombergFXDataWarehouse.dto.DealDTO;
import com.victor.BloombergFXDataWarehouse.exception.DealNotFoundException;
import com.victor.BloombergFXDataWarehouse.service.DealService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class DealControllerTest {
    private MockMvc mockMvc;

    @Mock
    private DealService dealService;

    @InjectMocks
    private DealController dealController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dealController)
                .setControllerAdvice(new CustomizedResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void createDeal_ValidDealDTO_DealCreatedSuccessfully() throws Exception {
        DealDTO dealDTO = new DealDTO();
        dealDTO.setDealUniqueId("123");
        dealDTO.setFromCurrencyISOCode("USD");
        dealDTO.setToCurrencyISOCode("EUR");
        dealDTO.setDealTimestamp(LocalDateTime.parse("2023-07-09T12:34:56"));
        dealDTO.setDealAmount(BigDecimal.valueOf(100.0));

        mockMvc.perform(post("/api/deals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dealDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value("Deal saved successfully."))
                .andExpect(jsonPath("$.message").value("Deal saved successfully."));
    }

    @Test
    public void createDeal_InvalidDealDTO_ValidationErrorResponseReturned() throws Exception {
        DealDTO dealDTO = new DealDTO();
        // Leave fields empty to trigger validation errors

        mockMvc.perform(post("/api/deals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dealDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.debugMessage").doesNotExist())
                .andExpect(jsonPath("$.subErrors").isArray())
                .andExpect(jsonPath("$.subErrors").isNotEmpty())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.subErrors[0].message").isNotEmpty())
                .andExpect(jsonPath("$.subErrors[0].field").isNotEmpty())
                .andExpect(jsonPath("$.subErrors[0].object").value("dealDTO"))
                .andExpect(jsonPath("$.subErrors.length()").value(5));
    }


    @Test
    public void getDealByUniqueId_ExistingDealUniqueId_DealDTOReturned() throws Exception {
        String dealUniqueId = "123";
        DealDTO dealDTO = new DealDTO();
        // Set necessary fields for dealDTO

        when(dealService.getDealByUniqueId(dealUniqueId)).thenReturn(dealDTO);

        mockMvc.perform(get("/api/deals/{dealUniqueId}", dealUniqueId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(dealDTO))
                .andExpect(jsonPath("$.message").value("Deal retrieved successfully."));
    }

    @Test
    public void getDealByUniqueId_NonExistingDealUniqueId_DealNotFoundErrorResponseReturned() throws Exception {
        String dealUniqueId = "456";

        when(dealService.getDealByUniqueId(dealUniqueId)).thenThrow(new DealNotFoundException("Deal not found."));

        mockMvc.perform(get("/api/deals/{dealUniqueId}", dealUniqueId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").value("Deal not found."));
    }

    // Helper method to convert object to JSON string
    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }












    @Test
    public void getAllDeals_ReturnsListOfDeals_Success() throws Exception {

        DealDTO dealDTO1 = new DealDTO();
        dealDTO1.setDealUniqueId("123");
        dealDTO1.setFromCurrencyISOCode("USD");
        dealDTO1.setToCurrencyISOCode("EUR");
        dealDTO1.setDealTimestamp(LocalDateTime.now());
        dealDTO1.setDealAmount(BigDecimal.valueOf(100.0));

        DealDTO dealDTO2 = new DealDTO();
        dealDTO2.setDealUniqueId("456");
        dealDTO2.setFromCurrencyISOCode("GBP");
        dealDTO2.setToCurrencyISOCode("USD");
        dealDTO2.setDealTimestamp(LocalDateTime.now());
        dealDTO2.setDealAmount(BigDecimal.valueOf(200.0));
        List<DealDTO> deals = Arrays.asList(
                dealDTO1, dealDTO2
        );

        when(dealService.getAllDeals()).thenReturn(deals);

        mockMvc.perform(get("/api/deals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Deals retrieved successfully."))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data", hasSize(2)));

        verify(dealService, times(1)).getAllDeals();
    }

}
