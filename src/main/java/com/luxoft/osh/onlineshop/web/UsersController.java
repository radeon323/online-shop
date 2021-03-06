package com.luxoft.osh.onlineshop.web;

import com.luxoft.osh.onlineshop.entity.User;
import com.luxoft.osh.onlineshop.service.SecurityService;
import com.luxoft.osh.onlineshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
public class UsersController {

    Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;
    private final SecurityService securityService;


    @GetMapping("/login")
    protected String getLoginPage() {
        return "login";
    }


    @PostMapping("/login")
    protected String login(@RequestParam String email, @RequestParam String password,
                           HttpSession session, HttpServletResponse resp, Model model) {

        System.out.println("email - " + email + " : password - " + password + " is user exist?" + userService.isUserExist(email));

        if(userService.isUserExist(email)) {
            User user = userService.findByEmail(email);

            String md5 = null;
            try {
                md5 = SecurityService.md5(password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            if(user.getPassword().equals(md5)) {
                String userToken = UUID.randomUUID().toString();
                securityService.getUserTokens().add(userToken);
                session.setAttribute("usrEmail", email);
                session.setAttribute("usrId", user.getId());
                session.setMaxInactiveInterval(-1);

                Cookie cookie = new Cookie("user-token", userToken);
                resp.addCookie(cookie);
                return "redirect:/products";

            } else {
                String errorMsg = "Please enter correct password. <a href='/login'> Forgot password?</a>";
                model.addAttribute("errorMsg", errorMsg);
                return "login";
            }

        } else {
            String errorMsg = "User not found. Please enter correct email or <a href='/register'>register</a>.";
            model.addAttribute("errorMsg", errorMsg);
            return "login";
        }

    }


    @GetMapping("/logout")
    protected String logout(HttpServletRequest req, HttpServletResponse resp) {
        String userToken = securityService.getUserToken(req);
        Cookie cookie = new Cookie("user-token", userToken);
        cookie.setValue(null);
        cookie.setMaxAge(0);

        HttpSession session = req.getSession();
        session.removeAttribute("name");
        resp.addCookie(cookie);
        return "redirect:/login";
    }



    @GetMapping("/register")
    protected String getRegisterPage() {
        return "register";
    }


    @PostMapping("/register")
    protected String register(@RequestParam String email, @RequestParam String password,
                              @RequestParam(defaultValue= "") String gender, @RequestParam(defaultValue= "") String firstName,
                              @RequestParam(defaultValue= "") String lastName, @RequestParam(defaultValue= "") String about,
                              @RequestParam(defaultValue= "0") int age, Model model) {
        String md5 = null;
        try {
            md5 = SecurityService.md5(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (!userService.isUserExist(email)) {
            if (email != null && email.length() > 0 && password != null) {
                try {
                    User user = User.builder().
                            email(email)
                            .password(md5)
                            .gender(gender)
                            .firstName(firstName)
                            .lastName(lastName)
                            .about(about)
                            .age(age)
                            .build();
                    userService.add(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new NullPointerException();
                }

                String msgSuccess = "User <i>" + email + "</i> was successfully registered!";
                model.addAttribute("msgSuccess", msgSuccess);
                return "register";

            } else {
                String errorMsg = "Please fill up all fields!";
                model.addAttribute("errorMsg", errorMsg);
                return "register";
            }

        } else {
            String errorMsg = "This user is already exist! <a href='/login'> Login page</a>";
            model.addAttribute("errorMsg", errorMsg);
            return "register";
        }
    }

    @GetMapping("/users/edit")
    protected String getEditUserPage(@RequestParam String id, Model model) {
        User user = userService.usrFindById(Integer.parseInt(id));
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PostMapping("/users/edit")
    protected String editUser(@RequestParam String email, @RequestParam String password,
                              @RequestParam(defaultValue= "") String gender, @RequestParam(defaultValue= "") String firstName,
                              @RequestParam(defaultValue= "") String lastName, @RequestParam(defaultValue= "") String about,
                              @RequestParam(defaultValue= "0") int age, @RequestParam int id, Model model) {

        String md5 = null;
        try {
            md5 = SecurityService.md5(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            User user = User.builder().
                    id(id)
                    .email(email)
                    .password(md5)
                    .gender(gender)
                    .firstName(firstName)
                    .lastName(lastName)
                    .about(about)
                    .age(age)
                    .build();

            model.addAttribute("user", user);

            if(email != null && email.length() > 0 && password != null) {
                userService.edit(user);

                String msgSuccess = "Info was successfully updated!";
                model.addAttribute("msgSuccess", msgSuccess);
                return "edit_user";

            } else {
                String errorMsg = "Please fill up all fields";
                model.addAttribute("errorMsg", errorMsg);
                return "edit_user";
            }
        } catch (Exception e) {
            String errorMsg = "Please fill up all fields";
            model.addAttribute("errorMsg", errorMsg);
            return "edit_user";
        }
    }


}
