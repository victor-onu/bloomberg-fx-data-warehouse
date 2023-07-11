package com.victor.BloombergFXDataWarehouse.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BloombergFxDataWarehouseException extends RuntimeException{

    protected String message;
    
    public BloombergFxDataWarehouseException(String message) {
        this.message=message;
    }
}
