package com.bookmyshow.Book.My.Show.Controller;

import com.bookmyshow.Book.My.Show.DTO.RequestDTO.RegularUserSignupDTO;
import com.bookmyshow.Book.My.Show.Models.ApplicationUser;
import com.bookmyshow.Book.My.Show.Service.RegularUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Regular User API", description = "This controller contains all the Regular User related service endpoint details")
@RestController
@RequestMapping("/user")
public class RegularUserController {
    @Autowired
    RegularUserService regularUserService;

    @Operation(
            summary = "This endpoint is used for Regular User to SingUp ",
            description = "1.  The registration process for a regular user involves the submission of a 'RegularUserSignupDTO' through the @RequestBody. " +
                    "This DTO encapsulates crucial information such as the user's 'name', 'email', 'phoneNumber', 'password', 'type' and 'age'. " +
                    "The primary purpose of this DTO is to structure and transmit the necessary data for the user registration operation." + "\n\n" +
                    "2.  Upon receiving the RegularUserSignupDTO, the system proceeds to store its attributes in the 'application_user' table within the database. " +
                    "The stored information includes the regular user's 'name', 'email', 'phoneNumber', 'password', 'type' and 'age'. " +
                    "Significantly, the 'type' field in the 'application_user' table is explicitly set to 'RegularUser' during this process, " +
                    "distinguishing the role of the user within the system." + "\n\n" +
                    "3.  By organizing the data in this manner, the system ensures a comprehensive " +
                    "representation of regular user information within a centralized 'application_user' table."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Regular User SignUp Successfully!!", content = @Content(schema = @Schema(implementation = ApplicationUser.class), mediaType = "Application/json"))
    })
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody RegularUserSignupDTO regularUserSignupDTO){
        ApplicationUser regularUser = regularUserService.signUp(regularUserSignupDTO);
        return new ResponseEntity(regularUser, HttpStatus.CREATED);
    }
}
