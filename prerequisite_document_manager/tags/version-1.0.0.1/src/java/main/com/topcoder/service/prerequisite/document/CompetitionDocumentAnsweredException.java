/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import javax.ejb.ApplicationException;

import com.topcoder.service.prerequisite.document.model.MemberAnswer;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown if the competition document has been answered by the user while the user wants to answer the
 * competition document again.
 * </p>
 * <p>
 * This exception must have the "@ApplicationException(rollback=true)" annotation so that if any error occurs the
 * transaction could be automatically rolled back by EJB container. It also makes sure the exception is directly thrown
 * back to client without any wrapping.
 * </p>
 *
 * @author biotrail, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class CompetitionDocumentAnsweredException extends DocumentPersistenceException {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 5976907617226796366L;
    /**
     * <p>
     * Represents the replicate member answer, it is set in the constructor.
     * </p>
     */
    private final MemberAnswer memberAnswer;

    /**
     * <p>
     * Creates a new exception instance with the given error message and replicate member answer.
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     * @param memberAnswer
     *            the replicate member answer to add
     */
    public CompetitionDocumentAnsweredException(String message, MemberAnswer memberAnswer) {
        super(message);
        this.memberAnswer = memberAnswer;
    }

    /**
     * <p>
     * Creates a new exception instance with the given message, cause and replicate member answer.
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     * @param cause
     *            Underlying cause of the error. Can be null, which means that initial exception is nonexistent or
     *            unknown.
     * @param memberAnswer
     *            the replicate member answer to add
     */
    public CompetitionDocumentAnsweredException(String message, Throwable cause, MemberAnswer memberAnswer) {
        super(message, cause);
        this.memberAnswer = memberAnswer;
    }

    /**
     * <p>
     * Creates a new exception instance with the given error message, data and replicate member answer.
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     * @param data
     *            The additional data to attach to the exception - This can be null and if this is null, a new
     *            ExceptionData() will be used instead.
     * @param memberAnswer
     *            the replicate member answer to add
     */
    public CompetitionDocumentAnsweredException(String message, ExceptionData data, MemberAnswer memberAnswer) {
        super(message, data);
        this.memberAnswer = memberAnswer;
    }

    /**
     * <p>
     * Creates a new exception instance with the given error message, cause, the additional data to attach and replicate
     * member answer.
     * </p>
     *
     * @param message
     *            Explanation of the error. Can be empty String or null (useless, but allowed).
     * @param cause
     *            Underlying cause of the error. Can be null, which means that initial exception is nonexistent or
     *            unknown.
     * @param data
     *            The additional data to attach to the exception - This can be null and if this is null, a new
     *            ExceptionData() will be used instead.
     * @param memberAnswer
     *            the replicate member answer to add
     */
    public CompetitionDocumentAnsweredException(String message, Throwable cause, ExceptionData data,
        MemberAnswer memberAnswer) {
        super(message, cause, data);
        this.memberAnswer = memberAnswer;
    }

    /**
     * <p>
     * Returns the replicate member answer.
     * </p>
     *
     * @return the replicate member answer
     */
    public MemberAnswer getMemberAnswer() {
        return memberAnswer;
    }
}
