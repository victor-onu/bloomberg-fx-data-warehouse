package com.victor.BloombergFXDataWarehouse.exception;


public class InvalidCredentialException extends BloombergFxDataWarehouseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InvalidCredentialException() {
	super("Invalid data");
    }

    public InvalidCredentialException(String message) {
	super(message);
    }

}
