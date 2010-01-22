/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project {

    /**
     * An auditable object.
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class AuditableObject {

        public function AuditableObject() {
        }

        /**
         * The date/time that the project was created.
         */
        public var creationTimestamp:Date=null;

        /**
         * The name of the user that was responsible for creating the project.
         */
        public var creationUser:String=null;

        /**
         * The date/time that the project was last modified.
         */
        public var modificationTimestamp:Date=null;

        /**
         * The name of the user that was responsible for the last modification to the project.
         */
        public var modificationUser:String=null;

    }
}