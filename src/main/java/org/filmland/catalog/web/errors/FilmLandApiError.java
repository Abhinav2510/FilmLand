package org.filmland.catalog.web.errors;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class FilmLandApiError {
    private final String status;
    private final String message;
}
