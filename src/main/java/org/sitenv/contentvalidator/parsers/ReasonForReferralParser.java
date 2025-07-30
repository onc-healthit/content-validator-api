package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.CCDADisabilityStatusObservation;
import org.sitenv.contentvalidator.model.CCDAFunctionalStatus;
import org.sitenv.contentvalidator.model.CCDAFunctionalStatusObservation;
import org.sitenv.contentvalidator.model.CCDAIndication;
import org.sitenv.contentvalidator.model.CCDAPatientReferralAct;
import org.sitenv.contentvalidator.model.CCDAReasonForReferral;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ReasonForReferralParser {
	
	private static Logger log = LoggerFactory.getLogger(ReasonForReferralParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022, boolean svap2023)
			throws XPathExpressionException {
    	log.info(" *** Parsing Reason For Referral *** ");
    	model.setReferrals(retrieveReferrals(doc));	    	
	}

	private static CCDAReasonForReferral retrieveReferrals(Document doc) throws XPathExpressionException {
		
		CCDAReasonForReferral referral = null;
		Element sectionElement = (Element) CCDAConstants.REASON_FOR_REFERRAL_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		if(sectionElement != null)
		{
			log.info("Adding Reason For Referral ");
			referral = new CCDAReasonForReferral();
			referral.setSectionTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(sectionElement, XPathConstants.NODESET)));
			
			referral.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			referral.setSectionText(ParserUtilities.readTextContext((Element) CCDAConstants.REL_TEXT_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			NodeList patientReferralActList = (NodeList) CCDAConstants.REL_PATIENT_REFERRAL_ACT_EXPRESSION.
				evaluate(sectionElement, XPathConstants.NODESET);			
		
			referral.setReferralActs(readPatientReferrals(patientReferralActList));
		
		}
		return referral;
	}

	private static ArrayList<CCDAPatientReferralAct> readPatientReferrals(NodeList patientReferralActList) throws XPathExpressionException {
		
			ArrayList<CCDAPatientReferralAct> patientReferralActs = null;
			
			if(!ParserUtilities.isNodeListEmpty(patientReferralActList))
			{
				patientReferralActs = new ArrayList<>();
			}
			
			CCDAPatientReferralAct patientReferralAct;
			
			for (int i = 0; i < patientReferralActList.getLength(); i++) {
				
				log.info("Adding CCDA Functional Status Observation");
				patientReferralAct = new CCDAPatientReferralAct();
				
				Element patientReferralActElem = (Element) patientReferralActList.item(i);
				
				patientReferralAct.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
															evaluate(patientReferralActElem, XPathConstants.NODESET)));
				
				patientReferralAct.setReferralCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
						evaluate(patientReferralActElem, XPathConstants.NODE)));
				
				patientReferralAct.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
						evaluate(patientReferralActElem, XPathConstants.NODE)));
				
				patientReferralAct.setEffectiveTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
						evaluate(patientReferralActElem, XPathConstants.NODE)));
				
				patientReferralAct.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
						evaluate(patientReferralActElem, XPathConstants.NODE)));
				
				NodeList indicationList = (NodeList) CCDAConstants.REL_INDICATION_EXPRESSION.
						evaluate(patientReferralActElem, XPathConstants.NODESET);
				
				patientReferralAct.setIndications(ParserUtilities.readIndications(indicationList));
				
				patientReferralActs.add(patientReferralAct);
			}
			
			
			return patientReferralActs;
	}

}
