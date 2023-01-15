package com.revature.exceptions;

public class AlreadyFollowingException extends RuntimeException {
    public AlreadyFollowingException() {
    }

    public AlreadyFollowingException(String message) {
        super(message);
    }

    public AlreadyFollowingException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyFollowingException(Throwable cause) {
        super(cause);
    }

    public AlreadyFollowingException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
