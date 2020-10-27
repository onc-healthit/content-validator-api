package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.model.CCDAAllergyConcern;
import org.sitenv.contentvalidator.model.CCDAAuthor;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AuthorParser {

	private static Logger log = Logger.getLogger(AuthorParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate) throws XPathExpressionException {
    	
    	log.info(" *** Parsing Author *** ");
    	model.setAuthors(retrieveAuthor(doc));	
	}
	
	public static ArrayList<CCDAAuthor> retrieveAuthor(Document doc) throws XPathExpressionException {
		
		ArrayList<CCDAAuthor> auths = new ArrayList<CCDAAuthor>();
		CCDAAuthor auth = null;
		NodeList docAuths = (NodeList)(CCDAConstants.AUTHOR_EXP.evaluate(doc, XPathConstants.NODESET));
		
		for (int i = 0; i < docAuths.getLength(); i++) {
			
			log.info("Parsing Author at document level ");
			Element authElement = (Element) docAuths.item(i);
			
			auth = ParserUtilities.readAuthor(authElement);
			
			if(auth != null) {
				log.info(" Adding Author ");
				auths.add(auth);
			}				
		}
		
		return auths;
	}
}
