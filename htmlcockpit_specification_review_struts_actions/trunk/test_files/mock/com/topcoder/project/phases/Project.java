package com.topcoder.project.phases;

import com.topcoder.date.workdays.DefaultWorkdays;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "project", namespace = "com.topcoder.project.phases")
public class Project extends AttributableObject {

    private static final long serialVersionUID = 1L;

    private Set<Phase> phases = new HashSet();

    private DefaultWorkdays workdays;

    private Date startDate;

    private long id;

    private boolean changed = true;

    public Project(Date startDate, DefaultWorkdays workdays) {
        this.workdays = workdays;
        setStartDate(startDate);
    }

    public Project() {
        this.workdays = null;
    }

    public DefaultWorkdays getWorkdays() {
        return this.workdays;
    }

    public void setWorkdays(DefaultWorkdays workdays) {
        this.workdays = workdays;
    }

    public Date getStartDate() {
        return new Date(this.startDate.getTime());
    }

    public void setStartDate(Date startDate) {
        if (startDate == null) {
            if (this.startDate != null) {
                this.startDate = null;
                this.changed = true;
            }
        } else if (!(startDate.equals(this.startDate))) {
            this.startDate = new Date(startDate.getTime());
            this.changed = true;
        }
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    boolean isChanged() {
        return this.changed;
    }

    void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void addPhase(Phase phase) {
        ProjectPhaseHelper.checkObjectNotNull(phase, "phase");

        if (!(this.phases.contains(phase))) {
            this.phases.add(phase);
            this.changed = true;
        }
    }

    public void removePhase(Phase phase) {
        ProjectPhaseHelper.checkObjectNotNull(phase, "phase");
        if (!(this.phases.contains(phase))) {
            throw new IllegalArgumentException("The phase is not exist.");
        }

        Set removedPhases = getAllRemovedPhases(phase);

        this.phases.removeAll(removedPhases);
        this.changed = true;
    }

    private Set<Phase> getAllRemovedPhases(Phase phase) {
        Set removedPhases = new HashSet();
        Set visitedPhases = new HashSet();
        removedPhases.add(phase);
        visitedPhases.add(phase);

        Stack stack = new Stack();
        Stack arrayStack = new Stack();
        Stack indexStack = new Stack();
        stack.ensureCapacity(this.phases.size());
        arrayStack.ensureCapacity(this.phases.size());
        indexStack.ensureCapacity(this.phases.size());

        for (Iterator itr = this.phases.iterator(); itr.hasNext();) {
            Phase nextPhase = (Phase) itr.next();

            if (visitedPhases.contains(nextPhase)) {
                continue;
            }

            visitedPhases.add(nextPhase);
            stack.push(nextPhase);
            arrayStack.push(nextPhase.getAllDependencies());
            indexStack.push(new Integer(0));
            do {
                Dependency[] currentDependencies = (Dependency[]) (Dependency[]) arrayStack.peek();
                int currentIndex = ((Integer) indexStack.peek()).intValue();

                if (currentIndex < currentDependencies.length) {
                    Phase dependency = currentDependencies[currentIndex].getDependency();

                    indexStack.pop();
                    indexStack.push(new Integer(currentIndex + 1));

                    if (!(visitedPhases.contains(dependency))) {
                        visitedPhases.add(dependency);
                        stack.push(dependency);
                        arrayStack.push(dependency.getAllDependencies());
                        indexStack.push(new Integer(0));
                    } else {
                        if (stack.contains(dependency)) {
                            throw new CyclicDependencyException("Cyclic dependency detected.");
                        }

                        if (removedPhases.contains(dependency)) {
                            do
                                removedPhases.add(stack.pop());
                            while (!(stack.empty()));

                            arrayStack.clear();
                            indexStack.clear();
                            break;
                        }
                    }
                } else {
                    stack.pop();
                    arrayStack.pop();
                    indexStack.pop();
                }
            } while (!(stack.empty()));
        }

        return removedPhases;
    }

    public void clearPhases() {
        this.phases.clear();
        this.changed = true;
    }

    public void setPhases(Set<Phase> phases) {
        if (phases == null)
            return;
        this.phases = phases;
    }

    public Set<Phase> getPhases() {
        return this.phases;
    }

    public Phase[] getAllPhases() {
        return getAllPhases(new PhaseDateComparator());
    }

    public Phase[] getAllPhases(Comparator comparator) {
        ProjectPhaseHelper.checkObjectNotNull(comparator, "comparator");

        Phase[] results = (Phase[]) (Phase[]) this.phases.toArray(new Phase[this.phases.size()]);
        Arrays.sort(results, comparator);
        return results;
    }

    public Phase[] getInitialPhases() {
        Set initialPhases = new HashSet();

        for (Iterator itr = this.phases.iterator(); itr.hasNext();) {
            Phase phase = (Phase) itr.next();
            if (phase.getAllDependencies().length == 0) {
                initialPhases.add(phase);
            }
        }

        return ((Phase[]) (Phase[]) initialPhases.toArray(new Phase[initialPhases.size()]));
    }

    boolean containsPhase(Phase phase) {
        return this.phases.contains(phase);
    }

    public Date calcEndDate() {
        Date projectEndDate = this.startDate;

        for (Iterator itr = this.phases.iterator(); itr.hasNext();) {
            Date phaseEndDate = ((Phase) itr.next()).calcEndDate();
            if (phaseEndDate.getTime() > projectEndDate.getTime()) {
                projectEndDate = phaseEndDate;
            }

        }

        return new Date(projectEndDate.getTime());
    }

    void calculateProjectDate() {
        if (!(this.changed)) {
            return;
        }

        Map startDateCache = new HashMap();
        Map endDateCache = new HashMap();
        for (Iterator itr = this.phases.iterator(); itr.hasNext();) {
            Phase phase = (Phase) itr.next();
            phase.setCachedStartDate(phase.calcStartDate(new HashSet(), startDateCache, endDateCache));
            phase.setCachedEndDate(phase.calcEndDate(new HashSet(), startDateCache, endDateCache));
        }

        this.changed = false;
    }
}
