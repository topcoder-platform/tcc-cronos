/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases {

    /**
     * <p>
     * This is the main class of this component, each application or component can be represented by a <code>Project</code>
     * instance which is composed of a collection of phases depending on each other.
     * </p>
     * <p>
     * For example, an application may be composed of design phase, design review phase, development phase, development
     * review phase, and deployment phase. The phases (except the first one) can only be started if the former one is
     * finished, each phase has a start date and length to finish, at the same time, the project has a start date so
     * that we can know when will the project be finished.
     * </p>
     * <p>
     * All the phases added to the project and dependent should belong to the same project.
     * </p>
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class SoftwareProjectPhases extends AttributableObject {

        /**
         * A default empty constructor.
         */
        public function SoftwareProjectPhases() {
        }

        /**
         * Represents a set of phases owned by this project.
         */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases.SoftwarePhase")]
        public var phases:Array=null;

        /**
         * Represents the start date of the project.
         */
        public var startDate:Date=null;

        /**
         * Represents the project id.
         */
        public var id:Number=0;
    }
}