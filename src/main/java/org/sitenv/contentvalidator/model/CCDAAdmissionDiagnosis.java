package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class CCDAAdmissionDiagnosis {

	private static Logger log = Logger.getLogger(CCDAAdmissionDiagnosis.class.getName());

	private ArrayList<CCDAII>    templateId;
	private CCDACode  sectionCode;
	private ArrayList<CCDAProblemObs> diagnosis;
	
	public void log() {
		
		if(sectionCode != null)
			log.info("Admission Diagnosis Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		for(int k = 0; k < diagnosis.size(); k++) {
			diagnosis.get(k).log();
		}
	}
	
	public CCDAAdmissionDiagnosis() {
		super();
		diagnosis = new ArrayList<CCDAProblemObs>();
		templateId = new ArrayList<CCDAII>();
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDAProblemObs> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(ArrayList<CCDAProblemObs> diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	
}
