package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDATribalAffiliationObservation {

	private static Logger log = LoggerFactory.getLogger(CCDASexualOrientation.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            tribalAffiliationCode;
	private CCDACode							tribalAffiliationValue;
	private CCDAEffTime							observationTime;
	private CCDAAuthor							author;
	
	public CCDATribalAffiliationObservation() {
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** CCDA Tribal Affiliation Observation *** ");
		
		if(tribalAffiliationCode != null)
			log.info(" Tribal Affiliation Code = " + tribalAffiliationCode.getCode());
		
		if(tribalAffiliationValue != null)
			log.info(" Tribal Affiliation Value = " + tribalAffiliationValue.getCode());

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

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getTribalAffiliationCode() {
		return tribalAffiliationCode;
	}

	public void setTribalAffiliationCode(CCDACode tribalAffiliationCode) {
		this.tribalAffiliationCode = tribalAffiliationCode;
	}

	public CCDACode getTribalAffiliationValue() {
		return tribalAffiliationValue;
	}

	public void setTribalAffiliationValue(CCDACode tribalAffiliationValue) {
		this.tribalAffiliationValue = tribalAffiliationValue;
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

	public void compare(CCDATribalAffiliationObservation refValue, ArrayList<ContentValidationResult> results, String context) {
		
		log.info("Comparing Tribal Affiliation Observation ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refValue.getTemplateIds(), templateIds, results, context);
		
		// Compare Sex Codes 
		String elementNameVal = "Tribal Affiliation code element for " + context;
		ParserUtilities.compareCode(refValue.getTribalAffiliationCode(), tribalAffiliationCode, results, elementNameVal);
		
		// Compare Sex Value 
		String valElementContext = "Tribal Affiliation value element for " + context;
		ParserUtilities.compareCode(refValue.getTribalAffiliationValue(), tribalAffiliationValue, results, valElementContext);
		
	}
	
	
}
