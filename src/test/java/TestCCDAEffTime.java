import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.model.CCDADataElement;
import org.sitenv.contentvalidator.model.CCDAEffTime;

public class TestCCDAEffTime {



	static void printCCDAEffTime(String description,CCDAEffTime ccdaEffTime) {
		ContentValidatorTester.println();
		ContentValidatorTester.println(description + "CCDAEffTime Value");
		if (ccdaEffTime.getValuePresent()) {
			ContentValidatorTester.println("@value : " + ccdaEffTime.getValue().getValue());
		}
		if (ccdaEffTime.getLowPresent()) {
			ContentValidatorTester.println("low/@value : " + ccdaEffTime.getLow().getValue());
		}
		if (ccdaEffTime.getHighPresent()) {
			ContentValidatorTester.println("high/@value : " + ccdaEffTime.getHigh().getValue());
		}
		ContentValidatorTester.println();
	}

	static void printResult(String testName,ArrayList<ContentValidationResult> results ) {
		if (results.isEmpty()) {
			ContentValidatorTester.println(testName+" No Results Returned");
		} else {
			for (ContentValidationResult result : results) {
				ContentValidatorTester.println(testName + " " + result.getMessage());
			}

		}


	}

	@Test
	public void testCCDAEffTimeComparison() {

		CCDAEffTime ref = new CCDAEffTime();
		CCDADataElement refValue = new CCDADataElement();
		refValue.setValue("20250611");

		CCDAEffTime submittedWithNoValue = new CCDAEffTime();

		CCDADataElement subLowValue = new CCDADataElement();
		subLowValue.setValue("20250611120000");

		CCDADataElement subHighValue = new CCDADataElement();
		subHighValue.setValue("20250611130000");

		ref.setValue(refValue );
		CCDAEffTime submittedWithValue = new CCDAEffTime();
		submittedWithValue.setValue(refValue);

		CCDAEffTime submittedWithLow = new CCDAEffTime();
		submittedWithLow.setLow(subLowValue);
		CCDAEffTime submittedWithHigh = new CCDAEffTime();
		submittedWithHigh.setHigh(subHighValue);
		CCDAEffTime submittedWithBoth = new CCDAEffTime();
		submittedWithBoth.setLow(subLowValue);
		submittedWithBoth.setHigh(subHighValue);


		ArrayList<ContentValidationResult> results = new ArrayList<>();
		ref.compareValueElement(submittedWithNoValue, results , "testCCDAEffTimeComparison");
		ContentValidatorTester.println("==================");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has No Value " ,submittedWithNoValue);
		printResult("Submit No Value Test",results);
		assertTrue("Submit No Value Failed", !results.isEmpty());

		results.clear();
		ref.compareValueElement(submittedWithValue, results , "testCCDAEffTimeComparison");
		ContentValidatorTester.println("==================");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has Value " ,submittedWithValue);
		printResult("Submit Value Test",results);
		assertTrue("Submit Value Failed", results.isEmpty());


		results.clear();
		ref.compareValueElement(submittedWithLow, results , "testCCDAEffTimeComparison");
		ContentValidatorTester.println("==================");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has Low Value " ,submittedWithLow);
		printResult("Submit With Low Test",results);
		assertTrue("Submit With Low Failed", !results.isEmpty());

		results.clear();
		ref.compareValueElement(submittedWithHigh, results , "testCCDAEffTimeComparison");
		ContentValidatorTester.println("==================");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has High" ,submittedWithHigh);
		printResult("Submit With High Test",results);
		assertTrue("Submit With High Failed", results.isEmpty());


		results.clear();
		ref.compareValueElement(submittedWithBoth, results , "testCCDAEffTimeComparison");
		ContentValidatorTester.println("==================");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has Both" ,submittedWithBoth);
		printResult("Submit With Both Test",results);
		assertTrue("Submit With Both Failed", results.isEmpty());

		CCDADataElement wrongValue = new CCDADataElement();
		wrongValue.setValue("1999");


		submittedWithValue.setValue(wrongValue);
		ref.compareValueElement(submittedWithValue, results , "testCCDAEffTimeComparison");
		ContentValidatorTester.println("==================");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has Wrong Value " ,submittedWithValue);
		printResult("Submit With Value Test",results);
		assertTrue("Submit Value Failed", !results.isEmpty());

		submittedWithLow.setLow(wrongValue);
		results.clear();
		ref.compareValueElement(submittedWithLow, results , "testCCDAEffTimeComparison");
		ContentValidatorTester.println("==================");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has Wrong Low Value " ,submittedWithLow);
		printResult("Submit With Wrong Low Test",results);
		assertTrue("Submit With Low Failed", !results.isEmpty());

		submittedWithHigh.setHigh(wrongValue);
		results.clear();
		ref.compareValueElement(submittedWithHigh, results , "testCCDAEffTimeComparison");
		ContentValidatorTester.println("==================");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has Wrong High Value " ,submittedWithHigh);
		printResult("Submit With Wrong High Test",results);
		assertTrue("Submit With High Failed", !results.isEmpty());

		submittedWithBoth.setLow(wrongValue);
		results.clear();
		ref.compareValueElement(submittedWithBoth, results , "testCCDAEffTimeComparison");
		ContentValidatorTester.println("==================");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has Wrong Low Value " ,submittedWithBoth);
		printResult("Submit With Wrong Low Test",results);
		assertTrue("Submit With Wrong Low Failed", !results.isEmpty());

		submittedWithBoth.setLow(refValue);
		submittedWithBoth.setHigh(wrongValue);

		results.clear();
		ref.compareValueElement(submittedWithBoth, results , "testCCDAEffTimeComparison");
		ContentValidatorTester.println("==================");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has Wrong  High " ,submittedWithBoth);
		printResult("Submit With Wrong High Test",results);
		assertTrue("Submit With Wrong High Failed", !results.isEmpty());



		submittedWithBoth.setLow(wrongValue);
		results.clear();
		ref.compareValueElement(submittedWithBoth, results , "testCCDAEffTimeComparison");
		printCCDAEffTime("Reference" ,ref);
		printCCDAEffTime("Submit Has Wrong Low and High " ,submittedWithBoth);
		printResult("Submit With Wrong Both Test",results);
		assertTrue("Submit With Wrong  Both Failed", !results.isEmpty());






	}

}
