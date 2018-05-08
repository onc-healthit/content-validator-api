package org.sitenv.contentvalidator.model;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class CCDAHeaderElements {
	
	private static Logger log = Logger.getLogger(CCDAHeaderElements.class.getName());

	ArrayList<CCDAII>   docTemplates;
	CCDACode docCode;
	
	public ArrayList<CCDAII> getDocTemplates() {
		return docTemplates;
	}

	public void setDocTemplates(ArrayList<CCDAII> docTemplates) {
		this.docTemplates = docTemplates;
	}

	public CCDACode getDocCode() {
		return docCode;
	}

	public void setDocCode(CCDACode docCode) {
		this.docCode = docCode;
	}

	public CCDAHeaderElements() 
	{
		docTemplates = new ArrayList<CCDAII>();
	}
	
	public void log() {
		
		log.info("*** CCDA Header Elements ***");
		
		if(docCode != null)
			log.info("Doc Type Code = " + docCode.getCode());
		
		if(docTemplates != null) {
			for(int j = 0; j < docTemplates.size(); j++) {
				log.info(" Tempalte Id [" + j + "] = " + docTemplates.get(j).getRootValue());
				log.info(" Tempalte Id Ext [" + j + "] = " + docTemplates.get(j).getExtValue());
			}
		}
	
		
	}

	
	
}
