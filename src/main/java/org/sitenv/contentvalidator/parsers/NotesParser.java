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
	
	public static void parse(Document doc, CCDARefModel model) throws XPathExpressionException {
    	
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
				note.setNotesActivity(readNotesActivity((NodeList) CCDAConstants.REL_NOTES_ACTIVITY_EXPRESSION.
						evaluate(elem, XPathConstants.NODESET)));
				
				notes.add(note);
			}
			
		}
		
		return notes;
	}
	
	public static ArrayList<CCDANotesActivity> readNotesActivity(NodeList notesActivityList) throws XPathExpressionException
	{
		ArrayList<CCDANotesActivity> notesActList = null;
		
		if(!ParserUtilities.isNodeListEmpty(notesActivityList)) {
			
			notesActList = new ArrayList<>();
			
			for (int i = 0; i < notesActivityList.getLength(); i++) {
				
				log.info("Found Notes Activity ");
				
				CCDANotesActivity notesActivity = new CCDANotesActivity();
				
				Element notesActElem = (Element) notesActivityList.item(i);
				
				notesActivity.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
											evaluate(notesActElem, XPathConstants.NODESET)));
				
				notesActivity.setActivityCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
						evaluate(notesActElem, XPathConstants.NODE)));
				
				notesActivity.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
						evaluate(notesActElem, XPathConstants.NODE)));
				
				notesActivity.setText(ParserUtilities.readTextContext((Element) CCDAConstants.REL_TEXT_EXP.
						evaluate(notesActElem, XPathConstants.NODE)));
				
				notesActivity.setEffTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
									evaluate(notesActElem, XPathConstants.NODE)));
				
				notesActivity.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
						evaluate(notesActElem, XPathConstants.NODE)));
				
				notesActList.add(notesActivity);
			}
			
		}
		
		return notesActList;
	}
}
