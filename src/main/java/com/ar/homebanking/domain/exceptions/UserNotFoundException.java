package com.ar.homebanking.domain.exceptions;

public class UserNotFoundException extends RuntimeException{

        public UserNotFoundException (String message) {
            super(message);
        }

    }
