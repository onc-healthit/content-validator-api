package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDASmokingStatus {

	private static Logger log = Logger.getLogger(CCDASmokingStatus.class.getName());
	
	private ArrayList<CCDAII>					smokingStatusTemplateIds;
	private CCDACode							smokingStatusCode;
	private CCDAEffTime							observationTime;
	
	private CCDAAuthor author;
	
	public static void compareSmokingStatus(HashMap<String, CCDASmokingStatus> refStatus, 
			HashMap<String, CCDASmokingStatus> subStatus, 	ArrayList<ContentValidationResult> results) {

		log.info(" Start Smoking Status comparison ");
		// For each Vital Sign Observation in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDASmokingStatus> ent: refStatus.entrySet()) {

			if(subStatus.containsKey(ent.getKey())) {

				log.info("Smoking Status found ");

			} else {
				// Error
				String error = "The scenario contains Smoking Status data with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}
		
	}
	
	public void log() {
		
		if(smokingStatusCode != null)
			log.info(" Smoking Status Code = " + smokingStatusCode.getCode());
		
		for(int j = 0; j < smokingStatusTemplateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + smokingStatusTemplateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + smokingStatusTemplateIds.get(j).getExtValue());
		}
		
		if(observationTime != null)
			observationTime.log();
		
		if(author != null)
			author.log();
	}
	
	public CCDASmokingStatus()
	{
		smokingStatusTemplateIds = new ArrayList<CCDAII>();
	}
	
	

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public ArrayList<CCDAII> getSmokingStatusTemplateIds() {
		return smokingStatusTemplateIds;
	}

	public void setSmokingStatusTemplateIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.smokingStatusTemplateIds = ids;
	}

	public CCDACode getSmokingStatusCode() {
		return smokingStatusCode;
	}

	public void setSmokingStatusCode(CCDACode smokingStatusCode) {
		this.smokingStatusCode = smokingStatusCode;
	}

	public CCDAEffTime getObservationTime() {
		return observationTime;
	}

	public void setObservationTime(CCDAEffTime observationTime) {
		this.observationTime = observationTime;
	}
}
