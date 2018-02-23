package org.sitenv.contentvalidator.parsers;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.model.CCDACarePlanSections;
import org.sitenv.contentvalidator.model.CCDAII;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CarePlanSectionsParser {
	
	private static Logger log = Logger.getLogger(CarePlanSectionsParser.class.getName());
	
	private static final CCDAII INTERVENTIONS_SECTION_V3 = 
			new CCDAII("2.16.840.1.113883.10.20.21.2.3", "2015-08-01");
	private static final CCDAII HEALTH_STATUS_EVALUATIONS_AND_OUTCOMES_SECTION = 
			new CCDAII("2.16.840.1.113883.10.20.22.2.61"); 
	
	public static void parse(Document doc, CCDARefModel model) throws XPathExpressionException {
		log.info(" *** Parsing CarePlan Sections *** ");
		model.setCarePlanSections(getSuggestedSections(doc));		
	}
	
	private static CCDACarePlanSections getSuggestedSections(Document doc) throws XPathExpressionException {
		CCDACarePlanSections carePlanSections = new CCDACarePlanSections();
		
		Element interventions = (Element) CCDAConstants.INTERVENTIONS_SECTION_V3_EXP.evaluate(doc, XPathConstants.NODE);				
		if(interventions != null) {
			log.info("interventions tagName: " + interventions.getTagName());
			log.info("Setting: Document HAS Interventions Section (V3) 2.16.840.1.113883.10.20.21.2.3:2015-08-01");
			carePlanSections.setInterventionsSectionV3(true);
		} else {
			log.info("Document does NOT have Interventions Section (V3) 2.16.840.1.113883.10.20.21.2.3:2015-08-01");
		}
		
		Element healthStatusEvals = (Element) CCDAConstants.HEALTH_STATUS_EVALUATIONS_AND_OUTCOMES_SECTION_EXP
				.evaluate(doc, XPathConstants.NODE);
		if(healthStatusEvals != null) {
			log.info("healthStatusEvals tagName: " + healthStatusEvals.getTagName());
			log.info("Setting: Document HAS Health Status Evaluations and Outcomes Section 2.16.840.1.113883.10.20.22.2.61");
			carePlanSections.setHealthStatusEvaluationsAndOutcomesSection(true);			
		} else {
			log.info("Document does NOT have Health Status Evaluations and Outcomes Section 2.16.840.1.113883.10.20.22.2.61");
		}
		
		return carePlanSections;
	}
	
}
