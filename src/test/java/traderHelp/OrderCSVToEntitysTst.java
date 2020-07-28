package traderHelp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.JulianFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import traderHelp.entity.EntityOrder;
import traderHelp.services.OrderCSVToEntitys;

public class OrderCSVToEntitysTst {

	OrderCSVToEntitys oEntitys = new OrderCSVToEntitys();

	@Test
	public void testLoadInFile() {

		List<EntityOrder> orders = oEntitys.loadInFile("מעקוע חא אןנוכüפÿ.csv");
		assertEquals(97, orders.size());
	}

	@Test
	public void testCompareOrder() {
		EntityOrder o1 = new EntityOrder();
		o1.setPrice(2);
		o1.setType(false);

		EntityOrder o2 = new EntityOrder();
		o2.setPrice(3);
		o2.setType(false);

		EntityOrder o3 = new EntityOrder();
		o3.setPrice(2);
		o3.setType(true);
		List<EntityOrder> openOrders = new ArrayList<EntityOrder>();
//		openOrders.add(o3);
		openOrders.add(o2);
		openOrders.add(o1);

		EntityOrder checkOrder = new EntityOrder();

		checkOrder.setType(true);
		checkOrder.setPrice(1);

		EntityOrder resEntityOrder = OrderCSVToEntitys.comareOrder(checkOrder, openOrders);
		System.out.println(resEntityOrder);
	}

	Map<String, List<EntityOrder>> openned = new HashMap<String, List<EntityOrder>>();

	@Test
	public void testAddOrder() {

		EntityOrder o1 = new EntityOrder();
		o1.setPrice(10);
		o1.setType(false);
		o1.setCount(1);
		o1.setName("1");

		EntityOrder o2 = new EntityOrder();
		o2.setPrice(3);
		o2.setName("1");
		o2.setCount(1);
		o2.setType(false);

		EntityOrder o3 = new EntityOrder();
		o3.setPrice(2);
		o3.setName("1");
		o3.setCount(3);
		o3.setType(true);
		List<EntityOrder> openOrders = new ArrayList<EntityOrder>();
//		openOrders.add(o3);
		openOrders.add(o2);
		openOrders.add(o1);

		EntityOrder checkOrder = new EntityOrder();
		checkOrder.setName("1");
		checkOrder.setCount(1);
		checkOrder.setType(false);
		checkOrder.setPrice(10);
		/*
		 * assertEquals(addOrder(o1), 0.0); assertEquals(addOrder(o2), 0.0);
		 * assertEquals(addOrder(o3), -9.0); assertEquals(addOrder(checkOrder), 8.0);
		 */
		allProfit();
	}

	@Test
	private void testAllProfit() {
		allProfit();
	}

	private void allProfit() {
		float total = 0;
		List<EntityOrder> orders = oEntitys.loadInFile("מעקוע חא אןנוכüפÿ.csv");

		for (int i = 0; i < orders.size(); i++) {
			System.out.println(orders.get(i));
			orders.get(i).setId(i);
			float f = addOrder(orders.get(i));
			total += f;
			if (f > 0) {
				System.out.println(i + "|" + f);
			}
		}
		System.out.println("total profit" + total);
		System.out.println("total:" + openned);
	}

	private float addOrder(EntityOrder order) {
		float summ = 0;
		List<EntityOrder> openedByName = openned.get(order.getName());
		if (openedByName == null || openedByName.size() == 0) {
			openned.put(order.getName(), new ArrayList<EntityOrder>() {
				{
					add(order);
				}
			});
		} else {
			EntityOrder compareOrder = OrderCSVToEntitys.comareOrder(order, openedByName);
			if (compareOrder == null) {
				openedByName.add(order);

			} else {
				int counOrder = order.getCount();
				int cmpareCount = compareOrder.getCount();
				while (cmpareCount > 0 && counOrder > 0) {
					float ns = order.getPrice() - compareOrder.getPrice();
					if(compareOrder.isType()) {
						summ += Math.abs(ns);
					}else {
						summ += ns;
					}
					cmpareCount--;
					counOrder--;
				}
				compareOrder.setCount(cmpareCount);
				order.setCount(counOrder);
				if (cmpareCount == 0) {
					System.out.println("close:" + compareOrder.getId());
					openedByName.remove(compareOrder);

					System.out.println(order.getName() + ":" + openned.get(order.getName()).size());

				}

				if (counOrder == 0) {
					return summ;
				} else {
					summ += addOrder(order);
				}
			}

		}
		return summ;
	}

}
