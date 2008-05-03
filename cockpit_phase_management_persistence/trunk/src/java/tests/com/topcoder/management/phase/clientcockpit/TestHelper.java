/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.project.phases.Phase;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestManagementException;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.submission.Submission;

import com.topcoder.util.config.ConfigManager;

import java.io.File;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * <p>
 * This helper class provides utilities used for testing purpose.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class TestHelper {
    /**
     * <p>
     * Private modifier prevents the creation of new instance.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Use the given file to config the configuration manager.
     * </p>
     *
     * @param fileName config file to set up environment.
     * @throws Exception to jUnit.
     */
    static void loadXMLConfig(String fileName) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        File file = new File(fileName);

        cm.add(file.getCanonicalPath());
    }

    /**
     * <p>
     * Clears all namespace from configuration manager.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator<?> i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * <p>
     * Creates a Contest instance with given contest id, start date, and contest status.
     * </p>
     *
     * @param contestId the contest id.
     * @param startDate the contest start date.
     * @param status the contest status.
     * @return Contest instance.
     */
    static Contest createContest(long contestId, Date startDate, ContestStatus status) {
        Contest contest = new Contest();

        contest.setContestId(contestId);
        contest.setStartDate(startDate);
        contest.setEndDate(new Date(System.currentTimeMillis() - 1000));
        contest.setEventId(1L);
        contest.setContestChannel(new ContestChannel());
        contest.setContestType(new ContestType());
        contest.setCreatedUser(2L);
        contest.setForumId(3L);
        contest.setSubmissions(new HashSet<Submission>());
        contest.setName("myContest");
        contest.setStatus(status);

        ContestConfig config = new ContestConfig();
        ContestProperty property = new ContestProperty();
        property.setPropertyId(contestId);
        property.setDescription("property");
        config.setValue("value");
        config.setProperty(property);

        Set<ContestConfig> configs = new HashSet<ContestConfig>();
        configs.add(config);
        contest.setConfig(configs);

        return contest;
    }

    /**
     * <p>
     * Creates a ContestStatus instance with given status id, name and description.
     * </p>
     *
     * @param statusId the contest status id.
     * @param name the contest status name.
     * @param description the contest status description.
     * @return ContestStatus instance.
     */
    static ContestStatus createContestStatus(long statusId, String name, String description) {
        ContestStatus status = new ContestStatus();
        status.setContestStatusId(statusId);
        status.setName(name);
        status.setDescription(description);
        status.setStatuses(new ArrayList<ContestStatus>());

        return status;
    }

    /**
     * <p>
     * Creates a Date with given year, month and dayOfMonth.
     * </p>
     *
     * @param year the value used to set the year.
     * @param month the value used to set the month. Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the day of month.
     * @return Date instance.
     */
    static Date createDate(int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);

        return calendar.getTime();
    }

    /**
     * <p>
     * Adds contest statuses to the given contest manager.
     * </p>
     *
     * @param contestManager the contest manager to add the contest status.
     * @throws ContestManagementException if any error occurs while adding the contest status.
     */
    static void initContestStatuses(ContestManager contestManager)
        throws ContestManagementException {
        contestManager.addContestStatus(createContestStatus(1, "Draft", "Draft status"));
        contestManager.addContestStatus(createContestStatus(2, "Scheduled", "Scheduled status"));
        contestManager.addContestStatus(createContestStatus(3, "Active", "Active status"));
        contestManager.addContestStatus(createContestStatus(4, "Action Required", "Action Required status"));
        contestManager.addContestStatus(createContestStatus(5, "Insufficient Submissions - ReRun Possible",
                "Insufficient Submissions - ReRun Possible Required status"));
        contestManager.addContestStatus(createContestStatus(6, "Completed", "Completed status"));
        contestManager.addContestStatus(createContestStatus(7, "In Danger", "In Danger status"));
        contestManager.addContestStatus(createContestStatus(8, "Abandoned", "Abandoned status"));
        contestManager.addContestStatus(createContestStatus(9, "Extended", "Extended status"));
        contestManager.addContestStatus(createContestStatus(10, "Insufficient Submissions",
                "Insufficient Submissions status"));
        contestManager.addContestStatus(createContestStatus(11, "Repost", "Repost status"));
        contestManager.addContestStatus(createContestStatus(12, "Cancelled", "Cancelled status"));
        contestManager.addContestStatus(createContestStatus(13, "No Winner Chosen", "No Winner Chosen status"));
    }

    /**
     * <p>
     * Finds a phase in the given array by the phase type name.
     * </p>
     *
     * @param phases the array of phase.
     * @param typeName the phase type name.
     * @return Phase instance.
     */
    static Phase findPhaseByTypeName(Phase[] phases, String typeName) {
        for (Phase phase : phases) {
            if (phase.getPhaseType().getName().equals(typeName)) {
                return phase;
            }
        }

        return null;
    }
}
