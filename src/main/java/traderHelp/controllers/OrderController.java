package traderHelp.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import traderHelp.entity.EntityOrder;
import traderHelp.repository.OrderRepository;
import traderHelp.repository.ToolsRepository;
import traderHelp.services.StockMarketService;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

	@Autowired
	OrderRepository orderRepository;

	@RequestMapping(name = "add", path = "add", method = RequestMethod.POST)
	public String addOrder(String price, String count, String operation, String date, String sid) {

		log.info(date);
		EntityOrder entityOrder = new EntityOrder();
		Integer couInteger = Integer.parseInt(count);
		Float priceFloat = Float.parseFloat(price);
		entityOrder.setCount(couInteger);
		entityOrder.setName(sid);
		entityOrder.setPrice(priceFloat);
		entityOrder.setSumm(priceFloat * couInteger);
		long d = 0;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date).getTime();
			log.info(new Date(d).toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		entityOrder.setDate(new Date(d));
		entityOrder.setType(operation.equals("buy"));
		orderRepository.save(entityOrder);
		log.info(entityOrder.toString());
		return "ok";
	}
}
