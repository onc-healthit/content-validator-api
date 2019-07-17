package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.CCDAConstants;

public class CCDACarePlanSections {
	
	private static Logger log = Logger.getLogger(CCDACarePlanSections.class.getName());
	private static String INTERVENTIONS_SECTION_V3 = "Interventions Section (V3) 2.16.840.1.113883.10.20.21.2.3:2015-08-01";
	private static String HEALTH_STATUS_EVALUATIONS_AND_OUTCOMES_SECTION = 
			"Health Status Evaluations and Outcomes Section 2.16.840.1.113883.10.20.22.2.61";
	public static String REQUIRED_SECTIONS = INTERVENTIONS_SECTION_V3 + " and " + HEALTH_STATUS_EVALUATIONS_AND_OUTCOMES_SECTION;
	
	private ArrayList<CCDAII> templateIds;
	private boolean interventionsSectionV3;
	private boolean healthStatusEvaluationsAndOutcomesSection; 
	
	public CCDACarePlanSections() {
		templateIds = new ArrayList<CCDAII>();
	}
	
	public CCDACarePlanSections(CCDAII ...templateIds) {
		this();
		for(CCDAII curII : templateIds) {
			this.templateIds.add(curII);
		}
	}
	
	public void log() {
		log.info("*** Logging CCDACarePlanSections Data ****");
		log.info("Document " + (interventionsSectionV3 ? "DOES" : "does NOT") + " contain " 
				+ INTERVENTIONS_SECTION_V3 + ": " + interventionsSectionV3);
		log.info("Document " + (healthStatusEvaluationsAndOutcomesSection ? "DOES" : "does NOT") + " contain " 
				+  HEALTH_STATUS_EVALUATIONS_AND_OUTCOMES_SECTION + ": " + healthStatusEvaluationsAndOutcomesSection);

		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id root [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id extension [" + j + "] = " + templateIds.get(j).getExtValue());
		}
	}	
	
	public void compare(CCDACarePlanSections submittedCarePlanSections, ArrayList<ContentValidationResult> results, CCDARefModel submittedCCDA) {		
		if (submittedCCDA.warningsPermitted()) {
			final String warningPrefix = "A Care Plan section is missing: The scenario contains the ";
			final String warningSuffix = ", but it was not found in the submitted document";
			
			if(interventionsSectionV3) {
				if(!submittedCarePlanSections.interventionsSectionV3) {
					results.add(new ContentValidationResult(warningPrefix + INTERVENTIONS_SECTION_V3 + warningSuffix,
							ContentValidationResultLevel.WARNING, CCDAConstants.DEFAULT_XPATH, CCDAConstants.DEFAULT_LINE_NUMBER));
				}
			}
			if(healthStatusEvaluationsAndOutcomesSection) {
				if(!submittedCarePlanSections.healthStatusEvaluationsAndOutcomesSection) {
					results.add(new ContentValidationResult(warningPrefix + HEALTH_STATUS_EVALUATIONS_AND_OUTCOMES_SECTION + warningSuffix,
							ContentValidationResultLevel.WARNING, CCDAConstants.DEFAULT_XPATH, CCDAConstants.DEFAULT_LINE_NUMBER));
				}
			}
		} else {
			log.info(
					"Skipping CCDACarePlanSections.compare 'interventionsSectionV3' and 'healthStatusEvaluationsAndOutcomesSection' checks  due to severityLevel: "
							+ submittedCCDA.getSeverityLevelName());			
		}
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}
	
	public void setInterventionsSectionV3(boolean interventionsSectionV3) {
		this.interventionsSectionV3 = interventionsSectionV3;
	}
	
	public void setHealthStatusEvaluationsAndOutcomesSection(boolean healthStatusEvaluationsAndOutcomesSection) {
		this.healthStatusEvaluationsAndOutcomesSection = healthStatusEvaluationsAndOutcomesSection;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {		
		if(templateIds != null) {
			this.templateIds = templateIds;
		}
	}

}
