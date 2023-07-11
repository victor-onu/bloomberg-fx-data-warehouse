package com.victor.BloombergFXDataWarehouse.controller;

import com.victor.BloombergFXDataWarehouse.apiresponse.ApiResponse;
import com.victor.BloombergFXDataWarehouse.dto.DealDTO;
import com.victor.BloombergFXDataWarehouse.service.DealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DealController {
    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping("/deals")
    public ResponseEntity<ApiResponse<String>> createDeal(@Valid @RequestBody DealDTO dealDTO) {
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.CREATED);
        dealService.saveDeal(dealDTO);
        response.setMessage("Deal saved successfully.");
        response.setData("Deal saved successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/deals/{dealUniqueId}")
    public ResponseEntity<ApiResponse<DealDTO>> getDealByUniqueId(@PathVariable String dealUniqueId) {
        DealDTO dealDTO = dealService.getDealByUniqueId(dealUniqueId);
        ApiResponse<DealDTO> response = new ApiResponse<>(HttpStatus.OK);
        response.setMessage("Deal retrieved successfully.");
        response.setData(dealDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deals")
    public ResponseEntity<ApiResponse<List<DealDTO>>> getAllDeals() {
        List<DealDTO> deals = dealService.getAllDeals();
        ApiResponse<List<DealDTO>> response = new ApiResponse<>(HttpStatus.OK);
        response.setMessage("Deals retrieved successfully.");
        response.setData(deals);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
