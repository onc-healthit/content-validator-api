package org.sitenv.contentvalidator.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.model.CCDALabResult;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

public class LabTestParser {
	
	private static Logger log = LoggerFactory.getLogger(LabTestParser.class.getName());
	
	public static void parse(Document doc, CCDARefModel model, boolean curesUpdate, boolean svap2022)
			throws XPathExpressionException {
    	
    	log.info(" *** Parsing Lab Test *** ");
    	model.setLabTests(retrieveLabTests(doc));	
	}
	
	public static CCDALabResult retrieveLabTests(Document doc) throws XPathExpressionException
	{
		CCDALabResult labTests = null;
		
		Element sectionElement = (Element) CCDAConstants.RESULTS_EXPRESSION.evaluate(doc, XPathConstants.NODE);
		if(sectionElement != null)
		{
			log.info("Lab Test Created ");
			labTests = new CCDALabResult();
			labTests.setResultSectionTempalteIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
								evaluate(sectionElement, XPathConstants.NODESET)));
			
			labTests.setSectionCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
		
			labTests.setResultOrg(LabResultParser.readResultOrganizer((NodeList) CCDAConstants.REL_LAB_TEST_ORG_EXPRESSION.
													evaluate(sectionElement, XPathConstants.NODESET)));
			
			labTests.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(sectionElement, XPathConstants.NODE)));
			
			labTests.setIsLabTestInsteadOfResult(true);
		}
		return labTests;
	}

}
