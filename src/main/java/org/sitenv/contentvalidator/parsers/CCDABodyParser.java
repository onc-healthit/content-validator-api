package org.sitenv.contentvalidator.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;

public class CCDABodyParser {

	private static Logger log = LoggerFactory.getLogger(CCDABodyParser.class.getName());

	static public void parseBody(Document doc, CCDARefModel model,
			boolean curesUpdate, boolean svap2022, boolean svap2023, boolean uscdiv4)
			throws XPathExpressionException {

		log.info(" Parsing Encounters ");
		EncounterParser.parse(doc,model, curesUpdate, svap2022, svap2023);

		log.info(" Parsing Problems ");
		ProblemParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		log.info(" Parsing Medications ");
		MedicationParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		log.info("Parsing Allergies ");
		AllergiesParser.parse(doc,model, curesUpdate, svap2022, svap2023);

		log.info("Parsing Social History ");
		SocialHistoryParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		log.info(" Parsing lab Results ");
		LabResultParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		log.info(" Parsing lab tests ");
		LabTestParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		log.info("Parsing Vitals ");
		VitalSignParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		log.info("Parsing Procedures ");
		ProcedureParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		log.info("Parsing Care Team Members ");
		CareTeamMemberParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		log.info("Parsing CarePlan Sections ");
		CarePlanSectionsParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		log.info("Parsing Immunizations ");
		ImmunizationParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		log.info("Parsing Medical Equipments");
		MedicalEquipmentParser.parse(doc, model, curesUpdate, svap2022, svap2023);

		logUscdiTypesStatus(curesUpdate, svap2022, svap2023, uscdiv4);

			log.info(" Parsing Notes Section ");
			NotesParser.parse(doc, model, curesUpdate, svap2022, svap2023);

			// Not required by the spec but required by our scenarios due to them having authors in the header
			log.info(" Parsing Doc Author ");
			AuthorParser.parse(doc, model, curesUpdate, svap2022, svap2023);

			log.info(" Parsing Care Team Section ");
			CareTeamMemberParser.parseCareTeamSection(doc, model, curesUpdate, svap2022, svap2023);


		log.info("Parsing Goals");
		GoalParser.parse(doc, model, curesUpdate, svap2022, svap2023);

			log.info(" Parsing Health Concerns ");
			HealthConcernParser.parse(doc, model, curesUpdate, svap2022, svap2023);

			log.info(" Parsing Plan of Treatment ");
			PlanOfTreatmentParser.parse(doc, model, curesUpdate, svap2022, svap2023);

			log.info(" Parsing Plan of Treatment ");
			AssessmentAndPlanParser.parse(doc, model, curesUpdate, svap2022, svap2023);

			if(svap2023 || uscdiv4) {

				log.info("Parsing Care Team Members from Care Team Section ");
				CareTeamMemberParser.parseCareTeamSection(doc, model, curesUpdate, svap2022, svap2023);

				log.info("Parsing Payers Section ");
				PayerParser.parse(doc, model, curesUpdate, svap2022, svap2023);

				log.info(" Functional Status Section ");
				FunctionalStatusParser.parse(doc, model, curesUpdate, svap2022, svap2023);

				log.info(" Mental Status Section ");
				MentalStatusParser.parse(doc, model, curesUpdate, svap2022, svap2023);

				log.info(" Reason for Referral Section");
				ReasonForReferralParser.parse(doc, model, curesUpdate, svap2022, svap2023);
			}

			if(uscdiv4) {

				log.info(" Parsing Treatment Intervention Preferences ");
				TreatmentInterventionPreferenceParser.parse(doc, model, curesUpdate, svap2022, svap2023, uscdiv4);

				log.info(" Parsing Care Experience Preferences ");
				CareExperiencePreferenceParser.parse(doc, model, curesUpdate, svap2022, svap2023, uscdiv4);

			}

			if(svap2022 || svap2023 || uscdiv4) {
				curesUpdate = true;
			}

	}

	private static void logUscdiTypesStatus(boolean curesUpdate, boolean svap2022, boolean svap2023, boolean uscdiv4) {
		log.info("logUscdiTypesStatus()");
		log.info("curesUpdate: " + curesUpdate);
		log.info("svap2022: " + svap2022);
		log.info("svap2023: " + svap2023);
		log.info("uscdiv4: " + uscdiv4);
	}
}