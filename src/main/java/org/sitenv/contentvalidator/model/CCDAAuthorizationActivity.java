package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAAuthorizationActivity {
	
	private static Logger log = LoggerFactory.getLogger(CCDAAuthorizationActivity.class.getName());
	
	private CCDAII 			planIdentifier;
	private String			planDescription;
	
	public void log() {
		
		log.info(" PlanIdentifier: {}:{}",planIdentifier.getRootValue(),planIdentifier.getExtValue());
		log.info(" PlanDescription: {}",planDescription);
	}
	
	public CCDAAuthorizationActivity() {
		
	}

	public CCDAII getPlanIdentifier() {
		return planIdentifier;
	}

	public void setPlanIdentifier(CCDAII planIdentifier) {
		this.planIdentifier = planIdentifier;
	}

	public String getPlanDescription() {
		return planDescription;
	}

	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}

	
}
