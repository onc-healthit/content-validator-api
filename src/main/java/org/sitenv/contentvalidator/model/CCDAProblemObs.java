package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDAProblemObs {

	private static Logger log = LoggerFactory.getLogger(CCDAProblemObs.class.getName());
	
	private ArrayList<CCDAII>    templateId;
	private CCDACode             problemType;
	private ArrayList<CCDACode>  translationProblemType;
	private CCDAEffTime          effTime;
	private CCDACode             problemCode;
	private DiagnosisActDate	 dateOfDiagnosis;
	private ArrayList<AssessmentScaleObservation> assessmentScaleObservations;
	
	private CCDAAuthor author;
	
	public static void compareProblemObservationData(HashMap<String, CCDAProblemObs> refProblems, 
			HashMap<String, CCDAProblemObs> subProblems, 	ArrayList<ContentValidationResult> results, String context, boolean svap2022) {

		log.info(" Start Comparing Problem Observations for " + context);
		
		// For each problem Observation in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDAProblemObs> ent: refProblems.entrySet()) {

			if(subProblems.containsKey(ent.getKey())) {

				log.info("Comparing Problem Observation ");
				String compContext = "Problem Observation Entry associated with " + context + " for code " + ent.getKey();
				ent.getValue().compare(subProblems.get(ent.getKey()), compContext, results, svap2022);


			} 
			// Handle the cases where the codes are present in translation elements in either submitted or reference ccda.
			else if(!checkCodeAndTrans(ent.getValue(),subProblems)) {
				// Error
				
				String error = "The scenario contains Encounter Diagnosis data with code(s) " + 
				        ((ent.getValue().getProblemCode() != null)?ent.getValue().getProblemCode().getDebugCodeString():"") +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		/*	else {
				// Error
				String error = "The scenario contains Encounter Diagnosis data with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}*/
		}
		
	}
	
	public void compare(CCDAProblemObs subObs, String probObsContext, ArrayList<ContentValidationResult> results, boolean svap2022) {
		
		log.info(" Comparing data for problem observation Value element/code attribute: " + probObsContext);

		String elementName = "Problem Observation for Value element/code attribute: " + probObsContext;

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateId, subObs.getTemplateId(), results, elementName);

		// Compare Effective Times
		String elementNameTime = "Problem Observation Effective Time for Value element/code attribute: " + probObsContext;
		//ParserUtilities.compareEffectiveTime(effTime, subObs.getEffTime(), results, elementNameTime);
		
		// Compare PRoblem Codes 
		String elementNameVal = "Problem Observation Value element/code attribute: " + probObsContext;
		ParserUtilities.compareCode(problemCode, subObs.getProblemCode(), results, elementNameVal);
		
		// Compare Diagnosis Date Act
		String diagnosisElement = "Problem Observation Diagnosis Element attribute: " + probObsContext;
		
		if(dateOfDiagnosis != null && svap2022) {
			log.info(" Comparing Diagnosis Date ");
			dateOfDiagnosis.compare(subObs.dateOfDiagnosis, diagnosisElement, results);
		}
		
	}
	
	public void log() {
		
		log.info("***Problem Obs***");
		
		if(problemType != null)
			log.info("Problem Type Code = " + problemType.getCode());
		
		for(int l = 0; l < translationProblemType.size(); l++) {
			log.info("Problem Translation Type Code = " + translationProblemType.get(l).getCode());
		}
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		for(int k = 0; k < assessmentScaleObservations.size(); k++) {
			assessmentScaleObservations.get(k).log();
		}
		
		if(effTime != null)
			effTime.log();
		
		if(problemCode != null)
			log.info("Problem Code = " + problemCode.getCode());
		
		if(author != null)
			author.log();
		
		if(dateOfDiagnosis != null)
			dateOfDiagnosis.log();
	}
	
	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateId = ids;
	}

	public ArrayList<AssessmentScaleObservation> getAssessmentScaleObservations() {
		return assessmentScaleObservations;
	}

	public void setAssessmentScaleObservations(ArrayList<AssessmentScaleObservation> assessmentScaleObservations) {
		this.assessmentScaleObservations = assessmentScaleObservations;
	}

	public CCDACode getProblemType() {
		return problemType;
	}

	public void setProblemType(CCDACode problemType) {
		this.problemType = problemType;
	}

	public ArrayList<CCDACode> getTranslationProblemType() {
		return translationProblemType;
	}

	public void setTranslationProblemType(ArrayList<CCDACode> translationProblemType) {
		if(translationProblemType != null)
			this.translationProblemType = translationProblemType;
	}

	public CCDAEffTime getEffTime() {
		return effTime;
	}

	public void setEffTime(CCDAEffTime effTime) {
		this.effTime = effTime;
	}

	public CCDACode getProblemCode() {
		return problemCode;
	}

	public void setProblemCode(CCDACode problemCode) {
		this.problemCode = problemCode;
	}

	public CCDAProblemObs()
	{
		templateId = new ArrayList<CCDAII>();
		translationProblemType = new ArrayList<CCDACode>();
		assessmentScaleObservations = new ArrayList<>();
	}

	
	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public DiagnosisActDate getDateOfDiagnosis() {
		return dateOfDiagnosis;
	}

	public void setDateOfDiagnosis(DiagnosisActDate dateOfDiagnosis) {
		this.dateOfDiagnosis = dateOfDiagnosis;
	}

	static Boolean checkCodeAndTrans(CCDAProblemObs refObs, HashMap<String, CCDAProblemObs> subProblems) {
		
		/*
		 * Need to check the following conditions
		 * Code is present in the submitted Problem's code element - Already checked earlier , so not needed again.
		 * Code is present in the submitted Problem's Translation element
		 * Code is not present, but translation of code is present in the submitted code element
		 * Code is not present, but translation of code is present in the submitted code translation element
		 */
		
		CCDACode refCode = refObs.getProblemCode();
		
		for(Map.Entry<String,CCDAProblemObs> ent : subProblems.entrySet()) {
			
			Boolean retVal = ent.getValue().compareCodesAndTranslations(refCode);
			
			if(retVal)
				return true;
			
		}
		
		return false;
	}
	
	public Boolean compareCodesAndTranslations(CCDACode refCode) {
		
		return ParserUtilities.compareCodesAndTranlations(refCode, this.getProblemCode()); 
	}
	
	public HashMap<String, AssessmentScaleObservation> getAllSdohData() {
		
		HashMap<String, AssessmentScaleObservation> assessments = new HashMap<>();
		if(assessmentScaleObservations != null) {
			
			for(AssessmentScaleObservation obs : assessmentScaleObservations) {
			
				if(obs.getAssessmentCode() != null && obs.getAssessmentCode().getCode() != null) {
					assessments.put(obs.getAssessmentCode().getCode(), obs);
				}
				
			}
		}		
		return assessments;
	}

	public static void compareHcActs(HashMap<String, CCDAProblemObs> refHcActs,
			HashMap<String, CCDAProblemObs> subHcActs, ArrayList<ContentValidationResult> results, boolean svap2022) {
		
		// Check only the reference ones
		for(Map.Entry<String,CCDAProblemObs> entry: refHcActs.entrySet()) {
					
					if(subHcActs.containsKey(entry.getKey())) {
						
						// Since the observation was found, compare other data elements.
						log.info(" Comparing Health Concern Act Problem Observation");
						String compContext = " Comparing Health Concern Act Problem Observation for code " + entry.getKey();
						entry.getValue().compare(subHcActs.get(entry.getKey()), compContext, results, svap2022);
						
					}
					else {
						
						String error = "The scenario contains Health Concern Act with Problem Observation data with code " + entry.getKey() + 
								" , however there is no matching data in the submitted CCDA.";
						ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
						results.add(rs);
					}
					
				}
	}
	
	
}
