package com.smartbuilding;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VueController {

	
	@RequestMapping("/")
	public String index() {
		
		return "index";
	}
	//methode pour retourner la page qui liste les services 
		@RequestMapping("/listeService")
		public String listeService() {
			
			return "listeService";
		}
		
}
