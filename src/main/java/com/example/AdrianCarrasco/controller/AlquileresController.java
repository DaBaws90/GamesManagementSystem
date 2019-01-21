package com.example.AdrianCarrasco.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import com.example.AdrianCarrasco.model.AlquilerModel;
import com.example.AdrianCarrasco.model.JuegoModel;
import com.example.AdrianCarrasco.service.AlquilerService;
import com.example.AdrianCarrasco.service.JuegoService;
import com.example.AdrianCarrasco.service.UserService;

import constant.Constants;

@Controller
@RequestMapping("/alquileres")
public class AlquileresController {
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@Autowired
	@Qualifier("alquilerServiceImpl")
	private AlquilerService alquilerService;
	
	@Autowired
	@Qualifier("juegoServiceImpl")
	private JuegoService juegoService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@GetMapping("/")
	public RedirectView toIndex() {
		logger.redirect("/alquileres/index", "/alquileres/");
		return new RedirectView("/alquileres/index");
	}
	
	@GetMapping("/index/pendientes")
	public ModelAndView pendientes() {
		List<AlquilerModel> alquileresModels = alquilerService.findAllByJuegoAlquiladoTrue();
		return new ModelAndView(Constants.ALQUILERES_PENDIENTES).addObject("alquileresModels", alquileresModels);
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		List<AlquilerModel> alquileresModels = alquilerService.listAllAlquileres();
		logger.info("GET", "index", "ALQUILERES_INDEX", "AlquileresController", "ALQUILERES", "RETRIEVED", alquileresModels);
		return new ModelAndView(Constants.ALQUILERES_INDEX).addObject("alquileresModels", alquileresModels);
	}
	
//	Id del juego en cuestión que se va a alquilar
	@GetMapping("/alquilarJuego/{id}")
	public ModelAndView addAlquilerForm(@PathVariable(name = "id") int id) {
		JuegoModel juegoModel = juegoService.findById(id);
		logger.info("GET", "addAlquilerForm", "ALQUILERES_ADD", "AlquileresController", "GAME", "RENTED", juegoModel);
		return new ModelAndView(Constants.ALQUILERES_ADD).addObject("alquilerModel", new AlquilerModel())
				.addObject("juegoModel", juegoModel);
//				.addObject("userModel", userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
	}
	
	@PostMapping("/insert")
	public ModelAndView addAlquiler(@Valid @ModelAttribute("alquilerModel") AlquilerModel alquilerModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		JuegoModel juegoModel = juegoService.findById(alquilerModel.getJuego().getId());
		logger.info("POST", "addAlquiler", "ALQUILERES_ADD", "AlquileresController", "GAME", "RENTED", juegoModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.ALQUILERES_ADD);
			mav.addObject("juegoModel", juegoService.findById(alquilerModel.getJuego().getId()));
//				.addObject("userModel", userService.findById(alquilerModel.getUser().getId()));
		}
		else {
			if(alquilerService.checkAvailability(juegoModel)) {
				alquilerModel.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
				if(alquilerService.addAlquiler(alquilerModel) != null) {
					logger.success("GAME", "RENTED", juegoModel);
					mav.setViewName("redirect:/juegos/index/alquileres");
					redirectAttributes.addFlashAttribute("rent", 1);
				}
				else {
					logger.unsuccessful("RENT", "GAME", juegoModel);
					mav.setViewName("redirect:/alquileres/alquilarJuego/" + juegoModel.getId());
					redirectAttributes.addFlashAttribute("rent", 0);
				}
			}
			else {
				logger.regularMessage("GAME IS CURRENTLY NOT AVAILABLE");
				mav.setViewName("redirect:/alquileres/alquilarJuego/" + juegoModel.getId());
				redirectAttributes.addFlashAttribute("available", 0);
			}
		}
		return mav;
	}
	
	@GetMapping("/devolucion/{id}")
	public ModelAndView devolverJuego(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(); /* "redirect:/juegos/index/alquileres" */
		JuegoModel juegoModel = juegoService.findById(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		logger.info("GET", "devolverJuego", "JUEGOS_VENTAS", "AlquileresController TO JuegosController", "GAME", "RETURNED", juegoModel);
		if(juegoService.devolverJuego(juegoModel) != null) {
			logger.success("GAME", "RETURNED", juegoModel);
			if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				mav.setViewName("redirect:/alquileres/index");
			}
			else {
				mav.setViewName("redirect:/users/profile");
			}
			redirectAttributes.addFlashAttribute("returned", 1);
		}
		else {
			logger.unsuccessful("RETURN", "GAME", juegoModel);
			if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				mav.setViewName("redirect:/alquileres/index/pendientes");
			}
			else {
				mav.setViewName("redirect:/users/profile");
			}
			redirectAttributes.addFlashAttribute("returned", 0);
		}
		return mav;
	}

}
