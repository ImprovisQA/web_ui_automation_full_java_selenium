package com.helpsystems.common.base;

@SuppressWarnings("serial")
public class ValidateException extends Exception {

    //@param message is the exception message

    public ValidateException(final String message) {
        super(message);
    }

    //@param message is the exception message
    //@param cause is the cause of the original exception

    public ValidateException(final String message, final Throwable cause) {
        super(message, cause);

    }
}