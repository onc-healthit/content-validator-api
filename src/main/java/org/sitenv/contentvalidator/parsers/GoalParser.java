package org.sitenv.contentvalidator.parsers;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.sitenv.contentvalidator.model.CCDACareTeamMember;
import org.sitenv.contentvalidator.model.CCDACareTeamMemberAct;
import org.sitenv.contentvalidator.model.CCDAGoals;
import org.sitenv.contentvalidator.model.CCDAParticipant;
import org.sitenv.contentvalidator.model.CCDAProblem;
import org.sitenv.contentvalidator.model.CCDAProblemConcern;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.model.GoalObservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GoalParser {
	
	private static Logger log = LoggerFactory.getLogger(CareTeamMemberParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022)
			throws XPathExpressionException {    	
    	log.info(" *** Parsing Goals *** ");
    	model.setGoals(retrieveGoals(doc));	
	}
	
	public static CCDAGoals retrieveGoals(Document doc) throws XPathExpressionException
	{
		Element sectionElement = (Element) CCDAConstants.GOALS_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		
		CCDAGoals goals = null;
		if(sectionElement != null)
		{
			log.info(" Found Goals Section ");
			goals = new CCDAGoals();
			goals.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
											evaluate(sectionElement, XPathConstants.NODESET)));
			
			goals.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			goals.setGoalObservations(readGoalObservation((NodeList) CCDAConstants.REL_GOAL_OBSERVATION_EXP.
					evaluate(sectionElement, XPathConstants.NODESET)));
			
			goals.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		}
		return goals;
	}
	
	public static ArrayList<GoalObservation> readGoalObservation(NodeList goalList) throws XPathExpressionException
	{
		ArrayList<GoalObservation> goalObs = new ArrayList<>();
		GoalObservation goalObservation;
		for (int i = 0; i < goalList.getLength(); i++) {
			
			log.info("Adding Goal Observation ");
			goalObservation = new GoalObservation();
			Element goalObsElement = (Element) goalList.item(i);
			
			goalObservation.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
										evaluate(goalObsElement, XPathConstants.NODESET)));
			
			goalObservation.setGoalCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(goalObsElement, XPathConstants.NODE)));
			
			goalObservation.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(goalObsElement, XPathConstants.NODE)));
			
			goalObservation.setEffectiveTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
								evaluate(goalObsElement, XPathConstants.NODE)));
			
			Element resultValue = (Element) CCDAConstants.REL_VAL_EXP.
					evaluate(goalObsElement, XPathConstants.NODE);
			
			if(resultValue != null)
			{
				if(!ParserUtilities.isEmpty(resultValue.getAttribute("xsi:type")))
				{
					String xsiType = resultValue.getAttribute("xsi:type");
					if ((xsiType.equalsIgnoreCase("ST") && (resultValue.getFirstChild()) != null) || 
							(xsiType.equalsIgnoreCase("INT") && (resultValue.getFirstChild()) != null))
					{
						log.info("Goal Value is ST or INT");
						String value = resultValue.getFirstChild().getNodeValue();
						goalObservation.setResultString(value);
					}else if(xsiType.equalsIgnoreCase("PQ"))
					{
						log.info("Goal Value is PQ ");
						goalObservation.setResults(ParserUtilities.readQuantity(resultValue));
					}
					else if(xsiType.equalsIgnoreCase("CD"))
					{
						log.info("Goal Value is CD ");
						goalObservation.setResultCode(ParserUtilities.readCode(resultValue));
					}
					else
					{
						log.info("Unknown Lab Value");
					}
				}
			}
			
			
			goalObservation.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(goalObsElement, XPathConstants.NODE)));
			
			goalObs.add(goalObservation);
		}
		return goalObs;
	}


}
