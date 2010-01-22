/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software {
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.catalog.SoftwareCategory;

    import mx.controls.Label;

    /**
    * Software Asset DTO class.
    *
    * It stores contest's id, details (functional, detailed, short), root category, list of categories, technologies, and other details too.
    *
    * Version 1.1 (Cockpit Upload Attachment) Change Notes:
    *    - Class was cleaned up and resorted
    *
    * @author pulky
    *
    * @version 1.1
    * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
    */
    public class SoftwareAssetDTO {

        public function SoftwareAssetDTO() {
        }

        /**
        * Identifer of this asset.
        */
        public var id:Number;

        /**
        * Name of this asset.
        */
        public var name:String;

        /**
        * An array of <code>Number</code> client ids.
        */
        [ArrayElementType("Number")]
        public var clientIds:Array=new Array();

        /**
        * Version text of this asset.
        */
        public var versionText:String;

        /**
        * Version number of this asset.
        */
        public var versionNumber:Number;

        /**
        * Short description.
        */
        public var shortDescription:String;

        /**
        * Detailed description for this contest.
        */
        public var detailedDescription:String;

        /**
        * Function description.
        */
        public var functionalDescription:String;

        /**
        * Root category of type <code>SoftwareCategory</code> for this asset.
        */
        public var rootCategory:SoftwareCategory;

        /**
        * An array of <code>SoftwareCategory</code> dto.
        */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.catalog.SoftwareCategory")]
        public var categories:Array;

        /**
        * An array of <code>SoftwareTechnology</code>
        */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.catalog.SoftwareTechnology")]
        public var technologies:Array;

        /**
        * Production date of this asset.
        */
        public var productionDate:String;

        /**
        * Determines whether current version of the asset is also the latest for the contest.
        */
        public var isCurrentVersionAlsoLatestVersion:Boolean;

        /**
        * Version id of this asset.
        */
        public var compVersionId:Number;

        /**
        * An array of <code>Number</code> user identifiers.
        */
        [ArrayElementType("Number")]
        public var userIds:Array=new Array();

        /**
        * True if information of this asset is complete, else false.
        */
        public var informationComplete:Boolean;

        /**
        * An array of <code>SoftwareCompDocumentation</code>
        */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.catalog.CompDocumentation")] // BUGR-1600
        public var documentation:Array;

        /**
        * @since BUGR-1600
        */
        [ArrayElementType("com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.catalog.CompUploadedFile")]
        public var compUploadedFiles:Array;

        /**
        * Detailed comment for this contest.
        */
        public var compComments:String;

        /**
        * Phase name of this asset.
        */
        public var phase:String;

        /**
        * An array of <code>Number</code>
        */
        [ArrayElementType("Number")]
        public var dependencies:Array;


        /**
        * A <code>SoftwareCompForum</code>
        */
        //public var forum:SoftwareCompForum;


        /**
        * A <code>SoftwareCompLink</code> dto.
        */
        //public var link:SoftwareCompLink;




    }
}
