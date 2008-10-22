/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.failuretests;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestConfig;
import com.topcoder.service.studio.contest.ContestProperty;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.ContestConfig.Identifier;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.util.config.ConfigManager;

/**
 * Helper class for failure test.
 * 
 * @author hfx
 * @version 1.0
 */
final class TestHelper {
    /**
     * The name of directory stores test files.
     */
    public static final String TEST_FILES_DIRECTORY = "test_files/failuretests";

    /**
     * <p>
     * Empty private constructor.
     * </p>
     */
    private TestHelper() {
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
        Identifier id = new Identifier();
        id.setContest(contest);
        config.setId(id);
        config.getId().setProperty(property);

        Set<ContestConfig> configs = new HashSet<ContestConfig>();
        configs.add(config);
        contest.setConfig(configs);

        return contest;
    }
    /**
     * Get a File instance with the given file name.
     * 
     * @param fileName
     *            name of the file
     * @return File instance of the given file name
     */
    private static File getFile(String fileName) {
        return new File(TEST_FILES_DIRECTORY + File.separator + fileName);
    }

    /**
     * Load a configuration file.
     * 
     * @param fileName
     *            name of the configuration file
     * @throws IOException
     *             If error occurs
     */
    static void loadConfig(String fileName) throws IOException {
        ConfigManager.getInstance().add(getFile(fileName).getCanonicalPath());
    }

    /**
     * <p>
     * Clears all namespace from configuration manager.
     * </p>
     * 
     * @throws Exception
     *             to jUnit.
     */
    static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator<?> i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }
}
