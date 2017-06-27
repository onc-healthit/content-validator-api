package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;

public class CCDAAllergyObs {

	private static Logger log = Logger.getLogger(CCDAAllergyObs.class.getName());
	
	private ArrayList<CCDAII>    			templateId;
	private CCDACode             			allergyIntoleranceType;
	private CCDACode             			allergySubstance;
	private CCDAEffTime          			effTime;
	private ArrayList<CCDAAllergyReaction>  reactions;
	private Boolean							negationInd;
	
	public void compare(CCDAAllergyObs subObs, String allergyObsContext, ArrayList<ContentValidationResult> results) {
		
		log.info(" Comparing data for Allergy observation Value element/code attribute: " + allergyObsContext);

		String elementName = "Allergy Observation for Value element/code attribute: " + allergyObsContext;

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateId, subObs.getTemplateId(), results, elementName);

		// Compare Effective Times
		String elementNameTime = "Allergy Observation Effective Time for Value element/code attribute: " + allergyObsContext;
		//ParserUtilities.compareEffectiveTime(effTime, subObs.getEffTime(), results, elementNameTime);
		
		// Compare Allergy Codes 
		String elementNameVal = "Allergy Observation Value element/code attribute: " + allergyObsContext;
		ParserUtilities.compareCode(allergySubstance, subObs.getAllergySubstance(), results, elementNameVal);
		
		// Compare Allergy Codes 
		String elementNameType = "Allergy Observation Type element : " + allergyObsContext;
		//ParserUtilities.compareCode(allergyIntoleranceType, subObs.getAllergyIntoleranceType(), results, elementNameType);
		
		// Add negation indicator
		
		// Reaction comparison.
		for( int i = 0; i < reactions.size(); i++) {
			
			CCDAAllergyReaction reactionFound = subObs.getReaction(reactions.get(i), allergyObsContext, results);
			if(reactionFound == null) {
				String error = "The scenario contains Allergy Reaction for " + allergyObsContext +
						" , however there is no matching Allergy Reaction (Entry Template Ids + Code) in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			else {
				
				log.info("Reaction was found, let us check Severity now ");
				
				// Check Severity since the reaction was found.
				reactionFound.validateSeverity(reactions.get(i).getSeverity(),allergyObsContext, results);
			}
		}
	}
	
	public CCDAAllergyReaction getReaction(CCDAAllergyReaction refReact,String allergyObsContext, ArrayList<ContentValidationResult> results) {
		
		// run thru the reactions in the submitted model and verify if the Ref Reaction is present.
		for (int i = 0; i < reactions.size(); i++) {
			
			if(reactions.get(i).isSameAs(refReact))
				return reactions.get(i);
		}
		
		// No reaction found
		return null;
	}
	
	public void log() {
		
		log.info("***Allergy Obs***");
		
		if(allergyIntoleranceType != null)
			log.info("Allergy Intolerance Type Code = " + allergyIntoleranceType.getCode());
		
		if(allergySubstance != null)
			log.info("Allergy Substance Code = " + allergySubstance.getCode());
	
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		if(effTime != null) 
			effTime.log();
		
		for(int k = 0; k < reactions.size(); k++) {
			reactions.get(k).log();
		}
		
	}
	
	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}


	public void setTemplateId(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateId = ids;
	}


	public CCDACode getAllergyIntoleranceType() {
		return allergyIntoleranceType;
	}


	public void setAllergyIntoleranceType(CCDACode allergyIntoleranceType) {
		this.allergyIntoleranceType = allergyIntoleranceType;
	}


	public CCDACode getAllergySubstance() {
		return allergySubstance;
	}


	public void setAllergySubstance(CCDACode allergySubstance) {
		this.allergySubstance = allergySubstance;
	}


	public CCDAEffTime getEffTime() {
		return effTime;
	}


	public void setEffTime(CCDAEffTime effTime) {
		this.effTime = effTime;
	}


	public ArrayList<CCDAAllergyReaction> getReactions() {
		return reactions;
	}


	public void setReactions(ArrayList<CCDAAllergyReaction> rs) {
		
		if(rs != null)
			this.reactions = rs;
	}


	public CCDAAllergyObs()
	{
		templateId = new ArrayList<CCDAII>();
		reactions = new ArrayList<CCDAAllergyReaction>();
	}
	
}
