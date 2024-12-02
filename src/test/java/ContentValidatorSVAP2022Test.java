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

public class ContentValidatorSVAP2022Test extends ContentValidatorTester {

	private static final String SVAP_2022_SCENARIO_DIRECTORY = TEST_RESOURCES_DIRECTORY + "/svap2022/ref";
	private static HashMap<String, CCDARefModel> refModelHashMap = loadAndParseScenariosAndGetRefModelHashMap(
			SVAP_2022_SCENARIO_DIRECTORY);
	private static ContentValidatorService validator = new ContentValidatorService(refModelHashMap);
	static {
		println();
		println("Keys: " + refModelHashMap.keySet());
		println("Values: " + refModelHashMap.values());
	}

	private static final boolean LOG_RESULTS_TO_CONSOLE = true;

	private static final String S = "svap2022/sub/";
	private static final String R = "svap2022/ref/";

	private static final String B1_TOC_AMB_VALIDATION_OBJECTIVE = "170.315_b1_ToC_Amb";
	
	private static final String REF_TEST_DELETE = "test-delete-and-replace-with-real-final-refs.xml";

	private static final int SUB_EF = 0;

	private static URI[] SUBMITTED_CCDA = new URI[0];
	static {
		try {
			SUBMITTED_CCDA = new URI[] {
					ContentValidatorSVAP2022Test.class.getResource(S + "C-CDA_R2-1_CCD_EF.xml").toURI(), // 0
			};
		} catch (URISyntaxException e) {
			if (LOG_RESULTS_TO_CONSOLE)
				e.printStackTrace();
		}
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2022(String referenceFileName,
			String ccdaFileAsString) {
		return validateDocumentAndReturnResultsSvap2022(B1_TOC_AMB_VALIDATION_OBJECTIVE, referenceFileName,
				ccdaFileAsString);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2022(String validationObjective,
			String referenceFileName, String ccdaFileAsString) {
		return validateDocumentAndReturnResultsSvap2022(validationObjective, referenceFileName, ccdaFileAsString,
				SeverityLevel.INFO);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2022(String validationObjective,
			String referenceFileName, String ccdaFileAsString, SeverityLevel severityLevel) {
		return validator.validate(validationObjective, referenceFileName, ccdaFileAsString, false, true, false, false, severityLevel);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2022(String referenceFileName,
			URI ccdaFileURI) {
		return validateDocumentAndReturnResultsSvap2022(B1_TOC_AMB_VALIDATION_OBJECTIVE, referenceFileName,
				ccdaFileURI);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2022(String validationObjective,
			String referenceFileName, URI ccdaFileURI) {
		return validateDocumentAndReturnResultsSvap2022(validationObjective, referenceFileName, ccdaFileURI,
				SeverityLevel.INFO);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsSvap2022(String validationObjective,
			String referenceFileName, URI ccdaFileURI, SeverityLevel severityLevel) {
		String ccdaFileAsString = convertCCDAFileToString(ccdaFileURI);
		return validateDocumentAndReturnResultsSvap2022(validationObjective, referenceFileName, ccdaFileAsString,
				severityLevel);
	}

	@Test
	public void svap2022_basicContentValidationTest() {
		printHeader("svap2022_basicContentValidationTest");
		try {
			ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsSvap2022(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_TEST_DELETE, SUBMITTED_CCDA[SUB_EF],
					SeverityLevel.ERROR);
			printResults(results);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
