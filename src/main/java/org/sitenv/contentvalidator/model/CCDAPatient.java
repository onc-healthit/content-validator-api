package org.sitenv.contentvalidator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;

import java.util.ArrayList;

public class CCDAPatient {
	
	private static Logger log = LoggerFactory.getLogger(CCDAPatient.class.getName());
	
	private CCDADataElement firstName;
	private CCDADataElement lastName;
	private CCDADataElement middleName;
	private CCDADataElement previousName;
	private CCDAEffTime 	previousNamePeriod;
	private CCDADataElement suffix;
	private CCDADataElement dob;
	private CCDADataElement deathDate;
	private ArrayList<CCDAAddress> addresses;
	private CCDAAddress     birthPlace;
	private ArrayList<CCDACode> raceCodes;
	private ArrayList<CCDACode> raceCodeExt;
	private CCDACode ethnicity;
	private CCDACode sex;
	private ArrayList<CCDATelecom> telecom;
	private CCDACode                   adminGender;
	private CCDACode                   maritalStatus;
	private CCDACode                   religiousAffiliation;
	private ArrayList<CCDAPreferredLanguage>          languageCommunication;
	private ArrayList<CCDARelatedPerson> 				relatedPersons;
	
	public void log() {
		
		log.info("*** Logging Patient Details ****");
		log.info("First Name = " + (firstName==null ? "No Data" : firstName.getValue()));
		log.info("Middle Name = " + (middleName==null ? "No Data" : middleName.getValue()));
		log.info("Last Name = " + (lastName==null ? "No Data" : lastName.getValue()));
		log.info("Previous Name = " + (previousName==null ? "No Data" : previousName.getValue()));
		log.info("Suffix = " + (suffix==null ? "No Data" : suffix.getValue()));
		log.info("Date of Birth " + (dob==null ? "No Data" : dob.getValue()));
		
		for(int i = 0; i < addresses.size(); i++) {
			addresses.get(i).log();
		}
		
		if(birthPlace != null)
		{
			log.info("Birth Place Address ");
			birthPlace.log();
		}
		else
		{
			log.info("Birth Place Address : No Data");
		}
		
		for(int j = 0; j < raceCodes.size(); j++) {
			log.info(" Race Code [" + j + "] = " + raceCodes.get(j).getCode());
		}
		
		for(int k = 0; k < raceCodeExt.size(); k++) {
			log.info(" Race Code Ext [" + k + "] = " + raceCodeExt.get(k).getCode());
		}
		
		if(ethnicity != null) {
			log.info(" Ethnicity : " + ethnicity.getCode());
		}
		
		if(sex != null) {
			log.info(" Sex : " + sex.getValue());
		}
		
		for(int l = 0; l < telecom.size(); l++) {
			telecom.get(l).log(l);
		}
		
		if(adminGender != null) {
			log.info(" Admin Gender " + adminGender.getCode());
		}
		
		if(maritalStatus != null) {
			log.info(" Marital Status " + maritalStatus.getCode());
		}
		
		if(religiousAffiliation != null) {
			log.info(" Religious Affiliation " + religiousAffiliation.getCode());
		}
		
		for(int m = 0; m < languageCommunication.size(); m++) {
			log.info(" Language Communication [" + m + "] = " );
			languageCommunication.get(m).log();
		}
		
		for(int n = 0; n < relatedPersons.size(); n++) {
			relatedPersons.get(n).log();
		}
	}
	
	public CCDACode getReligiousAffiliation() {
		return religiousAffiliation;
	}

	public void setReligiousAffiliation(CCDACode religiousAffiliation) {
		this.religiousAffiliation = religiousAffiliation;
	}

	public CCDACode getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(CCDACode maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public ArrayList<CCDATelecom> getTelecom() {
		return telecom;
	}

	public void setTelecom(ArrayList<CCDATelecom> tels) {
		if(tels != null)
			this.telecom = tels;
	}

	public CCDACode getAdminGender() {
		return adminGender;
	}

	public void setAdminGender(CCDACode adminGender) {
		this.adminGender = adminGender;
	}

	public CCDADataElement getFirstName() {
		return firstName;
	}

	public void setFirstName(CCDADataElement firstName) {
		this.firstName = firstName;
	}

	public CCDADataElement getLastName() {
		return lastName;
	}

	public void setLastName(CCDADataElement lastName) {
		this.lastName = lastName;
	}

	public CCDADataElement getMiddleName() {
		return middleName;
	}

	public void setMiddleName(CCDADataElement middleName) {
		this.middleName = middleName;
	}

	public CCDADataElement getPreviousName() {
		return previousName;
	}

	public void setPreviousName(CCDADataElement previousName) {
		this.previousName = previousName;
	}

	public CCDADataElement getSuffix() {
		return suffix;
	}

	public void setSuffix(CCDADataElement suffix) {
		this.suffix = suffix;
	}

	public CCDADataElement getDob() {
		return dob;
	}

	public void setDob(CCDADataElement dob) {
		this.dob = dob;
	}

	public ArrayList<CCDAAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(ArrayList<CCDAAddress> addr) {
		if(addr != null)
			this.addresses = addr;
	}

	public ArrayList<CCDAPreferredLanguage> getPreferredLanguage() {
		return languageCommunication;
	}

	public void addPreferredLanguage(CCDAPreferredLanguage language) {
		this.languageCommunication.add(language);
	}

	public ArrayList<CCDACode> getRaceCodes() {
		return raceCodes;
	}

	public ArrayList<CCDACode> getRaceCodeExt() {
		return raceCodeExt;
	}

	public void setRaceCodeExt(ArrayList<CCDACode> rext) {
		
		if(rext != null)
			this.raceCodeExt = rext;
	}

	public void setRaceCodes(ArrayList<CCDACode> rc) {
		
		if(rc != null)
			this.raceCodes = rc;
	}
	
	public void addRaceCode(CCDACode raceCode) {
		raceCodes.add(raceCode);
	}
	
	public void addRaceCodeExt(CCDACode raceCode) {
		raceCodeExt.add(raceCode);
	}

	public CCDACode getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(CCDACode ethnicity) {
		this.ethnicity = ethnicity;
	}

	public CCDACode getSex() {
		return sex;
	}

	public void setSex(CCDACode sex) {
		this.sex = sex;
	}

	public CCDAEffTime getPreviousNamePeriod() {
		return previousNamePeriod;
	}

	public void setPreviousNamePeriod(CCDAEffTime previousNamePeriod) {
		this.previousNamePeriod = previousNamePeriod;
	}

	public CCDAPatient()
	{
		addresses = new ArrayList<CCDAAddress>();
		raceCodes = new ArrayList<CCDACode>();
		raceCodeExt = new ArrayList<CCDACode>();
		telecom = new ArrayList<CCDATelecom>();
		languageCommunication = new ArrayList<CCDAPreferredLanguage>();
		relatedPersons = new ArrayList<>();
	}

	public ArrayList<CCDAPreferredLanguage> getLanguageCommunication() {
		return languageCommunication;
	}

	public void setLanguageCommunication(ArrayList<CCDAPreferredLanguage> lc) {
		
		if(lc != null)
			this.languageCommunication = lc;
	}

	public void setBirthPlace(CCDAAddress add) {
		birthPlace = add;
	}
	
	public CCDAAddress getBirthPlace() { 
		return birthPlace;
	}
	
	public Boolean containsRaceCode(CCDACode code) {
		
		if (raceCodes == null)
			return false;
		
		for(CCDACode c : raceCodes) {
			if( (c.getCode().equalsIgnoreCase(code.getCode())) && 
				(c.getCodeSystem().equalsIgnoreCase(code.getCodeSystem())))
				return true;
		}
				
		return false;
		
		
	}

	public Boolean containsRaceCodeExt(CCDACode code) {
		
		if (raceCodeExt == null)
			return false;
		
		for(CCDACode c : raceCodeExt) {
			if( (c.getCode().equalsIgnoreCase(code.getCode())) && 
				(c.getCodeSystem().equalsIgnoreCase(code.getCodeSystem())))
				return true;
		}
				
		return false;
		
	}
	
	
	public Boolean containsLanguage(CCDAPreferredLanguage lang) {
		
		/* Since the reference model has a Preferred language code element 
		 * but the submitted model does not have the element , the comparison fails.
		 * The scenario where the language code element exists in the ref model indicates
		 * that the code element is not null flavor so when languagecommunication is not 
		 * present in the submitted model indicates a failure. 
		 */
		if (lang.getLanguageCode() != null &&
			languageCommunication == null) 
			return false;
		
		for(CCDAPreferredLanguage l : languageCommunication) {
		
			if((l.getLanguageCode() != null) && 
				(lang.getLanguageCode() != null) ) {
		
				// Extract the language part from the preferred language in the ref model.
				log.info("Extracting Patient's Preferred Language");
				String[] language = lang.getLanguageCode().getCode().split("-");
				
				String langPart = null;
				if(language.length > 0)
					langPart = language[0];
				else
					langPart = lang.getLanguageCode().getCode();
				
				if(l.getLanguageCode().getCode().toLowerCase().contains(langPart.toLowerCase())) {
					// Happy Path , Normal Case
					return true;
				}
				else {
					log.info(" Lang Code in submitted CCDA " + l.getLanguageCode().getCode() 
							+ " does not contain the required language string " + langPart );
				}
			}
			else if( l.getLanguageCode() == null && 
					 lang.getLanguageCode() == null )
			{
				log.info(" Patient's language null Flavored in both cases ");
				// Normal case of null Flavor.
				return true;
			}
			else if (lang.getLanguageCode() == null &&
					 l.getLanguageCode() != null ) {
				
				// SUT has more data , so it is all right
				// Do nothing. Cannot make a decision on just one element 
				// in the array, so continue until we hit all the elements and 
				// if we dont hit the positive cases anywhere, it will return false.
				log.warn("SUT including more data elements ");
			}
			else if( lang.getLanguageCode() != null &&
					 l.getLanguageCode() == null ) {
				
				// SUT has a data element which is not right , so it is all right
				// Do nothing. Cannot make a decision on just one element 
				// in the array, so continue until we hit all the elements and 
				// if we dont hit the positive cases anywhere, it will return false.
				log.warn("SUT including bad data elements ");
			}
		}
		
		// Never hit the positive cases, so return false.
		return false;
		
		
	}
	
	public Boolean containsTelecomUseAndValue(CCDATelecom subTelecom) {
		for(CCDATelecom t : telecom) {
			//equals is overridden in CCDATelecom
			if(t.equals(subTelecom)) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean containsTelecomValue(CCDATelecom subTelecom) {				
		if(telecom == null) {
			return false;
		}
		for(CCDATelecom t : telecom) {			
			if(t.getValueAttribute().equalsIgnoreCase(subTelecom.getValueAttribute())) {
				return true;
			}
		}
		return false;
	}	

	public void compare(CCDAPatient patient, ArrayList<ContentValidationResult> results, CCDARefModel submittedCCDA, 
			boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {
	
		compareNames(patient, results, submittedCCDA);
		compareMiscellaneous(patient, results);
		compareRaceAndEthnicity(patient, results);
		compareTelecoms(patient, results, submittedCCDA, curesUpdate, svap2022, svap2023, svap2024);
	}
	
	private void compareRaceAndEthnicity(CCDAPatient patient, ArrayList<ContentValidationResult> results) {
		
		log.info("Comparing Patient's Aggregate Race Code ");
		// Compare Race Code
		for(CCDACode c : raceCodes) {
			if(!patient.containsRaceCode(c))
			{
				String errorMessage = "Patient Race Code = " + c.getCode() 
						+ " expected but, submitted file does not contain the expected race code";
				ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.ERROR,
						"/ClinicalDocument", "0");
				results.add(rs);
			}
		}

		log.info("Comparing Patient's Granular Race Value ");
		// Compare Race Code Ext
		for(CCDACode c : raceCodeExt) {
			if(!patient.containsRaceCodeExt(c))
			{
				String errorMessage = "Patient Granular Race Code = " + c.getCode() 
						+ " expected in sdtc:raceCode extension but, submitted file does not contain the expected granular race code";
				ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.WARNING, 
						"/ClinicalDocument", "0");
				results.add(rs);
			}
		}
		
		log.info("Comparing Patient's Ethnicity");
		// Compare Ethnicity
		if((ethnicity != null) && (patient.getEthnicity() != null) ) {
			if( !(ethnicity.getCode().equalsIgnoreCase(patient.getEthnicity().getCode())))
			{
				String errorMessage = "Patient Ethnicity code Expected = " + ethnicity.getCode() 
				+ " but, submitted file contains ethnicity code of " + patient.getEthnicity().getCode();
				ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.ERROR, 
						"/ClinicalDocument", "0");
				results.add(rs);
			}					
		}
		else if( (ethnicity == null) && (patient.getEthnicity() != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require patient ethnicity information, but submitted file does have patient ethnicity information", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if( (ethnicity != null) && (patient.getEthnicity() == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires patient ethnicity information, but submitted file does not have patient ethnicitiy information", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("Ethnicity information is null in both reference and submitted CCDA models ");
		}
		
		
	}

	private void compareMiscellaneous(CCDAPatient patient, ArrayList<ContentValidationResult> results) {
		
		log.info("Comparing Patient's Date of Birth ");
		// Compare date of birth
		if((dob != null) && (patient.getDob() != null) ) {
			if( !(dob.getValue().equalsIgnoreCase(patient.getDob().getValue())))
			{
				String errorMessage = "Patient Date of Birth Expected = " + dob.getValue() 
				+ " but, submitted file contains Date of Birth of " + patient.getDob().getValue();
				ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.ERROR, 
						"/ClinicalDocument", "0");
				results.add(rs);
			}					
		}
		else if( (dob == null) && (patient.getDob() != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require patient date of birth information, but submitted file does have patient date of birth information", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if( (dob != null) && (patient.getDob() == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires patient date of birth information, but submitted file does not have patient date of birth information", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("Both submitted and reference models have a null dob ");
		}
		
		log.info("Comparing Patient's Preferred Language ");
			
		// Compare Preferred Language
		for(CCDAPreferredLanguage lang : languageCommunication) {
			if(!patient.containsLanguage(lang))
			{
				String errorMessage = "Patient Language = " + lang.getLanguageCode().getCode() 
				+ " expected but, submitted file does not contain the expected language code";
				ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.ERROR, 
						"/ClinicalDocument", "0");
				results.add(rs);
			}
		}
		
		// A case where the scenario does not require language and is nullFlavored where as the 
		// SUT has data which is not specified should be handled.
		if( languageCommunication != null && 
			languageCommunication.isEmpty() && 
			patient.getLanguageCommunication() != null && 
			!(patient.getLanguageCommunication().isEmpty()) ) {
			
			String errorMessage = "Patient Language not expected, but submitted file contains language code";
					ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.ERROR, 
							"/ClinicalDocument", "0");
					results.add(rs);
		}
		
	}

	private void compareNames(CCDAPatient patient, ArrayList<ContentValidationResult> results, CCDARefModel submittedCCDA) {
		
		log.info("Comparing Patient's First Name ");
		// Compare First Name
		if((firstName != null) && (patient.getFirstName() != null) ) {
			if( !(firstName.getValue().equalsIgnoreCase(patient.getFirstName().getValue())))
			{
				String errorMessage = "Patient First Name Expected = " + firstName.getValue() 
					+ " but, submitted file contains first name of " + patient.getFirstName().getValue();
				ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.ERROR, 
						"/ClinicalDocument", "0");
				results.add(rs);
			}					
		}
		else if( (firstName == null) && (patient.getFirstName() != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require patient first name, but submitted file does have patient first name", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if( (firstName != null) && (patient.getFirstName() == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires patient first name, but submitted file does not have patient first name", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("Submitted and Reference CCDA models have null first name ");
		}
		
		log.info("Comparing Patient's Last Name ");
		// Compare Last Name
		if((lastName != null) && (patient.getLastName() != null) ) {
			if( !(lastName.getValue().equalsIgnoreCase(patient.getLastName().getValue())))
			{
				String errorMessage = "Patient Last Name Expected = " + lastName.getValue() 
					+ " but, submitted file contains last name of " + patient.getLastName().getValue();
				ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.ERROR, 
						"/ClinicalDocument", "0");
				results.add(rs);
			}					
		}
		else if( (lastName == null) && (patient.getLastName() != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require patient last name, but submitted file does have patient last name", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if( (lastName != null) && (patient.getLastName() == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires patient last name, but submitted file does not have patient last name", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("Submitted and Reference CCDA models have null last name ");
		}
		
		log.info("Comparing Patient's Middle Name ");
		// Compare Middle Name
		if((middleName != null) && (patient.getMiddleName() != null) ) {
			if( !(middleName.getValue().equalsIgnoreCase(patient.getMiddleName().getValue())))
			{
				String errorMessage = "Patient Middle Name Expected = " + middleName.getValue() 
				+ " but, submitted file contains middle name of " + patient.getMiddleName().getValue();
				ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.ERROR, 
						"/ClinicalDocument", "0");
				results.add(rs);
			}					
		}
		else if( (middleName == null) && (patient.getMiddleName() != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require patient middle name, but submitted file does have patient middle name", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if( (middleName != null) && (patient.getMiddleName() == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires patient middle name, but submitted file does not have patient middle name", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("Submitted and Reference CCDA models have null Middle name ");
		}
		
		log.info("Comparing Patient's Previous Name ");
		// Compare Previous Name
		if((previousName != null) && (patient.getPreviousName() != null) ) {
			if( !(previousName.getValue().equalsIgnoreCase(patient.getPreviousName().getValue())))
			{
				String errorMessage = "Patient Previous Name Expected = " + previousName.getValue() 
				+ " but, submitted file contains previous name of " + patient.getPreviousName().getValue();
				ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.ERROR, 
						"/ClinicalDocument", "0");
				results.add(rs);
			}					
		}
		else if( (previousName == null) && (patient.getPreviousName() != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require patient previous name, but submitted file does have patient previous name", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if( (previousName != null) && (patient.getPreviousName() == null)){
			if (submittedCCDA.warningsPermitted()) {				
				// MAKE THIS A WARNING Since it is a best practice and cannot be enforced.
				ContentValidationResult rs = new ContentValidationResult("The scenario requires patient previous name, but submitted file does not have patient previous name", ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0" );
				results.add(rs);
			} else {
				log.info(
						"Skipping 'else if( (previousName != null) && (patient.getPreviousName() == null))' check in CCDAPatient.compareNames due to severityLevel: "
								+ submittedCCDA.getSeverityLevelName());
			}
		}
		else {
			log.info("Submitted and Reference CCDA models have null previous name ");
		}
		
		log.info("Comparing Patient's Suffix");
		// Compare Suffix
		if((suffix != null) && (patient.getSuffix() != null) ) {
			if( !(suffix.getValue().equalsIgnoreCase(patient.getSuffix().getValue())))
			{
				String errorMessage = "Patient Suffix Expected = " + suffix.getValue() 
				+ " but, submitted file contains suffix of " + patient.getSuffix().getValue();
				ContentValidationResult rs = new ContentValidationResult(errorMessage, ContentValidationResultLevel.ERROR, 
						"/ClinicalDocument", "0");
				results.add(rs);
			}					
		}
		else if( (suffix == null) && (patient.getSuffix() != null)) {
			ContentValidationResult rs = new ContentValidationResult("The scenario does not require patient suffix, but submitted file does have patient sufix", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else if( (suffix != null) && (patient.getSuffix() == null)){
			ContentValidationResult rs = new ContentValidationResult("The scenario requires patient suffix, but submitted file does not have patient suffix", ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0" );
			results.add(rs);
		}
		else {
			log.info("Submitted and Reference CCDA models have null patient suffix information ");
		}
	}
	
	private void compareTelecoms(CCDAPatient patient, ArrayList<ContentValidationResult> results,
			CCDARefModel submittedCCDA, boolean curesUpdate, boolean svap2022, boolean svap2023, boolean svap2024) {

		log.info("Comparing Patient's telecom/@use and telecom/@value");
		for (CCDATelecom tel : telecom) {
			if (!patient.containsTelecomUseAndValue(tel)) {
				String message = "Patient Telecom in the submitted file does not match the expected Telecom. "
						+ "The following values are expected: " + "telecom/@use = " + tel.getUseAttribute()
						+ " and telecom/@value = " + tel.getValueAttribute();
				if (curesUpdate) {
					ContentValidationResult rs = new ContentValidationResult(message,
							ContentValidationResultLevel.ERROR, "/ClinicalDocument", "0");
					results.add(rs);
				} else {
					if (submittedCCDA.warningsPermitted()) {
						ContentValidationResult rs = new ContentValidationResult(message,
								ContentValidationResultLevel.WARNING, "/ClinicalDocument", "0");
						results.add(rs);
					} else {
						log.info(
								"Skipping 'Comparing Patient's telecom/@use and telecom/@value' check in "
								+ "CCDAPatient.compareTelecoms due to severityLevel: "
										+ submittedCCDA.getSeverityLevelName());
					}
				}

			}
		}

	}

	public CCDADataElement getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(CCDADataElement deathDate) {
		this.deathDate = deathDate;
	}

	public ArrayList<CCDARelatedPerson> getRelatedPersons() {
		return relatedPersons;
	}

	public void setRelatedPersons(ArrayList<CCDARelatedPerson> relatedPersons) {
		this.relatedPersons = relatedPersons;
	}
	
	
}
