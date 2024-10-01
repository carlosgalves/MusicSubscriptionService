/*
 * Copyright (c) 2022-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package leti.sidis.users.services;

import leti.sidis.users.amqp.AmqpSender;
import leti.sidis.users.api.*;
import leti.sidis.users.auth.AuthRequest;
import leti.sidis.users.model.Role;
import leti.sidis.users.model.User;
import leti.sidis.users.repositories.UserRepository;
import leti.sidis.users.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.*;

/**
 * Based on https://github.com/Yoh0xFF/java-spring-security-example
 *
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepo;
	private final EditUserMapper userEditMapper;
	private final UserViewMapper userViewMapper;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User create(final CreateUserRequest request) {
		if (userRepo.findByUsername(request.getUsername()).isPresent()) {
			throw new ConflictException("Username already exists!");
		}
		if (!request.getPassword().equals(request.getRePassword())) {
			throw new ValidationException("Passwords don't match!");
		}
		User user = userEditMapper.create(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user = userRepo.save(user);

		return user;
	}

	@Transactional
	public User update(final UUID id, final EditUserRequest request) {
		User user = userRepo.getById(id);
		userEditMapper.update(request, user);

		user = userRepo.save(user);

		return user;
	}

	@Transactional
	public UserView updateUserRole(UUID userId, String role) {

		User user = userRepo.getById(userId);
		Set<Role> userRoles = user.getAuthorities();

		Iterator<Role> iterator = userRoles.iterator();
		if (iterator.hasNext()) {
			Role existingRole = iterator.next();
			iterator.remove();
		}

		Role newRole = new Role(role);
		user.addAuthority(newRole);

		user = userRepo.save(user);

		return userViewMapper.toUserView(user);
	}

	public ResponseEntity<UserView> loginOnAnotherInstance(AuthRequest request) {
		return userRepo.loginOnAnotherInstance(request);
	}

	@Transactional
	public User upsert(final CreateUserRequest request) {
		final Optional<User> optionalUser = userRepo.findByUsername(request.getUsername());

		if (optionalUser.isEmpty()) {
			return create(request);
		}
		final EditUserRequest updateUserRequest = new EditUserRequest(request.getFullName(), request.getAuthorities());
		return update(optionalUser.get().getId(), updateUserRequest);
	}

	@Transactional
	public UserView delete(final UUID id) {
		User user = userRepo.getById(id);

		// user.setUsername(user.getUsername().replace("@", String.format("_%s@",
		// user.getId().toString())));
		//user.setEnabled(false);
		userRepo.delete(user);
//		user = userRepo.save(user);

		return userViewMapper.toUserView(user);
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User with username - %s, not found", username)));
	}

	public boolean usernameExists(final String username) {
		return userRepo.findByUsername(username).isPresent();
	}

	public UserView getUser(final UUID id) {
		return userViewMapper.toUserView(userRepo.getById(id));
	}

	public List<UserView> searchUsers(Page page, SearchUsersQuery query) {
		if (page == null) {
			page = new Page(1, 10);
		}
		if (query == null) {
			query = new SearchUsersQuery("", "");
		}
		final List<User> users = userRepo.searchUsers(page, query);
		return userViewMapper.toUserView(users);
	}

}
