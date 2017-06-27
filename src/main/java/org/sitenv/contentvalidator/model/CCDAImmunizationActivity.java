package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDAImmunizationActivity {
	
	private static Logger log = Logger.getLogger(CCDAImmunizationActivity.class.getName());
	
	private ArrayList<CCDAII>     				templateIds;
	private CCDAEffTime							time;
	private CCDACode							routeCode;
	private CCDACode							approachSiteCode;
	private CCDAPQ								doseQuantity;
	private CCDACode							adminUnitCode;
	private CCDAConsumable						consumable;
	private CCDAOrganization					organization;
	
	public static void compareImmunizationActivityData(HashMap<String, CCDAImmunizationActivity> refActivities, 
			HashMap<String, CCDAImmunizationActivity> subActivities, 	ArrayList<ContentValidationResult> results) {

		log.info(" Start Comparing Immunization Activities ");
		// For each immunization Activity in the Ref Model, check if it is present in the subCCDA Med.
		for(Map.Entry<String, CCDAImmunizationActivity> ent: refActivities.entrySet()) {

			if(subActivities.containsKey(ent.getKey())) {

				log.info("Comparing Immunization Activities ");
				String context = "Immunization Activity Entry corresponding to the code " + ent.getKey();
				subActivities.get(ent.getKey()).compare(ent.getValue(), results, context);


			} else {
				// Error
				String error = "The scenario contains Immunization Activity data for Immunization with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the immunization data is not present in the reference, 
		if( (refActivities == null || refActivities.size() == 0) && (subActivities != null && subActivities.size() > 0) ) {

			// Error
			String error = "The scenario does not require Immunization Activity data " + 
					" , however there is Immunization activity data in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}
	
	
	public void compare(CCDAImmunizationActivity refActivity, ArrayList<ContentValidationResult> results , String context) {
		
		log.info("Comparing Immunization Activity ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refActivity.getTemplateIds(), templateIds, results, context);
		
		// Compare Effective Times
		String elementNameTime = "Immunization Time for " + context;
		//ParserUtilities.compareEffectiveTime(refMedActivity.getDuration(), duration, results, elementNameTime);
		
		// Compare template Ids 
		String consumableElement = "Consumable TemplateIds for " + context;
		ParserUtilities.compareTemplateIds(refActivity.getConsumable().getTemplateIds(), 
				consumable.getTemplateIds(), results, consumableElement);

		// Compare immunization Codes 
		String elementNameVal = "Consumable code element: " + context;
		ParserUtilities.compareCode(refActivity.getConsumable().getMedcode(), consumable.getMedcode(), results, elementNameVal);
	}
	
	public Boolean hasSameMedication(CCDAConsumable refConsumable) {
		
		if(consumable != null &&
		   refConsumable != null) {
			
			return consumable.hasSameMedCode(refConsumable);
			
		}
		
		return false;
	}

	
	
	public void log() {
		
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		if(routeCode != null)
			log.info("Medication Activity Route Code = " + routeCode.getCode());
		
		if(approachSiteCode != null)
			log.info("Medication Activity Approach Site Code = " + approachSiteCode.getCode());
		
		if(adminUnitCode != null)
			log.info("Medication Activity Admin Unit Code = " + adminUnitCode.getCode());
		
		if(time != null) {
			time.log();
		}
		
		if(doseQuantity != null){
			doseQuantity.log();
		}
		
		if(consumable != null) {
			consumable.log();
		}
		
		if(organization != null) {
			organization.log();
		}
	}
	
	public CCDAOrganization getOrganization() {
		return organization;
	}

	public CCDAImmunizationActivity()
	{
		templateIds = new ArrayList<CCDAII>();
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateIds = ids;
	}

	public CCDAEffTime getTime() {
		return time;
	}

	public CCDACode getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(CCDACode routeCode) {
		this.routeCode = routeCode;
	}

	public CCDACode getApproachSiteCode() {
		return approachSiteCode;
	}

	public void setApproachSiteCode(CCDACode approachSiteCode) {
		this.approachSiteCode = approachSiteCode;
	}

	public CCDAPQ getDoseQuantity() {
		return doseQuantity;
	}

	public void setDoseQuantity(CCDAPQ doseQuantity) {
		this.doseQuantity = doseQuantity;
	}

	public CCDACode getAdminUnitCode() {
		return adminUnitCode;
	}

	public void setAdminUnitCode(CCDACode adminUnitCode) {
		this.adminUnitCode = adminUnitCode;
	}

	public CCDAConsumable getConsumable() {
		return consumable;
	}

	public void setConsumable(CCDAConsumable consumable) {
		this.consumable = consumable;
	}

	public void setTime(CCDAEffTime t) {
		
		time = t;
	}

	public void setOrganization(CCDAOrganization representedOrg) {
		organization = representedOrg;
	}
}
