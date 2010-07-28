package com.topcoder.project.phases;

import java.io.Serializable;

public class PhaseType implements Serializable {

    private long id;

    private String name;

    public PhaseType(long id, String name) {
        setId(id);
        setName(name);
    }

    public PhaseType() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        ProjectPhaseHelper.checkLongNotNegative(id, "id");

        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        ProjectPhaseHelper.checkObjectNotNull(name, "name");

        this.name = name;
    }
}
