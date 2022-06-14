package org.filmland.catalog.web.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmLandFeatureException extends RuntimeException {

    private final ErrorCodes errorCode;
    private final String message ;

    public FilmLandFeatureException(ErrorCodes errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
        this.message = null;
    }
    public FilmLandFeatureException(ErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

}
