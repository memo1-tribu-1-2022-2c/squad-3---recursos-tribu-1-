
package ar.uba.fi.recursos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class OverlappingDatesException extends RuntimeException {
    public OverlappingDatesException(String message){
        super(message);
    }
}