package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDAProblem {

	private static Logger log = Logger.getLogger(CCDAProblem.class.getName());
	
	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		sectionCode;
	private ArrayList<CCDAProblemConcern>  	problemConcerns;
	
	public void compare(CCDAProblem submittedProblem, ArrayList<ContentValidationResult> results) {
	
		// handle section code.
		ParserUtilities.compareCode(sectionCode, submittedProblem.getSectionCode(), results, "Problem Section");
		
		// Hanlde Section Template Ids
		ParserUtilities.compareTemplateIds(sectionTemplateId, submittedProblem.getSectionTemplateId(), results, "Problem Section");
		
		//Compare details
		compareProblemData(submittedProblem, results);
	}
	
	private void compareProblemData(CCDAProblem submittedProblem, ArrayList<ContentValidationResult> results) {
		
		HashMap<CCDAProblemObs, CCDAProblemConcern> probs = getProblemObservationsConcernMap();
		
		for(Map.Entry<CCDAProblemObs, CCDAProblemConcern> ent: probs.entrySet()) {
			
			//check to see if the ref data is part of the problem data submitted
			submittedProblem.validateProblemData(ent.getKey(), ent.getValue(), results);
			
		}
		
	}
	
	private void validateProblemData(CCDAProblemObs refPo, CCDAProblemConcern refCo, ArrayList<ContentValidationResult> results ) {
	
		HashMap<CCDAProblemObs, CCDAProblemConcern> probs = getProblemObservationsConcernMap();
		
		CCDAProblemObs subObs = null;
		CCDAProblemConcern conc = null;
		for(Map.Entry<CCDAProblemObs, CCDAProblemConcern> ent: probs.entrySet()) {
			
			// Find the entry which corresponds to the same value as the refPo
			if(ent.getKey().getProblemCode() != null && 
			   refPo.getProblemCode() != null && 
			   refPo.getProblemCode().getCode().equalsIgnoreCase(ent.getKey().getProblemCode().getCode())) {
				
				log.info("Found the Problem Observation in submitted CCDA");
				subObs = ent.getKey();
				conc = ent.getValue();
				
				break;
			}
			
		}
		
		if( (subObs != null) && (conc != null)) {
			
			String probObsContext = ((refPo.getProblemCode() != null)?(refPo.getProblemCode().getDisplayName()):" Unknown Observation ");
			refCo.compare(conc, probObsContext, results);
			refPo.compare(subObs, probObsContext, results);
		}
		else {
			String error = "The scenario contains problem observation for " + 
							((refPo.getProblemCode() != null)?((refPo.getProblemCode().getDisplayName()) + " Code: " + refPo.getProblemCode().getCode()) :" Unknown Observation ") +
							" , however there is no matching observation in the submitted CCDA. ";
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		
	}
	
	private HashMap<CCDAProblemObs, CCDAProblemConcern> getProblemObservationsConcernMap() {
		
		HashMap<CCDAProblemObs, CCDAProblemConcern> pobs = new HashMap<CCDAProblemObs, CCDAProblemConcern>();
		
		for(CCDAProblemConcern c: problemConcerns) {
			
			ArrayList<CCDAProblemObs> obs = c.getProblems();
			
			for(CCDAProblemObs o: obs) {
				pobs.put(o,  c);
			}
			
		}
		
		return pobs;
	}
	
	public void log() {
		
		if(sectionCode != null)
			log.info(" Problem Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < sectionTemplateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateId.get(j).getExtValue());
		}
		
		for(int k = 0; k < problemConcerns.size(); k++) {
			problemConcerns.get(k).log();
		}
	}
	
	public ArrayList<CCDAProblemConcern> getProblemConcerns() {
		return problemConcerns;
	}

	public void setProblemConcerns(ArrayList<CCDAProblemConcern> pc) {
		
		if(pc != null)
			this.problemConcerns = pc;
	}

	public ArrayList<CCDAII> getSectionTemplateId() {
		return sectionTemplateId;
	}

	public void setSectionTemplateId(ArrayList<CCDAII> ids) {
		if(ids != null)
			this.sectionTemplateId = ids;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public CCDAProblem()
	{
		problemConcerns = new ArrayList<CCDAProblemConcern>();
		sectionTemplateId = new ArrayList<CCDAII>();
	}
}
