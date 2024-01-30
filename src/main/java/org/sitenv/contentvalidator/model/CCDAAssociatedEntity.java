package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAAssociatedEntity {
	
	private static Logger log = LoggerFactory.getLogger(CCDAAssociatedEntity.class.getName());

	private CCDADataElement firstName;
	private CCDADataElement lastName;
	private CCDADataElement middleName;
	private CCDADataElement suffix;
	
	private CCDACode		associatedEntityCode;
	
	public CCDAAssociatedEntity() {
		
	}
	
	public void log() {
		
		log.info(" *** Associated Entity Log ***");
		
		if(firstName != null)
			log.info(" Associated Entity Person First Name : {}",firstName);
		if(lastName != null)
			log.info(" Associated Entity Person First Name : {}",lastName);
		if(middleName != null)
			log.info(" Associated Entity Person First Name : {}",middleName);
		if(associatedEntityCode != null) 
			log.info(" Associated Entity Code : {}", associatedEntityCode.getCode());
	}

	public CCDADataElement getFirstName() {
		return firstName;
	}

	public void setFirstName(CCDADataElement firstName) {
		this.firstName = firstName;
	}

	public CCDADataElement getLastName() {
		return lastName;
	}

	public void setLastName(CCDADataElement lastName) {
		this.lastName = lastName;
	}

	public CCDADataElement getMiddleName() {
		return middleName;
	}

	public void setMiddleName(CCDADataElement middleName) {
		this.middleName = middleName;
	}

	public CCDADataElement getSuffix() {
		return suffix;
	}

	public void setSuffix(CCDADataElement suffix) {
		this.suffix = suffix;
	}

	public CCDACode getAssociatedEntityCode() {
		return associatedEntityCode;
	}

	public void setAssociatedEntityCode(CCDACode associatedEntityCode) {
		this.associatedEntityCode = associatedEntityCode;
	}
	
	

}
