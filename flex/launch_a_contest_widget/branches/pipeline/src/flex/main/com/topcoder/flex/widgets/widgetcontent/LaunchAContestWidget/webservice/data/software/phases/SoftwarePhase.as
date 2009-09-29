/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases {

    /**
     * <p>
     * Represent the phase making up of the project. Any phase can only belong to one project and has a non-negative length
     * in milliseconds. The phase also has the id, type and status attributes.
     * </p>
     * <p>
     * A phase could depend on a collection of other phases. The relationship between dependency and dependent could be
     * specified by the <code>Dependency</code> class, that dependent phase will start/end after the dependency phase
     * starts/ends with a lag time.
     * </p>
     * <p>
     * Phase could be attached a fixed start timestamp. Fixed start time is interpreted as "start no early than". It's the
     * earliest point when a phase can start.
     * </p>
     * <p>
     * Phase could also be attached scheduled start/end timestamps and actual start/end timestamps. Scheduled start/end
     * timestamps are the original plan for the project timeline. Actual start/end timestamps are available for the phase
     * that's already started/ended. They can override the start/end time calculated otherwise.
     * </p>
     * <p>
     * The phase start and end date could be calculated based on the dependencies and the timestamps.
     * </p>
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class SoftwarePhase extends AttributableObject {

        /**
         * A default empty constructor.
         */
        public function SoftwarePhase() {
        }

        /**
         * Represents the length of the phase in milliseconds.
         */
        public var length:Number;

        /**
         * Represents the phase id. The value could not be negative.
         */
        public var id:Number=0;

        /**
         * Represents the phase type. The value could be negative.
         */
        public var phaseType:SoftwarePhaseType=null;

        /**
         * Represents the phase status. The value could be negative.
         */
        public var phaseStatus:SoftwarePhaseStatus=null;

        /**
         * Scheduled start timestamp is the original plan for the project start timeline. The value could be negative.
         */
        public var fixedStartDate:Date=null;

        /**
         * Scheduled start timestamp is the original plan for the project start timeline. The value could be negative.
         */
        public var scheduledStartDate:Date=null;

        /**
         * Scheduled end timestamp is the original plan for the project end timeline. The value could be negative.
         */
        public var scheduledEndDate:Date=null;

        /**
         * Actual start timestamp is available for the phase that's already started. It can override the start time
         * calculated. The value could be negative.
         */
        public var actualStartDate:Date=null;

        /**
         * Actual end timestamp is available for the phase that's already ended. It can override the end time calculated. The value could be negative.
         */
        public var actualEndDate:Date=null;
    }
}