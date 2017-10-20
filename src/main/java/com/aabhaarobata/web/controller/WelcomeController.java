package com.aabhaarobata.web.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
	private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);
	
	
	@Value("${welcome.message}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		log.debug("rendering default page..");
		model.put("message", this.message);
		return "selectcolor";
	}
}
