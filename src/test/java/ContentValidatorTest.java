import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.dto.enums.SeverityLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.service.ContentValidatorService;

public class ContentValidatorTest extends ContentValidatorTester {	
	
	private static final String PRE_CURES_SCENARIO_DIRECTORY = TEST_RESOURCES_DIRECTORY + "/preCures/ref";
	private static HashMap<String, CCDARefModel> refModelHashMap = loadAndParseScenariosAndGetRefModelHashMap(
			PRE_CURES_SCENARIO_DIRECTORY);
	private static ContentValidatorService validator = new ContentValidatorService(refModelHashMap);
	static {
		println();
		println("Keys: " + refModelHashMap.keySet());
		println("Values: " + refModelHashMap.values());
	}
	
	private static final String DEFAULT_VALIDATION_OBJECTIVE = "170.315_b1_ToC_Amb";
	private static final String VO_TOC_AMBULATORY = DEFAULT_VALIDATION_OBJECTIVE;
	private static final String VO_CAREPLAN_AMBULATORY = "170.315_b9_CP_Amb";
	private static final String VO_CAREPLAN_INPATIENT = "170.315_b9_CP_Inp";
	private static final String VO_E1_VDT_AMBULATORY = "170.315_e1_VDT_Amb";
	private static final String VO_E1_VDT_INPATIENT = "170.315_e1_VDT_Inp";	
	
	/*
	 * List of scenarios aka reference files
	 * Note that the key in the map only needs to <i>contain</i> these strings in part of its name. 
	 * This is why extensions or specifics beyond that are not an issue.
	 * 170.315_b4_ccds_create_amb_sample1, 170.315_b1_toc_inp_sample2, 170.315_b4_ccds_create_amb_sample2, 170.315_b1_toc_inp_sample1, 
	 * 170.315_b7_ds4p_inp_sample1, 170.315_b7_ds4p_inp_sample2, 170.315_b4_ccds_create_inp_sample1, 170.315_e1_vdt_inp_sample2, 
	 * 170.315_b2_ciri__r21_sample1_rn, 170.315_e1_vdt_inp_sample1, 170.315_b6_de_inp_sample2, 170.315_b6_de_inp_sample1, 
	 * 170.315_b7_ds4p_amb_sample1, 170.315_b6_de_amb_sample1, 170.315_b7_ds4p_amb_sample2, 170.315_b9_cp_amb_sample1, 
	 * 170.315_b9_cp_inp_sample1, 170.315_b1_toc_amb_sample1, 170.315_g9_api_access_inp_sample2, 170.315_g9_api_access_inp_sample1, 
	 * 170.315_b1_toc_amb_sample2, 170.315_b6_de_amb_sample2, 170.315_b2_ciri__r11_sample1, 170.315_e1_vdt_amb_sample1, 
	 * 170.315_e1_vdt_amb_sample2, 170.315_b2_ciri__r21_sample1_ds, 170.315_b4_ccds_create_inp_sample2, 170.315_g9_api_access_amb_sample2, 
	 * 170.315_b2_ciri__r21_sample1_ccd, 170.315_g9_api_access_amb_sample1
	*/

	private static final String DEFAULT_REFERENCE_FILENAME = "170.315_b1_toc_amb_sample1_v13.pdf";
	private static final String REF_TOC_AMBULATORY = DEFAULT_REFERENCE_FILENAME;	
	private static final String REF_CAREPLAN_AMBULATORY = "170.315_b9_cp_amb_sample1.xml";
	private static final String REF_CAREPLAN_INPATIENT = "170.315_b9_cp_inp_sample1.xml";
	private static final String REF_CAREPLAN_NO_INTERVENTION_OR_HEALTH_STATUS = REF_TOC_AMBULATORY;
	private static final String REF_CAREPLAN_NO_INTERVENTION_HAS_HEALTH_STATUS = "ModRef_cp_NoInterventionsHasHealthStatus.xml";
	private static final String REF_CAREPLAN_NO_HEALTH_STATUS_HAS_INTERVENTION = "ModRef_cp_NoHealthStatusHasIntervention.xml";
	private static final String REF_E1_VDT_AMBULATORY = "170.315_e1_vdt_amb_sample1.xml";
	private static final String REF_E1_VDT_INPATIENT = "170.315_e1_vdt_inp_sample2.xml";
	
	private static final String MOD_REF_ADDBIRTHSEX_B1_TOC_AMB_SAMPLE1 = "ModRef_AddBirthSex_b1TocAmbSample1.xml";
	private static final String MOD_REF_NO_BIRTH_SEX_B1_TOC_AMB_SAMPLE1 = "ModRef_NonCures_NoBirthSex_b1TocAmbSample1.xml";
	
	/**
	 * One example of many related issues:
	 * Expected (170.315_b1_toc_amb_sample1.xml test scenario):
	 * 	<telecom value="tel:+1(555)-777-1234" use="MC"/>
	 * 	<telecom value="tel:+1(555)-723-1544" use="HP"/>
	 * XML: 170.315_b1_toc_amb_ccd_r21_sample1_v10.xml
	 * 	<telecom value="tel:+1(555)-111-1234" use="HP"/> <!-- use is HP instead of MC -dbTest -->
	 * 	<telecom value="tel:+1(555)-112-1544" use="HP"/> <!-- value is 964-4466 instead of 112-1544 -dbTest -->
	*/
	private static final int SUB_HAS_TELECOM_MISMATCHES = 0;
	private static final int SUB_NO_BIRTH_SEX = SUB_HAS_TELECOM_MISMATCHES;
	private static final int SUB_HAS_INTERVENTIONS_AND_HEALTH_STATUS_AND_IS_CP_AMB = 1;
	private static final int SUB_MISSING_INTERVENTIONS_AND_HAS_HEALTH_STATUS_AND_IS_CP_AMB = 2;
	private static final int SUB_MISSING_HEALTH_STATUS_AND_HAS_INTERVENTIONS_AND_IS_CP_AMB = 3;
	private static final int SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS = 4;
	private static final int SUB_B1_TOC_AMB_SAMPLE1_ADD_BIRTH_SEX = 5;

	private static URI[] SUBMITTED_CCDA = new URI[0];
	static {
		try {
			SUBMITTED_CCDA = new URI[] {
					ContentValidatorTest.class.getResource("preCures/sub/170.315_b1_toc_amb_sample1_Submitted_T1.xml").toURI(),
					ContentValidatorTest.class.getResource("preCures/sub/170.315_b9_cp_amb_sample1_v5.xml").toURI(),
					ContentValidatorTest.class.getResource("preCures/sub/170.315_b9_cp_amb_sample1_v5_Remove_Interventions.xml").toURI(),
					ContentValidatorTest.class.getResource("preCures/sub/170.315_b9_cp_amb_sample1_v5_Remove_HealthStatus.xml").toURI(),
					ContentValidatorTest.class.getResource("preCures/sub/NT_CCDS_Sample1_r21_v4.xml").toURI(),
					ContentValidatorTest.class.getResource("preCures/sub/170.315_b1_toc_amb_sample1_Submitted_T1_AddBirthSex.xml").toURI()
			};
		} catch (URISyntaxException e) {
			if(LOG_RESULTS_TO_CONSOLE) e.printStackTrace();
		}
	}
	
	private static final URI DEFAULT_SUBMITTED_CCDA = SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES];		
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String referenceFileName, String ccdaFileAsString) {
		return validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, referenceFileName, ccdaFileAsString);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String validationObjective, 
			String referenceFileName, String ccdaFileAsString) {
		return validateDocumentAndReturnResults(validationObjective, referenceFileName, ccdaFileAsString, SeverityLevel.INFO);
	}
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String validationObjective, 
			String referenceFileName, String ccdaFileAsString, SeverityLevel severityLevel) {
		return validator.validate(validationObjective, referenceFileName, ccdaFileAsString, false, false, severityLevel);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String referenceFileName, URI ccdaFileURI) {
		return validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, referenceFileName, ccdaFileURI);
	}
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String validationObjective, 
			String referenceFileName, URI ccdaFileURI) {
		return validateDocumentAndReturnResults(validationObjective, referenceFileName, ccdaFileURI, SeverityLevel.INFO);
	}
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String validationObjective, 
			String referenceFileName, URI ccdaFileURI, SeverityLevel severityLevel) {
		String ccdaFileAsString = convertCCDAFileToString(ccdaFileURI);
		return validateDocumentAndReturnResults(validationObjective, referenceFileName, ccdaFileAsString, severityLevel);
	}    
    
	@Test
	public void stringConversionAndResultsSizeTest() {
		printHeader("stringConversionAndResultsSizeTest");
		
		String ccdaFileAsString = convertCCDAFileToString(DEFAULT_SUBMITTED_CCDA);
		println("submitted ccdaFileAsString: " + ccdaFileAsString);
		assertFalse(
				"The C-CDA file String conversion failed as no data was captured",
				ccdaFileAsString.isEmpty());
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResults(DEFAULT_REFERENCE_FILENAME, ccdaFileAsString);
		assertFalse("No results were returned", results.isEmpty());
		
		printResults(results);
		
		println("***************** No Exceptions were thrown during the test******************"
						+ System.lineSeparator() + System.lineSeparator());
	}
	
	@Test
	public void telecomTest() {
		printHeader("telecomTest");
		
		// non-cures enforces a WARNING for telecom issues as opposed to an ERROR with cures
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResults(DEFAULT_REFERENCE_FILENAME,
				DEFAULT_SUBMITTED_CCDA);
		assertFalse("No results were returned", results.isEmpty());
		println("FINAL RESULTS");
		println("No of Entries = " + results.size());
		
		final String telecomMessage = "Patient Telecom in the submitted file does not match the expected Telecom";
		assertTrue("The results do not contain the expected message of: " + telecomMessage, 
				resultsContainMessage(telecomMessage, results, ContentValidationResultLevel.WARNING));
		
		printResults(results);
	}
	
	@Test
	public void carePlanSectionsTest() {
		printHeader("carePlanSectionsTest");
		
		final String healthStatusWarning = "A Care Plan section is missing: The scenario contains the Health Status Evaluations and Outcomes Section 2.16.840.1.113883.10.20.22.2.61, "
				+ "but it was not found in the submitted document";
		final String interventionsWarning = "A Care Plan section is missing: The scenario contains the Interventions Section (V3) 2.16.840.1.113883.10.20.21.2.3:2015-08-01, "
				+ "but it was not found in the submitted document";
		String carePlanMessage = "A Care Plan section is missing";
		
		ArrayList<ContentValidationResult> results;
		
		// basic and altered sub tests
		
		printHeader("unrelated objective, and, ref is missing sections so not required in sub, "
				+ "expect no related warnings for missing cp sections");
		results = validateDocumentAndReturnResults(DEFAULT_REFERENCE_FILENAME, DEFAULT_SUBMITTED_CCDA);		
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
						
		printHeader("cp amb, has both in ref and sub, expect no related warnings for missing cp sections");
		results = validateDocumentAndReturnResults(VO_CAREPLAN_AMBULATORY, REF_CAREPLAN_AMBULATORY,
				SUBMITTED_CCDA[SUB_HAS_INTERVENTIONS_AND_HEALTH_STATUS_AND_IS_CP_AMB]);
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));

		printHeader("cp inp, has both in ref and sub, expect no related warnings for missing cp sections");
		results = validateDocumentAndReturnResults(VO_CAREPLAN_INPATIENT, REF_CAREPLAN_INPATIENT,
				SUBMITTED_CCDA[SUB_HAS_INTERVENTIONS_AND_HEALTH_STATUS_AND_IS_CP_AMB]);
		printResults(results);
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		
		printHeader("cp amb, missing interventions, has health status, expect a related warning for missing interventions section");
		results = validateDocumentAndReturnResults(VO_CAREPLAN_AMBULATORY, REF_CAREPLAN_AMBULATORY,
				SUBMITTED_CCDA[SUB_MISSING_INTERVENTIONS_AND_HAS_HEALTH_STATUS_AND_IS_CP_AMB]);
		printResults(results);
		carePlanMessage = interventionsWarning;
		assertTrue("The results do not contain the expected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		carePlanMessage = healthStatusWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));		
		
		printHeader("unrelated objective, missing interventions, has health status, HOWEVER, due to objective expect no related warning");
		results = validateDocumentAndReturnResults(VO_TOC_AMBULATORY, REF_CAREPLAN_AMBULATORY,
				SUBMITTED_CCDA[SUB_MISSING_INTERVENTIONS_AND_HAS_HEALTH_STATUS_AND_IS_CP_AMB]);
		printResults(results);
		carePlanMessage = interventionsWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		carePlanMessage = healthStatusWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		
		
		printHeader("cp amb, missing interventions, has health status, expect a related warning for missing interventions section");
		results = validateDocumentAndReturnResults(VO_CAREPLAN_INPATIENT, REF_CAREPLAN_AMBULATORY,
				SUBMITTED_CCDA[SUB_MISSING_HEALTH_STATUS_AND_HAS_INTERVENTIONS_AND_IS_CP_AMB]);
		printResults(results);
		carePlanMessage = healthStatusWarning;
		assertTrue("The results do not contain the expected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		carePlanMessage = interventionsWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));				
		 
		printHeader("cp amb, has both interventions and health status in ref, neither in submitted "
				+ "expect a related warning for missing both sections");
		results = validateDocumentAndReturnResults(VO_CAREPLAN_AMBULATORY, REF_CAREPLAN_AMBULATORY,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES]);
		printResults(results);
		carePlanMessage = interventionsWarning;
		assertTrue("The results do not contain the expected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		carePlanMessage = healthStatusWarning;
		assertTrue("The results do not contain the expected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));		
		
		// altered reference tests
		
		printHeader("cp amb, Ref does NOT have interventions or health status, both are in submitted "
				+ "expect no related warnings for missing both sections - "
				+ "The scenario (ref) does not have any suggested Care Plan sections thus no requirment is enforced");
		results = validateDocumentAndReturnResults(VO_CAREPLAN_AMBULATORY, REF_CAREPLAN_NO_INTERVENTION_OR_HEALTH_STATUS,
				SUBMITTED_CCDA[SUB_HAS_INTERVENTIONS_AND_HEALTH_STATUS_AND_IS_CP_AMB]);
		printResults(results);
		carePlanMessage = interventionsWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		carePlanMessage = healthStatusWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		
		printHeader("cp amb, Neither the Ref Nor the Submitted file have interventions or health status "
				+ "expect no related warnings for missing both sections - "
				+ "The data for Both the Ref Model and the Submitted Care Plan Sections is null. No requirment needs to be enforced");
		results = validateDocumentAndReturnResults(VO_CAREPLAN_AMBULATORY, REF_CAREPLAN_NO_INTERVENTION_OR_HEALTH_STATUS,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES]);
		printResults(results);
		carePlanMessage = interventionsWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		carePlanMessage = healthStatusWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
				
		printHeader("ref is missing interventions but has health status / "
				+ "submitted is missing interventions and is missing health status - " +
				"since ref has no interventions, we only enforce the missing health status");
		results = validateDocumentAndReturnResults(VO_CAREPLAN_INPATIENT, REF_CAREPLAN_NO_INTERVENTION_HAS_HEALTH_STATUS,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES]);
		printResults(results);
		carePlanMessage = healthStatusWarning;
		assertTrue("The results do not contain the expected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		printResults(results);
		
		carePlanMessage = interventionsWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		
		printHeader("ref is missing health status but has interventions / "
				+ "submitted is missing health status and is missing interventions - " +
				"since ref has no health status, we only enforce the missing interventions");
		results = validateDocumentAndReturnResults(VO_CAREPLAN_INPATIENT, REF_CAREPLAN_NO_HEALTH_STATUS_HAS_INTERVENTION,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES]);
		printResults(results);
		carePlanMessage = interventionsWarning;		
		assertTrue("The results do not contain the expected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		carePlanMessage = healthStatusWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		
		printHeader("ref is missing both health status and interventions / "
				+ "submitted has health status only. " +
				" Since ref has no neither section, interventions is not enforced for being missing");
		results = validateDocumentAndReturnResults(VO_CAREPLAN_AMBULATORY, REF_CAREPLAN_NO_INTERVENTION_OR_HEALTH_STATUS,
				SUBMITTED_CCDA[SUB_MISSING_INTERVENTIONS_AND_HAS_HEALTH_STATUS_AND_IS_CP_AMB]);
		printResults(results);
		carePlanMessage = interventionsWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
		carePlanMessage = healthStatusWarning;
		assertFalse("The results contain the unexpected message of: " + carePlanMessage, 
				resultsContainMessage(carePlanMessage, results, ContentValidationResultLevel.WARNING));
	}

	@Test
	public void encounterDiagnosisTest() {
		printHeader("encounterDiagnosisTest");

		final String encounterDiagnosisMessage = "Encounter Diagnosis data";
		
		// has an objective which should NOT validate encounter diagnosis in ANY case, expect NO related error(s)
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResults(VO_E1_VDT_AMBULATORY,
				REF_E1_VDT_AMBULATORY, SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS]);
		printResults(results);
		assertFalse("No results were returned", results.isEmpty());
		assertFalse("The results contain the UNexpected message of: " + encounterDiagnosisMessage,
				resultsContainMessage(encounterDiagnosisMessage, results));
		
		results = validateDocumentAndReturnResults(VO_E1_VDT_INPATIENT, REF_E1_VDT_INPATIENT,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS]);
		printResults(results);
		assertFalse("The results contain the UNexpected message of: " + encounterDiagnosisMessage,
				resultsContainMessage(encounterDiagnosisMessage, results));
		
		// has an objective which validates encounter diagnosis, expect related error(s)
		results = validateDocumentAndReturnResults(VO_TOC_AMBULATORY, REF_E1_VDT_AMBULATORY,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS]);
		printResults(results);
		assertTrue("The results do not contain the expected message of: " + encounterDiagnosisMessage,
				resultsContainMessage(encounterDiagnosisMessage, results));
		
		// has an objective which can validate encounter diagnosis in instructed cases (CarePlan), expect related error(s)
		// e.g. 170.315_b1_ToC_Amb, 170.315_b1_ToC_Inp, 170.315_b4_CCDS_Amb, 
		//		170.315_b4_CCDS_Inp, 170.315_b6_DE_Amb, 170.315_b6_DE_Inp
		results = validateDocumentAndReturnResults(VO_CAREPLAN_INPATIENT, REF_E1_VDT_AMBULATORY,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS]);
		printResults(results);
		assertFalse("The results contain the UNexpected message of: " + encounterDiagnosisMessage,
				resultsContainMessage(encounterDiagnosisMessage, results));
	}
	
	@Test
	public void birthSexTest_old_delete_when_birth_sex_added_to_all_refs() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Sub missing birth sex returns an ERROR for curesUpdate or a WARNING for non-cures (2015) 
		// as per regulation https://www.healthit.gov/isa/uscdi-data/birth-sex
		// Note: Even though the birthSexMessage implies birth sex is required because it is in the scenario, 
		// we require it regardless of it being there or not - 
		// the source code (CCDARefModel.validateBirthSex ) purposely does not even reference the scenario, only the submitted file.
		
		String birthSexMessage = "The scenario requires patient's birth sex to be captured as part of social history data, "
				+ "but submitted file does not have birth sex information";
		
		ArrayList<ContentValidationResult> results;		
		
		// *** these tests are NOT written in a future proof manner - although they will likely work after birth sex is added to scenarios, 
		// we need to delete this entire test at that point
		
		printHeader("Ref has birth sex. Sub does not have birth sex. Expect birth sex warning");
		results = validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, MOD_REF_ADDBIRTHSEX_B1_TOC_AMB_SAMPLE1,
				DEFAULT_SUBMITTED_CCDA, SeverityLevel.WARNING);
		printResults(results);
		assertTrue("Expect birth sex warning: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.WARNING));
		
		printHeader("Ref does not have have birth sex. Sub does not have birth sex. "
				+ "Still expect birth sex warning despite ref not requiring it");
		results = validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, DEFAULT_REFERENCE_FILENAME,
				DEFAULT_SUBMITTED_CCDA, SeverityLevel.WARNING);
		printResults(results);
		assertTrue("Expect birth sex warning despite ref not requiring it: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.WARNING));
		
		printHeader("Ref has birth sex. Sub has birth sex. Expect NO birth sex warning as sub has birth sex");
		results = validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, MOD_REF_ADDBIRTHSEX_B1_TOC_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_B1_TOC_AMB_SAMPLE1_ADD_BIRTH_SEX], SeverityLevel.WARNING);
		printResults(results);
		assertFalse("Expect NO birth sex warning as sub has birth sex but got: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.WARNING));				
	}
	
	@Test
	public void birthSexTest() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Sub missing birth sex returns an ERROR for curesUpdate or a WARNING for non-cures (2015) 
		// as per regulation https://www.healthit.gov/isa/uscdi-data/birth-sex
		// Note: Even though the birthSexMessage implies birth sex is required because it is in the scenario, 
		// we require it regardless of it being there or not - 
		// the source code (CCDARefModel.validateBirthSex ) purposely does not even reference the scenario, only the submitted file.
		
		String birthSexMessage = "The scenario requires patient's birth sex to be captured as part of social history data, "
				+ "but submitted file does not have birth sex information";
		
		ArrayList<ContentValidationResult> results;		
		
		// *** these tests are written in a future proof manner, knowing that birth sex will be added to all the scenarios ***
		
		printHeader("Ref has birth sex. Sub does not have birth sex. Expect birth sex warning");
		results = validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, REF_TOC_AMBULATORY,
				SUBMITTED_CCDA[SUB_NO_BIRTH_SEX], SeverityLevel.WARNING);
		printResults(results);
		assertTrue("Expect birth sex warning: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.WARNING));
		
		printHeader("Ref does not have have birth sex. Sub does not have birth sex. "
				+ "Still expect birth sex warning despite ref not requiring it");
		results = validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, MOD_REF_NO_BIRTH_SEX_B1_TOC_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_NO_BIRTH_SEX], SeverityLevel.WARNING);
		printResults(results);
		assertTrue("Expect birth sex warning despite ref not requiring it: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.WARNING));
		
		printHeader("Ref has birth sex. Sub has birth sex. Expect NO birth sex warning as sub has birth sex");
		results = validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, REF_TOC_AMBULATORY,
				SUBMITTED_CCDA[SUB_B1_TOC_AMB_SAMPLE1_ADD_BIRTH_SEX], SeverityLevel.WARNING);
		printResults(results);
		assertFalse("Expect NO birth sex warning as sub has birth sex but got: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.WARNING));				
	}	
	
	@Test
	public void severityLevelLimitTestFileWithThreeWarningsOnly() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// For non-cures, telecom issues are a WARNING vs an error
		
		final String warning1 = "Patient Telecom in the submitted file does not match the expected Telecom. "
				+ "The following values are expected: telecom/@use = MC and telecom/@value = tel:+1(555)-777-1234";
		final String warning2 = "Patient Telecom in the submitted file does not match the expected Telecom. "
				+ "The following values are expected: telecom/@use = HP and telecom/@value = tel:+1(555)-723-1544";
		final String warning3 = "The scenario requires patient's birth sex to be captured as part of social history data, "
				+ "but submitted file does not have birth sex information";

		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE,
				DEFAULT_REFERENCE_FILENAME, DEFAULT_SUBMITTED_CCDA, SeverityLevel.INFO);
		printResults(results);
		assertTrue("expecting 3 warnings", results.size() == 3);
		ContentValidationResultLevel expectedSeverity = ContentValidationResultLevel.WARNING;
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, warning1, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, warning2, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, warning3, expectedSeverity);

		results = validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, DEFAULT_REFERENCE_FILENAME,
				DEFAULT_SUBMITTED_CCDA, SeverityLevel.WARNING);
		printResults(results);
		assertTrue("expecting (the same) 3 warnings", results.size() == 3);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, warning1, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, warning2, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, warning3, expectedSeverity);

		results = validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, DEFAULT_REFERENCE_FILENAME,
				DEFAULT_SUBMITTED_CCDA, SeverityLevel.ERROR);
		printResults(results);
		assertTrue("expecting no warnings or info (and no errors due to no (content) errors in file)",
				results.isEmpty());
	}
	
	@Test
	public void severityLevelLimitTestFileWithErrorsAndWarnings() {
		printHeader("severityLevelLimitTestFileWithErrorsAndWarnings");
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResults(VO_E1_VDT_AMBULATORY, REF_E1_VDT_AMBULATORY ,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS], SeverityLevel.INFO);
		assertFalse("No results were returned", results.isEmpty());
		println("FINAL RESULTS");
		println("No of Entries = " + results.size());
		printResults(results);
		
		// expect warnings and errors (that's the maximum returned for now since content val does not have any info defined yet)
		ContentValidationResultLevel expectedSeverity = ContentValidationResultLevel.WARNING;
		severityLevelLimitTestHelperAssertSeverityOnly(results, expectedSeverity);
		expectedSeverity = ContentValidationResultLevel.ERROR;
		severityLevelLimitTestHelperAssertSeverityOnly(results, expectedSeverity);
		
		results = validateDocumentAndReturnResults(VO_E1_VDT_AMBULATORY, REF_E1_VDT_AMBULATORY ,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS], SeverityLevel.WARNING);
		assertFalse("No results were returned", results.isEmpty());
		println("FINAL RESULTS");
		println("No of Entries = " + results.size());
		printResults(results);
		
		// expect warnings and errors again and ensure no INFO
		expectedSeverity = ContentValidationResultLevel.WARNING;
		severityLevelLimitTestHelperAssertSeverityOnly(results, expectedSeverity);
		expectedSeverity = ContentValidationResultLevel.ERROR;
		severityLevelLimitTestHelperAssertSeverityOnly(results, expectedSeverity);	
		expectedSeverity = ContentValidationResultLevel.INFO;
		assertFalse(
				"The reults contain the unexpected severity of: " + expectedSeverity.name(),
				resultsContainSeverity(results, expectedSeverity));	

		results = validateDocumentAndReturnResults(VO_E1_VDT_AMBULATORY, REF_E1_VDT_AMBULATORY ,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS], SeverityLevel.ERROR);
		assertFalse("No results were returned", results.isEmpty());
		println("FINAL RESULTS");
		println("No of Entries = " + results.size());
		printResults(results);
		
		// expect ERRORS ONLY
		expectedSeverity = ContentValidationResultLevel.ERROR;
		severityLevelLimitTestHelperAssertSeverityOnly(results, expectedSeverity);
		expectedSeverity = ContentValidationResultLevel.WARNING;
		assertFalse(
				"The reults contain the unexpected severity of: " + expectedSeverity.name(),
				resultsContainSeverity(results, expectedSeverity));
		expectedSeverity = ContentValidationResultLevel.INFO;
		assertFalse(
				"The reults contain the unexpected severity of: " + expectedSeverity.name(),
				resultsContainSeverity(results, expectedSeverity));		
	}

}
