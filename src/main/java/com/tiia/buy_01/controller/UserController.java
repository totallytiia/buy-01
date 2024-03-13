package com.tiia.buy_01.controller;

import com.tiia.buy_01.converter.TempConverter;
import com.tiia.buy_01.exceptions.DataNotValidatedException;
import com.tiia.buy_01.model.User;
import com.tiia.buy_01.response.UserResponse;
import com.tiia.buy_01.services.UserService;
import com.tiia.buy_01.utils.AuthenticationRequest;
import com.tiia.buy_01.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private TempConverter tempConverter;


    @PostMapping(value = "/register/client")
    public ResponseEntity<String> registerClient(@Validated @RequestBody UserResponse user, Errors errors) {

        if (errors.hasErrors()) {
            Error error = new Error("Data is not validated!");
            throw new DataNotValidatedException(error);
        }

        UserResponse storedUser = userService.storeClient(user);

        return ResponseEntity.ok().body("Registration completed! Your ID is: " + storedUser.getId());
    }

    @PostMapping(value = "/register/seller")
    public ResponseEntity<String> registerSeller(@Validated @RequestBody UserResponse user, Errors errors) {

        if (errors.hasErrors()) {
            Error error = new Error("Data is not validated!");
            throw new DataNotValidatedException(error);
        }

        UserResponse storedUser = userService.storeSeller(user);

        return ResponseEntity.ok().body("Registration completed! Your ID is: " + storedUser.getId());
    }




    @PostMapping(value = "/login")
    public ResponseEntity<UserResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or Password incorrect", e);
        }
        final User userDetails = userService.getUserByEmail(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        UserResponse userResponse = tempConverter.userToUserResponse(userDetails);

        userResponse.setAuthToken(jwt);

        return ResponseEntity.ok().body(userResponse);

    }

    // @PreAuthorize(value = "hasAuthority('ROLE_USER')")
    @PutMapping(value = "/updateUser/{userId}")
    public ResponseEntity<String> updateUser(@Validated @RequestBody UserResponse userResponse,
                                             Errors errors, @PathVariable("userId") String userId) {
        if(errors.hasErrors()) {
            Error error = new Error("The data is not validated!");
            throw new DataNotValidatedException(error);
        }

        UserResponse updatedUser = userService.updateUser(userResponse, userId);
        return ResponseEntity.ok().body("The user with id " + updatedUser.getId()
                + " has been updated without any issues");
    }

    @GetMapping(value = "/currentUser")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserResponse userResponse = userService.getCurrentUser();
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping(value = "/loggedIn")
    public ResponseEntity<Boolean> isAuthenticated() {
        Boolean isAuthenticated = false;
        Optional<UserResponse> userResponseOptional = Optional.ofNullable(userService.getCurrentUser());
        if (userResponseOptional.isPresent()) {
            isAuthenticated = true;
        }
        return ResponseEntity.ok().body(isAuthenticated);
    }
}
