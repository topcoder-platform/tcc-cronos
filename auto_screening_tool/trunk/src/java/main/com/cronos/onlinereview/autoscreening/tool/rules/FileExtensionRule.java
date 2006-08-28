/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This rule determines whether files with a given extension exist within the
 * submission.
 * </p>
 * <p>
 * It is used to encapsulate JavaSourceCodeRule and CSharpSourceCodeRule from
 * the previous version.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class FileExtensionRule implements ScreeningRule {
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
            LOG = LogFactory.getLogWithExceptions(FileExtensionRule.class.getName());
        } catch (LogException e) {
            throw new ScreeningException("Unable to get a Log instance from name ["
                + FileExtensionRule.class.getName() + "].", e);
        }
    }

    /**
     * <p>
     * The file extension to search for.
     * </p>
     */
    private final String fileExtension;

    /**
     * <p>
     * This indicates the path to start looking for source files in. When
     * assigning it, the input string should be separated by the '/' symbol to
     * indicate a directory delimited. However this sort of input string may not
     * apply in all file systems, so '/' symbol is replaced by
     * File.separatorChar when assigning it to the startDirectoryName.
     * </p>
     */
    private final String startDirectoryName;

    /**
     * <p>
     * Creates a FileExtensionRule that will search from the specified starting
     * directory for the specified extensions.
     * </p>
     * @param fileExtension
     *            The extension to search for.
     * @param startDirectoryName
     *            This indicates the path to start looking for source files in.
     *            It should be separated by the '/' symbol to indicate a
     *            directory delimited.
     * @throws IllegalArgumentException
     *             if extension or dirStart is null or an empty String.
     */
    public FileExtensionRule(String fileExtension, String startDirectoryName) {
        if (fileExtension == null) {
            throw new IllegalArgumentException("fileExtension should not be null.");
        }
        if (fileExtension.trim().length() == 0) {
            throw new IllegalArgumentException("fileExtension should not be empty (trimmed).");
        }
        if (startDirectoryName == null) {
            throw new IllegalArgumentException("startDirectoryName should not be null.");
        }
        if (startDirectoryName.trim().length() == 0) {
            throw new IllegalArgumentException("startDirectoryName should not be empty (trimmed).");
        }

        this.fileExtension = fileExtension.toLowerCase();
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
     * <li>Start from the provided dirStart directory within the unzipped
     * directory.</li>
     * <li>Iterate over all files in the directory.</li>
     * <li>If any files end in the specified extension, then terminate with
     * success and a detailed message.</li>
     * <li>Otherwise, recursively process all subdirectories within the root
     * directory.</li>
     * <li>If no files with specified extension are anywhere, then terminate
     * with failure and a detailed message.</li>
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

            // get the start directory
            File startDir = new File(submissionDir, startDirectoryName);

            // find the required files from startDir
            // NOTE: ScreeningRuleUtils.listAllFiles are used here so that all
            // files are checked instead of returning once a file meets the
            // requirement. The efficiency is not a big problem here, so here we
            // reuse ScreeningRuleUtils.listAllFiles method for simplicity and
            // extensibility.
            File[] files = RuleUtils.listAllFiles(startDir, new FileFilter() {
                public boolean accept(File file) {
                    return file != null && file.exists()
                        && file.getName().toLowerCase().endsWith(fileExtension);
                }
            });

            // check if no files with the required extension are found
            if (files.length == 0) {
                return new RuleResult[] {new RuleResult(false, "No files with extension ["
                    + fileExtension + "] are found under directory [" + startDir.getAbsolutePath()
                    + "].")};
            }

            // success
            return new RuleResult[] {new RuleResult(true, "OK. Files with extension ["
                + fileExtension + "] are found under directory [" + startDir.getAbsolutePath()
                + "].")};
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
