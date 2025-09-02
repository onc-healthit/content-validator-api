package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
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

	public static void compare(HashMap<String, CCDAFunctionalStatusObservation> refObservations,
			HashMap<String, CCDAFunctionalStatusObservation> subObservations,
			ArrayList<ContentValidationResult> results) {
		
		String context = " Comparing Functional Status Observation Data ";
		// Check only the reference ones
		for(Map.Entry<String,CCDAFunctionalStatusObservation> entry: refObservations.entrySet()) {
			
			if(subObservations.containsKey(entry.getKey())) {
				
				// Since the observation was found, compare other data elements.
				log.info(" Comparing Functional Status Observation ");
				String compContext = " Comparing Functional Status Observation data for code " + entry.getKey();
				entry.getValue().compare(subObservations.get(entry.getKey()), compContext, results);
				
			}
			else {
				
				String error = "The scenario contains Functional Status Observation data with code " + entry.getKey() + 
						" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
		}
		
	}
	
	private void compare(CCDAFunctionalStatusObservation subObservation, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		String elementName = compContext + " , Template Id Comparison : ";

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateId, subObservation.getTemplateId(), results, elementName);

		// Compare Assessment  Codes 
		String elementNameCode = compContext + " , Disability Status Observation Code Element Comparison : ";
		ParserUtilities.compareCode(functionalStatusCode, subObservation.getFunctionalStatusCode(), results, elementNameCode);
		 		 	 
		// Compare Time  Codes 
		String elementTime = compContext + " , Disability Status Observation Time Comparison : ";
		ParserUtilities.compareEffectiveTime(effTime, subObservation.getEffTime(), results, elementTime);
		
		// Compare Assessment Value 
		// Compare Assessment  Codes 
		String elementNameValue = compContext + " , Disability Status Observation Value Element Comparison : ";
		ParserUtilities.compareCode(functionalStatusValueCode, subObservation.getFunctionalStatusValueCode(), results, elementNameValue);
					
		
	}
	
	
}
