package com.bookmyshow.Book.My.Show.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Schema(description = "This represents Show model")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "c0529823-2aa8-41c8-99b4-2e76b570f9e7")
    UUID id;

    @JsonIgnore
    @ManyToOne
    Hall hall;

    @JsonIgnore
    @ManyToOne
    Movie movie; // Unidirectional

    @JsonIgnore
    @ManyToOne
    Screen screen; // Unidirectional

    @Schema(example = "9")
    int availableTickets;

    @Schema(example = "2024-01-02 13:42:00.029")
    Date startTime;

    @Schema(example = "2024-01-02 16:42:00.031")
    Date endTime;

    @Schema(example = "280")
    int ticketPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "show")
    List<Ticket> tickets;
}
