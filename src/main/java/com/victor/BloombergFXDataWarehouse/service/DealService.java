package com.victor.BloombergFXDataWarehouse.service;

import com.victor.BloombergFXDataWarehouse.dto.DealDTO;
import com.victor.BloombergFXDataWarehouse.exception.DealNotFoundException;
import com.victor.BloombergFXDataWarehouse.exception.DuplicateDealException;
import com.victor.BloombergFXDataWarehouse.mapper.DealMapper;
import com.victor.BloombergFXDataWarehouse.model.Deal;
import com.victor.BloombergFXDataWarehouse.repository.DealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealService {
    private final DealRepository dealRepository;
    private final DealMapper dealMapper;

    public DealService(DealRepository dealRepository, DealMapper dealMapper) {
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
    }

    public void saveDeal(DealDTO dealDTO) {
        validateISOCode(dealDTO.getFromCurrencyISOCode());
        validateISOCode(dealDTO.getToCurrencyISOCode());

        if (dealRepository.findByDealUniqueId(dealDTO.getDealUniqueId()).isPresent()) {
            throw new DuplicateDealException("Deal with the same unique ID already exists.");
        }

        Deal deal = dealMapper.toEntity(dealDTO);
        dealRepository.save(deal);
    }

    public DealDTO getDealByUniqueId(String dealUniqueId) {
        Deal deal = dealRepository.findByDealUniqueId(dealUniqueId)
                .orElseThrow(() -> new DealNotFoundException("Deal with the given unique ID not found."));
        return dealMapper.toDTO(deal);
    }

    public List<DealDTO> getAllDeals() {
        List<Deal> deals = dealRepository.findAll();
        return dealMapper.toDTOList(deals);
    }

    private void validateISOCode(String isoCode) {
        String isoCodePattern = "^[A-Za-z]{3}$";

        if (!isoCode.matches(isoCodePattern)) {
            throw new IllegalArgumentException("Invalid ISO code: " + isoCode);
        }
    }
}

