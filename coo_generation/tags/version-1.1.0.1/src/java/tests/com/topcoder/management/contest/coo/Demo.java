/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.impl.DefaultCOOReportGenerator;
import com.topcoder.management.contest.coo.impl.TestHelper;
import com.topcoder.management.contest.coo.impl.componentmanager.DBComponentManager;
import com.topcoder.management.contest.coo.serializer.PDFCOOReportSerializer;
import com.topcoder.management.contest.coo.serializer.XMLCOOReportSerializer;

/**
 *
 * <p>
 * Demo test for COO generator component.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * //create DefaultCOOReportGenerator from configuration
 * DefaultCOOReportGenerator generator = new DefaultCOOReportGenerator(configuration);
 *
 * //Generate COO Report based on the given project id.
 * COOReport report = generator.generateCOOReport(13);
 *
 * //create PDF report Serializer from configuration
 * COOReportSerializer pdfSerializer = new PDFCOOReportSerializer(pdfSerializerConfig);
 * //serialize to byte array
 * pdfSerializer.serializeCOOReportToByteArray(report);
 * //or serialize to PDF report file.
 * serializeCOOReportToFile(report, &quot;report.pdf&quot;);
 *
 * //create XML report Serializer from configuration
 * COOReportSerializer xmlSerializer = new XMLCOOReportSerializer(pdfSerializerConfig);
 * //serialize to byte array
 * xmlSerializer.serializeCOOReportToByteArray(report);
 * //or serialize to XML report file.
 * serializeCOOReportToFile(report, &quot;report.xml&quot;);
 *
 * //create ComponentManager from configuration
 * ComponentManager manager = new DBComponentManager(componentManagerConfig);
 * //add component from excel file.
 * manager.addComponents(&quot;third_party_list.xls&quot;);
 * </pre>
 *
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * <p>
     * set up test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {
        TestHelper.executeSqlFile("test_files/clean.sql");
        TestHelper.executeSqlFile("test_files/insert.sql");
    }

    /**
     * <p>
     * tear down test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void tearDown() throws Exception {
        TestHelper.executeSqlFile("test_files/clean.sql");
    }
    /**
     * the main demo method.
     *
     * @throws Exception to JUnit.
     */
    public void testDemo() throws Exception {
        // create COOReportGenerator implementation
        // assume configuration already exists
        ConfigurationObject pdfSerializerConfig = TestHelper.getConfiguration("test_files/config.xml");
        COOReportGenerator generator = new DefaultCOOReportGenerator(pdfSerializerConfig);

        // generate COOReport for projectId = 1
        COOReport report = generator.generateCOOReport(1);
        // serialize the report to PDF
        // assume pdfSerializerConfig already exists
        // assume that "Templates.xml" is used as the template
        pdfSerializerConfig = TestHelper.getConfiguration("test_files/serializer.xml");
        
        COOReportSerializer pdfSerializer = new PDFCOOReportSerializer(pdfSerializerConfig);

        // serialize to file: "project.pdf"
        pdfSerializer.serializeCOOReportToFile(report, "test_files/project.pdf");

        // serialize to byte array
        pdfSerializer.serializeCOOReportToByteArray(report);

        // serialize the report to XML
        // assume xmlSerializerConfig already exists
        // assume that "XML template.xml" is used as the template
        ConfigurationObject xmlSerializerConfig = TestHelper.
        getConfiguration("test_files/serializer_2.xml");
        COOReportSerializer xmlSerializer = new XMLCOOReportSerializer(xmlSerializerConfig);

        // serialize to file: "project.xml"
        xmlSerializer.serializeCOOReportToFile(report, "test_files/project.xml");
        // serialize to default file
        xmlSerializer.serializeCOOReportToFile(report, null);
        // the result would be stored in file: "COO_13.xml"
        TestHelper.deleteFile("COO_13.xml");

        // serialize to byte array
        xmlSerializer.serializeCOOReportToByteArray(report);

        // exporting the third party list XML file into database
        // assume componentManagerConfig already exists
        ConfigurationObject componentManagerConfig = TestHelper.getConfiguration("test_files/componentManager.xml");

        ComponentManager manager = new DBComponentManager(componentManagerConfig);
        // add the list defined in "third_party_list.xls" to the database
 //       manager.addComponents("test_files/third_party_list.xls");
    }
}
