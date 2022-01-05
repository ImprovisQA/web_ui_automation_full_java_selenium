package com.helpsystems.common.base;

@SuppressWarnings("serial")
public class CleanUpException extends Exception {

    //@param message is the exception message

    public CleanUpException(final String message) {
        super(message);
    }

    //@param message is the exception message
    //@param cause is the cause of the original exception

    public CleanUpException(final String message, final Throwable cause) {
        super(message, cause);

    }
}