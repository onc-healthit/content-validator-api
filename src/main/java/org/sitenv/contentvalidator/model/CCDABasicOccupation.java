package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDABasicOccupation {

	private static Logger log = LoggerFactory.getLogger(CCDABasicOccupation.class.getName());

	private ArrayList<CCDAII>					templateIds;
	private CCDACode                            basicOccupationCode;
	private CCDACode							basicOccupationValueCode;
	private String								basicOccupationValueString;
	private CCDABasicOccupationIndustry			industry;
	private CCDAAuthor							author;
	
	public CCDABasicOccupation() {
		templateIds = new ArrayList<>();
	}
	
	public void log() {
		
		log.info(" *** CCDA Basic Occupation Observation *** ");
		
		if(basicOccupationCode != null)
			log.info(" Basic Occupation Code = " + basicOccupationCode.getCode());
		
		if(basicOccupationValueCode != null)
			log.info(" Basic Occupation Value Code = " + basicOccupationValueCode.getCode());
		
		if(basicOccupationValueString != null)
			log.info(" Basic Occupation Value Code = " + basicOccupationValueString);

		for(int j = 0; j < templateIds.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + templateIds.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + templateIds.get(j).getExtValue());
		}

		if(author != null)
			author.log();
		
		if(industry != null)
			industry.log();
	}

	public ArrayList<CCDAII> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(ArrayList<CCDAII> templateIds) {
		this.templateIds = templateIds;
	}

	public CCDACode getBasicOccupationCode() {
		return basicOccupationCode;
	}

	public void setBasicOccupationCode(CCDACode basicOccupationCode) {
		this.basicOccupationCode = basicOccupationCode;
	}

	public CCDACode getBasicOccupationValueCode() {
		return basicOccupationValueCode;
	}

	public void setBasicOccupationValueCode(CCDACode basicOccupationValueCode) {
		this.basicOccupationValueCode = basicOccupationValueCode;
	}

	public String getBasicOccupationValueString() {
		return basicOccupationValueString;
	}

	public void setBasicOccupationValueString(String basicOccupationValueString) {
		this.basicOccupationValueString = basicOccupationValueString;
	}

	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public CCDABasicOccupationIndustry getIndustry() {
		return industry;
	}

	public void setIndustry(CCDABasicOccupationIndustry industry) {
		this.industry = industry;
	}
	
	
}
