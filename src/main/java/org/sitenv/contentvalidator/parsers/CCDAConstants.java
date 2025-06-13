package org.sitenv.contentvalidator.parsers;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.util.Iterator;

public class CCDAConstants {

    private final static CCDAConstants constants = new CCDAConstants();

	static public XPath CCDAXPATH;
	static public XPathExpression DOC_TEMPLATE_EXP;
	static public XPathExpression DOC_TYPE_EXP;
	static public XPathExpression PATIENT_ROLE_EXP;
	static public XPathExpression DOC_PARTICIPANT_EXP;
	static public XPathExpression REL_ASSOCIATED_CODE_EXP;
	static public XPathExpression REL_ASSOCIATED_PERSON_NAME_EXP;
	static public XPathExpression REL_ADDR_EXP;
	static public XPathExpression REL_STREET_ADDR1_EXP;
	static public XPathExpression REL_STREET_ADDR2_EXP;
	static public XPathExpression REL_CITY_EXP;
	static public XPathExpression REL_STATE_EXP;
	static public XPathExpression REL_POSTAL_EXP;
	static public XPathExpression REL_COUNTRY_EXP;
	static public XPathExpression REL_PATIENT_BIRTHPLACE_EXP;
	static public XPathExpression REL_PATIENT_NAME_EXP;
	static public XPathExpression REL_PATIENT_PREV_NAME_EXP;
	static public XPathExpression REL_PLAY_ENTITY_NAME_EXP;
	static public XPathExpression REL_PLAY_ENTITY_EXP;
	static public XPathExpression REL_GIVEN_NAME_EXP;
	static public XPathExpression REL_MIDDLE_NAME_EXP;
	static public XPathExpression REL_FAMILY_NAME_EXP;
	static public XPathExpression REL_GIVEN_PREV_NAME_EXP;
	static public XPathExpression REL_SUFFIX_EXP;
	static public XPathExpression REL_PATIENT_ADMINGEN_EXP;
	static public XPathExpression REL_PATIENT_BIRTHTIME_EXP;
	static public XPathExpression REL_PATIENT_MARITAL_EXP;
	static public XPathExpression REL_PATIENT_DEATHDATE_EXP;
	static public XPathExpression REL_PATIENT_RELIGION_EXP;
	static public XPathExpression REL_PATIENT_RACE_EXP;
	static public XPathExpression REL_PATIENT_ETHNICITY_EXP;
	static public XPathExpression REL_PATIENT_LANGUAGE_EXP;
	static public XPathExpression REL_TELECOM_EXP;
	static public XPathExpression REL_LANG_CODE_EXP;
	static public XPathExpression REL_LANG_MODE_EXP;
	static public XPathExpression REL_LANG_PREF_EXP;
	static public XPathExpression REL_TEXT_EXP;

	static public XPathExpression REL_TEMPLATE_ID_EXP;
	static public XPathExpression REL_CODE_EXP;
	static public XPathExpression REL_CODE_WITH_TRANS_EXP;
	static public XPathExpression REL_CODE_TRANS_EXP;
	static public XPathExpression REL_TRANS_EXP;
	static public XPathExpression REL_VAL_EXP;
	static public XPathExpression REL_VAL_WITH_NF_EXP;
	static public XPathExpression REL_VAL__WITH_TRANS_EXP;
	static public XPathExpression REL_STATUS_CODE_EXP;
	static public XPathExpression REL_INT_CODE_EXP;
	static public XPathExpression REL_REF_RANGE_EXP;

	static public XPathExpression REL_EFF_TIME_EXP;
	static public XPathExpression REL_EFF_TIME_LOW_EXP;
	static public XPathExpression REL_EFF_TIME_HIGH_EXP;
	static public XPathExpression REL_LOW_EXP;
	static public XPathExpression REL_HIGH_EXP;
	static public XPathExpression REL_TIME_EXP;
	static public XPathExpression REL_SDTC_TIME_EXP;

	//Encounter stuff
	static public XPathExpression REL_ENC_ENTRY_EXP;
	static public XPathExpression ENCOUNTER_EXPRESSION;
	static public XPathExpression ADMISSION_DIAG_EXP;
	static public XPathExpression DISCHARGE_DIAG_EXP;
	static public XPathExpression REL_HOSPITAL_ADMISSION_DIAG_EXP;
	static public XPathExpression REL_HOSPITAL_DISCHARGE_DIAG_EXP;
	static public XPathExpression SDTC_REL_DISCHARGE_DISPOSITION_EXP;

	// Notes and Companion Guide templates
	static public XPathExpression NOTES_EXPRESSION;
	static public XPathExpression REL_NOTES_ACTIVITY_EXPRESSION;
	static public XPathExpression REL_ENTRY_REL_NOTES_ACTIVITY_EXPRESSION;
	static public XPathExpression NOTES_ACTIVITY_EXPRESSION;
	static public XPathExpression REL_COMPONENT_ACTIVITY_EXPRESSION;

	//Problem Stuff
	static public XPathExpression PROBLEM_EXPRESSION;
	static public XPathExpression REL_PROBLEM_OBS_EXPRESSION;
	static public XPathExpression PAST_ILLNESS_EXP;
	static public XPathExpression PAST_ILLNESS_PROBLEM_OBS_EXPRESSION;
	static public XPathExpression REL_DIAGNOSIS_DATE_ACT_EXP;

	//Medication stuff
	static public XPathExpression MEDICATION_EXPRESSION;
	static public XPathExpression ADM_MEDICATION_EXPRESSION;
	static public XPathExpression DM_MEDICATION_EXPRESSION;
	static public XPathExpression REL_MED_ENTRY_EXP;
	static public XPathExpression DM_ENTRY_EXP;
	static public XPathExpression DM_MED_ACT_EXP;
	static public XPathExpression REL_ROUTE_CODE_EXP;
	static public XPathExpression REL_DOSE_EXP;
	static public XPathExpression REL_RATE_EXP;
	static public XPathExpression REL_APP_SITE_CODE_EXP;
	static public XPathExpression REL_ADMIN_UNIT_CODE_EXP;
	static public XPathExpression REL_CONSUM_EXP;
	static public XPathExpression REL_MMAT_CODE_EXP;
	static public XPathExpression REL_MMAT_CODE_TRANS_EXP;
	static public XPathExpression REL_MANU_ORG_NAME_EXP;
	static public XPathExpression REL_MMAT_LOT_EXP;

	// Labs
	public static XPathExpression RESULTS_EXPRESSION;
    public static XPathExpression REL_LAB_RESULT_ORG_EXPRESSION;
    public static XPathExpression REL_LAB_TEST_ORG_EXPRESSION;
    public static XPathExpression REL_COMP_OBS_EXP;
    public static XPathExpression REL_SPECIMEN_EXP;
    public static XPathExpression REL_SPECIMEN_CODE_EXP;
    public static XPathExpression IMMUNIZATION_EXPRESSION;
    public static XPathExpression VITALSIGNS_EXPRESSION;
    public static XPathExpression REL_VITAL_ORG_EXPRESSION;

    // Procedure and Med Equipments
    public static XPathExpression MEDICAL_EQUIPMENT_EXPRESSION;
    public static XPathExpression MEDICAL_EQUIPMENT_ORG_EXPRESSION;
    public static XPathExpression MEDICAL_EQUIPMENT_ORG_PAP_EXPRESSION;
    public static XPathExpression PROCEDURE_EXPRESSION;
    public static XPathExpression REL_PROCEDURE_UDI_EXPRESSION;
    public static XPathExpression REL_PROCEDURE_SDL_EXPRESSION;
    public static XPathExpression REL_PROC_ACT_PROC_EXP;
    public static XPathExpression REL_PROC_ACT_ACT_EXP;
    public static XPathExpression REL_TARGET_SITE_CODE_EXP;
    public static XPathExpression REL_PERF_ENTITY_EXP;
    public static XPathExpression REL_PERF_ENTITY_ORG_EXP;
    public static XPathExpression REL_REP_ORG_EXP;
    public static XPathExpression REL_NAME_EXP;
    public static XPathExpression REL_ID_EXP;
    public static XPathExpression REL_PLAYING_DEV_CODE_EXP;
    public static XPathExpression REL_SCOPING_ENTITY_ID_EXP;

	// Allergies
	static public XPathExpression ALLERGIES_EXPRESSION;
	static public XPathExpression REL_ALLERGY_REACTION_EXPRESSION;
	static public XPathExpression REL_ALLERGY_SEVERITY_EXPRESSION;

	// Coverage and Payers
	static public XPathExpression PAYERS_EXPRESSION;

	// Goals and Health Concerns and Plan of Treatment
	static public XPathExpression GOALS_EXPRESSION;
	static public XPathExpression HEALTH_CONCERNS_EXPRESSION;
	static public XPathExpression PLAN_OF_TREATMENT_EXPRESSION;
	static public XPathExpression ASSESSMENT_AND_PLAN_EXPRESSION;
	static public XPathExpression REL_GOAL_OBSERVATION_EXP;
	static public XPathExpression REL_HEALTH_CONCERN_ACT_EXP;
	static public XPathExpression REL_PLANNED_PROCEDURE_EXP;
	static public XPathExpression TREATMENT_PREFERENCE_EXPRESSION;
	static public XPathExpression CARE_PREFERENCE_EXPRESSION;

	// Participants and Performers for Care Team / Coverage etc.
	static public XPathExpression CARE_TEAM_EXPRESSION;
	static public XPathExpression CARE_TEAM_SECTION_EXPRESSION;
	static public XPathExpression REL_CARE_TEAM_ORG_EXPRESSION;
	static public XPathExpression REL_CARE_TEAM_MEMBER_ACT_EXPRESSION;
	static public XPathExpression REL_PERFORMER_EXP;
	static public XPathExpression REL_PARTICIPANT_EXP;
	static public XPathExpression REL_PARTICIPANT_EXP_NO_TYPE_CODE;
	static public XPathExpression REL_FUNCTION_CODE;

	// Social history
	static public XPathExpression SOCIAL_HISTORY_EXPRESSION;
	static public XPathExpression FUNCTIONAL_STATUS_EXPRESSION;
	static public XPathExpression MENTAL_STATUS_EXPRESSION;
	static public XPathExpression REASON_FOR_REFERRAL_EXPRESSION;
	static public XPathExpression REL_FUNCTIONAL_STATUS_OBSERVATION_EXPRESSION;
	static public XPathExpression REL_PATIENT_REFERRAL_ACT_EXPRESSION;
	static public XPathExpression REL_MENTAL_STATUS_OBSERVATION_EXPRESSION;
	static public XPathExpression REL_DISABILITY_STATUS_OBSERVATION_EXPRESSION;
	static public XPathExpression REL_INDICATION_EXPRESSION;
	static public XPathExpression REL_MEDICATION_DISPENSE_EXPRESSION;
	static public XPathExpression REL_MEDICATION_FREE_SIG_ENTRY;
	static public XPathExpression REL_MEDICATION_ADHERENCE_ENTRY;
	static public XPathExpression REL_SMOKING_STATUS_EXP;
	static public XPathExpression REL_TOBACCO_USE_EXP;
	static public XPathExpression REL_BIRTHSEX_OBS_EXP;
	static public XPathExpression REL_SEX_ORIENTATION_EXP;
	static public XPathExpression REL_TRIBAL_AFFILIATION_EXP;
	static public XPathExpression REL_PREGNANCY_EXP;
	static public XPathExpression REL_SEX_EXP;
	static public XPathExpression REL_OCCUPATION_EXP;
	static public XPathExpression REL_OCCUPATION_INDUSTRY_EXP;
	static public XPathExpression REL_GENDER_IDENTITY_EXP;
	static public XPathExpression REL_SOCIAL_HISTORY_OBS_EXP;
	static public XPathExpression REL_ASSESSMENT_SCALE_OBS_EXP;
	static public XPathExpression REL_ENTRY_ASSESSMENT_SCALE_OBS_EXP;
  // TODO: These 2 assessment scale entry expressions ahead are duplicates.
  // Look into removing the duplicate reference here and externally if logical
	static public XPathExpression REL_ASSESSMENT_SCALE_ENTRY_OBS_EXP;
	static public XPathExpression REL_ASSESSMENT_SCALE_SUP_OBS_EXP;

	// Care Plan
	static public XPathExpression INTERVENTIONS_SECTION_V3_EXP;
	static public XPathExpression HEALTH_STATUS_EVALUATIONS_AND_OUTCOMES_SECTION_EXP;

	//Generic
	static public XPathExpression REL_ENTRY_RELSHIP_ACT_EXP;
	static public XPathExpression REL_ENTRY_EXP;
	static public XPathExpression REL_SBDM_ENTRY_EXP;
	static public XPathExpression REL_PART_ROLE_EXP;
	static public XPathExpression REL_ENTRY_RELSHIP_OBS_EXP;
	static public XPathExpression REL_ACT_ENTRY_EXP;
	static public XPathExpression REL_PART_PLAY_ENTITY_CODE_EXP;
	static public XPathExpression REL_ASSN_ENTITY_ADDR;
	static public XPathExpression REL_ASSN_ENTITY_PERSON_NAME;
	static public XPathExpression REL_ASSN_ENTITY_TEL_EXP;
	static public XPathExpression AUTHORS_FROM_HEADER_EXP;
	static public XPathExpression AUTHORS_WITH_LINKED_REFERENCE_DATA_EXP;
	static public XPathExpression REL_AUTHOR_EXP;
	static public XPathExpression REL_ASSIGNED_AUTHOR_EXP;
	static public XPathExpression REL_ASSIGNED_PERSON_EXP;
	static public XPathExpression REL_ASSIGNED_ENTITY_EXP;

	static public String RACE_EL_NAME = "raceCode";
	static public String TYPECODE_EL_NAME = "typeCode";

	public static final String DEFAULT_XPATH = "/ClinicalDocument";
	public static final String DEFAULT_LINE_NUMBER = "0";

	public static final String US_REALM_TEMPLATE = "2.16.840.1.113883.10.20.22.1.1";
	public static final String CCDA_2015_AUG_EXT = "2015-08-01";
	public static final String CCD_TEMPLATE = "2.16.840.1.113883.10.20.22.1.2";
	public static final String CCD_CODE = "34133-9";
	public static final String DS_TEMPLATE = "2.16.840.1.113883.10.20.22.1.8";
	public static final String DS_CODE = "18842-5";
	public static final String RN_TEMPLATE = "2.16.840.1.113883.10.20.22.1.14";
	public static final String RN_CODE = "57133-1";
	public static final String CP_TEMPLATE = "2.16.840.1.113883.10.20.22.1.15";
	public static final String CP_CODE = "52521-2";

	public static final String PROVENANCE_TEMPLATE_ID_ROOT = "2.16.840.1.113883.10.20.22.5.6";
	public static final String PROVENANCE_TEMPLATE_ID_EXT = "2019-10-01";


	private CCDAConstants()
	{
		initialize();
	}

	public static CCDAConstants getInstance()
	{
		return constants;
	}

	private void initialize() {

		CCDAXPATH = XPathFactory.newInstance().newXPath();

		try {

			DOC_TEMPLATE_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/templateId[not(@nullFlavor)]");
			DOC_TYPE_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/code[not(@nullFlavor)]");
			PATIENT_ROLE_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/recordTarget/patientRole[not(@nullFlavor)]");
			REL_ADDR_EXP = CCDAConstants.CCDAXPATH.compile("./addr[not(@nullFlavor)]");
			REL_STREET_ADDR1_EXP = CCDAConstants.CCDAXPATH.compile("./streetAddressLine[not(@nullFlavor)]");
			REL_STREET_ADDR2_EXP = CCDAConstants.CCDAXPATH.compile("./streetAddressLine2[not(@nullFlavor)]");
			REL_CITY_EXP = CCDAConstants.CCDAXPATH.compile("./city[not(@nullFlavor)]");
			REL_STATE_EXP = CCDAConstants.CCDAXPATH.compile("./state[not(@nullFlavor)]");
			REL_POSTAL_EXP = CCDAConstants.CCDAXPATH.compile("./postalCode[not(@nullFlavor)]");
			REL_COUNTRY_EXP = CCDAConstants.CCDAXPATH.compile("./country[not(@nullFlavor)]");
			REL_PATIENT_BIRTHPLACE_EXP = CCDAConstants.CCDAXPATH.compile("./patient/birthplace/place/addr[not(@nullFlavor)]");
			REL_PATIENT_NAME_EXP = CCDAConstants.CCDAXPATH.compile("(./patient/name[not(@nullFlavor)])[1]");
			REL_PATIENT_PREV_NAME_EXP = CCDAConstants.CCDAXPATH.compile("(./patient/name[not(@nullFlavor)])[2]");
			REL_PLAY_ENTITY_NAME_EXP = CCDAConstants.CCDAXPATH.compile("./playingEntity/name[not(@nullFlavor)]");
			REL_PLAY_ENTITY_EXP = CCDAConstants.CCDAXPATH.compile("./playingEntity");
			REL_GIVEN_NAME_EXP = CCDAConstants.CCDAXPATH.compile("(./given[not(@nullFlavor)])[1]");
			REL_MIDDLE_NAME_EXP = CCDAConstants.CCDAXPATH.compile("(./given[not(@nullFlavor)])[2]");
			REL_GIVEN_PREV_NAME_EXP = CCDAConstants.CCDAXPATH.compile("(./given[not(@nullFlavor) and @qualifier='BR'])[1]");
			REL_FAMILY_NAME_EXP = CCDAConstants.CCDAXPATH.compile("./family[not(@nullFlavor)]");
			REL_SUFFIX_EXP = CCDAConstants.CCDAXPATH.compile("./suffix[not(@nullFlavor)]");
			REL_PATIENT_ADMINGEN_EXP = CCDAConstants.CCDAXPATH.compile("./patient/administrativeGenderCode[not(@nullFlavor)]");
			REL_PATIENT_BIRTHTIME_EXP = CCDAConstants.CCDAXPATH.compile("./patient/birthTime[not(@nullFlavor)]");
			REL_PATIENT_DEATHDATE_EXP = CCDAConstants.CCDAXPATH.compile("./patient/sdtc:deceasedTime[not(@nullFlavor)]");
			REL_PATIENT_MARITAL_EXP = CCDAConstants.CCDAXPATH.compile("./patient/maritalStatusCode[not(@nullFlavor)]");
			REL_PATIENT_RELIGION_EXP = CCDAConstants.CCDAXPATH.compile("./patient/religiousAffiliationCode[not(@nullFlavor)]");
			REL_PATIENT_RACE_EXP = CCDAConstants.CCDAXPATH.compile("./patient/raceCode[not(@nullFlavor)]");
			REL_PATIENT_ETHNICITY_EXP = CCDAConstants.CCDAXPATH.compile("./patient/ethnicGroupCode[not(@nullFlavor)]");
			REL_PATIENT_LANGUAGE_EXP = CCDAConstants.CCDAXPATH.compile("./patient/languageCommunication[not(@nullFlavor)]");
			REL_LANG_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./languageCode[not(@nullFlavor)]");
			REL_LANG_MODE_EXP = CCDAConstants.CCDAXPATH.compile("./modeCode[not(@nullFlavor)]");
			REL_LANG_PREF_EXP = CCDAConstants.CCDAXPATH.compile("./preferenceInd[not(@nullFlavor)]");
			REL_TELECOM_EXP = CCDAConstants.CCDAXPATH.compile("./telecom[not(@nullFlavor)]");
			REL_TEXT_EXP = CCDAConstants.CCDAXPATH.compile("./text[not(@nullFlavor)]");
			AUTHORS_FROM_HEADER_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/author[not(@nullFlavor)]");
			AUTHORS_WITH_LINKED_REFERENCE_DATA_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument//author[not(@nullFlavor) "
					+ "and assignedAuthor[not(@nullFlavor) and id[not(@nullFlavor) and string(@root) and string(@extension)] "
					+ "and (code[not(@nullFlavor)] or addr[not(@nullFlavor)] or telecom[not(@nullFlavor)] or assignedPerson[not(@nullFlavor)] or representedOrganization[not(@nullFlavor)]) ] ]");
			REL_AUTHOR_EXP = CCDAConstants.CCDAXPATH.compile("./author[not(@nullFlavor)]");
			REL_ASSIGNED_AUTHOR_EXP = CCDAConstants.CCDAXPATH.compile("./assignedAuthor[not(@nullFlavor)]");
			REL_ASSIGNED_PERSON_EXP = CCDAConstants.CCDAXPATH.compile("./assignedPerson[not(@nullFlavor)]");


			ENCOUNTER_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='46240-8']]");
			ADMISSION_DIAG_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='46241-6']]");
			DISCHARGE_DIAG_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='11535-2']]");
			SDTC_REL_DISCHARGE_DISPOSITION_EXP = CCDAConstants.CCDAXPATH.compile("./sdtc:dischargeDispositionCode[not(@nullFlavor)]");

			REL_HOSPITAL_ADMISSION_DIAG_EXP = CCDAConstants.CCDAXPATH.compile("./entry/act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.34']]");
			REL_HOSPITAL_DISCHARGE_DIAG_EXP = CCDAConstants.CCDAXPATH.compile("./entry/act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.33']]");
			REL_ENC_ENTRY_EXP = CCDAConstants.CCDAXPATH.compile("./entry/encounter[not(@nullFlavor)]");
			PROBLEM_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='11450-4']]");
			PAST_ILLNESS_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='11348-0']]");
			PAST_ILLNESS_PROBLEM_OBS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.4']]");
			REL_PROBLEM_OBS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entryRelationship/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.4']]");
			REL_DIAGNOSIS_DATE_ACT_EXP = CCDAConstants.CCDAXPATH.compile("./entryRelationship/act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.502']]");
			MEDICATION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='10160-0']]");
			ADM_MEDICATION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='42346-7']]");
			DM_MEDICATION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='10183-2']]");
			REL_MED_ENTRY_EXP = CCDAConstants.CCDAXPATH.compile("./entry/substanceAdministration[not(@nullFlavor)]");
			DM_ENTRY_EXP = CCDAConstants.CCDAXPATH.compile("./entry/act/entryRelationship/substanceAdministration[not(@nullFlavor)]");
			DM_MED_ACT_EXP = CCDAConstants.CCDAXPATH.compile("./entry/substanceAdministration[not(@nullFlavor)]");
			REL_ROUTE_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./routeCode[not(@nullFlavor)]");
			REL_DOSE_EXP = CCDAConstants.CCDAXPATH.compile("./doseQuantity[not(@nullFlavor)]");
			REL_RATE_EXP = CCDAConstants.CCDAXPATH.compile("./rateQuantity[not(@nullFlavor)]");
			REL_APP_SITE_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./approachSiteCode[not(@nullFlavor)]");
			REL_ADMIN_UNIT_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./administrationUnitCode[not(@nullFlavor)]");
			REL_CONSUM_EXP = CCDAConstants.CCDAXPATH.compile("./consumable/manufacturedProduct[not(@nullFlavor)]");
			REL_MMAT_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./manufacturedMaterial/code[not(@nullFlavor)]");
			REL_MMAT_CODE_TRANS_EXP = CCDAConstants.CCDAXPATH.compile("./manufacturedMaterial/code/translation[not(@nullFlavor)]");
			REL_MANU_ORG_NAME_EXP = CCDAConstants.CCDAXPATH.compile("./manufacturerOrganization/name[not(@nullFlavor)]");
			REL_MMAT_LOT_EXP = CCDAConstants.CCDAXPATH.compile("./manufacturedMaterial/lotNumberText[not(@nullFlavor)]");
			ALLERGIES_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='48765-2']]");
			REL_ALLERGY_REACTION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entryRelationship/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.9']]");
		    REL_ALLERGY_SEVERITY_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entryRelationship/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.8']]");

		    SOCIAL_HISTORY_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='29762-2']]");
		    FUNCTIONAL_STATUS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='47420-5']]");
		    MENTAL_STATUS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='10190-7']]");
		    REASON_FOR_REFERRAL_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='42349-1']]");
		    REL_FUNCTIONAL_STATUS_OBSERVATION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.67']]");
		    REL_DISABILITY_STATUS_OBSERVATION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.505']]");
		    REL_MENTAL_STATUS_OBSERVATION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.74']]");
		    REL_PATIENT_REFERRAL_ACT_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.140']]");
		    REL_INDICATION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entryRelationship/act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.19']]");
		    REL_MEDICATION_DISPENSE_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entryRelationship/supply[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.18']]");
		    REL_MEDICATION_FREE_SIG_ENTRY = CCDAConstants.CCDAXPATH.compile("./entryRelationship/substanceAdministration[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.147']]");
		    REL_MEDICATION_ADHERENCE_ENTRY = CCDAConstants.CCDAXPATH.compile("./entryRelationship/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.508']]");

		    REL_SMOKING_STATUS_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.78']]");
		    REL_TOBACCO_USE_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.85']]");
		    REL_BIRTHSEX_OBS_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.200']]");
		    REL_SEX_ORIENTATION_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.501']]");
		    REL_TRIBAL_AFFILIATION_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.506']]");
		    REL_PREGNANCY_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.15.3.8']]");
		    REL_SEX_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.507']]");
		    REL_OCCUPATION_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.503']]");
		    REL_OCCUPATION_INDUSTRY_EXP = CCDAConstants.CCDAXPATH.compile("./entryRelationship/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.504']]");
		    REL_GENDER_IDENTITY_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.34.3.45']]");
		    REL_SOCIAL_HISTORY_OBS_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.38']]");
		    REL_ASSESSMENT_SCALE_ENTRY_OBS_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.69']]");

		    REL_ASSESSMENT_SCALE_OBS_EXP = CCDAConstants.CCDAXPATH.compile("./entryRelationship/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.69']]");
		    REL_ENTRY_ASSESSMENT_SCALE_OBS_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.69']]");
		    REL_ASSESSMENT_SCALE_SUP_OBS_EXP = CCDAConstants.CCDAXPATH.compile("./entryRelationship/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.86']]");

		    RESULTS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='30954-2']]");
		    REL_LAB_RESULT_ORG_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/organizer[not(@nullFlavor) and statusCode[@code='completed']]");
		    REL_LAB_TEST_ORG_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/organizer[not(@nullFlavor) and statusCode[@code='active']]");
		    REL_COMP_OBS_EXP = CCDAConstants.CCDAXPATH.compile("./component/observation[not(@nullFlavor)]");
		    REL_SPECIMEN_EXP = CCDAConstants.CCDAXPATH.compile("./specimen[not(@nullFlavor)]");
		    REL_SPECIMEN_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./specimenRole/specimenPlayingEntity/code[@nullFlavor]");
		    IMMUNIZATION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='11369-6']]");
		    VITALSIGNS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='8716-3']]");
		    REL_VITAL_ORG_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/organizer[not(@nullFlavor)]");

		    MEDICAL_EQUIPMENT_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='46264-8']]");
		    MEDICAL_EQUIPMENT_ORG_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/organizer[not(@nullFlavor) and not(@negationInd='true')]");
		    MEDICAL_EQUIPMENT_ORG_PAP_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./component/procedure[not(@nullFlavor) and not(@negationInd='true')]");
		    PROCEDURE_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='47519-4']]");
		    REL_PROCEDURE_UDI_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./participant[not(@nullFlavor) and @typeCode='DEV']/participantRole[not(@nullFlavor)]");
		    REL_PROCEDURE_SDL_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./participant[not(@nullFlavor) and @typeCode='LOC']/participantRole[not(@nullFlavor)]");
		    REL_PROC_ACT_PROC_EXP = CCDAConstants.CCDAXPATH.compile("./entry/procedure[not(@nullFlavor) and not(@negationInd='true')]");
		    REL_PROC_ACT_ACT_EXP = CCDAConstants.CCDAXPATH.compile("./entry/act[not(@nullFlavor) and not(@negationInd='true') and templateId[@root='2.16.840.1.113883.10.20.22.4.12']]");
		    REL_TARGET_SITE_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./targetSiteCode[not(@nullFlavor)]");
		    REL_PERF_ENTITY_EXP = CCDAConstants.CCDAXPATH.compile("./performer/assignedEntity[not(@nullFlavor)]");
		    REL_PERF_ENTITY_ORG_EXP = CCDAConstants.CCDAXPATH.compile("./performer/assignedEntity/representedOrganization[not(@nullFlavor)]");
		    REL_REP_ORG_EXP = CCDAConstants.CCDAXPATH.compile("./representedOrganization[not(@nullFlavor)]");
		    REL_NAME_EXP = CCDAConstants.CCDAXPATH.compile("./name[not(@nullFlavor)]");
		    REL_ID_EXP = CCDAConstants.CCDAXPATH.compile("./id[not(@nullFlavor)]");
		    REL_PLAYING_DEV_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./playingDevice/code[not(@nullFlavor)]");
		    REL_SCOPING_ENTITY_ID_EXP = CCDAConstants.CCDAXPATH.compile("./scopingEntity/id[not(@nullFlavor)]");

		    CARE_TEAM_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/documentationOf/serviceEvent/performer[not(@nullFlavor)]");
		    CARE_TEAM_SECTION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='85847-2']]");
		    REL_CARE_TEAM_ORG_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/organizer[not(@nullFlavor)]");
		    REL_CARE_TEAM_MEMBER_ACT_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./component/act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.500.1']]");

		    GOALS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='61146-7']]");
		    HEALTH_CONCERNS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='75310-3']]");
		    PLAN_OF_TREATMENT_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='18776-5']]");
		    ASSESSMENT_AND_PLAN_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='51847-2']]");

		    REL_PLANNED_PROCEDURE_EXP = CCDAConstants.CCDAXPATH.compile("./entry/procedure[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.41']]");
		    REL_GOAL_OBSERVATION_EXP = CCDAConstants.CCDAXPATH.compile("./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.121']]");
		    REL_HEALTH_CONCERN_ACT_EXP = CCDAConstants.CCDAXPATH.compile("./entry/act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.132']]");

		    // Payers Section
		    PAYERS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='48768-6']]");


			REL_ALLERGY_REACTION_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entryRelationship/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.9']]");
		    REL_ALLERGY_SEVERITY_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entryRelationship/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.8']]");


			REL_TEMPLATE_ID_EXP = CCDAConstants.CCDAXPATH.compile("./templateId[not(@nullFlavor)]");
			REL_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./code[not(@nullFlavor)]");
			REL_CODE_WITH_TRANS_EXP = CCDAConstants.CCDAXPATH.compile("./code[not(@nullFlavor) or @nullFlavor='OTH']");
			REL_CODE_TRANS_EXP = CCDAConstants.CCDAXPATH.compile("./code/translation[not(@nullFlavor)]");
			REL_TRANS_EXP = CCDAConstants.CCDAXPATH.compile("./translation[not(@nullFlavor)]");
			REL_VAL_EXP = CCDAConstants.CCDAXPATH.compile("./value[not(@nullFlavor)]");
			REL_VAL_WITH_NF_EXP = CCDAConstants.CCDAXPATH.compile("./value");
			REL_VAL__WITH_TRANS_EXP = CCDAConstants.CCDAXPATH.compile("./value[not(@nullFlavor) or @nullFlavor='OTH']");
			REL_STATUS_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./statusCode[not(@nullFlavor)]");
			REL_INT_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./interpretationCode[not(@nullFlavor)]");
			REL_REF_RANGE_EXP = CCDAConstants.CCDAXPATH.compile("./referenceRange/observationRange/value[@type='IVL_PQ']");
			REL_LOW_EXP = CCDAConstants.CCDAXPATH.compile("./low[not(@nullFlavor)]");
			REL_HIGH_EXP = CCDAConstants.CCDAXPATH.compile("./high[not(@nullFlavor)]");
			REL_PERFORMER_EXP = CCDAConstants.CCDAXPATH.compile("./performer[not(@nullFlavor)]");
			REL_PARTICIPANT_EXP_NO_TYPE_CODE = CCDAConstants.CCDAXPATH.compile("./participant[not(@nullFlavor)]");
			DOC_PARTICIPANT_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/participant[not(@nullFlavor)]");
			REL_FUNCTION_CODE = CCDAConstants.CCDAXPATH.compile("./functionCode[not(@nullFlavor)]");
			REL_PARTICIPANT_EXP = CCDAConstants.CCDAXPATH.compile("./participant[not(@nullFlavor) and @typeCode='IND']");

			INTERVENTIONS_SECTION_V3_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component"
					+ "/section[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.21.2.3' and @extension='2015-08-01']]");
			HEALTH_STATUS_EVALUATIONS_AND_OUTCOMES_SECTION_EXP = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component"
					+ "/section[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.2.61']]");

			REL_ACT_ENTRY_EXP = CCDAConstants.CCDAXPATH.compile("./entry/act[not(@nullFlavor)]");
			REL_ENTRY_EXP = CCDAConstants.CCDAXPATH.compile("./entry[not(@nullFlavor)]");
			REL_SBDM_ENTRY_EXP = CCDAConstants.CCDAXPATH.compile("./substanceAdministration[not(@nullFlavor)]");
			REL_ENTRY_RELSHIP_ACT_EXP = CCDAConstants.CCDAXPATH.compile("./entryRelationship/act[not(@nullFlavor)]");
			REL_PART_ROLE_EXP = CCDAConstants.CCDAXPATH.compile("./participant/participantRole[not(@nullFlavor)]");
			REL_PART_PLAY_ENTITY_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./participant/participantRole/playingEntity/code[not(@nullFlavor)]");
			REL_EFF_TIME_EXP = CCDAConstants.CCDAXPATH.compile("./effectiveTime[not(@nullFlavor)]");
			REL_EFF_TIME_LOW_EXP = CCDAConstants.CCDAXPATH.compile("./low[not(@nullFlavor)]");
			REL_EFF_TIME_HIGH_EXP = CCDAConstants.CCDAXPATH.compile("./high[not(@nullFlavor)]");
			REL_TIME_EXP = CCDAConstants.CCDAXPATH.compile("./time[not(@nullFlavor)]");
			REL_SDTC_TIME_EXP = CCDAConstants.CCDAXPATH.compile("./sdtc:birthTime[not(@nullFlavor)]");
			REL_ENTRY_RELSHIP_OBS_EXP = CCDAConstants.CCDAXPATH.compile("./entryRelationship/observation[not(@nullFlavor)]");
			REL_ASSN_ENTITY_ADDR = CCDAConstants.CCDAXPATH.compile("./assignedEntity/addr[not(@nullFlavor)]");
			REL_ASSN_ENTITY_PERSON_NAME = CCDAConstants.CCDAXPATH.compile("./assignedEntity/assignedPerson/name[not(@nullFlavor)]");
			REL_ASSN_ENTITY_TEL_EXP = CCDAConstants.CCDAXPATH.compile("./assignedEntity/telecom[not(@nullFlavor)]");
			REL_ASSIGNED_ENTITY_EXP = CCDAConstants.CCDAXPATH.compile("./assignedEntity[not(@nullFlavor)]");
			REL_ASSOCIATED_CODE_EXP = CCDAConstants.CCDAXPATH.compile("./associatedEntity/code[not(@nullFlavor)]");
			REL_ASSOCIATED_PERSON_NAME_EXP = CCDAConstants.CCDAXPATH.compile("./associatedEntity/associatedPerson/name[not(@nullFlavor)]");

			NOTES_EXPRESSION = CCDAConstants.CCDAXPATH.compile("/ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.2.65']]");
			REL_COMPONENT_ACTIVITY_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./component/act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.202']]");
			REL_NOTES_ACTIVITY_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entry/act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.202']]");
			REL_ENTRY_REL_NOTES_ACTIVITY_EXPRESSION = CCDAConstants.CCDAXPATH.compile("./entryRelationship/act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.202']]");
			NOTES_ACTIVITY_EXPRESSION = CCDAConstants.CCDAXPATH.compile("//act[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.202']]");

			// Treatment Preference
			TREATMENT_PREFERENCE_EXPRESSION = CCDAConstants.CCDAXPATH.compile("//observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.510']]");
			CARE_PREFERENCE_EXPRESSION = CCDAConstants.CCDAXPATH.compile("//observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.509']]");


		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	NamespaceContext ctx = new NamespaceContext() {
	    public String getNamespaceURI(String prefix) {
	    	if(prefix.contentEquals("hl7"))
	    	{
	    		return "urn:hl7-org:v3";
	    	}
	    	else if(prefix.contentEquals("hl7:sdtc"))
	    	{
	    		return "urn:hl7-org:v3:sdtc";
	    	}
	    	else
	    		return null;
	    }
	    public Iterator getPrefixes(String val) {
	        return null;
	    }
	    public String getPrefix(String uri) {
	        return null;
	    }
	};

}
