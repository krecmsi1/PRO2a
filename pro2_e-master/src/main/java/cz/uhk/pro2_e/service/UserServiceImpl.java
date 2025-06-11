package cz.uhk.pro2_e.service;

import cz.uhk.pro2_e.model.Role;
import cz.uhk.pro2_e.model.User;
import cz.uhk.pro2_e.repository.UserRepository;
import cz.uhk.pro2_e.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
public void saveUser(User user) {
    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    } else if (user.getId() != 0) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            user.setPassword(existingUser.getPassword());
        }
    } else {
        throw new IllegalArgumentException("Password cannot be null for new user");
    }

    if (user.getRole() == null) {
        user.setRole(Role.USER);
    }

    userRepository.save(user);
    System.out.println("Ukládám uživatele: " + user.getUsername() + ", heslo: " + user.getPassword());

}


    @Override
    public User getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserDetails(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public boolean isAdmin(String username) {
    User user = findByUsername(username);
    return user != null && user.getRole() == Role.ADMIN;
    }

}
