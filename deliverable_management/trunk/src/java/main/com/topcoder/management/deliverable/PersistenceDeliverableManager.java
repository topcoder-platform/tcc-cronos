/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The PersistenceDeliverableManager class implements the DeliverableManager interface. It ties
 * together a persistence mechanism and two Search Builder instances (for searching for various
 * types of data). The methods in this class use a SearchBundle to execute a search and then use the
 * persistence to load an object for each of the ids found from the search.
 * </p>
 * <p>
 * This class is immutable and hence thread-safe.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0
 */
public class PersistenceDeliverableManager implements DeliverableManager {

    /**
     * The name under which the deliverable search bundle should appear in the SearchBundleManager,
     * if the SearchBundleManager constructor is used.
     */
    public static final String DELIVERABLE_SEARCH_BUNDLE_NAME = "Deliverable Search Bundle";

    /**
     * The name under which the deliverable with submissions search bundle should appear in the
     * SearchBundleManager, if the SearchBundleManager constructor is used.
     */
    public static final String DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME
        = "Deliverable With Submission Search Bundle";

    /**
     * The persistence store for Deliverables. This field is set in the constructor, is immutable,
     * and can never be null.
     */
    private final DeliverablePersistence persistence;

    /**
     * The search bundle that is used when searching for deliverables. This field is set in the
     * constructor, is immutable, and can never be null.
     */
    private final SearchBundle deliverableSearchBundle;

    /**
     * The checkers that are used to determine whether a deliverable is complete or not. This field
     * is set in the constructor and can never be null.
     */
    private final Map deliverableCheckers = new HashMap();

    /**
     * The search bundle that is used when searching for deliverables combined with submissions.
     * This field is set in the constructor, is immutable, and can never be null.
     */
    private final SearchBundle deliverableWithSubmissionsSearchBundle;

    /**
     * Creates a new PersistenceDeliverableManager, initializing all fields to the given values. The
     * deliverableCheckers map is cloned.
     *
     * @param persistence The persistence for Deliverables
     * @param deliverableCheckers The checkers to run on the deliverables to see if each is
     *            complete. Map from String - DeliverableChecker.
     * @param deliverableSearchBundle The search bundle for deliverables
     * @param submissionSearchBundle The search bundle for deliverables with submissions
     * @throws IllegalArgumentException If any argument is null
     * @throws IllegalArgumentException If deliverableCheckers is not a Map of non-null String -
     *             DeliverableChecker
     */
    public PersistenceDeliverableManager(DeliverablePersistence persistence, Map deliverableCheckers,
            SearchBundle deliverableSearchBundle, SearchBundle submissionSearchBundle) {
        DeliverableHelper.checkObjectNotNull(persistence, "persistence");
        DeliverableHelper.checkObjectNotNull(deliverableCheckers, "deliverableCheckers");
        DeliverableHelper.checkObjectNotNullFullDesp(deliverableSearchBundle,
                "deliverableSearchBundle is null, or searchBundleManager doesn't contain"
                    + " SearchBundle of required names DELIVERABLE_SEARCH_BUNDLE_NAME");
        DeliverableHelper.checkObjectNotNullFullDesp(submissionSearchBundle,
                "submissionSearchBundle is null, or searchBundleManager doesn't contain"
                    +  " SearchBundle of required names DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME");

        // Check if deliverableCheckers is a Map of non-null String - DeliverableChecker
        DeliverableHelper.checkDeliverableCheckers(deliverableCheckers);

        // Set the searchable fields of SearchBundle.
        DeliverableHelper.setSearchableFields(deliverableSearchBundle,
                DeliverableHelper.DELIVERABLE_SEARCH_BUNDLE);
        DeliverableHelper.setSearchableFields(submissionSearchBundle,
                DeliverableHelper.DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE);

        this.persistence = persistence;
        this.deliverableCheckers.putAll(deliverableCheckers);
        this.deliverableSearchBundle = deliverableSearchBundle;
        this.deliverableWithSubmissionsSearchBundle = submissionSearchBundle;
    }

    /**
     * Creates a new PersistenceDeliverableManager.
     *
     * @param persistence The persistence for Deliverables
     * @param deliverableCheckers The checkers to run on the deliverables to see if each is
     *            complete. Map from String - DeliverableChecker.
     * @param searchBundleManager The manager containing the various SearchBundles needed
     * @throws IllegalArgumentException If any argument is null
     * @throws IllegalArgumentException If any search bundle is not available under the required
     *             names
     * @throws IllegalArgumentException If deliverableCheckers is not a Map of non-null String -
     *             DeliverableChecker
     */
    public PersistenceDeliverableManager(DeliverablePersistence persistence, Map deliverableCheckers,
            SearchBundleManager searchBundleManager) {
        // Check if searchBundleManager is null, if not, get the SearchBundles, then delegate to
        // the first constructor.
        this(persistence,
                deliverableCheckers,
                (DeliverableHelper.checkObjectNotNull(searchBundleManager, "searchBundleManager"))
                    ? searchBundleManager.getSearchBundle(DELIVERABLE_SEARCH_BUNDLE_NAME)
                    : null,
                searchBundleManager.getSearchBundle(DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE_NAME));
    }

    /**
     * <p>
     * Searches the deliverables in the persistence store using the given filter. The filter can be
     * formed using the field names and utility methods in DeliverableFilterBuilder. The return will
     * always be a non-null (possibly 0 item) array. This method is designed to be used with
     * submission id filters, and returns only deliverables that are "per submission".
     * </p>
     *
     * @return The Deliverables meeting the filter and complete conditions
     * @param filter The filter to use
     * @param complete True to include only those deliverables that have been completed, false to
     *            include only those deliverables that are not complete, null to include both
     *            complete and incomplete deliverables
     * @throws IllegalArgumentException If filter is null
     * @throws DeliverablePersistenceException If there is an error reading the persistence
     * @throws SearchBuilderException If there is an error executing the filter
     * @throws SearchBuilderConfigurationException If the manager is not properly configured with a
     *             correct SearchBundle for searching
     * @throws DeliverableCheckingException If there is an error when determining whether a
     *             Deliverable has been completed or not
     */
    public Deliverable[] searchDeliverablesWithSubmissionFilter(Filter filter, Boolean complete)
        throws DeliverablePersistenceException, SearchBuilderException, DeliverableCheckingException {
        // Delegate to searchDeliverablesHelper. 2 indicates that there are two columns in the
        // CustomResultSet.
        return searchDeliverablesHelper(filter, complete, 2);
    }

    /**
     * <p>
     * searchDeliverables: Searches the deliverables in the persistence store using the given
     * filter. The filter can be formed using the field names and utility methods in
     * DeliverableFilterBuilder. The return will always be a non-null (possibly 0 item) array. This
     * method should not be used with submission id filters.
     * </p>
     *
     * @return The Deliverables meeting the Filter and complete conditions
     * @param filter The filter to use
     * @param complete True to include only those deliverables that have been completed, false to
     *            include only those deliverables that are not complete, null to include both
     *            complete and incomplete deliverables
     * @throws IllegalArgumentException If filter is null
     * @throws DeliverablePersistenceException If there is an error reading the persistence store
     * @throws SearchBuilderException If there is an error executing the filter
     * @throws SearchBuilderConfigurationException If the manager is not properly configured with a
     *             correct SearchBundle for searching
     * @throws DeliverableCheckingException If there is an error when determining whether a
     *             Deliverable has been completed or not
     */
    public Deliverable[] searchDeliverables(Filter filter, Boolean complete)
        throws DeliverablePersistenceException, SearchBuilderException, DeliverableCheckingException {
        // Delegate to searchDeliverablesHelper. 1 indicates that there is one column in the
        // CustomResultSet.
        return searchDeliverablesHelper(filter, complete, 1);
    }

    /**
     * Help method for methods: searchDeliverables and searchDeliverablesWithSubmissionFilter.
     * Searches the deliverables in the persistence store using the given filter. The filter can be
     * formed using the field names and utility methods in DeliverableFilterBuilder. The return will
     * always be a non-null (possibly 0 item) array. The String operation is used for distinguish
     * call from two methods.
     *
     * @param filter The filter to use
     * @param complete True to include only those deliverables that have been completed, false to
     *            include only those deliverables that are not complete, null to include both
     *            complete and incomplete deliverables
     * @param operation the identifier for the caller, 1 is searchDeliverables, 2 is
     *            searchDeliverablesWithSubmissionFilter
     * @return The Deliverables meeting the Filter and complete conditions
     * @throws DeliverablePersistenceException If there is an error reading the persistence store
     * @throws SearchBuilderException If there is an error executing the filter
     * @throws SearchBuilderConfigurationException If the manager is not properly configured with a
     *             correct SearchBundle for searching
     * @throws DeliverableCheckingException If there is an error when determining whether a
     *             Deliverable has been completed or not
     */
    private Deliverable[] searchDeliverablesHelper(Filter filter, Boolean complete, int operation)
        throws SearchBuilderException, DeliverablePersistenceException, DeliverableCheckingException {
        DeliverableHelper.checkObjectNotNull(filter, "filter");

        // Get object with given filter from corresponding search bundle.
        Object obj;
        if (operation == 1) {
            obj = deliverableSearchBundle.search(filter);
        } else {
            obj = deliverableWithSubmissionsSearchBundle.search(filter);
        }

        // long arrays for temporary storage.
        long[][] array = new long[2][];

        // Check if the return object is a CustomResultSet, and it's record count is correct too.
        // And retrieve long[][] from correct CustomResultSet.
        array = DeliverableHelper.checkAndGetCustomResultSetValid(obj, operation);

        // Create a List for temporary storage.
        List list = new ArrayList();

        // Get Deliverable[] from persistence layer.
        Deliverable[] deliverableArray;
        if (operation == 1) {
            deliverableArray = persistence.loadDeliverables(array[0]);
        } else {
            deliverableArray = persistence.loadDeliverables(array[0], array[1]);
        }

        // For each deliverable in deliverableArray
        for (int i = 0, n = deliverableArray.length; i < n; i++) {
            Deliverable deliverable = deliverableArray[i];

            // Get the name of deliverable. This can not be null, if it is, throw a
            // DeliverablePersistenceException.
            String name = deliverable.getName();
            DeliverableHelper.checkObjectNotNullFullDesp(name, "name in the deliverable can't be null.");

            // Look up the deliverable checker with the name.
            DeliverableChecker deliverableChecker = (DeliverableChecker) deliverableCheckers.get(name);

            // If DeliverableChecker is found for the name, run the retrieved DeliverableChecker
            // on the deliverable. Otherwise do not check the deliverable.
            if (deliverableChecker != null) {
                deliverableChecker.check(deliverable);
            }

            // If the isCompleted property of the deliverable matches the complete argument
            // (when complete is not null), then add the deliverable to the return array.
            // When complete is null, the deliverable is always added to the return array.
            if ((complete == null) || (complete.booleanValue() == deliverable.isComplete())) {
                list.add(deliverable);
            }
        }

        // Convert List to Deliverable[]
        return (Deliverable[]) list.toArray(new Deliverable[0]);
    }

}
