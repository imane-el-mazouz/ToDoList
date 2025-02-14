package com.ToDoList.service;


import com.ToDoList.model.User;
import com.ToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Lazy
    @Autowired
    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public JwtResponseDTO signUp(User userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new RuntimeException("Email is already taken.");
        }
        if (userRequest.getRole() == null) {
            userRequest.setRole(Role.ADMIN);
        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User savedUser = userRepository.save(userRequest);

        String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getRole());

        return JwtResponseDTO.builder()
                .accessToken(token)
                .user(savedUser)
                .build();
    }

    public JwtResponseDTO login(AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword())
            );
            System.out.println("Authentification réussie : " + authentication.isAuthenticated());

            if (authentication.isAuthenticated()) {
                User user = userRepository.findByEmail(authRequestDTO.getEmail());
                String token = jwtService.generateToken(user.getEmail(), user.getRole());
                return JwtResponseDTO.builder()
                        .accessToken(token)
                        .user(user)
                        .build();
            } else {
                throw new UsernameNotFoundException("Invalid user request.");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log l'exception pour mieux la comprendre
            throw e;  // Lancer l'exception après avoir imprimé les logs
        }
    }

}
