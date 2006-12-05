/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import com.orpheus.administration.Helper;
import com.orpheus.game.persistence.Domain;
import com.topcoder.user.profile.UserProfile;

/**
 * This is a data structure which holds a given sponsor represented by
 * UserProfile instance and its domains represented by Domain[]. An array of
 * SponsorDomain instances is set as request attribute value by
 * PendingSponsorHandler class.<br/> Thread-Safety: This class is immutable and
 * hence thread-safe.
 *
 * @author bose_java, KKD
 * @version 1.0
 */
public class SponsorDomain {

    /**
     * holds the UserProfile instance representing the sponsor.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null.<br/>
     *
     */
    private final UserProfile sponsor;

    /**
     * holds the domains associated with the sponsor.<br/> It is initialized in
     * the constructor and does not change after that.<br/> It may be null or
     * empty depending on what is returned by Game Persistence component when
     * searching for domains for a given sponsor id.<br/>
     *
     */
    private final Domain[] domains;

    /**
     * Creates a SponsorDomain instance with given sponsor and associated
     * domains.
     *
     *
     * @param sponsor
     *            the sponsor.
     * @param domains
     *            the associated domains.
     * @throws IllegalArgumentException
     *             if sponsor is null.
     */
    public SponsorDomain(UserProfile sponsor, Domain[] domains) {
        Helper.checkNull(sponsor, "sponsor");

        this.sponsor = sponsor;
        this.domains = domains;
    }

    /**
     * returns the UserProfile representing the sponsor.
     *
     *
     * @return the UserProfile representing the sponsor.
     */
    public UserProfile getSponsor() {
        return sponsor;
    }

    /**
     * returns the domains associated with this sponsor.
     *
     *
     * @return the domains associated with this sponsor.
     */
    public Domain[] getDomains() {
        return domains;
    }
}
