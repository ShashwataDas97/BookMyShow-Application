package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.DTO.RequestDTO.MovieOwnerSignupDTO;
import com.bookmyshow.Book.My.Show.Models.ApplicationUser;
import com.bookmyshow.Book.My.Show.Models.Movie;
import com.bookmyshow.Book.My.Show.Models.Ticket;
import com.bookmyshow.Book.My.Show.Repository.ApplicationUserRepository;
import com.bookmyshow.Book.My.Show.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    MovieRepository movieRepository;

    public ApplicationUser signUp(MovieOwnerSignupDTO movieOwnerSignupDTO){
        ApplicationUser movieOwner = new ApplicationUser();
        movieOwner.setName(movieOwnerSignupDTO.getName());
        movieOwner.setEmail(movieOwnerSignupDTO.getEmail());
        movieOwner.setPhoneNumber(movieOwnerSignupDTO.getPhoneNumber());
        movieOwner.setPassword(movieOwnerSignupDTO.getPassword());
        movieOwner.setType(movieOwnerSignupDTO.getType().toString());
        movieOwner.setAge(movieOwnerSignupDTO.getCompanyAge());
        List<Movie> movies = movieOwnerSignupDTO.getMovies();
        applicationUserRepository.save(movieOwner);
        for(Movie movie : movies){
            movie.setMovieOwner(movieOwner);
            movieRepository.save(movie);
        }
        return movieOwner;
    }

    public Movie getMovieById(UUID movieId){
        return movieRepository.findById(movieId).orElse(null);
    }

    public int getTotalTicketCount(Movie movie){
        return movie.getTickets().size();
    }

    public int getBoxOfficeCollection(Movie movie){
        int totalIncome = 0;
        for(Ticket ticket : movie.getTickets()){
            totalIncome = totalIncome + ticket.getShow().getTicketPrice();
        }
        return totalIncome;
    }
}
