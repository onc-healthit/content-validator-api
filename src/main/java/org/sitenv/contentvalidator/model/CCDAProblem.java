package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDAProblem {

	private static Logger log = LoggerFactory.getLogger(CCDAProblem.class.getName());
	
	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		sectionCode;
	private ArrayList<CCDAProblemConcern>  	problemConcerns;
	private ArrayList<CCDAProblemObs>       pastIllnessProblems;
	
	private CCDAAuthor author;
	
	public ArrayList<CCDAProblemObs> getPastIllnessProblems() {
		return pastIllnessProblems;
	}

	public void setPastIllnessProblems(ArrayList<CCDAProblemObs> pastIllnessProblems) {
		this.pastIllnessProblems = pastIllnessProblems;
	}

	public void compare(CCDAProblem submittedProblem, ArrayList<ContentValidationResult> results) {
	
		// handle section code.
		ParserUtilities.compareCode(sectionCode, submittedProblem.getSectionCode(), results, "Problem Section");
		
		// Hanlde Section Template Ids
		ParserUtilities.compareTemplateIds(sectionTemplateId, submittedProblem.getSectionTemplateId(), results, "Problem Section");
		
		// Compare details
		compareProblemData(submittedProblem, results);
	}
	
	private void compareProblemData(CCDAProblem submittedProblem, ArrayList<ContentValidationResult> results) {
		
		HashMap<CCDAProblemObs, CCDAProblemConcern> probs = getProblemObservationsConcernMap();
		
		for(Map.Entry<CCDAProblemObs, CCDAProblemConcern> ent: probs.entrySet()) {
			
			// check to see if the ref data is part of the problem data submitted
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
			
			subObs = null;
			// Check if the problem is part of the Past Illness Section problems.
			log.info(" Looking for problem in Past Illness section ");
			
			if(pastIllnessProblems != null) {
				
				for(int i = 0; i < pastIllnessProblems.size(); ++i) {
					
					CCDAProblemObs entry = pastIllnessProblems.get(i);
					if(entry.getProblemCode() != null && 
					   refPo.getProblemCode() != null && 
					   refPo.getProblemCode().getCode().equalsIgnoreCase(entry.getProblemCode().getCode())) {
								
							log.info("Found the Problem Observation in submitted CCDA in Past Illness Section");
							subObs = entry;
							break;
					}
				}
			}
			
			if(subObs != null) 
			{
				// Compare the problem observations
				String probObsContext = ((refPo.getProblemCode() != null)?(refPo.getProblemCode().getDisplayName()):" Unknown Observation ");
				refPo.compare(subObs, probObsContext, results);
			}
			else 
			{
			
				String error = "The scenario contains problem observation for " + 
							((refPo.getProblemCode() != null)?((refPo.getProblemCode().getDisplayName()) + " Code: " + refPo.getProblemCode().getCode()) :" Unknown Observation ") +
							" , however there is no matching observation in the submitted CCDA. ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
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
	
	public void compareAuthor(CCDAProblem subProblem, ArrayList<ContentValidationResult> results, boolean curesUpdate,
			ArrayList<CCDAAuthor> authorsWithLinkedReferenceData) {
		String elName = "Problem Section";
		CCDAAuthor.compareSectionLevelAuthor(elName, author,
				subProblem != null && subProblem.getAuthor() != null ? subProblem.getAuthor() : null, results);		
		
		log.info("Comparing Authors for Problem Concerns");
		ArrayList<CCDAAuthor> refAllConcAuths = this.getProblemConcernAuthors();
		ArrayList<CCDAAuthor> subAllConcAuths = subProblem != null && subProblem.getProblemConcernAuthors() != null
				? subProblem.getProblemConcernAuthors()
				: null;
		elName += "/ProblemConcern";
		CCDAAuthor.compareAuthors(refAllConcAuths, subAllConcAuths, results, elName, authorsWithLinkedReferenceData);
		
		log.info("Comparing Authors for Problem Observation in Problem Concern (problems)");
		ArrayList<CCDAAuthor> refAllObsInProbConcAuths = this.getProblemObsInProblemConcAuthors();
		ArrayList<CCDAAuthor> subAllObsInProbConcAuths = subProblem != null && subProblem.getProblemObsInProblemConcAuthors() != null
				? subProblem.getProblemObsInProblemConcAuthors()
				: null;
		elName += "/ProblemObservation";
		CCDAAuthor.compareAuthors(refAllObsInProbConcAuths, subAllObsInProbConcAuths, results, elName,
				authorsWithLinkedReferenceData);
		
		log.info("Comparing Authors for Problem Observation in Past Medical History Section/Problem Observation (pastIllnessProblems)");
		ArrayList<CCDAAuthor> refAllObsPastIllnessAuths = this.getProblemObsPastIllnessAuthors();
		ArrayList<CCDAAuthor> subAllObsPastIllnessAuths = subProblem != null && subProblem.getProblemObsPastIllnessAuthors() != null 
				? subProblem.getProblemObsPastIllnessAuthors()
				: null;
		// Providing elName directly for this one vs concatenating since part of a different section but stored in thie CCDAProblem model
		CCDAAuthor.compareAuthors(refAllObsPastIllnessAuths, subAllObsPastIllnessAuths, results,
				"Past Medical History Section (Past Illness)/ProblemObservation", authorsWithLinkedReferenceData);
	}
	
	public ArrayList<CCDAAuthor> getProblemConcernAuthors() {
		ArrayList<CCDAAuthor> authors = new ArrayList<CCDAAuthor>();
		
		for (CCDAProblemConcern curConcern : problemConcerns) {
			if (curConcern.getAuthor() != null) {
				authors.add(curConcern.getAuthor());
			}
		}
		
		return authors;
	}

	public ArrayList<CCDAAuthor> getProblemObsInProblemConcAuthors() {
		ArrayList<CCDAAuthor> authors = new ArrayList<CCDAAuthor>();

		for (CCDAProblemConcern curConcern : problemConcerns) {
			for (CCDAProblemObs curObs : curConcern.getProblems()) {
				if (curObs.getAuthor() != null) {
					authors.add(curObs.getAuthor());
				}
			}
		}

		return authors;
	}
	 
	public ArrayList<CCDAAuthor> getProblemObsPastIllnessAuthors() {
		ArrayList<CCDAAuthor> authors = new ArrayList<CCDAAuthor>();

		for (CCDAProblemObs curObs : pastIllnessProblems) {
			if (curObs.getAuthor() != null) {
				authors.add(curObs.getAuthor());
			}
		}
		
		return authors;
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
		
		if(pastIllnessProblems != null)
		{
			log.info("Size of Past Illness Problems " + pastIllnessProblems.size());
			for(int i = 0; i < pastIllnessProblems.size(); i++) {
				pastIllnessProblems.get(i).log();
			}
		}
		
		if(author != null)
			author.log();
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

	
	public CCDAAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CCDAAuthor author) {
		this.author = author;
	}

	public CCDAProblem()
	{
		problemConcerns = new ArrayList<CCDAProblemConcern>();
		sectionTemplateId = new ArrayList<CCDAII>();
		pastIllnessProblems = new ArrayList<CCDAProblemObs>();
	}
}
