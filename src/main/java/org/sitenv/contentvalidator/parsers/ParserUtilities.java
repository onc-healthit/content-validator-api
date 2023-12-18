package org.sitenv.contentvalidator.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.model.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import java.util.ArrayList;
import java.util.HashMap;

public class ParserUtilities {
	
	private static Logger log = LoggerFactory.getLogger(ParserUtilities.class.getName());
	
	public static void compareAuthor(CCDAAuthor refAuthor, CCDAAuthor subAuthor,
			ArrayList<ContentValidationResult> results, String elementName,
			ArrayList<CCDAAuthor> submittedAuthorsWithLinkedReferenceData) {		
		// handle nulls.
		if ((refAuthor != null) && (subAuthor != null)) {
			refAuthor.matches(subAuthor, results, elementName, submittedAuthorsWithLinkedReferenceData);
		} else if ((refAuthor == null) && (subAuthor != null)) {
			log.info(" Getting additional author information which is allowed ");
		} else if ((refAuthor != null) && (subAuthor == null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName
					+ " data, but submitted file does not contain " + elementName + " data",
					ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
			results.add(rs);
		} else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref Authors are null for " + elementName);
		}				
	}
	
	public static void compareProvenanceOrgName(CCDADataElement refDe, CCDAAuthor subAuthor,
			ArrayList<ContentValidationResult> results, String elementName,
			ArrayList<CCDAAuthor> submittedAuthorsWithLinkedReferenceData) {
		// handle nulls.
		if ((refDe != null) && (subAuthor.getOrgName() != null) && (refDe.getValue() != null)
				&& (subAuthor.getOrgName().getValue() != null)) {
			if (subAuthor.getOrgName().getValue().equalsIgnoreCase(refDe.getValue())) {
				// do nothing since both match.
				log.info(" Both Submitted and Ref codes match for " + elementName);
			} else {
				// both ref and sub not null and not equal - so for example, inline data exists,
				// but is mismatched, triggers an error:
				ContentValidationResult rs = new ContentValidationResult(
						"The scenario requires Provenance Org Name of: (" + refDe.getValue() + ") for: " + elementName
								+ ", but submitted file contains Provenance Org Name of: ("
								+ subAuthor.getOrgName().getValue()
								+ ") which does not match the inline author data.",
						ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
				results.add(rs);
			}
		} else if ((refDe == null) && (subAuthor.getOrgName() != null)) {
			log.info(" The submitted file has Provenance Org Name which is OK ");
		} else if ((refDe != null) && (subAuthor.getOrgName() == null)) {
			// ref not null and sub is null - so for example, inline data does not exist in
			// sub, which means we need check for references before failing
			// All we have to do is worry about linked ref check cause this is the situation
			// where sub org name is null
			// the non-null situation is covered and an error returned in first check. So we
			// don't need a 2 step process.
			CCDAAuthor curLinkedSubAuth = CCDAAuthor.findLinkedSubAuth(submittedAuthorsWithLinkedReferenceData,
					subAuthor);
			if (refDe.getValue() != null) {
				if (curLinkedSubAuth.getOrgName() != null && curLinkedSubAuth.getOrgName().getValue() != null) {
					final boolean isValidLinkedRefAndMatch = curLinkedSubAuth.getOrgName().getValue()
							.equalsIgnoreCase(refDe.getValue());
					// We only trigger the error if there's no link and no match. We'll know that as
					// the value in the author will be empty if there wasn't a match as per findLinkedSubAuth
					if (!isValidLinkedRefAndMatch) {
						ContentValidationResult rs = new ContentValidationResult(
								"The scenario requires Provenance Org Name (" + refDe.getValue() + ") as part of: " + elementName
										+ " data, but submitted file contains Provenance Org Name (" 
										+ curLinkedSubAuth.getOrgName().getValue() 
										+ ") in the linked reference, which does not match.",
								ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
						results.add(rs);
					} else {
						log.info("Passed Note Activity Provenance Org Name due to linked reference match");
					}
				} else {
					// if either of these are null then there's no way there could be a valid linked
					// reference so it fails
					ContentValidationResult rs = new ContentValidationResult(
							"The scenario requires Provenance Org Name as part of: " + elementName
									+ " data, but submitted file does not contain Provenance Org Name as part of: "
									+ elementName
									+ " which does not match since the linked reference or id link is missing or invalid.",
							ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
					results.add(rs);
				}
			} else {
				log.info("Can't fail if there is no scenario requirement as refDe.getValue() is null");
			}
		} else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref codes are null for " + elementName);
		}
	}
	
	public static void compareDataElementText(CCDADataElement refDe, CCDADataElement subDe,
			  ArrayList<ContentValidationResult> results, String elementName) {		
		// handle nulls.
		if ((refDe != null) && (subDe != null) && (refDe.getValue() != null) && (subDe.getValue() != null)) {		
			if (subDe.getValue().equalsIgnoreCase(refDe.getValue())) {
				// do nothing since both match.
				log.info(" Both Submitted and Ref codes match for " + elementName);
			}
			else {
				// both ref and sub not null and not equal - so for example, inline data exists, but is mismatched, triggers an error:
				ContentValidationResult rs = new ContentValidationResult(
						"The scenario requires Provenance Org Name of: (" + refDe.getValue() + ") for: " + elementName
								+ ", but submitted file contains Provenance Org Name of: (" + subDe.getValue()
								+ ") which does not match.",
						ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
				results.add(rs);				
			}
		}
		else if ((refDe == null) && (subDe != null)) {
			log.info(" The submitted file has Provenance Org Name which is ok ");
		}
		else if ((refDe != null) && (subDe == null)) {
			// ref not null and sub is null
			ContentValidationResult rs = new ContentValidationResult(
					"The scenario requires Provenance Org Name as part of: " + elementName
							+ " data, but submitted file does not contain Provenance Org Name as part of: "
							+ elementName + " which does not match.",
					ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref codes are null for " + elementName);
		}
	}

	public static void compareDataElement(CCDADataElement refCode, CCDADataElement submittedCode,
										  ArrayList<ContentValidationResult> results, String elementName) {
		// handle nulls.
		if((refCode != null) && (submittedCode != null) ) {

			if(refCode.matches(submittedCode, results, elementName)) {
				// do nothing since both match.
				log.info(" Both Submitted and Ref codes match for " + elementName);
			}

		}
		else if ((refCode == null) && (submittedCode != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName
					+ " data, but submitted file does have " + elementName + " data",
					ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
			results.add(rs);
		} else if ((refCode != null) && (submittedCode == null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName
					+ " data, but submitted file does not contain " + elementName + " data",
					ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
			results.add(rs);
		} else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref codes are null for " + elementName);
		}
	}
	
	public static void compareEffectiveTime(CCDAEffTime refTime, CCDAEffTime submittedTime,
											ArrayList<ContentValidationResult> results, String elementName) {
		
		// handle nulls.
		if((refTime != null) && (submittedTime != null) ) {

			refTime.compare(submittedTime, results, elementName);

		}
		else if ((refTime == null) && (submittedTime != null && submittedTime.hasValidData()) ) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName + " data, but submitted file does have " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if((refTime != null && refTime.hasValidData()) && (submittedTime == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " data, but submitted file does not contain " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref times are null for " + elementName);
		}
	}
	
	public static void compareEffectiveTimeValue(CCDAEffTime refTime, CCDAEffTime submittedTime,
			ArrayList<ContentValidationResult> results, String elementName) {

		// handle nulls.
		if ((refTime != null) && (submittedTime != null) ) {
			log.info(" Effective Times are not null in both Ref and Submitted models, so compare value attributes for them. ");
			refTime.compareValueElement(submittedTime, results, elementName);
		
		}
		else if ((refTime == null) && (submittedTime != null && submittedTime.hasValidData()) ) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName
					+ " data, but submitted file does have " + elementName + " data",
					ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
			results.add(rs);
		}
		else if ((refTime != null && refTime.hasValidData()) && (submittedTime == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName
					+ " data, but submitted file does not contain " + elementName + " data",
					ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref times are null for " + elementName);
		}
	}
	
	public static void compareEffectiveTimeValueWithExactMatchFullPrecision(CCDAEffTime refTime, CCDAEffTime submittedTime,
			ArrayList<ContentValidationResult> results, String elementName) {

		// handle nulls.
		if((refTime != null) && (submittedTime != null) ) {
		
			log.info(" Effective Times are not null in both Ref and Submitted models, so compare value attributes for them. ");
			refTime.compareValueElementWithExactMatchFullPrecision(submittedTime, results, elementName);
		
		}
		else if ((refTime == null) && (submittedTime != null && submittedTime.hasValidData()) ) {
			
			log.info(" Submitted CCDA File can have time values even if not present in the Ref C-CDA ");
		}
		else if((refTime != null && refTime.hasValidData()) && (submittedTime == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " data, but submitted file does not contain " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref times are null for " + elementName);
		}
	}
	
	public static void validateTimeValueLengthDateTimeAndTimezoneDependingOnPrecision(CCDAEffTime effTime,
			ArrayList<ContentValidationResult> results, String localElName, String parentElName, int index,
			boolean isSub) {
		log.info("!! Enter validateTimeValueLengthDateTimeAndTimezoneDependingOnPrecision");
		if (effTime != null && effTime.hasValidData()) {
			log.info("!! effTime != null && effTime.hasValidData()");
			effTime.validateValueLengthDateTimeAndTimezoneDependingOnPrecision(results, localElName, parentElName,
					index, isSub);
		}
	}
	
	public static Boolean compareCodesAndTranlations(CCDACode refCode, CCDACode submittedCode) {
		
		/*
		 * Need to check the following conditions
		 * Ref Code is present in the submitted code element 
		 * Ref Code is present in the submitted Problem's Translation element
		 * Ref Code is not present, but translation of code is present in the submitted code element
		 * Ref Code is not present, but translation of code is present in the submitted code translation element
		 */
		
		if(refCode != null && submittedCode != null &&
		   submittedCode.isCodePresent(refCode) ) {
			
			log.info(" Ref code is present in Submitted Code ");
			// Code is present in the submitted code element or its translations (First 2 conditions)
			return true;
		}
		
		// Check for the 3rd and 4th conditions.
		if(refCode != null) {
			for(CCDACode trans : refCode.getTranslations()) {
				
				if(submittedCode != null && 
				   submittedCode.isCodePresent(trans) ) {
					log.info(" Translation code in reference file is present in submitted code ");
					return true;
				}
					 
			}
		}
		
		log.info(" Could not find the Ref Code or Translation code in the Submitted codes ");
		return false;
	}
	
	public static void compareCodeAndTranslations(CCDACode refCode, CCDACode submittedCode,
			   ArrayList<ContentValidationResult> results, String elementName) {

		// handle section code.
		if((refCode != null) && (submittedCode != null) ) {
		
			if(refCode.matches(submittedCode, results, elementName)) {
				// do nothing since both match.
				log.info(" Both Submitted and Ref codes match for " + elementName);
			}
			
		}
		else if ((refCode == null) && (submittedCode != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName + " data, but submitted file does have " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if((refCode != null) && (submittedCode == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " data, but submitted file does not contain " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref codes are null for " + elementName);
		}
	}
	
	public static void compareCodeIncludingNullFlavor(CCDACode refCode, CCDACode submittedCode,
			   ArrayList<ContentValidationResult> results, String elementName) {

		// handle section code.
		if((refCode != null) && (submittedCode != null) ) {
		
			if(refCode.matchesIncludingNullFlavor(submittedCode, results, elementName)) {
			// do nothing since both match.
			log.info(" Both Submitted and Ref codes match for " + elementName);
			}
		
		}
		else if ((refCode == null) && (submittedCode != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName + " data, but submitted file does have " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if((refCode != null) && (submittedCode == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " data, but submitted file does not contain " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref codes are null for " + elementName);
		}
	}

	public static void compareCode(CCDACode refCode, CCDACode submittedCode,
								   ArrayList<ContentValidationResult> results, String elementName) {
		
		// handle section code.
		if((refCode != null) && (submittedCode != null) ) {

			if(refCode.matches(submittedCode, results, elementName)) {
				// do nothing since both match.
				log.info(" Both Submitted and Ref codes match for " + elementName);
			}

		}
		else if ((refCode == null) && (submittedCode != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName + " data, but submitted file does have " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if((refCode != null) && (submittedCode == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " data, but submitted file does not contain " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref codes are null for " + elementName);
		}
	}
	
	public static void justCompareCode(CCDACode refCode, CCDACode submittedCode,
			   ArrayList<ContentValidationResult> results, String elementName) {

		// handle section code.
		if((refCode != null) && (submittedCode != null) ) {

			if(refCode.codeMatches(submittedCode, results, elementName)) {
				// 	do nothing since both match.
				log.info(" Both Submitted and Ref codes match for " + elementName);
			}

		}
		else if ((refCode == null) && (submittedCode != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName + " data, but submitted file does have " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if((refCode != null) && (submittedCode == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " data, but submitted file does not contain " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref codes are null for " + elementName);
		}
	}
	
	public static void compareQuantity(CCDAPQ refQuantity, CCDAPQ subQuantity,
			   ArrayList<ContentValidationResult> results, String elementName) {

		// handle section code.
		if((refQuantity != null) && (subQuantity != null) ) {

			if(refQuantity.compare(subQuantity, results, elementName)) {
				// 	do nothing since both match.
				log.info(" Both Submitted and Ref quantities match for " + elementName);
			}

		}
		else if ((refQuantity == null) && (subQuantity != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName + " data, but submitted file does have " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if((refQuantity != null) && (subQuantity == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " data, but submitted file does not contain " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref quantity are null for " + elementName);
		}
	}
	
	public static void compareQuantityWithTolerance(CCDAPQ refQuantity, CCDAPQ subQuantity,
			   ArrayList<ContentValidationResult> results, String elementName, Double tolerancePercentage) {

		// handle section code.
		if((refQuantity != null) && (subQuantity != null) ) {

			if(refQuantity.compareWithTolerance(subQuantity, results, elementName, tolerancePercentage)) {
				// 	do nothing since both match.
				log.info(" Both Submitted and Ref quantities match for " + elementName);
			}

		}
		else if ((refQuantity == null) && (subQuantity != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName + " data, but submitted file does have " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if((refQuantity != null) && (subQuantity == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " data, but submitted file does not contain " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref quantity are null for " + elementName);
		}
	}
	
	public static void compareQuantityWithString(CCDAPQ quantity, String val,
			   ArrayList<ContentValidationResult> results, String elementName, Boolean refPQ) {

		if((quantity != null) && (quantity.getValue() != null) && (quantity.getUnits() != null) &&
			(quantity.getUnits().equalsIgnoreCase("1") || quantity.getUnits().equalsIgnoreCase("")) &&
			(val != null) ) {
				
			log.info(" Quantity Val = " + quantity.getValue());
			log.info(" String Val = " + val);
				
			if(quantity.getValue().equalsIgnoreCase(val)) {
				// Do nothing since it is all good.
				log.info("Everything is equal and good");
			}
			else {
				
				String error = "";
				
				if(refPQ) {
					error += "The " + elementName + " : Value PQ - value = " + ((quantity.getValue() != null)?quantity.getValue():"None Specified")
							+ "  , does not match the submitted CCDA : ST - value = " + val; 
				}
				else {
					error += "The " + elementName + " : Value PQ - value = " + ((quantity.getValue() != null)?quantity.getValue():"None Specified")
							+ "  , does not match the test data provided : ST - value = " + val; 
				
				}
				
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);

			}
		
		}
		else if ((quantity == null) && (val != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName + " data, but submitted file does have " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if((quantity != null) && (val == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " data, but submitted file does not contain " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref quantity are null for " + elementName);
		}
	}

	
	public static void compareString(String refString, String subString,
			   ArrayList<ContentValidationResult> results, String elementName) {

		// handle section code.
		if((refString != null) && (subString != null) ) {

			if(refString.equalsIgnoreCase(subString)) {
				// 	do nothing since both match.
				log.info(" Both Submitted and Ref strings match for " + elementName);
			}
			else {
				ContentValidationResult rs = new ContentValidationResult("The String value (" + refString + ") does not match the string value (" + subString + ") in submitted C-CDA for: " + elementName, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
				

		}
		else if ((refString == null) && (subString != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName + " data, but submitted file does have " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if((refString != null) && (subString == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " data, but submitted file does not contain " + elementName + " data", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null.
			log.info(" Both Submitted and Ref strings are null for " + elementName);
		}
	}
	
	public static Boolean templateIdsAreFound(ArrayList<CCDAII> refList, ArrayList<CCDAII> submittedList) {
		
		log.info("Checking Template Ids lists ");
		
		if ((refList != null) && (submittedList != null)) {

			// Check to see if each of the templates in the reflist are part of
			// the submitted list.
			for (CCDAII r : refList) {

				if (!r.isPartOf(submittedList)) {
					return false;
				}
			}
			return true;
		} 
		else if ((refList == null) && (submittedList != null)) {
			return false;
		} 
		else if ((refList != null) && (submittedList == null)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static void compareTemplateIds(ArrayList<CCDAII> refList, ArrayList<CCDAII> submittedList,
										  ArrayList<ContentValidationResult> results, String elementName) {
		
		if((refList != null) && (submittedList != null)) {
		
			// Check to see if each of the templates in the reflist are part of the submitted list.
			for(CCDAII r : refList) {
			
				if(!r.isPartOf(submittedList)) {
					String error = "The " + elementName + " : element - template id, Root Value = " 
							+ ((r.getRootValue() != null)?r.getRootValue():"None specified") + " and Extension Value = " 
					        + ((r.getExtValue() != null)?r.getExtValue():"No Extension value") + " is not present in the submitted CCDA's ";
					ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
					results.add(rs);
				}
				else {
					log.info("Template Ids for " + elementName + " matched. ");
				}
			
			}
		}
		else if((refList == null) && (submittedList != null)) {

			ContentValidationResult rs = new ContentValidationResult("The scenario does not require " + elementName + " template ids, but submitted file does contain " + elementName + " template ids", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if ((refList != null) && (submittedList == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires " + elementName + " template ids, but submitted file does not containt " + elementName + " template ids", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		} 
		else {
			// do nothing since both are null
			log.info("Both Ref and submitted CCDA have no templated Ids for " + elementName);
		}
	}
	
	public static CCDAAuthor readAuthor(Element auth) throws XPathExpressionException {
		
		CCDAAuthor author = null;
		
		if(auth != null) {
			
			log.info(" Found Author ");
			author = new CCDAAuthor();
			
			author.setTemplateIds(readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
					evaluate(auth, XPathConstants.NODESET)));
			
			author.setEffTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_TIME_EXP.
						evaluate(auth, XPathConstants.NODE)));
			
			author.setAuthorIds(readTemplateIdList((NodeList) CCDAConstants.REL_ID_EXP.
					evaluate(auth, XPathConstants.NODESET)));
			
			Element assignedAuthor = (Element)CCDAConstants.REL_ASSIGNED_AUTHOR_EXP.evaluate(auth, XPathConstants.NODE);
			
			if(assignedAuthor != null) {
				
				log.info(" Found Assigned Author ");
				
				author.setAuthorIds(readTemplateIdList((NodeList) CCDAConstants.REL_ID_EXP.
						evaluate(assignedAuthor, XPathConstants.NODESET)));
				
				Element assignedPerson = (Element)CCDAConstants.REL_ASSIGNED_PERSON_EXP.evaluate(assignedAuthor, XPathConstants.NODE);
				
				if(assignedPerson != null) {
					
					log.info(" Found Assigned Person ");
					author.setAuthorFirstName(ParserUtilities.readTextContext((Element) CCDAConstants.REL_GIVEN_NAME_EXP.
							evaluate(assignedPerson, XPathConstants.NODE)));
					
					author.setAuthorFirstName(ParserUtilities.readTextContext((Element) CCDAConstants.REL_FAMILY_NAME_EXP.
							evaluate(assignedPerson, XPathConstants.NODE)));
					
					author.setAuthorName(ParserUtilities.readTextContext((Element) CCDAConstants.REL_NAME_EXP.
							evaluate(assignedPerson, XPathConstants.NODE)));
				
				}
				
				Element repOrg = (Element)CCDAConstants.REL_REP_ORG_EXP.evaluate(assignedAuthor, XPathConstants.NODE);
				
				if(repOrg != null) {
					
					log.info(" Found Rep Org ");
				
					author.setRepOrgIds(readTemplateIdList((NodeList) CCDAConstants.REL_ID_EXP.
							evaluate(repOrg, XPathConstants.NODESET)));
					
					author.setOrgName(ParserUtilities.readTextContext((Element) CCDAConstants.REL_NAME_EXP.
							evaluate(repOrg, XPathConstants.NODE)));
					
					author.setTelecoms(ParserUtilities.readTelecomList((NodeList) CCDAConstants.REL_TELECOM_EXP.
							evaluate(repOrg, XPathConstants.NODESET)));
				
				}
				
			}
			
		}
				
		return author;
	}
	
	public static CCDACode readCode(Element codeElement)
	{
		CCDACode code = null;
		if(codeElement != null)
		{
			code = new CCDACode();
			if(!isEmpty(codeElement.getAttribute("code")))
			{
				code.setCode(codeElement.getAttribute("code"));
			}
			if(!isEmpty(codeElement.getAttribute("codeSystem")))
			{
				code.setCodeSystem(codeElement.getAttribute("codeSystem"));
			}
			if(!isEmpty(codeElement.getAttribute("codeSystemName")))
			{
				code.setCodeSystemName(codeElement.getAttribute("codeSystemName"));
			}
			if(!isEmpty(codeElement.getAttribute("displayName")))
			{
				code.setDisplayName(codeElement.getAttribute("displayName"));
			}
			if(!isEmpty(codeElement.getAttribute("xsi:type")))
			{
				code.setXpath(codeElement.getAttribute("xsi:type"));
			}
			if(!isEmpty(codeElement.getAttribute("nullFlavor"))) 
			{
				code.setNullFlavor(codeElement.getAttribute("nullFlavor"));
			}
		}
		return code;
	}
	
	public static CCDACode readCodeWithTranslation(Element codeElement) throws XPathExpressionException {
		
		CCDACode cd = readCode(codeElement);
		
		if(cd != null) {
			
			NodeList transList = (NodeList) CCDAConstants.REL_TRANS_EXP.
					evaluate(codeElement, XPathConstants.NODESET);	
			
			if(transList != null) {
				
				for (int i = 0; i < transList.getLength(); i++) {
					
					log.info("Reading Translation Code ");
					
					Element node = (Element) transList.item(i);
					
					CCDACode transCode = readCode(node);
					
					if(transCode != null) {
						
						cd.addTranslation(transCode);
					}
					
				}
			}
			
			
		}
		
		return cd;
		
	}

	
	public static CCDAII readTemplateID(Element templateElement)
	{
		CCDAII templateID = null;
		
		if(templateElement != null)
		{
			templateID = new CCDAII();
			if(!isEmpty(templateElement.getAttribute("root")))
			{
				templateID.setRootValue(templateElement.getAttribute("root"));
			}
			if(!isEmpty(templateElement.getAttribute("extension")))
			{
				templateID.setExtValue(templateElement.getAttribute("extension"));
			}
		}
		return templateID;
	}
	
	public static ArrayList<CCDAII> readTemplateIdList(NodeList templateIDNodeList)
	{
		ArrayList<CCDAII> templateList = null;
		if( ! isNodeListEmpty(templateIDNodeList))
		{
			templateList = new ArrayList<>();
		}
		Element templateElement;
		for (int i = 0; i < templateIDNodeList.getLength(); i++) {
			templateElement = (Element) templateIDNodeList.item(i);
			templateList.add(readTemplateID(templateElement));
		}
		return templateList;
	} 
	
	public static CCDAEffTime readEffectiveTime(Element effectiveTimeElement) throws XPathExpressionException
	{
		CCDAEffTime effectiveTime = null;
		
		if(effectiveTimeElement != null)
		{
			effectiveTime = new CCDAEffTime();
			
			effectiveTime.setLow(readDataElement((Element) CCDAConstants.REL_EFF_TIME_LOW_EXP.
	    				evaluate(effectiveTimeElement, XPathConstants.NODE)));
			effectiveTime.setHigh(readDataElement((Element) CCDAConstants.REL_EFF_TIME_HIGH_EXP.
	    				evaluate(effectiveTimeElement, XPathConstants.NODE)));
			
			if((effectiveTime.getLow() == null) && (effectiveTime.getHigh() == null)) {
				effectiveTime.setValue(readDataElement(effectiveTimeElement));
			}
			
			/*if(effectiveTime.getLow() != null)
			{
				effectiveTime.setLowPresent(true);
			}else
				effectiveTime.setLowPresent(false);
			
			if(effectiveTime.getHigh() != null)
			{
				effectiveTime.setHighPresent(true);
			}else
				effectiveTime.setHighPresent(false);*/
				
		}
		return effectiveTime;
	}
	
	public static CCDADataElement readDataElement(Element nodeElement)
	{
		
		CCDADataElement dataElement = null;
		if(nodeElement != null)
		{
			dataElement = new CCDADataElement();
			
			log.info(" Node name = " + nodeElement.getNodeName());
			
			// Handle element which has value attribute
			if(!isEmpty(nodeElement.getAttribute("value")))
			{
				log.info("Reading Value for node: " + nodeElement.getNodeName() + " = " + nodeElement.getAttribute("value"));
				dataElement.setValue(nodeElement.getAttribute("value"));
			}
			else if(nodeElement.getFirstChild() != null && 
					nodeElement.getFirstChild().getNodeValue() != null &&
					!(nodeElement.getFirstChild().getNodeValue().isEmpty())) {
				log.info("Reading Value for node: " + nodeElement.getNodeName() + " = " + nodeElement.getFirstChild().getNodeValue());
				dataElement.setValue(nodeElement.getFirstChild().getNodeValue());
			}
			
			
			if(!isEmpty(nodeElement.getAttribute("lineNumber")))
			{
				dataElement.setLineNumber(Integer.parseInt(nodeElement.getAttribute("lineNumber")));
			}
			if(!isEmpty(nodeElement.getAttribute("xpath")))
			{
				dataElement.setXpath(nodeElement.getAttribute("xpath"));
			}
			if(!isEmpty(nodeElement.getAttribute("use")))
			{
				dataElement.setUse(nodeElement.getAttribute("use"));
			}
		}
		return dataElement;
	}
	
	public static ArrayList<CCDADataElement> readDataElementList(NodeList dataElementNodeList)
	{
		ArrayList<CCDADataElement> dataElementList = null;
		if( ! isNodeListEmpty(dataElementNodeList))
		{
			dataElementList = new ArrayList<>();
		}
		Element dataElement;
		for (int i = 0; i < dataElementNodeList.getLength(); i++) {
			dataElement = (Element) dataElementNodeList.item(i);
			dataElementList.add(readDataElement(dataElement));
		}
		return dataElementList;
	}
	
	public static CCDAPQ readQuantity(Element quantityElement)
	{
		CCDAPQ quantity = null;
		if(quantityElement != null)
		{
			quantity = new CCDAPQ();
			if(!isEmpty(quantityElement.getAttribute("unit")))
			{
				quantity.setUnits(quantityElement.getAttribute("unit"));
			}
			if(!isEmpty(quantityElement.getAttribute("value")))
			{
				quantity.setValue(quantityElement.getAttribute("value"));
			}
			quantity.setXsiType("PQ");
		}
		return quantity;
	}
	
	public static ArrayList<CCDACode> readCodeList(NodeList codeNodeList)
	{
		ArrayList<CCDACode> codeList = null;
		if( ! isNodeListEmpty(codeNodeList))
		{
			codeList = new ArrayList<>();
		}
		Element codeElement;
		for (int i = 0; i < codeNodeList.getLength(); i++) {
			codeElement = (Element) codeNodeList.item(i);
			codeList.add(readCode(codeElement));
		}
		return codeList;
	}
	
	public static CCDAFrequency readFrequency(Element frequencyElement)
	{
		CCDAFrequency frequency = null;
		if(frequencyElement != null)
		{
			frequency =new CCDAFrequency();
			if(!isEmpty(frequencyElement.getAttribute("institutionSpecified")))
			{
				frequency.setInstitutionSpecified(Boolean.parseBoolean(frequencyElement.getAttribute("institutionSpecified")));
			}
			if(!isEmpty(frequencyElement.getAttribute("operator")))
			{
				frequency.setOperator(frequencyElement.getAttribute("operator"));
			}
			Element periodElement  = (Element) frequencyElement.getElementsByTagName("period").item(0);
			if(periodElement != null)
			{
				if(!isEmpty(periodElement.getAttribute("value")))
				{
					frequency.setValue(periodElement.getAttribute("value"));
				}
				if(!isEmpty(periodElement.getAttribute("unit")))
				{
					frequency.setUnit(periodElement.getAttribute("unit"));
				}
			}
		}
		
		return frequency;
	}
	
	public static boolean isEmpty(final String str)
	{
		return str == null || str.trim().length() == 0;
	}
	
	public static boolean isNodeListEmpty(final NodeList nodeList)
	{
		return nodeList == null || nodeList.getLength() == 0;
	}
	
	public static ArrayList<CCDAAddress> readAddressList(NodeList addressNodeList)throws XPathExpressionException
	{
		ArrayList<CCDAAddress> addressList = null;
		if( ! isNodeListEmpty(addressNodeList))
		{
			log.info("Address list found");
			addressList = new ArrayList<CCDAAddress>(); 
		}
		
		Element addrElement;
		for (int i = 0; i < addressNodeList.getLength(); i++) {
			
			log.info("Address found");
			addrElement = (Element) addressNodeList.item(i);
			addressList.add(readAddress(addrElement));
		}
		return addressList;
	}
	
	public static ArrayList<CCDADataElement> readTextContentList(NodeList inputNodeList)
	{
		ArrayList<CCDADataElement> dataList = null;
		if( ! isNodeListEmpty(inputNodeList))
		{
			dataList = new ArrayList<CCDADataElement>();
		}
		for (int i = 0; i < inputNodeList.getLength(); i++) {
			Element value = (Element) inputNodeList.item(i);
			dataList.add(readTextContext(value));
		}
		return dataList;
	}
	
	public static CCDADataElement readTextContext(Element element)
	{
		return element == null ? null : readDataElement(element) ;
	}
	
	public static CCDAAddress readAddress(Element addrElement)throws XPathExpressionException
	{
		CCDAAddress address = null;
		if(addrElement != null)
		{
			log.info("Creating address");
			address = new CCDAAddress();
			
			if(!isEmpty(addrElement.getAttribute("use")))
			{
				address.setPostalAddressUse(addrElement.getAttribute("use"));
			}
			
			address.setAddressLine1(readTextContext((Element) CCDAConstants.REL_STREET_ADDR1_EXP.
	    				evaluate(addrElement, XPathConstants.NODE)));
			
			address.setAddressLine2(readTextContext((Element) CCDAConstants.REL_STREET_ADDR2_EXP.
	    				evaluate(addrElement, XPathConstants.NODE)));
			
			address.setCity(readTextContext((Element) CCDAConstants.REL_CITY_EXP.
	    				evaluate(addrElement, XPathConstants.NODE)));
			
			address.setState(readTextContext((Element) CCDAConstants.REL_STATE_EXP.
	    				evaluate(addrElement, XPathConstants.NODE)));
			
			address.setPostalCode(readTextContext((Element) CCDAConstants.REL_POSTAL_EXP.
	    				evaluate(addrElement, XPathConstants.NODE)));
			
			address.setCountry(readTextContext((Element) CCDAConstants.REL_COUNTRY_EXP.
	    				evaluate(addrElement, XPathConstants.NODE)));
		}
		return address;
	}
	
	public static CCDATelecom readTelecom(Element telecomElement) {
		CCDATelecom telecom = null;
		if(telecomElement != null) {
			telecom = new CCDATelecom();
			final String useVal = telecomElement.getAttribute("use"); 
			if(!isEmpty(useVal)) {
				telecom.setUseAttribute(useVal);
			}
			final String valueVal = telecomElement.getAttribute("value");
			if(!isEmpty(valueVal)) {
				telecom.setValueAttribute(valueVal);
			}
		}
		return telecom;
	}
	
	public static ArrayList<CCDATelecom> readTelecomList(NodeList telecomNodeList) {
		ArrayList<CCDATelecom> telecoms = null;
		if(!isNodeListEmpty(telecomNodeList)) {
			telecoms = new ArrayList<CCDATelecom>();
		}
		Element telecomElement;
		for(int i = 0; i < telecomNodeList.getLength(); i++) {
			telecomElement = (Element) telecomNodeList.item(i);
			telecoms.add(readTelecom(telecomElement));
		}
		return telecoms;
	}	
	
	public static ArrayList<CCDANotesActivity> readNotesActivity(NodeList notesActivityList, CCDANotes parent) throws XPathExpressionException
	{
		ArrayList<CCDANotesActivity> notesActList = null;
		
		if(!ParserUtilities.isNodeListEmpty(notesActivityList)) {
			
			notesActList = new ArrayList<>();
			
			for (int i = 0; i < notesActivityList.getLength(); i++) {
				
				log.info("Found Notes Activity ");
				
				CCDANotesActivity notesActivity = new CCDANotesActivity();
				
				notesActivity.setParent(parent);
				
				Element notesActElem = (Element) notesActivityList.item(i);
				
				notesActivity.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
											evaluate(notesActElem, XPathConstants.NODESET)));
				
				notesActivity.setActivityCode(ParserUtilities.readCodeWithTranslation((Element) CCDAConstants.REL_CODE_EXP.
						evaluate(notesActElem, XPathConstants.NODE)));
				
				notesActivity.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
						evaluate(notesActElem, XPathConstants.NODE)));
				
				notesActivity.setText(ParserUtilities.readTextContext((Element) CCDAConstants.REL_TEXT_EXP.
						evaluate(notesActElem, XPathConstants.NODE)));
				
				notesActivity.setEffTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
									evaluate(notesActElem, XPathConstants.NODE)));
				
				notesActivity.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
						evaluate(notesActElem, XPathConstants.NODE)));
				
				notesActList.add(notesActivity);
			}
			
		}
		
		return notesActList;
	}
	
	public static void populateNotesActiviteis(ArrayList<CCDANotesActivity> notesActs, HashMap<String,CCDANotesActivity> results) {
		
		if(notesActs != null && notesActs.size() > 0) {
		
			for(CCDANotesActivity act : notesActs) {
				
				CCDACode actCode = act.getActivityCode();
				
				// If Translations exist, we need to use translation codes.
				if(actCode.getTranslations() != null && actCode.getTranslations().size() > 0) {
					
					ArrayList<CCDACode> actTransCodes = actCode.getTranslations();
					
					// Already size has been checked.
					if(actTransCodes.get(0).getCode() != null) {
						
						log.info(" Adding the activity using translation instead of base-level code "
								+ actTransCodes.get(0).getCode());
						results.put(actTransCodes.get(0).getCode(), act);
					}
				}
				else {
					
					log.info(" Adding the activity by code itslef and not translation " + actCode.getCode());
					results.put(actCode.getCode(), act);
				}
			} // For all Notes Activities
		}		
	}
	
	public static ArrayList<AssessmentScaleObservation> retrieveAssessmentScaleObservations(NodeList obsList) throws XPathExpressionException {
		
		ArrayList<AssessmentScaleObservation> assessmentObs = null;
		
		if(!ParserUtilities.isNodeListEmpty(obsList))
		{
			assessmentObs = new ArrayList<>();
		}
		
		AssessmentScaleObservation assessment;
		for (int i = 0; i < obsList.getLength(); i++) {
			
			log.info("Adding Assessment Scale Observations");
			assessment = new AssessmentScaleObservation();
			
			Element assessmentObsElement = (Element) obsList.item(i);
			
			assessment.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(assessmentObsElement, XPathConstants.NODESET)));
			
			assessment.setAssessmentCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODE)));
			
			assessment.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODE)));
			
			assessment.setAssessmentTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODE)));
			
			assessment.setInterpretationCode(ParserUtilities.readCode((Element) CCDAConstants.REL_INT_CODE_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODE)));
			
			Element resultValue = (Element) CCDAConstants.REL_VAL_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODE);
			
			if(resultValue != null)
			{
				if(!ParserUtilities.isEmpty(resultValue.getAttribute("xsi:type")))
				{
					String xsiType = resultValue.getAttribute("xsi:type");
					if ((xsiType.equalsIgnoreCase("ST") && (resultValue.getFirstChild()) != null) || 
							(xsiType.equalsIgnoreCase("INT") && (resultValue.getFirstChild()) != null))
					{
						log.info("Assessment Value is ST or INT");
						String value = resultValue.getFirstChild().getNodeValue();
						assessment.setResultString(value);
					}else if(xsiType.equalsIgnoreCase("PQ"))
					{
						log.info("Assessment Value is PQ ");
						assessment.setResultQuantity(ParserUtilities.readQuantity(resultValue));
					}
					else if(xsiType.equalsIgnoreCase("CD"))
					{
						log.info("Assessment Value is CD ");
						assessment.setResultCode(ParserUtilities.readCode(resultValue));
					}
					else
					{
						log.info("Unknown Lab Value");
					}
				}
			}
			
			assessment.setSupportingObservations(ParserUtilities.retrieveAssessmentScaleSupportingObservations((NodeList) CCDAConstants.REL_ASSESSMENT_SCALE_SUP_OBS_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODESET)));
			
			assessment.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODE)));
			
			
			
			assessmentObs.add(assessment);
		}
		
		
		return assessmentObs;
		
	}
	
	public static ArrayList<AssessmentScaleSupportingObs> retrieveAssessmentScaleSupportingObservations(NodeList obsList) throws XPathExpressionException {
		
		ArrayList<AssessmentScaleSupportingObs> assessmentObs = null;
		
		if(!ParserUtilities.isNodeListEmpty(obsList))
		{
			assessmentObs = new ArrayList<>();
		}
		
		AssessmentScaleSupportingObs assessment;
		for (int i = 0; i < obsList.getLength(); i++) {
			
			log.info("Adding Sexual Orientation");
			assessment = new AssessmentScaleSupportingObs();
			
			Element assessmentObsElement = (Element) obsList.item(i);
			
			assessment.setTemplateIds(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
														evaluate(assessmentObsElement, XPathConstants.NODESET)));
			
			assessment.setSupportingObsCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODE)));
			
			assessment.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODE)));
			
			assessment.setSupportingObsTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_EFF_TIME_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODE)));
			
			Element resultValue = (Element) CCDAConstants.REL_VAL_EXP.
					evaluate(assessmentObsElement, XPathConstants.NODE);
			
			if(resultValue != null)
			{
				if(!ParserUtilities.isEmpty(resultValue.getAttribute("xsi:type")))
				{
					String xsiType = resultValue.getAttribute("xsi:type");
					if ((xsiType.equalsIgnoreCase("ST") && (resultValue.getFirstChild()) != null) || 
							(xsiType.equalsIgnoreCase("INT") && (resultValue.getFirstChild()) != null))
					{
						log.info("Assessment Value is ST or INT");
						String value = resultValue.getFirstChild().getNodeValue();
						assessment.setResultString(value);
					}else if(xsiType.equalsIgnoreCase("PQ"))
					{
						log.info("Assessment Value is PQ ");
						assessment.setResultQuantity(ParserUtilities.readQuantity(resultValue));
					}
					else if(xsiType.equalsIgnoreCase("CD"))
					{
						log.info("Assessment Value is CD ");
						assessment.setResultCode(ParserUtilities.readCode(resultValue));
					}
					else
					{
						log.info("Unknown Lab Value");
					}
				}
			}
			
			assessmentObs.add(assessment);
		}
		
		
		return assessmentObs;
		
	}

	public static ArrayList<CCDAPerformer> readPerformers(Element nodeElement) throws XPathExpressionException {
		
		ArrayList<CCDAPerformer> performers = new ArrayList<>();
		CCDAPerformer perf = null;
		
		NodeList perfNodeList = (NodeList) CCDAConstants.REL_PERFORMER_EXP.
				evaluate(nodeElement, XPathConstants.NODESET);
		
		
		if(perfNodeList != null && perfNodeList.getLength() > 0) {
			
			for(int i = 0; i < perfNodeList.getLength(); i++) {
				
				Element perfNodeElement = (Element) perfNodeList.item(i);
				perf = new CCDAPerformer();
				
				perf.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(perfNodeElement, XPathConstants.NODESET)));

				// Get Type code 
				String typeCode = getAttribute(perfNodeElement, CCDAConstants.TYPECODE_EL_NAME);
				
				if(typeCode != null && !typeCode.isEmpty())
					perf.setTypeCode(typeCode);
				
				// Add Assigned Entity
				perf.setAssignedEntity(ParserUtilities.readAssignedEntity((Element) CCDAConstants.REL_ASSIGNED_ENTITY_EXP.
						evaluate(perfNodeElement, XPathConstants.NODE)));
				
				
				// Add performer
				performers.add(perf);
			}
		}
		
		

		return performers;
	}
	
	private static CCDAAssignedEntity readAssignedEntity(Element node) throws XPathExpressionException {

		if(node != null) {
			
			CCDAAssignedEntity ae = new CCDAAssignedEntity();
			
			ae.setAssignedEntityId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_ID_EXP.
					evaluate(node, XPathConstants.NODESET)));
			
			ae.setAssignedEntityCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
					evaluate(node, XPathConstants.NODE)));
			
			ae.setAddresses(ParserUtilities.readAddressList((NodeList) CCDAConstants.REL_ADDR_EXP.
					evaluate(node, XPathConstants.NODESET)));
			
			ae.setTelecom(ParserUtilities.readTelecomList((NodeList) CCDAConstants.REL_TELECOM_EXP.
					evaluate(node, XPathConstants.NODESET)));
			
			ae.setOrganization(ParserUtilities.readRepresentedOrganization((Element) CCDAConstants.REL_REP_ORG_EXP.
					evaluate(node, XPathConstants.NODE)));
			
			
			return ae;
		}
		
		return null;
	}

	private static CCDAOrganization readRepresentedOrganization(Element elem) throws XPathExpressionException {
		
		if(elem != null) {
			
			CCDAOrganization org = new CCDAOrganization();
			
			org.setNames(ParserUtilities.readDataElementList((NodeList) CCDAConstants.REL_NAME_EXP.
					evaluate(elem, XPathConstants.NODESET)));
			
			org.setAddress(ParserUtilities.readAddressList((NodeList) CCDAConstants.REL_ADDR_EXP.
					evaluate(elem, XPathConstants.NODESET)));
			
			org.setTelecom(ParserUtilities.readTelecomList((NodeList) CCDAConstants.REL_TELECOM_EXP.
					evaluate(elem, XPathConstants.NODESET)));
			
			return org;
		}
		return null;
	}

	public static void readParticipantName(Element nameElement,CCDAParticipant participant) throws XPathExpressionException
	{
		if(nameElement != null)
		{
			NodeList giveNameNodeList = (NodeList) CCDAConstants.REL_GIVEN_NAME_EXP.
					evaluate(nameElement, XPathConstants.NODESET);
			
			for (int i = 0; i < giveNameNodeList.getLength(); i++) {
				Element givenNameElement = (Element) giveNameNodeList.item(i);
				if(!ParserUtilities.isEmpty(givenNameElement.getAttribute("qualifier")))
				{
					participant.getAssignedEntity().setPreviousName(ParserUtilities.readTextContext(givenNameElement));
				}else if (i == 0) {
					participant.getAssignedEntity().setFirstName(ParserUtilities.readTextContext(givenNameElement));
				}else {
					participant.getAssignedEntity().setMiddleName(ParserUtilities.readTextContext(givenNameElement));
				}
			}
			
			participant.getAssignedEntity().setLastName(ParserUtilities.readTextContext((Element) CCDAConstants.REL_FAMILY_NAME_EXP.
					evaluate(nameElement, XPathConstants.NODE)));
			participant.getAssignedEntity().setSuffix(ParserUtilities.readTextContext((Element) CCDAConstants.REL_SUFFIX_EXP.
					evaluate(nameElement, XPathConstants.NODE)));
		}
	}
	
	public static String getAttribute(Element element, String attribute) {
		
		if (element.hasAttributes()) {
	        Attr attr = (Attr) element.getAttributes().getNamedItem(attribute);
	        if (attr != null) {
	            return attr.getValue();
	        }
	    }
		return null;
	}

	public static ArrayList<CCDAParticipant> readParticipant(Element policyActivityElement) throws XPathExpressionException {
		

		ArrayList<CCDAParticipant> participants = new ArrayList<>();
		CCDAParticipant part = null;
		
		NodeList partNodeList = (NodeList) CCDAConstants.REL_PARTICIPANT_EXP_NO_TYPE_CODE.
				evaluate(policyActivityElement, XPathConstants.NODESET);
		
		if(partNodeList != null && partNodeList.getLength() > 0) {
			
			for(int i = 0; i < partNodeList.getLength(); i++) {
				
				Element partNodeElement = (Element) partNodeList.item(i);
				part = new CCDAParticipant();
				
				part.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(partNodeElement, XPathConstants.NODESET)));
				
				// Get Type code 
				String typeCode = getAttribute(partNodeElement, CCDAConstants.TYPECODE_EL_NAME);
				
				if(typeCode != null && !typeCode.isEmpty())
					part.setParticipantTypeCode(typeCode);
				
				part.setEffectiveTime(ParserUtilities.readEffectiveTime((Element) CCDAConstants.REL_TIME_EXP.
						evaluate(partNodeElement, XPathConstants.NODE)));
				
				setParticipantRoleDetails(part, partNodeElement);
				
				// Add performer
				participants.add(part);
			}
		}
		
		

		return participants;

	}

	private static void setParticipantRoleDetails(CCDAParticipant part, Element partNodeElement) throws XPathExpressionException {
		
		if(partNodeElement != null && part != null) {
			
			// Get the ParticipantRole 
			Element partRoleElem = (Element) CCDAConstants.REL_PART_ROLE_EXP.
					evaluate(partNodeElement, XPathConstants.NODE);
			 
			if(partRoleElem != null) {
				
				part.setParticipantRoleId(ParserUtilities.readTemplateID((Element)CCDAConstants.REL_ID_EXP.
						evaluate(partRoleElem, XPathConstants.NODE)));
				
				part.setParticipantRoleCode(ParserUtilities.readCode((Element)CCDAConstants.REL_CODE_EXP.
						evaluate(partRoleElem, XPathConstants.NODE)));
				
				part.setParticipantFunctionCode(ParserUtilities.readCode((Element)CCDAConstants.REL_FUNCTION_CODE.
						evaluate(partRoleElem, XPathConstants.NODE)));
				
				part.setParticipantRoleAddress(ParserUtilities.readAddress((Element)CCDAConstants.REL_ADDR_EXP.
						evaluate(partRoleElem, XPathConstants.NODE)));
								
				part.setPlayingEntity(ParserUtilities.readPlayingEntity(partRoleElem));
			}
				
			
			
		}
		
	}

	private static CCDAPlayingEntity readPlayingEntity(Element partRoleElem) throws XPathExpressionException {
		
		if(partRoleElem != null) {
			
			// Get the Playing Entity 
			Element playingEntityElem = (Element) CCDAConstants.REL_PLAY_ENTITY_EXP.
								evaluate(partRoleElem, XPathConstants.NODE);
			
			if(playingEntityElem != null) {
				
				Element nameElement = (Element) CCDAConstants.REL_NAME_EXP.
						evaluate(partRoleElem, XPathConstants.NODE);
				
				if(nameElement != null) {
					
					CCDAPlayingEntity pe = new CCDAPlayingEntity();
					NodeList giveNameNodeList = (NodeList) CCDAConstants.REL_GIVEN_NAME_EXP.
							evaluate(nameElement, XPathConstants.NODESET);
					
					for (int i = 0; i < giveNameNodeList.getLength(); i++) {
						Element givenNameElement = (Element) giveNameNodeList.item(i);
						if(!ParserUtilities.isEmpty(givenNameElement.getAttribute("qualifier")))
						{
							pe.setPreviousName(ParserUtilities.readTextContext(givenNameElement));
						}else if (i == 0) {
							pe.setFirstName(ParserUtilities.readTextContext(givenNameElement));
						}else {
							pe.setMiddleName(ParserUtilities.readTextContext(givenNameElement));
						}
					}
					
					pe.setLastName(ParserUtilities.readTextContext((Element) CCDAConstants.REL_FAMILY_NAME_EXP.
							evaluate(nameElement, XPathConstants.NODE)));
					pe.setSuffix(ParserUtilities.readTextContext((Element) CCDAConstants.REL_SUFFIX_EXP.
							evaluate(nameElement, XPathConstants.NODE)));
					
					pe.setDob(ParserUtilities.readDataElement((Element) CCDAConstants.REL_SDTC_TIME_EXP.
							evaluate(nameElement, XPathConstants.NODE)));
					
					return pe;
					
				}
				
			}
			
		}
		
		return null;
	}

	public static ArrayList<CCDAIndication> readIndications(NodeList indicationList) throws XPathExpressionException {
		
		if(indicationList != null) {
			
			ArrayList<CCDAIndication> indList = new ArrayList<>();
			
			for(int i = 0; i < indicationList.getLength(); i++) {
				
				log.info("Adding CCDA Indication Observation");
				CCDAIndication ind = new CCDAIndication();
				
				Element indElement = (Element) indicationList.item(i);
				
				ind.setTemplateId(ParserUtilities.readTemplateIdList((NodeList) CCDAConstants.REL_TEMPLATE_ID_EXP.
						evaluate(indElement, XPathConstants.NODESET)));

				ind.setIndicationCode(ParserUtilities.readCode((Element) CCDAConstants.REL_CODE_EXP.
				evaluate(indElement, XPathConstants.NODE)));
				
				ind.setStatusCode(ParserUtilities.readCode((Element) CCDAConstants.REL_STATUS_CODE_EXP.
				evaluate(indElement, XPathConstants.NODE)));
				
				ind.setIndicationType(ParserUtilities.readCode((Element) CCDAConstants.REL_VAL_WITH_NF_EXP.
				evaluate(indElement, XPathConstants.NODE)));
				
				ind.setAuthor(ParserUtilities.readAuthor((Element) CCDAConstants.REL_AUTHOR_EXP.
				evaluate(indElement, XPathConstants.NODE)));

				indList.add(ind);
				
			}
			
			return indList;
			
		}
		
		return null;
		
	}

	
}
