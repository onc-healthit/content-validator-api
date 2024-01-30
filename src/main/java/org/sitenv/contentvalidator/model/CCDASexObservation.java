package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDASexObservation {
	
	private static Logger log = LoggerFactory.getLogger(CCDASexObservation.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            sexCode;
	private CCDACode							sexValue;
	private CCDAEffTime							observationTime;
	
	private CCDAAuthor	author;

	public CCDASexObservation() {
		templateIds = new ArrayList<CCDAII>();
	}

	public void log() {
		
		log.info(" *** Sexual Orientation Observation ***");

		if(sexCode != null)
			log.info(" Sex Code = " + sexCode.getCode());
		
		if(sexValue != null)
			log.info(" Sex Value = " + sexValue.getCode());

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

	public CCDACode getSexCode() {
		return sexCode;
	}

	public void setSexCode(CCDACode sexCode) {
		this.sexCode = sexCode;
	}

	public CCDACode getSexValue() {
		return sexValue;
	}

	public void setSexValue(CCDACode sexValue) {
		this.sexValue = sexValue;
	}

	public void compare(CCDASexObservation refSex, ArrayList<ContentValidationResult> results, String context) {
		
		log.info("Comparing Sex Observation ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refSex.getTemplateIds(), templateIds, results, context);
		
		// Compare Sex Codes 
		String elementNameVal = "Sex Observation code element for " + context;
		ParserUtilities.compareCode(refSex.getSexCode(), sexCode, results, elementNameVal);
		
		// Compare Sex Value 
		String valElementContext = "Sex Observation value element for " + context;
		ParserUtilities.compareCode(refSex.getSexValue(), sexValue, results, valElementContext);	
	}

}
