package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.model.CCDATreatmentInterventionPreference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TreatmentInterventionPreferenceParser {
	
private static Logger log = LoggerFactory.getLogger(TreatmentInterventionPreferenceParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022, boolean svap2023, 
			boolean uscdiv4)
			throws XPathExpressionException {
    	log.info(" *** Parsing Treatment Intervention Preference *** ");
    	model.setTreatmentPreferences(retrieveTreatmentPreferences(doc));	    	
	}

	private static ArrayList<CCDATreatmentInterventionPreference> retrieveTreatmentPreferences(Document doc) throws XPathExpressionException {
		
		ArrayList<CCDATreatmentInterventionPreference> treatmentPreferences = new ArrayList<>();
		NodeList treatmentPrefElements = (NodeList) CCDAConstants.TREATMENT_PREFERENCE_EXPRESSION.evaluate(doc, XPathConstants.NODESET);
		if(treatmentPrefElements != null)
		{
			log.info("No of Treatment Preferences found " + treatmentPrefElements.getLength());
			
			for (int i = 0; i < treatmentPrefElements.getLength(); i++) {
				
				CCDATreatmentInterventionPreference tp = new CCDATreatmentInterventionPreference();
				Element treatmentElement = (Element) treatmentPrefElements.item(i);
				tp.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(treatmentElement, XPathConstants.NODESET)));
				tp.setTreatmentPreferenceCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(treatmentElement, XPathConstants.NODE)));
				
				Element resultValue = (Element) CCDAConstants.REL_VAL_EXP.
						evaluate(treatmentElement, XPathConstants.NODE);
				
				if(resultValue != null)
				{
					if(!ParserUtilities.isEmpty(resultValue.getAttribute("xsi:type")))
					{
						String xsiType = resultValue.getAttribute("xsi:type");
						if (xsiType.equalsIgnoreCase("ST") && (resultValue.getFirstChild() != null))
						{
							log.info("Treatment Preference Value is ST ");
							String value = resultValue.getFirstChild().getNodeValue();
							tp.setTreatmentPreference(value);
						}
						else if(xsiType.equalsIgnoreCase("ED") && (resultValue.getFirstChild() != null))
						{
							log.info("Treatment Preference Value is ED with text ");
							String value = resultValue.getFirstChild().getNodeValue();
							tp.setTreatmentPreference(value);
						}
						else if(xsiType.equalsIgnoreCase("ED"))
						{
							log.info("Treatment Preference Value is ED with reference");
							NodeList refList = resultValue.getElementsByTagName("reference");
							if(refList != null && refList.getLength() > 0 ) {
								Element refElement = (Element) refList.item(0);
								String refLink = refElement.getAttribute("value");
								
								if(refLink != null && !refLink.isEmpty()) {
									
									Boolean linkPresent = ParserUtilities.isLinkPresentInDocument(doc, refLink);
									// Find the reference in the document
									tp.setTreatmentLinkPresent(linkPresent);
								}
							}
							
						}
						else if(xsiType.equalsIgnoreCase("CD")) {
							log.info(" Treatment Preference Value is CD ");
							tp.setTreatmentPreferenceCode(ParserUtilities.readCode(resultValue));
						}
						else {
							log.error(" Value is expected to be ST or ED ");
						}
					}
					else {
						log.error(" Xsi:type not present for value element ");
					}
				} // resultValue != null
				else {
					log.error(" Value element not present ");
				}
				
				treatmentPreferences.add(tp);
			}// for
			
		
		}
		return treatmentPreferences;
	}
}
