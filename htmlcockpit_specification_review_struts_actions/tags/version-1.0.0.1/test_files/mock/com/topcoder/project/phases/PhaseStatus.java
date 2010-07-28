package com.topcoder.project.phases;

import java.io.Serializable;

public class PhaseStatus implements Serializable {

    private static final int SCHEDULED_ID = 1;

    private static final String SCHEDULED_NAME = "Scheduled";

    private static final int OPEN_ID = 2;

    private static final String OPEN_NAME = "Open";

    private static final int CLOSED_ID = 3;

    private static final String CLOSED_NAME = "Closed";

    public static final PhaseStatus SCHEDULED = new PhaseStatus(1L, "Scheduled");

    public static final PhaseStatus OPEN = new PhaseStatus(2L, "Open");

    public static final PhaseStatus CLOSED = new PhaseStatus(3L, "Closed");

    private long id;

    private String name;

    public PhaseStatus(long id, String name) {
        setId(id);
        setName(name);
    }

    public PhaseStatus() {
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
