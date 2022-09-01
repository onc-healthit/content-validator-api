package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

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
	
	
}
