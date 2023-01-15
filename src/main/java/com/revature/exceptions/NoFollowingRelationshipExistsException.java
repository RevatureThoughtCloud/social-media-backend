package com.revature.exceptions;

public class NoFollowingRelationshipExistsException extends RuntimeException {
    public NoFollowingRelationshipExistsException() {
    }

    public NoFollowingRelationshipExistsException(String message) {
        super(message);
    }

    public NoFollowingRelationshipExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFollowingRelationshipExistsException(Throwable cause) {
        super(cause);
    }

    public NoFollowingRelationshipExistsException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
