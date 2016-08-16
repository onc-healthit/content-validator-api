package org.sitenv.contentvalidator.service.utils;

import org.apache.log4j.Logger;
import org.sitenv.contentvalidator.model.CCDARefModel;

import java.util.HashMap;
import java.util.Set;

public class ReferenceContent {
	private static final Logger log = Logger.getLogger(ReferenceContent.class);
	private HashMap<String, CCDARefModel>   referenceModels;
	
	public CCDARefModel getCCDARefModel(String scenarioName) {
		Set<String> keys = referenceModels.keySet();
		
		for (String s : keys) {
		    
			log.info("Comparing " + scenarioName + " to " + s);
			if(scenarioName.contains(s)) {
				log.info("Returning Content Model for Comparison " + s );
				return referenceModels.get(s);
			}
		}
		return null;
	}
	
	public void addCCDARefModel(String scenario, CCDARefModel m) {
		referenceModels.put(scenario,  m);
	}
}
