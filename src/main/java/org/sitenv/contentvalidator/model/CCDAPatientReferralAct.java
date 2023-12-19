package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAPatientReferralAct {
	
	private static Logger log = LoggerFactory.getLogger(CCDAPatientReferralAct.class.getName());
	
	private ArrayList<CCDAII>     				templateIds;
	private CCDACode							referralCode;
	private CCDACode							statusCode;
	private CCDAEffTime							effectiveTime;
	private ArrayList<CCDAIndication>			indications;
	CCDAAuthor 									author;
	
	public CCDAPatientReferralAct() {		
		templateIds = new ArrayList<>();
		indications = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** Patient Referral Act *** ");
		
		if(referralCode != null)
			log.info(" Patient Referral Act Code = " + referralCode.getCode());
		
		if(statusCode != null)
			log.info(" Patient Referral Act Status Code = " + statusCode.getCode());
		
		if(effectiveTime != null)
			effectiveTime.log();
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		for(int k = 0; k < indications.size(); k++) {
			indications.get(k).log();
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

	public CCDACode getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(CCDACode referralCode) {
		this.referralCode = referralCode;
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

	public ArrayList<CCDAIndication> getIndications() {
		return indications;
	}

	public void setIndications(ArrayList<CCDAIndication> indications) {
		this.indications = indications;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public static void compare(HashMap<String, CCDAPatientReferralAct> refModelActs,
			HashMap<String, CCDAPatientReferralAct> subModelActs, ArrayList<ContentValidationResult> results) {
		
		String context = " Comparing Referral Data ";
		// Check only the reference ones
		for(Map.Entry<String,CCDAPatientReferralAct> entry: refModelActs.entrySet()) {
			
			if(subModelActs.containsKey(entry.getKey())) {
				
				// Since the act was found, compare other data elements.
				log.info(" Comparing Referral Data");
				String compContext = " Comparing Referral data for code " + entry.getKey();
				entry.getValue().compare(subModelActs.get(entry.getKey()), compContext, results);
				
			}
			else {
				
				String error = "The scenario contains Referral data with code " + entry.getKey() + 
						" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
		}
		
	}
	
	private void compare(CCDAPatientReferralAct subReferral, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		String elementName = compContext + " , Template Id Comparison : ";

		// Compare template Ids 
		ParserUtilities.compareTemplateIds(templateIds, subReferral.getTemplateIds(), results, elementName);

		// Compare Assessment  Codes 
		String elementNameCode = compContext + " , Referral Code Element Comparison : ";
		ParserUtilities.compareCode(referralCode, subReferral.getReferralCode(), results, elementNameCode);
		 		 	 
		// Compare Assessment  Codes 
		String elementTime = compContext + " , Referral Time Comparison : ";
		ParserUtilities.compareEffectiveTime(effectiveTime, subReferral.getEffectiveTime(), results, elementTime);
		
	}
}
