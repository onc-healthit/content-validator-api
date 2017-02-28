package org.sitenv.contentvalidator.model;

import org.sitenv.contentvalidator.dto.ContentValidationResult;

import java.util.ArrayList;

public class CCDADataElement {

	private String  value;
	private Integer lineNumber;
	private String  xpath;
	private String  use;
	
	public Boolean matches(CCDADataElement cd, ArrayList<ContentValidationResult> results, String elementName) {
		
		if( (value != null) && (cd.getValue() != null) &&
			(value.equalsIgnoreCase(cd.getValue())) ) {
			
			return true;
		}
		else if((value == null) && (cd.getValue() == null))
		{
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void removeSpecialCharacters(String regex, String repl) {
		value = value.replaceAll(regex, repl);
	}
	
	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public CCDADataElement(String value)
	{
		this.value = value;
	}
	
	public CCDADataElement()
	{
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	
	
}
