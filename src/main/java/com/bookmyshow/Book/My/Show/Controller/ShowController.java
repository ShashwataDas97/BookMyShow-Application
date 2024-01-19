package com.bookmyshow.Book.My.Show.Controller;

import com.bookmyshow.Book.My.Show.DTO.ResponseDTO.GeneralMessageDTO;
import com.bookmyshow.Book.My.Show.Models.Show;
import com.bookmyshow.Book.My.Show.Service.ShowService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Show API", description = "This controller contains all the Show related service endpoint details")
@RestController
@RequestMapping("/show")
public class ShowController {
    @Autowired
    ShowService showService;

    @Operation(
            summary = "This endpoint is used to find shows",
            description = "1.  The system accepts request parameters (@RequestParam) for either 'movieId', 'hallId', or both." +
                    "At least one of these parameters must be provided for the operation to proceed." + "\n\n" +
                    "2.  The objective is to retrieve all shows that match the specified criteria." + "\n\n" +
                    "3.  If only the 'movieId' is provided, the system returns all shows associated with that particular movie." + "\n\n" +
                    "4.  If only the 'hallId' is provided, the system returns all shows scheduled for that specific hall." + "\n\n" +
                    "5.  If both 'movieId' and 'hallId' are provided, the system returns shows that are associated with both the specified movie and hall." + "\n\n" +
                    "2.  In case the provided Movie ID, Hall ID, or both do not match any shows in the system," +
                    "an empty result set is returned, signifying that no shows meet the specified criteria."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Please pass at least one parameter / Get All Shows", content = @Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json"))
    })
    @Parameters({
            @Parameter(name = "movieId", description = "Here you need to pass Movie ID"),
            @Parameter(name = "hallId", description = "Here you need to pass Hall ID")
    })
    @GetMapping("/search")
    public ResponseEntity searchShow(@RequestParam(required = false) UUID movieId, @RequestParam(required = false) UUID hallId){
        if(movieId != null && hallId != null){
            List<Show> showList = showService.getShowByMovieIDAndHallID(movieId,hallId);
            return new ResponseEntity(showList,HttpStatus.OK);
        }else if(movieId == null && hallId != null){
            List<Show> showList = showService.getShowByHallID(hallId);
            return new ResponseEntity(showList,HttpStatus.OK);
        }else if(movieId != null && hallId == null){
            List<Show> showList = showService.getShowByMovieID(movieId);
            return new ResponseEntity(showList, HttpStatus.OK);
        }else {
            return new ResponseEntity(new GeneralMessageDTO(String.format("Please pass at least one parameter")),HttpStatus.OK);
        }
    }
}
