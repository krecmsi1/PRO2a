package cz.uhk.pro2_e;

import cz.uhk.pro2_e.model.Role;
import cz.uhk.pro2_e.model.User;
import cz.uhk.pro2_e.repository.UserRepository;
import cz.uhk.pro2_e.security.MyUserDetails;
import cz.uhk.pro2_e.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testSaveUserPasswordEncodedAndSaved() {
        User user = new User();
        user.setPassword("rawPassword");
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        userService.saveUser(user);

        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSaveUserUsesExistingPasswordIfPasswordEmpty() {
        User user = new User();
        user.setId(1L);
        user.setPassword(""); // empty password

        User existingUser = new User();
        existingUser.setPassword("existingPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        userService.saveUser(user);

        assertEquals("existingPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSaveUserThrowsIfNewUserAndNoPassword() {
        User user = new User();
        user.setId(0L);
        user.setPassword(null);

        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    void testGetUserReturnsUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User result = userService.getUser(2L);

        assertNull(result);
    }

    @Test
    void testDeleteUserCallsRepository() {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testLoadUserByUsernameFound() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("testUser");

        assertTrue(userDetails instanceof MyUserDetails);
        assertEquals("testUser", ((MyUserDetails) userDetails).getUsername());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("notfound")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("notfound"));
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("jirka");
        when(userRepository.findByUsername("jirka")).thenReturn(user);

        User found = userService.findByUsername("jirka");

        assertNotNull(found);
        assertEquals("jirka", found.getUsername());
    }

    @Test
    void testIsAdminReturnsTrue() {
        User user = new User();
        user.setRole(Role.ADMIN);
        when(userRepository.findByUsername("adminUser")).thenReturn(user);

        boolean result = userService.isAdmin("adminUser");

        assertTrue(result);
    }

    @Test
    void testIsAdminReturnsFalseForUser() {
        User user = new User();
        user.setRole(Role.USER);
        when(userRepository.findByUsername("normalUser")).thenReturn(user);

        boolean result = userService.isAdmin("normalUser");

        assertFalse(result);
    }

    @Test
    void testIsAdminReturnsFalseIfUserNotFound() {
        when(userRepository.findByUsername("ghost")).thenReturn(null);

        boolean result = userService.isAdmin("ghost");

        assertFalse(result);
    }
}
