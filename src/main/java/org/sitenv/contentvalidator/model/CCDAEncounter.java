package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;

public class CCDAEncounter {
	
	private static Logger log = LoggerFactory.getLogger(CCDAEncounter.class.getName());

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
	
	public void compareAuthor(CCDAEncounter subEncounter, ArrayList<ContentValidationResult> results,
			boolean curesUpdate, ArrayList<CCDAAuthor> authorsWithLinkedReferenceData, boolean svap2022, boolean svap2023) {
		final String encSec = "Encounters Section";
		String elName = encSec;

		CCDAAuthor.compareSectionLevelAuthor(elName, author,
				subEncounter != null && subEncounter.getAuthor() != null ? subEncounter.getAuthor() : null, results);

		log.info("Comparing Authors for Encounter Activity");
		ArrayList<CCDAAuthor> refAllEncActAuths = this.getEncounterActivityAuthors();
		ArrayList<CCDAAuthor> subAllEncActAuths = subEncounter != null && subEncounter.getEncounterActivityAuthors() != null
				? subEncounter.getEncounterActivityAuthors()
				: null;
		elName += "/EncounterActivity";
		CCDAAuthor.compareAuthors(refAllEncActAuths, subAllEncActAuths, results, elName, authorsWithLinkedReferenceData);

		log.info("Comparing Authors for Encounter Diagnoses");
		ArrayList<CCDAAuthor> refAllEncDiagAuths = this.getEncounterDiagnosesAuthors();
		ArrayList<CCDAAuthor> subAllEncDiagAuths = subEncounter != null && subEncounter.getEncounterDiagnosesAuthors() != null
				? subEncounter.getEncounterDiagnosesAuthors()
				: null;
		elName += "/EncounterDiagnoses";
		CCDAAuthor.compareAuthors(refAllEncDiagAuths, subAllEncDiagAuths, results, elName, authorsWithLinkedReferenceData);

		log.info("Comparing Authors for Encounter Diagnoses/Problem Observation");
		ArrayList<CCDAAuthor> refAllEncDiagProbObsAuths = this.getEncounterDiagnosesProblemObservationAuthors();
		ArrayList<CCDAAuthor> subAllEncDiagProbObsAuths = subEncounter != null
				&& subEncounter.getEncounterDiagnosesProblemObservationAuthors() != null
				? subEncounter.getEncounterDiagnosesProblemObservationAuthors()
				: null;
		elName += "/ProblemObservation";
		CCDAAuthor.compareAuthors(refAllEncDiagProbObsAuths, subAllEncDiagProbObsAuths, results, elName,
				authorsWithLinkedReferenceData);

		log.info("Comparing Authors for Encounter Activity/Indication");
		ArrayList<CCDAAuthor> refAllEncActIndicationAuths = this.getEncounterActivityIndicationAuthors();
		ArrayList<CCDAAuthor> subAllEncActIndicationAuths = subEncounter != null
				&& subEncounter.getEncounterActivityIndicationAuthors() != null
				? subEncounter.getEncounterActivityIndicationAuthors()
				: null;
		CCDAAuthor.compareAuthors(refAllEncActIndicationAuths, subAllEncActIndicationAuths, results,
				encSec + "/EncounterActivity/Indication", authorsWithLinkedReferenceData);
	}

	public ArrayList<CCDAAuthor> getEncounterActivityAuthors() {
		ArrayList<CCDAAuthor> authors = new ArrayList<CCDAAuthor>();

		for (CCDAEncounterActivity curEncAct : encActivities) {
			if (curEncAct.getAuthor() != null) {
				authors.add(curEncAct.getAuthor());
			}
		}

		return authors;
	}

	public ArrayList<CCDAAuthor> getEncounterDiagnosesAuthors() {
		ArrayList<CCDAAuthor> authors = new ArrayList<CCDAAuthor>();

		for (CCDAEncounterActivity curEncAct : encActivities) {
			for (CCDAEncounterDiagnosis curEncDiag : curEncAct.getDiagnoses()) {
				if (curEncDiag.getAuthor() != null) {
					authors.add(curEncDiag.getAuthor());
				}
			}
		}

		return authors;
	}

	public ArrayList<CCDAAuthor> getEncounterDiagnosesProblemObservationAuthors() {
		ArrayList<CCDAAuthor> authors = new ArrayList<CCDAAuthor>();

		for (CCDAEncounterActivity curEncAct : encActivities) {
			for (CCDAEncounterDiagnosis curEncDiag : curEncAct.getDiagnoses()) {
				for (CCDAProblemObs curProbObs : curEncDiag.getProblemObs()) {
					if (curProbObs.getAuthor() != null) {
						authors.add(curProbObs.getAuthor());
					}
				}
			}
		}

		return authors;
	}

	public ArrayList<CCDAAuthor> getEncounterActivityIndicationAuthors() {
		ArrayList<CCDAAuthor> authors = new ArrayList<CCDAAuthor>();

		for (CCDAEncounterActivity curEncAct : encActivities) {
			for (CCDAProblemObs curIndication : curEncAct.getIndications()) {
				if (curIndication.getAuthor() != null) {
					authors.add(curIndication.getAuthor());
				}
			}
		}

		return authors;
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
		
		if(notesActivity != null) {
		for(int l = 0; l < notesActivity.size(); l++) {
			notesActivity.get(l).log();
		}
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
