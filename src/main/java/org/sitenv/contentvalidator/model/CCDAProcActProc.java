package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDAProcActProc {
	
	private static Logger log = Logger.getLogger(CCDAProcActProc.class.getName());

	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		procCode;
	private CCDACode						procStatus;
	private CCDACode						targetSiteCode;
	private ArrayList<CCDAAssignedEntity>  	performer;
	private ArrayList<CCDAServiceDeliveryLoc>  	sdLocs;
	private CCDAII								piTemplateId;
	private ArrayList<CCDAUDI>					udi;
	private CCDACode							deviceCode;
	private CCDAII								scopingEntityId;
	
	public static void compareProcedures(HashMap<String, CCDAProcActProc> refProcs, 
			HashMap<String, CCDAProcActProc> subProcs, 	ArrayList<ContentValidationResult> results) {

		log.info(" Start Procedure Acts ");
		// For each Vital Sign Observation in the Ref Model, check if it is present in the subCCDA Model.
		for(Map.Entry<String, CCDAProcActProc> ent: refProcs.entrySet()) {

			if(subProcs.containsKey(ent.getKey())) {

				log.info("Comparing Procedure Acts ");
				String context = "Procedure Act Procedure Entry corresponding to the code " + ent.getKey();
				subProcs.get(ent.getKey()).compare(ent.getValue(), results, context);


			} else {
				// Error
				String error = "The scenario contains Procedure Activity Procedure data with code " + ent.getKey() +
						" , however there is no matching data in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
		}

		// Handle the case where the Vital Signs data is not present in the reference, 
		if( (refProcs == null || refProcs.size() == 0) && (subProcs != null && subProcs.size() > 0) ) {

			// Error
			String error = "The scenario does not require Procedure data " + 
					" , however there is Procedure data in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}
	
	public void compare(CCDAProcActProc refProc, ArrayList<ContentValidationResult> results , String context) {
		
		log.info("Comparing Procedure Activity Procedure ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refProc.getSectionTemplateId(), sectionTemplateId, results, context);
		
		// Compare Effective Times
		String elementNameTime = "Effective Time for " + context;
		//ParserUtilities.compareEffectiveTime(refResult.getMeasurementTime(), measurementTime, results, elementNameTime);
		
		// Compare Lab Codes 
		String elementNameVal = "Procedure Act Procedure code element for " + context;
		ParserUtilities.compareCode(refProc.getProcCode(), procCode, results, elementNameVal);
		
		String statusCodeElem = "Procedure Act Procedure Status code element for " + context;
		ParserUtilities.justCompareCode(refProc.getProcStatus(), procStatus, results, statusCodeElem);
		
	}

	
	
	public void log() {
		
		if(procCode != null)
			log.info(" Procedure Code = " + procCode.getCode());
		
		if(procStatus != null)
			log.info(" Procedure status = " + procStatus.getCode());
		
		for(int j = 0; j < sectionTemplateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateId.get(j).getExtValue());
		}
		
		if(targetSiteCode != null)
			log.info(" Target Site Code = " + targetSiteCode.getCode());
		
		if(deviceCode != null)
			log.info(" Device Code = " + deviceCode.getCode());
		
		if(piTemplateId != null)
			log.info(" Tempalte Id = " + piTemplateId.getRootValue());
		
		if(scopingEntityId != null)
			log.info(" Scoping Entity Id = " + scopingEntityId.getRootValue());
		
		for(int k = 0; k < performer.size(); k++) {
			performer.get(k).log();
		}
		
		for(int l = 0; l < sdLocs.size(); l++) {
			sdLocs.get(l).log();
		}
		
		for(int m = 0; m < udi.size(); m++) {
			udi.get(m).log();
		}
	}
	
	public CCDAProcActProc()
	{
		sectionTemplateId = new ArrayList<CCDAII>();
		performer = new ArrayList<CCDAAssignedEntity>();
		sdLocs = new ArrayList<CCDAServiceDeliveryLoc>();
		udi = new ArrayList<CCDAUDI>();
	}

	public ArrayList<CCDAII> getSectionTemplateId() {
		return sectionTemplateId;
	}

	public void setSectionTemplateId(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.sectionTemplateId = ids;
	}

	public CCDACode getProcCode() {
		return procCode;
	}

	public void setProcCode(CCDACode procCode) {
		this.procCode = procCode;
	}

	public CCDACode getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(CCDACode procStatus) {
		this.procStatus = procStatus;
	}

	public CCDACode getTargetSiteCode() {
		return targetSiteCode;
	}

	public void setTargetSiteCode(CCDACode targetSiteCode) {
		this.targetSiteCode = targetSiteCode;
	}

	public ArrayList<CCDAAssignedEntity> getPerformer() {
		return performer;
	}

	public void setPerformer(ArrayList<CCDAAssignedEntity> ps) {
		
		if(ps != null)
			this.performer = ps;
	}

	public ArrayList<CCDAServiceDeliveryLoc> getSdLocs() {
		return sdLocs;
	}

	public void setSdLocs(ArrayList<CCDAServiceDeliveryLoc> sdl) {
		
		if(sdl != null)
		this.sdLocs = sdl;
	}

	public CCDAII getPiTemplateId() {
		return piTemplateId;
	}

	public void setPiTemplateId(CCDAII piTemplateId) {
		this.piTemplateId = piTemplateId;
	}

	public ArrayList<CCDAUDI> getUdi() {
		return udi;
	}

	public void setUdi(ArrayList<CCDAUDI> udis) {
		
		if(udis != null)
			this.udi = udis;
	}

	public CCDACode getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(CCDACode deviceCode) {
		this.deviceCode = deviceCode;
	}

	public CCDAII getScopingEntityId() {
		return scopingEntityId;
	}

	public void setScopingEntityId(CCDAII scopingEntityId) {
		this.scopingEntityId = scopingEntityId;
	}

	public void setPatientUDI(ArrayList<CCDAUDI> udis) {
		
		if(udis != null)
			this.udi = udis;
		
	}
	
}
