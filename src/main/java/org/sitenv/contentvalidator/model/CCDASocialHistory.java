package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CCDASocialHistory {

	private static Logger log = LoggerFactory.getLogger(CCDASocialHistory.class.getName());
	
	private ArrayList<CCDAII>					sectionTemplateIds;
	private CCDACode							sectionCode;
	private ArrayList<CCDASmokingStatus>		smokingStatus;
	private ArrayList<CCDATobaccoUse>			tobaccoUse;
	private CCDABirthSexObs						birthSex;
	private ArrayList<CCDASexualOrientation>	sexualOrientations;
	private ArrayList<CCDAGenderIdentityObs>		genderIdentities;
	private ArrayList<CCDASocialHistoryObs>			socialHistoryObservations;
	
	private CCDAAuthor author;
	
	public CCDABirthSexObs getBirthSex() {
		return birthSex;
	}

	public void setBirthSex(CCDABirthSexObs birthSex) {
		this.birthSex = birthSex;
	}

	public void log() {
		
		if(sectionCode != null)
			log.info(" SocialHistory Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < sectionTemplateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateIds.get(j).getExtValue());
		}
		
		for(int k = 0; k < smokingStatus.size(); k++) {
			smokingStatus.get(k).log();
		}
		
		for(int l = 0; l < tobaccoUse.size(); l++) {
			tobaccoUse.get(l).log();
		}
		
		for(int m = 0; m < sexualOrientations.size(); m++) {
			sexualOrientations.get(m).log();
		}
		
		for(int n = 0; n < genderIdentities.size(); n++) {
			genderIdentities.get(n).log();
		}
		
		for(int p = 0; p < socialHistoryObservations.size(); p++) {
			genderIdentities.get(p).log();
		}
		
		if(birthSex != null)
			birthSex.log();
		
		if(author != null)
			author.log();
	}
	
	public CCDASocialHistory()
	{
		sectionTemplateIds = new ArrayList<CCDAII>();
		smokingStatus = new ArrayList<CCDASmokingStatus>();
		tobaccoUse = new ArrayList<CCDATobaccoUse>();
		sexualOrientations = new ArrayList<CCDASexualOrientation>();
		genderIdentities = new ArrayList<>();
		socialHistoryObservations = new ArrayList<>();
	}

	
	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public ArrayList<CCDAII> getSectionTemplateIds() {
		return sectionTemplateIds;
	}

	public void setSectionTemplateIds(ArrayList<CCDAII> ids) {
		
		
		if(ids != null)
			this.sectionTemplateIds = ids;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDASmokingStatus> getSmokingStatus() {
		return smokingStatus;
	}

	public void setSmokingStatus(ArrayList<CCDASmokingStatus> ss) {
		
		if(ss != null)
			this.smokingStatus = ss;
	}

	public ArrayList<CCDATobaccoUse> getTobaccoUse() {
		return tobaccoUse;
	}

	public void setTobaccoUse(ArrayList<CCDATobaccoUse> tu) {
		
		if(tu != null)
			this.tobaccoUse = tu;
	}

	public ArrayList<CCDASexualOrientation> getSexualOrientations() {
		return sexualOrientations;
	}

	public void setSexualOrientations(ArrayList<CCDASexualOrientation> sexualOrientations) {
		this.sexualOrientations = sexualOrientations;
	}

	public ArrayList<CCDAGenderIdentityObs> getGenderIdentities() {
		return genderIdentities;
	}

	public void setGenderIdentities(ArrayList<CCDAGenderIdentityObs> genderIdentities) {
		this.genderIdentities = genderIdentities;
	}

	public ArrayList<CCDASocialHistoryObs> getSocialHistoryObservations() {
		return socialHistoryObservations;
	}

	public void setSocialHistoryObservations(ArrayList<CCDASocialHistoryObs> socialHistoryObservations) {
		this.socialHistoryObservations = socialHistoryObservations;
	}
	
	
	
}
