package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDACareExperiencePreference {
	
	private static Logger log = LoggerFactory.getLogger(CCDACareExperiencePreference.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            careExperiencePreferenceCode;
	private String								careExperiencePreference;
	private boolean 							careExperiencePreferenceLinkPresent;
	private CCDAAuthor							author;
	
	public CCDACareExperiencePreference() {
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** CCDA Care Experience Observation *** ");
		
		if(careExperiencePreferenceCode != null)
			log.info(" Care Experience Preference Code = " + careExperiencePreferenceCode.getCode());
		
		if(careExperiencePreference != null)
			log.info(" Treatment Preference = " + careExperiencePreference);

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

	public CCDACode getCareExperiencePreferenceCode() {
		return careExperiencePreferenceCode;
	}

	public void setCareExperiencePreferenceCode(CCDACode careExperiencePreferenceCode) {
		this.careExperiencePreferenceCode = careExperiencePreferenceCode;
	}

	public String getCareExperiencePreference() {
		return careExperiencePreference;
	}

	public void setCareExperiencePreference(String careExperiencePreference) {
		this.careExperiencePreference = careExperiencePreference;
	}

	public boolean isCareExperiencePreferenceLinkPresent() {
		return careExperiencePreferenceLinkPresent;
	}

	public void setCareExperiencePreferenceLinkPresent(boolean careExperiencePreferenceLinkPresent) {
		this.careExperiencePreferenceLinkPresent = careExperiencePreferenceLinkPresent;
	}

	
}
