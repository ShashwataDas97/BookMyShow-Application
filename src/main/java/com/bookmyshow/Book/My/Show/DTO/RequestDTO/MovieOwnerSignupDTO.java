package com.bookmyshow.Book.My.Show.DTO.RequestDTO;

import com.bookmyshow.Book.My.Show.Enum.UserType;
import com.bookmyshow.Book.My.Show.Models.Movie;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieOwnerSignupDTO {
    @Schema(example = "Red Chilli Entertainment Pvt. Ltd")
    String name;

    @Schema(example = "kekadas186@gmail.com")
    String email;

    @Schema(example = "6291723928")
    long phoneNumber;

    @Schema(example = "RedChilli@12345")
    String password;

    @Schema(example = "MovieOwner")
    UserType type; // HallOwner, MovieOwner, RegularUser

    @Schema(example = "20")
    int companyAge;

    List<Movie> movies;
}
