package com.basic.sensor2browser.service;

import com.basic.sensor2browser.dto.UserDto;
import com.basic.sensor2browser.exception.EmailAlreadyExistsException;
import com.basic.sensor2browser.exception.ResourceNotFoundException;
import com.basic.sensor2browser.model.EnumRole;
import com.basic.sensor2browser.model.User;
import com.basic.sensor2browser.repository.UserRepository;
import com.basic.sensor2browser.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * Service responsible for user registration, login, profile updates,
 * and JWT token generation/refresh using Spring Security.
 */
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${file.upload-dir}")
    private String uploadDir;

    public User signUp(User user) throws EmailAlreadyExistsException {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Please use another email.");
        }

        User _user = User.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(passwordEncoder.encode((user.getPassword())))
                .role(EnumRole.USER)
                .build();

        return userRepository.save(_user);
    }

    @Transactional
    public UserDto signIn(User user) throws ResourceAccessException {

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(user.getEmail(), user.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        /**
         * SecurityContextHolder.getContext().setAuthentication(authenticationResponse) - logs the authenticated user
         * authenticationResponse.getPrincipal() - returns an Object (class: UserDetails)
         * Cast returned UserDetails to User entity (userDetails doesn't have access modifier for "userName")
         * Within userDetails "userName" ≠ attribute "username")
         * The typecasting allows the extraction of: userName (≠ username), email and role
         */

        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        User _user = (User) authenticationResponse.getPrincipal();

        String token = jwtUtils.generateToken(_user.getUserName(), _user);
        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), _user.getUserName(), _user);
        Long expirationTime = jwtUtils.extractExpirationTime(token);

        UserDto userDto = UserDto.builder()
                .id(_user.getId())
                .userName(_user.getUserName())
                .email(_user.getEmail())
                .token(token)
                .refreshToken(refreshToken)
                .expirationTime(expirationTime)
                .message("success")
                .role(_user.getRole())
                .build();

        return userDto;
    }


    public UserDto update(User user, MultipartFile image) throws IOException, ResourceNotFoundException {

        // Obtain the user's identity from Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        // Fetch the managed user
        User existingUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Map ONLY the fields you want to allow updating

        if (user.getUserName() != null)
            existingUser.setUserName(user.getUserName());

        if (user.getEmail() != null)
            existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null)
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));

        // role should not be updated by an end user

        userRepository.saveAndFlush(existingUser);

        String token = jwtUtils.generateToken(existingUser.getUserName(), existingUser);
        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), existingUser.getUserName(), existingUser);
        Long expirationTime = jwtUtils.extractExpirationTime(token);

        // package the data to return
        UserDto userDto = UserDto.builder()
                .userName(existingUser.getUserName())
                .email(existingUser.getEmail())
                .token(token)                                   // return a new token as email may have changed
                .refreshToken(refreshToken)
                .expirationTime(expirationTime)
                .message("update success")
                .build();

        // The following are not returned as updates
        // - role
        // - token
        // - refreshToken
        // - expiration
        return userDto;

    }

    public void deleteUser(Integer userId) throws Throwable {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Integer userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Long findUserIdByEmail(String email) throws ResourceNotFoundException {
        java.util.Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        User user = optionalUser.get();
        return user.getId();
    }

}
