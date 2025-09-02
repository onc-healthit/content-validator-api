package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
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

	public static void compare(HashMap<String, CCDADisabilityStatusObservation> refDisabilities,
			HashMap<String, CCDADisabilityStatusObservation> subDisabilities,
			ArrayList<ContentValidationResult> results) {
		
		String context = " Comparing Disability Status Observation Data ";
		// Check only the reference ones
		for(Map.Entry<String,CCDADisabilityStatusObservation> entry: refDisabilities.entrySet()) {
			
			if(subDisabilities.containsKey(entry.getKey())) {
				
				// Since the observation was found, compare other data elements.
				log.info(" Comparing Disability Status Observation ");
				String compContext = " Comparing Disability Status Observation data for code " + entry.getKey();
				entry.getValue().compare(subDisabilities.get(entry.getKey()), compContext, results);
				
			}
			else {
				
				String error = "The scenario contains Disability Status Observation data with code " + entry.getKey() + 
						" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
		}
		
	}

	private void compare(CCDADisabilityStatusObservation subDisabilityStatusObservation, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		String elementName = compContext + " , Template Id Comparison : ";

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateId, subDisabilityStatusObservation.getTemplateId(), results, elementName);

		// Compare Assessment  Codes 
		String elementNameCode = compContext + " , Disability Status Observation Code Element Comparison : ";
		ParserUtilities.compareCode(disabilityStatusCode, subDisabilityStatusObservation.getDisabilityStatusCode(), results, elementNameCode);
		 		 	 
		// Compare Time  Codes 
		String elementTime = compContext + " , Disability Status Observation Time Comparison : ";
		ParserUtilities.compareEffectiveTime(effTime, subDisabilityStatusObservation.getEffTime(), results, elementTime);
		
		// Compare Assessment Value 
		// Compare Assessment  Codes 
		String elementNameValue = compContext + " , Disability Status Observation Value Element Comparison : ";
		ParserUtilities.compareCode(disabilityStatusValue, subDisabilityStatusObservation.getDisabilityStatusValue(), results, elementNameValue);
					
		
	}

	
}
