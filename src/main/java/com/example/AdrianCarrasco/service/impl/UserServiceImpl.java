package com.example.AdrianCarrasco.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.converter.UserConverter;
import com.example.AdrianCarrasco.entity.User;
import com.example.AdrianCarrasco.entity.UserRole;
import com.example.AdrianCarrasco.model.UserModel;
import com.example.AdrianCarrasco.repository.UserJpaRepository;
import com.example.AdrianCarrasco.repository.UserRoleJpaRepository;
import com.example.AdrianCarrasco.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	
	@Autowired
	@Qualifier("userJpaRepository")
	private UserJpaRepository userJpaRepository;
	
	@Autowired
	@Qualifier("userRoleJpaRepository")
	private UserRoleJpaRepository userRoleJpaRepository;
	
	@Autowired
	@Qualifier("userConverter")
	private UserConverter userConverter;

	@Override
	public List<UserModel> listAllUsers() {
		return new ArrayList<UserModel>(userJpaRepository.findAll().stream().map(u -> userConverter.transform(u)).collect(Collectors.toList()));
	}

	@Override
	public User addUser(UserModel userModel) {
//		Generamos un objeto BCrypt para utilizar un algoritmo de cofrado de contraseñas
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		Ciframos la contraseña del objeto userModel y luego establecemos su atributo pasword con ese valor ante de guardarlo en la base de datos
		userModel.setPassword(encoder.encode(userModel.getPassword()));
//		Enabled a true
		userModel.setEnabled(true);
//		Lo asigno a objeto "User" para que obtenga un valor de id tras persistirlo en la BD, así puedo aignarle los roles correctamente
		User user = userJpaRepository.save(userConverter.transform(userModel));
//		Con cada nuevo registro, asigno rol de usuario convencional y persisto los datos en la tabla role_user
		userRoleJpaRepository.save(new UserRole(user, "ROLE_USER"));
//		Devuelvo el objeto "user" que se generó en primera instancia
		return user;
		
//		REALIZAREMOS MEJOR LA COMPROBACION EN EL CONTROLADOR, ASÍ SABRÉ SI EL ERROR ES POR EL EMAIL O POR EL USERNAME, YA QUE NO HABRÁ EVALUACION 
//		CONDICIONAL DOBLE, ADEMAS PODRÉ REDIRECCIONAR CON MENSAJES DE AYUDA AL USUARIO (dejo la chuletita aquí)
		
//		if(checkUsernameAvailability(userModel.getUsername()) && checkEmailAvailability(userModel.getEmail())) {
//			return userJpaRepository.save(userConverter.transform(userModel));
//		}
//		else {
//			return null;
//		}
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
	
	@Override // Controlar que no haya emails repetidos en el sistema
	public boolean checkEmailAvailability(String email) {
		return userJpaRepository.findByEmail(email) == null;
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
