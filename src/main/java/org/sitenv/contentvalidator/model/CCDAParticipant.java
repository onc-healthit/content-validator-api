package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAParticipant {
	
	private static Logger log = LoggerFactory.getLogger(CCDAParticipant.class.getName());

	private CCDAAssignedEntity assignedEntity;
	private CCDACode		participantFunctionCode;
	private CCDACode		participantRoleCode;
	private CCDAII			participantRoleId;
	private String			participantTypeCode;
	private CCDAPlayingEntity participantRolePlayingEntity;
	private CCDAAddress		  participantRoleAddress;
	private ArrayList<CCDAII>    templateIds;
	private CCDAEffTime		  effectiveTime;
	private CCDAAssociatedEntity associatedEntity;
	
	public void log()
	{
		log.info(" *** Participant ***");
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		
		if(assignedEntity != null)
			assignedEntity.log();
		if(participantRoleCode != null)
			participantRoleCode.log();
		if(participantRoleId != null)
			participantRoleId.log();
		if(participantFunctionCode != null)
			participantFunctionCode.log();
		if(participantRolePlayingEntity != null)
			participantRolePlayingEntity.log();
		if(effectiveTime != null) 
			effectiveTime.log();
		if(participantRoleAddress != null)
			participantRoleAddress.log();
		if(associatedEntity != null)
			associatedEntity.log();
	}
	
	public CCDAParticipant()
	{
		templateIds = new ArrayList<>();
	}

	public CCDAAssignedEntity getAssignedEntity() {
		return assignedEntity;
	}

	public void setAssignedEntity(CCDAAssignedEntity assignedEntity) {
		this.assignedEntity = assignedEntity;
	}

	public CCDACode getParticipantRoleCode() {
		return participantRoleCode;
	}

	public void setParticipantRoleCode(CCDACode participantRoleCode) {
		this.participantRoleCode = participantRoleCode;
	}

	public CCDAII getParticipantRoleId() {
		return participantRoleId;
	}

	public void setParticipantRoleId(CCDAII participantRoleId) {
		this.participantRoleId = participantRoleId;
	}

	public CCDACode getParticipantFunctionCode() {
		return participantFunctionCode;
	}

	public void setParticipantFunctionCode(CCDACode participantFunctionCode) {
		this.participantFunctionCode = participantFunctionCode;
	}

	public String getParticipantTypeCode() {
		return participantTypeCode;
	}

	public void setParticipantTypeCode(String participantTypeCode) {
		this.participantTypeCode = participantTypeCode;
	}

	public CCDAPlayingEntity getPlayingEntity() {
		return participantRolePlayingEntity;
	}

	public void setPlayingEntity(CCDAPlayingEntity playingEntity) {
		this.participantRolePlayingEntity = playingEntity;
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDAEffTime getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(CCDAEffTime effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public CCDAPlayingEntity getParticipantRolePlayingEntity() {
		return participantRolePlayingEntity;
	}

	public void setParticipantRolePlayingEntity(CCDAPlayingEntity participantRolePlayingEntity) {
		this.participantRolePlayingEntity = participantRolePlayingEntity;
	}

	public CCDAAddress getParticipantRoleAddress() {
		return participantRoleAddress;
	}

	public void setParticipantRoleAddress(CCDAAddress participantRoleAddress) {
		this.participantRoleAddress = participantRoleAddress;
	}

	public CCDAAssociatedEntity getAssociatedEntity() {
		return associatedEntity;
	}

	public void setAssociatedEntity(CCDAAssociatedEntity associatedEntity) {
		this.associatedEntity = associatedEntity;
	}

	public static void compareRelatedPersons(ArrayList<CCDAParticipant> refParts, ArrayList<CCDAParticipant> subParts,
			ArrayList<ContentValidationResult> results) {
		
		for(CCDAParticipant ref : refParts) {
			checkForParticipantInSubmitted(ref, subParts, results);
		}
		
	}

	private static void checkForParticipantInSubmitted(CCDAParticipant ref, ArrayList<CCDAParticipant> subParts,
			ArrayList<ContentValidationResult> results) {
		
		Boolean found = false;
		if(ref.getAssociatedEntity() != null) {
			
			for(CCDAParticipant sub : subParts) {
				if(sub.checkForAssociatedEntity(ref.getAssociatedEntity())) {
					ParserUtilities.compareTemplateIds(ref.getTemplateIds(), sub.getTemplateIds(), results, "Related Person TemplateId Comparison");
					found = true;
					break;
				}
			}
			
			if(!found) {
				
				String relationship = "";
				if(ref.getAssociatedEntity().getAssociatedEntityCode() != null && 
						ref.getAssociatedEntity().getAssociatedEntityCode().getCode() != null) 
					relationship = ref.getAssociatedEntity().getAssociatedEntityCode().getCode();
				
				String fname = "";
				if(ref.getAssociatedEntity().getFirstName() != null)
					fname = ref.getAssociatedEntity().getFirstName().getValue();
				
				String lname = "";
				if(ref.getAssociatedEntity().getLastName() != null)
					lname = ref.getAssociatedEntity().getLastName().getValue();
				
				// Error
				String error = "The scenario contains RelatedPerson data with Relationship:, lastName: , firstName: " + 
						relationship + ", " + 
						fname + ", " +
						lname + 
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}
		
	}

	private boolean checkForAssociatedEntity(CCDAAssociatedEntity ref) {
		
		if(ref.getAssociatedEntityCode() != null && 
			ref.getAssociatedEntityCode().getCode() != null &&
			this.getAssociatedEntity() != null &&
			this.getAssociatedEntity().getAssociatedEntityCode() != null &&
			this.getAssociatedEntity().getAssociatedEntityCode().getCode() != null &&
			ref.getAssociatedEntityCode().getCode().equalsIgnoreCase(this.getAssociatedEntity().getAssociatedEntityCode().getCode()) &&
			ref.getFirstName() != null &&
			ref.getFirstName().getValue() != null &&
			this.getAssociatedEntity().getFirstName() != null &&
			this.getAssociatedEntity().getFirstName().getValue() != null &&
			ref.getFirstName().getValue().equalsIgnoreCase(this.getAssociatedEntity().getFirstName().getValue()) &&
			ref.getLastName() != null &&
			ref.getLastName().getValue() != null &&
			this.getAssociatedEntity().getLastName() != null &&
			this.getAssociatedEntity().getLastName().getValue() != null &&
			ref.getLastName().getValue().equalsIgnoreCase(this.getAssociatedEntity().getLastName().getValue()) ||
			(
				this.getAssociatedEntity().getName() != null &&
				this.getAssociatedEntity().getName().getValue() != null &&
				(
					this.getAssociatedEntity().getName().getValue().contains(ref.getFirstName().getValue()) &&
					this.getAssociatedEntity().getName().getValue().contains(ref.getLastName().getValue())
				)
			)
		)
		{
			return true;
		}
		return false;
	}
}
