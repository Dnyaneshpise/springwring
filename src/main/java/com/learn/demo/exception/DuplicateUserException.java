package com.learn.demo.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String name) {
        super("User already exists with name: " + name);
    }
}