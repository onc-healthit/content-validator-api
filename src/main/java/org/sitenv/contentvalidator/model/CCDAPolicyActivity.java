package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAPolicyActivity {
	
private static Logger log = LoggerFactory.getLogger(CCDAAllergyObs.class.getName());
	
	private ArrayList<CCDAII>    			templateId;
	private int 							sequenceNumber;
	private CCDAII							groupIdentifier;
	private CCDACode             			coverageType;
	private CCDACode 						statusCode;
	private CCDAPerformer					payer;
	private CCDAPerformer					guarantor;
	private CCDAParticipant					coverageParticipant;
	private CCDAParticipant					holderOrSubscriberParticipant;
	
	public void log() {
		
		log.info(" ***** Policy Activity *****");
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		log.info(" Sequence Number of Coverage {}", sequenceNumber);
		
		if(groupIdentifier != null) 
			groupIdentifier.log();
		
		if(coverageType != null) 
			log.info(" Coverage Type Code {}", coverageType.getCode());
		
		if(statusCode != null) 
			log.info(" Policy Activity Status Code {}", statusCode.getCode());
		
		if(payer != null) 
			payer.log();
		
		if(guarantor != null) 
			guarantor.log();
		
		if(coverageParticipant != null) 
			coverageParticipant.log();
		
		if(holderOrSubscriberParticipant != null) 
			holderOrSubscriberParticipant.log();
	}
	
	public CCDAPolicyActivity() {
		templateId = new ArrayList<CCDAII>();
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public CCDAII getGroupIdentifier() {
		return groupIdentifier;
	}

	public void setGroupIdentifier(CCDAII groupIdentifier) {
		this.groupIdentifier = groupIdentifier;
	}

	public CCDACode getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(CCDACode coverageType) {
		this.coverageType = coverageType;
	}

	public CCDACode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDACode statusCode) {
		this.statusCode = statusCode;
	}

	public CCDAPerformer getPayer() {
		return payer;
	}

	public void setPayer(CCDAPerformer payer) {
		this.payer = payer;
	}

	public CCDAPerformer getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(CCDAPerformer guarantor) {
		this.guarantor = guarantor;
	}

	public CCDAParticipant getCoverageParticipant() {
		return coverageParticipant;
	}

	public void setCoverageParticipant(CCDAParticipant coverageParticipant) {
		this.coverageParticipant = coverageParticipant;
	}

	public CCDAParticipant getHolderOrSubscriberParticipant() {
		return holderOrSubscriberParticipant;
	}

	public void setHolderOrSubscriberParticipant(CCDAParticipant holderOrSubscriberParticipant) {
		this.holderOrSubscriberParticipant = holderOrSubscriberParticipant;
	}

	
}
