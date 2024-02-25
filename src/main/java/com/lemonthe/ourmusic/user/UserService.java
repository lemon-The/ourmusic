package com.lemonthe.ourmusic.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Transactional
	public User register(User user) {
		return userRepo.save(user);
	}
}
