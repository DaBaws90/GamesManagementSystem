package com.example.AdrianCarrasco.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.converter.ParticipacionConverter;
import com.example.AdrianCarrasco.entity.Participacion;
import com.example.AdrianCarrasco.model.ParticipacionModel;
import com.example.AdrianCarrasco.model.UserModel;
import com.example.AdrianCarrasco.repository.ParticipacionJpaRepository;
import com.example.AdrianCarrasco.service.ParticipacionService;

@Service("participacionServiceImpl")
public class ParticipacionServiceImpl implements ParticipacionService{
	
	@Autowired
	@Qualifier("participacionJpaRepository")
	private ParticipacionJpaRepository repository;
	
	@Autowired
	@Qualifier("participacionConverter")
	private ParticipacionConverter converter;
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;

	@Override
	public List<ParticipacionModel> listAllParticipaciones() {
		return new ArrayList<ParticipacionModel>(repository.findAll().stream().map(p -> converter.transform(p)).collect(Collectors.toList()));
	}

	@Override
	public Participacion addParticipacion(ParticipacionModel participacionModel, UserModel userModel) {
//		participacionModel.setPosicion(null);
//		participacionModel.setUserModel(userModel);
		logger.regularMessage(participacionModel.toString());
		return repository.save(converter.transform(participacionModel));
	}

	@Override
	public Participacion updateParticipacion(ParticipacionModel participacionModel) {
		return repository.save(converter.transform(participacionModel));
	}

	@Override
	public boolean deleteParticipacion(int id) {
		if(repository.findById(id) != null) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public ParticipacionModel findById(int id) {
		return converter.transform(repository.findById(id));
	}

	@Override
	public List<ParticipacionModel> findAllByUser(UserModel userModel) {
		return new ArrayList<ParticipacionModel>(repository.findAllByUser(userModel).stream().map(p -> converter.transform(p)).collect(Collectors.toList()));
	}

	@Override
	public boolean checkAvailability(ParticipacionModel participacionModel) {
		for (Participacion part : repository.findAll()) {
			if(part.getCompeticion().getId() == participacionModel.getCompeticion().getId() && 
					part.getUser().getId() == participacionModel.getUser().getId()) {
				return false;
			}
		}
		return true;
	}
	
	
}
