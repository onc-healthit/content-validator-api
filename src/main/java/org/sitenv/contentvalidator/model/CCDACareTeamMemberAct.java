package org.sitenv.contentvalidator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;

public class CCDACareTeamMemberAct {
	
	private static Logger log = Logger.getLogger(CCDACareTeamMemberAct.class.getName());

	private ArrayList<CCDAII>    			templateIds;
	private CCDACode             			memberActCode;
    private CCDACode						statusCode;
    private CCDAEffTime						effectiveTime;
    
    private CCDAParticipant					primaryPerformer;
    private ArrayList<CCDAParticipant>		otherCareTeamMembers;
	
	private CCDAAuthor						author;
	
	public void log() {
		
		if(memberActCode != null && memberActCode.getCode() != null) {		
			log.info(" Member Act Code = " + memberActCode.getCode());
		}
		
		if(statusCode != null && statusCode.getCode() != null) {		
			log.info(" Status Code = " + statusCode.getCode());
		}
		
		if(effectiveTime != null && effectiveTime.getLowPresent()) {		
			log.info(" Effective Time Low = " + effectiveTime.getLow().getValue());
		}
		
		if(effectiveTime != null && effectiveTime.getHighPresent()) {		
			log.info(" Effective Time High = " + effectiveTime.getHigh().getValue());
		}
		
		if(primaryPerformer != null)
			primaryPerformer.log();
		
		
		for (int i = 0; i < otherCareTeamMembers.size(); i++) {
			otherCareTeamMembers.get(i).log();
		}
		
		if(author != null)
			author.log();
	}
	
	public CCDACareTeamMemberAct()
	{
		otherCareTeamMembers = new ArrayList<CCDAParticipant>();
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateId) {
		this.templateIds = templateId;
	}

	public CCDACode getMemberActCode() {
		return memberActCode;
	}

	public void setMemberActCode(CCDACode memberActCode) {
		this.memberActCode = memberActCode;
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

	public CCDAParticipant getPrimaryPerformer() {
		return primaryPerformer;
	}

	public void setPrimaryPerformer(CCDAParticipant primaryPerformer) {
		this.primaryPerformer = primaryPerformer;
	}

	public ArrayList<CCDAParticipant> getOtherCareTeamMembers() {
		return otherCareTeamMembers;
	}

	public void setOtherCareTeamMembers(ArrayList<CCDAParticipant> otherCareTeamMembers) {
		this.otherCareTeamMembers = otherCareTeamMembers;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}
	
	public void addParticipant(CCDAParticipant pp)
	{
		if(otherCareTeamMembers != null)
			otherCareTeamMembers.add(pp);
		else 
		{
			otherCareTeamMembers = new ArrayList<CCDAParticipant>();
			otherCareTeamMembers.add(pp);
		}
	}
	
	public static void compareMembers(HashMap<String, CCDACareTeamMemberAct> refActs,
			HashMap<String, CCDACareTeamMemberAct> subActs, 
			ArrayList<ContentValidationResult> results) {
		
		log.info(" Start Comparing Care Team Members level data ");
		
		// For each Notes activity in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDACareTeamMemberAct> ent: refActs.entrySet()) {

			if(subActs.containsKey(ent.getKey())) {

				log.info("Comparing Care Team Member data for name " + ent.getKey());
				
				// For now, nothing else to do since we are only comparing names.

			} else {
				// Error
				String error = "The scenario contains Care Team Member data with name " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}
		
	}
	
}
