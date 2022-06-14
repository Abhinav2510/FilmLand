package org.filmland.catalog.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SubscribedCategoryResponseDTO {

    private String name;
    private long remainingContent;
    private LocalDate startDate;
    private double price;
}
