package com.bookmyshow.Book.My.Show.DTO.ResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GeneralMessageDTO {
    @Schema(example = "We just print the message here")
    String message;
}
