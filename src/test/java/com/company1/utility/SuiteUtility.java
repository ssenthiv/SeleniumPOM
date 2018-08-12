//******************************************************************************************************************************
//Script Name: SuiteUtility
//Test Cases Covered: Master Class
//Developed By: Srinivas Anand
//Created Date: 03-Sep-2014
//Last Modified:
//Last Modified By:
//Description: To Check from excel whether to run the test or not
//******************************************************************************************************************************

package com.company1.utility;

public class SuiteUtility {

	public static boolean checkToRunUtility(Read_XLS xls, String sheetName, String ToRun, String testSuite) {

		boolean Flag = false;
		if (xls.retrieveToRunFlag(sheetName, ToRun, testSuite).equalsIgnoreCase("y")) {
			Flag = true;
		} else {
			Flag = false;
		}
		return Flag;
	}

	public static String[] checkToRunUtilityOfData(Read_XLS xls, String sheetName, String ColName) {
		return xls.retrieveToRunFlagTestData(sheetName, ColName);
	}

	public static Object[][] GetTestDataUtility(Read_XLS xls, String sheetName) {

		return xls.retrieveTestData(sheetName);

	}

	public static boolean WriteResultUtility(Read_XLS xls, String sheetName, String ColName, int rowNum,
			String Result) {
		return xls.writeResult(sheetName, ColName, rowNum, Result);
	}

	public static boolean WriteResultUtility(Read_XLS xls, String sheetName, String ColName, String rowName,
			String Result) {
		return xls.writeResult(sheetName, ColName, rowName, Result);
	}

}
