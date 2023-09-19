import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Ignore;
import org.junit.Test;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.dto.enums.SeverityLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.service.ContentValidatorService;
import org.springframework.beans.factory.parsing.Problem;

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
	
	private static final String S = "cures/sub/";
	private static final String R = "cures/ref/";
	private static final String PS = "preCures/sub/";
	private static final String PR = "preCures/ref/";
	
	private static final String B1_TOC_AMB_VALIDATION_OBJECTIVE = "170.315_b1_ToC_Amb";
	private static final String B1_TOC_INP_VALIDATION_OBJECTIVE = "170.315_b1_ToC_Inp";
	
	private static final String E1_VDT_AMB_VALIDATION_OBJECTIVE = "170.315_e1_VDT_Amb";
	private static final String E1_VDT_INP_VALIDATION_OBJECTIVE = "170.315_e1_VDT_Inp";
	
	private static final String G9_APIACCESS_INP_VALIDATION_OBJECTIVE = "170.315_g9_APIAccess_Inp"; 
	
	private static final String REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA = "170.315_g9_api_access_inp_sample1.xml";
	
	private static final String REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF = "170.315_b1_toc_amb_sample1_v1.pdf";
	private static final String REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2 = "170.315_b1_toc_amb_sample1_v2.pdf";
	
	private static final String REF_CURES_B1_TOC_AMB_SAMPLE2_JEREMY = "170.315_b1_toc_amb_sample2.xml";
	private static final String REF_CURES_B1_TOC_AMB_SAMPLE2_V2_JEREMY = "toc_amb_s2_v2.xml";
																		 
	private static final String REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2 = "170.315_b1_toc_amb_sample3_v2.xml";
	private static final String REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V5 = "170.315_b1_toc_amb_sample3_v5.xml";
	private static final String REF_CURES_B1_INP_AMB_SAMPLE3_JANE = "170.315_b1_toc_inp_sample3.xml";
	
	private static final String REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE = "170.315_e1_vdt_amb_sample1.xml";
	private static final String REF_CURES_E1_VDT_INP_SAMPLE1_REBECCA = "170.315_e1_vdt_inp_sample1.xml";

	private static final String MOD_REF_CURES_ADD_AUTHORS = "ModRef_AddAuthors_b1TocAmbCcdR21Aample1V13.xml";
	private static final String MOD_REF_CURES_NO_BIRTH_SEX_B1_TOC_AMB_SAMPLE1 = "ModRef_CuresNoBirthSex_b1TocAmbSample1.xml";
	private static final String MOD_REF_CURES_MODREF_REMOVE_VITAL_SIGNS_OBS_AUTHORS_E1_VDT_INP_SAMPLE1_REBECCA = 
			"ModRef_RemoveVitalSignsObsAuthors_E1VdtInpSample1.xml";
	private static final String MOD_REF_ADD_NOTES_ACTIVITY_ENCOUNTER_ENTRY_B1_TOC_AMB_S1 = 
			"ModRef_AddNotesActivityEncounterEntry_b1TocAmbS1.xml";
	private static final String MOD_REF_ADD_NOTES_ACTIVITY_PAP_ENTRY_RELATIONSHIP_B1_TOC_AMB_S1 = 
			"ModRef_AddNotesActivityProcActProcEntryRel_b1TocAmbS1.xml";	
	private static final String MOD_REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA_DOC_AUTH_PRECISE_TO_TIME = 
			"ModRef_AccurateDateAndTimeForAuthor_DocLvl_VitalSection_g9AAInpS1.xml";
	private static final String MOD_REF_UPDATE_NOTES_TIMES_CURES_B1_TOC_AMB_SAMPLE3_HAPPY = "ModRef_ChangeNotesTimes_b1TocAmbS3.xml";
	private static final String MOD_REF_UPDATE_NOTES_TIMES_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_ONE_NOTE_ACT_ENT = 
			"ModRef_ChangeNotesTimes_b1TocAmbS3_OneNoteActivityEntry.xml";
	private static final String MOD_REF_VITAL_SIGNS_SEC_VS_OBS_TIME_REPRO_SITE_3261 = "ModRef_b1TocAmbS3_prod0421_repro_Site3261.xml";
	private static final String MOD_REF_REPLACE_MED_ACT_AUTHOR_PARTICIPATION_WITH_PROVENANCE_B1_TOC_AMB_S3_V3 = 
			"ModRef_ReplaceMedActAuthorParticipationWithProvenance_b1TocAmbS3v3.xml";
	private static final String MOD_REF_ADD_PROV_SITE_3262 = "ModRef_AddProv_Site3262.xml";
	private static final String MOD_REF_RPLC_AUTH_PAR_WITH_PROV_SITE_3300 = "ModRef_Rplc_Auth_Par_With_Prov_Site3300.xml";
	private static final String MOD_REF_G9_INP_SWAP_AUTH_PAR_WITH_PROV = "ModRef_g9_inp_SwapAuthParWithProv.xml";
	
	
	private static final int SUB_CURES_MISSING_AUTHOR_IN_HEADER = 0;
	private static final int SUB_HAS_BIRTH_SEX = SUB_CURES_MISSING_AUTHOR_IN_HEADER;
	private static final int SUB_EF = 1;
	private static final int SUB_NO_BIRTH_SEX = SUB_EF;
	private static final int SUB_MATCH_REF_B1_TOC_AMB_SAMPLE3 = 2;
	private static final int SUB_HAS_TELECOM_MISMATCHES = 3;
	private static final int SUB_HAS_NOTE_ACTIVITY_WITH_AUTHOR_IN_PROCEDURES = 4;
	private static final int SUB_HAS_PROCEDURE_ACTIVITY_PROCEDURE_WITH_AUTHOR_IN_PROCEDURES = 5;
	private static final int SUB_HAS_RESULT_ORGANIZER_WITH_AUTHOR_IN_RESULTS = 6;
	private static final int SUB_HAS_RESULT_ORGANIZER_WITHOUT_AUTHOR_IN_RESULTS = 7;
	private static final int SUB_LAB_RESULTS_NOT_FOUND_SITE_3199 = 8;
	private static final int SUB_LAB_RESULTS_STILL_NOT_FOUND_REMOVED_NULL_FLAVOR_ORG_CODE_SITE_3199 = 9;
	private static final int SUB_LAB_RESULTS_FOUND_REMOVED_NULL_FLAVOR_ORG_CODE_AND_OBS_CODES_SITE_3199 = 10;
	private static final int SUB_SOCIAL_HISTORY_WITHOUT_BIRTH_SEX_OBS_TEMPLATE_SITE_3094 = 11;
	private static final int SUB_SOCIAL_HISTORY_WITH_BIRTH_SEX_OBS_TEMPLATE_SITE_3094 = 12;
	private static final int ADD_SMOKING_STATUS_ENTRY_FORMER_SMOKER_B1_TOC_AMB_S3_SITE_3220 = 13;
	private static final int ADD_SMOKING_STATUS_ENTRY_FORMER_SMOKER_B1_TOC_INP_S3_SITE_3220 = 14;
	private static final int ADD_SMOKING_STATUS_ENTRY_UNKNOWN_SMOKER_B1_TOC_AMB_S3_SITE_3220 = 15;
	private static final int ADD_SMOKING_STATUS_ENTRY_UNKNOWN_SMOKER_B1_TOC_INP_S3_SITE_3220 = 16;
	private static final int ADD_SMOKING_STATUS_ENTRY_FORMER_AND_UNKNOWN_SMOKER_B1_TOC_AMB_S3_SITE_3220 = 17;
	private static final int SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_10_AUTHORS_TOTAL_REBECCA_SITE_3232 = 18;
	private static final int SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_2_AUTHORS_TOTAL_REBECCA_SITE_3232 = 19;
	private static final int SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_4_AUTHORS_TOTAL_REBECCA_SITE_3232 = 20;
	private static final int SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_0_AUTHORS_TOTAL_REBECCA_SITE_3232 = 21;
	private static final int SUB_ADD_2_AUTHORS_TO_PROB_SEC_CONC_OBS_B1_TOC_AMB_CCD_R21_SAMPLE1V13_SITE3235 = 22;
	private static final int HAS_2_AUTHORS_INHEADER_B1_TOC_AMB_S1_SITE_3235 = 23;
	private static final int SUB_DUPLICATE_OF_B1_TOC_AMB_SAMPLE1_REF = 24;
	private static final int SUB_DUPLICATE_OF_MOD_REF_ADD_NOTES_ACTIVITY_ENCOUNTER_ENTRY_B1_TOC_AMB_S1 = 25;
	private static final int SUB_DUPLICATE_OF_MOD_REF_ADD_NOTES_ACTIVITY_PAP_ENTRY_RELATIONSHIP_B1_TOC_AMB_S1 = 26;
	private static final int SUB_HAS_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241 = 27;
	private static final int SUB_HAS_DATE_ONLY_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241 = 28;
	private static final int SUB_HAS_DATE_ONLY_INVERSE_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241 = 29;
	private static final int SUB_HAS_ACCURATE_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241 = 30;
	private static final int SUB_HAS_MIXED_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241 = 31;
	private static final int SUB_HAS_NOTES_SEC_NOTE_ACT_AUTHOR_TIME_TIMEZONE_0400_SITE_3252 = 32;
	private static final int SUB_VITAL_SIGNS_SEC_VS_OBS_TIME_REPRO_HAPPY_413_SITE_3261 = 33;
	private static final int SUB_VITAL_SIGNS_SEC_VS_OBS_TIME_REPRO_HAPPY_413_MATCH_NAME_SITE_3261 = 34;
	private static final int SUB_MEDICATION_SEC_MED_ACT_PROV_TIME_MISMATCHED_HAPPY_416_SITE_3262 = 35;
	private static final int SUB_MEDICATION_SEC_MED_ACT_PROV_TIME_FIX_TO_MATCH_HAPPY_416_SITE_3262 = 36;
	private static final int SUB_PROB_SEC_PROB_CONC_PROB_OBS_REPRO_HAPPYBLD420V2_MISSING_NAME_SITE_3265 = 37;
	private static final int SUB_PROB_SEC_PROB_CONC_PROB_OBS_REPRO_HAPPYBLD420V2_ADD_MATCHING_NAME_SITE_3265 = 38;	
	private static final int SUB_CARE_TEAM_SEC_PERF_SITE_3259 = 39;
	private static final int SUB_CARE_TEAM_SEC_PERF_AND_PART_SITE_3259 = 40;
	private static final int SUB_REBECCA621_SITE_3300 = 41;
	private static final int SUB_REBECCA621_MATCHED_TIME_SITE_3300 = 42;
	private static final int SUB_REBECCA621_MATCHED_TIME_BUT_MISSING_LINKED_REFERENCES_IN_DOCUMENT_SITE_3300 = 43;
	private static final int SUB_REBECCA621_ONE_NOTE_ACT_VALID_LINK_3300 = 44;
	private static final int SUB_REBECCA621_ONE_NOTE_ACT_VALID_INLINE_3300 = 45;
	private static final int SUB_REBECCA621_ONE_NOTE_ACT_BAD_LINK_BAD_EXT_AND_ROOT_3300 = 46;
	private static final int SUB_REBECCA621_ONE_NOTE_ACT_EMPTY_ASSIGNED_AUTHOR_3300 = 47;
	private static final int SUB_REBECCA621_ONE_NOTE_ACT_VALID_LINK_BAD_NAME_3300 = 48;
	private static final int SUB_REBECCA621_ONE_NOTE_ACT_NO_LINKED_REF_IN_DOC_3300 = 49;
	private static final int SUB_REBECCA621_ONE_NOTE_ACT_INLINE_AUTH_MISMATCHED_NAME_3300 = 50;
	private static final int SUB_HAS_NOTES_SEC_NOTE_ACT_AUTHOR_TIME_TIMEZONE_0400_SWITCH_E2_AND_E3_FOR_SITE_3263 = 51;
	private static final int SUB_HAS_NOTES_SEC_NOTE_ACT_AUTHOR_TIME_TIMEZONE_0400_SWITCH_E2_AND_E1_FOR_SITE_3263 = 52;
	private static final int SUB_VALID_PROVENANCE_TS_INVALID_AUTHOR_PARTICIPATION_TS_SITE_3331 = 53;
	private static final int SUB_INVALID_PROVENANCE_TS_VALID_AUTHOR_PARTICIPATION_TS_SITE_3331 = 54;
	private static final int SUB_INVALID_PROVENANCE_TS_VALID_AUTHOR_PARTICIPATION_TS_SWAP_AUTHORS_SITE_3331 = 55;
	private static final int SUB_INVALID_PROVENANCE_TS_INVALID_AUTHOR_PARTICIPATION_TS_SITE_3331 = 56;
	private static final int SUB_VALID_PROVENANCE_TS_VALID_AUTHOR_PARTICIPATION_TS_SITE_3331 = 57;
	private static final int SUB_INVALID_PROVENANCE_TS_INVALID_AUTHOR_PARTICIPATION_TS_SWAP_AUTHORS_SITE_3331 = 58;
	private static final int SUB_3354_REPRO_PROBLEMSEC_CONC_OBS_PROVENANCE = 59;
	private static final int SUB_3362_IS_AUTHOR_OF_TYPE_PROVENANCE_NPE = 60;
	private static final int SUB_3350_JEREMY_PROVENANCE_LINKED_REFERENCE = 61;

	private static URI[] SUBMITTED_CCDA = new URI[0];
	static {
		try {
			SUBMITTED_CCDA = new URI[] {
					ContentValidatorCuresTest.class.getResource(S+"RemoveAuthorInHeader_170.315_b1_toc_amb_ccd_r21_sample1_v13.xml").toURI(), // 0
					ContentValidatorCuresTest.class.getResource(S+"C-CDA_R2-1_CCD_EF.xml").toURI(), // 1
					ContentValidatorCuresTest.class.getResource(R+"170.315_b1_toc_amb_sample3_v2.xml").toURI(), // 2
					ContentValidatorCuresTest.class.getResource(PS+"170.315_b1_toc_amb_sample1_Submitted_T1.xml").toURI(), // 3
					ContentValidatorCuresTest.class.getResource(S+"AddNoteActivityWithAuthorToProcedures_b1_toc_amb_s1.xml").toURI(), // 4
					ContentValidatorCuresTest.class.getResource(S+"AddAuthorToProceduresProcedureActivityProcedure_b1_toc_amb_s1.xml").toURI(), // 5
					ContentValidatorCuresTest.class.getResource(S+"AddAuthorToResultsResultOrganizer_e1_vdt_amb_s1.xml").toURI(), // 6
					ContentValidatorCuresTest.class.getResource(S+"AddResultOrganizerWithoutAuthorToResults_e1_vdt_amb_s1.xml").toURI(), // 7
					ContentValidatorCuresTest.class.getResource(S+"HasNullFlavorOnResultOrganizerCode.xml").toURI(), // 8
					ContentValidatorCuresTest.class.getResource(S+"DoesNotHaveNullFlavorOnResultOrganizerCode.xml").toURI(), // 9
					ContentValidatorCuresTest.class.getResource(S+"DoesNotHaveNullFlavorOnResultOrganizerObservationCodes.xml").toURI(), // 10
					ContentValidatorCuresTest.class.getResource(S+"SocialHistoryWithoutBirthSexObsTemplateSite3094.xml").toURI(), // 11
					ContentValidatorCuresTest.class.getResource(S+"SocialHistoryWithBirthSexObsTemplateSite3094.xml").toURI(), // 12
					ContentValidatorCuresTest.class.getResource(S+"AddSmokingStatusEntryFormerSmoker_b1_toc_amb_s3_Site3220.xml").toURI(), // 13
					ContentValidatorCuresTest.class.getResource(S+"AddSmokingStatusEntryFormerSmoker_b1_inp_amb_s3_Site3220.xml").toURI(), // 14
					ContentValidatorCuresTest.class.getResource(S+"AddSmokingStatusEntryUnknownSmoker_b1_toc_amb_s3_Site3220.xml").toURI(), // 15
					ContentValidatorCuresTest.class.getResource(S+"AddSmokingStatusEntryUnknownSmoker_b1_inp_amb_s3_Site3220.xml").toURI(), // 16
					ContentValidatorCuresTest.class.getResource(S+"AddSmokingStatusEntryFormerAndUnknownSmoker_b1_toc_amb_s3_Site3220.xml").toURI(), // 17
					ContentValidatorCuresTest.class.getResource(S+"vitalSignsSectionWith5Organizers10Observations_10AuthorsTotal_rebecca_Site3232.xml").toURI(), // 18
					ContentValidatorCuresTest.class.getResource(S+"vitalSignsSectionWith5Organizers10Observations_2AuthorsTotal_rebecca_Site3232.xml").toURI(), // 19
					ContentValidatorCuresTest.class.getResource(S+"vitalSignsSectionWith5Organizers10Observations_4AuthorsTotal_rebecca_Site3232.xml").toURI(), // 20
					ContentValidatorCuresTest.class.getResource(S+"vitalSignsSectionWith5Organizers10Observations_0AuthorsTotal_rebecca_Site3232.xml").toURI(), // 21
					ContentValidatorCuresTest.class.getResource(S+"Add2AuthorsToProbSecConcObs_b1TocAmbCcdR21Sample1V13_Site3235.xml").toURI(), // 22
					ContentValidatorCuresTest.class.getResource(S+"Has2AuthorsInHeader_b1TocAmbS1_Site3235.xml").toURI(), // 23
					ContentValidatorCuresTest.class.getResource(R+"170.315_b1_toc_amb_sample1.xml").toURI(), // 24
					ContentValidatorCuresTest.class.getResource(R+"ModRef_AddNotesActivityEncounterEntry_b1TocAmbS1.xml").toURI(), // 25
					ContentValidatorCuresTest.class.getResource(R+"ModRef_AddNotesActivityProcActProcEntryRel_b1TocAmbS1.xml").toURI(), // 26
					ContentValidatorCuresTest.class.getResource(S+"hasDateAndTimeForAuthorTimeInDocLevAndVitalSignsSite3241.xml").toURI(), // 27
					ContentValidatorCuresTest.class.getResource(S+"hasDateOnlyForAuthorTimeInDocLevAndVitalSignsSite3241.xml").toURI(), // 28
					ContentValidatorCuresTest.class.getResource(S+"hasDateOnlyInverseForAuthorTimeInDocLevAndVitalSignsSite3241.xml").toURI(), // 29
					ContentValidatorCuresTest.class.getResource(R+"ModRef_AccurateDateAndTimeForAuthor_DocLvl_VitalSection_g9AAInpS1.xml").toURI(), // 30
					ContentValidatorCuresTest.class.getResource(S+"hasMixedDateAndTimeForAuthorTimeInDocLevAndVitalSignsSite3241.xml").toURI(), // 31
					ContentValidatorCuresTest.class.getResource(S+"SubHasNotesSecNoteActAuthorTimeTimezone0400_SITE-3252_b1TocAmbS3.xml").toURI(), // 32
					ContentValidatorCuresTest.class.getResource(S+"VitalSignsSecVSObsTime_happy413_repro_Site3261.xml").toURI(), // 33
					ContentValidatorCuresTest.class.getResource(S+"VitalSignsSecVSObsTime_happy413_repro_match_name_Site3261.xml").toURI(), // 34
					ContentValidatorCuresTest.class.getResource(S+"MedicationSecMedActProvTimeMismatched_happy416_Site3262.xml").toURI(), // 35
					ContentValidatorCuresTest.class.getResource(S+"MedicationSecMedActProvTimeFixToMatch_happy416_Site3262.xml").toURI(), // 36
					ContentValidatorCuresTest.class.getResource(S+"SUB_PROB_SEC_PROB_CONC_PROB_OBS_REPRO_HAPPYBLD420V2_MISSING_NAME_SITE_3265.xml").toURI(), // 37
					ContentValidatorCuresTest.class.getResource(S+"SUB_PROB_SEC_PROB_CONC_PROB_OBS_REPRO_HAPPYBLD420V2_ADD_MATCHING_NAME_SITE_3265.xml").toURI(), // 38
					ContentValidatorCuresTest.class.getResource(S+"SUB_CARE_TEAM_SEC_PERF_SITE_3259.xml").toURI(), // 39
					ContentValidatorCuresTest.class.getResource(S+"SUB_CARE_TEAM_SEC_PERF_AND_PART_SITE_3259.xml").toURI(), // 40
					ContentValidatorCuresTest.class.getResource(S+"SUB_REBECCA621_SITE_3300.xml").toURI(), // 41
					ContentValidatorCuresTest.class.getResource(S+"SUB_REBECCA621_MATCHED_TIME_SITE_3300.xml").toURI(), // 42
					ContentValidatorCuresTest.class.getResource(S+"SUB_REBECCA621_MATCHED_TIME_BUT_MISSING_LINKED_REFERENCES_IN_DOCUMENT_SITE_3300.xml").toURI(), // 43
					ContentValidatorCuresTest.class.getResource(S+"SUB_REBECCA621_ONE_NOTE_ACT_VALID_LINK_3300.xml").toURI(), // 44
					ContentValidatorCuresTest.class.getResource(S+"SUB_REBECCA621_ONE_NOTE_ACT_VALID_INLINE_3300.xml").toURI(), // 45
					ContentValidatorCuresTest.class.getResource(S+"SUB_REBECCA621_ONE_NOTE_ACT_BAD_LINK_BAD_EXT_AND_ROOT_3300.xml").toURI(), // 46
					ContentValidatorCuresTest.class.getResource(S+"SUB_REBECCA621_ONE_NOTE_ACT_EMPTY_ASSIGNED_AUTHOR_3300.xml").toURI(), // 47
					ContentValidatorCuresTest.class.getResource(S+"SUB_REBECCA621_ONE_NOTE_ACT_VALID_LINK_BAD_NAME_3300.xml").toURI(), // 48
					ContentValidatorCuresTest.class.getResource(S+"SUB_REBECCA621_ONE_NOTE_ACT_NO_LINKED_REF_IN_DOC_3300.xml").toURI(), // 49
					ContentValidatorCuresTest.class.getResource(S+"SUB_REBECCA621_ONE_NOTE_ACT_INLINE_AUTH_MISMATCHED_NAME_3300.xml").toURI(), // 50
					ContentValidatorCuresTest.class.getResource(S+"SubHasNotesSecNoteActAuthorTimeTimezone0400_SITE-3263_switchE2AndE3.xml").toURI(), // 51
					ContentValidatorCuresTest.class.getResource(S+"SubHasNotesSecNoteActAuthorTimeTimezone0400_SITE-3263_switchE2AndE1.xml").toURI(), // 52
					ContentValidatorCuresTest.class.getResource(S+"SUB_VALID_PROVENANCE_TS_INVALID_AUTHOR_PARTICIPATION_TS_SITE_3331.xml").toURI(), // 53
					ContentValidatorCuresTest.class.getResource(S+"SUB_INVALID_PROVENANCE_TS_VALID_AUTHOR_PARTICIPATION_TS_SITE_3331.xml").toURI(), // 54
					ContentValidatorCuresTest.class.getResource(S+"SUB_INVALID_PROVENANCE_TS_VALID_AUTHOR_PARTICIPATION_TS_SWAP_AUTHORS_SITE_3331.xml").toURI(), // 55
					ContentValidatorCuresTest.class.getResource(S+"SUB_INVALID_PROVENANCE_TS_INVALID_AUTHOR_PARTICIPATION_TS_SITE_3331.xml").toURI(), // 56
					ContentValidatorCuresTest.class.getResource(S+"SUB_VALID_PROVENANCE_TS_VALID_AUTHOR_PARTICIPATION_TS_SITE_3331.xml").toURI(), // 57
					ContentValidatorCuresTest.class.getResource(S+"SUB_INVALID_PROVENANCE_TS_INVALID_AUTHOR_PARTICIPATION_TS_SWAP_AUTHORS_SITE_3331.xml").toURI(), // 58
					ContentValidatorCuresTest.class.getResource(S+"SUB_3354_REPRO_PROBLEMSEC_CONC_OBS_PROVENANCE.xml").toURI(), // 59
					ContentValidatorCuresTest.class.getResource(S+"SUB_3362_IS_AUTHOR_OF_TYPE_PROVENANCE_NPE.xml").toURI(), // 60
					ContentValidatorCuresTest.class.getResource(S+"SUB_3350_JEREMY_PROVENANCE_LINKED_REFERENCE.xml").toURI() // 61 
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
		return validator.validate(validationObjective, referenceFileName, ccdaFileAsString, true, false, false, severityLevel);
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
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2, SUBMITTED_CCDA[SUB_EF],
					SeverityLevel.ERROR);
			printResults(results);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void cures_careTeamValidationTest() {
		printHeader("cures_careTeamContentValidationTest");
		try {
			ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V5,
					SUBMITTED_CCDA[SUB_CARE_TEAM_SEC_PERF_SITE_3259], SeverityLevel.ERROR);
			printResults(results);
			
			if(results.size() > 1)
				fail("There should not be any errrors");
			
			ArrayList<ContentValidationResult> addResults = validateDocumentAndReturnResultsCures(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V5,
					SUBMITTED_CCDA[SUB_CARE_TEAM_SEC_PERF_AND_PART_SITE_3259], SeverityLevel.ERROR);
			printResults(addResults);
			
			if(addResults.size() > 1)
				fail("There should not be any errrors");
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void cures_matchingSubAndRefExpectNoIssuesTest() {
		printHeader("cures_matchingSubAndRefExpectNoIssuesTest");

		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
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
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
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
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
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
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[SUB_HAS_BIRTH_SEX], SeverityLevel.ERROR);
		printResults(results);
		assertFalse("Expect NO birth sex error as sub has birth sex but got: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_labResultsNotFoundDueToNullFLavorOnResultOrganizerCodeSite3199Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// Result Organizer has nullFlavor on code but not part of parsing so irrelevant
		// Result Organizer/Observation/code HAS nullFlavor on all 7 instances
		// the first 6 of which should not have nullFlavor as they also have data.
		// The nullFlavor is causing nothing to be parsed in the sub, and therefore no lab data to be found
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE,
				SUBMITTED_CCDA[SUB_LAB_RESULTS_NOT_FOUND_SITE_3199], SeverityLevel.ERROR);			
		printResults(results);
		
		final String missingLabResultsMessage = "The scenario requires data related to patient's lab results, "
				+ "but the submitted C-CDA does not contain lab result data."; 
		assertTrue("Results should have contained the following message but did not: " + missingLabResultsMessage, 
				resultsContainMessage(missingLabResultsMessage, results, ContentValidationResultLevel.ERROR));	
	}
	
	@Test
	public void cures_labResultsStillNotFoundDueToNoNullFLavorOnResultOrganizerObservationCodesSite3199Test() {		
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		

		// Result Organizer does not have nullFlavor on code but not part of parsing so irrelevant 
		// and therefore doesn't fix C-CDA issue
		// Result Organizer/Observation/code still HAS nullFlavor on all 7 instances
		// the first 6 of which should not have nullFlavor as they also have data.
		// The nullFlavor is causing nothing to be parsed in the sub, and therefore no lab data to be found
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE,
				SUBMITTED_CCDA[SUB_LAB_RESULTS_STILL_NOT_FOUND_REMOVED_NULL_FLAVOR_ORG_CODE_SITE_3199],
				SeverityLevel.ERROR);		
		printResults(results);
		
		final String missingLabResultsMessage = "The scenario requires data related to patient's lab results, "
				+ "but the submitted C-CDA does not contain lab result data."; 
		assertTrue("Results should have contained the following message but did not: " + missingLabResultsMessage, 
				resultsContainMessage(missingLabResultsMessage, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_labResultsAreFoundDueToNoNullFLavorOnResultOrganizerObservationCodesSite3199Test() {		
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		

		// Result Organizer has nullFlavor on code but not part of parsing so irrelevant
		// Result Organizer/Observation/code does not have nullFlavor on the first 6 instances
		// which allows for a pass due to it being able to parse the codes/lab results.
		// 7th instance is not needed and has no data so nullFlavor actually makes sense
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE,
				SUBMITTED_CCDA[SUB_LAB_RESULTS_FOUND_REMOVED_NULL_FLAVOR_ORG_CODE_AND_OBS_CODES_SITE_3199],
				SeverityLevel.ERROR);
		printResults(results);
		
		final String missingLabResultsMessage = "The scenario requires data related to patient's lab results, "
				+ "but the submitted C-CDA does not contain lab result data."; 
		assertFalse("Results should not have contained the following message : " + missingLabResultsMessage, 
				resultsContainMessage(missingLabResultsMessage, results, ContentValidationResultLevel.ERROR));
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
				REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF, SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES], SeverityLevel.INFO);
		printResults(results);
		assertTrue("expecting 3 errors", results.size() == 3);
		ContentValidationResultLevel expectedSeverity = ContentValidationResultLevel.ERROR;
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error1, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error2, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error3, expectedSeverity);

		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES], SeverityLevel.WARNING);
		printResults(results);
		assertTrue("expecting (the same) 3 errors", results.size() == 3);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error1, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error2, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error3, expectedSeverity);
		
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
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
				REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
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
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[SUB_HAS_PROCEDURE_ACTIVITY_PROCEDURE_WITH_AUTHOR_IN_PROCEDURES], SeverityLevel.ERROR);
		printResults(results);
		
		// Procedures Section/ProcedureActivityProcedure author missing in ref yet is in sub (sub does have author, ref does not/not require it)
		// There is no situation where we should be requiring 0 of something. The submitted file should be allowed to have more information than the scenario.
		final String zeroAuthorsInRefOneOrMoreInSub = "The scenario requires a total of 0 Author Entries for "
				+ "Procedures Section/ProcedureActivityProcedure, however the submitted data had only 1 entries.";
		assertFalse("The results should not contain the unexpected message of: " + zeroAuthorsInRefOneOrMoreInSub, 
				resultsContainMessage(zeroAuthorsInRefOneOrMoreInSub, results, ContentValidationResultLevel.ERROR));			
	}	
	
	@Test
	public void cures_ZeroAuthorsInRefOneOrMoreInSubProceduresNoteActivityTest() {
		// SITE-3193		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
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
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE,
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
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE,
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
	
	@Test
	public void cures_SocialHistoryWithoutBirthSexObsTemplate_Site3094Test() {		
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		

		// Social History does not have a birth Sex observation entry. Instead, it has Social History Observation (missing extension though) 2.16.840.1.113883.10.20.22.4.38:2015-08-01
		// Expect no exception thrown handling this scenario, and expect a relevant birth sex error if no exception is thrown
		// THe XML for the code element is irrelevant, because the template is not the Birth Sex template
		ArrayList<ContentValidationResult> results = null;
		try {
			results = validateDocumentAndReturnResultsCures(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
					SUBMITTED_CCDA[SUB_SOCIAL_HISTORY_WITHOUT_BIRTH_SEX_OBS_TEMPLATE_SITE_3094], SeverityLevel.ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		if (results != null) {
			printResults(results);		
			final String requiresBirthSexError = "The scenario requires patient's birth sex to be captured "
					+ "as part of social history data, but submitted file does not have birth sex information";
			assertTrue("The results do not contain the expected message of: " + requiresBirthSexError, 
					resultsContainMessage(requiresBirthSexError, results, ContentValidationResultLevel.ERROR));
		}
	}
	
	@Test
	public void cures_SocialHistoryWithBirthSexObsTemplate_Site3094Test() {		
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		

		// Social History has a proper Birth Sex Observation entry. <templateId root="2.16.840.1.113883.10.20.22.4.200" extension="2016-06-01"/>
		// Expect no exception thrown handling this scenario, but do not expect a birth sex error for requiring it, but do expect one for the proper code, M, or F.
		// Notice in the below XML there is no @code, so @code is not handled, but, it's within the Birth Sex template (see file), so template inclusion is handled
		// Also notice that although there is a nullFlavor, it is not an exception for the requirement of the code 
		// because you cannot use a nullFlavor for a fixed single value in C-CDA.
		// <code nullFlavor="UNK" displayName="Birth Sex"/>  
		ArrayList<ContentValidationResult> results = null;
		try {
			results = validateDocumentAndReturnResultsCures(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
					SUBMITTED_CCDA[SUB_SOCIAL_HISTORY_WITH_BIRTH_SEX_OBS_TEMPLATE_SITE_3094], SeverityLevel.ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());			
		}
		
		if (results != null) {
			printResults(results);
			
			final String requiresBirthSexError = "The scenario requires patient's birth sex to be captured "
					+ "as part of social history data, but submitted file does not have birth sex information";
			assertFalse("The contain the unexpected message of: " + requiresBirthSexError, 
					resultsContainMessage(requiresBirthSexError, results, ContentValidationResultLevel.ERROR));
			
			final String requiresBirthSexCodeError = "The scenario requires patient's birth sex to use the codes M or F "
					+ "but the submitted C-CDA does not contain either of these codes.";
			assertTrue("The results do not contain the expected message of: " + requiresBirthSexCodeError, 
					resultsContainMessage(requiresBirthSexCodeError, results, ContentValidationResultLevel.ERROR));			
		}
	}
	
	@Test
	public void cures_socialHistorySmokingStatusValueCodeNotEqualToUnknownIfEverSmoked_b1_amb_s3_happy_Site3220Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// Without 'Unknown' code exception (has former smoker code):
		// Expect Fail AFTER fix and FAIL in prod BEFORE fix (regression test)
		// Targeted Snippet: <value xsi:type="CD" code="8517006" displayName="Former smoker" codeSystem="2.16.840.1.113883.6.96" />
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
				SUBMITTED_CCDA[ADD_SMOKING_STATUS_ENTRY_FORMER_SMOKER_B1_TOC_AMB_S3_SITE_3220], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario does not require data related to patient's Smoking Status, "
				+ "but the submitted C-CDA does contain Smoking Status data."; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));	
	}
	
	@Test
	public void cures_socialHistorySmokingStatusValueCodeNotEqualToUnknownIfEverSmoked_b1_inp_s3_jane_Site3220Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// Without 'Unknown' code exception (has former smoker code):
		// Expect Fail AFTER fix and FAIL in prod BEFORE fix (regression test)
		// Targeted Snippet: <value xsi:type="CD" code="8517006" displayName="Former smoker" codeSystem="2.16.840.1.113883.6.96" />
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_INP_VALIDATION_OBJECTIVE, REF_CURES_B1_INP_AMB_SAMPLE3_JANE,
				SUBMITTED_CCDA[ADD_SMOKING_STATUS_ENTRY_FORMER_SMOKER_B1_TOC_INP_S3_SITE_3220], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario does not require data related to patient's Smoking Status, "
				+ "but the submitted C-CDA does contain Smoking Status data."; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));	
	}
	
	@Test
	public void cures_socialHistorySmokingStatusValueCodeIsEqualToUnknownIfEverSmoked_b1_amb_s3_happy_Site3220Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// With 'Unknown' code exception:
		// Expect Pass AFTER fix and FAIL in prod BEFORE fix
		// Targeted Snippet: <value xsi:type="CD" code="266927001" displayName="Unknown if ever smoked" 
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
				SUBMITTED_CCDA[ADD_SMOKING_STATUS_ENTRY_UNKNOWN_SMOKER_B1_TOC_AMB_S3_SITE_3220], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario does not require data related to patient's Smoking Status, "
				+ "but the submitted C-CDA does contain Smoking Status data."; 
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}	
	
	@Test
	public void cures_socialHistorySmokingStatusValueCodeIsEqualToUnknownIfEverSmoked_b1_inp_s3_jane_Site3220Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// With 'Unknown' code exception:
		// Expect Pass AFTER fix and FAIL in prod BEFORE fix
		// Targeted Snippet: <value xsi:type="CD" code="266927001" displayName="Unknown if ever smoked"
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_INP_VALIDATION_OBJECTIVE, REF_CURES_B1_INP_AMB_SAMPLE3_JANE,
				SUBMITTED_CCDA[ADD_SMOKING_STATUS_ENTRY_UNKNOWN_SMOKER_B1_TOC_INP_S3_SITE_3220], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario does not require data related to patient's Smoking Status, "
				+ "but the submitted C-CDA does contain Smoking Status data."; 
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_socialHistorySmokingStatusValueCodeNotEqualToUnknownInAtLeastOneEntry_b1_amb_s3_happy_Site3220Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// Has 2 Smoking Status Observation entries, one passes with 'Unknown' exception and one fails since it is not the 'Unknown' exception 
		// Expect one failure error for the fail that is not the 'Unknown' exception
		// Expect Fail AFTER fix and FAIL in prod BEFORE fix (regression test)
		// Targeted Snippets: 
		// 1: <value xsi:type="CD" code="8517006" displayName="Former smoker" codeSystem="2.16.840.1.113883.6.96" />
		// 2: <value xsi:type="CD" code="266927001" displayName="Unknown if ever smoked" codeSystem="2.16.840.1.113883.6.96"/> 
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
				SUBMITTED_CCDA[ADD_SMOKING_STATUS_ENTRY_FORMER_AND_UNKNOWN_SMOKER_B1_TOC_AMB_S3_SITE_3220], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario does not require data related to patient's Smoking Status, "
				+ "but the submitted C-CDA does contain Smoking Status data."; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_VitalSignsSection5Organizers10Observations10AuthorsTotal_ExpectNoError_Site3232Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub > ref expect pass (sub only needs at least as many or equal to ref to pass, sub never needs more than ref)
		// From ETT GG: "The fact that the validator is throwing an error for "only" having 10 entries instead of the required 4 seems like a lingering bug."
		// https://groups.google.com/u/0/g/edge-test-tool/c/8AbZgOd-QgM
		// There are 5 VItal Signs Organizers and 10 VItal Signs Observations (within them, not all evenly spread).
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_INP_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_10_AUTHORS_TOTAL_REBECCA_SITE_3232],
				SeverityLevel.ERROR);
		printResults(results);		 
		
		final String message = "The scenario requires a total of 4 Author Entries for Vital Signs Section"
				+ "/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 10 entries.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_VitalSignsSection5Organizers10Observations2AuthorsTotal_ExpectError_Site3232Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub < ref expect fail (sub has to have at least as many as ref to pass)
		// https://groups.google.com/u/0/g/edge-test-tool/c/8AbZgOd-QgM
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_INP_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_2_AUTHORS_TOTAL_REBECCA_SITE_3232],
				SeverityLevel.ERROR);
		printResults(results);
		
		final String message = "The scenario requires a total of 4 Author Entries for Vital Signs Section"
				+ "/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 2 entries.";
		assertTrue("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_VitalSignsSection5Organizers10Observations4AuthorsTotal_ExpectNoError_Site3232Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub == ref (non 0) expect pass (if sub has as many as ref, then we are meeting the req)
		// https://groups.google.com/u/0/g/edge-test-tool/c/8AbZgOd-QgM
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_INP_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_4_AUTHORS_TOTAL_REBECCA_SITE_3232],
				SeverityLevel.ERROR);
		printResults(results);
		
		final String message = "The scenario requires a total of 4 Author Entries for Vital Signs Section"
				+ "/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 4 entries.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_VitalSignsSection5Organizers10Observations0AuthorsTotal_ExpectError_Site3232Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub < ref (sub is 0) expect fail (sub has to have at least as many as ref to pass)
		// https://groups.google.com/u/0/g/edge-test-tool/c/8AbZgOd-QgM
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_INP_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_0_AUTHORS_TOTAL_REBECCA_SITE_3232],
				SeverityLevel.ERROR);
		printResults(results);
		
		final String message = "The scenario requires a total of 4 Author Entries for Vital Signs Section"
				+ "/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 0 entries.";
		assertTrue("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_VitalSignsSection5Organizers10Observations0AuthorsTotal_ExpectNoError_Site3232Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub == ref (0) expect pass
		// https://groups.google.com/u/0/g/edge-test-tool/c/8AbZgOd-QgM
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_INP_VALIDATION_OBJECTIVE, MOD_REF_CURES_MODREF_REMOVE_VITAL_SIGNS_OBS_AUTHORS_E1_VDT_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_0_AUTHORS_TOTAL_REBECCA_SITE_3232],
				SeverityLevel.ERROR);
		printResults(results);
		
		// actual situation
		final String message = "The scenario requires a total of 0 Author Entries for Vital Signs Section"
				+ "/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 0 entries.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_ProblemSectionConcernObsRef1AuthSub2Auths_ExpectNoError_Site3235Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub > ref expect pass
		// https://groups.google.com/u/1/g/edge-test-tool/c/a2CRrzzDffY/m/Lt2f7IrYBAAJ
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_CURES_ADD_AUTHORS,
				SUBMITTED_CCDA[SUB_ADD_2_AUTHORS_TO_PROB_SEC_CONC_OBS_B1_TOC_AMB_CCD_R21_SAMPLE1V13_SITE3235],
				SeverityLevel.ERROR);
		printResults(results);
		
		// Problem Section/ProblemConcern/ProblemObservation author missing in sub but required in modified ref
		final String message = "The scenario requires a total of 1 Author Entries for "
				+ "Problem Section/ProblemConcern/ProblemObservation, "
				+ "however the submitted data had only 2 entries.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_Ref1AuthInHeaderSub2AuthsInHeader_ExpectNoError_Site3235Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());

		// sub > ref expect pass
		// https://groups.google.com/u/1/g/edge-test-tool/c/a2CRrzzDffY/m/Lt2f7IrYBAAJ		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[HAS_2_AUTHORS_INHEADER_B1_TOC_AMB_S1_SITE_3235], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario requires a total of 1 Author Entries for Document Level., "
				+ "however the submitted data had only 2 entries.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_NotesActivityInResultsParser_ExpectError_Site3153Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Note Activity Results entry
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_ADD_NOTES_ACTIVITY_ENCOUNTER_ENTRY_B1_TOC_AMB_S1,
				SUBMITTED_CCDA[SUB_DUPLICATE_OF_B1_TOC_AMB_SAMPLE1_REF], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message1 = "The scenario requires data related to patient's Notes for ";
		final String message2 = " , but the submitted C-CDA does not contain corresponding clinical Notes data";
		assertTrue("Results should have contained the following message but did not: " + message1, 
				resultsContainMessage(message1, results, ContentValidationResultLevel.ERROR));
		assertTrue("Results should have contained the following message but did not: " + message2, 
				resultsContainMessage(message2, results, ContentValidationResultLevel.ERROR));	
	}
	
	@Test
	public void cures_NotesActivityInResultsParser_ExpectNoError_Site3153Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Note Activity Results entry
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_ADD_NOTES_ACTIVITY_ENCOUNTER_ENTRY_B1_TOC_AMB_S1,
				SUBMITTED_CCDA[SUB_DUPLICATE_OF_MOD_REF_ADD_NOTES_ACTIVITY_ENCOUNTER_ENTRY_B1_TOC_AMB_S1],
				SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario requires data related to patient's Notes, "
				+ "but the submitted C-CDA does not contain Notes data."; 
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_NotesActivityInProceudureParser_ExpectError_Site3153Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Note Activity in Procedures Procedure Activity Procedure entryRelationship
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_ADD_NOTES_ACTIVITY_PAP_ENTRY_RELATIONSHIP_B1_TOC_AMB_S1,
				SUBMITTED_CCDA[SUB_DUPLICATE_OF_B1_TOC_AMB_SAMPLE1_REF], SeverityLevel.ERROR);
		printResults(results);
		
		final String message1 = "The scenario requires data related to patient's Notes for ";
		final String message2 = " , but the submitted C-CDA does not contain corresponding clinical Notes data";
		assertTrue("Results should have contained the following message but did not: " + message1, 
				resultsContainMessage(message1, results, ContentValidationResultLevel.ERROR));
		assertTrue("Results should have contained the following message but did not: " + message2, 
				resultsContainMessage(message2, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_NotesActivityInProceudureParser_ExpectNoError_Site3153Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Note Activity in Procedures Procedure Activity Procedure entryRelationship
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_ADD_NOTES_ACTIVITY_PAP_ENTRY_RELATIONSHIP_B1_TOC_AMB_S1,
				SUBMITTED_CCDA[SUB_DUPLICATE_OF_MOD_REF_ADD_NOTES_ACTIVITY_PAP_ENTRY_RELATIONSHIP_B1_TOC_AMB_S1],
				SeverityLevel.ERROR);
		printResults(results);
		
		final String message = "The scenario requires data related to patient's Notes, "
				+ "but the submitted C-CDA does not contain Notes data."; 
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}	
	
	@Test
	public void cures_ProvenanceTimeComparison_DocLevDatesDoNotMatch_SecLevDatesMatchTimeDoesNot_ETTGGFileFix_Site3241Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
				
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				G9_APIACCESS_INP_VALIDATION_OBJECTIVE, REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_HAS_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241], SeverityLevel.ERROR);
		printResults(results);		

		// Provenance at the document level (does not match in any way (date or time)):
		// in ref:
		// <time value="20150622"/>
		// in sub:
		// <time value="20210317101614-0500"/>
		// Note: This is 10:16 and 14 seconds for the time, we allow seconds so it's a valid format
		// There is NO orgName in either the ref or the sub (TODO: does there need to be orgName at doc level, I think NOT)
		// Notice how since there is no orgName, the message does not mention the missing name as an option for the error -
		// It is filtered out in CCDAAuthor.compareAuthors as expected
		// Expected result: The following Error should be produced:		
		String message = "The scenario requires Document Level Provenance data of time "
				+ "which was not found in the submitted data. The scenario time value is 20150622 "
				+ "and a submitted time value should at a minimum match the 8-digit date portion of the data.";
		expectError(message, results);
		
		// Provenance at the document level (does not match in any way (date or time)):
		// in ref:
		// <time value="20150622"/>
		// in sub:
		// <time value="20210317101614-0500"/>
		// Note: This is 10:16 and 14 seconds for the time, we allow seconds so it's a valid format
		// Expected result: The following Error should be produced:
		message = "The submitted Provenance (Time: Value) 20210317101614-0500 at Document Level is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', "
				+ "and finally, a 4-digit time-zone. The invalid time and time-zone portion of the value is 101614-0500"; 
		expectNoError(message, results);
		
		// 4: If the scenario includes a date only (no time), then the submitted file must match the exact date, 
		// but is allowed to have time as well, which matches any point in time, but also matches the required C-CDA format.
		//
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		//
		//  ref 1st occurrence:
		//   <time value="20150622"/>
		//  sub 1st occurrence:
		//   <time value="201506221100-0500"/>
		//  Comparison: 
		//   Date matches, sub has extra time info beyond the date. 
		//  Expected result: 
		//   A sub is allowed to have more data then the ref as long as it meets the refs reqs.
		//   Therefore, no error is expected for this occurrence.
		// 
		//  ref 2nd occurrence:
		//   <time value="20150622"/>
		//  sub 2nd occurrence:
		//   <time value="201506221100-0500"/>
		//  Comparison: 
		//   Date matches, sub has extra time info beyond the date.
		//  Expected result: 
		//   A sub is allowed to have more data then the ref as long as it meets the refs reqs.
		//   Therefore, no error is expected for this occurrence.
		// 
		//  ref 3rd occurrence:
		//   <time value="20150622"/>
		//  sub 3rd occurrence:
		//   <time value="201506221100-0500"/>
		//  Comparison: 
		//   Date matches, sub has extra time info beyond the date.
		//  Expected result: 
		//   A sub is allowed to have more data then the ref as long as it meets the refs reqs.
		//   Therefore, no error is expected for this occurrence.
		// 
		//  ref 4th occurrence:
		//   <time value="20150622"/>
		//  sub 4th occurrence:
		//   <time value="201506221100-0500"/>
		//  Comparison: 
		//   Date matches, sub has extra time info beyond the date.
		//  Expected result: 
		//   A sub is allowed to have more data then the ref as long as it meets the refs reqs.
		//   Therefore, no error is expected for this occurrence.		
		
		// Note: No further organizers contain an author in the ref, 
		// therefore, no more potential relevant errors can/should be created
		//
		// We can't check for a specific error to not be fired as that error would match one we do want fired in this same test.
		// Therefore, we must check the count. Currently, these are the only 2 errors we expect. 
		// However, in the future, if another UNRELATED error is appropriate due to an update, we will have to modify the document so it does not fire.
		// If the error is related, then it is a valid fail and breakdown of our logic, which must be addressed in our source code.
		assertTrue("Results should not have contained more than 1 error but did", results.size() < 2);
	}
	
	@Test
	public void cures_ProvenanceTimeComparison_ExactDateOnlyDate_DocLvlDif_SecLvlSame_Site3241Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// 1: If the scenario includes a date, then the submitted file must match that date exactly	
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				G9_APIACCESS_INP_VALIDATION_OBJECTIVE, REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_HAS_DATE_ONLY_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241], SeverityLevel.ERROR);
		printResults(results);

		// Provenance at the document level (does not match):
		// in ref:
		// <time value="20150622"/>
		// in sub:
		// <time value="20210317"/>	
		// Expected result: The following Error should be produced:
		String message = "The scenario requires Document Level Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is 20150622 and a submitted time value should at a minimum match the 8-digit date portion of the data.";
		expectError(message, results);
		
		// 1: If the scenario includes a date, then the submitted file must match that date exactly
		//
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		//
		//  ref 1st occurrence:
		//   <time value="20150622"/>
		//  sub 1st occurrence:
		//   <time value="20150622"/>
		//  Comparison: 
		//   Date matches exactly, date only 
		//  Expected result: 
		//   No error as a match
		// 
		//  Note: The next 3 occurrences are the same as the 1st
		// 
		// We can't check for a specific error to not be fired as that error would match one we do want fired in this same test.
		// Therefore, we must check the count. Currently, these is only 1 error we expect. 
		// However, in the future, if another UNRELATED error is appropriate due to an update, we will have to modify the document so it does not fire.
		// If the error is related, then it is a valid fail and breakdown of our logic, which must be addressed in our source code.		
		assertTrue("Results should not have contained more tha 1 error but did", results.size() < 2);	
	}
	
	@Test
	public void cures_ProvenanceTimeComparison_ExactDateOnlyDate_DocLvlSame_SecLvlDif_Site3241Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// 1: If the scenario includes a date, then the submitted file must match that date exactly	
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				G9_APIACCESS_INP_VALIDATION_OBJECTIVE, MOD_REF_G9_INP_SWAP_AUTH_PAR_WITH_PROV,
				SUBMITTED_CCDA[SUB_HAS_DATE_ONLY_INVERSE_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241],
				SeverityLevel.ERROR);
		printResults(results);
		
		// Provenance at the document level (matches):
		// in ref:
		// <time value="20150622"/>
		// in sub:
		// <time value="20150622"/>	
		// Expected result: The following Error should NOT be produced:
		String message = "The scenario requires Document Level Provenance data of time "
				+ "which was not found in the submitted data. The scenario time value is 20150622 and a submitted time value should "
				+ "at a minimum match the 8-digit date portion of the data.";
		expectNoError(message,results);
		
		// 1: If the scenario includes a date, then the submitted file must match that date exactly
		// *Note: We can't compare one to one. We can only prove that at least one exists in the sub that matches the ref.
		// So, the following rule is really more like, If the scenario includes a time wth a date, then the sub must include
		// at least one example of a time with that exact date as well.		
		//
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		//
		//  ref 1st occurrence:
		//   <time value="20150622"/>
		//  sub 1st occurrence:
		//   <time value="19990215"/>
		//  Comparison: 
		//   Date does not match 
		//  Expected result: 
		//   Error as does not match
		// 
		//  The next 3 occurrences are the same as the 1st (so we expect 4 of these errors...)
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time "
				+ "which was not found in the submitted data. The scenario time value is 20150622 and a submitted time value should "
				+ "at a minimum match the 8-digit date portion of the data.";
		expectError(message, results);
	}
	
	@Test
	public void cures_ProvenanceTimeComparison_DateAndTime_DocLvlSame_SecLvlSame_Site3241Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// 2: If the scenario also includes a time, then the submitted file must match the precision and format of that time, but not the exact values (reg ex)
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				G9_APIACCESS_INP_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA_DOC_AUTH_PRECISE_TO_TIME,
				SUBMITTED_CCDA[SUB_HAS_ACCURATE_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241],
				SeverityLevel.ERROR);
		removeBirthSexError(results);
		printResults(results);
		
		// Provenance at the document level
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100-0500"/>
		//  Comparison: 
		//   Exact match full precision (except seconds)
		//  Expected result: Pass		
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		//
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100-0500"/>
		//  Comparison: 
		//   Exact match full precision (except seconds)
		//  Expected result: Pass
		
		expectNoError("Provenance", results);
	}
	
	@Test
	public void cures_ProvenanceTimeComparison_DateAndTime_DocLvlNotPrecise_SecLvlNotPreciseAndMore_Site3241Test() {		
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// If the scenario also includes a time, then the submitted file must match the precision and format of that time, 
		// but not the exact values
		// *Note: Since we can't compare by index, since the match can be anywhere, we can't say if the ref instance at some index has
		// a full precision then the sub must match it.		
		// We can only confirm that at least one match exists within the document somewhere that has full precision.
		// For now, we are simply validating that the sub results have proper formatting for reg ex validation and for ref/sub matching that the base date matches somewhere. 
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				G9_APIACCESS_INP_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA_DOC_AUTH_PRECISE_TO_TIME,
				SUBMITTED_CCDA[SUB_HAS_MIXED_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241],
				SeverityLevel.ERROR);
		removeBirthSexError(results);
		printResults(results);

		// Provenance at the document level (sub does not include time-zone):
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100"/>
		//  Comparison: 
		//   Has no time-zone (has time only)
		//  Expected result: fail		
		String message = "The submitted Provenance (Time: Value) 201506221100 at Document Level is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 1100."; 
		expectError(message, results);
		
		// ---- Section level below ----		
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S1
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100-"/>
		//  Comparison: 
		//   Has dash but no timezone 
		//  Expected result: Fail
		message = "The submitted Provenance (Time: Value) 201506221100- at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 1 "
				+ "is invalid. Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', "
				+ "and finally, a 4-digit time-zone. The invalid time and time-zone portion of the value is 1100-.";
		expectError(message, results);				
	
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S2
		// in ref:
		// <time value="200312121100-0500"/> // Note: used to be: <time value="201506221100-0500"/>
		// in sub:
		// <time value="201901113333-0500"/>
		//  Comparison: 
		//   Has different base level date (also has different time but that's not an error to have if valid format)
		//   Therefore, we aren't expecting a validation error, but, a match error, if, the sub doesn't have at least one example of 20031212(any time/timezone)
		//  Expected result: Fail
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is 200312121100-0500 and a submitted time value should at a minimum match the 8-digit date portion of the data.";
		expectError(message, results);	

		// add has different timezone
		// fix all these issues with sub times
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S3
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100*0500"/>
		//  Comparison: 
		//   Has incorrect format (* instead of + or -)
		//  Expected result: Fail
		message = "The submitted Provenance (Time: Value) 201506221100*0500 at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 3 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 1100*0500.";
		expectError(message, results);	
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S4
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100-06001234"/>
		//  Comparison: 
		//   Has too many characters in time-zone
		//  Expected result: Fail
		message = "The submitted Provenance (Time: Value) 201506221100-06001234 at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 4 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 1100-06001234.";
		expectError(message, results);
				
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S5
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="2015062211007878-0500"/>
		//  Comparison: 
		//   Has too many characters in time, 2 past seconds, 8 total 
		//  Expected result: Fail
		message = "The submitted Provenance (Time: Value) 2015062211007878-0500 at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 5 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 11007878-0500.";
		expectError(message, results);		

		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S6
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="2015062211000500"/>
		//  Comparison: 
		//   Missing time-zone separator
		//  Expected result: Fail
		message = "The submitted Provenance (Time: Value) 2015062211000500 at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 6 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 11000500.";
		expectError(message, results);

		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S7
		// in ref:
		// <time value="999906221100-0500"/>
		// in sub:
		// <time value="292906221100+1234"/>
		//  Comparison: 
		//   Year is wrong and has random time-zone but that part is OK, and uses plus vs minus, also OK, so fails date portion only
		//  Expected result: Fail
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is 999906221100-0500 and a submitted time value should at a minimum match the 8-digit date portion of the data.";		
		expectError(message, results);
		// 2nd test
		message = "The submitted Provenance (Time: Value) 292906221100+1234 at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 7 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 1100+1234.";
		expectNoError(message, results);

/*
		// If the scenario includes a time, then the sub must include a time (in general) (has it's own specific error)
		// TODO: Is the above req even possible to enforce? See note at top of method for reasoning (can't compare 1to1 by index, only looking for one sub instance to match)
		// -Maybe we could write some logic to check that at least one of the subs with that matching date, has a full precision version (with any digits) somewhere... 
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S8
		// in ref:
		// <time value="333306221100-0500"/>
		// in sub:
		// <time value="33330622"/>
		//  Comparison: 
		//   Sub has date only, which matches, but needs a time since ref has one.
		//  Expected result: Fail
//		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
//				+ "The scenario Author Provenance (Time: Value) 201506221100-0500 is more precise than the date, "
//				+ "but the submitted C-CDA (Time: Value) 29290622 does not include time or time-zone data.";
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation (Time: Value) Provenance data which was not found "
				+ "in the submitted data. The scenario value is 333306221100-0500 and a submitted value must at a minimum match the 8-digit date portion of the data.";
		expectError(message, results);
*/
				
		// If the scenario contains any value at all, and the sub does not, an error is produced
		// Note, this can only be kicked off if the sub doesn't containt that time value elsewhere...since we can't do index comparison
		// So for example, had the ref value been 20150622, there would be no error, even when sub has no element, as there are plenty of matches for that.
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S9		
		// in ref:
		// <time value="44440622"/>
		// in sub:
		// NO TIME ELEMENT
		//  Comparison: 
		//   Sub has no time element but needs one since ref has one. Refs time element is precise to day
		//  Expected result: Fail
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is 44440622 and a submitted time value should at a minimum match the 8-digit date portion of the data.";		
		expectError(message, results);

		// If the scenario contains any value at all, and the sub does not, an error is produced
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S10
		// in ref:
		// <time value="555506221100-0500"/>
		// in sub:
		// NO TIME ELEMENT
		//  Comparison: 
		//   Sub has no time element but needs one since ref has one. Refs time element is precise to time-zone
		//  Expected result: Fail
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is 555506221100-0500 and a submitted time value should at a minimum match the 8-digit date portion of the data.";		
		expectError(message, results);	


		/// EXPECT pass
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S11
		// in ref:
		// <time value="991106221100-0500"/>
		// in sub:
		// <time value="991106221100-0500"/>
		//  Comparison: 
		//   perfect match
		//  Expected result: Pass
		message = "The submitted Provenance (Time: Value) 991106221100-0500 at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 11 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 991106221100-0500.";
		expectNoError(message, results);

		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S12
		// in ref:
		// <time value="992206221100-0500"/>
		// in sub:
		// <time value="992206229999-0500"/>
		//  Comparison: 
		//   match to ref date but has dif time (9999)
		//  Expected result: Pass
		message = "The submitted Provenance (Time: Value) 992206229999-0500 at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 12 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 992206229999-0500.";		
		expectNoError(message, results);		

		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S13
		// in ref:
		// <time value="993306221100-0500"/>
		// in sub:
		// <time value="993306221100-6666"/>
		//  Comparison: 
		//   match to ref date but has dif time-zone (6666)
		//  Expected result: Pass
		message = "The submitted Provenance (Time: Value) 993306221100-6666 at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 13 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 993306221100-6666.";
		expectNoError(message, results);
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S14
		// in ref:
		// <time value="994406221100-0500"/>
		// in sub:
		// <time value="994406221100+0500"/>
		//  Comparison: 
		//   match to ref date but has dif time-zone separator plus vs minus which is OK
		//  Expected result: Pass
		message = "The submitted Provenance (Time: Value) 994406221100+0500 at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 14 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 1100+0500.";
		expectNoError(message, results);

		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S15
		// in ref:
		// <time value="99550622"/>
		// in sub:
		// <time value="99550622"/>
		//  Comparison: 
		//   scenario doesn't have precision past date, so sub doesn't need to either
		//  Expected result: Pass
		message = "The submitted Provenance (Time: Value) 99550622 at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation index 15 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 99550622.";
		expectNoError(message, results);		

		// If the scenario does not include a date, but the sub does, that is not an error, as more data is acceptable
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S16
		// in ref:
		// no author at all
		// in sub:
		// <time value="99660622"/>
		//  Comparison: 
		//   scenario doesn't have an author at all, ref has date level precision on time
		//  Expected result: Pass
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is null and a submitted time value should at a minimum match the 8-digit date portion of the data.";		
		expectNoError(message, results);
					
		// If the scenario does not include a date, but the sub does, that is not an error, as more data is acceptable
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S17
		// in ref:
		// no time at all
		// in sub:
		// <time value="99770622"/>
		//  Comparison: 
		//   scenario doesn't have a time at all, ref has date level precision on time
		//  Expected result: Pass
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is null and a submitted time value should at a minimum match the 8-digit date portion of the data.";
		expectNoError(message, results);

		// If the scenario does not include a date, then the sub file would not need to include a date.
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S18
		// in ref:
		// no author at all
		// in sub:
		// no author at all
		//  Comparison: 
		//   scenario doesn't have an author at all, nor does ref
		//  Expected result: Pass
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is null and a submitted time value should at a minimum match the 8-digit date portion of the data.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		expectNoError(message, results);	
				
		// If the scenario does not include a date, then the sub file would not need to include a date.
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S19
		// in ref:
		// no time at all
		// in sub:
		// no time at all
		//  Comparison: 
		//   scenario doesn't have an time at all, nor does ref
		//  Expected result: Pass
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is null and a submitted time value should at a minimum match the 8-digit date portion of the data.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		expectNoError(message, results);
			
		
		// More Expect Fail
		
		// If the scenario contains any value at all, and the sub does not, an error is produced
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S20
		// in ref:
		// <time value="77110622"/>
		// in sub:
		// NO AUTHOR ELEMENT
		//  Comparison: 
		//   Sub has no author element but needs one since ref has one and a time element. Refs time element value is precise to day
		//  Expected result: Fail
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is 77110622 and a submitted time value should at a minimum match the 8-digit date portion of the data.";
		expectError(message, results);
		
		// If the scenario contains any value at all, and the sub does not, an error is produced
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S21
		// in ref:
		// <time value="773306221100-0500"/>
		// in sub:
		// NO AUTHOR ELEMENT
		//  Comparison: 
		//   Sub has no time element but needs one since ref has one. Refs time element is precise to time-zone
		//  Expected result: Fail
		message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance data of time which was not found in the submitted data. "
				+ "The scenario time value is 773306221100-0500 and a submitted time value should at a minimum match the 8-digit date portion of the data.";		
		expectError(message, results);	
		// This one also triggers the author entry count req - because it's the entire author missing vs just a time
		// Note this is 18 instead of 21 because some of the observations are missing authors in ref
		message = "The scenario requires a total of 19 Author Entries for Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation, "
				+ "however the submitted data had only 18 entries.";
		expectError(message, results);
	}
	
	@Test
	public void cures_NotesSectionNoteActivityAuthorTime_DoNotEnforceTimeZoneDifSite3252And3263Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// https://groups.google.com/g/edge-test-tool/c/0S-BHfJDgUY
		// "...getting the error for the clinical notes (Happy Kid for the b1) that is requiring ET time 
		// but not considering daylight saving. Here is the error"
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_UPDATE_NOTES_TIMES_CURES_B1_TOC_AMB_SAMPLE3_HAPPY,
				SUBMITTED_CCDA[SUB_HAS_NOTES_SEC_NOTE_ACT_AUTHOR_TIME_TIMEZONE_0400_SITE_3252],
				SeverityLevel.ERROR);
		removeBirthSexError(results);
		printResults(results);
		
		// T1
        // Notes Section/Note Activity entry 1/author/time
		//
		// ref | ModRef_ChangeNotesTimes_b1TocAmbS3.xml
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="202006221100-0500" />		
		// sub | SubHasNotesSecNoteActAuthorTimeTimezone0400_SITE-3252_b1TocAmbS3.xml
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="202006221100-0400" />
		//  Comparison: 
		//  time zone is 0400 in sub vs 0500 in ref
		//  Expected result: Pass (since we don't care if time-zone is different only that it is valid)
		String message = "The Comparing Author Time for  , Comparing Author Entry for : Notes Section "
				+ "corresponding to the code 11488-4 ( Time Value ) is : 202006221100-0500 , "
				+ "but submitted CCDA ( Time Value ) is : 202006221100-0400 which does not match ";
		expectNoError(message, results);
		
		/*
		 * As per SITE-3263, we could only fail the final index entry.
		 * This test ensures we can fail an entry at at different index, in this case, the 2nd index in the array. 
		 * Note: If there is only 1 entry, the final index will be the first. So it was possible, even with the bug,
		 * to fail the 1st index, in that case. This is likely how the bug was left unnoticed for some time.
		 * 
		 * The bug defined in SITE-3263 is caused by:
		 * 
		 * However, due to the high risk (complexity and time)/low value (priority, user interest, and low chance of occurrence)
		 * nature of resolving the bug defined in SITE-3263, 
		 * tests T2, T4, T5, and T6, etc. are not yet expected to pass. And, have been commented out for now.
		 * If we do resolve the issue, these tests should be uncommented, and would be expected to pass.
		 * 
		 * The bug which causes Note Entry to appear to only compare the final entry vs all of them is caused by: 
		 * We use a map where the key is the code/translation/@code. But that key is not always unique. 
		 * For example, if we have multiple entries with the same key. In that case, only the last entry will be compared, 
		 * because when the map is created, it will continue to overwrite the key until it is only actually storing the last entry. 
		 * This means that the sub will only ever appear to contain 1 entry, regardless of how many. And it can never fail, 
		 * for any entry other than the last index.
		 * See CCDANotesActivity.compareNotesActivityEntryLevel and note how the maps are created prior.
		 */
		// T2
		// Notes Section/Note Activity entry 2/author/time
		//
		// ref | ModRef_ChangeNotesTimes_b1TocAmbS3.xml
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="199906221100-0500" />		
		// sub | SubHasNotesSecNoteActAuthorTimeTimezone0400_SITE-3252_b1TocAmbS3.xml
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="149206221100-0500" />
		//  Comparison: 
		//  base date is 1492 in sub vs expected 1999 in ref
		//  Expected result: Fail (since we don't have an example of 19990622 but instead sub has 14920622)
		/*
		message = "The Comparing Author Time for Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11488-4 ( Time Value ) is : 199906221100-0500 , "
				+ "but submitted CCDA ( Time Value ) is : 149206221100-0500 which does not match"; 		
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		*/		
		
		// T3
		// Notes Section/Note Activity entry 3/author/time
		//
		// *Note: This is the only test which tests for the sub only, for an invalid TS (if provenance)
		// As such, this test, and the sub, were updated to be provenance instead of author participation (as per SITE-3331)		
		//
		// ref | ModRef_ChangeNotesTimes_b1TocAmbS3.xml
		// Note: The ref data is not relevant to how the error is produced in this case, but here it is regardless...
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="198506221100-0500" />		
		// sub | SubHasNotesSecNoteActAuthorTimeTimezone0400_SITE-3252_b1TocAmbS3.xml
		// <!-- Provenance = 5.6 -->
        // <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01"/>
		// 					<time value="198506221100-05AB" />		
		//  Comparison: 
		//  ref time zone is correct, 4 digits, sub time-zone is incorrect, has letters.
		//  Although since this is validation, comparison is irrelevant, only looking at sub, and sub fails alone
		//  Expected result: Fail (invalid format on time-zone - has 04AB, not 4 digits)
		message = "The submitted Provenance (Time: Value) 198506221100-05AB at Note Activity Author Entry "
				+ "for Notes Section corresponding to the code 11488-4 is invalid. "
				+ "Please ensure the time and time-zone starts with a 4 or 6-digit time, "
				+ "followed by a '+' or a '-', and finally, a 4-digit time-zone. "
				+ "The invalid time and time-zone portion of the value is 1100-05AB.";
		expectError(message, results);

		// T4
		// Notes Section/Note Activity entry 3/author/time
		//
		// ref | ModRef_ChangeNotesTimes_b1TocAmbS3.xml
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="199906221100-0500" />		
		// sub | SubHasNotesSecNoteActAuthorTimeTimezone0400_SITE-3252_switchE2AndE3.xml
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="149206221100-0500" />
		//  Comparison:
		//  base date is 1492 in sub vs expected 1999 in ref
		//  Expected result: Fail (since we don't have an example of 19990622 but instead sub has 14920622)
		//
		//  SITE-3263 ContentVal Provenance Notes Entry update to check all entries vs final only
		//  Note: This test uses a modified file of test 2 where it switches the 2nd and 3rd Note Activity Entry.
		//  There is a bug in the way Note Activity entries are parsed where only the last index will fail, 
		//  due to a shared key (based on code/translation/@code) in the map getting overwritten each time
		//  so that only the last index exists and is checked.
		/*
		results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_UPDATE_NOTES_TIMES_CURES_B1_TOC_AMB_SAMPLE3_HAPPY,
				SUBMITTED_CCDA[SUB_HAS_NOTES_SEC_NOTE_ACT_AUTHOR_TIME_TIMEZONE_0400_SWITCH_E2_AND_E3_FOR_SITE_3263],
				SeverityLevel.ERROR);
		removeBirthSexError(results);
		printResults(results);		
		message = "The Comparing Author Time for Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11488-4 ( Time Value ) is : 198506221100-0500 , "
				+ "but submitted CCDA ( Time Value ) is : 149206221100-0500 which does not match";
		// TODO: should the message above be looking for 1985 or 1999?
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		*/

		
		// T5
		// Notes Section/Note Activity entry 1/author/time
		//
		// ref | ModRef_ChangeNotesTimes_b1TocAmbS3.xml
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="199906221100-0500" />		
		// sub | SubHasNotesSecNoteActAuthorTimeTimezone0400_SITE-3252_switchE2AndE1.xml
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="149206221100-0500" />
		//  Comparison:
		//  base date is 1492 in sub vs expected 1999 in ref
		//  Expected result: Fail (since we don't have an example of 19990622 but instead sub has 14920622)
		//
		//  SITE-3263 ContentVal Provenance Notes Entry update to check all entries vs final only
		//  Note: This test uses a modified file of test 2 where it switches the 2nd and 1st Note Activity Entry.
		/*
		results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_UPDATE_NOTES_TIMES_CURES_B1_TOC_AMB_SAMPLE3_HAPPY,
				SUBMITTED_CCDA[SUB_HAS_NOTES_SEC_NOTE_ACT_AUTHOR_TIME_TIMEZONE_0400_SWITCH_E2_AND_E1_FOR_SITE_3263],
				SeverityLevel.ERROR);
		removeBirthSexError(results);
		printResults(results);	
		message = "The Comparing Author Time for Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11488-4 ( Time Value ) is : 198506221100-0500 , "
				+ "but submitted CCDA ( Time Value ) is : 149206221100-0500 which does not match";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		*/
		
		
		/*
		 * This is just like T2, except the mod ref now has 1 entry instead of 3
		 */
		// T6
		// Notes Section/Note Activity entry 2/author/time
		//
		// ref | ModRef_ChangeNotesTimes_b1TocAmbS3.xml
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="199906221100-0500" />		
		// sub | SubHasNotesSecNoteActAuthorTimeTimezone0400_SITE-3252_b1TocAmbS3.xml
        // <author>
        // <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//					<time value="149206221100-0500" />
		//  Comparison: 
		//  base date is 1492 in sub vs expected 1999 in ref
		//  Expected result: Fail (since we don't have an example of 19990622 but instead sub has 14920622)
		/*
		results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_UPDATE_NOTES_TIMES_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_ONE_NOTE_ACT_ENT,
				SUBMITTED_CCDA[SUB_HAS_NOTES_SEC_NOTE_ACT_AUTHOR_TIME_TIMEZONE_0400_SWITCH_E2_AND_E1_FOR_SITE_3263],
				SeverityLevel.ERROR);
		removeBirthSexError(results);
		printResults(results);		
		message = "The Comparing Author Time for Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11488-4 ( Time Value ) is : 199906221100-0500 , "
				+ "but submitted CCDA ( Time Value ) is : 149206221100-0500 which does not match";		
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		*/
	}
	
	@Test
	public void cures_VitalSignsSecVSObs_OldBasicReproSite3261Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());

		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_VITAL_SIGNS_SEC_VS_OBS_TIME_REPRO_SITE_3261,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SEC_VS_OBS_TIME_REPRO_HAPPY_413_SITE_3261],
				SeverityLevel.ERROR);
		printResults(results);
		
		// T1 entry 1 Obs 1
		// Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation/author/assignedAuthor/representedOrganization
		//
		// ref
		//  Author Participation only II (does not have Provenance II)
		// 	<name>Neighborhood Physicians Practice</name>
		// sub
		//  Author Participation only II AND Provenance II
		// 	<name>Epic FACILITY</name>
		// 
		//  Comparison: 
		//  No fail is possible as the ref does not have provenance and therefore cannot require it in the sub
		String message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation "
				+ "Provenance data of time and/or representedOrganization/name which was not found in the "
				+ "submitted data. The scenario time value is 202006221100-0500 and a submitted time value "
				+ "should at a minimum match the 8-digit date portion of the data. "
				+ "The scenario representedOrganization/name value is Neighborhood Physicians Practice and a "
				+ "submitted name should match. One or all of the prior issues exist and must be resolved.";
		expectNoError(message, results);			
	}
	
	@Test
	public void cures_VitalSignsSecVSObs_NewBasicReproSite3261_ModRefToAddProvenanceTest() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());

		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_ADD_PROV_SITE_3262,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SEC_VS_OBS_TIME_REPRO_HAPPY_413_SITE_3261],
				SeverityLevel.ERROR);
		printResults(results);
		
		// Using original doc from Matt with EPIC name, run expect fail test.
		// T1 entry 1 Obs 1
		// Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation/author/assignedAuthor/representedOrganization
		//
		// ref
		//  Author Participation only II AND Provenance II
		// 	<name>Neighborhood Physicians Practice</name>
		// sub
		//  Author Participation only II AND Provenance II
		// 	<name>Epic FACILITY</name>
		// 
		//  Comparison: 
		//  name inner text value fails as does not match ref, and at least one must match
		String message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation "
				+ "Provenance data of time and/or representedOrganization/name which was not found in the "
				+ "submitted data. The scenario time value is 202006221100-0500 and a submitted time value "
				+ "should at a minimum match the 8-digit date portion of the data. "
				+ "The scenario representedOrganization/name value is Neighborhood Physicians Practice and a "
				+ "submitted name should match. One or all of the prior issues exist and must be resolved.";
		expectError(message, results);		
	}	
	
	@Test
	public void cures_VitalSignsSecVSObs_UpdateReproTestFileNameToMatchSite3261Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Using original doc from Matt, update it to match <name>Neighborhood Physicians Practice</name> and run expect pass test.
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_VITAL_SIGNS_SEC_VS_OBS_TIME_REPRO_SITE_3261,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SEC_VS_OBS_TIME_REPRO_HAPPY_413_MATCH_NAME_SITE_3261],
				SeverityLevel.ERROR);
		printResults(results);
		
		// Using original doc from Matt with EPIC name, run expect fail test.
		// T1 entry 1 Obs 1
		// Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation/author/assignedAuthor/representedOrganization
		//
		// ref
		// 	<name>Neighborhood Physicians Practice</name>
		// sub
		//	<name>Neighborhood Physicians Practice</name>
		// 
		//  Comparison: 
		//  name inner text value passes as matches ref and and at least one must match/does
		String message = "The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation "
				+ "Provenance data of time and/or representedOrganization/name which was not found in the "
				+ "submitted data. The scenario time value is 202006221100-0500 and a submitted time value "
				+ "should at a minimum match the 8-digit date portion of the data. "
				+ "The scenario representedOrganization/name value is Neighborhood Physicians Practice and a "
				+ "submitted name should match. One or all of the prior issues exist and must be resolved.";
		expectNoError(message, results);
	}
	
	@Test
	public void cures_MedicationSectionMedicationActivity_OldBasicReproSite3262_RefHasNoProvSoCannotFailSubForNotHaving_Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// https://groups.google.com/g/edge-test-tool/c/wUtWOdf55dI/m/YlGHKa5WAQAJ
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
				SUBMITTED_CCDA[SUB_MEDICATION_SEC_MED_ACT_PROV_TIME_MISMATCHED_HAPPY_416_SITE_3262],
				SeverityLevel.ERROR);
		printResults(results);
		
		// Using original doc from Jess, run expect pass test.
		// T1 entry 1
		// Medications Section/MedicationActivity/author/time
		// Both Ref and Sub have only author participation to compare. None of these are provenenace.
		//
		// ref
		//    Author Participation templateId root="2.16.840.1.113883.10.20.22.4.119"
		// 	  <time value="202006221100-0500" />
		// sub
		//    Author Participation templateId root="2.16.840.1.113883.10.20.22.4.119"
		//    Note: This template has both author par and prov IIs
		// 	  <time value="20201130155603-0600" />
		// 
		//  Comparison: 
		//  value does not match in multiple ways. But, it's irrelevant, because the ref does not have provenance,
		//  and therefore cannot require the sub to have it. Instead, the ref has author participation
		String message = "The scenario requires Medications Section/MedicationActivity Provenance data of time"
				+ " and/or representedOrganization/name which was not found in the submitted data."
				+ " The scenario time value is 202006221100-0500 and a submitted time value should at a minimum "
				+ "match the 8-digit date portion of the data. The scenario representedOrganization/name value is "
				+ "Neighborhood Physicians Practice and a submitted name should match. "
				+ "One or all of the prior issues exist and must be resolved.";
		expectNoError(message, results);
	}
	
	@Test
	public void cures_MedicationSectionMedicationActivity_NewBasicReproSite3262_ModAuthParToProv_Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// https://groups.google.com/g/edge-test-tool/c/wUtWOdf55dI/m/YlGHKa5WAQAJ
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_REPLACE_MED_ACT_AUTHOR_PARTICIPATION_WITH_PROVENANCE_B1_TOC_AMB_S3_V3,
				SUBMITTED_CCDA[SUB_MEDICATION_SEC_MED_ACT_PROV_TIME_MISMATCHED_HAPPY_416_SITE_3262],
				SeverityLevel.ERROR);
		printResults(results);
		
		// Using original doc from Jess, run expect fail test.
		// T1 entry 1
		// Medications Section/MedicationActivity/author/time
		//
		// ref
		//    Provenance templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01"
		// 	  <time value="202006221100-0500" />
		// sub
		//    Author Participation templateId root="2.16.840.1.113883.10.20.22.4.119"
		//    Note: This template has both author par and prov IIs... Not sure this is legal, but that's the case.
		// 	  <time value="20201130155603-0600" />
		// 
		//  Comparison: 
		//  value does not match in multiple ways. What triggers the error is the base level 8-digit date mismatch though.
		//  This is a valid failure because the ref requires provenance, and the sub does not have a match.
		String message = "The scenario requires Medications Section/MedicationActivity Provenance data of time"
				+ " and/or representedOrganization/name which was not found in the submitted data."
				+ " The scenario time value is 202006221100-0500 and a submitted time value should at a minimum "
				+ "match the 8-digit date portion of the data. The scenario representedOrganization/name value is "
				+ "Neighborhood Physicians Practice and a submitted name should match. "
				+ "One or all of the prior issues exist and must be resolved.";
		expectError(message, results);
	}
	
	@Test
	public void cures_MedicationSectionMedicationActivity_FixSubTimeToMatchRef3262Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// https://groups.google.com/g/edge-test-tool/c/wUtWOdf55dI/m/YlGHKa5WAQAJ
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
				SUBMITTED_CCDA[SUB_MEDICATION_SEC_MED_ACT_PROV_TIME_FIX_TO_MATCH_HAPPY_416_SITE_3262],
				SeverityLevel.ERROR);
		printResults(results);
		
		// Fix original doc from Jess, run expect pass test.
		// T1 entry 1
		// Medications Section/MedicationActivity/author/time
		//
		// ref
		// 	  <time value="202006221100-0500" />
		// sub
		// 	  <time value="20200622155603-0600" />
		// 
		//  Comparison: 
		//  base level date matches in at least one entry (the 1st) which passes our test
		String message = "The scenario requires Medications Section/MedicationActivity Provenance data of time"
				+ " and/or representedOrganization/name which was not found in the submitted data."
				+ " The scenario time value is 202006221100-0500 and a submitted time value should at a minimum "
				+ "match the 8-digit date portion of the data. The scenario representedOrganization/name value is "
				+ "Neighborhood Physicians Practice and a submitted name should match. "
				+ "One or all of the prior issues exist and must be resolved.";
		expectNoError(message, results);
	}
	
	@Test
	public void cures_ProblemSecProbConcProbObs_BasicReproMissingNameInlineButHasInLinkedReferenceSite3265Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, RefVal, ContentVal: Handle reference id "Problems data provenance error"
		// https://groups.google.com/g/edge-test-tool/c/-kjLTnBuB00
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
				SUBMITTED_CCDA[SUB_PROB_SEC_PROB_CONC_PROB_OBS_REPRO_HAPPYBLD420V2_MISSING_NAME_SITE_3265],
				SeverityLevel.ERROR);
		printResults(results);
		
		// T1 entry 1
		// Problem Section/ProblemConcern/ProblemObservation
		//
		// ref
		//   <author>
        //    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//    <time value="202006221100-0500" />
		/* Note how this is inline non-referenced data... */
		//    <assignedAuthor>
		//      ...
		//      <representedOrganization>
        //        <id root="2.16.840.1.113883.19.5" />
        //        <name>Neighborhood Physicians Practice</name> <!-- *** requires name *** -->
        //      </representedOrganization>
        //    </assignedAuthor>
        //   </author>
		//
		// sub
		//   <author>
        //    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
        //    ...
		//    <time value="20200622110000-0500" />
		//      <assignedAuthor>
        //        <!--  This id's root and extension are used to match the referenced data elsewhere in the document -dbTest -->
        //        <id extension="236598153" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
		//          <!-- Would normally fail due to missing a representedOrganization/name however, 
		//               there is a reference linked via the id elements @root and @extension which returns
		//               valid data elsewhere in the document (where the matching root and extension exists. 
		//               Therefore, the provenance requirement is fulfilled via the linked data.
		//      	     The fix for this (used to fail without inline data) is part of SITE-3265 -dbTest -->
		/*
	        <!-- This is the referenced author which contains the required provenance data -dbTest -->
	        <author>
	          <templateId root="2.16.840.1.113883.10.20.22.4.119" />
	          <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
	          <time value="20200622" />
	          <assignedAuthor>
	            <id extension="236598153" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
	            <id extension="21657" root="1.2.840.114350.1.13.51786.1.7.2.836982" />
	            <id root="2.16.840.1.113883.4.6" nullFlavor="UNK" />
	            <code code="207Q00000X" codeSystem="2.16.840.1.113883.6.101" displayName="Family Medicine Physician">
	              <originalText>Family Medicine</originalText>
	            </code>
	            <addr use="WP">
	              <streetAddressLine>1006 Healthcare Dr.</streetAddressLine>
	              <city>Portland</city>
	              <state>OR</state>
	              <postalCode>97005</postalCode>
	            </addr>
	            <telecom use="WP" value="tel:+1-555-555-1006" />
	            <assignedPerson>
	              <name use="L">
	                <given>Albert</given>
	                <family>Davis</family>
	                <suffix qualifier="AC"> MD</suffix>
	              </name>
	            </assignedPerson>
	            <representedOrganization>
	              <id extension="50" root="1.2.840.114350.1.13.51786.1.7.2.688879" />
	              <id root="2.16.840.1.113883.4.2" nullFlavor="UNK" />
	              <name>Neighborhood Physicians Practice</name>
	              <addr use="WP">
	                <streetAddressLine>1979 MILKY WAY</streetAddressLine>
	                <city>Portland</city>
	                <state>OR</state>
	                <postalCode>97005</postalCode>
	                <country>USA</country>
	              </addr>
	            </representedOrganization>
	          </assignedAuthor>
	        </author>
	    */
		//  Comparison: 
		//  Base level time matches and name matches. The name does not exist inline, but it exists in a linked reference.
		//  The linked reference has a representedOrganization/name.
		//  At least one instance in sub must exist/match the ref repOrgName, and it exists in the reference.		
		
		expectNoError("The scenario requires Problem Section/ProblemConcern/ProblemObservation Provenance "
				+ "data of time and/or representedOrganization/name which was not found in the submitted data. "
				+ "The scenario time value is 202006221100-0500 and a submitted time value should at a minimum "
				+ "match the 8-digit date portion of the data. The scenario representedOrganization/name value "
				+ "is Neighborhood Physicians Practice and a submitted name should match. "
				+ "One or all of the prior issues exist and must be resolved.", results);
	}
	
	@Test
	public void cures_ProblemSecProbConcProbObs_AddMatchingNameInlineSite3265Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// SITE-3265 ETT GG NCC ContentVal "Problems data provenance error" User-error missing name
		// https://groups.google.com/g/edge-test-tool/c/-kjLTnBuB00
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
				SUBMITTED_CCDA[SUB_PROB_SEC_PROB_CONC_PROB_OBS_REPRO_HAPPYBLD420V2_ADD_MATCHING_NAME_SITE_3265],
				SeverityLevel.ERROR);
		printResults(results);
		
		// Fix original doc from Jess, run expect pass test 
		// We have fixed the submitted document by adding a matching name
		// T1 entry 1
		// Problem Section/ProblemConcern/ProblemObservation
		//
		// ref
		//   <author>
        //    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//    <time value="202006221100-0500" />
		//    <assignedAuthor>
		//      ...
		//      <representedOrganization>
        //        <id root="2.16.840.1.113883.19.5" />
        //        <name>Neighborhood Physicians Practice</name> <!-- *** requires name *** -->
        //      </representedOrganization>
        //    </assignedAuthor>
        //   </author>
		//
		// sub
		//   <author>
        //    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
        //    ...
		//    <time value="20200622110000-0500" />
		//      <assignedAuthor>
		//      	...
		//			<assignedPerson>
		//          <name>
		//            	<prefix>Dr</prefix>
		//              <given>Albert</given>
		//              <family>Davis</family>
		//          </name>
		//   		</assignedPerson>
		//	        <!-- Passes because it contains a representedOrganization with a matching name -dbTest -->	  					
		//			<representedOrganization>
		//	            <id root="2.16.840.1.113883.19.5" />
		//	            <name>Neighborhood Physicians Practice</name>
		//	        </representedOrganization>
		//
		//  Comparison: 
		//  Base level time matches and representedOrganization/name matches
		//  At least one instance in sub must exist/match the ref repOrgName, and does (the 1st instance in this case)
		
		expectNoError("The scenario requires Problem Section/ProblemConcern/ProblemObservation Provenance "
				+ "data of time and/or representedOrganization/name which was not found in the submitted data. "
				+ "The scenario time value is 202006221100-0500 and a submitted time value should at a minimum "
				+ "match the 8-digit date portion of the data. The scenario representedOrganization/name value "
				+ "is Neighborhood Physicians Practice and a submitted name should match. "
				+ "One or all of the prior issues exist and must be resolved.", results);
	}
	
	@Test
	public void cures_ProblemSecProbConcProbObsAndManyMore_OldPassesMismatchedTimeInlineSinceRefHasNoProvenanceSite3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
				SUBMITTED_CCDA[SUB_REBECCA621_SITE_3300],
				SeverityLevel.ERROR);
		printResults(results);
		
		// T1 entry 1
		// Problem Section/ProblemConcern/ProblemObservation
		//
		// ref
		//   <author>
		//    <!-- Author participation only -->
        //    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//    <time value="202006221100-0500" />
		/* Note how this is inline non-referenced data... */
		//    <assignedAuthor>
		//      ...
		//      <representedOrganization>
        //        <id root="2.16.840.1.113883.19.5" />
        //        <name>Neighborhood Physicians Practice</name> <!-- *** requires name *** -->
        //      </representedOrganization>
        //    </assignedAuthor>
        //   </author>
		//
		// sub
        /*
        <!-- prob sec / prob conc / Problem Observation author with a link (no inline data) -->
        <author>
          <!-- Author participation AND Provenance -->
          <templateId root="2.16.840.1.113883.10.20.22.4.119" />
          <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
          <!-- Invalid time on the original file does not match sub -dbTest -->
          <time value="20201130175429-0600" />
          <!-- this time WOULD pass as matches sub -->
          <!-- <time value="20200622" />  -->                    
          <assignedAuthor>
            <!--  This id's root and extension are used to match the referenced data elsewhere in the document -dbTest 
            A match DOES exist -->
            <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
          </assignedAuthor>
        </author>
        
        ...
        
        linked reference:
				<author>
					<!-- Author participation AND Provenance -->
                    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                    <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                    <!-- time does not match 1st 8 digits of sub time of 20200622 so this error is correct: 
                    The scenario requires Allergy Section/AllergyConcern/AllergyObservation Provenance data of time 
                    and/or representedOrganization/name which was not found in the submitted data. The scenario time value 
                    is 202006221100-0500 and a submitted time value should at a minimum match the 8-digit date portion of the 
                    data. The scenario representedOrganization/name value is Neighborhood Physicians Practice and a submitted 
                    name should match. One or all of the prior issues exist and must be resolved. -->                    
                    <time value="20201130" />
                    <assignedAuthor>
                      <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                      <id extension="10" root="1.2.840.114350.1.13.51786.1.7.2.836982" />
                      <id root="2.16.840.1.113883.4.6" nullFlavor="UNK" />
                      <addr use="WP">
                        <streetAddressLine>1002 Healthcare Dr</streetAddressLine>
                        <city>Portland</city>
                        <state>OR</state>
                        <postalCode>97266</postalCode>
                      </addr>
                      <telecom use="WP" value="tel:+1-555-555-1002" />
                      <assignedPerson>
                        <name use="L">
                          <given>Henry</given>
                          <family>Seven</family>
                        </name>
                      </assignedPerson>
                      <representedOrganization>
                        <id extension="50" root="1.2.840.114350.1.13.51786.1.7.2.688879" />
                        <id root="2.16.840.1.113883.4.2" nullFlavor="UNK" />
                        <name>Neighborhood Physicians Practice</name>
                        <addr use="WP">
                          <streetAddressLine>1979 MILKY WAY</streetAddressLine>
                          <city>Portland</city>
                          <state>OR</state>
                          <postalCode>97005</postalCode>
                          <country>USA</country>
                        </addr>
                      </representedOrganization>
                    </assignedAuthor>
                  </author>        
        
        */
		//  Comparison: 
		//  Base level time does not match, name does match (in reference).	
		//  Above is the Problem Section/Problem Concern/ Problem Observaation example, but there are many more, see errors below.
		//  However, none of this matters since there is no Provenance in the ref file so it cannot be required in the sub.
		
		//  These errors are partial messages...
		expectNoError("The scenario requires Problem Section/ProblemConcern/ProblemObservation Provenance", results); // x1		
		expectNoError("The scenario requires Allergy Section/AllergyConcern/AllergyObservation Provenance", results); // x2
		expectNoError("The scenario requires Medications Section/MedicationActivity Provenance", results); // x3
		expectNoError("The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance", results); //x10
	}
	
	@Test
	public void cures_ProblemSecProbConcProbObsAndManyMore_NewFailsMismatchedTimeInlineSite3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_RPLC_AUTH_PAR_WITH_PROV_SITE_3300,
				SUBMITTED_CCDA[SUB_REBECCA621_SITE_3300],
				SeverityLevel.ERROR);
		printResults(results);
		
		// T1 entry 1
		// Problem Section/ProblemConcern/ProblemObservation
		//
		// ref
		//   <author>
		//    <!-- Provenance only -->
        //    <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
		//    <time value="202006221100-0500" />
		/* Note how this is inline non-referenced data... */
		//    <assignedAuthor>
		//      ...
		//      <representedOrganization>
        //        <id root="2.16.840.1.113883.19.5" />
        //        <name>Neighborhood Physicians Practice</name> <!-- *** requires name *** -->
        //      </representedOrganization>
        //    </assignedAuthor>
        //   </author>
		//
		// sub
        /*
        <!-- prob sec / prob conc / Problem Observation author with a link (no inline data) -->
        <author>
          <!-- Author participation AND Provenance -->
          <templateId root="2.16.840.1.113883.10.20.22.4.119" />
          <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
          <!-- Invalid time on the original file does not match sub -dbTest -->
          <time value="20201130175429-0600" />
          <!-- this time WOULD pass as matches sub -->
          <!-- <time value="20200622" />  -->                    
          <assignedAuthor>
            <!--  This id's root and extension are used to match the referenced data elsewhere in the document -dbTest 
            A match DOES exist -->
            <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
          </assignedAuthor>
        </author>
        
        ...
        
        linked reference:
				<author>
					<!-- Author participation AND Provenance -->
                    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                    <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                    <!-- time does not match 1st 8 digits of sub time of 20200622 so this error is correct: 
                    The scenario requires Allergy Section/AllergyConcern/AllergyObservation Provenance data of time 
                    and/or representedOrganization/name which was not found in the submitted data. The scenario time value 
                    is 202006221100-0500 and a submitted time value should at a minimum match the 8-digit date portion of the 
                    data. The scenario representedOrganization/name value is Neighborhood Physicians Practice and a submitted 
                    name should match. One or all of the prior issues exist and must be resolved. -->                    
                    <time value="20201130" />
                    <assignedAuthor>
                      <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                      <id extension="10" root="1.2.840.114350.1.13.51786.1.7.2.836982" />
                      <id root="2.16.840.1.113883.4.6" nullFlavor="UNK" />
                      <addr use="WP">
                        <streetAddressLine>1002 Healthcare Dr</streetAddressLine>
                        <city>Portland</city>
                        <state>OR</state>
                        <postalCode>97266</postalCode>
                      </addr>
                      <telecom use="WP" value="tel:+1-555-555-1002" />
                      <assignedPerson>
                        <name use="L">
                          <given>Henry</given>
                          <family>Seven</family>
                        </name>
                      </assignedPerson>
                      <representedOrganization>
                        <id extension="50" root="1.2.840.114350.1.13.51786.1.7.2.688879" />
                        <id root="2.16.840.1.113883.4.2" nullFlavor="UNK" />
                        <name>Neighborhood Physicians Practice</name>
                        <addr use="WP">
                          <streetAddressLine>1979 MILKY WAY</streetAddressLine>
                          <city>Portland</city>
                          <state>OR</state>
                          <postalCode>97005</postalCode>
                          <country>USA</country>
                        </addr>
                      </representedOrganization>
                    </assignedAuthor>
                  </author>        
        
        */
		//  Comparison: 
		//  Base level time does not match, name does match (in reference). Expect fail.		
		//  Above is the Problem Section/Problem Concern/ Problem Observaation example, but there are many more, see errors below
		
		//  These errors are partial messages...
		expectError("The scenario requires Problem Section/ProblemConcern/ProblemObservation Provenance", results); // x1		
		expectError("The scenario requires Allergy Section/AllergyConcern/AllergyObservation Provenance", results); // x2
		expectError("The scenario requires Medications Section/MedicationActivity Provenance", results); // x3
		expectError("The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance", results); //x10
	}
		
	@Test
	public void cures_ProblemSecProbConcProbObsAndManyMore_MatchedTimeInlineSite3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
				SUBMITTED_CCDA[SUB_REBECCA621_MATCHED_TIME_SITE_3300],
				SeverityLevel.ERROR);
		printResults(results);
		
		// T1 entry 1
		// Problem Section/ProblemConcern/ProblemObservation
		//
		// ref
		//   <author>
        //    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//    <time value="202006221100-0500" />
		/* Note how this is inline non-referenced data... */
		//    <assignedAuthor>
		//      ...
		//      <representedOrganization>
        //        <id root="2.16.840.1.113883.19.5" />
        //        <name>Neighborhood Physicians Practice</name> <!-- *** requires name *** -->
        //      </representedOrganization>
        //    </assignedAuthor>
        //   </author>
		//
		// sub
        /*
	      <!-- prob sec / prob conc / Problem Observation author with a link (no inline data) -->
	      <author>
	        <templateId root="2.16.840.1.113883.10.20.22.4.119" />
	        <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
	        <!-- !FIXED! Invalid time on the original file does not match sub -dbTest -->
	        <!-- <time value="20201130175429-0600" /> -->
	        <!-- this time WOULD pass as matches sub -->
	        <time value="20200622" />          
	        <assignedAuthor>
	          <!--  This id's root and extension are used to match the referenced data elsewhere in the document -dbTest 
	          A match DOES exist and is correct and is required since the name is not here inline -->
	          <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
	        </assignedAuthor>
	      </author>
        
        ...
        
        linked reference:
                  <!--  Allergy Observation 1  -dbTest -->
                  <author>
                    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                    <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                    <!-- !FIXED! time does not match 1st 8 digits of sub time of 20200622 so this error is correct: 
                    The scenario requires Allergy Section/AllergyConcern/AllergyObservation Provenance data of time and/or representedOrganization/name which was not found in the submitted data. The scenario time value is 202006221100-0500 and a submitted time value should at a minimum match the 8-digit date portion of the data. The scenario representedOrganization/name value is Neighborhood Physicians Practice and a submitted name should match. One or all of the prior issues exist and must be resolved. -->                    
                    <!-- <time value="20201130" /> -->
                    <!-- Note: This doesn't use a reference, the data is inline. And, even though there are other allergies sections with missing data
                    we were only looking for 1 in the scenario that matched, so they can be erroneous without triggering a content error.
                    BTW, the REASON this is inline, is because the IS the reference that other links are using... -dbTest -->
                    <time value="20200622" />
                    <assignedAuthor>
                      <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                      <id extension="10" root="1.2.840.114350.1.13.51786.1.7.2.836982" />
                      <id root="2.16.840.1.113883.4.6" nullFlavor="UNK" />
                      <addr use="WP">
                        <streetAddressLine>1002 Healthcare Dr</streetAddressLine>
                        <city>Portland</city>
                        <state>OR</state>
                        <postalCode>97266</postalCode>
                      </addr>
                      <telecom use="WP" value="tel:+1-555-555-1002" />
                      <assignedPerson>
                        <name use="L">
                          <given>Henry</given>
                          <family>Seven</family>
                        </name>
                      </assignedPerson>
                      <representedOrganization>
                        <id extension="50" root="1.2.840.114350.1.13.51786.1.7.2.688879" />
                        <id root="2.16.840.1.113883.4.2" nullFlavor="UNK" />
                        <name>Neighborhood Physicians Practice</name>
                        <addr use="WP">
                          <streetAddressLine>1979 MILKY WAY</streetAddressLine>
                          <city>Portland</city>
                          <state>OR</state>
                          <postalCode>97005</postalCode>
                          <country>USA</country>
                        </addr>
                      </representedOrganization>
                    </assignedAuthor>
                  </author>       
        
        */
		//  Comparison: 
		//  Base level time DOES match, name matches in linked reference in most cases, and in some cases, inline. Expect pass.	
		//  Above is the Problem Section/Problem Concern/ Problem Observaation example, but there are many more, see errors below
		
		//  These errors are partial messages...		
		expectNoError("The scenario requires Allergy Section/AllergyConcern/AllergyObservation Provenance", results); // x2
		expectNoError("The scenario requires Problem Section/ProblemConcern/ProblemObservation Provenance", results); // x1
		expectNoError("The scenario requires Medications Section/MedicationActivity Provenance", results); // x3
		expectNoError("The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance", results); //x10		
	}
	
	// NO linkedRefAUths in document AND doc has links that are looking for that variable (ref does not have prov)
	@Test
	public void cures_ProblemSecProbConcProbObsAndManyMore_OldAuthorLinksButNoMatchAsNoLinkedReferenceAuthorsExistButNoProvSite3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V2,
				SUBMITTED_CCDA[SUB_REBECCA621_MATCHED_TIME_BUT_MISSING_LINKED_REFERENCES_IN_DOCUMENT_SITE_3300],
				SeverityLevel.ERROR);
		printResults(results);
		
		// T1 entry 1
		// Problem Section/ProblemConcern/ProblemObservation
		//
		// ref
		//   <author>
		//    <!-- Author participation only -->
        //    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//    <time value="202006221100-0500" />
		/* Note how this is inline non-referenced data... */
		//    <assignedAuthor>
		//      ...
		//      <representedOrganization>
        //        <id root="2.16.840.1.113883.19.5" />
        //        <name>Neighborhood Physicians Practice</name> <!-- *** requires name *** -->
        //      </representedOrganization>
        //    </assignedAuthor>
        //   </author>
		//
		// sub
        /*
	      <!-- prob sec / prob conc / Problem Observation author with a link (no inline data) -->
	      <author>
	        <templateId root="2.16.840.1.113883.10.20.22.4.119" />
	        <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
	        <!-- !FIXED! Invalid time on the original file does not match sub -dbTest -->
	        <!-- <time value="20201130175429-0600" /> -->
	        <!-- this time WOULD pass as matches sub -->
	        <time value="20200622" />          
	        <assignedAuthor>
	          <!--  This id's root and extension are used to match the referenced data elsewhere in the document -dbTest 
	          A match DOES exist and is correct and is required since the name is not here inline -->
	          <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
	        </assignedAuthor>
	      </author>
        
        ...
        
        linked reference:
        N/A: Missing/doesn't exist - submitted authorsWithLinkedReferenceData is null or empty 
        
        */
		//  Comparison: 
		//  Base level time DOES match, nothing matches in linked references because they don't exist at all.
		//  However, it doesn't matter, we can't fail because the ref does not require (since it does not have) provenance.
		//  Instead, it only has author participation. So, we can't require it in the sub and expect a pass.
		
		//  These errors are partial messages...		
		expectNoError("The scenario requires Problem Section/ProblemConcern/ProblemObservation Provenance", results); // x1
		expectNoError("The scenario requires Allergy Section/AllergyConcern/AllergyObservation Provenance", results); // x2
		expectNoError("The scenario requires Medications Section/MedicationActivity Provenance", results); // x3
		expectNoError("The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance", results); //x10		
	}
	
	// NO linkedRefAUths in document AND doc has links that are looking for that variable (ref has prov)	
	@Test
	public void cures_ProblemSecProbConcProbObsAndManyMore_NewAuthorLinksButNoMatchAsNoLinkedReferenceAuthorsExistSite3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_RPLC_AUTH_PAR_WITH_PROV_SITE_3300,
				SUBMITTED_CCDA[SUB_REBECCA621_MATCHED_TIME_BUT_MISSING_LINKED_REFERENCES_IN_DOCUMENT_SITE_3300],
				SeverityLevel.ERROR);
		printResults(results);
		
		// T1 entry 1
		// Problem Section/ProblemConcern/ProblemObservation
		//
		// ref
		//   <author>
		//    <!-- Provenance only -->
        //    <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
		//    <time value="202006221100-0500" />
		/* Note how this is inline non-referenced data... */
		//    <assignedAuthor>
		//      ...
		//      <representedOrganization>
        //        <id root="2.16.840.1.113883.19.5" />
        //        <name>Neighborhood Physicians Practice</name> <!-- *** requires name *** -->
        //      </representedOrganization>
        //    </assignedAuthor>
        //   </author>
		//
		// sub
        /*
	      <!-- prob sec / prob conc / Problem Observation author with a link (no inline data) -->
	      <author>
	        <templateId root="2.16.840.1.113883.10.20.22.4.119" />
	        <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
	        <!-- !FIXED! Invalid time on the original file does not match sub -dbTest -->
	        <!-- <time value="20201130175429-0600" /> -->
	        <!-- this time WOULD pass as matches sub -->
	        <time value="20200622" />          
	        <assignedAuthor>
	          <!--  This id's root and extension are used to match the referenced data elsewhere in the document -dbTest 
	          A match DOES exist and is correct and is required since the name is not here inline -->
	          <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
	        </assignedAuthor>
	      </author>
        
        ...
        
        linked reference:
        N/A: Missing/doesn't exist - submitted authorsWithLinkedReferenceData is null or empty 
        
        */
		//  Comparison: 
		//  Base level time DOES match, nothing matches in linked references because they don't exist at all.
		
		//  These errors are partial messages...		
		expectError("The scenario requires Problem Section/ProblemConcern/ProblemObservation Provenance", results); // x1
		expectError("The scenario requires Allergy Section/AllergyConcern/AllergyObservation Provenance", results); // x2
		expectError("The scenario requires Medications Section/MedicationActivity Provenance", results); // x3
		expectError("The scenario requires Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Provenance", results); //x10
		
		// TODO: consider adding these tests for reference linking:
		// cures_ProblemSecProbConcProbObs_AuthorLinksButNoMatchAsNoMatchingRootSite3300Test	
		// cures_ProblemSecProbConcProbObs_AuthorLinksButNoMatchAsNoMatchingExtensionSite3300Test
		// cures_ProblemSecProbConcProbObs_AuthorLinksButNoMatchAsNoMatchingRootOrExtensionSite3300Test
		// missing root, missing extension, missing both
		// nullFlavor in element with root and nullFlavor instead of ext, ext and nullFlavor instead of root, nullFlavor only
	}	
	
	// valid linked reference, expect pass
	@Test
	public void cures_NoteActivity_ExternallyLinkedReference_Site3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_REBECCA621_ONE_NOTE_ACT_VALID_LINK_3300],
				SeverityLevel.ERROR);
		printResults(results);
				
		// sub
        /*
          <!-- -dbTest-SITE-3300 caused invalid error before linked reference implementation - reference is valid -->
          <entry>
            <act classCode="ACT" moodCode="EVN">
              <!-- Note Activity -->
              <templateId root="2.16.840.1.113883.10.20.22.4.202" extension="2016-11-01" />
              <code code="34109-9" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Note">
                <translation code="11506-3" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Progress note" />
              </code>
              <text>
                <reference value="#Note38" />
              </text>
              <statusCode code="completed" />
              <effectiveTime value="20150622000000-0500" />
              <author>
                <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                <time value="201506221100-0500" />
                <assignedAuthor>
                  <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                </assignedAuthor>
              </author>
            </act>
          </entry>
        
        ...
        
        linked reference:
                  <!--  Allergy Observation 1  -dbTest -->
                  <author>
                    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                    <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                    <time value="20200622" />
                    <assignedAuthor>
                      <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                      <id extension="10" root="1.2.840.114350.1.13.51786.1.7.2.836982" />
                      <id root="2.16.840.1.113883.4.6" nullFlavor="UNK" />
                      <addr use="WP">
                        <streetAddressLine>1002 Healthcare Dr</streetAddressLine>
                        <city>Portland</city>
                        <state>OR</state>
                        <postalCode>97266</postalCode>
                      </addr>
                      <telecom use="WP" value="tel:+1-555-555-1002" />
                      <assignedPerson>
                        <name use="L">
                          <given>Henry</given>
                          <family>Seven</family>
                        </name>
                      </assignedPerson>
                      <representedOrganization>
                        <id extension="50" root="1.2.840.114350.1.13.51786.1.7.2.688879" />
                        <id root="2.16.840.1.113883.4.2" nullFlavor="UNK" />
                        <name>Neighborhood Physicians Practice</name>
                        <addr use="WP">
                          <streetAddressLine>1979 MILKY WAY</streetAddressLine>
                          <city>Portland</city>
                          <state>OR</state>
                          <postalCode>97005</postalCode>
                          <country>USA</country>
                        </addr>
                      </representedOrganization>
                    </assignedAuthor>
                  </author>       
        
        */
		//  Comparison: 
		//  Note Activity requires a matching author/assignedAuthor/representedOrganization/name to meet provenance requirements
		//  This example has the name in a linked reference. 		
		expectNoError("The scenario requires Provenance Org Name as part of", results);
	}
	
	// inline, expect pass
	@Test
	public void cures_NoteActivity_TestInlineAuthorRepOrg_Site3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_REBECCA621_ONE_NOTE_ACT_VALID_INLINE_3300],
				SeverityLevel.ERROR);
		printResults(results);
				
		// sub
        /*   
          <!-- -dbTest-SITE-3300 inline assignedAuthore is valid -->
          <entry>
            <act classCode="ACT" moodCode="EVN">
              <!-- Note Activity -->
              <templateId root="2.16.840.1.113883.10.20.22.4.202" extension="2016-11-01" />
              <code code="34109-9" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Note">
                <translation code="11506-3" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Progress note" />
              </code>
              <text>
                <reference value="#Note38" />
              </text>
              <statusCode code="completed" />
              <effectiveTime value="20150622000000-0500" />
              <author>
                <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                <time value="201506221100-0500" />
                  <!--  valid inline author -dbTest -->
                  <assignedAuthor>
                    <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                    <id extension="10" root="1.2.840.114350.1.13.51786.1.7.2.836982" />
                    <id root="2.16.840.1.113883.4.6" nullFlavor="UNK" />
                    <addr use="WP">
                      <streetAddressLine>1002 Healthcare Dr</streetAddressLine>
                      <city>Portland</city>
                      <state>OR</state>
                      <postalCode>97266</postalCode>
                    </addr>
                    <telecom use="WP" value="tel:+1-555-555-1002" />
                    <assignedPerson>
                      <name use="L">
                        <given>Henry</given>
                        <family>Seven</family>
                      </name>
                    </assignedPerson>
                    <representedOrganization>
                      <id extension="50" root="1.2.840.114350.1.13.51786.1.7.2.688879" />
                      <id root="2.16.840.1.113883.4.2" nullFlavor="UNK" />
                      <name>Neighborhood Physicians Practice</name>
                      <addr use="WP">
                        <streetAddressLine>1979 MILKY WAY</streetAddressLine>
                        <city>Portland</city>
                        <state>OR</state>
                        <postalCode>97005</postalCode>
                        <country>USA</country>
                      </addr>
                    </representedOrganization>
                  </assignedAuthor>
              </author>
            </act>
          </entry>        
        */
		//  Comparison: 
		//  Note Activity requires a matching author/assignedAuthor/representedOrganization/name to meet provenance requirements
		//  This example has valid inline data which matches the name required
		expectNoError("The scenario requires Provenance Org Name as part of", results);
	}
	
	// bad extension and root, expect fail as no match
	@Test
	public void cures_NoteActivity_ExternallyLinkedButBadExtAndRoot_Site3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_REBECCA621_ONE_NOTE_ACT_BAD_LINK_BAD_EXT_AND_ROOT_3300],
				SeverityLevel.ERROR);
		printResults(results);
				
		// sub
        /*
          <!-- -dbTest-SITE-3300 invalid reference due to bad link (mistmatched root and ext) -->
          <entry>
            <act classCode="ACT" moodCode="EVN">
              <!-- Note Activity -->
              <templateId root="2.16.840.1.113883.10.20.22.4.202" extension="2016-11-01" />
              <code code="34109-9" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Note">
                <translation code="11506-3" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Progress note" />
              </code>
              <text>
                <reference value="#Note38" />
              </text>
              <statusCode code="completed" />
              <effectiveTime value="20150622000000-0500" />
              <author>
                <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                <time value="201506221100-0500" />
                <assignedAuthor>
                  <!--  invalid linked reference -dbTest -->
                  <id extension="666" root="1.2.3.4.5.6.7.8.9" />
                </assignedAuthor>
              </author>
            </act>
          </entry>
        
        ...
        
        linked reference with the proper id that we are not referencing and thus failing:
                  <!--  Allergy Observation 1  -dbTest -->
                  <author>
                    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                    <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                    <time value="20200622" />
                    <assignedAuthor>
                      <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                      <id extension="10" root="1.2.840.114350.1.13.51786.1.7.2.836982" />
                      <id root="2.16.840.1.113883.4.6" nullFlavor="UNK" />
                      <addr use="WP">
                        <streetAddressLine>1002 Healthcare Dr</streetAddressLine>
                        <city>Portland</city>
                        <state>OR</state>
                        <postalCode>97266</postalCode>
                      </addr>
                      <telecom use="WP" value="tel:+1-555-555-1002" />
                      <assignedPerson>
                        <name use="L">
                          <given>Henry</given>
                          <family>Seven</family>
                        </name>
                      </assignedPerson>
                      <representedOrganization>
                        <id extension="50" root="1.2.840.114350.1.13.51786.1.7.2.688879" />
                        <id root="2.16.840.1.113883.4.2" nullFlavor="UNK" />
                        <name>Neighborhood Physicians Practice</name>
                        <addr use="WP">
                          <streetAddressLine>1979 MILKY WAY</streetAddressLine>
                          <city>Portland</city>
                          <state>OR</state>
                          <postalCode>97005</postalCode>
                          <country>USA</country>
                        </addr>
                      </representedOrganization>
                    </assignedAuthor>
                  </author>       
        
        */
		//  Comparison: 
		//  Note Activity requires a matching author/assignedAuthor/representedOrganization/name to meet provenance requirements
		//  This example has a bad root and extension in the id attempting to point to the linked reference. It is only 'bad' because
		//  it doeesn't match with the intended one (or any one) in the document. Therefore, the required data is missing.
		expectError("The scenario requires Provenance Org Name as part of: Author Represented Organization Name "
				+ "for Note Activity Author Entry for Notes Section corresponding to the code 11506-3 data, "
				+ "but submitted file does not contain Provenance Org Name as part of: Author Represented Organization Name for "
				+ "Note Activity Author Entry for Notes Section corresponding to the code 11506-3 which does not match "
				+ "since the linked reference or id link is missing or invalid.", results);
	}
	
	// null/deleted id, expect fail as no match
	@Test
	public void cures_NoteActivity_NoExternalLinkOrInlineAsMissingAssignedAuthorData_Site3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_REBECCA621_ONE_NOTE_ACT_EMPTY_ASSIGNED_AUTHOR_3300],
				SeverityLevel.ERROR);
		printResults(results);
				
		// sub
        /*
          <!-- -dbTest-SITE-3300 invalid reference due to empty assigned author (has no id with reference id and ext) -->
          <entry>
            <act classCode="ACT" moodCode="EVN">
              <!-- Note Activity -->
              <templateId root="2.16.840.1.113883.10.20.22.4.202" extension="2016-11-01" />
              <code code="34109-9" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Note">
                <translation code="11506-3" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Progress note" />
              </code>
              <text>
                <reference value="#Note38" />
              </text>
              <statusCode code="completed" />
              <effectiveTime value="20150622000000-0500" />
              <author>
                <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                <time value="201506221100-0500" />
                <assignedAuthor>
                  <!-- null/ invalid linked reference -dbTest -->
                </assignedAuthor>
              </author>
            </act>
          </entry>
        */
		//  Comparison: 
		//  Note Activity requires a matching author/assignedAuthor/representedOrganization/name to meet provenance requirements
		//  This example has an empty assigned author. It neither has an inline refernce via id/@root and @ext,
		//  nor does it have an inline version of the requirements.
		expectError("The scenario requires Provenance Org Name as part of: Author Represented Organization Name "
				+ "for Note Activity Author Entry for Notes Section corresponding to the code 11506-3 data, "
				+ "but submitted file does not contain Provenance Org Name as part of: Author Represented Organization Name for "
				+ "Note Activity Author Entry for Notes Section corresponding to the code 11506-3 which does not match "
				+ "since the linked reference or id link is missing or invalid.", results);
	}
	
	// linked reference is valid, and linked correctly, but it has a mismatched org name, expect fail
	@Test
	public void cures_NoteActivity_ExternallyLinkedReferenceButMismatchedOrgName_Site3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_REBECCA621_ONE_NOTE_ACT_VALID_LINK_BAD_NAME_3300],
				SeverityLevel.ERROR);
		printResults(results);
				
		// sub
        /*
          <!-- -dbTest-SITE-3300 reference is a valid link by root and ext but has mismatched org name -->
          <entry>
            <act classCode="ACT" moodCode="EVN">
              <!-- Note Activity -->
              <templateId root="2.16.840.1.113883.10.20.22.4.202" extension="2016-11-01" />
              <code code="34109-9" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Note">
                <translation code="11506-3" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Progress note" />
              </code>
              <text>
                <reference value="#Note38" />
              </text>
              <statusCode code="completed" />
              <effectiveTime value="20150622000000-0500" />
              <author>
                <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                <time value="201506221100-0500" />
                <assignedAuthor>
                  <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                </assignedAuthor>
              </author>
            </act>
          </entry>
        
        ...
        
        linked reference:
                  <!--  Allergy Observation 1  -dbTest -->
                  <author>
                    <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                    <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                    <time value="20200622" />
                    <assignedAuthor>
                      <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                      <id extension="10" root="1.2.840.114350.1.13.51786.1.7.2.836982" />
                      <id root="2.16.840.1.113883.4.6" nullFlavor="UNK" />
                      <addr use="WP">
                        <streetAddressLine>1002 Healthcare Dr</streetAddressLine>
                        <city>Portland</city>
                        <state>OR</state>
                        <postalCode>97266</postalCode>
                      </addr>
                      <telecom use="WP" value="tel:+1-555-555-1002" />
                      <assignedPerson>
                        <name use="L">
                          <given>Henry</given>
                          <family>Seven</family>
                        </name>
                      </assignedPerson>
                      <representedOrganization>
                        <id extension="50" root="1.2.840.114350.1.13.51786.1.7.2.688879" />
                        <id root="2.16.840.1.113883.4.2" nullFlavor="UNK" />
                        <name>Dan's Physicians Practice</name>
                        <addr use="WP">
                          <streetAddressLine>1979 MILKY WAY</streetAddressLine>
                          <city>Portland</city>
                          <state>OR</state>
                          <postalCode>97005</postalCode>
                          <country>USA</country>
                        </addr>
                      </representedOrganization>
                    </assignedAuthor>
                  </author>        
        */
		//  Comparison: 
		//  Note Activity requires a matching author/assignedAuthor/representedOrganization/name to meet provenance requirements
		//  This example has a valid linked reference, but the name in the linked reference does not match the requirements of the ref.
		expectError("The scenario requires Provenance Org Name (Neighborhood Physicians Practice) as part of: "
				+ "Author Represented Organization Name for Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11506-3 data, but submitted file contains Provenance Org Name "
				+ "(Dan's Physicians Practice) in the linked reference, which does not match.", results);
	}
	
	// no linked reference at all in sub so it's id root and extension are pointing to nowhere
	@Test
	public void cures_NoteActivity_AttemptedExternallyLinkedReferenceButNoneInDoc_Site3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_REBECCA621_ONE_NOTE_ACT_NO_LINKED_REF_IN_DOC_3300],
				SeverityLevel.ERROR);
		printResults(results);
				
		// sub
        /*
          <!-- -dbTest-SITE-3300 reference is a valid structure, but reference does not exist in document -->
          <entry>
            <act classCode="ACT" moodCode="EVN">
              <!-- Note Activity -->
              <templateId root="2.16.840.1.113883.10.20.22.4.202" extension="2016-11-01" />
              <code code="34109-9" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Note">
                <translation code="11506-3" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Progress note" />
              </code>
              <text>
                <reference value="#Note38" />
              </text>
              <statusCode code="completed" />
              <effectiveTime value="20150622000000-0500" />
              <author>
                <templateId root="2.16.840.1.113883.10.20.22.4.119" />
                <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01" />
                <time value="201506221100-0500" />
                <assignedAuthor>
                  <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                </assignedAuthor>
              </author>
            </act>
          </entry>
        
        ...
        
        linked reference:
                DOES NOT EXIST!
     			
        */
		//  Comparison: 
		//  Note Activity requires a matching author/assignedAuthor/representedOrganization/name to meet provenance requirements
		//  This example has a valid linked id stucture, but the linked reference does not exist in the document. 		
		expectError("The scenario requires Provenance Org Name as part of: Author Represented Organization Name "
				+ "for Note Activity Author Entry for Notes Section corresponding to the code 11506-3 data, "
				+ "but submitted file does not contain Provenance Org Name as part of: Author Represented Organization Name for "
				+ "Note Activity Author Entry for Notes Section corresponding to the code 11506-3 which does not match "
				+ "since the linked reference or id link is missing or invalid.", results);
	}
	
	// inline data is wrong (mimatched org name inline)
	@Test
	public void cures_NoteActivity_InlineAuthorDataMistmatchedName_Site3300Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// ETT GG, ContentVal: "Provenance error for Rebecca patient"
		// https://groups.google.com/g/edge-test-tool/c/GVOQgl1YlCo
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_REBECCA621_ONE_NOTE_ACT_INLINE_AUTH_MISMATCHED_NAME_3300],
				SeverityLevel.ERROR);
		printResults(results);
				
		// sub
        /*
          <!-- -dbTest-SITE-3300 inline data, mismatched name -->
          <entry>
            <act classCode="ACT" moodCode="EVN">
              <!-- Note Activity -->
              <templateId root="2.16.840.1.113883.10.20.22.4.202" extension="2016-11-01" />
              <code code="34109-9" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Note">
                <translation code="11506-3" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Progress note" />
              </code>
              <text>
                <reference value="#Note38" />
              </text>
              <statusCode code="completed" />
              <effectiveTime value="20150622000000-0500" />
                    <assignedAuthor>
                      <id extension="299935226" root="1.2.840.114350.1.13.51786.1.7.1.1133" />
                      <id extension="10" root="1.2.840.114350.1.13.51786.1.7.2.836982" />
                      <id root="2.16.840.1.113883.4.6" nullFlavor="UNK" />
                      <addr use="WP">
                        <streetAddressLine>1002 Healthcare Dr</streetAddressLine>
                        <city>Portland</city>
                        <state>OR</state>
                        <postalCode>97266</postalCode>
                      </addr>
                      <telecom use="WP" value="tel:+1-555-555-1002" />
                      <assignedPerson>
                        <name use="L">
                          <given>Henry</given>
                          <family>Seven</family>
                        </name>
                      </assignedPerson>
                      <representedOrganization>
                        <id extension="50" root="1.2.840.114350.1.13.51786.1.7.2.688879" />
                        <id root="2.16.840.1.113883.4.2" nullFlavor="UNK" />
                        <!-- ERROR: SUPPOSED TO BE: Neighborhood Physicians Practice< -dbTest-->
                        <name>Dan's Physicians Practice</name>
                        <addr use="WP">
                          <streetAddressLine>1979 MILKY WAY</streetAddressLine>
                          <city>Portland</city>
                          <state>OR</state>
                          <postalCode>97005</postalCode>
                          <country>USA</country>
                        </addr>
                      </representedOrganization>
                    </assignedAuthor>
            </act>
          </entry>
        
        ...
        
        linked reference:
                DOES NOT EXIST (but not needed since data is inline)
     			
        */
		//  Comparison: 
		//  Note Activity requires a matching author/assignedAuthor/representedOrganization/name to meet provenance requirements
		//  This example has a valid inline author structure, but the name does not match the ref 		
		expectError("The scenario requires Provenance Org Name of: (Neighborhood Physicians Practice) for: "
				+ "Author Represented Organization Name for Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11506-3, but submitted file contains Provenance Org Name of: (Dan's Physicians Practice) "
				+ "which does not match the inline author data.", results);
	}
	
	@Test
	public void cures_NoteActivity_ValidProvenanceTS_InvalidAuthorParticipationTS_Repro_Site3331Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
				
		// ETT GG, ContentVal: "Validator provenance timestamp errors when multiple author nodes present"
		// https://groups.google.com/g/edge-test-tool/c/SHhXehxABZg/m/ZtdajeaQAgAJ
		// https://oncprojectracking.healthit.gov/support/browse/SITE-3331
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_VALID_PROVENANCE_TS_INVALID_AUTHOR_PARTICIPATION_TS_SITE_3331],
				SeverityLevel.ERROR);
		printResults(results);
				
		// sub
        /* 
        Error 1 in Procedures/Note Activity:
        
                    <entry>
                        <act classCode="ACT" moodCode="EVN">
							<!-- Note Activity -->
                            <templateId root="2.16.840.1.113883.10.20.22.4.202"
                                extension="2016-11-01"/>
                            <code code="34109-9" displayName="Note"
                                codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
								<!-- relevant identifying code 28570-0 in error -->
                                <translation code="28570-0" displayName="Procedure note"
                                    codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
                            </code>
                            <text>
                                <reference value="#clinNotes_Procedures_0"/>
                            </text>
                            <statusCode code="completed"/>
                            <effectiveTime value="20210125"/>
                            <author>
								<!-- Author Participation (original) = 4.119 -->
                                <templateId root="2.16.840.1.113883.10.20.22.4.119"/>
								<!-- -dbTest here is the location of the time which errors for provenance
											 but it is actually the original Author Participation (see II, 4.119, NOT 5.6  -->                                
                                <time value="20210125145611"/> <!-- this time is incorrectly reported as a 'provenance' error -->

        */
		//  Validation: 
		//  Although the TS is invalid as per provenenace reqs (has no time-zone) for Author Participation, 
		//  we do not expect an error for Author Participation time as it has looser regEx TS-only reqs, and, 
		//  most importantly, it is not provenance. 
		//  They are different templates with different IIs.
		//  We are only enforcing TS validation for provenance within the content validator.
		//  Non-provenance authors will be validated in the future by MDHT as per the schema regEx for TS.
		//  Those regEx requirements are less strict than what we are applying for provenance.
		//  There may be a duplication for provenance as well in MDHT.
		expectNoError("The submitted Provenance (Time: Value) 20210125145611 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 28570-0 is invalid", results);
		
		/*
		Error 2 in Progress Note/Note Activity
		
                    <entry>
                        <act classCode="ACT" moodCode="EVN">
                        	<!--  Note Activity -->
                            <templateId root="2.16.840.1.113883.10.20.22.4.202"
                                extension="2016-11-01"/>
                            <code code="34109-9" displayName="Note"
                                codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC">
                                <translation code="11506-3" displayName="Progress note"
                                    codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"/>
                            </code>
                            <text>
                                <reference value="#clinNotes_ProgressNoteNoteClinicalNotes_0"/>
                            </text>
                            <statusCode code="completed"/>
                            <effectiveTime value="20210920"/>
                            <author>
                                <!-- Author Participation -->
                                <templateId root="2.16.840.1.113883.10.20.22.4.119"/>
                                <!-- dbTest 2 
                                 invalid TS as per provenance reqs, but is not provenance, so should not be content validation error
                                 -->
                                <time value="20210920144244"/>		
		 
		*/
		// Validation:
		// The same bug is happening in Progress Note/Note Activity/Author Participation.
		// Although it is invalid, it is not provenance and therefore should not produce the error within the content validator.
		// Has no time-zone
		expectNoError("The submitted Provenance (Time: Value) 20210920144244 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11506-3 is invalid", results);		
	}
	
	@Test
	@Ignore // ignore until we have fixed the index bug as provenance is the 2nd index, not 1st, and cannot fail yet
	public void cures_NoteActivity_InvalidProvenanceTS_ValidAuthorParticipationTS_Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());				
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_INVALID_PROVENANCE_TS_VALID_AUTHOR_PARTICIPATION_TS_SITE_3331],
				SeverityLevel.ERROR);
		printResults(results);
				
		// sub
        /* 
        Error 1 in Procedures/Note Activity:
        
                            <author>
								<!-- Provenance Provenance - Author Participation = 5.6 -->
                                <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01"/>
								<!-- this time should now show up as a valid provenance error as is provenance and has no time-zone -->
                                <time value="20210125145611"/>

        */
		expectError("The submitted Provenance (Time: Value) 20210125145611 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 28570-0 is invalid", results);
		
		/*
		Error 2 in Progress Note/Note Activity
		
                            <author>
                                <!--  Provenance -->
                                <templateId root="2.16.840.1.113883.10.20.22.5.6"
                                    extension="2019-10-01"/>
                                <!-- Invalid time and should produce error (no time-zone) -->
                                <time value="20210920144244"/>
		 
		*/
		expectError("The submitted Provenance (Time: Value) 20210920144244 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11506-3 is invalid", results);		
	}
	
	@Test
	public void cures_NoteActivity_InvalidProvenanceTS_ValidAuthorParticipationTS_SwapAuthorIndexes_Site3331Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
				
		// ETT GG, ContentVal: "Validator provenance timestamp errors when multiple author nodes present"
		// https://groups.google.com/g/edge-test-tool/c/SHhXehxABZg/m/ZtdajeaQAgAJ
		// https://oncprojectracking.healthit.gov/support/browse/SITE-3331
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_INVALID_PROVENANCE_TS_VALID_AUTHOR_PARTICIPATION_TS_SWAP_AUTHORS_SITE_3331],
				SeverityLevel.ERROR);
		printResults(results);
		
		// This test is the same as cures_NoteActivity_InvalidProvenanceTS_ValidAuthorParticipationTS_Site3331Test except that
		// the indexes of the authors are swapped. The reason for this is that there is a bug where only the 1st author index will fail.
		// Until we resolve that bug, we need to swap the provenance with the error to the first occurring author, so that it can fail for it's
		// TS, and prove proper identification.
		// After the index bug is fixed, this will remain as a regression test.
				
		// sub
        /* 
        Error 1 in Procedures/Note Activity:
		Same as cures_NoteActivity_InvalidProvenanceTS_ValidAuthorParticipationTS_Site3331Test but now provenance is 1st author vs 2nd

        */
		expectError("The submitted Provenance (Time: Value) 20210125145611 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 28570-0 is invalid", results);
		
		/*
		Error 2 in Progress Note/Note Activity
		Same as cures_NoteActivity_InvalidProvenanceTS_ValidAuthorParticipationTS_Site3331Test but now provenance is 1st author vs 2nd
		 
		*/
		expectError("The submitted Provenance (Time: Value) 20210920144244 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11506-3 is invalid", results);		
	}
	
	@Test
	@Ignore // ignore until we have fixed the index bug as provenance is the 2nd index, not 1st, and cannot fail yet
	public void cures_NoteActivity_InvalidProvenanceTS_InvalidAuthorParticipationTS_Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());				
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_INVALID_PROVENANCE_TS_INVALID_AUTHOR_PARTICIPATION_TS_SITE_3331],
				SeverityLevel.ERROR);
		printResults(results);
		
		// sub
		/*
		Non-Error 1 in Procedures/Note Activity:
 		AuthorParticipation TS being invalid is irrelevant/not supposed to be enforced so not fired
                            <author>
								<!-- Author Participation (original) = 4.119 -->
                                <templateId root="2.16.840.1.113883.10.20.22.4.119"/>
                                <time value="20660125145611"/> 					 
		 */		
		expectNoError("The submitted Provenance (Time: Value) 20660125145611 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 28570-0 is invalid", results);
				
		// sub
        /* 
        Error 1 in Procedures/Note Activity:
        
                            <author>
								<!-- Provenance Provenance - Author Participation = 5.6 -->
                                <templateId root="2.16.840.1.113883.10.20.22.5.6"
                                    extension="2019-10-01"/>
								<!-- this time should now show up as a valid provenance error as is provenance and has no time-zone -->
                                <time value="20210125145611"/>

        */
		expectError("The submitted Provenance (Time: Value) 20210125145611 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 28570-0 is invalid", results);
		
		
		/*
		Non-Error 2 in Progress Note/Note Activity
		
                            <author>
                                <!-- Author Participation -->
                                <templateId root="2.16.840.1.113883.10.20.22.4.119"/>
                                <!-- dbTest 2 
                                 INvalid TS as per provenance reqs, but is not provenance, so irrelevant, no error expected
                                 -->
                                <time value="20660920144244"/>
		 
		*/
		expectNoError("The submitted Provenance (Time: Value) 20660920144244 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11506-3 is invalid", results);		
		
		/*
		Error 2 in Progress Note/Note Activity
		
                            <author>
                                <!--  Provenance -->
                                <templateId root="2.16.840.1.113883.10.20.22.5.6"
                                    extension="2019-10-01"/>
                                <!-- Invalid time and should produce error (no time-zone) -->
                                <time value="20210920144244"/>
		 
		*/
		expectError("The submitted Provenance (Time: Value) 20210920144244 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11506-3 is invalid", results);
	}
	
	@Test
	public void cures_NoteActivity_ValidProvenanceTS_ValidAuthorParticipationTS_EpectPass_Site3331Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
				
		// ETT GG, ContentVal: "Validator provenance timestamp errors when multiple author nodes present"
		// https://groups.google.com/g/edge-test-tool/c/SHhXehxABZg/m/ZtdajeaQAgAJ
		// https://oncprojectracking.healthit.gov/support/browse/SITE-3331
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_VALID_PROVENANCE_TS_VALID_AUTHOR_PARTICIPATION_TS_SITE_3331],
				SeverityLevel.ERROR);
		printResults(results);
				
		// All times are valid, no provenance issues expected at all
		// All 4 instances should pass. 
		// The 2 Author Participation authors would never fail either way. 
		// The 2 Provenance would if fail invalid, but are valid.
		// Search dbTest to find the instances
		expectNoError("The submitted Provenance (Time: Value) ", results);
	}
	
	@Test
	public void cures_NoteActivity_InvalidProvenanceTS_InvalidAuthorParticipationTS_SwapAuthorIndexes_Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());				
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF_V2,
				SUBMITTED_CCDA[SUB_INVALID_PROVENANCE_TS_INVALID_AUTHOR_PARTICIPATION_TS_SWAP_AUTHORS_SITE_3331],
				SeverityLevel.ERROR);
		printResults(results);
		
		// sub
        /* 
        Error 1 in Procedures/Note Activity:
        
                            <author>
								<!-- Provenance Provenance - Author Participation = 5.6 -->
                                <templateId root="2.16.840.1.113883.10.20.22.5.6"
                                    extension="2019-10-01"/>
								<!-- this time should now show up as a valid provenance error as is provenance and has no time-zone -->
                                <time value="20210125145611"/>

        */
		expectError("The submitted Provenance (Time: Value) 20210125145611 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 28570-0 is invalid", results);		
		
		// sub
		/*
		Non-Error 1 in Procedures/Note Activity:
 		AuthorParticipation TS being invalid is irrelevant/not supposed to be enforced so not fired
                            <author>
								<!-- Author Participation (original) = 4.119 -->
                                <templateId root="2.16.840.1.113883.10.20.22.4.119"/>
                                <time value="20660125145611"/> 					 
		 */		
		expectNoError("The submitted Provenance (Time: Value) 20660125145611 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 28570-0 is invalid", results);

		/*
		Error 2 in Progress Note/Note Activity
		
                            <author>
                                <!--  Provenance -->
                                <templateId root="2.16.840.1.113883.10.20.22.5.6"
                                    extension="2019-10-01"/>
                                <!-- Invalid time and should produce error (no time-zone) -->
                                <time value="20210920144244"/>
		 
		*/
		expectError("The submitted Provenance (Time: Value) 20210920144244 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11506-3 is invalid", results);		
		
		/*
		Non-Error 2 in Progress Note/Note Activity
		
                            <author>
                                <!-- Author Participation -->
                                <templateId root="2.16.840.1.113883.10.20.22.4.119"/>
                                <!-- dbTest 2 
                                 INvalid TS as per provenance reqs, but is not provenance, so irrelevant, no error expected
                                 -->
                                <time value="20660920144244"/>
		 
		*/
		expectNoError("The submitted Provenance (Time: Value) 20660920144244 at Note Activity Author Entry for Notes Section "
				+ "corresponding to the code 11506-3 is invalid", results);		
	}
	
	@Test
	public void cures_ProblemSection_Conc_Obs_ValidProvenanceTSInSub_NoProvInRef_RefIncorrectlyThinksAuthorParIsProvAndSaysNoMatch_Repro_Site3354Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		// ETT GG Analyze Provenance TS identification in Problem Section in "Happy Kid" sample patient
		// https://groups.google.com/g/edge-test-tool/c/SHhXehxABZg
		// https://oncprojectracking.healthit.gov/support/browse/SITE-3354
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY_V5,
				SUBMITTED_CCDA[SUB_3354_REPRO_PROBLEMSEC_CONC_OBS_PROVENANCE],
				SeverityLevel.ERROR);
		printResults(results);
				 
		// Problem Section/ProblemConcern/ProblemObservation/author/time
		//
		// ref
		//    1st index:
		//		<author>
		//			<!-- Author Participation (original) = 4.119 -->
		//			<templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//			<time value="202006221100-0500" />
		//	   2nd index:
		//       N/A no 2nd author exists in the ref
		//
		// sub
		//    1st index:
		//		<author>
		//			<!-- Author Participation (original) = 4.119 -->
		//			<templateId root="2.16.840.1.113883.10.20.22.4.119" />
		//			<time value="20180601" /> <!-- changed to a different time than in scenario, but should not matter, as not provenance -dbTest -->
		//	   2nd index:
		//		<!-- Add provenance author here to reproduce, even though it's not required here -dbTest -->
		//		 <author>
		//		 	<!-- Provenance -->
		//           <templateId root="2.16.840.1.113883.10.20.22.5.6" extension="2019-10-01"/>
		//           <time value="202006221100-0500"/>
		// 
		//  Comparison:
		//	Ref has no provenance, but instead, has author participation. 
		//  Sub has the same author participation, but with a different time.
		//  Sub has added a 2nd author which is of type provenance.
		//  Any of the differences above should be irrelevant as far as triggering a provenance error, 
		//  as the ref does not have provenance at all.
		//
		//  Issue in logic:
		//  The ref doesn't require/have any provenance at the location, so there shouldn't be an error of a mismatch,
		//  it (the ref) only has author participation
		//  Adding a provenance as a 2nd author to the sub does not cause an error, 
		//  unless, the author participation at 1st index has a different time
		//  then, for w/e rason, it enforces that the author participation in the sub is also provenance, 
		//  and must match the refs author participation time (but calls it provenance)
		expectNoError("The scenario requires Problem Section/ProblemConcern/ProblemObservation Provenance data of time "
				+ "and/or representedOrganization/name which was not found in the submitted data. "
				+ "The scenario time value is 202006221100-0500 and a submitted time value should at a minimum match the "
				+ "8-digit date portion of the data. The scenario representedOrganization/name value is "
				+ "Neighborhood Physicians Practice and a submitted name should match. "
				+ "One or all of the prior issues exist and must be resolved.", results);				
	}
	
	@Test
	public void cures_isAuthorOfTypeProvenance_NPE_ReproAndExpectFixed_Site3362Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		// ETT GG
		// https://groups.google.com/g/edge-test-tool/c/iFbsKCqxQ3s/m/vf2QHkQTEQAJ
		// SITE-3362 ETT GG "Validator - Cures Update - b1 ToC" NPE @ contentvalidator.model.CCDAAuthor.lambda$isAuthorOfTypeProvenance
				
		// Note: Ref file names MUST be FULLY unique otherwise the test won't work properly. Simply adding _v2 does not cause that file to be selected. 
		// Instead, the original file w/o v_x will be selected.
		// In this case, we intended to use 170.315_b1_toc_amb_sample2_v2.xml but could not as that would select 170.315_b1_toc_amb_sample2.xml, 
		// so we renamed it to  toc_amb_s2_v2.xml
		// TODO: There are probably other cases where this is not working as expected and we need more unique names for refs.
		// We should either update those in the tests, or consider changing the way the file selection works to consider then entire filename vs just a match within it.
		// It's pretty likely that any tests involving 170.315_b1_toc_amb_sample3_v5.xml or 170.315_b1_toc_amb_sample1_v2.xml are not working as expected
		// and instead selecting the non-versioned versions.
		// However, sample3_v5 might be choosing the right file, because both version of the filenames have a version so that makes it more unique.
		// There is no unversioned one at this time. So, another solution may be to have everything have a version even if some are v1, it must be explicit.
		// What we should really be doing though, is keeping the refs up to date, and always have the latest version only.
		// And, we should update old tests, and make sure they still work with the latest versions.
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE2_V2_JEREMY,
				SUBMITTED_CCDA[SUB_3362_IS_AUTHOR_OF_TYPE_PROVENANCE_NPE], SeverityLevel.ERROR);
		printResults(results);
	}
	
	@Test
	public void cures_externallyLinkedReference_mimatchedNameInLink_Site3350Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// SITE-3350 ETT GG, ContentVal: Note Activity linked reference consider update to messages linked vs not
		// https://groups.google.com/u/1/g/edge-test-tool/c/-kjLTnBuB00
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE2_V2_JEREMY,
				SUBMITTED_CCDA[SUB_3350_JEREMY_PROVENANCE_LINKED_REFERENCE], SeverityLevel.ERROR);
		printResults(results);
				
		// Comparison: 
		// linked reference in the sub is invalid due to incorrect/mismatched representedOrganization
		// (Community Health Hospitals vs the expected Neighborhood Physicians Practice) 
		// in linked reference, expect provenance failure		
		expectError("The scenario requires Provenance Org Name (Neighborhood Physicians Practice) as part of: "
		+ "Author Represented Organization Name for Note Activity Author Entry for Notes Section "
		+ "corresponding to the code 34117-2 data, but submitted file contains Provenance Org Name "
		+ "(Community Health Hospitals) in the linked reference, which does not match.", results);
		
		// Note: The old, less useful message before this fix in SITE-3350 was:
		// The scenario requires Provenance Org Name as part of: Author Represented Organization Name for 
		// Note Activity Author Entry for Notes Section corresponding to the code 34117-2 data, but submitted file does not 
		// contain Provenance Org Name as part of: Author Represented Organization Name for Note Activity Author Entry for 
		// Notes Section corresponding to the code 34117-2 which does not match in the linked reference.
	}
	
}
