/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.file.directoryvalidator.DirectoryManager;
import com.topcoder.file.directoryvalidator.XmlFileLoader;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * Checks if the submission conforms to proper directory structure.
 * </p>
 * <p>
 * This class encapsulates the CSharpDevDirRule, JavaDevDirRule, DesignDirRule,
 * TestLogRule from the initial version. With the correct configuration, the
 * scenarios that needed validation could be validated using the Directory
 * Validator component.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class DirectoryStructureRule implements ScreeningRule {

    /**
     * Represents the Log instance for this class to log additional information
     * to.
     */
    private static final Log LOG;

    /**
     * Static constructor.
     */
    static {
        // try to create a Log instance
        try {
            LOG = LogFactory.getLogWithExceptions(DirectoryStructureRule.class.getName());
        } catch (LogException e) {
            throw new ScreeningException("Unable to get a Log instance from name ["
                + DirectoryStructureRule.class.getName() + "].", e);
        }
    }

    /**
     * <p>
     * This string contains the path to the XML Configuration file used to
     * validate the directory structure. The Classloader.getSystemResource will
     * be used to retrieve the config file.
     * </p>
     */
    private final String configFileName;

    /**
     * <p>
     * Constructor that utilizes the provided config file to configure Directory
     * Validation.
     * </p>
     * @param configFileName
     *            The name of the config file to use.
     * @throws IllegalArgumentException
     *             if configFileName is null or an empty String
     */
    public DirectoryStructureRule(String configFileName) {
        if (configFileName == null) {
            throw new IllegalArgumentException("configFileName should not be null.");
        }
        if (configFileName.trim().length() == 0) {
            throw new IllegalArgumentException("configFileName should not be empty (trimmed).");
        }

        this.configFileName = configFileName;
    }

    /**
     * <p>
     * Performs screening on the specified screening task. This implementation
     * will expect the context to contain the File object pointing to the root
     * directory of an unzipped submission.
     * </p>
     * <p>
     * Generally speaking this method does the following things:
     * <ol>
     * <li>Retrieve the unzipped directory from the context using
     * ArchiveFileRule.SUBMISSION_DIRECTORY_KEY.</li>
     * <li>Use configFile and ClassLoader to retrieve the Xml configuration
     * file as a resource.</li>
     * <li>Use the retrieved resource to initialize XmlFileLoader</li>
     * <li>Use XmlFileLoader with DirectoryManager to validate. The
     * ValidationLoggerImpl is used.</li>
     * <li>Retrieve a list of RuleResult from the ValidationLoggerImpl.</li>
     * <li>If the RuleResults from ValidationLoggerImpl was empty, then create
     * a RuleResult containing a validation successful message.</li>
     * <li>Otherwise, return the results of validation along.</li>
     * <li>Log this method according to the CS section 1.3.3.</li>
     * </ol>
     * </p>
     * @param screeningTask
     *            The screeningTask to screen.
     * @param context
     *            The context which allows additional screening information to
     *            be provided.
     * @return The results of screening.
     * @throws IllegalArgumentException
     *             if either parameter is null.
     */
    public RuleResult[] screen(ScreeningTask screeningTask, Map context) {
        if (screeningTask == null) {
            throw new IllegalArgumentException("screeningTask should not be null.");
        }
        if (context == null) {
            throw new IllegalArgumentException("context should not be null.");
        }

        RuleUtils.beginScreeningLogging(LOG, screeningTask);
        RuleResult[] ruleResults = screenWithoutLog(screeningTask, context);
        RuleUtils.endScreeningLogging(LOG, ruleResults);

        return ruleResults;
    }

    /**
     * Performs screening on the specified screening task without log
     * information.
     * @param screeningTask
     *            The screeningTask to screen.
     * @param context
     *            The context which allows additional screening information to
     *            be provided.
     * @return The results of screening.
     */
    private RuleResult[] screenWithoutLog(ScreeningTask screeningTask, Map context) {

        try {
            // get the unzipped submission directory
            File submissionDir = (File) RuleUtils.getContextProperty(context,
                ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, File.class);

            // get the URL of configFile from ClassLoader
            URL configFileURL = ClassLoader.getSystemResource(configFileName);
            if (configFileURL == null) {
                throw new FileNotFoundException("Unable to find config file [" + configFileName
                    + "] using ClassLoader.getSystemResource(String).");
            }

            // create the directory manager
            XmlFileLoader xmlFileLoader = new XmlFileLoader(new File(configFileURL.getFile()));
            ValidationLoggerImpl validationLogger = new ValidationLoggerImpl();
            DirectoryManager directoryManager = new DirectoryManager(validationLogger);

            // validate the unzipped submission directory
            if (directoryManager.validate(xmlFileLoader, submissionDir.getAbsolutePath())) {
                // success
                return new RuleResult[] {new RuleResult(true,
                    "OK. The directory structure of the submission is as expected.")};
            }

            // get the failure results
            return validationLogger.getRuleResults();
        } catch (Throwable e) {
            return new RuleResult[] {new RuleResult(e)};
        }
    }

    /**
     * <p>
     * Cleans up any resources related to this screening rule.
     * </p>
     * <p>
     * This rule does not use any resources that need to be cleaned, and so this
     * method remains empty.
     * </p>
     * @param screeningTask
     *            The screeningTask for which cleanup must be performed.
     * @param context
     *            The screening context which may contain some resources to be
     *            released.
     */
    public void cleanup(ScreeningTask screeningTask, Map context) {
        // do nothing
    }
}
