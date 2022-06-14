package org.filmland.catalog.web.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.filmland.catalog.web.errors.ErrorCodes.*;

/**
 * Exception handler to generate status and message on exceptions
 */
@ControllerAdvice
public class FilmLandFeatureExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * generate correct response body with http status code depending on {@link FilmLandFeatureException} error code and message
     * @param filmLandFeatureException
     * @return
     */
    @ExceptionHandler(value = {FilmLandFeatureException.class})
    private ResponseEntity<FilmLandApiError> handleFilmLandFeatureException(FilmLandFeatureException filmLandFeatureException) {
        switch (filmLandFeatureException.getErrorCode()) {
            case LOGIN_FAILED:
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new FilmLandApiError(LOGIN_FAILED.getStatus(), filmLandFeatureException.getMessage() == null ? LOGIN_FAILED.getMessage() : filmLandFeatureException.getMessage()));
            case ALREADY_SUBSCRIBED:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new FilmLandApiError(ALREADY_SUBSCRIBED.getStatus(), filmLandFeatureException.getMessage() == null ? ALREADY_SUBSCRIBED.getMessage() : filmLandFeatureException.getMessage()));
            case OPERATION_NOT_PERMITTED:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new FilmLandApiError(OPERATION_NOT_PERMITTED.getStatus(), filmLandFeatureException.getMessage() == null ? OPERATION_NOT_PERMITTED.getMessage() : filmLandFeatureException.getMessage()));
            case CATEGORY_DOESNT_EXIST:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmLandApiError(CATEGORY_DOESNT_EXIST.getStatus(), filmLandFeatureException.getMessage() == null ? CATEGORY_DOESNT_EXIST.getMessage() : filmLandFeatureException.getMessage()));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FilmLandApiError(ErrorCodes.UNKNOWN.getStatus(), filmLandFeatureException.getMessage() == null ? ErrorCodes.UNKNOWN.getMessage() : filmLandFeatureException.getMessage()));
        }
    }

}
