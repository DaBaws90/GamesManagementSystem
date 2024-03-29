package com.example.AdrianCarrasco.service;

import java.util.List;

import com.example.AdrianCarrasco.entity.User;
import com.example.AdrianCarrasco.model.UserModel;

public interface UserService {

	public abstract List<UserModel> listAllUsers();
	public abstract User addUser(UserModel userModel);
	public abstract User updateUser(UserModel userModel);
	public abstract boolean deleteUser(int id);
	
	public abstract User disableUser(int id);
	public abstract boolean checkUsernameAvailability(String username);
	public abstract boolean checkEmailAvailability(String email);
	
	public abstract UserModel findById(int id);
	public abstract UserModel findByUsername(String username);
	
	public abstract List<UserModel> listNotDisableUsers();
}
