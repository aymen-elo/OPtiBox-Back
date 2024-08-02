package com.example.tdspring.services;

import com.example.tdspring.exceptions.DBException;
import com.example.tdspring.exceptions.NotFoundException;

import com.example.tdspring.models.Action;
import com.example.tdspring.models.Check;
import com.example.tdspring.models.History;
import com.example.tdspring.models.User;

import com.example.tdspring.repositories.CheckRepository;
import com.example.tdspring.repositories.HistoryRepository;
import com.example.tdspring.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;
    private final CheckRepository checkRepository;


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

    /* Get information for user : the most recent action (deposit or withdraw or check) */
    public String getUserInfo(Long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        // Scans (deposit or withdraw) are in History table
        List<History> userHistories = historyRepository.findByUser(user);
        List<Check> userChecks = checkRepository.findByUser(user); // Fetch checks

        // Find the most recent scan (deposit or withdraw)
        History latestScan = userHistories.stream()
                .max(Comparator.comparing(History::getDate))
                .orElse(null);

        // Find the most recent check
        Check latestCheck = userChecks.stream()
                .max(Comparator.comparing(Check::getDate))
                .orElse(null);

        // No recent action
        if (latestScan == null && latestCheck == null) {
            return "NA";
        }

        String actionType;
        Long stockId;
        String productName;

        // True when the latest Action is Scan, False when it is a Check
        boolean scanIsLatest =
                latestScan != null && latestCheck == null ||
                 latestScan != null && latestScan.getDate().after(latestCheck.getDate());

        Action latestAction = (latestScan != null && scanIsLatest) ? latestScan :
                                                                      latestCheck;

        actionType = latestAction.getType();
        stockId = latestAction.getStock().getId();
        productName = latestAction.getStock().getProduct().getTitle();

        return actionType + "|" + stockId + "|" + productName;
    }
}
