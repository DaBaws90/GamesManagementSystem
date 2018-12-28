package com.example.AdrianCarrasco.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.converter.UserConverter;
import com.example.AdrianCarrasco.entity.User;
import com.example.AdrianCarrasco.model.UserModel;
import com.example.AdrianCarrasco.repository.UserJpaRepository;
import com.example.AdrianCarrasco.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	
	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@Autowired
	@Qualifier("userConverter")
	private UserConverter userConverter;

	@Override
	public List<UserModel> listAllUsers() {
		return new ArrayList<UserModel>(userJpaRepository.findAll().stream().map(u -> userConverter.transform(u)).collect(Collectors.toList()));
	}

	@Override
	public User addUser(UserModel userModel) {
		return userJpaRepository.save(userConverter.transform(userModel));
	}

	@Override
	public User updateUser(UserModel userModel) {
		return userJpaRepository.save(userConverter.transform(userModel));
	}

	@Override
	public boolean deleteUser(int id) {
		if(userJpaRepository.findById(id) != null) {
			userJpaRepository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public User disableUser(UserModel userModel) {
		userModel.setEnabled(false);
		return userJpaRepository.save(userConverter.transform(userModel));
	}

	@Override // Controlar que no haya usernames repetidos en el sistema
	public boolean checkUsernameAvailability(String username) {
		return userJpaRepository.findByUsername(username) == null;
	}

	@Override
	public UserModel findById(int id) {
		return userConverter.transform(userJpaRepository.findById(id));
	}

	@Override
	public UserModel findByUsername(String username) {
		return userConverter.transform(userJpaRepository.findByUsername(username));
	}

	
}
