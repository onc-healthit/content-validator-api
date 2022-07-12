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

public class LabResultParser {
	
	private static Logger log = LoggerFactory.getLogger(LabResultParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate) throws XPathExpressionException {
    	
    	log.info(" *** Parsing Lab Results *** ");
    	model.setLabResults(retrieveLabResults(doc));	
	}
	
	public static CCDALabResult retrieveLabResults(Document doc) throws XPathExpressionException
	{
		CCDALabResult labResults = null;
		Element sectionElement = (Element) CCDAConstants.RESULTS_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		
		if(sectionElement != null)
		{
			log.info("Adding Lab Result ");
			labResults = new CCDALabResult();
			labResults.setResultSectionTempalteIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
													evaluate(sectionElement, XPathConstants.NODESET)));
			
			labResults.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
						evaluate(sectionElement, XPathConstants.NODE)));
			
			labResults.setResultOrg(readResultOrganizer((NodeList) CCDAConstants.REL_LAB_RESULT_ORG_EXPRESSION.
									evaluate(sectionElement, XPathConstants.NODESET)));
			
			labResults.setIsLabTestInsteadOfResult(false);
			
			// Add Notes Activity if present in Results entry
			labResults.setNotesActivity(ParserUtilities.readNotesActivity((NodeList) CCDAConstants.REL_NOTES_ACTIVITY_EXPRESSION.
					evaluate(sectionElement, XPathConstants.NODESET), null));			
			
			labResults.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		}
		return labResults;
	}
	
	
	public static ArrayList<CCDALabResultOrg> readResultOrganizer(NodeList resultOrganizerNodeList) throws XPathExpressionException
	{
		ArrayList<CCDALabResultOrg> labResultOrgList = new ArrayList<>();
		CCDALabResultOrg labResultOrg;
		
		for (int i = 0; i < resultOrganizerNodeList.getLength(); i++) {
			
			log.info("Adding Organizer ");
			labResultOrg = new CCDALabResultOrg();
			
			Element labResultOrgElement = (Element) resultOrganizerNodeList.item(i);
			
			labResultOrg.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
										evaluate(labResultOrgElement, XPathConstants.NODESET)));
			
			labResultOrg.setOrgCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(labResultOrgElement, XPathConstants.NODE)));
			
			labResultOrg.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(labResultOrgElement, XPathConstants.NODE)));
			
			labResultOrg.setEffTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(labResultOrgElement, XPathConstants.NODE)));
			
			labResultOrg.setResultObs(readResultObservation((NodeList) CCDAConstants.REL_COMP_OBS_EXP.
					evaluate(labResultOrgElement, XPathConstants.NODESET)));
			
			// Add Notes Activity if present in Lab Result Observtaion entryRelationship
			labResultOrg.setNotesActivity(
					ParserUtilities.readNotesActivity((NodeList) CCDAConstants.REL_COMPONENT_ACTIVITY_EXPRESSION
						.evaluate(labResultOrgElement, XPathConstants.NODESET), null));
			
			labResultOrg.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(labResultOrgElement, XPathConstants.NODE)));
			labResultOrgList.add(labResultOrg);
		}
		return labResultOrgList;
	}
	
	public static ArrayList<CCDALabResultObs> readResultObservation(NodeList resultObservationNodeList) throws XPathExpressionException
	{
		
		ArrayList<CCDALabResultObs> resultObservationList = new ArrayList<>();
		CCDALabResultObs resultObservation;
		for (int i = 0; i < resultObservationNodeList.getLength(); i++) {
			
			log.info(" Adding lab Observation ");
			resultObservation = new CCDALabResultObs();
			
			Element resultObservationElement = (Element) resultObservationNodeList.item(i);
			resultObservation.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
					evaluate(resultObservationElement, XPathConstants.NODESET)));
			
			resultObservation.setLabCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(resultObservationElement, XPathConstants.NODE)));
			
			resultObservation.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(resultObservationElement, XPathConstants.NODE)));
			
			resultObservation.setMeasurementTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(resultObservationElement, XPathConstants.NODE)));
			
			resultObservation.setInterpretationCode(ParserUtilities.readCode((Element) CCDAConstants.REL_INT_CODE_EXP.
					evaluate(resultObservationElement, XPathConstants.NODE)));
			
			// Add Notes Activity if present in Lab Result Observtaion entryRelationship
			resultObservation.setNotesActivity(
								ParserUtilities.readNotesActivity((NodeList) CCDAConstants.REL_ENTRY_REL_NOTES_ACTIVITY_EXPRESSION
										.evaluate(resultObservationElement, XPathConstants.NODESET), null));
			
			resultObservation.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(resultObservationElement, XPathConstants.NODE)));
			
			Element resultValue = (Element) CCDAConstants.REL_VAL_EXP.
					evaluate(resultObservationElement, XPathConstants.NODE);
			
			if(resultValue != null)
			{
				if(!ParserUtilities.isEmpty(resultValue.getAttribute("xsi:type")))
				{
					String xsiType = resultValue.getAttribute("xsi:type");
					if (xsiType.equalsIgnoreCase("ST") && (resultValue.getFirstChild() != null))
					{
						log.info("Lab Value is ST ");
						String value = resultValue.getFirstChild().getNodeValue();
						resultObservation.setResultString(value);
					}else if(xsiType.equalsIgnoreCase("PQ"))
					{
						log.info("Lab Value is PQ ");
						resultObservation.setResults(ParserUtilities.readQuantity(resultValue));
					}
					else if(xsiType.equalsIgnoreCase("CO"))
					{
						log.info("Lab Value is CO ");
						resultObservation.setResultCode(ParserUtilities.readCode(resultValue));
					}
					else
					{
						log.info("Unknown Lab Value");
					}
				}
			}
			
			NodeList referenceRangeNodeList = (NodeList) CCDAConstants.REL_REF_RANGE_EXP.
			evaluate(resultObservationElement, XPathConstants.NODESET);
			
			ArrayList<CCDAPQ> referenceValueList =null;
			
			if(! ParserUtilities.isNodeListEmpty(referenceRangeNodeList))
			{
				referenceValueList = new ArrayList<>();
			}
			
			for (int j = 0; j < referenceRangeNodeList.getLength(); j++) { 
				
				log.info("Adding Reference Range");
				Element referenceRangeElement = (Element) referenceRangeNodeList.item(j);
				
				if(referenceRangeElement != null)
				{
					log.info("Processing Range");
					
					CCDAPQ lowQuantity = ParserUtilities.readQuantity((Element) CCDAConstants.REL_LOW_EXP.
		    				evaluate(referenceRangeElement, XPathConstants.NODE));
					if(lowQuantity != null) {
						referenceValueList.add(lowQuantity);
					}
					
					CCDAPQ highQuantity = ParserUtilities.readQuantity((Element) CCDAConstants.REL_HIGH_EXP.
		    				evaluate(referenceRangeElement, XPathConstants.NODE));
					if(highQuantity != null) {
						referenceValueList.add(highQuantity);
					}
				}
			}
			resultObservation.setReferenceRange(referenceValueList);
			resultObservationList.add(resultObservation);
			
		}
		
		return resultObservationList;
	}

}
