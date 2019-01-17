package com.example.AdrianCarrasco.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.AdrianCarrasco.component.MethodLogger;
import com.example.AdrianCarrasco.model.CategoriaModel;
import com.example.AdrianCarrasco.model.JuegoModel;
import com.example.AdrianCarrasco.model.PlataformaModel;
import com.example.AdrianCarrasco.service.CategoriaService;
import com.example.AdrianCarrasco.service.FileService;
import com.example.AdrianCarrasco.service.JuegoService;
import com.example.AdrianCarrasco.service.PlataformaService;

import constant.Constants;

@Controller
@RequestMapping("/juegos")
public class JuegosController {

	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@Autowired
	@Qualifier("juegoServiceImpl")
	private JuegoService juegoService;
	
	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;
	
	@Autowired
	@Qualifier("plataformaServiceImpl")
	private PlataformaService plataformaService;
	
	@Autowired
	@Qualifier("fileServiceImpl")
	private FileService fileService;
	
	@GetMapping("/")
	public RedirectView toIndex() {
		logger.redirect("/juegos/index", "/juegos/");
		return new RedirectView("/juegos/index");
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		List<JuegoModel> juegosModels = juegoService.listAllJuegos();
		logger.info("GET", "index", "JUEGOS_INDEX", "JuegosController", "JUEGOS", "RETRIEVED", juegosModels);
		return new ModelAndView(Constants.JUEGOS_INDEX).addObject("juegosModels", juegosModels);
	}
	
	@GetMapping("/index/compras")
	public ModelAndView indexCompras() {
		List<JuegoModel> juegosCompra = juegoService.findAllByTipo("Venta");
		logger.info("GET", "indexCompras", "JUEGOS_VENTAS", "JuegosController", "JUEGOS", "RETRIEVED", juegosCompra);
		return new ModelAndView(Constants.JUEGOS_VENTAS).addObject("juegosCompra", juegosCompra);
	}
	
	@GetMapping("/index/alquileres")
	public ModelAndView indexAlquileres() {
		List<JuegoModel> juegosAlquiler = juegoService.findAllByTipo("Alquiler");
		logger.info("GET", "indexAlquileres", "JUEGOS_ALQUILERES", "JuegosController", "JUEGOS", "RETRIEVED", juegosAlquiler);
		return new ModelAndView(Constants.JUEGOS_ALQUILERES).addObject("juegosAlquiler", juegosAlquiler);
	}
	
	@GetMapping("/addJuego")
	public ModelAndView addJuegoForm() {
		List<CategoriaModel> categoriasModels = categoriaService.listAllCategories();
		List<PlataformaModel> plataformasModels = plataformaService.listAllPlataformas();
		logger.info("GET", "addJuegoForm", "JUEGOS_ADD", "JuegosController", "JUEGO", "INSERTED", "NEW JUEGO (Empty)");
		return new ModelAndView(Constants.JUEGOS_ADD)
				.addObject("juegoModel", new JuegoModel())
				.addObject("categoriasModels", categoriasModels)
				.addObject("plataformasModels", plataformasModels)
				.addObject("categoriasList", new ArrayList<String>());
	}
	
	@PostMapping("/insert")
	public ModelAndView addJuego(@Valid @ModelAttribute("juegoModel") JuegoModel juegoModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam List<Integer> categoriasList, @RequestParam List<Integer> plataformasList,
			@ModelAttribute("file") MultipartFile file) {
		ModelAndView mav = new ModelAndView();
		List<CategoriaModel> categoriasModels = categoriaService.listAllCategories();
		List<PlataformaModel> plataformasModels = plataformaService.listAllPlataformas();
		logger.info("POST", "addJuego", "JUEGOS_ADD", "JuegosController", "JUEGO", "INSERTED", juegoModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.JUEGOS_ADD);
			mav.addObject("categoriasModels", categoriasModels).addObject("plataformasModels", plataformasModels);
		}
		else {
			if(!juegoService.checkCoincidences(juegoModel)) {
				try {
					juegoModel.setCaratula(fileService.saveFile(file));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(juegoService.addJuego(juegoModel, categoriasList, plataformasList) != null) {
					logger.success("JUEGO", "INSERTED", juegoModel);
					mav.setViewName("redirect:/juegos/index");
					redirectAttributes.addFlashAttribute("insert", 1);
				}
				else {
					
				}
			}
			else {
				logger.regularMessage("GAME ALREADY EXIST FOR " + juegoModel.getTipo().toUpperCase());
				mav.setViewName("redirect:/juegos/addJuego");
				redirectAttributes.addFlashAttribute("exists", 1);
				redirectAttributes.addFlashAttribute("type", juegoModel.getTipo());
			}
		}
		return mav;
	}
}
