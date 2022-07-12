package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CCDAHealthConcerns {

	private static Logger log = LoggerFactory.getLogger(CCDAHealthConcerns.class.getName());
    
	private ArrayList<CCDAII>     templateIds;
	
	private CCDAAuthor	author;
	
	public CCDAHealthConcerns() {
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
