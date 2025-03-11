package org.sitenv.contentvalidator.model;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	private ArrayList<CCDASexObservation>		    sexObservations;
	private ArrayList<AssessmentScaleObservation>   assessments;
	
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
		
		if(sexObservations != null) {
			for(int s = 0; s < sexObservations.size(); s++) {
				sexObservations.get(s).log();
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
		sexObservations = new ArrayList<>();
		assessments = new ArrayList<>();
	}
	
	public ArrayList<AssessmentScaleObservation> getAssessments() {
		return assessments;
	}

	public void setAssessments(ArrayList<AssessmentScaleObservation> assessments) {
		this.assessments = assessments;
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
	
	public HashMap<String, AssessmentScaleObservation> getAllAssessments() {
		
		HashMap<String, AssessmentScaleObservation> assessment= new HashMap<>();
		if(assessments != null && !assessments.isEmpty()) { 
			
			for(AssessmentScaleObservation obs : assessments) {
				if(obs.getAssessmentCode() != null && obs.getAssessmentCode().getCode() != null) {
					assessment.put(obs.getAssessmentCode().getCode(), obs);
				}
			}
		}
		return assessment;
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
	
	public HashMap<String, CCDASexObservation> getAllSexObservations() {
		
		HashMap<String, CCDASexObservation> sexObs = new HashMap<>();
		if(sexObservations != null) {
			for(int i = 0; i < sexObservations.size(); i++) {
				
				if(sexObservations.get(i).getSexValue() != null && 
						sexObservations.get(i).getSexValue().getCode() != null) {
					
					sexObs.put(sexObservations.get(i).getSexValue().getCode(),
							sexObservations.get(i));
				}
				else if (sexObservations.get(i).getSexValue() != null && 
						sexObservations.get(i).getSexValue().getNullFlavor() != null) {
					
					sexObs.put(sexObservations.get(i).getSexValue().getNullFlavor(),
							sexObservations.get(i));
				}
				
			}
		}		
		return sexObs;
	}
	
	public HashMap<String, CCDATribalAffiliationObservation> getAllTribalAffiliations() {
		
		HashMap<String, CCDATribalAffiliationObservation> tribalObs = new HashMap<>();
		if(tribalAffiliations != null) {
			for(int i = 0; i < tribalAffiliations.size(); i++) {
				
				if(tribalAffiliations.get(i).getTribalAffiliationValue() != null && 
						tribalAffiliations.get(i).getTribalAffiliationValue().getCode() != null) {
					
					tribalObs.put(tribalAffiliations.get(i).getTribalAffiliationValue().getCode(),
							tribalAffiliations.get(i));
				}
			}
		}		
		return tribalObs;
	}
	
	public HashMap<String, CCDABasicOccupation> getAllBasicOccupations() {
		
		HashMap<String, CCDABasicOccupation> basicObs = new HashMap<>();
		if(occupation != null) {
			for(int i = 0; i < occupation.size(); i++) {
				
				if(occupation.get(i).getBasicOccupationValueCode() != null && 
						occupation.get(i).getBasicOccupationValueCode().getCode() != null) {
					
					basicObs.put(occupation.get(i).getBasicOccupationValueCode().getCode(),
							occupation.get(i));
				}
			}
		}		
		return basicObs;
	}
	
	public HashMap<String, CCDAPregnancyObservation> getAllPregnancyObservations() {
		
		HashMap<String, CCDAPregnancyObservation> pregnancyObs = new HashMap<>();
		if(pregnancyObservations != null) {
			for(int i = 0; i < pregnancyObservations.size(); i++) {
				
				if(pregnancyObservations.get(i).getPregnancyValue() != null && 
						pregnancyObservations.get(i).getPregnancyValue().getCode() != null) {
					
					pregnancyObs.put(pregnancyObservations.get(i).getPregnancyValue().getCode(),
							pregnancyObservations.get(i));
				}
			}
		}		
		return pregnancyObs;
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

	public ArrayList<CCDASexObservation> getSexObservations() {
		return sexObservations;
	}

	public void setSexObservations(ArrayList<CCDASexObservation> sexObservations) {
		this.sexObservations = sexObservations;
	}

	public void compare(String validationObjective, CCDASocialHistory subSocialHistory, boolean curesUpdate,
			boolean svap2022, boolean svap2023, boolean svap2024, ArrayList<ContentValidationResult> results) {
		
		// Get Submitted observations
		HashMap<String, CCDASexObservation> subSexObs = null;
		HashMap<String, CCDABasicOccupation> subOccObs = null;
		HashMap<String, CCDATribalAffiliationObservation> subTribalAffiliationObs = null;
		HashMap<String, CCDAPregnancyObservation> subPregnancyObs = null;
		HashMap<String, AssessmentScaleObservation> subAssessments = null;
		
		if(subSocialHistory != null) {
			subSexObs = subSocialHistory.getAllSexObservations();
			subOccObs = subSocialHistory.getAllBasicOccupations();
			subTribalAffiliationObs = subSocialHistory.getAllTribalAffiliations();
			subPregnancyObs = subSocialHistory.getAllPregnancyObservations();
			subAssessments = subSocialHistory.getAllAssessments();
			
		}
		
		// Compare Sex Observations
		compareSexObservations(this.getAllSexObservations(), subSexObs,
							results);
					
		// Compare Occupation Observation
		compareOccObservations(this.getAllBasicOccupations(), subOccObs,
							results);
					
		// Compare Tribal Affiliation Observations
		compareTribalAffiliations(this.getAllTribalAffiliations(), subTribalAffiliationObs,
							results);
					
		// Compare Pregnancy Observations
		comparePregnancyObservations(this.getAllPregnancyObservations(), subPregnancyObs,
							results);		
		
		if(svap2024)
			compareAssessments(this.getAllAssessments(), subAssessments, results);
	}

	private void compareAssessments(HashMap<String, AssessmentScaleObservation> refAssessments,
			HashMap<String, AssessmentScaleObservation> subAssessments, ArrayList<ContentValidationResult> results) {
		
		if( (refAssessments != null && refAssessments.size() > 0) &&  
				(subAssessments != null && subAssessments.size() > 0)  ) {
				
				log.info("Assessments present in both models ");
				AssessmentScaleObservation.compare(refAssessments, subAssessments, results);
				
			} 	
			else if ( (refAssessments != null && refAssessments.size() > 0) && 
					(subAssessments == null || subAssessments.size() == 0) ) {
				
				ContentValidationResult rs = new ContentValidationResult("The scenario requires Patient's Asssessment data for one of [Alcohol|Substance|PhysicalActivity] "
						+ "but the submitted C-CDA does not contain Asssessment data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info(" Scenario requires Asssessment data for one of [Alcohol|Substance|PhysicalActivity], but submitted document does not contain Assessment data");
				
			}else if ((refAssessments == null || refAssessments.size() == 0) && 
					(subAssessments != null && subAssessments.size() > 0) ) {
				
				
				log.info("Model does not have Assessment Data for comparison, allow this to pass");
				
			} else {
				
				log.info("Model and Submitted CCDA do not have Assessment data for comparison ");
			}
		
	}

	public static void comparePregnancyObservations(HashMap<String, CCDAPregnancyObservation> refPregnancyObservations,
			HashMap<String, CCDAPregnancyObservation> subPregnancyObs, ArrayList<ContentValidationResult> results) {
		
		log.info(" Start Comparing Pregnancy Observations ");
		// For each sex observation  in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDAPregnancyObservation> ent: refPregnancyObservations.entrySet()) {

			if(subPregnancyObs != null && subPregnancyObs.containsKey(ent.getKey())) {

				log.info("Comparing Pregnancy Observation ");
				String context = "Pregnancy Observation Entry corresponding to the code " + ent.getKey();
				subPregnancyObs.get(ent.getKey()).compare(ent.getValue(), results, context);


			} else {
				// Error
				String error = "The scenario contains Pregnancy Observations with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the Occupation data is not present in the reference, 
		if( (refPregnancyObservations == null || refPregnancyObservations.size() == 0) && (subPregnancyObs != null && subPregnancyObs.size() > 0) ) {

			// Error
			String error = "The scenario does not require Pregnancy Observation data " + 
					" , however there is Pregnancy Observation in the submitted CCDA, check if this is appropriate manually. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}

	public static void compareTribalAffiliations(HashMap<String, CCDATribalAffiliationObservation> refTribalAffiliations,
			HashMap<String, CCDATribalAffiliationObservation> subTribalAffiliationObs,
			ArrayList<ContentValidationResult> results) {
		
		log.info(" Start Comparing Tribal Affiliation Observations ");
		// For each sex observation  in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDATribalAffiliationObservation> ent: refTribalAffiliations.entrySet()) {

			if(subTribalAffiliationObs != null && subTribalAffiliationObs.containsKey(ent.getKey())) {

				log.info("Comparing Tribal Affiliation Observation ");
				String context = "Tribal Affiliation Observation Entry corresponding to the code " + ent.getKey();
				subTribalAffiliationObs.get(ent.getKey()).compare(ent.getValue(), results, context);


			} else {
				// Error
				String error = "The scenario contains Tribal Affiliation data with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the Occupation data is not present in the reference, 
		if( (refTribalAffiliations == null || refTribalAffiliations.size() == 0) && (subTribalAffiliationObs != null && subTribalAffiliationObs.size() > 0) ) {

			// Error
			String error = "The scenario does not require Tribal Affiliation data " + 
					" , however there is Tribal Affiliation data in the submitted CCDA, check if this is appropriate manually. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}

	public static void compareOccObservations(HashMap<String, CCDABasicOccupation> refOccObs,
			HashMap<String, CCDABasicOccupation> subOccObs, ArrayList<ContentValidationResult> results) {
		
		log.info(" Start Comparing Occupation Observations ");
		// For each sex observation  in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDABasicOccupation> ent: refOccObs.entrySet()) {

			if(subOccObs != null && subOccObs.containsKey(ent.getKey())) {

				log.info("Comparing Basic Occupation Observation ");
				String context = "Basic Occupation Observation Entry corresponding to the code " + ent.getKey();
				subOccObs.get(ent.getKey()).compare(ent.getValue(), results, context);


			} else {
				// Error
				String error = "The scenario contains Basic Occupation Observation data with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the Occupation data is not present in the reference, 
		if( (refOccObs == null || refOccObs.size() == 0) && (subOccObs != null && subOccObs.size() > 0) ) {

			// Error
			String error = "The scenario does not require Basic Occupation Observation data " + 
					" , however there is Occupation Observation in the submitted CCDA, check if this is appropriate manually. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
		
	}

	public static void compareSexObservations(HashMap<String, CCDASexObservation> refSexObservations,
			HashMap<String, CCDASexObservation> subSexObs, ArrayList<ContentValidationResult> results) {
		
		log.info(" Start Comparing Sex Observations ");
		// For each sex obseration  in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDASexObservation> ent: refSexObservations.entrySet()) {

			if(subSexObs != null && subSexObs.containsKey(ent.getKey())) {

				log.info("Comparing Sex Observation ");
				String context = "Sex Observation Entry corresponding to the code " + ent.getKey();
				subSexObs.get(ent.getKey()).compare(ent.getValue(), results, context);


			} else {
				// Error
				String error = "The scenario contains Sex Observation data with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the Sex Observation data is not present in the reference, 
		if( (refSexObservations == null || refSexObservations.size() == 0) && (subSexObs != null && subSexObs.size() > 0) ) {

			// Error
			String error = "The scenario does not require Sex Observation data " + 
					" , however there is Sex Observation in the submitted CCDA, check if this is appropriate manually. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}

	
	
	
	
}
