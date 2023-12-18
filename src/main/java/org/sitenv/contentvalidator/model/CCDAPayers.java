package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAPayers {

	private static Logger log = LoggerFactory.getLogger(CCDAPayers.class.getName());

	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		sectionCode;
	private ArrayList<CCDACoverageActivity>	coverageActivities;
	
	private CCDAAuthor 						author;
	
	public CCDAPayers() {
		sectionTemplateId = new ArrayList<>();
		coverageActivities = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" ***** Payers Section *****");
		
		if(sectionCode != null)
			log.info(" Payers Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < sectionTemplateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateId.get(j).getExtValue());
		}
		
		for(int k = 0; k < coverageActivities.size(); k++) {
			coverageActivities.get(k).log();
		}
		
		if(author != null)
			author.log();
	}

	public ArrayList<CCDAII> getSectionTemplateId() {
		return sectionTemplateId;
	}

	public void setSectionTemplateId(ArrayList<CCDAII> sectionTemplateId) {
		this.sectionTemplateId = sectionTemplateId;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDACoverageActivity> getCoverageActivities() {
		return coverageActivities;
	}

	public void setCoverageActivities(ArrayList<CCDACoverageActivity> coverageActivities) {
		this.coverageActivities = coverageActivities;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	
	
}
