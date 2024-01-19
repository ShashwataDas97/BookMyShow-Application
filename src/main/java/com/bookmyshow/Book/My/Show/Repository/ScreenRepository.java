package com.bookmyshow.Book.My.Show.Repository;

import com.bookmyshow.Book.My.Show.Models.Screen;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, UUID> {

    @Modifying
    @Transactional
    @Query(value = "update screen set status = true where id = :screenId", nativeQuery = true)
    public void bookScreen(UUID screenId);

}
