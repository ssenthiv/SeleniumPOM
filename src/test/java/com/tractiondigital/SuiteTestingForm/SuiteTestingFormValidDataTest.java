package com.tractiondigital.SuiteTestingForm;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

//import LearningSeleniumTestNg.demoPOMPage;

@Listeners(com.company1.utility.ScreenshotUtility.class)
public class SuiteTestingFormValidDataTest extends SuiteTestingFormBase {

	@BeforeTest 
	public void checkCaseToRun() throws Exception {
		// This function is to start test with initializing all files and
		// drivers.
		startTest(TestCaseListExcelSuiteTestingForm);
	}

	@Test(dataProvider = "TestDataToRun_Test", description = "This Class is used to Verify SuiteTestingForm InValid Data")
	public void SuiteTestingFormValidDataTestPOM(String FirstName, String LastName, String Email, String MobileNo,
			String Address, String Suburb, String State, String PostCode, String Dob, String TermsAndCondition,
			String Submit) {
		DataSet++;
		Testfail = false;

		// Created object of Testng SoftAssert class.
		s_assert = new SoftAssert();

		// If found DataToRun = "N" for data set then execution will be
		// skipped for that data set.

		if (!TestDataToRun[DataSet].equalsIgnoreCase("Y")) {
			// If DataToRun = "N", Set Testskip=true.
			Testskip = true;
			throw new SkipException(
					"DataToRun for row number " + DataSet + " Is No Or Blank. So Skipping Its Execution.");
		}

		Add_Log.info(TestCaseName + ": ****** Testing Form Submit with Valid Data Test ******");
		try {

			// To navigate to URL.
			driver.get(Param.getProperty("siteURL"));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			// Initialize Page object
			SuiteTestingFormPage TFpage = PageFactory.initElements(driver, SuiteTestingFormPage.class);
			
			
			// Enter FirstName
			TFpage.sendKeysToFirstName(FirstName);

			// Enter LastName
			TFpage.sendKeysToLastName(LastName);

			// Enter Email
			TFpage.sendKeysToEmail(Email);

			// Enter Mobile No
			TFpage.sendKeysToMobileNo(MobileNo);

			// Enter Address
			TFpage.sendKeysToAddress(Address);

			// Enter Suburb
			TFpage.sendKeysToSuburb(Suburb);

			// Enter State
			TFpage.selectState(State);

			// Enter PostCode
			TFpage.sendKeysToPostCode(PostCode);

			// Enter Date of Birth
			TFpage.sendKeysToDob(Dob);

			// Enter Terms and Conditions
			TFpage.clickOnTermsAndCondition(TermsAndCondition);

			// Click on Submit
			TFpage.clickOnSubmit(Submit);

			// Verify Results
			TFpage.verifyTestingFormConfirmation("Thank you for entering the competition.");

			if (Testfail) {
				// This function is to file captured failures in the reports.
				reportFailures("SuiteTestingFormInValidData  --> 'FAIL'");
			} else {
				Add_Log.info(TestCaseName + " --> 'PASS'");
			}

		} catch (Throwable e) {

			e.printStackTrace();
			// This function is to file captured failures in the reports.
			reportFailures("SuiteTestingFormValidData  '" + "'  --> 'FAIL'");
		} finally {

			if (Testfail) {
				// captureScreenShot2("fail");
				s_assert.assertAll();
			}

		}
	}

	// @AfterMethod method will be executed after execution of @Test method
	/**
	 * Reporter data results.
	 */
	// every time.
	@AfterMethod
	public void reporterDataResults() {
		// This function is to report results in Excel file for each test data.
		reportDataResults();

	}

	// Iteration.
	@DataProvider(name = "TestDataToRun_Test")
	public Object[][] TestDataToRun() {
		FilePath = TestCaseListExcelSuiteTestingForm;
		TestCaseName = this.getClass().getSimpleName();

		return FilePath.retrieveTestData(TestCaseName);

	}

	// To report result as pass or fail for test cases In TestCasesList sheet.
	@AfterTest
	public void endTest() {
		// This function is to end test with reporting final result and closing
		// the browser
		testClosure();
	}

}
