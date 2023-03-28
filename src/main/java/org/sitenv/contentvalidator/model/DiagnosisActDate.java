package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiagnosisActDate {

private static Logger log = LoggerFactory.getLogger(DiagnosisActDate.class.getName());
	
	private ArrayList<CCDAII>    templateId;
	private CCDACode             diagnosisActCode;
	private CCDAEffTime          effTime;
	
	public DiagnosisActDate() {
		
		templateId = new ArrayList<>();
	}
	
	public void log() {
		
		log.info("***Diagnosis Act Date***");
		
		if(diagnosisActCode != null)
			log.info("Diagnosis Act Code = " + diagnosisActCode.getCode());
	
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		if(effTime != null)
			effTime.log();
		
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}

	public CCDACode getDiagnosisActCode() {
		return diagnosisActCode;
	}

	public void setDiagnosisActCode(CCDACode diagnosisActCode) {
		this.diagnosisActCode = diagnosisActCode;
	}

	public CCDAEffTime getEffTime() {
		return effTime;
	}

	public void setEffTime(CCDAEffTime effTime) {
		this.effTime = effTime;
	}

	public void compare(DiagnosisActDate subDateOfDiagnosis, String context,
			ArrayList<ContentValidationResult> results) {
		
		if(subDateOfDiagnosis != null) {
		
		// Compare template Ids 
				ParserUtilities.compareTemplateIds(templateId, subDateOfDiagnosis.getTemplateId(), results, context);

				// Compare Effective Times
				String elementNameTime = "Diagnosis Date Time  attribute: " + context;
				ParserUtilities.compareEffectiveTime(effTime, subDateOfDiagnosis.getEffTime(), results, elementNameTime);
				
				// Compare PRoblem Codes 
				String elementNameVal = "Diagnosis Act Code elementattribute: " + context;
				ParserUtilities.compareCode(diagnosisActCode, subDateOfDiagnosis.getDiagnosisActCode(), results, elementNameVal);
		}
		else {
			String error = "The scenario contains Date of Diagnosis Act data " + context + 
					" , however there is no matching data in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
	}
	
	
	
}
