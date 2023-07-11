package com.victor.BloombergFXDataWarehouse.exception;

public class InvalidOperationException extends BloombergFxDataWarehouseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InvalidOperationException() {
	super("Data is not unique");
    }

    public InvalidOperationException(String message) {
	super(message);
    }

    public InvalidOperationException(String message, Exception ex) {
	super(message);
    }

}
