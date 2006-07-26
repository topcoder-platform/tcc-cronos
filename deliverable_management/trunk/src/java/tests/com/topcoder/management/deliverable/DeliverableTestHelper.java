/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * Helper class for test.
 *
 * @author singlewood
 * @version 1.0
 */
public class DeliverableTestHelper {
    /** Represents namespace containing db connection factory configurations. */
    private static final String DB_CONNECTION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * private constructor.
     */
    private DeliverableTestHelper() {
    }

    /**
     * Loads necessary configurations into ConfigManager.
     *
     * @param file the file contains configurations.
     *
     * @throws Exception pass to JUnit.
     */
    public static void loadConfig(String file) throws Exception {
        ConfigManager.getInstance().add(file);
    }

    /**
     * Unloads all configurations. All namespaces in ConfigManager are removed.
     *
     * @throws Exception pass to JUnit.
     */
    public static void unloadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace(it.next().toString());
        }
    }

    /**
     * Executes a sql batch contains in a file.
     *
     * @param file the file contains the sql statements.
     *
     * @throws Exception pass to JUnit.
     */
    public static void executeBatch(String file) throws Exception {
        // get db connection
        Connection connection = new DBConnectionFactoryImpl(DB_CONNECTION_NAMESPACE).createConnection();
        Statement statement = connection.createStatement();

        // get sql statements and add to statement
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                statement.addBatch(line);
            }
        }

        statement.executeBatch();
    }

    /**
     * Set the AuditedDeliverableStructure field (creationUser, creationTimestamp, modificationUser,
     * modificationTimestamp) with valid values.
     *
     * @param obj the object to set
     */
    public static void setAuditedDeliverableStructureField(AuditedDeliverableStructure obj) {
        Date date = new Date();
        obj.setCreationUser("CreationUser");
        obj.setCreationTimestamp(date);
        obj.setModificationUser("ModificationUser");
        obj.setModificationTimestamp(date);
    }

    /**
     * Set the NamedDeliverableStructure field (creationUser, creationTimestamp, modificationUser,
     * modificationTimestamp) with valid values.
     *
     * @param obj the object to set
     */
    public static void setNamedDeliverableStructureField(NamedDeliverableStructure obj) {
        setAuditedDeliverableStructureField(obj);
        obj.setDescription("desp");
        obj.setName("name");
    }

    /**
     * Get a Upload instance which is valid to persist. Note that the id field is not set.
     *
     * @return a Upload object
     */
    public static Upload getValidToPersitUpload() {
        Upload upload = new Upload();
        setAuditedDeliverableStructureField(upload);
        upload.setOwner(1);
        upload.setParameter("11");
        upload.setProject(1);

        UploadType uploadType = getValidToPersitUploadType();
        uploadType.setId(1);
        UploadStatus uploadStatus = getValidToPersitUploadStatus();
        uploadStatus.setId(1);
        upload.setUploadStatus(uploadStatus);
        upload.setUploadType(uploadType);

        return upload;
    }

    /**
     * Get a UploadType instance which is valid to persist. Note that the id field is not set.
     *
     * @return a UploadType object
     */
    public static UploadType getValidToPersitUploadType() {
        UploadType uploadType = new UploadType();
        setNamedDeliverableStructureField(uploadType);
        return uploadType;
    }

    /**
     * Get a UploadStatus instance which is valid to persist. Note that the id field is not set.
     *
     * @return a UploadStatus object
     */
    public static UploadStatus getValidToPersitUploadStatus() {
        UploadStatus uploadStatus = new UploadStatus();
        setNamedDeliverableStructureField(uploadStatus);
        return uploadStatus;
    }

    /**
     * Get a Submission instance which is valid to persist. Note that the id field is not set.
     *
     * @return a Submission object
     */
    public static Submission getValidToPersitSubmission() {
        Submission submission = new Submission();
        setAuditedDeliverableStructureField(submission);

        Upload upload = getValidToPersitUpload();
        upload.setId(1);
        submission.setUpload(upload);

        SubmissionStatus submissionStatus = getValidToPersitSubmissionStatus();
        submissionStatus.setId(1);
        submission.setSubmissionStatus(submissionStatus);

        return submission;
    }

    /**
     * Get a SubmissionStatus instance which is valid to persist. Note that the id field is not set.
     *
     * @return a SubmissionStatus object
     */
    public static SubmissionStatus getValidToPersitSubmissionStatus() {
        SubmissionStatus submissionStatus = new SubmissionStatus();
        setNamedDeliverableStructureField(submissionStatus);
        return submissionStatus;
    }

}
