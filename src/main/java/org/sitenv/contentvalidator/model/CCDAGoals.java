package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;

public class CCDAGoals {

	private static Logger log = Logger.getLogger(CCDAGoals.class.getName());
	
	private ArrayList<CCDAII>     templateIds;
	
	private CCDAAuthor author;
	
	public CCDAGoals() {
		templateIds = new ArrayList<CCDAII>();
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
		
		if(author != null)
			author.log();
	}
}
