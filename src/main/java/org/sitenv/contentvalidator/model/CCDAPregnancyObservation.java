package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAPregnancyObservation {
	
	private static Logger log = LoggerFactory.getLogger(CCDAPregnancyObservation.class.getName());

	private ArrayList<CCDAII>    templateIds;
	private CCDACode             pregnancyCode;
	private CCDACode			 statusCode;
	private CCDAEffTime          effTime;
	private CCDACode             pregnancyValue;
	
	private CCDAAuthor			 author;
	
	public CCDAPregnancyObservation() {
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		log.info("***Pregnancy Observation***");
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		if(effTime != null)
			effTime.log();
		
		if(author != null)
			author.log();
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
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

	public void compare(CCDAPregnancyObservation refValue, ArrayList<ContentValidationResult> results, String context) {
		log.info("Comparing Pregnancy Observation ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refValue.getTemplateIds(), templateIds, results, context);
		
		// Compare Sex Codes 
		String elementNameVal = "Pregnancy Observation code element for " + context;
		ParserUtilities.compareCode(refValue.getPregnancyCode(), pregnancyCode, results, elementNameVal);
		
		// Compare Sex Value 
		String valElementContext = "Pregnancy Observation value element for " + context;
		ParserUtilities.compareCode(refValue.getPregnancyValue(), pregnancyValue, results, valElementContext);
		
	}
	
	
}
