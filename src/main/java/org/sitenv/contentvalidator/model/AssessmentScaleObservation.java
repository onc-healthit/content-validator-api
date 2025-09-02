package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssessmentScaleObservation {
	
	private static Logger log = LoggerFactory.getLogger(AssessmentScaleObservation.class.getName());
		
	private ArrayList<CCDAII>   			templateIds;
	private CCDACode						assessmentCode;
	private CCDACode						statusCode;
	private CCDAEffTime						assessmentTime;
	private CCDAPQ							resultQuantity;
	private CCDACode						resultCode;
	private String 							resultString;
	private CCDACode						interpretationCode;
	private ArrayList<AssessmentScaleSupportingObs> supportingObservations;
	
	private CCDAAuthor author;
	
	public AssessmentScaleObservation() {
		
		templateIds = new ArrayList<>();
		supportingObservations = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** Assessment Scale Observation ***");
		
		if(assessmentCode != null)
			log.info(" Assessment Code = " + assessmentCode.getCode());
		
		if(statusCode != null)
			log.info(" Lab Result Status  Code = " + statusCode.getCode());
		
		if(assessmentTime != null)
			assessmentTime.log();
		
		if(resultString != null && !resultString.isEmpty())
			log.info(" Assessment Result String = {}", resultString);
		
		if(resultQuantity != null)
			resultQuantity.log();
		
		if(resultCode != null)
			log.info(" Asessment Result Code = " + resultCode.getCode());
		
		if(interpretationCode != null)
			log.info(" Interpretation Code = " + interpretationCode.getCode());
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		if(supportingObservations != null) {
			for(int k = 0; k < supportingObservations.size(); k++) {
				supportingObservations.get(k).log();
			}		
		}
		
		if(author != null)
			author.log();
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getAssessmentCode() {
		return assessmentCode;
	}

	public void setAssessmentCode(CCDACode assessmentCode) {
		this.assessmentCode = assessmentCode;
	}

	public CCDACode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDACode statusCode) {
		this.statusCode = statusCode;
	}

	public CCDAEffTime getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(CCDAEffTime assessmentTime) {
		this.assessmentTime = assessmentTime;
	}

	public CCDAPQ getResultQuantity() {
		return resultQuantity;
	}

	public void setResultQuantity(CCDAPQ resultQuantity) {
		this.resultQuantity = resultQuantity;
	}

	public CCDACode getResultCode() {
		return resultCode;
	}

	public void setResultCode(CCDACode resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public CCDACode getInterpretationCode() {
		return interpretationCode;
	}

	public void setInterpretationCode(CCDACode interpretationCode) {
		this.interpretationCode = interpretationCode;
	}

	public ArrayList<AssessmentScaleSupportingObs> getSupportingObservations() {
		return supportingObservations;
	}

	public void setSupportingObservations(ArrayList<AssessmentScaleSupportingObs> supportingObservations) {
		this.supportingObservations = supportingObservations;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public static void compare(HashMap<String, AssessmentScaleObservation> refSdohData,
			HashMap<String, AssessmentScaleObservation> subSdohData, ArrayList<ContentValidationResult> results) {
		
		String context = " Comparing Assessment Scale Observation Data ";
		// Check only the reference ones
		for(Map.Entry<String,AssessmentScaleObservation> entry: refSdohData.entrySet()) {
			
			if(subSdohData.containsKey(entry.getKey())) {
				
				// Since the observation was found, compare other data elements.
				log.info(" Comparing Assessment Scale Observation ");
				String compContext = " Comparing Assessment Scale Observation data for code " + entry.getKey();
				entry.getValue().compare(subSdohData.get(entry.getKey()), compContext, results);
				
			}
			else {
				
				String error = "The scenario contains Assessment data with code " + entry.getKey() + 
						" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
		}
		
	}

	private void compare(AssessmentScaleObservation subAssessmentScaleObservation, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		String elementName = compContext + " , Template Id Comparison : ";

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateIds, subAssessmentScaleObservation.getTemplateIds(), results, elementName);

		// Compare Assessment  Codes 
		String elementNameCode = compContext + " , Assessment Code Element Comparison : ";
		ParserUtilities.compareCode(assessmentCode, subAssessmentScaleObservation.getAssessmentCode(), results, elementNameCode);
		 
		// Compare Assessment  Codes 
		String elementStatus = compContext + " , Assessment Status Element Comparison : ";
	//	ParserUtilities.compareCode(statusCode, subAssessmentScaleObservation.getStatusCode(), results, elementStatus);
				 	 
		// Compare Assessment  Codes 
		String elementTime = compContext + " , Assessment Time Comparison : ";
		ParserUtilities.compareEffectiveTime(assessmentTime, subAssessmentScaleObservation.getAssessmentTime(), results, elementTime);
		
		// Compare Assessment Value 
		String elementVal = compContext + " , Assessment Value Comparison : ";
		ParserUtilities.compareString(resultString, subAssessmentScaleObservation.getResultString(), results, elementVal);
		
		for(AssessmentScaleSupportingObs sobs : supportingObservations) {
			
			subAssessmentScaleObservation.compareSupportingObs(sobs, results, compContext);
			
		}
	}

	private void compareSupportingObs(AssessmentScaleSupportingObs referenceObs, ArrayList<ContentValidationResult> results,
			String compContext) {
		
		Boolean found = false;
		// Check the submitted file for presence of the reference obs
		if(supportingObservations != null) {
			
			for(AssessmentScaleSupportingObs supObs : supportingObservations) {
				
				if(supObs.getSupportingObsCode() != null && supObs.getSupportingObsCode().getCode() != null 
						&& referenceObs.getSupportingObsCode() != null && referenceObs.getSupportingObsCode().getCode() != null
						&& supObs.getSupportingObsCode().getCode().contentEquals(referenceObs.getSupportingObsCode().getCode())) {
				
					compContext += " Comparing Assessment Scale Supporting Observation with Code " + referenceObs.getSupportingObsCode().getCode();

					referenceObs.compare(supObs, results, compContext);
					found = true;
				}
				
			}
			
			if(!found) {
				
				String error = "The scenario contains Assessment Scale Supporting Observaton data with code ";
				String code = (referenceObs.getSupportingObsCode() != null)?(referenceObs.getSupportingObsCode().getCode()):"Unknown Code";
				error += code +
						" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}
		
		
	}
	

}
