package org.sitenv.contentvalidator.parsers;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import java.util.ArrayList;

public class MedicationParser {
	
	private static Logger log = Logger.getLogger(MedicationParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate) throws XPathExpressionException {
    	
    	model.setMedication(retrieveMedicationDetails(doc));	
    	model.setDischargeMedication(retrieveDischargeMedicationDetails(doc));
	}
	
	public static CCDAMedication retrieveMedicationDetails(Document doc) throws XPathExpressionException
	{
		CCDAMedication medications = null;
		Element sectionElement = (Element) CCDAConstants.MEDICATION_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		
		if(sectionElement != null)
		{
			log.info("Creating Medication ");
			medications = new CCDAMedication();
			medications.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(sectionElement, XPathConstants.NODESET)));
			
			medications.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			medications.setMedActivities(readMedication((NodeList) CCDAConstants.REL_MED_ENTRY_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			medications.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		}
		return medications;
	}
	
	public static CCDAMedication retrieveAdmissionMedicationDetails(Document doc) throws XPathExpressionException
	{
		CCDAMedication medications = null;
		Element sectionElement = (Element) CCDAConstants.MEDICATION_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		
		if(sectionElement != null)
		{
			log.info("Creating Medication ");
			medications = new CCDAMedication();
			medications.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(sectionElement, XPathConstants.NODESET)));
			
			medications.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			medications.setMedActivities(readMedication((NodeList) CCDAConstants.REL_MED_ENTRY_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			medications.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		}
		return medications;
	}
	
	public static CCDADischargeMedication retrieveDischargeMedicationDetails(Document doc) throws XPathExpressionException
	{
		CCDADischargeMedication medications = null;
		Element sectionElement = (Element) CCDAConstants.DM_MEDICATION_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		
		if(sectionElement != null)
		{
			log.info("Creating Discharge Medication ");
			medications = new CCDADischargeMedication();
			medications.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(sectionElement, XPathConstants.NODESET)));
			
			medications.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			medications.setMedActivities(readDischargeMedication((NodeList) CCDAConstants.DM_ENTRY_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			medications.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		}
		return medications;
	}
	
	public static ArrayList<CCDAMedicationActivity> readMedication(NodeList entryNodeList) throws XPathExpressionException
	{
		ArrayList<CCDAMedicationActivity> medicationList = new ArrayList<>();
		CCDAMedicationActivity medicationActivity;
		
		for (int i = 0; i < entryNodeList.getLength(); i++) {
			
			log.info("Creating Medication Activity ");
			
			medicationActivity = new CCDAMedicationActivity();
			Element entryElement = (Element) entryNodeList.item(i);
			
			medicationActivity.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
									evaluate(entryElement, XPathConstants.NODESET)));
			
			NodeList effectiveTime = (NodeList) CCDAConstants.REL_EFF_TIME_EXP.evaluate(entryElement, XPathConstants.NODESET);
			
			for (int j = 0; j < effectiveTime.getLength(); j++) {
				
				Element effectiveTimeElement = (Element) effectiveTime.item(j);
				if(effectiveTimeElement.getAttribute("xsi:type").equalsIgnoreCase("IVL_TS"))
				{
					medicationActivity.setDuration(readDuration(effectiveTimeElement));
				} else if(effectiveTimeElement.getAttribute("xsi:type").equalsIgnoreCase("PIVL_TS") && 
						  effectiveTimeElement.hasAttribute("institutionSpecified") && 
						  effectiveTimeElement.getAttribute("institutionSpecified").equalsIgnoreCase("true") && 
						  effectiveTimeElement.hasAttribute("operator") && 
						  effectiveTimeElement.getAttribute("operator").equalsIgnoreCase("A") )
				{
					medicationActivity.setFrequency(ParserUtilities.readFrequency(effectiveTimeElement));
				}
			}
			
			medicationActivity.setRouteCode(ParserUtilities.readCode((Element) CCDAConstants.REL_ROUTE_CODE_EXP.
					evaluate(entryElement, XPathConstants.NODE)));
			
			medicationActivity.setDoseQuantity(ParserUtilities.readQuantity((Element) CCDAConstants.REL_DOSE_EXP.
					evaluate(entryElement, XPathConstants.NODE)));
			
			medicationActivity.setRateQuantity(ParserUtilities.readQuantity((Element) CCDAConstants.REL_RATE_EXP.
						evaluate(entryElement, XPathConstants.NODE)));
			
			medicationActivity.setApproachSiteCode(ParserUtilities.readCode((Element) CCDAConstants.REL_APP_SITE_CODE_EXP.
						evaluate(entryElement, XPathConstants.NODE)));
			
			medicationActivity.setAdminUnitCode(ParserUtilities.readCode((Element) CCDAConstants.REL_ADMIN_UNIT_CODE_EXP.
						evaluate(entryElement, XPathConstants.NODE)));
			
			medicationActivity.setConsumable(readMedicationInformation((Element) CCDAConstants.REL_CONSUM_EXP.
					evaluate(entryElement, XPathConstants.NODE)));
			
			medicationActivity.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(entryElement, XPathConstants.NODE)));
			
			medicationList.add(medicationActivity);
		}
		return medicationList;
	}
	
	public static ArrayList<CCDAMedicationActivity> readDischargeMedication(NodeList entryNodeList) throws XPathExpressionException
	{
		ArrayList<CCDAMedicationActivity> medicationList = new ArrayList<>();
		CCDAMedicationActivity medicationActivity;
		
		for (int i = 0; i < entryNodeList.getLength(); i++) {
			
			Element entryElementDM = (Element) entryNodeList.item(i);
			
			Element medAct = (Element) (CCDAConstants.REL_CONSUM_EXP.
					evaluate(entryElementDM, XPathConstants.NODE));
			
			if(medAct != null) {
				
				log.info("Creating Medication Activity ");
				
				medicationActivity = new CCDAMedicationActivity();
				
				medicationActivity.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
										evaluate(entryElementDM, XPathConstants.NODESET)));
				
				NodeList effectiveTime = (NodeList) CCDAConstants.REL_EFF_TIME_EXP.evaluate(entryElementDM, XPathConstants.NODESET);
				
				for (int j = 0; j < effectiveTime.getLength(); j++) {
					
					Element effectiveTimeElement = (Element) effectiveTime.item(j);
					if(effectiveTimeElement.getAttribute("xsi:type").equalsIgnoreCase("IVL_TS"))
					{
						medicationActivity.setDuration(readDuration(effectiveTimeElement));
					} else if(effectiveTimeElement.getAttribute("xsi:type").equalsIgnoreCase("PIVL_TS") && 
							  effectiveTimeElement.hasAttribute("institutionSpecified") && 
							  effectiveTimeElement.getAttribute("institutionSpecified").equalsIgnoreCase("true") && 
							  effectiveTimeElement.hasAttribute("operator") && 
							  effectiveTimeElement.getAttribute("operator").equalsIgnoreCase("A") )
					{
						medicationActivity.setFrequency(ParserUtilities.readFrequency(effectiveTimeElement));
					}
				}
				
				medicationActivity.setRouteCode(ParserUtilities.readCode((Element) CCDAConstants.REL_ROUTE_CODE_EXP.
						evaluate(entryElementDM, XPathConstants.NODE)));
				
				medicationActivity.setDoseQuantity(ParserUtilities.readQuantity((Element) CCDAConstants.REL_DOSE_EXP.
						evaluate(entryElementDM, XPathConstants.NODE)));
				
				medicationActivity.setRateQuantity(ParserUtilities.readQuantity((Element) CCDAConstants.REL_RATE_EXP.
							evaluate(entryElementDM, XPathConstants.NODE)));
				
				medicationActivity.setApproachSiteCode(ParserUtilities.readCode((Element) CCDAConstants.REL_APP_SITE_CODE_EXP.
							evaluate(entryElementDM, XPathConstants.NODE)));
				
				medicationActivity.setAdminUnitCode(ParserUtilities.readCode((Element) CCDAConstants.REL_ADMIN_UNIT_CODE_EXP.
							evaluate(entryElementDM, XPathConstants.NODE)));
				
				medicationActivity.setConsumable(readMedicationInformation((Element) CCDAConstants.REL_CONSUM_EXP.
						evaluate(entryElementDM, XPathConstants.NODE)));
				
				medicationActivity.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
						evaluate(entryElementDM, XPathConstants.NODE)));
				
				medicationList.add(medicationActivity);	
			}
			
			
		} // for
		return medicationList;
	}
	
	public static CCDAEffTime readDuration(Element duration)throws XPathExpressionException
	{
		CCDAEffTime medicationDuration = null; 
				
		if (!ParserUtilities.isEmpty(duration.getAttribute("value")))
		{
			medicationDuration = new CCDAEffTime();
			medicationDuration.setValue(ParserUtilities.readDataElement(duration));
		}else
		{
			medicationDuration = ParserUtilities.readEffectiveTime(duration);
		}
		return medicationDuration;
		
	}
	
	public static CCDAConsumable readMedicationInformation(Element medicationInformationElement) throws XPathExpressionException
	{
		
		CCDAConsumable consumable = null;
		
		if(medicationInformationElement != null)
		{
			consumable = new CCDAConsumable();
			consumable.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
							evaluate(medicationInformationElement, XPathConstants.NODESET)));
			
			consumable.setMedcode(ParserUtilities.readCode((Element) CCDAConstants.REL_MMAT_CODE_EXP.
					evaluate(medicationInformationElement, XPathConstants.NODE)));
			
			consumable.setTranslations(ParserUtilities.readCodeList((NodeList) CCDAConstants.REL_MMAT_CODE_TRANS_EXP.
						evaluate(medicationInformationElement, XPathConstants.NODESET)));
			
			consumable.setManufacturingOrg(ParserUtilities.readTextContext((Element) CCDAConstants.REL_MANU_ORG_NAME_EXP.
						evaluate(medicationInformationElement, XPathConstants.NODE)));
			
			consumable.setLotNumberText(ParserUtilities.readTextContext((Element) CCDAConstants.REL_MMAT_LOT_EXP.
						evaluate(medicationInformationElement, XPathConstants.NODE)));
		}
		
		return consumable;
	}

}
