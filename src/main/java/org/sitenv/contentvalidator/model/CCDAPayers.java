package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class CCDAPayers {

	private static Logger log = LoggerFactory.getLogger(CCDAPayers.class.getName());

	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		sectionCode;
	private ArrayList<CCDACoverageActivity>	coverageActivities;
	
	private CCDAAuthor 						author;
	
	public CCDAPayers() {
		sectionTemplateId = new ArrayList<>();
		coverageActivities = new ArrayList<>();
	}
	
	public ArrayList<String> getCoverageStatus() {
		
		ArrayList<String> coverageStatus = new ArrayList<>();
		if(coverageActivities != null) {
			
			for(CCDACoverageActivity coverageActivity: coverageActivities) {
				
				if(coverageActivity.getCoverageCode() != null && 
						StringUtils.isEmpty(coverageActivity.getCoverageCode().getCode())) {
					coverageStatus.add(coverageActivity.getCoverageCode().getCode());
				}		
			}
		}
		return coverageStatus;
	}
	
	public void log() {
		
		log.info(" ***** Payers Section *****");
		
		if(sectionCode != null)
			log.info(" Payers Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < sectionTemplateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateId.get(j).getExtValue());
		}
		
		for(int k = 0; k < coverageActivities.size(); k++) {
			coverageActivities.get(k).log();
		}
		
		if(author != null)
			author.log();
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

	public ArrayList<CCDACoverageActivity> getCoverageActivities() {
		return coverageActivities;
	}

	public void setCoverageActivities(ArrayList<CCDACoverageActivity> coverageActivities) {
		this.coverageActivities = coverageActivities;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public void compare(CCDAPayers submittedPayer, ArrayList<ContentValidationResult> results, boolean svap2022,
			boolean svap2023) {
		
		// handle section code.
		ParserUtilities.compareCode(sectionCode, submittedPayer.getSectionCode(), results, "Payers Section");
				
		// Hanlde Section Template Ids
		ParserUtilities.compareTemplateIds(sectionTemplateId, submittedPayer.getSectionTemplateId(), results, "Payers Section");
				
		// Compare details
		comparePayerData(submittedPayer, results, svap2022, svap2023);
		
	}

	private void comparePayerData(CCDAPayers submittedPayer, ArrayList<ContentValidationResult> results,
			boolean svap2022, boolean svap2023) {
		
		// Get Coverage Status for Submitted 
		ArrayList<String> subCodes = submittedPayer.getCoverageStatus();
		
		ArrayList<String> refCodes = this.getCoverageStatus();
		
		// Compare Coverage Status
		compareCoverageStatus(refCodes, subCodes, results, svap2022, svap2023);
		
		HashMap<String, CCDAPolicyActivity> subPolicies = submittedPayer.getPolicyActivities();
		
		HashMap<String, CCDAPolicyActivity> refPolicies = this.getPolicyActivities();
		
		comparePolicyActivities(refPolicies, subPolicies, results, svap2022, svap2023);
	}
	
	
	
	private void comparePolicyActivities(HashMap<String, CCDAPolicyActivity> refPolicies,
			HashMap<String, CCDAPolicyActivity> subPolicies, ArrayList<ContentValidationResult> results,
			boolean svap2022, boolean svap2023) {
	
		if(refPolicies != null && subPolicies != null && 
				subPolicies.size() >= refPolicies.size() ) {
			
			for(Map.Entry<String,CCDAPolicyActivity> ent : refPolicies.entrySet()) {
				
				ent.getValue().compare(ent.getKey(), subPolicies, results, svap2022, svap2023);
				
			}
			
		}
		else if(refPolicies != null && subPolicies != null &&
				(subPolicies.size() < refPolicies.size())) {
			
			// handle the case where the coverage data does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's coverage policies, but the submitted C-CDA does not contain the right number of Policy Activity entry data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);		
		}
		else if(refPolicies != null && subPolicies == null) {
			
			// handle the case where the coverage data does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's coverage policies, but the submitted C-CDA does not contain the right number of Policy Activity entry data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			//Ignore the comparison
		}
		
		
	}

	private HashMap<String, CCDAPolicyActivity> getPolicyActivities() {
		
		HashMap<String, CCDAPolicyActivity> policyActivities = new HashMap<>();
		if(coverageActivities != null) {
			
			for(CCDACoverageActivity coverage: coverageActivities) {
				
				coverage.getPolicyActivities(policyActivities);				
			}
			
		}
		
		return policyActivities;
	}

	private void compareCoverageStatus(
			ArrayList<String> refCodes, 
			ArrayList<String> subCodes,
			ArrayList<ContentValidationResult> results,
			boolean svap2022, boolean svap2023) {
		
		if(refCodes != null && subCodes != null && 
				subCodes.size() >= refCodes.size() ) {
			
			for(String refCode : refCodes) {
				
				if(!subCodes.contains(refCode)) {
					
					String message = "The scenario requires data related to patient's coverage with a Coverage Status of " + refCode + " , however the submitted C-CDA does not contain a Coverage Activity with the code.";
					// handle the case where the coverage data does not exist in the submitted CCDA
					ContentValidationResult rs = new ContentValidationResult(message, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
					results.add(rs);
				}
			}	
		}
		else if(refCodes != null && subCodes != null &&
				(subCodes.size() < refCodes.size())) {
			
			// handle the case where the coverage data does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's coverages, but the submitted C-CDA does not contain the right number of Coverage entries data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);		
		}
		else if(refCodes != null && subCodes == null) {
			
			// handle the case where the coverage data does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's coverages, but the submitted C-CDA does not contain the right number of Coverage entries data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			//Ignore the comparison
		}
				
		
	}
	
	
	
	
}
