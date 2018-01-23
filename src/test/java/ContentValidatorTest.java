import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.sitenv.contentvalidator.configuration.ScenarioLoader;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.parsers.CCDAParser;
import org.sitenv.contentvalidator.service.ContentValidatorService;

@Ignore
public class ContentValidatorTest {

	private static final boolean LOG_RESULTS_TO_CONSOLE = true;
	
	private static final String DEFAULT_VALIDATION_OBJECTIVE = "170.315_b1_ToC_Amb";
	/*
	 * List of scenarios aka reference files
	 * Note that the key in the map only needs to <i>contain</i> these strings in part of its name. 
	 * This is why extensions or specifics beyond that are not an issue.
	 * 170.315_b4_ccds_create_amb_sample1, 170.315_b1_toc_inp_sample2, 170.315_b4_ccds_create_amb_sample2, 170.315_b1_toc_inp_sample1, 
	 * 170.315_b7_ds4p_inp_sample1, 170.315_b7_ds4p_inp_sample2, 170.315_b4_ccds_create_inp_sample1, 170.315_e1_vdt_inp_sample2, 
	 * 170.315_b2_ciri__r21_sample1_rn, 170.315_e1_vdt_inp_sample1, 170.315_b6_de_inp_sample2, 170.315_b6_de_inp_sample1, 
	 * 170.315_b7_ds4p_amb_sample1, 170.315_b6_de_amb_sample1, 170.315_b7_ds4p_amb_sample2, 170.315_b9_cp_amb_sample1, 
	 * 170.315_b9_cp_inp_sample1, 170.315_b1_toc_amb_sample1, 170.315_g9_api_access_inp_sample2, 170.315_g9_api_access_inp_sample1, 
	 * 170.315_b1_toc_amb_sample2, 170.315_b6_de_amb_sample2, 170.315_b2_ciri__r11_sample1, 170.315_e1_vdt_amb_sample1, 
	 * 170.315_e1_vdt_amb_sample2, 170.315_b2_ciri__r21_sample1_ds, 170.315_b4_ccds_create_inp_sample2, 170.315_g9_api_access_amb_sample2, 
	 * 170.315_b2_ciri__r21_sample1_ccd, 170.315_g9_api_access_amb_sample1
	*/
	private static final String DEFAULT_REFERENCE_FILENAME = "170.315_b1_toc_amb_sample1_v13.pdf";
	private static final String LOCAL_SCENARIO_DIRECTORY = "C:/Programming/SITE/scenarios/";
	
	/**
	 * One example of many related issues:
	 * Expected (170.315_b1_toc_amb_sample1.xml test scenario):
	 * 	<telecom value="tel:+1(555)-777-1234" use="MC"/>
	 * 	<telecom value="tel:+1(555)-723-1544" use="HP"/>
	 * XML: 170.315_b1_toc_amb_ccd_r21_sample1_v10.xml
	 * 	<telecom value="tel:+1(555)-111-1234" use="HP"/> <!-- use is HP instead of MC -dbTest -->
	 * 	<telecom value="tel:+1(555)-112-1544" use="HP"/> <!-- value is 964-4466 instead of 112-1544 -dbTest -->
	*/
	private static final int HAS_TELECOM_MISMATCHES = 0;

	private static URI[] SUBMITTED_CCDA = new URI[0];
	static {
		try {
			SUBMITTED_CCDA = new URI[] {
					ContentValidatorTest.class.getResource("/170.315_b1_toc_amb_sample1_Submitted_T1.xml").toURI(),
			};
		} catch (URISyntaxException e) {
			if(LOG_RESULTS_TO_CONSOLE) e.printStackTrace();
		}
	}
	
	private static final URI DEFAULT_SUBMITTED_CCDA = SUBMITTED_CCDA[HAS_TELECOM_MISMATCHES];
	
	private static void println() {
		if (LOG_RESULTS_TO_CONSOLE) System.out.println();		
	}
	
	private static void println(String message) {
		print(message);
		println();
	}
	
	private static void print(String message) {
		if (LOG_RESULTS_TO_CONSOLE) System.out.print(message);		
	}	
	
	private static String convertCCDAFileToString(URI ccdaFileURI) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(ccdaFileURI.getPath()));
			String sCurrentLine = "";
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}
		} catch (Exception e) {
			println(e.toString());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				if(LOG_RESULTS_TO_CONSOLE) e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	private static ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String referenceFileName, String ccdaFileAsString) {
		return validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, referenceFileName, ccdaFileAsString);
	}
	
	private static ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String validationObjective, 
			String referenceFileName, String ccdaFileAsString) {
		
		HashMap<String, CCDARefModel> refModelHashMap = loadAndParseScenariosAndGetRefModelHashMap();
		println("Keys: " + refModelHashMap.keySet());
		println("Values: " + refModelHashMap.values());
		
		ContentValidatorService validator = new ContentValidatorService(refModelHashMap);
		return validator.validate(validationObjective, referenceFileName, ccdaFileAsString);
	}

	private static ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String referenceFileName, URI ccdaFileURI) {
		return validateDocumentAndReturnResults(DEFAULT_VALIDATION_OBJECTIVE, referenceFileName, ccdaFileURI);
	}
	
	private static ArrayList<ContentValidationResult> validateDocumentAndReturnResults(String validationObjective, 
			String referenceFileName, URI ccdaFileURI) {
		String ccdaFileAsString = convertCCDAFileToString(ccdaFileURI);
		return validateDocumentAndReturnResults(validationObjective, referenceFileName, ccdaFileAsString);
	}
	
    private static ScenarioLoader setupAndReturnScenarioLoader(CCDAParser ccdaParser){
        ScenarioLoader scenarioLoader;
        String scenarioDir = LOCAL_SCENARIO_DIRECTORY;
        scenarioLoader = new ScenarioLoader();
        scenarioLoader.setScenarioFilePath(scenarioDir);
        scenarioLoader.setCcdaParser(ccdaParser);
        return scenarioLoader;
    }
	
    private static HashMap<String, CCDARefModel> loadAndParseScenariosAndGetRefModelHashMap() {
		ScenarioLoader scenarioLoader = setupAndReturnScenarioLoader(new CCDAParser());
		try {
			scenarioLoader.afterPropertiesSet(); //we manually set the props and dir...so this manually kicks off loading the scenarios
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scenarioLoader.getRefModelHashMap();
    }
    
    private static void printResults(List<ContentValidationResult> results) {
    	int resultIndex = 1;
    	for(ContentValidationResult curResult : results) {
    			println("Result #" + resultIndex);
    			println("Severity: " + (curResult.getContentValidationResultLevel() != null 
    					? curResult.getContentValidationResultLevel() : "null"));
    			println("Line Number: " + (curResult.getLineNumber() != null 
    					? curResult.getLineNumber() : "null"));
    			println("Message: " + (curResult.getMessage() != null 
    					? curResult.getMessage() : "null"));
    			println("XPath: " + (curResult.getXpath() != null 
    					? curResult.getXpath() : "null"));
    			resultIndex++;
    	}
    }

    private static boolean resultsContainMessage(String searchString, List<ContentValidationResult> results) {
    	return resultsContainMessage(searchString, results, null);
    }
    
    private static boolean resultsContainMessage(String searchString, List<ContentValidationResult> results, 
    		ContentValidationResultLevel expectedSeverity) {
    	for(ContentValidationResult curResult: results) {
    		if(curResult.getMessage().contains(searchString)) {
    			if(expectedSeverity != null && curResult.getContentValidationResultLevel() == expectedSeverity) {
	        		return true;
    			}
    			return true;
    		}
    	}
    	return false;
    }
    
	@Test
	public void stringConversionAndResultsSizeTest() {
		String ccdaFileAsString = convertCCDAFileToString(DEFAULT_SUBMITTED_CCDA);
		println("submitted ccdaFileAsString: " + ccdaFileAsString);
		assertFalse(
				"The C-CDA file String conversion failed as no data was captured",
				ccdaFileAsString.isEmpty());
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResults(DEFAULT_REFERENCE_FILENAME, ccdaFileAsString);
		assertFalse("No results were returned", results.isEmpty());
		
		println("***************** No Exceptions were thrown during the test******************"
						+ System.lineSeparator() + System.lineSeparator());
	}
	
	@Test
	public void TelecomTest() {
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResults(DEFAULT_REFERENCE_FILENAME, DEFAULT_SUBMITTED_CCDA);
		assertFalse("No results were returned", results.isEmpty());
		println("FINAL RESULTS");
		println("No of Entries = " + results.size());
		
		final String telecomMessage = "Patient Telecom in the submitted file does not match the expected Telecom";
		assertTrue("The results do not contain the expected message of: " + telecomMessage, 
				resultsContainMessage(telecomMessage, results, ContentValidationResultLevel.WARNING));
		
		printResults(results);
	}
	
}
