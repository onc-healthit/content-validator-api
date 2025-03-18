package org.sitenv.contentvalidator.model;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDAServiceDeliveryLoc {
	
	private static Logger log = LoggerFactory.getLogger(CCDAServiceDeliveryLoc.class.getName());

	private ArrayList<CCDAII>           templateId;
	private ArrayList<CCDAII>			facilityIdentifiers;
	private CCDACode                    locationCode;
	private ArrayList<CCDAAddress>      address;
	private ArrayList<CCDATelecom>  telecom;
	private CCDADataElement             name;
	
	public void log() {
		
		log.info("*** Service Delivery Location ***");
				
		for(int j = 0; j < templateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateId.get(j).getExtValue());
		}
	
		if(locationCode != null)
			log.info("Location Code = " + locationCode.getCode());
		
		for(int i = 0; i < address.size(); i++) {
			address.get(i).log();
		}
		
		for(int l = 0; l < telecom.size(); l++) {
			telecom.get(l).log(l);
		}
		
		if(name != null)
			log.info(" Name = " + name.getValue());
		
	}
	
	public ArrayList<CCDAII> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.templateId = ids;
	}

	public CCDACode getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(CCDACode locationCode) {
		this.locationCode = locationCode;
	}

	public ArrayList<CCDAAddress> getAddress() {
		return address;
	}

	public void setAddress(ArrayList<CCDAAddress> addr) {
		
		if(addr != null)
			this.address = addr;
	}

	public ArrayList<CCDATelecom> getTelecom() {
		return telecom;
	}

	public void setTelecom(ArrayList<CCDATelecom> tels) {
		
		if( tels != null)
			this.telecom = tels;
	}

	public CCDADataElement getName() {
		return name;
	}

	public void setName(CCDADataElement name) {
		this.name = name;
	}

	public CCDAServiceDeliveryLoc()
	{
		templateId = new ArrayList<CCDAII>();
		telecom = new ArrayList<CCDATelecom>();
		address = new ArrayList<CCDAAddress>();
		facilityIdentifiers = new ArrayList<CCDAII>();
	}

	public ArrayList<CCDAII> getFacilityIdentifiers() {
		return facilityIdentifiers;
	}

	public void setFacilityIdentifiers(ArrayList<CCDAII> facilityIdentifiers) {
		this.facilityIdentifiers = facilityIdentifiers;
	}

	public static void compare(
			HashMap<String, CCDAServiceDeliveryLoc> refLocs,
			HashMap<String, CCDAServiceDeliveryLoc> subLocs, 
			ArrayList<ContentValidationResult> results,
			String context) {
		
		// Check only the reference ones
		for(Map.Entry<String,CCDAServiceDeliveryLoc> entry: refLocs.entrySet()) {
							
			if(subLocs.containsKey(entry.getKey())) {
									
				// Since the observation was found, compare other data elements.
				log.info(" Comparing Facility Information ");
				String compContext = "Facility information for " + entry.getKey() + " , for " + context;
				entry.getValue().compare(subLocs.get(entry.getKey()), compContext, results);
									
			}
			else {
									
				String error = "The scenario contains Facility data with type " + entry.getKey() + 
											" , however there is no matching data in the submitted CCDA.";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
							
		} // for
		
	}

	private void compare(CCDAServiceDeliveryLoc subSdLoc, String compContext,
			ArrayList<ContentValidationResult> results) {
		
		log.info("Comparing Facility Information");
		
		if(subSdLoc.getFacilityIdentifiers() == null ||
				subSdLoc.getFacilityIdentifiers().size() == 0) 
		{
			String context = compContext + " : The scenario requires FacilityIdentifiers data, but submitted file does have FacilityIdentifiers data.";
			ContentValidationResult rs = new ContentValidationResult(context, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
		if(subSdLoc.getName() == null ||
				subSdLoc.getName().getValue().isEmpty()) 
		{
			String context = compContext + " : The scenario requires FacilityName data, but submitted file does have FacilityName data.";
			ContentValidationResult rs = new ContentValidationResult(context, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}
	
	
}
