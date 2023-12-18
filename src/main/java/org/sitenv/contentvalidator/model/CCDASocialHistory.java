package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

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
	private ArrayList<CCDAPregnancyObservation>		pregnancyObservations;
	private ArrayList<CCDATribalAffiliationObservation> tribalAffiliations;
	private ArrayList<CCDABasicOccupation>			occupation;
	
	private CCDAAuthor author;
	
	public CCDABirthSexObs getBirthSex() {
		return birthSex;
	}

	public void setBirthSex(CCDABirthSexObs birthSex) {
		this.birthSex = birthSex;
	}

	public void log() {
		
		log.info(" *** Social History Section ***");
		
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
		
		if(sexualOrientations != null) {
		for(int m = 0; m < sexualOrientations.size(); m++) {
			sexualOrientations.get(m).log();
		}
		}
	
		if(genderIdentities != null) {
		for(int n = 0; n < genderIdentities.size(); n++) {
			genderIdentities.get(n).log();
		}
		}
		
		
		if(socialHistoryObservations != null) {
		for(int p = 0; p < socialHistoryObservations.size(); p++) {
			socialHistoryObservations.get(p).log();
		}
		}
		
		if(pregnancyObservations != null) {
		for(int q = 0; q < pregnancyObservations.size(); q++) {
			pregnancyObservations.get(q).log();
		}
		}
		
		if(tribalAffiliations != null) {
		for(int r = 0; r < tribalAffiliations.size(); r++) {
			tribalAffiliations.get(r).log();
		}
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
		pregnancyObservations = new ArrayList<>();
		tribalAffiliations = new ArrayList<>();
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

	public HashMap<String, CCDASexualOrientation> getAllSexualOrientations() {
		
		HashMap<String, CCDASexualOrientation> orientations = new HashMap<>();
		if(sexualOrientations != null) {
			for(int i = 0; i < sexualOrientations.size(); i++) {
				
				if(sexualOrientations.get(i).getSexualOrientationValue() != null && 
						sexualOrientations.get(i).getSexualOrientationValue().getCode() != null) {
					
					orientations.put(sexualOrientations.get(i).getSexualOrientationValue().getCode(),
							sexualOrientations.get(i));
				}
				else if (sexualOrientations.get(i).getSexualOrientationValue() != null && 
						sexualOrientations.get(i).getSexualOrientationValue().getNullFlavor() != null) {
					
					orientations.put(sexualOrientations.get(i).getSexualOrientationValue().getNullFlavor(),
							sexualOrientations.get(i));
				}
				
			}
		}		
		return orientations;
	}
	
	public HashMap<String, CCDAGenderIdentityObs> getAllGenderIdentities() {
		
		HashMap<String, CCDAGenderIdentityObs> identities = new HashMap<>();
		if(genderIdentities != null) {
			for(int i = 0; i < genderIdentities.size(); i++) {
				
				log.info(" Found a Potential Gender Identity ");
				if(genderIdentities.get(i).getGenderIdentityValue() != null && 
						genderIdentities.get(i).getGenderIdentityValue().getCode() != null) {
					log.info(" Adding Gender Identity based on code");
					identities.put(genderIdentities.get(i).getGenderIdentityValue().getCode(),
							genderIdentities.get(i));
				}
				else if (genderIdentities.get(i).getGenderIdentityValue() != null && 
						genderIdentities.get(i).getGenderIdentityValue().getNullFlavor() != null) {
					
					log.info(" Adding Gender Identity based on NullFlavor");
					identities.put(genderIdentities.get(i).getGenderIdentityValue().getNullFlavor(),
							genderIdentities.get(i));
				}
				else {
					log.info(" Gender Identity not added ");
					log.info(" Value = ",genderIdentities.get(i).getGenderIdentityValue().getNullFlavor());
					log.info(" Value = ",genderIdentities.get(i).getGenderIdentityValue().getCode());
				}
				
			}
		}		
		return identities;
	}
	
	public HashMap<String, AssessmentScaleObservation> getAllSdohData() {
		
		return getAllAssessmentScaleObservations();
	}
	
	public HashMap<String, AssessmentScaleObservation> getAllAssessmentScaleObservations() {
		
		HashMap<String, AssessmentScaleObservation> assessments = new HashMap<>();
		if(socialHistoryObservations != null) {
			for(CCDASocialHistoryObs obs: socialHistoryObservations) {
				
				HashMap<String, AssessmentScaleObservation> observations = obs.getAllAssessmentScaleObservations();
				
				if(observations != null && !observations.isEmpty()) {
					assessments.putAll(observations);
				}
				
			}
		}		
		return assessments;
	}

	public ArrayList<CCDAPregnancyObservation> getPregnancyObservations() {
		return pregnancyObservations;
	}

	public void setPregnancyObservations(ArrayList<CCDAPregnancyObservation> pregnancyObservations) {
		this.pregnancyObservations = pregnancyObservations;
	}

	public ArrayList<CCDATribalAffiliationObservation> getTribalAffiliations() {
		return tribalAffiliations;
	}

	public void setTribalAffiliations(ArrayList<CCDATribalAffiliationObservation> tribalAffiliations) {
		this.tribalAffiliations = tribalAffiliations;
	}

	public ArrayList<CCDABasicOccupation> getOccupation() {
		return occupation;
	}

	public void setOccupation(ArrayList<CCDABasicOccupation> occupation) {
		this.occupation = occupation;
	}
	
	
	
}
