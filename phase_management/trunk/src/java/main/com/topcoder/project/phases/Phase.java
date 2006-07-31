
package com.topcoder.project.phases;
import com.topcoder.project.phases.CyclicDependencyException;
import java.util.*;

/**
 * <p>Represent the phase making up of the project. Any phase can only belong to one project and has a non-negative length in milliseconds. The phase also has the id, type and status attributes.</p>
 * <p>A phase could depend on a collection of other phases. The relationship between dependency and dependent could be specified by the Dependency class, that dependent phase will start/end after the dependency phase starts/ends with a lag time. </p>
 * <p>Phase could be attached a fixed start timestamp. Fixed start time is interpreted as “start no early than”, It's the earliest point when a phase can start.</p>
 * <p> Phase could also be attached scheduled start/end timestamps and actual start/end timestamps. Scheduled start/end timestamps are the original plan for the project timeline. Actual start/end timestamps are available for the phase that's already started/ended. They can override the start/end time calculated otherwise. </p>
 * <p>The phase start and end date could be calculated based on the dependencies and the timestamps. </p>
 * <p>Thread Safety: This class is mutable. so it's not thread safe.</p>
 *
 *
 *
 * @author TCSDESIGNER
 * @version 2.0
 */
public class Phase extends com.topcoder.project.phases.AttributableObject {

/**
 * <p>Represents a set of dependency of this phase, It could be accessed in the add/remove/clear/get methods. </p>
 *
 */
    private final java.util.Set dependencies = new HashSet();

/**
 * Fixed start time is interpreted as “start no early than”, It's the earliest point when a phase can start.
 * Initialized in the constructor and could be accessed via getter and setter method. The value could be null.
 *
 */
    private java.util.Date fixedStartDate = null;

/**
 * <p>Represents the length of the phase in milliseconds, it will be initialized in the constructor, and set in the setter method afterward.</p>
 *
 */
    private long length = 0;

/**
 * <p>Represents the project instance this phase belong to, it is initialized in the constructor and never changed afterward.</p>
 *
 */
    private final com.topcoder.project.phases.Project project;

/**
 * Scheduled start timestamp is the original plan for the project start timeline. Initialized in the constructor and could be accessed via getter and setter method. The value could be null.
 *
 */
    private java.util.Date scheduledStartDate = null;

/**
 * Scheduled end timestamp is the original plan for the project end timeline.
 * Initialized in the constructor and could be accessed via getter and setter method. The value could be null.
 *
 */
    private java.util.Date scheduledEndDate = null;

/**
 * Actual start timestamp is available for the phase that's already started.
 * It can override the start time calculated.
 * Initialized in the constructor and could be accessed via getter and setter method. The value could be null.
 *
 */
    private java.util.Date actualStartDate = null;

/**
 * Actual end timestamp is available for the phase that's already ended.
 * It can override the end time calculated.
 * Initialized in the constructor and could be accessed via getter and setter method. The value could be null.
 *
 */
    private java.util.Date actualEndDate = null;

/**
 * <p>Represents the phase type. Initialized in the constructor and could be accessed via getter and setter method. The value could be null.</p>
 *
 */
    private com.topcoder.project.phases.PhaseType phaseType = null;

/**
 * <p>Represents the phase status. Initialized in the constructor and could be accessed via getter and setter method. The value could be null.</p>
 *
 */
    private com.topcoder.project.phases.PhaseStatus phaseStatus = null;

/**
 * <p>Represents the phase id. Initialized in the constructor and could be accessed via getter and setter method. The value could not be negative .</p>
 *
 */
    private long id = 0;

/**
 * <p>Represents the cached end date of this phase. It will be reset when calculating the phase start/end date after any part of the project was changed.</p>
 *
 */
    private java.util.Date cachedEndDate = null;

/**
 * <p>Represents the cached start date of this phase. It will be reset when calculating the phase start/end date after any part of the project was changed.</p>
 *
 */
    private java.util.Date cachedStartDate = null;
/**
 *
 *
 *
 * @poseidon-type com.topcoder.project.phases.Dependency
 */
    private java.util.Collection dependency = new java.util.TreeSet();

/**
 * <p>Create a new instance of Phase with the project this phase belong to, and length in milliseconds of this phase. Phase will be add to the project automatically.</p>
 * <p>Note: Notify the change by this.notifyChange()</p>
 *
 *
 * @param project the project this phase belong to
 * @param length the length in milliseconds of the phase
 * @throws IllegalArgumentException if project is null or length is negative
 */
    public  Phase(com.topcoder.project.phases.Project project, long length) {
        this.length = length;
        this.project = project;
    }

/**
 * <p>Add a dependency to this phase, if the given phase already exists in the dependency list, nothing will happen. Both dependency and dependent phases are expected to be in current project.</p>
 * <p>For the sake of efficiency, this method will not detect the loop dependency. Loop dependency will be detected in calcEndDate() and calcStartDate() method.</p>
 * <p>Implementation Note: check the argument first and then add it to the dependencies set. Notify the change by this.notifyChange()</p>
 *
 *
 * @param dependency the dependeny to add
 * @throws IllegalArgumentException if the argument is null, or the dependency does not exist in the project, or dependent is not this phase
 */
    public void addDependency(com.topcoder.project.phases.Dependency dependency) {
        // your code here
    }

/**
 * <p>Remove the dependency from the dependency set. If the given phase does not exist in the dependency list, nothing will happen. After the removal, this phase will not depend on the removed one any more</p>
 * <p>Implementation Note: check the argument first and then remove it from the dependencies set. Notify the change by this.notifyChange()</p>
 *
 *
 * @param dependency the dependency to remove
 * @throws IllegalArgumentException if the argument is null, or the dependency does not exist in the project, or dependent is not this phase.
 */
    public void removeDependency(com.topcoder.project.phases.Dependency dependency) {
        // your code here
    }

/**
 * <p>Clear all the dependency phases from the dependency set. This phase will not depend on any phase in the project any more.</p>
 * <p>Note: Notify the change by this.notifyChange()</p>
 *
 */
    public void clearDependencies() {
        // your code here
    }

/**
 * <p>Return an array of all Dependency instances of this phase.</p>
 *
 *
 * @return an array of all Dependency instances of this phase
 */
    public Dependency[] getAllDependencies() {
        // your code here
        return null;
    }

/**
 * Return the fixed start date of the phase. Fixed start time is interpreted as &ldquo;start no early than&rdquo;, It's the earliest point when a phase can start. This date is optional. Implementation Note: return new Date(fixedStartDate.getTime())
 *
 *
 * @return the fixed start date of the phase
 */
    public java.util.Date getFixedStartDate() {
        // your code here
        return null;
    }

/**
 * Sets the fixed start date of the phase. Fixed start time is interpreted as &ldquo;start no early than&rdquo;, It's earliest point when a phase can start. This date is optional. Implementation Note: this.fixedStartDate = new Date(fixedStartDate.getTime())
 * <p>Note: Notify the change by this.notifyChange()</p>
 *
 *
 * @param startDate the fixed start date of the phase
 */
    public void setFixedStartDate(java.util.Date startDate) {
        this.fixedStartDate = startDate;
    }

/**
 * <p>Return the length of the phase in milliseconds. This method will simply return the length variable.</p>
 *
 *
 * @return the length of phase in milliseconds
 */
    public long getLength() {
        // your code here
        return 0;
    }

/**
 * <p>Set the length of the phase in milliseconds.</p>
 * <p>Note: Notify the change by this.notifyChange()</p>
 *
 *
 * @param length the length of phase in milliseconds
 * @throws llegalArgumentException if the length is negative
 */
    public void setLength(long length) {
        // your code here
    }

/**
 * <p>Return the project this phase belong to, this method will simply return the project variable.</p>
 *
 *
 * @return the project this phase belongs to
 */
    public com.topcoder.project.phases.Project getProject() {
        return this.project;
    }

/**
 * <p>Calculate the end date of this phase. It's calculated in this way:<br/>
 *  if the actual end time exists, return it, otherwise take the latest date among:<br/>
 *  a) calcStartDate() plus phase length<br/>
 *  b) if end dependencies exist, the dependency calculations plus lag time <br/>
 * </p>
 * <p>Implementation Note: execute project.calculateProjectDate() first, and return a copy of cachedEndDate.</p>
 *
 *
 * @return the end date of the phase
 * @throws CyclicDependencyException if cyclic dependency exists
 */
    public java.util.Date calcEndDate() {
        return new Date(length + calcStartDate().getTime());
    }

/**
 * <p>Calculate the start date of this phase. It's calculate in this way:<br/>
 *  if actual start time exists, return it, otherwise take the latest date among:<br/>
 *  a) fixed start time if it exists<br/>
 *  b) if no start dependencies exist, the project start time<br/>
 *  c) if start dependencies exist, the dependency calculations plus lag time<br/>
 * </p>
 * <p>Implementation&nbsp; Note: execute project.calculateProjectDate() first, and return a copy of cachedStartDate.</p>
 *
 *
 * @return the start date of the phase
 * @throws CyclicDependencyException if cyclic dependency exists
 */
    public java.util.Date calcStartDate() {
        return fixedStartDate;
    }

/**
 * Gets the phase id.
 *
 *
 * @return the phase id
 */
    public long getId() {
        return this.id;
    }

/**
 * Sets the phase id.
 *
 *
 * @param id the phase id
 * @throws IllegalArgumentException if id is negative
 */
    public void setId(long id) {
        this.id = id;
    }

/**
 * Gets the phase type.
 *
 *
 * @return the phase type
 */
    public com.topcoder.project.phases.PhaseType getPhaseType() {
        return phaseType;
    }

/**
 * Sets the phase type.The type could be null.
 *
 *
 * @param type the phase type
 */
    public void setPhaseType(com.topcoder.project.phases.PhaseType type) {
        phaseType = type;
    }

/**
 * Gets the phase type.
 *
 *
 * @return the phase status
 */
    public com.topcoder.project.phases.PhaseStatus getPhaseStatus() {
        return this.phaseStatus;
    }

/**
 * Sets the phase status. The status could be null.
 *
 *
 * @param status the phase status
 */
    public void setPhaseStatus(com.topcoder.project.phases.PhaseStatus status) {
        this.phaseStatus = status;
    }

/**
 * Gets the actual start date of this phase. Implementation Note: return new Date(date.getTime())
 *
 *
 * @return the actual start date of this phase.
 */
    public java.util.Date getActualStartDate() {
        return actualStartDate;
    }

/**
 * Sets the actual start date of this phase. Could be null, but cannot be after actualEndDate. Implementation Note: this.actualStartDate = new Date(actualStartDate.getTime())
 * <p>Note: Notify the change by this.notifyChange()</p>
 *
 *
 * @param actualStartDate the actual start date of this phase.
 * @throws llegalArgumentException if actualEndDate and actualStartDate are not null, but actualEndDate is before actualStartDate
 */
    public void setActualStartDate(java.util.Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

/**
 * Gets the actual end date of this phase. Implementation Note: return new Date(date.getTime())
 *
 *
 * @return the actual end date of this phase.
 */
    public java.util.Date getActualEndDate() {
        return actualEndDate;
    }

/**
 * Sets the scheduled start date of this phase. Scheduled start timestamp is the original plan for the project start timeline Implementation
 * <p>Note: Notify the change by this.notifyChange()</p>
 *
 *
 * @param actualEndDate the actual end date of this phase.
 * @throws llegalArgumentException if actualEndDate and actualStartDate are not null, but actualEndDate is before actualStartDate
 */
    public void setActualEndDate(java.util.Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

/**
 * Gets the scheduled start date of this phase. Scheduled start timestamp is the original plan for the project start timeline Implementation Note: return new Date(date.getTime())
 *
 *
 * @return the scheduled end date of this phase.
 */
    public java.util.Date getScheduledEndDate() {
        return this.scheduledEndDate;
    }

/**
 * Sets the scheduled end date of this phase. Scheduled end timestamp is the original plan for the project end timeline Could be null, but cannot be before scheduledStartDate. Implementation Note: this.scheduledEndDate = new Date(scheduledEndDate.getTime())
 *
 *
 * @param scheduledEndDate the scheduled end date of this phase.
 * @throws llegalArgumentException if scheduledEndDate and scheduledStartDate are not null, but scheduledEndDate is before scheduledStartDate
 */
    public void setScheduledEndDate(java.util.Date scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

/**
 * Sets the scheduled start date of this phase. Scheduled start timestamp is the original plan for the project start timeline Could be null, but cannot be after scheduledEndDate. Implementation Note: this.scheduledStartDate = new Date(scheduledStartDate.getTime())
 *
 *
 * @return Sets the scheduled start date of this phase.
 */
    public java.util.Date getScheduledStartDate() {
        return this.scheduledStartDate;
    }

/**
 * Sets the scheduled start date of this phase. Scheduled start timestamp is the original plan for the project start timeline Could be null, but cannot be after scheduledEndDate. Implementation Note: this.scheduledStartDate = new Date(scheduledStartDate.getTime())
 *
 *
 * @param scheduledStartDate the scheduled start date of this phase.
 * @throws llegalArgumentException if scheduledEndDate and scheduledStartDate are not null, but scheduledEndDate is before scheduledStartDate
 */
    public void setScheduledStartDate(java.util.Date scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

/**
 * <p>Calculate the end date of this phase. Refer to CS 1.3.2 for more details about the algorithm.</p>
 * <pre>
 * Implementation Note:
 * if the actual end time exists, return it, otherwise take the latest date among:
 * a) calcStartDate() plus phase length
 * b) if end dependencies exist, the dependency calculations plus lag time
 * </pre>
 * <br/>
 *  <br/>
 *
 *
 * @param visited the visited phases.
 * @param startDateCache the cached phase start date
 * @param endDateCache the cached phase end date
 * @return the end date of the phase
 * @throws CyclicDependencyException if cyclic dependency exists
 */
    java.util.Date calcEndDate(java.util.Set visited, Map startDateCache, Map endDateCache) {
        // your code here
        return null;
    }

/**
 * <p>Calculate the start date of this phase. Refer to CS 1.3.1 for more details about the algorithm.</p>
 * <p></p>
 * <pre>
 * Implementation Note:
 * if actual start time exists, return it, otherwise take the latest date among:
 * a) fixed start time if it exists
 * b) if no start dependencies exist, the project start time
 * c) if start dependencies exist, the dependency calculations plus lag time
 * </pre>
 *
 *
 * @param visited the visited phases.
 * @param startDateCache the cached phase start date
 * @param endDateCache the cached phase end date
 * @return the end date of the phase
 * @throws CyclicDependencyException if cyclic dependency exists
 */
    java.util.Date calcStartDate(java.util.Set visited, Map startDateCache, Map endDateCache) {
        // your code here
        return null;
    }

/**
 * <p>Notifies that this phase is changed, simply call project.setChanged(true);</p>
 *
 */
    void notifyChange() {
        // your code here
    }

/**
 * <p>Sets the cached start date of this phase. Implementation Note: this.cachedStartDate = new Date(cachedStartDate.getTime())</p>
 *
 *
 * @param cachedStartDate the cached start date
 */
    void setCachedStartDate(java.util.Date cachedStartDate) {
        // your code here
    }

/**
 * <p>Sets the cached end date of this phase. Implementation Note: this.cachedEndDate = new Date(cachedEndDate.getTime())</p>
 *
 *
 * @param cachedEndDate the cached end date
 */
    void setCachedEndDate(java.util.Date cachedEndDate) {
        // your code here
    }
 }
