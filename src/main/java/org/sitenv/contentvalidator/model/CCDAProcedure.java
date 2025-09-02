package org.sitenv.contentvalidator.model;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;

public class CCDAProcedure {
	
	private static Logger log = LoggerFactory.getLogger(CCDAProcedure.class.getName());
	
	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		sectionCode;
	private ArrayList<CCDAProcActProc>		procActsProcs;
	private ArrayList<CCDANotesActivity>	notesActivity;
	
	private CCDAAuthor author;
	
	public void getAllNotesActivities(HashMap<String, CCDANotesActivity> results) {
		
		if(notesActivity != null && notesActivity.size() > 0) {
			
			log.info(" Found non-null notes activity ");
			ParserUtilities.populateNotesActiviteis(notesActivity, results);
		}
		
		if(procActsProcs != null && procActsProcs.size() > 0) {
			
			for(CCDAProcActProc proc : procActsProcs) {
				
				proc.getAllNotesActivities(results);
			}
		}
	}
	
	public ArrayList<CCDAUDI> getAllUdis() {
		
		ArrayList<CCDAUDI> udis = new ArrayList<CCDAUDI>();
		for(int i = 0; i < procActsProcs.size(); i++) {
			
			ArrayList<CCDAUDI> actUdi = procActsProcs.get(i).getUdi();
			
			if(actUdi != null) {
				
				log.info("Size of UDIs in Proc Act Proc in Procedures " + actUdi.size());
				udis.addAll(actUdi);
			}
			
		}
		
		return udis;
	}
	
	public HashMap<String, CCDAProcActProc> getProcedureMap() {
		
		HashMap<String, CCDAProcActProc> results = new HashMap<String, CCDAProcActProc>();
		
		for(int k = 0; k < procActsProcs.size(); k++) {
			
			log.info(" Iterating through procedures ");
			if(procActsProcs.get(k).getProcCode() != null && 
					procActsProcs.get(k).getProcCode().getCode() != null) {

				String code = procActsProcs.get(k).getProcCode().getCode();
				log.info("Adding procedure code = " + code);
				results.put(code, procActsProcs.get(k));
				
				if(procActsProcs.get(k).getProcCode().getTranslations() != null) {
					
					ArrayList<CCDACode> translations = procActsProcs.get(k).getProcCode().getTranslations();
					
					for(int j = 0; j < translations.size(); j++) {
						
						CCDACode trans = translations.get(j);
						
						if(trans.getCode() != null) {
							log.info("Adding translation code = " + code);
							results.put(trans.getCode(), procActsProcs.get(k));
						}
						
					}
					
				}
			}	
		}// for
		
		return results;
	}
	
	public void compareAuthor(CCDAProcedure subProcedure, ArrayList<ContentValidationResult> results,
			boolean curesUpdate, ArrayList<CCDAAuthor> authorsWithLinkedReferenceData, boolean svap2022, boolean svap2023) {
		String elName = "Procedures Section";

		CCDAAuthor.compareSectionLevelAuthor(elName, author,
				subProcedure != null && subProcedure.getAuthor() != null ? subProcedure.getAuthor() : null, results);

		log.info("Comparing Authors for Procedure Activity Procedure");
		ArrayList<CCDAAuthor> refAllPAPAuths = this.getProcedureActivityProcedureAuthors();
		ArrayList<CCDAAuthor> subAllPAPAuths = subProcedure != null && subProcedure.getProcedureActivityProcedureAuthors() != null
				? subProcedure.getProcedureActivityProcedureAuthors()
				: null;
		elName += "/ProcedureActivityProcedure";
		CCDAAuthor.compareAuthors(refAllPAPAuths, subAllPAPAuths, results, elName, authorsWithLinkedReferenceData);
	}
	
	public ArrayList<CCDAAuthor> getProcedureActivityProcedureAuthors() {
		ArrayList<CCDAAuthor> authors = new ArrayList<CCDAAuthor>();
		
		for (CCDAProcActProc curProcActProc : procActsProcs) {
			if (curProcActProc.getAuthor() != null) {
				authors.add(curProcActProc.getAuthor());
			}
		}
		
		return authors;
	}	
	
	public void log() {
		
		if(sectionCode != null)
			log.info(" Procedure Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < sectionTemplateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateId.get(j).getExtValue());
		}
		
		for(int k = 0; k < procActsProcs.size(); k++) {
			procActsProcs.get(k).log();
		}
		
		if(notesActivity != null) {
		for(int l = 0; l < notesActivity.size(); l++) {
			notesActivity.get(l).log();
		}
		}
		
		if(author != null)
			author.log();
	}
	
	public CCDAProcedure()
	{
		sectionTemplateId = new ArrayList<CCDAII>();
		procActsProcs = new ArrayList<CCDAProcActProc>();
		notesActivity = new ArrayList<CCDANotesActivity>();
	}

	public ArrayList<CCDAII> getSectionTemplateId() {
		return sectionTemplateId;
	}

	public void setSectionTemplateId(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.sectionTemplateId = ids;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDAProcActProc> getProcActsProcs() {
		return procActsProcs;
	}

	public void setProcActsProcs(ArrayList<CCDAProcActProc> paps) {
		
		if(paps != null)
			this.procActsProcs = paps;
	}
	
	
	
	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		CCDAProcedure.log = log;
	}

	public ArrayList<CCDANotesActivity> getNotesActivity() {
		return notesActivity;
	}

	public void setNotesActivity(ArrayList<CCDANotesActivity> notesActivity) {
		this.notesActivity = notesActivity;
	}
	
	

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public void addProcActsProcs(ArrayList<CCDAProcActProc> paps) {
		
		if(paps != null) {
			for(int i = 0; i < paps.size(); i++) {
				this.procActsProcs.add(paps.get(i));
			}
		}
	}
	
	public HashMap<String, AssessmentScaleObservation> getAllSdohData() {
		
		HashMap<String, AssessmentScaleObservation> assessments = new HashMap<>();
		if(procActsProcs != null) {
			for(CCDAProcActProc proc: procActsProcs) {
				
				HashMap<String, AssessmentScaleObservation> procAssessments = proc.getAllSdohData();
				
				if(procAssessments != null && !procAssessments.isEmpty()) {
					assessments.putAll(procAssessments);
				}
				
			}
		}		
		return assessments;
	}
	
}
