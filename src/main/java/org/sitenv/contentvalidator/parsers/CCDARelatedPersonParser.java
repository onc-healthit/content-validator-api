package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.CCDAAssociatedEntity;
import org.sitenv.contentvalidator.model.CCDAParticipant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class CCDARelatedPersonParser {

	private static Logger log = LoggerFactory.getLogger(CCDARelatedPersonParser.class.getName());

	static public ArrayList<CCDAParticipant> getRelatedPersons(Document doc, boolean curesUpdate, boolean svap2022, boolean svap2023)
			throws XPathExpressionException {
		
		ArrayList<CCDAParticipant> rps = new ArrayList<>();
		
		// Retrieve the participant  element.
		NodeList nodeList = (NodeList) CCDAConstants.DOC_PARTICIPANT_EXP.evaluate(doc, XPathConstants.NODESET);
		
		if(nodeList != null) {
			rps = readRelatedPerson(nodeList);
		}
		
		return rps;
	}

	private static ArrayList<CCDAParticipant> readRelatedPerson(NodeList nodeList) throws XPathExpressionException {
		
		ArrayList<CCDAParticipant> rps = new ArrayList<>();
		if(nodeList != null) {
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Node nd = nodeList.item(i);
				
				CCDAParticipant part = new CCDAParticipant();
				part.setAssociatedEntity(new CCDAAssociatedEntity());

				//set template id
				part.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList)CCDAConstants.REL_TEMPLATE_ID_EXP.evaluate(nd,XPathConstants.NODESET)));

				part.getAssociatedEntity().setAssociatedEntityCode(ParserUtilities.readCode((Element) CCDAConstants.REL_ASSOCIATED_CODE_EXP.
		    				evaluate(nd, XPathConstants.NODE)));
				
				NodeList namenodes = (NodeList) CCDAConstants.REL_ASSOCIATED_PERSON_NAME_EXP.
	    				evaluate(nd, XPathConstants.NODESET);
				
				if(namenodes != null && namenodes.getLength() > 0) {
					
					Node namend = namenodes.item(0);
					// set the name
					part.getAssociatedEntity().setName(ParserUtilities.readTextContext((Element)namend));

					part.getAssociatedEntity().setLastName(ParserUtilities.readTextContext((Element) CCDAConstants.REL_FAMILY_NAME_EXP.
							evaluate(namend, XPathConstants.NODE)));
					
					part.getAssociatedEntity().setFirstName(ParserUtilities.readTextContext((Element) CCDAConstants.REL_GIVEN_NAME_EXP.
							evaluate(namend, XPathConstants.NODE)));
					
				}
				
				rps.add(part);
			}
		}
		
		return rps;
	}
}
