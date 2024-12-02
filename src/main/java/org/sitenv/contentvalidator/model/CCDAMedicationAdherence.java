package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAMedicationAdherence {
	
	private static Logger log = LoggerFactory.getLogger(CCDAMedicationAdherence.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            adherenceCode;
	private CCDACode 							statusCode;
	private CCDAEffTime          				effTime;
	private CCDACode							adherenceValue;
	private ArrayList<CCDAAssignedEntity>		informants;
	
	public CCDAMedicationAdherence() {
		templateIds = new ArrayList<>();
		informants = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** CCDA Medication Adherence *** ");
		
		if(adherenceCode != null)
			log.info(" Medication Adherence Code = " + adherenceCode.getCode());
		
		if(statusCode != null)
			log.info(" Medication Adherence Status Code = " + statusCode.getCode());
		
		if(effTime != null)
			effTime.log();
		
		if(adherenceValue != null)
			log.info(" Medication Adherence Value = " + adherenceValue.getCode());
		

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

	public CCDACode getAdherenceCode() {
		return adherenceCode;
	}

	public void setAdherenceCode(CCDACode adherenceCode) {
		this.adherenceCode = adherenceCode;
	}

	public CCDACode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDACode statusCode) {
		this.statusCode = statusCode;
	}

	public CCDAEffTime getEffTime() {
		return effTime;
	}

	public void setEffTime(CCDAEffTime effTime) {
		this.effTime = effTime;
	}

	public CCDACode getAdherenceValue() {
		return adherenceValue;
	}

	public void setAdherenceValue(CCDACode adherenceValue) {
		this.adherenceValue = adherenceValue;
	}

	public ArrayList<CCDAAssignedEntity> getInformants() {
		return informants;
	}

	public void setInformants(ArrayList<CCDAAssignedEntity> informants) {
		this.informants = informants;
	}

	
}
