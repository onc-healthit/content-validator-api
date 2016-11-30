package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDALabResultObs {

	private static Logger log = Logger.getLogger(CCDALabResultObs.class.getName());
	
	private ArrayList<CCDAII>   			templateIds;
	private CCDACode						labCode;
	private CCDACode						statusCode;
	private CCDAEffTime						measurementTime;
	private CCDAPQ							results;
	private CCDACode						resultCode;
	private String 							resultString;
	private CCDACode						interpretationCode;
	private ArrayList<CCDAPQ>				referenceValue;
	
	public static void compareLabResultData(HashMap<String, CCDALabResultObs> refResults, 
			HashMap<String, CCDALabResultObs> subResults, 	ArrayList<ContentValidationResult> results) {

		log.info(" Start Comparing Lab Result Observations ");
		// For each lab result in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDALabResultObs> ent: refResults.entrySet()) {

			if(subResults.containsKey(ent.getKey())) {

				log.info("Comparing Lab Result Observation ");
				String context = "Lab Result Observation Entry corresponding to the code " + ent.getKey();
				subResults.get(ent.getKey()).compare(ent.getValue(), results, context);


			} else {
				// Error
				String error = "The scenario contains Lab Result data with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the medication data is not present in the reference, 
		if( (refResults == null || refResults.size() == 0) && (subResults != null && subResults.size() > 0) ) {

			// Error
			String error = "The scenario does not require Lab Result data " + 
					" , however there is Lab Result data in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}
	
	public void compare(CCDALabResultObs refResult, ArrayList<ContentValidationResult> results , String context) {
		
		log.info("Comparing Lab Result ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refResult.getTemplateIds(), templateIds, results, context);
		
		// Compare Effective Times
		String elementNameTime = "Effective Time for " + context;
		//ParserUtilities.compareEffectiveTime(refResult.getMeasurementTime(), measurementTime, results, elementNameTime);
		
		// Compare Lab Codes 
		String elementNameVal = "Lab Test code element for " + context;
		ParserUtilities.compareCode(refResult.getLabCode(), labCode, results, elementNameVal);
		
		String statusCodeElem = "Lab Test Status code element for " + context;
		ParserUtilities.justCompareCode(refResult.getStatusCode(), statusCode, results, statusCodeElem);
		
		String valPQ = "Lab Test Value (Quantity - PQ) Comparison for " + context;
		ParserUtilities.compareQuantity(refResult.getResults(), this.getResults(), results, valPQ);
		
		String valCode = "Lab Test Value (Code Type - CD/CO) Comparison for " + context;
		ParserUtilities.compareCode(refResult.getResultCode(), resultCode, results, valCode);
		
		String valString = "Value (String Type) Comparison associated with " + context;
		ParserUtilities.compareString(refResult.getResultString(), resultString, results, valString);
		
	}

	
	
	public CCDALabResultObs()
	{
		templateIds = new ArrayList<CCDAII>();
		referenceValue = new ArrayList<CCDAPQ>();
	}
	
	public void log() {
		
		if(labCode != null)
			log.info(" Lab Result  Code = " + labCode.getCode());
		
		if(statusCode != null)
			log.info(" Lab Result Status  Code = " + statusCode.getCode());
		
		if(measurementTime != null)
			measurementTime.log();
		
		if(results != null)
			results.log();
		
		if(resultCode != null)
			log.info(" Observation Result Code = " + resultCode.getCode());
		
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

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateIds = ids;
	}

	public CCDACode getLabCode() {
		return labCode;
	}

	public void setLabCode(CCDACode labCode) {
		this.labCode = labCode;
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

	public CCDAPQ getResults() {
		return results;
	}

	public void setResults(CCDAPQ results) {
		this.results = results;
	}

	public CCDACode getResultCode() {
		return resultCode;
	}

	public void setResultCode(CCDACode resultCode) {
		this.resultCode = resultCode;
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

	public void setReferenceRange(ArrayList<CCDAPQ> rvl) {
		
		if(rvl != null)
			this.referenceValue = rvl;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public void setReferenceValue(ArrayList<CCDAPQ> referenceValue) {
		this.referenceValue = referenceValue;
	}
	
	
}

