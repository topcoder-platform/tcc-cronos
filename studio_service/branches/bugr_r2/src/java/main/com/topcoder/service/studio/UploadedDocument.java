/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * <p>
 * It is the DTO class which is used to transfer document and document content
 * data. The information can be null or can be empty, therefore this check is
 * not present in the setters. It's the related to the equivalent Document
 * entity.
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadedDocument", propOrder = { "documentId", "contestId", "file", "description",
        "fileName", "documentTypeId", "mimeTypeId", "path" })
public class UploadedDocument implements Serializable {
    /**
     * <p>
     * Represents the document Id.
     * </p>
     */
    private long documentId = -1;

    /**
     * <p>
     * Represents the contest Id.
     * </p>
     */
    private long contestId = -1;

    /**
     * <p>
     * Represents the file content.
     * </p>
     */
    private byte[] file;

    /**
     * <p>
     * Represents the description of document type.
     * </p>
     */
    private String description;

    /**
     * <p>
     * Represents the fileName.
     * </p>
     */
    private String fileName;

    /**
     * <p>
     * Represents the document Type Id.
     * </p>
     */
    private long documentTypeId = -1;

    /**
     * <p>
     * Represents the mimeType Id.
     * </p>
     */
    private long mimeTypeId = -1;

    /**
     * <p>
     * Represents the document path.
     * </p>
     */
    private String path;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public UploadedDocument() {
    }

    /**
     * <p>
     * Return the documentId.
     * </p>
     *
     * @return the documentId
     */
    public long getDocumentId() {
        return documentId;
    }

    /**
     * <p>
     * Set the documentId.
     * </p>
     *
     * @param documentId the documentId to set
     */
    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    /**
     * <p>
     * Return the contestId.
     * </p>
     *
     * @return the contestId
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Set the contestId.
     * </p>
     *
     * @param contestId the contestId to set
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Return the file.Return the reference, to save a bit of memory, a file
     * could be large.
     * </p>
     *
     * @return the file.
     */
    public byte[] getFile() {
        return file;
    }

    /**
     * <p>
     * Set the file. Set the reference, to save a bit of memory, a file could be
     * large.
     * </p>
     *
     * @param file the file to set
     */
    public void setFile(byte[] file) {
        this.file = file;
    }

    /**
     * <p>
     * Return the description.
     * </p>
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Set the description.
     * </p>
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Return the fileName.
     * </p>
     *
     * @return the fileName.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * <p>
     * Set the fileName.
     * </p>
     *
     * @param fileName the fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * <p>
     * Return the documentTypeId.
     * </p>
     *
     * @return the documentTypeId.
     */
    public long getDocumentTypeId() {
        return documentTypeId;
    }

    /**
     * <p>
     * Set the documentTypeId.
     * </p>
     *
     * @param documentTypeId the documentTypeId to set.
     */
    public void setDocumentTypeId(long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    /**
     * <p>
     * Return the mimeTypeId.
     * </p>
     *
     * @return the mimeTypeId.
     */
    public long getMimeTypeId() {
        return mimeTypeId;
    }

    /**
     * <p>
     * Set the mimeTypeId.
     * </p>
     *
     * @param mimeTypeId the mimeTypeId to set.
     */
    public void setMimeTypeId(long mimeTypeId) {
        this.mimeTypeId = mimeTypeId;
    }

    /**
     * <p>
     * Return the path.
     * </p>
     *
     * @return the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * <p>
     * Set the path.
     * </p>
     *
     * @param path the path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }
}
