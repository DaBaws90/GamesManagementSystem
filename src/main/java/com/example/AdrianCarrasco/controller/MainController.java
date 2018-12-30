package com.example.AdrianCarrasco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.service.NoticiaService;

import constant.Constants;

@Controller
@RequestMapping("/home")
public class MainController {
	
	@Autowired
	@Qualifier("noticiaServiceImpl")
	private NoticiaService noticiaService;
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;

	@GetMapping("/")
	public RedirectView redirect1() {
		logger.redirect("/home/index", "/home/");
		return new RedirectView("/home/index");
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(Constants.HOME);
		mav.addObject("noticiasModels", noticiaService.listAllNoticias());
		logger.info("GET", "index", "MainController", "NOTICIAS", "RETRIEVED", noticiaService.listAllNoticias().toString());
//		mav.addObject("username", SecurityContextHolder.getContext().getAuthentication().getName());
		return mav;
	}
	
	
}