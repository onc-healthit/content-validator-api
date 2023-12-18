package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAFunctionalStatus {

	private static Logger log = LoggerFactory.getLogger(CCDAFunctionalStatus.class.getName());

	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		sectionCode;
	private ArrayList<CCDAFunctionalStatusObservation> functionalStatusObservation;
	private ArrayList<CCDADisabilityStatusObservation> disabilityStatusObservation;
	private ArrayList<AssessmentScaleObservation>  functionalAssessmentsObservations;
	
	private CCDAAuthor 						author;
	
	public void log() {
		
		log.info(" ***** Functional Status Section *****");
		
		if(sectionCode != null)
			log.info(" Functional Status Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < sectionTemplateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateId.get(j).getExtValue());
		}
		
		for(int k = 0; k < functionalStatusObservation.size(); k++) {
			functionalStatusObservation.get(k).log();
		}
		
		for(int l = 0; l < disabilityStatusObservation.size(); l++) {
			disabilityStatusObservation.get(l).log();
		}
		
		for(int k = 0; k < functionalAssessmentsObservations.size(); k++) {
			functionalAssessmentsObservations.get(k).log();
		}
		
		if(author != null)
			author.log();	
		
	}
	
	public CCDAFunctionalStatus() {		
		sectionTemplateId = new ArrayList<>(); 
		functionalStatusObservation = new ArrayList<>();
		disabilityStatusObservation = new ArrayList<>();
		functionalAssessmentsObservations = new ArrayList<>();
	}

	public ArrayList<CCDAII> getSectionTemplateId() {
		return sectionTemplateId;
	}

	public void setSectionTemplateId(ArrayList<CCDAII> sectionTemplateId) {
		this.sectionTemplateId = sectionTemplateId;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDAFunctionalStatusObservation> getFunctionalStatusObservation() {
		return functionalStatusObservation;
	}

	public void setFunctionalStatusObservation(ArrayList<CCDAFunctionalStatusObservation> functionalStatusObservation) {
		this.functionalStatusObservation = functionalStatusObservation;
	}

	public ArrayList<CCDADisabilityStatusObservation> getDisabilityStatusObservation() {
		return disabilityStatusObservation;
	}

	public void setDisabilityStatusObservation(ArrayList<CCDADisabilityStatusObservation> disabilityStatusObservation) {
		this.disabilityStatusObservation = disabilityStatusObservation;
	}
	
	

	public ArrayList<AssessmentScaleObservation> getFunctionalAssessmentsObservations() {
		return functionalAssessmentsObservations;
	}

	public void setFunctionalAssessmentsObservations(
			ArrayList<AssessmentScaleObservation> functionalAssessmentsObservations) {
		this.functionalAssessmentsObservations = functionalAssessmentsObservations;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	
}

