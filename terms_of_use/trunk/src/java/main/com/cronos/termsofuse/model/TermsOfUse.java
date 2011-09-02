/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.termsofuse.model;

import java.io.Serializable;

/**
 * <p>
 * This class defines simple entity of terms of use. It is associated with the database table terms_of_use. It
 * provides model fields and getters/setters for them.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The class is mutable and not thread safe. The class is used in thread safe manner
 * by this component. Any external application should access it in a thread safe manner.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public class TermsOfUse implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = -4700551976551534017L;

    /**
     * <p>
     * The terms of use id.
     * </p>
     *
     * <p>
     * It is accessed by getter and modified by setter. The default value is not set. The legal value is any value.
     * </p>
     */
    private long termsOfUseId;

    /**
     * <p>
     * The terms of use type id.
     * </p>
     *
     * <p>
     * It is accessed by getter and modified by setter. The default value is not set. The legal value is any value.
     * </p>
     */
    private int termsOfUseTypeId;

    /**
     * <p>
     * The title of terms.
     * </p>
     *
     * <p>
     * It is accessed by getter and modified by setter. The default value is null. The legal value is any value.
     * </p>
     */
    private String title;

    /**
     * <p>
     * The flag, specifying whether the member can electronically sign these terms.
     * </p>
     *
     * <p>
     * It is accessed by getter and modified by setter. The default value is not set. The legal value is any value.
     * </p>
     */
    private boolean electronicallySignable;

    /**
     * <p>
     * The url of terms.
     * </p>
     *
     * <p>
     * It is accessed by getter and modified by setter. The default value is null. The legal value is any value.
     * </p>
     */
    private String url;

    /**
     * <p>
     * The flag, specifying whether the member can agree to these terms.
     * </p>
     *
     * <p>
     * It is accessed by getter and modified by setter. The default value is not set. The legal value is any value.
     * </p>
     */
    private boolean memberAgreeable;

    /**
     * Creates an instance of TermsOfUse.
     */
    public TermsOfUse() {
        // Empty
    }

    /**
     * Gets the terms of use id.
     *
     * @return the terms of use id.
     */
    public long getTermsOfUseId() {
        return termsOfUseId;
    }

    /**
     * Sets the terms of use id.
     *
     * @param termsOfUseId
     *            the terms of use id.
     */
    public void setTermsOfUseId(long termsOfUseId) {
        this.termsOfUseId = termsOfUseId;
    }

    /**
     * Gets the terms of use type id.
     *
     * @return the terms of use type id.
     */
    public int getTermsOfUseTypeId() {
        return termsOfUseTypeId;
    }

    /**
     * Sets the terms of use type id.
     *
     * @param termsOfUseTypeId
     *            the terms of use type id.
     */
    public void setTermsOfUseTypeId(int termsOfUseTypeId) {
        this.termsOfUseTypeId = termsOfUseTypeId;
    }

    /**
     * Gets the title of terms.
     *
     * @return the title of terms.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of terms.
     *
     * @param title
     *            the title of terms.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the flag, specifying whether the member can electronically sign these terms.
     *
     * @return the flag, specifying whether the member can electronically sign these terms.
     */
    public boolean isElectronicallySignable() {
        return electronicallySignable;
    }

    /**
     * Sets the flag, specifying whether the member can electronically sign these terms.
     *
     * @param electronicallySignable
     *            the flag, specifying whether the member can electronically sign these terms.
     */
    public void setElectronicallySignable(boolean electronicallySignable) {
        this.electronicallySignable = electronicallySignable;
    }

    /**
     * Gets the url of terms.
     *
     * @return the url of terms.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url of terms.
     *
     * @param url
     *            the url of terms.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the flag, specifying whether the member can agree to these terms.
     *
     * @return the flag, specifying whether the member can agree to these terms.
     */
    public boolean isMemberAgreeable() {
        return memberAgreeable;
    }

    /**
     * Sets the flag, specifying whether the member can agree to these terms.
     *
     * @param memberAgreeable
     *            the flag, specifying whether the member can agree to these terms.
     */
    public void setMemberAgreeable(boolean memberAgreeable) {
        this.memberAgreeable = memberAgreeable;
    }
}
