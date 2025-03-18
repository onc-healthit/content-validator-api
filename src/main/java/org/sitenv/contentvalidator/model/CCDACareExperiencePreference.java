package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
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

	public static void compare(HashMap<String, CCDACareExperiencePreference> refPreferences,
			HashMap<String, CCDACareExperiencePreference> subPreferences,
			ArrayList<ContentValidationResult> results) {
		
		String context = " Comparing Care Experience Preference Data ";
		// Check only the reference ones
		for(Map.Entry<String,CCDACareExperiencePreference> entry: refPreferences.entrySet()) {
			
			if(subPreferences.containsKey(entry.getKey())) {
				
				// Since the observation was found, compare other data elements.
				log.info(" Comparing Care Experience Preference Data Observation ");
				String compContext = " Comparing Care Experience Preference data for code " + entry.getKey();
				entry.getValue().compare(subPreferences.get(entry.getKey()), compContext, results);
				
			}
			else {
				
				String error = "The scenario contains Care Experience Preference data with code " + entry.getKey() + 
						" , however there is no matching care experience preference value in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
		}
		
	}

	private void compare(CCDACareExperiencePreference subPreference, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		String elementName = compContext + " , Template Id Comparison : ";

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateIds, subPreference.getTemplateIds(), results, elementName);

		// Check for Treatment Preference Value to be present
		String elementNameVal = compContext + " , Treatment Preference Value Element Comparison : ";
		if(subPreference.getCareExperiencePreference() == null || 
				subPreference.getCareExperiencePreference().isEmpty())
		{
			String error = "The scenario contains Care Experience Preference data with code " + subPreference.getCareExperiencePreferenceCode().getCode() + 
					" , however there is no matching data in the submitted CCDA.";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}		 
		
	}
}
