package com.in28minutes.springboot.myfirstwebapp.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class LoginController {
	
	private AuthenticationService authenticationSrc;
	
	public LoginController(AuthenticationService authenticationSrc) {
		super();
		this.authenticationSrc = authenticationSrc;
	}

	@RequestMapping(value = "login", method= RequestMethod.GET)
	public String goToLoginPage(@RequestParam(required=false) String name, ModelMap model) {
		model.put("name", name);
		return "login";
	}
	
	@RequestMapping(value = "login", method= RequestMethod.POST)
	public String goToWelcomPage(@RequestParam String name, @RequestParam String password, ModelMap model) {
		if (authenticationSrc.authenticate(name, password)) {
			model.put("name", name);
			model.put("password", password);
			return "welcome";
		} 
		model.put("errorMessage", "Invalid Credentials! Please try again.");
		return "login";
	}

}
