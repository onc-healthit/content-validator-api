package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;
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

	public void compare(CCDABasicOccupation refOcc, ArrayList<ContentValidationResult> results, String context) {
		
		log.info("Comparing Basic Occupation Observation ");
		
		// Handle Template Ids
		ParserUtilities.compareTemplateIds(refOcc.getTemplateIds(), templateIds, results, context);
		
		// Compare Sex Codes 
		String elementNameVal = "Basic Occupation Observation code element for " + context;
		ParserUtilities.compareCode(refOcc.getBasicOccupationCode(), basicOccupationCode, results, elementNameVal);
		
		// Compare Sex Value 
		String valElementContext = "Basic Occupation Observation value element for " + context;
		ParserUtilities.compareCode(refOcc.getBasicOccupationValueCode(), basicOccupationValueCode, results, valElementContext);
		
		if(industry != null) {
			String industryContext = "Basic Occupation Industry Observation comparison for " + context;
			industry.compare(refOcc.getIndustry(), results, industryContext);
		}
		else if(refOcc.getIndustry() != null) {
			
			// Error
			String error = "The scenario does not require Basic Occupation Industry Observation data " + 
								" , however there is no Basic Occupation Industry Observation in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
						results.add(rs);
		}
		
	}
	
	
}
