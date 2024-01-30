package org.sitenv.contentvalidator.parsers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.enums.SeverityLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Component
public class CCDAParser {
	
	private static Logger log = LoggerFactory.getLogger(CCDAParser.class.getName());
	
	private DocumentBuilder        builder;
	private Document               doc;
	
	public void initDoc(String ccdaFile) throws ParserConfigurationException, SAXException, IOException {
		log.info("Initializing Document ");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(
				"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl",
				ClassLoader.getSystemClassLoader());
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		factory.setXIncludeAware(false);
		factory.setExpandEntityReferences(false);		
		builder = factory.newDocumentBuilder();
		doc = builder.parse(new BOMInputStream(IOUtils.toInputStream(ccdaFile, StandardCharsets.UTF_8.name())));
	}
	
	/*
	 * Called by scenario loader on application start, parses scenarios themselves
	 */
	public CCDARefModel parse(String ccdaFile, boolean curesUpdate, boolean svap2022, boolean svap2023) {
		return parse(ccdaFile, SeverityLevel.INFO, curesUpdate, svap2022, svap2023);
	}
	
	/*
	 * Called by scenario loader on application start, parses scenarios themselves
	 */
//	public CCDARefModel parse(String ccdaFile) {
//		return parse(ccdaFile, SeverityLevel.INFO, true, true);
//	}
	
	/*
	 * Called by each validation, on the users file
	 */	
	public CCDARefModel parse(String ccdaFile, SeverityLevel severityLevel, 
			boolean curesUpdate, boolean svap2022, boolean svap2023) {
		
		try {
			//log.info(" Parsing File " + ccdaFile);
			initDoc(ccdaFile);
			CCDAConstants.getInstance();
		
			log.info("Creating Model");
			CCDARefModel model = new CCDARefModel(severityLevel);
			
			model.setPatient(CCDAHeaderParser.getPatient(doc, curesUpdate, svap2022, svap2023));
			model.setHeader(CCDAHeaderParser.getHeaderElements(doc, curesUpdate, svap2022, svap2023));
			model.setRelatedPersons(CCDARelatedPersonParser.getRelatedPersons(doc,curesUpdate,svap2022,svap2023));
			CCDABodyParser.parseBody(doc, model, curesUpdate, svap2022, svap2023);
		
			log.info("Returning Parsed Model");
			model.log();
			
			return model;
		} catch (ParserConfigurationException e1) {
			System.out.println("Caught Parser config Excep");
			e1.printStackTrace();
		} catch (SAXException e1) {
			System.out.println("Caught SAX Excep");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("Caught IO  Excep");
			e1.printStackTrace();
		} catch (XPathExpressionException e) {
			System.out.println("Caught Xpath Expression ");
			e.printStackTrace();
		}
		
		return null;
	}
	
}
