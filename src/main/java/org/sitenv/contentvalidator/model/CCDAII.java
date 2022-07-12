package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCDAII extends CCDADataElement{
	
	private static Logger log = LoggerFactory.getLogger(CCDAII.class.getName());
	
	private String  rootValue;
	private String  extValue;
	
	public CCDAII() {
		
	}
	
	public CCDAII(String rootValue) {
		this.rootValue = rootValue;
	}
	
	public CCDAII(String rootValue, String extValue) {
		this(rootValue);
		this.extValue = extValue;
	}
	
	public Boolean isPartOf(ArrayList<CCDAII> list) {
		
		for( CCDAII item : list) {
			
			//this.log();
			//item.log();
			
			// Both Root and Extensions are present.
			if( (rootValue != null) && (item.getRootValue() != null) && 
				(extValue != null)  && (item.getExtValue() != null) && 
				(rootValue.equalsIgnoreCase(item.getRootValue())) && 
				(extValue.equalsIgnoreCase(item.getExtValue()))) {
				return true;
			}
			// Only Root value are present
			else if( (rootValue != null) && (item.getRootValue() != null) && 
					(extValue == null)  && (item.getExtValue() == null) && 
					(rootValue.equalsIgnoreCase(item.getRootValue()))) {
				return true;
			}
			
			// continue through the list
		}
		
		//if we never hit the postive case
		return false;
	}
	
	public void log() { 
		
		log.info(" *** Intance Identifier *** ");
		
		log.info(" Root : " + rootValue);
		log.info(" Extension : " + extValue);
		
	}
	
	public String getRootValue() {
		return rootValue;
	}

	public void setRootValue(String rootValue) {
		this.rootValue = rootValue;
	}

	public String getExtValue() {
		return extValue;
	}

	public void setExtValue(String extValue) {
		this.extValue = extValue;
	}

}
