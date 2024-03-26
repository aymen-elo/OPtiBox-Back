package com.example.tdspring.services;

import com.example.tdspring.exceptions.DBException;
import com.example.tdspring.exceptions.NotFoundException;
import com.example.tdspring.models.User;
import com.example.tdspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User updateUser(User user) throws DBException, NotFoundException {
        User existing;

        if (user.getId() != null) {
            existing = this.userRepository.findById(user.getId()).orElse(null);
            if (existing == null) throw new NotFoundException("Could not find user with id : " + user.getId());
        } else {
            existing = new User();
        }


        if (user.getUsername() != null) existing.setUsername(user.getUsername());
        if (user.getPassword() != null) existing.setPassword(user.getPassword());
        if (user.getToken() != null) existing.setToken(user.getToken());
        if (user.getRole() != null) existing.setRole(user.getRole());

        try {
            User userCreated = this.userRepository.save(existing);
            return userCreated;
        } catch (Exception e) {
            throw new DBException("Could not create user");
        }
    }

    public User deleteUser(Long id) throws NotFoundException, DBException {
        User existing = this.userRepository.findById(id).orElse(null);
        if (existing == null) throw new NotFoundException("Could not find user with id : " + id);

        try {
            this.userRepository.delete(existing);
            return existing;
        } catch (Exception e) {
            throw new DBException("Could not delete user");
        }
    }

    public User getUser(Long id) throws NotFoundException {
        User existing = this.userRepository.findById(id).orElse(null);
        if (existing == null) throw new NotFoundException("Could not find user with id : " + id);
        return existing;
    }
}
