package org.sitenv.contentvalidator.parsers; 

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.model.CCDAMedicalEquipment;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.model.CCDAUDI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MedicalEquipmentParser {

	private static Logger log = LoggerFactory.getLogger(MedicalEquipmentParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022, boolean svap2023)
			throws XPathExpressionException {	    	
	    	log.info(" *** Parsing Medical Equipments *** ");
	    	model.setMedEquipments(retrieveMedicalEquipments(doc));	
	}
	
	public static CCDAMedicalEquipment retrieveMedicalEquipments(Document doc) throws XPathExpressionException
	{
		CCDAMedicalEquipment meq = null;
		Element sectionElement = (Element) CCDAConstants.MEDICAL_EQUIPMENT_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		if(sectionElement !=null)
		{
			log.info("Adding Medical Equipment ");
			meq = new CCDAMedicalEquipment();
			meq.setSectionTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			meq.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			log.info(" Adding UDIs from Organizers ");
			// Account for UDIs
			meq.addUDIs(readUDIsFromOrganizer((NodeList)CCDAConstants.MEDICAL_EQUIPMENT_ORG_EXPRESSION.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			log.info("Adding UDIs from Procedures Activity Procedures ");
			meq.addUDIs(readUDIsFromProcedures((NodeList)CCDAConstants.REL_PROC_ACT_PROC_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			meq.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
		}
		
		return meq;
	}
	
	public static ArrayList<CCDAUDI> readUDIsFromOrganizer(NodeList orgNodeList) throws XPathExpressionException
	{
		ArrayList<CCDAUDI> udisList = null;
		
		for (int i = 0; i < orgNodeList.getLength(); i++) {
			
			log.info(" Reading UDIs from Organizer ");
			Element orgElement = (Element) orgNodeList.item(i);
			
			readUDIsFromProcedures((NodeList)CCDAConstants.MEDICAL_EQUIPMENT_ORG_PAP_EXPRESSION.
					evaluate(orgElement, XPathConstants.NODESET));
		}
		
		return udisList;
	}
	
	public static ArrayList<CCDAUDI> readUDIsFromProcedures(NodeList proceduresNodeList ) throws XPathExpressionException
	{
		ArrayList<CCDAUDI> udis = new ArrayList<CCDAUDI>();
		for (int i = 0; i < proceduresNodeList.getLength(); i++) {
			
			log.info("Adding UDIs from procs ");
			
			Element procedureElement = (Element) proceduresNodeList.item(i);
			
			if(procedureElement != null) {
				
				log.info(" Procedure Not null ");
				
				NodeList deviceNodeList = (NodeList) CCDAConstants.REL_PROCEDURE_UDI_EXPRESSION.
						evaluate(procedureElement, XPathConstants.NODESET);
			
				ArrayList<CCDAUDI> devices = readUDI(deviceNodeList);
				if(devices != null) 
					udis.addAll(devices);	
			}
			
			
		}
		
		return udis;
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

}
