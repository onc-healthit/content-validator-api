package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAReasonForReferral {
	
private static Logger log = LoggerFactory.getLogger(CCDAReasonForReferral.class.getName());
	
	private ArrayList<CCDAII>					sectionTemplateIds;
	private CCDACode							sectionCode;
	private ArrayList<CCDAPatientReferralAct>	referralActs;
	private CCDAAuthor							author;
	private CCDADataElement						sectionText;
	
	public CCDAReasonForReferral() {
		sectionTemplateIds = new ArrayList<>();
		referralActs = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** Reason for Referral Section *** ");
		
		if(sectionCode != null)
			log.info(" SocialHistory Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < sectionTemplateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateIds.get(j).getExtValue());
		}
		
		for(int k = 0; k < referralActs.size(); k++) {
			referralActs.get(k).log();
		}
		
	}

	public ArrayList<CCDAII> getSectionTemplateIds() {
		return sectionTemplateIds;
	}

	public void setSectionTemplateIds(ArrayList<CCDAII> sectionTemplateIds) {
		this.sectionTemplateIds = sectionTemplateIds;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDAPatientReferralAct> getReferralActs() {
		return referralActs;
	}

	public void setReferralActs(ArrayList<CCDAPatientReferralAct> referralActs) {
		this.referralActs = referralActs;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	public CCDADataElement getSectionText() {
		return sectionText;
	}

	public void setSectionText(CCDADataElement sectionText) {
		this.sectionText = sectionText;
	}

	public HashMap<String, CCDAPatientReferralAct> getAllReferrals() {
		
		HashMap<String, CCDAPatientReferralAct> referrals = new HashMap<>();
		if(referralActs != null && !referralActs.isEmpty()) { 
			
			for(CCDAPatientReferralAct act : referralActs) {
				if(act.getReferralCode() != null && act.getReferralCode().getCode() != null) {
					referrals.put(act.getReferralCode().getCode(), act);
				}
			}
		}
		return referrals;
	}

}
