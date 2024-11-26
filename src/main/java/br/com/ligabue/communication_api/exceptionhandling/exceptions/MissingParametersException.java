package br.com.ligabue.communication_api.exceptionhandling.exceptions;

public class MissingParametersException extends RuntimeException {

    public MissingParametersException(String message) {
        super(message);
    }
}
