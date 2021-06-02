package org.sitenv.contentvalidator.parsers;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;

public class EncounterParser {
	
	private static Logger log = Logger.getLogger(EncounterParser.class.getName());
	
    public static void parse(Document doc, CCDARefModel model, boolean curesUpdate) throws XPathExpressionException {
    	
    	model.setEncounter(retrieveEncounterDetails(doc, curesUpdate));	
    	
    	model.setAdmissionDiagnosis(retrieveAdmissionDiagnosisDetails(doc));
    	
    	model.setDischargeDiagnosis(retrieveDischargeDiagnosisDetails(doc));
	}
    
    public static CCDAEncounter retrieveEncounterDetails(Document doc, boolean curesUpdate) throws XPathExpressionException
	{
		Element sectionElement = (Element) CCDAConstants.ENCOUNTER_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		CCDAEncounter encounters = null;
		
		if(sectionElement != null)
		{
			log.info(" Adding Encounter ");
			encounters = new CCDAEncounter();
			
			//Get Template Ids
			encounters.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			// Get Section Code
			encounters.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			// Get Entries
			encounters.setEncActivities(readEncounterActivity((NodeList) CCDAConstants.REL_ENC_ENTRY_EXP.
					evaluate(sectionElement, XPathConstants.NODESET), curesUpdate));
			
			encounters.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			// Add Notes Activity if present in Results entry
			encounters.setNotesActivity(ParserUtilities.readNotesActivity((NodeList) CCDAConstants.REL_NOTES_ACTIVITY_EXPRESSION.
								evaluate(sectionElement, XPathConstants.NODESET), null));
		}
		return encounters;
	}
    
	public static ArrayList<CCDAEncounterActivity> readEncounterActivity(NodeList encounterActivityNodeList, boolean curesUpdate) throws XPathExpressionException
	{
		ArrayList<CCDAEncounterActivity> encounterActivityList = new ArrayList<>();
		CCDAEncounterActivity encounterActivity;
		for (int i = 0; i < encounterActivityNodeList.getLength(); i++) {
			
			Element encounterActivityElement = (Element) encounterActivityNodeList.item(i);
			
			if(encounterActivityElement != null)
			{
				log.info(" Adding Encounter Activity");
				encounterActivity = new CCDAEncounterActivity();
				
				//Get Tempalte Ids
				encounterActivity.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(encounterActivityElement, XPathConstants.NODESET)));

				encounterActivity.setEncounterTypeCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
										evaluate(encounterActivityElement, XPathConstants.NODE)));
				
				encounterActivity.setEffectiveTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
															evaluate(encounterActivityElement, XPathConstants.NODE)));
				
				encounterActivity.setSdLocs(readServiceDeliveryLocators((NodeList) CCDAConstants.REL_PART_ROLE_EXP.
																evaluate(encounterActivityElement, XPathConstants.NODESET)));
				
				NodeList encounterDiagnosisNodeList = (NodeList) CCDAConstants.REL_ENTRY_RELSHIP_ACT_EXP.
								evaluate(encounterActivityElement, XPathConstants.NODESET);
				
				encounterActivity.setDiagnoses(readEncounterDiagnosis(encounterDiagnosisNodeList));
				
				NodeList indicationNodeList = (NodeList) CCDAConstants.REL_ENTRY_RELSHIP_OBS_EXP.
								evaluate(encounterActivityElement, XPathConstants.NODESET);
				
				encounterActivity.setIndications(readProblemObservation(indicationNodeList));
				
				// Add Notes Activity if present.
				encounterActivity.setNotesActivity(
						ParserUtilities.readNotesActivity((NodeList) CCDAConstants.REL_ENTRY_REL_NOTES_ACTIVITY_EXPRESSION
								.evaluate(encounterActivityElement, XPathConstants.NODESET), null));
				
				encounterActivity.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
						evaluate(encounterActivityElement, XPathConstants.NODE)));
				
				encounterActivityList.add(encounterActivity);
				
				
			}
		}
		return encounterActivityList;
	}
	
	public static ArrayList<CCDAEncounterDiagnosis> readEncounterDiagnosis(NodeList encounterDiagnosisNodeList) throws XPathExpressionException
	{
		ArrayList<CCDAEncounterDiagnosis> encounterDiagnosisList = null;
		if(encounterDiagnosisNodeList.getLength() > 0)
		{
			encounterDiagnosisList = new ArrayList<>();
		}
		CCDAEncounterDiagnosis encounterDiagnosis;
		for (int i = 0; i < encounterDiagnosisNodeList.getLength(); i++) {
			
			log.info(" Adding Encounter Diagnosis");
			
			Element encounterDiagnosisElement = (Element) encounterDiagnosisNodeList.item(i);
			encounterDiagnosis = new CCDAEncounterDiagnosis();
			encounterDiagnosis.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
					evaluate(encounterDiagnosisElement, XPathConstants.NODESET)));
			
			encounterDiagnosis.setEntryCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
									evaluate(encounterDiagnosisElement, XPathConstants.NODE)));
			
			NodeList problemObservationNodeList = (NodeList) CCDAConstants.REL_ENTRY_RELSHIP_OBS_EXP.
										evaluate(encounterDiagnosisElement, XPathConstants.NODESET);
			
			encounterDiagnosis.setProblemObs(readProblemObservation(problemObservationNodeList));
			
			encounterDiagnosis.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(encounterDiagnosisElement, XPathConstants.NODE)));
			
			encounterDiagnosisList.add(encounterDiagnosis);
		}
		
		return encounterDiagnosisList;
	}
	
	public static ArrayList<CCDAProblemObs> readProblemObservation(NodeList problemObservationNodeList) throws XPathExpressionException
	{
		ArrayList<CCDAProblemObs> problemObservationList = null;
		if(problemObservationNodeList.getLength() > 0)
		{
			problemObservationList = new ArrayList<>();
		}
		CCDAProblemObs problemObservation;
		for (int i = 0; i < problemObservationNodeList.getLength(); i++) {
			
			log.info(" Adding Problem Observation as part of encounter ");
			problemObservation = new CCDAProblemObs();
			
			Element problemObservationElement = (Element) problemObservationNodeList.item(i);
			problemObservation.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
					evaluate(problemObservationElement, XPathConstants.NODESET)));
			
			problemObservation.setProblemType(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
									evaluate(problemObservationElement, XPathConstants.NODE)));
			
			problemObservation.setTranslationProblemType(ParserUtilities.readCodeList((NodeList) CCDAConstants.REL_CODE_TRANS_EXP.
									evaluate(problemObservationElement, XPathConstants.NODESET)));
			
			problemObservation.setEffTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
										evaluate(problemObservationElement, XPathConstants.NODE)));
			
			problemObservation.setProblemCode(ParserUtilities.readCodeWithTranslation((Element) CCDAConstants.REL_VAL__WITH_TRANS_EXP.
					evaluate(problemObservationElement, XPathConstants.NODE)));
			
			problemObservation.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(problemObservationElement, XPathConstants.NODE)));
			
			problemObservationList.add(problemObservation);
		}
		
		return problemObservationList;
	}	

	public static CCDAAdmissionDiagnosis retrieveAdmissionDiagnosisDetails(Document doc) throws XPathExpressionException
	{
		Element sectionElement = (Element) CCDAConstants.ADMISSION_DIAG_EXP.evaluate(doc, XPathConstants.NODE);
		CCDAAdmissionDiagnosis admDiag = null;
		
		if(sectionElement != null)
		{
			log.info(" Adding Admission Diagnosis ");
			admDiag = new CCDAAdmissionDiagnosis();
			
			//Get Template Ids
			admDiag.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			// Get Section Code
			admDiag.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			// Get Entries
			admDiag.setDiagnosis(readHospitalAdmissionDiagnosis((NodeList) CCDAConstants.REL_HOSPITAL_ADMISSION_DIAG_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			admDiag.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		}
		
		return admDiag;
	}
    
    public static ArrayList<CCDAProblemObs> readHospitalAdmissionDiagnosis(NodeList hospitalAdmDiag) throws XPathExpressionException 
    {
    	ArrayList<CCDAProblemObs> encDiagList = new ArrayList<CCDAProblemObs>();

		for (int i = 0; i < hospitalAdmDiag.getLength(); i++) {
			
			log.info("Found Hospital Admission Diagnosis");
			Element hosAdmDiag = (Element) hospitalAdmDiag.item(i);
			
			NodeList problemObservationNodeList = (NodeList) CCDAConstants.REL_ENTRY_RELSHIP_OBS_EXP.
					evaluate(hosAdmDiag, XPathConstants.NODESET);

			log.info("Read Problem Observations ");
			encDiagList.addAll(readProblemObservation(problemObservationNodeList));
			
		}
		
		log.info(" Size of Admission Diagnosis Problem Observations : " + encDiagList.size());
		return encDiagList;
    }

    public static CCDADischargeDiagnosis retrieveDischargeDiagnosisDetails(Document doc) throws XPathExpressionException
	{
		Element sectionElement = (Element) CCDAConstants.DISCHARGE_DIAG_EXP.evaluate(doc, XPathConstants.NODE);
		CCDADischargeDiagnosis dischargeDiag = null;
		
		if(sectionElement != null)
		{
			log.info(" Adding Discharge Diagnosis ");
			dischargeDiag = new CCDADischargeDiagnosis();
			
			//Get Template Ids
			dischargeDiag.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			// Get Section Code
			dischargeDiag.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			// Get Entries
			dischargeDiag.setDiagnosis(readHospitalDischargeDiagnosis((NodeList) CCDAConstants.REL_HOSPITAL_DISCHARGE_DIAG_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			dischargeDiag.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		}
		
		return dischargeDiag;
	}
    
    public static ArrayList<CCDAProblemObs> readHospitalDischargeDiagnosis(NodeList hospitalDischargeDiag) throws XPathExpressionException 
    {
    	ArrayList<CCDAProblemObs> encDiagList = new ArrayList<CCDAProblemObs>();

		for (int i = 0; i < hospitalDischargeDiag.getLength(); i++) {
			
			log.info("Found Hospital Discharge Diagnosis");
			Element hosAdmDiag = (Element) hospitalDischargeDiag.item(i);
			
			NodeList problemObservationNodeList = (NodeList) CCDAConstants.REL_ENTRY_RELSHIP_OBS_EXP.
					evaluate(hosAdmDiag, XPathConstants.NODESET);

			log.info("Read Problem Observations ");
			encDiagList.addAll(readProblemObservation(problemObservationNodeList));
			
		}
		
		log.info(" Size of Discharge Diagnosis Problem Observations : " + encDiagList.size());
		return encDiagList;
    }	
	
	public static ArrayList<CCDAServiceDeliveryLoc> readServiceDeliveryLocators(NodeList serviceDeliveryLocNodeList) throws XPathExpressionException
	{
		ArrayList<CCDAServiceDeliveryLoc> serviceDeliveryLocsList = null;
		if(serviceDeliveryLocNodeList.getLength() > 0)
		{
			serviceDeliveryLocsList = new ArrayList<>();
		}
		CCDAServiceDeliveryLoc serviceDeliveryLoc;
		for (int i = 0; i < serviceDeliveryLocNodeList.getLength(); i++) {
			
			
			log.info(" Adding Service Delivery  Location");
			serviceDeliveryLoc = new CCDAServiceDeliveryLoc();
			
			Element serviceDeliveryLocElement = (Element) serviceDeliveryLocNodeList.item(i);
			serviceDeliveryLoc.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
											evaluate(serviceDeliveryLocElement, XPathConstants.NODESET)));
			
			serviceDeliveryLoc.setLocationCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
											evaluate(serviceDeliveryLocElement, XPathConstants.NODE)));
			
			serviceDeliveryLoc.setName(ParserUtilities.readTextContext((Element) CCDAConstants.REL_PLAY_ENTITY_NAME_EXP.
					evaluate(serviceDeliveryLocElement, XPathConstants.NODE)));
			
			serviceDeliveryLoc.setTelecom(ParserUtilities.readTelecomList((NodeList) CCDAConstants.REL_TELECOM_EXP.
											evaluate(serviceDeliveryLocElement, XPathConstants.NODESET)));
			
			serviceDeliveryLoc.setAddress(ParserUtilities.readAddressList((NodeList) CCDAConstants.REL_ADDR_EXP.
											evaluate(serviceDeliveryLocElement, XPathConstants.NODESET)));
			
			serviceDeliveryLocsList.add(serviceDeliveryLoc);
		}
		
		return serviceDeliveryLocsList;
	}

}
