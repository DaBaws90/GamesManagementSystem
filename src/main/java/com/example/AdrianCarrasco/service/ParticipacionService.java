package com.example.AdrianCarrasco.service;

import java.util.List;

import com.example.AdrianCarrasco.entity.Participacion;
import com.example.AdrianCarrasco.model.ParticipacionModel;
import com.example.AdrianCarrasco.model.UserModel;

public interface ParticipacionService {
	public abstract List<ParticipacionModel> listAllParticipaciones();
	public abstract Participacion addParticipacion(ParticipacionModel participacionModel, UserModel userModel);
	public abstract Participacion updateParticipacion(ParticipacionModel participacionModel);
	public abstract boolean deleteParticipacion(int id);
	
	public abstract ParticipacionModel findById(int id);
	public abstract List<ParticipacionModel> findAllByUser(UserModel userModel);
	
	public abstract boolean checkAvailability(ParticipacionModel participacionModel);
}
