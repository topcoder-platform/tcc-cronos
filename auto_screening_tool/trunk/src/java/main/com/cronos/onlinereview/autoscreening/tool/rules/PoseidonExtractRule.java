/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Map;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.file.type.FileType;
import com.topcoder.file.type.MagicNumbers;
import com.topcoder.util.archiving.ArchiveUtility;
import com.topcoder.util.archiving.ZipArchiver;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * Checks if a poseidon file exists. If it does, it will extract the xmi and
 * proj3 files and make them available to other rules for additional checking.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class PoseidonExtractRule implements ScreeningRule {
    /**
     * <p>
     * This is a constant which may be used by other rules to retrieve the
     * extracted PROJ3 file from the ZUML for further processing.
     * </p>
     */
    public static final String PROJ3_KEY = "extracted_proj3_file";

    /**
     * <p>
     * This is a constant which may be used by other rules to retrieve the
     * extracted XMI file from the ZUML for further processing.
     * </p>
     */
    public static final String XMI_KEY = "extracted_xmi_file";

    /**
     * <p>
     * This is a constant which may be used by other rules to retrieve the ZUML
     * file for further processing.
     * </p>
     */
    public static final String EXTRACTED_ZUML_KEY = "extracted_zuml_file";

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
            LOG = LogFactory.getLogWithExceptions(PoseidonExtractRule.class.getName());
        } catch (LogException e) {
            throw new ScreeningException("Unable to get a Log instance from name ["
                + PoseidonExtractRule.class.getName() + "].", e);
        }
    }

    /**
     * <p>
     * This is a String that represents the path to the temporary directory to
     * use for storing the extracted file. It is optional, and if it is not
     * specified, then the default temporary directory as specified by the
     * system property java.io.tmpdir will be used.
     * </p>
     */
    private final String temporaryDir;

    /**
     * <p>
     * Default constructor using default temporary directory.
     * </p>
     */
    public PoseidonExtractRule() {
        this(null);
    }

    /**
     * <p>
     * Constructor that allows the temporary directory to be specified. If it is
     * not specified, then the default temporary directory as specified by the
     * system property java.io.tmpdir will be used.
     * </p>
     * @param temporaryDir
     *            The temporary directory on which to store the extracted PROJ3
     *            file.
     * @throws IllegalArgumentException
     *             if temporaryDir is empty (trimmed).
     */
    public PoseidonExtractRule(String temporaryDir) {
        if (temporaryDir != null && temporaryDir.trim().length() == 0) {
            throw new IllegalArgumentException("temporaryDir should not be empty (trimmed).");
        }

        this.temporaryDir = temporaryDir == null ? System.getProperty("java.io.tmpdir")
            : temporaryDir;
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
     * <li>Search the "docs" directory for a ZUML or ZARGO file. (If no file,
     * then fail with detail message)</li>
     * <li>If it exists, use MagicNumbers to determine whether file type is
     * "application/zip".</li>
     * <li>Then use ArchiveUtility to extract it to a temporary directory.</li>
     * <li>Store the directory where the extracted ZUML in the context under
     * EXTRACTED_ZUML_KEY.</li>
     * <li>Search and see if a proj3 file was extracted. (If no file, then fail
     * with detail message)</li>
     * <li>Store this proj3 file within the context via the PROJ3_KEY.</li>
     * <li>Search and see if a xmi file was extracted. (If no file, then fail
     * with detail message)</li>
     * <li>Store this xmi file within the context via the XMI_KEY.</li>
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

            // get the docs directory
            File docsDir = new File(submissionDir, "docs");

            // get the ZUML files
            File[] zumlFiles = docsDir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    String fileName = file.getName().toLowerCase();
                    return fileName.endsWith(".zuml") || fileName.endsWith(".zargo");
                }
            });
            if (zumlFiles.length != 1) {
                return new RuleResult[] {new RuleResult(false,
                    "No or duplicate 'zuml' or 'zargo' files are found under directory ["
                        + docsDir.getAbsolutePath() + "].")};
            }

            // determine file type of zumlFiles[0]
            FileType type = new MagicNumbers().determineFileType(zumlFiles[0]);
            if (type == null || !type.getMime().equals("application/zip")) {
                return new RuleResult[] {new RuleResult(false, "The 'zuml' or 'zargo' file ["
                    + zumlFiles[0].getName() + "] is not a zip archive.")};
            }

            // unzip the ZUML file
            File contentsDir = new File(temporaryDir, zumlFiles[0].getName() + ".contents");
            if (!contentsDir.exists() && !contentsDir.mkdir()) {
                throw new IOException("Unable to make the unzipped zuml directory ["
                    + contentsDir.getAbsolutePath() + "].");
            }

            ArchiveUtility archiveUtility = new ArchiveUtility(zumlFiles[0], new ZipArchiver());
            archiveUtility.extractContents(contentsDir);
            // put the contents directory of the unzipped ZUML file to the
            // screening context
            context.put(EXTRACTED_ZUML_KEY, contentsDir);

            // get the proj3 files
            File[] proj3Files = contentsDir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    String fileName = file.getName().toLowerCase();
                    return fileName.endsWith(".proj3");
                }
            });
            if (proj3Files.length != 1) {
                return new RuleResult[] {new RuleResult(false,
                    "No or duplicate 'proj3' files are found under directory ["
                        + contentsDir.getAbsolutePath() + "].")};
            }
            // put the proj3 file to the screening context
            context.put(PROJ3_KEY, proj3Files[0]);

            // get the xmi files
            File[] xmiFiles = contentsDir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    String fileName = file.getName().toLowerCase();
                    return fileName.endsWith(".xmi");
                }
            });
            if (xmiFiles.length != 1) {
                return new RuleResult[] {new RuleResult(false,
                    "No or duplicate 'xmi' files are found under directory ["
                        + contentsDir.getAbsolutePath() + "].")};
            }
            // put the proj3 file to the screening context
            context.put(XMI_KEY, xmiFiles[0]);

            // success
            return new RuleResult[] {new RuleResult(true,
                "OK. The 'zuml' or 'zargo' file is unzipped successfully.")};
        } catch (Throwable e) {
            return new RuleResult[] {new RuleResult(e)};
        }
    }

    /**
     * <p>
     * This method will clean-up by deleting temporary file that was stored in
     * the context under EXTRACTED_ZUML_KEY.
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
        if (screeningTask == null) {
            throw new IllegalArgumentException("screeningTask should not be null.");
        }
        if (context == null) {
            throw new IllegalArgumentException("context should not be null.");
        }

        LOG.log(Level.INFO, "Cleanup screening task with id [" + screeningTask.getId()
            + "] by screener with id [" + screeningTask.getScreenerId() + "].");

        // get the unzipped zuml directory
        File zumlDir;
        try {
            zumlDir = (File) RuleUtils.getContextProperty(context, EXTRACTED_ZUML_KEY, File.class);
        } catch (ScreeningException e) {
            LOG.log(Level.FATAL, RuleUtils.getStackTrace(e));
            return;
        }

        // try to delete the unzipped zuml directory
        if (zumlDir.exists() && !RuleUtils.deleteDir(zumlDir)) {
            LOG.log(Level.FATAL, "Failed to completely delete the unzipped zuml directory ["
                + zumlDir.getAbsolutePath() + "].");
            return;
        }

        // success
        LOG.log(Level.INFO, "OK. The temporary unzipped zuml directory ["
            + zumlDir.getAbsolutePath() + "] was deleted successfully.");
    }
}
