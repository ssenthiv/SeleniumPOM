/* This is Page class for Testing Form */

package com.tractiondigital.SuiteTestingForm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import com.company1.TestSuiteBase.SuiteBase;
import com.company1.utility.Read_XLS;
import com.company1.utility.SuiteUtility;

public class SuiteTestingFormPage extends SuiteBase {

	WebDriver driver;
	
	public SuiteTestingFormPage(WebDriver ldriver){
		
		driver=ldriver;
	}
	
	//Object repositories
	@FindBy(id="firstname")
	WebElement TestingForm_FirstName;
	
	@FindBy(id="lastname")
	WebElement TestingForm_LastName;

	
	/*@FindBy(id="state")
	WebElement TestingForm_State;
*/

	
	@FindBy(id="postcode")
	WebElement TestingForm_PostCode;

	@FindBy(id="dob")
	WebElement TestingForm_Dob;

	@FindBy(id="terms-1")
	WebElement TestingForm_TermsAndCondition;

	
	@FindBy(id="subm")
	WebElement TestingForm_SubmitButton;

	@FindBy(id="mobile")
	WebElement TestingForm_MobileNo;

	
	// By Name
	
	@FindBy(name="customer.STATE_1")
	WebElement TestingForm_State;
	@FindBy(name="customer.ADDRESS1")
	WebElement TestingForm_Address;
	
	@FindBy(name="customer.SUBURBTOWN")
	WebElement TestingForm_Suburb;
	
	// By xpath
	
	@FindBy(xpath=".//*[@id='main-form-area']/div[3]/div/input")
	WebElement TestingForm_Email;

	@FindBy(xpath=".//*[@id='the-form']/div[2]/div[1]/div/h2")
	WebElement TestingForm_ThankU;

	
	
	public void sendKeysToFirstName(String element){
		
		waitUntilElementToBeClickable(TestingForm_FirstName, 10);
		TestingForm_FirstName.clear();
		TestingForm_FirstName.sendKeys(element);
		Add_Log.info(TestCaseName + ":- Entered  'FirstName'");
	}
	
	public void sendKeysToLastName(String element){
		
			waitUntilElementToBeClickable(TestingForm_LastName, 10);
			TestingForm_LastName.clear();
			TestingForm_LastName.sendKeys(element);
			Add_Log.info(TestCaseName + ":- Entered  'LastName'");
		}
	
	public void sendKeysToEmail(String element){
		
		waitUntilElementToBeClickable(TestingForm_Email, 10);
		TestingForm_Email.clear();
		TestingForm_Email.sendKeys(element);
		Add_Log.info(TestCaseName + ":- Entered  'Email'");
	}
	
	public void sendKeysToMobileNo(String element){
		
		waitUntilElementToBeClickable(TestingForm_MobileNo, 10);
		TestingForm_MobileNo.clear();
		TestingForm_MobileNo.sendKeys(element);
		Add_Log.info(TestCaseName + ":- Entered  'MobileNo'");
	}
	
	
	public void sendKeysToAddress(String element){
		
		waitUntilElementToBeClickable(TestingForm_Address, 10);
		TestingForm_Address.clear();
		TestingForm_Address.sendKeys(element);
		Add_Log.info(TestCaseName + ":- Entered  'Address'");
	}
	
	
	public void sendKeysToSuburb(String element){
		
		waitUntilElementToBeClickable(TestingForm_Suburb, 10);
		TestingForm_Suburb.clear();
		TestingForm_Suburb.sendKeys(element);
		Add_Log.info(TestCaseName + ":- Entered  'Suburb'");
	}
	
	
	
	
	public void selectState(String element){
	waitUntilElementToBeClickable(TestingForm_State, 10);
	Select selectState = new Select(TestingForm_State);
	selectState.selectByVisibleText(element);

	Add_Log.info(TestCaseName + ":- Entered  'State'");
	}
	
	public void sendKeysToPostCode(String element){
		
		waitUntilElementToBeClickable(TestingForm_PostCode, 10);
		TestingForm_PostCode.clear();
		TestingForm_PostCode.sendKeys(element);
		Add_Log.info(TestCaseName + ":- Entered  'Post Code'");

	}
	
	public void sendKeysToDob(String element){
		
		waitUntilElementToBeClickable(TestingForm_Dob, 10);
		TestingForm_Dob.clear();
		TestingForm_Dob.sendKeys(element);
		Add_Log.info(TestCaseName + ":- Entered  'Date of Birth'");

	}
	
	public void clickOnTermsAndCondition(String element){
	waitUntilElementToBeClickable(TestingForm_TermsAndCondition, 10);
	System.out.println(" Is Terms & Condition = " + TestingForm_TermsAndCondition.isSelected());
	if ((element.equals("Y")) && (!TestingForm_TermsAndCondition.isSelected())) {

		TestingForm_TermsAndCondition.click();

		Add_Log.info(TestCaseName + ":- Clicked on  'Terms and Condition'");
	}
	}
	
	
	public void clickOnSubmit(String element){
	if (element.equals("Y")) {
		waitUntilElementToBeClickable(TestingForm_SubmitButton, 10);
		TestingForm_SubmitButton.click();
		Add_Log.info(TestCaseName + ":- Clicked on  'Submit Button'");
	}
	}
	

	public void verifyTestingFormSubmission() throws InterruptedException {
		try {
			// Wait for Submit Button
			TestingForm_SubmitButton.isDisplayed();
			Add_Log.info(TestCaseName + ":- Testing Form is not submitted as it is negative scenario  ");

		} catch (Exception e) {

			// This function is to file captured failures in the reports.
			reportFailures(TestCaseName + ":- Testing Form is unexpectedly submitted");

		}
		Add_Log.info(TestCaseName + ":- Verified Testing Form Status  ");
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
			waitUntilElementToBeVisible(TestingForm_ThankU, 5);

			// Check for Thank You Message

			String actualMsg = TestingForm_ThankU.getText();
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
		finally{

			Add_Log.info(TestCaseName + ":- Verified Text  'TestingForm_ThankU'");

		}
	}

	

	
	
	
	/*
	
	//@BeforeSuite
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
	
	*/
	

}
