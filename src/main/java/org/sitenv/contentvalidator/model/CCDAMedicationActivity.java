package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;

public class CCDAMedicationActivity {

	private static Logger log = Logger.getLogger(CCDAMedicationActivity.class.getName());
	
	private ArrayList<CCDAII>     				templateIds;
	private CCDAEffTime							duration;
	private CCDAFrequency						frequency;
	private CCDACode							routeCode;
	private CCDACode							approachSiteCode;
	private CCDAPQ								doseQuantity;
	private CCDAPQ								rateQuantity;
	private CCDACode							adminUnitCode;
	private CCDAConsumable						consumable;
	
	public void compare(CCDAMedicationActivity refMedActivity, ArrayList<ContentValidationResult> results , String context) {
		
		log.info("Comparing Medication Activity ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refMedActivity.getTemplateIds(), templateIds, results, context);
		
		// Compare Effective Times
		String elementNameTime = "Medication Duration for " + context;
		ParserUtilities.compareEffectiveTime(refMedActivity.getDuration(), duration, results, elementNameTime);
		
		// Compare template Ids 
		String consumableElement = "Consumable TemplateIds for " + context;
		ParserUtilities.compareTemplateIds(refMedActivity.getConsumable().getTemplateIds(), 
				consumable.getTemplateIds(), results, consumableElement);

		// Compare Med Codes 
		String elementNameVal = "Consumable code element: " + context;
		ParserUtilities.compareCode(refMedActivity.getConsumable().getMedcode(), consumable.getMedcode(), results, elementNameVal);
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
		
		if(duration != null) {
			duration.log();
		}
		
		if(frequency != null) {
			frequency.log();
		}
		
		if(doseQuantity != null){
			doseQuantity.log();
		}
		
		if(rateQuantity != null){
			rateQuantity.log();
		}
		
		if(consumable != null) {
			consumable.log();
		}
	}
	
	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateIds = ids;
	}

	public CCDAEffTime getDuration() {
		return duration;
	}

	public void setDuration(CCDAEffTime duration) {
		this.duration = duration;
	}

	public CCDAFrequency getFrequency() {
		return frequency;
	}

	public void setFrequency(CCDAFrequency ccdaFrequency) {
		this.frequency = ccdaFrequency;
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

	public CCDAPQ getRateQuantity() {
		return rateQuantity;
	}

	public void setRateQuantity(CCDAPQ rateQuantity) {
		this.rateQuantity = rateQuantity;
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

	public CCDAMedicationActivity()
	{
		templateIds = new ArrayList<CCDAII>();
	}
	
}
