package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CCDAAssignedEntity {
	
	private static Logger log = LoggerFactory.getLogger(CCDAAssignedEntity.class.getName());

	private ArrayList<CCDATelecom>			telecom;
	private ArrayList<CCDAAddress>			addresses;
	private CCDAOrganization				organization;
	private CCDACode						assignedEntityCode;
	private ArrayList<CCDAII>				assignedEntityId;
	private CCDADataElement firstName;
	private CCDADataElement lastName;
	private CCDADataElement middleName;
	private CCDADataElement suffix;
	
	private CCDADataElement previousName;
	
	private CCDAII			idReference;
	
	public void addAddress(CCDAAddress addr) {
		addresses.add(addr);
	}
	
	public void addTelecom(CCDATelecom tel) {
		telecom.add(tel);
	}
	
	public void log() {
		
		log.info(" *** Assigned Entity Log ***");
		
		if(assignedEntityId != null) {
		for(int i = 0; i < assignedEntityId.size(); i++) {
			log.info(" Tempalte Id [" + i+ "] = " + assignedEntityId.get(i).getRootValue());
			log.info(" Tempalte Id Ext [" + i + "] = " + assignedEntityId.get(i).getExtValue());
		}
	}
		
		if(organization != null)
			organization.log();
		
		if(assignedEntityCode != null) 
			log.info(" Assigned Entity Code {}", assignedEntityCode.getCode());
		
		if(firstName != null)
			log.info(" Assigned Entity Person First Name : {}",firstName);
		if(lastName != null)
			log.info(" Assigned Entity Person First Name : {}",lastName);
		if(middleName != null)
			log.info(" Assigned Entity Person First Name : {}",middleName);
	}
	
	public ArrayList<CCDATelecom> getTelecom() {
		return telecom;
	}

	public void setTelecom(ArrayList<CCDATelecom> tels) {
		
		if(tels != null)
			this.telecom = tels;
	}

	public ArrayList<CCDAAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(ArrayList<CCDAAddress> addrs) {
		
		if(addrs != null)
			this.addresses = addrs;
	}

	public CCDAOrganization getOrganization() {
		return organization;
	}

	public void setOrganization(CCDAOrganization organization) {
		this.organization = organization;
	}

	public CCDACode getAssignedEntityCode() {
		return assignedEntityCode;
	}

	public void setAssignedEntityCode(CCDACode assignedEntityCode) {
		this.assignedEntityCode = assignedEntityCode;
	}

	public CCDAAssignedEntity()
	{
		telecom = new ArrayList<CCDATelecom>();
		addresses = new ArrayList<CCDAAddress>();
		
	}

	public ArrayList<CCDAII> getAssignedEntityId() {
		return assignedEntityId;
	}

	public void setAssignedEntityId(ArrayList<CCDAII> assignedEntityId) {
		this.assignedEntityId = assignedEntityId;
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

	public CCDADataElement getPreviousName() {
		return previousName;
	}

	public void setPreviousName(CCDADataElement previousName) {
		this.previousName = previousName;
	}

	public CCDADataElement getSuffix() {
		return suffix;
	}

	public void setSuffix(CCDADataElement suffix) {
		this.suffix = suffix;
	}

	public CCDAII getIdReference() {
		return idReference;
	}

	public void setIdReference(CCDAII idReference) {
		this.idReference = idReference;
	}
	
	
}
