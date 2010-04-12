/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Represents the data for creating a competition. Provides data about the names
 * of the project, contest, types and sub types, the requested start date, the
 * associated billing project, and flags whether to auto-start the competition
 * and whether a CCA is required for it. It is passed in the createCompetition
 * method.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class CompetitionData implements Serializable {

    /**
     * <p>
     * Represents design contest
     * <p>
     */
    public static final String DESIGN = "Design";

    /**
     * <p>
     * Represents dev contest
     * <p>
     */
    public static final String DEVELOPMENT = "Development";

    /**
     * <p>
     * Represents arch
     * <p>
     */
    public static final String ARCHITECTURE = "Architecture";

    /**
     * <p>
     * Represents Test Suites
     * <p>
     */
    public static final String TEST_SUITES = "Test Suites";



    /**
     * <p>
     * Represents Assembly Competition
     * <p>
     */
    public static final String ASSEMBLY = "Assembly Competition";

    /**
     * <p>
     * Represents UI Prototypes
     * <p>
     */
    public static final String UI_PROTOTYPES = "UI Prototypes";

    /**
     * <p>
     * Represents Specification
     * <p>
     */
    public static final String SPECIFICATION = "Specification";

    /**
     * <p>
     * Represents Conceptualization
     * <p>
     */
    public static final String CONCEPTUALIZATION = "Conceptualization";

    /**
     * <p>
     * Represents RIA Build
     * <p>
     */
    public static final String RIA_BUILD = "RIA Build";

    /**
     * <p>
     * Represents RIA Component
     * <p>
     */
    public static final String RIA_COMPONENT = "RIA Component";

    /**
     * <p>
     * Represents Test Scenarios
     * <p>
     */
    public static final String TEST_SCENARIOS = "Test Scenarios";

    /**
     * <p>
     * Represents studio contest
     * <p>
     */
    public static final String STUDIO = "Studio";


    /**
     * <p>
     * Represents studio Web Page Design
     * <p>
     */
    public static final String STUDIO_WEB_PAGE_DESIGN = "Web Page Design";

    /**
     * <p>
     * Represents studio Application Front End Design
     * <p>
     */
    public static final String STUDIO_APPLICATION_FRONT_END_DESIGN = "Application Front End Design";

    /**
     * <p>
     * Represents studio Web Elements
     * <p>
     */
    public static final String STUDIO_WEB_ELEMENTS = "Web Elements";


    /**
     * <p>
     * Represents studio Logo Design
     * <p>
     */
    public static final String STUDIO_LOGO_DESIGN = "Logo Design";


    /**
     * <p>
     * Represents studio Icons
     * <p>
     */
    public static final String STUDIO_ICONS = "Icons";

     /**
     * <p>
     * Represents studio Print Design
     * <p>
     */
    public static final String STUDIO_PRINT_DESIGN = "Print Design";


    /**
     * <p>
     * Represents studio Logo Design
     * <p>
     */
    public static final String STUDIO_POWERPOINT_PRESENTATION = "PowerPoint Presentation";


    /**
     * <p>
     * Represents studio Other Static Design
     * <p>
     */
    public static final String STUDIO_OTHER_STATIC_DESIGN = "Other Static Design";

    /**
     * <p>
     * Represents the serial version unique id.
     * <p>
     */
    private static final long serialVersionUID = 5728736186476527499L;

    /**
     * <p>
     * Represents the contest type name. If its value is "studio", it means the
     * contest is a studio contest, otherwise, the contest is a software
     * competition, and this value means the project category name.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter.
     * </p>
     */
    private String contestTypeName;

    /**
     * <p>
     * Represents the studio contest type name if it's a studio contest.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter.
     * </p>
     */
    private String subContestTypeName;

    /**
     * <p>
     * Represents the name of contest.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter.
     * </p>
     */
    private String contestName;

    /**
     * <p>
     * Represents the name of Cockpit Project to associate contest to.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter
     * </p>
     */
    private String cockpitProjectName;

    /**
     * <p>
     * Represents the earliest date contest should launch.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter.
     * </p>
     */
    private Date requestedStartDate;

    /**
     * <p>
     * Represents the flag whether it is okay to find an alternative date and
     * time if capacity is full.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter.
     * </p>
     */
    private boolean autoReschedule;

    /**
     * <p>
     * Represents the billing project that contest will be associated with.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     */
    private long billingProjectId;

    /**
     * <p>
     * Represents the flag whether the contest should be created with CCA terms.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter.
     * </p>
     */
    private boolean cca;

    /**
     * <p>
     * Represents the flag whether creating design should auto create dev
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter.
     * </p>
     */ 
     private boolean autoCreateDev;


     /**
     * <p>
     * Represents the flag whether dev contest is dev only
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter.
     * </p>
     */ 
     private boolean devOnly;


    /**
     * <p> Default empty constructor. </p>
     */
    public CompetitionData() {
    }

    /**
     * <p>
     * Gets the contest type name.
     * </p>
     *
     * @return the contest type name
     */
    public String getContestTypeName() {
        return contestTypeName;
    }

    /**
     * <p>
     * Sets the contest type name.
     * </p>
     *
     * @param contestTypeName
     *            the contest type name
     */
    public void setContestTypeName(String contestTypeName) {
        this.contestTypeName = contestTypeName;
    }

    /**
     * <p>
     * Gets the studio contest type name if it's a studio contest.
     * </p>
     *
     * @return the studio contest type name if it's a studio contest
     */
    public String getSubContestTypeName() {
        return subContestTypeName;
    }

    /**
     * <p>
     * Sets the studio contest type name.
     * </p>
     *
     * @param subContestTypeName
     *            the studio contest type name
     */
    public void setSubContestTypeName(String subContestTypeName) {
        this.subContestTypeName = subContestTypeName;
    }

    /**
     * <p>
     * Gets the name of contest.
     * </p>
     *
     * @return the name of contest
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * <p>
     * Sets the name of contest.
     * <p>
     *
     * @param contestName
     *            the name of contest
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * <p>
     * Gets the name of Cockpit Project to associate contest to.
     * </p>
     *
     * @return the name of Cockpit Project to associate contest to
     */
    public String getCockpitProjectName() {
        return cockpitProjectName;
    }

    /**
     * <p>
     * Sets the name of Cockpit Project to associate contest to.
     * </p>
     *
     * @param cockpitProjectName
     *            the name of Cockpit Project to associate contest to
     */
    public void setCockpitProjectName(String cockpitProjectName) {
        this.cockpitProjectName = cockpitProjectName;
    }

    /**
     * <p>
     * Gets the earliest date contest should launch.
     * </p>
     *
     * @return the earliest date contest should launch
     */
    public Date getRequestedStartDate() {
        return requestedStartDate;
    }

    /**
     * <p>
     * Sets the earliest date contest should launch.
     * </p>
     *
     * @param requestedStartDate
     *            the earliest date contest should launch
     */
    public void setRequestedStartDate(Date requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    /**
     * <p>
     * Gets the flag whether it is okay to find an alternative date and time if
     * capacity is full.
     * </p>
     *
     * @return the flag whether it is okay to find an alternative date and time
     *         if capacity is full
     */
    public boolean getAutoReschedule() {
        return autoReschedule;
    }

    /**
     * <p>
     * Sets the flag whether it is okay to find an alternative date and time if
     * capacity is full.
     * </p>
     *
     * @param autoReschedule
     *            the flag whether it is okay to find an alternative date and
     *            time if capacity is full
     */
    public void setAutoReschedule(boolean autoReschedule) {
        this.autoReschedule = autoReschedule;
    }

    /**
     * <p>
     * Gets the billing project that contest will be associated with.
     * </p>
     *
     * @return the billing project that contest will be associated with
     */
    public long getBillingProjectId() {
        return billingProjectId;
    }

    /**
     * <p>
     * Sets the billing project that contest will be associated with.
     * </p>
     *
     * @param billingProjectId
     *            the billing project that contest will be associated with
     */
    public void setBillingProjectId(long billingProjectId) {
        this.billingProjectId = billingProjectId;
    }

    /**
     * <p>
     * Gets the flag whether the contest should be created with CCA terms.
     * </p>
     *
     * @return the flag whether the contest should be created with CCA terms
     */
    public boolean getCca() {
        return cca;
    }

    /**
     * <p>
     * Gets the flag whether the contest should be created with CCA terms.
     * </p>
     *
     * @param cca
     *            the flag whether the contest should be created with CCA terms
     */
    public void setCca(boolean cca) {
        this.cca = cca;
    }


    /**
     * <p>
     * Gets the flag whether to auto create dev
     * </p>
     *
     * @return the flag whether the contest should be created with CCA terms
     */
    public boolean getAutoCreateDev() {
        return autoCreateDev;
    }

    /**
     * <p>
     * Gets the flag whether to auto create dev
     * </p>
     *
     * @param autoCreateDev
     *            the flag whether the contest should be created with CCA terms
     */
    public void setAutoCreateDev(boolean autoCreateDev) {
        this.autoCreateDev = autoCreateDev;
    }

     /**
     * <p>
     * Gets the flag whether it is dev only
     * </p>
     *
     * @return the flag whether the contest should be created with CCA terms
     */
    public boolean getDevOnly() {
        return devOnly;
    }

    /**
     * <p>
     * Gets the flag whether it is dev only
     * </p>
     *
     * @param autoCreateDev
     *            the flag whether the contest should be created with CCA terms
     */
    public void setDevOnly(boolean devOnly) {
        this.devOnly = devOnly;
    }
}
