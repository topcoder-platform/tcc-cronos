
package com.topcoder.project.phases;
import java.util.*;

/**
 * <p>The Dependency class represents the relationship between two phases. This class specifies whether the dependent phase will start/end after the dependency phase starts/ends with a lag time. This class is serializable.</p>
 * <p>Thread Safety: This class is mutable. so it's not thread safe.</p>
 *
 *
 * @author TCSDESIGNER
 * @version 2.0
 */
public class Dependency implements java.io.Serializable {

/**
 * The dependency phase. Initialized in the constructor and could be accessed via getter and setter method. Cannot be null.
 *
 */
    private com.topcoder.project.phases.Phase dependency = null;

/**
 * The dependent phase. Initialized in the constructor and could be accessed via getter and setter method. Cannot be null.
 *
 */
    private com.topcoder.project.phases.Phase dependent = null;

/**
 * <p>The dependency  start/end flag. If the value is true, the dependent phase will start/end after the dependency phase starts. If the value is false, the dependent phase will start/end after the dependency phase ends.</p>
 * <p>Initialized in the constructor and could be accessed via getter and setter method.</p>
 *
 */
    private boolean dependencyStart = false;

/**
 * <p>The dependent start/end flag. If the value is true, the dependent phase will start after the dependency phase starts/ends. If the value is false, the dependent phase will end after the dependency phase starts/ends.</p>
 * <p>Initialized in the constructor and could be accessed via getter and setter method.</p>
 *
 */
    private boolean dependentStart = false;

/**
 * <p>The lag time in milliseconds. The dependent phase will start/end after the dependency phase starts/ends with this lag time. Negetive lag time is allowed.</p>
 * <p>Initialized in the constructor and could be accessed via getter and setter method.</p>
 *
 */
    private long lagTime = 0;
/**
 *
 *
 *
 * @poseidon-type com.topcoder.project.phases.Phase
 */
    private java.util.Collection phase = new java.util.TreeSet();

/**
 * Creates a Dependency instance with given dependency, dependent, dependency start/end flag, dependent start/end flag and the lag time.
 *
 *
 * @param dependency the dependency phase
 * @param dependent the dependent phase
 * @param dependencyStart the dependency start/end flag
 * @param dependentStart the dependent start/end flag
 * @param lagTime the lag time in milliseconds
 * @throws IllegalArgumentException if dependency or dependent is null, or dependency and dependent are same instance
 */
    public  Dependency(com.topcoder.project.phases.Phase dependency, com.topcoder.project.phases.Phase dependent, boolean dependencyStart, boolean dependentStart, long lagTime) {
        // your code here
    }

/**
 * <p>Sets the dependency phase. </p>
 * <p>Note: notify the change by dependency.notifyChange()</p>
 *
 *
 * @param dependency the dependency phase
 * @throws IllegalArgumentException if argument is null, or dependency and dependent are same instance
 */
    public void setDependency(com.topcoder.project.phases.Phase dependency) {
        // your code here
    }

/**
 * <p>Gets the dependency phase.</p>
 *
 *
 * @return the dependency phase
 */
    public com.topcoder.project.phases.Phase getDependency() {
        // your code here
        return null;
    }

/**
 * <p>Sets the dependent phase.</p>
 * <p>Note: notify the change by dependency.notifyChange()</p>
 *
 *
 * @param dependent the dependent phase
 * @throws IllegalArgumentException if argument is null,or dependency and dependent are same instance
 */
    public void setDependent(com.topcoder.project.phases.Phase dependent) {
        // your code here
    }

/**
 * <p>Gets the dependent phase.</p>
 *
 *
 * @return the dependent phase
 */
    public com.topcoder.project.phases.Phase getDependent() {
        // your code here
        return null;
    }

/**
 * Sets the dependency start/end flag. If the value is true, the dependent phase will start/end after the dependency phase starts. If the value is false, the dependent phase will start/end after the dependency phase ends.
 * <p>Note: notify the change by dependency.notifyChange()</p>
 *
 *
 * @param dependencyStart the dependency start/end flag
 */
    public void setDependencyStart(boolean dependencyStart) {
        // your code here
    }

/**
 * Returns the dependency  start/end flag. If the value is true, the dependent phase will start/end after the dependency phase starts. If the value is false, the dependent phase will start/end after the dependency phase ends.
 *
 *
 * @return the dependency start/end flag
 */
    public boolean isDependencyStart() {
        // your code here
        return false;
    }

/**
 * Sets the dependent start/end flag. If the value is true, the dependent phase will start after the dependency phase starts/ends. If the value is false, the dependent phase will end after the dependency phase starts/ends.
 * <p>Note: notify the change by dependency.notifyChange()</p>
 *
 *
 * @param dependentStart the dependent start/end flag
 */
    public void setDependentStart(boolean dependentStart) {
        // your code here
    }

/**
 * Returns the dependent start/end flag. If the value is true, the dependent phase will start after the dependency phase starts/ends. If the value is false, the dependent phase will end after the dependency phase starts/ends.
 *
 *
 * @return the dependent start/end flag
 */
    public boolean isDependentStart() {
        // your code here
        return false;
    }

/**
 * <p>Sets the lag time in milliseconds. The dependent phase will start/end after the dependency phase starts/ends with this lag time.</p>
 * <p>Note: notify the change by dependency.notifyChange()</p>
 *
 *
 * @param lagTime the lag time in milliseconds
 */
    public void setLagTime(long lagTime) {
        // your code here
    }

/**
 * <p>Gets the lag time in milliseconds. The dependent phase will start/end after the dependency phase starts/ends with this lag time.</p>
 *
 *
 * @return the lag time in milliseconds
 */
    public long getLagTime() {
        // your code here
        return 0;
    }
 }
