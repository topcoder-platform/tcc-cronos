/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project {

    /**
     * Maps to the 'FullProjectData' dto of backend-server.
     *
     * @author TCSDEVELOPER
     *
     * @since Cockpit Release Assembly 1 v1.0
     */
    public class SoftwareProjectData {

        /**
        * A default empty constructor.
        */
        public function SoftwareProjectData() {
        }

        /**
        * An array of <code>SoftwareProjectSaleData</code> dto.
        */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project.SoftwareProjectSaleData")]
        public var contestSales:Array;
    }
}