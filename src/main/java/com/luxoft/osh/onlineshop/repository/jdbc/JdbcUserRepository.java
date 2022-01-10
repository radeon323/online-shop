package com.luxoft.osh.onlineshop.repository.jdbc;

import com.luxoft.osh.onlineshop.entity.User;
import com.luxoft.osh.onlineshop.repository.UserRepository;
import com.luxoft.osh.onlineshop.repository.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private static final String ADD = "INSERT INTO users (email, password, gender, firstName, lastName, about, age) VALUES (:email, :password, :gender, :firstName, :lastName, :about, :age);";
    private static final String FIND_ALL_SQL = "SELECT id, email, password, gender, firstName, lastName, about, age FROM users;";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?;";
    private static final String UPDATE_BY_ID_PASS = "UPDATE users SET email = ?, password = ? WHERE id = ?;";
    private static final String UPDATE_BY_ID_DATA = "UPDATE users SET gender = ?, firstName = ?, lastName = ?, about = ?, age = ? WHERE id = ?;";
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email = ?";

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    @Override
    public void remove(int id) {

    }

    @Override
    public void edit(User user) {

    }

    @Override
    public void add(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        parameters.put("gender", user.getGender());
        parameters.put("firstName", user.getFirstName());
        parameters.put("lastName", user.getLastName());
        parameters.put("about", user.getAbout());
        parameters.put("age", user.getAge());
        namedParameterJdbcTemplate.update(ADD, parameters);
    }

    @Override
    public User usrFindById(int id) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return (User) jdbcTemplate.queryForObject(FIND_BY_EMAIL, USER_ROW_MAPPER, new Object[]{email});
    }

    @Override
    public boolean isUserExist(String email) {
        if (findByEmail(email) != null) {
            if(Objects.equals(email, findByEmail(email).getEmail())) {
                return true;
            }
        }
        return false;
    }


}
