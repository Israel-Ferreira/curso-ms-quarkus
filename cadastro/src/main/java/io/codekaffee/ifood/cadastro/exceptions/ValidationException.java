package io.codekaffee.ifood.cadastro.exceptions;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

public class ValidationException extends BadRequestException {

    public ValidationException(Response response) {
        super(response);
    }

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }
    
}
