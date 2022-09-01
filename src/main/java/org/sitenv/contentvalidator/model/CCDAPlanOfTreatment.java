package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CCDAPlanOfTreatment {

	private static Logger log = LoggerFactory.getLogger(CCDAPlanOfTreatment.class.getName());
	
	private ArrayList<CCDAII>     templateIdsPOT;
	private ArrayList<CCDAII>     templateIdsAP;
	private CCDACode			  sectionCodePOT;
	private ArrayList<PlannedProcedure>	plannedProcedures;
	
	private CCDAAuthor author;
	
	public CCDAPlanOfTreatment() {
		templateIdsPOT = new ArrayList<CCDAII>();
		templateIdsAP = new ArrayList<CCDAII>();
		plannedProcedures = new ArrayList<>();
	}
	
	
	public ArrayList<CCDAII> getTemplateIdsPOT() {
		return templateIdsPOT;
	}

	

	public CCDACode getSectionCodePOT() {
		return sectionCodePOT;
	}


	public void setSectionCodePOT(CCDACode sectionCodePOT) {
		this.sectionCodePOT = sectionCodePOT;
	}


	public ArrayList<PlannedProcedure> getPlannedProcedures() {
		return plannedProcedures;
	}


	public void setPlannedProcedures(ArrayList<PlannedProcedure> plannedProcedures) {
		this.plannedProcedures = plannedProcedures;
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
		
		for(int k = 0; k < plannedProcedures.size(); k++) {
			plannedProcedures.get(k).log();
		}
		
		if(author != null)
			author.log();
	}
}
