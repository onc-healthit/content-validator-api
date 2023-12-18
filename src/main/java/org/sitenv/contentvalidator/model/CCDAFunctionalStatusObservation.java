package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAFunctionalStatusObservation {

	private static Logger log = LoggerFactory.getLogger(CCDAFunctionalStatusObservation.class.getName());
	
	private ArrayList<CCDAII>    templateId;
	private CCDACode             functionalStatusCode;
	private CCDACode			 statusCode;
	private CCDAEffTime          effTime;
	private CCDACode             functionalStatusValueCode;
	private String				 functionalStatusValueString;
	private CCDAPQ		         functionalStatusValueQuantity;
	
	private CCDAAuthor			 author;
	
	public CCDAFunctionalStatusObservation() {
		templateId = new ArrayList<>();
	}
	
	public void log() {
		log.info("***Functional Status Observation***");
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		if(effTime != null)
			effTime.log();
		
		if(functionalStatusCode != null)
			log.info("Functional Status Code = " + functionalStatusCode.getCode());
		
		if(functionalStatusValueCode != null)
			log.info("Functional Status Value Code = " + functionalStatusValueCode.getCode());
		
		if(functionalStatusValueString != null)
			log.info(" Functional Status Value String : {}", functionalStatusValueString);
		
		if(functionalStatusValueQuantity != null)
			log.info(" Functional Status Value Quantity : {}:{}", functionalStatusValueQuantity.getValue(), functionalStatusValueQuantity.getUnits());
		
		
		if(author != null)
			author.log();
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}

	public CCDACode getFunctionalStatusCode() {
		return functionalStatusCode;
	}

	public void setFunctionalStatusCode(CCDACode functionalStatusCode) {
		this.functionalStatusCode = functionalStatusCode;
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

	public CCDACode getFunctionalStatusValueCode() {
		return functionalStatusValueCode;
	}

	public void setFunctionalStatusValueCode(CCDACode functionalStatusValueCode) {
		this.functionalStatusValueCode = functionalStatusValueCode;
	}

	public String getFunctionalStatusValueString() {
		return functionalStatusValueString;
	}

	public void setFunctionalStatusValueString(String functionalStatusValueString) {
		this.functionalStatusValueString = functionalStatusValueString;
	}

	public CCDAPQ getFunctionalStatusValueQuantity() {
		return functionalStatusValueQuantity;
	}

	public void setFunctionalStatusValueQuantity(CCDAPQ functionalStatusValueQuantity) {
		this.functionalStatusValueQuantity = functionalStatusValueQuantity;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	
}
