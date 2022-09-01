package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

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
	
	
	
}
