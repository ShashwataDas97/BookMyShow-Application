package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.DTO.RequestDTO.AddScreenDTO;
import com.bookmyshow.Book.My.Show.DTO.RequestDTO.AddShowDTO;
import com.bookmyshow.Book.My.Show.DTO.RequestDTO.HallOwnerSignupDTO;
import com.bookmyshow.Book.My.Show.Exception.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.Exception.UnAuthorizedException;
import com.bookmyshow.Book.My.Show.Exception.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.Models.*;
import com.bookmyshow.Book.My.Show.Repository.ApplicationUserRepository;
import com.bookmyshow.Book.My.Show.Repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HallService {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    HallRepository hallRepository;

    @Autowired
    RegularUserService regularUserService;

    @Autowired
    ScreenService screenService;

    @Autowired
    MovieService movieService;

    @Autowired
    ShowService showService;

    public ApplicationUser signUp(HallOwnerSignupDTO hallOwnerSignupDTO){
        ApplicationUser hallOwner = new ApplicationUser();
        hallOwner.setName(hallOwnerSignupDTO.getName());
        hallOwner.setEmail(hallOwnerSignupDTO.getEmail());
        hallOwner.setPhoneNumber(hallOwnerSignupDTO.getPhoneNumber());
        hallOwner.setPassword(hallOwnerSignupDTO.getPassword());
        hallOwner.setType(hallOwnerSignupDTO.getType().toString());
        hallOwner.setAge(hallOwnerSignupDTO.getCompanyAge());
        List<Hall> halls = hallOwnerSignupDTO.getHalls();
        applicationUserRepository.save(hallOwner);
        for(Hall hall : halls){
            hall.setHallOwner(hallOwner);
            hallRepository.save(hall);
        }
        return hallOwner;
    }

    public Hall getHallById(UUID id){
        return hallRepository.findById(id).orElse(null);
    }

    public void addScreen(AddScreenDTO addScreenDTO, String email) {
        List < Screen > screens = addScreenDTO.getScreens();
        UUID hallId = addScreenDTO.getHallId();
        // Validate email exist in "application_user" table or not in database
        // If email not exist in "application_user" table in database, then we throw 'UserDoesNotExistException'
        // If Email exist in "application_user" table in database, then we get user by email
        ApplicationUser user = regularUserService.getUserByEmail(email);
        if (user == null) {
            throw new UserDoesNotExistException(String.format("User with this email does not exist."));
        }
        // If the type of the user is 'HallOwner', then we will do nothing
        // Else we will throw 'UnAuthorizedException'
        String userType = user.getType();
        if (userType.equals("HallOwner") == false) {
            throw new UnAuthorizedException(String.format("User does not have access to perform this operation."));
        }
        // Validate this particular hallId is existing in "hall" table or not in database
        // If this particular hallId not exist in "hall" table in database, then we throw 'ResourceNotExistException'
        // If this particular hallId exist in "hall" table in database, then we get hall by hallId
        Hall hall = getHallById(hallId);
        if (hall == null) {
            throw new ResourceNotExistException(String.format("Hall ID %s does not exist in System.", hallId.toString())); // This hallId variable is the variable we are passing in postman
        }
        // Validate user owns this hall or not
        // If user does not own this hall then user is unauthorized to add screen in this hall. In that case, we throw 'UnAuthorizedException'
        ApplicationUser hallOwner = hall.getHallOwner();
        UUID hallOwnerId = hallOwner.getId();
        UUID userId = user.getId();
        if (hallOwnerId != userId) {
            throw new UnAuthorizedException(String.format("User does not own this hall ID."));
        }
        // After all the validation passed, all the screens in the list stored in "screen" table in database for that corresponding hall
        for (Screen screen: screens) {
            screen.setHall(hall);
            screenService.registerScreen(screen);
        }
    }

    public Show createShow(AddShowDTO addShowDTO, String email) {
        // Validate email exist in "application_user" table or not in database
        // If email not exist in "application_user" table in database, then we throw 'UserDoesNotExistException'
        // If Email exist in "application_user" table in database, then we get user by email
        ApplicationUser user = regularUserService.getUserByEmail(email);
        if (user == null) {
            throw new UserDoesNotExistException(String.format("User with email id %s does not exist in system.", email));
        }
        // If the type of the user is 'HallOwner', then we will do nothing
        // Else we will throw 'UnAuthorizedException'
        String userType = user.getType();
        if (userType.equals("HallOwner") == false) {
            throw new UnAuthorizedException(String.format("User with emailId %s does not have required permission to perform this action.", email));
        }
        // Validate this particular hallId is existing in "hall" table or not in database
        // If this particular hallId not exist in "hall" table in database, then we throw 'ResourceNotExistException'
        // If this particular hallId exist in "hall" table in database, then we get hall by hallId
        UUID hallId = addShowDTO.getHallId();
        Hall hall = getHallById(hallId);
        if (hall == null) {
            throw new ResourceNotExistException(String.format("Hall with id %s does not exist in system.", hallId.toString()));
        }
        // Validate user owns this hall or not
        // If user does not own this hall then user is unauthorized to create show in this hall. In that case, we throw 'UnAuthorizedException'
        ApplicationUser hallOwner = hall.getHallOwner();
        UUID hallOwnerId = hallOwner.getId();
        UUID userId = user.getId();
        if (hallOwnerId != userId) {
            throw new UnAuthorizedException(String.format("User with email ID %s does not own hall with Hall ID %s.", email, hallId.toString()));
        }
        // Validate this particular movieId is existing in "movie" table or not in database
        // If this particular movieId not exist in "movie" table in database, then we throw 'ResourceNotExistException'
        // If this particular movieId exist in "movie" table in database, then we get movie by movieId
        UUID movieId = addShowDTO.getMovieId();
        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            throw new ResourceNotExistException(String.format("Movie with movie ID %s does not exist in system.", movieId.toString()));
        }
        // After that get screen that are not occupied for that particular hall from "screen" table in database
        // If we can't get any screen that are not occupied for that particular hall from "screen" table in database, then we throw 'ResourceNotExistException
        List < Screen > screenList = new ArrayList < > ();
        for (Screen screen: hall.getScreens()) {
            if (screen.isStatus() == false) {
                screenList.add(screen);
            }
        }
        if (screenList.size() == 0) {
            throw new ResourceNotExistException(String.format("Hall with hall ID %s does not have any unoccupied screen", hallId.toString()));
        }
        Screen screen = screenList.get(0);
        // After all the validation passed, create show and stored it in "show" table in database for that particular movie and for that particular hall
        Show show = new Show();
        show.setHall(hall);
        show.setMovie(movie);
        show.setScreen(screen);
        show.setAvailableTickets(screen.getScreenCapacity());
        Date startDateTime = new Date();
        startDateTime.setHours(addShowDTO.getHours());
        startDateTime.setMinutes(addShowDTO.getMinutes());
        startDateTime.setSeconds(0);
        // 22:00 -> EndTime -> 22 + Movie Duration(4) -> 22 + 4 -> 26:00 -> 26%24 -> 02:00
        Date endDateTime = new Date();
        int hours = (int)(addShowDTO.getHours() + movie.getDuration()) % 24;
        endDateTime.setHours(hours);
        endDateTime.setMinutes(addShowDTO.getMinutes());
        endDateTime.setSeconds(0);
        show.setStartTime(startDateTime);
        show.setEndTime(endDateTime);
        show.setTicketPrice(addShowDTO.getTicketPrice());
        // Mark status of screen as true. Such that no other show can book that
        screenService.bookScreen(screen.getId());
        showService.createShow(show);
        return show;
    }
}
