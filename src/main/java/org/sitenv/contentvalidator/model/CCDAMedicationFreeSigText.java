package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAMedicationFreeSigText {
	
	private static Logger log = LoggerFactory.getLogger(CCDAMedicationFreeSigText.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            medicationFreeSigTextCode;
	private String								freeSigText;
	
	public CCDAMedicationFreeSigText() {
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** CCDA Medication Free Sig Text *** ");
		
		if(medicationFreeSigTextCode != null)
			log.info(" Medication Free Sig Text Code = " + medicationFreeSigTextCode.getCode());
		
		if(freeSigText != null)
			log.info(" Sig Text = " + freeSigText);

		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getMedicationFreeSigTextCode() {
		return medicationFreeSigTextCode;
	}

	public void setMedicationFreeSigTextCode(CCDACode medicationFreeSigTextCode) {
		this.medicationFreeSigTextCode = medicationFreeSigTextCode;
	}

	public String getFreeSigText() {
		return freeSigText;
	}

	public void setFreeSigText(String freeSigText) {
		this.freeSigText = freeSigText;
	}

	


}
