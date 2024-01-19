package com.bookmyshow.Book.My.Show.Service;

import com.bookmyshow.Book.My.Show.DTO.RequestDTO.RegularUserSignupDTO;
import com.bookmyshow.Book.My.Show.Models.ApplicationUser;
import com.bookmyshow.Book.My.Show.Repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegularUserService {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public ApplicationUser signUp(RegularUserSignupDTO regularUserSignupDTO){
        ApplicationUser regularUser = new ApplicationUser();
        regularUser.setName(regularUserSignupDTO.getName());
        regularUser.setEmail(regularUserSignupDTO.getEmail());
        regularUser.setPhoneNumber(regularUserSignupDTO.getPhoneNumber());
        regularUser.setPassword(regularUserSignupDTO.getPassword());
        regularUser.setType(regularUserSignupDTO.getType().toString());
        regularUser.setAge(regularUserSignupDTO.getAge());
        applicationUserRepository.save(regularUser);
        return regularUser;
    }

    public ApplicationUser getUserByEmail(String email){
        return applicationUserRepository.findByEmail(email);
    }
}
