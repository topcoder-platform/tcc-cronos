/*     */package com.topcoder.project.phases;

/*     */
/*     */import com.topcoder.date.workdays.DefaultWorkdays; /*     */
import com.topcoder.date.workdays.WorkdaysUnitOfTime; /*     */
import java.util.Date; /*     */
import java.util.HashSet; /*     */
import java.util.Iterator; /*     */
import java.util.Map; /*     */
import java.util.Set; /*     */
import javax.xml.bind.annotation.XmlType;

/*     */
/*     */@XmlType(name = "phase", namespace = "com.topcoder.project.phases")
/*     */public class Phase extends AttributableObject
/*     */{

    /*     */private static final long MINUTE_MS = 60000L;

    /* 54 */private Set<Dependency> dependencies = new HashSet();

    /*     */private Project project;

    /*     */private long length;

    /*     */private long id;

    /*     */private PhaseType phaseType;

    /*     */private PhaseStatus phaseStatus;

    /*     */private Date fixedStartDate;

    /*     */private Date scheduledStartDate;

    private Date scheduledEndDate;

    private Date actualStartDate;

    private Date actualEndDate;

    private Date cachedEndDate;

    private Date cachedStartDate;

    public Phase() {
        this.project = null;
    }

    public Phase(Project project, long length) {
        ProjectPhaseHelper.checkObjectNotNull(project, "project");
        ProjectPhaseHelper.checkLongNotNegative(length, "length");

        this.project = project;
        this.length = length;

        this.project.addPhase(this);
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long length) {
        ProjectPhaseHelper.checkLongNotNegative(length, "length");

        this.length = length;
        notifyChange();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        ProjectPhaseHelper.checkLongNotNegative(id, "id");

        this.id = id;
    }

    public PhaseType getPhaseType() {
        return this.phaseType;
    }

    public void setPhaseType(PhaseType type) {
        this.phaseType = type;
    }

    public PhaseStatus getPhaseStatus() {
        return this.phaseStatus;
    }

    public void setPhaseStatus(PhaseStatus status) {
        this.phaseStatus = status;
    }

    public Date getFixedStartDate() {
        return new Date(this.fixedStartDate.getTime());
    }

    public void setFixedStartDate(Date fixedStartDate) {
        this.fixedStartDate = new Date(fixedStartDate.getTime());
        notifyChange();
    }

    public Date getScheduledStartDate() {
        return new Date(this.scheduledStartDate.getTime());
    }

    public void setScheduledStartDate(Date scheduledStartDate) {
        ProjectPhaseHelper.checkDateNotBefore(this.scheduledEndDate, scheduledStartDate, "scheduledEndDate",
            "scheduledStartDate");

        this.scheduledStartDate = new Date(scheduledStartDate.getTime());
    }

    public Date getScheduledEndDate() {
        return new Date(this.scheduledEndDate.getTime());
    }

    public void setScheduledEndDate(Date scheduledEndDate) {
        ProjectPhaseHelper.checkDateNotBefore(scheduledEndDate, this.scheduledStartDate, "scheduledEndDate",
            "scheduledStartDate");

        this.scheduledEndDate = new Date(scheduledEndDate.getTime());
    }

    public Date getActualStartDate() {
        return new Date(this.actualStartDate.getTime());
    }

    public void setActualStartDate(Date actualStartDate) {
        ProjectPhaseHelper
            .checkDateNotBefore(this.actualEndDate, actualStartDate, "actualEndDate", "actualStartDate");
        this.actualStartDate = new Date(actualStartDate.getTime());
        notifyChange();
    }

    public Date getActualEndDate() {
        return new Date(this.actualEndDate.getTime());
    }

    public void setActualEndDate(Date actualEndDate) {
        ProjectPhaseHelper
            .checkDateNotBefore(actualEndDate, this.actualStartDate, "actualEndDate", "actualStartDate");
        this.actualEndDate = new Date(actualEndDate.getTime());
        notifyChange();
    }

    void setCachedStartDate(Date cachedStartDate) {
        this.cachedStartDate = new Date(cachedStartDate.getTime());
    }

    void setCachedEndDate(Date cachedEndDate) {
        this.cachedEndDate = new Date(cachedEndDate.getTime());
    }

    public void addDependency(Dependency dependency) {
        checkDependency(dependency);

        if (!(this.dependencies.contains(dependency))) {
            this.dependencies.add(dependency);
            notifyChange();
        }
    }

    private void checkDependency(Dependency dependency) {
        ProjectPhaseHelper.checkObjectNotNull(dependency, "dependency");

        if (!(this.project.containsPhase(dependency.getDependency()))) {
            throw new IllegalArgumentException("The dependency does not exist in this project.");
        }

        if (dependency.getDependent() != this)
            throw new IllegalArgumentException("The dependent is not this phase.");
    }

    public void removeDependency(Dependency dependency) {
        checkDependency(dependency);

        if (this.dependencies.contains(dependency)) {
            this.dependencies.remove(dependency);
            notifyChange();
        }
    }

    public void clearDependencies() {
        this.dependencies.clear();
        notifyChange();
    }

    public Set<Dependency> getDependencies() {
        return this.dependencies;
    }

    public void setDependencies(Set<Dependency> dependencies) {
        if (dependencies == null)
            return;
        this.dependencies = dependencies;
    }

    public Dependency[] getAllDependencies() {
        return ((Dependency[]) (Dependency[]) this.dependencies.toArray(new Dependency[this.dependencies.size()]));
    }

    public Date calcEndDate() {
        this.project.calculateProjectDate();

        return this.cachedEndDate;
    }

    public Date calcStartDate() {
        this.project.calculateProjectDate();

        return this.cachedStartDate;
    }

    Date calcEndDate(Set visited, Map startDateCache, Map endDateCache) {
        if (visited.contains(this)) {
            throw new CyclicDependencyException("Cycle dependency detected.");
        }

        if (this.actualEndDate != null) {
            return this.actualEndDate;
        }

        if (endDateCache.containsKey(this)) {
            return ((Date) endDateCache.get(this));
        }

        Date latest = addDate(calcStartDate(visited, startDateCache, endDateCache), this.length);

        visited.add(this);

        for (Iterator itr = this.dependencies.iterator(); itr.hasNext();) {
            Dependency dependency = (Dependency) itr.next();
            if (!(dependency.isDependentStart())) {
                Date dependencyDate = null;
                if (dependency.isDependencyStart()) {
                    dependencyDate =
                        addDate(dependency.getDependency().calcStartDate(visited, startDateCache, endDateCache),
                            dependency.getLagTime());
                } else {
                    dependencyDate =
                        addDate(dependency.getDependency().calcEndDate(visited, startDateCache, endDateCache),
                            dependency.getLagTime());
                }

                if (dependencyDate.getTime() > latest.getTime()) {
                    latest = dependencyDate;
                }
            }

        }

        visited.remove(this);

        endDateCache.put(this, latest);

        return latest;
    }

    Date calcStartDate(Set visited, Map startDateCache, Map endDateCache) {
        if (visited.contains(this)) {
            throw new CyclicDependencyException("Cycle dependency detected.");
        }

        if (this.actualStartDate != null) {
            return new Date(this.actualStartDate.getTime());
        }

        if (startDateCache.containsKey(this)) {
            return ((Date) startDateCache.get(this));
        }

        Date latest = this.project.getStartDate();

        if ((this.fixedStartDate != null) && (this.fixedStartDate.getTime() > latest.getTime())) {
            latest = this.fixedStartDate;
        }

        visited.add(this);

        for (Iterator itr = this.dependencies.iterator(); itr.hasNext();) {
            Dependency dependency = (Dependency) itr.next();
            if (dependency.isDependentStart()) {
                Date dependencyDate = null;
                if (dependency.isDependencyStart()) {
                    dependencyDate =
                        addDate(dependency.getDependency().calcStartDate(visited, startDateCache, endDateCache),
                            dependency.getLagTime());
                } else {
                    dependencyDate =
                        addDate(dependency.getDependency().calcEndDate(visited, startDateCache, endDateCache),
                            dependency.getLagTime());
                }

                if (dependencyDate.getTime() > latest.getTime()) {
                    latest = dependencyDate;
                }
            }

        }

        visited.remove(this);

        startDateCache.put(this, latest);

        return latest;
    }

    private Date addDate(Date date, long length) {
        return this.project.getWorkdays().add(date, WorkdaysUnitOfTime.MINUTES, (int) (length / 60000L));
    }

    void notifyChange() {
        if (this.project == null)
            return;
        this.project.setChanged(true);
    }
}
