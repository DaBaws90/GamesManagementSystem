package com.example.AdrianCarrasco.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AdrianCarrasco.entity.Participacion;
import com.example.AdrianCarrasco.model.UserModel;

@Repository("participacionJpaRepository")
public interface ParticipacionJpaRepository extends JpaRepository<Participacion, Serializable>{
	public abstract Participacion findById(int id);
	public abstract List<Participacion> findAllByUser(UserModel userModel);
}
