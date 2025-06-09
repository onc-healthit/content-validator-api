package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.CCDABasicOccupation;
import org.sitenv.contentvalidator.model.CCDABasicOccupationIndustry;
import org.sitenv.contentvalidator.model.CCDABirthSexObs;
import org.sitenv.contentvalidator.model.CCDAGenderIdentityObs;
import org.sitenv.contentvalidator.model.CCDAPregnancyObservation;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.model.CCDASexObservation;
import org.sitenv.contentvalidator.model.CCDASexualOrientation;
import org.sitenv.contentvalidator.model.CCDASmokingStatus;
import org.sitenv.contentvalidator.model.CCDASocialHistory;
import org.sitenv.contentvalidator.model.CCDASocialHistoryObs;
import org.sitenv.contentvalidator.model.CCDATobaccoUse;
import org.sitenv.contentvalidator.model.CCDATribalAffiliationObservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SocialHistoryParser {
	
	private static Logger log = LoggerFactory.getLogger(SocialHistoryParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022, boolean svap2023)
			throws XPathExpressionException {
    	log.info(" *** Parsing Social History *** ");
    	model.setSmokingStatus(retrieveSocialHistoryDetails(doc));	
	}
		
	public static CCDASocialHistory retrieveSocialHistoryDetails(Document doc) throws XPathExpressionException
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
			NodeList socialHistoryObsList = (NodeList)CCDAConstants.REL_SOCIAL_HISTORY_OBS_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			socialHistory.setSocialHistoryObservations(retrieveSocialHistoryObs(socialHistoryObsList));

			NodeList assessmentScaleObsList = (NodeList)CCDAConstants.REL_ASSESSMENT_SCALE_ENTRY_OBS_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			socialHistory.setAssessmentScaleObservations(ParserUtilities.retrieveAssessmentScaleObservations(assessmentScaleObsList));

			
			// Add Tribal Affiliation
			NodeList tribalAffList = (NodeList)CCDAConstants.REL_TRIBAL_AFFILIATION_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			socialHistory.setTribalAffiliations(retrieveTribalAffiliations(tribalAffList));
			
			// Add Pregnancy Observation
			NodeList pregnancyObsList = (NodeList)CCDAConstants.REL_PREGNANCY_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			socialHistory.setPregnancyObservations(retrievePregnancyObserations(pregnancyObsList));
			
			// Add Occupation Observation
			NodeList occupationObsList = (NodeList)CCDAConstants.REL_OCCUPATION_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			socialHistory.setOccupation(retrieveOccupationObservations(occupationObsList));
			
			// Add Sex Observation
			NodeList sexObsList = (NodeList)CCDAConstants.REL_SEX_EXP.
								evaluate(sectionElement, XPathConstants.NODESET);
			socialHistory.setSexObservations(retrieveSexObserations(sexObsList));
			
			// Grab all assessments
			NodeList assessmentScaleObservationList = (NodeList) CCDAConstants.REL_ENTRY_ASSESSMENT_SCALE_OBS_EXP.
					evaluate(sectionElement, XPathConstants.NODESET);
			
			socialHistory.setAssessments(ParserUtilities.retrieveAssessmentScaleObservations(assessmentScaleObservationList));	

		}
		
		return socialHistory;
	}
	
	private static ArrayList<CCDASexObservation> retrieveSexObserations(NodeList sexObsList) throws XPathExpressionException {
		
		ArrayList<CCDASexObservation> sexObservations = null;
		
		if(!ParserUtilities.isNodeListEmpty(sexObsList))
		{
			sexObservations = new ArrayList<>();
		}
		
		CCDASexObservation sexObs;
		
		for (int i = 0; i < sexObsList.getLength(); i++) {
			
			log.info("Adding CCDA Sex Observation");
			sexObs = new CCDASexObservation();
			
			Element occupationElement = (Element) sexObsList.item(i);
			
			sexObs.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(occupationElement, XPathConstants.NODESET)));
			
			sexObs.setSexCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(occupationElement, XPathConstants.NODE)));
			
			sexObs.setSexValue(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
					evaluate(occupationElement, XPathConstants.NODE)));
			
			sexObs.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(occupationElement, XPathConstants.NODE)));
			
			sexObservations.add(sexObs);
		}
		
		
		return sexObservations;
	}

	private static ArrayList<CCDABasicOccupation> retrieveOccupationObservations(
			NodeList occupationObsList) throws XPathExpressionException {
		
		ArrayList<CCDABasicOccupation> occupationObservations = null;
		
		if(!ParserUtilities.isNodeListEmpty(occupationObsList))
		{
			occupationObservations = new ArrayList<>();
		}
		
		CCDABasicOccupation occupationObseration;
		
		for (int i = 0; i < occupationObsList.getLength(); i++) {
			
			log.info("Adding CCDA Basic Occupation");
			occupationObseration = new CCDABasicOccupation();
			
			Element occupationElement = (Element) occupationObsList.item(i);
			
			occupationObseration.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(occupationElement, XPathConstants.NODESET)));
			
			occupationObseration.setBasicOccupationCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(occupationElement, XPathConstants.NODE)));
			
			occupationObseration.setBasicOccupationValueCode(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
					evaluate(occupationElement, XPathConstants.NODE)));
			
			occupationObseration.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(occupationElement, XPathConstants.NODE)));
			
			occupationObseration.setIndustry(readOccupationIndustry((Element)CCDAConstants.REL_OCCUPATION_INDUSTRY_EXP.
					evaluate(occupationElement, XPathConstants.NODE)));
			
			occupationObservations.add(occupationObseration);
		}
		
		
		return occupationObservations;
	}
	
	

	private static CCDABasicOccupationIndustry readOccupationIndustry(Element industryElement) throws XPathExpressionException {
		
		if(industryElement != null) {
			
			CCDABasicOccupationIndustry industry = new CCDABasicOccupationIndustry();
			
			industry.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
					evaluate(industryElement, XPathConstants.NODESET)));

			industry.setBasicOccupationIndustryCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
			evaluate(industryElement, XPathConstants.NODE)));
			
			industry.setBasicOccupationIndustryValueCode(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
			evaluate(industryElement, XPathConstants.NODE)));
			
			industry.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
			evaluate(industryElement, XPathConstants.NODE)));
			
			return industry;
		}
		
		return null;
	}

	private static ArrayList<CCDAPregnancyObservation> retrievePregnancyObserations(NodeList pregnancyObsList) throws XPathExpressionException {
		
		ArrayList<CCDAPregnancyObservation> pregnancyObservations = null;
		
		if(!ParserUtilities.isNodeListEmpty(pregnancyObsList))
		{
			pregnancyObservations = new ArrayList<>();
		}
		
		CCDAPregnancyObservation pregnancyObservation;
		
		for (int i = 0; i < pregnancyObsList.getLength(); i++) {
			
			log.info("Adding CCDA Tribal Affiliation");
			pregnancyObservation = new CCDAPregnancyObservation();
			
			Element pregnancyElement = (Element) pregnancyObsList.item(i);
			
			pregnancyObservation.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(pregnancyElement, XPathConstants.NODESET)));
			
			pregnancyObservation.setPregnancyCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(pregnancyElement, XPathConstants.NODE)));
			
			pregnancyObservation.setPregnancyValue(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
					evaluate(pregnancyElement, XPathConstants.NODE)));
			
			pregnancyObservation.setEffTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(pregnancyElement, XPathConstants.NODE)));
			
			pregnancyObservation.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(pregnancyElement, XPathConstants.NODE)));
			
			pregnancyObservations.add(pregnancyObservation);
		}
		
		
		return pregnancyObservations;
		
	}

	private static ArrayList<CCDATribalAffiliationObservation> retrieveTribalAffiliations(NodeList tribalAffList) throws XPathExpressionException {
		
		ArrayList<CCDATribalAffiliationObservation> tribalAffiliations = null;
		
		if(!ParserUtilities.isNodeListEmpty(tribalAffList))
		{
			tribalAffiliations = new ArrayList<>();
		}
		
		CCDATribalAffiliationObservation tribalAffiliation;
		
		for (int i = 0; i < tribalAffList.getLength(); i++) {
			
			log.info("Adding CCDA Tribal Affiliation");
			tribalAffiliation = new CCDATribalAffiliationObservation();
			
			Element tribalAffiliationElement = (Element) tribalAffList.item(i);
			
			tribalAffiliation.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(tribalAffiliationElement, XPathConstants.NODESET)));
			
			tribalAffiliation.setTribalAffiliationCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(tribalAffiliationElement, XPathConstants.NODE)));
			
			tribalAffiliation.setTribalAffiliationValue(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
					evaluate(tribalAffiliationElement, XPathConstants.NODE)));
			
			tribalAffiliation.setObservationTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(tribalAffiliationElement, XPathConstants.NODE)));
			
			tribalAffiliation.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(tribalAffiliationElement, XPathConstants.NODE)));
			
			tribalAffiliations.add(tribalAffiliation);
		}
		
		
		return tribalAffiliations;
		
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
			
			sexOr.setSexualOrientationValue(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
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
			
			log.info("Adding Gender Identity from list {}", genderIdList.getLength());
			genderIdentity = new CCDAGenderIdentityObs();
			
			Element genderIdElement = (Element) genderIdList.item(i);
			
			genderIdentity.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(genderIdElement, XPathConstants.NODESET)));
			
			genderIdentity.setGenderIdentityObsCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(genderIdElement, XPathConstants.NODE)));
			
			genderIdentity.setGenderIdentityValue(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
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
