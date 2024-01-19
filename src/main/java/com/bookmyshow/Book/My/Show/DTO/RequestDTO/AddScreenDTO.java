package com.bookmyshow.Book.My.Show.DTO.RequestDTO;

import com.bookmyshow.Book.My.Show.Models.Screen;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddScreenDTO {
    List<Screen> screens;

    @Schema(example = "72ff14f8-70d6-4ff1-8b36-bbbfc6d470c2")
    UUID hallId;
}
