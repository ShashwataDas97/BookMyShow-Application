package com.bookmyshow.Book.My.Show.Controller;

import com.bookmyshow.Book.My.Show.DTO.RequestDTO.AddScreenDTO;
import com.bookmyshow.Book.My.Show.DTO.RequestDTO.AddShowDTO;
import com.bookmyshow.Book.My.Show.DTO.RequestDTO.HallOwnerSignupDTO;
import com.bookmyshow.Book.My.Show.DTO.ResponseDTO.GeneralMessageDTO;
import com.bookmyshow.Book.My.Show.Exception.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.Exception.UnAuthorizedException;
import com.bookmyshow.Book.My.Show.Exception.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.Models.ApplicationUser;
import com.bookmyshow.Book.My.Show.Models.Hall;
import com.bookmyshow.Book.My.Show.Models.Screen;
import com.bookmyshow.Book.My.Show.Models.Show;
import com.bookmyshow.Book.My.Show.Service.HallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Hall API", description = "This controller contains all the Hall related service endpoint details")
@RestController
@RequestMapping("/hall")
public class HallController {
    @Autowired
    HallService hallService;



    @Operation(
            summary = "This endpoint is used for Hall Owner to SingUp ",
            description = "1.  To facilitate the signup process for a hall owner, the application expects a 'HallOwnerSignupDTO' as a @RequestBody, " +
                    "encapsulating essential information such as 'name', 'email', 'phoneNumber', 'password', 'type', 'companyAge', and 'a list of halls'. " +
                    "This DTO serves as a convenient means to convey the necessary data for the operation." + "\n\n" +
                    "2.  Upon receiving the DTO, the system proceeds to store most of its attributes in the 'application_user' table within the database. " +
                    "These attributes include the hall owner's 'name', 'email', 'phoneNumber', 'password', 'type' and 'companyAge'. " +
                    "Notably, the 'type' field in the 'application_user' table is specifically set to 'HallOwner' during this process." + "\n\n" +
                    "3.  The unique aspect of handling the list of halls involves storing them in a separate 'hall' table in the database. " +
                    "For each hall specified in the list, a corresponding entry is made in the 'hall' table, associating it with the hall owner. " +
                    "This linkage is established through a shared identifier, such as the 'hall_owner_id', " +
                    "allowing for easy retrieval of halls belonging to a particular hall owner."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hall Owner SignUp Successfully!!", content = @Content(schema = @Schema(implementation = ApplicationUser.class), mediaType = "Application/json"))
    })
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody HallOwnerSignupDTO hallOwnerSignupDTO){
        ApplicationUser hallOwner = hallService.signUp(hallOwnerSignupDTO);
        return new ResponseEntity(hallOwner, HttpStatus.CREATED);
    }



    @Operation(
            summary = "This endpoint enables hall owner to add screens to their respective halls",
            description = "1.  The system receives an 'AddScreenDTO' through the @RequestBody, containing a 'list of screens' and a 'hallId'. Simultaneously, the 'email of a user' is sent as a @RequestParam." + "\n\n" +
                    "2.  The system initiates the validation process by checking whether the provided 'email' exists in the 'application_user' table within the database." + "\n\n" +
                    "3.  If the 'email' does not exist in the 'application_user' table, a 'UserDoesNotExistException' is thrown, indicating that the specified user is not registered in the system." + "\n\n" +
                    "4.  If the email does exist, the system retrieves the 'user' based on the provided 'email'." + "\n\n" +
                    "5.  Subsequently, the system checks the 'type' of the user. If the user's 'type' is 'HallOwner', no further action is taken, as hall owners are authorized to perform the operation." + "\n\n" +
                    "6.  If the user's 'type' is different from 'HallOwner', an 'UnAuthorizedException' is thrown, indicating that the user is not authorized to add screens." + "\n\n" +
                    "7.  Moving on, the system validates the existence of the specified 'hallId' in the 'hall' table within the database." + "\n\n" +
                    "8.  If the 'hallId' is not found in the 'hall' table, a 'ResourceNotExistException' is thrown, signifying that the referenced hall does not exist in the system." + "\n\n" +
                    "9.  If the 'hallId' exists, the system retrieves the corresponding 'hall'." + "\n\n" +
                    "10.  The system proceeds to validate whether the user owns the hall identified by the 'hallId'." + "\n\n" +
                    "11.  If the user does not own the hall, an 'UnAuthorizedException' is thrown, indicating that the user lacks the necessary authorization to add screens to this particular hall." + "\n\n" +
                    "12.  After successfully passing all the aforementioned validations, the system stores all the screens from the provided list in the 'screen' table, associating them with the corresponding hall. " +
                    "This ensures a secure and controlled process, preventing unauthorized users from manipulating the system and maintaining data integrity."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Screens got added successfully in the respective hall", content = @Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json")),
            @ApiResponse(responseCode = "404", description = "User does not exist / Resource does not exist", content = @Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json")),
            @ApiResponse(responseCode = "401", description = "User is not authorized to add screens into hall", content = @Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json"))
    })
    @PostMapping("/addscreen")
    public ResponseEntity addScreen(@RequestBody AddScreenDTO addScreenDTO, @Parameter(description = "Here you need to pass hall owner's email") @RequestParam String email){
        try {
            hallService.addScreen(addScreenDTO,email);
        }catch (UserDoesNotExistException userDoesNotExistException){
            return new ResponseEntity(new GeneralMessageDTO(userDoesNotExistException.getMessage()),HttpStatus.NOT_FOUND); // 404
        }catch (UnAuthorizedException unAuthorizedException){
            return new ResponseEntity(new GeneralMessageDTO(unAuthorizedException.getMessage()),HttpStatus.UNAUTHORIZED); // 401
        }catch (ResourceNotExistException resourceNotExistException){
            return new ResponseEntity(new GeneralMessageDTO(resourceNotExistException.getMessage()),HttpStatus.NOT_FOUND); // 404
        }
        return new ResponseEntity(new GeneralMessageDTO("Screens added successfully."),HttpStatus.CREATED); // 201
    }



    @Operation(
            summary = "This endpoint enables hall owner to create show to their respective halls",
            description = "1.  The system receives an 'AddShowDTO' through the @RequestBody, containing details such as show timings ('hours' and 'minutes'), 'ticketPrice', 'movieId', and 'hallId'. Simultaneously, the 'email' of a 'user' is sent as a @RequestParam." + "\n\n" +
                    "2.  The validation process begins by checking whether the provided 'email' exists in the 'application_user' table within the database." + "\n\n" +
                    "3.  If the 'email' does not exist in the 'application_user' table, a 'UserDoesNotExistException' is thrown, indicating that the specified user is not registered in the system." + "\n\n" +
                    "4.  If the 'email' does exist, the system retrieves the 'user' based on the provided 'email'." + "\n\n" +
                    "5.  Subsequently, the system checks the 'type' of the 'user'. If the user's 'type' is 'HallOwner', no further action is taken, as hall owners are authorized to perform the operation." + "\n\n" +
                    "6.  If the user's 'type' is different from 'HallOwner', an 'UnAuthorizedException' is thrown, indicating that the user is not authorized to create shows." + "\n\n" +
                    "7.  Moving on, the system validates the existence of the specified hallId in the 'hall' table within the database." + "\n\n" +
                    "8.  If the 'hallId' is not found in the 'hall' table, a 'ResourceNotExistException' is thrown, signifying that the referenced hall does not exist in the system." + "\n\n" +
                    "9.  If the 'hallId' exists, the system retrieves the corresponding 'hall'." + "\n\n" +
                    "10.  The system proceeds to validate whether the user owns the 'hall' identified by the 'hallId'." + "\n\n" +
                    "11.  If the user does not own the hall, an 'UnAuthorizedException' is thrown, indicating that the user lacks the necessary authorization to create shows in this particular hall." + "\n\n" +
                    "12.  Next, the system validates the existence of the specified 'movieId' in the 'movie table within the database." + "\n\n" +
                    "13.  If the 'movieId' is not found in the 'movie table, a 'ResourceNotExistException' is thrown, indicating that the referenced movie does not exist in the system." + "\n\n" +
                    "14.  If the 'movieId' exists, the system retrieves the corresponding 'movie'." + "\n\n" +
                    "15.  Subsequently, the system retrieves screens that are not occupied for the particular hall from the 'screen' table in the database." + "\n\n" +
                    "16.  If no unoccupied screens are available for the specified 'hall', a 'ResourceNotExistException' is thrown, indicating the unavailability of resources." + "\n\n" +
                    "12.  After successfully passing all the validations, the system creates a show and stores it in the 'show' table in the database for the specified 'movie' and 'hall'. " +
                    "This ensures a controlled and secure process, maintaining data integrity and preventing unauthorized users from manipulating the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show created successfully for the specified movie and hall", content = @Content(schema = @Schema(implementation = Show.class), mediaType = "Application/json")),
            @ApiResponse(responseCode = "404", description = "User does not exist / Resource does not exist", content = @Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json")),
            @ApiResponse(responseCode = "401", description = "User is not authorized to create show for the specified movie and hall", content = @Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json"))
    })
    @PostMapping("/addshow")
    public ResponseEntity addShow(@RequestBody AddShowDTO addShowDTO, @Parameter(description = "Here you need to pass hall owner's email") @RequestParam String email){
        try{
            Show show = hallService.createShow(addShowDTO,email);
            return new ResponseEntity(show,HttpStatus.CREATED); // 201
        }catch (UserDoesNotExistException userDoesNotExistException){
            return new ResponseEntity(new GeneralMessageDTO(userDoesNotExistException.getMessage()),HttpStatus.NOT_FOUND); // 404
        }catch (UnAuthorizedException unAuthorizedException){
            return new ResponseEntity(new GeneralMessageDTO(unAuthorizedException.getMessage()),HttpStatus.UNAUTHORIZED); // 401
        }catch (ResourceNotExistException resourceNotExistException){
            return new ResponseEntity(new GeneralMessageDTO(resourceNotExistException.getMessage()),HttpStatus.NOT_FOUND); // 404
        }
    }
}
