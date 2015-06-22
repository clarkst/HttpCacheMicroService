package metapack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No store locations found for this city")
public class CityNotFoundException extends RuntimeException {
}
