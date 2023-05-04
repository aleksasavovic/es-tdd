package asavovic.courseProject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeficientResourcesException extends RuntimeException {
    public DeficientResourcesException(String message) {
        super(message);
    }
}