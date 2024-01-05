package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAPerformer {

	private static Logger log = LoggerFactory.getLogger(CCDAPerformer.class.getName());

	private ArrayList<CCDAII>				templateId;
	private String							typeCode;
	private CCDAAssignedEntity 				assignedEntity;
	
	public void log() {
		
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
		
		if(typeCode != null) 
			log.info(" Performer Code {}", typeCode);
		
		if(assignedEntity != null) 
			assignedEntity.log();
		
	}
	
	public CCDAPerformer() {
		templateId = new ArrayList<>();
	}

	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> templateId) {
		this.templateId = templateId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public CCDAAssignedEntity getAssignedEntity() {
		return assignedEntity;
	}

	public void setAssignedEntity(CCDAAssignedEntity assignedEntity) {
		this.assignedEntity = assignedEntity;
	}
	
	
	
}
