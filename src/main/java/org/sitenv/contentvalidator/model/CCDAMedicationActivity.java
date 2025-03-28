package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDAMedicationActivity {

	private static Logger log = LoggerFactory.getLogger(CCDAMedicationActivity.class.getName());
	
	private ArrayList<CCDAII>     				templateIds;
	private CCDAEffTime							duration;
	private CCDAFrequency						frequency;
	private CCDACode							routeCode;
	private CCDACode							approachSiteCode;
	private CCDAPQ								doseQuantity;
	private CCDAPQ								rateQuantity;
	private CCDACode							adminUnitCode;
	private CCDAConsumable						consumable;
	private ArrayList<CCDAIndication>			indications;
	private ArrayList<CCDAMedicationDispense>	medicationDispenses;
	private ArrayList<CCDAMedicationFreeSigText> medicationFreeSigTexts;
	private ArrayList<CCDAMedicationAdherence>   medicationAdherences; 
	
	private CCDAAuthor author;
	
	public static void compareMedicationActivityData(HashMap<String, CCDAMedicationActivity> refActivities, 
			HashMap<String, CCDAMedicationActivity> subActivities, 	ArrayList<ContentValidationResult> results, boolean svap2024) {

		log.info(" Start Comparing Medication Activities ");
		// For each medication Activity in the Ref Model, check if it is present in the subCCDA Med.
		for(Map.Entry<String, CCDAMedicationActivity> ent: refActivities.entrySet()) {

			if(subActivities.containsKey(ent.getKey())) {

				log.info("Comparing Medication Activities ");
				String context = "Medication Activity Entry corresponding to the code " + ent.getKey();
				subActivities.get(ent.getKey()).compare(ent.getValue(), results, context, svap2024);


			} else {
				// Error
				String error = "The scenario contains Medication Activity data for Medication with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the medication data is not present in the reference, 
		if( (refActivities == null || refActivities.size() == 0) && (subActivities != null && subActivities.size() > 0) ) {

			// Error
			String error = "The scenario does not require Medication Activity data " + 
					" , however there is medication activity data in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}
	
	
	public void compare(CCDAMedicationActivity refMedActivity, ArrayList<ContentValidationResult> results , String context, boolean svap2024) {
		
		log.info("Comparing Medication Activity ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refMedActivity.getTemplateIds(), templateIds, results, context);
		
		// Compare Effective Times
		String elementNameTime = "Medication Duration for " + context;
		//ParserUtilities.compareEffectiveTime(refMedActivity.getDuration(), duration, results, elementNameTime);
		
		// Compare template Ids 
		String consumableElement = "Consumable TemplateIds for " + context;
		ParserUtilities.compareTemplateIds(refMedActivity.getConsumable().getTemplateIds(), 
				consumable.getTemplateIds(), results, consumableElement);

		// Compare Med Codes 
		String elementNameVal = "Consumable code element: " + context;
		ParserUtilities.compareCode(refMedActivity.getConsumable().getMedcode(), consumable.getMedcode(), results, elementNameVal);
		
		//Compare Medication Sigs
		if(svap2024) {
			String freeTextSig = " Free Text Sigs for " + context;
			if(refMedActivity.getMedicationFreeSigTexts() != null) {
				compareFreeTextSigs(refMedActivity.getMedicationFreeSigTexts(), this.medicationFreeSigTexts, freeTextSig, results);
			}
			
			String medAdherence = " Medication Adherence for " + context;
			if(refMedActivity.getMedicationAdherences() != null) {
				compareMedicationAdherences(refMedActivity.getMedicationAdherences(), this.medicationAdherences, medAdherence, results);
			}
		}
	}
	
	
	
	private void compareMedicationAdherences(ArrayList<CCDAMedicationAdherence> refAdherences,
			ArrayList<CCDAMedicationAdherence> subAdherences, String medAdherenceContext,
			ArrayList<ContentValidationResult> results) {
		
		if(refAdherences != null && subAdherences != null 
				&& refAdherences.size() == subAdherences.size()) {
			
			// Compare Codes 
			for(CCDAMedicationAdherence refAd : refAdherences) {
				
				refAd.compare(subAdherences, medAdherenceContext, results);
				
			}
		}
		else if(refAdherences != null && refAdherences.size() > 0 && 
				(subAdherences == null || subAdherences.size() < refAdherences.size())) {
			// Error
			String error = "The scenario contains a total of " + refAdherences.size() + medAdherenceContext + 
					" , however the data in the submitted CCDA does not match.";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}


	private void compareFreeTextSigs(ArrayList<CCDAMedicationFreeSigText> refFreeTextSigs,
			ArrayList<CCDAMedicationFreeSigText> subFreeTextSigs, String freeTextSig,
			ArrayList<ContentValidationResult> results) {
		
		if(refFreeTextSigs != null && subFreeTextSigs != null 
				&& refFreeTextSigs.size() == subFreeTextSigs.size()) {
			
			// Compare Codes 
			for(CCDAMedicationFreeSigText refText : refFreeTextSigs) {
				
				if(!isCodePresent(refText.getMedicationFreeSigTextCode(), subFreeTextSigs)) {
				
					String error = "The scenario contains code of " + (refText.getMedicationFreeSigTextCode() != null?refText.getMedicationFreeSigTextCode().getCode():"Unknown") + " in " + freeTextSig + 
							" , however there is no data in the submitted CCDA that matches the free text sig code.";
					ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
					results.add(rs);
				}
				
				if(!isTextPresent(subFreeTextSigs)) {					
					String error = "The scenario contains text in " + freeTextSig + 
							" , however there is no data in the submitted CCDA that has the free text sig instructions.";
					ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
					results.add(rs);
				}
			}
		}
		else if(refFreeTextSigs != null && refFreeTextSigs.size() > 0 && 
				(subFreeTextSigs == null || subFreeTextSigs.size() < refFreeTextSigs.size())) {
			// Error
			String error = "The scenario contains a total of " + refFreeTextSigs.size() + freeTextSig + 
					" , however the data in the submitted CCDA does not match.";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
		
	}
	
	


	private Boolean isTextPresent(ArrayList<CCDAMedicationFreeSigText> subFreeTextSigs) {
		
		if(subFreeTextSigs != null) {
			for(CCDAMedicationFreeSigText subText : subFreeTextSigs) {
				
				if(subText.getFreeSigText() == null || 
						subText.getFreeSigText().getValue() == null || 
						subText.getFreeSigText().getValue().isEmpty()) {
					return false;
				}
			}
		}
		
		return true;
	}


	private Boolean isCodePresent(CCDACode medicationFreeSigTextCode, ArrayList<CCDAMedicationFreeSigText> subFreeTextSigs) {
		
		if(subFreeTextSigs != null) {
			for(CCDAMedicationFreeSigText subText : subFreeTextSigs) {
				
				if(subText.getMedicationFreeSigTextCode() != null && 
						subText.getMedicationFreeSigTextCode().getCode() != null && 
						subText.getMedicationFreeSigTextCode().getCode().contentEquals(medicationFreeSigTextCode.getCode())) {
					return true;
				}
			}
		}
		
		return false;
		
	}


	public Boolean hasSameMedication(CCDAConsumable refConsumable) {
		
		if(consumable != null &&
		   refConsumable != null) {
			
			return consumable.hasSameMedCode(refConsumable);
			
		}
		
		return false;
	}
	
	public void log() {
		
		log.info(" *** Medication Activity *** ");
			
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
		
		if(author != null)
			author.log();
		
		if(indications != null) {
		for(int k = 0; k < indications.size(); k++) {
			indications.get(k).log();
		}
		}
		
		if(medicationDispenses != null) {
		for(int p = 0; p < medicationDispenses.size(); p++) {
			medicationDispenses.get(p).log();
		}
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
		indications = new ArrayList<>();
		medicationDispenses = new ArrayList<>();
		medicationFreeSigTexts = new ArrayList<>();
		medicationAdherences = new ArrayList<>();
	}


	public CCDAAuthor getAuthor() {
		return author;
	}


	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}


	public ArrayList<CCDAIndication> getIndications() {
		return indications;
	}


	public void setIndications(ArrayList<CCDAIndication> indications) {
		this.indications = indications;
	}


	public ArrayList<CCDAMedicationDispense> getMedicationDispenses() {
		return medicationDispenses;
	}


	public void setMedicationDispenses(ArrayList<CCDAMedicationDispense> medicationDispenses) {
		this.medicationDispenses = medicationDispenses;
	}


	public ArrayList<CCDAMedicationFreeSigText> getMedicationFreeSigTexts() {
		return medicationFreeSigTexts;
	}


	public void setMedicationFreeSigTexts(ArrayList<CCDAMedicationFreeSigText> medicationFreeSigTexts) {
		this.medicationFreeSigTexts = medicationFreeSigTexts;
	}


	public ArrayList<CCDAMedicationAdherence> getMedicationAdherences() {
		return medicationAdherences;
	}


	public void setMedicationAdherences(ArrayList<CCDAMedicationAdherence> medicationAdherences) {
		this.medicationAdherences = medicationAdherences;
	}
	
	
	
}
