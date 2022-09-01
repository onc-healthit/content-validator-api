package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssessmentScaleSupportingObs {
	
	private static Logger log = LoggerFactory.getLogger(AssessmentScaleObservation.class.getName());

	private ArrayList<CCDAII>   			templateIds;
	private CCDACode						supportingObsCode;
	private CCDACode						statusCode;
	private CCDAEffTime						supportingObsTime;
	private CCDAPQ							resultQuantity;
	private CCDACode						resultCode;
	private String 							resultString;
	
	public AssessmentScaleSupportingObs() {
		
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		if(supportingObsCode != null)
			log.info(" Supporting Obs Code = " + supportingObsCode.getCode());
		
		if(statusCode != null)
			log.info(" Status  Code = " + statusCode.getCode());
		
		if(supportingObsTime != null)
			supportingObsTime.log();
		
		if(resultString != null && !resultString.isEmpty())
			log.info(" Supporting Obs Result String = {}", resultString);
		
		if(resultQuantity != null)
			resultQuantity.log();
		
		if(resultCode != null)
			log.info(" Asessment Result Code = " + resultCode.getCode());
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDACode statusCode) {
		this.statusCode = statusCode;
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

	public CCDACode getSupportingObsCode() {
		return supportingObsCode;
	}

	public void setSupportingObsCode(CCDACode supportingObsCode) {
		this.supportingObsCode = supportingObsCode;
	}

	public CCDAEffTime getSupportingObsTime() {
		return supportingObsTime;
	}

	public void setSupportingObsTime(CCDAEffTime supportingObsTime) {
		this.supportingObsTime = supportingObsTime;
	}
	
	
}
