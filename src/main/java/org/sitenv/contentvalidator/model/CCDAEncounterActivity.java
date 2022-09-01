package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;

public class CCDAEncounterActivity {

	private static Logger log = LoggerFactory.getLogger(CCDAEncounterActivity.class.getName());
	
	private ArrayList<CCDAII>                 templateId;
	private CCDACode                          encounterTypeCode;
	private CCDAEffTime                       effectiveTime;
	private ArrayList<CCDAEncounterDiagnosis> diagnoses;
	private ArrayList<CCDAServiceDeliveryLoc> sdLocs;
	private ArrayList<CCDAProblemObs>         indications;
	private ArrayList<CCDANotesActivity>		notesActivity;
	
	private CCDAAuthor	author;
	
	public void log() {
		
		log.info("*** Encounter Activit ***");
		
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
		
		for(int p = 0; p < notesActivity.size(); p++) {
			notesActivity.get(p).log();
		}
		
		if(author != null)
			author.log();
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
	
	
}
