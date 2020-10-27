package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

public class CCDAAuthor {
	
	private static Logger log = Logger.getLogger(CCDAAuthor.class.getName());
	
	private ArrayList<CCDAII>    			templateId;
	private CCDAEffTime          			effTime;
	private ArrayList<CCDAII>    			authorIds;
	private CCDADataElement					authorFirstName;
	private CCDADataElement					authorLastName;
	private CCDADataElement					authorName;
	private ArrayList<CCDAII>				repOrgIds;
	private ArrayList<CCDATelecom>			telecoms;
	private CCDADataElement					orgName;
	
	public CCDAAuthor() {
		
		templateId = new ArrayList<CCDAII>();
		authorIds = new ArrayList<CCDAII>();
		repOrgIds = new ArrayList<CCDAII>();
		telecoms = new ArrayList<CCDATelecom>();
		
	}
	
	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}
	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}
	public CCDAEffTime getEffTime() {
		return effTime;
	}
	public void setEffTime(CCDAEffTime effTime) {
		this.effTime = effTime;
	}
	public ArrayList<CCDAII> getAuthorIds() {
		return authorIds;
	}
	public void setAuthorIds(ArrayList<CCDAII> authorIds) {
		this.authorIds = authorIds;
	}
	public ArrayList<CCDAII> getRepOrgIds() {
		return repOrgIds;
	}
	public void setRepOrgIds(ArrayList<CCDAII> repOrgIds) {
		this.repOrgIds = repOrgIds;
	}
	public ArrayList<CCDATelecom> getTelecoms() {
		return telecoms;
	}
	public void setTelecoms(ArrayList<CCDATelecom> telecoms) {
		this.telecoms = telecoms;
	}
	public CCDADataElement getOrgName() {
		return orgName;
	}
	public void setOrgName(CCDADataElement orgName) {
		this.orgName = orgName;
	}

	public CCDADataElement getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(CCDADataElement authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	public CCDADataElement getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(CCDADataElement authorLastName) {
		this.authorLastName = authorLastName;
	}

	public CCDADataElement getAuthorName() {
		return authorName;
	}

	public void setAuthorName(CCDADataElement authorName) {
		this.authorName = authorName;
	}
	
	public void log() {
		
		log.info("***Author Entry ***");
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		
		for(int k = 0; k < authorIds.size(); k++) {
			log.info(" Author Id [" + k + "] = " + templateId.get(k).getRootValue());
			log.info(" Author Id Ext [" + k + "] = " + templateId.get(k).getExtValue());
		}
		
		for(int l = 0; l < repOrgIds.size(); l++) {
			log.info(" Rep Org Id [" + l + "] = " + templateId.get(l).getRootValue());
			log.info(" Rep Org Id Ext [" + l + "] = " + templateId.get(l).getExtValue());
		}
		
		
		if(effTime != null) 
			effTime.log();
		
		if(authorFirstName != null) {
			log.info(" Author First Name = " + authorFirstName.getValue());
		}
		
		if(authorLastName != null) {
			log.info(" Author Last Name = " + authorLastName.getValue());
		}
		
		if(authorName != null) {
			log.info(" Author  Name = " + authorName.getValue());
		}
		
		if(orgName != null) {
			log.info(" Rep Org  Name = " + orgName.getValue());
		}
		
	}	
	
	public void matches(CCDAAuthor subAuthor, ArrayList<ContentValidationResult> results, String elName) {
		
		log.info(" Comparing data for Author Template Ids: ");

		// Compare template Ids 
		String elementName = "Comapring Author Template Ids for : " + elName;		 // Not mandatory so skipping
		// ParserUtilities.compareTemplateIds(templateId, subAuthor.getTemplateId(), results, elementName);
		
		// Compare Author Ids 
		elementName = "Comapring Author Ids for : " + elName; // Not mandatory so skipping
		// ParserUtilities.compareTemplateIds(authorIds, subAuthor.getAuthorIds(), results, elementName);
		
		// Compare REp Ord Ids 		
		elementName = "Comapring Rep Org Ids for : " + elName; // Not mandatory so skipping
		// ParserUtilities.compareTemplateIds(repOrgIds, subAuthor.getRepOrgIds(), results, elementName);

		// Compare Effective Times
		elementName = "Comparing Author Time for " + elName; 
		ParserUtilities.compareEffectiveTimeValueWithFullPrecision(effTime, subAuthor.getEffTime(), results, elementName);
		
		// Compare Org Name 
		elementName = "Comparing Author Organization Name for " + elName;
		ParserUtilities.compareDataElementText(orgName, subAuthor.getOrgName(), results, elementName);
	}
	
    public static void compareAuthors(ArrayList<CCDAAuthor> refAuths, ArrayList<CCDAAuthor> subAuths, 
    		ArrayList<ContentValidationResult> results, String elName) {
		
		log.info(" Comparing data for Author.");
		log.info(" Ref Model Auth Size = " + (refAuths !=null ? refAuths.size():0));
		log.info(" Sub Model Auth Size = " + (subAuths !=null ? subAuths.size():0));
		
		for(CCDAAuthor auth : refAuths) {
			
			log.info("Checking Ref Author with Sub Authors ");
			if(auth.getEffTime() != null && 
					auth.getEffTime().getValuePresent() && !isProvenancePresent(auth.getEffTime(), auth.getOrgName(), subAuths)) {
				
				String orgName = "";
				if(auth.getOrgName() != null && auth.getOrgName().getValue() != null)
					orgName = auth.getOrgName().getValue();
				
				ContentValidationResult rs = new ContentValidationResult("The scenario requires Provenance data of Time  : " + auth.getEffTime().getValue().getValue() + " and an Organization Name of : " + 
				  orgName + " at the " + elName + ", which was not found in the submitted data. ", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			else {
				log.info(" Found Provenance data, nothing else to do ..");
			}
		}
		
		// Compare Author Sizes
		if(refAuths != null && subAuths != null && 
				!(refAuths.size() == subAuths.size())) {
			
			ContentValidationResult rs = new ContentValidationResult("The scenario requires a total of " + refAuths.size() + " Author Entries for " + elName
					+ ", however the submitted data had only " + subAuths.size() + " entries.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
					results.add(rs);
		}
		
	}
    
    public static boolean isProvenancePresent(CCDAEffTime effTime, CCDADataElement name, ArrayList<CCDAAuthor> subAuths) {
    	
    	boolean retVal = false;
    	String elName = "Comparing Author Provenance Data";
    	ArrayList<ContentValidationResult> res = new ArrayList<ContentValidationResult>();
    	
    	for(CCDAAuthor auth : subAuths) {
    		   		
    		ParserUtilities.compareEffectiveTimeValueWithFullPrecision(effTime, auth.getEffTime(), res, elName);
    		
    		ParserUtilities.compareDataElementText(name, auth.getOrgName(), res, elName);
    		
    		if(res != null && res.size() == 0 ) {
    			
    			log.info(" Matched Provenance Data ");
    			retVal = true;
    			break;
    		}
    		else {
    			res.clear();
    		}
    	}
    	
    	return retVal;
    }
    
    public static boolean isProvenancePresent(CCDAEffTime effTime, CCDADataElement name, CCDAAuthor subAuth) {
    	
    	boolean retVal = false;
    	String elName = "Comparing Author Provenance Data";
    	ArrayList<ContentValidationResult> res = new ArrayList<ContentValidationResult>();   	
    	
    	ParserUtilities.compareEffectiveTimeValueWithFullPrecision(effTime, subAuth.getEffTime(), res, elName);
    		
    	ParserUtilities.compareDataElementText(name, subAuth.getOrgName(), res, elName);
    		
    	if(res != null && res.size() == 0 ) {
    			
    		log.info(" Matched Provenance Data ");
    		retVal = true;
    			
    	}
    	else {
    		log.info(" Provenance data did not match ");
    		retVal = false;
    	}
    	
    	return retVal;
    }

}
