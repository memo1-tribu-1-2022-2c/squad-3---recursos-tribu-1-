
package ar.uba.fi.recursos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidHourDetailHoursException extends RuntimeException {
    public InvalidHourDetailHoursException(String message) {
        super(message);
    }
}