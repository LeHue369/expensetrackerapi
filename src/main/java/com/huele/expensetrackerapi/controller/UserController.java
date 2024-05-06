package com.huele.expensetrackerapi.controller;

import com.huele.expensetrackerapi.entity.User;
import com.huele.expensetrackerapi.entity.UserModel;
import com.huele.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.huele.expensetrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/read")
	public ResponseEntity<User> get(){
		return new ResponseEntity<User>(userService.readUser(), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<User> update(@RequestBody UserModel user){
		User responseUser = userService.updateUser(user);
		return new ResponseEntity<User>(responseUser, HttpStatus.OK);
	}

	@DeleteMapping("/deactivate")
	public ResponseEntity<HttpStatus> delete() throws ResourceNotFoundException{
		userService.deleteUser();
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/email")
	public ResponseEntity<User> findByEmail(String email) throws ResourceNotFoundException{
		User responseUser = userService.findByEmail(email);
		return new ResponseEntity<User>(responseUser, HttpStatus.OK);
	}
}
