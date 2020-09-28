package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDAMedication {
	
	private static Logger log = Logger.getLogger(CCDAMedication.class.getName());
	

	private ArrayList<CCDAII>     				templateIds;
	private CCDACode                 			sectionCode;
	private ArrayList<CCDAMedicationActivity>  	medActivities;
	
	private CCDAAuthor author;
	
	public void compare(CCDAMedication subMedication, ArrayList<ContentValidationResult> results ) {
		
		// handle section code.
		ParserUtilities.compareCode(sectionCode, subMedication.getSectionCode(), results, "Medication Section");
				
		// Handle Section Template Ids
		ParserUtilities.compareTemplateIds(templateIds, subMedication.getTemplateIds(), results, "Medication Section");
				
		//Compare details
		compareMedicationData(subMedication, results);
	
	}
	
	private void compareMedicationData(CCDAMedication subMedication, ArrayList<ContentValidationResult> results) {
		
		HashMap<String, CCDAMedicationActivity> refActivities = getMedActivitiesMap();
		HashMap<String, CCDAMedicationActivity> subActivities = subMedication.getMedActivitiesMap();
	
		CCDAMedicationActivity.compareMedicationActivityData(refActivities,subActivities,results);
	}
		
	public HashMap<String, CCDAMedicationActivity> getMedActivitiesMap() {
	
		HashMap<String, CCDAMedicationActivity> acts = new HashMap<String, CCDAMedicationActivity>();
		for(int k = 0; k < medActivities.size(); k++) {
			
			if(medActivities.get(k).getConsumable() != null &&
		       medActivities.get(k).getConsumable().getMedcode() != null &&
		       medActivities.get(k).getConsumable().getMedcode().getCode() != null) {
				
				acts.put(medActivities.get(k).getConsumable().getMedcode().getCode(), medActivities.get(k));
			}
					
		}// for
		
		return acts;
	}
	
	public void log() {
		
		if(sectionCode != null)
			log.info(" Medication Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		for(int k = 0; k < medActivities.size(); k++) {
			medActivities.get(k).log();
		}
		
		if(author != null)
			author.log();
	}
	
	public ArrayList<CCDAMedicationActivity> getMedActivities() {
		return medActivities;
	}

	public void setMedActivities(ArrayList<CCDAMedicationActivity> meds) {
		
		if(meds != null)
			this.medActivities = meds;
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateIds = ids;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public CCDAMedication()
	{
		templateIds = new ArrayList<CCDAII>();
		medActivities = new ArrayList<CCDAMedicationActivity>();
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	
}
