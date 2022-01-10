package com.luxoft.osh.onlineshop.repository;

import com.luxoft.osh.onlineshop.entity.User;

public interface UserRepository {

    void remove(int id);

    void edit(User user);

    void add(User user);

    User usrFindById(int id);

    User findByEmail (String email);

    boolean isUserExist(String email);
}
