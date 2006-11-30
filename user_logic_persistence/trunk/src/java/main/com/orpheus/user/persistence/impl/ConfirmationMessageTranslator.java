/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import com.orpheus.user.persistence.ObjectTranslator;
import com.orpheus.user.persistence.OrpheusPendingConfirmationStorage;
import com.orpheus.user.persistence.PendingConfirmationDAO;
import com.orpheus.user.persistence.TranslationException;
import com.orpheus.user.persistence.ejb.ConfirmationMessageDTO;
import com.topcoder.validation.emailconfirmation.ConfirmationMessage;

/**
 * <p>
 * An implementation of the {@link ObjectTranslator} interface which converts
 * <code>ConfirmationMessage</code> objects to corresponding
 * {@link ConfirmationMessageDTO} instances, and vice-versa. The
 * <code>ConfirmationMessage</code> objects are value objects, and are usually
 * used outside the component, while the <code>ConfirmationMessageDTO</code>
 * instances are serializable data transfer object that used to transfer
 * confirmation message information between EJB clients and the
 * {@link PendingConfirmationDAO} class.
 * </p>
 * <p>
 * <code>ConfirmationMessageTranslator</code> is used by the
 * {@link OrpheusPendingConfirmationStorage} class.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br> This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see ConfirmationMessage
 * @see ConfirmationMessageDTO
 */
public class ConfirmationMessageTranslator implements ObjectTranslator {

    /**
     * <p>
     * Creates a new <code>ConfirmationMessageTranslator</code>.
     * </p>
     */
    public ConfirmationMessageTranslator() {
        // Empty constructor.
    }

    /**
     * <p>
     * Assembles and returns a <code>ConfirmationMessage</code> object when
     * given a {@link ConfirmationMessageDTO} argument. If the argument is not a
     * <code>ConfirmationMessageDTO</code> instance, an
     * <code>IllegalArgumentException</code> is thrown. If assembling the
     * <code>ConfirmationMessage</code> from the
     * <code>ConfirmationMessageDTO</code> argument fails, a
     * <code>TranslationException</code> is thrown.
     * </p>
     *
     * @param dataTransferObject the <code>ConfirmationMessageDTO</code>
     *        instance from which to assemble the corresponding
     *        <code>ConfirmationMessage</code> object
     * @return the <code>ConfirmationMessage</code> corresponding to the given
     *         <code>ConfirmationMessageDTO</code> object
     * @throws IllegalArgumentException if the given data transfer object is
     *         <code>null</code>, or is not of type
     *         <code>ConfirmationMessageDTO</code>
     * @throws TranslationException if assembling the
     *         <code>ConfirmationMessage</code> from the given
     *         <code>ConfirmationMessageDTO</code> fails
     * @see #assembleDTO(Object)
     */
    public Object assembleVO(Object dataTransferObject) throws TranslationException {
        if (!(dataTransferObject instanceof ConfirmationMessageDTO)) {
            throw new IllegalArgumentException("The DTO must be a non-null ConfirmationMessageDTO instance");
        }

        ConfirmationMessageDTO messageDTO = (ConfirmationMessageDTO) dataTransferObject;

        try {
            return new ConfirmationMessage(messageDTO.getAddress(), messageDTO.getUnlockCode(),
                    messageDTO.getDateSent(), messageDTO.getMessageSubject(), messageDTO.getMessageBody());
        } catch (IllegalArgumentException e) {
            // IllegalArgumentException is thrown whenever a ConfirmationMessage
            // constructor argument is null. We simply treat it as a translation
            // error.
            throw new TranslationException("Invalid information in ConfirmationMessageDTO", e);
        }
    }

    /**
     * <p>
     * Assembles and returns a {@link ConfirmationMessageDTO} when given a
     * <code>ConfirmationMessage</code> argument. If the argument is not a
     * <code>ConfirmationMessage</code> instance, an
     * <code>IllegalArgumentException</code> is thrown. If assembling the
     * <code>ConfirmationMessageDTO</code> from the
     * <code>ConfirmationMessage</code> argument fails, a
     * <code>TranslationException</code> is thrown.
     * </p>
     *
     * @param valueObject the <code>ConfirmationMessage</code> instance from
     *        which to assemble the corresponding
     *        <code>ConfirmationMessageDTO</code> object
     * @return the <code>ConfirmationMessageDTO</code> object corresponding to
     *         the given <code>ConfirmationMessage</code> object
     * @throws IllegalArgumentException if the given value object is
     *         <code>null</code>, or is not of type
     *         <code>ConfirmationMessage</code>
     * @see #assembleVO(Object)
     */
    public Object assembleDTO(Object valueObject) {
        if (!(valueObject instanceof ConfirmationMessage)) {
            throw new IllegalArgumentException("The value object must be a non-null ConfirmationMessage instance");
        }

        ConfirmationMessage message = (ConfirmationMessage) valueObject;

        ConfirmationMessageDTO messageDTO = new ConfirmationMessageDTO();
        messageDTO.setAddress(message.getAddress());
        messageDTO.setDateSent(message.getDateSent());
        messageDTO.setMessageBody(message.getMessageBody());
        messageDTO.setMessageSubject(message.getMessageSubject());
        messageDTO.setUnlockCode(message.getUnlockCode());

        return messageDTO;
    }

}
