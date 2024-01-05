package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAMentalStatusObservation {
	
	private static Logger log = LoggerFactory.getLogger(CCDAFunctionalStatusObservation.class.getName());
	
	private ArrayList<CCDAII>    templateId;
	private CCDACode             mentalStatusCode;
	private CCDACode			 statusCode;
	private CCDAEffTime          effTime;
	private CCDACode             mentalStatusValue;
	
	private CCDAAuthor			 author;
	
	public CCDAMentalStatusObservation() {
		templateId = new ArrayList<>();
	}
	
	public void log() {
		log.info("***Mental Status Observation***");
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		if(effTime != null)
			effTime.log();
		
		if(mentalStatusCode != null)
			log.info("Functional Status Code = " + mentalStatusCode.getCode());
		
		if(mentalStatusValue != null)
			log.info("Functional Status Value = " + mentalStatusValue.getCode());
		
		
		if(author != null)
			author.log();
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}

	public CCDACode getMentalStatusCode() {
		return mentalStatusCode;
	}

	public void setMentalStatusCode(CCDACode mentalStatusCode) {
		this.mentalStatusCode = mentalStatusCode;
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

	public CCDACode getMentalStatusValue() {
		return mentalStatusValue;
	}

	public void setMentalStatusValue(CCDACode mentalStatusValue) {
		this.mentalStatusValue = mentalStatusValue;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	
}
