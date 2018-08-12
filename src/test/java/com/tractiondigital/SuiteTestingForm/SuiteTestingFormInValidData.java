package com.tractiondigital.SuiteTestingForm;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Listeners(com.company1.utility.ScreenshotUtility.class)
public class SuiteTestingFormInValidData extends SuiteTestingFormBase {

	@BeforeTest 
	public void checkCaseToRun() throws Exception {
		// This function is to start test with initializing all files and
		// drivers.
		startTest(TestCaseListExcelSuiteTestingForm);
	}

	@Test(dataProvider = "TestDataToRun_Test", description = "This Class is used to Verify SuiteTestingForm InValid Data")
	public void SuiteTestingFormInValidDataTest(String FirstName, String LastName, String Email, String MobileNo,
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
			//driver.get(Param.getProperty("siteURL"));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// Enter FirstName

			waitUntilElementToBeClickable(getElementByID("TestingForm_FirstName"), 10);
			getElementByID("TestingForm_FirstName").clear();
			getElementByID("TestingForm_FirstName").sendKeys(FirstName);

			Add_Log.info(TestCaseName + ":- Entered  'FirstName'");

			// Enter LastName

			waitUntilElementToBeClickable(getElementByID("TestingForm_LastName"), 10);
			getElementByID("TestingForm_LastName").clear();
			getElementByID("TestingForm_LastName").sendKeys(LastName);

			Add_Log.info(TestCaseName + ":- Entered  'LastName'");

			// Enter Email

			waitUntilElementToBeClickable(getElementByXPath("TestingForm_Email"), 10);
			getElementByXPath("TestingForm_Email").clear();
			getElementByXPath("TestingForm_Email").sendKeys(Email);

			Add_Log.info(TestCaseName + ":- Entered  'Email'");

			// Enter Mobile No

			waitUntilElementToBeClickable(getElementByID("TestingForm_MobileNo"), 10);
			getElementByID("TestingForm_MobileNo").clear();
			getElementByID("TestingForm_MobileNo").sendKeys(MobileNo);

			Add_Log.info(TestCaseName + ":- Entered  'Mobile No'");

			// Enter Address

			waitUntilElementToBeClickable(getElementByName("TestingForm_Address"), 10);
			getElementByName("TestingForm_Address").clear();
			getElementByName("TestingForm_Address").sendKeys(Address);

			Add_Log.info(TestCaseName + ":- Entered  'Address'");

			// Enter Suburb

			waitUntilElementToBeClickable(getElementByName("TestingForm_Suburb"), 10);
			getElementByName("TestingForm_Suburb").clear();
			getElementByName("TestingForm_Suburb").sendKeys(Suburb);

			Add_Log.info(TestCaseName + ":- Entered  'Suburb'");

			// Enter State

			waitUntilElementToBeClickable(getElementByID("TestingForm_State"), 10);
			Select selectState = new Select(getElementByID("TestingForm_State"));
			selectState.selectByVisibleText(State);

			Add_Log.info(TestCaseName + ":- Entered  'State'");

			// Enter PostCode

			waitUntilElementToBeClickable(getElementByID("TestingForm_PostCode"), 10);
			getElementByID("TestingForm_PostCode").clear();
			getElementByID("TestingForm_PostCode").sendKeys(PostCode);

			Add_Log.info(TestCaseName + ":- Entered  'Post Code'");

			// Enter Date of Birth

			waitUntilElementToBeClickable(getElementByID("TestingForm_Dob"), 10);
			getElementByID("TestingForm_Dob").clear();
			getElementByID("TestingForm_Dob").sendKeys(Dob);

			Add_Log.info(TestCaseName + ":- Entered  'Date of Birth'");

			// Enter Terms and Conditions

			waitUntilElementToBeClickable(getElementByID("TestingForm_TermsAndCondition"), 10);
			System.out
					.println(" Is Terms & Condition = " + getElementByID("TestingForm_TermsAndCondition").isSelected());
			if ((TermsAndCondition.equals("Y")) && (!getElementByID("TestingForm_TermsAndCondition").isSelected())) {

				getElementByID("TestingForm_TermsAndCondition").click();

				Add_Log.info(TestCaseName + ":- Clicked on  'Terms and Condition'");
			}
			// Click on Submit
			if (Submit.equals("Y")) {
				waitUntilElementToBeClickable(getElementByID("TestingForm_SubmitButton"), 10);
				getElementByID("TestingForm_SubmitButton").click();

				Add_Log.info(TestCaseName + ":- Clicked on  'Submit Button'");
			}

			// Verify Results

			verifyTestingFormSubmission("TestingForm_SubmitButton");

			Add_Log.info(TestCaseName + ":- Verified Testing Form Status  ");

			if (Testfail) {
				// This function is to file captured failures in the reports.
				reportFailures("SuiteTestingFormInValidData  --> 'FAIL'");
			} else {
				Add_Log.info(TestCaseName + " --> 'PASS'");
			}

		} catch (Throwable e) {

			e.printStackTrace();
			// This function is to file captured failures in the reports.
			reportFailures("SuiteTestingFormInValidData  '" + "'  --> 'FAIL'");
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
