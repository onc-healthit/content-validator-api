package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger; 
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;

import java.util.ArrayList;

public class CCDAUDI {
	
	private static Logger log = Logger.getLogger(CCDAUDI.class.getName());
	
	private ArrayList<CCDAII>     templateIds;
	private ArrayList<CCDAII>     UDIValue;
	private CCDACode deviceCode;
	private ArrayList<CCDAII> scopingEntityId;
	
	private CCDAAuthor author;
	
	public CCDAUDI() {
		templateIds = new ArrayList<CCDAII>();
		UDIValue = new ArrayList<CCDAII>();
		scopingEntityId = new ArrayList<CCDAII>();
	}
	
	public static void compareUdis(ArrayList<CCDAUDI> refUdis, ArrayList<CCDAUDI> subUdis, ArrayList<ContentValidationResult> results) {
		
		log.info(" Comparing Udi values ");
		
		for(int i = 0; i < refUdis.size(); i++) {
			
			//for each Udi in the Reference Model, it should be present in the Submitted model.
			if(CCDAUDI.isPresent(refUdis.get(i), subUdis, results)) {
				
				// We have to compare each UDI and verify it is present.
				log.info(" Found UDI at index " + i + " in submitted model ");
			}
			else {
				log.info(" Did not find the UDI at index " + i + " in submitted model ");
			}
		}
	}
	
	public static Boolean isPresent(CCDAUDI refUdi, ArrayList<CCDAUDI> subUdis, ArrayList<ContentValidationResult> results) {
		
		for(int i = 0; i < subUdis.size();i++) {
			
			CCDAUDI subUdi = subUdis.get(i);
			
			if(subUdi.contains(refUdi, results)) {
				log.info(" Ref Udi is present in the Sub Udis ");
				return true;
			}
		}
			
		return false;
	}
	
	public Boolean contains(CCDAUDI refUdi, ArrayList<ContentValidationResult> results) {
		
		ArrayList<CCDAII> udiVals = refUdi.getUDIValue();
		
		Boolean found = false;
		for(int i = 0; i < udiVals.size(); i++) {
			
			CCDAII refii = udiVals.get(i);
			if(this.getUDIValue() != null && 
				refii.isPartOf(this.getUDIValue())) {
				
				log.info(" Found one of the Udis in the list " + refii.getRootValue() + refii.getExtValue());
				found = true; // Make it true as long as we keep hitting this condition.
			}
			else {
				log.info(" Did not find the UDI " + refii.getRootValue() + " : " + refii.getExtValue());
				ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's UDI: Root = " + refii.getRootValue() + " Extension = " + refii.getExtValue() + ", but the submitted C-CDA does not contain UDI data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				return false; // If we dont find even one of them..then the data is not matching.
			}
			
		}
		
		return found;
	}
	
	public void log() { 
		
		log.info(" *** UDI *** ");
		
		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}
		
		for(int k = 0; k < UDIValue.size(); k++) {
			log.info(" Tempalte Id [" + k + "] = " + UDIValue.get(k).getRootValue());
			log.info(" Tempalte Id Ext [" + k + "] = " + UDIValue.get(k).getExtValue());
		}
		
		for(int l = 0; l < scopingEntityId.size(); l++) {
			log.info(" Tempalte Id [" + l + "] = " + scopingEntityId.get(l).getRootValue());
			log.info(" Tempalte Id Ext [" + l + "] = " + scopingEntityId.get(l).getExtValue());
		}
		
		if(deviceCode != null)
			log.info(" Device Code = " + deviceCode.getCode());
		
		if(author != null)
			author.log();
		
	}
	
	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}
	public void setTemplateIds(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateIds = ids;
	}
	public CCDACode getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(CCDACode deviceCode) {
		this.deviceCode = deviceCode;
	}
	public ArrayList<CCDAII> getUDIValue() {
		return UDIValue;
	}
	public void setUDIValue(ArrayList<CCDAII> udis) {
		
		if(udis != null)
			UDIValue = udis;
	}
	public ArrayList<CCDAII> getScopingEntityId() {
		return scopingEntityId;
	}
	public void setScopingEntityId(ArrayList<CCDAII> sids) {
		
		if(sids != null)
			this.scopingEntityId = sids;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((UDIValue == null) ? 0 : UDIValue.hashCode());
		result = prime * result
				+ ((deviceCode == null) ? 0 : deviceCode.hashCode());
		result = prime * result
				+ ((scopingEntityId == null) ? 0 : scopingEntityId.hashCode());
		result = prime * result
				+ ((templateIds == null) ? 0 : templateIds.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CCDAUDI other = (CCDAUDI) obj;
		if (UDIValue == null) {
			if (other.UDIValue != null)
				return false;
		} else if (!UDIValue.equals(other.UDIValue))
			return false;
		if (deviceCode == null) {
			if (other.deviceCode != null)
				return false;
		} else if (!deviceCode.equals(other.deviceCode))
			return false;
		if (scopingEntityId == null) {
			if (other.scopingEntityId != null)
				return false;
		} else if (!scopingEntityId.equals(other.scopingEntityId))
			return false;
		if (templateIds == null) {
			if (other.templateIds != null)
				return false;
		} else if (!templateIds.equals(other.templateIds))
			return false;
		return true;
	}
	
	
}
