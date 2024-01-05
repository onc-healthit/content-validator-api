package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDACoverageActivity {
	
	private static Logger log = LoggerFactory.getLogger(CCDACoverageActivity.class.getName());

	private ArrayList<CCDAII>     		templateIds;
	private CCDAII						id;
	private CCDACode         	   		coverageCode;
	private CCDADataElement  			statusCode;
	private CCDAEffTime					coverageEffectiveTime;
	private ArrayList<CCDAPolicyActivity>  	policyActivities;
	
	private CCDAAuthor						author;
	
	public CCDACoverageActivity() {
		templateIds = new ArrayList<>();
		policyActivities = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" ***** Coverage Activity *****");
		
		if(coverageCode != null)
			log.info(" Coverage Activity Code = " + coverageCode.getCode());
		
		if(statusCode != null)
			log.info(" Coverage Activity Status = " + statusCode.getValue());
		
		if(id != null)
			log.info("Coverage Id Root : Extension = {}:,{}", id.getRootValue(), id.getExtValue());
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		if(coverageEffectiveTime != null)
			coverageEffectiveTime.log();
		
		for (int k = 0; k < policyActivities.size(); k++)   {
			
			log.info(" Policy Activty Information ***");
			policyActivities.get(k).log();
		}
		
		if(author != null) 
			author.log();
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateIds;
	}

	public void setTemplateId(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getCoverageCode() {
		return coverageCode;
	}

	public void setCoverageCode(CCDACode coverageCode) {
		this.coverageCode = coverageCode;
	}

	public CCDADataElement getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDADataElement statusCode) {
		this.statusCode = statusCode;
	}

	public ArrayList<CCDAPolicyActivity> getPolicyActivities() {
		return policyActivities;
	}

	public void setPolicyActivities(ArrayList<CCDAPolicyActivity> policyActivities) {
		this.policyActivities = policyActivities;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDAII getId() {
		return id;
	}

	public void setId(CCDAII id) {
		this.id = id;
	}

	public CCDAEffTime getCoverageEffectiveTime() {
		return coverageEffectiveTime;
	}

	public void setCoverageEffectiveTime(CCDAEffTime coverageEffectiveTime) {
		this.coverageEffectiveTime = coverageEffectiveTime;
	}
	
	

}
