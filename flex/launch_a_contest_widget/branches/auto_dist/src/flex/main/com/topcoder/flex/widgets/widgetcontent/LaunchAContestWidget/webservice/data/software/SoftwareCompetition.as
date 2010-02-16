/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software {
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.CompetionType;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.Competition;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases.SoftwareProjectPhases;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project.SoftwareProjectData;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project.SoftwareProjectHeader;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.resource.SoftwareResource;

    /**
     * Software Competition DTO class.
     *
     * It stores assetDTO, projectHeader, projectHeaderReason, projectPhases, projectResources and projectData property.
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class SoftwareCompetition extends Competition {

        /**
        * A default empty constructor.
        */
        public function SoftwareCompetition() {
        }

        /**
        * A <code>SoftwareAssetDTO</code> dto.
        */
        public var assetDTO:SoftwareAssetDTO;
        
        public var projectData:SoftwareProjectData;

        /**
        * A <code>SoftwareProjectHeader</code> dto.
        */
        public var projectHeader:SoftwareProjectHeader;

        /**
        * A <code>SoftwareProjectHeader</code> dto.
        */
        public var developmentProjectHeader:SoftwareProjectHeader;

        /**
        * A <code>String</code> reason for project header update.
        */
        public var projectHeaderReason:String;

        /**
        * A <code>SoftwareProjectPhases</code> dto.
        */
        public var projectPhases:SoftwareProjectPhases;

        /**
        * An array of <code>SoftwareResource</code> dto.
        */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.resource.SoftwareResource")]
        public var projectResources:Array;
    }
}
