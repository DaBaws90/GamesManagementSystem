package com.example.AdrianCarrasco.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.model.JuegoModel;
import com.example.AdrianCarrasco.model.VentaModel;
import com.example.AdrianCarrasco.service.JuegoService;
import com.example.AdrianCarrasco.service.UserService;
import com.example.AdrianCarrasco.service.VentaService;

import constant.Constants;

@Controller
@RequestMapping("/ventas")
public class VentasController {

	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@Autowired
	@Qualifier("ventaServiceImpl")
	private VentaService ventaService;
	
	@Autowired
	@Qualifier("juegoServiceImpl")
	private JuegoService juegoService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@GetMapping("/")
	public RedirectView toIndex() {
		logger.redirect("/ventas/index", "ventas/");
		return new RedirectView("/ventas/index");
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		List<VentaModel> ventasModels = ventaService.listAllVentas();
		logger.info("GET", "index", "VENTAS_INDEX", "VentasController", "VENTAS", "RETRIEVED", ventasModels);
		return new ModelAndView(Constants.VENTAS_INDEX).addObject("ventasModels", ventasModels);
	}
	
//	Recibe id del juego que se va a comprar desde la vista detalles del juego, con boton de comprar o, mejor dicho, enlace con apariencia de botón
	@GetMapping("/venderJuego/{id}")
	public ModelAndView addVentaForm(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView(Constants.VENTAS_ADD);
		JuegoModel juegoModel = juegoService.findById(id);
		logger.info("GET", "addVentaForm", "VENTAS_ADD", "VentasController", "GAME", "SOLD", juegoModel);
		mav.addObject("ventaModel", new VentaModel());
		mav.addObject("juegoModel", juegoModel);
		return mav;
	}
	
	@PostMapping("/insert")
	public ModelAndView addVenta(@Valid @ModelAttribute("ventaModel") VentaModel ventaModel, BindingResult bindingResult, 
			RedirectAttributes redirectAttributes, @RequestParam int amount) {
		ModelAndView mav = new ModelAndView();
		JuegoModel juegoModel = juegoService.findById(ventaModel.getJuego().getId());
		logger.info("POST", "addVenta", "VENTAS_ADD", "VentasController", "VENTA", "INSERTED", ventaModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.VENTAS_ADD);
			mav.addObject("juegoModel", juegoModel);
		}
		else {
			if((juegoService.findById(ventaModel.getJuego().getId()).getStock() - amount) >= 0) {
				ventaModel.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
				if(ventaService.addVenta(ventaModel, amount) != null) {
					logger.success("VENTA", "INSERTED", ventaModel);
					mav.setViewName("redirect:/juegos/index/compras");
					redirectAttributes.addFlashAttribute("sold", 1);
				}
				else {
					logger.unsuccessful("INSERT", "VENTA", ventaModel);
					mav.setViewName("redirect:/ventas/venderJuego/" + ventaModel.getJuego().getId());
					redirectAttributes.addFlashAttribute("sold", 0);
				}
			}
			else {
				logger.regularMessage("NOT ENOUGH STOCK AVAILABLE");
				mav.setViewName("redirect:/ventas/venderJuego/" + ventaModel.getJuego().getId());
				redirectAttributes.addFlashAttribute("stock", 0);
			}
		}
		return mav;
	}
	
}
