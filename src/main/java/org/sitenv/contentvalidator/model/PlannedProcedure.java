package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlannedProcedure {

private static Logger log = LoggerFactory.getLogger(PlannedProcedure.class.getName());
	
	private ArrayList<CCDAII>   			templateIds;
	private CCDACode						procedureCode;
	private CCDACode						statusCode;
	private CCDAEffTime						procedureTime;
	
	CCDAAuthor	author;
	
	public PlannedProcedure() {
		
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		if(procedureCode != null)
			log.info(" Procedure  Code = " + procedureCode.getCode());
		
		if(statusCode != null)
			log.info(" Lab Result Status  Code = " + statusCode.getCode());
		
		if(procedureTime != null)
			procedureTime.log();
		
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

	public CCDACode getProcedureCode() {
		return procedureCode;
	}

	public void setProcedureCode(CCDACode procedureCode) {
		this.procedureCode = procedureCode;
	}

	public CCDACode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDACode statusCode) {
		this.statusCode = statusCode;
	}

	public CCDAEffTime getProcedureTime() {
		return procedureTime;
	}

	public void setProcedureTime(CCDAEffTime procedureTime) {
		this.procedureTime = procedureTime;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	
}
