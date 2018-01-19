package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;

public class CCDAParticipant {
	
	private static Logger log = Logger.getLogger(CCDAParticipant.class.getName());
	
	private CCDADataElement firstName;
	private CCDADataElement lastName;
	private CCDADataElement middleName;
	private CCDAAddress     address;
	private CCDATelecom 	telecom;
	
	public void log()
	{
		log.info(" First Name = " + ((firstName==null)?"No Data":firstName.getValue()));
		log.info(" Last Name = " + ((lastName==null)?"No Data":lastName.getValue()));
		log.info(" Middle Name = " + ((middleName==null)?"No Data":middleName.getValue()));
		
		if(address != null)
			address.log();
		
		telecom.log();		
	}
	
	public CCDAParticipant()
	{
		
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

	public CCDAAddress getAddress() {
		return address;
	}

	public void setAddress(CCDAAddress address) {
		this.address = address;
	}

	public CCDATelecom getTelecom() {
		return telecom;
	}

	public void setTelecom(CCDATelecom telecom) {
		this.telecom = telecom;
	}

	public void setPreviousName(CCDADataElement readTextContext) {
		// No need for the data element.
		
	}

	public void setSuffix(CCDADataElement readTextContext) {
		//No need for the data element.
		
	}
}
