package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAMedicationAdherence {
	
	private static Logger log = LoggerFactory.getLogger(CCDAMedicationAdherence.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            adherenceCode;
	private CCDADataElement						text;
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

	public CCDADataElement getText() {
		return text;
	}

	public void setText(CCDADataElement text) {
		this.text = text;
	}

	public void compare(ArrayList<CCDAMedicationAdherence> subAdherences, String medAdherenceContext,
			ArrayList<ContentValidationResult> results) {
		
		boolean foundCode = false;
		boolean foundValue = false;
		// Compare Codes 	
		for(CCDAMedicationAdherence subMed : subAdherences) {			
			
			if(!foundCode && ParserUtilities.compareCodesAndTranlations(this.adherenceCode, subMed.getAdherenceCode())) {
				foundCode = true;
			}
			
			if(!foundValue && ParserUtilities.compareCodesAndTranlations(this.adherenceValue, subMed.getAdherenceValue())) {
				foundValue = true;
			}
				
			
		}
		
		if(!foundCode) {
			String error =  "Expected code element of: " + this.adherenceCode.getCode() + "for " + medAdherenceContext +
					" , however the data in the submitted CCDA does not match.";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
		if(!foundValue) {
			String error =  "Expected value element of: " + this.adherenceValue.getCode() + "for " + medAdherenceContext +
					" , however the data in the submitted CCDA does not match.";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}
	
	
	
}
