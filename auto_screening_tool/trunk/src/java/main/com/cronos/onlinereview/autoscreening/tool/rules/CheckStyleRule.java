/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.util.exec.Exec;
import com.topcoder.util.exec.ExecutionResult;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This rule runs checkstyle on the Java source code.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class CheckStyleRule implements ScreeningRule {
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
            LOG = LogFactory.getLogWithExceptions(CheckStyleRule.class.getName());
        } catch (LogException e) {
            throw new ScreeningException("Unable to get a Log instance from name ["
                + CheckStyleRule.class.getName() + "].", e);
        }
    }

    /**
     * <p>
     * This is the command that is used in order to execute checkStyle on the
     * main source code.
     * </p>
     */
    private final String checkStyleCommand;

    /**
     * <p>
     * This indicates the path to start check style for source files in. When
     * assigning it, the input string should be separated by the '/' symbol to
     * indicate a directory delimited. However this sort of input string may not
     * apply in all file systems, so '/' symbol is replaced by
     * File.separatorChar when assigning it to the startDirectoryName.
     * </p>
     */
    private final String startDirectoryName;

    /**
     * <p>
     * Creates a CheckStyleRule that will use the provided commands to run
     * checkstyle.
     * </p>
     * @param checkStyleCommand
     *            The command used to execute checkstyle on the source code.
     * @param startDirectoryName
     *            This indicates the path to start looking for source files in.
     *            It should be separated by the '/' symbol to indicate a
     *            directory delimited.
     * @throws IllegalArgumentException
     *             if any parameter is null or an empty String.
     */
    public CheckStyleRule(String checkStyleCommand, String startDirectoryName) {
        if (checkStyleCommand == null) {
            throw new IllegalArgumentException("checkStyleCommand should not be null.");
        }
        if (checkStyleCommand.trim().length() == 0) {
            throw new IllegalArgumentException("checkStyleCommand should not be empty (trimmed).");
        }
        if (startDirectoryName == null) {
            throw new IllegalArgumentException("startDirectoryName should not be null.");
        }
        if (startDirectoryName.trim().length() == 0) {
            throw new IllegalArgumentException("startDirectoryName should not be empty (trimmed).");
        }

        this.checkStyleCommand = checkStyleCommand;
        this.startDirectoryName = startDirectoryName.replace('/', File.separatorChar);
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
     * <li>Use executable wrapper to execute the specified checkStyleCommand on
     * the unzipped directory, starting from the start directory.</li>
     * <li>Retrieve the output result of the execution result.</li>
     * <li>Process each line (trim the first line which should be "Starting
     * audit..." and last line which should be "Audit done."</li>
     * <li>Each line will result in a failed RuleResult whose detail message is
     * the line that was processed.</li>
     * <li>If no errors were found, then return a successful RuleResult with
     * detailed message.</li>
     * <li>Remember to log this method according to the CS section 1.3.3.</li>
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

            // get the start directory
            File startDir = new File(submissionDir, startDirectoryName);
            if (!startDir.exists()) {
                throw new IOException("Directory [" + startDir.getAbsolutePath()
                    + "] to start checking style from does not exist.");
            }

            // split the command with white spaces
            // and execute the check style command
            ExecutionResult executionResult = Exec.execute((checkStyleCommand + " " + startDir
                .getAbsolutePath()).split("\\s"));

            // split the output of the command by lines
            String[] lines = executionResult.getOut().split(System.getProperty("line.separator"));

            // the first line of output should be 'Starting audit...'
            // and the last line of output should be 'Audit done.'
            if (lines.length < 2 || !lines[0].startsWith("Starting audit...")
                || !lines[lines.length - 1].startsWith("Audit done.")) {
                return new RuleResult[] {new RuleResult(false,
                    "The format of CheckStyle output is invalid.")};
            }

            // process each other line of the output
            ArrayList resultList = new ArrayList();
            for (int i = 1; i < lines.length - 1; ++i) {
                // add the message of the line if not empty (trimmed)
                if (lines[i].trim().length() != 0) {
                    resultList.add(new RuleResult(false, lines[i]));
                }
            }

            // check if there are any failures
            if (resultList.size() != 0) {
                return (RuleResult[]) resultList.toArray(new RuleResult[0]);
            }

            // success
            return new RuleResult[] {(new RuleResult(true, "OK. CheckStyle passed."))};
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
