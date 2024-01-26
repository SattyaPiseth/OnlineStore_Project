package co.devkh.onlinestore.reviewonlinestore.exception;

import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class HttpClientErrorException extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(java.lang.IllegalStateException.class)
    public ResponseEntity<StructureRS> badRequestException(java.lang.IllegalStateException ex){

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.NotFound.class)
    public ResponseEntity<StructureRS> notFoundException(org.springframework.web.client.HttpClientErrorException.NotFound ex){

        StructureRS structureRS = new StructureRS(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.BadRequest.class)
    public ResponseEntity<StructureRS> badRequestException(org.springframework.web.client.HttpClientErrorException.BadRequest ex){

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<StructureRS> unauthorizedException(org.springframework.web.client.HttpClientErrorException.Unauthorized ex){

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.Forbidden.class)
    public ResponseEntity<StructureRS> forbiddenException(org.springframework.web.client.HttpClientErrorException.Forbidden ex){

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.MethodNotAllowed.class)
    public ResponseEntity<StructureRS> methodNotAllowedException(org.springframework.web.client.HttpClientErrorException.MethodNotAllowed ex){

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.UnsupportedMediaType.class)
    public ResponseEntity<StructureRS> unsupportedMediaTypeException(org.springframework.web.client.HttpClientErrorException.UnsupportedMediaType ex){

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.springframework.web.client.HttpClientErrorException.UnprocessableEntity.class)
    public ResponseEntity<StructureRS>  unprocessableEntityException(org.springframework.web.client.HttpClientErrorException.UnprocessableEntity ex){

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    public ResponseEntity<StructureRS>  illegalArgumentException(java.lang.IllegalArgumentException ex){

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }
}
