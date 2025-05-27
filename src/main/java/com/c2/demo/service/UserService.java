package com.c2.demo.service;

import com.c2.demo.entity.User;
import com.c2.demo.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId requestedUserId) {
        return userRepository.findById(requestedUserId);
    }

    public void deleteUserById(ObjectId userId) {
        userRepository.deleteById(userId);
    }

    public User updateUserByName(User user, String name){
        User oldUser = userRepository.findByName(name);
        if(oldUser!=null){
            oldUser.setPassword(user.getPassword());
            oldUser.setName(user.getName());
            userRepository.save(oldUser);  //since id will be same, old entry will be overwritten
        }
        return oldUser;
    }

    public User getUserByUserName(String userName){
        return userRepository.findByName(userName);
    }
}
