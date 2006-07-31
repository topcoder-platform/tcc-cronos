
package com.topcoder.project.phases;
import java.util.*;

/**
 * <p>This class represents the phase status. A phase status consists of a numeric identifier and a name. This class is serializable.</p>
 * <p>This class defines three build in phase types- SCHEDULED, OPEN, CLOSED</p>
 * <p>Thread Safety: This class is mutable. so it's not thread safe.</p>
 *
 *
 * @author TCSDESIGNER
 * @version 2.0
 */
public class PhaseStatus implements java.io.Serializable {

/**
 * A build in phase status that represents the phase is scheduled.
 *
 */
    public static final com.topcoder.project.phases.PhaseStatus SCHEDULED = new PhaseStatus(1, "Scheduled");

/**
 * A build in phase status that represents the phase is open.
 *
 */
    public static final com.topcoder.project.phases.PhaseStatus OPEN = new PhaseStatus(2, "Open");

/**
 * A build in phase status that represents the phase is close.
 *
 */
    public static final com.topcoder.project.phases.PhaseStatus CLOSE = new PhaseStatus(3, "Close");

/**
 * <p>Represents the phase status id. Initialized in the constructor and could be accessed via getter and setter method. The value could not be negative .</p>
 *
 */
    private long id = 0;

/**
 * <p>Represents the phase status name. Initialized in the constructor and could be accessed via getter and setter method. The value could not be null .</p>
 *
 */
    private String name = null;

/**
 * The constructor with the phase status id and name.
 *
 *
 * @param id the phase status id
 * @param name the phase status name
 * @throws IllegalArgumentException if id is negative or name is null
 */
    public  PhaseStatus(long id, String name) {
        // your code here
    }

/**
 * Gets the phase status id.
 *
 *
 * @return the phase status id
 */
    public long getId() {
        // your code here
        return 0;
    }

/**
 * Sets the phase status id.
 *
 *
 * @param id the phase status id
 * @throws IllegalArgumentException if id is negative
 */
    public void setId(long id) {
        // your code here
    }

/**
 * Gets the phase status name.
 *
 *
 * @return the phase status name
 */
    public String getName() {
        // your code here
        return null;
    }

/**
 * Sets the phase status name.
 *
 *
 * @param name the phase status name
 * @return
 * @throws IllegalArgumentException if name is null
 */
    public String setName(String name) {
        // your code here
        return null;
    }
 }
