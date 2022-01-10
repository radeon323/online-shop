package com.luxoft.osh.onlineshop.service;

import com.luxoft.osh.onlineshop.entity.User;
import com.luxoft.osh.onlineshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void add(User user) throws SQLException {
        userRepository.add(user);
        System.out.println("Add user " + user.getFirstName() + user.getLastName());
    }

    public void remove(int id) {
        userRepository.remove(id);
        System.out.println("Remove user with id " + id);
    }

    public void edit(User user) {
        userRepository.edit(user);
    }

    public User usrFindById(int id) {
        return userRepository.usrFindById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isUserExist(String email) {
        return userRepository.isUserExist(email);
    }

}
