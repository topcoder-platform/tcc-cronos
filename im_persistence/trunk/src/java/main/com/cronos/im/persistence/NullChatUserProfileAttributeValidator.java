/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.chat.user.profile.ChatUserProfile;

import com.topcoder.util.datavalidator.ObjectValidator;

/**
 * <p>An implementation of <code>ObjectValidator</code> that verifies that a {@link ChatUserProfile ChatUserProfile}
 * has a non-empty {@link UserDefinedAttributeNames#FIRST_NAME first name}, {@link
 * UserDefinedAttributeNames#LAST_NAME last name}, and {@link UserDefinedAttributeNames#EMAIL e-mail address}.
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class NullChatUserProfileAttributeValidator implements ObjectValidator {
    /**
     * Constructs a new <code>NullChatUserProfileAttributeValidator</code>.
     */
    public NullChatUserProfileAttributeValidator() {
    }

    /**
     * Determines whether the specified object is valid. An object is valid if and only if it is a
     * <code>ChatUserProfile</code> that has a non-empty first name, last name, and e-mail address.
     *
     * @param obj the object ot test for validity
     * @return <code>true</code> if <code>obj</code> is valid, otherwise <code>false</code>
     */
    public boolean valid(Object obj) {
        return getMessage(obj) == null;
    }

    /**
     * Returns a string describing all of the problems with <code>obj</code>, or <code>null</code> if no problems
     * were found. Problems include a missing or invalid first name, last name, or e-mail address.
     *
     * @param obj the object to test for problems
     * @return an appropriate error message or <code>null</code> if no errors were found
     */
    public String getMessage(Object obj) {
        if (obj == null) {
            return "profile is null";
        }

        ChatUserProfile profile;

        try {
            profile = (ChatUserProfile) obj;
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("object is not a ChatUserProfile");
        }

        StringBuffer message = new StringBuffer();

        String[] firstName = profile.getPropertyValue(UserDefinedAttributeNames.FIRST_NAME);
        if (firstName.length == 0) {
            message.append("Missing first name.\n");
        } else if (firstName[0].trim().length() == 0) {
            message.append("Empty first name.\n");
        }

        String[] lastName = profile.getPropertyValue(UserDefinedAttributeNames.LAST_NAME);
        if (lastName.length == 0) {
            message.append("Missing last name.\n");
        } else if (lastName[0].trim().length() == 0) {
            message.append("Empty last name.\n");
        }

        String[] email = profile.getPropertyValue(UserDefinedAttributeNames.EMAIL);
        if (email.length == 0) {
            message.append("Missing e-mail address.\n");
        } else if (email[0].trim().length() == 0) {
            message.append("Empty e-mail address.\n");
        }

        return (message.length() == 0 ? null : message.toString());
    }
}
