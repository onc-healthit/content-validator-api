package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CCDAEffTime {
	
	private static Logger log = Logger.getLogger(CCDAEffTime.class.getName());

	private CCDADataElement low;
	private Boolean         lowPresent;
	private CCDADataElement high;
	private Boolean         highPresent;
	private CCDADataElement value;
	private Boolean         valuePresent;
	private String 			singleAdministration;
	
	public Boolean hasValidData() {
		
		if( (lowPresent || highPresent || valuePresent) )
			return true;
		else
			return false;
	}
	
	public void compare(CCDAEffTime subTime, ArrayList<ContentValidationResult> results, String elementName) {
		
		String refTime;
		String submittedtime;
		log.info(" Comparing Effective Times for " + elementName);
		
		// Compare low time values
		if(lowPresent && subTime.getLowPresent() ) {

			if(low.getValue().length() >= 8)
				refTime = low.getValue().substring(0,8);
			else 
				refTime = low.getValue();
			
			if(subTime.getLow().getValue().length() >= 8)
				submittedtime = subTime.getLow().getValue().substring(0,8);
			else 
				submittedtime = subTime.getLow().getValue();
			
			if(refTime.equalsIgnoreCase(submittedtime) ) {
				log.info("Low Time element matches");
			}
			else {
				String error = "The " + elementName + " (Effective Time: low time value) is " + low.getValue() + " , but submitted CCDA (Effective Time: low time value) is " + subTime.getLow().getValue() + " which does not match ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
			
						
		}
		else if(lowPresent && !subTime.getLowPresent()) {
			
			String error = "The " + elementName + " (low time value) is required, but submitted CCDA does not contain the (low time value) for " + elementName;
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if(!lowPresent && subTime.getLowPresent()) {
			
			String error = "The " + elementName + " (low time value) is not required, but submitted CCDA contains the (low time value) for " + elementName;
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("Low value absent in both refernce and submitted models ");
		}
		
		// Compare High Times values
		if(highPresent && subTime.getHighPresent() ) {

			if(high.getValue().length() >= 8)
				refTime = high.getValue().substring(0,8);
			else 
				refTime = high.getValue();
			
			if(subTime.getHigh().getValue().length() >= 8)
				submittedtime = subTime.getHigh().getValue().substring(0,8);
			else 
				submittedtime = subTime.getHigh().getValue();
			
			if(refTime.equalsIgnoreCase(submittedtime) ) {
				log.info("High Time element matches");
			}
			else {
				String error = "The " + elementName + " (Effective Time: High time value) is " + high.getValue() + " , but submitted CCDA (Effective Time: High time value) is " + subTime.getHigh().getValue() + " which does not match ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}
						
		}
		else if(highPresent && !subTime.getHighPresent()) {
			
			String error = "The " + elementName + " (high time value) is required, but submitted CCDA does not contain the (high time value) for " + elementName;
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if(!highPresent && subTime.getHighPresent()) {
			
			String error = "The " + elementName + " (high time value) is not required, but submitted CCDA contains the (high time value) for " + elementName;
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("High value absent in both refernce and submitted models ");
		}
	 
		// Compare Time value element
/*		if(valuePresent && subTime.getValuePresent() ) {

			if(value.getValue().length() >= 8)
				refTime = value.getValue().substring(0,8);
			else 
				refTime = value.getValue();

			if(subTime.getValue().getValue().length() >= 8)
				submittedtime = subTime.getValue().getValue().substring(0,8);
			else 
				submittedtime = subTime.getValue().getValue();

			if(refTime.equalsIgnoreCase(submittedtime) ) {
				log.info("Value Time element matches");
			}
			else {
				String error = "The " + elementName + " (Effective Time: Value ) is " + value.getValue() + " , but submitted CCDA (Effective Time: Value ) is " + subTime.getValue().getValue() + " which does not match ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}

		}
		else if(valuePresent && !subTime.getValuePresent()) {

			String error = "The " + elementName + " (value time element ) is required, but submitted CCDA does not contain the (value time element) for " + elementName;
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if(!valuePresent && subTime.getValuePresent()) {

			String error = "The " + elementName + " (value time element) is not required, but submitted CCDA contains the (value time element) for " + elementName;
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("Value Time elements absent in both refernce and submitted models ");
		}*/
	}
	
	public void compareValueElement(CCDAEffTime subTime, ArrayList<ContentValidationResult> results, String elementName) {
		
		String refTime;
		String submittedtime;
		log.info(" Comparing Effective Times for " + elementName);
			 
		// Compare Time value element
		if(valuePresent && subTime.getValuePresent() ) {

			if(value.getValue().length() >= 8)
				refTime = value.getValue().substring(0,8);
			else 
				refTime = value.getValue();

			if(subTime.getValue().getValue().length() >= 8)
				submittedtime = subTime.getValue().getValue().substring(0,8);
			else 
				submittedtime = subTime.getValue().getValue();

			if(refTime.equalsIgnoreCase(submittedtime) ) {
				log.info("Value Time element matches");
			}
			else {
				String error = "The " + elementName + " (Effective Time: Value ) is " + value.getValue() + " , but submitted CCDA (Effective Time: Value ) is " + subTime.getValue().getValue() + " which does not match ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}

		}
		else if(valuePresent && !subTime.getValuePresent()) {

			String error = "The " + elementName + " (value time element ) is required, but submitted CCDA does not contain the (value time element) for " + elementName;
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if(!valuePresent && subTime.getValuePresent()) {

			String error = "The " + elementName + " (value time element) is not required, but submitted CCDA contains the (value time element) for " + elementName;
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("Value Time elements absent in both refernce and submitted models ");
		}
	}
	
	public void compareValueElementWithExactMatchFullPrecision(CCDAEffTime subTime, ArrayList<ContentValidationResult> results, String elementName) {
		
		String refTime;
		String submittedtime;
		log.info(" Comparing Effective Times for " + elementName);
			 
		// Compare Time value element
		if(valuePresent && subTime.getValuePresent() ) {

			
			refTime = value.getValue();
			submittedtime = subTime.getValue().getValue();

			if(refTime.equalsIgnoreCase(submittedtime) ) {
				log.info("Value Time element matches");
			}
			else {
				String error = "The " + elementName + " (Effective Time: Value ) is " + value.getValue() + " , but submitted CCDA (Effective Time: Value ) is " + subTime.getValue().getValue() + " which does not match ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
				results.add(rs);
			}

		}
		else if(valuePresent && !subTime.getValuePresent()) {

			String error = "The " + elementName + " (value time element ) is required, but submitted CCDA does not contain the (value time element) for " + elementName;
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if(!valuePresent && subTime.getValuePresent()) {

			log.info(" It is ok to have author time even if it is not present in the Ref CCDA");
		}
		else {
			log.info("Value Time elements absent in both refernce and submitted models ");
		}
	}
	
	public void validateValueLengthDateTimeAndTimezoneDependingOnPrecision(ArrayList<ContentValidationResult> results,
			String localElName, String parentElName, int index, boolean isSub) {
		System.out.println("!!: ENTER validateValueLengthDateTimeAndTimezoneDependingOnPrecision");				
					
		if (valuePresent) {
			log.info(" Validating Times for " + localElName);
			final String timeDocType = isSub ? "submitted" : "scenario";
			final String errorPrefix = "The " + timeDocType + " Provenance (Time: Value) ";
			final boolean isParentElNameDocLevel = parentElName.equalsIgnoreCase("Document Level");

			// validate date only in first 8 chars so we can have more specific errors returned
			// This validation fails for letters, symbols, or being too short. Too long ends up in the next validation.
			// instead of one big RegEx with an or condition and one mixed less-specific error
			String dateOnly8CharTime;
			if (value.getValue().length() > 8) {
				log.info("!!: time > 8: " + value.getValue() != null ? value.getValue() : "null");
				// we only have > 8 characters, store only the 1st 8
				dateOnly8CharTime = value.getValue().substring(0, 8);
			} else {
				log.info("!!: time < 9: " + value.getValue() != null ? value.getValue() : "null");
				// we only have 8 characters, store them all
				dateOnly8CharTime = value.getValue();
			}			
			System.out.println("!!: stored dateOnly8CharTime: " + dateOnly8CharTime);
			// validate dateOnly8CharTime with RegEx for 1st 8 chars
//			^[0-9]{8}$
//			^ asserts position at start of a line
//			Match a single character present in the list below [0-9]
//			{8} matches the previous token exactly 8 times
//			0-9 matches a single character in the range between 0 (index 48) and 9 (index 57) (case sensitive)
//			$ asserts position at the end of a line			
			Pattern baseDatePattern = Pattern.compile("^[0-9]{8}$");
			Matcher baseDateMatcher = baseDatePattern.matcher(dateOnly8CharTime);
			if (baseDateMatcher.find()) {
				log.info("We have a validly formatted base 8 character date");
			} else {
				log.info("!! The date portion of the " + timeDocType + " time element value " + dateOnly8CharTime + " is invalid data as per RegEx");
				String error = errorPrefix + value.getValue() + " at " + parentElName + (!isParentElNameDocLevel ? " index " + (index + 1) : "")
						+ ", is invalid. Please ensure the value starts with an 8-digit date. "
						+ "The invalid date portion of the value is " + baseDateMatcher + ".";

				ContentValidationResult rs = new ContentValidationResult(error,
						ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
				results.add(rs);						
			}
						
			// validate time and time-zone portions specifically
			if (value.getValue().length() > 8) {
				log.info("!!: time > 8 in time and time-zone validation: " + value.getValue() != null ? value.getValue() : "null");
				String timeAndTimeZone = value.getValue().substring(8);
				System.out.println("!!: stored timeAndTimeZone: " + timeAndTimeZone);
				// validate timeAndTimeZone with RegEx for chars after first 8
				// Note: If there is an issue where the base date > 8 chars, that error will show up in the time portion.
				// This is a perfectly reasonable result as the time zone is supposed to start after 8 chars and if it does not it's invalid
//				^([0-9]{4})(-|\+)([0-9]{4})$
//				^ asserts position at start of a line
//				1st Capturing Group ([0-9])
//				Match a single character present in the list below [0-9]
//				{4} matches the previous token exactly 4 times
//				0-9 matches a single character in the range between 0 (index 48) and 9 (index 57) (case sensitive)
//				2nd Capturing Group (-|\+)
//				1st Alternative -
//				- matches the character - literally (case sensitive)
//				2nd Alternative \+
//				\+ matches the character + literally (case sensitive)
//				3rd Capturing Group ([0-9])
//				Match a single character present in the list below [0-9]
//				{4} matches the previous token exactly 4 times
//				0-9 matches a single character in the range between 0 (index 48) and 9 (index 57) (case sensitive)
//				$ asserts position at the end of a line
				Pattern timeAndTimeZoneDatePattern = Pattern.compile("^([0-9]{4})(-|\\\\+)([0-9]{4})$");
				Matcher timeAndTimeZoneDateMatcher = timeAndTimeZoneDatePattern.matcher(timeAndTimeZone);
				if (timeAndTimeZoneDateMatcher.find()) {
					log.info("We have a validly formatted base 8 character date");
				} else {
					log.info("!! The time and time-zone portion of the " + timeDocType + " time element value " + timeAndTimeZone + " is invalid data as per RegEx");
					String error = errorPrefix + value.getValue() + " at " + parentElName + (!isParentElNameDocLevel ? " index " + (index + 1) : "") 
							+ " is invalid. Please ensure the time and time-zone starts with a 4-digit time, "
							+ "followed by a '+' or a '-', and finally, a 4-digit time-zone. "
							+ "The invalid time and time-zone portion of the value is " + timeAndTimeZone + ".";
					ContentValidationResult rs = new ContentValidationResult(error,
							ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
					results.add(rs);
				}				
			}
		
		}
		
	}
	
	public void log() {
		
		log.info("Eff Time Low = " + (lowPresent ? low.getValue() : "No Low"));
		log.info("Eff Time High = " + (highPresent ? high.getValue() : "No High"));
		log.info("Eff Time Value = " + (valuePresent ? value.getValue() : "No Value"));
		log.info(" Single Admin = " + singleAdministration);
	}
	
	public String getSingleAdministration() {
		return singleAdministration;
	}

	public void setSingleAdministration(String singleAdministration) {
		this.singleAdministration = singleAdministration;
	}

	public CCDADataElement getLow() {
		return low;
	}

	public void setLow(CCDADataElement l) {
		
		if(l != null)
		{
			this.low = l;
			lowPresent = true;
		}
	}

	public Boolean getLowPresent() {
		return lowPresent;
	}

	public CCDADataElement getHigh() {
		return high;
	}

	public void setHigh(CCDADataElement h) {
		
		if(h != null)
		{
			this.high = h;
			highPresent = true;
		}
	}

	public Boolean getHighPresent() {
		return highPresent;
	}

	public CCDADataElement getValue() {
		return value;
	}

	public void setValue(CCDADataElement v) {
		
		if(v != null)
		{
			this.value = v;
			valuePresent = true;
		}
	}

	public Boolean getValuePresent() {
		return valuePresent;
	}

	public CCDAEffTime()
	{
		valuePresent = false;
		lowPresent = false;
		highPresent = false;
	}
}
