package com.tiia.buy_01.converter;

import com.tiia.buy_01.model.Role;
import com.tiia.buy_01.model.User;
import com.tiia.buy_01.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TempConverter {

    @Autowired
    private ModelMapper mapper;

    public UserResponse userToUserResponse(User user) {
        UserResponse returnValue = mapper.map(user, UserResponse.class);
        Optional<Role> roleOptional = Optional.ofNullable(user.getRole());
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            String roleStr = role.toString();
            returnValue.setRole(roleStr);
        }
        return returnValue;
    }

    public User userResponseToUser(UserResponse userResponse) {
        return mapper.map(userResponse, User.class);
    }
}
