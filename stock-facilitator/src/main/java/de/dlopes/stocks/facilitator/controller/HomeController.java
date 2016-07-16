package de.dlopes.stocks.facilitator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.dlopes.stocks.facilitator.config.ConfigurationSettings;
import de.dlopes.stocks.facilitator.services.StockDataCollector;

@Controller
public class HomeController {

	@Autowired
	ConfigurationSettings cs;
	
	@RequestMapping("/")
    public String index(Model model) {
		StockDataCollector dataCollector = cs.getDataCollector();
		model.addAttribute("stocks", dataCollector.getData());
		return "index";
		
    }
	
}