import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Test;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.dto.enums.SeverityLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.service.ContentValidatorService;

public class ContentValidatorSVAP2024Test extends ContentValidatorTester {

	private static final String SVAP_2024_SCENARIO_DIRECTORY = TEST_RESOURCES_DIRECTORY + "/svap2024/ref";
	private static HashMap<String, CCDARefModel> refModelHashMap = loadAndParseScenariosAndGetRefModelHashMap(
			SVAP_2024_SCENARIO_DIRECTORY);
	private static ContentValidatorService validator = new ContentValidatorService(refModelHashMap);
	static {
		println();
		println("Keys: " + refModelHashMap.keySet());
		println("Values: " + refModelHashMap.values());
	}

	private static final boolean LOG_RESULTS_TO_CONSOLE = true;

	private static final String S = "svap2024/sub/";
	private static final String R = "svap2024/ref/";

	private static final String B1_TOC_AMB_VALIDATION_OBJECTIVE = "170.315_b1_ToC_Amb";
	
	private static final String REF_TEST_DELETE = "170.315_b1_toc_amb_svap_uscdiv4_sample1.xml";

	private static final int SUB_EF = 0;

	private static URI[] SUBMITTED_CCDA = new URI[0];
	private static URI[] SUBMITTED_CCDA_1 = new URI[0];
	static {
		try {
			SUBMITTED_CCDA = new URI[] {
					ContentValidatorSVAP2024Test.class.getResource(S + "170.315_b1_toc_amb_svap_uscdiv4_sample1_submitted.xml").toURI(), // 0
			};
			SUBMITTED_CCDA_1 = new URI[] {
					ContentValidatorSVAP2024Test.class.getResource(S + "170.315_b1_toc_amb_svap_uscdiv4_sample1_submitted_1.xml").toURI(), // 0
			};
		} catch (URISyntaxException e) {
			if (LOG_RESULTS_TO_CONSOLE)
				e.printStackTrace();
		}
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2023(String referenceFileName,
			String ccdaFileAsString) {
		return validateDocumentAndReturnResultsSvap2024(B1_TOC_AMB_VALIDATION_OBJECTIVE, referenceFileName,
				ccdaFileAsString);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2024(String validationObjective,
			String referenceFileName, String ccdaFileAsString) {
		return validateDocumentAndReturnResultsSvap2024(validationObjective, referenceFileName, ccdaFileAsString,
				SeverityLevel.INFO);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2024(String validationObjective,
			String referenceFileName, String ccdaFileAsString, SeverityLevel severityLevel) {
		return validator.validate(validationObjective, referenceFileName, ccdaFileAsString, "uscdiv4", severityLevel);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2024(String referenceFileName,
			URI ccdaFileURI) {
		return validateDocumentAndReturnResultsSvap2024(B1_TOC_AMB_VALIDATION_OBJECTIVE, referenceFileName,
				ccdaFileURI);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2024(String validationObjective,
			String referenceFileName, URI ccdaFileURI) {
		return validateDocumentAndReturnResultsSvap2024(validationObjective, referenceFileName, ccdaFileURI,
				SeverityLevel.INFO);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2024(String validationObjective,
			String referenceFileName, URI ccdaFileURI, SeverityLevel severityLevel) {
		String ccdaFileAsString = convertCCDAFileToString(ccdaFileURI);
		return validateDocumentAndReturnResultsSvap2024(validationObjective, referenceFileName, ccdaFileAsString,
				severityLevel);
	}

	@Test
	public void svap2024_basicContentValidationTest() {
		printHeader("svap2024_basicContentValidationTest");
		try {
		ArrayList<ContentValidationResult> results = new ArrayList<>();
		
		results = validateDocumentAndReturnResultsSvap2024(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_TEST_DELETE, SUBMITTED_CCDA[SUB_EF],
					SeverityLevel.ERROR);
			printResults(results); 
			 
			results.clear();
			/* results = validateDocumentAndReturnResultsSvap2024(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_TEST_DELETE, SUBMITTED_CCDA_1[SUB_EF],
					SeverityLevel.ERROR);
			printResults(results); */
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
