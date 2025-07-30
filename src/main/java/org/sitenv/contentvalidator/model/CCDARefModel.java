package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.dto.enums.SeverityLevel;
import org.sitenv.contentvalidator.parsers.CCDAConstants;
import org.sitenv.contentvalidator.parsers.ParserUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CCDARefModel {

	private static Logger log = LoggerFactory.getLogger(CCDARefModel.class.getName());

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
	private CCDAAssessmentAndPlanSection assessmentAndPlanSection;


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

	// Cures Update changes for authors
	private ArrayList<CCDAAuthor> authorsFromHeader; // header-level only
	private ArrayList<CCDAAuthor> authorsWithLinkedReferenceData;

	// Cures Update changes for section level notes
	private ArrayList<CCDANotes> notes;
	private ArrayList<CCDANotesActivity> notesEntries;

	// USCDI v3
	private CCDAPayers payers;
	private CCDAReasonForReferral referrals;
	private CCDAFunctionalStatus functionalStatus;
	private CCDAMentalStatus	 mentalStatus;
	private ArrayList<CCDAParticipant> relatedPersons;

	// USCDI v4 changes
	private ArrayList<CCDATreatmentInterventionPreference>  treatmentPreferences;
	private ArrayList<CCDACareExperiencePreference>			carePreferences;

	public ArrayList<CCDAAuthor> getAuthorsFromHeader() {
		return authorsFromHeader;
	}

	public void setAuthorsFromHeader(ArrayList<CCDAAuthor> author) {
		this.authorsFromHeader = author;
	}

	public ArrayList<CCDAAuthor> getAuthorsWithLinkedReferenceData() {
		return authorsWithLinkedReferenceData;
	}

	public void setAuthorsWithLinkedReferenceData(ArrayList<CCDAAuthor> authorsWithLinkedReferenceData) {
		this.authorsWithLinkedReferenceData = authorsWithLinkedReferenceData;
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
		authorsFromHeader = new ArrayList<CCDAAuthor>();
		authorsWithLinkedReferenceData = new ArrayList<CCDAAuthor>();
		relatedPersons = new ArrayList<>();
		treatmentPreferences = new ArrayList<>();
		carePreferences = new ArrayList<>();

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

	public ArrayList<ContentValidationResult> compare(String validationObjective, CCDARefModel submittedCCDA,
			boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		ArrayList<ContentValidationResult> results = new ArrayList<ContentValidationResult>();

		if(doesObjectiveRequireCCDS(validationObjective))
		{
			log.info(" Performing CCDS checks ");
			compareCCDS(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);
		}
		else if(doesObjectiveRequireCIRI(validationObjective))
		{
			log.info(" Performing CIRI checks ");
			performCIRIValidation(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);
		}
		else if(doesObjectiveRequireCarePlan(validationObjective))
		{
			log.info(" Performing Care Plan checks ");
			performCarePlanValidation(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);
		}
		else if(doesObjectiveRequireDS4P(validationObjective))
		{
			log.info(" Performing DS4P checks ");
			performDS4PValidation(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);
		}
		else
		{
			log.info(" Not Performing any content validation checks ");
		}

		log.info(" Compare non CCDS Structured Data ");
		compareNonCCDSStructuredData(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

		validateDocElements(validationObjective,submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

		log.info(" Total Number of Content Validation Issues " + results.size());
		return results;
	}

	public void validateDocElements(String valObj, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024)
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

	public void compareCCDS(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		if(svap2022 || svap2023 || svap2024) {
			curesUpdate = true;
		}

		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

		log.info("Comparing Social History Smoking Status ");
		validateSmokingStatus(submittedCCDA, results, curesUpdate, svap2022, svap2023);

		if(!svap2023 && !svap2024) {
			log.info("Validating Social History Birth Sex ");
			validateBirthSex(submittedCCDA, results, curesUpdate, svap2022, svap2023);
		}

		log.info("Comparing Problems ");
		compareProblems(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

		log.info("Comparing Allergies ");
		compareAllergies(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

		log.info("Comparing Medications ");
		compareMedications(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

		log.info("Comparing Lab Results ");
		compareLabResults(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

		log.info("Comparing Vital Signs ");
		compareVitalObs(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

		log.info("Comparing Procedures ");
		compareProcedures(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

		log.info("Comparing Udis ");
		compareUdis(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

		log.info("Comparing Immunizations ");
		compareImmunizations(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);


		if (curesUpdate) {

			log.info(" Comparing data for Cures Update (USCDI v1) specific entries ");

			ArrayList<CCDAAuthor> submittedAuthorsWithLinkedReferenceData = null;
			submittedAuthorsWithLinkedReferenceData = submittedCCDA.getAuthorsWithLinkedReferenceData() != null
					? submittedCCDA.getAuthorsWithLinkedReferenceData()
					: null;
			logSubmittedAuthorsWithLinkedReferenceData(submittedAuthorsWithLinkedReferenceData);

			log.info(" Comparing Notes ");
			compareNotesActivities(validationObjective, submittedCCDA, results, curesUpdate,
					submittedAuthorsWithLinkedReferenceData, svap2022, svap2023);

			log.info(" Comparing Author ");
			compareAuthorEntries(validationObjective, submittedCCDA, results, curesUpdate,
					submittedAuthorsWithLinkedReferenceData, svap2022, svap2023);

			log.info(" Comparing Care Team ");
			compareCareTeamMembers(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);
		}

		if (svap2022 || svap2023 || svap2024 ) {

			log.info(" Comparing data for Cures Update (USCDI v2, USCDIv3, USCDIv4) specific entries ");

			log.info(" Comparing Sexual Orientation ");
			// compareSexOrientation(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

			log.info(" Comparing Gender Identity ");
			// compareGenderIdentity(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

			log.info(" Comparing SDOH Data ");
			compareSdohData(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

			log.info(" Comparing Goals Data ");
			compareGoalsData(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

			log.info(" Comparing HealthConcerns Data ");
			compareHealthConcernsData(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

			log.info(" Comparing Plan of Treatment Data ");
			comparePlanOfTreatmentData(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);
		}

		if (svap2023 || svap2024) {
			log.info(" Comparing data for Cures Update (USCDI v3, USCDIv4) specific entries ");
			svap2023 = true;

			log.info(" Comparing Functional Status Data ");
			compareFunctionalStatus(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

			log.info(" Comparing Mental Status Data ");
			compareMentalStatus(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

			log.info(" Comparing Reason For Referral Status Data ");
			compareReasonForReferral(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

			log.info(" Comparing Payers Data ");
			comparePayers(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

			log.info(" Comparing Social History Observations ");
			compareSocialHistoryObservations(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

			log.info(" Comparing Social History Observations ");
			compareRelatedPersons(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

		}

		if(svap2024)
		{
			log.info(" Comparing data for Cures Update (USCDI v4) specific entries ");
			log.info("Comparing Encounters ");
			compareEncounters(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

			log.info(" Comparing Treatment Preferences ");
			compareTreatmentPreferences(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

			log.info(" Comparing Care Experience Preferences ");
			compareCareExperiencePreferences(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

		}

		log.info("Finished comparison, returning results");

	}



	private void compareSocialHistoryObservations(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		log.info("Comparing Social History Observations");
		if(this.socialHistory != null) {
			this.socialHistory.compare(validationObjective, submittedCCDA.getSocialHistory(), curesUpdate, svap2022, svap2023, svap2024, results);
		}
		else {
			log.info(" Nothing to do ");
		}
	}

	private void compareRelatedPersons(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		log.info("Retrieving Related Persons for comparison ");
		ArrayList<CCDAParticipant> refParts = this.getAllRelatedPersons();
		ArrayList<CCDAParticipant> subParts = submittedCCDA.getAllRelatedPersons();

		if( (refParts != null && refParts.size() > 0) &&
				(subParts != null && subParts.size() > 0)  ) {

				log.info("Specimens present in both models ");
				CCDAParticipant.compareRelatedPersons(refParts, subParts, results);

			} else if ( (refParts != null && refParts.size() > 0) &&
					(subParts == null || subParts.size() == 0) ) {

				// handle the case where the RelatedPerson does not exist in the submitted CCDA
				ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to RelatedPerson, but the submitted C-CDA does not contain RelatedPerson data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info(" Scenario requires ReleatedPerson data but submitted document does not contain RelatedPerson data");

			}else if ((refParts == null || refParts.size() == 0) &&
					(subParts != null && subParts.size() > 0) ) {

				ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to RelatedPerson, but the submitted C-CDA does contain RelatedPerson data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info("Model does not have RelatedPerson for comparison ");

			} else {

				log.info("Model and Submitted CCDA do not have Related Persons for comparison ");
			}

	}

	private void compareReasonForReferral(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		CCDADataElement sectionText = null;
		if(svap2024) {
		HashMap<String, CCDAPatientReferralAct> refModelActs  = null;

		if(this.getReferrals() != null)
			refModelActs = this.getReferrals().getAllReferrals();

		HashMap<String, CCDAPatientReferralAct> subModelActs = null;

		
		if(submittedCCDA.getReferrals() != null) {			
			sectionText = submittedCCDA.getReferrals().getSectionText();
			subModelActs = submittedCCDA.getReferrals().getAllReferrals();
		}

		if( (refModelActs != null && refModelActs.size() > 0) &&
			(subModelActs != null && subModelActs.size() > 0)  ) {

			log.info("Mental Status Assessments present in both models ");
			CCDAPatientReferralAct.compare(refModelActs, subModelActs, results);

		}
		else if ( (refModelActs != null && refModelActs.size() > 0) &&
				(subModelActs == null || subModelActs.size() == 0) ) {

			ContentValidationResult rs = new ContentValidationResult("The scenario requires Patient's Referral data "
					+ "but the submitted C-CDA does not contain Referral data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Referral data, but submitted document does not contain Referral data");

		}else if ((refModelActs == null || refModelActs.size() == 0) &&
				(subModelActs != null && subModelActs.size() > 0) ) {


			log.info("Model does not have Referral Data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have Referral data for comparison ");
		}
		
		}//svap2024
		
		// Check presence of narrative text
	/*	if(sectionText == null || 
				sectionText.getValue() == null ||
				sectionText.getValue().isEmpty()) {
			ContentValidationResult rs = new ContentValidationResult("The scenario requires Patient's Referral data in the section narrative text element"
					+ "but the submitted C-CDA does not contain Referral data in the section narrative text element.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Referral Text data element , but submitted document does not contain Referral Text data element");

			
		}*/

	}

	private HashMap<String, AssessmentScaleObservation> getMentalStatusAssessments() {

		if(mentalStatus != null) {

			return this.getMentalStatus().getAllAssessmentScaleObservations();
		}

		return null;
	}

	private void compareMentalStatus(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		HashMap<String, AssessmentScaleObservation> refAssessments = this.getMentalStatusAssessments();

		HashMap<String, AssessmentScaleObservation> subAssessments = submittedCCDA.getMentalStatusAssessments();

		if( (refAssessments != null && refAssessments.size() > 0) &&
			(subAssessments != null && subAssessments.size() > 0)  ) {

			log.info("Mental Status Assessments present in both models ");
			AssessmentScaleObservation.compare(refAssessments, subAssessments, results);

		}
		else if ( (refAssessments != null && refAssessments.size() > 0) &&
				(subAssessments == null || subAssessments.size() == 0) ) {

			ContentValidationResult rs = new ContentValidationResult("The scenario requires Patient's Mental Status Asssessment data "
					+ "but the submitted C-CDA does not contain Mental Status Asssessment data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Mental Status Assessment data, but submitted document does not contain Mental Status Assessment data");

		}else if ((refAssessments == null || refAssessments.size() == 0) &&
				(subAssessments != null && subAssessments.size() > 0) ) {


			log.info("Model does not have Assessment Data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have Assessment data for comparison ");
		}

	}

	private HashMap<String, AssessmentScaleObservation> getFunctionalStatusAssessments() {

		if(functionalStatus != null) {

			return this.getFunctionalStatus().getAllAssessmentScaleObservations();
		}

		return null;
	}


	private void compareFunctionalStatus(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		HashMap<String, AssessmentScaleObservation> refAssessments = this.getFunctionalStatusAssessments();

		HashMap<String, AssessmentScaleObservation> subAssessments = submittedCCDA.getFunctionalStatusAssessments();

		if( (refAssessments != null && refAssessments.size() > 0) &&
			(subAssessments != null && subAssessments.size() > 0)  ) {

			log.info("Functional Status Assessments present in both models ");
			AssessmentScaleObservation.compare(refAssessments, subAssessments, results);

		}
		else if ( (refAssessments != null && refAssessments.size() > 0) &&
				(subAssessments == null || subAssessments.size() == 0) ) {

			ContentValidationResult rs = new ContentValidationResult("The scenario requires Patient's Functional Status Asssessment data "
					+ "but the submitted C-CDA does not contain Functional Status Asssessment data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Functional Status Assessment data, but submitted document does not contain Functional Status Assessment data");

		}else if ((refAssessments == null || refAssessments.size() == 0) &&
				(subAssessments != null && subAssessments.size() > 0) ) {


			log.info("Model does not have Assessment Data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have Assessment data for comparison ");
		}

	}

	private void comparePayers(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		if((this.getPayers() != null) && (submittedCCDA.getPayers() != null) ) {
			log.info("Start Payers Comparison ");
			this.payers.compare(submittedCCDA.getPayers(), results, svap2022, svap2023);
		}
		else if ( (this.getPayers() != null) && (submittedCCDA.getPayers() == null))
		{
			// handle the case where the payers section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's health insurance, but the submitted C-CDA does not contain payer data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires health insurance data but submitted document does not contain health insurance (payers) section");
		}
		else if ( (this.getPayers() == null) && (submittedCCDA.getPayers() != null) ){

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's health insurance, but the submitted C-CDA does contain payers (health insurance) data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have payers for comparison ");
		}
		else {

			log.info("Model and Submitted CCDA do not have payers for comparison ");
		}

	}

	private void compareProblems(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		if((this.getProblem() != null) && (submittedCCDA.getProblem() != null) ) {
			log.info("Start Problem Comparison ");
			this.problem.compare(submittedCCDA.getProblem(), results, svap2022, svap2023);
		}
		else if ( (this.getProblem() != null) && (submittedCCDA.getProblem() == null) )
		{
			// handle the case where the problem section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's problems, but the submitted C-CDA does not contain problem data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires problems but submitted document does not contain problems section");
		}
		else if ( (this.getProblem() == null) && (submittedCCDA.getProblem() != null) ){

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's problems, but the submitted C-CDA does contain problem data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have problems for comparison ");
		}
		else {

			log.info("Model and Submitted CCDA do not have problems for comparison ");
		}

	}

	private void compareAllergies(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

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

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's allergies, but the submitted C-CDA does contain allergy data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
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

	public void compareMedications(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		log.info("Retrieving Medication Activities for comparison ");
		HashMap<String, CCDAMedicationActivity> refActivities = this.getAllMedActivities();
		HashMap<String, CCDAMedicationActivity> subActivities = submittedCCDA.getAllMedActivities();

		if( (refActivities != null && refActivities.size() > 0) &&
			(subActivities != null && subActivities.size() > 0)  ) {

			log.info("Medication Activities in both models ");
			CCDAMedicationActivity.compareMedicationActivityData(refActivities, subActivities, results, svap2024);

		} else if ( (refActivities != null && refActivities.size() > 0) &&
				(subActivities == null || subActivities.size() == 0) ) {

			// handle the case where the allergy section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's medications, but the submitted C-CDA does not contain medication data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires medications but submitted document does not contain medication data");

		}else if ((refActivities == null || refActivities.size() == 0) &&
				(subActivities != null && subActivities.size() > 0) ) {

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's medications, but the submitted C-CDA does contain medication data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have medications for comparison ");

		} else {

			log.info("Model and Submitted CCDA do not have medications for comparison ");
		}
	}

	public void compareImmunizations(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

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

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's immunizations, but the submitted C-CDA does contain immunization data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have immunization for comparison ");

		} else {

			log.info("Model and Submitted CCDA do not have immunization for comparison ");
		}
	}


	public void compareLabResults(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		log.info("Retrieving Lab Results for comparison ");
		HashMap<String, CCDALabResultObs> refResults = this.getAllLabResultObs();
		HashMap<String, CCDALabResultObs> subResults = submittedCCDA.getAllLabResultObs();

		if( (refResults != null && refResults.size() > 0) &&
			(subResults != null && subResults.size() > 0)  ) {

			log.info("Lab Results present in both models ");
			CCDALabResultObs.compareLabResultData(refResults, subResults, results, svap2024);

		} else if ( (refResults != null && refResults.size() > 0) &&
				(subResults == null || subResults.size() == 0) ) {

			// handle the case where the allergy section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's lab results, but the submitted C-CDA does not contain lab result data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires lab results but submitted document does not contain lab results data");

		}else if ((refResults == null || refResults.size() == 0) &&
				(subResults != null && subResults.size() > 0) ) {

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's lab results, but the submitted C-CDA does contain lab result data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have lab results for comparison ");

		} else {

			log.info("Model and Submitted CCDA do not have lab results for comparison ");
		}

		compareSpecimens(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);
	}

	public void compareSpecimens(String validationObjective, CCDARefModel submittedCCDA,
	ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		log.info("Retrieving Specimens for comparison ");
		HashMap<String, CCDASpecimen> refSpecs = this.getAllSpecimens();
		HashMap<String, CCDASpecimen> subSpecs = submittedCCDA.getAllSpecimens();

		if( (refSpecs != null && refSpecs.size() > 0) &&
				(subSpecs != null && subSpecs.size() > 0)  ) {

				log.info("Specimens present in both models ");
				CCDASpecimen.compareSpecimens(refSpecs, subSpecs, results);

			} else if ( (refSpecs != null && refSpecs.size() > 0) &&
					(subSpecs == null || subSpecs.size() == 0) ) {

				// handle the case where the Vitals Section does not exist in the submitted CCDA
				ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to Specimen, but the submitted C-CDA does not contain Specimen data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info(" Scenario requires Specimen data but submitted document does not contain Specimen data");

			}else if ((refSpecs == null || refSpecs.size() == 0) &&
					(subSpecs != null && subSpecs.size() > 0) ) {

				ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to Specimen, but the submitted C-CDA does contain Specimen data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
				results.add(rs);
				log.info("Model does not have Specimen for comparison ");

			} else {

				log.info("Model and Submitted CCDA do not have Specimens for comparison ");
			}

	}

	public void compareVitalObs(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

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

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's Vital Signs, but the submitted C-CDA does contain Vital Sign data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have Vital Signs for comparison ");

		} else {

			log.info("Model and Submitted CCDA do not have Vital Signs for comparison ");
		}
	}

	public void compareUdis(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

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

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's Udis, but the submitted C-CDA does contain UDI data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have UDIs for comparison ");

		} else {

			log.info("Model and Submitted CCDA do not have UDIs for comparison ");
		}
	}

	public void compareProcedures(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

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

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's Procedures, but the submitted C-CDA does contain Procedure data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have Procedures for comparison ");

		} else {

			log.info("Model and Submitted CCDA do not have Procedures for comparison ");
		}
	}

	public void compareNotes(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		log.info("Retrieving Notes Section for comparison ");
		HashMap<String, CCDANotes> refNotes = this.getAllNotes();
		HashMap<String, CCDANotes> subNotes= submittedCCDA.getAllNotes();

		if( (refNotes != null && refNotes.size() > 0) &&
			(subNotes != null && subNotes.size() > 0)  ) {

			log.info("Notes present in both models ");
			CCDANotes.compareNotes(refNotes, subNotes, results);

		} else if ( (refNotes != null && refNotes.size() > 0) &&
				(subNotes == null || subNotes.size() == 0) ) {

			// handle the case where the Notes section does not exist in the submitted CCDA
			// ref has Notes but sub does not
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's Notes, "
					+ "but the submitted C-CDA does not contain Notes data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Notes data, but submitted document does not contain Notes data");

		} else if ((refNotes == null || refNotes.size() == 0) &&
				(subNotes != null && subNotes.size() > 0) ) {

			// ref doesn't have notes but sub does also results in an error
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's Notes, "
					+ "but the submitted C-CDA does contain Notes data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have Notes for comparison ");

		} else {

			log.info("Model and Submitted CCDA do not have Notes for comparison ");
		}
	}

	public void compareSexOrientation(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		log.info("Retrieving Sexual Orientation for comparison ");
		HashMap<String, CCDASexualOrientation> refSexOrientation = this.getAllSexualOrientations();
		HashMap<String, CCDASexualOrientation> subSexOrientation = submittedCCDA.getAllSexualOrientations();

		if( (refSexOrientation != null && refSexOrientation.size() > 0) &&
			(subSexOrientation != null && subSexOrientation.size() > 0)  ) {

			log.info("Sexual Orientation present in both models ");
			CCDASexualOrientation.compare(refSexOrientation, subSexOrientation, results);

		}
		else if ( (refSexOrientation != null && refSexOrientation.size() > 0) &&
				(subSexOrientation == null || subSexOrientation.size() == 0) ) {

			// handle the case where the Notes section does not exist in the submitted CCDA
			// ref has Notes but sub does not
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to the patient's sexual orientation"
					+ "but the submitted C-CDA does not contain sexual orientation data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires sexual orientation data, but submitted document does not contain sexual orientation data");

		}else if ((refSexOrientation == null || refSexOrientation.size() == 0) &&
				(subSexOrientation != null && subSexOrientation.size() > 0) ) {


			log.info("Model does not have Sexual Orientation data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have Sexual Orientation data for comparison ");
		}
	}

	public void compareGenderIdentity(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		log.info("Retrieving Gender Identities for comparison ");
		HashMap<String, CCDAGenderIdentityObs> refGenders = this.getAllGenderIdentities();
		HashMap<String, CCDAGenderIdentityObs> subGenders = submittedCCDA.getAllGenderIdentities();

		if( (refGenders != null && refGenders.size() > 0) &&
			(subGenders != null && subGenders.size() > 0)  ) {

			log.info("Gender Identity present in both models ");
			CCDAGenderIdentityObs.compare(refGenders, subGenders, results);

		}
		else if ( (refGenders != null && refGenders.size() > 0) &&
				(subGenders == null || subGenders.size() == 0) ) {

			// handle the case where the Notes section does not exist in the submitted CCDA
			// ref has Notes but sub does not
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to the patient's gender identity"
					+ "but the submitted C-CDA does not contain gender identity data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires gender identity data, but submitted document does not contain gender identity data");

		}else if ((refGenders == null || refGenders.size() == 0) &&
				(subGenders != null && subGenders.size() > 0) ) {

			log.info("Model does not have Gender Identity data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have Gender Identity data for comparison ");
		}
	}

	public void compareSdohData(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		log.info("Retrieving SDOH Data for comparison ");
		HashMap<String, AssessmentScaleObservation> refSdohData = this.getAssessmentScaleObservations();
		HashMap<String, AssessmentScaleObservation> subSdohData = submittedCCDA.getAssessmentScaleObservations();

		if( (refSdohData != null && refSdohData.size() > 0) &&
			(subSdohData != null && subSdohData.size() > 0)  ) {

			log.info("SDOH Data present in both models ");
			AssessmentScaleObservation.compare(refSdohData, subSdohData, results);

		}
		else if ( (refSdohData != null && refSdohData.size() > 0) &&
				(subSdohData == null || subSdohData.size() == 0) ) {

			// handle the case where the Notes section does not exist in the submitted CCDA
			// ref has Notes but sub does not
			ContentValidationResult rs = new ContentValidationResult("The scenario requires Patient's SDOH data "
					+ "but the submitted C-CDA does not contain SDOH data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires SDOH data, but submitted document does not contain SDOH data");

		}else if ((refSdohData == null || refSdohData.size() == 0) &&
				(subSdohData != null && subSdohData.size() > 0) ) {


			log.info("Model does not have SDOH Data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have SDOH data for comparison ");
		}
	}


	private void compareGoalsData(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		log.info("Retrieving Goal Observations for comparison ");
		HashMap<String, GoalObservation> refGoals = this.getAllGoalObservations();
		HashMap<String, GoalObservation> subGoals = submittedCCDA.getAllGoalObservations();

		if( (refGoals != null && refGoals.size() > 0) &&
			(subGoals != null && subGoals.size() > 0)  ) {

			log.info("Goal Observations present in both models ");
			GoalObservation.compare(refGoals, subGoals, results);

		}
		else if ( (refGoals != null && refGoals.size() > 0) &&
				(subGoals == null || subGoals.size() == 0) ) {

			// handle the case where the Goals section does not exist in the submitted CCDA
			// ref has Notes but sub does not
			ContentValidationResult rs = new ContentValidationResult("The scenario requires Goal Observation data "
					+ "but the submitted C-CDA does not contain Goal Observation data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Goal Observation data, but submitted document does not contain gender identity data");

		}else if ((refGoals == null || refGoals.size() == 0) &&
				(subGoals != null && subGoals.size() > 0) ) {


			log.info("Model does not have Goal Observation data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have Goal Observation data for comparison ");
		}
	}

	private void compareHealthConcernsData(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		log.info("Retrieving Health Concern Acts  for comparison ");
		HashMap<String, CCDAProblemObs> refHcActs = this.getAllHealthConcernActs();
		HashMap<String, CCDAProblemObs> subHcActs = submittedCCDA.getAllHealthConcernActs();

		if( (refHcActs != null && refHcActs.size() > 0) &&
			(subHcActs != null && subHcActs.size() > 0)  ) {

			log.info("Health Concern Acts present in both models ");
			CCDAProblemObs.compareHcActs(refHcActs, subHcActs, results, svap2022, svap2023);

		}
		else if ( (refHcActs != null && refHcActs.size() > 0) &&
				(subHcActs == null || subHcActs.size() == 0) ) {

			// handle the case where the Goals section does not exist in the submitted CCDA
			// ref has Notes but sub does not
			ContentValidationResult rs = new ContentValidationResult("The scenario requires Health Concerns Act data "
					+ "but the submitted C-CDA does not contain Health Concern Act data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Health Concern Act data, but submitted document does not contain Health Concern Act data");

		}else if ((refHcActs == null || refHcActs.size() == 0) &&
				(subHcActs != null && subHcActs.size() > 0) ) {


			log.info("Model does not have Health Concern Act data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have Health Concern Act data for comparison ");
		}
	}

	private void comparePlanOfTreatmentData(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

		log.info("Retrieving Planned Procedures for comparison ");
		HashMap<String, PlannedProcedure> refProcs = this.getAllPlannedProcedures();
		HashMap<String, PlannedProcedure> subProcs = submittedCCDA.getAllPlannedProcedures();

		if( (refProcs != null && refProcs.size() > 0) &&
			(subProcs != null && subProcs.size() > 0)  ) {

			log.info("Planned Procedures present in both models ");
			PlannedProcedure.compare(refProcs, subProcs, results);

		}
		else if ( (refProcs != null && refProcs.size() > 0) &&
				(subProcs == null || subProcs.size() == 0) ) {

			// handle the case where the Goals section does not exist in the submitted CCDA
			// ref has Notes but sub does not
			ContentValidationResult rs = new ContentValidationResult("The scenario requires Planned Procedure data "
					+ "but the submitted C-CDA does not contain Planned Procedure data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Planned Procedure data, but submitted document does not contain Planned Procedure data");

		}else if ((refProcs == null || refProcs.size() == 0) &&
				(subProcs != null && subProcs.size() > 0) ) {


			log.info("Model does not have Plan of Treatment data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have Planned Procedure data for comparison ");
		}
	}

	private void compareEncounters(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		if(!svap2024)
		{
			return;
		}

		log.info("Retrieving EncounterActivities for comparison ");
		HashMap<String, CCDAEncounterActivity> refEncounters = this.getAllEncounterActivities();
		HashMap<String, CCDAEncounterActivity> subEncounters = submittedCCDA.getAllEncounterActivities();

		if( (refEncounters != null && refEncounters.size() > 0) &&
			(subEncounters != null && subEncounters.size() > 0)  ) {

			log.info("EncounterActivities present in both models ");
			CCDAEncounterActivity.compareEncounterActivity(refEncounters, subEncounters, results);

		} else if ( (refEncounters != null && refEncounters.size() > 0) &&
				(subEncounters == null || subEncounters.size() == 0) ) {

			// handle the case where the Encounters does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to patient's EncounterActivity, but the submitted C-CDA does not contain EncounterActivity data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires EncounterActivity data but submitted document does not contain EncounterActivity data");

		}else if ((refEncounters == null || refEncounters.size() == 0) &&
				(subEncounters != null && subEncounters.size() > 0) ) {

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require data related to patient's EncounterActivity, but the submitted C-CDA does contain EncounterActivity data, please check if it is appropriate.", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info("Model does not have EncounterActivity for comparison ");

		} else {

			log.info("Model and Submitted CCDA do not have EncounterActivity for comparison ");
		}

	}

	public void compareTreatmentPreferences(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		log.info("Retrieving Treatment Preferences for comparison ");
		HashMap<String, CCDATreatmentInterventionPreference> refTreatments = this.getAllTreatmentInterventionPreferences();
		HashMap<String, CCDATreatmentInterventionPreference> subTreatments = submittedCCDA.getAllTreatmentInterventionPreferences();

		if( (refTreatments != null && refTreatments.size() > 0) &&
			(subTreatments != null && subTreatments.size() > 0)  ) {

			log.info("Treatment Intervention Preference present in both models ");
			CCDATreatmentInterventionPreference.compare(refTreatments, subTreatments, results);

		}
		else if ( (refTreatments != null && refTreatments.size() > 0) &&
				(subTreatments == null || subTreatments.size() == 0) ) {

			// handle the case where the Treatment Preferences does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to the patient's Treatment Intervention Preferences"
					+ "but the submitted C-CDA does not contain Treatment Intervention Preference data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Treatment Intervention Preference data, but submitted document does not contain Treatment Intervention Preference data");

		}else if ((refTreatments == null || refTreatments.size() == 0) &&
				(subTreatments != null && subTreatments.size() > 0) ) {


			log.info("Model does not have Treatment Intervention Preference data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have Treatment Intervention Preference for comparison ");
		}
	}

	public void compareCareExperiencePreferences(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		log.info("Retrieving Care Experience Preferences for comparison ");
		HashMap<String, CCDACareExperiencePreference> refExperiences = this.getAllCareExperiencePreferences();
		HashMap<String, CCDACareExperiencePreference> subExperiences = submittedCCDA.getAllCareExperiencePreferences();

		if( (refExperiences != null && refExperiences.size() > 0) &&
			(subExperiences != null && subExperiences.size() > 0)  ) {

			log.info("Care Experience Preference present in both models ");
			CCDACareExperiencePreference.compare(refExperiences, subExperiences, results);

		}
		else if ( (refExperiences != null && refExperiences.size() > 0) &&
				(subExperiences == null || subExperiences.size() == 0) ) {

			// handle the case where the Treatment Preferences does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to the patient's Care Experience Preferences"
					+ "but the submitted C-CDA does not contain Care Experience Preference data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Care Experience Preference data, but submitted document does not contain Care Experience Preference data");

		}else if ((refExperiences == null || refExperiences.size() == 0) &&
				(subExperiences != null && subExperiences.size() > 0) ) {


			log.info("Model does not have Care Experience Preference data for comparison, allow this to pass");

		} else {

			log.info("Model and Submitted CCDA do not have Care Experience Preference for comparison ");
		}
	}

	private HashMap<String, CCDATreatmentInterventionPreference> getAllTreatmentInterventionPreferences() {

		HashMap<String, CCDATreatmentInterventionPreference> treatments = new HashMap<>();
		if(treatmentPreferences != null && treatmentPreferences.size() > 0) {

			for(CCDATreatmentInterventionPreference pref : treatmentPreferences) {

				if(pref.getTreatmentPreferenceCode() != null &&
						pref.getTreatmentPreferenceCode().getCode() != null &&
						!pref.getTreatmentPreferenceCode().getCode().isEmpty()) {
					treatments.put(pref.getTreatmentPreferenceCode().getCode(), pref);
				}
			}
		}

		return treatments;
	}

	private HashMap<String, CCDACareExperiencePreference> getAllCareExperiencePreferences() {

		HashMap<String, CCDACareExperiencePreference> carePrefs = new HashMap<>();
		if(carePreferences != null && carePreferences.size() > 0) {

			for(CCDACareExperiencePreference pref : carePreferences) {

				if(pref.getCareExperiencePreferenceCode() != null &&
						pref.getCareExperiencePreferenceCode().getCode() != null &&
						!pref.getCareExperiencePreferenceCode().getCode().isEmpty()) {
					carePrefs.put(pref.getCareExperiencePreferenceCode().getCode(), pref);
				}
			}
		}

		return carePrefs;
	}

	public HashMap<String, CCDAEncounterActivity> getAllEncounterActivities() {

		if(encounter != null) {
			return encounter.getAllEncounterActivities();
		}

		return null;

	}

	public HashMap<String, PlannedProcedure> getAllPlannedProcedures() {
	    boolean hasPlan = getPlanOfTreatment() != null;
	    boolean hasAssessment = getAssessmentAndPlanSection() != null;

	    if (hasPlan && hasAssessment) {
	        HashMap<String, PlannedProcedure> combined = new HashMap<>(getPlanOfTreatment().getAllPlannedProcedures());
	        combined.putAll(getAssessmentAndPlanSection().getAllPlannedProcedures());
	        return combined;
	    } else if (hasPlan) {
	        return getPlanOfTreatment().getAllPlannedProcedures();
	    } else if (hasAssessment) {
	        return getAssessmentAndPlanSection().getAllPlannedProcedures();
	    } else {
	        return new HashMap<>(); // Return empty map instead of null
	    }
	}

	public HashMap<String, CCDAProblemObs> getAllHealthConcernActs() {

		if(this.getHcs() != null) {
			return getHcs().getAllHealthConcernActs();
		}
		else
			return null;
	}


	public HashMap<String, GoalObservation> getAllGoalObservations() {

		if(this.getGoals() != null) {
			return getGoals().getAllGoalObservations();
		}
		else
			return null;
	}

	public HashMap<String, CCDASexualOrientation> getAllSexualOrientations() {

		if(this.getSocialHistory() != null) {
			return getSocialHistory().getAllSexualOrientations();
		}
		else
			return null;
	}

	public HashMap<String, CCDAGenderIdentityObs> getAllGenderIdentities() {

		if(this.getSocialHistory() != null) {
			log.info(" Social History Not null ");
			return getSocialHistory().getAllGenderIdentities();
		}
		else
			return null;
	}

	public HashMap<String, AssessmentScaleObservation> getAssessmentScaleObservations() {

		HashMap<String, AssessmentScaleObservation> assessments = new HashMap<>();
		if(this.getSocialHistory() != null) {
			HashMap<String, AssessmentScaleObservation> socAssessments = new HashMap<>();
			socAssessments = getSocialHistory().getAllSdohData();
			if(socAssessments != null && socAssessments.size() > 0) {
				assessments.putAll(socAssessments);
			}
			if(this.getSocialHistory().getAssessmentScaleObservations() != null && !this.getSocialHistory().getAssessmentScaleObservations().isEmpty()) {

				for(AssessmentScaleObservation obs : this.getSocialHistory().getAssessmentScaleObservations()) {
					if(obs.getAssessmentCode() != null && obs.getAssessmentCode().getCode() != null) {
						assessments.put(obs.getAssessmentCode().getCode(), obs);
					}
				}
			}

		}

		if(this.getProblem() != null) {
			HashMap<String, AssessmentScaleObservation> probAssessments = new HashMap<>();
			probAssessments = this.getProblem().getAllSdohData();
			if(probAssessments != null && probAssessments.size() > 0) {
				assessments.putAll(probAssessments);
			}
		}

		if(this.getProcedure() != null) {
			HashMap<String, AssessmentScaleObservation> procAssessments = new HashMap<>();
			procAssessments = getProcedure().getAllSdohData();
			if(procAssessments != null && procAssessments.size() > 0) {
				assessments.putAll(procAssessments);
			}
		}

		return assessments;
	}



	public void compareCareTeamMembers(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023) {

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

	public void compareNotesActivities(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate,
			ArrayList<CCDAAuthor> submittedAuthorsWithLinkedReferenceData, boolean svap2022, boolean svap2023) {

		log.info("Retrieving Notes Section for comparison ");

		HashMap<String, CCDANotesActivity> refNotesActs = this.getAllNotesActivities();
		HashMap<String, CCDANotesActivity> subNotesActs= submittedCCDA.getAllNotesActivities();

		if( (refNotesActs != null && refNotesActs.size() > 0) &&
			(subNotesActs != null && subNotesActs.size() > 0)  ) {

			log.info("Notes present in both models ");
			CCDANotesActivity.compareNotesActivities(refNotesActs, subNotesActs, results,
					submittedAuthorsWithLinkedReferenceData);

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

		} else if ((refNotesActs == null || refNotesActs.size() == 0) &&
				(subNotesActs != null && subNotesActs.size() > 0) ) {
			log.info("Model does not have Notes for comparison, it is OK for submitted C-CDA to include notes for all occasions");
		} else {
			log.info("Model and Submitted C-CDA do not have Notes for comparison ");
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
		HashMap<String, CCDANotesActivity> results = new HashMap<String, CCDANotesActivity>();
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

	private HashMap<String, CCDASpecimen> getAllSpecimens() {

		if(labResults != null) {

			return labResults.getAllSpecimens();
		}

		return null;
	}

	private ArrayList<CCDAParticipant> getAllRelatedPersons() {

		if(relatedPersons != null)
			return relatedPersons;
		else
			return null;

	}

	private void logSubmittedAuthorsWithLinkedReferenceData(ArrayList<CCDAAuthor> authorsWithLinkedReferenceData) {
		log.info("logSubmittedAuthorsWithLinkedReferenceData: ");
		if (authorsWithLinkedReferenceData != null) { // since set in constructor to a new list, this shouldn't happen
			if (!authorsWithLinkedReferenceData.isEmpty()) {
				int curAuth = 1;
				for (CCDAAuthor auth : authorsWithLinkedReferenceData) {
					log.info("Linked Sub Auth #" + curAuth);
					auth.log();
					curAuth++;
				}
			} else {
				log.info("Submitted authorsWithLinkedReferenceData is empty");
			}
		} else {
			log.info("Submitted authorsWithLinkedReferenceData is null");
		}
	}

	public void compareAuthorEntries(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate,
			ArrayList<CCDAAuthor> submittedAuthorsWithLinkedReferenceData, boolean svap2022, boolean svap2023) {
		ArrayList<CCDAAuthor> refAuths = this.getAuthorsFromHeader();
		ArrayList<CCDAAuthor> subAuths = submittedCCDA.getAuthorsFromHeader();

		log.info("Retrieving Author Entries for comparison ");
		if( (refAuths != null && refAuths.size() > 0) &&
			(subAuths != null && subAuths.size() > 0)  ) {
			log.info("Authors present in both models ");
			String elName = "Document Level";
			// Although very unlikely there would be a linked reference in the header level author, we offer support for it
			CCDAAuthor.compareAuthors(refAuths, subAuths, results, elName, submittedAuthorsWithLinkedReferenceData);
		} else if ( (refAuths != null && refAuths.size() > 0) &&
				    (subAuths == null || subAuths.size() == 0) ) {
			// Handle the case where the Notes section does not exist in the submitted CCDA
			ContentValidationResult rs = new ContentValidationResult("The scenario requires data related to Author (Provenance), "
					+ "but the submitted C-CDA does not contain Author data.", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			log.info(" Scenario requires Author data, but submitted document does not contain Author data");
		} else if ( (refAuths == null || refAuths.size() == 0) &&
				    (subAuths != null && subAuths.size() > 0) ) {
			log.info("Model does not have Authors for comparison, it is OK for submitted CCDA to include Authors for all occasions");
		} else {
			log.info("Model and Submitted CCDA do not have Authors for comparison ");
		}

		// Links to linked author references are most likely located in these locations
		// Note: Section level provenance requirements for author only check if author exists at all.
		// So, no linked reference check is required for Section Level.
		// This is why CCDAAuthor.compareSectionLevelAuthor is not passed submittedAuthorsWithLinkedReferenceData.
		// And, why CCDAAuthor.compareAuthors, is, since, more details are required for entry and below
		// to pass provenance requirements, such as repOrg name (and time, but no need for a link there).
		compareSectionAndEntryLevelProvenance(validationObjective, submittedCCDA, results, curesUpdate,
				submittedAuthorsWithLinkedReferenceData, svap2022, svap2023);

		// Note Activity can be in many places and can have authors which must meet provenance reqs
		// Disabling for now as using old Note Activity specific (compare) impl to hanlde
		// Note: This version is a work in progress and should not be used unless finished and removing old impl
//		compareNoteActivityProvenance(validationObjective, submittedCCDA, results, curesUpdate,
//				submittedAuthorsWithLinkedReferenceData, svap2022, svap2023);
	}

	// WIP: The idea is for this to replace the current provenance org name checks specific to notes activities class
	private void compareNoteActivityProvenance(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate,
			ArrayList<CCDAAuthor> submittedAuthorsWithLinkedReferenceData, boolean svap2022, boolean svap2023) {
		if (notesEntries != null) {
			for (CCDANotesActivity noteActivity : notesEntries) {
				if (submittedCCDA.getNotesEntries() != null) {
					for (CCDANotesActivity subNoteActivity : submittedCCDA.getNotesEntries()) {
						noteActivity.compareAuthor(subNoteActivity, results, curesUpdate,
								submittedAuthorsWithLinkedReferenceData, svap2022, svap2023);
					}
				} else {
					// error? some note activity is missing since there is at least one in ref...?
				}
			}
		}
	}

	public void compareSectionAndEntryLevelProvenance(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate,
			ArrayList<CCDAAuthor> submittedAuthorsWithLinkedReferenceData, boolean svap2022, boolean svap2023) {
		if (allergy != null)
			allergy.compareAuthor(submittedCCDA.getAllergy() != null ? submittedCCDA.getAllergy() : null,
					results, curesUpdate, submittedAuthorsWithLinkedReferenceData, svap2022, svap2023);
		if (problem != null)
			problem.compareAuthor(submittedCCDA.getProblem() != null ? submittedCCDA.getProblem() : null,
					results, curesUpdate, submittedAuthorsWithLinkedReferenceData, svap2022, svap2023);
		if (procedure != null)
			procedure.compareAuthor(submittedCCDA.getProcedure() != null ? submittedCCDA.getProcedure() : null,
					results, curesUpdate, submittedAuthorsWithLinkedReferenceData, svap2022, svap2023); // TODO-db: Finish if required: PAP/UDI, PAP/Notes, PAAct?, PAObs?
		if (medication != null)
			medication.compareAuthor(submittedCCDA.getMedication() != null ? submittedCCDA.getMedication() : null,
					results, curesUpdate, submittedAuthorsWithLinkedReferenceData, svap2022, svap2023); // TODO-db: Look at parser, may be more authors to collect
		if (immunization != null)
			immunization.compareAuthor(submittedCCDA.getImmunization() != null ? submittedCCDA.getImmunization() : null,
					results, curesUpdate, submittedAuthorsWithLinkedReferenceData, svap2022, svap2023);
		if (labResults != null)
			labResults.compareAuthor(submittedCCDA.getLabResults() != null ? submittedCCDA.getLabResults() : null,
					results, curesUpdate, submittedAuthorsWithLinkedReferenceData, svap2022, svap2023);
		if (vitalSigns != null)
			vitalSigns.compareAuthor(submittedCCDA.getVitalSigns() != null ? submittedCCDA.getVitalSigns() : null,
					results, curesUpdate, submittedAuthorsWithLinkedReferenceData, svap2022, svap2023);
		if (encounter != null)
			encounter.compareAuthor(submittedCCDA.getEncounter() != null ? submittedCCDA.getEncounter() : null,
					results, curesUpdate, submittedAuthorsWithLinkedReferenceData, svap2022, svap2023); // TODO-db: Consider adding remaining items in EncounterParser (multiple problem observations, see retrieveAdmissionDiagnosisDetails and below)
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

	private void validateBirthSex(CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results,
			boolean curesUpdate, boolean svap2022, boolean svap2023) {

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

	private void validateSmokingStatus(CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results,
			boolean curesUpdate, boolean svap2022, boolean svap2023) {

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

	private void compareEncounterDiagnosis(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean svap2022, boolean svap2023) {

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
				CCDAProblemObs.compareProblemObservationData(refDiags, subDiags, results, context, svap2022, svap2023);


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


	private void comparePatients(CCDARefModel submittedCCDA, ArrayList<ContentValidationResult> results,
			boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		if((patient != null) && (submittedCCDA.getPatient() != null)) {
			this.patient.compare(submittedCCDA.getPatient(), results, submittedCCDA, curesUpdate, svap2022, svap2023, svap2024);
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

		if(notes != null) {
		for(int k = 0; k < notes.size(); k++) {

			notes.get(k).log();
		}
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

	public void performCIRIValidation(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024)
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

		log.info("Comparing Problems ");
		compareProblems(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

		log.info("Comparing Allergies ");
		compareAllergies(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023);

		log.info("Comparing Medications ");
		compareMedications(validationObjective, submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

		log.info("Finished comparison , returning results");

	}

	public void performCarePlanValidation(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024)
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

		log.info("Comparing CarePlan Sections");
		compareCarePlanSections(submittedCCDA, results);

		log.info("Finished comparison , returning results");

	}

	public void performDS4PValidation(String validationObjective, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024)
	{
		log.info("Comparing Patient Data ");
		comparePatients(submittedCCDA, results, curesUpdate, svap2022, svap2023, svap2024);

		log.info("Finished comparison , returning results");

	}

	public void compareNonCCDSStructuredData(String valObj, CCDARefModel submittedCCDA,
			ArrayList<ContentValidationResult> results, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024)
	{
		// validate encounter diagnosis.
		if(valObj.equalsIgnoreCase("170.315_b1_ToC_Amb") ||
				valObj.equalsIgnoreCase("170.315_b1_ToC_Inp") ||
				valObj.equalsIgnoreCase("170.315_b4_CCDS_Amb") ||
				valObj.equalsIgnoreCase("170.315_b4_CCDS_Inp") ||
				valObj.equalsIgnoreCase("170.315_b6_DE_Amb") ||
				valObj.equalsIgnoreCase("170.315_b6_DE_Inp") ) {

			log.info("Comparing Encounter Diagnosis for b1, b4, and b6 ");
			compareEncounterDiagnosis(valObj, submittedCCDA, results, svap2022, svap2023);
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

	public CCDAPayers getPayers() {
		return payers;
	}

	public void setPayers(CCDAPayers payers) {
		this.payers = payers;
	}

	public ArrayList<CCDAII> getCcdTemplates() {
		return ccdTemplates;
	}

	public void setCcdTemplates(ArrayList<CCDAII> ccdTemplates) {
		this.ccdTemplates = ccdTemplates;
	}

	public ArrayList<CCDAII> getDsTemplates() {
		return dsTemplates;
	}

	public void setDsTemplates(ArrayList<CCDAII> dsTemplates) {
		this.dsTemplates = dsTemplates;
	}

	public ArrayList<CCDAII> getRnTemplates() {
		return rnTemplates;
	}

	public void setRnTemplates(ArrayList<CCDAII> rnTemplates) {
		this.rnTemplates = rnTemplates;
	}

	public ArrayList<CCDAII> getCpTemplates() {
		return cpTemplates;
	}

	public void setCpTemplates(ArrayList<CCDAII> cpTemplates) {
		this.cpTemplates = cpTemplates;
	}

	public CCDAReasonForReferral getReferrals() {
		return referrals;
	}

	public void setReferrals(CCDAReasonForReferral referrals) {
		this.referrals = referrals;
	}

	public CCDAFunctionalStatus getFunctionalStatus() {
		return functionalStatus;
	}

	public void setFunctionalStatus(CCDAFunctionalStatus functionalStatus) {
		this.functionalStatus = functionalStatus;
	}

	public CCDAMentalStatus getMentalStatus() {
		return mentalStatus;
	}

	public void setMentalStatus(CCDAMentalStatus mentalStatus) {
		this.mentalStatus = mentalStatus;
	}

	public ArrayList<CCDAParticipant> getRelatedPersons() {
		return relatedPersons;
	}

	public void setRelatedPersons(ArrayList<CCDAParticipant> relatedPersons) {
		this.relatedPersons = relatedPersons;
	}

	public ArrayList<CCDATreatmentInterventionPreference> getTreatmentPreferences() {
		return treatmentPreferences;
	}

	public void setTreatmentPreferences(ArrayList<CCDATreatmentInterventionPreference> treatmentPreferences) {
		this.treatmentPreferences = treatmentPreferences;
	}

	public ArrayList<CCDACareExperiencePreference> getCarePreferences() {
		return carePreferences;
	}

	public void setCarePreferences(ArrayList<CCDACareExperiencePreference> carePreferences) {
		this.carePreferences = carePreferences;
	}


	public CCDAAssessmentAndPlanSection getAssessmentAndPlanSection() {
		return assessmentAndPlanSection;
	}

	public void setAssessmentAndPlanSection(CCDAAssessmentAndPlanSection assessmentAndPlanSection) {
		this.assessmentAndPlanSection = assessmentAndPlanSection;
	}

}
