package org.sitenv.contentvalidator.model;
 
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class CCDAMedicalEquipment {

	private static Logger log = Logger.getLogger(CCDAMedicalEquipment.class.getName());

	private ArrayList<CCDAII>       		sectionTemplateId;
	private CCDACode                 		sectionCode;
	private ArrayList<CCDAUDI>			    udis;

	public void log() {
		
		log.info("***Medical Equipment ***");
		
		for(int i = 0; i < sectionTemplateId.size(); i++) {
			log.info(" Tempalte Id [" + i + "] = " + sectionTemplateId.get(i).getRootValue());
			log.info(" Tempalte Id Ext [" + i + "] = " + sectionTemplateId.get(i).getExtValue());
		}
		
		if(sectionCode != null)
			log.info("Section Code = " + sectionCode.getCode());
		
		for(int j = 0; j < udis.size(); j++) {
			udis.get(j).log();
		}
		
	}
	
	public CCDAMedicalEquipment()
	{
		sectionTemplateId = new ArrayList<CCDAII>();
		udis = new ArrayList<CCDAUDI>();
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

	public ArrayList<CCDAUDI> getUdis() {
		return udis;
	}

	public void setUDIs(ArrayList<CCDAUDI> udis) {

		if(udis != null)
			this.udis = udis;
	}

	public void addUDIs(ArrayList<CCDAUDI> udis) {
		
		if(udis != null){
			for(int i = 0; i < udis.size(); i++) {
				this.udis.add(udis.get(i));
			}
		}
	}

}
