import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.sitenv.contentvalidator.configuration.ScenarioLoader;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.parsers.CCDAParser;
import org.sitenv.contentvalidator.service.ContentValidatorService;

@Ignore
public class ContentValidatorTest {
	
	private static HashMap<String, CCDARefModel> refModelHashMap = loadAndParseScenariosAndGetRefModelHashMap();	
	private static ContentValidatorService validator = new ContentValidatorService(refModelHashMap);
	{
		println();
		println("Keys: " + refModelHashMap.keySet());
		println("Values: " + refModelHashMap.values());
	}
	
	private static final boolean LOG_RESULTS_TO_CONSOLE = true;
	
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
	private static final String LOCAL_SCENARIO_DIRECTORY = "C:/Programming/SITE/scenarios/";
	private static final String DEFAULT_REFERENCE_FILENAME = "170.315_b1_toc_amb_sample1_v13.pdf";
	private static final String REF_TOC_AMBULATORY = DEFAULT_REFERENCE_FILENAME;
	private static final String REF_CAREPLAN_AMBULATORY = "170.315_b9_cp_amb_sample1.xml";
	private static final String REF_CAREPLAN_INPATIENT = "170.315_b9_cp_inp_sample1.xml";
	private static final String REF_CAREPLAN_NO_INTERVENTION_OR_HEALTH_STATUS = REF_TOC_AMBULATORY;
	private static final String REF_CAREPLAN_NO_INTERVENTION_HAS_HEALTH_STATUS = "cp_NoInterventionsHasHealthStatus.xml";
	private static final String REF_CAREPLAN_NO_HEALTH_STATUS_HAS_INTERVENTION = "cp_NoHealthStatusHasIntervention.xml";
	private static final String REF_E1_VDT_AMBULATORY = "170.315_e1_vdt_amb_sample1.xml";
	private static final String REF_E1_VDT_INPATIENT = "170.315_e1_vdt_inp_sample2.xml";	
	
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
	private static final int SUB_HAS_INTERVENTIONS_AND_HEALTH_STATUS_AND_IS_CP_AMB = 1;
	private static final int SUB_MISSING_INTERVENTIONS_AND_HAS_HEALTH_STATUS_AND_IS_CP_AMB = 2;
	private static final int SUB_MISSING_HEALTH_STATUS_AND_HAS_INTERVENTIONS_AND_IS_CP_AMB = 3;
	private static final int SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS = 4;

	private static URI[] SUBMITTED_CCDA = new URI[0];
	static {
		try {
			SUBMITTED_CCDA = new URI[] {
					ContentValidatorTest.class.getResource("/170.315_b1_toc_amb_sample1_Submitted_T1.xml").toURI(),
					ContentValidatorTest.class.getResource("/170.315_b9_cp_amb_sample1_v5.xml").toURI(),
					ContentValidatorTest.class.getResource("/170.315_b9_cp_amb_sample1_v5_Remove_Interventions.xml").toURI(),
					ContentValidatorTest.class.getResource("/170.315_b9_cp_amb_sample1_v5_Remove_HealthStatus.xml").toURI(),
					ContentValidatorTest.class.getResource("/NT_CCDS_Sample1_r21_v4.xml").toURI()
			};
		} catch (URISyntaxException e) {
			if(LOG_RESULTS_TO_CONSOLE) e.printStackTrace();
		}
	}
	
	private static final URI DEFAULT_SUBMITTED_CCDA = SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES];
	
	private static void println() {
		if (LOG_RESULTS_TO_CONSOLE) System.out.println();		
	}
	
	private static void println(String message) {
		print(message);
		println();
	}
	
	private static void print(String message) {
		if (LOG_RESULTS_TO_CONSOLE) System.out.print(message);		
	}
	
	private static void printHeader(String title) {
		println();
		println("------------------------RUNNING TEST-------------------------");
		println("********************" + title + "********************");
		println();
	}
	
	private static String convertCCDAFileToString(URI ccdaFileURI) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(ccdaFileURI.getPath()));
			String sCurrentLine = "";
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}
		} catch (Exception e) {
			println(e.toString());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				if(LOG_RESULTS_TO_CONSOLE) e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	private static ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String referenceFileName, String ccdaFileAsString) {
		return validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, referenceFileName, ccdaFileAsString);
	}
	
	private static ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String validationObjective, 
			String referenceFileName, String ccdaFileAsString) {
		return validator.validate(validationObjective, referenceFileName, ccdaFileAsString);
	}

	private static ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String referenceFileName, URI ccdaFileURI) {
		return validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, referenceFileName, ccdaFileURI);
	}
	
	private static ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String validationObjective, 
			String referenceFileName, URI ccdaFileURI) {
		String ccdaFileAsString = convertCCDAFileToString(ccdaFileURI);
		return validateDocumentAndReturnResults(validationObjective, referenceFileName, ccdaFileAsString);
	}
	
    private static ScenarioLoader setupAndReturnScenarioLoader(CCDAParser ccdaParser){
        ScenarioLoader scenarioLoader;
        String scenarioDir = LOCAL_SCENARIO_DIRECTORY;
        scenarioLoader = new ScenarioLoader();
        scenarioLoader.setScenarioFilePath(scenarioDir);
        scenarioLoader.setCcdaParser(ccdaParser);
        return scenarioLoader;
    }
	
    private static HashMap<String, CCDARefModel> loadAndParseScenariosAndGetRefModelHashMap() {
		ScenarioLoader scenarioLoader = setupAndReturnScenarioLoader(new CCDAParser());
		try {
			scenarioLoader.afterPropertiesSet(); //we manually set the props and dir...so this manually kicks off loading the scenarios
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scenarioLoader.getRefModelHashMap();
    }
    
    private static void printResults(List<ContentValidationResult> results) {
    	int resultIndex = 1;
    	for(ContentValidationResult curResult : results) {
    			println("Result #" + resultIndex);
    			println("Severity: " + (curResult.getContentValidationResultLevel() != null 
    					? curResult.getContentValidationResultLevel() : "null"));
    			println("Line Number: " + (curResult.getLineNumber() != null 
    					? curResult.getLineNumber() : "null"));
    			println("Message: " + (curResult.getMessage() != null 
    					? curResult.getMessage() : "null"));
    			println("XPath: " + (curResult.getXpath() != null 
    					? curResult.getXpath() : "null"));
    			resultIndex++;
    	}
    }

    private static boolean resultsContainMessage(String searchString, List<ContentValidationResult> results) {
    	return resultsContainMessage(searchString, results, null);
    }
    
    private static boolean resultsContainMessage(String searchString, List<ContentValidationResult> results, 
    		ContentValidationResultLevel expectedSeverity) {
    	for(ContentValidationResult curResult: results) {
    		if(curResult.getMessage().contains(searchString)) {
    			if(expectedSeverity != null && curResult.getContentValidationResultLevel() == expectedSeverity) {
	        		return true;
    			}
    			return true;
    		}
    	}
    	return false;
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
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResults(DEFAULT_REFERENCE_FILENAME, DEFAULT_SUBMITTED_CCDA);
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
		
		// has an objective which should NOT validate encounter diagnosis in ANY case, expect NO related error(s)
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResults(VO_E1_VDT_AMBULATORY, REF_E1_VDT_AMBULATORY ,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS]);
		assertFalse("No results were returned", results.isEmpty());
		println("FINAL RESULTS");
		println("No of Entries = " + results.size());
	
		final String encounterDiagnosisMessage = "Encounter Diagnosis data";
		assertFalse("The results contain the UNexpected message of: " + encounterDiagnosisMessage, 
				resultsContainMessage(encounterDiagnosisMessage, results));
		printResults(results);
		
		results = validateDocumentAndReturnResults(VO_E1_VDT_INPATIENT, REF_E1_VDT_INPATIENT ,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS]);
		assertFalse("The results contain the UNexpected message of: " + encounterDiagnosisMessage, 
				resultsContainMessage(encounterDiagnosisMessage, results));
		printResults(results);
		
		// has an objective which validates encounter diagnosis, expect related error(s)
		results = validateDocumentAndReturnResults(VO_TOC_AMBULATORY, REF_E1_VDT_AMBULATORY ,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS]);
		assertTrue("The results do not contain the expected message of: " + encounterDiagnosisMessage, 
				resultsContainMessage(encounterDiagnosisMessage, results));
		printResults(results);
		
		results = validateDocumentAndReturnResults(VO_TOC_AMBULATORY, REF_E1_VDT_AMBULATORY ,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS]);
		assertTrue("The results do not contain the expected message of: " + encounterDiagnosisMessage, 
				resultsContainMessage(encounterDiagnosisMessage, results));
		printResults(results);
		
		// has an objective which can validate encounter diagnosis in instructed cases (CarePlan), expect related error(s)
		// e.g. 170.315_b1_ToC_Amb, 170.315_b1_ToC_Inp, 170.315_b4_CCDS_Amb, 
		//		170.315_b4_CCDS_Inp, 170.315_b6_DE_Amb, 170.315_b6_DE_Inp
		results = validateDocumentAndReturnResults(VO_CAREPLAN_INPATIENT, REF_E1_VDT_AMBULATORY ,
				SUBMITTED_CCDA[SUB_NT_CCDS_SAMPLE1_R21_HAS_ENCOUNTER_DIAGNOSIS]);
		assertFalse("The results contain the UNexpected message of: " + encounterDiagnosisMessage, 
				resultsContainMessage(encounterDiagnosisMessage, results));
		printResults(results);
	}
	
}