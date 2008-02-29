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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.topcoder.service.prerequisite.document.Helper;

/**
 * <p>
 * This is a data class for competition document. It is an entity that can be managed by JPA. This entity is for
 * <b>competition_document</b> table.
 * </p>
 * <p>
 * A competition document has the following attributes:
 * <ul>
 * <li>competition document id</li>
 * <li>competition id</li>
 * <li>role id</li>
 * </ul>
 * A competition document also contains the following reference attributes:
 * <ul>
 * <li>document version</li>
 * <li>a set of member answers</li>
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
@Table(name = "competition_document")
public class CompetitionDocument implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -4124920488243358808L;

    /**
     * <p>
     * Represents the id of competition document.
     * </p>
     * <p>
     * Initially it is null. Once it is set it can't be null again. Getter and setter methods are provided. Normally
     * this value should be set by id generator.
     * </p>
     */
    private Long competitionDocumentId;

    /**
     * <p>
     * Represents the concrete document version.
     * </p>
     * <p>
     * Initially it is null. Once it is set, it can't be null. Getter and setter methods are provided.
     * </p>
     */
    private DocumentVersion documentVersion;

    /**
     * <p>
     * Represents the id of competition.
     * </p>
     * <p>
     * Initially it is null. Once it is set, it can't be null. Getter and setter methods are provided.
     * </p>
     */
    private Long competitionId;

    /**
     * <p>
     * Represents the id of user role.
     * </p>
     * <p>
     * Initially it is null. Once it is set, it can't be null. Getter and setter methods are provided.
     * </p>
     */
    private Long roleId;

    /**
     * <p>
     * Represents the member answers that associated with this competition document.
     * </p>
     * <p>
     * It is set to an empty HashSet and the reference will be frozen. The content can be changed in runtime. This set
     * will not contain any null element. Getter and setter methods are provided.
     * </p>
     */
    private final Set<MemberAnswer> memberAnswers = new HashSet<MemberAnswer>();

    /**
     * This is the default constructor. It does nothing.
     */
    public CompetitionDocument() {
    }

    /**
     * <p>
     * Returns the id of the competition document.
     * </p>
     * @return the id of the competition document, possibly null if not set.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CompetitionDocumentSeq")
    @SequenceGenerator(name = "CompetitionDocumentSeq", sequenceName = "CompetitionDocumentSeq")
    @Column(name = "competition_document_id")
    public Long getCompetitionDocumentId() {
        return competitionDocumentId;
    }

    /**
     * <p>
     * Sets the competition document id.
     * </p>
     * @param competitionDocumentId
     *            the competition document id to set
     * @throws IllegalArgumentException
     *             if competitionDocumentId is null
     */
    public void setCompetitionDocumentId(Long competitionDocumentId) {
        Helper.checkNull(competitionDocumentId, "competitionDocumentId");

        this.competitionDocumentId = competitionDocumentId;
    }

    /**
     * <p>
     * Returns the associated document version.
     * </p>
     * @return the associated document version, possibly null if not set.
     */
    @ManyToOne
    @JoinColumn(name = "document_version_id")
    public DocumentVersion getDocumentVersion() {
        return documentVersion;
    }

    /**
     * <p>
     * Sets the associated document version.
     * </p>
     * @param documentVersion
     *            the new document version to set
     * @throws IllegalArgumentException
     *             if documentVersion is null
     */
    public void setDocumentVersion(DocumentVersion documentVersion) {
        Helper.checkNull(documentVersion, "documentVersion");

        this.documentVersion = documentVersion;
    }

    /**
     * <p>
     * Returns the competition id for this competition document.
     * </p>
     * @return the competition id, possibly null if not set.
     */
    @Column(name = "competition_id")
    public Long getCompetitionId() {
        return competitionId;
    }

    /**
     * <p>
     * Sets the competition id.
     * </p>
     * @param competitionId
     *            the new competition id to set
     * @throws IllegalArgumentException
     *             if competitionId is null
     */
    public void setCompetitionId(Long competitionId) {
        Helper.checkNull(competitionId, "competitionId");

        this.competitionId = competitionId;
    }

    /**
     * <p>
     * Returns the role id for this competition document.
     * </p>
     * @return the role id, possibly null if not set.
     */
    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }

    /**
     * <p>
     * Sets the role id.
     * </p>
     * @param roleId
     *            the new role id to set
     * @throws IllegalArgumentException
     *             if roleId is null
     */
    public void setRoleId(Long roleId) {
        Helper.checkNull(roleId, "roleId");

        this.roleId = roleId;
    }

    /**
     * <p>
     * Returns a set of associated member answers.
     * </p>
     * @return a set of associated member answers, never null, possibly empty.
     */
    @OneToMany(mappedBy = "competitionDocument")
    public Set<MemberAnswer> getMemberAnswers() {
        return new HashSet<MemberAnswer>(memberAnswers);
    }

    /**
     * <p>
     * Sets the associated member answers to the given value.
     * </p>
     * <p>
     * The old associated member answers will completely be replace with the given one.
     * </p>
     * @param memberAnswers
     *            the new member answers to be associated with this competition document
     * @throws IllegalArgumentException
     *             if memberAnswers is null or contains null element.
     */
    public void setMemberAnswers(Set<MemberAnswer> memberAnswers) {
        Helper.checkCollection(memberAnswers, "memberAnswers");

        this.memberAnswers.clear();
        this.memberAnswers.addAll(memberAnswers);
    }
}
