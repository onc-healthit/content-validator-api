package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAGenderIdentityObs {

	private static Logger log = LoggerFactory.getLogger(CCDAGenderIdentityObs.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            genderIdentityObsCode;
	private CCDACode							genderIdentityValue;
	private CCDAEffTime							observationTime;
	
	private CCDAAuthor	author;

	public CCDAGenderIdentityObs() {
		templateIds = new ArrayList<CCDAII>();
	}

	public void log() {

		if(genderIdentityObsCode != null)
			log.info(" Gender Identity Obs Code = " + genderIdentityObsCode.getCode());
		
		if(genderIdentityValue != null)
			log.info(" Gender Identity Value = " + genderIdentityValue.getCode());

		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}

		if(observationTime != null)
			observationTime.log();
		
		if(author != null)
			author.log();
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateIds = ids;
	}

	public CCDAEffTime getObservationTime() {
		return observationTime;
	}

	public void setObservationTime(CCDAEffTime observationTime) {
		this.observationTime = observationTime;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public CCDACode getGenderIdentityObsCode() {
		return genderIdentityObsCode;
	}

	public void setGenderIdentityObsCode(CCDACode genderIdentityObsCode) {
		this.genderIdentityObsCode = genderIdentityObsCode;
	}

	public CCDACode getGenderIdentityValue() {
		return genderIdentityValue;
	}

	public void setGenderIdentityValue(CCDACode genderIdentityValue) {
		this.genderIdentityValue = genderIdentityValue;
	}
	
	
}
