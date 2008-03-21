/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.topcoder.service.prerequisite.document.Helper;

/**
 * <p>
 * This is a data class for member answer for a competition document. It is an entity that can be managed by JPA. This
 * entity is for <b>member_answer</b> table.
 * </p>
 * <p>
 * A member answer has the following attributes:
 * <ul>
 * <li>member answer id</li>
 * <li>member id</li>
 * <li>accept or reject answer</li>
 * <li>date of answer</li>
 * </ul>
 * A member answer also contains the following reference attributes:
 * <ul>
 * <li>competition document</li>
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
@Table(name = "member_answer")
public class MemberAnswer implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 1980305975778967353L;

    /**
     * <p>
     * Represents the id of member answer.
     * </p>
     * <p>
     * Initially it is null. Once it is set it can't be null again. Getter and setter methods are provided. Normally
     * this value should be set by id generator.
     * </p>
     */
    private Long memberAnswerId;

    /**
     * <p>
     * Represents the competition document that this member answer responses for.
     * </p>
     * <p>
     * Initially it is null. Once it is set, it can't be null. Getter and setter methods are provided.
     * </p>
     */
    private CompetitionDocument competitionDocument;

    /**
     * <p>
     * Represents the id of user.
     * </p>
     * <p>
     * Initially it is null. Once it is set, it can't be null. Getter and setter methods are provided.
     * </p>
     */
    private Long memberId;

    /**
     * Represents the answer result: true - user accept the document, otherwise false By default the answer is true.
     * Getter and setter methods are provided.
     */
    private boolean answer;

    /**
     * <p>
     * Represents the date of answer.
     * </p>
     * <p>
     * Initially it is null. Once it is set, it can't be null. Getter and setter methods are provided.
     * </p>
     */
    private Date answerDate;

    /**
     * This is the default constructor. It does nothing.
     */
    public MemberAnswer() {
    }

    /**
     * <p>
     * Returns the member answer id.
     * </p>
     *
     * @return the member answer id, possibly null if not set.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MemberAnswerSeq")
    @SequenceGenerator(name = "MemberAnswerSeq", sequenceName = "MemberAnswerSeq")
    @Column(name = "member_answer_id")
    public Long getMemberAnswerId() {
        return memberAnswerId;
    }

    /**
     * <p>
     * Sets the member answer id to the given value.
     * </p>
     *
     * @param memberAnswerId
     *            the member answer id to set
     * @throws IllegalArgumentException
     *             if memberAnswerId is null
     */
    public void setMemberAnswerId(Long memberAnswerId) {
        Helper.checkNull(memberAnswerId, "memberAnswerId");

        this.memberAnswerId = memberAnswerId;
    }

    /**
     * <p>
     * Returns the competition document that this member answer responses for.
     * </p>
     *
     * @return the competition document that this member answer responses for, possibly null if not set.
     */
    @ManyToOne
    @JoinColumn(name = "competition_document_id")
    public CompetitionDocument getCompetitionDocument() {
        return competitionDocument;
    }

    /**
     * <p>
     * Sets the competition document that this member answer responses for to the given value.
     * </p>
     *
     * @param competitionDocument
     *            the new competition document to set
     * @throws IllegalArgumentException
     *             if competitionDocument is null
     */
    public void setCompetitionDocument(CompetitionDocument competitionDocument) {
        Helper.checkNull(competitionDocument, "competitionDocument");

        this.competitionDocument = competitionDocument;
    }

    /**
     * <p>
     * Returns the member id.
     * </p>
     *
     * @return the member id, possibly null if not set.
     */
    @Column(name = "member_id")
    public Long getMemberId() {
        return memberId;
    }

    /**
     * <p>
     * Sets the member id to the given value.
     * </p>
     *
     * @param memberId
     *            the new member id to set
     * @throws IllegalArgumentException
     *             if memberId is null
     */
    public void setMemberId(Long memberId) {
        Helper.checkNull(memberId, "memberId");

        this.memberId = memberId;
    }

    /**
     * <p>
     * Returns the member answer, true - user accept the document, otherwise false.
     * </p>
     *
     * @return the member answer, true - user accept the document, otherwise false.
     */
    @Column(name = "answer")
    public boolean getAnswer() {
        return answer;
    }

    /**
     * <p>
     * Sets the member answer, true - user accept the document, otherwise false.
     * </p>
     *
     * @param answer
     *            the member answer to set
     */
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    /**
     * <p>
     * Returns the date of answer.
     * </p>
     *
     * @return the date of answer, possibly null if not set.
     */
    @Column(name = "answer_date")
    public Date getAnswerDate() {
        return answerDate;
    }

    /**
     * <p>
     * Sets the date of answer to the given value.
     * </p>
     *
     * @param answerDate
     *            the date of answer
     * @throws IllegalArgumentException
     *             if answerDate is null
     */
    public void setAnswerDate(Date answerDate) {
        Helper.checkNull(answerDate, "answerDate");

        this.answerDate = answerDate;
    }
}
