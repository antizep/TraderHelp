package traderHelp.services;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.scaffold.MethodGraph.NodeList;
import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Xslt30Transformer;
import net.sf.saxon.s9api.XsltExecutable;
import traderHelp.models.Price;

@Slf4j
public class XmlParser {

	public static Configuration conf = new Configuration();
	private static Processor saxonProcessor = new Processor(conf);
	private static XsltExecutable executablePrice;
	private static Xslt30Transformer transformerPrice;
	
	private static Xslt30Transformer transformerFullName;
	private static XsltExecutable executableFullName;
	TransformerFactory factory;
	private final static String JAVAX_TRANSFORM_FACTORY = "javax.xml.transform.TransformerFactory";
	private final static String SAXON_TRANSFORM_FACTORY = "net.sf.saxon.TransformerFactoryImpl";
	private final static String XALAN_TRANSFORM_FACTORY = "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl";

	  public static String edition = saxonProcessor.getSaxonEdition();
	    public static String version = saxonProcessor.getSaxonProductVersion();

    static { 
    	//System.setProperty("javax.xml.parsers.SAXParserFactory","org.apache.xmlbeans.impl.piccolo.xml.JAXPSAXParserFactory");
        log.info("Using Saxon " + edition + " processor");
        log.info("Saxon processor version is " + version);
        log.info(System.getProperty("javax.xml.parsers.SAXParserFactory"));
        log.info(System.getProperty("javax.xml.parsers.DocumentBuilderFactory"));
        log.info(System.getProperty("javax.xml.transform.TransformerFactory"));
        log.info(System.getProperty("javax.xml.xpath.XPathFactory"));

    }
	static {
		InputStream stream = XmlParser.class.getResourceAsStream("/xslt/priceXSLT.xml");
		try {
			executablePrice = saxonProcessor.newXsltCompiler().compile(new StreamSource(stream));
		} catch (SaxonApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transformerPrice = executablePrice.load30();
	}
	
	static {
		InputStream stream =  XmlParser.class.getResourceAsStream("/xslt/fullNameXSLT.xml");
		try {
		executableFullName = saxonProcessor.newXsltCompiler().compile(new StreamSource(stream));
		}catch (SaxonApiException e) {
			e.printStackTrace();
		}
	}
	
	public List<Price> getPrice(InputStream xml) throws TransformerConfigurationException, SaxonApiException {
		List<Price> prices = new ArrayList<Price>(); 
		
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();

		Source source = new StreamSource(xml);
		transformerPrice.applyTemplates(source, saxonProcessor.newSerializer(oStream));
		byte[] b = oStream.toByteArray();
		ByteArrayOutputStream bosName = new ByteArrayOutputStream();
		ByteArrayOutputStream bosPrice = new ByteArrayOutputStream();
		byte stopName = ":".getBytes()[0];
		byte stopPrice = ",".getBytes()[0];
		boolean isName = true;
		for (byte c : b) {
			if (c == stopName) {
				isName = false;
				continue;
			}
			if(c == stopPrice) {
				try {
				Price price = new Price();
				price.setPrice(Double.parseDouble(bosPrice.toString()));
				String trash = "\n";
				price.setSecId(bosName.toString().replace(trash, ""));
				prices.add(price);
				System.out.println("name = "+price.getSecId());
				}catch(NumberFormatException e) {
					log.debug(bosName.toString()+"|"+bosPrice.toString());
				}
				bosPrice.reset();
				bosName.reset();
				isName = true;
				continue;
			}
			
			if(isName) {
				bosName.write(c);
			}else {
				bosPrice.write(c);
			}
		}
		
		return prices;
	}
	
	public String parseFullName(InputStream xml,String scsid) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = factory.newDocumentBuilder().parse(xml);
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		XPathExpression xPathExpression = xPath.compile("/document/data/rows/row[contains(@secid,'"+scsid+"')]/@emitent_title");
		String res =(String) xPathExpression.evaluate(document,XPathConstants.STRING);
		System.out.println(res);
		return res;
		
	}
}
