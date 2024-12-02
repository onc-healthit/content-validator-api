package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDASpecimenConditionObservation {
	
	private static Logger log = LoggerFactory.getLogger(CCDASpecimenConditionObservation.class.getName());
	
	private ArrayList<CCDAII>				templateIds;
	private CCDACode						specimenConditionCode;
	private CCDACode						specimenConditionValue;
	
	public CCDASpecimenConditionObservation() {
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** CCDA Specimen Condition Observation ***");
		
		if(specimenConditionCode != null)
			specimenConditionCode.log();
		
		if(specimenConditionValue != null)
			specimenConditionValue.log();
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getSpecimenConditionCode() {
		return specimenConditionCode;
	}

	public void setSpecimenConditionCode(CCDACode specimenConditionCode) {
		this.specimenConditionCode = specimenConditionCode;
	}

	public CCDACode getSpecimenConditionValue() {
		return specimenConditionValue;
	}

	public void setSpecimenConditionValue(CCDACode specimenConditionValue) {
		this.specimenConditionValue = specimenConditionValue;
	}

	
}
