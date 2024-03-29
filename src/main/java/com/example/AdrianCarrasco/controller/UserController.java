package com.example.AdrianCarrasco.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/")
	public RedirectView toIndex() {
		logger.redirect("users/index", "users/");
		return new RedirectView("/users/index");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/index")
	public ModelAndView index() {
//		Asigno a una lista la llamada al servicio para recuperar todos los usuarios, de este modo solo realizao la consulta a la base de datos una vez
		List<UserModel> usersModels = userService.listAllUsers();
		logger.info("GET", "index", "USERS_INDEX", "UserController", "USERS", "RETRIEVED", usersModels);
		return new ModelAndView(Constants.USERS_INDEX).addObject("usersModels", usersModels);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
//	Parametro opcional para que el mismo metodo sea reutilizable, tanto para el admin como para el usuario. Si admin está logueado, enviará el id para
//	editar ese usuario, si un usuario está logueado, al hacer clikck en editar perfil, llamará a este método pero sin el id, por lo que inyectaremos el usuario correspondiente.
	@GetMapping("/editUser")
	public ModelAndView editUserForm(@RequestParam(name = "id", required = false) Integer id) {
		ModelAndView mav = new ModelAndView(Constants.USERS_EDIT);
//		Recuperamos los roles del usuario logueado para comprobar si es ADMIN o USER
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//			Soy ROLE_ADMIN, uso el ID del RequestParam
			UserModel temp = userService.findById(id);
			logger.info("GET", "editUserForm", "USERS_EDIT", "UserController (AS ADMIN)", "USER", "EDITED", temp);
			mav.addObject("userModel", temp);
		}
		else{
//			Soy ROLE_USER, no recibo ID desde el perfil (Editar perfil del menú desplegable)
			UserModel temp = userService.findByUsername(auth.getName());
			logger.info("GET", "editUserForm", "USERS_EDIT", "UserController (AS USER)", "USER", "EDITED", temp);
			mav.addObject("userModel", temp);
		}
		return mav;
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@PostMapping("/update")
	public ModelAndView updateUser(@Valid @ModelAttribute("userModel") UserModel userModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		logger.info("POST", "updateUser", "USERS_EDIT", "UserController", "USER", "UPDATED", userModel);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		if(bindingResult.hasErrors()) {
			mav.setViewName(Constants.USERS_EDIT);
			logger.validationError();
		}
		else {
			if(userService.findByUsername(userModel.getUsername()).getEmail().equals(userModel.getEmail())) {
				if(userService.updateUser(userModel) != null) {
					logger.success("USER", "EDITED", userModel);
					if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
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
						if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/disable/{id}")
	public ModelAndView disableUser(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("redirect:/users/index");
		UserModel userModel = userService.findById(id);
		logger.info("GET", "disableUser", "USERS_INDEX", "UserController", "USER", "DISABLE", userModel);
		if(userService.disableUser(id) != null) {
			redirectAttributes.addFlashAttribute("disabled", 1);
			redirectAttributes.addFlashAttribute("username", userModel.getUsername());
			logger.success("USER", "DISABLED", userModel);
		}
		else {
			redirectAttributes.addFlashAttribute("disabled", 0);
			redirectAttributes.addFlashAttribute("username", userModel.getUsername());
			logger.unsuccessful("DISABLE", "USER", userModel);
		}
		return mav;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
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
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@GetMapping("/profile")
	public ModelAndView profile() {
		ModelAndView mav = new ModelAndView(Constants.USERS_PROFILE);
		UserModel userModel = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.info("GET", "profile", "USERS_PROFILE", "UserController", "USER", "REDIRECTED TO HIS PROFILE", userModel);
//		logger.regularMessage("USER '" + userModel.getUsername() + "' REDIRECTED TO HIS PROFILE (PROFILE_VIEW)");
		mav.addObject("userModel", userModel);
		mav.addObject("participacionesModel", userModel.getParticipaciones());
		mav.addObject("alquileresModel", userModel.getAlquileres());
		mav.addObject("ventasModel", userModel.getVentas());
		return mav;
	}
}
