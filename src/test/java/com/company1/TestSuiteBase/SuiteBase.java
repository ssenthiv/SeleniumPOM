/**
//Script Name: SuiteBase
//Test Cases Covered:Master Class
//Developed By: Srinivas Anand
//Created Date: 03-Sep-2014
//Last Modified:
//Last Modified By:
//Description: All the Common functions used for the test Suite and test cases will be developed in this base class
 */

package com.company1.TestSuiteBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

//import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.company1.utility.Read_XLS;
import com.company1.utility.SuiteUtility;

public class SuiteBase {
	public static Read_XLS TestSuiteListExcel = null;

	// XLS object for all Modules
	// public static Read_XLS TestCaseListExcelOne = null;
	// public static Read_XLS TestCaseListExcelTwo = null;
	public static Read_XLS TestCaseListExcelSuiteTestingForm = null;

	public static Read_XLS FilePath = null;
	public static String SheetName = null;
	public static String SuiteName = null;
	public static String ToRunColumnName = null;
	public static String TestCaseName = null;
	public static String ToRunColumnNameTestCase = null;
	public static String ToRunColumnNameTestData = null;
	public static String TestDataToRun[] = null;
	public static boolean TestCasePass = true;
	public static int DataSet = -1;
	public static boolean Testskip = false;
	public static boolean Testfail = false;
	public static SoftAssert s_assert = null;
	public static boolean selectionState = true;

	public static Logger Add_Log = null;
	public boolean BrowseralreadyLoaded = false;
	public static Properties Param = null;
	public static Properties Object = null;
	public static WebDriver driver = null;
	public static WebDriver ExistingchromeBrowser = null;
	public static WebDriver ExistingmozillaBrowser = null;
	public static WebDriver ExistingIEBrowser = null;
	public boolean isAlreadyLogIn = false;
	public boolean acceptNextAlert = true;
	SoftAssert Soft_Assert = new SoftAssert();
	public static boolean browserLoaded = false;
	public static int step_Number = 1;

	DesiredCapabilities capabilities = null;

	public void init() throws IOException {

		// driver.manage().deleteAllCookies();
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		System.out.println(sdf.format(date));

		System.setProperty("logfile.name",
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\company1\\Logging\\applog_" + sdf.format(date));
		System.out.println(System.getProperty("logfile.name"));
		// To Initialize logger service.
		Add_Log = Logger.getLogger("rootLogger");

		// Please change file's path strings bellow If you have stored them at
		// location other than bellow.

		TestSuiteListExcel = new Read_XLS(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\company1\\ExcelFiles\\GlobalTestSuiteList.xls");
/*
		TestCaseListExcelSuiteTestingForm = new Read_XLS(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\company1\\ExcelFiles\\SuiteTestingForm.xls");

	*/
		// Bellow given syntax will Insert log In applog.log file.
		// Add_Log.info("All Excel Files Initialized successfully.");

		// Create object of Java Properties class
		Param = new Properties();
		FileInputStream fip = new FileInputStream(
				System.getProperty("user.dir") + "//src//test//java//com//company1//property//Param.properties");
		Param.load(fip);
		// Add_Log.info("Param.properties file loaded successfully.");
/*
		// Initialize Objects.properties file.
		Object = new Properties();
		fip = new FileInputStream(
				System.getProperty("user.dir") + "//src//test//java//com//company1//property//Objects.properties");
		Object.load(fip);
*/
		// Add_Log.info("Objects.properties file loaded successfully.");
	}

	public void initPageTestingForm() throws IOException{
		
		TestCaseListExcelSuiteTestingForm = new Read_XLS(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\company1\\ExcelFiles\\SuiteTestingForm.xls");

		/*
		// Initialize Objects.properties file.
				Object = new Properties();
				FileInputStream fip = new FileInputStream(
						System.getProperty("user.dir") + "//src//test//java//com//company1//property//Objects.properties");
				Object.load(fip);
		*/
	}
	
	// Call this function to open the browser depending on the param properties
	// parameter
	public void loadWebBrowser() {
		// Check If any previous webdriver browser Instance Is exist then run
		// new test In that existing webdriver browser Instance.

		try {

			if (Param.getProperty("testBrowser").equalsIgnoreCase("Mozilla") && ExistingmozillaBrowser != null) {
				driver = ExistingmozillaBrowser;
				browserLoaded = true;
				return;
			} else if (Param.getProperty("testBrowser").equalsIgnoreCase("chrome") && ExistingchromeBrowser != null) {
				driver = ExistingchromeBrowser;
				browserLoaded = true;
				return;
			} else if (Param.getProperty("testBrowser").equalsIgnoreCase("IE") && ExistingIEBrowser != null) {
				driver = ExistingIEBrowser;
				browserLoaded = true;
				return;
			}

			if ((Param.getProperty("testBrowser").equalsIgnoreCase("Mozilla") && ExistingmozillaBrowser != null)
					|| (Param.getProperty("testBrowser").equalsIgnoreCase("IE") && ExistingIEBrowser != null)
					|| (Param.getProperty("testBrowser").equalsIgnoreCase("chrome") && ExistingchromeBrowser != null)) {
				// Clear all cookies
				try {
					driver.manage().deleteAllCookies();
				} catch (Exception e) {
					Add_Log.info("Exception in delete all cookies");
				}
			}
		} catch (Exception e) {
			Add_Log.info("Exception occurred in first if case");
			Add_Log.info("Details of failure are:- " + e.getLocalizedMessage());
		}

		if (Param.getProperty("testBrowser").equalsIgnoreCase("Mozilla") && ExistingchromeBrowser == null) {
			do {
				try {
					// To Load Firefox driver Instance.
					
					System.setProperty("webdriver.gecko.driver",
							System.getProperty("user.dir") + "//BrowserDrivers//geckodriver.exe");
				
					driver = new FirefoxDriver();
					
					ExistingmozillaBrowser = driver;
					Add_Log.info("Firefox Driver Instance loaded successfully.");
					browserLoaded = true;
				} catch (Exception e) {
					browserLoaded = false;
					Add_Log.info("Failing to load Firefox Driver.");
					Add_Log.info("Details of failure are:- " + e.getLocalizedMessage());
				}
			} while (browserLoaded != true);

		} else if (Param.getProperty("testBrowser").equalsIgnoreCase("Chrome") && ExistingchromeBrowser == null) {
			do {
				try {
					/*
					 * To Load Chrome driver Instance.
					 * System.setProperty("webdriver.chrome.driver",
					 * System.getProperty("user.dir") +
					 * "//BrowserDrivers//chromedriver.exe"); driver = new
					 * ChromeDriver(); ExistingchromeBrowser = driver;
					 * Add_Log.info(
					 * "Chrome Driver Instance loaded successfully.");
					 * browserLoaded = true;
					 */

					System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + "//BrowserDrivers//chromedriver.exe");
					ChromeOptions options = new ChromeOptions();

					options.addArguments("chrome.switches", "--disable-extensions");
					new DesiredCapabilities();
					DesiredCapabilities caps = DesiredCapabilities.chrome();
					caps.setCapability(ChromeOptions.CAPABILITY, options);

					Map<String, Object> prefs = new HashMap<String, Object>();
					prefs.put("credentials_enable_service", false);
					prefs.put("profile.password_manager_enabled", false);
					options.setExperimentalOption("prefs", prefs);

					driver = new ChromeDriver(caps);
					ExistingchromeBrowser = driver;
					Add_Log.info("Chrome Driver Instance loaded successfully.");
					browserLoaded = true;

				} catch (Exception e) {
					browserLoaded = false;
					Add_Log.info("Failing to load Chrome Driver.");
					Add_Log.info("Details of failure are:- " + e.getLocalizedMessage());
				}
			} while (browserLoaded != true);

		} else if (Param.getProperty("testBrowser").equalsIgnoreCase("IE") && ExistingchromeBrowser == null) {
			do {
				try {
					/*
					 * // To Load IE driver Instance.
					 * System.setProperty("webdriver.ie.driver",
					 * System.getProperty("user.dir") +
					 * "//BrowserDrivers//IEDriverServer.exe"); driver = new
					 * InternetExplorerDriver(); ExistingIEBrowser = driver;
					 * Add_Log.info("IE Driver Instance loaded successfully.");
					 * browserLoaded = true;
					 */

					// To Load IE driver Instance.
					System.setProperty("webdriver.ie.driver",
							System.getProperty("user.dir") + "//BrowserDrivers//IEDriverServer.exe");
					DesiredCapabilities cap = new DesiredCapabilities();
					cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					cap.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL,
							"http://integr8a.trclient.com/testing/");
					DesiredCapabilities.internetExplorer().setCapability("ignoreProtectedModeSettings", true);
					cap.setCapability("IE.binary", "C:/Program Files (x86)/Internet Explorer/iexplore.exe");
					cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					cap.setJavascriptEnabled(true);
					cap.setCapability("requireWindowFocus", true);
					cap.setCapability("enablePersistentHover", false);

					// capabilities = DesiredCapabilities.internetExplorer();
					// capabilities.setCapability(
					// InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false
					// );
					// capabilities.setCapability(
					// InternetExplorerDriver.IGNORE_ZOOM_SETTING, true );
					// capabilities.setCapability(
					// InternetExplorerDriver.NATIVE_EVENTS, false );
					// capabilities.setCapability(
					// "ignoreProtectedModeSettings", true );
					// capabilities.setCapability(
					// InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true );
					// capabilities.setCapability(
					// InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					// true );
					driver = new InternetExplorerDriver(cap);
					ExistingIEBrowser = driver;
					Add_Log.info("IE Driver Instance loaded successfully.");
					browserLoaded = true;
				} catch (Exception e) {
					browserLoaded = false;
					Add_Log.info("Failing to load IE Driver.");
					Add_Log.info("Details of failure are:- " + e.getLocalizedMessage());
				}
			} while (browserLoaded != true);

			if ((Param.getProperty("testBrowser").equalsIgnoreCase("Mozilla") && ExistingchromeBrowser == null)
					|| (Param.getProperty("testBrowser").equalsIgnoreCase("Chrome") && ExistingchromeBrowser == null)
					|| (Param.getProperty("testBrowser").equalsIgnoreCase("IE") && ExistingchromeBrowser == null)) {
				// Delete all cookies
				try {
					driver.manage().deleteAllCookies();
				} catch (Exception e) {
					Add_Log.info("Exception in delete all cookies");
				}
			}
		}
		driver.manage().window().maximize();
	}

	// Call this function to Login to the application
	// Can accept userID and password as a string
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void logIn(String userID, String password) {
		// To check If already login previously then don't execute this
		// function.

		// If Not login then login In to your account.
		try {
			driver.manage().deleteAllCookies();
			duplicateSession(userID);
			// Thread.sleep(5000);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (!isAlreadyLogIn) {

				Wait wait = new FluentWait(driver).withTimeout(70, TimeUnit.SECONDS).pollingEvery(2, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class);
				Thread.sleep(5000);

				wait.until(ExpectedConditions.elementToBeClickable(getElementByID("UserName")));

				getElementByID("UserName").clear();
				getElementByID("UserName").sendKeys(userID);
				getElementByID("Password").clear();
				getElementByID("Password").sendKeys(password);
				getElementByName("SubmitButton").click();

				Thread.sleep(5000);
				// waitAndClickable(driver.findElement(By.xpath(Object.getProperty("SignoutMenu"))));
				wait.until(ExpectedConditions
						.elementToBeClickable(driver.findElement(By.xpath(Object.getProperty("SignoutMenu")))));

				driver.findElement(By.xpath(Object.getProperty("SignoutMenu"))).isDisplayed();

				isAlreadyLogIn = true;

			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		} catch (Throwable e) {
			System.out.println(e.getLocalizedMessage());
		}

	}

	public Boolean logIn1(String userID, String password) {
		// To check If already login previously then don't execute this
		// function.
		// If Not login then login In to your account.
		/*
		 * try { duplicateSession(userID); } catch (Exception e1) {
		 * e1.printStackTrace(); }
		 */
		try {
			if (!isAlreadyLogIn) {
				// if (driver.getCurrentUrl()
				// .equalsIgnoreCase(Param.getProperty("siteURL")))
				// {
				// System.out.println("Do Nothing");
				// }
				// else
				// {
				// // To navigate to URL.
				// driver.get(Param.getProperty("siteURL"));
				// Thread.sleep(5000);
				// }

				try {

					driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
					waitUntilElementToBeClickable(getElementByID("UserName"));
					// Enter User Name
					waitUntilElementToBeClickable(getElementByID("UserName"), 10);
					getElementByID("UserName").clear();
					getElementByID("UserName").sendKeys(userID);
					Add_Log.info(TestCaseName + ":- Entered username as '" + userID + "'");

					// Enter Password
					waitUntilElementToBeClickable(getElementByID("Password"), 5);
					getElementByID("Password").clear();
					getElementByID("Password").sendKeys(password);
					Add_Log.info(TestCaseName + ":- Entered Password as '" + password + "'");

					// Click on Login button
					waitUntilElementToBeClickable(getElementByName("SubmitButton"), 10);
					getElementByName("SubmitButton").click();
					Add_Log.info(TestCaseName + ":- Clicked on 'SubmitButton'");
					Thread.sleep(2000);

					new WebDriverWait(driver, 10)
							.until(ExpectedConditions.visibilityOf(getElementByXPath("SignoutMenu")));
					waitUntilElementToBeClickable(getElementByXPath("SignoutMenu"), 10);
					Add_Log.info("Login is successful");
					isAlreadyLogIn = true;

				} catch (Exception e) {

					waitUntilElementToBeClickable(getElementByXPath("dl_DocListSelectYesPopUp"), 10);

					getElementByXPath("dl_DocListSelectYesPopUp").click();
					Thread.sleep(1000);
					// driver.navigate().refresh();
					// Enter User Name
					waitUntilElementToBeClickable(getElementByID("UserName"), 10);
					getElementByID("UserName").clear();
					getElementByID("UserName").sendKeys(userID);
					Add_Log.info(TestCaseName + ":- Entered username as '" + userID + "'");

					// Enter Password
					waitUntilElementToBeClickable(getElementByID("Password"), 10);
					getElementByID("Password").clear();
					getElementByID("Password").sendKeys(password);
					Add_Log.info(TestCaseName + ":- Entered Password as '" + password + "'");

					// Click on Login button
					waitUntilElementToBeClickable(getElementByName("SubmitButton"), 5);
					getElementByName("SubmitButton").click();
					Add_Log.info(TestCaseName + ":- Clicked on 'SubmitButton'");
					Thread.sleep(2000);

					waitUntilElementToBeClickable(getElementByXPath("SignoutMenu"), 10);
					Add_Log.info("Session is closed and login is successful");
					isAlreadyLogIn = true;
				}

			}
			return true;
		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Failure on Login Screen");
			Add_Log.info("Details of failure:- " + e.getLocalizedMessage());
			return false;
		}
	}

	// Call this function to log out from the application
	public void logOut() {
		try {
			// Scroll page to Top
			scrollPageToTop();
			Thread.sleep(2000);

			// Click on Signout menu
			clickBy(getElementByXPath("SignoutMenu"), "SignoutMenu", 10);

			// Click on Sign Out button
			clickBy(getElementByXPath("SignOutButton"), "SignOutButton", 10);
			Thread.sleep(5000);

			// //Added for Docview module- To handle confirmation popup while
			// signout. It won't impact others
			// try{
			// getElementByXPath("ct_popupYes").click();
			// Add_Log.info("For DocView - Pop up confirmation dialog is shown
			// and clicked on yes button");
			// }catch(Exception e1){
			// Add_Log.info("For Doc View - Pop up confirmation dialog is not
			// appeared");
			// }

			// Check whether logout is successfully done or not
			try {
				waitUntilElementToBeClickable(driver.findElement(By.id(Object.getProperty("UserName"))), 10);
				Add_Log.info("Logout is done successfully.\n");
			} catch (Exception e1) {
				Add_Log.info("Logout is failed:-\n" + e1.getMessage());
			}

		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Logout Function - Failed");
			Add_Log.info("Details of the exception are:-\n" + e.getMessage());
		} finally {
			isAlreadyLogIn = false;
		}
	}

	// Call this function to close the browser
	public void closeWebBrowser() {
		try {
			try {
				driver.close();
			} catch (Exception e) {
				Add_Log.info("Exception in Close browser code. ");
				Add_Log.info("Details of failure are:- " + e.getLocalizedMessage());
			} finally {
				browserLoaded = false;
			}

			// To kill the browser processes
			try {
				// To close the browser instance.
				if (Param.getProperty("testBrowser").equals("Chrome")) {
					Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				} else if (Param.getProperty("testBrowser").equals("IE")) {
					Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
				} else if (Param.getProperty("testBrowser").equals("Mozilla")) {
					Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
				}
				Thread.sleep(5000);
			} catch (Throwable e) {
				Add_Log.info("Exception in Kill browser code. ");
				Add_Log.info("Details of failure are:- " + e.getLocalizedMessage());
			}

		} catch (Throwable e) {
			Add_Log.info("Exception in closeWebBrowser function ");
			Add_Log.info("Details of failure are:- " + e.getLocalizedMessage());
		} finally {
			// null browser Instance when close.
			ExistingchromeBrowser = null;
			ExistingmozillaBrowser = null;
			ExistingIEBrowser = null;
		}
	}

	// getElementByXPath function for static xpath
	public WebElement getElementByXPath(String Key) {
		// Check to avoid waste of time.
		try {
			if (driver.getCurrentUrl().equalsIgnoreCase(Param.getProperty("siteURL") + "en-us/Base/AccessDenied")) {
				logFailureAndAbortFurtherExecution(
						"Aborted the execution as application is throwing an error -'Access Denied'");
			}
		} catch (Exception e) {
			System.out.println("Ignore as it is ad-on to avoid waste of time");
		}

		// Actual Test
		try {
			// This block will find element using Key value from web page and
			// return It.
			return driver.findElement(By.xpath(Object.getProperty(Key)));
		} catch (Throwable t) {
			// Add_Log.info("Object not found for key --" + Key);
			return null;
		}
	}

	// getElementByXPath function for dynamic xpath
	public WebElement getElementByXPath(String Key1, int val, String key2) {
		// Check to avoid waste of time.
		try {
			if (driver.getCurrentUrl().equalsIgnoreCase(Param.getProperty("siteURL") + "en-us/Base/AccessDenied")) {
				logFailureAndAbortFurtherExecution(
						"Aborted the execution as application is throwing an error -'Access Denied'");
			}
		} catch (Exception e) {
			System.out.println("Ignore as it is ad-on to avoid waste of time");
		}

		try {
			// This block will find element using values of Key1, val and key2
			// from web page and return It.
			return driver.findElement(By.xpath(Object.getProperty(Key1) + val + Object.getProperty(key2)));
		} catch (Throwable t) {
			// Add_Log.info("Object not found for custom xpath");

			return null;
		}
	}

	// Call this function to locate element by ID locator.
	public WebElement getElementByID(String Key) {
		// Check to avoid waste of time.
		try {
			if (driver.getCurrentUrl().equalsIgnoreCase(Param.getProperty("siteURL") + "en-us/Base/AccessDenied")) {
				logFailureAndAbortFurtherExecution(
						"Aborted the execution as application is throwing an error -'Access Denied'");
			}
		} catch (Exception e) {
			System.out.println("Ignore as it is ad-on to avoid waste of time");
		}

		try {
			return driver.findElement(By.id(Object.getProperty(Key)));
		} catch (Exception e) {
			// Add_Log.info("Object not found for key --" + Key);
			return null;
		}
	}

	// Call this function to locate element by Name Locator.
	public WebElement getElementByName(String Key) {
		// Check to avoid waste of time.
		try {
			if (driver.getCurrentUrl().equalsIgnoreCase(Param.getProperty("siteURL") + "en-us/Base/AccessDenied")) {
				logFailureAndAbortFurtherExecution(
						"Aborted the execution as application is throwing an error -'Access Denied'");
			}
		} catch (Exception e) {
			System.out.println("Ignore as it is ad-on to avoid waste of time");
		}

		// Actual Test
		try {
			return driver.findElement(By.name(Object.getProperty(Key)));
		} catch (Throwable t) {
			// Add_Log.info("Object not found for key --" + Key);
			return null;
		}
	}

	// Call this function to locate element by cssSelector Locator.
	public WebElement getElementByCSS(String Key) {
		// Check to avoid waste of time.
		try {
			if (driver.getCurrentUrl().equalsIgnoreCase(Param.getProperty("siteURL") + "en-us/Base/AccessDenied")) {
				logFailureAndAbortFurtherExecution(
						"Aborted the execution as application is throwing an error -'Access Denied'");
			}
		} catch (Exception e) {
			System.out.println("Ignore as it is ad-on to avoid waste of time");
		}

		try {
			return driver.findElement(By.cssSelector(Object.getProperty(Key)));
		} catch (Throwable t) {
			// Add_Log.info("Object not found for key --" + Key);
			return null;
		}
	}

	// Call this function to locate element by ClassName Locator.
	public WebElement getElementByClass(String Key) {
		// Check to avoid waste of time.
		try {
			if (driver.getCurrentUrl().equalsIgnoreCase(Param.getProperty("siteURL") + "en-us/Base/AccessDenied")) {
				logFailureAndAbortFurtherExecution(
						"Aborted the execution as application is throwing an error -'Access Denied'");
			}
		} catch (Exception e) {
			System.out.println("Ignore as it is ad-on to avoid waste of time");
		}

		try {
			return driver.findElement(By.className(Object.getProperty(Key)));
		} catch (Throwable t) {
			// Add_Log.info("Object not found for key --" + Key);
			return null;
		}
	}

	// Call this function to locate element by tagName Locator.
	public WebElement getElementByTagName(String Key) {
		// Check to avoid waste of time.
		try {
			if (driver.getCurrentUrl().equalsIgnoreCase(Param.getProperty("siteURL") + "en-us/Base/AccessDenied")) {
				logFailureAndAbortFurtherExecution(
						"Aborted the execution as application is throwing an error -'Access Denied'");
			}
		} catch (Exception e) {
			System.out.println("Ignore as it is ad-on to avoid waste of time");
		}

		try {
			return driver.findElement(By.tagName(Object.getProperty(Key)));
		} catch (Throwable t) {
			// Add_Log.info("Object not found for key --" + Key);
			return null;
		}
	}

	// Call this function to locate element by link text Locator.
	public WebElement getElementBylinkText(String Key) {
		try {
			return driver.findElement(By.linkText(Object.getProperty(Key)));
		} catch (Throwable t) {
			// Add_Log.info("Object not found for key --" + Key);
			return null;
		}
	}

	// Call this function to locate element by partial link text Locator.
	public WebElement getElementBypLinkText(String Key) {
		try {
			return driver.findElement(By.partialLinkText(Object.getProperty(Key)));
		} catch (Throwable t) {
			// Add_Log.info("Object not found for key --" + Key);
			return null;
		}
	}

	// Call to fetch count of values from combobox/listbox
	public Integer getCountfromList(WebElement Key) {
		try {
			Select se = new Select(Key);
			List<WebElement> l = se.getOptions();
			return l.size();
		} catch (Exception e) {
			Add_Log.info("No Data or Object Found --" + Key);
			return -1;
		}
	}

	// Call this function to compare two strings
	public boolean compareStrings(String actualStr, String expectedStr) {
		try {
			// If this assertion will fail, It will throw exception and catch
			// block will be executed.
			Assert.assertEquals(actualStr, expectedStr);
		} catch (Throwable t) {
			Add_Log.info("Actual String: " + actualStr + " Does not match Expected: " + expectedStr);
			return false;
		}
		// If Strings match, return true.
		return true;
	}

	// Call this function to compare two Integer values
	public boolean compareIntegerVals(int actualIntegerVal, int expectedIntegerVal) {
		try {
			// If this assertion will fail, It will throw exception and catch
			// block will be executed.
			Assert.assertEquals(actualIntegerVal, expectedIntegerVal);
		} catch (Throwable t) {

			Add_Log.info("Actual Integer: " + actualIntegerVal + " Does not match Expected: " + expectedIntegerVal);
			// If Integer values will not match, return false.
			return false;
		}
		// If Integer values match, return true.
		return true;
	}

	// Call this function to compare two double values
	public boolean compareDoubleVals(double actualDobVal, double expectedDobVal) {
		try {
			// If this assertion will fail, It will throw exception and catch
			// block will be executed.
			Assert.assertEquals(actualDobVal, expectedDobVal);
		} catch (Throwable t) {

			Add_Log.info("Actual Double: " + actualDobVal + " Does not match Expected: " + expectedDobVal);
			// If double values will not match, return false.
			return false;
		}
		// If double values match, return true.
		return true;
	}

	// To check a particular text available in the source of the page
	public boolean isTextPresent(String txtValue) {
		boolean b = false;
		try {
			b = driver.getPageSource().contains(txtValue);
		} catch (Exception e) {
			Add_Log.info("The Object Name " + txtValue + " Is Not Present in the Page");
			// If Text not present in the screen, return false.
			return b;
		}
		return b;
	}

	// call this function to check whether the element is available
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	// Call this function to verify whether if any Alert Dialog exists
	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	// Call this function to close the Alert dialog and get the text from it
	public String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	// Call this function to wait till a Element exists or time out
	public static WebElement waitForElementPresent(WebDriver driver, final By by, int timeOutInSeconds) {

		WebElement element;

		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
																			// implicitlyWait()

			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // reset
																				// implicitlyWait
			return element; // return the element
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Call this function to get data from the database.
	public String GetDataFromDB(String sqlQuery, String ColumName, String DBName)
			throws SQLException, ClassNotFoundException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager
				.getConnection("jdbc:sqlserver://10.55.28.44;user=sa;password=ConAdm1n;database=" + DBName);
		System.out.println("test");
		Statement sta = conn.createStatement();
		String Sql = sqlQuery;
		ResultSet rs = sta.executeQuery(Sql);
		String resultset = null;
		while (rs.next()) {
			System.out.println(rs.getString(ColumName));
			resultset = rs.getString(ColumName);
		}

		return resultset;
	}

	// ******************************************************************************************************************************
	// Function Name: selectValueFromList
	// Developed By:
	// Created Date: 07-Oct-2014
	// Last Modified:
	// Description: To get the selected value from a dropdown list
	// ******************************************************************************************************************************
	public void selectValueFromList(String Key, String val) {
		try {
			Select dropdown = new Select(getElementByID(Key));
			dropdown.selectByVisibleText(val);
		} catch (Throwable t) {
			Add_Log.info("Object is not found for key --" + Key);
			// driver.close();
		}
	}

	// ******************************************************************************************************************************
	// Function Name: selectValueFromList
	// Developed By:
	// Created Date: 09-Oct-2014
	// Last Modified:
	// Description: To get the selected value from a dropdown list
	// ******************************************************************************************************************************
	public int countItemsFromList(String Key) {
		try {
			Select dropdown = new Select(getElementByID(Key));
			List<WebElement> intListItem = dropdown.getOptions();
			return (intListItem.size());
		} catch (Throwable t) {
			Add_Log.info("Object not found for key --" + Key);
		}
		return 0;
	}

	// Call this function to click sub Menu hovering main menu
	public void Submenu_Click(WebElement HoverMenu, WebElement Sublink) {

		Actions actions = new Actions(driver);
		WebElement menuHoverLink = HoverMenu;
		actions.moveToElement(menuHoverLink);

		WebElement subLink = Sublink;
		actions.moveToElement(subLink);
		actions.click();
		actions.perform();
	}

	// ******************************************************************************************************************************
	// Function Name: searchNotification
	// Developed By: Satish R. Pawal
	// Created Date: 17-Oct-2014
	// Last Modified:
	// Description: If search is taking more than 8 sec. then background search
	// will start
	// ******************************************************************************************************************************
	// call this function To get the text from table or grid
	public void searchNotification() throws InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			int flagElmtExist = driver.findElements(By.id(Object.getProperty("bs_NotificationNo12"))).size();
			driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			System.out.println(flagElmtExist);
			if (flagElmtExist != 0) {
				driver.findElement(By.id(Object.getProperty("bs_NotificationNo12"))).click();

				Thread.sleep(2000);

				driver.findElement(By.id(Object.getProperty("bs_NotificationOk14"))).click();
				Thread.sleep(2000);

				driver.findElement(By.xpath(Object.getProperty("bs_SavedSearch_Sen15"))).click();

				Thread.sleep(12000);
			} else {
				Thread.sleep(100);
			}
		}

		catch (Exception e) {
			System.out.println("ERRRO ");
			// Add_Log.info("Object Not found"+e.getMessage());
		}

	}

	// ******************************************************************************************************************************
	// Function Name: multipleSearchNotification
	// Developed By: Satish R. Pawal
	// Created Date: 28-Oct-2014
	// Last Modified: Satish R. Pawal - Added If condition.
	// Description: If search is taking more than 8 sec. then background search
	// will start multiple times
	// ******************************************************************************************************************************
	// call this function If search is taking more than 8 sec. then background
	// search will start multiple times
	public void multipleSearchNotification() throws InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			int flagElmtExist = driver.findElements(By.id(Object.getProperty("bs_NotificationNo12"))).size();
			driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			System.out.println(flagElmtExist);
			if (flagElmtExist != 0) {
				for (int intID = 1; intID < 10;) {
					// String strNo="(//button[@id='btnNo'])["+intID+"]";
					String strNo = Object.getProperty("bs_NotificationNo16").toString();
					strNo = strNo + intID + "]";
					String strOk = Object.getProperty("bs_NotificationOk17").toString();
					strOk = strOk + intID + "]";

					// here adding to verify size code
					int flagElmtExist1 = driver.findElements(By.xpath(strNo)).size();
					driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
					// System.out.println(flagElmtExist1);
					if (flagElmtExist1 != 0) {
						// here adding to verify size
						boolean flgCheck = driver.findElement(By.xpath(strNo)).isDisplayed();
						if (flgCheck == true) {
							driver.findElement(By.xpath(strNo)).click();
							Thread.sleep(2000);
							driver.findElement(By.xpath(strOk)).click();
							Thread.sleep(2000);
							driver.findElement(By.xpath(Object.getProperty("bs_SavedSearch_Sen15"))).click();
							Thread.sleep(12000);
							break;
						} else {
							intID = intID + 1;
						}
					} else {
						Thread.sleep(100);
						intID = intID + 1;
					}

				}

			} else {
				Thread.sleep(100);
			}
		} catch (Exception e) {
			Add_Log.info("Object Not found" + e.getMessage());
		}
	}

	// ******************************************************************************************************************************
	// Function Name: readMessageFromPopUp
	// Developed By: Satish R. Pawal
	// Created Date: 30-Oct-2014
	// Last Modified:
	// Description: To read and return message from pop up.
	// ******************************************************************************************************************************
	// call this function To read and return message from pop up.
	public String readMessageFromPopUp(String keyPopUP) throws InterruptedException {
		String strReturnMessage = null;
		try {
			// Capturing Actual error message
			String strClosePopUp = Object.getProperty(keyPopUP).toString();
			strReturnMessage = driver.findElement(By.xpath(strClosePopUp)).getText();
			driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
			Thread.sleep(12000);
			return strReturnMessage;
		} catch (Exception e) {
			Add_Log.info("Object Not found" + e.getMessage());
			return strReturnMessage;
		}
	}

	public String readMessageFromPopUp(WebElement key) {
		String strReturnMessage = null;
		try {
			// Capturing Actual error message
			strReturnMessage = key.getText();
			Add_Log.info("Fetching Message from Popup" + strReturnMessage);
			return strReturnMessage;
		} catch (Exception e) {
			Add_Log.info("Object Not found" + e.getMessage());
			return strReturnMessage;
		}
	}

	// ***************************************************************************************************************
	// Developed by :Gouri Dhavalikar
	// ****************************************************************************************************************
	// call this function to check whether element is displayed or not.

	public boolean isDisplayedPresent(String Key) {
		try {
			// System.out.println("Client Entity found on page"+Key);
			getElementByName(Key).isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	// Added by Sunita
	public boolean isElementPresentOnPage(WebElement isExistElement) {
		try {

			isExistElement.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Call this Function to get the number of rows in a table in current page
	public int getTableRowsCount(WebElement Key) {
		int cntRow = 0;
		try {

			WebElement table = Key;
			// get number of rows from the table
			List<WebElement> allRows = table.findElements(By.tagName("tr"));
			cntRow = allRows.size() - 1;
		} catch (Exception e) {
			Add_Log.info("The Table Not Found: " + Key);
		}
		return cntRow;
	}

	// Call this function to verify whether the data is available in a table in
	// the current page
	public boolean findTableData(WebElement Key, String VerifyData) {
		boolean dataFound = false;
		WebElement mytable = Key;
		// To locate rows of table.
		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		// To calculate no of rows In table.
		int rows_count = rows_table.size();
		String celtext = null;
		// Loop will execute till the last row of table.
		for (int row = 0; row < rows_count; row++) {
			// To locate columns(cells) of that specific row.
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			// To calculate no of columns(cells) In that specific row.
			int columns_count = Columns_row.size();
			System.out.println("columns_count:  " + columns_count);
			// Loop will execute till the last cell of that specific row.
			System.out.println("Verifydata in loop: " + VerifyData + "- row:" + row);
			for (int column = 0; column < columns_count; column++) {
				// To retrieve text from that specific cell.
				celtext = Columns_row.get(column).getText();
				if (celtext.equalsIgnoreCase(VerifyData)) {
					dataFound = true;
				}
			}
		}
		return dataFound;
	}

	// Call this function to verify whether the data is available in a table in
	// the current page
	public void clickButtonInaRow(WebElement Key, String VerifyData, String Key1, String Key2) {

		WebElement mytable = Key;
		if (Key1.contains("?")) {
			Key1.replace("?", VerifyData);
		}
		// To locate rows of table.
		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		// To calculate no of rows In table.
		int rows_count = rows_table.size();
		String celtext = null;
		// Loop will execute till the last row of table.
		for (int row = 0; row < rows_count; row++) {
			// To locate columns(cells) of that specific row.
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			// To calculate no of columns(cells) In that specific row.
			int columns_count = Columns_row.size();
			// Loop will execute till the last cell of that specific row.
			System.out.println("Verifydata in loop: " + VerifyData + "- row:" + row);
			for (int column = 0; column < columns_count; column++) {
				// To retrieve text from that specific cell.
				celtext = Columns_row.get(column).getText();
				System.out.println(celtext + ":" + VerifyData);
				if (celtext.equals(VerifyData)) {
					mytable.findElement(By.xpath(Key1)).click();
					break;
				}

			}
		}
	}

	// Call this function to compare a value in a List box
	public boolean compareListValue(WebElement Key, String Value) {

		boolean result = false;

		WebElement dropdown = Key;
		Select select = new Select(dropdown);
		int cnt = getCountfromList(Key);
		List<WebElement> options = select.getOptions();

		for (WebElement we : options) {
			for (int i = 0; i < cnt; i++) {
				if (we.getText().equals(Value)) {
					result = true;
				} else {
					result = false;
				}
			}
		}
		return result;
	}

	// Call this Function to get the column number in a web table using column
	// name
	public int getColumnNumber(WebElement Key, String ColumnName) {
		int ColumnHeader = 0;
		int returnColumnNumber = 0;
		try {
			WebElement table = Key;
			List<WebElement> allHeaders = table.findElements(By.tagName("th"));

			// Get the column where the quantity is listed
			for (WebElement header : allHeaders) {
				ColumnHeader++;
				if (header.getText().equalsIgnoreCase(ColumnName)) {
					returnColumnNumber = ColumnHeader;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Add_Log.info("Column Name Not Foun in The Table: " + ColumnName);
		}

		return returnColumnNumber;
	}

	// //Call this to get the selected value with index from a dropdown list
	public void selectValueFromList_byindex(WebElement Key, int val) {
		try {
			Select dropdown = new Select(Key);
			dropdown.selectByIndex(val);
		} catch (Throwable t) {
			Add_Log.info("Value Not Found in the List Box --" + val);

		}
	}

	// Call this function to get data from the database.
	public String GetDataFromDB(String sqlQuery, String ColumName) throws SQLException, ClassNotFoundException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection(Param.getProperty("DBConnectionString"));
		Statement sta = conn.createStatement();
		String Sql = sqlQuery;
		ResultSet rs = sta.executeQuery(Sql);
		String resultset = null;
		while (rs.next()) {
			System.out.println(rs.getString(ColumName));
			resultset = rs.getString(ColumName);
		}

		return resultset;
	}

	// Call this function to get the selected value from a dropdown list
	public void selectValueFromList(WebElement Key, String val) {
		try {
			Select dropdown = new Select(Key);
			dropdown.selectByVisibleText(val);
		} catch (Throwable t) {
			Add_Log.info("Value Not Found --" + val);

		}
	}

	// ******************************************************************************************************************************
	// Function Name: retriveDataFromDB
	// Developed By: Satish R. Pawal
	// Created Date: 21-Nov-2014
	// Last Modified:
	// Description: To read and return data from database
	// ******************************************************************************************************************************
	// call this function To read and return data from database

	public String retriveDataFromDB(String sqlQuery, String ColumName) throws SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			Add_Log.info("Class Not found" + e.getMessage());
		}
		Connection objConn = DriverManager.getConnection(Param.getProperty("DBConnectionString"));
		Statement statment = objConn.createStatement();
		String strSqlQuery = sqlQuery;
		ResultSet rs = statment.executeQuery(strSqlQuery);
		String resultset = null;
		while (rs.next()) {
			resultset = rs.getString(ColumName);
		}
		return resultset;
	}

	// ******************************************************************************************************************************
	// Function Name: checkExistanceOfListValue
	// Developed By: Satish R. Pawal
	// Created Date: 09-Dec-2014
	// Last Modified:
	// Description: To check existance of data from the List or not
	// ******************************************************************************************************************************
	// Call this function to check value exists in a List or not
	public boolean checkExistanceOfListValue(WebElement Key, String Value) {
		boolean result = false;
		WebElement dropdown = Key;
		Select select = new Select(dropdown);
		List<WebElement> options = select.getOptions();
		for (WebElement we : options) {
			if (we.getText().equals(Value)) {
				result = true;
				break;
			} else {
				result = false;
			}
		}
		return result;
	}

	// ******************************************************************************************************************************
	// Function Name: dragAndDropObject
	// Developed By: Satish R. Pawal
	// Created Date: 25-December- 2014
	// Last Modified:
	// Description: This function used to drag and drop objects.
	// ******************************************************************************************************************************
	// call this function To drag and drop objects.
	public void dragAndDropObject(WebElement elementToMove, WebElement destination) {
		try {
			WebElement dragElement = elementToMove;

			WebElement dropElement = destination;
			(new Actions(driver)).dragAndDrop(dragElement, dropElement).perform();
			Thread.sleep(10000);
		} catch (Exception e) {
			Add_Log.info("Drag-gable Object is not found --");
		}
	}

	// *****************************************************************************************************************************
	// Developed by :Gouri Dhavalikar
	// Open email through the gmail account
	public void gmail(String Username, String Password) {
		loadWebBrowser();
		driver.get(Param.getProperty("gmail"));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		getElementByID("GmailUser").sendKeys(Username);
		getElementByID("GmailPwd").sendKeys(Password);
		getElementByID("SignIn").click();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		WebElement btn = driver.findElement(By.xpath("//span[contains(text(),'Your Account has been Created')]"));
		btn.click();
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		WebElement btn1 = driver.findElement(By.xpath("//a[contains(text(),'http://10.55.79.33/Login')]"));
		btn1.click();
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	// Close gmail
	public void Closegmail() {
		for (String winHandle : driver.getWindowHandles()) {
			if (winHandle == driver.getWindowHandles().toArray()[driver.getWindowHandles().size() - 1]) {
				continue;
			}
			driver.switchTo().window(winHandle);
			driver.close();
		}
	}

	// get first selected value
	public String getfirstselectedoption(WebElement Key) {
		try {
			Select dropdown = new Select(Key);
			return dropdown.getFirstSelectedOption().getText();
		} catch (Throwable t) {
			Add_Log.info("Object Not Found --");

		}
		return null;
	}

	// scroll down
	public void scrollingToBottomofAPage() {
		// driver.navigate().to(Param.getProperty("siteURL"));
		// driver.navigate().to("http://10.55.79.33/User/UserListView#");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void scrollingToElementofAPage(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			System.out.println("Scrolling Into View Exception");
		}
	}

	public void scrollingByCoordinatesofAPage() {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
	}

	// to select multiple values form the list box.
	public void SelectMultipleValues(Select select, String selectfirst, String selectsecond) {
		Actions act = new Actions(driver);

		List list = select.getOptions();
		WebElement first = null, second = null;

		for (Object element1 : list) {
			WebElement element12 = (WebElement) element1;
			if (element12.getText().equals(selectfirst)) {
				first = element12;
			}

			if (element12.getText().equals(selectsecond)) {
				second = element12;
			}

			act.keyDown(Keys.CONTROL).clickAndHold(first).clickAndHold(second).keyUp(Keys.CONTROL).build().perform();
		}
	}

	// login for SuiteManage
	public void SuiteMangeLogin(String userID, String password) {
		getElementByID("UserName").clear();
		getElementByID("UserName").sendKeys(userID);
		getElementByID("Password").clear();
		getElementByID("Password").sendKeys(password);
		getElementByName("SubmitButton").click();
	}

	public Boolean verifyMsg(String KeyToGetActualMsg, String ExpectedMsg) {
		try {

			// waitUntilElementToBeVisible(driver.findElement(By.xpath(Object.getProperty(KeyToGetActualMsg))),
			// 10);
			String Actual = driver.findElement(By.xpath(Object.getProperty(KeyToGetActualMsg))).getText();
			String actualMsg = (((Actual.trim()).replaceAll(" ", "")).replace("\n", "")).replace("\r", "");
			String expectedMsg = (ExpectedMsg.trim()).replaceAll(" ", "");
			if (actualMsg.equalsIgnoreCase(expectedMsg)) {
				Add_Log.info(TestCaseName + ":- Actual Message :-'" + Actual + "'");
				Add_Log.info(TestCaseName + ":- Expected Message :- '" + ExpectedMsg + "'");
				Add_Log.info(TestCaseName + ":- Actual Message and Expected Message --> 'Matched'");
				return true;
			} else {
				Add_Log.info(TestCaseName + ":- Actual Message :-'" + Actual + "'");
				Add_Log.info(TestCaseName + ":- Expected Message :- '" + ExpectedMsg + "'");
				Add_Log.info(TestCaseName + ":- Actual Message and Expected Message --> 'NOT Matching'");
				return false;
			}
		} catch (Throwable t) {
			Add_Log.info(TestCaseName + ":- Exception occurred in Verify Message Function");
			return false;
		}
	}

	/*
	 * @ Author : Harshal S. Kakade
	 * 
	 * @ param key1 : First part of xpath
	 * 
	 * @ param Value : Value from test data
	 * 
	 * @ param key2 : later part of xpath
	 * 
	 * @ Description : This one is used for dynamic xpath which contains varying
	 * string.
	 */
	public WebElement getElementByXPath(String Key1, String val, String key2) {
		// Check to avoid waste of time.
		try {
			if (driver.getCurrentUrl().equalsIgnoreCase(Param.getProperty("siteURL") + "en-us/Base/AccessDenied")) {
				logFailureAndAbortFurtherExecution(
						"Aborted the execution as application is throwing an error -'Access Denied'");
			}
		} catch (Exception e) {
			System.out.println("Ignore as it is ad-on to avoid waste of time");
		}

		// Actual Code
		try {
			// This block will find element using values of Key1, val and key2
			// from web page and return It.
			return driver.findElement(By.xpath(Object.getProperty(Key1) + val + Object.getProperty(key2)));
		} catch (Throwable t) {
			// If element not found on page then It will return null.
			Add_Log.info(TestCaseName + ":- Object not found for custom xpath");
			return null;
		}
	}

	public void duplicateSession(String emailID) throws SQLException {
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			Add_Log.info("Class Not found" + e.getMessage());
		}

		Connection objConn1 = DriverManager.getConnection(Param.getProperty("DBConnectionString"));

		Statement statment1 = objConn1.createStatement();
		String strSqlQuery1 = "update [Central].[dbo].[UserAuditLogs] set [Central].[dbo].[UserAuditLogs].IsActive=0 where [Central].[dbo].[UserAuditLogs].UserID=(Select [Central].[dbo].[users].UserID  from [Central].[dbo].[Users] where [Central].[dbo].[Users].EmailID='"
				+ emailID + "')";
		int rs = statment1.executeUpdate(strSqlQuery1);
		System.out.println(rs);
		statment1.close();
		objConn1.close();
	}

	/*
	 * @ Author : Harshal S. Kakade
	 * 
	 * @ param element : wait for Webelement to be clickable
	 * 
	 * @ Description : This one is used for waiting particular object to load
	 * for specific time.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void waitUntilElementToBeClickable(WebElement element) {
		try {
			Wait wait = new FluentWait(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
					.ignoring(Exception.class);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			// new WebDriverWait(driver,
			// 60).until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Element '" + (element) + "' not displayed after 30 Seconds of wait. ");
		}

	}

	/*
	 * @ Author : Harshal S. Kakade
	 * 
	 * @ param element : wait for Webelement to be clickable
	 * 
	 * @ param timeInSeconds : Time in seconds to wait for the element.
	 * 
	 * @ Description : This one is used for waiting particular object to load
	 * for specific time.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void waitUntilElementToBeClickable(WebElement element, int timeInSeconds) {
		try {
			Wait wait = new FluentWait(driver).withTimeout(timeInSeconds, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(Exception.class);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			// new WebDriverWait(driver,
			// 30).until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Element '" + (element) + "' not displayed after '" + timeInSeconds
					+ "' Seconds of wait. ");
		}

	}

	/*
	 * @ Author : Harshal S. Kakade
	 * 
	 * @ param element : wait for Webelement to be selectable
	 * 
	 * @ param timeInSeconds : Time in seconds to wait for the element.
	 * 
	 * @ Description : This one is used for waiting particular object to load
	 * for specific time.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void waitUntilElementToBeSelectable(WebElement element, int timeInSeconds) {
		try {
			Wait wait = new FluentWait(driver).withTimeout(timeInSeconds, TimeUnit.SECONDS)
					.pollingEvery(1, TimeUnit.SECONDS).ignoring(Exception.class);
			wait.until(ExpectedConditions.elementSelectionStateToBe(element, true));

		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Element '" + (element) + "' not displayed after '" + timeInSeconds
					+ "' Seconds of wait. ");
		}
	}

	/*
	 * @ Author : Harshal S. Kakade
	 * 
	 * @ param element : wait for Webelement to be clickable
	 * 
	 * @ param timeInSeconds : Time in seconds to wait for the element.
	 * 
	 * @ Description : This one is used for waiting particular object to load
	 * for specific time.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void waitUntilElementToBeSelected(WebElement element, int timeInSeconds) {
		try {
			Wait wait = new FluentWait(driver).withTimeout(timeInSeconds, TimeUnit.SECONDS)
					.pollingEvery(1, TimeUnit.SECONDS).ignoring(Exception.class);
			wait.until(ExpectedConditions.elementToBeSelected(element));
			// new WebDriverWait(driver,
			// 60).until(ExpectedConditions.elementToBeSelected(element));
		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Element '" + (element) + "' not selectable after '" + timeInSeconds
					+ "' Seconds of wait. ");
		}
	}

	/*
	 * @ Author : Harshal S. Kakade
	 * 
	 * @ param element : Webelement to retrieve the text
	 * 
	 * @ param msg : Text message to be present
	 * 
	 * @ Description : This one is used for waiting for particular text to be
	 * present.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void waitUntilTextToBePresentInElement(WebElement element, String msg) {
		try {
			Wait wait = new FluentWait(driver).withTimeout(20, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
					.ignoring(Exception.class);

			wait.until(ExpectedConditions.textToBePresentInElement(element, msg));
		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Either element-'" + (element) + "' or proper message-'" + msg
					+ "' not displayed after 20 Seconds of wait. ");
		}
	}

	/*
	 * @ Author : Harshal S. Kakade
	 * 
	 * @ param element : Webelement to retrieve the text
	 * 
	 * @ param msg : Text message to be present
	 * 
	 * @ param timeInSeconds : time to wait for the element.
	 * 
	 * @ Description : This one is used for waiting for particular text to be
	 * present.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void waitUntilTextToBePresentInElement(WebElement element, String msg, int timeInSeconds) {
		try {
			Wait wait = new FluentWait(driver).withTimeout(timeInSeconds, TimeUnit.SECONDS)
					.pollingEvery(1, TimeUnit.SECONDS).ignoring(Exception.class);

			wait.until(ExpectedConditions.textToBePresentInElement(element, msg));
		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Either element-'" + (element) + "' or proper message-'" + msg
					+ "' not displayed after '" + timeInSeconds + "' Seconds of wait. ");
		}
	}

	/*
	 * @ Author : Harshal S. Kakade
	 * 
	 * @ param element : Webelement to check visibility.
	 * 
	 * @ param timeInSeconds : time to wait for the element.
	 * 
	 * @ Description : This one is used for waiting for particular element to be
	 * visible.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void waitUntilElementToBeVisible(WebElement element, int timeInSeconds) {
		try {
			Wait wait = new FluentWait(driver).withTimeout(timeInSeconds, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(Exception.class);

			wait.until(ExpectedConditions.visibilityOf(element));
			// new WebDriverWait(driver,
			// 60).until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Element '" + (element) + "' not displayed after '" + timeInSeconds
					+ "' Seconds of wait. ");
		}
	}

	/**
	 * 
	 * @param MsgHeader
	 * @param Msg
	 */

	public void verifySuccessMsgDocView(String ExpectedResult) throws InterruptedException, Exception {
		try {

			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			// Wait for Success Message Window to display.
			waitUntilElementToBeVisible(getElementByXPath("SuccessMsgHeading"), 5);
			// waitUntilTextToBePresentInElement(getElementByXPath("SuccessMsgHeading"),
			// "Success !", 20);

			// Check for Success Heading displayed on Success Message
			// Window.
			// call verifySearchResults to verify results
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			String actualSuccessMsg = jse.executeScript("return arguments[0].innerHTML",
					driver.findElement(By.xpath(Object.getProperty("SuccessMsgHeading")))).toString();
			String actualMsg = jse.executeScript("return arguments[0].innerHTML",
					driver.findElement(By.xpath(Object.getProperty("SuccessMsg")))).toString();
			/*
			 * String actualSuccessMsg =
			 * driver.findElement(By.xpath(Object.getProperty(
			 * "SuccessMsgHeading"))).getText(); String actualMsg =
			 * driver.findElement(By.xpath(Object.getProperty("SuccessMsg"))).
			 * getText();
			 */

			String expectedSucessMsg = "Success !";
			if (actualSuccessMsg.equalsIgnoreCase(expectedSucessMsg)) {
				Add_Log.info(TestCaseName + ":- Success Message header(Success !) displayed Properly.");
			} else {
				// This function is to file captured failures in the reports.
				reportFailures("Success Message header(Success !) not displayed Properly.");

			}

			// Check for Proper Success Message Displayed.
			// String actualMsg =
			// driver.findElement(By.xpath(Object.getProperty("SuccessMsg"))).getText();
			if (actualMsg.equalsIgnoreCase(ExpectedResult)) {
				// Add_Log.info(TestCaseName + ":- Success Message displayed
				// Properly.");
				Add_Log.info(TestCaseName + ":- Success Message actual: '" + actualMsg + "' matched with expected '"
						+ ExpectedResult + "'");

			} else {
				// This function is to file captured failures in the reports.
				reportFailures(TestCaseName + ":- Success Message actual: '" + actualMsg
						+ "' is not matched with expected '" + ExpectedResult + "'");
			}

			// Close the success message window after verification
			try {
				getElementByXPath("Message_Close").click();
			} catch (Exception e) {
				Add_Log.info(TestCaseName + ":- Excpetion thrown during closing Success Message Window.");
			}
		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Excpetion thrown during Verification of Success Message");
		}
	}

	/*
	 * 
	 * @param ExpectedResult : Expected Result to verify
	 * 
	 * @ Description : This is to verify whether intended message displayed or
	 * not after an action.
	 */
	public void verifyConfirmationMessage(String MsgHeader, String Msg) {
		try {
			// Validate Delete Message Header
			waitUntilTextToBePresentInElement(getElementByXPath("ConfirmationMessageTitle"), MsgHeader, 10);
			if (!verifyMsg("ConfirmationMessageTitle", MsgHeader)) {
				// This function is to file captured failures in the reports.
				reportFailures("Confirmation Message header(" + MsgHeader + ") not displayed Properly.");
			} else {
				Add_Log.info(TestCaseName + ":- Confirmation Message header(" + MsgHeader + ") displayed Properly.");
			}

			// Validate Delete Message Content
			if (!verifyMsg("ConfirmationMessageContent", Msg)) {
				// This function is to file captured failures in the reports.
				reportFailures("Confirmation Message not displayed Properly.");
			} else {
				Add_Log.info(TestCaseName + ":- Confirmation Message displayed Properly.");
			}
		} catch (Exception e) {
			// This function is to file captured failures in the reports.
			reportFailures("Confirmation Message not displayed Properly.");
		}
	}

	/*
	 * 
	 * @ Description : This function is to report results in Excel file for each
	 * test data. after an action.
	 */
	public void reportDataResults() {
		try {
			if (Testskip) {
				// If found Testskip = true, Result will be reported as SKIP
				// against
				// data set line In excel sheet.
				SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet + 1, "SKIP");
				Add_Log.info(
						TestCaseName + " :- Reporting test data set line '" + (DataSet + 1) + "' as SKIP In excel.");

				Add_Log.info(TestCaseName + " :- *************************************************");
			} else if (Testfail) {
				// To make object reference null after reporting In report.
				s_assert = null;
				// Set TestCasePass = false to report test case as fail In excel
				// sheet.
				TestCasePass = false;
				// If found Testfail = true, Result will be reported as FAIL
				// against
				// data set line In excel sheet.
				SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet + 1, "FAIL");

				Add_Log.info(
						TestCaseName + " :- Reporting test data set line '" + (DataSet + 1) + "' as FAIL In excel.");

				Add_Log.info(TestCaseName + " :- *************************************************");
			} else {
				// If found Testskip = false and Testfail = false, Result will
				// be
				// reported as PASS against data set line In excel sheet.
				SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", DataSet + 1, "PASS");

				Add_Log.info(
						TestCaseName + " :- Reporting test data set line '" + (DataSet + 1) + "' as PASS In excel.");

				Add_Log.info(TestCaseName + " :- *************************************************");
			}
		} catch (Exception e) {
			System.out.println("Do Nothing");
		} finally {
			// At last make both flags as false for next data set.
			Testskip = false;
			Testfail = false;
			step_Number++;
		}
	}

	/*
	 * 
	 * @ Description : This function is to end test with reporting final result
	 * and closing the browser after an action.
	 */
	public void testClosure() {
		try {
			Add_Log.info("******************************** End of Execution for :- " + TestCaseName
					+ " *************************************");

			if (TestCasePass) {
				SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "PASS");
			} else {
				SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "FAIL");
			}
		} catch (Exception e) {
			System.out.println("Do Nothing");
		} finally {
			// Reset all flags and counters to default value.
			DataSet = -1;
			s_assert = null;
			TestCasePass = true;
			step_Number = 1;
			Testfail = false;

			// To Close the web browser at the end of test.
			closeWebBrowser();
		}
	}

	/*
	 * 
	 * @ Description : This function is to file captured failures in the
	 * reports.
	 */
	public void reportFailures(String msg) {
		Testfail = true;
		Add_Log.info(TestCaseName + ":- " + msg);
		s_assert.fail("\n" + msg);
	}

	/*
	 * 
	 * @ Description : This function is to file captured failures in the reports
	 * and then abort the further execution.
	 */
	public void logFailureAndAbortFurtherExecution(String msg) {
		Testfail = true;
		Add_Log.info(TestCaseName + ":- " + msg);
		Assert.fail("\n" + msg);
	}

	/*
	 * 
	 * @ Description : This function is to start test with intializing all files
	 * and drivers.
	 */
	public void startTest(Read_XLS FileAddress) throws Exception {
		// init();

		// To set SuiteTwo.xls file's path In FilePath Variable.
		FilePath = FileAddress;
		TestCaseName = this.getClass().getSimpleName();

		// SheetName to check CaseToRun flag against test case.
		SheetName = "TestCasesList";

		// Name of column In TestCasesList Excel sheet.
		ToRunColumnNameTestCase = "CaseToRun";

		// Name of column In Test Case Data sheets.
		ToRunColumnNameTestData = "DataToRun";

		// To check test case's CaseToRun = Y or N In related excel sheet.
		// If CaseToRun = N or blank, Test case will skip execution. Else It
		// will be executed.

		if (!SuiteUtility.checkToRunUtility(FilePath, SheetName, ToRunColumnNameTestCase, TestCaseName)) {
			// To report result as skip for test cases In TestCasesList
			// sheet.
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "SKIP");
			// To throw skip exception for this test case.
			throw new SkipException(
					TestCaseName + "'s CaseToRun Flag Is 'N' Or Blank. So Skipping Execution Of " + TestCaseName);
		}

		// To retrieve DataToRun flags of all data set lines from related
		// test
		// data sheet.
		TestDataToRun = SuiteUtility.checkToRunUtilityOfData(FilePath, TestCaseName, ToRunColumnNameTestData);

		// To Initialize browser.
		loadWebBrowser();
		Thread.sleep(5000);

		Add_Log.info("*************************** Execution Started For :- " + TestCaseName
				+ " *****************************");

		// To navigate to URL.
		driver.get(Param.getProperty("siteURL"));
	}

	/*
	 * 
	 * @ Description : This function is to scroll the webpage at top.
	 */
	public static void scrollPageToTop() {
		// Scroll page to make element visisble.

		for (int i = 0; i < 5; i++) {
			try {
				((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
				Thread.sleep(500);
			} catch (Throwable e) {
				System.out.println("IGNORE" + e.getLocalizedMessage() + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/*
	 * 
	 * @ Description : This function is to scroll the webpage at bottom.
	 */
	public void scrollPageToBottom() {
		// Scroll page to Bottom
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(500);
		} catch (Exception e) {
			System.out.println("IGNORE" + e.getLocalizedMessage());
		}
	}

	/*
	 * 
	 * @ Description : This function is click on the element.
	 */
	public void clickBy(WebElement element, String Key, int time) {
		// scrollPageToTop();
		waitUntilElementToBeClickable(element, time);
		try {
			element.click();
			Add_Log.info(TestCaseName + ":- Clicked on '" + Key + "'");
		} catch (Exception e) {
			reportFailures(TestCaseName + ":- Failed to click on '" + Key + "'\n" + e.getLocalizedMessage());
		}
	}

	// ******************************************************************************************************************************
	// Function Name: NumberOfExistenceFile
	//
	// Description: To get the Number Of Existence File
	// *****************************************************************************************************************************
	public int NumberOfExistenceFile(String strFolder) {

		int intNumberOfFiles = 0;

		File dir = new File(strFolder);
		File[] files = dir.listFiles();
		try {
			if (files == null || files.length == 0) {
				intNumberOfFiles = 0;
				System.out.println("Folder Is BLANK");
			} else {
				intNumberOfFiles = files.length;
				System.out.println("Total Number of Files are " + intNumberOfFiles);
			}
			return intNumberOfFiles;
		} catch (Exception e) {
			intNumberOfFiles = 0;
			System.out.println(e.getMessage());
		}
		return intNumberOfFiles;
	}

	// ******************************************************************************************************************************
	// Function Name: testNumberOfRowsInDownloadedFile
	// Last Modified:
	// Description: Verification of number of rows in downloaded file.
	// ******************************************************************************************************************************

	public int testNumberOfRowsInDownloadedFile(String downloadFilePath) {
		int lineNumberCount = 0;
		File fileName = new File(downloadFilePath);

		File dir = new File(downloadFilePath);
		File[] files = dir.listFiles();

		if (files == null || files.length == 0) {
			System.out.println("Folder Is BLANK");
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}

		fileName = lastModifiedFile;

		if (fileName.exists()) {
			FileReader FileIn = null;
			try {
				FileIn = new FileReader(fileName);
				LineNumberReader LNR = new LineNumberReader(FileIn);

				try {
					while (LNR.readLine() != null) {
						lineNumberCount = lineNumberCount + 1;
					}

				} catch (IOException e) {

					System.out.println("Unable to Read Line Number " + e.getMessage());
				}

			} catch (FileNotFoundException e) {
				System.out.println("Unable to Read File " + e.getMessage());
			}
		} else {
			System.out.println("File Does not exist");
		}
		return lineNumberCount;
	}

	/*
	 * @ Description : This function is enter the value.
	 */
	public void enterInto(WebElement element, String Key, String Value, int time) {
		waitUntilElementToBeClickable(element, time);
		try {
			element.clear();
			element.sendKeys(Value);
			Add_Log.info(TestCaseName + ":- Entered '" + Value + "' for " + Key);
		} catch (Exception e) {
			reportFailures(
					TestCaseName + ":- Failed to enter '" + Value + "for " + Key + "'\n" + e.getLocalizedMessage());
		}
	}

	/**
	 * To get matching text element from List of web elements
	 * 
	 * @param elements
	 * @param contenttext
	 * @return elementToBeReturned as WebElement
	 * @throws Exception
	 */
	public static WebElement getMachingTextElementFromList(List<WebElement> elements, String contenttext)
			throws Exception {
		WebElement elementToBeReturned = null;
		boolean status = false;
		if (elements.size() > 0) {
			try {
				for (WebElement element : elements) {
					scrollingToElementofAPage(element);
					if (element.getText().trim().replaceAll("\\s+", " ").equals(contenttext)) {
						elementToBeReturned = element;
						status = true;
						break;
					} else {
						status = false;
					}
				}
			} catch (Exception e) {
				if (status == false) {
					throw new Exception("Didn't find the correct text(" + contenttext + ")..! on the page", e);
				}
			}
		} else {
			throw new Exception("Unable to find list element...!");
		}
		return elementToBeReturned;
	}

	/**
	 * Switch to child window
	 * 
	 */
	public String switchToChildWindow(final WebDriver driver, String parentWindowHandle) throws Exception {
		Set<String> childWindows = driver.getWindowHandles();
		Iterator<String> iterator = childWindows.iterator();
		String childWindow = null;
		while (iterator.hasNext()) {
			String handle = iterator.next();
			if (!handle.equals(parentWindowHandle)) {
				driver.switchTo().window(handle);
				Thread.sleep(4000);
				Add_Log.info("Child window title: " + driver.getTitle());
				childWindow = handle;
				System.out.println("Child window object: " + childWindow);
			}
		}
		return childWindow;
	}

	public void VerifyErrorMessagevalidation(String ExpectedResult) throws InterruptedException {
		// Wait for Error Message Window to display.
		waitUntilElementToBeVisible(getElementByXPath("SuccessMsgHeading"), 5);
		// waitUntilTextToBePresentInElement(getElementByXPath("SuccessMsgHeading"),
		// "Error !", 20);
		// Check for Success Heading displayed on Success Message
		// Window.
		String actualErrorMsg = driver.findElement(By.xpath(Object.getProperty("SuccessMsgHeading"))).getText();
		String expectedErrorMsg = "Error !";

		if (actualErrorMsg.equalsIgnoreCase(expectedErrorMsg)) {
			Add_Log.info(TestCaseName + ":- Error Message header(Error !) displayed Properly.");
		} else {
			// This function is to file captured failures in the reports.
			reportFailures("Error Message header(Error !) not displayed Properly.");
		}

		// Check for Proper Success Message Displayed.
		String actualMsg = driver.findElement(By.xpath(Object.getProperty("SuccessMsg"))).getText();
		if (actualMsg.equalsIgnoreCase(ExpectedResult)) {
			// Add_Log.info(TestCaseName + ":- Success Message displayed
			// Properly.");
			Add_Log.info(TestCaseName + ":- Error Message actual: '" + actualMsg + "' matched with expected '"
					+ ExpectedResult + "'");
		} else {
			// This function is to file captured failures in the reports.
			reportFailures(TestCaseName + ":- Error Message actual: '" + actualMsg + "' is not matched with expected '"
					+ ExpectedResult + "'");
		}

		// Close the success message window after verification
		try {
			getElementByXPath("Message_Close").click();
		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Excpetion thrown during closing Error Message Window.");
		}
	}

	// Logout with conformation dialog
	public void logOutWithConfirmation() {
		try {
			// Scroll page to Top
			scrollPageToTop();
			Thread.sleep(2000);

			// Click on Signout menu
			clickBy(getElementByXPath("SignoutMenu"), "SignoutMenu", 10);

			// Click on Sign Out button
			clickBy(getElementByXPath("SignOutButton"), "SignOutButton", 8);
			Thread.sleep(5000);

			try {
				getElementByXPath("ct_popupYes").click();
				Add_Log.info("For DocView - Pop up confirmation dialog is shown and clicked on yes button");
			} catch (Exception e1) {
				Add_Log.info("For Doc View - Pop up confirmation dialog is not appeared");
			}

			// Check whether logout is successfully done or not
			try {
				waitUntilElementToBeClickable(driver.findElement(By.id(Object.getProperty("UserName"))), 10);
				Add_Log.info("Logout is done successfully.\n");
			} catch (Exception e1) {
				Add_Log.info("Logout is failed:-\n" + e1.getMessage());
			}

		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Logout Function - Failed");
			Add_Log.info("Details of the exception are:-\n" + e.getMessage());
		} finally {
			isAlreadyLogIn = false;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean waitUntilElementToBeDisplayed(WebElement element, int timeInSeconds) {
		boolean status = false;
		try {
			Wait wait = new FluentWait(driver).withTimeout(timeInSeconds, TimeUnit.SECONDS)
					.pollingEvery(1, TimeUnit.SECONDS).ignoring(Exception.class);

			wait.until(ExpectedConditions.visibilityOf(element));

			// new WebDriverWait(driver,
			// 60).until(ExpectedConditions.visibilityOf(element));
			status = true;
		} catch (Exception e) {
			Add_Log.info(TestCaseName + ":- Element '" + (element) + "' not displayed after '" + timeInSeconds
					+ "' Seconds of wait. ");
		}
		return status;
	}

	/** Added for all the background pop up handle ***/
	public void WaitforBackGroundPopup() {
		try {
			// Click on Yes button
			waitUntilElementToBeClickable(getElementByID("BackgroundPopup_Yes"), 15);
			getElementByID("BackgroundPopup_Yes").click();
			Add_Log.info(TestCaseName + ":- Clicked on 'ButtonYes'");

			// Click on OK button
			/*
			 * Thread.sleep(2000);
			 * getElementByID("BasicSearch_DialogBox_ButtonOK").click();
			 * Add_Log.info(TestCaseName+
			 * ":- Clicked on 'BasicSearch_DialogBox_ButtonOK'");
			 */
		} catch (Exception e2) {
			Add_Log.info("No Dialog box appeared");
		}
	}

	/**
	 * @param element
	 * @param timoutSec
	 * @param pollingSec
	 * @return
	 */
	public WebElement waitUntilElementToBePresent(WebElement element, int timoutSec, int pollingSec) {

		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver).withTimeout(timoutSec, TimeUnit.SECONDS)
				.pollingEvery(pollingSec, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class, TimeoutException.class)
				.ignoring(StaleElementReferenceException.class);

		for (int i = 0; i < 5; i++) {
			try {
				fWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='Loader_Spinner']")));
				fWait.until(ExpectedConditions.visibilityOf(element));
				fWait.until(ExpectedConditions.elementToBeClickable(element));
				// new WebDriverWait(driver,
				// 60).until(ExpectedConditions.elementToBeClickable(element));

			} catch (Exception e) {

				Add_Log.info("Element Not found trying again - " + element.toString().substring(70));
				e.printStackTrace();

			}
		}

		return element;

	}

	public void confirmPopUp() {

		try {
			waitUntilElementToBeClickable(getElementByXPath("dl_DocListSelectYesPopUp"), 10);
			String Comment = getElementByXPath("CommentDeleteMsgHeader").getText();
			Add_Log.info("Message Displayed in dialog: " + getElementByXPath("CommentDeleteMsgHeader").getText());
			getElementByXPath("dl_DocListSelectYesPopUp").click();
			Add_Log.info("Confirmation pop-up is displayed and clicked on continue.");

			Thread.sleep(1000);
		} catch (Exception e) {
			Add_Log.info("Confirmation pop-up is not displayed");
		}

	}

	// Function to delete all temp files
	public void deleteFile(File element) {
		if (element.isDirectory()) {
			for (File sub : element.listFiles()) {
				deleteFile(sub);
				Add_Log.info("Deleted temp file.");
			}
		}
		element.delete();
	}

	// Wait for element to click wehn page/part of the page is masked

	public void waitAndClickable(WebElement element) throws InterruptedException {
		int i;
		for (i = 1; i <= 3; i++) {
			try {
				element.click();
				System.out.println("Clicked after trying " + i + "th time!");
				break;
			} catch (Exception e) {

				// Keep waitiing
				Thread.sleep(1000);
			}

		}
		System.out.println("tried" + i + "th time!");
	}

	// Wait for element to get text when page is masked

	public String waitAndGetText(String locatorType, String key) throws InterruptedException {

		int j;
		String text = null;
		for (int i = 1; i <= 90; i++) {
			try {

				text = driver.findElement(getWebElement(locatorType, key)).getText();

				if (text.matches("-?\\d+(\\.\\d+)?") && !text.isEmpty()) {
					System.out.println("Got after trying " + i + "th time!");
					break;
				} else {
					// Added to move to catch
					j = 2 / 0;
				}

			} catch (Exception e) {
				// Keep waitiing
				Thread.sleep(1000);
			}

		}

		return text;
	}

	// waitforVisibilty of an element

	@SuppressWarnings("unchecked")
	public void waitForVisibilityOfanElement(String locatorType, String key) throws InterruptedException {
		WebElement e = driver.findElement(getWebElement(locatorType, key));
		waitUntilElementToBeClickable(e, 60);

	}

	public By getWebElement(String locatorType, String key) {

		By by;
		switch (locatorType) {
		case "id":
			by = By.id(key);
			break;
		case "name":
			by = By.name(key);
			break;
		case "cssSelector":
			by = By.cssSelector(key);
			break;
		case "xpath":
			by = By.xpath(key);
			break;
		case "linkText":
			by = By.linkText(key);
			break;
		default:
			by = By.id(key);
			break;

		}
		return by;

	}

	/*
	 * @ param GetActualCount : object to get actual count
	 * 
	 * @ param Expectedcount : Expected Count
	 * 
	 * @ Description : This one is used to verify the count displayed after any
	 * action.
	 */
	public Boolean verifyCount(String GetActualCount, String Expectedcount) {
		try {
			int Actual = Integer.parseInt(waitAndGetText("xpath", Object.getProperty(GetActualCount)));
			int ExpCount = Integer.parseInt(Expectedcount);
			if (Actual == ExpCount) {
				Add_Log.info(TestCaseName + ":- Actual Count :-'" + Actual + "'");
				Add_Log.info(TestCaseName + ":- Expected Count :- '" + ExpCount + "'");
				Add_Log.info(TestCaseName + ":- Actual Count and Expected Count --> 'Matched'");
				return true;
			} else {
				Add_Log.info(TestCaseName + ":- Actual Count :-'" + Actual + "'");
				Add_Log.info(TestCaseName + ":- Expected Count :- '" + ExpCount + "'");
				Add_Log.info(TestCaseName + ":- Actual Count and Expected Count --> 'NOT Matching'");
				return false;
			}
		} catch (Throwable t) {
			Add_Log.info(TestCaseName + ":- Exception occurred in Verify Message Function");
			return false;
		}
	}

	public boolean SortingAlphabeticalorder(String Key) {
		try {
			ArrayList<String> ActualList = new ArrayList<>();
			List<WebElement> elementList = driver.findElements(By.xpath(Object.getProperty(Key)));
			for (WebElement we : elementList) {
				ActualList.add(we.getText());
				System.out.println(ActualList);
			}
			ArrayList<String> sortedList = new ArrayList<>();
			for (String s : ActualList) {
				sortedList.add(s);
			}
			Collections.sort(sortedList, Collator.getInstance(Locale.ENGLISH));
			System.out.println(sortedList);
			if (sortedList.equals(ActualList)) {
				Add_Log.info("Sorted in alpabetical order->PASS");
			} else {
				reportFailures("Sorting is not in alpabetical order->FAIL");
				return false;
			}
		} catch (Exception e) {
			reportFailures("Sorting is not in alpabetical order->PASS");
			return false;
		}
		return true;
	}
}
