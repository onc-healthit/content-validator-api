package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

public class CCDADischargeMedication {
	
	private static Logger log = Logger.getLogger(CCDAMedication.class.getName());
		
	private ArrayList<CCDAII>     				templateIds;
	private CCDACode                 			sectionCode;
	private ArrayList<CCDAMedicationActivity>  	medActivities;
		
	public void compare(CCDADischargeMedication subMedication, ArrayList<ContentValidationResult> results ) {
			
		// handle section code.
		ParserUtilities.compareCode(sectionCode, subMedication.getSectionCode(), results, "Discharge Medication Section");

		// Handle Section Template Ids
		ParserUtilities.compareTemplateIds(templateIds, subMedication.getTemplateIds(), results, "Discharge Medication Section");

		//Compare details
		compareMedicationData(subMedication, results);

	}

	private void compareMedicationData(CCDADischargeMedication subMedication, ArrayList<ContentValidationResult> results) {

		HashMap<String, CCDAMedicationActivity> refActivities = getMedActivitiesMap();
		HashMap<String, CCDAMedicationActivity> subActivities = subMedication.getMedActivitiesMap();

		// For each medication Activity in the Ref Model, check if it is present in the subCCDA Med.
		for(Map.Entry<String, CCDAMedicationActivity> ent: refActivities.entrySet()) {

			if(subActivities.containsKey(ent.getKey())) {

				log.info("Comparing Medication Activities ");
				String context = "Medication Activity Entry corresponding to the code " + ent.getKey();
				subActivities.get(ent.getKey()).compare(ent.getValue(), results, context);


			} else {
				// Error
				String error = "The scenario contains Medication Activity data for Medication with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the medication data is not present in the reference, 
		if( (refActivities.size() == 0) && (subActivities.size() > 0) ) {

			// Error
			String error = "The scenario does not require Medication Activity data " + 
					" , however there is medication activity data in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}

	}

	private HashMap<String, CCDAMedicationActivity> getMedActivitiesMap() {

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

	public CCDADischargeMedication()
	{
		templateIds = new ArrayList<CCDAII>();
		medActivities = new ArrayList<CCDAMedicationActivity>();
	}

}
