package io.codekaffee.ifood.cadastro.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.codekaffee.ifood.cadastro.dto.ConstraintViolationImpl;

@Provider   
public class ConstrainViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // TODO Auto-generated method stub

        List<ConstraintViolationImpl> violations = exception.getConstraintViolations()
            .stream()
            .map(ConstraintViolationImpl::of)
            .collect(Collectors.toList());

        return Response.status(Status.BAD_REQUEST).entity(violations).build();
    }
    
}
