package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;

public class CCDAPlanOfTreatment {

	private static Logger log = Logger.getLogger(CCDAPlanOfTreatment.class.getName());
	
	private ArrayList<CCDAII>     templateIdsPOT;
	private ArrayList<CCDAII>     templateIdsAP;
	
	private CCDAAuthor author;
	
	public CCDAPlanOfTreatment() {
		templateIdsPOT = new ArrayList<CCDAII>();
		templateIdsAP = new ArrayList<CCDAII>();
	}
	
	
	public ArrayList<CCDAII> getTemplateIdsPOT() {
		return templateIdsPOT;
	}


	public void setTemplateIdsPOT(ArrayList<CCDAII> templateIdsPOT) {
		this.templateIdsPOT = templateIdsPOT;
	}


	public ArrayList<CCDAII> getTemplateIdsAP() {
		return templateIdsAP;
	}


	public void setTemplateIdsAP(ArrayList<CCDAII> templateIdsAP) {
		this.templateIdsAP = templateIdsAP;
	}


	public CCDAAuthor getAuthor() {
		return author;
	}


	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}


	public void log() { 
		
		for(int j = 0; j < templateIdsPOT.size(); j++) {
			log.info(" POT Tempalte Id [" + j + "] = " + templateIdsPOT.get(j).getRootValue());
			log.info(" POT Tempalte Id Ext [" + j + "] = " + templateIdsPOT.get(j).getExtValue());
		}	
		
		for(int j = 0; j < templateIdsAP.size(); j++) {
			log.info(" AP Tempalte Id [" + j + "] = " + templateIdsAP.get(j).getRootValue());
			log.info(" AP Tempalte Id Ext [" + j + "] = " + templateIdsAP.get(j).getExtValue());
		}
		
		if(author != null)
			author.log();
	}
}
