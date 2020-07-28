package traderHelp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import traderHelp.repository.ToolsRepository;
import traderHelp.services.StockMarketService;

@RestController
@RequestMapping("/service")
public class StartServiceController {
	@Autowired
	ToolsRepository repository;
	
	@RequestMapping(path = "/start",method = RequestMethod.POST)
	public String startService() {

		new Thread(new StockMarketService(repository)).start();
		return "ok";
	}

	
}

