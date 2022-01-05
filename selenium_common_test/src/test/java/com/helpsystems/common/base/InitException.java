package com.helpsystems.common.base;

@SuppressWarnings("serial")
public class InitException extends Exception {

    //@param message is the exception message

    public InitException(final String message) {
        super(message);
    }

    //@param message is the exception message
    //@param cause is the cause of the original exception

    public InitException(final String message, final Throwable cause) {
        super(message, cause);

    }
}