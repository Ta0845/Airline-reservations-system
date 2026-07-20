package com.thanvi.airline;

import com.thanvi.airline.entity.User;
import com.thanvi.airline.repository.UserRepository;
import com.thanvi.airline.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginExistingUser() {
        User user = new User();
        user.setUsername("thanvi");
        user.setPassword("encoded-password");
        user.setRole("ROLE_PASSENGER");

        when(userRepository.findByUsername("thanvi")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encoded-password")).thenReturn(true);

        String token = authService.loginUser("thanvi", "password");

        assertNotNull(token);
    }
}
