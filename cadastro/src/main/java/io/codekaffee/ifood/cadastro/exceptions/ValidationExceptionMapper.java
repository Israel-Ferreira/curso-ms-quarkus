package io.codekaffee.ifood.cadastro.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.codekaffee.ifood.cadastro.dto.ErrorDTO;


@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        // TODO Auto-generated method stub

        ErrorDTO errorDTO =  new ErrorDTO(exception.getMessage(), Status.BAD_REQUEST.getStatusCode());
        return Response.status(Status.BAD_REQUEST).entity(errorDTO).build();
    }
    
}
