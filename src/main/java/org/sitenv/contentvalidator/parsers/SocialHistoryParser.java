package org.sitenv.contentvalidator.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;

public class SocialHistoryParser {
	
	private static Logger log = LoggerFactory.getLogger(SocialHistoryParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022)
			throws XPathExpressionException {
    	log.info(" *** Parsing Social History *** ");
    	model.setSmokingStatus(retrieveSmokingStatusDetails(doc));	
	}
	
	public static CCDASocialHistory retrieveSmokingStatusDetails(Document doc) throws XPathExpressionException
	{
		CCDASocialHistory socialHistory = null;
		Element sectionElement = (Element) CCDAConstants.SOCIAL_HISTORY_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		if(sectionElement != null)
		{
			log.info("Adding Social History ");
			socialHistory = new CCDASocialHistory();
			socialHistory.setSectionTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(sectionElement, XPathConstants.NODESET)));
			
			socialHistory.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			NodeList smokingStatusNodeList = (NodeList) CCDAConstants.REL_SMOKING_STATUS_EXP.
				evaluate(sectionElement, XPathConstants.NODESET);
		
			socialHistory.setSmokingStatus(readSmokingStatus(smokingStatusNodeList));
		
			NodeList tobaccoUseNodeList = (NodeList) CCDAConstants.REL_TOBACCO_USE_EXP.
				evaluate(sectionElement, XPathConstants.NODESET);
		
			socialHistory.setTobaccoUse(readTobaccoUse(tobaccoUseNodeList));
			
			NodeList bsList = (NodeList) CCDAConstants.REL_BIRTHSEX_OBS_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			
			socialHistory.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
	
			socialHistory.setBirthSex(readBirthSex(bsList));
			
			// Add Sexual Orientation
			NodeList sexOrList = (NodeList)CCDAConstants.REL_SEX_ORIENTATION_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			socialHistory.setSexualOrientations(retrieveSexualOrientation(sexOrList));
			
			// Add Gender Identity
			NodeList genderIdList = (NodeList)CCDAConstants.REL_GENDER_IDENTITY_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			socialHistory.setGenderIdentities(retrieveGenderIdentity(genderIdList));
			
			// Add Social History Observation 
			NodeList socialHistoryObsList = (NodeList)CCDAConstants.REL_GENDER_IDENTITY_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			socialHistory.setSocialHistoryObservations(retrieveSocialHistoryObs(socialHistoryObsList));
		}
		
		return socialHistory;
	}
	
	public static ArrayList<CCDASocialHistoryObs> retrieveSocialHistoryObs(NodeList socHisList) throws XPathExpressionException {
		
		ArrayList<CCDASocialHistoryObs> socObs = null;
		
		if(!ParserUtilities.isNodeListEmpty(socHisList))
		{
			socObs = new ArrayList<>();
		}
		
		CCDASocialHistoryObs socObservation;
		for (int i = 0; i < socHisList.getLength(); i++) {
			
			log.info("Adding Social History Observation");
			socObservation = new CCDASocialHistoryObs();
			
			Element socObsElement = (Element) socHisList.item(i);
			
			socObservation.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(socObsElement, XPathConstants.NODESET)));
			
			socObservation.setSocialHistoryObsCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(socObsElement, XPathConstants.NODE)));
			
			socObservation.setSocialHistoryObsValue(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_EXP.
					evaluate(socObsElement, XPathConstants.NODE)));
			
			socObservation.setObservationTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(socObsElement, XPathConstants.NODE)));
			
			socObservation.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(socObsElement, XPathConstants.NODE)));
			
			socObservation.setAssessmentScaleObservations(ParserUtilities.retrieveAssessmentScaleObservations((NodeList) CCDAConstants.REL_ASSESSMENT_SCALE_OBS_EXP.
					evaluate(socObsElement, XPathConstants.NODESET)));
			
			socObs.add(socObservation);
		}
		
		
		return socObs;
		
	}
	
	public static ArrayList<CCDASexualOrientation> retrieveSexualOrientation(NodeList sexOrList) throws XPathExpressionException {
		
		ArrayList<CCDASexualOrientation> sexOrs = null;
		
		if(!ParserUtilities.isNodeListEmpty(sexOrList))
		{
			sexOrs = new ArrayList<>();
		}
		
		CCDASexualOrientation sexOr;
		for (int i = 0; i < sexOrList.getLength(); i++) {
			
			log.info("Adding Sexual Orientation");
			sexOr = new CCDASexualOrientation();
			
			Element sexOrElement = (Element) sexOrList.item(i);
			
			sexOr.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(sexOrElement, XPathConstants.NODESET)));
			
			sexOr.setSexualOrientationCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sexOrElement, XPathConstants.NODE)));
			
			sexOr.setSexualOrientationValue(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_EXP.
					evaluate(sexOrElement, XPathConstants.NODE)));
			
			sexOr.setObservationTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(sexOrElement, XPathConstants.NODE)));
			
			sexOr.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sexOrElement, XPathConstants.NODE)));
			
			sexOrs.add(sexOr);
		}
		
		
		return sexOrs;
		
	}
	
	public static ArrayList<CCDAGenderIdentityObs> retrieveGenderIdentity(NodeList genderIdList) throws XPathExpressionException {
		
		ArrayList<CCDAGenderIdentityObs> genderIdentities = null;
		
		if(!ParserUtilities.isNodeListEmpty(genderIdList))
		{
			genderIdentities = new ArrayList<>();
		}
		
		CCDAGenderIdentityObs genderIdentity;
		for (int i = 0; i < genderIdList.getLength(); i++) {
			
			log.info("Adding Gender Identity");
			genderIdentity = new CCDAGenderIdentityObs();
			
			Element genderIdElement = (Element) genderIdList.item(i);
			
			genderIdentity.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(genderIdElement, XPathConstants.NODESET)));
			
			genderIdentity.setGenderIdentityObsCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(genderIdElement, XPathConstants.NODE)));
			
			genderIdentity.setGenderIdentityValue(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_EXP.
					evaluate(genderIdElement, XPathConstants.NODE)));
			
			genderIdentity.setObservationTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(genderIdElement, XPathConstants.NODE)));
			
			genderIdentity.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(genderIdElement, XPathConstants.NODE)));
			
			genderIdentities.add(genderIdentity);
		}
		
		
		return genderIdentities;
		
	}
	
	public static CCDABirthSexObs readBirthSex(NodeList bsList) throws XPathExpressionException
	{
		CCDABirthSexObs birthSex  = null;
		for (int i = 0; i < bsList.getLength(); i++) {
			
			log.info("Adding Birth Sex ");
			birthSex = new CCDABirthSexObs();
			
			Element bsElement = (Element) bsList.item(i);
			
			birthSex.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(bsElement, XPathConstants.NODESET)));
			
			birthSex.setBirthSexObsCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(bsElement, XPathConstants.NODE)));
			
			birthSex.setSexCode(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_EXP.
					evaluate(bsElement, XPathConstants.NODE)));
			
			birthSex.setObservationTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(bsElement, XPathConstants.NODE)));
			
		}
		
		return birthSex;

	}
	
	public static ArrayList<CCDASmokingStatus> readSmokingStatus(NodeList smokingStatusNodeList) throws XPathExpressionException
	{
		ArrayList<CCDASmokingStatus> smokingStatusList = null;
		if(!ParserUtilities.isNodeListEmpty(smokingStatusNodeList))
		{
			smokingStatusList = new ArrayList<>();
		}
		CCDASmokingStatus smokingStatus;
		for (int i = 0; i < smokingStatusNodeList.getLength(); i++) {
			
			log.info("Adding Smoking Status");
			smokingStatus = new CCDASmokingStatus();
			
			Element smokingStatusElement = (Element) smokingStatusNodeList.item(i);
			
			smokingStatus.setSmokingStatusTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(smokingStatusElement, XPathConstants.NODESET)));
			
			smokingStatus.setSmokingStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_EXP.
					evaluate(smokingStatusElement, XPathConstants.NODE)));
			
			smokingStatus.setObservationTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(smokingStatusElement, XPathConstants.NODE)));
			
			smokingStatus.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(smokingStatusElement, XPathConstants.NODE)));
			
			smokingStatusList.add(smokingStatus);
		}
		return smokingStatusList;
	}
	
	public static ArrayList<CCDATobaccoUse> readTobaccoUse(NodeList tobaccoUseNodeList) throws XPathExpressionException
	{
		ArrayList<CCDATobaccoUse> tobaccoUseList = null;
		if(!ParserUtilities.isNodeListEmpty(tobaccoUseNodeList))
		{
			tobaccoUseList = new ArrayList<>();
		}
		CCDATobaccoUse tobaccoUse;
		for (int i = 0; i < tobaccoUseNodeList.getLength(); i++) {
			
			log.info("Adding Tobacco Use");
			tobaccoUse = new CCDATobaccoUse();
			
			Element tobaccoUseElement = (Element) tobaccoUseNodeList.item(i);
			
			tobaccoUse.setTobaccoUseTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(tobaccoUseElement, XPathConstants.NODESET)));
			
			tobaccoUse.setTobaccoUseCode(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_EXP.
					evaluate(tobaccoUseElement, XPathConstants.NODE)));
			
			tobaccoUse.setTobaccoUseTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(tobaccoUseElement, XPathConstants.NODE)));
			
			tobaccoUse.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(tobaccoUseElement, XPathConstants.NODE)));
			
			tobaccoUseList.add(tobaccoUse);
		}
		return tobaccoUseList;
	}

}
