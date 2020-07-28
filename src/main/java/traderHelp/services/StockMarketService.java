/**
 * 
 */
package traderHelp.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.ls.LSException;

import traderHelp.entity.EntityTools;
import traderHelp.models.Price;
import traderHelp.repository.ToolsRepository;

/**
 * @author antiz
 *
 */
public class StockMarketService implements Runnable {

	public ToolsRepository repository;

	private static Map<String, EntityTools> tMap = new HashMap<String, EntityTools>();

	@Autowired
	public StockMarketService(ToolsRepository repository) {
		this.repository = repository;
		if (tMap.isEmpty()) {
			Iterable<EntityTools> tollsIterable = repository.findAll();
			for (EntityTools entityTools : tollsIterable) {
				tMap.put(entityTools.getSecId(), entityTools);
			}
		}

	}

	private static final String URL_PRICE = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities.xml?iss.meta=off&iss.only=securities&securities.columns=SECID,PREVADMITTEDQUOTE";
	private static final String URL_INFO = "https://iss.moex.com/iss/securities.xml?q=";
	private static final String USER_AGENT = "Mozilla/5.0";

	@Override
	public void run() {
		XmlParser parser = new XmlParser();
		try (CloseableHttpClient client = HttpClients.createDefault();) {
			Map<String, Double> preList = new HashMap<>();

			if (true) {

				long t = System.currentTimeMillis();
				HttpGet get = new HttpGet(URL_PRICE);
				get.addHeader("User-Agent", USER_AGENT);
				CloseableHttpResponse resp = client.execute(get);
				List<Price> prices = parser.getPrice(resp.getEntity().getContent());
				if (preList.isEmpty()) {
					for (Price price : prices) {
						preList.put(price.getSecId(), price.getPrice());
						loadFullName(price.getSecId());
					}
				} else {
					for (Price price : prices) {
						Double preRes = preList.get(price.getSecId());

						loadFullName(price.getSecId());
						if (preRes != price.getPrice()) {
							System.out.println(price.getSecId() + "|" + price.getPrice());
							preList.put(price.getSecId(), price.getPrice());
						}
					}

				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<EntityTools> getAllTools() {
		List<EntityTools> result = new ArrayList<EntityTools>();
		Set<String> keys = tMap.keySet();

		for (String key : keys) {
			result.add(tMap.get(key));
		}

		return result;
	}

	public void loadFullName(String secId) {
		if (!tMap.containsKey(secId)) {

			EntityTools tools = repository.findBySecId(secId);
			if (tools == null) {
				tools = new EntityTools();
				XmlParser parser = new XmlParser();
				try (CloseableHttpClient client = HttpClients.createDefault();) {
					String urlString = URL_INFO + secId;
					System.out.println(urlString);
					HttpGet get = new HttpGet(urlString);
					get.addHeader("User-Agent", USER_AGENT);
					CloseableHttpResponse resp = client.execute(get);
					String fullName = parser.parseFullName(resp.getEntity().getContent(), secId);
					tools.setFullName(fullName);
					tools.setSecId(secId);
					repository.save(tools);
				} catch (Exception e) {

					e.printStackTrace();
				}
			} else {
				tMap.put(tools.getSecId(), tools);
			}
		}
	}
	public EntityTools getToolsBySid(String sid) {
		return tMap.get(sid);
	}
}
