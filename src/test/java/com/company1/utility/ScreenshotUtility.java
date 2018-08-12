//Find More Tutorials On WebDriver at -> http://software-testing-tutorials-automation.blogspot.com
package com.company1.utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.company1.TestSuiteBase.SuiteBase;

public class ScreenshotUtility extends SuiteBase implements ITestListener {
	// This method will execute before starting of Test suite.
	public void onStart(ITestContext tr) {

	}

	// This method will execute, Once the Test suite is finished.
	public void onFinish(ITestContext tr) {

	}

	// This method will execute only when the test is pass.
	public void onTestSuccess(ITestResult tr) {
		// If screenShotOnPass = yes, call captureScreenShot.
		if (SuiteBase.Param.getProperty("screenShotOnPass").equalsIgnoreCase("yes")) {
			captureScreenShot(tr, "pass");
		}
	}

	// This method will execute only on the event of fail test.

	public void onTestFailure(ITestResult tr) {
		// If screenShotOnFail = yes, call captureScreenShot.
		if (SuiteBase.Param.getProperty("screenShotOnFail").equalsIgnoreCase("yes")) {
			captureScreenShot(tr, "fail");
		}
	}

	// This method will execute before the main test start (@Test)
	public void onTestStart(ITestResult tr) {

	}

	// This method will execute only if any of the main test(@Test) get skipped
	public void onTestSkipped(ITestResult tr) {
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
	}

	// Function to capture screenshot.
	public void captureScreenShot(ITestResult result, String status) {
		try {
			String destDir = "";
			String passfailMethod = result.getMethod().getRealClass().getSimpleName() + "."
					+ result.getMethod().getMethodName();
			// To capture screenshot.
			File scrFile = ((TakesScreenshot) SuiteBase.driver).getScreenshotAs(OutputType.FILE);
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");

			DateFormat dateFormat1 = new SimpleDateFormat("dd-MMM-yyyy");

			// If status = fail then set folder name "screenshots/Failures"
			if (status.equalsIgnoreCase("fail")) {
				destDir = "screenshots/Failures/" + dateFormat1.format(new Date());
			}
			// If status = pass then set folder name "screenshots/Success"
			else if (status.equalsIgnoreCase("pass")) {
				destDir = "screenshots/Success/" + dateFormat1.format(new Date());
			}

			// To create folder to store screenshots
			new File(destDir).mkdirs();
			// Set file name with combination of test class name + date time.
			String destFile = passfailMethod + " - " + dateFormat.format(new Date()) + ".png";

			// Store file at destination folder location
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
			com.company1.TestSuiteBase.SuiteBase.Add_Log
					.info(" Screen shot  is stored at '" + destDir + "' as:- '" + destFile + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}