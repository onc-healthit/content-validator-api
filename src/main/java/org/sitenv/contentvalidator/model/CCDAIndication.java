package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAIndication {
	
	private static Logger log = LoggerFactory.getLogger(CCDAIndication.class.getName());
	
	private ArrayList<CCDAII>    templateId;
	private CCDACode             indicationType;
	private CCDAEffTime          effTime;
	private CCDACode             indicationCode;
	private CCDACode			 statusCode;
	
	CCDAAuthor					 author;
	
	public CCDAIndication() {
		templateId = new ArrayList<>();
	}

	public void log() {
		
		log.info("***Indication Obs***");
		
		if(indicationType != null)
			log.info("Indication Type Code = " + indicationType.getCode());
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		if(effTime != null)
			effTime.log();
		
		if(indicationCode != null)
			log.info("Indication Code = " + indicationCode.getCode());
		
		if(statusCode != null)
			log.info("Status Code = " + statusCode.getCode());
		
		if(author != null)
			author.log();
		
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}

	public CCDACode getIndicationType() {
		return indicationType;
	}

	public void setIndicationType(CCDACode indicationType) {
		this.indicationType = indicationType;
	}

	public CCDAEffTime getEffTime() {
		return effTime;
	}

	public void setEffTime(CCDAEffTime effTime) {
		this.effTime = effTime;
	}

	public CCDACode getIndicationCode() {
		return indicationCode;
	}

	public void setIndicationCode(CCDACode indicationCode) {
		this.indicationCode = indicationCode;
	}

	public CCDACode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDACode statusCode) {
		this.statusCode = statusCode;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	
	
}
