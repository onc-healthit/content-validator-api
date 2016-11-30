package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDAVitalObs {
	
	private static Logger log = Logger.getLogger(CCDAVitalObs.class.getName());
	
	private ArrayList<CCDAII>   			templateIds;
	private CCDACode						vsCode;
	private CCDACode						statusCode;
	private CCDAEffTime						measurementTime;
	private CCDAPQ							vsResult;
	private CCDACode						interpretationCode;
	private ArrayList<CCDAPQ> 				referenceValue;
	
	public static void compareVitalObsData(HashMap<String, CCDAVitalObs> refVitals, 
			HashMap<String, CCDAVitalObs> subVitals, 	ArrayList<ContentValidationResult> results) {

		log.info(" Start Comparing Vital Signs Observations ");
		// For each Vital Sign Observation in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDAVitalObs> ent: refVitals.entrySet()) {

			if(subVitals.containsKey(ent.getKey())) {

				log.info("Comparing Vital Signs Observation ");
				String context = "Vital Sign Observation Entry corresponding to the code " + ent.getKey();
				subVitals.get(ent.getKey()).compare(ent.getValue(), results, context);


			} else {
				// Error
				String error = "The scenario contains Vital Sign data with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the Vital Signs data is not present in the reference, 
		if( (refVitals == null || refVitals.size() == 0) && (subVitals != null && subVitals.size() > 0) ) {

			// Error
			String error = "The scenario does not require Vital Signs data " + 
					" , however there is Vital Signs data in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}
	
	public void compare(CCDAVitalObs refVital, ArrayList<ContentValidationResult> results , String context) {
		
		log.info("Comparing Vital Signs ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refVital.getTemplateIds(), templateIds, results, context);
		
		// Compare Effective Times
		String elementNameTime = "Effective Time for " + context;
		//ParserUtilities.compareEffectiveTime(refResult.getMeasurementTime(), measurementTime, results, elementNameTime);
		
		// Compare Lab Codes 
		String elementNameVal = "Vital Sign Observation code element for " + context;
		ParserUtilities.compareCode(refVital.getVsCode(), vsCode, results, elementNameVal);
		
		String statusCodeElem = "Vital Sign Observation Status code element for " + context;
		ParserUtilities.justCompareCode(refVital.getStatusCode(), statusCode, results, statusCodeElem);
		
		String valPQ = "Vital Sign Observation Value (Quantity - PQ) Comparison for " + context;
		ParserUtilities.compareQuantity(refVital.getVsResult(), vsResult, results, valPQ);
		
	}

	
	public void log() {
		
		if(vsCode != null)
			log.info(" Vital Sign  Code = " + vsCode.getCode());
		
		if(statusCode != null)
			log.info(" Lab Result Status  Code = " + statusCode.getCode());
		
		if(measurementTime != null)
			measurementTime.log();
		
		if(vsResult != null)
			vsResult.log();
		
		if(interpretationCode != null)
			log.info(" Interpretation Code = " + interpretationCode.getCode());
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		for(int k = 0; k < referenceValue.size(); k++) {
			referenceValue.get(k).log();
		}
	}
	
	public CCDAVitalObs()
	{
		templateIds = new ArrayList<CCDAII>();
		referenceValue = new ArrayList<CCDAPQ>();
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateIds = ids;
	}

	public CCDACode getVsCode() {
		return vsCode;
	}

	public void setVsCode(CCDACode vsCode) {
		this.vsCode = vsCode;
	}

	public CCDACode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDACode statusCode) {
		this.statusCode = statusCode;
	}

	public CCDAEffTime getMeasurementTime() {
		return measurementTime;
	}

	public void setMeasurementTime(CCDAEffTime measurementTime) {
		this.measurementTime = measurementTime;
	}

	public CCDAPQ getVsResult() {
		return vsResult;
	}

	public void setVsResult(CCDAPQ vsResult) {
		this.vsResult = vsResult;
	}

	public CCDACode getInterpretationCode() {
		return interpretationCode;
	}

	public void setInterpretationCode(CCDACode interpretationCode) {
		this.interpretationCode = interpretationCode;
	}

	public ArrayList<CCDAPQ> getReferenceValue() {
		return referenceValue;
	}

	public void setReferenceValue(ArrayList<CCDAPQ> rvl) {
		
		if(rvl != null)
			this.referenceValue = rvl;
	}
}
