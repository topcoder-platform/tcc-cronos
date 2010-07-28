package com.topcoder.project.phases;

import java.util.Date;

final class ProjectPhaseHelper {

    public static void checkObjectNotNull(Object obj, String name) {
        if (obj == null)
            throw new IllegalArgumentException(name + " can not be null.");
    }

    public static void checkLongNotNegative(long value, String name) {
        if (value < 0L)
            throw new IllegalArgumentException(name + " can not be negative.");
    }

    public static void checkDateNotBefore(Date endDate, Date startDate, String endDateName, String startDateName) {
        if ((endDate == null) || (startDate == null)) {
            return;
        }

        if (endDate.getTime() < startDate.getTime())
            throw new IllegalArgumentException(endDateName + " can not before " + startDateName);
    }

    public static void checkPhaseNotSameInstance(Phase dependency, Phase dependent) {
        if (dependency == dependent)
            throw new IllegalArgumentException("dependency and dependent are same instance.");
    }
}
