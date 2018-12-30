package com.example.AdrianCarrasco.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.AdrianCarrasco.model.UserModel;
import com.example.AdrianCarrasco.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@GetMapping("/")
	public RedirectView toIndex() {
		return new RedirectView("/users/index");
	}

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("usersIndex");
//		mav.addObject("user", contex)
		return mav;
	}
	
	@GetMapping("/register")
	public ModelAndView registerForm() {
		ModelAndView mav = new ModelAndView("usersAdd");
		mav.addObject("userModel", new UserModel());
		return mav;
	}
	
	@PostMapping("/signup")
	public ModelAndView registerUser(@Valid @ModelAttribute("userModel") UserModel userModel, BindingResult bindingResult, 
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		if(bindingResult.hasErrors()) {
			mav.setViewName("usersAdd");
			return mav;
		}
		else {
			if(!userService.checkUsernameAvailability(userModel.getUsername())) {
				mav.setViewName("redirect:/users/register");
				redirectAttributes.addFlashAttribute("username", 0);
				return mav;
			}
			if(!userService.checkEmailAvailability(userModel.getEmail())) {
				mav.setViewName("redirect:/users/register");
				redirectAttributes.addFlashAttribute("email", 0);
				return mav;
			}
			userService.addUser(userModel);
			mav.setViewName("redirect:/login");
			redirectAttributes.addFlashAttribute("userAdded", 1);
			return mav;
		}
	}
}
