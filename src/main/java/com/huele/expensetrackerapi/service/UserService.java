package com.huele.expensetrackerapi.service;

import com.huele.expensetrackerapi.entity.User;
import com.huele.expensetrackerapi.entity.UserModel;

public interface UserService {

	User createUser(UserModel user);

	User readUser();

	User updateUser(UserModel userModel);

	void deleteUser();

	User getLoggedInUser();

	User findByEmail(String email);
}
