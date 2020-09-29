package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.model.CCDANotes;
import org.sitenv.contentvalidator.model.CCDANotesActivity;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class NotesParser {

private static Logger log = Logger.getLogger(NotesParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate) throws XPathExpressionException {
    	
    	model.setNotes(retrieveNotesDetails(doc));	
    	
	}
	
	public static ArrayList<CCDANotes> retrieveNotesDetails(Document doc) throws XPathExpressionException {
		
		ArrayList<CCDANotes> notes = null;
		NodeList sectionNodes = (NodeList) CCDAConstants.NOTES_EXPRESSION.evaluate(doc, XPathConstants.NODESET);
		
		if( !ParserUtilities.isNodeListEmpty(sectionNodes)) {		
		
			notes = new ArrayList<CCDANotes>();
			log.info(" Found Notes sections ");
			
			for(int i = 0; i < sectionNodes.getLength(); i++) {
				
				Element elem = (Element)sectionNodes.item(i);
				
				CCDANotes note = new CCDANotes();
				
				note.setSectionTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(elem, XPathConstants.NODESET)));
				
				note.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(elem, XPathConstants.NODE)));
				
				// Grab the notes activities
				note.setNotesActivity(ParserUtilities.readNotesActivity((NodeList) CCDAConstants.REL_NOTES_ACTIVITY_EXPRESSION.
						evaluate(elem, XPathConstants.NODESET), note));
				
				note.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
						evaluate(elem, XPathConstants.NODE)));
				
				notes.add(note);
			}
			
		}
		
		return notes;
	}
	
	
}
