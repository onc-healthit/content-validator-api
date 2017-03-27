package org.sitenv.contentvalidator.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class CCDAProcedure {
	
	private static Logger log = Logger.getLogger(CCDAProcedure.class.getName());
	
	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		sectionCode;
	private ArrayList<CCDAProcActProc>		procActsProcs;
	
	public HashMap<String, CCDAProcActProc> getProcedureMap() {
		
		HashMap<String, CCDAProcActProc> results = new HashMap<String, CCDAProcActProc>();
		
		for(int k = 0; k < procActsProcs.size(); k++) {
			
			log.info(" Iterating through procedures ");
			if(procActsProcs.get(k).getProcCode() != null && 
					procActsProcs.get(k).getProcCode().getCode() != null) {

				String code = procActsProcs.get(k).getProcCode().getCode();
				log.info("Adding procedure code = " + code);
				results.put(code, procActsProcs.get(k));
			}	
		}// for
		
		return results;
	}

	
	
	public void log() {
		
		if(sectionCode != null)
			log.info(" Procedure Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < sectionTemplateId.size(); j++) {
			log.info(" Tempalte Id [" + j + "] = " + sectionTemplateId.get(j).getRootValue());
			log.info(" Tempalte Id Ext [" + j + "] = " + sectionTemplateId.get(j).getExtValue());
		}
		
		for(int k = 0; k < procActsProcs.size(); k++) {
			procActsProcs.get(k).log();
		}
	}
	
	public CCDAProcedure()
	{
		sectionTemplateId = new ArrayList<CCDAII>();
		procActsProcs = new ArrayList<CCDAProcActProc>();
	}

	public ArrayList<CCDAII> getSectionTemplateId() {
		return sectionTemplateId;
	}

	public void setSectionTemplateId(ArrayList<CCDAII> ids) {
		
		if(ids != null)
			this.sectionTemplateId = ids;
	}

	public CCDACode getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(CCDACode sectionCode) {
		this.sectionCode = sectionCode;
	}

	public ArrayList<CCDAProcActProc> getProcActsProcs() {
		return procActsProcs;
	}

	public void setProcActsProcs(ArrayList<CCDAProcActProc> paps) {
		
		if(paps != null)
			this.procActsProcs = paps;
	}
	
	public void addProcActsProcs(ArrayList<CCDAProcActProc> paps) {
		
		if(paps != null) {
			for(int i = 0; i < paps.size(); i++) {
				this.procActsProcs.add(paps.get(i));
			}
		}
	}
	
}
