/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.topcoder.validation.emailconfirmation.ContentCustomizerInterface;

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

/**
 * <p>A custom implementation of {@link ContentCustomizerInterface} to be used in context of <code>Orpheus Game Server
 * </code> application. Such a customizer is required by <code>Email Confirmation</code> component.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusEmailContentCustomizer implements ContentCustomizerInterface {

    /**
     * <p>Constructs new <code>OrpheusEmailContentCustomizer</code> instance. This implementation does nothing.</p>
     */
    public OrpheusEmailContentCustomizer() {
    }

    /**
     * <p>Returns the customized content that matches <code>fieldName</code> for specified address. This implementation
     * simply returns the <code>fieldName</code> as is.</p>
     *
     * @param address a <code>String</code> providing the e-mail address message is being generated for.
     * @param fieldName a <code>String</code> poviding the identifier for the field being replaced.
     * @return content a <code>String</code> to replace <code>fieldName</code> in message.
     */
    public String customize(String address, String fieldName) {
        try {
            return URLEncoder.encode(fieldName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return fieldName;
        }
    }
}
