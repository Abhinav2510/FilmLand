package org.filmland.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

/**
 * FilmCategory represents categories at persistence layer
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilmCategory {

    @Id
    private String name;
    @PositiveOrZero
    private long availableContent;
    @NotEmpty
    private String currency;
    @PositiveOrZero
    private long price;

}
