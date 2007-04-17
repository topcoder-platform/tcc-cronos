
package com.topcoder.timetracker.audit;

/**
 * Extending the Type Safe Enumeration component's Enum class, this enumeration contains information about all the application areas available for audits. Each enumeration is identifiable by its name (e.g. "Expense" / "Fixed Billing"...) or its cardinal ID. ApplicationArea has a private constructor, instances are available only as public static final members of the class - in addition, each is immutable after construction.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c59]
 */
public class ApplicationArea extends com.topcoder.util.collection.typesafeenum.Enum {

/**
 * Enumeration value for the "Expense" application area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7caf]
 */
    public static final com.topcoder.timetracker.audit.ApplicationArea TT_EXPENSE = new ApplicationArea("Expense");

/**
 * Enumeration value for the "Fixed Billing" application area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7ca9]
 */
    public static final com.topcoder.timetracker.audit.ApplicationArea TT_FIXED_BILLING = new ApplicationArea("Fixed Billing");

/**
 * Enumeration value for the "Time" application area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7ca3]
 */
    public static final com.topcoder.timetracker.audit.ApplicationArea TT_TIME = new ApplicationArea("Time");

/**
 * Enumeration value for the "Client" application area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c9d]
 */
    public static final com.topcoder.timetracker.audit.ApplicationArea TT_CLIENT = new ApplicationArea("Client");

/**
 * Enumeration value for the "Company" application area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c97]
 */
    public static final com.topcoder.timetracker.audit.ApplicationArea TT_COMPANY = new ApplicationArea("Company");

/**
 * Enumeration value for the "Project" application area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c91]
 */
    public static final com.topcoder.timetracker.audit.ApplicationArea TT_PROJECT = new ApplicationArea("Project");

/**
 * Enumeration value for the "User" application area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c8b]
 */
    public static final com.topcoder.timetracker.audit.ApplicationArea TT_USER = new ApplicationArea("User");

/**
 * Enumeration value for the "Invoice" application area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c85]
 */
    public static final com.topcoder.timetracker.audit.ApplicationArea TT_INVOICE = new ApplicationArea("Invoice");

/**
 * Enumeration value for the "Notification" application area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c7f]
 */
    public static final com.topcoder.timetracker.audit.ApplicationArea TT_NOTIFICATION = new ApplicationArea("Notification");

/**
 * Enumeration value for the "Configuration" application area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm249f]
 */
    public static final com.topcoder.timetracker.audit.ApplicationArea TT_CONFIGURATION = new ApplicationArea("Configuration");

/**
 * The descriptive name containing which application area the enumeration value describes. This is set during (private) construction, and immutable afterwards, obtained by calling getName
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c6b]
 */
    private final String name;

/**
 * Private enumeration constructor, which calls the Enum superconstructor, as well as initialising the description name for the enumeration.
 * 
 * @param name 
 */
    protected  ApplicationArea(String name) {        
        this.name = name;
    } 

/**
 * This returns the cardinal number of the enumeration constant, converted to a long for use with a database. This is delegated to Enum's getCardinal, and returns the result.
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm247c]
 * @return Cardinal ID, as a long
 */
    public long getId() {        
        // your code here
        return 0;
    } 

/**
 * Returns the name of the area
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm7c5f]
 * @return this.name;
 */
    public String getName() {        
        // your code here
        return null;
    } 

/**
 * Returns a string representation of the enumeration. This should simply call return name;
 * 
 * @poseidon-object-id [Im2fdf4ecfm110c3f53debmm24d2]
 * @return this.name;
 */
    public String toString() {        
        // your code here
        return null;
    } 
 }
