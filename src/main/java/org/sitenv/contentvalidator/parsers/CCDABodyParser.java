package org.sitenv.contentvalidator.parsers;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;

public class CCDABodyParser {
	
	private static Logger log = Logger.getLogger(CCDABodyParser.class.getName());
	
	static public void parseBody(Document doc, CCDARefModel model, boolean curesUpdate) throws XPathExpressionException{
	
		log.info(" Parsing Encounters ");
		EncounterParser.parse(doc,model, curesUpdate);
		
		log.info(" Parsing Problems ");
		ProblemParser.parse(doc, model, curesUpdate);
		
		log.info(" Parsing Medications ");
		MedicationParser.parse(doc, model, curesUpdate);
		
		log.info("Parsing Allergies ");
		AllergiesParser.parse(doc,model, curesUpdate);
		
		log.info("Parsing Social History ");
		SocialHistoryParser.parse(doc, model, curesUpdate);
		
		log.info(" Parsing lab Results ");
		LabResultParser.parse(doc, model, curesUpdate);
		
		log.info(" Parsing lab tests ");
		LabTestParser.parse(doc, model, curesUpdate);
		
		log.info("Parsing Vitals ");
		VitalSignParser.parse(doc, model, curesUpdate);
		
		log.info("Parsing Procedures "); 
		ProcedureParser.parse(doc, model, curesUpdate);
		
		log.info("Parsing Care Team Members ");
		CareTeamMemberParser.parse(doc, model, curesUpdate);
		
		log.info("Parsing CarePlan Sections ");
		CarePlanSectionsParser.parse(doc, model, curesUpdate);
		
		log.info("Parsing Immunizations ");
		ImmunizationParser.parse(doc, model, curesUpdate);
		
		log.info("Parsing Medical Equipments");
		MedicalEquipmentParser.parse(doc, model, curesUpdate);
		
		if(curesUpdate) {
			log.info(" Parsing Notes Section ");
			NotesParser.parse(doc,model, curesUpdate);
			
			log.info(" Parsing Doc Author ");
			AuthorParser.parse(doc, model, curesUpdate);
		}
		
	}
}