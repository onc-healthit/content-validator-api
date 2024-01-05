package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDASexualOrientation {

	private static Logger log = LoggerFactory.getLogger(CCDASexualOrientation.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            sexualOrientationCode;
	private CCDACode							sexualOrientationValue;
	private CCDAEffTime							observationTime;
	
	private CCDAAuthor	author;

	public CCDASexualOrientation() {
		templateIds = new ArrayList<CCDAII>();
	}

	public void log() {
		
		log.info(" *** Sexual Orientation Observation ***");

		if(sexualOrientationCode != null)
			log.info(" Sexual Orientation Code = " + sexualOrientationCode.getCode());
		
		if(sexualOrientationValue != null)
			log.info(" Sexual Orientation Value = " + sexualOrientationValue.getCode());

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

	public CCDACode getSexualOrientationCode() {
		return sexualOrientationCode;
	}

	public void setSexualOrientationCode(CCDACode sexualOrientationCode) {
		this.sexualOrientationCode = sexualOrientationCode;
	}

	public CCDACode getSexualOrientationValue() {
		return sexualOrientationValue;
	}

	public void setSexualOrientationValue(CCDACode sexualOrientationValue) {
		this.sexualOrientationValue = sexualOrientationValue;
	}

	public static void compare(HashMap<String, CCDASexualOrientation> refSexOrientation,
			HashMap<String, CCDASexualOrientation> subSexOrientation, ArrayList<ContentValidationResult> results) {
		
		String context = " Comparing Sexual Orientation Data ";
		// Check only the reference ones
		for(Map.Entry<String,CCDASexualOrientation> entry: refSexOrientation.entrySet()) {
			
			if(subSexOrientation.containsKey(entry.getKey())) {
				
				// Since the observation was found, compare other data elements.
				log.info(" Comparing Sexual Orientation Observation ");
				String compContext = " Comparing Sexual Orientation data for code " + entry.getKey();
				entry.getValue().compare(subSexOrientation.get(entry.getKey()), compContext, results);
				
			}
			else {
				
				String error = "The scenario contains sexual orientation with code " + entry.getKey() + 
						" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
		}
		
	}

	private void compare(CCDASexualOrientation subCcdaSexualOrientation, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		String elementName = compContext + " , Template Id Comparison : ";

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateIds, subCcdaSexualOrientation.getTemplateIds(), results, elementName);

		// Compare Sexual Orientation Codes 
		String elementNameCode = compContext + " , Sexual Orientation Code Element Comparison : ";
		ParserUtilities.compareCode(sexualOrientationCode, subCcdaSexualOrientation.getSexualOrientationCode(), results, elementNameCode);
		 
		// Compare Sexual Orientation Codes 
		String elementNameVal = compContext + " , Sexual Orientation Value Element Comparison : ";
		ParserUtilities.compareCodeIncludingNullFlavor(sexualOrientationValue, subCcdaSexualOrientation.getSexualOrientationValue(), results, elementNameVal);
				 
	}

	
}
