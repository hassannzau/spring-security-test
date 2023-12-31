package com.hassan.springsecurity.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hassan.springsecurity.test.entity.UserEntity;
import com.hassan.springsecurity.test.repository.UserRepository;
import com.hassan.springsecurity.test.util.CustomUserDetails;

@Service
public class UserService implements UserDetailsService{
	
	 @Autowired
	    UserRepository userRepo;

	    public PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        Optional<UserEntity> user = userRepo.findByEmail(email);
	        return user.map(CustomUserDetails::new)
	                .orElseThrow(() -> new UsernameNotFoundException("Username "+email+" not found"));
	    }

	    //Create a user
	    public UserEntity addUser(UserEntity user) {
	        user.setPassword(passwordEncoder().encode(user.getPassword()));
	        return userRepo.save(user);
	    }

	    //Fetch all users
	    public List<UserEntity> getAllUsers() {
	        return userRepo.findAll();
	    }

	    //Get a single user by Id
	    public UserEntity getUserById(long id) {
	        return userRepo.findById(id)
	                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
	    }

}
