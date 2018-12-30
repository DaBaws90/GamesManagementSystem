package com.example.AdrianCarrasco.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AdrianCarrasco.entity.User;
import com.example.AdrianCarrasco.entity.UserRole;

@Repository("userRoleJpaRepository")
public interface UserRoleJpaRepository extends JpaRepository<UserRole, Serializable>{
	public abstract User findByUser(User user);
}
