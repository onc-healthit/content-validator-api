import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.sitenv.contentvalidator.configuration.ScenarioLoader;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.parsers.CCDAParser;

public class ContentValidatorTester {

	public static final boolean LOG_RESULTS_TO_CONSOLE = true;
	
	public static final String TEST_RESOURCES_DIRECTORY = "src/test/resources";

	public static void println() {
		if (LOG_RESULTS_TO_CONSOLE)
			System.out.println();
	}

	public static void println(String message) {
		print(message);
		println();
	}

	public static void print(String message) {
		if (LOG_RESULTS_TO_CONSOLE)
			System.out.print(message);
	}

	public void printHeader(String title) {
		println();
		println("------------------------RUNNING TEST-------------------------");
		println("********************" + title + "********************");
		println();
	}

	public String convertCCDAFileToString(URI ccdaFileURI) {
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
				if (LOG_RESULTS_TO_CONSOLE)
					e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
//
//	public static ScenarioLoader setupAndReturnScenarioLoader(CCDAParser ccdaParser, String scenarioDirectory) {
//		ScenarioLoader scenarioLoader;
//		File file = new File(scenarioDirectory);
//		String scenarioDir = file.getAbsolutePath();
//		scenarioLoader = new ScenarioLoader();
//		scenarioLoader.setScenarioFilePath(scenarioDir);
//		scenarioLoader.setCcdaParser(ccdaParser);
//		return scenarioLoader;
//	}
//
	
//	public static void setupScenariosAndService(final String scenarioDirectory,
//			HashMap<String, CCDARefModel> refModelHashMap, ContentValidatorService validator) {
//		refModelHashMap = loadAndParseScenariosAndGetRefModelHashMap(scenarioDirectory);
//		validator = new ContentValidatorService(refModelHashMap);
//		println();
//		println("Keys: " + refModelHashMap.keySet());
//		println("Values: " + refModelHashMap.values());
//	}
	
	public static HashMap<String, CCDARefModel> loadAndParseScenariosAndGetRefModelHashMap(String scenarioDirectory) {
		ScenarioLoader scenarioLoader = setupAndReturnScenarioLoader(new CCDAParser(), scenarioDirectory);
		try {
			scenarioLoader.afterPropertiesSet(); // we manually set the props and dir...so this manually kicks off loading the scenarios
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scenarioLoader.getRefModelHashMap();
	}
	
	public static ScenarioLoader setupAndReturnScenarioLoader(CCDAParser ccdaParser, String scenarioDirectory) {
		ScenarioLoader scenarioLoader;
		File file = new File(scenarioDirectory);
		String scenarioDir = file.getAbsolutePath();
		scenarioLoader = new ScenarioLoader();
		scenarioLoader.setScenarioFilePath(scenarioDir);
		scenarioLoader.setCcdaParser(ccdaParser);
		return scenarioLoader;
	}	

	public void printResults(List<ContentValidationResult> results) {
		int resultIndex = 1;
		for (ContentValidationResult curResult : results) {
			println("Result #" + resultIndex);
			println("Severity: "
					+ (curResult.getContentValidationResultLevel() != null ? curResult.getContentValidationResultLevel()
							: "null"));
			println("Line Number: " + (curResult.getLineNumber() != null ? curResult.getLineNumber() : "null"));
			println("Message: " + (curResult.getMessage() != null ? curResult.getMessage() : "null"));
			println("XPath: " + (curResult.getXpath() != null ? curResult.getXpath() : "null"));
			resultIndex++;
		}
	}

	public boolean resultsContainMessage(String searchString, List<ContentValidationResult> results) {
		return resultsContainMessage(searchString, results, null);
	}

	public boolean resultsContainMessage(String searchString, List<ContentValidationResult> results,
			ContentValidationResultLevel expectedSeverity) {
		for (ContentValidationResult curResult : results) {
			if (curResult.getMessage().contains(searchString)) {
				return resultContainsSeverity(expectedSeverity, curResult);
			}
		}
		return false;
	}

	public boolean resultContainsSeverity(ContentValidationResultLevel expectedSeverity,
			ContentValidationResult curResult) {
		if (expectedSeverity != null && curResult.getContentValidationResultLevel() == expectedSeverity) {
			return true;
		}
		return false;
	}

	public boolean resultsContainSeverity(List<ContentValidationResult> results,
			ContentValidationResultLevel expectedSeverity) {
		boolean isExpectedSeverity = false;
		for (ContentValidationResult curResult : results) {
			isExpectedSeverity = resultContainsSeverity(expectedSeverity, curResult);
			if (isExpectedSeverity) {
				return true;
			}
		}
		return false;
	}

	public void severityLevelLimitTestHelperAssertMessageAndSeverity(ArrayList<ContentValidationResult> results,
			String curIssue, ContentValidationResultLevel expectedSeverity) {
		assertTrue(
				"The results do not contain the expected message of: " + curIssue
						+ " or do not have the expected severity of: " + expectedSeverity.name(),
				resultsContainMessage(curIssue, results, expectedSeverity));
	}

	public void severityLevelLimitTestHelperAssertSeverityOnly(ArrayList<ContentValidationResult> results,
			ContentValidationResultLevel expectedSeverity) {
		assertTrue("The results do not contain the expected severity of: " + expectedSeverity.name(),
				resultsContainSeverity(results, expectedSeverity));
	}
	
	public void failIfNoResults(ArrayList<ContentValidationResult> results) {
		assertFalse("No results were returned but we expect results", results.isEmpty());
		println("# of ContentValidationResults: " + results.size());
	}

}
