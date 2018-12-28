package com.example.AdrianCarrasco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.AdrianCarrasco.service.NoticiaService;

import constant.Constants;

@Controller
@RequestMapping("/noticias")
public class NoticiasController {
	
	@Autowired
	@Qualifier("noticiaServiceImpl")
	private NoticiaService noticiaService;
	
	@GetMapping("/")
	public RedirectView toIndex() {
		return new RedirectView("/noticias/index");
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(Constants.noticiasIndex);
		mav.addObject("noticiasModels", noticiaService.listAllNoticias());
		return mav;
	}
	
	@GetMapping("/details/{id}")
	public ModelAndView details(@PathVariable(name="id") int id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("noticiaModel", noticiaService.findById(id));
		return mav;
	}

}
