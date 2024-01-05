package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAGenderIdentityObs {

	private static Logger log = LoggerFactory.getLogger(CCDAGenderIdentityObs.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            genderIdentityObsCode;
	private CCDACode							genderIdentityValue;
	private CCDAEffTime							observationTime;
	
	private CCDAAuthor	author;

	public CCDAGenderIdentityObs() {
		templateIds = new ArrayList<CCDAII>();
	}

	public void log() {

		if(genderIdentityObsCode != null)
			log.info(" Gender Identity Obs Code = " + genderIdentityObsCode.getCode());
		
		if(genderIdentityValue != null)
			log.info(" Gender Identity Value = " + genderIdentityValue.getCode());

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

	public CCDACode getGenderIdentityObsCode() {
		return genderIdentityObsCode;
	}

	public void setGenderIdentityObsCode(CCDACode genderIdentityObsCode) {
		this.genderIdentityObsCode = genderIdentityObsCode;
	}

	public CCDACode getGenderIdentityValue() {
		return genderIdentityValue;
	}

	public void setGenderIdentityValue(CCDACode genderIdentityValue) {
		this.genderIdentityValue = genderIdentityValue;
	}

	public static void compare(HashMap<String, CCDAGenderIdentityObs> refGenders,
			HashMap<String, CCDAGenderIdentityObs> subGenders, ArrayList<ContentValidationResult> results) {
		
		String context = " Comparing Gender Identity Data ";
		// Check only the reference ones
		for(Map.Entry<String,CCDAGenderIdentityObs> entry: refGenders.entrySet()) {
			
			if(subGenders.containsKey(entry.getKey())) {
				
				// Since the observation was found, compare other data elements.
				log.info(" Comparing Gender Identity Data Observation ");
				String compContext = " Comparing Gender Identity data for code " + entry.getKey();
				entry.getValue().compare(subGenders.get(entry.getKey()), compContext, results);
				
			}
			else {
				
				String error = "The scenario contains gender identity data with code " + entry.getKey() + 
						" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
		}
		
	}

	private void compare(CCDAGenderIdentityObs subCcdaGenderIdentityObs, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		String elementName = compContext + " , Template Id Comparison : ";

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateIds, subCcdaGenderIdentityObs.getTemplateIds(), results, elementName);

		// Compare Gender Identity Codes 
		String elementNameCode = compContext + " , Gender Identity Code Element Comparison : ";
		ParserUtilities.compareCode(genderIdentityObsCode, subCcdaGenderIdentityObs.getGenderIdentityObsCode(), results, elementNameCode);
		 
		// Compare Gender Identity Codes 
		String elementNameVal = compContext + " , Gender Identity Value Element Comparison : ";
		ParserUtilities.compareCodeIncludingNullFlavor(genderIdentityValue, subCcdaGenderIdentityObs.getGenderIdentityValue(), results, elementNameVal);
				 
		
	}
	
	
}
