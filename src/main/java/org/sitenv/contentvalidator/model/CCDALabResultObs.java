package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDALabResultObs {

	private static Logger log = LoggerFactory.getLogger(CCDALabResultObs.class.getName());
	
	private ArrayList<CCDAII>   			templateIds;
	private CCDACode						labCode;
	private CCDACode						statusCode;
	private CCDAEffTime						measurementTime;
	private CCDAPQ							results;
	private CCDACode						resultCode;
	private String 							resultString;
	private CCDACode						interpretationCode;
	private ArrayList<CCDAPQ>				referenceRangeValue;
	private String 							referenceRangeString;
	private ArrayList<CCDANotesActivity>	notesActivity;
	private ArrayList<CCDASpecimen>			specimenType;
	
	private CCDAAuthor	author;
	
	public void getAllNotesActivities(HashMap<String, CCDANotesActivity> results) {
		
		if(notesActivity != null && notesActivity.size() > 0) {
			
			log.info(" Found non-null notes activity ");
			ParserUtilities.populateNotesActiviteis(notesActivity, results);
		}
	}
	
	public static void compareLabResultData(HashMap<String, CCDALabResultObs> refResults, 
			HashMap<String, CCDALabResultObs> subResults, 	ArrayList<ContentValidationResult> results,
			boolean svap2024) {

		log.info(" Start Comparing Lab Result Observations ");
		// For each lab result in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDALabResultObs> ent: refResults.entrySet()) {

			if(subResults.containsKey(ent.getKey())) {

				log.info("Comparing Lab Result Observation ");
				String context = "Lab Result Observation Entry corresponding to the code " + ent.getKey();
				subResults.get(ent.getKey()).compare(ent.getValue(), results, context, svap2024);


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
	
	public void compare(CCDALabResultObs refResult, ArrayList<ContentValidationResult> results , String context,
			boolean svap2024) {
		
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
		if( (refResult.getResults() != null) && (getResults() != null)) {
			log.info(" Comparing PQ ");
			ParserUtilities.compareQuantity(refResult.getResults(), this.getResults(), results, valPQ);
		}
		else if( (refResult.getResults() != null) && 
				  this.getResultString() != null){
			
			// Reference is PQ and Submitted is a String, compare the two.
			log.info(" Comparing PQ with String ");
			Boolean refPQ = true;
			ParserUtilities.compareQuantityWithString(refResult.getResults(), this.getResultString(), results, valPQ, refPQ );
			
		}
		else if( (this.getResults() != null) && 
				  refResult.getResultString() != null) {
			
			// Submitted is PQ and Reference is a String, compare the two.
			log.info(" Comparing PQ with String ");
			Boolean refPQ = false;
			ParserUtilities.compareQuantityWithString(this.getResults(), refResult.getResultString(), results, valPQ, refPQ);
						
		}
		
		String valCode = "Lab Test Value (Code Type - CD/CO) Comparison for " + context;
		// Compare Values.
		String refCode = "";
		String subCode = "";
					
		if(refResult.getResultCode() != null && resultCode != null) {
			log.info(" Comparing Lab codes for value elements ");
			ParserUtilities.compareCode(refResult.getResultCode(), resultCode, results, valCode);	
		}
		else if(refResult.getResultCode() != null) {
			log.info(" Setting Reference code for Value ");
			refCode = refResult.getResultCode().getDisplayName();
		}
		else if(resultCode != null) {
			log.info(" Setting Submitted code for Value ");
			subCode = resultCode.getDisplayName();
		}
		
		String valString = "Value (String Type) Comparison associated with " + context;
		
		if((refResult.getResultString() != null) && (resultString != null)) {
			log.info(" Comparing String code for Value ");
			ParserUtilities.compareString(refResult.getResultString(), resultString, results, valString);
		}
		else if(refResult.getResultString() != null) {
			log.info(" Setting Reference code for Value with string ");
			refCode = refResult.getResultString();
		}
		else if(resultString != null) {
			log.info(" Setting submitted code for Value with string ");
			subCode = resultString;
		}
		
		valString = "Comparing CD/CO/String value with " + context;
		if(!refCode.equalsIgnoreCase("") && !subCode.equalsIgnoreCase("")) {
			log.info("Comparing strings with codes ");
			ParserUtilities.compareString(refCode, subCode, results, valString);
		}
		
		if(svap2024) 
		{
			// Compare Lab Interpretation Codes 
			String interpretCodeVal = "Lab Result Interpretation code element for " + context;
			ParserUtilities.compareCode(refResult.getInterpretationCode(), interpretationCode, results, interpretCodeVal);
					
			// Compare Reference Range
			String refRangeVal = "Lab Result Reference Range element for " + context;
			ParserUtilities.checkQuantities(refResult.getReferenceRangeValue(), referenceRangeValue, results, refRangeVal);
		}
	}

	
	
	public CCDALabResultObs()
	{
		templateIds = new ArrayList<CCDAII>();
		referenceRangeValue = new ArrayList<CCDAPQ>();
		notesActivity = new ArrayList<CCDANotesActivity>();
		specimenType = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** Lab Result Observation ***");
		
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
		
		for(int k = 0; k < referenceRangeValue.size(); k++) {
			referenceRangeValue.get(k).log();
		}
		
		if(notesActivity != null) {
		for(int l = 0; l < notesActivity.size();l++) {
			notesActivity.get(l).log();
		}
		}
		
		if(author != null)
			author.log();
		
		for(int m = 0; m < specimenType.size(); m++) {
			specimenType.get(m).log();
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
		return referenceRangeValue;
	}

	public void setReferenceRange(ArrayList<CCDAPQ> rvl) {
		
		if(rvl != null)
			this.referenceRangeValue = rvl;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public void setReferenceValue(ArrayList<CCDAPQ> referenceValue) {
		this.referenceRangeValue = referenceValue;
	}

	public ArrayList<CCDANotesActivity> getNotesActivity() {
		return notesActivity;
	}

	public void setNotesActivity(ArrayList<CCDANotesActivity> notesActivity) {
		this.notesActivity = notesActivity;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public ArrayList<CCDASpecimen> getSpecimenType() {
		return specimenType;
	}

	public void setSpecimenType(ArrayList<CCDASpecimen> specimenType) {
		this.specimenType = specimenType;
	}

	public ArrayList<CCDAPQ> getReferenceRangeValue() {
		return referenceRangeValue;
	}

	public void setReferenceRangeValue(ArrayList<CCDAPQ> referenceRangeValue) {
		this.referenceRangeValue = referenceRangeValue;
	}

	public String getReferenceRangeString() {
		return referenceRangeString;
	}

	public void setReferenceRangeString(String referenceRangeString) {
		this.referenceRangeString = referenceRangeString;
	}

	public void getAllSpecimens(HashMap<String, CCDASpecimen> specs) {
		
		if(specimenType != null) {
			
			for(CCDASpecimen s: specimenType) {
				
				if(s.getSpecimenType() != null 
						&& !StringUtils.isEmpty(s.getSpecimenType().getCode())) {
					specs.put(s.getSpecimenType().getCode(), s);
				}
				
			}
		}
		
	}
	
}

