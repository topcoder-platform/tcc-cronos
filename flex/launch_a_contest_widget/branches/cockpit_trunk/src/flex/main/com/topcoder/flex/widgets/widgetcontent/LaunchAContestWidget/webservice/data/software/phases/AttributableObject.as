/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases {

    /**
     * The class to provide a extensible way for including custom attributes for both <code>Project</code> and
     * <code>Phase</code> classes. The attributes are stored in key-value pairs.
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class AttributableObject {

        /**
         * A default empty contructor.
         */
        public function AttributableObject() {
        }

        /**
         * An array of attributes.
         */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases.Attribute")]
        public var attributes:Array;

    }
}