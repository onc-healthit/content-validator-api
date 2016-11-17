package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;

public class CCDAAllergyConcern {
	
	private static Logger log = Logger.getLogger(CCDAAllergyConcern.class.getName());

	private ArrayList<CCDAII>     		templateId;
	private CCDACode         	   		concernCode;
	private CCDADataElement  			statusCode;
	private CCDAEffTime      			effTime;
	private ArrayList<CCDAAllergyObs>  	allergyObs;
	
	public void compare(CCDAAllergyConcern subConc, String allergyObsContext, ArrayList<ContentValidationResult> results ) {
		
		log.info(" Comparing Concern data for Allergy " + allergyObsContext);
		
		String elementName = "Allergy Concern for " + allergyObsContext;
		
		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateId, subConc.getTemplateId(), results, elementName);
		
		// Compare Status Codes 
		String elementNameStatus = "Allergy Concern Status code for " + allergyObsContext;
		ParserUtilities.compareDataElement(statusCode, subConc.getStatusCode(), results, elementNameStatus);
				
		// Compare Effective Times
		String elementNameTime = "Allergy Concern Effective Time for " + allergyObsContext;
		//ParserUtilities.compareEffectiveTime(effTime, subConc.getEffTime(), results, elementNameTime);
		
		
	}
	
	public void log() {
		
		if(concernCode != null)
			log.info(" Allergy Concern Code = " + concernCode.getCode());
		
		if(statusCode != null)
			log.info(" Allergy Concern Status = " + statusCode.getValue());
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		if(effTime != null)
			effTime.log();
		
		for(int k = 0; k < allergyObs.size(); k++) {
			allergyObs.get(k).log();
		}
	}
	
	public CCDAAllergyConcern()
	{
		templateId = new ArrayList<CCDAII>();
		allergyObs = new ArrayList<CCDAAllergyObs>();
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateId = ids;
	}

	public CCDACode getConcernCode() {
		return concernCode;
	}

	public void setConcernCode(CCDACode concernCode) {
		this.concernCode = concernCode;
	}

	public CCDADataElement getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDADataElement statusCode) {
		this.statusCode = statusCode;
	}

	public CCDAEffTime getEffTime() {
		return effTime;
	}

	public void setEffTime(CCDAEffTime effTime) {
		this.effTime = effTime;
	}

	public ArrayList<CCDAAllergyObs> getAllergyObs() {
		return allergyObs;
	}

	public void setAllergyObs(ArrayList<CCDAAllergyObs> aobs) {
		
		if(aobs != null)
			this.allergyObs = aobs;
	}
}
