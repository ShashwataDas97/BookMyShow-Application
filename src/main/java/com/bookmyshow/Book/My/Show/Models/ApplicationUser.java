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
@Schema(description = "This represents ApplicationUser model")
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "3af8c039-cabf-4422-8d27-0209f670520b")
    UUID id;

    @Schema(example = "Shashwata Das")
    String name;

    @Column(unique = true)
    @Schema(example = "shashwatadas802@gmail.com")
    String email;

    @Column(unique = true)
    @Schema(example = "9875445477")
    long phoneNumber;

    @Schema(example = "Shashwata@12345")
    String password;

    @Schema(example = "RegularUser")
    String type; // Hall Owner, Movie Owner, Regular User

    @Schema(example = "26")
    int age;

    @OneToMany(mappedBy = "user")
    List<Ticket> tickets;
}
