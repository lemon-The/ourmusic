package com.lemonthe.ourmusic.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public User register(User user) {
		return userRepo.save(user);
	}

	public Optional<User> getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
}
