package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAPlayingEntity {

	private static Logger log = LoggerFactory.getLogger(CCDAPlayingEntity.class.getName());

	private CCDADataElement previousName;
	private CCDADataElement firstName;
	private CCDADataElement lastName;
	private CCDADataElement middleName;
	private CCDADataElement suffix;
	private CCDADataElement dob;
	
	public CCDAPlayingEntity() {
		
	}
	
	public void log() {
		
		if(firstName != null)
			log.info(" First Name = {}", firstName.getValue());
		
		if(lastName != null)
			log.info(" Last Name = {}", lastName.getValue());
		
		if(middleName != null)
			log.info(" Middle Name = {}", middleName.getValue());
		
		if(suffix != null)
			log.info(" Suffix= {}", suffix.getValue());
		
		if(previousName != null)
			log.info(" Previous Name = {}", previousName.getValue());
		
		if(dob != null)
			log.info(" Dob = {}", dob.getValue());
		
	}

	public CCDADataElement getDob() {
		return dob;
	}

	public void setDob(CCDADataElement dob) {
		this.dob = dob;
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

	public CCDADataElement getPreviousName() {
		return previousName;
	}

	public void setPreviousName(CCDADataElement previousName) {
		this.previousName = previousName;
	}
	
	
}
