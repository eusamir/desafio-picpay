package com.example.picpay_challenger.suport.expection;

public class BadGatewayException extends RuntimeException {
    public BadGatewayException(String message) {
        super(message);
    }
}
