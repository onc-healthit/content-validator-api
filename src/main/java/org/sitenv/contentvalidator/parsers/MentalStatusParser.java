package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.CCDAMentalStatus;
import org.sitenv.contentvalidator.model.CCDAMentalStatusObservation;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MentalStatusParser {

private static Logger log = LoggerFactory.getLogger(MentalStatusParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022, boolean svap2023)
			throws XPathExpressionException {
    	log.info(" *** Parsing Mental Status *** ");
    	model.setMentalStatus(retrieveMentalStatusDetails(doc));	    	
	}

	private static CCDAMentalStatus retrieveMentalStatusDetails(Document doc) throws XPathExpressionException {
		
		CCDAMentalStatus mentalStatus = null;
		Element sectionElement = (Element) CCDAConstants.MENTAL_STATUS_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		if(sectionElement != null)
		{
			log.info("Adding Mental Status ");
			mentalStatus = new CCDAMentalStatus();
			mentalStatus.setSectionTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(sectionElement, XPathConstants.NODESET)));
			
			mentalStatus.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			NodeList mentalStatusObservationList = (NodeList) CCDAConstants.REL_MENTAL_STATUS_OBSERVATION_EXPRESSION.
				evaluate(sectionElement, XPathConstants.NODESET);
		
			mentalStatus.setMentalStatusObservations(readMentalStatusObservations(mentalStatusObservationList));
		
			NodeList assessmentScaleObservationList = (NodeList) CCDAConstants.REL_ASSESSMENT_SCALE_OBS_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			
			mentalStatus.setFullMentalStatusAssessments(ParserUtilities.retrieveAssessmentScaleObservations(assessmentScaleObservationList));	
			
		}
		return mentalStatus;
	}

	private static ArrayList<CCDAMentalStatusObservation> readMentalStatusObservations(NodeList mentalStatusObservationList) throws XPathExpressionException {
		
			ArrayList<CCDAMentalStatusObservation> mentalStatusObservations = null;
			
			if(!ParserUtilities.isNodeListEmpty(mentalStatusObservationList))
			{
				mentalStatusObservations = new ArrayList<>();
			}
			
			CCDAMentalStatusObservation mentalStatusObservation;
			
			for (int i = 0; i < mentalStatusObservationList.getLength(); i++) {
				
				log.info("Adding CCDA Functional Status Observation");
				mentalStatusObservation = new CCDAMentalStatusObservation();
				
				Element mentalObsElement = (Element) mentalStatusObservationList.item(i);
				
				mentalStatusObservation.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
															evaluate(mentalObsElement, XPathConstants.NODESET)));
				
				mentalStatusObservation.setMentalStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
						evaluate(mentalObsElement, XPathConstants.NODE)));
				
				mentalStatusObservation.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
						evaluate(mentalObsElement, XPathConstants.NODE)));
				
				mentalStatusObservation.setMentalStatusValue(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
						evaluate(mentalObsElement, XPathConstants.NODE)));
				
				mentalStatusObservation.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
						evaluate(mentalObsElement, XPathConstants.NODE)));
				
				mentalStatusObservations.add(mentalStatusObservation);
			}
			
			
			return mentalStatusObservations;
	}
	
	
	
	
}
