/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.resource {

    /**
     * The AuditableResourceStructure is the base class for the modeling classes in
     * this component. It holds the information about when the structure was created
     * and updated. This class simply holds the four data fields needed for this
     * auditing information
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class AuditableResourceStructure {

        /**
        * A default empty constructor.
        */
        public function AuditableResourceStructure() {
        }

        /**
         * The date/time that the resource structure was created.
         */
        public var creationTimestamp:Date=null;

        /**
         * The name of the user that was responsible for creating the
         * resource structure.
         */
        public var creationUser:String=null;

        /**
         * The date/time that the resource structure was last
         * modified.
         */
        public var modificationTimestamp:Date=null;

        /**
         * The name of the user that was responsible for the last
         * modification to the resource structure.
         */
        public var modificationUser:String=null;

    }
}