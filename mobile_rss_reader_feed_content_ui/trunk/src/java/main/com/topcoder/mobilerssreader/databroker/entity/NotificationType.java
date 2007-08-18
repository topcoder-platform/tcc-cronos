
package com.topcoder.mobilerssreader.databroker.entity;
import java.io.*;

/**
 * This class is an Enum class. It publics three notification types, which are used in RSSSubscription class.
 * <p>User can also refer its member to get notification type.</p>
 * <p>There are two reasons to utilize int value to represent notification type. One is int value can be store into RMS storage directly as byte array. Another is it saves some memory usage. It seems not good choice to use object instead of int value.</p>
 * <p>Thread safety: All members are final and there is no method to modify members.</p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm763b]
 */
public class NotificationType {

/**
 * <p>Represents default sound notification type, whose notification type id is 1.</p>
 * <p>It is initialized in constructor and referred as one notification type.</p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm75c2]
 */
    public static final int DEFAULTSOUND = 1;

/**
 * <p>Represents vibrate notification type, whose notification type id is 2.</p>
 * <p>It is initialized in constructor and referred as one notification type.</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm758d]
 */
    public static final int VIBRATE = 2;

/**
 * <p>Represents display new message icon notification type, whose notification type id is 3.</p>
 * <p>It is initialized in constructor and referred as one notification type.</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm7558]
 */
    public static final int DISPLAYNEWMSGICON = 3;

/**
 * <p>Private empty constrcutor.</p>
 * 
 * @poseidon-object-id [Im4b8a5597m1137fb8bb8cmm7623]
 */
    private  NotificationType() {        
        // your code here
    } 
 }
