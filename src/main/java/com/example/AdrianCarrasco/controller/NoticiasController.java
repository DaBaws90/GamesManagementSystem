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
import com.example.AdrianCarrasco.model.NoticiaModel;
import com.example.AdrianCarrasco.service.NoticiaService;

import constant.Constants;

@Controller
@RequestMapping("/noticias")
public class NoticiasController {
	
	@Autowired
	@Qualifier("noticiaServiceImpl")
	private NoticiaService noticiaService;
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@GetMapping("/")
	public RedirectView toIndex() {
		return new RedirectView("/noticias/index");
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(Constants.NOTICIAS_INDEX);
		mav.addObject("noticiasModels", noticiaService.listAllNoticias());
		return mav;
	}
	
	@GetMapping("/details/{id}")
	public ModelAndView details(@PathVariable(name="id") int id) {
		ModelAndView mav = new ModelAndView(Constants.NOTICIAS_DETAILS);
		mav.addObject("noticiaModel", noticiaService.findById(id));
		return mav;
	}
	
	@GetMapping("/editNoticia/{id}")
	public ModelAndView editNoticiaForm(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView(Constants.NOTICIAS_EDIT);
		mav.addObject("noticiaModel", noticiaService.findById(id));
		return mav;
	}
	
	@PostMapping("/update")
	public ModelAndView updateNoticia(@Valid @ModelAttribute("noticiaModel") NoticiaModel noticiaModel, BindingResult bindingResult, 
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "updateNoticia", "NOTICIAS_EDIT", "NoticiasController", "NOTICIA", "UPDATED", noticiaModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.NOTICIAS_EDIT);
		}
		else {
			if(noticiaService.addNoticia(noticiaModel) != null) {
				logger.regularMessage("NOTICIA EDITED SUCCESFULLY: " + noticiaModel);
				mav.setViewName("redirect:/noticias/index");
				redirectAttributes.addFlashAttribute("edited", 1);
			}
			else {
				logger.regularMessage("UNABLE TO EDIT NOTICIA: " + noticiaModel);
				mav.setViewName("redirect:/noticias/editNoticia/" + noticiaModel.getId());
				redirectAttributes.addFlashAttribute("edited", 0);
			}
		}
		return mav;
	}
	
	@GetMapping("/addNoticia")
	public ModelAndView addNoticiaForm() {
		logger.info("GET", "addNoticiaForm", "NOTICIAS_ADD", "NoticiasController", "NOTICIA", "INSERTED", "NEW NOTICIA (Empty)");
		return new ModelAndView(Constants.NOTICIAS_ADD).addObject("noticiaModel", new NoticiaModel());
	}
	
	@PostMapping("/insert")
	public ModelAndView addNoticia(@Valid @ModelAttribute("noticiaModel") NoticiaModel noticiaModel, BindingResult bindingResult, 
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "addNoticia", "NOTICIAS_ADD", "NoticiasController", "NOTICIA", "INSERTED", noticiaModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.NOTICIAS_ADD);
		}
		else {
			if(noticiaService.addNoticia(noticiaModel) != null) {
				logger.success("NOTICIA", "INSERTED", noticiaModel);
				mav.setViewName("redirect:/noticias/index");
				redirectAttributes.addFlashAttribute("insert", 1);
			}
			else {
				logger.unsuccessful("INSERT", "NOTICIA", noticiaModel);
				mav.setViewName("redirect:/noticias/addNoticia");
				redirectAttributes.addFlashAttribute("insert", 0);
			}
		}
		return mav;
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView deleteNoticia(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(Constants.NOTICIAS_INDEX);
		NoticiaModel temp = noticiaService.findById(id);
		if(noticiaService.deleteNoticia(id)) {
			logger.success("NOTICIA", "DELETED", temp);
			redirectAttributes.addFlashAttribute("deleted", 1);
		}
		else {
			logger.unsuccessful("DELETE", "NOTICIA", temp);
			redirectAttributes.addFlashAttribute("deleted", 0);
		}
		return mav;
	}

}
