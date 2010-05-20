/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.COOReport;
import com.topcoder.management.contest.coo.COOReportSerializer;
import com.topcoder.management.contest.coo.COOReportSerializerException;
import com.topcoder.management.contest.coo.Helper;
import com.topcoder.management.contest.coo.serializer.PDFCOOReportSerializer;
import com.topcoder.management.contest.coo.serializer.XMLCOOReportSerializer;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class represents the base class for report serializers provided in this
 * component.
 * </p>
 * <p>
 * This class provides few common fields as well as implementing
 * <code>serializeCOOReportToFile</code> by delegating to
 * <code>serializeCOOReportToByteArray</code> method that is to be implemented
 * by concrete subclass.
 * </p>
 *
 * <p>
 * <strong>Thread safe:</strong> This class is thread safe since it is
 * immutable.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseCOOReportSerializer implements COOReportSerializer {
    /**
     * <p>
     * Represents the logger to be used.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. Can be null to indicate
     * no logging.
     * </p>
     */
    private final Log logger;
    /**
     * <p>
     * Represents the template file name of the template file used to generate
     * report.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed. Must not be null/empty.
     * </p>
     */
    private final String templateFilename;
    /**
     * <p>
     * Represents the default output file name of the generated COO report
     * </p>
     * <p>
     * Initialized in constructor. Must not be empty. if null default value was
     * set
     * </p>
     */
    private String defaultOutputFilename;

    /**
     * <p>
     * Represents whether generated PDF file or MS Word file
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed.
     * </p>
     */
    private boolean isPDF;

    /**
     * <p>
     * Represents the language field for this project.
     * </p>
     * <p>
     * Initialized in constructor. Will not be changed.
     * </p>
     */
    private String language;

    /**
     * <p>
     * Constructor. Initialize instance variables.
     * </p>
     *
     * @param configuration The configuration object. must not be null.
     * @throws ConfigurationException if any error in configuration/missing
     *             value/etc
     *
     */
    public BaseCOOReportSerializer(ConfigurationObject configuration)
        throws ConfigurationException {

        Helper.checkNull("configuration", configuration);

        configuration = Helper.getChild(configuration, "default");

        // get logger instance
        logger = Helper.getLogger(configuration);

        // set templateFilename
        templateFilename = Helper.getStringProperty(configuration, "templateFilename", true);

        // set language
        language = Helper.getStringProperty(configuration, "language", false);

        if (language == null) {
            language = "N/A";
        }

        // set isPDF
        String value = Helper.getStringProperty(configuration, "format", false);
        if (value == null) {
            isPDF = true;
        } else {
            if (value.equals("PDF")) {
                isPDF = true;
            } else if (value.equals("WORD")) {
                isPDF = false;
            } else {
                throw new ConfigurationException("the property format must be PDF or WORD");
            }
        }

        // set defaultOutputFilename
        defaultOutputFilename = Helper.getStringProperty(configuration, "defaultOutputFilename", false);

        if (defaultOutputFilename == null) {
            if (this instanceof PDFCOOReportSerializer) {
                if (isPDF) {
                    defaultOutputFilename = "COO#DESID#DEVID#DATE.pdf";
                } else {
                    defaultOutputFilename = "COO#DESID#DEVID#DATE.rtf";
                }
            }
            if (this instanceof XMLCOOReportSerializer) {
                defaultOutputFilename = "COO#DESID#DEVID#DATE.xml";
            }
        } else {
            if (this instanceof PDFCOOReportSerializer) {
                if (isPDF && !defaultOutputFilename.endsWith(".pdf")) {
                    throw new ConfigurationException("PDF file extension must end with .pdf");
                } else if (!isPDF && !defaultOutputFilename.endsWith(".rtf")) {
                    throw new ConfigurationException("WORD file extension must end with .rtf");
                }
            }
        }
    }

    /**
     * <p>
     * Serialize the given report to the given file.
     * </p>
     *
     * @param report The COO Report to be serialized. must not be null.
     * @param filename The output filename. can be null to indicate default
     *            filename. can not be empty.
     * @throws COOReportSerializerException if any exception occurred when
     *             performing this method
     *
     */
    public void serializeCOOReportToFile(COOReport report, String filename)
        throws COOReportSerializerException {
        // signature for logging.
        final String signature = "BaseCOOReportSerializer#serializeCOOReportToFile";
        Helper.logEnter(logger, signature);
        Helper.checkNull(logger, "COOReport", report);
        FileOutputStream fos = null;
        try {

            if (filename == null) {
                filename = getOutputFileName(report);
            } else if (filename.trim().length() == 0) {
                throw new IllegalArgumentException("file name can not be empty");
            }
            byte[] res = this.serializeCOOReportToByteArray(report);
            fos = new FileOutputStream(filename);
            fos.write(res);
        } catch (FileNotFoundException e) {
            throw Helper.logError(logger, new COOReportSerializerException("file ["
                    + filename + " not found", e));
        } catch (IOException e) {
            throw Helper.logError(logger, new COOReportSerializerException("fail to do I/O operation.", e));
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // ignore.
            }
            Helper.logExit(logger, signature);
        }
    }

    /**
     * Returns output file name.
     * @param report coo report.
     * @return output file name.
     */
    private String getOutputFileName(COOReport report) {
        String desProjectId = report.getContestData().getDesignProjectId();
        String devProjectId = report.getContestData().getDevelopmentProjectId();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String name = defaultOutputFilename;
        if (desProjectId == null) {
            desProjectId = "";
        } else {
            desProjectId = "_" + desProjectId;
        }
        if (devProjectId == null) {
            devProjectId = "";
        } else {
            devProjectId = "_" + devProjectId;
        }
        name = name.replace("#DESID", desProjectId);
        name = name.replace("#DEVID", devProjectId);
        name = name.replace("#DATE", "_" + date);
        return name;
    }

    /**
     * <p>
     * Getter for the logger field.
     * </p>
     *
     * @return The value of the logger field.
     */
    protected Log getLogger() {
        return logger;
    }

    /**
     * <p>
     * Getter for the templateFilename field.
     * </p>
     *
     * @return The value of the templateFilename field
     */
    protected String getTemplateFilename() {
        return templateFilename;
    }

    /**
     * <p>
     * Getter for the isPDF field.
     * </p>
     *
     * @return The value of isPDF field
     */
    protected boolean getIsPDF() {
        return isPDF;
    }

    /**
     * <p>
     * Getter for the language field.
     * </p>
     *
     * @return The value of language field
     */
    protected String getLanguage() {
        return language;
    }
}
