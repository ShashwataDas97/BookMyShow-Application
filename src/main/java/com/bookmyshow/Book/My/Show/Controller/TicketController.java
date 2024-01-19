package com.bookmyshow.Book.My.Show.Controller;

import com.bookmyshow.Book.My.Show.DTO.ResponseDTO.GeneralMessageDTO;
import com.bookmyshow.Book.My.Show.Models.Ticket;
import com.bookmyshow.Book.My.Show.Service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Ticket API", description = "This controller contains all the Ticket related service endpoint details")
@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @Operation(
            summary = "This endpoint enables regular user to buy a ticket for a particular show",
            description = "1.  Accept the user's 'email' and 'showId' as @RequestParam." + "\n\n" +
                    "2.  Check if the provided 'email' exists in the 'application_user' table in the database." + "\n\n" +
                    "3.  If the 'email' does not exist, throw a 'UserDoesNotExistException'." + "\n\n" +
                    "4.  If the 'email' exists, retrieve the user details based on the provided 'email'." + "\n\n" +
                    "5.  If the user 'type' is 'RegularUser', proceed to the next step." + "\n\n" +
                    "6.  If the user 'type' is not 'RegularUser', throw an 'UnAuthorizedException'." + "\n\n" +
                    "7.  Check if the provided 'showId' exists in the 'show' table in the database." + "\n\n" +
                    "8.  If the 'showId' does not exist, throw a 'ResourceNotExistException'." + "\n\n" +
                    "9.  If the 'showId' exists, retrieve the show details based on the provided 'showId'." + "\n\n" +
                    "10.  Decrease the count of 'available_tickets' for the specific 'showId' in the 'show' table as one ticket is being purchased." + "\n\n" +
                    "11.  After all validations pass, store the ticket details in the 'ticket' table in the database for the specified user." + "\n\n" +
                    "12.  Send an email to the user containing important ticket details and the barcode." + "\n\n" +
                    "13.  Send an email to the hall owner containing important ticket details." + "\n\n" +
                    "14.  Send an email to the movie owner containing important ticket details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created successfully", content = @Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json"))
    })
    @PostMapping("/buyticket")
    public ResponseEntity buyTicket(@Parameter(description = "Here you need to pass user's email")@RequestParam String email, @Parameter(description = "Here you need to pass showId") @RequestParam UUID showId){
        Ticket ticket = ticketService.buyTicket(email,showId);
        return new ResponseEntity("Ticket created successfully !!", HttpStatus.CREATED);
    }
}
