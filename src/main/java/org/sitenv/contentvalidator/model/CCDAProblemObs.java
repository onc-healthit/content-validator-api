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
	
	private CCDAAuthor author;
	
	public static void compareProblemObservationData(HashMap<String, CCDAProblemObs> refProblems, 
			HashMap<String, CCDAProblemObs> subProblems, 	ArrayList<ContentValidationResult> results, String context) {

		log.info(" Start Comparing Problem Observations for " + context);
		
		// For each problem Observation in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDAProblemObs> ent: refProblems.entrySet()) {

			if(subProblems.containsKey(ent.getKey())) {

				log.info("Comparing Problem Observation ");
				String compContext = "Problem Observation Entry associated with " + context + " for code " + ent.getKey();
				ent.getValue().compare(subProblems.get(ent.getKey()), compContext, results);


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
	
	public void compare(CCDAProblemObs subObs, String probObsContext, ArrayList<ContentValidationResult> results) {
		
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
		
		// Add negation indicator
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
		
		if(effTime != null)
			effTime.log();
		
		if(problemCode != null)
			log.info("Problem Code = " + problemCode.getCode());
		
		if(author != null)
			author.log();
	}
	
	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateId = ids;
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
	}

	
	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
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
}
