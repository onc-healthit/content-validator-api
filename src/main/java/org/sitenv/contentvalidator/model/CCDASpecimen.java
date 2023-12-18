package org.sitenv.contentvalidator.model;

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
	
	
}
