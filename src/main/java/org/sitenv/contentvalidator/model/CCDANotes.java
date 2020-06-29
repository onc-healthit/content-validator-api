package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

public class CCDANotes {
	
	private static Logger log = Logger.getLogger(CCDANotes.class.getName());
	
	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		sectionCode;
	private ArrayList<CCDANotesActivity>		notesActivity;
	
	public CCDANotes() { 
		
		sectionTemplateId = new ArrayList<CCDAII>();
		notesActivity = new ArrayList<CCDANotesActivity>();
	}
	
	public ArrayList<CCDAII> getSectionTemplateId() {
		return sectionTemplateId;
	}
	public void setSectionTemplateId(ArrayList<CCDAII> sectionTemplateId) {
		this.sectionTemplateId = sectionTemplateId;
	}
	public CCDACode getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}
	public ArrayList<CCDANotesActivity> getNotesActivity() {
		return notesActivity;
	}
	public void setNotesActivity(ArrayList<CCDANotesActivity> notesActivity) {
		this.notesActivity = notesActivity;
	}
	
	public void log() {
		
		log.info(" *** Notes Section *** ");
		
		if(sectionCode != null)
			log.info(" Notes Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < sectionTemplateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateId.get(j).getExtValue());
		}
		
		for(int k = 0; k < notesActivity.size(); k++) {
			notesActivity.get(k).log();
		}
	}	
	
	public static void compareNotes(HashMap<String, CCDANotes> refNotes, 
			HashMap<String, CCDANotes> subNotes, 	ArrayList<ContentValidationResult> results) {

		log.info(" Start Comparing Notes Section level data ");
		
		// For each Notes section in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDANotes> ent: refNotes.entrySet()) {

			if(subNotes.containsKey(ent.getKey())) {

				log.info("Comparing Notes Section Observation ");
				String context = "Comparing Notes Section corresponding to the code " + ent.getKey();
				subNotes.get(ent.getKey()).compare(ent.getValue(), results, context);


			} else {
				// Error
				String error = "The scenario contains Notes data with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the Notes data is not present in the reference, 
		if( (refNotes == null || refNotes.size() == 0) && (subNotes != null && subNotes.size() > 0) ) {

			// Error
			String error = "The scenario does not require Notes data " + 
					" , however there is Notes data in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}
	
	public void compare(CCDANotes refNote, ArrayList<ContentValidationResult> results , String context) {
		
		log.info("Comparing Notes ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refNote.getSectionTemplateId(), sectionTemplateId, results, context);
		
		// Compare Effective Times
		String elementNameTime = "Effective Time for " + context;
		//ParserUtilities.compareEffectiveTime(refResult.getMeasurementTime(), measurementTime, results, elementNameTime);
		
		// Compare Lab Codes 
		String elementNameVal = "Comparing Notes Section code element for " + context;
		ParserUtilities.compareCode(refNote.getSectionCode(), sectionCode, results, elementNameVal);
		
		context += ", Comapring Notes Activity ";
		CCDANotesActivity.compareNotesActivity(refNote.getNotesActivity(), this.getNotesActivity(), results, context);
	}


}
