package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAPregnancyObservation {
	
	private static Logger log = LoggerFactory.getLogger(CCDAPregnancyObservation.class.getName());

	private ArrayList<CCDAII>    templateId;
	private CCDACode             pregnancyCode;
	private CCDACode			 statusCode;
	private CCDAEffTime          effTime;
	private CCDACode             pregnancyValue;
	
	private CCDAAuthor			 author;
	
	public CCDAPregnancyObservation() {
		templateId = new ArrayList<>();
	}
	
	public void log() {
		log.info("***Pregnancy Observation***");
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		if(effTime != null)
			effTime.log();
		
		if(author != null)
			author.log();
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}

	public CCDACode getPregnancyCode() {
		return pregnancyCode;
	}

	public void setPregnancyCode(CCDACode pregnancyCode) {
		this.pregnancyCode = pregnancyCode;
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

	public CCDACode getPregnancyValue() {
		return pregnancyValue;
	}

	public void setPregnancyValue(CCDACode pregnancyValue) {
		this.pregnancyValue = pregnancyValue;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	
}
