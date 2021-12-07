package uz.pdp.exceptions;

import uz.pdp.enums.http.Http;

public class APIException extends Exception {
    private final Integer code;

    public APIException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public APIException(String message, Http status) {
        super(message);
        this.code = status.getCode();
    }
}
