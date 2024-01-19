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
@Schema(description = "This represents Screen model")
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "c5725519-8f98-4dd3-8a87-8fefc93700d8")
    UUID id;

    @Schema(example = "Screen1")
    String screenName;

    @ManyToOne
    Hall hall;

    @Schema(example = "10")
    int screenCapacity;

    @Schema(example = "true")
    boolean status; // Booked, Not Booked

    @Schema(example = "2D")
    String type; // 2D, 3D
}
