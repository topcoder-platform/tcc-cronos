/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import java.util.HashMap;
import java.util.Map;

import com.cronos.onlinereview.deliverables.AppealResponsesDeliverableChecker;
import com.cronos.onlinereview.deliverables.FinalReviewDeliverableChecker;
import com.cronos.onlinereview.deliverables.IndividualReviewDeliverableChecker;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.deliverable.DeliverableChecker;
import com.topcoder.management.deliverable.PersistenceDeliverableManager;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.search.builder.SearchBundleManager;

/**
 * <p>
 * Mock PersistenceDeliverableManager.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockPersistenceDeliverableManager extends PersistenceDeliverableManager {
    /**
     * Name space.
     */
    private static final String DEFAULT_NAMESPACE = DBConnectionFactoryImpl.class.getName();

    /**
     * Default constructor.
     *
     * @param persistence The persistence for Deliverables
     * @param searchBundleManager The manager containing the various SearchBundles needed
     *
     * @throws IllegalArgumentException If any argument is null
     * @throws IllegalArgumentException If any search bundle is not available under the required names
     */
    public MockPersistenceDeliverableManager(DeliverablePersistence persistence,
        SearchBundleManager searchBundleManager) {
        super(persistence, createDeliverableCheckers(), searchBundleManager);
    }

    /**
     * Create deliverable checkers programmatically.
     *
     * @return the created deliverable checkers.
     */
    private static Map<String, DeliverableChecker> createDeliverableCheckers() {
        Map<String, DeliverableChecker> deliverableCheckers = new HashMap<String, DeliverableChecker>();

        try {
            DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DEFAULT_NAMESPACE);
            deliverableCheckers.put("Screening Scorecard", new IndividualReviewDeliverableChecker(connectionFactory));
            deliverableCheckers.put("Review Evaluation Scorecard", new IndividualReviewDeliverableChecker(
                connectionFactory));
            deliverableCheckers.put("Appeals Response", new AppealResponsesDeliverableChecker(connectionFactory));
            deliverableCheckers.put("Final Review", new FinalReviewDeliverableChecker(connectionFactory));
            deliverableCheckers
                .put("New Review Scorecard", new IndividualReviewDeliverableChecker(connectionFactory));
        } catch (UnknownConnectionException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        } catch (ConfigurationException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        return deliverableCheckers;
    }
}
