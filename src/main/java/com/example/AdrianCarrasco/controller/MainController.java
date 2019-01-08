package com.example.AdrianCarrasco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.model.NoticiaModel;
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
		List<NoticiaModel> noticiasModels = noticiaService.listAllNoticias();
		mav.addObject("noticiasModels", noticiasModels);
//		logger.info("GET", "index", "HOME view", "MainController", "NOTICIAS", "RETRIEVED", "List<NoticiaModel> noticiasModel");
		logger.info("GET", "index", "HOME view", "MainController", "NOTICIAS", "RETRIEVED", noticiasModels.toString());
		return mav;
	}
	
	
}