package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

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
	private ArrayList<CCDAII> templateId;
	private CCDAEffTime		  effectiveTime;
	
	public void log()
	{
		log.info(" *** Participant ***");
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
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
	}
	
	public CCDAParticipant()
	{
		templateId = new ArrayList<>();
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

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
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
	
	
	
	
}
