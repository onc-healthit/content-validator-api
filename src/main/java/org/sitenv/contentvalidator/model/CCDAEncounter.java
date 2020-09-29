package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;

public class CCDAEncounter {
	
	private static Logger log = Logger.getLogger(CCDAEncounter.class.getName());

	private ArrayList<CCDAII>    templateId;
	private CCDACode  sectionCode;
	private ArrayList<CCDAEncounterActivity> encActivities;
	private ArrayList<CCDANotesActivity>		notesActivity;
	
	private CCDAAuthor	author;
	
	public HashMap<String, CCDAProblemObs> getAllDiagnosisObservations(Boolean includeTrans) {
		
		HashMap<String, CCDAProblemObs> diagnoses = new HashMap<String, CCDAProblemObs>();
		
		for(CCDAEncounterActivity encact : encActivities) {
			
			ArrayList<CCDAEncounterDiagnosis> encdiags = encact.getDiagnoses();
			
			for(CCDAEncounterDiagnosis encdiag : encdiags) {
				
				ArrayList<CCDAProblemObs> probs = encdiag.getProblemObs();
				
				for(CCDAProblemObs prob : probs) {
				
					if(prob.getProblemCode() != null && 
					   prob.getProblemCode().getCode() != null ) {
					
						log.info("Adding Diagnosis from Problem Obs: " + prob.getProblemCode().getCode());
						diagnoses.put(prob.getProblemCode().getCode(), prob);
						
					}
					else if(prob.getProblemCode() != null && prob.getProblemCode().isProperNFForTranslation()){
						log.info("Adding Diagnosis from Problem Obs: " + prob.getProblemCode().getNullFlavor());
						diagnoses.put(prob.getProblemCode().getNullFlavor(), prob);

					}
					
					//Add entries for translations also.
				/*	if(prob.getProblemCode() != null && 
					   prob.getProblemCode().getTranslations() != null && 
					   prob.getProblemCode().getTranslations().size() > 0 ) 
					{
						
						if(includeTrans || prob.getProblemCode().isProperNFForTranslation() ) {
							
							ArrayList<CCDACode> translist = prob.getProblemCode().getTranslations();
							
							for(CCDACode trans : translist) {
								log.info("Adding Diagnosis from Problem Obs Code Translations : " + trans.getCode());
								diagnoses.put(trans.getCode(), prob);
							}
						}
						
					}*/
					
				}// for problems
				
			}//for enc diags
			
		}// for enc activities 
		
		return diagnoses;
	}
	
	public void getAllNotesActivities(HashMap<String, CCDANotesActivity> results) {
		
		if(notesActivity != null && notesActivity.size() > 0) {
			
			log.info(" Found non-null notes activity ");
			ParserUtilities.populateNotesActiviteis(notesActivity, results);
		}
		
		if( encActivities != null && encActivities.size() > 0) {
			
			for(CCDAEncounterActivity encAct : encActivities) {
				
				encAct.getAllNotesActivities(results);
			}
		}
	}
	
	public void log() {
		
		if(sectionCode != null)
			log.info("Encounter Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		for(int k = 0; k < encActivities.size(); k++) {
			encActivities.get(k).log();
		}
		
		for(int l = 0; l < notesActivity.size(); l++) {
			notesActivity.get(l).log();
		}
		
		if(author != null)
			author.log();
	}
	
	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templId) {
		if(templId != null)
			this.templateId = templId;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDAEncounterActivity> getEncActivities() {
		return encActivities;
	}

	public void setEncActivities(ArrayList<CCDAEncounterActivity> enActivities) {
		if(enActivities != null)
			this.encActivities = enActivities;
	}
	
	

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		CCDAEncounter.log = log;
	}

	public ArrayList<CCDANotesActivity> getNotesActivity() {
		return notesActivity;
	}

	public void setNotesActivity(ArrayList<CCDANotesActivity> notesActivity) {
		this.notesActivity = notesActivity;
	}

	public CCDAEncounter()
	{
		encActivities = new ArrayList<CCDAEncounterActivity>();
		templateId = new ArrayList<CCDAII>();
		notesActivity = new ArrayList<CCDANotesActivity>();
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	
}
