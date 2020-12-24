import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Test;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.dto.enums.SeverityLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.service.ContentValidatorService;

public class ContentValidatorCuresTest extends ContentValidatorTester {
	
	private static final String CURES_SCENARIO_DIRECTORY = TEST_RESOURCES_DIRECTORY + "/cures/ref";
	private static HashMap<String, CCDARefModel> refModelHashMap = loadAndParseScenariosAndGetRefModelHashMap(
			CURES_SCENARIO_DIRECTORY);
	private static ContentValidatorService validator = new ContentValidatorService(refModelHashMap);
	static {
		println();
		println("Keys: " + refModelHashMap.keySet());
		println("Values: " + refModelHashMap.values());
	}	
	
	private static final boolean LOG_RESULTS_TO_CONSOLE = true;
	
	private static final String B1_TOC_AMB_VALIDATION_OBJECTIVE = "170.315_b1_ToC_Amb";
	private static final String E1_VDT_AMB_VALIDATION_OBJECTIVE = "170.315_e1_VDT_Amb";
	
	private static final String REF_CURES_B1_TOC_AMB_SAMPLE1 = "170.315_b1_toc_amb_sample1_v1.pdf";
	private static final String REF_CURES_B1_TOC_AMB_SAMPLE3 = "170.315_b1_toc_amb_sample3_v2.xml";
	private static final String REF_CURES_E1_VDT_AMB_SAMPLE1 = "170.315_e1_vdt_amb_sample1.xml";
	private static final String MOD_REF_CURES_ADD_AUTHORS = "ModRef_AddAuthors_170.315_b1_toc_amb_ccd_r21_sample1_v13.xml";
	private static final String MOD_REF_CURES_NO_BIRTH_SEX_B1_TOC_AMB_SAMPLE1 = "ModRef_CuresNoBirthSex_b1TocAmbSample1.xml";
	
	private static final int SUB_CURES_MISSING_AUTHOR_IN_HEADER = 0;
	private static final int SUB_HAS_BIRTH_SEX = SUB_CURES_MISSING_AUTHOR_IN_HEADER;
	private static final int SUB_EF = 1;
	private static final int SUB_NO_BIRTH_SEX = SUB_EF;
	private static final int SUB_MATCH_REF_B1_TOC_AMB_SAMPLE3 = 2;
	private static final int SUB_HAS_TELECOM_MISMATCHES = 3;
	private static final int SUB_HAS_NOTE_ACTIVITY_WITH_AUTHOR_IN_PROCEDURES = 4;
	private static final int SUB_HAS_PROCEDURE_ACTIVITY_PROCEDURE_WITH_AUTHOR_IN_PROCEDURES = 5;
	private static final int SUB_HAS_RESULT_ORGANIZER_WITH_AUTHOR_IN_RESULTS= 6;
	private static final int SUB_HAS_RESULT_ORGANIZER_WITHOUT_AUTHOR_IN_RESULTS= 7;
	

	private static URI[] SUBMITTED_CCDA = new URI[0];
	static {
		try {
			SUBMITTED_CCDA = new URI[] {
					ContentValidatorCuresTest.class.getResource("cures/sub/RemoveAuthorInHeader_170.315_b1_toc_amb_ccd_r21_sample1_v13.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/C-CDA_R2-1_CCD_EF.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/ref/170.315_b1_toc_amb_sample3.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("preCures/sub/170.315_b1_toc_amb_sample1_Submitted_T1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddNoteActivityWithAuthorToProcedures_b1_toc_amb_s1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddAuthorToProceduresProcedureActivityProcedure_b1_toc_amb_s1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddAuthorToResultsResultOrganizer_e1_vdt_amb_s1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddResultOrganizerWithoutAuthorToResults_e1_vdt_amb_s1.xml").toURI()
			};
		} catch (URISyntaxException e) {
			if(LOG_RESULTS_TO_CONSOLE) e.printStackTrace();
		}
	}
	
	private static final URI URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER = SUBMITTED_CCDA[SUB_CURES_MISSING_AUTHOR_IN_HEADER];		
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String referenceFileName, String ccdaFileAsString) {
		return validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, referenceFileName, ccdaFileAsString);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String validationObjective, 
			String referenceFileName, String ccdaFileAsString) {
		return validateDocumentAndReturnResultsCures(validationObjective, referenceFileName, ccdaFileAsString, SeverityLevel.INFO);
	}
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String validationObjective, 
			String referenceFileName, String ccdaFileAsString, SeverityLevel severityLevel) {
		return validator.validate(validationObjective, referenceFileName, ccdaFileAsString, true, severityLevel);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String referenceFileName, URI ccdaFileURI) {
		return validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, referenceFileName, ccdaFileURI);
	}
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String validationObjective, 
			String referenceFileName, URI ccdaFileURI) {
		return validateDocumentAndReturnResultsCures(validationObjective, referenceFileName, ccdaFileURI, SeverityLevel.INFO);
	}
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String validationObjective, 
			String referenceFileName, URI ccdaFileURI, SeverityLevel severityLevel) {
		String ccdaFileAsString = convertCCDAFileToString(ccdaFileURI);
		return validateDocumentAndReturnResultsCures(validationObjective, referenceFileName, ccdaFileAsString, severityLevel);
	}
	
	@Test
	public void cures_basicContentValidationTest() {
		printHeader("cures_basicContentValidationTest");
		try {
			ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3, SUBMITTED_CCDA[SUB_EF],
					SeverityLevel.ERROR);
			printResults(results);
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void cures_matchingSubAndRefExpectNoIssuesTest() {
		printHeader("cures_matchingSubAndRefExpectNoIssuesTest");

		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3,
				SUBMITTED_CCDA[SUB_MATCH_REF_B1_TOC_AMB_SAMPLE3], SeverityLevel.ERROR);
		printResults(results);
		
		if (results.size() > 1) {
			fail("There should not be more than 1 error");
		} else if(results.size() == 1) {
			// birth sex is an exception as it is not dependent on what is in the scenario
			// if we have a message, and it is not the birth sex exception, we fail
			// zero is a pass, so we don't mention
			if (!resultsContainMessage("The scenario requires patient's birth sex to be captured as part of social history data, "
					+ "but submitted file does not have birth sex information", results, ContentValidationResultLevel.ERROR)) {
				fail("The submitted and reference files match so there should not be any results yet there are results");
			}
		}		
	}
	
	@Test
	public void cures_telecomTest() {
		printHeader("telecomTest");
		
		// Cures enforces an ERROR for telecom issues as opposed to a warning with non-cures
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(REF_CURES_B1_TOC_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES]);
		assertFalse("No results were returned", results.isEmpty());
		println("FINAL RESULTS");
		println("No of Entries = " + results.size());
		
		final String telecomMessage = "Patient Telecom in the submitted file does not match the expected Telecom";
		assertTrue("The results do not contain the expected message of: " + telecomMessage, 
				resultsContainMessage(telecomMessage, results, ContentValidationResultLevel.ERROR));
		
		printResults(results);
	}	
	
	@Test
	public void cures_birthSexTest() {
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
		
		printHeader("Ref has birth sex. Sub does not have birth sex. Expect birth sex error");
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_NO_BIRTH_SEX], SeverityLevel.ERROR);
		printResults(results);
		assertTrue("Expect birth sex error: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.ERROR));
		
		printHeader("Ref does not have have birth sex. Sub does not have birth sex. "
				+ "Still expect birth sex error despite ref not requiring it");
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_NO_BIRTH_SEX_B1_TOC_AMB_SAMPLE1, SUBMITTED_CCDA[SUB_NO_BIRTH_SEX], SeverityLevel.ERROR);
		printResults(results);
		assertTrue("Expect birth sex error despite ref not requiring it: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.ERROR));
		
		printHeader("Ref has birth sex. Sub has birth sex. Expect NO birth sex error as sub has birth sex");
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_HAS_BIRTH_SEX], SeverityLevel.ERROR);
		printResults(results);
		assertFalse("Expect NO birth sex error as sub has birth sex but got: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_severityLevelLimitTestFileWithThreeErrorsOnly() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		final String error1 = "Patient Telecom in the submitted file does not match the expected Telecom. "
				+ "The following values are expected: telecom/@use = MC and telecom/@value = tel:+1(555)-777-1234";
		final String error2 = "Patient Telecom in the submitted file does not match the expected Telecom. "
				+ "The following values are expected: telecom/@use = HP and telecom/@value = tel:+1(555)-723-1544";
		final String error3 = "The scenario requires patient's birth sex to be captured as part of social history data, "
				+ "but submitted file does not have birth sex information";

		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				REF_CURES_B1_TOC_AMB_SAMPLE1, SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES], SeverityLevel.INFO);
		printResults(results);
		assertTrue("expecting 3 errors", results.size() == 3);
		ContentValidationResultLevel expectedSeverity = ContentValidationResultLevel.ERROR;
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error1, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error2, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error3, expectedSeverity);

		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES], SeverityLevel.WARNING);
		printResults(results);
		assertTrue("expecting (the same) 3 errors", results.size() == 3);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error1, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error2, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error3, expectedSeverity);
		
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES], SeverityLevel.ERROR);
		printResults(results);
		assertTrue("expecting (the same) 3 errors", results.size() == 3);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error1, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error2, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error3, expectedSeverity);
	}	
		
	@Test
	public void cures_authorinHeaderTest() {		
		printHeader("cures_authorinHeaderTest");
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				REF_CURES_B1_TOC_AMB_SAMPLE1, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
				
		final String noAuthorDataMessage = "The scenario requires data related to Author (Provenance), "
				+ "but the submitted C-CDA does not contain Author data.";
		assertTrue("The results do not contain the expected message of: " + noAuthorDataMessage, 
				resultsContainMessage(noAuthorDataMessage, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_authorinAllergiesTest() {
		printHeader("cures_authorinAllergiesTest");
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Allergy Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInAllergiesSectionRootLevel = "The scenario requires Provenance data for Allergy Section "
				+ "however the submitted file does not contain the Provenance data for Allergy Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInAllergiesSectionRootLevel, 
				resultsContainMessage(noAuthorInAllergiesSectionRootLevel, results, ContentValidationResultLevel.ERROR));		
		
		// Allergy Section/AllergyConcern author missing in sub but required in modified ref
		final String noAuthorInAllergiesSectionAllergyConcernMessage = "The scenario requires a total of 1 Author Entries "
				+ "for Allergy Section/AllergyConcern, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInAllergiesSectionAllergyConcernMessage, 
				resultsContainMessage(noAuthorInAllergiesSectionAllergyConcernMessage, results, ContentValidationResultLevel.ERROR));
		
		// Allergy Section/AllergyConcern/AllergyObservation author missing in sub but required in modified ref
		final String noAuthorInAllergyObsMessage = "The scenario requires a total of 1 Author Entries for "
				+ "Allergy Section/AllergyConcern/AllergyObservation, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInAllergyObsMessage, 
				resultsContainMessage(noAuthorInAllergyObsMessage, results, ContentValidationResultLevel.ERROR));

		// Allergy Section/AllergyConcern/AllergyObservation/AllergyReaction author missing in sub but required in modified ref
		// Note: Adding author to AllergyReaction is NOT a rule in the IG. But, it is an open standard. And, it's part of our model. 
		final String noAuthorInAllergyReactionMessage = "The scenario requires a total of 1 Author Entries for "
				+ "Allergy Section/AllergyConcern/AllergyObservation/AllergyReaction, "
				+ "however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInAllergyReactionMessage, 
				resultsContainMessage(noAuthorInAllergyReactionMessage, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_authorinProblemsTest() {
		printHeader("cures_authorinProblemsTest");		
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Problem Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInProblemSectionRootLevel = "The scenario requires Provenance data for Problem Section "
				+ "however the submitted file does not contain the Provenance data for Problem Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInProblemSectionRootLevel, 
				resultsContainMessage(noAuthorInProblemSectionRootLevel, results, ContentValidationResultLevel.ERROR));		
		
		// Problem Section/ProblemConcern author missing in sub but required in modified ref
		final String noAuthorInProblemSectionProblemConcern = "The scenario requires a total of 1 Author Entries for "
				+ "Problem Section/ProblemConcern, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInProblemSectionProblemConcern, 
				resultsContainMessage(noAuthorInProblemSectionProblemConcern, results, ContentValidationResultLevel.ERROR));
		
		// Problem Section/ProblemConcern/ProblemObservation author missing in sub but required in modified ref
		final String noAuthorInProblemSectionProblemConcernProblemObservation = "The scenario requires a total of 1 Author Entries for "
				+ "Problem Section/ProblemConcern/ProblemObservation, "
				+ "however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInProblemSectionProblemConcernProblemObservation, 
				resultsContainMessage(noAuthorInProblemSectionProblemConcernProblemObservation, results, ContentValidationResultLevel.ERROR));
		
		/* Past Medical History Section/ProblemObservation author missing in sub but required in modified ref
		   Note: This one is NOT in the Problems Section in C-CDA but is part of our CCDAProblem model as a CCDAProblemObs pastIllnessProblems, which it contains
		   We parse it from the Past Medical History (V3) [section: identifier urn:hl7ii:2.16.840.1.113883.10.20.22.2.20:2015-08-01(open)] which can contain a Problem Observation (V3) (optional)
		   Parsed with:
		   PAST_ILLNESS_EXP = CCDAConstants.CCDAXPATH.compile("
		   /ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='11348-0']]");
		   PAST_ILLNESS_PROBLEM_OBS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("
		   ./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.4']]"); */
		final String noAuthorInPastMedicalHistoryProblemObservation = "The scenario requires a total of 1 Author Entries for "
				+ "Past Medical History Section (Past Illness)/ProblemObservation, however the submitted data had only 0 entries";
		assertTrue("The results do not contain the expected message of: " + noAuthorInPastMedicalHistoryProblemObservation, 
				resultsContainMessage(noAuthorInPastMedicalHistoryProblemObservation, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_authorInProceduresTest() {
		printHeader("cures_authorInProceduresTest");		
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Procedures Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInProceduresSectionRootLevel = "The scenario requires Provenance data for Procedures Section "
				+ "however the submitted file does not contain the Provenance data for Procedures Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInProceduresSectionRootLevel, 
				resultsContainMessage(noAuthorInProceduresSectionRootLevel, results, ContentValidationResultLevel.ERROR));
		
		// Procedures Section/ProcedureActivityProcedure author missing in sub but required in modified ref
		final String noAuthorInProcedureActivityProcedure = "The scenario requires a total of 2 Author Entries for "
				+ "Procedures Section/ProcedureActivityProcedure, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInProcedureActivityProcedure, 
				resultsContainMessage(noAuthorInProcedureActivityProcedure, results, ContentValidationResultLevel.ERROR));
		
		// TODO-db: Add more tests for remaining potential sub entries				
	}
	
	@Test
	public void cures_ZeroAuthorsInRefOneOrMoreInSubProceduresProcedureActivityProcedureTest() {
		// SITE-3193
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_HAS_PROCEDURE_ACTIVITY_PROCEDURE_WITH_AUTHOR_IN_PROCEDURES], SeverityLevel.ERROR);
		printResults(results);
		
		// Procedures Section/ProcedureActivityProcedure author missing in ref yet is in sub (sub does have author, ref does not/not require it)
		// There is no situation where we should be requiring 0 of something. The submitted file should be allowed to have more information than the scenario.
		final String zeroAuthorsInRefOneOrMoreInSub = "The scenario requires a total of 0 Author Entries for "
				+ "Procedures Section/ProcedureActivityProcedure, however the submitted data had only 1 entries.";
		assertFalse("The results should not contain the unexpected message of: " + zeroAuthorsInRefOneOrMoreInSub, 
				resultsContainMessage(zeroAuthorsInRefOneOrMoreInSub, results, ContentValidationResultLevel.ERROR));			
	}	
	
	// TODO: Analyze: Seems like a bug that this is being identified as Procedure Activity Procedure when it is Note Activity, check parser/check with dragon. Do we enforce author here too?
	// To see the bug we must comment out 'if (refAuths != null && refAuths.size() != 0) {' compareAuthors in CCDAAuthore and run this test
	// Try to find a way to repro with current code legitimately and go from there...
	@Test
	public void cures_ZeroAuthorsInRefOneOrMoreInSubProceduresNoteActivityTest() {
		// SITE-3193 (done), SITE-3194 (not yet done, see todo above)		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_HAS_NOTE_ACTIVITY_WITH_AUTHOR_IN_PROCEDURES], SeverityLevel.ERROR);
		printResults(results);
		
		// Procedures Section/Note Activity author missing in ref yet is in sub (sub does have author, ref does not/not require it)
		// There is no situation where we should be requiring 0 of something. The submitted file should be allowed to have more information than the scenario.
		final String zeroAuthorsInRefOneOrMoreInSub = "The scenario requires a total of 0 Author Entries for "
				+ "Procedures Section/ProcedureActivityProcedure, however the submitted data had only 1 entries.";
		assertFalse("The results should not contain the unexpected message of: " + zeroAuthorsInRefOneOrMoreInSub, 
				resultsContainMessage(zeroAuthorsInRefOneOrMoreInSub, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_authorInMedicationsTest() {
		printHeader("cures_authorInMedicationsTest");		
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Medications Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInMedicationsSectionRootLevel = "The scenario requires Provenance data for Medications Section "
				+ "however the submitted file does not contain the Provenance data for Medications Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInMedicationsSectionRootLevel, 
				resultsContainMessage(noAuthorInMedicationsSectionRootLevel, results, ContentValidationResultLevel.ERROR));
		
		// Medications Section/MedicationActivity author missing in sub but required in modified ref
		final String noAuthorInMedicationActivity = "The scenario requires a total of 1 Author Entries for "
				+ "Medications Section/MedicationActivity, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInMedicationActivity, 
				resultsContainMessage(noAuthorInMedicationActivity, results, ContentValidationResultLevel.ERROR));
		
		// TODO-db: Look at parser, may be more authors to collect
	}
	
	@Test
	public void cures_authorInImmunizationsTest() {
		printHeader("cures_authorInImmunizationsTest");		
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Immunizations Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInImmunizationsSectionRootLevel = "The scenario requires Provenance data for Immunizations Section "
				+ "however the submitted file does not contain the Provenance data for Immunizations Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInImmunizationsSectionRootLevel, 
				resultsContainMessage(noAuthorInImmunizationsSectionRootLevel, results, ContentValidationResultLevel.ERROR));
		
		// Immunizations Section/ImmunizationActivity author missing in sub but required in modified ref
		final String noAuthorInImmunizationActivity = "The scenario requires a total of 1 Author Entries for "
				+ "Immunizations Section/ImmunizationActivity, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInImmunizationActivity, 
				resultsContainMessage(noAuthorInImmunizationActivity, results, ContentValidationResultLevel.ERROR));				
	}
	
	@Test
	public void cures_authorInResultsTest() {
		printHeader("cures_authorInResultsTest");
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Results Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInResultsSectionRootLevel = "The scenario requires Provenance data for Results Section "
				+ "however the submitted file does not contain the Provenance data for Results Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInResultsSectionRootLevel, 
				resultsContainMessage(noAuthorInResultsSectionRootLevel, results, ContentValidationResultLevel.ERROR));		
		
		// Results Section/ResultsOrganizer author missing in sub but required in modified ref
		final String noAuthorInResultsSectionResultOrganizerMessage = "The scenario requires a total of 1 Author Entries "
				+ "for Results Section/ResultOrganizer, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInResultsSectionResultOrganizerMessage, 
				resultsContainMessage(noAuthorInResultsSectionResultOrganizerMessage, results, ContentValidationResultLevel.ERROR));
		
		// Results Section/ResultsOrganizer/ResultsObservation author missing in sub but required in modified ref
		final String noAuthorInResultObsMessage = "The scenario requires a total of 1 Author Entries for "
				+ "Results Section/ResultOrganizer/ResultObservation, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInResultObsMessage, 
				resultsContainMessage(noAuthorInResultObsMessage, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_ZeroAuthorsInRefOneOrMoreInSubResultsResultOrganizerTest() {
		// SITE-3189
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_HAS_RESULT_ORGANIZER_WITH_AUTHOR_IN_RESULTS], SeverityLevel.ERROR);
		printResults(results);
		
		// Results Section/ResultsOrganizer author missing in ref yet is in sub (sub does have author, ref does not/not require it)
		// There is no situation where we should be requiring 0 of something. The submitted file should be allowed to have more information than the scenario.
		final String zeroAuthorsInRefOneOrMoreInSub = "The scenario requires a total of 0 Author Entries for "
				+ "Results Section/ResultOrganizer, however the submitted data had only 1 entries.";
		assertFalse("The results should not contain the unexpected message of: " + zeroAuthorsInRefOneOrMoreInSub, 
				resultsContainMessage(zeroAuthorsInRefOneOrMoreInSub, results, ContentValidationResultLevel.ERROR));			
	}
	
	@Test
	public void cures_ZeroAuthorsInRefAndZeroInSubResultsResultOrganizerTest() {
		// SITE-3189
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1,
				SUBMITTED_CCDA[SUB_HAS_RESULT_ORGANIZER_WITHOUT_AUTHOR_IN_RESULTS], SeverityLevel.ERROR);
		printResults(results);
		
		// Results Section/ResultsOrganizer author missing in ref yet and missing in sub
		final String zeroAuthorsInRefOneOrMoreInSub = "The scenario requires a total of 0 Author Entries for "
				+ "Results Section/ResultOrganizer, however the submitted data had only 1 entries.";
		assertFalse("The results should not contain the unexpected message of: " + zeroAuthorsInRefOneOrMoreInSub, 
				resultsContainMessage(zeroAuthorsInRefOneOrMoreInSub, results, ContentValidationResultLevel.ERROR));			
	}		
	
	@Test
	public void cures_authorInVitalSignsTest() {
		printHeader("cures_authorInVitalSignsTest");
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Vital Signs Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInVitalSignsSectionRootLevel = "The scenario requires Provenance data for Vital Signs Section "
				+ "however the submitted file does not contain the Provenance data for Vital Signs Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInVitalSignsSectionRootLevel, 
				resultsContainMessage(noAuthorInVitalSignsSectionRootLevel, results, ContentValidationResultLevel.ERROR));		
		
		// Vital Signs Section/VitalSignsOrganizer author missing in sub but required in modified ref
		final String noAuthorInVitalSignsSectionResultOrganizerMessage = "The scenario requires a total of 1 Author Entries "
				+ "for Vital Signs Section/VitalSignsOrganizer, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInVitalSignsSectionResultOrganizerMessage, 
				resultsContainMessage(noAuthorInVitalSignsSectionResultOrganizerMessage, results, ContentValidationResultLevel.ERROR));
		
		// Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation author missing in sub but required in modified ref
		final String noAuthorInResultObsMessage = "The scenario requires a total of 1 Author Entries for "
				+ "Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInResultObsMessage, 
				resultsContainMessage(noAuthorInResultObsMessage, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_authorInEncountersTest() {
		printHeader("cures_authorInEncountersTest");		
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Encounters Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInEncountersSectionRootLevel = "The scenario requires Provenance data for Encounters Section "
				+ "however the submitted file does not contain the Provenance data for Encounters Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInEncountersSectionRootLevel, 
				resultsContainMessage(noAuthorInEncountersSectionRootLevel, results, ContentValidationResultLevel.ERROR));
		
		// Encounters Section/EncounterActivity author missing in sub but required in modified ref
		final String noAuthorInEncounterActivity = "The scenario requires a total of 1 Author Entries for "
				+ "Encounters Section/EncounterActivity, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInEncounterActivity, 
				resultsContainMessage(noAuthorInEncounterActivity, results, ContentValidationResultLevel.ERROR));
		
		// Encounters Section/EncounterActivity/EncounterDiagnoses author missing in sub but required in modified ref
		final String noAuthorInEncounterActivityEncounterDiagnoses = "The scenario requires a total of 1 Author Entries for "
				+ "Encounters Section/EncounterActivity/EncounterDiagnoses, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInEncounterActivityEncounterDiagnoses, 
				resultsContainMessage(noAuthorInEncounterActivityEncounterDiagnoses, results, ContentValidationResultLevel.ERROR));
		
		// Encounters Section/EncounterActivity/EncounterDiagnoses/ProblemObservation author missing in sub but required in modified ref
		final String noAuthorInEncounterActivityEncounterDiagnosesProblemObservation = "The scenario requires a total of 1 Author Entries for "
				+ "Encounters Section/EncounterActivity/EncounterDiagnoses/ProblemObservation, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInEncounterActivityEncounterDiagnosesProblemObservation, 
				resultsContainMessage(noAuthorInEncounterActivityEncounterDiagnosesProblemObservation, results, ContentValidationResultLevel.ERROR));
		
		// Encounters Section/EncounterActivity/EncounterDiagnoses/ProblemObservation author missing in sub but required in modified ref
		// This is the Problem-Observation-like Indication (V2) observation within EncounterActivity/EncounterDiagnoses
		// Parsed by readEncounterActivity in EncounterParser via the relative observation in the XML (see readEncounterActivity)
		final String noAuthorInEncounterActivityIndication = "The scenario requires a total of 1 Author Entries for "
				+ "Encounters Section/EncounterActivity/Indication, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInEncounterActivityIndication, 
				resultsContainMessage(noAuthorInEncounterActivityIndication, results, ContentValidationResultLevel.ERROR));		
	}

}
