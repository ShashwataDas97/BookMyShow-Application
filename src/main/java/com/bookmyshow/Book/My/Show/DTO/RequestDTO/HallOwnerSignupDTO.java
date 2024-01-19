package com.bookmyshow.Book.My.Show.DTO.RequestDTO;

import com.bookmyshow.Book.My.Show.Enum.UserType;
import com.bookmyshow.Book.My.Show.Models.Hall;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HallOwnerSignupDTO {
    @Schema(example = "MyCinema Pvt. Ltd")
    String name;

    @Schema(example = "arunimashome2000@gmail.com")
    String email;

    @Schema(example = "6290634534")
    long phoneNumber;

    @Schema(example = "Arunima12345@")
    String password;

    @Schema(example = "HallOwner")
    UserType type; // HallOwner, MovieOwner, RegularUser

    @Schema(example = "12")
    int companyAge;

    List<Hall> halls;
}
