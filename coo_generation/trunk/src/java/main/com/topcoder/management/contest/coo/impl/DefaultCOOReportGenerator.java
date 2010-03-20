/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.COOReportGenerator;
import com.topcoder.management.contest.coo.COOReportGeneratorException;
import com.topcoder.management.contest.coo.Component;
import com.topcoder.management.contest.coo.ComponentDependency;
import com.topcoder.management.contest.coo.ComponentDependencyExtractor;
import com.topcoder.management.contest.coo.ComponentDependencyExtractorException;
import com.topcoder.management.contest.coo.ComponentManager;
import com.topcoder.management.contest.coo.ComponentManagerException;
import com.topcoder.management.contest.coo.ContestData;
import com.topcoder.management.contest.coo.ContestDataRetriever;
import com.topcoder.management.contest.coo.ContestDataRetrieverException;
import com.topcoder.management.contest.coo.DependencyType;
import com.topcoder.management.contest.coo.Helper;
import com.topcoder.management.contest.coo.InvalidContestCategoryException;
import com.topcoder.util.log.Log;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * This class is the default implementation of <code>COOReportGenerator</code>
 * interface.
 * </p>
 * <p>
 * This class utilizes the contest data retriever to retrieve the contest data,
 * component dependency extractor to extract component dependency information
 * and component manager to retrieve the component information.
 * </p>
 * <p>
 * This class would retrieve the build dependency file from the remote SVN
 * location located in configurable base URL. This class supports configurable
 * user name & password to access the SVN.
 * </p>
 *
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
 * </pre>
 *
 * </p>
 * <p>
 * <strong>Thread safety:</strong> The thread safety of this class depends on
 * the thread safety of the dependent classes used. If the dependent classes are
 * thread safe, then this class is thread safe since it is immutable.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultCOOReportGenerator implements COOReportGenerator {
    /**
     * <p>
     * Represents the contest data retriever to be used.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. Must not be null.
     * </p>
     */
    private final ContestDataRetriever contestDataRetriever;
    /**
     * <p>
     * Represents the component dependency extractor to be used for Java
     * component.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. Must not be null.
     * </p>
     */
    private final ComponentDependencyExtractor javaComponentDependencyExtractor;
    /**
     * <p>
     * Represents the component dependency extractor to be used for .NET
     * component.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. Must not be null.
     * </p>
     */
    private final ComponentDependencyExtractor dotNetComponentDependencyExtractor;
    /**
     * <p>
     * Represents the component manager to be used.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. Must not be null.
     * </p>
     */
    private final ComponentManager componentManager;
    /**
     * <p>
     * Represents the prefix url of the svn location used.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. Must not be null, can be
     * empty.
     * </p>
     */
    private final String svnBaseUrl;
    /**
     * <p>
     * Represents the base 64 encoded svn authentication (user name & password).
     * </p>
     */
    private final String svnAuthentication;
    /**
     * <p>
     * Represents the logger to be used. Will not be changed. Can be null to
     * indicate no logging.
     * </p>
     */
    private final Log logger;

    /**
     * <p>
     * Constructor. Initialize instance variables.
     * </p>
     *
     * @param configuration The configuration object. must not be null.
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ConfigurationException if any error in configuration/missing
     *             value/etc
     *
     */
    public DefaultCOOReportGenerator(ConfigurationObject configuration)
        throws ConfigurationException {
        Helper.checkNull("configuration", configuration);
        this.logger = Helper.getLogger(configuration);
        ConfigurationObject objFactoryConfig = Helper.getChild(configuration, "objectFactoryConfiguration");

        ObjectFactory factory = Helper.createFactory(objFactoryConfig);

        configuration = Helper.getChild(configuration, "default");
        // get svnAuthentication
        String svnPassword = Helper.getStringProperty(configuration, "svnPassword", false);
        String svnUserName = Helper.getStringProperty(configuration, "svnUsername", false);

        // svnAuthentication be null to indicate no authentication
        if (svnPassword != null && svnUserName != null) {
            String combined = svnUserName + ":" + svnPassword;
            svnAuthentication = new sun.misc.BASE64Encoder().encode(combined.getBytes());
        } else {
            svnAuthentication = null;
        }
        // get svnBaseUrl,can be empty
        String url = Helper.getProperty(configuration, "svnBaseUrl");

        if (url == null || url.trim().length() == 0) {
            svnBaseUrl = "";
        } else {
            svnBaseUrl = url;
        }

        // create component manager
        componentManager = Helper.createObject(factory, Helper.getStringProperty(configuration,
            "componentManagerKey", true), ComponentManager.class);

        // create java dependency extractor
        javaComponentDependencyExtractor = Helper.createObject(factory, Helper.getStringProperty(configuration,
            "javaComponentDependencyExtractorKey", true), ComponentDependencyExtractor.class);

        // create .NET dependency extractor
        dotNetComponentDependencyExtractor = Helper.createObject(factory, Helper.getStringProperty(configuration,
            "dotNetComponentDependencyExtractorKey", true), ComponentDependencyExtractor.class);

        // create database retriever
        contestDataRetriever = Helper.createObject(factory, Helper.getStringProperty(configuration,
            "contestDataRetrieverKey", true), ContestDataRetriever.class);
    }

    /**
     * Generate COO Report based on the given project id.
     *
     * @param projectId The project id. must be positive.
     * @return The generated report
     * @throws IllegalArgumentException if any argument is invalid
     * @throws COOReportGeneratorException if there is error in executing this
     *             method
     *
     */
    public COOReport generateCOOReport(long projectId)
        throws COOReportGeneratorException {
        // signature for logging.
        final String signature = "DefaultCOOReportGenerator#generateCOOReport";
        Helper.logEnter(logger, signature);
        Helper.checkId(logger, "projectId", projectId);
        try {
            COOReport report = new COOReport();
            report.setProjectId(projectId);
            ContestData contestData = contestDataRetriever.retrieveContestData(projectId);
            report.setContestData(contestData);

            // get the URL connection from SVN URL
            if (contestData.getSvnPath() != null) {
                ComponentDependencyExtractor componentDependencyExtractor;
                String dependenciesFile;
                if (contestData.getCategory().equals("Java") || contestData.getCategory().equals("Java Custom")) {
                    componentDependencyExtractor = javaComponentDependencyExtractor;
                    dependenciesFile = "build-dependencies.xml";
                } else if (contestData.getCategory().equals(".NET")
                    || contestData.getCategory().equals(".NET Custom")) {
                    componentDependencyExtractor = dotNetComponentDependencyExtractor;
                    dependenciesFile = "Build.dependencies";
                } else {
                    throw new InvalidContestCategoryException("only support Java and .NET component now.");
                }
                URLConnection connection = new URL(
                    svnBaseUrl + contestData.getSvnPath() + "/" + dependenciesFile).openConnection();
                System.out.println(svnBaseUrl + contestData.getSvnPath() + "/" + dependenciesFile);
                if (svnAuthentication != null) {
                    connection.setRequestProperty("Authorization", "Basic " + svnAuthentication);
                }
                InputStream in = null;
                try {
                    in = connection.getInputStream();
                } catch (FileNotFoundException e) {
                    // skip file
                }
                if (in != null) {
                    //extract from input stream.
                    List<ComponentDependency> dependencies = componentDependencyExtractor.
                        extractDependencies(in);
                    List<ComponentDependency> externalDependencies = new ArrayList<ComponentDependency>();
                    for (ComponentDependency dependency : dependencies) {
                        if (dependency == null) {
                            report.setDependenciesError(true);
                        } else if (dependency.getType().equals(DependencyType.EXTERNAL)) {
                            externalDependencies.add(dependency);
                        }
                    }
                    report.setComponentDependencies(externalDependencies);

                    // fully populate the component property of each dependency
                    for (ComponentDependency dependency : externalDependencies) {
                        Component component = dependency.getComponent();
                        // for external dependency where the version can be determined,
                        // get the full info of the component.
                        // if (component.getVersion() != null) {
                                Component fullComponent = componentManager.retrieveComponent(component.getName(),
                            component.getVersion());
                        if (fullComponent != null) {
                            dependency.setComponent(fullComponent);
                        } else {
                            component.setDescription("N/A");
                            component.setLicense("N/A");
                            component.setUrl("N/A");
                            Helper.logInfo(logger, "Component [" + component.getName()
                                + " " + component.getVersion() + "]" + " not found in database.");
                        }
                    }
                }
            }
            // return the result
            return report;
        } catch (MalformedURLException e) {
            throw Helper.logError(logger, new COOReportGeneratorException("the URL is malformed.", e));
        } catch (ComponentDependencyExtractorException e) {
            throw Helper.logError(logger, new COOReportGeneratorException("fail to extract dependency.", e));
        } catch (IOException e) {
            throw Helper.logError(logger, new COOReportGeneratorException("fail to do some I/O operation.", e));
        } catch (ContestDataRetrieverException e) {
            throw Helper.logError(logger, new COOReportGeneratorException("fail to retrieve contest data.", e));
        } catch (ComponentManagerException e) {
            throw Helper.logError(logger, new COOReportGeneratorException("fail to retrieve component.", e));
        } finally {
            Helper.logExit(logger, signature);
        }
    }
}
