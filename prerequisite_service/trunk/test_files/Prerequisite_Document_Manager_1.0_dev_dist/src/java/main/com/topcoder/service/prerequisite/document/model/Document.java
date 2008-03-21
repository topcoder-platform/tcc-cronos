/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.topcoder.service.prerequisite.document.Helper;

/**
 * <p>
 * This is a data class for a document. It is an entity that can be managed by JPA. This entity is for <b>document</b>
 * table.
 * </p>
 * <p>
 * A document has the following attributes:
 * <ul>
 * <li>document id</li>
 * <li>create date</li>
 * <li>description</li>
 * </ul>
 * A document also contains the following reference attributes:
 * <ul>
 * <li>a set of document versions</li>
 * </ul>
 * </p>
 * <p>
 * <b>Thread Safety</b>: this class is mutable and so is not thread safety.
 * </p>
 *
 * @author biotrail, TCSDEVELOPER
 * @version 1.0
 */
@Entity
@Table(name = "document")
public class Document implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -8849286443222995948L;

    /**
     * <p>
     * Represents the id of document.
     * </p>
     * <p>
     * Initially it is null. Once it is set it can't be null again. Getter and setter methods are provided. Normally
     * this value should be set by id generator.
     * </p>
     */
    private Long documentId;

    /**
     * <p>
     * Represents the create date of document.
     * </p>
     * <p>
     * Initially it is null. Once it is set, it can't be null. Getter and setter methods are provided.
     * </p>
     */
    private Date createDate;

    /**
     * <p>
     * Represents the optional description of document.
     * </p>
     * <p>
     * It can be any String value. It is null initially. Getter and setter methods are provided.
     * </p>
     */
    private String description;

    /**
     * <p>
     * Represents all the document versions for this document.
     * </p>
     * <p>
     * It is set to an empty HashSet and the reference will be frozen. The content can be changed in runtime. This set
     * will not contain any null element. Getter and setter methods are provided.
     * </p>
     */
    private final Set<DocumentVersion> documentVersions = new HashSet<DocumentVersion>();

    /**
     * This is the default constructor. It does nothing.
     */
    public Document() {
    }

    /**
     * <p>
     * Returns the id of the document.
     * </p>
     *
     * @return the id of the document, possibly null if not set.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DocumentSeq")
    @SequenceGenerator(name = "DocumentSeq", sequenceName = "DocumentSeq")
    @Column(name = "document_id")
    public Long getDocumentId() {
        return documentId;
    }

    /**
     * <p>
     * Sets the document id to the given value.
     * </p>
     *
     * @param documentId
     *            the document id to set
     * @throws IllegalArgumentException
     *             if documentId is null
     */
    public void setDocumentId(Long documentId) {
        Helper.checkNull(documentId, "documentId");

        this.documentId = documentId;
    }

    /**
     * <p>
     * Returns the create date of the document.
     * </p>
     *
     * @return the create date of the document, possibly null if not set.
     */
    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>
     * Sets the create date.
     * </p>
     *
     * @param createDate
     *            the new create date to set
     * @throws IllegalArgumentException
     *             if createDate is null
     */
    public void setCreateDate(Date createDate) {
        Helper.checkNull(createDate, "createDate");

        this.createDate = createDate;
    }

    /**
     * <p>
     * Gets the description of the document.
     * </p>
     * <p>
     * Note, the returned value may be null or empty
     * </p>
     *
     * @return the description of the document, possibly null or empty.
     */
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the description of the document to the given value.
     * </p>
     * <p>
     * Note, the given value can be null or empty.
     * </p>
     *
     * @param description
     *            the new description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Returns all the document versions for this document.
     * </p>
     *
     * @return A shallow copy of all the document versions for this document, never null, possibly empty.
     */
    @OneToMany(mappedBy = "document")
    public Set<DocumentVersion> getDocumentVersions() {
        return new HashSet<DocumentVersion>(documentVersions);
    }

    /**
     * <p>
     * Sets the associated document versions to the given value.
     * </p>
     * <p>
     * The old associated document versions will completely be replaced with the given one.
     * </p>
     *
     * @param documentVersions
     *            the new document versions to be associated with this document
     * @throws IllegalArgumentException
     *             if documentVersions is null or contains null element in it
     */
    public void setDocumentVersions(Set<DocumentVersion> documentVersions) {
        Helper.checkCollection(documentVersions, "documentVersions");

        this.documentVersions.clear();
        this.documentVersions.addAll(documentVersions);
    }
}
