/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.model;

import java.io.Serializable;
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
 * This is a data class for document name. It is an entity that can be managed by JPA. This entity is for
 * <b>document_name</b> table.
 * </p>
 * <p>
 * A document name has the following attributes:
 * <ul>
 * <li>document name id</li>
 * <li>name</li>
 * </ul>
 * A document name also contains the following reference attributes:
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
@Table(name = "document_name")
public class DocumentName implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 5212044994503465170L;

    /**
     * <p>
     * Represents the id of document name.
     * </p>
     * <p>
     * Initially it is null. Once it is set it can't be null again. Getter and setter methods are provided. Normally
     * this value should be set by id generator.
     * </p>
     */
    private Long documentNameId;

    /**
     * <p>
     * Represents the name of document name.
     * </p>
     * <p>
     * Initially it is null. Once it is set, it can't be null or empty. Getter and setter methods are provided.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Represents all the document versions for this document name.
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
    public DocumentName() {
    }

    /**
     * <p>
     * Returns the id of the document name.
     * </p>
     *
     * @return the id of the document name, possibly null if not set.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DocumentNameSeq")
    @SequenceGenerator(name = "DocumentNameSeq", sequenceName = "DocumentNameSeq")
    @Column(name = "document_name_id")
    public Long getDocumentNameId() {
        return documentNameId;
    }

    /**
     * <p>
     * Sets the document name id to the given value.
     * </p>
     *
     * @param documentNameId
     *            the document name id to set
     * @throws IllegalArgumentException
     *             if documentNameId is null
     */
    public void setDocumentNameId(Long documentNameId) {
        Helper.checkNull(documentNameId, "documentNameId");

        this.documentNameId = documentNameId;
    }

    /**
     * <p>
     * Returns the name of the document that this class represents for.
     * </p>
     *
     * @return the name of the document that this class represents for, possibly null if not set. never empty.
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name of the document that this class represents for to the given value.
     * </p>
     *
     * @param name
     *            the new document name to set
     * @throws IllegalArgumentException
     *             if name is null or empty
     */
    public void setName(String name) {
        Helper.checkNullOrEmpty(name, "name");

        this.name = name;
    }

    /**
     * <p>
     * Returns all the document versions for this document.
     * </p>
     *
     * @return all the document versions for this document, never null possibly empty.
     */
    @OneToMany(mappedBy = "documentName")
    public Set<DocumentVersion> getDocumentVersions() {
        return new HashSet<DocumentVersion>(documentVersions);
    }

    /**
     * <p>
     * Sets the associated document versions to the given value. The old associated document versions will completely be
     * replaced with the given one.
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
