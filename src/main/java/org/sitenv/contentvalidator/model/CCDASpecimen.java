package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDASpecimen {

	private static Logger log = LoggerFactory.getLogger(CCDASpecimen.class.getName());
	
	private CCDACode						specimenType;
	
	public CCDASpecimen() {
		
	}
	
	public void log() {
		
		log.info(" *** CCDA Specimen ***");
		
		if(specimenType != null)
			specimenType.log();
	}

	public CCDACode getSpecimenType() {
		return specimenType;
	}

	public void setSpecimenType(CCDACode specimenType) {
		this.specimenType = specimenType;
	}

	public static void compareSpecimens(HashMap<String, CCDASpecimen> refSpecs, HashMap<String, CCDASpecimen> subSpecs,
			ArrayList<ContentValidationResult> results) {
		
		log.info(" Start Comparing Specimens ");
		// For each lab result in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDASpecimen> ent: refSpecs.entrySet()) {

			if(subSpecs.containsKey(ent.getKey())) {

				log.info("Specimen found, no further action ");

			} else {
				// Error
				String error = "The scenario contains Specimen with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}
		
	}
	
	
}
