package com.c2.demo.controller;

import com.c2.demo.entity.User;
import com.c2.demo.service.UserService;
import jakarta.websocket.server.PathParam;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//controller -> service -> repository
//Controller handles HTTP layer (routing, request/response), Service handles business logic.

@RequestMapping("users")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User userToBeAdded){
        try{
            User addedUser = userService.saveUser(userToBeAdded);
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId userId){
        User user = userService.getUserById(userId).orElse(null);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("id/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/name/{name}")
    public ResponseEntity<?> updateUserByName(@RequestBody User user, @PathVariable String name){
        User updatedUser = userService.updateUserByName(user, name);
        if(updatedUser==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


}
