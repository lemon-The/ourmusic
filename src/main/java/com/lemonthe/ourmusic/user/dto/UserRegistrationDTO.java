package com.lemonthe.ourmusic.user.dto;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.lemonthe.ourmusic.user.User;

import jakarta.validation.constraints.NotBlank;

/**
 * UserRegistrationDTO
 */
public record UserRegistrationDTO(
		@NotBlank String username,
		@NotBlank String password,
		@NotBlank String email) {

	public User asUser(PasswordEncoder passwordEncoder, String role) {
		return new User(username, passwordEncoder.encode(password), email, role);
	}
}
