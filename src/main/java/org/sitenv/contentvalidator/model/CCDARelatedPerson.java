package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDARelatedPerson {
	
	private static Logger log = LoggerFactory.getLogger(CCDARelatedPerson.class.getName());

	private String                            	firstName;
	private String							  	lastName;
	private CCDACode							relationship;
	
	public CCDARelatedPerson() {
		
	}
	
	public void log() {
		
		log.info(" *** CCDA Related Person *** ");
		
		if(firstName != null)
			log.info("First Name : {}", firstName);
		
		if(lastName != null)
			log.info("Last Name : {}", lastName);
		
		if(relationship != null)
			relationship.log();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public CCDACode getRelationship() {
		return relationship;
	}

	public void setRelationship(CCDACode relationship) {
		this.relationship = relationship;
	}
	
	
	

}
