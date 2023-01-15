package com.revature.exceptions;

public class FollowingNotAllowedException extends RuntimeException {
    public FollowingNotAllowedException() {
    }

    public FollowingNotAllowedException(String message) {
        super(message);
    }

    public FollowingNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FollowingNotAllowedException(Throwable cause) {
        super(cause);
    }

    public FollowingNotAllowedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
