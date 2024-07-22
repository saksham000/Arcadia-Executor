package com.online.school.school.exceptions;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(String message){
        super(message);
    }
}
