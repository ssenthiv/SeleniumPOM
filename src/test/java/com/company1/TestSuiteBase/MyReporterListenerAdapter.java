package com.company1.TestSuiteBase;

import java.util.List;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

public class MyReporterListenerAdapter extends SuiteBase implements IReporter {
	public void generateReport(List<XmlSuite> xml, List<ISuite> suites, String outdir) {
	}
}