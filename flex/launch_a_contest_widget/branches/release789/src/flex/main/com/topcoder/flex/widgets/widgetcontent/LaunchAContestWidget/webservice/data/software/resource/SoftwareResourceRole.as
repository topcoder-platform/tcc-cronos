/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.resource {

    /**
     * It represents a type of resource and is used to tag instances of the Resource
     * class as playing a certain role. The ResourceRole class is simply a container
     * for a few basic data fields.
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class SoftwareResourceRole {

        /**
        * A default empty constructor.
        */
        public function SoftwareResourceRole() {
        }

        /**
         * The id of the ResourceRole.
         */
        public var id:Number=0;

        /**
         * The name of the ResourceRole.
         */
        public var name:String=null;

        /**
         * The description of the ResourceRole.
         */
        public var description:String=null;

        /**
         * The identifier of the phase type for this ResourceRole.
         */
        public var phaseType:Number;
    }
}