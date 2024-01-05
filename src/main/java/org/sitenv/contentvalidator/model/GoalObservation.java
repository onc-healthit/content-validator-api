package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoalObservation {

private static Logger log = LoggerFactory.getLogger(GoalObservation.class.getName());
	
	private ArrayList<CCDAII>   			templateIds;
	private CCDACode						goalCode;
	private CCDACode						statusCode;
	private CCDAEffTime						effectiveTime;
	private CCDAPQ							results;
	private CCDACode						resultCode;
	private String 							resultString;
	
	private CCDAAuthor	author;
	
	public GoalObservation() {
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** Goal Observation ***");
		
		if(goalCode != null)
			log.info(" Goal  Code = " + goalCode.getCode());
		
		if(statusCode != null)
			log.info(" Lab Result Status  Code = " + statusCode.getCode());
		
		if(effectiveTime != null)
			effectiveTime.log();
		
		if(results != null)
			results.log();
		
		if(resultCode != null)
			log.info(" Goal Obs Code = " + resultCode.getCode());
		
		if(resultString != null)
			log.info(" Goal Obs String = " + resultString);
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
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

	public CCDACode getGoalCode() {
		return goalCode;
	}

	public void setGoalCode(CCDACode goalCode) {
		this.goalCode = goalCode;
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

	public CCDAPQ getResults() {
		return results;
	}

	public void setResults(CCDAPQ results) {
		this.results = results;
	}

	public CCDACode getResultCode() {
		return resultCode;
	}

	public void setResultCode(CCDACode resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public static void compare(HashMap<String, GoalObservation> refGoals, HashMap<String, GoalObservation> subGoals,
			ArrayList<ContentValidationResult> results2) {
	
		// Check only the reference ones
		for(Map.Entry<String,GoalObservation> entry: refGoals.entrySet()) {
			
			if(subGoals.containsKey(entry.getKey())) {
				
				// Since the observation was found, compare other data elements.
				log.info(" Comparing Goal Observation");
				String compContext = " Comparing Goal Observation data for code " + entry.getKey();
				entry.getValue().compare(subGoals.get(entry.getKey()), compContext, results2);
				
			}
			else {
				
				String error = "The scenario contains Goal Observation data with code " + entry.getKey() + 
						" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results2.add(rs);
			}
			
		}
		
	}

	private void compare(GoalObservation subGoal, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		String elementName = compContext + " , Template Id Comparison : ";

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateIds, subGoal.getTemplateIds(), results, elementName);

		// Compare Assessment  Codes 
		String elementNameCode = compContext + " , Goal Code Element Comparison : ";
		ParserUtilities.compareCode(goalCode, subGoal.getGoalCode(), results, elementNameCode);
		 
		// Compare Assessment  Codes 
		String elementStatus = compContext + " , Goal Status Element Comparison : ";
	//	ParserUtilities.compareCode(statusCode, subGoal.getStatusCode(), results, elementStatus);
				 	 
		// Compare Assessment Value 
		String elementVal = compContext + " , Goal Result Value Comparison : ";
		ParserUtilities.compareString(resultString, subGoal.getResultString(), results, elementVal);
		
		// Compare Assessment  Codes 
		String resultCodeElement = compContext + " , Goal Result Code Element Comparison : ";
		ParserUtilities.compareCode(resultCode, subGoal.getResultCode(), results, resultCodeElement);
		
	}
	
	
}
