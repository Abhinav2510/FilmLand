package org.filmland.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * User represents users at persistence layer.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @NotEmpty
    @Email(message = "Email should be at least 8 characters long")
    private String email;
    @NotEmpty
    @Size(min = 8, message = "Password name should be at least 8 characters long")
    private String password;


}
