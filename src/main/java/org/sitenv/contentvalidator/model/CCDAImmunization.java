package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class CCDAImmunization {
	
	private static Logger log = Logger.getLogger(CCDAImmunization.class.getName());
	
	private ArrayList<CCDAII>     				templateIds;
	private CCDACode                 			sectionCode;
	private ArrayList<CCDAImmunizationActivity> immActivity;
	
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

	public CCDAImmunization() 
	{
		templateIds = new ArrayList<CCDAII>();
		immActivity = new ArrayList<CCDAImmunizationActivity>();
		
	}
}
