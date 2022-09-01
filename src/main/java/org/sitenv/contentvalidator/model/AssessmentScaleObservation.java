package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

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
		
		for(int k = 0; k < supportingObservations.size(); k++) {
			supportingObservations.get(k).log();
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
	

}
