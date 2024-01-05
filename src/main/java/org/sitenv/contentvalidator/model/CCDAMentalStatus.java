package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAMentalStatus {
	
	private static Logger log = LoggerFactory.getLogger(CCDAMentalStatus.class.getName());

	private ArrayList<CCDAII>       					sectionTemplateId;
	private CCDACode                 					sectionCode;
	private ArrayList<CCDAMentalStatusObservation>	    mentalStatusObservations;
	private ArrayList<AssessmentScaleObservation>		fullMentalStatusAssessments;
	
	private CCDAAuthor 						author;
	
	public void log() {
		
		log.info(" ***** Mental Status Section *****");
		
		if(sectionCode != null)
			log.info(" Mental Status Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < sectionTemplateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateId.get(j).getExtValue());
		}
		
		for(int l = 0; l < mentalStatusObservations.size(); l++) {
			mentalStatusObservations.get(l).log();
		}
		
		for(int k = 0; k < fullMentalStatusAssessments.size(); k++) {
			fullMentalStatusAssessments.get(k).log();
		}
		
		if(author != null)
			author.log();	
		
	}
	
	public CCDAMentalStatus() {		
		sectionTemplateId = new ArrayList<>(); 
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

	public ArrayList<CCDAMentalStatusObservation> getMentalStatusObservations() {
		return mentalStatusObservations;
	}

	public void setMentalStatusObservations(ArrayList<CCDAMentalStatusObservation> mentalStatusObservations) {
		this.mentalStatusObservations = mentalStatusObservations;
	}

	public ArrayList<AssessmentScaleObservation> getFullMentalStatusAssessments() {
		return fullMentalStatusAssessments;
	}

	public void setFullMentalStatusAssessments(ArrayList<AssessmentScaleObservation> fullMentalStatusAssessments) {
		this.fullMentalStatusAssessments = fullMentalStatusAssessments;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public HashMap<String, AssessmentScaleObservation> getAllAssessmentScaleObservations() {
			
			HashMap<String, AssessmentScaleObservation> assessments = new HashMap<>();
			if(fullMentalStatusAssessments != null && !fullMentalStatusAssessments.isEmpty()) { 
				
				for(AssessmentScaleObservation obs : fullMentalStatusAssessments) {
					if(obs.getAssessmentCode() != null && obs.getAssessmentCode().getCode() != null) {
						assessments.put(obs.getAssessmentCode().getCode(), obs);
					}
				}
			}
			return assessments;
	}
	
	

}
