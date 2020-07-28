package traderHelp.services;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.Case;

import lombok.extern.slf4j.Slf4j;
import traderHelp.entity.EntityOrder;
@Slf4j
public class OrderCSVToEntitys {

	public static List<EntityOrder> loadInFile(String fileName) {

		
		try (FileReader reader = new FileReader(fileName)) {

			int c;
			StringBuffer buffer = new StringBuffer();
			while ((c = reader.read()) != -1) {

				buffer.append((char) c);
			}
			
			return parseInString(buffer.toString());
			
		} catch (IOException | ParseException ex) {

			System.out.println(ex.getMessage());
		}

		return null;
	}
	
	public static List<EntityOrder> parseInString(String csvDoc) throws ParseException {
		List<EntityOrder> result = new ArrayList<EntityOrder>();
		
		String[] lines = csvDoc.split("\n");
		for (String line : lines) {
			EntityOrder entityOrder = new EntityOrder();

			String[] cells = line.split(";");
			if (cells.length < 24) {
				continue;
			}
			String type = cells[5];
			switch(type){
				case "Продажа":{
					entityOrder.setType(false);
					break;
				}
				case "Покупка":{
					entityOrder.setType(true);
					break;
				}
				default:{
					continue;
				}
			}
			entityOrder.setCount(Integer.parseInt(cells[6]));
			String dateS = cells[3]+" "+cells[4];
			
			DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			entityOrder.setDate(format.parse(dateS));
			entityOrder.setName(cells[1]);
			entityOrder.setPrice(Float.parseFloat(cells[7].replace(",",".").replace(" ", "")));
			entityOrder.setSumm(Float.parseFloat(cells[8].replace(",", ".").replace(" ", "")));
	
			result.add(entityOrder);
			
			 
		}
		return sortByDate(result);
	}
	
	private static List<EntityOrder> sortByDate(List<EntityOrder> orders) {
		Comparator<EntityOrder> comparator = new Comparator<EntityOrder>() {

			@Override
			public int compare(EntityOrder o1, EntityOrder o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		};
		Collections.sort(orders, comparator);
		
		return orders;
		
	}
	
	public static EntityOrder comareOrder(EntityOrder order,List<EntityOrder> openOrder) {
		float compare = 0;
		EntityOrder resultOrder = null;
		for(EntityOrder e: openOrder) {
			if(order.isType()!=e.isType()) {
				float ncompare = order.getPrice()- e.getPrice();
				if(order.isType()) {
					ncompare*=-1;
				}
				if(ncompare>compare) {
					resultOrder = e;
					compare = ncompare;
				}
			}
		}
		return resultOrder;
	}
	
	
}
