package com.lemonthe.ourmusic;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lemonthe.ourmusic.scraper.AudioDataRepository;
import com.lemonthe.ourmusic.scraper.HitmoScraper;
import com.lemonthe.ourmusic.scraper.ScraperService;
import com.lemonthe.ourmusic.user.User;
import com.lemonthe.ourmusic.user.UserRepository;

/**
 * OurmusicConfiguration
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
public class OurmusicConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			return userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User \"" + username + "\" not found"));
		};
	}

	@Bean
	public ScraperService getAudioScraperService(
			AudioDataRepository audioDataRepo,
			HitmoScraper hitmoScraper) {
		return new ScraperService(audioDataRepo, List.of(hitmoScraper));
	}
	
  @Bean
  public CommandLineRunner dataLoader(UserRepository userRepository) {
    return args -> {
			if (userRepository.count() > 0)
				return;
			//userRepository.deleteAll();
			User admin = new User("adm", passwordEncoder().encode("pass"), "email@test.com", "ROLE_ADMIN");
			User user = new User("user", passwordEncoder().encode("pass"), "email@test.com", "ROLE_USER");
			userRepository.save(admin);
			userRepository.save(user);
    };
  }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http//.build();
			.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
					.requestMatchers(HttpMethod.POST, "/artist")
							.authenticated()
					.requestMatchers(HttpMethod.GET, "/artist/pending")
							.authenticated()
					.requestMatchers(HttpMethod.GET, "/artist/pending/{id}")
							.authenticated()
					.requestMatchers(HttpMethod.PATCH, "/artist/pending/approve/{id}")
							.hasAnyRole("ADMIN", "MODERATOR")
					.requestMatchers(HttpMethod.PATCH, "/artist/pending/reject/{id}")
							.hasAnyRole("ADMIN", "MODERATOR")
					.requestMatchers(HttpMethod.DELETE, "/artist/delete/{id}")
							.hasAnyRole("ADMIN", "MODERATOR")
					.requestMatchers(HttpMethod.GET, "/artist/log/{id}")
							.hasAnyRole("ADMIN", "MODERATOR")

					//.requestMatchers("/userer").permitAll()
					.anyRequest().permitAll())
			.csrf(csrf -> csrf.disable())
			.httpBasic(Customizer.withDefaults()).build();
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    String hierarchy = "ROLE_ADMIN > ROLE_MODERATOR \n ROLE_MODERATOR > ROLE_USER";
    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
	}
	
}
