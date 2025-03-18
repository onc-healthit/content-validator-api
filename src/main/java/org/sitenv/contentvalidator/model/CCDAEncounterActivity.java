package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDAEncounterActivity {

	private static Logger log = LoggerFactory.getLogger(CCDAEncounterActivity.class.getName());
	
	private ArrayList<CCDAII>                 templateId;
	private ArrayList<CCDAII>	              encounterIdentifier;
	private CCDACode                          encounterTypeCode;
	private CCDAEffTime                       effectiveTime;
	private ArrayList<CCDAEncounterDiagnosis> diagnoses;
	private ArrayList<CCDAServiceDeliveryLoc> sdLocs;
	private ArrayList<CCDAProblemObs>         indications;
	private ArrayList<CCDANotesActivity>	  notesActivity;
	private CCDACode						  dischargeDisposition;
	
	private CCDAAuthor	author;
	
	public void log() {
		
		log.info("*** Encounter Activity ***");
		
		if(encounterTypeCode != null)
			log.info("Encounter Activity Type Code = " + encounterTypeCode.getCode());
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		if(effectiveTime != null) {
			effectiveTime.log();
		}
		
		
		for(int l = 0; l < diagnoses.size(); l++) {
			diagnoses.get(l).log();
		}
		
		for(int m = 0; m < sdLocs.size(); m++) {
			sdLocs.get(m).log();
		}
		
		for(int n = 0; n < indications.size(); n++) {
			indications.get(n).log();
		}
		
		if(notesActivity != null) {
		for(int p = 0; p < notesActivity.size(); p++) {
			notesActivity.get(p).log();
		}
		}
		
		if(author != null)
			author.log();
		
		if(dischargeDisposition != null)
			dischargeDisposition.log();
	}
	
	public void getAllNotesActivities(HashMap<String, CCDANotesActivity> results) {
		
		if(notesActivity != null && notesActivity.size() > 0) {
			
			log.info(" Found non-null notes activity ");
			ParserUtilities.populateNotesActiviteis(notesActivity, results);
		}
	}
	
	public ArrayList<CCDAProblemObs> getIndications() {
		return indications;
	}

	public void setIndications(ArrayList<CCDAProblemObs> inds) {
		if(inds != null)
			this.indications = inds;
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(ArrayList<CCDAII> ids) {
		if(ids != null)
			this.templateId = ids;
	}

	public CCDACode getEncounterTypeCode() {
		return encounterTypeCode;
	}

	public void setEncounterTypeCode(CCDACode encounterTypeCode) {
		this.encounterTypeCode = encounterTypeCode;
	}

	public CCDAEffTime getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(CCDAEffTime effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public ArrayList<CCDAEncounterDiagnosis> getDiagnoses() {
		return diagnoses;
	}

	public void setDiagnoses(ArrayList<CCDAEncounterDiagnosis> diag) {
		
		if(diag != null)
			this.diagnoses = diag;
	}

	public ArrayList<CCDAServiceDeliveryLoc> getSdLocs() {
		return sdLocs;
	}

	public void setSdLocs(ArrayList<CCDAServiceDeliveryLoc> sdl) {
		if(sdl != null)
			this.sdLocs = sdl;
	}

	public ArrayList<CCDANotesActivity> getNotesActivity() {
		return notesActivity;
	}

	public void setNotesActivity(ArrayList<CCDANotesActivity> notesActivity) {
		this.notesActivity = notesActivity;
	}

	public CCDAEncounterActivity()
	{
		diagnoses = new ArrayList<CCDAEncounterDiagnosis>();
		templateId = new ArrayList<CCDAII>();
		indications = new ArrayList<CCDAProblemObs>();
		sdLocs = new ArrayList<CCDAServiceDeliveryLoc>();
		notesActivity = new ArrayList<CCDANotesActivity>();
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public CCDACode getDischargeDisposition() {
		return dischargeDisposition;
	}

	public void setDischargeDisposition(CCDACode dischargeDisposition) {
		this.dischargeDisposition = dischargeDisposition;
	}

	public ArrayList<CCDAII> getEncounterIdentifier() {
		return encounterIdentifier;
	}

	public void setEncounterIdentifier(ArrayList<CCDAII> encounterIdentifier) {
		this.encounterIdentifier = encounterIdentifier;
	}

	public String getEncounterDiagnosisCode() {
		
		if(diagnoses != null) {
			
			for(CCDAEncounterDiagnosis diagnosis : diagnoses ) 
			{
				return diagnosis.getEncounterDiagnosisCode();
			}
			
		}
		
		return null;
		
	}

	public static void compareEncounterActivity(
			HashMap<String, CCDAEncounterActivity> refEncounters,
			HashMap<String, CCDAEncounterActivity> subEncounters, 
			ArrayList<ContentValidationResult> results) {
		
		// Check only the reference ones
		for(Map.Entry<String,CCDAEncounterActivity> entry: refEncounters.entrySet()) {
					
			if(subEncounters.containsKey(entry.getKey())) {
						
				// Since the EncounterActivity was found, compare other data elements.
				log.info(" Comparing Encounter Activity ");
				String compContext = "EncounterActivity data for code " + entry.getKey();
				entry.getValue().compare(subEncounters.get(entry.getKey()), compContext, results);
						
			}
			else {
						
				String error = "The scenario contains EncounterActivity data with a diganosis code " + entry.getKey() + 
								" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
					
		} // for
		
	}

	private void compare(CCDAEncounterActivity subEncounterActivity, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		log.info("Comparing Encounter Activity");
		
		if(subEncounterActivity.getEncounterIdentifier() == null ||
				subEncounterActivity.getEncounterIdentifier().size() == 0) 
		{
			String context = compContext + " : The scenario requires EncounterIdentifiers data, but submitted file does have EncounterIdentifier data.";
			ContentValidationResult rs = new ContentValidationResult(context, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
		// Compare Encounter Type Codes 
		String elementNameVal = "EncounterType code element for " + compContext;
		ParserUtilities.compareCode(this.getEncounterTypeCode(), subEncounterActivity.getEncounterTypeCode(), results, elementNameVal);
		
		HashMap<String, CCDAServiceDeliveryLoc> refLocs = this.getAllFacilityLocations();
		HashMap<String, CCDAServiceDeliveryLoc> subLocs = subEncounterActivity.getAllFacilityLocations();
		
		CCDAServiceDeliveryLoc.compare(refLocs, subLocs, results, compContext);
	}
	
	public HashMap<String, CCDAServiceDeliveryLoc> getAllFacilityLocations() {
		
		HashMap<String, CCDAServiceDeliveryLoc> locs = new HashMap<>();
		if(sdLocs != null) {
			for(CCDAServiceDeliveryLoc loc: sdLocs) {
				
				if(loc.getLocationCode() != null && loc.getLocationCode().getCode() != null)
				{
					locs.put(loc.getLocationCode().getCode(), loc);				
				}
				
			}
			
		}
		
		return locs;
		
	}
	
}
