package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;

public class CCDALabResult {

	private static Logger log = Logger.getLogger(CCDALabResult.class.getName());
	
	private ArrayList<CCDAII>			resultSectionTempalteIds;
	private CCDACode					sectionCode;
	private ArrayList<CCDALabResultOrg>	resultOrg;
	private Boolean						isLabTestInsteadOfResult;
	private ArrayList<CCDANotesActivity>		notesActivity;
	
	private CCDAAuthor	author;
	
	public void getAllNotesActivities(HashMap<String, CCDANotesActivity> results) {
		
		if(notesActivity != null && notesActivity.size() > 0) {
			
			log.info(" Found non-null notes activity ");
			ParserUtilities.populateNotesActiviteis(notesActivity, results);
		}
		
		if( resultOrg != null && resultOrg.size() > 0) {
			
			for(CCDALabResultOrg org : resultOrg) {
				
				org.getAllNotesActivities(results);
			}
		}
	}
	
	public HashMap<String, CCDALabResultObs> getLabResultsMap() {
		
		HashMap<String, CCDALabResultObs> results = new HashMap<String, CCDALabResultObs>();
		// log.info(" Iterating through Organizers ");
		for(int k = 0; k < resultOrg.size(); k++) {
			
			ArrayList<CCDALabResultObs> observations = resultOrg.get(k).getResultObs();
			
			for(int j = 0; j < observations.size(); j++) {
				
				log.info(" Iterating through Observations ");
				if(observations.get(j).getLabCode() != null && 
			       observations.get(j).getLabCode().getCode() != null) {
				
					String code = observations.get(j).getLabCode().getCode();
					log.info("Adding lab code = " + code);
					results.put(code, observations.get(j));
				}
			}// for	
		}// for
		
		return results;
	}
	
	public Boolean getIsLabTestInsteadOfResult() {
		return isLabTestInsteadOfResult;
	}

	public void setIsLabTestInsteadOfResult(Boolean isLabTestInsteadOfResult) {
		this.isLabTestInsteadOfResult = isLabTestInsteadOfResult;
	}

	public void log() {
		
		if(sectionCode != null)
			log.info(" Lab Result Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < resultSectionTempalteIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + resultSectionTempalteIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + resultSectionTempalteIds.get(j).getExtValue());
		}
		
		log.info(" Is Lab Test " + isLabTestInsteadOfResult);
		
		for(int k = 0; k < resultOrg.size(); k++) {
			resultOrg.get(k).log();
		}
		
		for(int l = 0; l < notesActivity.size(); l++) {
			notesActivity.get(l).log();
		}
		
		if(author != null)
			author.log();
	}
	
	public CCDALabResult()
	{
		resultSectionTempalteIds = new ArrayList<CCDAII>();
		resultOrg = new ArrayList<CCDALabResultOrg>();
		isLabTestInsteadOfResult = false;
		notesActivity = new ArrayList<CCDANotesActivity>();
	}

	public ArrayList<CCDAII> getResultSectionTempalteIds() {
		return resultSectionTempalteIds;
	}

	public void setResultSectionTempalteIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.resultSectionTempalteIds = ids;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDALabResultOrg> getResultOrg() {
		return resultOrg;
	}

	public void setResultOrg(ArrayList<CCDALabResultOrg> ros) {
		
		if(ros != null)
			this.resultOrg = ros;
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
	
	
}
