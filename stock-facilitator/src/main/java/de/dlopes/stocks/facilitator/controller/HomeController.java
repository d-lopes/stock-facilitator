package de.dlopes.stocks.facilitator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/")
    public String index() {
        return "Relax. You are home!";
    }
	
}
