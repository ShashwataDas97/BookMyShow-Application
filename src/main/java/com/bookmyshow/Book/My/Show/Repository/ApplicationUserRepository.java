package com.bookmyshow.Book.My.Show.Repository;

import com.bookmyshow.Book.My.Show.Models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, UUID> {

    public ApplicationUser findByEmail(String email);

}
