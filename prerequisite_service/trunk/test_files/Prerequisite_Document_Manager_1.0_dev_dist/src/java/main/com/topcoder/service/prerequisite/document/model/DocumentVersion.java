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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.topcoder.service.prerequisite.document.Helper;

/**
 * <p>
 * This is a data class for document version. It represents concrete document version. It is an entity that can be
 * managed by JPA. This entity is for <b>document_version</b> table.
 * </p>
 * <p>
 * A document version has the following attributes:
 * <ul>
 * <li>document version id</li>
 * <li>document version</li>
 * <li>date of version</li>
 * <li>content</li>
 * </ul>
 * A document version also contains the following reference attributes:
 * <ul>
 * <li>document</li>
 * <li>document name</li>
 * <li>a set of competition documents</li>
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
@Table(name = "document_version")
public class DocumentVersion implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -8330823717395393084L;

    /**
     * <p>
     * Represents the id of concrete document version.
     * </p>
     * <p>
     * Initially it is null. Once it is set it can't be null again. Getter and setter methods are provided. Normally
     * this value should be set by id generator.
     * </p>
     */
    private Long documentVersionId;

    /**
     * <p>
     * Represents the document that this document version refers to.
     * </p>
     * <p>
     * Initially it is null. Once it is set it can't be null again. Getter and setter methods are provided.
     * </p>
     */
    private Document document;

    /**
     * <p>
     * Represents the document name that this document version refers to.
     * </p>
     * <p>
     * Initially it is null. Once it is set it can't be null again. Getter and setter methods are provided.
     * </p>
     */
    private DocumentName documentName;

    /**
     * <p>
     * Represents the document version.
     * </p>
     * <p>
     * Initially it is null. Once it is set it can't be null again. Getter and setter methods are provided.
     * </p>
     */
    private Integer documentVersion;

    /**
     * <p>
     * Represents the date of version.
     * </p>
     * <p>
     * Initially it is null. Once it is set it can't be null again. Getter and setter methods are provided.
     * </p>
     */
    private Date versionDate;

    /**
     * <p>
     * Represents the content of this document version.
     * </p>
     * <p>
     * Initially it is null. Once it is set it can't be null or empty again. Getter and setter methods are provided.
     * </p>
     */
    private String content;

    /**
     * <p>
     * Represents the set of competition documents that are associated with this document version.
     * </p>
     * <p>
     * It is set to an empty HashSet and the reference will be frozen. The content can be changed in runtime. This set
     * will not contain any null element. Getter and setter methods are provided.
     * </p>
     */
    private final Set<CompetitionDocument> competitionDocuments = new HashSet<CompetitionDocument>();

    /**
     * This is the default constructor. It does nothing.
     */
    public DocumentVersion() {
    }

    /**
     * <p>
     * Returns the document version id.
     * </p>
     *
     * @return the document version id, possibly null if not set.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DocumentVersionSeq")
    @SequenceGenerator(name = "DocumentVersionSeq", sequenceName = "DocumentVersionSeq")
    @Column(name = "document_version_id")
    public Long getDocumentVersionId() {
        return documentVersionId;
    }

    /**
     * <p>
     * Sets the document version id to the given value.
     * </p>
     *
     * @param documentVersionId
     *            the document version id to set
     * @throws IllegalArgumentException
     *             if documentVersionId is null
     */
    public void setDocumentVersionId(Long documentVersionId) {
        Helper.checkNull(documentVersionId, "documentVersionId");

        this.documentVersionId = documentVersionId;
    }

    /**
     * <p>
     * Returns the document for this document version.
     * </p>
     *
     * @return the document for this document version, possibly null if not set.
     */
    @ManyToOne
    @JoinColumn(name = "document_id")
    public Document getDocument() {
        return document;
    }

    /**
     * <p>
     * Sets the document to the given value.
     * </p>
     *
     * @param document
     *            the new document to set
     * @throws IllegalArgumentException
     *             if document is null
     */
    public void setDocument(Document document) {
        Helper.checkNull(document, "document");

        this.document = document;
    }

    /**
     * <p>
     * Returns the document name for this document version.
     * </p>
     *
     * @return the document name for this document version, possibly null if not set.
     */
    @ManyToOne
    @JoinColumn(name = "document_name_id")
    public DocumentName getDocumentName() {
        return documentName;
    }

    /**
     * <p>
     * Sets the document name to the given value.
     * </p>
     *
     * @param documentName
     *            the new document name to set
     * @throws IllegalArgumentException
     *             if documentName is null
     */
    public void setDocumentName(DocumentName documentName) {
        Helper.checkNull(documentName, "documentName");

        this.documentName = documentName;
    }

    /**
     * <p>
     * Returns the document version.
     * </p>
     *
     * @return the document version, possibly null if not set.
     */
    @Column(name = "version")
    public Integer getDocumentVersion() {
        return documentVersion;
    }

    /**
     * <p>
     * Sets the document version to the given value.
     * </p>
     *
     * @param documentVersion
     *            the new document version to set
     * @throws IllegalArgumentException
     *             if documentVersion is null
     */
    public void setDocumentVersion(Integer documentVersion) {
        Helper.checkNull(documentVersion, "documentVersion");

        this.documentVersion = documentVersion;
    }

    /**
     * <p>
     * Returns the date of version.
     * </p>
     *
     * @return the date of version, possibly null if not set.
     */
    @Column(name = "version_date")
    public Date getVersionDate() {
        return versionDate;
    }

    /**
     * Sets the date of document version to the given value.
     *
     * @param versionDate
     *            the new version date to set
     * @throws IllegalArgumentException
     *             if versionDate is null
     */
    public void setVersionDate(Date versionDate) {
        Helper.checkNull(versionDate, "versionDate");

        this.versionDate = versionDate;
    }

    /**
     * <p>
     * Returns the content of this document version.
     * </p>
     *
     * @return the content of this document version, possibly null if not set.
     */
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    /**
     * <p>
     * Sets the content of this document version to the given value.
     * </p>
     *
     * @param content
     *            the new content to set
     * @throws IllegalArgumentException
     *             if content is null or empty
     */
    public void setContent(String content) {
        Helper.checkNullOrEmpty(content, "content");

        this.content = content;
    }

    /**
     * <p>
     * Returns all the competition documents that are associated with this document version.
     * </p>
     *
     * @return all the competition documents that are associated with this document version, never null, possibly empty.
     */
    @OneToMany(mappedBy = "documentVersion")
    public Set<CompetitionDocument> getCompetitionDocuments() {
        return new HashSet<CompetitionDocument>(competitionDocuments);
    }

    /**
     * Sets the associated competition documents to the given value. The old associated competition documents will
     * completely be replaced with the given one.
     *
     * @param competitionDocuments
     *            the new associated competition documents to set
     * @throws IllegalArgumentException
     *             if competitionDocuments is null or contains null element in it
     */
    public void setCompetitionDocuments(Set<CompetitionDocument> competitionDocuments) {
        Helper.checkCollection(competitionDocuments, "competitionDocuments");

        this.competitionDocuments.clear();
        this.competitionDocuments.addAll(competitionDocuments);
    }
}
