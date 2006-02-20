/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import org.apache.cactus.JspTestCase;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Comparator;


/**
 * This class is the base class for all unit test classes for the Time Tracker Report component, that depend on properly
 * configured environment. It performs the setup necessary for the Time Tracker Report component to work properly.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseTimeTrackerReportTest extends JspTestCase {
    /**
     * This is the {@link ConfigManager} namespace of the Informix DBConnectionFactory configuration.
     */
    protected static final String INFORMIX_DB_CONNECTION_FACTORY_NAMESPACE = "com.topcoder.timetracker.report.Informix";

    /**
     * This is the {@link ConfigManager} namespace of the default Time Tracker Report configuration.
     */
    protected static final String DEFAULT_CONFIGURATION_NAMESPACE
        = "com.topcoder.timetracker.report.DefaultConfiguration";
    /**
     * This is the {@link ConfigManager} namespace of the custom Time Tracker Report configuration.
     */
    protected static final String CUSTOM_CONFIGURATION_NAMESPACE
        = "com.topcoder.timetracker.report.CustomConfiguration";
    /**
     * This is the {@link ConfigManager} namespace of the Queries configuration.
     */
    protected static final String QUERIES_CONFIGURATION_NAMESPACE
        = "com.topcoder.timetracker.report.QueriesConfiguration";
    /**
     * This is the {@link ConfigManager} namespace of the filters configuration.
     */
    protected static final String FILTERS_CONFIGURATION_NAMESPACE
        = "com.topcoder.timetracker.report.FiltersConfiguration";
    /**
     * This is the {@link ConfigManager} namespace of the  columns configuration.
     */
    protected static final String COLUMNS_CONFIGURATION_NAMESPACE
        = "com.topcoder.timetracker.report.ColumnsConfiguration";
    /**
     * This is the {@link ConfigManager} namespace of the column types configuration.
     */
    protected static final String COLUMN_TYPES_CONFIGURATION_NAMESPACE = "com.topcoder.timetracker.report.ColumnTypes";
    /**
     * This is the {@link ConfigManager} namespace of the Reports configuration.
     */
    protected static final String REPORTS_CONFIGURATION_NAMESPACE = "com.topcoder.timetracker.report.Reports";
    /**
     * This is the {@link ConfigManager} namespace of the dbhandlers configuration.
     */
    protected static final String DBHANDLERS_CONFIGURATION_NAMESPACE = "com.topcoder.timetracker.report.DBHandlers";
    /**
     * This is the filename of the config file containing the default configuration used in the test cases.
     */
    protected static final String DEFAULT_TIME_TRACKER_REPORT_CONFIGURATION = "Time_Tracker_Report.xml";
    /**
     * This is the filename of the file containing the test data to be inserted into the database (using DBUnit, see
     * {@link UnitTests.TimeTrackerDBSetup}) as test setup.
     */
    protected static final String DEFAULT_DATABASE_CONTENT_FILE_NAME = "full.xml";
    /**
     * This is the name of the {@link ConfigManager} property containing the Informix database connection (used in test
     * setup).
     *
     * @see UnitTests.TimeTrackerDBSetup
     */
    protected static final String INFORMIX_CONNECTION_PROPERTY_NAME = "InformixConnection";
    /**
     * This is a {@link Comparator} that is used to sort the HTML table rows in the report render results.
     * <p/>
     * This sorting needs to be made as the {@link java.sql.ResultSet} row order is not assured, and can vary between
     * subsequent SQL queries or different database machines. To make a HTML result comparable to an expected HTML
     * result loaded from a file, all TABLE rows are sorted before comparing the HTML result.
     *
     * @see #checkContentsAreEqual(String, String)
     */
    protected static final Comparator TABLE_ROW_SORTING_COMPARATOR = new TableRowSortingComparator();

    /**
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        loadTimeTrackerReportConfiguration(DEFAULT_TIME_TRACKER_REPORT_CONFIGURATION);
    }

    /**
     * This method does the cleanup after test run.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        clearConfig();
    }

    /**
     * This method removes all Time Tracker Report-specific configuration namespaces plus the namespaces of dependant
     * components from the ConfigManager singleton of this class' classloader and then loads the configuration with the
     * given filename/classloader path into the ConfigManager instance.
     *
     * @param filename the config file to be loaded as new File Based Cache configuration
     *
     * @throws Exception            in case some unexpected Exception occurs
     * @throws NullPointerException in case the given filename is <tt>null</tt>
     */
    protected void loadTimeTrackerReportConfiguration(final String filename) throws Exception {
        if (filename == null) {
            throw new NullPointerException("The parameter named [filename] was null");
        }

        // clear config manager data
        clearConfig();

        // load the configuration for component,
        ConfigManager.getInstance().add(filename);
    }

    /**
     * This method removes all namespaces that are used by the Time Tracker Report component in testcases from the
     * ConfigManager singleton instance of this class' classloader.
     */
    protected void clearConfig() {
        removeNamespace(INFORMIX_DB_CONNECTION_FACTORY_NAMESPACE);
        removeNamespace(DEFAULT_CONFIGURATION_NAMESPACE);
        removeNamespace(CUSTOM_CONFIGURATION_NAMESPACE);
        removeNamespace(QUERIES_CONFIGURATION_NAMESPACE);
        removeNamespace(FILTERS_CONFIGURATION_NAMESPACE);
        removeNamespace(COLUMNS_CONFIGURATION_NAMESPACE);
        removeNamespace(COLUMN_TYPES_CONFIGURATION_NAMESPACE);
        removeNamespace(REPORTS_CONFIGURATION_NAMESPACE);
        removeNamespace(DBHANDLERS_CONFIGURATION_NAMESPACE);
    }

    /**
     * This method removes the given namespace from the ConfigManager singleton instance of this class' classloader.
     *
     * @param namespace the namespace to be removed from the ConfigManager, must not be <tt>null</tt>
     *
     * @throws NullPointerException in case the given namespace was <tt>null</tt>
     */
    protected void removeNamespace(final String namespace) {
        if (namespace == null) {
            throw new NullPointerException("The parameter named [namespace] was null");
        }

        final ConfigManager cm = ConfigManager.getInstance();
        if (cm.existsNamespace(namespace)) {
            try {
                cm.removeNamespace(namespace);
            } catch (UnknownNamespaceException ignored) {
                // This may happen if the config namespace to be removed did not exist
                // (which only can occur because of threading issues, as we check for
                // namespace existence before removing), but as the result is the
                // desired ConfigManager state (the namespace not existing any more
                // in CM), we can ignore this
            }
        }
    }

    /**
     * This method checks whether the given actualResult is equal to the expected result loaded from the file at the
     * given path.
     * <p/>
     * This check is performed somewhat complex, as the order of the rows may vary between subsequent database queries
     * and thus expected and actual result need to be re-ordered before comparing them. So the comparison performed is
     * actually a table-row-order-insensitive comparison, which checks whether both actual and expected result have the
     * same structure and the same table rows, without taking table row order into account.
     *
     * @param pathToExpectedResultFile the path to the file that contains the expected result
     * @param actualResult             the actual result as XML String to be checked for row-order-insensitve equality
     *                                 to the expected result
     *
     * @throws Exception in case an unexpected Exception occurs
     * @throws junit.framework.AssertionFailedError
     *                   in case the given actual result is not equal to the expected one loaded from the XML file
     */
    protected void checkContentsAreEqual(final String pathToExpectedResultFile, final String actualResult)
        throws Exception {
        final String expected = readResourceIntoString(pathToExpectedResultFile);
        XMLUnit.setIgnoreWhitespace(true);
        final Document expectedDocument = XMLUnit.buildControlDocument(expected);
        final Document actualDocument = XMLUnit.buildControlDocument(actualResult);

        // This needs to be done to make the comparison row-order insensitive
        // As the queries executed do not contain any ORDER BY clauses, the
        // order of the rows returned from the database is unspecified
        // and may even change between subsequent queries.
        sortRowsInTableElement(expectedDocument.getDocumentElement());
        sortRowsInTableElement(actualDocument.getDocumentElement());
        final Diff diff = XMLUnit.compareXML(expectedDocument, actualDocument);
        assertTrue(diff.toString(), diff.identical());
    }

    /**
     * This method sorts the table rows of a Table HTML element alphabetically. In case the given element does not
     * contain HTML table wows, no sorting occurs, but each of the child elements of the given element will be sorted by
     * a recursive call to this method.
     * <p/>
     * This method is needed as the order of the results returned by the configured statements is not guaranteed by the
     * database, and thus the comparison of actual and expected rendering result would fail in case the order of the
     * result returned is different from the order expected. As the order of the result is not specified by the CS,
     * order insensitive comparison suffices to check the achievement of the component requirements.
     *
     * @param element the element to be sorted in case it is a table containing multiple rows.
     */
    protected void sortRowsInTableElement(final Node element) {
        final NodeList childNodes = element.getChildNodes();
        if (childNodes.getLength() > 0) {
            //this the recursion
            for (int i = childNodes.getLength() - 1; i >= 0; i--) {
                final Node node = childNodes.item(i);
                sortRowsInTableElement(node);
            }

            //in case we have table sort the child elements
            if ("TABLE".equalsIgnoreCase(element.getLocalName())) {
                final Node[] toBeSorted = new Node[childNodes.getLength()];
                for (int i = childNodes.getLength() - 1; i >= 0; i--) {
                    final Node node = childNodes.item(i);
                    toBeSorted[i] = node;
                    element.removeChild(node);
                }

                Arrays.sort(toBeSorted, TABLE_ROW_SORTING_COMPARATOR);
                for (int i = 0; i < toBeSorted.length; i++) {
                    element.appendChild(toBeSorted[i]);
                }
            }
        }
    }

    /**
     * This method is used by {@link #TABLE_ROW_SORTING_COMPARATOR} to convert an {@link Element} into its XML {@link
     * String} representation, which is then used for comparison of two {@link Element}s.
     *
     * @param element the element to be converted into its XML String representation
     *
     * @return the XML String representation of the given {@link Element}
     */
    private static String elementToXmlFragmentString(final Element element) {
        final StringWriter stringWriter = new StringWriter();
        final OutputFormat outputFormat = new OutputFormat((String) null, null, false);
        outputFormat.setOmitXMLDeclaration(true);
        final XMLSerializer ds = new XMLSerializer(stringWriter, outputFormat);

        try {
            ds.serialize(element);
        } catch (IOException e) {
            fail("Serialization error in DOM node sorting");
        }
        return stringWriter.toString();
    }

    /**
     * This method is used to load resource file available in the classpath into a String.
     *
     * @param filename the name of the file to be loaded using the {@link ClassLoader} of this class
     *
     * @return the content of the file
     *
     * @throws Exception in case an I/O error occurs, e.g. the file cannot be found
     */
    private String readResourceIntoString(final String filename) throws Exception {
        final InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
        final StringBuffer expectedBuffer = new StringBuffer();
        final InputStreamReader inputStreamReader = new InputStreamReader(stream, "UTF-8");
        try {
            final char [] buffer = new char[4096];
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

    /**
     * This is a {@link Comparator} that is used to sort the HTML table rows in the report render results.
     * <p/>
     * This sorting needs to be made as the {@link java.sql.ResultSet} row order is not assured, and can vary between
     * subsequent SQL queries or different database machines. To make a HTML result comparable to an expected HTML
     * result loaded from a file, all TABLE rows are sorted before comparing the HTML result.
     *
     * @see BaseTimeTrackerReportTest#checkContentsAreEqual(String, String)
     */
    private static class TableRowSortingComparator implements Comparator {
        /**
         * Compares the two given Objects which are expected to be of type {@link Element}.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         *
         * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or
         *         greater than the second.
         *
         * @throws ClassCastException if the arguments' types prevent them from being compared by this Comparator.
         */
        public int compare(final Object o1, final Object o2) {
            final Node node1 = (Node) o1;
            final Node node2 = (Node) o2;
            final String node1String;
            final String node2String;
            if (node1 instanceof Element) {
                final Element element1 = (Element) node1;
                if ("TH".equalsIgnoreCase(element1.getLocalName())) {
                    return -1;
                }
                node1String = elementToXmlFragmentString(element1);
            } else {
                node1String = node1.getNodeValue();
            }

            if (node2 instanceof Element) {
                final Element element2 = (Element) node2;
                if ("TH".equalsIgnoreCase(element2.getLocalName())) {
                    return 1;
                }
                node2String = elementToXmlFragmentString(element2);
            } else {
                node2String = node2.getNodeValue();
            }
            return node1String.compareTo(node2String);
        }
    }
}
