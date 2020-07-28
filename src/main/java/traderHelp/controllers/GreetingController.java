package traderHelp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


import lombok.extern.slf4j.Slf4j;
import traderHelp.entity.EntityOrder;
import traderHelp.entity.EntityTools;
import traderHelp.repository.OrderRepository;
import traderHelp.repository.ToolsRepository;
import traderHelp.services.StockMarketService;

@Controller
@Slf4j
public class GreetingController {
	@Autowired
	public ToolsRepository repository;
	@Autowired
	public OrderRepository orderRepository;

	@GetMapping("/index")
	public String greeting(Model model) {

		StockMarketService service = new StockMarketService(repository);

		List<EntityTools> tools = service.getAllTools();
		
		List<EntityOrder> orders = new ArrayList<EntityOrder>();
		for(EntityOrder e:orderRepository.findAll()) {
			orders.add(e);
		}
		log.info("Service" + tools);
		model.addAttribute("lists", tools);
		model.addAttribute("orders",orders);
		
		return "index";

	}
}
