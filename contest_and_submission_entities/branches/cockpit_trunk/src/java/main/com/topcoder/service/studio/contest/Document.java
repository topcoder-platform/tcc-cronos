/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Represents the entity class for db table <i>document</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class Document implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 3488842007794442721L;

    /**
     * Represents the entity id.
     */
    private Long documentId;

    /**
     * Represents the contest which have this document.
     */
    private Set<Contest> contests = new HashSet<Contest>();

    /**
     * Represents the original file name.
     */
    private String originalFileName;

    /**
     * Represents the system file name.
     */
    private String systemFileName;

    /**
     * Represents the description.
     */
    private String description;

    /**
     * Represents the document path.
     */
    private FilePath path;

    /**
     * Represents the mime type.
     */
    private MimeType mimeType;

    /**
     * Represents the document type.
     */
    private DocumentType type;

    /**
     * Represents the create date.
     */
    private Date createDate;

    /**
     * Default constructor.
     */
    public Document() {
        // empty
    }

    /**
     * Returns the documentId.
     *
     * @return the documentId.
     */
    public Long getDocumentId() {
        return documentId;
    }

    /**
     * Updates the documentId with the specified value.
     *
     * @param documentId
     *            the documentId to set.
     */
    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    /**
     * Returns the contests.
     *
     * @return the contests.
     */
    public Set<Contest> getContests() {
        return contests;
    }

    /**
     * Updates the contests with the specified value.
     *
     * @param contests
     *            the contests to set.
     */
    public void setContests(Set<Contest> contests) {
        this.contests = contests;
    }

    /**
     * Returns the originalFileName.
     *
     * @return the originalFileName.
     */
    public String getOriginalFileName() {
        return originalFileName;
    }

    /**
     * Updates the originalFileName with the specified value.
     *
     * @param originalFileName
     *            the originalFileName to set.
     */
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    /**
     * Returns the systemFileName.
     *
     * @return the systemFileName.
     */
    public String getSystemFileName() {
        return systemFileName;
    }

    /**
     * Updates the systemFileName with the specified value.
     *
     * @param systemFileName
     *            the systemFileName to set.
     */
    public void setSystemFileName(String systemFileName) {
        this.systemFileName = systemFileName;
    }

    /**
     * Returns the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description with the specified value.
     *
     * @param description
     *            the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the path.
     *
     * @return the path.
     */
    public FilePath getPath() {
        return path;
    }

    /**
     * Updates the path with the specified value.
     *
     * @param path
     *            the path to set.
     */
    public void setPath(FilePath path) {
        this.path = path;
    }

    /**
     * Returns the mimeType.
     *
     * @return the mimeType.
     */
    public MimeType getMimeType() {
        return mimeType;
    }

    /**
     * Updates the mimeType with the specified value.
     *
     * @param mimeType
     *            the mimeType to set.
     */
    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Returns the type.
     *
     * @return the type.
     */
    public DocumentType getType() {
        return type;
    }

    /**
     * Updates the type with the specified value.
     *
     * @param type
     *            the type to set.
     */
    public void setType(DocumentType type) {
        this.type = type;
    }

    /**
     * Returns the createDate.
     *
     * @return the createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Updates the createDate with the specified value.
     *
     * @param createDate
     *            the createDate to set.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Document) {
            return getDocumentId() == ((Document) obj).getDocumentId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code Document}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(documentId);
    }
}
