package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDATreatmentInterventionPreference {
	
	private static Logger log = LoggerFactory.getLogger(CCDATreatmentInterventionPreference.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            treatmentPreferenceCode;
	private Boolean								treatmentLinkPresent;
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

	public Boolean getTreatmentLinkPresent() {
		return treatmentLinkPresent;
	}

	public void setTreatmentLinkPresent(Boolean treatmentLinkPresent) {
		this.treatmentLinkPresent = treatmentLinkPresent;
	}

	public static void compare(HashMap<String, CCDATreatmentInterventionPreference> refTreatments,
			HashMap<String, CCDATreatmentInterventionPreference> subTreatments,
			ArrayList<ContentValidationResult> results) {
		
		String context = " Comparing Treatment Intervention Preference Data ";
		// Check only the reference ones
		for(Map.Entry<String,CCDATreatmentInterventionPreference> entry: refTreatments.entrySet()) {
			
			if(subTreatments.containsKey(entry.getKey())) {
				
				// Since the observation was found, compare other data elements.
				log.info(" Comparing Treatment Intervention Preference Data Observation ");
				String compContext = " Comparing Treatment Intervention Preference data for code " + entry.getKey();
				entry.getValue().compare(subTreatments.get(entry.getKey()), compContext, results);
				
			}
			else {
				
				String error = "The scenario contains Treatment Intervention Preference data with code " + entry.getKey() + 
						" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
		}
		
	}

	private void compare(CCDATreatmentInterventionPreference subTreatmentPreference, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		String elementName = compContext + " , Template Id Comparison : ";

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateIds, subTreatmentPreference.getTemplateIds(), results, elementName);

		// Check for Treatment Preference Value to be present
		String elementNameVal = compContext + " , Treatment Preference Value Element Comparison : ";
		if(subTreatmentPreference.getTreatmentPreference() == null || 
				subTreatmentPreference.getTreatmentPreference().isEmpty())
		{
			String error = "The scenario contains Treatment Intervention Preference data with code " + subTreatmentPreference.getTreatmentPreferenceCode().getCode() + 
					" , however there is no matching treatment intervention preference value in the submitted CCDA.";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}		 
		
	}
	
}
