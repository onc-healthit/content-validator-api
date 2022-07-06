package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;

public class CCDAPQ extends CCDADataElement {
	
	private static Logger log = LoggerFactory.getLogger(CCDAPQ.class.getName());
	private static final double delta = 0.0001;
	
	private String  value;
	private String  units;
	private String  xsiType;
	
	public Boolean compare(CCDAPQ subValue, ArrayList<ContentValidationResult> results, String elementName) {
		
		Boolean exception = false;
		
		Boolean valueComp = false;
		Boolean unitComp = false;
		
		//Compare Values by converting to doubles.
		if( (value != null) && (subValue.getValue() != null) ){
			
			try {
				Double refval = Double.parseDouble(value);
				Double subval = Double.parseDouble(subValue.getValue());
				log.info(" Ref Val = " + refval);
				log.info(" Sub Val = " + subval);
				
				if(Math.abs(refval-subval) < delta) {
					log.info(" Values Match with actual values");
					valueComp = true;
				}
				
			}
			catch(NumberFormatException e) {
				exception = true;
			}
			
		}
		else if ( (value == null) && (subValue.getValue() == null)) {
			log.info(" Values are null and match ");
			valueComp = true;
		}
		
		// Compare units accounting for "unity"
		if( (units != null) && (subValue.getUnits() != null) ){
			
			log.info(" Units = " + units);
			log.info(" Sub Units = " + units);
			if(units.equalsIgnoreCase(subValue.getUnits())) {
				log.info(" Units Match with actual units ");
				unitComp = true;
			}
			else if( (units.equalsIgnoreCase("1") || units.equalsIgnoreCase("")) &&
					 (subValue.getUnits().equalsIgnoreCase("1") || subValue.getUnits().equalsIgnoreCase("")) )
			{
				log.info(" Units Match with default units ");
				unitComp = true;
			}
			
		}
		else if( (units == null) && (subValue.getUnits() == null) ) {
			log.info(" Units are null and match ");
			unitComp = true;
		}
		
		if(!valueComp || !unitComp) {
			log.info(" Value and/or Units did not match ");
			String error = "The " + elementName + " : Value PQ - value = " + ((value != null)?value:"None Specified") 
				       + " and Units = " + ((units != null)?units:"None Specified")
				       + "  , do not match the submitted CCDA : Value PQ - value = " + ((subValue.getValue() != null)?subValue.getValue():"None Specified") 
				       + " , and Units = " + ((subValue.getUnits() != null)?subValue.getUnits():"None Specified") + ".";

			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			return false;
			
		}
				
		return true;
		
		/*
		if( (value != null) && (subValue.getValue() != null) &&
			(units != null) && (subValue.getUnits() != null) &&
			(value.equalsIgnoreCase(subValue.getValue())) && 
			(units.equalsIgnoreCase(subValue.getUnits()))) {
			return true;
		}
		else
		{
			String error = "The " + elementName + " : Value PQ - value = " + ((value != null)?value:"None Specified") 
				       + " and Units = " + ((units != null)?units:"None Specified")
				       + "  , do not match the submitted CCDA : Value PQ - value = " + ((subValue.getValue() != null)?subValue.getValue():"None Specified") 
				       + " , and Units = " + ((subValue.getUnits() != null)?subValue.getUnits():"None Specified") + ".";

			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			return false;
		}*/
		
	}
	
	public Boolean compareWithTolerance(CCDAPQ subValue, ArrayList<ContentValidationResult> results, String elementName, Double tolerancePercentage) {
		
		Boolean exception = false;
		
		Boolean valueComp = false;
		Boolean unitComp = false;
		
		//Compare Values by converting to doubles.
		if( (value != null) && (subValue.getValue() != null) ){
			
			try {
				Double refval = Double.parseDouble(value);
				Double subval = Double.parseDouble(subValue.getValue());
				log.info(" Ref Val = " + refval);
				log.info(" Sub Val = " + subval);
				
				Double toleranceLimit = refval * tolerancePercentage;
				
				if(Math.abs(refval-subval) < toleranceLimit) {
					log.info(" Values Match with actual values");
					valueComp = true;
				}
				
			}
			catch(NumberFormatException e) {
				exception = true;
			}
			
		}
		else if ( (value == null) && (subValue.getValue() == null)) {
			log.info(" Values are null and match ");
			valueComp = true;
		}
		
		// Compare units accounting for "unity"
		if( (units != null) && (subValue.getUnits() != null) ){
			
			log.info(" Units = " + units);
			log.info(" Sub Units = " + units);
			if(units.equalsIgnoreCase(subValue.getUnits())) {
				log.info(" Units Match with actual units ");
				unitComp = true;
			}
			else if( (units.equalsIgnoreCase("1") || units.equalsIgnoreCase("")) &&
					 (subValue.getUnits().equalsIgnoreCase("1") || subValue.getUnits().equalsIgnoreCase("")) )
			{
				log.info(" Units Match with default units ");
				unitComp = true;
			}
			
		}
		else if( (units == null) && (subValue.getUnits() == null) ) {
			log.info(" Units are null and match ");
			unitComp = true;
		}
		
		if(!valueComp || !unitComp) {
			log.info(" Value and/or Units did not match ");
			String error = "The " + elementName + " : Value PQ - value = " + ((value != null)?value:"None Specified") 
				       + " and Units = " + ((units != null)?units:"None Specified")
				       + "  , do not match the submitted CCDA : Value PQ - value = " + ((subValue.getValue() != null)?subValue.getValue():"None Specified") 
				       + " , and Units = " + ((subValue.getUnits() != null)?subValue.getUnits():"None Specified") + ".";
			
			error += " If the CEHRT vendor believes the submitted C-CDA values are accurate, please work with your ATL to verify the values submitted explaining the reason for not matching.";

			ContentValidationResult rs = new ContentValidationResult(error, ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
			return false;
			
		}
				
		return true;
		
	}
	
	public void log() {
		
		log.info(" Value = " + value);
		log.info(" Units = " + units);
		log.info(" xsiType = " + xsiType);
	}
	
	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public String getUnits() {
		return units;
	}



	public void setUnits(String units) {
		this.units = units;
	}

	public CCDAPQ(String value)
	{
		this.value = value;
		this.xsiType = "";
		this.units = "";
	}
	
	public CCDAPQ(String value, String xsiType)
	{
	  this.value = value;
	  this.xsiType = xsiType;
	  this.units = "";
	}
	
	public String getXsiType() {
		return xsiType;
	}



	public void setXsiType(String xsiType) {
		this.xsiType = xsiType;
	}



	public CCDAPQ()
	{
		value = "";
		units = "";
		xsiType = "";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((units == null) ? 0 : units.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((xsiType == null) ? 0 : xsiType.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CCDAPQ other = (CCDAPQ) obj;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (xsiType == null) {
			if (other.xsiType != null)
				return false;
		} else if (!xsiType.equals(other.xsiType))
			return false;
		return true;
	}
}

