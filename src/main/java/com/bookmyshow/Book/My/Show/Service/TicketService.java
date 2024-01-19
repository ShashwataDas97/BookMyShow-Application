package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.Exception.ResourceNotExistException;
import com.bookmyshow.Book.My.Show.Exception.UnAuthorizedException;
import com.bookmyshow.Book.My.Show.Exception.UserDoesNotExistException;
import com.bookmyshow.Book.My.Show.Models.*;
import com.bookmyshow.Book.My.Show.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    RegularUserService regularUserService;

    @Autowired
    ShowService showService;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MovieService movieService;

    @Autowired
    MailService mailService;

    @Autowired
    BarCodeGenerationService barCodeGenerationService;

    public Ticket buyTicket(String email, UUID showId){
        // Validate email exist in "application_user" table or not in database
        // If email not exist in "application_user" table in database, then we throw 'UserDoesNotExistException'
        // If Email exist in "application_user" table in database, then we get user by email
        ApplicationUser user = regularUserService.getUserByEmail(email);
        if(user == null){
            throw new UserDoesNotExistException(String.format("User with emailId %s does not exist in system.", email));
        }
        // If the type of the user is 'RegularUser', then we will do nothing
        // Else we will throw 'UnAuthorizedException'
        String userType = user.getType();
        if(userType.equals("RegularUser") == false){
            throw new UnAuthorizedException(String.format("User with email %s does not have required access.", email));
        }
        // Validate this particular showId is existing in "show" table or not in database
        // If this particular showId not exist in "show" table in database, then we throw 'ResourceNotExistException'
        // If this particular showId exist in "show" table in database, then we get show by showId
        Show show = showService.getShowByShowId(showId);
        if(show == null){
            throw new ResourceNotExistException(String.format("Show with show ID %s does not exist in our system.", showId));
        }
        // We have to decrease the count of 'available_tickets' for this particular showId from 'show' table in database as we are buying one ticket
        showService.updateAvailableTicketDecreaseByOne(show);
        Movie movie = show.getMovie();
        Hall hall = show.getHall();
        // After all validation passed, stored all the details of tickets in 'ticket' table in database for that particular user
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setMovie(movie);
        ticket.setHall(hall);
        ticket.setShow(show);
        ticketRepository.save(ticket);

        // Send all the important ticket details and barcode to user through mail
        String userMessage = String.format("Hey %s,\n" +
                "Congratulations!! your ticket got booked on our BookMyShow Application. Below are your ticket details:\n" +
                "1. Movie Name - %s\n" +
                "2. Hall Name - %s\n" +
                "3. Hall Address - %s\n" +
                "4. Date And timings - %s\n" +
                "5. Ticket Price- %d\n" +
                "\nHope you will enjoy your show, All The Best\n" +
                "BookMyShow Application", user.getName(), movie.getName(), hall.getName(), hall.getAddress(), show.getStartTime().toString(), show.getTicketPrice());
        String userSubject = String.format("Congratulations!! %s your ticket got generated !!",user.getName());
        try {
            barCodeGenerationService.generateQR(userMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
        mailService.generateMail(user.getEmail(), userSubject, userMessage, "./src/main/resources/static/QRcode.png");

        // Send all the important ticket details to hall owner through mail
        String hallMessage = String.format("Hey %s,\n" +
                "Congratulations!! one ticket got sold\n" +
                "Movie Name - %s\n" +
                "Hall Name - %s\n" +
                "Show ID - %s", hall.getHallOwner().getName(), movie.getName(), hall.getName(), show.getId().toString());
        String hallSubject = String.format("Congratulations!! %s One more ticket sold", hall.getHallOwner().getName());
        mailService.generateMail(hall.getHallOwner().getEmail(), hallSubject, hallMessage);

        // Send all the important ticket details to movie owner through mail
        int totalTickets = movieService.getTotalTicketCount(movie);
        int totalIncome = movieService.getBoxOfficeCollection(movie);
        String movieMessage = String.format("Hii %s\n" +
                "Congratulations!! your ticket got sold\n" +
                "TotalTicketsSold - %d\n" +
                "TotalIncome - %d", movie.getMovieOwner().getName(), totalTickets, totalIncome);
        String movieSubject = String.format("Congratulations!! %s One more ticket sold", movie.getMovieOwner().getName());
        mailService.generateMail(movie.getMovieOwner().getEmail(), movieSubject, movieMessage);

        return ticket;
    }
}
