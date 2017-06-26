package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;

public class CCDAAllergyReaction {
	
	private static Logger log = Logger.getLogger(CCDAAllergyReaction.class.getName());
	
	private ArrayList<CCDAII>			templateIds;
	private CCDACode					reactionCode;
	private CCDAAllergySeverity			severity;
	
	public Boolean isSameAs(CCDAAllergyReaction refReact) {
		
		log.info("Comparing Reactions ");
		if(codeEquals(reactionCode, refReact.getReactionCode()) && 
		   templateIdsAreFound(templateIds, refReact.getTemplateIds()) )
			return true;
		else
			return false;
	}
	
	public Boolean codeEquals(CCDACode subCode, CCDACode refCode) {
		
		// handle reaction code.
		if(subCode != null && 
		   refCode != null && 
		   refCode.codeEquals(subCode))
			return true;
		else
			return false;
	}
	
	public Boolean templateIdsAreFound(ArrayList<CCDAII> subIds, ArrayList<CCDAII> refIds) {
		
		return ParserUtilities.templateIdsAreFound(refIds,  subIds);
	}
	
	public void validateSeverity(CCDAAllergySeverity refSeverity,String allergyObsContext, ArrayList<ContentValidationResult> results) {
		
		log.info("Comparing Severity ");
		
		if( refSeverity != null &&
		    severity != null)
		{
		    if(severity.getSeverity() != null && 
		       refSeverity.getSeverity() != null &&
		       severity.getSeverity().codeEquals(refSeverity.getSeverity()) && 
		       templateIdsAreFound(severity.getTemplateIds(), refSeverity.getTemplateIds()))
		    {
		    	// Do nothing.
		    }
		    else {
		    	String error = "The scenario contains Allergy Reaction and Severity for " + allergyObsContext +
				" , however there is no matching Allergy Severity within the Reaction entry's entryRelationship in the submitted CCDA. ";
		    	ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
		    	results.add(rs);
		    }
		}
		else if(refSeverity != null && severity == null) {
			String error = "The scenario contains Allergy Reaction and Severity for " + allergyObsContext +
					" , however there is no matching Allergy Severity within the Reaction entry's entryRelationship in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if(refSeverity == null && severity != null) {
			String error = "The scenario does not contain Severity within the Allergy Reaction " + allergyObsContext +
					" , however there is an Allergy Severity within the Reaction entry's entryRelationship in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			// Do nothing
		}
			
	}
	
	public void log() {
		
		log.info("***Allergy Reaction ***");
		
		if(reactionCode != null)
			log.info("Allergy Reaction Code = " + reactionCode.getCode());
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}	
		
		if(severity != null)
			severity.log();
	}
	
	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateIds = ids;
	}

	public CCDACode getReactionCode() {
		return reactionCode;
	}

	public void setReactionCode(CCDACode reactionCode) {
		this.reactionCode = reactionCode;
	}
	
	public void setSeverity(CCDAAllergySeverity s) {
		this.severity = s;
	}
	
	public CCDAAllergySeverity getSeverity() {
		return severity;
	}

	public CCDAAllergyReaction()
	{
		templateIds = new ArrayList<CCDAII>();
	}

}
