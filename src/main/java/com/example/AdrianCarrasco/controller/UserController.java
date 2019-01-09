package com.example.AdrianCarrasco.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
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
import com.example.AdrianCarrasco.model.UserModel;
import com.example.AdrianCarrasco.service.UserService;

import constant.Constants;

@Controller
@RequestMapping("/users")
public class UserController {
	
//	private static final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("methodLogger")
	private MethodLogger logger;
	
	@GetMapping("/")
	public RedirectView toIndex() {
		logger.redirect("users/index", "users/");
		return new RedirectView("/users/index");
	}

	@GetMapping("/index")
	public ModelAndView index() {
//		Asigno a una lista la llamada al servicio para recuperar todos los usuarios, de este modo solo realizao la consulta a la base de datos una vez
		List<UserModel> usersModel = userService.listAllUsers();
		logger.info("GET", "index", "USERS_INDEX", "UserController", "USERS", "RETRIEVED", "List<UserModel> usersModel");
		return new ModelAndView(Constants.USERS_INDEX).addObject("usersModel", usersModel);
	}
	
//	Parametro opcional para que el mismo metodo sea reutilizable, tanto para el admin como para el usuario. Si admin está logueado, enviará el id para
//	editar ese usuario, si un usuario está logueado, al hacer clikck en editar perfil, llamará a este método pero sin el id, por lo que inyectaremos el usuario correspondiente.
	@GetMapping("/editUser")
	public ModelAndView editUserForm(@RequestParam(name = "id", required = false) Integer id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView(Constants.USERS_EDIT);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		boolean condition = Arrays.asList(auth.getAuthorities()).contains("ROLE_ADMIN");
		if(auth.getAuthorities().contains("ROLE_ADMIN")) {
			UserModel temp = userService.findById(id);
			logger.info("GET", "editUserForm", "USERS_EDIT", "UserController (AS ADMIN)", "USER", "EDITED", temp);
			mav.addObject("userModel", temp);
		}
		else{
			UserModel temp = userService.findByUsername(auth.getName());
			logger.info("GET", "editUserForm", "USERS_EDIT", "UserController (AS USER)", "USER", "EDITED", temp);
			mav.addObject("userModel", temp);
		}
		return mav;
	}
	
	@PostMapping("/update")
	public ModelAndView updateUser(@Valid @ModelAttribute("userModel") UserModel userModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "updateUser", "USERS_EDIT", "UserController", "USER", "UPDATED", userModel);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(bindingResult.hasErrors()) {
			mav.setViewName(Constants.USERS_EDIT);
			logger.validationError();
		}
		else {
			if(userService.findByUsername(userModel.getUsername()).getEmail().equals(userModel.getEmail())) {
				if(userService.updateUser(userModel) != null) {
					logger.success("USER", "EDITED", userModel);
					if(auth.getAuthorities().contains("ROLE_ADMIN")) {
						mav.setViewName("redirect:/users/index");
					}
					else {
						mav.setViewName("redirect:/users/profile");
					}
					redirectAttributes.addFlashAttribute("edited", 1);
				}
				else {
					logger.unsuccessful("EDIT", "USER", userModel);
					mav.setViewName("redirect:/users/editUser");
					redirectAttributes.addFlashAttribute("edited", 0);
				}
			}
			else {
				if(userService.checkEmailAvailability(userModel.getEmail())) {
					if(userService.updateUser(userModel) != null) {
						logger.success("USER", "EDITED", userModel);
						if(auth.getAuthorities().contains("ROLE_ADMIN")) {
							mav.setViewName("redirect:/users/index");
						}
						else {
							mav.setViewName("redirect:/users/profile");
						}
						redirectAttributes.addFlashAttribute("edited", 1);
					}
					else {
						logger.unsuccessful("EDIT", "USER", userModel);
						mav.setViewName("redirect:/users/editUser");
						redirectAttributes.addFlashAttribute("edited", 0);
					}
				}
				else {
					mav.setViewName("redirect:/users/editUser");
					logger.regularMessage("EMAIL IN USE");
					redirectAttributes.addFlashAttribute("email", 0);
				}
			}
		}
		return mav;
	}
	
	@GetMapping("/disable/{id}")
	public ModelAndView disableUser(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("redirect:/users/index");
		UserModel temp = userService.findById(id);
		logger.info("GET", "disableUser", "USERS_INDEX", "UserController", "USER", "DISABLE", temp);
		if(userService.disableUser(id) != null) {
			redirectAttributes.addFlashAttribute("disabled", 1);
			redirectAttributes.addFlashAttribute("username", temp.getUsername());
			logger.success("USER", "DISABLED", temp);
		}
		else {
			redirectAttributes.addFlashAttribute("disabled", 0);
			logger.unsuccessful("DISABLE", "USER", temp);
		}
		return mav;
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView deleteUser(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("redirect:/users/index");
		UserModel userModel = userService.findById(id);
		String username = userModel.getUsername();
		logger.info("GET", "deleteUser", "USERS_INDEX", "UserController", "USER", "DELETED", userModel);
		if(userService.deleteUser(id)) {
			redirectAttributes.addFlashAttribute("deleted", 1);
			redirectAttributes.addFlashAttribute("username", username);
			logger.success("USER", "DELETED", userModel);
		}
		else {
			redirectAttributes.addFlashAttribute("deleted", 0);
			logger.unsuccessful("DELETE", "USER", username);
		}
		return mav;
	}
	
	@GetMapping("/register")
	public ModelAndView registerForm() {
		logger.info("GET", "registerForm", "REGISTER_VIEW", "UserController", "USER", "REGISTERED", "NEW USER (Empty)");
		return new ModelAndView(Constants.REGISTER_VIEW).addObject("userModel", new UserModel());
	}
	
	@PostMapping("/signup")
	public ModelAndView registerUser(@Valid @ModelAttribute("userModel") UserModel userModel, BindingResult bindingResult, 
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "registerUser", "REGISTER", "UserController", "USER", "REGISTERED", userModel);
		if(bindingResult.hasErrors()) {
			logger.validationError();
			mav.setViewName(Constants.REGISTER_VIEW);
		}
		else {
			if(userService.checkEmailAvailability(userModel.getEmail()) && userService.checkUsernameAvailability(userModel.getUsername())) {
				if(userService.addUser(userModel) != null) {
					mav.setViewName("redirect:/login");
					redirectAttributes.addFlashAttribute("userAdded", 1);
					logger.success("USER", "REGISTERED", userModel);
				}
				else {
					mav.setViewName("redirect:/users/register");
					redirectAttributes.addFlashAttribute("userAdded", 0);
					logger.unsuccessful("REGISTER", "USER", userModel);
				}
			}
			else {
				if(!userService.checkUsernameAvailability(userModel.getUsername())) {
					mav.setViewName("redirect:/users/register");
					redirectAttributes.addFlashAttribute("username", 0);
					logger.regularMessage("USERNAME IN USE");
				}
				if(!userService.checkEmailAvailability(userModel.getEmail())) {
					mav.setViewName("redirect:/users/register");
					redirectAttributes.addFlashAttribute("email", 0);
					logger.regularMessage("EMAIL IN USE");
				}
			}
		}
		return mav;
	}
	
	@GetMapping("/profile")
	public ModelAndView profile() {
		ModelAndView mav = new ModelAndView(Constants.USERS_PROFILE);
		UserModel userModel = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.info("GET", "profile", "USERS_PROFILE", "UserController", "USER", "REDIRECTED TO HIS PROFILE", userModel);
//		logger.regularMessage("USER '" + userModel.getUsername() + "' REDIRECTED TO HIS PROFILE (PROFILE_VIEW)");
		mav.addObject("userModel", userModel);
		mav.addObject("participacionesModel", userModel.getParticipacionesModel());
		mav.addObject("alquileresModel", userModel.getAlquileresModel());
		mav.addObject("ventasModel", userModel.getVentasModel());
		return mav;
	}
}
