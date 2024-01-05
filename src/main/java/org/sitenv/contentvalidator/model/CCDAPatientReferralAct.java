package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAPatientReferralAct {
	
	private static Logger log = LoggerFactory.getLogger(CCDAPatientReferralAct.class.getName());
	
	private ArrayList<CCDAII>     				templateIds;
	private CCDACode							referralCode;
	private CCDACode							statusCode;
	private CCDAEffTime							effectiveTime;
	private ArrayList<CCDAIndication>			indications;
	CCDAAuthor 									author;
	
	public CCDAPatientReferralAct() {		
		templateIds = new ArrayList<>();
		indications = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** Patient Referral Act *** ");
		
		if(referralCode != null)
			log.info(" Patient Referral Act Code = " + referralCode.getCode());
		
		if(statusCode != null)
			log.info(" Patient Referral Act Status Code = " + statusCode.getCode());
		
		if(effectiveTime != null)
			effectiveTime.log();
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		for(int k = 0; k < indications.size(); k++) {
			indications.get(k).log();
		}
		
		if(author != null)
			author.log();
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(CCDACode referralCode) {
		this.referralCode = referralCode;
	}

	public CCDACode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDACode statusCode) {
		this.statusCode = statusCode;
	}

	public CCDAEffTime getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(CCDAEffTime effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public ArrayList<CCDAIndication> getIndications() {
		return indications;
	}

	public void setIndications(ArrayList<CCDAIndication> indications) {
		this.indications = indications;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	

	
}
