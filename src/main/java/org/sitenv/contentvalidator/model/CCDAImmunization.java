package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;

import java.util.ArrayList;
import java.util.HashMap;

public class CCDAImmunization {
	
	private static Logger log = LoggerFactory.getLogger(CCDAImmunization.class.getName());
	
	private ArrayList<CCDAII>     				templateIds;
	private CCDACode                 			sectionCode;
	private ArrayList<CCDAImmunizationActivity> immActivity;
	
	private CCDAAuthor	author;
	
	public HashMap<String, CCDAImmunizationActivity> getImmunizationActivitiesMap() {
		
		HashMap<String, CCDAImmunizationActivity> acts = new HashMap<String, CCDAImmunizationActivity>();
		for(int k = 0; k < immActivity.size(); k++) {
			
			if(immActivity.get(k).getConsumable() != null &&
			   immActivity.get(k).getConsumable().getMedcode() != null &&
     		   immActivity.get(k).getConsumable().getMedcode().getCode() != null) {
				
				acts.put(immActivity.get(k).getConsumable().getMedcode().getCode(), immActivity.get(k));
			}
					
		}// for
		
		return acts;
	}
	
	public void compareAuthor(CCDAImmunization subImmunization, ArrayList<ContentValidationResult> results,
			boolean curesUpdate, ArrayList<CCDAAuthor> authorsWithLinkedReferenceData, boolean svap2022) {
		String elName = "Immunizations Section";

		CCDAAuthor.compareSectionLevelAuthor(elName, author,
				subImmunization != null && subImmunization.getAuthor() != null ? subImmunization.getAuthor() : null,
				results);

		log.info("Comparing Authors for Immunization Activity");
		ArrayList<CCDAAuthor> refAllImmActAuths = this.getImmunizationActivityAuthors();
		ArrayList<CCDAAuthor> subAllImmActAuths = subImmunization != null && subImmunization.getImmunizationActivityAuthors() != null
				? subImmunization.getImmunizationActivityAuthors()
				: null;
		elName += "/ImmunizationActivity";
		CCDAAuthor.compareAuthors(refAllImmActAuths, subAllImmActAuths, results, elName, authorsWithLinkedReferenceData);
	}

	public ArrayList<CCDAAuthor> getImmunizationActivityAuthors() {
		ArrayList<CCDAAuthor> authors = new ArrayList<CCDAAuthor>();

		for (CCDAImmunizationActivity curImmAct : immActivity) {
			if (curImmAct.getAuthor() != null) {
				authors.add(curImmAct.getAuthor());
			}
		}

		return authors;
	}
	
	public void log() {
		
		if(sectionCode != null)
			log.info(" Medication Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		for(int k = 0; k < immActivity.size(); k++) {
			immActivity.get(k).log();
		}
		
		if(author != null)
			author.log();
	}
	
	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateIds = ids;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDAImmunizationActivity> getImmActivity() {
		return immActivity;
	}

	public void setImmActivity(ArrayList<CCDAImmunizationActivity> iats) {
		
		if(iats != null)
			this.immActivity = iats;
	}
	
	

	public CCDAAuthor getAuthor() {
		return author;
	}


	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}


	public CCDAImmunization() 
	{
		templateIds = new ArrayList<CCDAII>();
		immActivity = new ArrayList<CCDAImmunizationActivity>();
		
	}
}
