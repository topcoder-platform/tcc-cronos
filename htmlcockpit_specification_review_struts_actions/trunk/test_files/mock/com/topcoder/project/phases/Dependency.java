package com.topcoder.project.phases;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "dependency", namespace = "com.topcoder.project.phases")
public class Dependency implements Serializable {

    private Phase dependency;

    private Phase dependent;

    private boolean dependencyStart;

    private boolean dependentStart;

    private long lagTime;

    public Dependency(Phase dependency, Phase dependent, boolean dependencyStart, boolean dependentStart, long lagTime) {
        ProjectPhaseHelper.checkObjectNotNull(dependency, "dependency");
        ProjectPhaseHelper.checkObjectNotNull(dependent, "dependent");
        ProjectPhaseHelper.checkPhaseNotSameInstance(dependency, dependent);

        this.dependency = dependency;
        this.dependent = dependent;
        this.dependencyStart = dependencyStart;
        this.dependentStart = dependentStart;
        this.lagTime = lagTime;
    }

    public Dependency() {
        this.dependency = null;
        this.dependent = null;
        this.dependencyStart = false;
        this.dependentStart = false;
        this.lagTime = 0L;
    }

    public void setDependency(Phase dependency) {
        ProjectPhaseHelper.checkObjectNotNull(dependency, "dependency");
        ProjectPhaseHelper.checkPhaseNotSameInstance(dependency, this.dependent);

        this.dependency = dependency;
        this.dependent.notifyChange();
    }

    public Phase getDependency() {
        return this.dependency;
    }

    public void setDependent(Phase dependent) {
        ProjectPhaseHelper.checkObjectNotNull(dependent, "dependent");
        ProjectPhaseHelper.checkPhaseNotSameInstance(this.dependency, dependent);

        this.dependent = dependent;
        this.dependent.notifyChange();
    }

    public Phase getDependent() {
        return this.dependent;
    }

    public void setDependencyStart(boolean dependencyStart) {
        this.dependencyStart = dependencyStart;
        this.dependent.notifyChange();
    }

    public boolean isDependencyStart() {
        return this.dependencyStart;
    }

    public void setDependentStart(boolean dependentStart) {
        this.dependentStart = dependentStart;
        this.dependent.notifyChange();
    }

    public boolean isDependentStart() {
        return this.dependentStart;
    }

    public void setLagTime(long lagTime) {
        this.lagTime = lagTime;
        this.dependent.notifyChange();
    }

    public long getLagTime() {
        return this.lagTime;
    }
}
