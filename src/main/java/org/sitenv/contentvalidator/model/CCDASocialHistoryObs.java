package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDASocialHistoryObs {

	private static Logger log = LoggerFactory.getLogger(CCDASocialHistoryObs.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            socialHistoryObsCode;
	private CCDACode							socialHistoryObsValue;
	private CCDAEffTime							observationTime;
	private ArrayList<AssessmentScaleObservation> assessmentScaleObservations;
	
	private CCDAAuthor	author;

	public CCDASocialHistoryObs() {
		templateIds = new ArrayList<CCDAII>();
		assessmentScaleObservations = new ArrayList<>();
	}

	public void log() {

		if(socialHistoryObsCode != null)
			log.info(" Social History Obs Code = " + socialHistoryObsCode.getCode());
		
		if(socialHistoryObsValue != null)
			log.info(" Sexual Orientation Value = " + socialHistoryObsValue.getCode());

		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}

		for(int k = 0; k < assessmentScaleObservations.size(); k++) {
			assessmentScaleObservations.get(k).log();
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

	public CCDACode getSocialHistoryObsCode() {
		return socialHistoryObsCode;
	}

	public void setSocialHistoryObsCode(CCDACode socialHistoryObsCode) {
		this.socialHistoryObsCode = socialHistoryObsCode;
	}

	public CCDACode getSocialHistoryObsValue() {
		return socialHistoryObsValue;
	}

	public void setSocialHistoryObsValue(CCDACode socialHistoryObsValue) {
		this.socialHistoryObsValue = socialHistoryObsValue;
	}

	public ArrayList<AssessmentScaleObservation> getAssessmentScaleObservations() {
		return assessmentScaleObservations;
	}

	public void setAssessmentScaleObservations(ArrayList<AssessmentScaleObservation> assessmentScaleObservations) {
		this.assessmentScaleObservations = assessmentScaleObservations;
	}

	
}
