package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class CCDAEncounter {
	
	private static Logger log = Logger.getLogger(CCDAEncounter.class.getName());

	private ArrayList<CCDAII>    templateId;
	private CCDACode  sectionCode;
	private ArrayList<CCDAEncounterActivity> encActivities;
	
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
					else if(prob.getProblemCode().isProperNFForTranslation()){
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

	public CCDAEncounter()
	{
		encActivities = new ArrayList<CCDAEncounterActivity>();
		templateId = new ArrayList<CCDAII>();
	}
}
