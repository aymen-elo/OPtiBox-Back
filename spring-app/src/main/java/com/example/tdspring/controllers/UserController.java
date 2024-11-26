package com.example.tdspring.controllers;

import com.example.tdspring.exceptions.DBException;
import com.example.tdspring.exceptions.NotFoundException;
import com.example.tdspring.models.User;
import com.example.tdspring.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody User userSent) {
        try {
            log.info("Creating user ...");
            return userSent.getId() == null ?
                    new ResponseEntity<>(this.userService.updateUser(userSent), HttpStatus.CREATED) :
                    new ResponseEntity<>(this.userService.updateUser(userSent), HttpStatus.ACCEPTED);
        } catch (DBException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        try {
            log.info("Deleting user ...");
            return new ResponseEntity<>(this.userService.deleteUser(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DBException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.userService.getUser(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<String> getUserInfo(@PathVariable Long id) {
        try {
            String info = this.userService.getUserInfo(id).toString();
            return new ResponseEntity<>(info, HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
