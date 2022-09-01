package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

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

	
}
