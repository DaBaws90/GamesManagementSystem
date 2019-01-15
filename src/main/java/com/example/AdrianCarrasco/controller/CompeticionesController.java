package com.example.AdrianCarrasco.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.example.AdrianCarrasco.model.CompeticionModel;
import com.example.AdrianCarrasco.service.CompeticionService;
import com.example.AdrianCarrasco.service.JuegoService;

import constant.Constants;

@Controller
@RequestMapping("/competiciones")
public class CompeticionesController {

	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@Autowired
	@Qualifier("competicionServiceImpl")
	private CompeticionService competicionService;
	
	@Autowired
	@Qualifier("juegoServiceImpl")
	private JuegoService juegoService;
	
	@GetMapping("/")
	public RedirectView toIndex() {
		logger.redirect("/competiciones/index", "/competiciones/");
		return new RedirectView("/competiciones/index");
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		logger.info("GET", "index", "COMPETICIONES_INDEX", "CompeticionesController", "COMPETICIONES", "RETRIEVED", competicionService.listAllCompeticiones());
		return new ModelAndView(Constants.COMPETICIONES_INDEX).addObject("competicionesModels", competicionService.listAllCompeticiones());
	}
	
	@GetMapping("/editCompeticion/{id}")
	public ModelAndView editCompeticionForm(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView(Constants.COMPETICIONES_EDIT);
		CompeticionModel competicionModel = competicionService.findById(id);
		logger.info("GET", "editCompeticionForm", "COMPETICIONES_EDIT", "CompeticionesController", "COMPETICION", "EDITED", competicionModel);
		mav.addObject("competicionModel", competicionModel);
		mav.addObject("juegosModels", juegoService.listAllJuegos());
		return mav;
	}
	
	@PostMapping("/update")
	public ModelAndView updateCompeticion(@Valid @ModelAttribute("competicionModel") CompeticionModel competicionModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "updateCompeticion", "COMPETICIONES_EDIT", "CompeticionesController", "COMPETICION", "UPDATED", competicionModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.COMPETICIONES_EDIT);
		}
		else {
			if(competicionService.checkAvailability(competicionModel)) {
				if(competicionService.updateCompeticion(competicionModel) != null) {
					logger.success("COMPETICION", "UPDATED", competicionModel);
					mav.setViewName("redirect:/competiciones/index");
					redirectAttributes.addFlashAttribute("edited", 1);
				}
				else {
					logger.unsuccessful("UPDATE", "COMPETICION", competicionModel);
					mav.setViewName("redirect:/competiciones/editCompeticion/" + competicionModel.getId());
					redirectAttributes.addFlashAttribute("edited", 0);
				}
			}
			else {
				logger.regularMessage("THERE IS ALREADY THE SAME COMPETITION REGISTERED");
				mav.setViewName("redirect:/competiciones/editCompeticion/" + competicionModel.getId());
				redirectAttributes.addFlashAttribute("exists", 1);
			}
		}
		return mav;
	}
	
	@GetMapping("/addCompeticion")
	public ModelAndView addCompeticionForm() {
		logger.info("GET", "addCompeticionForm", "COMPETICIONES_ADD", "CompeticionesController", "COMPETICION", "INSERTED", "NEW COMPETICION (Empty)");
		return new ModelAndView(Constants.COMPETICIONES_ADD)
				.addObject("competicionModel", new CompeticionModel()).addObject("juegosModels", juegoService.listAllJuegos());
	}
	
	@PostMapping("/insert")
	public ModelAndView addCompeticion(@Valid @ModelAttribute("competicionModel") CompeticionModel competicionModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
//		List<JuegoModel> juegosModels = juegoService.listAllJuegos();
		logger.info("POST", "addCompeticion", "COMPETICIONES_ADD", "CompeticionesController", "COMPETICION", "INSERTED", competicionModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.COMPETICIONES_ADD);
			mav.addObject("juegosModels", juegoService.listAllJuegos());
		}
		else {
			if(competicionService.checkAvailability(competicionModel)) {
				if(competicionService.addCompeticion(competicionModel) != null) {
					logger.success("COMPETICION", "INSERTED", competicionModel);
					mav.setViewName("redirect:/competiciones/index");
					redirectAttributes.addFlashAttribute("insert", 1);
				}
				else {
					logger.unsuccessful("INSERT", "COMPETICION", competicionModel);
					mav.setViewName("redirect:/competiciones/addCompeticion");
//					mav.addObject("juegosModels", juegosModels);
					redirectAttributes.addFlashAttribute("insert", 0);
				}
			}
			else {
				logger.regularMessage("THERE IS ALREADY THE SAME COMPETITION REGISTERED");
				mav.setViewName("redirect:/competiciones/addCompeticion");
//				mav.addObject("juegosModels", juegosModels);
				redirectAttributes.addFlashAttribute("exists", 1);
			}
		}
		return mav;
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView deleteCompeticion(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("redirect:/competiciones/index");
		String competicionModel = competicionService.findById(id).toString();
		logger.info("GET", "deleteCompeticion", "COMPETICIONES_INDEX", "CompeticionesController", "COMPETICION", "DELETED", competicionModel);
		if(competicionService.deleteCompeticion(id)) {
			logger.success("COMPETICION", "DELETED", competicionModel);
			redirectAttributes.addFlashAttribute("deleted", 1);
		}
		else {
			logger.unsuccessful("DELETE", "COMPETICION", competicionModel);
			redirectAttributes.addFlashAttribute("delete", 0);
		}
		return mav;
	}
	
}
