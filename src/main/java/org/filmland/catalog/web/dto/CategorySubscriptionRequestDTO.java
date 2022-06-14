package org.filmland.catalog.web.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class CategorySubscriptionRequestDTO {
    @Email
    private String email;
    @Email
    private String customer;
    private String availableCategories;
}
