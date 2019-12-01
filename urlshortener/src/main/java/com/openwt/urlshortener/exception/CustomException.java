package com.openwt.urlshortener.exception;

public class CustomException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String errorCode;

    public CustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
