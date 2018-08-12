package com.tractiondigital.SuiteTestingForm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import com.company1.TestSuiteBase.SuiteBase;
import com.company1.utility.Read_XLS;
import com.company1.utility.SuiteUtility;

public class SuiteTestingFormBase extends SuiteBase {

	@BeforeSuite
	public void checkSuiteToRun() throws IOException {
		// Called init() function from SuiteBase class to Initialize .xls Files
		init();
		initPageTestingForm();
		
		
		// To set TestSuiteList.xls file's path In FilePath Variable.
		FilePath = TestSuiteListExcel;
		SheetName = "SuitesList";
		SuiteName = "SuiteTestingForm";
		ToRunColumnName = "SuiteToRun";

		// If SuiteToRun !== "y" then SuiteTwo will be skipped from execution.
		if (!SuiteUtility.checkToRunUtility(FilePath, SheetName, ToRunColumnName, SuiteName)) {
			// To report SuiteTwo as 'Skipped' In SuitesList sheet of
			// TestSuiteList.xls If SuiteToRun = N.
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Skipped/Executed", SuiteName, "Skipped");
			// It will throw SkipException to skip test suite's execution and
			// suite will be marked as skipped In testng report.
			throw new SkipException(
					SuiteName + "'s SuiteToRun Flag Is 'N' Or Blank. So Skipping Execution Of " + SuiteName);
		}
		// To report SuiteTwo as 'Executed' In SuitesList sheet of
		// TestSuiteList.xls If SuiteToRun = Y.
		SuiteUtility.WriteResultUtility(FilePath, SheetName, "Skipped/Executed", SuiteName, "Executed");
	}
	
	
	
	/**
	 * 
	 * @param ExpectedResult
	 *            : Expected Text to verify @ Description : This is to verify
	 * whether intended message displayed or not after an action.
	 */
	public void verifyTestingFormConfirmation(String ExpectedResult) throws InterruptedException {
		try {
			// Wait for Confirmation Message display.
			waitUntilElementToBeVisible(getElementByXPath("TestingForm_ThankU"), 5);

			// Check for Thank You Message

			String actualMsg = getElementByXPath("TestingForm_ThankU").getText();
			if (actualMsg.equalsIgnoreCase(ExpectedResult)) {
				Add_Log.info(TestCaseName + ":- Testing Form confirmation  actual: '" + actualMsg
						+ "' matched with expected '" + ExpectedResult + "'");

			} else {
				// This function is to file captured failures in the reports.
				reportFailures(TestCaseName + ":- Testing Form confirmation Message actual: '" + actualMsg
						+ "' is not matched with expected '" + ExpectedResult + "'");
			}

		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Excpetion thrown during Verification of Testing Form confirmation Message");
		}
	}

	public void verifyTestingFormSubmission(String element) throws InterruptedException {
		try {
			// Wait for Submit Button

			driver.findElement(By.id(Object.getProperty(element)));

			Add_Log.info(TestCaseName + ":- Testing Form is not submitted as it is negative scenario  ");

		} catch (Exception e) {

			// This function is to file captured failures in the reports.
			reportFailures(TestCaseName + ":- Testing Form is unexpectedly submitted");

		}
	}

}
