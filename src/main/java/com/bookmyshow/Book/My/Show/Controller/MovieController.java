package com.bookmyshow.Book.My.Show.Controller;

import com.bookmyshow.Book.My.Show.DTO.RequestDTO.MovieOwnerSignupDTO;
import com.bookmyshow.Book.My.Show.Models.ApplicationUser;
import com.bookmyshow.Book.My.Show.Service.MovieService;
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

@Tag(name = "Movie API", description = "This controller contains all the Movie related service endpoint details")
@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    MovieService movieService;

    @Operation(
            summary = "This endpoint is used for Movie Owner to SingUp ",
            description = "1.  The signup process for a movie owner involves the submission of a 'MovieOwnerSignupDTO' through the @RequestBody. " +
                    "This DTO encapsulates essential information such as the movie owner's 'name', 'email', 'phoneNumber', 'password', 'type', 'companyAge', and 'a list of movies'. " +
                    "The purpose of this DTO is to provide a structured means to convey the necessary data for the signup operation." + "\n\n" +
                    "2.  Upon receiving the 'MovieOwnerSignupDTO', the system proceeds to store most of its attributes in the 'application_user' table within the database. " +
                    "These attributes include the movie owner's 'name', 'email', 'phoneNumber', 'password', 'type' and 'companyAge'. " +
                    "Notably, the 'type' field in the 'application_user' table is specifically set to 'MovieOwner' during this process, distinguishing the role of the user." + "\n\n" +
                    "3.  The unique aspect of handling the list of movies involves storing them in a dedicated 'movie' table in the database. " +
                    "For each movie specified in the list, a corresponding entry is created in the 'movie' table, associating it with the movie owner. " +
                    "This linkage is established through a shared identifier, such as the movie_owner_id, " +
                    "facilitating the retrieval of movies belonging to a specific movie owner."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie Owner SignUp Successfully!!", content = @Content(schema = @Schema(implementation = ApplicationUser.class), mediaType = "Application/json"))
    })
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody MovieOwnerSignupDTO movieOwnerSignupDTO){
        ApplicationUser movieOwner = movieService.signUp(movieOwnerSignupDTO);
        return new ResponseEntity(movieOwner, HttpStatus.CREATED);
    }
}
