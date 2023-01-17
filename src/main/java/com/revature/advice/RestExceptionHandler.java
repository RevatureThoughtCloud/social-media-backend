package com.revature.advice;

import com.revature.exceptions.AlreadyFollowingException;
import com.revature.exceptions.FollowingNotAllowedException;
import com.revature.exceptions.NoFollowingRelationshipExistsException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.UserNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<Object> handleNotLoggedInException(HttpServletRequest request,
            NotLoggedInException notLoggedInException) {

        String errorMessage = "Must be logged in to perform this action";

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleNotUserNotFoundException(HttpServletRequest request,
            UserNotFoundException notLoggedInException) {

        String errorMessage = "User does not exist.";

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(FollowingNotAllowedException.class)
    public ResponseEntity<Object> handleFollowingNotAllowed(HttpServletRequest request,
            FollowingNotAllowedException notLoggedInException) {

        String errorMessage = "Following not allowed";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(NoFollowingRelationshipExistsException.class)
    public ResponseEntity<Object> handleNoFollowExists(HttpServletRequest request,
            NoFollowingRelationshipExistsException notLoggedInException) {

        String errorMessage = "User is not following.";

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(AlreadyFollowingException.class)
    public ResponseEntity<Object> handleAlreadyFollowing(HttpServletRequest request,
            AlreadyFollowingException notLoggedInException) {

        String errorMessage = "Already following.";

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }
}
