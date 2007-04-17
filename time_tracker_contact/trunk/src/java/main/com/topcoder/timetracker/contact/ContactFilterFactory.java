/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import java.util.Date;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.OrFilter;


/**
 * <p>
 * This class is used to create predefined filters or AND/OR/NOT filters which are used to search contacts.
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
public class ContactFilterFactory {
    /**
     * <p>
     * Represents the alias of <em>first_name</em> column in the <em>contact</em> table.
     * It is set to &quot;last_name&quot; initially and is immutable.
     * It will be referenced by <code>createNameKeywordFilter()</code> method.
     * </p>
     */
    public static final String FIRST_NAME = "first_name";

    /**
     * <p>
     * Represents the alias of <em>last_name</em> column in the <em>contact</em> table.
     * It is set to &quot;first_name&quot; initially and is immutable.
     * It will be referenced by <code>createNameKeywordFilter()</code> method.
     * </p>
     */
    public static final String LAST_NAME = "last_name";

    /**
     * <p>
     * Represents the alias of <em>creation_date</em> column in the <em>contact</em> table.
     * It is set to &quot;creation_date&quot; initially and is immutable.
     * It will be referenced by <code>createCreatedInFilter()</code> method.
     * </p>
     */
    public static final String CREATION_DATE = "creation_date";

    /**
     * <p>
     * Represents the alias of <em>modification_user</em> column in the <em>contact</em> table.
     * It is set to &quot;modification_user&quot; initially and is immutable.
     * It will be referenced by <code>createModifiedByFilter()</code> method.
     * </p>
     */
    public static final String MODIFICATION_USER = "modification_user";

    /**
     * <p>Represents the alias of <em>modification_date</em> column in the <em>contact</em> table.
     * It is set to &quot;modification_date&quot; initially and is immutable.
     * It will be referenced by <code>createModifiedInFilter()</code> method.
     * </p>
     */
    public static final String MODIFICATION_DATE = "modification_date";

    /**
     * <p>
     * Represents the alias of <em>creation_user</em> column in the <em>contact</em> table.
     * It is set to &quot;creation_user&quot; initially and is immutable.
     * It will be referenced by <code>createCreatedByFilter()</code> method.
     * </p>
     */
    public static final String CREATION_USER = "creation_user";

    /**
     * <p>
     * Represents the alias of <em>email</em> column in the <em>contact_relation</em> table.
     * It is set to &quot;email&quot; initially and is immutable.
     * It will be referenced by <code>createEmailAddressFilter()</code> method.
     * </p>
     */
    public static final String EMAIL = "email";

    /**
     * <p>
     * Represents the alias of <em>phone</em> column in the <em>contact_relation</em> table.
     * It is set to &quot;phone&quot; initially and is immutable.
     * It will be referenced by <code>createPhoneNumberFilter()</code> method.
     * </p>
     */
    public static final String PHONE = "phone";

    /**
     * <p>
     * Represents the alias of <em>contact_type_id</em> column in the <em>contact_relation</em> table.
     * It is set to &quot;contact_type_id&quot; initially and is immutable.
     * It will be referenced by <code>createTypeFilter()</code> method.
     * </p>
     */
    public static final String CONTACT_TYPE_ID = "contact_type_id";

    /**
     * <p>
     * Represents the alias of <em>entity_id</em> column in the <em>contact_relation</em> table.
     * It is set to &quot;entity_id&quot; initially and is immutable.
     * It will be referenced by <code>createEntityIDFilter()</code> method.
     * </p>
     */
    public static final String ENTITY_ID = "entity_id";

    /**
     * <p>Empty private constructor to ensure this class can't be instance.</p>
     */
    private ContactFilterFactory() {
        // does nothing
    }

    /**
     * <p>
     * Create filter which will return all contacts with first/last name that contains a given string.
     * </p>
     *
     * @param keyword non null, non empty(trim'd) string which is expected in the first/last name of contact
     * @return non null filter
     * @exception IllegalArgumentException if the keyword is null/empty(trim'd)
     */
    public static Filter createNameKeywordFilter(String keyword) {
        Helper.validateStringWithIAE(keyword, "Keyword to compare");
        return new OrFilter(new LikeFilter(FIRST_NAME, LikeFilter.CONTAIN_TAGS + keyword),
            new LikeFilter(LAST_NAME, LikeFilter.CONTAIN_TAGS + keyword));
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
     * @param from possible null start date of the range
     * @param to possible null end date of the range
     * @return non null filter
     * @throws IllegalArgumentException if both to and from are null
     */
    public static Filter createCreatedInFilter(Date from, Date to) {
        return AddressFilterFactory.createCreatedInFilter(from, to);
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
     * @param from possible null start date of the range
     * @param to possible null end date of the range
     * @return non null filter
     * @throws IllegalArgumentException if both to and from are null
     */
    public static Filter createModifiedInFilter(Date from, Date to) {
        return AddressFilterFactory.createModifiedInFilter(from, to);
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
        return AddressFilterFactory.createCreatedByFilter(userName);
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
        return AddressFilterFactory.createModifiedByFilter(userName);
    }

    /**
     * <p>Create filter which will return all contacts with a given email address.</p>
     *
     * @param emailAddress non null, non empty(trim'd) email address of the user
     * @return non null filter
     * @throws IllegalArgumentException if the email address is null/empty(trim'd)
     */
    public static Filter createEmailAddressFilter(String emailAddress) {
        Helper.validateStringWithIAE(emailAddress, "Email address to compare");
        return new EqualToFilter(EMAIL, emailAddress);
    }

    /**
     * <p>Create filter which will return all contacts with a given phone number.</p>
     *
     * @param phoneNumber non null, non empty(trim'd) phone number of the user
     * @return non null filter
     * @throws IllegalArgumentException if the phone number is null/empty(trim'd)
     */
    public static Filter createPhoneNumberFilter(String phoneNumber) {
        Helper.validateStringWithIAE(phoneNumber, "Phone number to compare");
        return new EqualToFilter(PHONE, phoneNumber);
    }

    /**
     * <p>Create filter which will return all contacts with a given type.</p>
     *
     * @param type the non null contact type
     * @return non null filter
     * @throws IllegalArgumentException if the contact type is null
     */
    public static Filter createTypeFilter(ContactType type) {
        Helper.validateNotNullWithIAE(type, "Contact type to compare");
        return new EqualToFilter(CONTACT_TYPE_ID, new Long(type.getId()));
    }

    /**
     * <p>Create filter which will return all contacts with a given entity id and address type.</p>
     *
     * @param entityId the positive id
     * @param type the non null contact type
     * @return non null filter
     * @throws IllegalArgumentException if the id is non-positive or the type is null
     */
    public static Filter createEntityIDFilter(long entityId, ContactType type) {
        Helper.validatePositiveWithIAE(entityId, "Entity id to compare");
        return new AndFilter(createTypeFilter(type), new EqualToFilter(ENTITY_ID, new Long(entityId)));
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
        return AddressFilterFactory.andFilter(first, second);
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
        return AddressFilterFactory.orFilter(first, second);
    }

    /**
     * <p>Create NOT filter of given filter.</p>
     *
     * @param filter non null original filter
     * @return non null filter which is the not filter of the original one
     * @throws IllegalArgumentException if the filter is null
     */
    public static Filter notFilter(Filter filter) {
        return AddressFilterFactory.notFilter(filter);
    }
}
