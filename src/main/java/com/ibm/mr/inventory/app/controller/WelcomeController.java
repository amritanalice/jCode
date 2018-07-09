package com.ibm.mr.inventory.app.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
    
    @RequestMapping("/")
	public String welcome() {
	return "forward:/ProductsView.html";
	}
    

}
