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
@Schema(description = "This represents Hall model")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "72ff14f8-70d6-4ff1-8b36-bbbfc6d470c2")
    UUID id;

    @Schema(example = "MyCinema Nexus")
    String name;

    @Schema(example = "Bangalore")
    String address;

    @ManyToOne
    ApplicationUser hallOwner;

    @OneToMany(mappedBy = "hall")
    List<Screen> screens;

    @OneToMany(mappedBy = "hall")
    List<Show> shows;

    @OneToMany(mappedBy = "hall")
    List<Ticket> tickets;
}
