package com.huele.expensetrackerapi.service;

import com.huele.expensetrackerapi.entity.User;
import com.huele.expensetrackerapi.entity.UserModel;
import com.huele.expensetrackerapi.exceptions.ItemAlreadyExistsException;
import com.huele.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.huele.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public User createUser(UserModel userModel) {
		if(userRepo.existsByEmail(userModel.getEmail())){
			throw new ItemAlreadyExistsException("user is already registered with this email: " + userModel.getEmail());
		}
		User user = new User();
		BeanUtils.copyProperties(userModel, user);
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public User readUser() throws ResourceNotFoundException {
		Long userId = getLoggedInUser().getId();
		return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for the id: " + userId));
	}

	@Override
	public User updateUser(UserModel user) throws ResourceNotFoundException{
		User updateddUser = readUser();
		updateddUser.setName(user.getName() != null ? user.getName() : updateddUser.getName());
		updateddUser.setEmail(user.getEmail() != null ? user.getEmail() : updateddUser.getEmail());
		updateddUser.setPassword(user.getPassword() != null
			? bcryptEncoder.encode(user.getPassword()) : updateddUser.getPassword());
		updateddUser.setAge(user.getAge() != null ? user.getAge() : updateddUser.getAge());
		return userRepo.save(updateddUser);
	}

	@Override
	public void deleteUser() {
		User user = readUser();
		userRepo.delete(user);
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found for the email: " + email));
	}

	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email).get();
	}
}
