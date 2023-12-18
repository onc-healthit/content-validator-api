package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAMedicationDispense {
	
	private static Logger log = LoggerFactory.getLogger(CCDAMedicationDispense.class.getName());
	
	private ArrayList<CCDAII>     				templateIds;
	private CCDACode							fillStatus;
	
	public CCDAMedicationDispense() {
		
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** Medication Dispense *** ");
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		if(fillStatus != null)
			log.info("Fill Status Code = " + fillStatus.getCode());
		
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getFillStatus() {
		return fillStatus;
	}

	public void setFillStatus(CCDACode fillStatus) {
		this.fillStatus = fillStatus;
	}

	
}
