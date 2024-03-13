package com.tiia.buy_01.services.impl;

import com.tiia.buy_01.converter.TempConverter;
import com.tiia.buy_01.exceptions.ExistingEmailException;
import com.tiia.buy_01.exceptions.InvalidUserException;
import com.tiia.buy_01.model.User;
import com.tiia.buy_01.repository.UserRepository;
import com.tiia.buy_01.response.UserResponse;
import com.tiia.buy_01.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TempConverter tempConverter;

    @Override
    public UserResponse storeClient(UserResponse user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            Error error = new Error("Email exists already.");
            throw new ExistingEmailException(error);
        }
        user.setRole("Client");
        user.setAvatar("");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User storedUser = userRepository.save(tempConverter.userResponseToUser(user));
        return tempConverter.userToUserResponse(storedUser);
    }

    @Override
    public UserResponse storeSeller(UserResponse user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            Error error = new Error("Email exists already.");
            throw new ExistingEmailException(error);
        }
        user.setRole("Seller");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User storedUser = userRepository.save(tempConverter.userResponseToUser(user));
        return tempConverter.userToUserResponse(storedUser);
    }

    @Override
    public UserResponse getUserById(String id) {
        UserResponse returnValue = null;
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            returnValue = tempConverter.userToUserResponse(userOptional.get());
        } else {
            Error error = new Error("The user has not been found.");
            throw new InvalidUserException(error);
        }
        return returnValue;
    }

    @Override
    public User getUserByEmail(String email) {
        User returnValue = null;
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            returnValue = userOptional.get();
        } else {
            Error error = new Error("The user has not been found.");
            throw new InvalidUserException(error);
        }
        return returnValue;
    }

    @Override
    public UserResponse getCurrentUser() {
        UserResponse returnValue = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                Optional<User> userOptional = userRepository.findByEmail(currentUserName);
                if (userOptional.isPresent()) {
                    returnValue = tempConverter.userToUserResponse(userOptional.get());
                }
            }
        } catch (Exception exc) {
            Error error = new Error("Invalid user!");
            throw new InvalidUserException(error);
        }
        return returnValue;
    }

    @Override
    public List<UserResponse> listAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> tempConverter.userToUserResponse(user))
                .collect(Collectors.toList());
    }


    @Override
    public UserResponse updateUser(UserResponse userResponse, String id) {
        // First, check if the user with the given ID exists
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            Error error = new Error("User not found with ID: " + id);
            throw new InvalidUserException(error);
        }

        userResponse.setId(id);
        User user = tempConverter.userResponseToUser(userResponse);
        User updatedUser = userRepository.save(user);

        return tempConverter.userToUserResponse(updatedUser);
    }
}
