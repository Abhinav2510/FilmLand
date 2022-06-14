package org.filmland.catalog.web.dto;

import jdk.jfr.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoriesListResponseDTO {
    List<CategoryResponseDTO> availableCategories;
    List<SubscribedCategoryResponseDTO> subscribedCategories;
}
