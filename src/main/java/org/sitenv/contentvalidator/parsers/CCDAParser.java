package org.sitenv.contentvalidator.parsers;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.enums.SeverityLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class CCDAParser {
	
	private static Logger log = Logger.getLogger(CCDAParser.class.getName());
	
	private DocumentBuilderFactory factory;
	private DocumentBuilder        builder;
	private Document               doc;
	
	public void initDoc(String ccdaFile) throws ParserConfigurationException, SAXException, IOException {
		log.info("Initializing Document ");
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		doc = builder.parse(new BOMInputStream(IOUtils.toInputStream(ccdaFile, StandardCharsets.UTF_8.name())));
	}
	
	public CCDARefModel parse(String ccdaFile, boolean curesUpdate) {
		return parse(ccdaFile, SeverityLevel.INFO, curesUpdate);
	}
	
	public CCDARefModel parse(String ccdaFile, SeverityLevel severityLevel, boolean curesUpdate) {
		try {
			//log.info(" Parsing File " + ccdaFile);
			initDoc(ccdaFile);
			CCDAConstants.getInstance();
		
			log.info("Creating Model");
			CCDARefModel model = new CCDARefModel(severityLevel);
			
			model.setPatient(CCDAHeaderParser.getPatient(doc, curesUpdate));
			model.setHeader(CCDAHeaderParser.getHeaderElements(doc, curesUpdate));
			CCDABodyParser.parseBody(doc, model, curesUpdate);
		
			log.info("Returning Parsed Model");
			// model.log();
			
			return model;
		} catch (ParserConfigurationException e1) {
			
			System.out.println("Caught Parser config Excep");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			
			System.out.println("Caught SAX Excep");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			
			System.out.println("Caught IO  Excep");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XPathExpressionException e) {
			
			System.out.println("Caught Xpath Expression ");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return null;
	}
	
	

}
