package cz.uhk.pro2_e;

import cz.uhk.pro2_e.model.Role;
import cz.uhk.pro2_e.model.User;
import cz.uhk.pro2_e.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Pro2EApplication {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Pro2EApplication(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            addUser("Admin", "admin", "heslo", Role.ADMIN);
        };
    }

    private void addUser(String name, String username, String password, Role role) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        userService.saveUser(user);
    }

    public static void main(String[] args) {
        SpringApplication.run(Pro2EApplication.class, args);
    }

}
