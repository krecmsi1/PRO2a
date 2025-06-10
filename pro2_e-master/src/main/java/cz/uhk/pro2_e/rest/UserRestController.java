package cz.uhk.pro2_e.rest;

import cz.uhk.pro2_e.model.User;
import cz.uhk.pro2_e.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/rest/users/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/rest/users/get/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PostMapping("/rest/users/new")
    public String newUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return user.toString();
    }

    @PutMapping("/rest/users/edit/{id}")
    public User editUser(@ModelAttribute User user, @PathVariable long id) {
        User editedUser = userService.getUser(id);
        if (editedUser != null) {
            userService.saveUser(editedUser);
        }
        return editedUser;
    }

    @DeleteMapping("/rest/users/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "ok";
    }

}
