package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.dto.enums.SeverityLevel;
import org.sitenv.contentvalidator.parsers.CCDAConstants;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDARefModel {
	
	private static Logger log = Logger.getLogger(CCDARefModel.class.getName());
	
	private CCDAPatient        patient;
	private CCDACareTeamMember members;
	private CCDACareTeamMember careTeamSectionMembers;
	private CCDACarePlanSections carePlanSections;
	private CCDAEncounter      encounter;
	private CCDAAdmissionDiagnosis admissionDiagnosis;
	private CCDADischargeDiagnosis dischargeDiagnosis;
	private CCDAAllergy        allergy;
	private CCDAMedication     medication;
	private CCDADischargeMedication dischargeMedication;
	private CCDAImmunization   immunization;
	private CCDALabResult      labResults;
	private CCDALabResult  	   labTests;
	private CCDAProcedure      procedure;
	private CCDASocialHistory  socialHistory;
	private CCDAVitalSigns     vitalSigns;
	private CCDAProblem        problem;
	private CCDAPlanOfTreatment planOfTreatment;
	private CCDAGoals          goals;
	private CCDAHealthConcerns hcs;
	private CCDAMedicalEquipment medEquipments;
	private ArrayList<CCDAUDI> udi;
	private CCDAHeaderElements header;
	private ArrayList<CCDAII>  ccdTemplates;
	private ArrayList<CCDAII>  dsTemplates;
	private ArrayList<CCDAII>  rnTemplates;
	private ArrayList<CCDAII>  cpTemplates;
	private SeverityLevel severityLevel;
	
	// Cures Update
	private ArrayList<CCDAAuthor> authors;
	
	// Cures Update changes for section level notes
	private ArrayList<CCDANotes> notes;
	private ArrayList<CCDANotesActivity> notesEntries;
	
	
	public ArrayList<CCDAAuthor> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<CCDAAuthor> author) {
		this.authors = author;
	}

	public ArrayList<CCDANotesActivity> getNotesEntries() {
		return notesEntries;
	}

	public void setNotesEntries(ArrayList<CCDANotesActivity> notesEntries) {
		this.notesEntries = notesEntries;
	}

	public ArrayList<CCDANotes> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<CCDANotes> notes) {
		this.notes = notes;
	}

	public CCDAHeaderElements getHeader() {
		return header;
	}

	public void setHeader(CCDAHeaderElements header) {
		this.header = header;
	}

	public void setSocialHistory(CCDASocialHistory socialHistory) {
		this.socialHistory = socialHistory;
	}

	public CCDACareTeamMember getCareTeamSectionMembers() {
		return careTeamSectionMembers;
	}

	public void setCareTeamSectionMembers(CCDACareTeamMember sectionMembers) {
		this.careTeamSectionMembers = sectionMembers;
	}

	public CCDARefModel() {
		this(SeverityLevel.INFO);		
	}
	
	public CCDARefModel(SeverityLevel severityLevel) {
		this.severityLevel = severityLevel;
		udi = new ArrayList<CCDAUDI>();
		notes = new ArrayList<CCDANotes>();
		notesEntries = new ArrayList<CCDANotesActivity>();
		authors = new ArrayList<CCDAAuthor>();
		
		ccdTemplates = new ArrayList<CCDAII>();
		ccdTemplates.add(new CCDAII(CCDAConstants.US_REALM_TEMPLATE, CCDAConstants.CCDA_2015_AUG_EXT));
		ccdTemplates.add(new CCDAII(CCDAConstants.CCD_TEMPLATE, CCDAConstants.CCDA_2015_AUG_EXT));
		
		dsTemplates = new ArrayList<CCDAII>();
		dsTemplates.add(new CCDAII(CCDAConstants.US_REALM_TEMPLATE, CCDAConstants.CCDA_2015_AUG_EXT));
		dsTemplates.add(new CCDAII(CCDAConstants.DS_TEMPLATE, CCDAConstants.CCDA_2015_AUG_EXT));

		rnTemplates = new ArrayList<CCDAII>();
		rnTemplates.add(new CCDAII(CCDAConstants.US_REALM_TEMPLATE, CCDAConstants.CCDA_2015_AUG_EXT));
		rnTemplates.add(new CCDAII(CCDAConstants.RN_TEMPLATE, CCDAConstants.CCDA_2015_AUG_EXT));

		cpTemplates = new ArrayList<CCDAII>();
		cpTemplates.add(new CCDAII(CCDAConstants.US_REALM_TEMPLATE, CCDAConstants.CCDA_2015_AUG_EXT));
		cpTemplates.add(new CCDAII(CCDAConstants.CP_TEMPLATE, CCDAConstants.CCDA_2015_AUG_EXT));
	}
	
	public ArrayList<ContentValidationResult> compare(String validationObjective, CCDARefModel submittedCCDA, boolean curesUpdate) {
		
		ArrayList<ContentValidationResult> results = new ArrayList<ContentValidationResult>();
		
		if(doesObjectiveRequireCCDS(validationObjective))
		{
			log.info(" Performing CCDS checks ");
			compareCCDS(validationObjective, submittedCCDA, results, curesUpdate);
		}
		else if(doesObjectiveRequireCIRI(validationObjective))
		{
			log.info(" Performing CIRI checks ");
			performCIRIValidation(validationObjective, submittedCCDA, results, curesUpdate);
		}
		else if(doesObjectiveRequireCarePlan(validationObjective))
		{
			log.info(" Performing Care Plan checks ");
			performCarePlanValidation(validationObjective, submittedCCDA, results, curesUpdate);
		}
		else if(doesObjectiveRequireDS4P(validationObjective))
		{
			log.info(" Performing DS4P checks ");
			performDS4PValidation(validationObjective, submittedCCDA, results, curesUpdate);
		}
		else 
		{
			log.info(" Not Performing any content validation checks ");
		}
		
		log.info(" Compare non CCDS Structured Data ");
		compareNonCCDSStructuredData(validationObjective, submittedCCDA, results, curesUpdate);
		
		validateDocElements(validationObjective,submittedCCDA, results, curesUpdate);
		
		log.info(" Total Number of Content Validation Issues " + results.size());
		return results;
	}
	
	public void validateDocElements(String valObj, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) 
	{
		if(valObj.equalsIgnoreCase("170.315_b1_ToC_Amb") ||
				valObj.equalsIgnoreCase("170.315_b4_CCDS_Amb") ) 
				 
		{
			// Validate it is one of CCD/RN
			String elementName = "Clinical Document Header ";
			ArrayList<ContentValidationResult> ccdResults = new ArrayList<ContentValidationResult>();
			ArrayList<ContentValidationResult> rnResults = new ArrayList<ContentValidationResult>();
			
			ParserUtilities.compareTemplateIds(ccdTemplates, submittedCCDA.getHeader().getDocTemplates(), ccdResults, elementName);
			ParserUtilities.compareTemplateIds(rnTemplates, submittedCCDA.getHeader().getDocTemplates(), rnResults, elementName);	
			
			if( (ccdResults.size() == 0) ||
				(rnResults.size() == 0) )
			{
				// Doc Type requirement is met.
				return;
			}
			else 
			{
				//Add the Errors to Result.
				ContentValidationResult rs = new ContentValidationResult("The scenario requires the submitted document type to be either a Continuity of Care Document, or a Referral Note, but the submitted C-CDA does not contain the relevant template Ids.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info(" Scenario requires correct document type, but submitted CCDA does not have the right document type.");
			}
		}
		else if(valObj.equalsIgnoreCase("170.315_b1_ToC_Inp") ||
				valObj.equalsIgnoreCase("170.315_b4_CCDS_Inp") )
		{
			// Validate it is one of CCD/RN/DS
			String elementName = "Clinical Document Header ";
			ArrayList<ContentValidationResult> ccdResults = new ArrayList<ContentValidationResult>();
			ArrayList<ContentValidationResult> rnResults = new ArrayList<ContentValidationResult>();
			ArrayList<ContentValidationResult> dsResults = new ArrayList<ContentValidationResult>();

			ParserUtilities.compareTemplateIds(ccdTemplates, submittedCCDA.getHeader().getDocTemplates(), ccdResults, elementName);
			ParserUtilities.compareTemplateIds(rnTemplates, submittedCCDA.getHeader().getDocTemplates(), rnResults, elementName);	
			ParserUtilities.compareTemplateIds(dsTemplates, submittedCCDA.getHeader().getDocTemplates(), dsResults, elementName);

			if( (ccdResults.size() == 0) ||
					(rnResults.size() == 0) ||
					(dsResults.size() == 0))
			{
				// Doc Type requirement is met.
				return;
			}
			else 
			{
				//Add the Errors to Result.
				ContentValidationResult rs = new ContentValidationResult("The scenario requires the submitted document type to be either a Continuity of Care Document, or a Referral Note or a Discharge Summary, but the submitted C-CDA does not contain the relevant template Ids.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info(" Scenario requires correct document type, but submitted CCDA does not have the right document type.");
			}
		}
		else if(valObj.equalsIgnoreCase("170.315_b2_CIRI_Amb") || 
				valObj.equalsIgnoreCase("170.315_b2_CIRI_Inp") ||				
				valObj.equalsIgnoreCase("170.315_b6_DE_Amb") ||
				valObj.equalsIgnoreCase("170.315_b6_DE_Inp") ||
			//	valObj.equalsIgnoreCase("170.315_b7_DS4P_Amb") || 
			//	valObj.equalsIgnoreCase("170.315_b7_DS4P_Inp") ||
			//	valObj.equalsIgnoreCase("170.315_b8_DS4P_Amb") || 
			//	valObj.equalsIgnoreCase("170.315_b8_DS4P_Inp") ||
				valObj.equalsIgnoreCase("170.315_e1_VDT_Amb") ||
				valObj.equalsIgnoreCase("170.315_e1_VDT_Inp") )
			//	valObj.equalsIgnoreCase("170.315_g9_APIAccess_Amb") || 
			//	valObj.equalsIgnoreCase("170.315_g9_APIAccess_Inp") )
		{
			// Validate for CCD
			String elementName = "Clinical Document Header ";
			ArrayList<ContentValidationResult> ccdResults = new ArrayList<ContentValidationResult>();
			
			ParserUtilities.compareTemplateIds(ccdTemplates, submittedCCDA.getHeader().getDocTemplates(), ccdResults, elementName);	
			
			if( (ccdResults.size() == 0))
			{
				// Doc Type requirement is met.
				return;
			}
			else 
			{
				//Add the Errors to Result.
				ContentValidationResult rs = new ContentValidationResult("The scenario requires the submitted document type to be a Continuity of Care Document, but the submitted C-CDA does not contain the relevant template Ids.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info(" Scenario requires correct document type, but submitted CCDA does not have the right document type.");
			}
		}
		else if(valObj.equalsIgnoreCase("170.315_b9_CP_Amb") || 
				valObj.equalsIgnoreCase("170.315_b9_CP_Inp") )
		{
			// Validate for CP
			String elementName = "Clinical Document Header ";
			ArrayList<ContentValidationResult> cpResults = new ArrayList<ContentValidationResult>();
			
			ParserUtilities.compareTemplateIds(cpTemplates, submittedCCDA.getHeader().getDocTemplates(), cpResults, elementName);	
			
			if( (cpResults.size() == 0))
			{
				// Doc Type requirement is met.
				return;
			}
			else 
			{
				//Add the Errors to Result.
				ContentValidationResult rs = new ContentValidationResult("The scenario requires the submitted document type to be a Care Plan Document, but the submitted C-CDA does not contain the relevant template Ids.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info(" Scenario requires correct document type, but submitted CCDA does not have the right document type.");
			}
		}
		else
		{
			return;
		}
	}
	
	public void compareCCDS(String validationObjective, CCDARefModel submittedCCDA,ArrayList<ContentValidationResult> results, boolean curesUpdate) 
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Social History Smoking Status ");
		validateSmokingStatus(submittedCCDA, results, curesUpdate);
		
		log.info("Validating Social History Birth Sex ");
		validateBirthSex(submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Problems ");
		compareProblems(validationObjective, submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Allergies ");
		compareAllergies(validationObjective, submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Medications ");
		compareMedications(validationObjective, submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Lab Results "); 
		compareLabResults(validationObjective, submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Vital Signs ");
		compareVitalObs(validationObjective, submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Procedures ");
		compareProcedures(validationObjective, submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Udis ");
		compareUdis(validationObjective, submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Immunizations ");
		compareImmunizations(validationObjective, submittedCCDA, results, curesUpdate);
		
		
		if(curesUpdate) {
			log.info(" Comparing Notes ");
			compareNotesActivities(validationObjective, submittedCCDA, results, curesUpdate);
			
			log.info(" Comparing Author ");
			compareAuthorEntries(validationObjective, submittedCCDA, results, curesUpdate);
			
			log.info(" Comparing Care Team ");
			compareCareTeamMembers(validationObjective, submittedCCDA, results, curesUpdate);
		}

		
		log.info("Finished comparison , returning results");
		
	}
	
	private void compareProblems(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		if((this.getProblem() != null) && (submittedCCDA.getProblem() != null) ) {
			log.info("Start Problem Comparison ");
			this.problem.compare(submittedCCDA.getProblem(), results);
		}
		else if ( (this.getProblem() != null) && (submittedCCDA.getProblem() == null) ) 
		{
			// handle the case where the problem section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's problems, but the submitted C-CDA does not contain problem data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires problems but submitted document does not contain problems section");
		}
		else if ( (this.getProblem() == null) && (submittedCCDA.getProblem() != null) ){
			
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's problems, but the submitted C-CDA does contain problem data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have problems for comparison ");
		}
		else {
			
			log.info("Model and Submitted CCDA do not have problems for comparison ");
		}
		
	}
	
	private void compareAllergies(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		if((this.getAllergy() != null) && (submittedCCDA.getAllergy() != null) ) {
			log.info("Start Allergy Comparison ");
			this.allergy.compare(submittedCCDA.getAllergy(), results, submittedCCDA);
		}
		else if ( (this.getAllergy() != null) && (submittedCCDA.getAllergy() == null) ) 
		{
			// handle the case where the allergy section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's allergies, but the submitted C-CDA does not contain allergy data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires allergies but submitted document does not contain allergies section");
		}
		else if ( (this.getAllergy() == null) && (submittedCCDA.getAllergy() != null) ){
			
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's allergies, but the submitted C-CDA does contain allergy data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have allergies for comparison ");
		}
		else {
			
			log.info("Model and Submitted CCDA do not have allergies for comparison ");
		}
		
	}
	
	public HashMap<String, CCDAMedicationActivity> getAllMedActivities() {
		
		HashMap<String,CCDAMedicationActivity> activities = new HashMap<String,CCDAMedicationActivity>();
		
		if(medication != null) {
			
			/*
			for(Map.Entry<String, CCDAMedicationActivity> ent: medication.getMedActivitiesMap().entrySet()) {
				log.info("Adding " + ent.getKey());
				activities.put(ent.getKey(), ent.getValue());		
			}*/
			
			activities.putAll(medication.getMedActivitiesMap());
			log.info(" Activities Size = " + activities.size());
		}
		
		if(dischargeMedication != null) {
			
			/*
			for(Map.Entry<String, CCDAMedicationActivity> ent: dischargeMedication.getMedActivitiesMap().entrySet()) {
				log.info("Adding " + ent.getKey());
				activities.put(ent.getKey(), ent.getValue());			
			}*/
			
			activities.putAll(dischargeMedication.getMedActivitiesMap());
			log.info("Activities Size = " + activities.size());
		}
		
		log.info("Final Med Activities Size = " + activities.size());
		return activities;
	}
	
	public HashMap<String, CCDAImmunizationActivity> getAllImmunizations() {
		
		HashMap<String,CCDAImmunizationActivity> activities = new HashMap<String,CCDAImmunizationActivity>();
		
		if(immunization != null) {
			
			activities.putAll(immunization.getImmunizationActivitiesMap());
			log.info(" Activities Size = " + activities.size());
		}
		
		return activities;
	}
	
	public void compareMedications(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Medication Activities for comparison ");
		HashMap<String, CCDAMedicationActivity> refActivities = this.getAllMedActivities();
		HashMap<String, CCDAMedicationActivity> subActivities = submittedCCDA.getAllMedActivities();
		
		if( (refActivities != null && refActivities.size() > 0) &&  
			(subActivities != null && subActivities.size() > 0)  ) {
			
			log.info("Medication Activities in both models ");
			CCDAMedicationActivity.compareMedicationActivityData(refActivities, subActivities, results);
			
		} else if ( (refActivities != null && refActivities.size() > 0) && 
				(subActivities == null || subActivities.size() == 0) ) {
			
			// handle the case where the allergy section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's medications, but the submitted C-CDA does not contain medication data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires medications but submitted document does not contain medication data");
			
		}else if ((refActivities == null || refActivities.size() == 0) && 
				(subActivities != null && subActivities.size() > 0) ) {
		
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's medications, but the submitted C-CDA does contain medication data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have medications for comparison ");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have medications for comparison ");
		}
	}
	
	public void compareImmunizations(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Immunization Activities for comparison ");
		HashMap<String, CCDAImmunizationActivity> refActivities = this.getAllImmunizations();
		HashMap<String, CCDAImmunizationActivity> subActivities = submittedCCDA.getAllImmunizations();
		
		if( (refActivities != null && refActivities.size() > 0) &&  
			(subActivities != null && subActivities.size() > 0)  ) {
			
			log.info("Immunization Activities in both models ");
			CCDAImmunizationActivity.compareImmunizationActivityData(refActivities, subActivities, results);
			
		} else if ( (refActivities != null && refActivities.size() > 0) && 
				(subActivities == null || subActivities.size() == 0) ) {
			
			// handle the case where the allergy section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's immunizations, but the submitted C-CDA does not contain immunization data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires immunizations but submitted document does not contain immunization data");
			
		}else if ((refActivities == null || refActivities.size() == 0) && 
				(subActivities != null && subActivities.size() > 0) ) {
		
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's immunizations, but the submitted C-CDA does contain immunization data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have immunization for comparison ");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have immunization for comparison ");
		}
	}
	
	
	public void compareLabResults(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Lab Results for comparison ");
		HashMap<String, CCDALabResultObs> refResults = this.getAllLabResultObs();
		HashMap<String, CCDALabResultObs> subResults = submittedCCDA.getAllLabResultObs();
		
		if( (refResults != null && refResults.size() > 0) &&  
			(subResults != null && subResults.size() > 0)  ) {
			
			log.info("Lab Results present in both models ");
			CCDALabResultObs.compareLabResultData(refResults, subResults, results);
			
		} else if ( (refResults != null && refResults.size() > 0) && 
				(subResults == null || subResults.size() == 0) ) {
			
			// handle the case where the allergy section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's lab results, but the submitted C-CDA does not contain lab result data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires lab results but submitted document does not contain lab results data");
			
		}else if ((refResults == null || refResults.size() == 0) && 
				(subResults != null && subResults.size() > 0) ) {
		
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's lab results, but the submitted C-CDA does contain lab result data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have lab results for comparison ");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have lab results for comparison ");
		}
	}
	
	public void compareVitalObs(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Vital Observations for comparison ");
		HashMap<String, CCDAVitalObs> refVitals = this.getAllVitalObs();
		HashMap<String, CCDAVitalObs> subVitals = submittedCCDA.getAllVitalObs();
		
		if( (refVitals != null && refVitals.size() > 0) &&  
			(subVitals != null && subVitals.size() > 0)  ) {
			
			log.info("Vital Signs present in both models ");
			CCDAVitalObs.compareVitalObsData(refVitals, subVitals, results);
			
		} else if ( (refVitals != null && refVitals.size() > 0) && 
				(subVitals == null || subVitals.size() == 0) ) {
			
			// handle the case where the Vitals Section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's Vital Signs, but the submitted C-CDA does not contain Vital Signs data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Vital Signs but submitted document does not contain Vital Signs data");
			
		}else if ((refVitals == null || refVitals.size() == 0) && 
				(subVitals != null && subVitals.size() > 0) ) {
		
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's Vital Signs, but the submitted C-CDA does contain Vital Sign data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have Vital Signs for comparison ");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have Vital Signs for comparison ");
		}
	}
	
	public void compareUdis(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Udis for comparison ");
		ArrayList<CCDAUDI> refUdis = this.getAllUDIs();
		ArrayList<CCDAUDI> subUdis = submittedCCDA.getAllUDIs();
		
		if( (refUdis != null && refUdis.size() > 0) &&  
			(subUdis != null && subUdis.size() > 0)  ) {
			
			log.info("Udis present in both models ");
			CCDAUDI.compareUdis(refUdis, subUdis, results);
			
		} else if ( (refUdis != null && refUdis.size() > 0) && 
				(subUdis == null || subUdis.size() == 0) ) {
			
			// handle the case where the Udisdoes not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's UDIs, but the submitted C-CDA does not contain UDI data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires UDIs but submitted document does not contain UDI data");
			
		}else if ((refUdis == null || refUdis.size() == 0) && 
				(subUdis != null && subUdis.size() > 0) ) {
		
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's Udis, but the submitted C-CDA does contain UDI data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have UDIs for comparison ");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have UDIs for comparison ");
		}
	}
	
	public void compareProcedures(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Procedure Acts for comparison ");
		HashMap<String, CCDAProcActProc> refProcs = this.getAllProcedures();
		HashMap<String, CCDAProcActProc> subProcs = submittedCCDA.getAllProcedures();
		
		if( (refProcs != null && refProcs.size() > 0) &&  
			(subProcs != null && subProcs.size() > 0)  ) {
			
			log.info("Procedures present in both models ");
			CCDAProcActProc.compareProcedures(refProcs, subProcs, results);
			
		} else if ( (refProcs != null && refProcs.size() > 0) && 
				(subProcs == null || subProcs.size() == 0) ) {
			
			// handle the case where the Vitals Section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's Procedures, but the submitted C-CDA does not contain Procedure data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Procedures but submitted document does not contain Procedures data");
			
		}else if ((refProcs == null || refProcs.size() == 0) && 
				(subProcs != null && subProcs.size() > 0) ) {
		
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's Procedures, but the submitted C-CDA does contain Procedure data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have Procedures for comparison ");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have Procedures for comparison ");
		}
	}
	
	public void compareNotes(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Notes Section for comparison ");
		HashMap<String, CCDANotes> refNotes = this.getAllNotes();
		HashMap<String, CCDANotes> subNotes= submittedCCDA.getAllNotes();
		
		if( (refNotes != null && refNotes.size() > 0) &&  
			(subNotes != null && subNotes.size() > 0)  ) {
			
			log.info("Notes present in both models ");
			CCDANotes.compareNotes(refNotes, subNotes, results);
			
		} 	
		else if ( (refNotes != null && refNotes.size() > 0) && 
				(subNotes == null || subNotes.size() == 0) ) {
			
			// handle the case where the Notes section does not exist in the submitted CCDA
			// ref has Notes but sub does not
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's Notes, "
					+ "but the submitted C-CDA does not contain Notes data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Notes data, but submitted document does not contain Notes data");
			
		}else if ((refNotes == null || refNotes.size() == 0) && 
				(subNotes != null && subNotes.size() > 0) ) {
			
			// ref doesn't have notes but sub does also results in an error
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's Notes, "
					+ "but the submitted C-CDA does contain Notes data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have Notes for comparison ");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have Notes for comparison ");
		}
	}
	
	public void compareCareTeamMembers(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Care Team Section for comparison ");
		HashMap<String, CCDACareTeamMemberAct> refCtm = this.getAllCareTeamMembers();
		HashMap<String, CCDACareTeamMemberAct> subCtm = submittedCCDA.getAllCareTeamMembers();
		
		if( (refCtm != null && refCtm.size() > 0) &&  
			(subCtm != null && subCtm.size() > 0)  ) {
			
			log.info("Notes present in both models ");
			CCDACareTeamMemberAct.compareMembers(refCtm, subCtm, results);
			
		} 	
		else if ( (refCtm != null && refCtm.size() > 0) && 
				(subCtm == null || subCtm.size() == 0) ) {
			
			// handle the case where the Notes section does not exist in the submitted CCDA
			// ref has Notes but sub does not
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to the patient's care Team Members "
					+ "but the submitted C-CDA does not contain Care Team Member data.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Care Team Member data, but submitted document does not contain Care Team Member data");
			
		}else if ((refCtm == null || refCtm.size() == 0) && 
				(subCtm != null && subCtm.size() > 0) ) {
			
			
			log.info("Model does not have Care Team Members for comparison, allow this to pass");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have Care Team Members for comparison ");
		}
	}
	
	public void compareNotesActivities(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Notes Section for comparison ");
		
		HashMap<String, CCDANotesActivity> refNotesActs = this.getAllNotesActivities();
		HashMap<String, CCDANotesActivity> subNotesActs= submittedCCDA.getAllNotesActivities();
		
		if( (refNotesActs != null && refNotesActs.size() > 0) &&  
			(subNotesActs != null && subNotesActs.size() > 0)  ) {
			
			log.info("Notes present in both models ");
			CCDANotesActivity.compareNotesActivities(refNotesActs, subNotesActs, results);
			
		} 	
		else if ( (refNotesActs != null && refNotesActs.size() > 0) && 
				(subNotesActs == null || subNotesActs.size() == 0) ) {
			
			// handle the case where the Notes section does not exist in the submitted CCDA
			
			for(Map.Entry<String, CCDANotesActivity> entries : refNotesActs.entrySet() ) {
				
				
				String errorMsg = "The scenario requires data related to patient's Notes for " + entries.getKey() 
								+ " , but the submitted C-CDA does not contain corresponding clinical Notes data.";
				
				ContentValidationResult rs = new ContentValidationResult(errorMsg, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info(" Scenario requires Notes data, but submitted document does not contain Notes data");
				
			}
			
			
			
		}else if ((refNotesActs == null || refNotesActs.size() == 0) && 
				(subNotesActs != null && subNotesActs.size() > 0) ) {
		
			log.info("Model does not have Notes for comparison, it is ok for submitted CCDA to include notes for all occasions");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have Notes for comparison ");
		}
	}
	
	private HashMap<String, CCDANotes> getAllNotes() 
	{
		HashMap<String,CCDANotes> results = new HashMap<String,CCDANotes>();
		
		if(notes != null) {
			
			for(CCDANotes note : notes) {
				results.put(note.getSectionCode().getCode(), note);
			}
		}
		
		return results;
	}
	
	private HashMap<String, CCDANotesActivity> getAllNotesActivities() 
	{
		HashMap<String,CCDANotesActivity> results = new HashMap<String,CCDANotesActivity>();
		
		ParserUtilities.populateNotesActiviteis(notesEntries, results);
		
		
		log.info(" Notes Activities Size = " + results.size());
		return results;
	}
	
	private HashMap<String, CCDACareTeamMemberAct> getAllCareTeamMembers() 
	{
		if(careTeamSectionMembers != null) {
			
			return careTeamSectionMembers.getAllCareTeamMembers();
		}
		
		return null;
	}
	
	public void compareAuthorEntries(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Author Entries for comparison ");
		
		ArrayList<CCDAAuthor> refAuths = this.getAuthors();
		ArrayList<CCDAAuthor> subAuths = submittedCCDA.getAuthors();
		
		if( (refAuths != null && refAuths.size() > 0) &&  
			(subAuths != null && subAuths.size() > 0)  ) {
			
			log.info("Authors present in both models ");
			String elName = "Document Level";
			CCDAAuthor.compareAuthors(refAuths, subAuths, results, elName);
			
		} 	
		else if ( (refAuths != null && refAuths.size() > 0) && 
				(subAuths == null || subAuths.size() == 0) ) {
			
			// handle the case where the Notes section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to Author (Provenance), but the submitted C-CDA does not contain Author data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Author data, but submitted document does not contain Author data");
			
		}else if ((refAuths == null || refAuths.size() == 0) && 
				(subAuths != null && subAuths.size() > 0) ) {
		
			log.info("Model does not have Authors for comparison, it is OK for submitted CCDA to include Authors for all occasions");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have Authors for comparison ");
		}
		
		compareSectionAndEntryLevelProvenance(validationObjective, submittedCCDA, results, curesUpdate);
	}
	
	public void compareSectionAndEntryLevelProvenance(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		if (allergy != null)
			allergy.compareAuthor(submittedCCDA.getAllergy() != null ? submittedCCDA.getAllergy() : null, 
					results, curesUpdate);
		if (problem != null)
			problem.compareAuthor(submittedCCDA.getProblem() != null ? submittedCCDA.getProblem() : null, 
					results, curesUpdate);
		if (procedure != null)
			procedure.compareAuthor(submittedCCDA.getProcedure() != null ? submittedCCDA.getProcedure() : null, 
					results, curesUpdate); // TODO-db: Finish if required: PAP/UDI, PAP/Notes, PAAct?, PAObs?
		if (medication != null)
			medication.compareAuthor(submittedCCDA.getMedication() != null ? submittedCCDA.getMedication() : null,
					results, curesUpdate); // TODO-db: Look at parser, may be more authors to collect
		if (immunization != null)
			immunization.compareAuthor(submittedCCDA.getImmunization() != null ? submittedCCDA.getImmunization() : null,
					results, curesUpdate);
		if (labResults != null)
			labResults.compareAuthor(submittedCCDA.getLabResults() != null ? submittedCCDA.getLabResults() : null,
					results, curesUpdate);
		if (vitalSigns != null)
			vitalSigns.compareAuthor(submittedCCDA.getVitalSigns() != null ? submittedCCDA.getVitalSigns() : null,
					results, curesUpdate);
		if (encounter != null)
			encounter.compareAuthor(submittedCCDA.getEncounter() != null ? submittedCCDA.getEncounter() : null, 
					results, curesUpdate); // TODO-db: Consider adding remaining items in EncounterParser (multiple problem observations, see retrieveAdmissionDiagnosisDetails and below)		
	}
	
	private HashMap<String, CCDALabResultObs> getAllLabResultObs() 
	{
		HashMap<String,CCDALabResultObs> results = new HashMap<String,CCDALabResultObs>();
		
		if(labResults != null) {
			
			/*
			for(Map.Entry<String, CCDALabResultObs> ent: labResults.getLabResultsMap().entrySet()) {
				log.info("Adding " + ent.getKey());
				results.put(ent.getKey(), ent.getValue());			
			}*/
			results.putAll(labResults.getLabResultsMap());
			log.info(" Results Size = " + results.size());
		}
		
		return results;
	}
	
	private HashMap<String, CCDAVitalObs> getAllVitalObs() 
	{
		HashMap<String,CCDAVitalObs> results = new HashMap<String,CCDAVitalObs>();
		
		if(vitalSigns != null) {
			
			results.putAll(vitalSigns.getVitalObsMap());
			log.info(" Vitals Size = " + results.size());
		}
		
		return results;
	}
	
	private HashMap<String, CCDASmokingStatus> getAllSmokingStatuses() 
	{
		HashMap<String,CCDASmokingStatus> results = new HashMap<String,CCDASmokingStatus>();
		
		if(socialHistory != null && 
		   socialHistory.getSmokingStatus() != null) {
			
			ArrayList<CCDASmokingStatus> ss = socialHistory.getSmokingStatus();
			
			for(CCDASmokingStatus stat : ss) {
				
				if(stat.getSmokingStatusCode() != null &&
				   stat.getSmokingStatusCode().getCode() != null &&
				   stat.getSmokingStatusCode().getCode().length() > 0 ) {
					
				   // Adding Smoking Status code.
				   log.info(" Adding Smoking Status for " + stat.getSmokingStatusCode().getCode());
				   results.put(stat.getSmokingStatusCode().getCode(), stat);
				}
						
			}
		
		}
		log.info(" Smoking Status Results Size = " + results.size());
		return results;
	}
	
	private HashMap<String, CCDAProcActProc> getAllProcedures() 
	{
		HashMap<String,CCDAProcActProc> results = new HashMap<String,CCDAProcActProc>();
		
		if(procedure != null) {
			
			results.putAll(procedure.getProcedureMap());
			log.info(" Procedure Size = " + results.size());
		}
		
		return results;
	}
	
	private ArrayList<CCDAUDI> getAllUDIs() 
	{
		ArrayList<CCDAUDI> udis = new ArrayList<CCDAUDI>();
		
		if(medEquipments != null) {
			log.info(" Retrieving UDIs from Med Eq ");
			ArrayList<CCDAUDI> mudi = medEquipments.getUdis();
			
			if(mudi != null) {
				
				log.info(" Size of Udi in Med Eq = " + mudi.size());
				udis.addAll(mudi);
			}
		}
		
		if(this.udi != null) {
			log.info(" Size of Udi in Ref Model = " + udi.size());
			udis.addAll(udi);
		}
		
		if(procedure != null) {
			log.info(" Retrieving UDIs from Procedures ");
			ArrayList<CCDAUDI> pudi = procedure.getAllUdis();
			
			if(pudi != null) {
				
				log.info(" Size of Udi in Procedures = " + pudi.size());
				udis.addAll(pudi);
			}
		}
		
		log.info(" Total Size of UDIs " + udis.size());
				
		return udis;
	}
	
	private void validateBirthSex(CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		if( (submittedCCDA.getSocialHistory() != null) &&
			 (submittedCCDA.getSocialHistory().getBirthSex() != null)) {
			
			// Validate that the code is M or F.
			if( (submittedCCDA.getSocialHistory().getBirthSex().getSexCode() != null && 
				 submittedCCDA.getSocialHistory().getBirthSex().getSexCode().getCode() != null) && 
					( (submittedCCDA.getSocialHistory().getBirthSex().getSexCode().getCode().equalsIgnoreCase("M") ) || 
					  (submittedCCDA.getSocialHistory().getBirthSex().getSexCode().getCode().equalsIgnoreCase("F") ) ) )
			{
				//do nothing.
				return;
			}
			else {
				ContentValidationResult rs = new ContentValidationResult(
						"The scenario requires patient's birth sex to use the codes M or F but the submitted C-CDA does not contain either of these codes.",
						ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
				results.add(rs);
			}
			
		}
		else {
			// Sub missing birth sex returns an ERROR for curesUpdate or a WARNING for non-cures (2015) as per regulation https://www.healthit.gov/isa/uscdi-data/birth-sex
			// Note: Even though the birthSexMessage implies birth sex is required because it is in the scenario, we require it regardless of it being there or not - 
			// this source code purposely does not even reference the scenario, only the submitted file.
			final String birthSexMessage = "The scenario requires patient's birth sex to be captured as part of social history data, "
					+ "but submitted file does not have birth sex information";
			if (curesUpdate) {
				ContentValidationResult rs = new ContentValidationResult(birthSexMessage,
						ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
				results.add(rs);
			} else {
				if (submittedCCDA.warningsPermitted()) {
					ContentValidationResult rs = new ContentValidationResult(birthSexMessage,
							ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0");
					results.add(rs);
				} else {
					log.info(
							"Skipping non-cures 'SocialHistory BirthSex code' check in CCDARefModel.validateBirthSex due to severityLevel: "
									+ submittedCCDA.getSeverityLevelName());
				}
			}
		}
	}
	
	private void validateSmokingStatus(CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		log.info("Retrieving Smoking Status for comparison ");
		HashMap<String, CCDASmokingStatus> refStatus = this.getAllSmokingStatuses();
		HashMap<String, CCDASmokingStatus> subStatus = submittedCCDA.getAllSmokingStatuses();
		
		if( (refStatus != null && refStatus.size() > 0) &&  
			(subStatus != null && subStatus.size() > 0) ) {
			
			log.info("Smoking Status present in both models ");
			CCDASmokingStatus.compareSmokingStatus(refStatus, subStatus, results);
			
		} else if ( (refStatus != null && refStatus.size() > 0) && 
				    (subStatus == null || subStatus.size() == 0) ) {
			
			// handle the case where the Vitals Section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult(
					"The scenario requires data related to patient's Smoking Status, "
					+ "but the submitted C-CDA does not contain Smoking Status data.",
					ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
			results.add(rs);
			log.info(" Scenario requires Smoking Status but submitted document does not contain Smoking Status data");
			
		} else if ( (refStatus == null || refStatus.size() == 0) && 
				    (subStatus != null && subStatus.size() > 0) ) {
			
			log.info("Model does not have Smoking Status for comparison ");			
			
			// Handle exception for SITE-3220 ETT GG, Content Val: "Smoking Status for b(1) / g(6)"
			boolean isError = false;
			for (CCDASmokingStatus ssVal : subStatus.values()) {
				// Note: Even though we are calling getSmokingStatusCode, it is actually populated by the C-CDA Value element in the parser, 
				// and the Value is what we want in this case, and is of type CD
				if (ssVal != null && ssVal.getSmokingStatusCode() != null
						&& ssVal.getSmokingStatusCode().getCode() != null
						&& ssVal.getSmokingStatusCode().getCode().equals("266927001")) {
					log.info("Skipping error for sub containing Smoking Status data "
							+ "due to inclusion of SNOMED CT code 266927001, \"Unknown if ever smoked\"");
				} else {
					isError = true; // At least one Smoking Status value/@code did not equal the exception code
					break; // Since we only ever fire one error regardless of the count, it's OK to break out for efficiency
				}
			}
			if (isError) {
				ContentValidationResult rs = new ContentValidationResult(
						"The scenario does not require data related to patient's Smoking Status, "
						+ "but the submitted C-CDA does contain Smoking Status data.",
						ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
				results.add(rs);
			}			
			
		} else {			
			log.info("Model and Submitted CCDA do not have Smoking Statuss for comparison ");
		}
		
	}
	
	public HashMap<String, CCDAProblemObs> getAllEncounterDiagnoses(Boolean includeTrans) {
		
		HashMap<String,CCDAProblemObs> diagnoses = new HashMap<String,CCDAProblemObs>();
		
		if(encounter != null) {
			
			diagnoses.putAll(encounter.getAllDiagnosisObservations(includeTrans));
			
			log.info(" Encounter Diagnoses Size = " + diagnoses.size());
		}
		
		if(admissionDiagnosis != null) {
			
			log.info(" Encounter Diagnoses Size = " + diagnoses.size());
			
			for(CCDAProblemObs prob : admissionDiagnosis.getDiagnosis()) {
			
				if(prob.getProblemCode() != null && 
				   prob.getProblemCode().getCode() != null && 
				   prob.getProblemCode().getCode().length() > 0) {
			
					log.info(" Adding Problem Obs for Code " + prob.getProblemCode().getCode() );
					diagnoses.put(prob.getProblemCode().getCode(), prob);
				}
				else if(prob.getProblemCode() != null && 
						prob.getProblemCode().isProperNFForTranslation() ) {
					
					log.info(" Adding Problem Obs for Code " + prob.getProblemCode().getNullFlavor() );
					diagnoses.put(prob.getProblemCode().getNullFlavor(), prob);
				}
			
				
			} // for
			
			
		}// if Adm Diag
		
		if(dischargeDiagnosis != null) {
			
			log.info(" Encounter Diagnoses Size = " + diagnoses.size());
			
			for(CCDAProblemObs prob : dischargeDiagnosis.getDiagnosis()) {
			
				if(prob.getProblemCode() != null && 
				   prob.getProblemCode().getCode() != null && 
				   prob.getProblemCode().getCode().length() > 0) {
			
					log.info(" Adding Problem Obs for Code " + prob.getProblemCode().getCode() );
					diagnoses.put(prob.getProblemCode().getCode(), prob);
				}
				else if(prob.getProblemCode() != null && 
						prob.getProblemCode().isProperNFForTranslation() ) {
					
					log.info(" Adding Problem Obs for Code " + prob.getProblemCode().getNullFlavor() );
					diagnoses.put(prob.getProblemCode().getNullFlavor(), prob);
				}
			
				
			} // for
			
			
		}// if Discharge Diag
		
		
		log.info("Final Enc Diagnosis Size = " + diagnoses.size());
		return diagnoses;
	}
	
	private void compareEncounterDiagnosis(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {
		
		if(!validationObjective.equalsIgnoreCase("170.315_e1_VDT_Amb") &&
		   !validationObjective.equalsIgnoreCase("170.315_e1_VDT_Inp") ) {
			log.info("Retrieving Procedure Acts for comparison ");
			HashMap<String, CCDAProblemObs> refDiags = this.getAllEncounterDiagnoses(false);
			HashMap<String, CCDAProblemObs> subDiags = submittedCCDA.getAllEncounterDiagnoses(false);
			
			if( (refDiags != null && refDiags.size() > 0) &&  
					(subDiags != null && subDiags.size() > 0)  ) {
	
				log.info("Encounter Diagnosis present in both models ");
				
				String context = "Enounter Diagnosis Entry ";
				// Iterate and verify that all reference diagnosis is present in submitted diagnosis
				CCDAProblemObs.compareProblemObservationData(refDiags, subDiags, results, context);
	
	
			} else if ( (refDiags != null && refDiags.size() > 0) && 
					(subDiags == null || subDiags.size() == 0) ) {
	
				// handle the case where the Encounter Diagnossi Section does not exist in the submitted CCDA
				ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to Encounter Diagnosis, but the submitted C-CDA does not contain Encounter Diagnosis data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info(" Scenario requires Encounter Diagnosis but submitted document does not contain Encounter Diagnosis data");
	
			}else if ((refDiags == null || refDiags.size() == 0) && 
					(subDiags != null && subDiags.size() > 0) ) {
	
				ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to Encounter Diagnosis, but the submitted C-CDA does contain Encounter Diagnosis data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info("Model does not have Encounter Diagnosis for comparison ");
	
			} else {
	
				log.info("Model and Submitted CCDA do not have Encounter Diagnosis for comparison ");
			}
		}
	}
	
	
	private void comparePatients(CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results, boolean curesUpdate) {
		
		if((patient != null) && (submittedCCDA.getPatient() != null)) {
			this.patient.compare(submittedCCDA.getPatient(), results, submittedCCDA, curesUpdate);
		}
		else if( (patient == null) && (submittedCCDA.getPatient() != null) ) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require patient data, but submitted file does have patient data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if((patient != null) && (submittedCCDA.getPatient() == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires patient data, but submitted file does have patient data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("Both the Ref Model and the Submitted Patient Data are null ");
		}
	}
	
	private void compareCarePlanSections(CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {		
		if(carePlanSections != null && submittedCCDA.getCarePlanSections() != null) {
			this.carePlanSections.compare(submittedCCDA.getCarePlanSections(), results, submittedCCDA);
		} else {
			log.error("An unexpected programmatic error has occurred where either "
					+ "carePlanSections is null or submittedCCDA.getCarePlanSections() is null, "
					+ "when both should have been initialized by CarePlanSectionsParser");
		}
	}	

	public Boolean doesObjectiveRequireCCDS(String valObj) {
		
		if(valObj.equalsIgnoreCase("170.315_b1_ToC_Amb") || 
			valObj.equalsIgnoreCase("170.315_b1_ToC_Inp") ||
			valObj.equalsIgnoreCase("170.315_b4_CCDS_Amb") ||
			valObj.equalsIgnoreCase("170.315_b4_CCDS_Inp") ||
			valObj.equalsIgnoreCase("170.315_b6_DE_Amb") ||
			valObj.equalsIgnoreCase("170.315_b6_DE_Inp") ||
			valObj.equalsIgnoreCase("170.315_e1_VDT_Amb") ||
			valObj.equalsIgnoreCase("170.315_e1_VDT_Inp") ||
			valObj.equalsIgnoreCase("170.315_g9_APIAccess_Amb") || 
			valObj.equalsIgnoreCase("170.315_g9_APIAccess_Inp") )
				return true;
		else
			return false;
	}
	
	public Boolean doesObjectiveRequireCIRI(String valObj) {
		
		if(valObj.equalsIgnoreCase("170.315_b2_CIRI_Amb") || 
			valObj.equalsIgnoreCase("170.315_b2_CIRI_Inp") )
				return true;
		else
			return false;
	}
	
	public Boolean doesObjectiveRequireCarePlan(String valObj) {
		
		if(valObj.equalsIgnoreCase("170.315_b9_CP_Amb") || 
			valObj.equalsIgnoreCase("170.315_b9_CP_Inp") )
				return true;
		else
			return false;
	}
	
	public Boolean doesObjectiveRequireDS4P(String valObj) {
		
		if(valObj.equalsIgnoreCase("170.315_b7_DS4P_Amb") || 
			valObj.equalsIgnoreCase("170.315_b7_DS4P_Inp") )
				return true;
		else
			return false;
	}
	
	public void log() {
		
		if(patient != null)
			patient.log();
		else
			log.info(" No Patient Data in the model ");
		
		if(encounter != null)
			encounter.log();
		else
			log.info("No Encounter data in the model");
		
		if(problem != null)
			problem.log();
		else
			log.info("No Problem data in the model");
		
		if(medication != null)
			medication.log();
		else
			log.info("No Medication data in the model");
		
		if(dischargeMedication != null)
			dischargeMedication.log();
		else
			log.info("No Discharge Medication data in the model");
		
		if(allergy != null)
			allergy.log();
		else
			log.info("No Allergy data in the model");
		
		if(immunization != null)
			immunization.log();
		else
			log.info("No Immunization data in the model");
		
		if(labResults != null)
			labResults.log();
		else
			log.info("No Lab Results data in the model");
		
		if(labTests != null)
			labTests.log();
		else
			log.info("No Lab Tests data in the model");
		
		if(procedure != null)
			procedure.log();
		else
			log.info("No Procedure data in the model");
		
		if(socialHistory != null)
			socialHistory.log();
		else
			log.info("No Smoking Status in the model");
		
		if(vitalSigns != null)
			vitalSigns.log();
		else
			log.info("No Vital Signs data in the model");
		
		if(goals != null)
			goals.log();
		else
			log.info("No Goals data in the model");
		
		if(planOfTreatment != null)
			planOfTreatment.log();
		else
			log.info("No Plan of Treatment data in the model");
		
		
		if(hcs != null)
			hcs.log();
		else
			log.info("No Health Concerns data in the model");
		
		if(members != null)
			members.log();
		else
			log.info("No Care Team Members data in the model");
		
		if(carePlanSections != null)
			carePlanSections.log();
		else
			log.info("No Care Plan Sections data (" + CCDACarePlanSections.REQUIRED_SECTIONS + ")"
					+ " available");
		
		for(int j = 0; j < udi.size(); j++) {
			udi.get(j).log();
			
		}
		
		for(int k = 0; k < notes.size(); k++) {
			
			notes.get(k).log();
		}
		
		if(medEquipments != null)
			medEquipments.log();
		else 
			log.info(" Medical Equipment Data is null ");
			
		
		if(admissionDiagnosis != null) 
			admissionDiagnosis.log();
		
		if(dischargeDiagnosis != null)
			dischargeDiagnosis.log();
		
		if(header != null)
			header.log();
	}
	
	public CCDAAdmissionDiagnosis getAdmissionDiagnosis() {
		return admissionDiagnosis;
	}

	public void setAdmissionDiagnosis(CCDAAdmissionDiagnosis admissionDiagnosis) {
		this.admissionDiagnosis = admissionDiagnosis;
	}

	public CCDAPatient getPatient() {
		return patient;
	}
	public void setPatient(CCDAPatient patient) {
		this.patient = patient;
	}
	public CCDACareTeamMember getMembers() {
		return members;
	}
	public void setMembers(CCDACareTeamMember members) {
		this.members = members;
	}
	public CCDACarePlanSections getCarePlanSections() {
		return carePlanSections;
	}
	public void setCarePlanSections(CCDACarePlanSections carePlanSections) {
		this.carePlanSections = carePlanSections;
	}
	public CCDAEncounter getEncounter() {
		return encounter;
	}
	public void setEncounter(CCDAEncounter encounter) {
		this.encounter = encounter;
	}
	public CCDAAllergy getAllergy() {
		return allergy;
	}
	public void setAllergy(CCDAAllergy allergy) {
		this.allergy = allergy;
	}
	public CCDAMedication getMedication() {
		return medication;
	}
	public void setMedication(CCDAMedication medication) {
		this.medication = medication;
	}
	public CCDAImmunization getImmunization() {
		return immunization;
	}
	public void setImmunization(CCDAImmunization immunization) {
		this.immunization = immunization;
	}
	public CCDALabResult getLabResults() {
		return labResults;
	}
	public void setLabResults(CCDALabResult labResults) {
		this.labResults = labResults;
	}
	public CCDALabResult getLabTests() {
		return labTests;
	}
	public void setLabTests(CCDALabResult labTests) {
		this.labTests = labTests;
	}
	public CCDAProcedure getProcedure() {
		return procedure;
	}
	public void setProcedure(CCDAProcedure procedure) {
		this.procedure = procedure;
	}
	public CCDASocialHistory getSocialHistory() {
		return socialHistory;
	}
	public void setSmokingStatus(CCDASocialHistory sh) {
		this.socialHistory = sh;
	}
	public CCDAVitalSigns getVitalSigns() {
		return vitalSigns;
	}
	public void setVitalSigns(CCDAVitalSigns vitalSigns) {
		this.vitalSigns = vitalSigns;
	}
	public CCDAProblem getProblem() {
		return problem;
	}
	public void setProblem(CCDAProblem problem) {
		this.problem = problem;
	}
	public CCDAPlanOfTreatment getPlanOfTreatment() {
		return planOfTreatment;
	}
	public void setPlanOfTreatment(CCDAPlanOfTreatment planOfTreatment) {
		this.planOfTreatment = planOfTreatment;
	}
	public CCDAGoals getGoals() {
		return goals;
	}
	public void setGoals(CCDAGoals goals) {
		this.goals = goals;
	}
	public CCDAHealthConcerns getHcs() {
		return hcs;
	}
	public void setHcs(CCDAHealthConcerns hcs) {
		this.hcs = hcs;
	}
	public ArrayList<CCDAUDI> getUdi() {
		return udi;
	}
	public void setUdi(ArrayList<CCDAUDI> udis) {
		
		if(udis != null)
			this.udi = udis;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allergy == null) ? 0 : allergy.hashCode());
		result = prime * result
				+ ((encounter == null) ? 0 : encounter.hashCode());
		result = prime * result + ((goals == null) ? 0 : goals.hashCode());
		result = prime * result + ((hcs == null) ? 0 : hcs.hashCode());
		result = prime * result
				+ ((immunization == null) ? 0 : immunization.hashCode());
		result = prime * result
				+ ((labResults == null) ? 0 : labResults.hashCode());
		result = prime * result
				+ ((labTests == null) ? 0 : labTests.hashCode());
		result = prime * result
				+ ((medication == null) ? 0 : medication.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		result = prime * result
				+ ((planOfTreatment == null) ? 0 : planOfTreatment.hashCode());
		result = prime * result + ((problem == null) ? 0 : problem.hashCode());
		result = prime * result
				+ ((procedure == null) ? 0 : procedure.hashCode());
		result = prime * result
				+ ((socialHistory == null) ? 0 : socialHistory.hashCode());
		result = prime * result + ((udi == null) ? 0 : udi.hashCode());
		result = prime * result
				+ ((vitalSigns == null) ? 0 : vitalSigns.hashCode());
		result = prime * result
				+ ((carePlanSections == null) ? 0 : carePlanSections.hashCode()); 
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
		CCDARefModel other = (CCDARefModel) obj;
		if (allergy == null) {
			if (other.allergy != null)
				return false;
		} else if (!allergy.equals(other.allergy))
			return false;
		if (encounter == null) {
			if (other.encounter != null)
				return false;
		} else if (!encounter.equals(other.encounter))
			return false;
		if (goals == null) {
			if (other.goals != null)
				return false;
		} else if (!goals.equals(other.goals))
			return false;
		if (hcs == null) {
			if (other.hcs != null)
				return false;
		} else if (!hcs.equals(other.hcs))
			return false;
		if (immunization == null) {
			if (other.immunization != null)
				return false;
		} else if (!immunization.equals(other.immunization))
			return false;
		if (labResults == null) {
			if (other.labResults != null)
				return false;
		} else if (!labResults.equals(other.labResults))
			return false;
		if (labTests == null) {
			if (other.labTests != null)
				return false;
		} else if (!labTests.equals(other.labTests))
			return false;
		if (medication == null) {
			if (other.medication != null)
				return false;
		} else if (!medication.equals(other.medication))
			return false;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (planOfTreatment == null) {
			if (other.planOfTreatment != null)
				return false;
		} else if (!planOfTreatment.equals(other.planOfTreatment))
			return false;
		if (problem == null) {
			if (other.problem != null)
				return false;
		} else if (!problem.equals(other.problem))
			return false;
		if (procedure == null) {
			if (other.procedure != null)
				return false;
		} else if (!procedure.equals(other.procedure))
			return false;
		if (socialHistory == null) {
			if (other.socialHistory != null)
				return false;
		} else if (!socialHistory.equals(other.socialHistory))
			return false;
		if (udi == null) {
			if (other.udi != null)
				return false;
		} else if (!udi.equals(other.udi))
			return false;
		if (vitalSigns == null) {
			if (other.vitalSigns != null)
				return false;
		} else if (!vitalSigns.equals(other.vitalSigns))
			return false;
		if (carePlanSections == null) {
			if (other.carePlanSections != null)
				return false;
		} else if (!carePlanSections.equals(other.carePlanSections))
			return false;
		
		return true;
	}

	public CCDADischargeMedication getDischargeMedication() {
		return dischargeMedication;
	}

	public void setDischargeMedication(CCDADischargeMedication dischargeMedication) {
		this.dischargeMedication = dischargeMedication;
	}

	public void performCIRIValidation(String validationObjective, CCDARefModel submittedCCDA,ArrayList<ContentValidationResult> results, boolean curesUpdate) 
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Problems ");
		compareProblems(validationObjective, submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Allergies ");
		compareAllergies(validationObjective, submittedCCDA, results, curesUpdate);
		
		log.info("Comparing Medications ");
		compareMedications(validationObjective, submittedCCDA, results, curesUpdate);
		
		log.info("Finished comparison , returning results");
		
	}

	public void performCarePlanValidation(String validationObjective, CCDARefModel submittedCCDA,ArrayList<ContentValidationResult> results, boolean curesUpdate) 
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results, curesUpdate);
		
		log.info("Comparing CarePlan Sections");
		compareCarePlanSections(submittedCCDA, results);
		
		log.info("Finished comparison , returning results");
		
	}

	public void performDS4PValidation(String validationObjective, CCDARefModel submittedCCDA,ArrayList<ContentValidationResult> results, boolean curesUpdate) 
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results, curesUpdate);
		
		log.info("Finished comparison , returning results");
		
	}
	
	public void compareNonCCDSStructuredData(String valObj, CCDARefModel submittedCCDA,ArrayList<ContentValidationResult> results, boolean curesUpdate)
	{
		// validate encounter diagnosis.
		if(valObj.equalsIgnoreCase("170.315_b1_ToC_Amb") || 
				valObj.equalsIgnoreCase("170.315_b1_ToC_Inp") ||
				valObj.equalsIgnoreCase("170.315_b4_CCDS_Amb") ||
				valObj.equalsIgnoreCase("170.315_b4_CCDS_Inp") ||
				valObj.equalsIgnoreCase("170.315_b6_DE_Amb") ||
				valObj.equalsIgnoreCase("170.315_b6_DE_Inp") ) {
			
			log.info("Comparing Encounter Diagnosis for b1, b4, and b6 ");
			compareEncounterDiagnosis(valObj, submittedCCDA, results);	
		}
	}

	public CCDADischargeDiagnosis getDischargeDiagnosis() {
		return dischargeDiagnosis;
	}

	public void setDischargeDiagnosis(CCDADischargeDiagnosis dischargeDiagnosis) {
		this.dischargeDiagnosis = dischargeDiagnosis;
	}

	public CCDAMedicalEquipment getMedEquipments() {
		return medEquipments;
	}

	public void setMedEquipments(CCDAMedicalEquipment medEquipments) {
		this.medEquipments = medEquipments;
	}
	
	public boolean warningsPermitted() {
		return severityLevel == SeverityLevel.WARNING || severityLevel == SeverityLevel.INFO;				
	}
	
	public boolean infoPermitted() {
		return severityLevel == SeverityLevel.INFO;				
	}

	public String getSeverityLevelName() {
		return severityLevel.name();
	}
	
}
