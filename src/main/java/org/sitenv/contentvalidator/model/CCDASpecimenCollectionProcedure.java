package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDASpecimenCollectionProcedure {
	
private static Logger log = LoggerFactory.getLogger(CCDASpecimenCollectionProcedure.class.getName());
	
	private ArrayList<CCDAII>				templateIds;
	private CCDACode						collectionProcCode;
	private ArrayList<CCDAII>				specimenReferences;
	private ArrayList<CCDASpecimenConditionObservation> specimenConditionObservations;
	
	public CCDASpecimenCollectionProcedure() {
		templateIds = new ArrayList<>();
		specimenReferences = new ArrayList<>();
		specimenConditionObservations = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** CCDA Specimen Collection Procedure ***");
		
		if(collectionProcCode != null)
			collectionProcCode.log();
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		for(int j = 0; j < specimenReferences.size(); j++) {
			log.info(" Specimen Reference Id [" + j + "] = " + specimenReferences.get(j).getRootValue());
			log.info(" Specimen Reference Id Ext [" + j + "] = " + specimenReferences.get(j).getExtValue());
		}
		
		for(int k = 0; k < specimenConditionObservations.size(); k++) {
			specimenConditionObservations.get(k).log();
		}
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getCollectionProcCode() {
		return collectionProcCode;
	}

	public void setCollectionProcCode(CCDACode collectionProcCode) {
		this.collectionProcCode = collectionProcCode;
	}

	public ArrayList<CCDAII> getSpecimenReferences() {
		return specimenReferences;
	}

	public void setSpecimenReferences(ArrayList<CCDAII> specimenReferences) {
		this.specimenReferences = specimenReferences;
	}

	public ArrayList<CCDASpecimenConditionObservation> getSpecimenConditions() {
		return specimenConditionObservations;
	}

	public void setSpecimenConditions(ArrayList<CCDASpecimenConditionObservation> specimenConditions) {
		this.specimenConditionObservations = specimenConditions;
	}

	

}
