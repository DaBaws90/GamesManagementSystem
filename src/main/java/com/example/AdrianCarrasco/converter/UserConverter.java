package com.example.AdrianCarrasco.converter;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.example.AdrianCarrasco.entity.User;
import com.example.AdrianCarrasco.model.UserModel;

@Component("userConverter")
public class UserConverter {

	public DozerBeanMapper dozer = new DozerBeanMapper();
	
	public UserModel transform(User user) {
		return dozer.map(user, UserModel.class);
	}
	
	public User transform(UserModel userModel) {
		return dozer.map(userModel, User.class);
	}
}
