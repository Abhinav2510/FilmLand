package org.filmland.catalog.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserCreateDTO {

    @Email
    private String email;

    @NotEmpty
    @Size(min = 8,message = "Password should be at least 8 characters long")
    private String password;
}
