package com.tiia.buy_01.services;

import com.tiia.buy_01.model.User;
import com.tiia.buy_01.response.UserResponse;

import java.util.List;


public interface UserService {

    UserResponse storeClient(UserResponse user);

    UserResponse storeSeller(UserResponse user);

    UserResponse getUserById(String id);

    User getUserByEmail(String email);

    UserResponse getCurrentUser();

    List<UserResponse> listAll();

    UserResponse updateUser(UserResponse userResponse, String id);
}
