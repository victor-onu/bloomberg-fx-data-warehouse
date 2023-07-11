package com.victor.BloombergFXDataWarehouse.service;

import com.victor.BloombergFXDataWarehouse.dto.DealDTO;
import com.victor.BloombergFXDataWarehouse.exception.DealNotFoundException;
import com.victor.BloombergFXDataWarehouse.exception.DuplicateDealException;
import com.victor.BloombergFXDataWarehouse.mapper.DealMapper;
import com.victor.BloombergFXDataWarehouse.model.Deal;
import com.victor.BloombergFXDataWarehouse.repository.DealRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DealServiceTest {
    @Mock
    private DealRepository dealRepository;

    @Mock
    private DealMapper dealMapper;

    private DealService dealService;

    @Before
    public void setUp() {
        dealService = new DealService(dealRepository, dealMapper);
    }

    @Test
    public void saveDeal_ValidDealDTO_DealSavedSuccessfully() {
        DealDTO dealDTO = new DealDTO();
        dealDTO.setDealUniqueId("123");
        dealDTO.setFromCurrencyISOCode("USD");
        dealDTO.setToCurrencyISOCode("EUR");
        dealDTO.setDealTimestamp(LocalDateTime.now());
        dealDTO.setDealAmount(BigDecimal.valueOf(100.0));

        Deal deal = new Deal();
        when(dealMapper.toEntity(dealDTO)).thenReturn(deal);
        when(dealRepository.findByDealUniqueId(dealDTO.getDealUniqueId())).thenReturn(Optional.empty());

        dealService.saveDeal(dealDTO);

        verify(dealRepository, times(1)).save(deal);
    }

    @Test(expected = DuplicateDealException.class)
    public void saveDeal_DealWithSameUniqueIdExists_DuplicateDealExceptionThrown() {
        String dealUniqueId = "123";

        DealDTO dealDTO = new DealDTO();
        dealDTO.setDealUniqueId(dealUniqueId);
        dealDTO.setFromCurrencyISOCode("USD");
        dealDTO.setToCurrencyISOCode("EUR");
        dealDTO.setDealTimestamp(LocalDateTime.now());
        dealDTO.setDealAmount(BigDecimal.valueOf(100.0));

        Deal existingDeal = new Deal();
        when(dealRepository.findByDealUniqueId(dealUniqueId)).thenReturn(Optional.of(existingDeal));

        dealService.saveDeal(dealDTO);
    }

    @Test
    public void getDealByUniqueId_ExistingDealUniqueId_DealDTOReturned() {
        String dealUniqueId = "123";

        Deal existingDeal = new Deal();
        when(dealRepository.findByDealUniqueId(dealUniqueId)).thenReturn(Optional.of(existingDeal));

        DealDTO expectedDTO = new DealDTO();
        when(dealMapper.toDTO(existingDeal)).thenReturn(expectedDTO);

        DealDTO resultDTO = dealService.getDealByUniqueId(dealUniqueId);

        assertEquals(expectedDTO, resultDTO);
    }

    @Test(expected = DealNotFoundException.class)
    public void getDealByUniqueId_NonExistingDealUniqueId_DealNotFoundExceptionThrown() {
        String dealUniqueId = "123";

        when(dealRepository.findByDealUniqueId(dealUniqueId)).thenReturn(Optional.empty());

        dealService.getDealByUniqueId(dealUniqueId);
    }

}
