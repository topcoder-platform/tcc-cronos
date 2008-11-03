/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import java.io.File;
import java.util.Iterator;

import com.topcoder.project.phases.PhaseType;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * A helper class to perform those common operations which are helpful for the test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {

    /**
     * <p>
     * The sample configuration file for this component.
     * </p>
     */
    public static final String CONFIG_FILE = "test_files/failuretests/config.xml";

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Uses the given file to config the configuration manager.
     * </p>
     *
     * @param fileName config file to set up environment
     *
     * @throws Exception when any exception occurs
     */
    public static void loadXMLConfig(String fileName) throws Exception {
        //set up environment
        ConfigManager config = ConfigManager.getInstance();
        File file = new File(fileName);

        config.add(file.getCanonicalPath());
    }

    /**
     * <p>
     * Clears all the namespaces from the configuration manager.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * <p>
     * Remove a property.
     * </p>
     *
     * @param namespace The namespace to remove property from.
     * @param property The property name.
     *
     * @throws Exception to JUnit.
     */
    public static void removeProperty(String namespace, String property) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.createTemporaryProperties(namespace);
        cm.removeProperty(namespace, property);
        cm.commit(namespace, "TCSDEVELOPER");
    }

    /**
     * <p>
     * Set property value(if property does not exist, it will be created).
     * </p>
     *
     * @param namespace The namespace to set property into.
     * @param property The property name.
     * @param value The property value.
     *
     * @throws Exception to JUnit.
     */
    public static void setProperty(String namespace, String property, String value) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.createTemporaryProperties(namespace);
        cm.setProperty(namespace, property, value);
        cm.commit(namespace, "TCSDEVELOPER");
    }

    /**
     * <p>
     * Set property values(if property does not exist, it will be created).
     * </p>
     *
     * @param namespace The namespace to set property into.
     * @param property The property name.
     * @param values The property values.
     *
     * @throws Exception to JUnit.
     */
    public static void setProperty(String namespace, String property, String[] values) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.createTemporaryProperties(namespace);
        cm.setProperty(namespace, property, values);
        cm.commit(namespace, "TCSDEVELOPER");
    }

    /**
     * <p>
     * Enumerate all the CockPit contest status.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    public static interface CockpitContestStatus {

        /**
         * <p>
         * Represents the "Draft" status.
         * </p>
         */
        public static final ContestStatus DRAFT = new ContestStatus();

        /**
         * <p>
         * Represents the "Scheduled" status.
         * </p>
         */
        public static final ContestStatus SCHEDULED = new ContestStatus();

        /**
         * <p>
         * Represents the "Active" status.
         * </p>
         */
        public static final ContestStatus ACTIVE = new ContestStatus();

        /**
         * <p>
         * Represents the "ActionRequired" status.
         * </p>
         */
        public static final ContestStatus ACTION_REQUIRED = new ContestStatus();

        /**
         * <p>
         * Represents the "InDanger" status.
         * </p>
         */
        public static final ContestStatus IN_DANGER = new ContestStatus();

        /**
         * <p>
         * Represents the "InsufficientSubmissionsReRunPossible" status.
         * </p>
         */
        public static final ContestStatus INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE = new ContestStatus();

        /**
         * <p>
         * Represents the "Extended" status.
         * </p>
         */
        public static final ContestStatus EXTENDED = new ContestStatus();

        /**
         * <p>
         * Represents the "Repost" status.
         * </p>
         */
        public static final ContestStatus REPOST = new ContestStatus();

        /**
         * <p>
         * Represents the "Rerun" status.
         * </p>
         */
        public static final ContestStatus RERUN = new ContestStatus();

        /**
         * <p>
         * Represents the "InsufficientSubmissions" status.
         * </p>
         */
        public static final ContestStatus INSUFFICIENT_SUBMISSIONS = new ContestStatus();

        /**
         * <p>
         * Represents the "NoWinnerChosen" status.
         * </p>
         */
        public static final ContestStatus NO_WINNER_CHOSEN = new ContestStatus();

        /**
         * <p>
         * Represents the "Completed" status.
         * </p>
         */
        public static final ContestStatus COMPLETED = new ContestStatus();

        /**
         * <p>
         * Represents the "Abandoned" status.
         * </p>
         */
        public static final ContestStatus ABANDONED = new ContestStatus();

        /**
         * <p>
         * Represents the "Cancelled" status.
         * </p>
         */
        public static final ContestStatus CANCELLED = new ContestStatus();

        /**
         * <p>
         * Represents the "Terminated" status.
         * </p>
         */
        public static final ContestStatus TERMINATED = new ContestStatus();

        /**
         * <p>
         * Place holder. No need to be implemented.
         * </p>
         */
        void placeHolder();
    }

    /**
     * <p>
     * Enumerate all the CockPit phases.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    public static interface CockpitPhaseType {

        /**
         * <p>
         * Represents the "Draft" phase.
         * </p>
         */
        public static final PhaseType DRAFT = new PhaseType(1, "Draft");

        /**
         * <p>
         * Represents the "Scheduled" phase.
         * </p>
         */
        public static final PhaseType SCHEDULED = new PhaseType(2, "Scheduled");

        /**
         * <p>
         * Represents the "Active" phase.
         * </p>
         */
        public static final PhaseType ACTIVE = new PhaseType(3, "Active");

        /**
         * <p>
         * Represents the "Action Required" phase.
         * </p>
         */
        public static final PhaseType ACTION_REQUIRED = new PhaseType(4, "Action Required");

        /**
         * <p>
         * Represents the "In Danger" phase.
         * </p>
         */
        public static final PhaseType IN_DANGER = new PhaseType(5, "In Danger");

        /**
         * <p>
         * Represents the "Insufficient Submissions - ReRun Possible" phase.
         * </p>
         */
        public static final PhaseType INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE = new PhaseType(6,
            "Insufficient Submissions - ReRun Possible");

        /**
         * <p>
         * Represents the "Extended" phase.
         * </p>
         */
        public static final PhaseType EXTENDED = new PhaseType(7, "Extended");

        /**
         * <p>
         * Represents the "Repost" phase.
         * </p>
         */
        public static final PhaseType REPOST = new PhaseType(8, "Repost");

        /**
         * <p>
         * Represents the "Insufficient Submissions" phase.
         * </p>
         */
        public static final PhaseType INSUFFICIENT_SUBMISSIONS = new PhaseType(9, "Insufficient Submissions");

        /**
         * <p>
         * Represents the "No Winner Chosen" phase.
         * </p>
         */
        public static final PhaseType NO_WINNER_CHOSEN = new PhaseType(10, "No Winner Chosen");

        /**
         * <p>
         * Represents the "Completed" phase.
         * </p>
         */
        public static final PhaseType COMPLETED = new PhaseType(11, "Completed");

        /**
         * <p>
         * Represents the "Abandoned" phase.
         * </p>
         */
        public static final PhaseType ABANDONED = new PhaseType(12, "Abandoned");

        /**
         * <p>
         * Represents the "Cancelled" phase.
         * </p>
         */
        public static final PhaseType CANCELLED = new PhaseType(13, "Cancelled");

        /**
         * <p>
         * Represents the "Terminated" phase.
         * </p>
         */
        public static final PhaseType TERMINATED = new PhaseType(14, "Terminated");

        /**
         * <p>
         * Represents an invalid phase type.
         * </p>
         */
        public static final PhaseType NO_SUCH_PHASE = new PhaseType(15, "NoSuchPhase");

        /**
         * <p>
         * Place holder. No need to be implemented.
         * </p>
         */
        void placeHolder();
    }

}
