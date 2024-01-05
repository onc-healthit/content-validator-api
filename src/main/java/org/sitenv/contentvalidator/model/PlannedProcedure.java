package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlannedProcedure {

private static Logger log = LoggerFactory.getLogger(PlannedProcedure.class.getName());
	
	private ArrayList<CCDAII>   			templateIds;
	private CCDACode						procedureCode;
	private CCDACode						statusCode;
	private CCDAEffTime						procedureTime;
	
	CCDAAuthor	author;
	
	public PlannedProcedure() {
		
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		if(procedureCode != null)
			log.info(" Procedure  Code = " + procedureCode.getCode());
		
		if(statusCode != null)
			log.info(" Lab Result Status  Code = " + statusCode.getCode());
		
		if(procedureTime != null)
			procedureTime.log();
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getProcedureCode() {
		return procedureCode;
	}

	public void setProcedureCode(CCDACode procedureCode) {
		this.procedureCode = procedureCode;
	}

	public CCDACode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CCDACode statusCode) {
		this.statusCode = statusCode;
	}

	public CCDAEffTime getProcedureTime() {
		return procedureTime;
	}

	public void setProcedureTime(CCDAEffTime procedureTime) {
		this.procedureTime = procedureTime;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public static void compare(HashMap<String, PlannedProcedure> refProcs, HashMap<String, PlannedProcedure> subProcs,
			ArrayList<ContentValidationResult> results) {
		
		// Check only the reference ones
		for(Map.Entry<String,PlannedProcedure> entry: refProcs.entrySet()) {
			
			if(subProcs.containsKey(entry.getKey())) {
				
				// Since the observation was found, compare other data elements.
				log.info(" Comparing Planned Procedure ");
				String compContext = " Comparing Planned Procedure data for code " + entry.getKey();
				entry.getValue().compare(subProcs.get(entry.getKey()), compContext, results);
				
			}
			else {
				
				String error = "The scenario contains Planned Procedure data with code " + entry.getKey() + 
						" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
		}
		
	}
	
	private void compare(PlannedProcedure subProc, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		String elementName = compContext + " , Template Id Comparison : ";

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateIds, subProc.getTemplateIds(), results, elementName);

		// Compare Assessment  Codes 
		String elementNameCode = compContext + " , Planned Procedure Code Element Comparison : ";
		ParserUtilities.compareCode(procedureCode, subProc.getProcedureCode(), results, elementNameCode);
		 
		// Compare Assessment  Codes 
		String elementStatus = compContext + " , Planned Procedure Element Comparison : ";
	//	ParserUtilities.compareCode(statusCode, subProc.getStatusCode(), results, elementStatus);
				 	 
		// Compare Assessment  Codes 
		String elementTime = compContext + " , Planned Procedure Comparison : ";
		ParserUtilities.compareEffectiveTime(procedureTime, subProc.getProcedureTime(), results, elementTime);
		
	}
	
	
}
