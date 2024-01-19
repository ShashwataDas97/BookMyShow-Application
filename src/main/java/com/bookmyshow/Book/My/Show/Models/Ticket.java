package com.bookmyshow.Book.My.Show.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Schema(description = "This represents Ticket model")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "6200299d-0591-4c24-997d-9a54a4b4eae8")
    UUID id;

    @ManyToOne
    ApplicationUser user;

    @ManyToOne
    Movie movie;

    @ManyToOne
    Hall hall;

    @ManyToOne
    Show show;
}
