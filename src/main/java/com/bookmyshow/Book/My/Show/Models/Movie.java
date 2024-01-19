package com.bookmyshow.Book.My.Show.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Schema(description = "This represents Movie model")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "ff7f0089-bc0c-4a5e-8eb8-ff5e6908f866")
    UUID id;

    @Schema(example = "Animal")
    String name;

    @Schema(example = "sandeep reddy vanga")
    String directorName;

    @Schema(example = "Ranbir Kapoor")
    String actorName;

    @Schema(example = "Rashmika Mandanna")
    String actressName;

    @Schema(example = "8")
    int imdbRating;

    @Schema(example = "3")
    double duration; // Hours

    @OneToMany(mappedBy = "movie")
    List<Ticket> tickets;

    @ManyToOne
    ApplicationUser movieOwner;
}
