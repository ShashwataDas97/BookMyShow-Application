package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.Models.Show;
import com.bookmyshow.Book.My.Show.Repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShowService {
    @Autowired
    ShowRepository showRepository;

    public void createShow(Show show){
        showRepository.save(show);
    }

    public List<Show> getShowByMovieIDAndHallID(UUID movieId, UUID hallId){
        return showRepository.getShowByMovieIDAndHallID(movieId,hallId);
    }

    public List<Show> getShowByHallID(UUID hallId){
        return showRepository.getShowByHallID(hallId);
    }

    public List<Show> getShowByMovieID(UUID movieId){
        return showRepository.getShowByMovieID(movieId);
    }

    public Show getShowByShowId(UUID showId){
        return showRepository.findById(showId).orElse(null);
    }

    public void updateAvailableTicketDecreaseByOne(Show show){
        UUID showId = show.getId();
        int updatedAvailableTicket = show.getAvailableTickets() - 1;
        showRepository.updateAvailableTicketDecreaseByOne(showId,updatedAvailableTicket);
    }
}
