package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CCDAGoals {

	private static Logger log = LoggerFactory.getLogger(CCDAGoals.class.getName());
	
	private ArrayList<CCDAII>     templateIds;
	private CCDACode			  sectionCode;
	private ArrayList<GoalObservation> goalObservations;
	
	private CCDAAuthor author;
	
	public CCDAGoals() {
		templateIds = new ArrayList<CCDAII>();
		goalObservations = new ArrayList<>();
	}
	
	
	
	public CCDACode getSectionCode() {
		return sectionCode;
	}



	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}



	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}



	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}



	public CCDAAuthor getAuthor() {
		return author;
	}



	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}



	public void log() { 
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		for(int k = 0; k < goalObservations.size(); k++) {
			goalObservations.get(k).log();
		}	
		
		if(author != null)
			author.log();
	}



	public ArrayList<GoalObservation> getGoalObservations() {
		return goalObservations;
	}



	public void setGoalObservations(ArrayList<GoalObservation> goalObservations) {
		this.goalObservations = goalObservations;
	}
	
	
}
