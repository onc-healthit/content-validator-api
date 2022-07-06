package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

public class CCDAAuthor {
	
	private static Logger log = LoggerFactory.getLogger(CCDAAuthor.class.getName());
	
	private ArrayList<CCDAII>    			templateIds;
	private CCDAEffTime          			effTime;
	private ArrayList<CCDAII>    			authorIds;
	private CCDADataElement					authorFirstName;
	private CCDADataElement					authorLastName;
	private CCDADataElement					authorName;
	private ArrayList<CCDAII>				repOrgIds;
	private ArrayList<CCDATelecom>			telecoms;
	private CCDADataElement					orgName;
	
	public CCDAAuthor() {
		templateIds = new ArrayList<CCDAII>();
		authorIds = new ArrayList<CCDAII>();
		repOrgIds = new ArrayList<CCDAII>();
		telecoms = new ArrayList<CCDATelecom>();
	}
	
	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}
	public void setTemplateIds(ArrayList<CCDAII> templateId) {
		this.templateIds = templateId;
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
		
		if (templateIds != null) { 
			for(int i = 0; i < templateIds.size(); i++) {
				log.info(" Tempalte Id [" + i + "] = " + templateIds.get(i).getRootValue());
				log.info(" Tempalte Id Ext [" + i + "] = " + templateIds.get(i).getExtValue());
			}
		}
		
		if (authorIds != null) {		
			for(int i = 0; i < authorIds.size(); i++) {
				log.info(" Author Id [" + i + "] = " + authorIds.get(i).getRootValue());
				log.info(" Author Id Ext [" + i + "] = " + authorIds.get(i).getExtValue());
			}
		}
		
		if (repOrgIds != null) {
			for(int i = 0; i < repOrgIds.size(); i++) {
				log.info(" Rep Org Id [" + i + "] = " + repOrgIds.get(i).getRootValue());
				log.info(" Rep Org Id Ext [" + i + "] = " + repOrgIds.get(i).getExtValue());
			}
		}
		
		if (telecoms != null ) {
			for(int i = 0; i < telecoms.size(); i++) {
				log.info(" Telecom use [" + i + "] = " + telecoms.get(i).getUseAttribute());
				log.info(" Telecom value [" + i + "] = " + telecoms.get(i).getValueAttribute());
			}
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
			log.info(" Author Name = " + authorName.getValue());
		}
		
		if(orgName != null) {
			log.info(" Rep Org Name = " + orgName.getValue());
		}
	}
	
	public void matches(CCDAAuthor subAuthor, ArrayList<ContentValidationResult> results, String elName) {
		matches(subAuthor, results, elName, null);
	}
	
	public void matches(CCDAAuthor subAuthor, ArrayList<ContentValidationResult> results, String elName,
			ArrayList<CCDAAuthor> submittedAuthorsWithLinkedReferenceData) {
		log.info(" Comparing data for Author Template Ids: ");

		String elementName = "";
		
		// Not mandatory so skipping
		// Compare template Ids 
		// elementName = "Comapring Author Template Ids for : " + elName;
		// ParserUtilities.compareTemplateIds(templateId, subAuthor.getTemplateId(), results, elementName);
		
		// Not mandatory so skipping
		// Compare Author Ids 
		// elementName = "Comapring Author Ids for : " + elName;
		// ParserUtilities.compareTemplateIds(authorIds, subAuthor.getAuthorIds(), results, elementName);
		
		// Not mandatory so skipping
		// Compare Rep Org Ids 		
		// elementName = "Comapring Rep Org Ids for : " + elName;
		// ParserUtilities.compareTemplateIds(repOrgIds, subAuthor.getRepOrgIds(), results, elementName);

		// Compare Effective Times
		elementName = "Comparing Author Time for " + elName; 
		ParserUtilities.compareEffectiveTimeValue(effTime, subAuthor.getEffTime(), results,
				elementName);
		
		// Validate Times
		ParserUtilities.validateTimeValueLengthDateTimeAndTimezoneDependingOnPrecision(subAuthor.getEffTime(), results,
				elementName, 
				(elName != null && !elName.isEmpty()) ? elName.replaceFirst(" , Comparing ", "") : elName,
				-1, true);
		
		// Compare RepOrg Name 
		elementName = "Author Represented Organization Name for " + elName;					
		if (submittedAuthorsWithLinkedReferenceData != null) {
			ParserUtilities.compareProvenanceOrgName(orgName, subAuthor, results, elementName,
					submittedAuthorsWithLinkedReferenceData);
		} else {
			ParserUtilities.compareDataElementText(orgName, subAuthor.getOrgName(), results, elementName);
		}
	}
	
    public static void compareAuthors(ArrayList<CCDAAuthor> refAuths, ArrayList<CCDAAuthor> subAuths, 
    		ArrayList<ContentValidationResult> results, String elName, ArrayList<CCDAAuthor> authorsWithLinkedReferenceData) {
		log.info(" Comparing data for Author.");
		log.info(" Ref Model Auth Size = " + (refAuths != null ? refAuths.size() : 0));
		log.info(" Sub Model Auth Size = " + (subAuths != null ? subAuths.size() : 0));

		// TODO: Can remove this log after code is proven in production
		// (already logged once which we can reference by CCDARefModel.logSubmittedAuthorsWithLinkedReferenceData 
		// as opposed to every comparison encountered here
		if (authorsWithLinkedReferenceData != null) {
			for ( CCDAAuthor auth : authorsWithLinkedReferenceData) {
				log.info(" authorsWithLinkedReferenceData: " );
				auth.log();
			}
		}
    	
    	if (refAuths != null && refAuths.size() != 0) { // If no authors in scenario (ref) file, skip the comparison 			
			for (CCDAAuthor curRefAuth : refAuths) {
				
				log.info("Checking Ref Author with Sub Authors ");
				if(curRefAuth.getEffTime() != null && 
						curRefAuth.getEffTime().getValuePresent()
						&& !isProvenancePresent(curRefAuth.getEffTime(), curRefAuth.getOrgName(), subAuths)) {
					
					// Now that we know there is a failure inline, check the linked references. 
					// The reason we wait is for performance, that way we only check the limited cases vs every case regardless beforehand
					if (!isProvenancePresentInReferencesWithData(curRefAuth.getEffTime(), curRefAuth.getOrgName(),
							subAuths, authorsWithLinkedReferenceData)) {
					// Note: This is the only result that is actually reported.
					// Many errors are generated in isProvenancePresent sub-routines but there's no way to connect them
					// to a specific sub which actually had the issue (since match can be in any location) so instead the results
					// generated externally are used as a reference for a boolean result which triggers this error 
					// vs adding the unique errors themselves
//					String message = "The scenario requires " + elName
//							+ " (Time: Value) Provenance data which was not found in the submitted data. The scenario value is "
//							+ auth.getEffTime().getValue().getValue()
//							+ " and a submitted value must at a minimum match the 8-digit date portion of the data.";
						final boolean isOrgNameNonNullAndPopulated = curRefAuth.getOrgName() != null
								&& curRefAuth.getOrgName().getValue() != null
								&& !curRefAuth.getOrgName().getValue().isEmpty();
						String message = "The scenario requires " + elName
								+ " Provenance data of time" + (isOrgNameNonNullAndPopulated ? " and/or representedOrganization/name" : "") 
								+ " which was not found in the submitted data. "
								+ "The scenario time value is " + curRefAuth.getEffTime().getValue().getValue()
								+ " and a submitted time value should at a minimum match the 8-digit date portion of the data."
								+ (isOrgNameNonNullAndPopulated ? " The scenario representedOrganization/name value is " + curRefAuth.getOrgName().getValue()
								+ " and a submitted name should match. One or all of the prior issues exist and must be resolved." : "");																						
							
						ContentValidationResult rs = new ContentValidationResult(message,
								ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
						results.add(rs);
					} else {
						log.info(
								" Found Provenance data in submitted authorsWithLinkedReferenceData, nothing else to do ..");
					}
					
				} else {
					log.info(" Found Provenance data, nothing else to do ..");
				}
			}
			
			// Validate time value in sub author time value instances specifically (not a comparison)
			// Results are added as individual errors
			if (subAuths != null && subAuths.size() > 0) {
				log.info("starting subAuth validation routine");
				final String localElName = "Author Provenance";
				final boolean isSub = true;
				int subAuthIndex = -1;
				for (CCDAAuthor subAuth : subAuths) {
					subAuthIndex++;
					if (subAuth.getEffTime() != null) {
						log.info("validating subauth at index " + subAuthIndex);
						ParserUtilities.validateTimeValueLengthDateTimeAndTimezoneDependingOnPrecision(
								subAuth.getEffTime(), results, localElName, elName, subAuthIndex, isSub);
					} else {
						log.info("subAuth at index " + subAuthIndex + " is null" );
					}
				}
			} else {
				log.info("subAuths is null or empty");
			}
			
			// Compare Author Sizes			
			// It's invalid to fire an error if ref is less than or equal to sub auth size
			if (refAuths != null && subAuths != null && 
					!(refAuths.size() <= subAuths.size())) {
				ContentValidationResult rs = new ContentValidationResult(
						"The scenario requires a total of " + refAuths.size() + " Author Entries for " + elName
								+ ", however the submitted data had only " + subAuths.size() + " entries.",
						ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
						results.add(rs);
			}
			
    	} else {
    		log.info("Skipping compareAuthors due to empty refAuths.");
    	}		
	}
    
    public static boolean isProvenancePresent(CCDAEffTime effTime, CCDADataElement refOrgName, ArrayList<CCDAAuthor> subAuths) {
    	log.info("enter isProvenancePresent(...)");
    	
    	boolean isProvenanceMatched = false;
    	String elName = "Comparing Author Provenance Data";
    	ArrayList<ContentValidationResult> contentValidationResults = new ArrayList<ContentValidationResult>();
    	
    	if (subAuths == null) {
    		log.info("subAuths is null, skipping: " + elName);
    	} else {
	    	for(CCDAAuthor curSubAuth : subAuths) {
	    		ParserUtilities.compareEffectiveTimeValue(effTime, curSubAuth.getEffTime(), contentValidationResults, elName);	    		
	    		ParserUtilities.compareDataElementText(refOrgName, curSubAuth.getOrgName(), contentValidationResults, elName);
	    		
	    		if(contentValidationResults != null && contentValidationResults.size() == 0 ) {	    			
	    			log.info(" Matched Provenance Data ");
	    			isProvenanceMatched = true;
	    			break;
	    		}
	    		else {
	    			contentValidationResults.clear();
	    		}
	    	}
		}
    	
    	return isProvenanceMatched;
    }
    
	public static boolean isProvenancePresentInReferencesWithData(CCDAEffTime effTime, CCDADataElement curRefAuthOrgName,
			ArrayList<CCDAAuthor> subAuths, ArrayList<CCDAAuthor> authorsWithLinkedReferenceData) {
		log.info("Checking current author(s) have a link and the linked reference (in authorsWithLinkedReferenceData) has valid data");
		
    	boolean isProvenanceMatched = false;
    	String elName = "Comparing Author Provenance Data and cross-checking references";
    	ArrayList<ContentValidationResult> contentValidationResults = new ArrayList<ContentValidationResult>();
    	
		if (subAuths == null) {
			log.info("subAuths is null, skipping: " + elName);
		} else {
			for (CCDAAuthor curSubAuth : subAuths) {
				ParserUtilities.compareEffectiveTimeValue(effTime, curSubAuth.getEffTime(), contentValidationResults, elName);
				
				CCDAAuthor curLinkedSubAuth = findLinkedSubAuth(authorsWithLinkedReferenceData, curSubAuth);
				// We still need to run this (compareDataElementText) a 2nd time because the time and name issues (among others) are not separated
				// TODO: We could consider separating out the issues so that we can either cache the data element result instead of re-running,
				// or, not have to because we return individual errors up front that can't be fixed by a link (like orgName can/time cannot).
				// However, there are complications/reasons it is combined, where the increase in logic to adhere to the reqs 
				// (only need at least one valid match from lists in sub to scenario) may make the performance improvement moot
				ParserUtilities.compareDataElementText(curRefAuthOrgName, curLinkedSubAuth.getOrgName(), contentValidationResults, elName);

				if (contentValidationResults != null && contentValidationResults.size() == 0) {
					log.info(" Matched Provenance Data ");
					isProvenanceMatched = true;
					break;
				} else {
					contentValidationResults.clear();
				}
			}
		}
    	
    	return isProvenanceMatched;
    }
	
	/**
	 * @return A populated linked submitted author (matches root and ext), or, an empty instantiated author if no match exists 
	 */
	public static CCDAAuthor findLinkedSubAuth(ArrayList<CCDAAuthor> authorsWithLinkedReferenceData,
			CCDAAuthor curSubAuth) {
		CCDAAuthor linkedSubAuth = new CCDAAuthor();
		
		if (curSubAuth != null && curSubAuth.getAuthorIds() != null && authorsWithLinkedReferenceData != null) {
			for (CCDAII curSubAuthId : curSubAuth.getAuthorIds()) {
				if (curSubAuthId.getRootValue() != null && curSubAuthId.getExtValue() != null) {

					for (CCDAAuthor curLinkableAuth : authorsWithLinkedReferenceData) {
						if (curLinkableAuth.getAuthorIds() != null) {
							for (CCDAII curLinkableAuthId : curLinkableAuth.getAuthorIds()) {
								
								if (curLinkableAuthId.getRootValue() != null && curLinkableAuthId.getExtValue() != null) {
									if (curSubAuthId.getRootValue().equals(curLinkableAuthId.getRootValue())
											&& curSubAuthId.getExtValue().equals(curLinkableAuthId.getExtValue())) {
										log.info("Found a linked author match. "
												+ "Returning the linked author to compare instead of the inline one.");
										log.info("Linked author: ");
										curLinkableAuth.log();
										return curLinkableAuth;
									}
								}
								
							}
						}
					}

				}
			}
		}
		
		return linkedSubAuth;
	}
    
    // This does not seem to be used....
    public static boolean isProvenancePresent(CCDAEffTime effTime, CCDADataElement name, CCDAAuthor subAuth) {
    	
    	boolean retVal = false;
    	String elName = "Comparing Author Provenance Data";
    	ArrayList<ContentValidationResult> res = new ArrayList<ContentValidationResult>();   	
    	
    	ParserUtilities.compareEffectiveTimeValueWithExactMatchFullPrecision(effTime, subAuth.getEffTime(), res, elName);
    		
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
    
	/**
	 * Compare if the root section-level author info in this reference author is present in submitted author
	 */
	public static void compareSectionLevelAuthor(String elName, CCDAAuthor refAuthor, CCDAAuthor subAuthor,
			ArrayList<ContentValidationResult> results) {		
		log.info("Comparing " + elName + ", section-level-only author");
		log.info("Ref Section Author " + (refAuthor != null ? "found" : "not found"));
		log.info("Sub Section Author " + (subAuthor != null ? "found" : "not found"));
		
		if (refAuthor != null && subAuthor != null) {
			refAuthor.matches(subAuthor, results, elName);
		} else if (refAuthor != null && subAuthor == null) {
			ContentValidationResult rs = new ContentValidationResult(
					"The scenario requires Provenance data for " + elName
							+ " however the submitted file does not contain the Provenance data for " + elName + ".",
					ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
			results.add(rs);
		} else {
			log.info("Author is null in the reference data, nothing to do");
		}		
	}

}
