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

public class ProcedureParser {
	
	private static Logger log = LoggerFactory.getLogger(ProcedureParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022, boolean svap2023)
			throws XPathExpressionException {
	    	log.info(" *** Parsing Procedures *** ");
	    	model.setProcedure(retrieveProcedureDetails(doc));	
	}
	
	public static CCDAProcedure retrieveProcedureDetails(Document doc) throws XPathExpressionException
	{
		CCDAProcedure procedures = null;
		Element sectionElement = (Element) CCDAConstants.PROCEDURE_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		if(sectionElement !=null)
		{
			log.info("Adding Procedures");
			procedures = new CCDAProcedure();
			procedures.setSectionTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			procedures.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			log.info("Adding Procedures Activity Procedures ");
			// Account for Procedures
			procedures.setProcActsProcs(readProcedures((NodeList) CCDAConstants.REL_PROC_ACT_PROC_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			log.info("Adding Procedure Activity Acts ");
			// Account for Acts
			procedures.addProcActsProcs(readProcedures((NodeList) CCDAConstants.REL_PROC_ACT_ACT_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			procedures.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			// Add Notes Activity if present in Results entry
			procedures.setNotesActivity(ParserUtilities.readNotesActivity((NodeList) CCDAConstants.REL_NOTES_ACTIVITY_EXPRESSION.
								evaluate(sectionElement, XPathConstants.NODESET), null));
			
		}
		return procedures;
	}
	
	public static ArrayList<CCDAProcActProc> readProcedures(NodeList proceduresNodeList ) throws XPathExpressionException
	{
		ArrayList<CCDAProcActProc> proceduresList = null;
		
		if(!ParserUtilities.isNodeListEmpty(proceduresNodeList))
		{
			log.info(" Procedure Entry nodes exist ");
			proceduresList = new ArrayList<>();
		}
		
		CCDAProcActProc procedure;
		for (int i = 0; i < proceduresNodeList.getLength(); i++) {
			
			log.info("Adding Proc Act Proc ");
			procedure = new CCDAProcActProc();
			Element procedureElement = (Element) proceduresNodeList.item(i);
			
			procedure.setSectionTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
										evaluate(procedureElement, XPathConstants.NODESET)));
			
			procedure.setProcCode(ParserUtilities.readCodeWithTranslation((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(procedureElement, XPathConstants.NODE)));
			
			procedure.setProcStatus(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(procedureElement, XPathConstants.NODE)));
			
			procedure.setPerformanceTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(procedureElement, XPathConstants.NODE)));
			
			procedure.setTargetSiteCode(ParserUtilities.readCode((Element) CCDAConstants.REL_TARGET_SITE_CODE_EXP.
					evaluate(procedureElement, XPathConstants.NODE)));
			
			NodeList performerNodeList = (NodeList) CCDAConstants.REL_PERF_ENTITY_EXP.
						evaluate(procedureElement, XPathConstants.NODESET);
			
			procedure.setPerformer(readPerformerList(performerNodeList));
			
			NodeList deviceNodeList = (NodeList) CCDAConstants.REL_PROCEDURE_UDI_EXPRESSION.
						evaluate(procedureElement, XPathConstants.NODESET);
			
			procedure.setPatientUDI(readUDI(deviceNodeList));
			
			NodeList serviceDeliveryNodeList = (NodeList) CCDAConstants.REL_PROCEDURE_SDL_EXPRESSION.
						evaluate(procedureElement, XPathConstants.NODESET);
			
			// Add Notes Activity if present in Procedures Procedure Activity Procedure entryRelationship
			procedure.setNotesActivity(
					ParserUtilities.readNotesActivity((NodeList) CCDAConstants.REL_ENTRY_REL_NOTES_ACTIVITY_EXPRESSION
							.evaluate(procedureElement, XPathConstants.NODESET), null));
			
			procedure.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(procedureElement, XPathConstants.NODE)));
			
			procedure.setSdLocs(readServiceDeliveryLocators(serviceDeliveryNodeList));
			
			procedure.setAssessmentScaleObservations(ParserUtilities.retrieveAssessmentScaleObservations((NodeList) CCDAConstants.REL_ASSESSMENT_SCALE_OBS_EXP.
					evaluate(procedureElement, XPathConstants.NODESET)));
			
			proceduresList.add(procedure);
		}
		return proceduresList;
	}
	
	public static ArrayList<CCDAAssignedEntity> readPerformerList(NodeList performerEntityNodeList) throws XPathExpressionException
	{
		ArrayList<CCDAAssignedEntity> assignedEntityList = null;
		if(!ParserUtilities.isNodeListEmpty(performerEntityNodeList))
		{
			assignedEntityList = new ArrayList<>();
		}
		CCDAAssignedEntity assignedEntity;
		
		for (int i = 0; i < performerEntityNodeList.getLength(); i++) {
			
			Element performerEntityElement = (Element) performerEntityNodeList.item(i);
			assignedEntity = new CCDAAssignedEntity();
			
			if(performerEntityElement != null)
			{
				log.info("Adding Performer");
				assignedEntity.setAddresses(ParserUtilities.readAddressList((NodeList) CCDAConstants.REL_ADDR_EXP.
													evaluate(performerEntityElement, XPathConstants.NODESET)));
				
				assignedEntity.setTelecom(ParserUtilities.readTelecomList((NodeList) CCDAConstants.REL_TELECOM_EXP.
													evaluate(performerEntityElement, XPathConstants.NODESET)));
					
				Element represntOrgElement = (Element) CCDAConstants.REL_REP_ORG_EXP.
													evaluate(performerEntityElement, XPathConstants.NODE);
				if(represntOrgElement != null)
				{
					CCDAOrganization representedOrg = new  CCDAOrganization();
						
					representedOrg.setAddress(ParserUtilities.readAddressList((NodeList) CCDAConstants.REL_ADDR_EXP.
								evaluate(represntOrgElement, XPathConstants.NODESET)));
						
					representedOrg.setTelecom(ParserUtilities.readTelecomList((NodeList) CCDAConstants.REL_TELECOM_EXP.
								evaluate(represntOrgElement, XPathConstants.NODESET)));
						
					representedOrg.setNames( ParserUtilities.readTextContentList((NodeList) CCDAConstants.REL_NAME_EXP.
								evaluate(represntOrgElement, XPathConstants.NODESET)));
						
					assignedEntity.setOrganization(representedOrg);
				}
			}
			
			assignedEntityList.add(assignedEntity);
		}
		
		return assignedEntityList;
		
	}
	
	public static ArrayList<CCDAUDI> readUDI(NodeList deviceNodeList) throws XPathExpressionException
	{
		ArrayList<CCDAUDI> deviceList =  null;
		if(!ParserUtilities.isNodeListEmpty(deviceNodeList))
		{
			deviceList = new ArrayList<>();
		}
		CCDAUDI device;
		for (int i = 0; i < deviceNodeList.getLength(); i++) {
			
			log.info("Adding UDIs");
			device = new CCDAUDI();
			
			Element deviceElement = (Element) deviceNodeList.item(i);
			device.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
											evaluate(deviceElement, XPathConstants.NODESET)));
			
			device.setUDIValue(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_ID_EXP.
											evaluate(deviceElement, XPathConstants.NODESET)));
			device.setDeviceCode(ParserUtilities.readCode((Element) CCDAConstants.REL_PLAYING_DEV_CODE_EXP.
					evaluate(deviceElement, XPathConstants.NODE)));
			
			device.setScopingEntityId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_SCOPING_ENTITY_ID_EXP.
					evaluate(deviceElement, XPathConstants.NODESET)));
			
			device.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(deviceElement, XPathConstants.NODE)));
			
			deviceList.add(device);
			
		}
		
		return deviceList;
		
	}
	
	public static ArrayList<CCDAServiceDeliveryLoc> readServiceDeliveryLocators(NodeList serviceDeliveryLocNodeList) throws XPathExpressionException
	{
		ArrayList<CCDAServiceDeliveryLoc> serviceDeliveryLocsList = null;
		if(!ParserUtilities.isNodeListEmpty(serviceDeliveryLocNodeList))
		{
			serviceDeliveryLocsList = new ArrayList<>();
		}
		CCDAServiceDeliveryLoc serviceDeliveryLoc;
		for (int i = 0; i < serviceDeliveryLocNodeList.getLength(); i++) {
			
			serviceDeliveryLoc = new CCDAServiceDeliveryLoc();
			
			Element serviceDeliveryLocElement = (Element) serviceDeliveryLocNodeList.item(i);
			serviceDeliveryLoc.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
											evaluate(serviceDeliveryLocElement, XPathConstants.NODESET)));
			
			serviceDeliveryLoc.setLocationCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(serviceDeliveryLocElement, XPathConstants.NODE)));
			
			serviceDeliveryLoc.setName(ParserUtilities.readCode((Element) CCDAConstants.REL_PLAY_ENTITY_NAME_EXP.
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
