package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.AssessmentScaleObservation;
import org.sitenv.contentvalidator.model.CCDABasicOccupation;
import org.sitenv.contentvalidator.model.CCDADisabilityStatusObservation;
import org.sitenv.contentvalidator.model.CCDAFunctionalStatus;
import org.sitenv.contentvalidator.model.CCDAFunctionalStatusObservation;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.model.CCDASocialHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FunctionalStatusParser {

	private static Logger log = LoggerFactory.getLogger(FunctionalStatusParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022, boolean svap2023)
			throws XPathExpressionException {
    	log.info(" *** Parsing Functional Status *** ");
    	model.setFunctionalStatus(retrieveFunctionalStatusDetails(doc));	    	
	}

	private static CCDAFunctionalStatus retrieveFunctionalStatusDetails(Document doc) throws XPathExpressionException {
		
		CCDAFunctionalStatus functionalStatus = null;
		Element sectionElement = (Element) CCDAConstants.FUNCTIONAL_STATUS_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		if(sectionElement != null)
		{
			log.info("Adding Functional Status ");
			functionalStatus = new CCDAFunctionalStatus();
			functionalStatus.setSectionTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(sectionElement, XPathConstants.NODESET)));
			
			functionalStatus.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			NodeList functionalStatusObservationList = (NodeList) CCDAConstants.REL_FUNCTIONAL_STATUS_OBSERVATION_EXPRESSION.
				evaluate(sectionElement, XPathConstants.NODESET);
		
			functionalStatus.setFunctionalStatusObservation(readFunctionalStatusObservations(functionalStatusObservationList));
		
			NodeList disabilityObservationList = (NodeList) CCDAConstants.REL_DISABILITY_STATUS_OBSERVATION_EXPRESSION.
				evaluate(sectionElement, XPathConstants.NODESET);
		
			functionalStatus.setDisabilityStatusObservation(readDisabilityObservations(disabilityObservationList));
			
			NodeList assessmentScaleObservationList = (NodeList) CCDAConstants.REL_ASSESSMENT_SCALE_OBS_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			
			functionalStatus.setFunctionalAssessmentsObservations(ParserUtilities.retrieveAssessmentScaleObservations(assessmentScaleObservationList));	
			
		}
		return functionalStatus;
	}

	private static ArrayList<CCDADisabilityStatusObservation> readDisabilityObservations(
			NodeList disabilityObservationList) throws XPathExpressionException {
		
		ArrayList<CCDADisabilityStatusObservation> disabilityObservations = null;
		
		if(!ParserUtilities.isNodeListEmpty(disabilityObservationList))
		{
			disabilityObservations = new ArrayList<>();
		}
		
		CCDADisabilityStatusObservation disabilityObservation;
		
		for (int i = 0; i < disabilityObservationList.getLength(); i++) {
			
			log.info("Adding CCDA Disability Observation");
			disabilityObservation = new CCDADisabilityStatusObservation();
			
			Element disabilityElement = (Element) disabilityObservationList.item(i);
			
			disabilityObservation.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(disabilityElement, XPathConstants.NODESET)));
			
			disabilityObservation.setDisabilityStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(disabilityElement, XPathConstants.NODE)));
			
			disabilityObservation.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(disabilityElement, XPathConstants.NODE)));
			
			disabilityObservation.setDisabilityStatusValue(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
					evaluate(disabilityElement, XPathConstants.NODE)));
			
			disabilityObservation.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(disabilityElement, XPathConstants.NODE)));
			
			disabilityObservations.add(disabilityObservation);
		}
		
		
		return disabilityObservations;
	}

	private static ArrayList<CCDAFunctionalStatusObservation> readFunctionalStatusObservations(NodeList functionalStatusObservationList) throws XPathExpressionException {
		
			ArrayList<CCDAFunctionalStatusObservation> funcStatusObservations = null;
			
			if(!ParserUtilities.isNodeListEmpty(functionalStatusObservationList))
			{
				funcStatusObservations = new ArrayList<>();
			}
			
			CCDAFunctionalStatusObservation funcStatusObsrvation;
			
			for (int i = 0; i < functionalStatusObservationList.getLength(); i++) {
				
				log.info("Adding CCDA Functional Status Observation");
				funcStatusObsrvation = new CCDAFunctionalStatusObservation();
				
				Element functionalObsElement = (Element) functionalStatusObservationList.item(i);
				
				funcStatusObsrvation.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
															evaluate(functionalObsElement, XPathConstants.NODESET)));
				
				funcStatusObsrvation.setFunctionalStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
						evaluate(functionalObsElement, XPathConstants.NODE)));
				
				funcStatusObsrvation.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
						evaluate(functionalObsElement, XPathConstants.NODE)));
				
				funcStatusObsrvation.setFunctionalStatusValueCode(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
						evaluate(functionalObsElement, XPathConstants.NODE)));
				
				funcStatusObsrvation.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
						evaluate(functionalObsElement, XPathConstants.NODE)));
				
				funcStatusObservations.add(funcStatusObsrvation);
			}
			
			
			return funcStatusObservations;
	}
	
	
	
	
}
