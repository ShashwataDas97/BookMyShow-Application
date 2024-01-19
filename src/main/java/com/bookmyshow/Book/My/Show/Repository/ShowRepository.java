package com.bookmyshow.Book.My.Show.Repository;

import com.bookmyshow.Book.My.Show.Models.Show;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShowRepository extends JpaRepository<Show, UUID> {

    @Query(value = "select * from show where movie_id = :movieId and hall_id = :hallId", nativeQuery = true)
    public List<Show> getShowByMovieIDAndHallID(UUID movieId, UUID hallId);

    @Query(value = "select * from show where hall_id = :hallId", nativeQuery = true)
    public List<Show> getShowByHallID(UUID hallId);

    @Query(value = "select * from show where movie_id = :movieId", nativeQuery = true)
    public List<Show> getShowByMovieID(UUID movieId);

    @Modifying
    @Transactional
    @Query(value = "update show set available_tickets = :updatedTicketCount where id = :showId", nativeQuery = true)
    public void updateAvailableTicketDecreaseByOne(UUID showId, int updatedTicketCount);
}
