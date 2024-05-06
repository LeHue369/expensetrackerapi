package com.huele.expensetrackerapi.controller;

import com.huele.expensetrackerapi.util.JwtTokenUtil;
import com.huele.expensetrackerapi.entity.JwtResponse;
import com.huele.expensetrackerapi.entity.LoginModel;
import com.huele.expensetrackerapi.entity.User;
import com.huele.expensetrackerapi.entity.UserModel;
import com.huele.expensetrackerapi.security.CustomUserDetailsService;
import com.huele.expensetrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody LoginModel login) throws Exception {
		authenticate(login.getEmail(), login.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}

	private void authenticate(String email, String password) throws Exception {
		try{
			authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		}catch (DisabledException e){
			throw new Exception("User disabled");
		}catch (BadCredentialsException e){
			throw new Exception("Bad credentials");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserModel userModel){
		return new ResponseEntity<User>(userService.createUser(userModel), HttpStatus.CREATED);
	}

}
