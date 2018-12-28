package com.example.AdrianCarrasco.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.AdrianCarrasco.service.NoticiaService;

import constant.Constants;

@Controller
@RequestMapping("/home")
public class MainController {
	
	private static final Log LOG = LogFactory.getLog(MainController.class);
	
	@Autowired
	@Qualifier("noticiaServiceImpl")
	private NoticiaService noticiaService;

	@GetMapping("/")
	public RedirectView redirect1() {
		LOG.info("REDIRECTING TO /home/index FROM /home/");
		return new RedirectView("/home/index");
	}
	
	@GetMapping("/index")
	public ModelAndView principal() {
		ModelAndView mav = new ModelAndView(Constants.HOME);
		mav.addObject("noticiasModels", noticiaService.listAllNoticias());
		return mav;
	}
}