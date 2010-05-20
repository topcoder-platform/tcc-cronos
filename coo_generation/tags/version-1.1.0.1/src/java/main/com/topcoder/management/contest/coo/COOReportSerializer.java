/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;


/**
 * <p>
 * This interface represents COO Report Serializer.
 * </p>
 * <p>
 * <strong> Thread safety:</strong> Implementation is not required to be thread
 * safe.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public interface COOReportSerializer {
    /**
     * Serialize the given report to the given file.
     *
     * @param report The COO Report to be serialized. must not be null.
     * @param filename The output filename. can be null to indicate default
     *            filename. can not be empty.
     * @throws COOReportSerializerException if any exception occur when
     *             performing this method
     */
    public void serializeCOOReportToFile(COOReport report, String filename)
        throws COOReportSerializerException;

    /**
     * Serialize the given report to byte array.
     *
     * @param report The COO Report to be serialized. must not be null.
     * @return The byte array representing the serialized report
     * @throws COOReportSerializerException if any exception occur when
     *             performing this method
     *
     */
    public byte[] serializeCOOReportToByteArray(COOReport report)
        throws COOReportSerializerException;
}
