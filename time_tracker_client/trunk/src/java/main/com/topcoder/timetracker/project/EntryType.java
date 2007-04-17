
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an enumeration that is used to distinguish between the different types of Entries that
 * may be added to the project.  At the moment, it supports Time, Expense, and Fixed Billing entries.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm74ff]
 */
public class EntryType extends com.topcoder.util.collection.typesafeenum.Enum {

/**
 * <p>
 * This is an Enumeration value representing a Time Entry.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm74e7]
 */
    public static final com.topcoder.timetracker.project.EntryType TIME_ENTRY = new EntryType("time_entry");

/**
 * <p>
 * This is an Enumeration value representing a Fixed Billing Entry.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm74d6]
 */
    public static final com.topcoder.timetracker.project.EntryType FIXED_BILLING_ENTRY = new EntryType("fixed_billing");

/**
 * <p>
 * This is an Enumeration value representing an Expense Entry
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm74c5]
 */
    public static final com.topcoder.timetracker.project.EntryType EXPENSE_ENTRY = new EntryType("expense_entry");

/**
 * <p>
 * This is a name that briefly describes the type of the entry.
 * </p>
 * <p>
 * Initial Value: Null
 * </p>
 * <p>
 * Accessed In: getName, toString
 * </p>
 * <p>
 * Modified In: Not Modified
 * </p>
 * <p>
 * Utilized In: Not Utilized
 * </p>
 * <p>
 * Valid Values: Nulls, or Strings that are not empty
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm69dc]
 */
    private final String name;

/**
 * <p>
 * Private constructor to create an EntryType.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm74b4]
 * @param name The name of the entryType.
 */
    private  EntryType(String name) {        
        this.name = name;
    } 

/**
 * <p>
 * Retrieves the name of the EntryType.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm69cb]
 * @return the name of the EntryType.
 */
    public String getName() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Retrieves the String representation of the EntryType, which is equivalent to it's name.
 * </p>
 * 
 * @poseidon-object-id [Im12955c6cm110be84a1c6mm6d75]
 * @return the name of the EntryType.
 */
    public String toString() {        
        // your code here
        return null;
    } 
 }
