package com.victor.BloombergFXDataWarehouse.exception;


public class CustomUniqueConstraintViolationException extends BloombergFxDataWarehouseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CustomUniqueConstraintViolationException() {
	super("Data is not unique");
    }

    public CustomUniqueConstraintViolationException(String message) {
	super(message);
    }

}
