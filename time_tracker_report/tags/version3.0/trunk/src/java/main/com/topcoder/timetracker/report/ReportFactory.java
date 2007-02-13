/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * This class is factory and contains all the Report implementation registered by the application. The registration of
 * the Report implementation happens automatically during the loading of the class from the configuration information
 * looked up from the {@link com.topcoder.util.config.ConfigManager}.
 * <p/>
 * This class allows the fetching of a Report registered by Format and Category.
 * <p/>
 * This class is thread-safe as the instance members are not modifiable.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class ReportFactory {
    /**
     * This is the name of the {@link ConfigManager} namespace from which to lookup the Reports configuration.
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.report.Reports";
    /**
     * This is the name of the {@link ConfigManager} property from which to lookup the Reports configuration.
     */
    private static final String REPORT_CLASSES_PROPERTY = "ReportClasses";

    /**
     * This map contains all the registered Report implementation. The key for the Map is a string representing the
     * combination of the Format and the category of the Report. (example: HTML_TIME, HTML_EXPENSE etc.). The value in
     * the map will be an implementation of {@link Report}.
     * <p/>
     * This filed cannot be <tt>null</tt> and is initialized during the instance creation. This Map cannot hold
     * <tt>null</tt> keys or values and will not be empty.
     */
    private final Map reports = new HashMap();

    /**
     * Creates a new ReportFactory which loads its configuration from {@link ConfigManager}.
     *
     * @throws ReportConfigurationException if there are problems during the reading from the configuration file or
     *                                      loading the specified implementation of Report through reflection.
     * @throws ReportException              if the configuration contained a duplicate report
     */
    public ReportFactory() throws ReportException {
        loadConfiguration();
    }

    /**
     * Initializes the {@link #reports} Map with the information specified in the Configuration which is looked up from
     * the {@link ConfigManager} namespace {@link #NAMESPACE} in property {@link #REPORT_CLASSES_PROPERTY}.
     *
     * @throws ReportConfigurationException if there are exceptions during the loading from the configuration
     * @throws ReportException              if the configuration contained a duplicate Report
     */
    private void loadConfiguration() throws ReportException {
        final ConfigManager manager = ConfigManager.getInstance();
        final String[] reportClassNames;
        try {
            reportClassNames = manager.getStringArray(NAMESPACE, REPORT_CLASSES_PROPERTY);
        } catch (Exception e) {
            throw new ReportConfigurationException(
                "Unable to configure reports, as the needed namespace [" + NAMESPACE + "] is unknown in ConfigManger.",
                e);
        }
        if (reportClassNames == null || reportClassNames.length == 0) {
            throw new ReportConfigurationException("No Reports have been defined in the configuration (namespace ["
                + NAMESPACE + "], property [" + REPORT_CLASSES_PROPERTY + "]) .");
        }

        for (int i = 0; i < reportClassNames.length; i++) {
            final Report report = createReport(reportClassNames[i]);
            final String key = report.getFormat() + "_" + report.getCategory().getCategory();
            if (reports.containsKey(key)) {
                throw new ReportException("There is already a report of format [" + report.getFormat()
                    + "] and category [" + report.getCategory() + "] registered.");
            } else {
                reports.put(key, report);
            }
        }

    }

    /**
     * Returns the Report instance registered for the specified Format and ReportCategory. If no Report implementation
     * is found registered with the specified registration name, then a ReportException will be thrown, saying the
     * particular report requested does not exist.
     * <p/>
     * <p/>
     *
     * @param format   the format of the Report expected
     * @param category the category of the Report
     *
     * @return Report for the specified format and category
     *
     * @throws ReportNotFoundException  if no Report is found registered for the Format and category specified
     * @throws NullPointerException     if any arg is <tt>null</tt>
     * @throws IllegalArgumentException if the format passed is an empty (trim'd) String
     */
    public Report getReport(final String format, final ReportCategory category) throws ReportNotFoundException {
        if (format == null) {
            throw new NullPointerException("The parameter named [format] was null.");
        }
        if (category == null) {
            throw new NullPointerException("The parameter named [category] was null.");
        }
        if (format.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter named [format] was an empty String.");
        }

        final String key = format + "_" + category.getCategory();
        final Report report = (Report) reports.get(key);
        if (report == null) {
            throw new ReportNotFoundException("The specified report for format [" + format + "] and category ["
                + category + "] was not registered with this ReportFactory.");
        }
        return report;
    }

    /**
     * This method creates a new {@link Report} instance by reflection.
     *
     * @param className the class name of the Report to be created
     *
     * @return the Report instance created
     *
     * @throws ReportConfigurationException in case the Report cannot be instantiated due to the implementation class
     *                                      not found, no public no-arg constructor does exist in the class or the
     *                                      constructor invocation throws an exception
     */
    private static Report createReport(final String className) throws ReportConfigurationException {
        //lookup and load Report implementation class

        //lookup impl class name
        if (className == null) {
            throw new ReportConfigurationException(
                "There was a null class name in the report classes configuration (namespace[" + NAMESPACE
                    + "], property [" + REPORT_CLASSES_PROPERTY + "]).");
        }
        if (className.trim().length() == 0) {
            throw new ReportConfigurationException(
                "There was an empty class name in the report classes configuration (namespace[" + NAMESPACE
                    + "], property [" + REPORT_CLASSES_PROPERTY + "]).");
        }

        //load impl class
        final Class implClass;
        try {
            implClass = Class.forName(className);
        } catch (Throwable t1) {
            throw new ReportConfigurationException(
                "Unable to load implementation class [" + className + "] for Report.", t1);
        }
        final Constructor constructor;
        try {
            constructor = implClass.getConstructor(null);
        } catch (NoSuchMethodException e) {
            throw new ReportConfigurationException("Report implementation class [" + implClass .getName()
                + "] did not have a public no-arg constructor.", e);

        }

        //create Report instance
        final Report report;
        try {
            report = (Report) constructor.newInstance(null);
        } catch (ClassCastException e) {
            throw new ReportConfigurationException("the Report implementation class [" + implClass.getName()
                + "] does not implement Report.", e);
        } catch (Throwable t) {
            throw new ReportConfigurationException("the Report implementation class [" + implClass .getName()
                + "] could not be instantiated, constructor invocation failed.", t);
        }
        return report;
    }
}
