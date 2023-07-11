package com.victor.BloombergFXDataWarehouse.mapper;

import com.victor.BloombergFXDataWarehouse.dto.DealDTO;
import com.victor.BloombergFXDataWarehouse.model.Deal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DealMapper {
    public Deal toEntity(DealDTO dealDTO) {
        Deal deal = new Deal();
        deal.setDealUniqueId(dealDTO.getDealUniqueId());
        deal.setFromCurrencyISOCode(dealDTO.getFromCurrencyISOCode());
        deal.setToCurrencyISOCode(dealDTO.getToCurrencyISOCode());
        deal.setDealTimestamp(dealDTO.getDealTimestamp());
        deal.setDealAmount(dealDTO.getDealAmount());
        return deal;
    }

    public DealDTO toDTO(Deal deal) {
        DealDTO dealDTO = new DealDTO();
        dealDTO.setDealUniqueId(deal.getDealUniqueId());
        dealDTO.setFromCurrencyISOCode(deal.getFromCurrencyISOCode());
        dealDTO.setToCurrencyISOCode(deal.getToCurrencyISOCode());
        dealDTO.setDealTimestamp(deal.getDealTimestamp());
        dealDTO.setDealAmount(deal.getDealAmount());
        return dealDTO;
    }

    public List<DealDTO> toDTOList(List<Deal> deals) {
        return deals.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}

