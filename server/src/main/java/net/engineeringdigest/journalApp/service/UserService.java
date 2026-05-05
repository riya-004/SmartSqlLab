package net.engineeringdigest.journalApp.service;

import lombok.NonNull;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {
    @Autowired
    private UserRepo userRepo;
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public User findByUserName(@NonNull String userName) {
       return userRepo.findByUserName(userName);
    }
}
