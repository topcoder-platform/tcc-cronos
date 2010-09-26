/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases {

    /**
     * <p>
     * This class represents the phase status. A phase status consists of a numeric identifier and a name.
     * </p>
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class SoftwarePhaseStatus {

        /**
         * A default empty constructor.
         */
        public function SoftwarePhaseStatus() {
        }

        /**
         * Represents the phase status id. The value could not be negative.
         */
        public var id:Number=0;

        /**
         * Represents the phase status name. The value could not be null.
         */
        public var name:String=null;

    }
}