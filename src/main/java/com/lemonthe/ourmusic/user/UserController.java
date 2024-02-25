package com.lemonthe.ourmusic.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lemonthe.ourmusic.user.dto.UserRegistrationDTO;

import jakarta.validation.Valid;

/**
 * UserController
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PreAuthorize("permitAll()")
	@PostMapping
	public ResponseEntity<?> registerUser(
			@Valid @RequestBody UserRegistrationDTO userDTO,
			Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);
		}
		User user = userService.register(userDTO.asUser(passwordEncoder, "ROLE_USER"));
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(user.getId())
			.toUri();
		return ResponseEntity.created(location).build();
	}
}
