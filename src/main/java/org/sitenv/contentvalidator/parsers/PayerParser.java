package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.CCDACoverageActivity;
import org.sitenv.contentvalidator.model.CCDAII;
import org.sitenv.contentvalidator.model.CCDAParticipant;
import org.sitenv.contentvalidator.model.CCDAPayers;
import org.sitenv.contentvalidator.model.CCDAPerformer;
import org.sitenv.contentvalidator.model.CCDAPolicyActivity;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PayerParser {
	
private static Logger log = LoggerFactory.getLogger(PayerParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022, boolean svap2023)
			throws XPathExpressionException {
    	
    	log.info(" *** Parsing Payers Section *** ");
    	model.setPayers(retrievePayersSectionDetails(doc));	
	}
	
	public static CCDAPayers retrievePayersSectionDetails(Document doc) throws XPathExpressionException
	{
		CCDAPayers payers = null;
		Element sectionElement = (Element) CCDAConstants.PAYERS_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		
		if(sectionElement != null)
		{
			log.info("Adding Payers ");
			payers = new CCDAPayers();
			payers.setSectionTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
							evaluate(sectionElement, XPathConstants.NODESET)));
			
			payers.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			payers.setCoverageActivities(readCoverageActivities((NodeList) CCDAConstants.REL_ACT_ENTRY_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			payers.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		}
		return payers;
	}
	
	public static ArrayList<CCDACoverageActivity> readCoverageActivities(NodeList coverageNodeList) throws XPathExpressionException
	{
		ArrayList<CCDACoverageActivity> coverageActivities = new ArrayList<>();;
		CCDACoverageActivity coverageActivity;
		
		for (int i = 0; i < coverageNodeList.getLength(); i++) {
			
			log.info("Adding Coverage Activity ");
			coverageActivity = new CCDACoverageActivity();
			Element coverageElement = (Element) coverageNodeList.item(i);
			coverageActivity.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
																evaluate(coverageElement, XPathConstants.NODESET)));
			
			coverageActivity.setCoverageCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(coverageElement, XPathConstants.NODE)));
			
			coverageActivity.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(coverageElement, XPathConstants.NODE)));
			
			coverageActivity.setCoverageEffectiveTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(coverageElement, XPathConstants.NODE)));
			
			NodeList policyActivityList = (NodeList) CCDAConstants.REL_ENTRY_RELSHIP_OBS_EXP.
								evaluate(coverageElement, XPathConstants.NODESET);
			
			coverageActivity.setPolicyActivities(readPolicyActivities((NodeList) CCDAConstants.REL_ENTRY_RELSHIP_ACT_EXP.
					evaluate(coverageElement, XPathConstants.NODESET)));
			
			coverageActivity.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(coverageElement, XPathConstants.NODE)));
			
			coverageActivities.add(coverageActivity);
		}
		return coverageActivities;
	}
	
	
	public static ArrayList<CCDAPolicyActivity> readPolicyActivities(NodeList policyActivities) throws XPathExpressionException
	{
		ArrayList<CCDAPolicyActivity> policyActivityList = new ArrayList<>();
		CCDAPolicyActivity policyActivity;
		
		for (int i = 0; i < policyActivities.getLength(); i++) {
			
			log.info("Adding Allergy Observation ");
			policyActivity = new CCDAPolicyActivity();
			Element policyActivityElement = (Element) policyActivities.item(i);
			policyActivity.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
												evaluate(policyActivityElement, XPathConstants.NODESET)));
			
			ArrayList<CCDAII> idlist = ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_ID_EXP.
					evaluate(policyActivityElement, XPathConstants.NODESET));
			
			if(idlist != null && idlist.size() > 0) {
				policyActivity.setGroupIdentifier(idlist.get(0));
			}
			
			policyActivity.setCoverageType(ParserUtilities.readCodeWithTranslation((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(policyActivityElement, XPathConstants.NODE)));
			
			policyActivity.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(policyActivityElement, XPathConstants.NODE)));
			
			ArrayList<CCDAPerformer> performers = ParserUtilities.readPerformers(policyActivityElement);
			
			ArrayList<CCDAParticipant> participants = ParserUtilities.readParticipant(policyActivityElement);
			
			if(performers != null) {
				
				for(CCDAPerformer perf : performers) {
					
					if( (perf.getTypeCode() != null && perf.getTypeCode() != null 
							&& perf.getTypeCode().contentEquals("PRF")) && 
							(perf.getAssignedEntity() != null && 
							perf.getAssignedEntity().getAssignedEntityCode() != null && 
							perf.getAssignedEntity().getAssignedEntityCode().getCode() != null &&
							!perf.getAssignedEntity().getAssignedEntityCode().getCode().contentEquals("GUAR")) ){
						
						// set as the payer
						policyActivity.setPayer(perf);
						
					}
					else if(perf.getAssignedEntity() != null && 
							perf.getAssignedEntity().getAssignedEntityCode() != null && 
							perf.getAssignedEntity().getAssignedEntityCode().getCode() != null &&
							!perf.getAssignedEntity().getAssignedEntityCode().getCode().contentEquals("GUAR")) {
						
						// set as guarantor
						policyActivity.setGuarantor(perf);
					}
				}
			}
			
			if(participants != null) {
				
				for(CCDAParticipant part : participants) {
					
					if(part.getParticipantTypeCode() != null && 
							part.getParticipantTypeCode().contentEquals("COV")) {
						
						// Set coverage participant
						policyActivity.setCoverageParticipant(part);
					}
					else if(part.getParticipantTypeCode() != null && 
							part.getParticipantTypeCode().contentEquals("HLD") ) {
						//set Holder
						policyActivity.setHolderOrSubscriberParticipant(part);
					}
					
				}
			}
			
			policyActivityList.add(policyActivity);
		}
	    return policyActivityList;
	}

}
