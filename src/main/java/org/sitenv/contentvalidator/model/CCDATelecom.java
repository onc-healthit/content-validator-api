package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class CCDATelecom {

	private static Logger log = LoggerFactory.getLogger(CCDATelecom.class.getName());
	
	private String useAttribute;
	private String valueAttribute;

	public void log() {
		log(-1);
	}	
	
	public void log(int index) {
		log.info("*** Logging Telecom" + (index != -1 ? " #" + index : "") + " ****");
		log.info("useAttribute value: " + (useAttribute != null ? useAttribute : "null"));
		log.info("valueAttribute value: " + (valueAttribute != null ? valueAttribute : "null"));
	}
	
	public String getUseAttribute() {
		return useAttribute;
	}
	
	public void setUseAttribute(String useAttribute) {
		this.useAttribute = useAttribute;
	}
	
	public String getValueAttribute() {
		return valueAttribute;
	}
	
	public void setValueAttribute(String valueAttribute) {
		this.valueAttribute = valueAttribute;
	}
	
	private String formatTelecomValue(String value) {
		if(!StringUtils.isEmpty(value) && !value.contains("mailto")) {
			return value.replaceAll("[^0-9+]", "");
		}
		return value;
	}
	
	private boolean isTelecomContainsMailValue(String value) {
		return value!=null && value.contains("mailto");
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CCDATelecom other = (CCDATelecom) obj;
		
		if(!isTelecomContainsMailValue(valueAttribute)) {
			if (useAttribute == null) {
				if (other.useAttribute != null)
					return false;
			} else if (!useAttribute.equalsIgnoreCase(other.useAttribute))
				return false;
		}
		
		if (valueAttribute == null) {
			if (other.valueAttribute != null)
				return false;
		} else if (!formatTelecomValue(valueAttribute).equalsIgnoreCase(formatTelecomValue(other.valueAttribute)))
			return false;
		return true;
	}	

}
