package org.sitenv.contentvalidator.service;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.SeverityLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.parsers.CCDAParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Component
public class ContentValidatorService {		
	private static Logger log = Logger.getLogger(ContentValidatorService.class.getName());
	
	@Autowired
	private CCDAParser parser;
	@Resource
	HashMap<String, CCDARefModel> refModelHashMap;
	
	public ContentValidatorService() {
	}
	
	public ContentValidatorService(final HashMap<String, CCDARefModel> refModelHashMap) {
		this.refModelHashMap = refModelHashMap;
		parser = new CCDAParser();
	}
	
	public ArrayList<ContentValidationResult> validate(String validationObjective, String referenceFileName, String ccdaFile) {
		return validate(validationObjective, referenceFileName, ccdaFile, false, SeverityLevel.INFO);
	}
	
	public ArrayList<ContentValidationResult> validate(String validationObjective, String referenceFileName,
			String ccdaFile, boolean curesUpdate, SeverityLevel severityLevel) {
		log.info(" ***** CAME INTO THE REFERENCE VALIDATOR *****");
		
		ArrayList<ContentValidationResult> results = new ArrayList<>();
		if(!isObjectiveValidForContentValidation(validationObjective)) {
			log.warn("Content Validation not performed for objective " + validationObjective);
		}else{
			log.info(" Val Obj " + validationObjective + " Ref File " + referenceFileName);

			// Parse passed in File
			CCDARefModel submittedCCDA = parser.parse(ccdaFile, severityLevel);

			CCDARefModel ref = null;
			if( (referenceFileName != null)
					&& (!referenceFileName.isEmpty())
					&& (!(referenceFileName.trim()).isEmpty())) {
				ref = getCCDARefModel(referenceFileName);
			}

			if((ref != null) && (submittedCCDA != null)) {
				log.info("Comparing the Ref Model to the Submitted Model ");
				results = ref.compare(validationObjective, submittedCCDA);
			}
			else {
				log.error(" Submitted Model = " + ((submittedCCDA==null)?" Model is null":submittedCCDA.toString()));
				log.error(" Reference Model = " + ((ref==null)?" Model is null":ref.toString()));
				log.error("Something is wrong, not able to find ref model for " + referenceFileName);
			}
		}
		return results;
	}
	
	private Boolean isObjectiveValidForContentValidation(String valObj) {
		if(valObj.equalsIgnoreCase("170.315_b1_ToC_Amb") || 
		   valObj.equalsIgnoreCase("170.315_b1_ToC_Inp") ||
		   valObj.equalsIgnoreCase("170.315_b2_CIRI_Amb") ||
		   valObj.equalsIgnoreCase("170.315_b2_CIRI_Inp") ||
		   valObj.equalsIgnoreCase("170.315_b4_CCDS_Amb") ||
		   valObj.equalsIgnoreCase("170.315_b4_CCDS_Inp") ||
		   valObj.equalsIgnoreCase("170.315_b6_DE_Amb") ||
		   valObj.equalsIgnoreCase("170.315_b6_DE_Inp") ||
		   valObj.equalsIgnoreCase("170.315_b7_DS4P_Amb") ||
		   valObj.equalsIgnoreCase("170.315_b7_DS4P_Inp") ||
		   valObj.equalsIgnoreCase("170.315_b9_CP_Amb") ||
		   valObj.equalsIgnoreCase("170.315_b9_CP_Inp") ||
		   valObj.equalsIgnoreCase("170.315_e1_VDT_Amb") ||
		   valObj.equalsIgnoreCase("170.315_e1_VDT_Inp") ||
		   valObj.equalsIgnoreCase("170.315_g9_APIAccess_Amb") || 
		   valObj.equalsIgnoreCase("170.315_g9_APIAccess_Inp") )
			return true;
		else
			return false;
	}
	
	public CCDARefModel getCCDARefModel(String scenarioName)
	{
		Set<String> keys = refModelHashMap.keySet();
		
		for (String s : keys) {
		    
			log.info("Comparing " + scenarioName + " to " + s);
			if(scenarioName.contains(s)) {
				log.info("Returning Content Model for Comparison " + s );
				return refModelHashMap.get(s);
			}
		}
		
		return null;
	}

}
