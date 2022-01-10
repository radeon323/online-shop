package com.luxoft.osh.onlineshop.repository.mapper;

import com.luxoft.osh.onlineshop.entity.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int numRow) throws SQLException {
        int id  = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String gender = resultSet.getString("gender");
        String firstName = resultSet.getString("firstName").trim();
        String lastName = resultSet.getString("lastName").trim();
        String about = resultSet.getString("about").trim();
        int age  = resultSet.getInt("age");
        User user = null;
        user = User.builder().
                id(id)
                .email(email)
                .password(password)
                .gender(gender)
                .firstName(firstName)
                .lastName(lastName)
                .about(about)
                .age(age)
                .build();
        return user;
    }


}
