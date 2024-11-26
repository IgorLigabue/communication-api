package br.com.ligabue.communication_api.exceptionhandling.exceptions;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
