/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.report.accuracytests;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

import org.apache.cactus.JspTestCase;
import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.w3c.dom.Document;

import com.cronos.timetracker.report.ReportCategory;
import com.cronos.timetracker.report.ReportDisplayTag;
import com.cronos.timetracker.report.ReportType;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Simple {@link ReportDisplayTag} rendering tests.
 * It renders time, expense and time/expense report for project and assert the result.
 * </p>
 *
 * @author kr00tki
 * @version 2.0
 */
public class RenderingTest extends JspTestCase {
    /**
     * This is the dataSet holding the test data read from XML file.
     */
    private static IDataSet fullDataSet;

    /**
     * This is the database connection used for inserting test data and cleaning up.
     */
    private DatabaseConnection databaseConnection;

    /**
     * The ReportDisplayTag to test on.
     */
    private ReportDisplayTag tag = null;

    /**
     * Sets up the test environment on the client side.
     *
     * @param theRequest ignored.
     * @throws Exception to JUnit.
     */
    public void begin(WebRequest theRequest) throws Exception {
        AccuracyBaseTest.loadConfig();
        AccuracyBaseTest.clearTables();

        Connection conn = AccuracyBaseTest.getConnection();

        // write data
        databaseConnection = new DatabaseConnection(conn);
        fullDataSet = new FlatXmlDataSet(getClass().getClassLoader().getResourceAsStream("accuracyTests/full.xml"));
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, fullDataSet);
    }

    /**
     * Clears after the test.
     *
     * @param resp ignored.
     * @throws Exception to JUnit.
     */
    public void end(WebResponse resp) throws Exception {
        // clear data
        try {
            DatabaseOperation.DELETE_ALL.execute(databaseConnection, fullDataSet);
        } finally {
            databaseConnection.close();
        }
        AccuracyBaseTest.clearCM();
    }

    /**
     * Sets up the test environment on the server side.
     *
     * @param theRequest ignored.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        // fillTables();
        AccuracyBaseTest.clearCM();
        ConfigManager.getInstance().add(AccuracyBaseTest.DB_CONFIG_FILE);
        ConfigManager.getInstance().add("accuracyTests/TimeTrackerReport_render.xml");

        tag = new ReportDisplayTag();
        tag.setPageContext(pageContext);
        tag.setType("type");
        tag.setCategory("category");
        tag.setProjectFilter("project");
        tag.setSortColumns("sort");
        tag.setStartDateFilter("start");
        tag.setEndDateFilter("end");
        tag.setCompanyFilter("company");
        tag.setNamespace("Accuracy_render");
    }

    /**
     * Set up for the time report rendering test. It sets up request attributes.
     *
     * @param theRequest the reguest.
     * @throws Exception to JUnit.
     */
    public void beginRenderTimeReport(WebRequest theRequest) throws Exception {
        theRequest.addParameter("type", ReportType.PROJECT.getType());
        theRequest.addParameter("category", ReportCategory.TIME.getCategory());
        theRequest.addParameter("client", "client1");
        theRequest.addParameter("company", "1");
        theRequest.addParameter("project", "Self-Service");
        theRequest.addParameter("sort", "DATE DESC");
        theRequest.addParameter("start", "01-01-2001");
        theRequest.addParameter("end", "01-01-2007");
    }

    /**
     * Renders the time report.
     *
     * @throws Exception to JUnit.
     */
    public void testRenderTimeReport() throws Exception {
        tag.doStartTag();
    }

    /**
     * The end of the time report test. It checks if content match the expected.
     *
     * @param response the web response.
     * @throws Exception to JUnit.
     */
    public void endRenderTimeReport(WebResponse response) throws Exception {
        checkRenderResult(response.getText(), "time_result.xml");
    }

    /**
     * Set up for the time-expense report rendering test. It sets up request attributes.
     *
     * @param theRequest the reguest.
     * @throws Exception to JUnit.
     */
    public void beginRenderTimeExpenseReport(WebRequest theRequest) throws Exception {
        theRequest.addParameter("type", ReportType.PROJECT.getType());
        theRequest.addParameter("category", ReportCategory.TIMEEXPENSE.getCategory());
        theRequest.addParameter("client", "client1");
        theRequest.addParameter("company", "1, 2");
        theRequest.addParameter("project", "Self-Service");
        theRequest.addParameter("sort", "HOURS DESC, AMOUNT DESC");
        theRequest.addParameter("start", "01-01-2001");
        theRequest.addParameter("end", "01-01-2007");
    }

    /**
     * Renders the time-expense report.
     *
     * @throws Exception to JUnit.
     */
    public void testRenderTimeExpenseReport() throws Exception {
        tag.doStartTag();
    }

    /**
     * The end of the time-expense report test. It checks if content match the expected.
     *
     * @param response the web response.
     * @throws Exception to JUnit.
     */
    public void endRenderTimeExpenseReport(WebResponse response) throws Exception {
        checkRenderResult(response.getText(), "timeexpense_result.xml");
    }

    /**
     * Set up for the expense report rendering test. It sets up request attributes.
     *
     * @param theRequest the reguest.
     * @throws Exception to JUnit.
     */
    public void beginRenderExpenseReport(WebRequest theRequest) throws Exception {
        theRequest.addParameter("type", ReportType.PROJECT.getType());
        theRequest.addParameter("category", ReportCategory.EXPENSE.getCategory());
        theRequest.addParameter("client", "client1");
        theRequest.addParameter("company", "1, 2");
        theRequest.addParameter("project", "Self-Service");
        theRequest.addParameter("sort", "AMOUNT DESC");
        theRequest.addParameter("start", "01-01-2001");
        theRequest.addParameter("end", "01-01-2007");
    }

    /**
     * Renders the expense report.
     *
     * @throws Exception to JUnit.
     */
    public void testRenderExpenseReport() throws Exception {
        tag.doStartTag();
    }

    /**
     * The end of the expense report test. It checks if content match the expected.
     *
     * @param response the web response.
     * @throws Exception to JUnit.
     */
    public void endRenderExpenseReport(WebResponse response) throws Exception {
        checkRenderResult(response.getText(), "expense_result.xml");
    }

    /**
     * Checks if rendered output match the expected result.
     *
     * @param rendered the output.
     * @param filename the name of the file with expected result.
     * @throws Exception to JUnit.
     */
    private void checkRenderResult(String rendered, String filename) throws Exception {
        System.out.println(rendered);
        rendered = "<BODY>" + rendered + "</BODY>";
        String expected = readResourceIntoString("accuracyTests/" + filename);

        XMLUnit.setIgnoreWhitespace(true);
        Document expectedDocument = XMLUnit.buildControlDocument(expected);
        Document actualDocument = XMLUnit.buildControlDocument(rendered);

        Diff diff = XMLUnit.compareXML(expectedDocument, actualDocument);
        assertTrue(diff.toString(), diff.identical());
    }

    /**
     * Reads the file into string.
     *
     * @param filename the name of the file.
     * @return the file content.
     * @throws Exception to JUnit.
     */
    private String readResourceIntoString(String filename) throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
        StringBuffer expectedBuffer = new StringBuffer();
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        try {
            char [] buffer = new char[4096];
            int bytesRead;
            while ((bytesRead = inputStreamReader.read(buffer)) != -1) {
                expectedBuffer.append(buffer, 0, bytesRead);
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return expectedBuffer.toString();
    }
}
