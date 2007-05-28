package com.topcoder.management.team.offer;

import com.topcoder.management.team.offer.OfferStatusType;
import com.topcoder.search.builder.filter.*;
import com.topcoder.search.builder.filter.Filter;

/**
 * This is a convenience class for creating filters to search for offers that match specific
 * properties and values. Searches can be done on offer ID, sender ID, receiver ID, position ID,
 * percentage offered, and status type. The constants used as search criterion names are provided by
 * this class for each factory method.
 * <p>
 * This factory also provides convenience methods for combining filters without needing to
 * instantiate specific Filter classes. One can associate filters by AND, OR, or NOT. Any filters,
 * including the ones created in this factory.
 * </p>
 * <p>
 * Thread Safety: This is a static, immutable thread-safe class.
 * </p>
 */
public class OfferFilterFactory {

    /**
     * Represents the field name of the search criterion for an offer ID. Used in the
     * createOfferIdFilter factory method.
     */
    public static final String OFFER_ID = "offerId";

    /**
     * Represents the field name of the search criterion for a sender ID. Used in the
     * createSenderIdFilter factory method.
     */
    public static final String SENDER_ID = "senderId";

    /**
     * Represents the field name of the search criterion for a receiver ID. Used in the
     * createReceiverIdFilter factory method.
     */
    public static final String RECEIVER_ID = "receiverId";

    /**
     * Represents the field name of the search criterion for a position ID. Used in the
     * createPositionIdFilter factory method.
     */
    public static final String POSITION_ID = "positionId";

    /**
     * Represents the field name of the search criterion for payment percentage offered. Used in the
     * createPaymentOfferedFilter factory method.
     */
    public static final String PAYMENT_OFFERED = "paymentOffered";

    /**
     * Represents the field name of the search criterion for offer status types. Used in the
     * createOfferStatusTypeFilter factory method.
     */
    public static final String STATUS_TYPE = "statusType";

    /**
     * Empty private constructor.
     */
    private OfferFilterFactory() {
        /** lock-end */
        // your code here
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Offers with the given offer ID. This will return at most one
     * result.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(OFFER_ID, new Long(offerId))</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param offerId
     *            The offer ID to match Offers with
     * @throws IllegalArgumentException
     *             if offerId is negative
     */
    public static Filter createOfferIdFilter(long offerId) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Offers with the given sender ID.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(SENDER_ID, new Long(senderId))</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param senderId
     *            The sender ID to match Offers with
     * @throws IllegalArgumentException
     *             if senderId is negative
     */
    public static Filter createSenderIdFilter(long senderId) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Offers with the given receiver ID.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(RECEIVER_ID, new Long(receiverId))</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param receiverId
     *            The receiver ID to match Offers with
     * @throws IllegalArgumentException
     *             if receiverId is negative
     */
    public static Filter createReceiverIdFilter(long receiverId) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Offers with the given position ID.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(POSITION_ID, new Long(positionId))</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param positionId
     *            The position ID to match Offers with
     * @throws IllegalArgumentException
     *             if offerId is negative
     */
    public static Filter createPositionIdFilter(long positionId) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Offers with the given payment offered amount.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(PAYMENT_OFFERED, new Integer(amount))</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param amount
     *            The percentage offered to the resource
     * @throws IllegalArgumentException
     *             if amount is negative or above 100
     */
    public static Filter createPaymentOfferedFilter(int amount) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Create a filter that returns all Offers with the given offer status type.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new EqualToFilter(STATUS, new Integer(statusType.getOfferStatusTypeId()))</li>
     * </ul>
     * @return Filter that matches the passed criterion
     * @param statusType
     *            The status type of the offers to retrieve
     * @throws IllegalArgumentException
     *             if status is null
     */
    public static Filter createOfferStatusTypeFilter(OfferStatusType statusType) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Creates an AND filter with the passed filters.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new AndFilter(first, second)</li>
     * </ul>
     * @return An AndFilter of the passed filters
     * @param first
     *            The first filter to put in the AndFilter
     * @param second
     *            The second filter to put in the AndFilter
     * @throws IllegalArgumentException
     *             if either filter is null
     */
    public static Filter createAndFilter(Filter first, Filter second) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Creates an AND filter with the passed filters.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Create ArrayList and put filters in it</li>
     * <li>Return new AndFilter(arrayList)</li>
     * </ul>
     * @return An AndFilter of the passed filters
     * @param filters
     *            The array of filters put in the AndFilter
     * @throws IllegalArgumentException
     *             if filters is null, empty, or contains null elements
     */
    public static Filter createAndFilter(Filter[] filters) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Creates an OR filter with the passed filters.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new OrFilter(first, second)</li>
     * </ul>
     * @return An OrFilter of the passed filters
     * @param first
     *            The first filter to put in the OrFilter
     * @param second
     *            The second filter to put in the OrFilter
     * @throws IllegalArgumentException
     *             if either filter is null
     */
    public static Filter createOrFilter(Filter first, Filter second) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Creates an OR filter with the passed filters.
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Create ArrayList and put filters in it</li>
     * <li>Return new OrFilter(arrayList)</li>
     * </ul>
     * @return An OrFilter of the passed filters
     * @param filters
     *            The array of filters put in the OrFilter
     * @throws IllegalArgumentException
     *             if filters is null, empty, or contains null elements
     */
    public static Filter createOrFilter(Filter[] filters) {
        /** lock-end */
        return null;
    }

    /** lock-begin */

    /**
     * Creates a NOT filter of given filter
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Return new NotFilter(filter)</li>
     * </ul>
     * @return A negation filter of the passed filter
     * @param filter
     *            The filter to negate
     * @throws IllegalArgumentException
     *             if filter is null
     */
    public static Filter createNotFilter(Filter filter) {
        /** lock-end */
        return null;
    }
    /** lock-begin */
}
