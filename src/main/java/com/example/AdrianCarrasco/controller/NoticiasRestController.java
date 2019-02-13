package com.example.AdrianCarrasco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AdrianCarrasco.service.NoticiaService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@PreAuthorize("permitAll()")
@RestController
@RequestMapping("/api")
public class NoticiasRestController {

	@Autowired
	@Qualifier("noticiaServiceImpl")
	private NoticiaService noticiaService;
	
	@GetMapping("/index")
	public String listNoticias(){
		Gson gsonBuilder = new GsonBuilder().create();
		// Convert Java Array into JSON
		String jsonFromJavaArrayList = gsonBuilder.toJson(noticiaService.listAllNoticias());

		return jsonFromJavaArrayList;
	}
	
	@GetMapping("/noticia/{id}")
	public String noticia(@PathVariable(name = "id") int id) {
		Gson gsonBuilder = new GsonBuilder().create();
		// Convert Java Object into JSON
		String jsonFromObject = gsonBuilder.toJson(noticiaService.findById(id));

		return jsonFromObject;
	}
	
	
//	@PostMapping("/noticia/update")
//	public String updateNoticia(@RequestBody NoticiaModel noticiaModel) {
//		Gson gsonBuilder = new GsonBuilder().create();
//		
//		return gsonBuilder.toJson(noticiaService.updateNoticia(noticiaModel));
//	}
	
	@GetMapping("/delete/{id}")
	public boolean deleteNoticia(@PathVariable(name = "id") int id) {
//		Devuelve true o false indicando si se llevó a cabo la acción o no
		return noticiaService.deleteNoticia(id);
	}
}
