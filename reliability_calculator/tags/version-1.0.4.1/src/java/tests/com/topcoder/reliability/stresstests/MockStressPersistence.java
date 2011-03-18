/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.impl.ReliabilityDataPersistence;
import com.topcoder.reliability.impl.ReliabilityDataPersistenceException;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;

/**
 * <p>
 * This is mock implementation of ReliabilityDataPersistence.
 * </p>
 * <p>
 * Provides great load(return big lists) in getIdsOfUsersWithReliability() and getUserParticipationData() methods.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class MockStressPersistence implements ReliabilityDataPersistence {

    /**
     * <p>
     * Represents {@link #getIdsOfUsersWithReliability(long, Date)} method run count value.
     * </p>
     */
    public static int getIdsOfUsersWithReliabilityMethodRun = 0;

    /**
     * <p>
     * Represents {@link #getUserParticipationData(long, long, Date)} method run count value.
     * </p>
     */
    public static int getUserParticipationDataMethodRun = 0;

    /**
     * <p>
     * Represents {@link #updateCurrentUserReliability(long, long, double)} method run count value.
     * </p>
     */
    public static int updateCurrentUserReliabilityMethodRun = 0;

    /**
     * <p>
     * Represents user count of getIdsOfUsersWithReliability() return list per category.
     * </p>
     */
    public static int USER_COUNT = 15;

    /**
     * <p>
     * Represents used project count of getUserParticipationData() return list.
     * </p>
     */
    public static int USER_PROJECT_COUNT = 10;

    /**
     * <p>
     * Do nothing.
     * </p>
     */
    public void close() {
    }

    /**
     * <p>
     * Returns list of users ids starting from 1..{@value #USER_COUNT}.
     * </p>
     * @param projectCategoryId the project category id
     * @param startDate the start date
     * @return List of users ids starting from 1..{@value #USER_COUNT}
     * @throws ReliabilityDataPersistenceException never
     */
    public List < Long > getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)
        throws ReliabilityDataPersistenceException {
        getIdsOfUsersWithReliabilityMethodRun++;
        List < Long > result = new ArrayList < Long >();
        for (long i = 0; i < USER_COUNT; i++) {
            result.add(i + 1);
        }
        return result;
    }

    /**
     * <p>
     * Creates list of UserProjectParticipationData with element count equal USER_PROJECT_COUNT.
     * </p>
     * @param userId the user id
     * @param projectCategoryId the project category id
     * @param startDate the start date
     * @return List of UserProjectParticipationData with element count equal USER_PROJECT_COUNT
     * @throws ReliabilityDataPersistenceException never
     */
    public List < UserProjectParticipationData > getUserParticipationData(long userId, long projectCategoryId,
            Date startDate) throws ReliabilityDataPersistenceException {
        getUserParticipationDataMethodRun++;
        List < UserProjectParticipationData > result = new ArrayList < UserProjectParticipationData >();
        for (int i = 0; i < USER_PROJECT_COUNT; i++) {
            UserProjectParticipationData participationData = new UserProjectParticipationData();
            // all projects should be resolved in ReliabilityCalculatorImplTest
            participationData.setPassedReview(true);
            participationData.setAppealsResponsePhaseEnd(new Date());
            result.add(participationData);
        }
        return result;
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     * @throws ReliabilityDataPersistenceException never
     */
    public void open() throws ReliabilityDataPersistenceException {
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     * @param projects the projects
     * @throws ReliabilityDataPersistenceException never
     */
    public void saveUserReliabilityData(List < UserProjectReliabilityData > projects)
        throws ReliabilityDataPersistenceException {
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     * @param userId the user id
     * @param projectCategoryId the project category id
     * @param reliability the reliability
     * @throws ReliabilityDataPersistenceException never
     */
    public void updateCurrentUserReliability(long userId, long projectCategoryId, double reliability)
        throws ReliabilityDataPersistenceException {
        updateCurrentUserReliabilityMethodRun++;
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     * @param config the configuration
     */
    public void configure(ConfigurationObject config) {
    }

    /**
     * Do nothing.
     * @param includedProjectStatuses the included project statuses
     */
    public void setIncludedProjectStatuses(List < Long > includedProjectStatuses) {
    }

    /**
     * Do nothing.
     * @param projectCategoryId the project category id
     * @throws ReliabilityDataPersistenceException never
     */
    public void deleteReliabilityData(long projectCategoryId) throws ReliabilityDataPersistenceException {
        
    }

    /**
     * Do nothing.
     * @throws ReliabilityDataPersistenceException never
     */
    public void clearUserReliability(long projectCategoryId) throws ReliabilityDataPersistenceException {
    }
}
