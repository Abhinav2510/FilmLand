package org.filmland.catalog.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryResponseDTO {
    private String name;
    private long availableContent;
    private double price;
}
