package com.bookmyshow.Book.My.Show.DTO.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddShowDTO {
    @Schema(example = "13")
    int hours; // 13

    @Schema(example = "00")
    int minutes; // 00

    @Schema(example = "280")
    int ticketPrice;

    @Schema(example = "ff7f0089-bc0c-4a5e-8eb8-ff5e6908f866")
    UUID movieId;

    @Schema(example = "72ff14f8-70d6-4ff1-8b36-bbbfc6d470c2")
    UUID hallId;
}
