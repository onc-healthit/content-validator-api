package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.model.CCDAAuthor;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AuthorParser {

	private static Logger log = Logger.getLogger(AuthorParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate) throws XPathExpressionException {    	
    	log.info(" *** Parsing Author *** ");
    	model.setAuthorsFromHeader(retrieveAuthorsFromHeader(doc));
    	// TODO: For performance reasons, consider only running on sub model, not ref models.
    	// I'm not sure a need yet for the linked references on the scenarios, but there may be one.
    	// However, since scenarios are only loaded once, it's not all that important for performance either. 
    	// Subs are loaded every time, but there's only one (and we need that data). That's the current purpose of the call.
    	model.setAuthorsWithLinkedReferenceData(retrieveAuthorsWithLinkedReferenceData(doc));    	
	}
	
	public static ArrayList<CCDAAuthor> retrieveAuthorsFromHeader(Document doc) throws XPathExpressionException {		
		ArrayList<CCDAAuthor> auths = new ArrayList<CCDAAuthor>();
		CCDAAuthor auth = null;
		NodeList docAuths = (NodeList)(CCDAConstants.AUTHORS_FROM_HEADER_EXP.evaluate(doc, XPathConstants.NODESET));
		
		for (int i = 0; i < docAuths.getLength(); i++) {
			
			log.info("Parsing Author at document level ");
			Element authElement = (Element) docAuths.item(i);
			
			auth = ParserUtilities.readAuthor(authElement);
			
			if(auth != null) {
				log.info(" Adding header Author ");
				auths.add(auth);
			}				
		}
		
		return auths;
	}
	
	public static ArrayList<CCDAAuthor> retrieveAuthorsWithLinkedReferenceData(Document doc)
			throws XPathExpressionException {
		ArrayList<CCDAAuthor> auths = new ArrayList<CCDAAuthor>();
		CCDAAuthor auth = null;
		NodeList bodyAuths = (NodeList) 
				CCDAConstants.AUTHORS_WITH_LINKED_REFERENCE_DATA_EXP.evaluate(doc, XPathConstants.NODESET);

		for (int i = 0; i < bodyAuths.getLength(); i++) {
			log.info(
					"Parsing Authors with linked reference data from the entire document "
					+ "(header, sections, entries, and statements)");
			Element authElement = (Element) bodyAuths.item(i);
			auth = ParserUtilities.readAuthor(authElement);
			if (auth != null) {
				log.info(" Adding Author ");
				auths.add(auth);
			}
		}

		return auths;
	}	

}
