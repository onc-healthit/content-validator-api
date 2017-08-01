package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDARefModel {
	
	private static Logger log = Logger.getLogger(CCDARefModel.class.getName());
	
	private CCDAPatient        patient;
	private CCDACareTeamMember members;
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
	private CCDASocialHistory  smokingStatus;
	private CCDAVitalSigns     vitalSigns;
	private CCDAProblem        problem;
	private CCDAPlanOfTreatment planOfTreatment;
	private CCDAGoals          goals;
	private CCDAHealthConcerns hcs;
	private ArrayList<CCDAUDI> udi;
	
	public CCDARefModel() {
		udi = new ArrayList<CCDAUDI>();
	}
	
	public ArrayList<ContentValidationResult> compare(String validationObjective, CCDARefModel submittedCCDA) {
		
		ArrayList<ContentValidationResult> results = new ArrayList<ContentValidationResult>();
		
		if(doesObjectiveRequireCCDS(validationObjective))
		{
			log.info(" Performing CCDS checks ");
			compareCCDS(validationObjective, submittedCCDA, results);
		}
		else if(doesObjectiveRequireCIRI(validationObjective))
		{
			log.info(" Performing CIRI checks ");
			performCIRIValidation(validationObjective, submittedCCDA, results);
		}
		else if(doesObjectiveRequireCarePlan(validationObjective))
		{
			log.info(" Performing Care Plan checks ");
			performCarePlanValidation(validationObjective, submittedCCDA, results);
		}
		else if(doesObjectiveRequireDS4P(validationObjective))
		{
			log.info(" Performing DS4P checks ");
			performDS4PValidation(validationObjective, submittedCCDA, results);
		}
		else 
		{
			log.info(" Not Performing any content validation checks ");
		}
		
		log.info(" Compare non CCDS Structured Data ");
		compareNonCCDSStructuredData(validationObjective, submittedCCDA, results);
		
		log.info(" Total Number of Content Validation Issues " + results.size());
		return results;
	}
	
	public void compareCCDS(String validationObjective, CCDARefModel submittedCCDA,ArrayList<ContentValidationResult> results) 
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results);
		
		log.info("Validating Birth Sex ");
		validateBirthSex(submittedCCDA, results);
		
		log.info("Comparing Problems ");
		compareProblems(validationObjective, submittedCCDA, results);
		
		log.info("Comparing Allergies ");
		compareAllergies(validationObjective, submittedCCDA, results);
		
		log.info("Comparing Medications ");
		compareMedications(validationObjective, submittedCCDA, results);
		
		log.info("Comparing Lab Results ");
		compareLabResults(validationObjective, submittedCCDA, results);
		
		log.info("Comparing Vital Signs ");
		compareVitalObs(validationObjective, submittedCCDA, results);
		
		log.info("Comparing Procedures ");
		compareProcedures(validationObjective, submittedCCDA, results);
		
		log.info("Comparing Immunizations ");
		compareImmunizations(validationObjective, submittedCCDA, results);
		
		log.info("Finished comparison , returning results");
		
	}
	
	private void compareProblems(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {
		
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
	
	private void compareAllergies(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {
		
		if((this.getAllergy() != null) && (submittedCCDA.getAllergy() != null) ) {
			log.info("Start Allergy Comparison ");
			this.allergy.compare(submittedCCDA.getAllergy(), results);
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
	
	public void compareMedications(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {
		
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
	
	public void compareImmunizations(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {
		
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
	
	
	public void compareLabResults(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {
		
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
	
	public void compareVitalObs(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {
		
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
	
	public void compareProcedures(String validationObjective, CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {
		
		log.info("Retrieving Procedure Acts for comparison ");
		HashMap<String, CCDAProcActProc> refProcs = this.getAllProcedures();
		HashMap<String, CCDAProcActProc> subProcs = submittedCCDA.getAllProcedures();
		
		if( (refProcs != null && refProcs.size() > 0) &&  
			(subProcs != null && subProcs.size() > 0)  ) {
			
			log.info("Vital Signs present in both models ");
			CCDAProcActProc.compareProcedures(refProcs, subProcs, results);
			
		} else if ( (refProcs != null && refProcs.size() > 0) && 
				(subProcs == null || subProcs.size() == 0) ) {
			
			// handle the case where the Vitals Section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's Procedures, but the submitted C-CDA does not contain Procedure data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Vital Signs but submitted document does not contain Procedures data");
			
		}else if ((refProcs == null || refProcs.size() == 0) && 
				(subProcs != null && subProcs.size() > 0) ) {
		
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's Procedures, but the submitted C-CDA does contain Procedure data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have Procedures for comparison ");
			
		} else {
			
			log.info("Model and Submitted CCDA do not have Procedures for comparison ");
		}
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
	
	private HashMap<String, CCDAProcActProc> getAllProcedures() 
	{
		HashMap<String,CCDAProcActProc> results = new HashMap<String,CCDAProcActProc>();
		
		if(procedure != null) {
			
			results.putAll(procedure.getProcedureMap());
			log.info(" Procedure Size = " + results.size());
		}
		
		return results;
	}
	
	private void validateBirthSex(CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {
		
		if( (submittedCCDA.getSmokingStatus() != null) &&
			 (submittedCCDA.getSmokingStatus().getBirthSex() != null)) {
			
			// Validate that the code is M or F.
			if( (submittedCCDA.getSmokingStatus().getBirthSex().getSexCode() != null)  && 
				((submittedCCDA.getSmokingStatus().getBirthSex().getSexCode().getCode().equalsIgnoreCase("M")) || 
				(submittedCCDA.getSmokingStatus().getBirthSex().getSexCode().getCode().equalsIgnoreCase("F"))) )
			{
				//do nothing.
				return;
			}
			else {
				ContentValidationResult rs = new ContentValidationResult("The scenario requires patient's birth sex to use the codes M or F but the submitted C-CDA does not contain either of these codes.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
		}
		else {
			
			// MAKE THIS A WARNING SINCE IT IS A BEST PRACTICE
			ContentValidationResult rs = new ContentValidationResult("The scenario requires patient's birth sex to be captured as part of social history data, but submitted file does have birth sex information", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
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
	
	
	private void comparePatients(CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results) {
		
		if((patient != null) && (submittedCCDA.getPatient() != null)) {
			this.patient.compare(submittedCCDA.getPatient(), results);
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
		
		if(smokingStatus != null)
			smokingStatus.log();
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
		
		for(int j = 0; j < udi.size(); j++) {
			udi.get(j).log();
			
		}
		
		if(admissionDiagnosis != null) 
			admissionDiagnosis.log();
		
		if(dischargeDiagnosis != null)
			dischargeDiagnosis.log();
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
	public CCDASocialHistory getSmokingStatus() {
		return smokingStatus;
	}
	public void setSmokingStatus(CCDASocialHistory smokingStatus) {
		this.smokingStatus = smokingStatus;
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
				+ ((smokingStatus == null) ? 0 : smokingStatus.hashCode());
		result = prime * result + ((udi == null) ? 0 : udi.hashCode());
		result = prime * result
				+ ((vitalSigns == null) ? 0 : vitalSigns.hashCode());
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
		if (smokingStatus == null) {
			if (other.smokingStatus != null)
				return false;
		} else if (!smokingStatus.equals(other.smokingStatus))
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
		return true;
	}

	public CCDADischargeMedication getDischargeMedication() {
		return dischargeMedication;
	}

	public void setDischargeMedication(CCDADischargeMedication dischargeMedication) {
		this.dischargeMedication = dischargeMedication;
	}

	public void performCIRIValidation(String validationObjective, CCDARefModel submittedCCDA,ArrayList<ContentValidationResult> results) 
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results);
		
		log.info("Comparing Problems ");
		compareProblems(validationObjective, submittedCCDA, results);
		
		log.info("Comparing Allergies ");
		compareAllergies(validationObjective, submittedCCDA, results);
		
		log.info("Comparing Medications ");
		compareMedications(validationObjective, submittedCCDA, results);
		
		log.info("Finished comparison , returning results");
		
	}

	public void performCarePlanValidation(String validationObjective, CCDARefModel submittedCCDA,ArrayList<ContentValidationResult> results) 
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results);
		
		log.info("Finished comparison , returning results");
		
	}

	public void performDS4PValidation(String validationObjective, CCDARefModel submittedCCDA,ArrayList<ContentValidationResult> results) 
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results);
		
		log.info("Finished comparison , returning results");
		
	}
	
	public void compareNonCCDSStructuredData(String validationObjective, CCDARefModel submittedCCDA,ArrayList<ContentValidationResult> results)
	{
		// validate encounter diagnosis.
		compareEncounterDiagnosis(validationObjective, submittedCCDA, results);
	}

	public CCDADischargeDiagnosis getDischargeDiagnosis() {
		return dischargeDiagnosis;
	}

	public void setDischargeDiagnosis(CCDADischargeDiagnosis dischargeDiagnosis) {
		this.dischargeDiagnosis = dischargeDiagnosis;
	}

	
}
