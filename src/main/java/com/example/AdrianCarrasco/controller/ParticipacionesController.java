package com.example.AdrianCarrasco.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.model.ParticipacionModel;
import com.example.AdrianCarrasco.model.UserModel;
import com.example.AdrianCarrasco.service.CompeticionService;
import com.example.AdrianCarrasco.service.ParticipacionService;
import com.example.AdrianCarrasco.service.UserService;

import constant.Constants;

@Controller
@RequestMapping("/participaciones")
public class ParticipacionesController {

	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@Autowired
	@Qualifier("participacionServiceImpl")
	private ParticipacionService participacionService;
	
	@Autowired
	@Qualifier("competicionServiceImpl")
	private CompeticionService competicionService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@GetMapping("/")
	public RedirectView toIndex() {
		logger.redirect("/participaciones/index", "/participaciones/");
		return new RedirectView("/participaciones/index");
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		logger.info("GET", "index", "PARTICIPACIONES_INDEX", "ParticipacionesController", "PARTICIPACIONES", "RETRIEVED", "listAllParticipaciones()");
		return new ModelAndView(Constants.PARTICIPACIONES_INDEX).addObject("participacionesModels", participacionService.listAllParticipaciones());
	}
	
//	No pueden ser editados ni el usuario, ni la competicion. Solamente el admin podrá registrar el resultado. Si el usuario quiere cambiar de competicion,
//	deberá borrar la solicitud y crear una nueva con la competicion pertinente
	@GetMapping("/editParticipacion/{id}")
	public ModelAndView editParticipacionForm(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView(Constants.PARTICIPACIONES_EDIT);
		ParticipacionModel participacionModel = participacionService.findById(id);
		logger.info("GET", "editParticipacionForm", "PARTICIPACIONES_EDIT", "ParticipacionesController", "PARTICIPACION", "EDITED", participacionModel);
		mav.addObject("participacionModel", participacionModel)
		.addObject("competicionesModels", competicionService.listAllCompeticiones());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getAuthorities().contains("ROLE_ADMIN")) {
			mav.addObject("userModel", participacionModel.getUser());
		}
		else {
			mav.addObject("userModel", userService.findByUsername(auth.getName()));
		}
		return mav;
	}
	
	@PostMapping("/update")
	public ModelAndView editParticipacion(@Valid @ModelAttribute("participacionModel") ParticipacionModel participacionModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "editParticipacion", "PARTICIPACIONES_EDIT", "ParticipacionesController", "PARTICIPACION", "UPDATED", participacionModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.PARTICIPACIONES_EDIT);
		}
		else {
			if(participacionService.checkAvailability(participacionModel)) {
				if(participacionService.updateParticipacion(participacionModel) != null) {
					logger.success("PARTICIPACION", "UPDATED", participacionModel);
					mav.setViewName("redirect:/participaciones/index");
					redirectAttributes.addFlashAttribute("edited", 1);
				}
				else {
					logger.unsuccessful("UPDATE", "PARTICIPACION", participacionModel);
					mav.setViewName("redirect:/participaciones/editParticipacion/" + participacionModel.getId());
					redirectAttributes.addFlashAttribute("edited", 0);
				}
			}
			else {
				logger.regularMessage("THE USER IS ALREADY REGISTERED IN THE TOURNAMENT");
				mav.setViewName("redirect:/participaciones/editParticipacion/" + participacionModel.getId());
				redirectAttributes.addFlashAttribute("exists", 1);
			}
		}
		return mav;
	}
	
	@GetMapping("/addParticipacion")
	public ModelAndView addParticipacionForm() {
		logger.info("GET", "addParticipacionForm", "PARTICIPACIONES_ADD", "ParticipacionesController", "PARTICIPACION", "INSERTED", "NEW PARTICIPACION (Empty)");
		return new ModelAndView(Constants.PARTICIPACIONES_ADD)
				.addObject("participacionModel", new ParticipacionModel())
				.addObject("competicionesModels", competicionService.listAllCompeticiones())
				.addObject("userModel", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
	}
	
	@PostMapping("/insert")
	public ModelAndView addParticipacion(@Valid @ModelAttribute("participacionModel") ParticipacionModel participacionModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @ModelAttribute("userModel") UserModel userModel) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "addParticipacion", "PARTICIPACIONES_ADD", "ParticipacionesController", "PARTICIPACION", "INSERTED", participacionModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.PARTICIPACIONES_ADD);
		}
		else {
			if(participacionService.checkAvailability(participacionModel)) {
				if(participacionService.addParticipacion(participacionModel, userModel) != null) {
					logger.success("PARTICIPACION", "INSERTED", participacionModel);
					mav.setViewName("redirect:/participaciones/index");
					redirectAttributes.addFlashAttribute("insert", 1);
				}
				else {
					logger.unsuccessful("INSERT", "PARTICIPACION", participacionModel);
					mav.setViewName("redirect/participaciones/addParticipacion");
					redirectAttributes.addFlashAttribute("insert", 0);
				}
			}
			else {
				logger.regularMessage("THE USER IS ALREADY REGISTERED IN THE TOURNAMENT");
				mav.setViewName("redirect:/participaciones/addParticipacion");
				redirectAttributes.addFlashAttribute("exists", 1);
			}
		}
		return mav;
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView deleteParticipacion(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(Constants.PARTICIPACIONES_INDEX);
		String participacionModel = participacionService.findById(id).toString();
		logger.info("GET", "deleteParticipacion", "PARTICIPACIONES_INDEX", "ParticipacionesController", "PARTICIPACION", "DELETED", participacionModel);
		if(participacionService.deleteParticipacion(id)) {
			logger.success("PARTICIPACION", "DELETED", participacionModel);
			redirectAttributes.addFlashAttribute("deleted", 1);
		}
		else {
			logger.unsuccessful("DELETE", "PARTICIPACION", participacionModel);
			redirectAttributes.addFlashAttribute("deleted", 0);
		}
		return mav;
	}
}
