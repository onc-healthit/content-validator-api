package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.CCDACareExperiencePreference;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CareExperiencePreferenceParser {
	
private static Logger log = LoggerFactory.getLogger(CareExperiencePreferenceParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022, boolean svap2023, 
			boolean uscdiv4)
			throws XPathExpressionException {
    	log.info(" *** Parsing Care Experience Preference *** ");
    	model.setCarePreferences(retrieveCareExperiencePreferences(doc));	    	
	}

	private static ArrayList<CCDACareExperiencePreference> retrieveCareExperiencePreferences(Document doc) throws XPathExpressionException {
		
		ArrayList<CCDACareExperiencePreference> carePreferences = new ArrayList<>();
		NodeList carePrefElements = (NodeList) CCDAConstants.CARE_PREFERENCE_EXPRESSION.evaluate(doc, XPathConstants.NODESET);
		if(carePrefElements != null)
		{
			log.info("No of Care Experience Preferences found " + carePrefElements.getLength());
			
			for (int i = 0; i < carePrefElements.getLength(); i++) {
				
				CCDACareExperiencePreference cep = new CCDACareExperiencePreference();
				Element carePrefElement = (Element) carePrefElements.item(i);
				cep.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(carePrefElement, XPathConstants.NODESET)));
				cep.setCareExperiencePreferenceCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(carePrefElement, XPathConstants.NODE)));
				
				Element resultValue = (Element) CCDAConstants.REL_VAL_EXP.
						evaluate(carePrefElement, XPathConstants.NODE);
				
				if(resultValue != null)
				{
					if(!ParserUtilities.isEmpty(resultValue.getAttribute("xsi:type")))
					{
						String xsiType = resultValue.getAttribute("xsi:type");
						if (xsiType.equalsIgnoreCase("ST") && (resultValue.getFirstChild() != null))
						{
							log.info("Care Experience Preference Value is ST ");
							String value = resultValue.getFirstChild().getNodeValue();
							cep.setCareExperiencePreference(value);
						}
						else if(xsiType.equalsIgnoreCase("ED") && (resultValue.getFirstChild() != null))
						{
							log.info("Care Experience Preference Value is ED with text ");
							String value = resultValue.getFirstChild().getNodeValue();
							cep.setCareExperiencePreference(value);
						}
						else if(xsiType.equalsIgnoreCase("ED"))
						{
							log.info("Care Experience Preference Value is ED with reference");
							NodeList refList = resultValue.getElementsByTagName("reference");
							if(refList != null && refList.getLength() > 0 ) {
								Element refElement = (Element) refList.item(0);
								String refLink = refElement.getAttribute("value");
								
								if(refLink != null && !refLink.isEmpty()) {
									
									Boolean linkPresent = ParserUtilities.isLinkPresentInDocument(doc, refLink);
									// Find the reference in the document
									cep.setCareExperiencePreferenceLinkPresent(linkPresent);
								}
							}
							
						}
						else if(xsiType.equalsIgnoreCase("CD")) {
							log.info(" Care Experience Preference Value is CD ");
							cep.setCareExperiencePreferenceCode(ParserUtilities.readCode(resultValue));
						}
						else {
							log.error(" Value is expected to be ST or ED or CD");
						}
					}
					else {
						log.error(" Xsi:type not present for value element ");
					}
				} // resultValue != null
				else {
					log.error(" Value element not present ");
				}
				
				carePreferences.add(cep);
			}// for
			
		
		}
		return carePreferences;
	}

}
