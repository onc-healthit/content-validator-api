package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDADisabilityStatusObservation {
	
private static Logger log = LoggerFactory.getLogger(CCDADisabilityStatusObservation.class.getName());
	
	private ArrayList<CCDAII>    templateId;
	private CCDACode             disabilityStatusCode;
	private CCDACode			 statusCode;
	private CCDAEffTime          effTime;
	private CCDACode             disabilityStatusValue;
	
	private CCDAAuthor			 author;
	
	public CCDADisabilityStatusObservation() {
		templateId = new ArrayList<>();
	}
	
	public void log() {
		
		log.info("***Disability Status Observation***");
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		if(effTime != null)
			effTime.log();
		
		if(disabilityStatusCode != null)
			log.info("Disability Status Code = " + disabilityStatusCode.getCode());
		
		if(disabilityStatusValue != null)
			log.info("Disability Status Value = " + disabilityStatusValue.getCode());
		
		if(author != null)
			author.log();
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}

	public CCDACode getDisabilityStatusCode() {
		return disabilityStatusCode;
	}

	public void setDisabilityStatusCode(CCDACode disabilityStatusCode) {
		this.disabilityStatusCode = disabilityStatusCode;
	}

	public CCDACode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDACode statusCode) {
		this.statusCode = statusCode;
	}

	public CCDAEffTime getEffTime() {
		return effTime;
	}

	public void setEffTime(CCDAEffTime effTime) {
		this.effTime = effTime;
	}

	public CCDACode getDisabilityStatusValue() {
		return disabilityStatusValue;
	}

	public void setDisabilityStatusValue(CCDACode disabilityStatusValue) {
		this.disabilityStatusValue = disabilityStatusValue;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	
}
