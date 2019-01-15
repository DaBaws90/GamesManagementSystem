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
import com.example.AdrianCarrasco.model.CategoriaModel;
import com.example.AdrianCarrasco.service.CategoriaService;

import constant.Constants;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;
	
	@GetMapping("/")
	public RedirectView toIndex() {
		logger.redirect("/categorias/index", "/categorias/");
		return new RedirectView("/categorias/index");
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		List<CategoriaModel> categoriasModels = categoriaService.listAllCategories();
		logger.info("GET", "index", "CATEGORIAS_INDEX", "CategoriasController", "CATEGORIAS", "RETRIEVED", "List<CategoriaModel> categoriasModels");
		return new ModelAndView(Constants.CATEGORIAS_INDEX).addObject("categoriasModels", categoriasModels);
	}
	
	@GetMapping("/editCategory/{id}")
	public ModelAndView editCategoryForm(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView(Constants.CATEGORIAS_EDIT);
		CategoriaModel categoriaModel = categoriaService.findById(id);
		logger.info("GET", "editCategoryForm", "CATEGORIAS_EDIT", "CategoriasController", "CATEGORIA", "EDITED", categoriaModel);
		mav.addObject("categoriaModel", categoriaModel);
		return mav;
	}
	
	@PostMapping("/update")
	public ModelAndView updateCategory(@Valid @ModelAttribute("categoriaModel") CategoriaModel categoriaModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "updateCategory", "CATEGORIAS_EDIT", "CategoriasController", "CATEGORIA", "UPDATED", categoriaModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.CATEGORIAS_EDIT);
		}
		else {
			if(categoriaService.findByNombre(categoriaModel.getNombre()) == null) {
				if(categoriaService.updateCategory(categoriaModel) != null) {
					logger.success("CATEGORIA", "UPDATED", categoriaModel);
					mav.setViewName("redirect:/categorias/index");
					redirectAttributes.addFlashAttribute("edited", 1);
				}
				else {
					logger.unsuccessful("UPDATE", "CATEGORIA", categoriaModel);
					mav.setViewName("redirect:/categorias/editCategory/" + categoriaModel.getId());
					redirectAttributes.addFlashAttribute("edited", 0);
				}
			}
			else {
				logger.regularMessage("CATEGORIA ALREADY EXIST");
				mav.setViewName("redirect:/categorias/editCategory/" + categoriaModel.getId());
				redirectAttributes.addFlashAttribute("exists", 1);
			}
		}
		return mav;
	}
	
	@GetMapping("/addCategory")
	public ModelAndView addCategoryForm() {
		logger.info("GET", "addCategoryForm", "CATEGORIAS_ADD", "CategoriasController", "CATEGORIA", "INSERTED", "NEW CATEGORIA (Empty)");
		return new ModelAndView(Constants.CATEGORIAS_ADD).addObject("categoriaModel", new CategoriaModel());
	}
	
	@PostMapping("/insert")
	public ModelAndView addCategory(@Valid @ModelAttribute("categoriaModel") CategoriaModel categoriaModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "addCategory", "CATEGORIAS_ADD", "CategoriasController", "CATEGORIA", "INSERTED", categoriaModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.CATEGORIAS_ADD);
		}
		else {
			if(categoriaService.findByNombre(categoriaModel.getNombre()) == null) {
				if(categoriaService.addCategory(categoriaModel) != null) {
					logger.success("CATEGORIA", "INSERTED", categoriaModel);
					mav.setViewName("redirect:/categorias/index");
					redirectAttributes.addFlashAttribute("insert", 1);
				}
				else {
					logger.unsuccessful("INSERT", "CATEGORIA", categoriaModel);
					mav.setViewName("redirect:/categorias/addCategory");
					redirectAttributes.addFlashAttribute("insert", 0);
				}
			}
			else {
				logger.regularMessage("CATEGORIA ALREADY EXIST");
				mav.setViewName("redirect:/categorias/addCategory");
				redirectAttributes.addFlashAttribute("exists", 1);
			}
		}
		return mav;
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView deleteCategory(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("redirect:/categorias/index");
		String categoriaModel = categoriaService.findById(id).toString();
		logger.info("POST", "deleteCategory", "CATEGORIAS_INDEX", "CategoriasController", "CATEGORIA", "DELETED", categoriaModel);
		if(categoriaService.deleteCategory(id)) {
			logger.success("CATEGORIA", "DELETED", categoriaModel);
			redirectAttributes.addFlashAttribute("deleted", 1);
		}
		else {
			logger.unsuccessful("DELETE", "CATEGORIA", categoriaModel);
			redirectAttributes.addFlashAttribute("deleted", 0);
		}
		return mav;
	}
	
}
