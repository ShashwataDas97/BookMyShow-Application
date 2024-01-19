package com.bookmyshow.Book.My.Show.DTO.RequestDTO;

import com.bookmyshow.Book.My.Show.Enum.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegularUserSignupDTO {
    @Schema(example = "Shashwata Das")
    String name;

    @Schema(example = "shashwatadas802@gmail.com")
    String email;

    @Schema(example = "9875445477")
    long phoneNumber;

    @Schema(example = "Shashwata@12345")
    String password;

    @Schema(example = "RegularUser")
    UserType type; // HallOwner, MovieOwner, RegularUser

    @Schema(example = "26")
    int age;
}
