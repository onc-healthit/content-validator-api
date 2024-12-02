package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDATreatmentInterventionPreference {
	
	private static Logger log = LoggerFactory.getLogger(CCDATreatmentInterventionPreference.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            treatmentPreferenceCode;
	private String								treatmentPreference;
	private CCDAAuthor							author;
	
	public CCDATreatmentInterventionPreference() {
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** CCDA Treatment Preference Observation *** ");
		
		if(treatmentPreferenceCode != null)
			log.info(" Treatment Preference Code = " + treatmentPreferenceCode.getCode());
		
		if(treatmentPreferenceCode != null)
			log.info(" Treatment Preference = " + treatmentPreference);

		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
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

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public CCDACode getTreatmentPreferenceCode() {
		return treatmentPreferenceCode;
	}

	public void setTreatmentPreferenceCode(CCDACode treatmentPreferenceCode) {
		this.treatmentPreferenceCode = treatmentPreferenceCode;
	}

	public String getTreatmentPreference() {
		return treatmentPreference;
	}

	public void setTreatmentPreference(String treatmentPreference) {
		this.treatmentPreference = treatmentPreference;
	}

	
	
}
