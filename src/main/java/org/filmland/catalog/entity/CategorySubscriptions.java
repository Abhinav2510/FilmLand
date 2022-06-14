package org.filmland.catalog.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * {@link CategorySubscriptions} represents the Subscriptions at persistence layer
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CategorySubscriptions {

    @Id
    @GeneratedValue
    private long subscriptionId;

    @ManyToOne
    private User user;
    @ManyToOne
    private FilmCategory filmCategory;

    private LocalDate subscribedOn;
    private LocalDateTime lastBillingDateTime;

    @ManyToOne
    @JoinColumn(name = "shared_with")
    private User sharedWith;

}
