package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.CCDAGoals;
import org.sitenv.contentvalidator.model.CCDAHealthConcerns;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.model.GoalObservation;
import org.sitenv.contentvalidator.model.HealthConcernAct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class HealthConcernParser {

private static Logger log = LoggerFactory.getLogger(CareTeamMemberParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022)
			throws XPathExpressionException {    	
    	log.info(" *** Parsing Health Concerns *** ");
    	model.setHcs(retrieveHealthConcerns(doc));	
	}
	
	public static CCDAHealthConcerns retrieveHealthConcerns(Document doc) throws XPathExpressionException
	{
		Element sectionElement = (Element) CCDAConstants.HEALTH_CONCERNS_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		
		CCDAHealthConcerns hc = null;
		if(sectionElement != null)
		{
			log.info(" Found Health Concerns Section ");
			hc = new CCDAHealthConcerns();
			hc.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
											evaluate(sectionElement, XPathConstants.NODESET)));
			
			hc.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			hc.setHealthConcernActs(readHealthConcernActs((NodeList) CCDAConstants.REL_HEALTH_CONCERN_ACT_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			hc.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		}
		return hc;
	}
	
	public static ArrayList<HealthConcernAct> readHealthConcernActs(NodeList goalList) throws XPathExpressionException
	{
		ArrayList<HealthConcernAct> hcActs = new ArrayList<>();
		HealthConcernAct hcAct;
		for (int i = 0; i < goalList.getLength(); i++) {
			
			log.info("Adding Health Concern Act ");
			hcAct = new HealthConcernAct();
			Element hcActElement = (Element) goalList.item(i);
			
			hcAct.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
										evaluate(hcActElement, XPathConstants.NODESET)));
			
			hcAct.setHealthConcernActCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(hcActElement, XPathConstants.NODE)));
			
			hcAct.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(hcActElement, XPathConstants.NODE)));
			
			hcAct.setEffectiveTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
								evaluate(hcActElement, XPathConstants.NODE)));
			
			hcAct.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(hcActElement, XPathConstants.NODE)));
			
			NodeList probObsList = (NodeList)CCDAConstants.REL_PROBLEM_OBS_EXPRESSION.
					evaluate(hcActElement, XPathConstants.NODESET);
			hcAct.setProblemObservations(ProblemParser.readProblemObservation(probObsList));
			
			
			hcActs.add(hcAct);
		}
		return hcActs;
	}

}
