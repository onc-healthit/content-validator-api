package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDABasicOccupationIndustry {
	
	private static Logger log = LoggerFactory.getLogger(CCDABasicOccupationIndustry.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            basicOccupationIndustryCode;
	private CCDACode							basicOccupationIndustryValueCode;
	private String								basicOccupationIndustryValueString;
	private CCDAAuthor							author;
	
	public CCDABasicOccupationIndustry() {
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** CCDA Basic Occupation Industry Observation *** ");
		
		if(basicOccupationIndustryCode != null)
			log.info(" Basic Occupation Industry Code = " + basicOccupationIndustryCode.getCode());
		
		if(basicOccupationIndustryValueCode != null)
			log.info(" Basic Occupation Industry Value Code = " + basicOccupationIndustryValueCode.getCode());
		
		if(basicOccupationIndustryValueString != null)
			log.info(" Basic Occupation Industry Value Code = " + basicOccupationIndustryValueString);

		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}

		if(author != null)
			author.log();
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getBasicOccupationIndustryCode() {
		return basicOccupationIndustryCode;
	}

	public void setBasicOccupationIndustryCode(CCDACode basicOccupationIndustryCode) {
		this.basicOccupationIndustryCode = basicOccupationIndustryCode;
	}

	public CCDACode getBasicOccupationIndustryValueCode() {
		return basicOccupationIndustryValueCode;
	}

	public void setBasicOccupationIndustryValueCode(CCDACode basicOccupationIndustryValueCode) {
		this.basicOccupationIndustryValueCode = basicOccupationIndustryValueCode;
	}

	public String getBasicOccupationIndustryValueString() {
		return basicOccupationIndustryValueString;
	}

	public void setBasicOccupationIndustryValueString(String basicOccupationIndustryValueString) {
		this.basicOccupationIndustryValueString = basicOccupationIndustryValueString;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	

}
