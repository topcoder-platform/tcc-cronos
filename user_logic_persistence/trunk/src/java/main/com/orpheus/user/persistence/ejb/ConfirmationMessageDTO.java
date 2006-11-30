/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.ejb;

import java.io.Serializable;
import java.util.Date;

import com.orpheus.user.persistence.impl.ConfirmationMessageTranslator;

/**
 * <p>
 * A serializable data transfer object that is used to transfer the information
 * in the <code>ConfirmationMessage</code> class between the EJB client and
 * the EJB session bean. This class does not perform any validation when its
 * fields are set.
 * </p>
 * <p>
 * The {@link ConfirmationMessageTranslator} can be used to convert this class
 * to the corresponding <code>ConfirmationMessage</code> instance and
 * vice-versa.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br> This class is mutable and, thus, not thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see ConfirmationMessageTranslator
 */
public class ConfirmationMessageDTO implements Serializable {

    /**
     * <p>
     * The message address.
     * </p>
     * <p>
     * This field is set and accessed in the setAddress(String) and getAddress()
     * methods, respectively. It can be any value.
     * </p>
     */
    private String address;

    /**
     * <p>
     * The unlock code.
     * </p>
     * <p>
     * This field is set and accessed in the setUnlockCode(String) and
     * getUnlockCode() methods, respectively. It can be any value.
     * </p>
     */
    private String unlockCode;

    /**
     * <p>
     * The date the message was sent.
     * </p>
     * <p>
     * This field is set and accessed in the setDateSent(Date) and getDateSent()
     * methods, respectively. It can be any value.
     * </p>
     */
    private Date dateSent;

    /**
     * <p>
     * The message subject.
     * </p>
     * <p>
     * This field is set and accessed in the setMessageSubject(String) and
     * getMessageSubject() methods, respectively. It can be any value.
     * </p>
     */
    private String messageSubject;

    /**
     * <p>
     * The message body.
     * </p>
     * <p>
     * This field is set and accessed in the setMessageBody(String) and
     * getMessageBody() methods, respectively. It can be any value.
     * </p>
     */
    private String messageBody;

    /**
     * <p>
     * Creates a new <code>ConfirmationMessageDTO</code> instance with all its
     * fields set to <code>null</code>.
     * </p>
     */
    public ConfirmationMessageDTO() {
        // Empty constructor.
    }

    /**
     * <p>
     * Gets the message address. Since no validation is performed when the
     * address is set, this method may return any value, including a
     * <code>null</code> or blank string.
     * </p>
     *
     * @return the message address
     * @see #setAddress(String)
     */
    public String getAddress() {
        return address;
    }

    /**
     * <p>
     * Sets the message address. This method accepts any input value, even
     * <code>null</code> or a blank string.
     * </p>
     *
     * @param address the message address
     * @see #getAddress()
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * <p>
     * Gets the unlock code. Since no validation is performed when the unlock
     * code is set, this method may return any value, including a
     * <code>null</code> or blank string.
     * </p>
     *
     * @return the unlock code
     * @see #setUnlockCode(String)
     */
    public String getUnlockCode() {
        return unlockCode;
    }

    /**
     * <p>
     * Sets the unlock code. This method accepts any input value, even
     * <code>null</code> or a blank string.
     * </p>
     *
     * @param unlockCode the unlock code
     * @see #getUnlockCode()
     */
    public void setUnlockCode(String unlockCode) {
        this.unlockCode = unlockCode;
    }

    /**
     * <p>
     * Gets the date the message was sent. Since no validation is performed when
     * the send date is set, this method may return any value, <code>null</code>.
     * </p>
     *
     * @return the date the message was sent
     * @see #setDateSent(Date)
     */
    public Date getDateSent() {
        return dateSent;
    }

    /**
     * <p>
     * Sets the date the message was sent. This method accepts any input value,
     * even <code>null</code>.
     * </p>
     *
     * @param dateSent the date the message was sent
     * @see #getDateSent()
     */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    /**
     * <p>
     * Gets the message subject. Since no validation is performed when the
     * message subject is set, this method may return any value, including a
     * <code>null</code> or blank string.
     * </p>
     *
     * @return the message subject
     * @see #setMessageSubject(String)
     */
    public String getMessageSubject() {
        return messageSubject;
    }

    /**
     * <p>
     * Sets the message subject. This method accepts any input value, even
     * <code>null</code> or a blank string.
     * </p>
     *
     * @param messageSubject the message subject
     * @see #getMessageSubject()
     */
    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    /**
     * <p>
     * Gets the message body. Since no validation is performed when the message
     * body is set, this method may return any value, including a
     * <code>null</code> or blank string.
     * </p>
     *
     * @return the message body
     * @see #setMessageBody(String)
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * <p>
     * Sets the message body. This method accepts any input value, even
     * <code>null</code> or a blank string.
     * </p>
     *
     * @param messageBody the message body
     * @see #getMessageBody()
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

}
