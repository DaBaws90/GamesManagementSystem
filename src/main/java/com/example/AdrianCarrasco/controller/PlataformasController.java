package com.example.AdrianCarrasco.controller;

import java.util.List;

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
import com.example.AdrianCarrasco.model.PlataformaModel;
import com.example.AdrianCarrasco.service.PlataformaService;

import constant.Constants;

@Controller
@RequestMapping("/plataformas")
public class PlataformasController {

	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@Autowired
	@Qualifier("plataformaServiceImpl")
	private PlataformaService plataformaService;

	@GetMapping("/")
	public RedirectView toIndex() {
		logger.redirect("/plataformas/index", "/plataformas/");
		return new RedirectView("/plataformas/index");
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		List<PlataformaModel> plataformasModels = plataformaService.listAllPlataformas();
		logger.info("GET", "index", "PLATAFORMAS_INDEX", "PlataformasController", "PLATAFORMAS", "RETRIEVED", "List<PlataformaModel> plataformasModels");
		return new ModelAndView(Constants.PLATAFORMAS_INDEX).addObject("plataformasModels", plataformasModels);
	}
	
	@GetMapping("/editPlataforma/{id}")
	public ModelAndView editPlataformaForm(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(Constants.PLATAFORMAS_EDIT);
		PlataformaModel plataformaModel = plataformaService.findById(id);
		logger.info("GET", "editPlataformaForm", "PLATAFORMAS_EDIT", "PlataformasController", "PLATAFORMA", "EDITED", plataformaModel);
		mav.addObject("plataformaModel", plataformaModel);
		return mav;
	}
	
	@PostMapping("/update")
	public ModelAndView updatePlataforma(@Valid @ModelAttribute("plataformaModel") PlataformaModel plataformaModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "updatePlataforma", "PLATAFORMAS_EDIT", "PlataformasController", "PLATAFORMA", "UPDATED", plataformaModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.PLATAFORMAS_EDIT);
		}
		else {
			if(plataformaService.findByNombre(plataformaModel.getNombre()) == null) {
				if(plataformaService.updatePlataforma(plataformaModel) != null) {
					logger.success("PLATAFORMA", "UPDATED", plataformaModel);
					mav.setViewName("redirect:/plataformas/index");
					redirectAttributes.addFlashAttribute("edited", 1);
				}
				else {
					logger.unsuccessful("UPDATE", "PLATAFORMA", plataformaModel);
					mav.setViewName("redirect:/plataformas/editPlataforma/" + plataformaModel.getId());
					redirectAttributes.addFlashAttribute("edited", 0);
				}
			}
			else {
				logger.regularMessage("PLATAFORMA ALREADY EXIST");
				mav.setViewName("redirect:/plataformas/editPlataforma/" + plataformaModel.getId());
				redirectAttributes.addFlashAttribute("exists", 1);
			}
		}
		return mav;
	}
	
	@GetMapping("/addPlataforma")
	public ModelAndView addPlataformaForm() {
		logger.info("GET", "addPlataformaForm", "PLATAFORMAS_ADD", "PlataformasController", "PLATAFORMA", "INSERTED", "NEW PLATAFORMA (Empty)");
		return new ModelAndView(Constants.PLATAFORMAS_ADD).addObject("plataformaModel", new PlataformaModel());
	}
	
	@PostMapping("/insert")
	public ModelAndView addPlataforma(@Valid @ModelAttribute("plataformaModel") PlataformaModel plataformaModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "addPlataforma", "PLATAFORMAS_ADD", "PlataformasController", "PLATAFORMA", "INSERTED", plataformaModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.PLATAFORMAS_ADD);
		}
		else {
			if(plataformaService.findByNombre(plataformaModel.getNombre()) == null) {
				if(plataformaService.addPlataforma(plataformaModel) != null) {
					logger.success("PLATAFORMA", "ADDED", plataformaModel);
					mav.setViewName("redirect:/plataformas/index");
					redirectAttributes.addFlashAttribute("insert", 1);
				}
				else {
					logger.unsuccessful("ADD", "PLATAFORMA", plataformaModel);
					mav.setViewName("redirect:/plataformas/addPlataforma/");
					redirectAttributes.addFlashAttribute("insert", 0);
				}
			}
			else {
				logger.regularMessage("PLATAFORMA ALREADY EXIST");
				mav.setViewName("redirect:/plataformas/addPlataforma");
				redirectAttributes.addFlashAttribute("exists", 1);
			}
		}
		return mav;
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView deletePlataforma(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("redirect:/plataformas/index");
		String plataformaModel = plataformaService.findById(id).toString();
		logger.info("POST", "deletePlataforma", "PLATAFORMAS_INDEX", "PlataformasController", "PLATAFORMA", "DELETED", plataformaModel);
		if(plataformaService.deletePlataforma(id)) {
			logger.success("PLATAFORMA", "DELETED", plataformaModel);
			redirectAttributes.addFlashAttribute("deleted", 1);
		}
		else {
			logger.unsuccessful("DELETE", "PLATAFORMA", plataformaModel);
			redirectAttributes.addFlashAttribute("deleted", 0);
		}
		return mav;
	}
}
