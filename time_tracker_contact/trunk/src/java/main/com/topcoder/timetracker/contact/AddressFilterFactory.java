/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import java.sql.Timestamp;
import java.util.Date;


/**
 * <p>
 * This class is used to create predefined filters or AND/OR/NOT filters which are used to search addresses.
 * </p>
 *
 * <p>
 * This class can't be created. The application can create filters by its static methods. The column names used in
 * this factory are aliases. The <code>SearchBundle</code> will convert the aliases to the actual names defined in
 * configuration files.
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 * This class is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class AddressFilterFactory {
    /**
     * <p>
     * Represents the alias of <em>creation_date</em> column in the <em>address</em> table.
     * It is set to &quot;creation_date&quot; initially and is immutable.
     * It will be referenced by <code>createCreatedInFilter()</code> method.
     * </p>
     */
    public static final String CREATION_DATE = "creation_date";

    /**
     * <p>
     * Represents the alias of <em>modification_user</em> column in the <em>address</em> table.
     * It is set to &quot;modification_user&quot; initially and is immutable.
     * It will be referenced by <code>createModifiedByFilter()</code> method.
     * </p>
     */
    public static final String MODIFICATION_USER = "modification_user";

    /**
     * <p>Represents the alias of <em>modification_date</em> column in the <em>address</em> table.
     * It is set to &quot;modification_date&quot; initially and is immutable.
     * It will be referenced by <code>createModifiedInFilter()</code> method.
     * </p>
     */
    public static final String MODIFICATION_DATE = "modification_date";

    /**
     * <p>
     * Represents the alias of <em>creation_user</em> column in the <em>address</em> table.
     * It is set to &quot;creation_user&quot; initially and is immutable.
     * It will be referenced by <code>createCreatedByFilter()</code> method.
     * </p>
     */
    public static final String CREATION_USER = "creation_user";

    /**
     * <p>
     * Represents the alias of <em>city</em> column in the <em>address</em> table.
     * It is set to &quot;city&quot; initially and is immutable.
     * It will be referenced by <code>createCityFilter()</code> method.
     * </p>
     */
    public static final String CITY = "city";

    /**
     * <p>
     * Represents the alias of <em>state_name_id</em> column in the <em>address</em> table.
     * It is set to &quot;state_name_id&quot; initially and is immutable.
     * It will be referenced by <code>createStateIDFilter()</code> method.
     * </p>
     */
    public static final String STATE_NAME_ID = "state_name_id";

    /**
     * <p>
     * Represents the alias of <em>zip_code</em> column in the <em>address</em> table.
     * It is set to &quot;zip_code&quot; initially and is immutable.
     * It will be referenced by <code>createZipCodeFilter()</code> method.
     * </p>
     */
    public static final String ZIP_CODE = "zip_code";

    /**
     * <p>
     * Represents the alias of <em>country_name_id</em> column in the <em>address</em> table.
     * It is set to &quot;country_name_id&quot; initially and is immutable.
     * It will be referenced by <code>createCountryIDFilter()</code> method.
     * </p>
     */
    public static final String COUNTRY_NAME_ID = "country_name_id";

    /**
     * <p>
     * Represents the alias of <em>address_type_id</em> column in the <em>address_relation</em> table.
     * It is set to &quot;address_type_id&quot; initially and is immutable.
     * It will be referenced by <code>createTypeFilter()</code> method.
     * </p>
     */
    public static final String ADDRESS_TYPE_ID = "address_type_id";

    /**
     * <p>
     * Represents the alias of <em>entity_id</em> column in the <em>address_relation</em> table.
     * It is set to &quot;entity_id&quot; initially and is immutable.
     * It will be referenced by <code>createEntityIDFilter()</code> method.
     * </p>
     */
    public static final String ENTITY_ID = "entity_id";

    /**
     * <p>
     * Represents the alias of <em>name</em> column in the <em>country_name</em> table.
     * It is set to &quot;country_name&quot; initially and is immutable.
     * It will be referenced by <code>createCountryNameFilter()</code> method.
     * </p>
     */
    public static final String COUNTRY_NAME = "country_name";

    /**
     * <p>
     * Represents the alias of <em>name</em> column in the <em>state_name table</em>.
     * It is set to &quot;state_name&quot; initially and is immutable.
     * It will be referenced by <code>createStateNameFilter()</code> method.
     * </p>
     */
    public static final String STATE_NAME = "state_name";

    /**
     * <p>Empty private constructor to ensure this class can't be instance.</p>
     */
    private AddressFilterFactory() {
        // does nothing
    }

    /**
     * <p>
     * Create filter which will return all addresses created within a given inclusive date range (may be open-ended).
     * </p>
     *
     * <p>
     *  <strong>Notes:</strong>
     *   <ol>
     *    <li>If to is null, <code>GreaterThanOrEqualToFilter</code> with from as comparator will be returned</li>
     *    <li>Else if from is null, <code>LessThanOrEqualToFilter</code> with to as comparator will be returned</li>
     *    <li>Else <code>BetweenFilter</code> will be returned</li>
     *   </ol>
     * </p>
     *
     * <p>
     * If the from and to are both not null, then the from date must not exceed the end date with precision of second.
     * </p>
     *
     * @param from possible null start date of the range
     * @param to possible null end date of the range
     * @return non null filter
     * @throws IllegalArgumentException if both to and from are null;
     *         Or they are both not null, but the from exceeds to.
     */
    public static Filter createCreatedInFilter(Date from, Date to) {
        Helper.validateDatesRange(from, to, "filtering creation_date");
        return createDatesFilter(from, to, true);
    }

    /**
     * <p>
     * Called by <code>createCreatedInFilter()</code> and <code>createModifiedInFilter()</code>
     * to create filter based on the given date range.
     * </p>
     *
     * @param from possible null start date of the range
     * @param to possible null end date of the range
     * @param forCreation Indicates whether for creation or for modification
     * @return non null filter
     */
    private static Filter createDatesFilter(Date from, Date to, boolean forCreation) {
        String alias = forCreation ? CREATION_DATE : MODIFICATION_DATE;
        if (from == null) {
            return new LessThanOrEqualToFilter(alias, new Timestamp(to.getTime()));
        } else if (to == null) {
            return new GreaterThanOrEqualToFilter(alias, new Timestamp(from.getTime()));
        } else {
            return new BetweenFilter(alias, new Timestamp(to.getTime()), new Timestamp(from.getTime()));
        }
    }
    /**
     * <p>
     * Create filter which will return all addresses modified within a given inclusive date range (may be open-ended).
     * </p>
     *
     * <p>
     *  <strong>Notes:</strong>
     *   <ol>
     *    <li>If to is null, <code>GreaterThanOrEqualToFilter</code> with from as comparator will be returned</li>
     *    <li>Else if from is null, <code>LessThanOrEqualToFilter</code> with to as comparator will be returned</li>
     *    <li>Else <code>BetweenFilter</code> will be returned</li>
     *   </ol>
     * </p>
     *
     * <p>
     * If the from and to are both not null, then the from date must not exceed the end date with precision of second.
     * </p>
     *
     * @param from possible null start date of the range
     * @param to possible null end date of the range
     * @return non null filter
     * @throws IllegalArgumentException if both to and from are null;
     *         Or they are both not null, but the from exceeds to.
     */
    public static Filter createModifiedInFilter(Date from, Date to) {
        Helper.validateDatesRange(from, to, "filtering modification_date");
        return createDatesFilter(from, to, false);
    }

    /**
     * <p>
     * Create filter which will return all addresses created by a given user name.
     * </p>
     *
     * @param userName non null, non empty(trim'd) name of the user
     * @return non null filter
     * @throws IllegalArgumentException if the name is null/empty(trim'd)
     */
    public static Filter createCreatedByFilter(String userName) {
        Helper.validateStringWithIAE(userName, "CreationUser name to compare");
        return new EqualToFilter(CREATION_USER, userName);
    }

    /**
     * <p>
     * Create filter which will return all addresses modified by given user name.
     * </p>
     *
     * @param userName non null, non empty(trim'd) name of the user
     * @return non null filter
     * @throws IllegalArgumentException if the name is null/empty(trim'd)
     */
    public static Filter createModifiedByFilter(String userName) {
        Helper.validateStringWithIAE(userName, "ModificationUser name to compare");
        return new EqualToFilter(MODIFICATION_USER, userName);
    }

    /**
     * <p>
     * Create filter which will return all addresses with a given city.
     * </p>
     *
     * @param city non null, non empty(trim'd) city of the user
     * @return non null filter
     * @throws IllegalArgumentException if the city is null/empty(trim'd)
     */
    public static Filter createCityFilter(String city) {
        Helper.validateStringWithIAE(city, "City name to compare");
        return new EqualToFilter(CITY, city);
    }

    /**
     * <p>
     * Create filter which will return all addressed with a given ID of state.
     * </p>
     *
     * @param stateId positive id of state
     * @return non null filter
     * @throws IllegalArgumentException if the state id is non-positive
     */
    public static Filter createStateIDFilter(long stateId) {
        Helper.validatePositiveWithIAE(stateId, "State id to compare");
        return new EqualToFilter(STATE_NAME_ID, new Long(stateId));
    }

    /**
     * <p>
     * Create filter which will return all addresses with a given zip code.
     * </p>
     *
     * @param zipCode non null, non empty(trim'd) zip code
     * @return non null filter
     * @exception IllegalArgumentException if the zipCode is null/empty(trim'd)
     */
    public static Filter createZipCodeFilter(String zipCode) {
        Helper.validateStringWithIAE(zipCode, "Zip code to compare");
        return new LikeFilter(ZIP_CODE, LikeFilter.CONTAIN_TAGS + zipCode);
    }

    /**
     * <p>
     * Create filter which will return all addresses with a given ID of country.
     * </p>
     *
     * @param countryId positive id of the country
     * @return non null filter
     * @throws IllegalArgumentException if the country id is non-positive
     */
    public static Filter createCountryIDFilter(long countryId) {
        Helper.validatePositiveWithIAE(countryId, "Country id to compare");
        return new EqualToFilter(COUNTRY_NAME_ID, new Long(countryId));
    }

    /**
     * <p>
     * Create filter which will return all addresses with a given type.
     * </p>
     *
     * @param type the non null address type
     * @return non null filter
     * @throws IllegalArgumentException if the address type is null
     */
    public static Filter createTypeFilter(AddressType type) {
        Helper.validateNotNullWithIAE(type, "Address type to compare");
        return new EqualToFilter(ADDRESS_TYPE_ID, new Long(type.getId()));
    }

    /**
     * <p>
     * Create filter which will return all addresses with a given entity id and address type.
     * </p>
     *
     * @param entityId the positive entity id
     * @param type non null type
     * @return non null filter
     * @throws IllegalArgumentException if the id is non-positive or type is null
     */
    public static Filter createEntityIDFilter(long entityId, AddressType type) {
        Helper.validatePositiveWithIAE(entityId, "Entity id to compare");
        return new AndFilter(createTypeFilter(type), new EqualToFilter(ENTITY_ID, new Long(entityId)));
    }

    /**
     * <p>
     * Create filter which will return all addresses with a given name of country.
     * </p>
     *
     * @param countryName non null. non empty name of country
     * @return non null filter
     * @throws IllegalArgumentException if the countryName is null or empty(trim'd)
     */
    public static Filter createCountryNameFilter(String countryName) {
        Helper.validateStringWithIAE(countryName, "Country name to compare");
        return new EqualToFilter(COUNTRY_NAME, countryName);
    }

    /**
     * <p>Create filter which will return all addressed with a given name of state.</p>
     *
     * @param stateName non null, non empty(trim'd) name of state
     * @return non null filter
     * @throws IllegalArgumentException if the stateName is null or empty(trim'd)
     */
    public static Filter createStateNameFilter(String stateName) {
        Helper.validateStringWithIAE(stateName, "State name to compare");
        return new EqualToFilter(STATE_NAME, stateName);
    }

    /**
     * <p>Create AND filter of given filters.</p>
     *
     * @param first non null first original filter
     * @param second non null second original filter
     * @return non null and filter of given filters
     * @throws IllegalArgumentException if any filter is null
     */
    public static Filter andFilter(Filter first, Filter second) {
        Helper.validateNotNullWithIAE(first, "First Filter to create AndFilter");
        Helper.validateNotNullWithIAE(second, "Second Filter to create AndFilter");
        return new AndFilter(first, second);
    }

    /**
     * <p>Create OR filter of given filters.</p>
     *
     * @param first non null first original filter
     * @param second non null second original filter
     * @return non null or filter of given filters
     * @throws IllegalArgumentException if any filter is null
     */
    public static Filter orFilter(Filter first, Filter second) {
        Helper.validateNotNullWithIAE(first, "First Filter to create OrFilter");
        Helper.validateNotNullWithIAE(second, "Second Filter to create OrFilter");
        return new OrFilter(first, second);
    }

    /**
     * <p>Create NOT filter of given filter.</p>
     *
     * @param filter non null original filter
     * @return non null filter which is the not filter of the original one
     * @throws IllegalArgumentException if the filter is null
     */
    public static Filter notFilter(Filter filter) {
        Helper.validateNotNullWithIAE(filter, "Filter to create NotFilter");
        return new NotFilter(filter);
    }
}
