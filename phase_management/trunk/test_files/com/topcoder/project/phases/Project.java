
package com.topcoder.project.phases;
import com.topcoder.project.phases.CyclicDependencyException;
import com.topcoder.project.phases.PhaseDateComparator;
import java.util.*;

/**
 * <p>It is the main class of this component, each application or component can be represented by a Project instance which is composed of a collection of phases depending on each other. For example, an application may be composed of design phase, design review phase, development phase, dev review phase, and deployment phase. The phases (except the first one) can only be started only if the former one is finished, and each phase has a start date, and length to finish, at the same time, the project has a start date so that we can know when will the project be finished.</p>
 * <p>All the phases added to the project and dependent should belong to the same project. When a phase in one project depends on the phase in another project, we can always combine the two projects into one so that all phases belong to the same project to make things easy.</p>
 * <p>Thread Safety: This class is mutable. so it's not thread safe.</p>
 *
 *
 * @author TCSDESIGNER
 * @version 2.0
 */
public class Project extends com.topcoder.project.phases.AttributableObject {

/**
 * <p>Represents a set of phases owned by this project. This set could be accessed by the create/get/clear/remove methods.</p>
 *
 */
    private final java.util.Set phases = new HashSet();

/**
 * <p>Represents the start date of the project, it is initialized in the constructor, and changed in the setter method.</p>
 *
 */
    private java.util.Date startDate = null;

/**
 * <p>Represents the instance of Workdays to calculate the end date for the phase in the project. It will be initialized in the constructor, and never changed later.</p>
 *
 */
    //private final com.topcoder.date.workdays.Workdays workdays;

/**
 * <p>Represents the project id. Initialized in the constructor and could be accessed via getter and setter method. The value could not be negative .</p>
 *
 */
    private long id = 0;

/**
 * <p>Represents ...</p>
 *
 */
    private boolean changed = true;

/**
 * <p>Create a new instance with the start date of the project and the Workdays instance. There is no phases in the project initially. This constructor will simply assign the startDate and workdays arguments to its corresponding variables.</p>
 *
 *
 * @param startDate the start date of the project
 * @param workdays the Workdays instance to calculate the end date for phases
 * @throws IllegalArgumentException if any argument is null
 */
    public  Project(java.util.Date startDate, com.topcoder.date.workdays.Workdays workdays) {
        // your code here
    }

/**
 * <p>Add the phase into the project. This method will simply add the phase into the inner phases set.</p>
 * <p>Note: the project changed flag should be set to true</p>
 *
 *
 * @param phase the phase to add into the project.
 * @throws IllegalArgumentException if the phase is null or phase's project is not this one
 */
    public void addPhase(com.topcoder.project.phases.Phase phase) {
        phases.add(phase);
    }

/**
 * <p>Remove the phase from the phases set, all its dependent phases will be removed automatically. Refer to CS for more details.</p>
 * <p>Note: the project changed flag should be set to true</p>
 *
 *
 * @param phase the phase to remove from project
 * @throws IllegalArgumentException if the phase is null or does not exist in this project
 */
    public void removePhase(com.topcoder.project.phases.Phase phase) {
        phases.remove(phase);
    }

/**
 * <p>Clear all the phases from the set.This method will simply call the phases.clear to remove all the phases.</p>
 * <p>Note: the project changed flag should be set to true</p>
 *
 */
    public void clearPhases() {
        phases.clear();
    }

/**
 * <p>Return an array of all phases in this project. The pashses are sorted by PhaseDateComparator.</p>
 *
 *
 * @return an array of all phases in this project
 */
    public Phase[] getAllPhases() {
        return (Phase[])phases.toArray(new Phase[0]);
    }

/**
 * <p>Return an array of phases sorted by the given comparator.</p>
 * <p>Implementation note: Get the phase array and delegate to Arrays.sort(phases, comparator)</p>
 *
 *
 * @param comparator the comparator instance to sort the phases
 * @return an array of sorted phases
 * @throws IllegalArgumentException if the argument is null
 * @throws ClassCastException if the comparator cannot compare the phases
 */
    public Phase[] getAllPhases(java.util.Comparator comparator) {
        // your code here
        return null;
    }

/**
 * <p>Return an array of all phases in this project. The pashses are sorted by PhaseDateComparator.</p>
 * <p>Note: the project changed flag should be set to true</p>
 *
 *
 * @param startDate the start date of this project
 * @throws IllegalArgumentException if the argument is null
 */
    public void setStartDate(java.util.Date startDate) {
        // your code here
    }

/**
 * <p>Return the startDate of the project, this method will return a clone of startDate variable so that it will not be modified from outside.</p>
 *
 *
 * @return the start date of this project
 */
    public java.util.Date getStartDate() {
        // your code here
        return null;
    }

/**
 * <p>Return the workdays instance.</p>
 *
 *
 * @return the workdays instance
 */
/*
    public com.topcoder.date.workdays.Workdays getWorkdays() {
        // your code here
        return null;
    }
*/

/**
 * <p>Return an array of initial phases which does not depend on the other phases in the project. For example, in the project, phase D depends on phase C, phase C depends on phase B and A, then phase A and B should be returned. This method will calculate the in-degree of each phase in the graph (project) first, and return all the phases whose in-degree is zero.</p>
 *
 *
 * @return an array of initial phases which does not depend on the other phases in the project.
 */
    public Phase[] getInitialPhases() {
        // your code here
        return null;
    }

/**
 * <p>Return the end date of the project, project end date is the latest end date of all phases.</p>
 * <p>Implementation note: This method will calculate the end date for each phase, and return the latest one. </p>
 *
 *
 * @return the end date of the project.
 * @throws CyclicDependencyException if cyclic dependency exists
 */
    public java.util.Date calcEndDate() {
        // your code here
        return null;
    }

/**
 * Gets the project id.
 *
 *
 * @return
 */
    public long getId() {
        // your code here
        return 0;
    }

/**
 * Sets the project id.
 *
 *
 * @param id the project id
 * @throws IllegalArgumentException if id is negative
 */
    public void setId(long id) {
        // your code here
    }

/**
 * <p>Calculate the state/end date of all phases and cache the date.</p>
 * <p>Implementation note: if change flag is false, simply return. Otherwise calculate the start and end date for each phase, and cache the date. Refer to CS for more details.</p>
 * <p></p>
 *
 */
    void calculateProjectDate() {
        // your code here
    }

/**
 * <p>Set the changed flag.</p>
 *
 *
 * @param changed the changed flag
 */
    void setChanged(boolean changed) {
        // your code here
    }

/**
 * <p>Gets the changed flag.</p>
 *
 *
 * @return the changed flag
 */
    boolean isChanged() {
        // your code here
        return false;
    }
/**
 *
 *
 */
    //public com.topcoder.date.workdays.Workdays workdays;
 }
