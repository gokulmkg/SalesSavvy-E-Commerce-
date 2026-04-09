package com.kodnest.ToyGalaxy.Controllers;

import com.kodnest.ToyGalaxy.Service.UserService;
import com.kodnest.ToyGalaxy.Service.emailService;
import com.kodnest.ToyGalaxy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final emailService emailService;

    public UserController(UserService userService, emailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            String subject = "🎉 Welcome to ToyGalaxy!";
            String body = "<div style='font-family: Arial, sans-serif; padding:20px;'>"
                    + "<h2 style='color:#4CAF50;'>Welcome, " + user.getUsername() + " 👋</h2>"
                    + "<p>Thank you for registering with us.</p>"
                    + "<p>We are excited to have you onboard.</p>"
                    + "<br>"
                    + "<a href='http://localhost:5174' "
                    + "style='background-color:#4CAF50; color:white; padding:10px 20px; "
                    + "text-decoration:none; border-radius:5px;'>Login Now</a>"
                    + "<br><br>"
                    + "<p style='color:gray; font-size:12px;'>If you did not register, please ignore this email.</p>"
                    + "<br>"
                    + "<p>Regards,<br><b>Your Company Team</b></p>"
                    + "</div>";
             emailService.sendEmail(user.getEmail(),subject,body);
            return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", registeredUser));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
