package com.github.nbaravik.springuser.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String id) {
        super("User with ID=" + id + " does not exist in the DB.");
    }
}
