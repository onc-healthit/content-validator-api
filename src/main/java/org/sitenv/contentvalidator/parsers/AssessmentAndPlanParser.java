package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.CCDAAssessmentAndPlanSection;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.model.PlannedProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AssessmentAndPlanParser {

private static Logger log = LoggerFactory.getLogger(CareTeamMemberParser.class.getName());

	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022, boolean svap2023)
			throws XPathExpressionException {
    	log.info(" *** Parsing Plan Of Treatment  *** ");
    	model.setAssessmentAndPlanSection(retrieveAssessmentAndPlanSection(doc));
	}


	public static CCDAAssessmentAndPlanSection retrieveAssessmentAndPlanSection(Document doc) throws XPathExpressionException
	{


		Element sectionElement = (Element) CCDAConstants.ASSESSMENT_AND_PLAN_EXPRESSION.evaluate(doc, XPathConstants.NODE);

		CCDAAssessmentAndPlanSection pot = null;
		if(sectionElement != null)
		{
			log.info(" Found Assessment And Plant Section ");
			pot = new CCDAAssessmentAndPlanSection();
			pot.setTemplateIdsPOT(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
											evaluate(sectionElement, XPathConstants.NODESET)));

			pot.setSectionCodePOT(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));

			pot.setPlannedProcedures(readPlannedProcedures((NodeList) CCDAConstants.REL_PLANNED_PROCEDURE_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));

			pot.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		}
		return pot;
	}

	public static ArrayList<PlannedProcedure> readPlannedProcedures(NodeList ppList) throws XPathExpressionException
	{
		ArrayList<PlannedProcedure> pps = new ArrayList<>();
		PlannedProcedure pp;
		for (int i = 0; i < ppList.getLength(); i++) {

			log.info("Adding Planned Procedure ");
			pp = new PlannedProcedure();
			Element ppElement = (Element) ppList.item(i);

			pp.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
										evaluate(ppElement, XPathConstants.NODESET)));

			pp.setProcedureCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(ppElement, XPathConstants.NODE)));

			pp.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(ppElement, XPathConstants.NODE)));

			pp.setProcedureTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
								evaluate(ppElement, XPathConstants.NODE)));

			pp.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(ppElement, XPathConstants.NODE)));

			pps.add(pp);
		}
		return pps;
	}

}
