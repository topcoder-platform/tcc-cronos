/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningData;
import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This rule ensures that the submission has no Personal Information. The
 * submitter information is retrieved from the ScreeningData, and the submission
 * is checked for any references to the submitter info.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class PersonalInfoRule implements ScreeningRule {

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
            LOG = LogFactory.getLogWithExceptions(PersonalInfoRule.class.getName());
        } catch (LogException e) {
            throw new ScreeningException("Unable to get a Log instance from name ["
                + PersonalInfoRule.class.getName() + "].", e);
        }
    }

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public PersonalInfoRule() {
        // empty
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
     * <li>Use the screening data of the ScreeningTask to retrieve the personal
     * information.</li>
     * <li>Traverse each file within the root directory and process it. </li>
     * <li>The file is processed by searching the contents for a given String
     * that matches any of the attributes of personal info excepting the design
     * rating, dev rating and reliabilities.</li>
     * <li>Log this method according to the CS section 1.3.3.</li>
     * </ol>
     * </p>
     * @param screeningTask
     *            The screeningTask to screen.
     * @param context
     *            The context which allows additional screening information to
     *            be provided.
     * @throws IllegalArgumentException
     *             if either parameter is null.
     * @return The results of screening.
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

            // get all files under the unzipped submission directory
            File[] files = RuleUtils.listAllFiles(submissionDir, null);

            // get the screeningData of this task that contains personal info
            ScreeningData screeningData = screeningTask.getScreeningData();
            if (screeningData == null) {
                throw new ScreeningException(
                    "The screening data of screeningTask has not been specified.");
            }

            ArrayList resultList = new ArrayList();
            // enumerate each file under the unzipped submission directory
            for (int i = 0; i < files.length; ++i) {
                // get the result
                RuleResult result = examineSingleFile(files[i], screeningData);

                // check if there is a failure result for the current file
                if (result != null) {
                    resultList.add(result);
                }
            }

            // check if there are any failure/error results
            if (resultList.size() != 0) {
                return (RuleResult[]) resultList.toArray(new RuleResult[0]);
            }

            // success
            return new RuleResult[] {new RuleResult(true,
                "OK. There are no personal info in the submission.")};
        } catch (Throwable e) {
            return new RuleResult[] {new RuleResult(e)};
        }
    }

    /**
     * Checks if the content of the file contains the personal info.
     * @param file
     *            the file to check
     * @param screeningData
     *            the screening data that contains personal info
     * @return a failure rule result describing what personal info the file
     *         contains, or null if no personal info exists
     * @throws IOException
     *             if error occurs when reading the file
     */
    private RuleResult examineSingleFile(File file, ScreeningData screeningData) throws IOException {

        // get the file content
        String content = RuleUtils.readToEnd(file).toLowerCase();

        // check handle
        if (content.indexOf(screeningData.getSubmitterHandle().toLowerCase()) != -1) {
            return new RuleResult(false, "File [" + file.getName()
                + "] contains submitter's handle [" + screeningData.getSubmitterHandle() + "].");
        }

        // check first name
        if (content.indexOf(screeningData.getSubmitterFirstName().toLowerCase()) != -1) {
            return new RuleResult(false, "File [" + file.getName()
                + "] contains submitter's first name [" + screeningData.getSubmitterFirstName()
                + "].");
        }

        // check last name
        if (content.indexOf(screeningData.getSubmitterLastName().toLowerCase()) != -1) {
            return new RuleResult(false, "File [" + file.getName()
                + "] contains submitter's last name [" + screeningData.getSubmitterLastName()
                + "].");
        }

        // check email
        if (content.indexOf(screeningData.getSubmitterEmail().toLowerCase()) != -1) {
            return new RuleResult(false, "File [" + file.getName()
                + "] contains submitter's email address [" + screeningData.getSubmitterEmail()
                + "].");
        }

        // check each alternative email
        Iterator iter = Arrays.asList(screeningData.getSubmitterAlternativeEmails()).iterator();
        while (iter.hasNext()) {
            // get the current alternative email
            String alternativeEmail = (String) iter.next();

            // check the current alternative email
            if (content.indexOf(alternativeEmail.toLowerCase()) != -1) {
                return new RuleResult(false, "File [" + file.getName()
                    + "] contains submitter's alternative email address [" + alternativeEmail
                    + "].");
            }
        }

        // success
        return null;
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
     * @throws IllegalArgumentException
     *             if either parameter is null.
     */
    public void cleanup(ScreeningTask screeningTask, Map context) {
        // do nothing
    }
}
