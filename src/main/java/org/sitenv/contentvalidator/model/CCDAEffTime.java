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
	
	public void compareValueElementWithFullPrecision(CCDAEffTime subTime, ArrayList<ContentValidationResult> results, String elementName) {
		
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
	
	public void compareValueElementEnforceExactDateButOnlyPrecisionForTime(CCDAEffTime subTime,
			ArrayList<ContentValidationResult> results, String elementName) {
		// Enforces the following
		// 1: If the scenario includes a date, then the submitted file must match that date exactly
		// 2: If the scenario also includes a time, then the submitted file must match the precision and format of that time, but not the exact values (reg ex)
		// 3: If the scenario does not include a date, then the submitted file would not need to include a date. Of course, we won't likely have a scenario without a date.
		// 4: If the scenario includes a date only (no time), then the submitted file must match the exact date, 
		// but is allowed to have time as well, which matches any point in time, but also matches the required C-CDA format.
		// 5: If the scenario does not include a date, but the sub does, that is not an error, as more data is acceptable
		// 6: If the scenario contains any value at all, and the sub does not, an error is produced
		// 7: If the scenario includes a time, then the ref must include a time (in general)
		String tempRefTime;
		String tempSubTime;
		log.info(" Comparing Times for " + elementName);

		// Compare Time value element
		if (valuePresent && subTime.getValuePresent()) {

			// check first 8 chars (date) first
			if (value.getValue().length() > 8) {
				// limit comparison to date only, first 8 characters
				tempRefTime = value.getValue().substring(0, 8);
			} else {
				// date is even less specific than the day, so compare what's there
				tempRefTime = value.getValue();
			}

			if (subTime.getValue().getValue().length() > 8) {
				tempSubTime = subTime.getValue().getValue().substring(0, 8);
			} else {
				tempSubTime = subTime.getValue().getValue();
			}

			if (tempRefTime.equalsIgnoreCase(tempSubTime)) {
				log.info("Value Time element matches");
			} else {
				// 1: If the scenario includes a date, then the submitted file must match that date exactly
				// 4: If the scenario includes a date only (no time), then the submitted file must match the exact date
				String error = "The date portion of " + elementName + " (Time: Value ) is " + tempRefTime
						+ " , but the date portion of submitted CCDA (Time: Value ) is " + tempSubTime
						+ " which does not match ";
				ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR,
						"/ClinicalDocument", "0");
				results.add(rs);
			}
			
			// check time (9th char and up)
			if (value.getValue().length() > 8) {
				// ref time has 9 chars or more
				// ref has time so sub needs the full precision which is 4 more digits (time) followed by a + or - followed by 4 more digits (time-zone)
				if (subTime.getValue().getValue().length() < 9) {
					// sub time has 8 chars or less and therefore does not include a time at at all (or time-zone)
					// 7: If the scenario includes a time, then the ref must include a time (in general)
					String error = "The " + elementName + " (Time: Value ) is more precise than the date " + value.getValue()
							+ " , but the submitted CCDA (Time: Value ) does not include time or time-zone data " + subTime.getValue().getValue()
							+ " which does not match ";
					ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR,
							"/ClinicalDocument", "0");
					results.add(rs);					
				} else {
					// sub time has at least 9 chars and is therefore attempting to be more precise than the date level
					// 2: If the scenario also includes a time, then the submitted file must match the precision and format of that time, but not the exact values (reg ex)					
					// Pure un-escaped Reg Ex: /([0-9]{8})([0-9]{4})(-|\+)([0-9]{4})/gm
					// 1st Capturing Group ([0-9])
					//  Match a single character present in the list below [0-9]
					//  {8} matches the previous token exactly 8 times
					//  0-9 matches a single character in the range between 0 (index 48) and 9 (index 57) (case sensitive)
					// 2nd Capturing Group ([0-9])
					//  Match a single character present in the list below [0-9]
					//  {4} matches the previous token exactly 4 times
					//  0-9 matches a single character in the range between 0 (index 48) and 9 (index 57) (case sensitive)
					// 3rd Capturing Group (-|\+)
					//  1st Alternative -
					//  - matches the character - literally (case sensitive)
					//  2nd Alternative \+
					//  \+ matches the character + literally (case sensitive)
					// 4th Capturing Group ([0-9])
					//  Match a single character present in the list below [0-9]
					//  {4} matches the previous token exactly 4 times
					//  0-9 matches a single character in the range between 0 (index 48) and 9 (index 57) (case sensitive)
					// Global pattern flags
					//  g modifier: global. All matches (don't return after first match)
					//  m modifier: multi line. Causes ^ and $ to match the begin/end of each line (not only begin/end of string)
					Pattern pattern = Pattern.compile("([0-9]{8})([0-9]{4})(-|\\+)([0-9]{4})");
					Matcher matcher = pattern.matcher(subTime.getValue().getValue());
					if (matcher.find()) {
						log.info("We have a validly formatted date, time, and timestamp with complete and proper precision");
					} else {
						String error = "The " + elementName + " (Time: Value ) is " + value.getValue()
								+ " , but the submitted CCDA time value " + subTime.getValue().getValue()
								+ " is either not as precise as the scenario or otherwise formatted improperly.";
						ContentValidationResult rs = new ContentValidationResult(error,
								ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
						results.add(rs);						
					}
				}
			}

		} else if (valuePresent && !subTime.getValuePresent()) {
			// 6: If the scenario contains any value at all, and the sub does not, an error is produced
			String error = "The " + elementName
					+ " (value time element ) is required, but submitted CCDA does not contain the (value time element) for "
					+ elementName;
			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR,
					"/ClinicalDocument", "0");
			results.add(rs);			
		} else if (!valuePresent && subTime.getValuePresent()) {
			// 5: If the scenario does not include a date, but the sub does, that is not an error, as more data is acceptable
			log.info("It is OK to have author time even if it is not present in the Ref CCDA");
		} else {
			log.info("Value Time elements absent in both refernce and submitted models ");
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
