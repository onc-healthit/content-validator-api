package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

public class CCDAAuthor {
	
	private static Logger log = Logger.getLogger(CCDAAuthor.class.getName());
	
	private ArrayList<CCDAII>    			templateId;
	private CCDAEffTime          			effTime;
	private ArrayList<CCDAII>    			authorIds;
	private CCDADataElement					authorFirstName;
	private CCDADataElement					authorLastName;
	private CCDADataElement					authorName;
	private ArrayList<CCDAII>				repOrgIds;
	private ArrayList<CCDATelecom>			telecoms;
	private CCDADataElement					orgName;
	
	public CCDAAuthor() {
		
		templateId = new ArrayList<CCDAII>();
		authorIds = new ArrayList<CCDAII>();
		repOrgIds = new ArrayList<CCDAII>();
		telecoms = new ArrayList<CCDATelecom>();
		
	}
	
	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}
	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}
	public CCDAEffTime getEffTime() {
		return effTime;
	}
	public void setEffTime(CCDAEffTime effTime) {
		this.effTime = effTime;
	}
	public ArrayList<CCDAII> getAuthorIds() {
		return authorIds;
	}
	public void setAuthorIds(ArrayList<CCDAII> authorIds) {
		this.authorIds = authorIds;
	}
	public ArrayList<CCDAII> getRepOrgIds() {
		return repOrgIds;
	}
	public void setRepOrgIds(ArrayList<CCDAII> repOrgIds) {
		this.repOrgIds = repOrgIds;
	}
	public ArrayList<CCDATelecom> getTelecoms() {
		return telecoms;
	}
	public void setTelecoms(ArrayList<CCDATelecom> telecoms) {
		this.telecoms = telecoms;
	}
	public CCDADataElement getOrgName() {
		return orgName;
	}
	public void setOrgName(CCDADataElement orgName) {
		this.orgName = orgName;
	}

	public CCDADataElement getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(CCDADataElement authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	public CCDADataElement getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(CCDADataElement authorLastName) {
		this.authorLastName = authorLastName;
	}

	public CCDADataElement getAuthorName() {
		return authorName;
	}

	public void setAuthorName(CCDADataElement authorName) {
		this.authorName = authorName;
	}
	
	public void log() {
		
		log.info("***Author Entry ***");
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		
		for(int k = 0; k < authorIds.size(); k++) {
			log.info(" Author Id [" + k + "] = " + templateId.get(k).getRootValue());
			log.info(" Author Id Ext [" + k + "] = " + templateId.get(k).getExtValue());
		}
		
		for(int l = 0; l < repOrgIds.size(); l++) {
			log.info(" Rep Org Id [" + l + "] = " + templateId.get(l).getRootValue());
			log.info(" Rep Org Id Ext [" + l + "] = " + templateId.get(l).getExtValue());
		}
		
		
		if(effTime != null) 
			effTime.log();
		
		if(authorFirstName != null) {
			log.info(" Author First Name = " + authorFirstName.getValue());
		}
		
		if(authorLastName != null) {
			log.info(" Author Last Name = " + authorLastName.getValue());
		}
		
		if(authorName != null) {
			log.info(" Author  Name = " + authorName.getValue());
		}
		
		if(orgName != null) {
			log.info(" Rep Org  Name = " + orgName.getValue());
		}
		
	}	
	
	public void matches(CCDAAuthor subAuthor, ArrayList<ContentValidationResult> results, String elName) {
		
		log.info(" Comparing data for Author Tempalte Ids: ");

		// Compare template Ids 
		String elementName = "Comapring Author Template Ids for : " + elName;		
		ParserUtilities.compareTemplateIds(templateId, subAuthor.getTemplateId(), results, elementName);
		
		// Compare Author Ids 
		elementName = "Comapring Author Ids for : " + elName;
		ParserUtilities.compareTemplateIds(authorIds, subAuthor.getAuthorIds(), results, elementName);
		
		// Compare REp Ord Ids 		
		elementName = "Comapring Rep Org Ids for : " + elName;
		ParserUtilities.compareTemplateIds(repOrgIds, subAuthor.getRepOrgIds(), results, elementName);

		// Compare Effective Times
		elementName = "Comparing Author Time for " + elName;
		ParserUtilities.compareEffectiveTime(effTime, subAuthor.getEffTime(), results, elementName);
		
		// Compare Org Name 
		elementName = "Comparing Author Represented Organization Name for " + elName;
		ParserUtilities.compareDataElement(orgName, subAuthor.getOrgName(), results, elementName);
	}

}
