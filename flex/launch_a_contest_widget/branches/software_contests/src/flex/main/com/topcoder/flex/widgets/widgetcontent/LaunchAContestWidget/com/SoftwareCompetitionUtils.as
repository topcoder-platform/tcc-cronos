/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.com {
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.MapEntry;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.SoftwareCompetition;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases.SoftwarePhase;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases.SoftwarePhaseStatus;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases.SoftwarePhaseType;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.phases.SoftwareProjectPhases;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project.SoftwareProjectStatus;

    import flash.utils.Dictionary;

    import mx.collections.ArrayCollection;

    /**
     * A constant and utility class that contains various constants and utility required for software competition based operations.
     *
     * @author TCSDEVELOPER
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    public class SoftwareCompetitionUtils {
        /**
         * Singleton instance of this class.
         */
        private static var _instance:SoftwareCompetitionUtils;

        /**
         * String Array of competition types.
         */
        private var _competitionTypes:ArrayCollection=new ArrayCollection();

        /**
         * Map of competition type to project id constants.
         */
        private var _projectTypeIds:Dictionary=new Dictionary();

        /**
         * Map of competition type to project category id constants.
         */
        private var _projectCategoryIds:Dictionary=new Dictionary();

	/**
         * Map of competition type to project type name constants.
         */
        private var _projectTypeNames:Dictionary=new Dictionary();

        /**
         * Resource role identifier constant.
         */
        private var _resourceRoleId:Number=13;

        /**
         * Asset version id constant.
         */
        private var _assetVersionId:Number=1;

        /**
         * Asset version number constant.
         */
        private var _assetVersionNumber:Number=1;

        /**
         * Asset version text constant.
         */
        private var _assetVersionText:String="1.0";

        /**
         * Id of the dto which is unset.
         */
        private var _unsetId:Number=-1;

        /**
         * Resource role key.
         */
        private var _resourceRoleKey:String="2";

        /**
         * Constructor for this class.
         *
         * It initializes various constants and their maps.
         */
        public function SoftwareCompetitionUtils() {
            _competitionTypes.addItem("CONCEPTUALIZATION");
            _projectTypeIds["CONCEPTUALIZATION"]=2;
            _projectCategoryIds["CONCEPTUALIZATION"]=23;
	    _projectTypeNames["CONCEPTUALIZATION"]="Application";

            _competitionTypes.addItem("SPECIFICATION");
            _projectTypeIds["SPECIFICATION"]=2;
            _projectCategoryIds["SPECIFICATION"]=6;
	    _projectTypeNames["SPECIFICATION"]="Application";

            _competitionTypes.addItem("ARCHITECTURE");
            _projectTypeIds["ARCHITECTURE"]=2;
            _projectCategoryIds["ARCHITECTURE"]=7;
	    _projectTypeNames["ARCHITECTURE"]="Application";

            _competitionTypes.addItem("DESIGN");
            _projectTypeIds["DESIGN"]=1;
            _projectCategoryIds["DESIGN"]=1;
	    _projectTypeNames["DESIGN"]="Component";

            _competitionTypes.addItem("DEVELOPMENT");
            _projectTypeIds["DEVELOPMENT"]=1;
            _projectCategoryIds["DEVELOPMENT"]=2;
	    _projectTypeNames["DEVELOPMENT"]="Component";

            _competitionTypes.addItem("ASSEMBLY");
            _projectTypeIds["ASSEMBLY"]=2;
            _projectCategoryIds["ASSEMBLY"]=14;
	    _projectTypeNames["ASSEMBLY"]="Application";

            _competitionTypes.addItem("TESTING");
            _projectTypeIds["TESTING"]=2;
            _projectCategoryIds["TESTING"]=13;
	    _projectTypeNames["TESTING"]="Application";
        }

        /**
         * Gets the singleton instance of this class.
         *
         * @return a <code>SoftwareCompetitionUtils</code>
         */
        public static function instance():SoftwareCompetitionUtils {
            if (!_instance) {
                _instance=new SoftwareCompetitionUtils();
            }

            return _instance;
        }

        /**
         * Gets the project type id for the given contest type.
         *
         * @return a <code>Number</code>
         */
        public function getProjectTypeId(typeName:String):Number {
            return this._projectTypeIds[typeName] as Number;
        }

	/**
         * Gets the project type name for the given contest type.
         *
         * @return a <code>String</code>
         */
        public function getProjectTypeName(typeName:String):String {
            return this._projectTypeNames[typeName] as String;
        }


        /**
         * Gets the project category id for the given contest type.
         *
         * @return a <code>Number</code>
         */
        public function getProjectCategoryId(typeName:String):Number {
            return this._projectCategoryIds[typeName] as Number;
        }

        /**
         * Gets the resource role id constant.
         *
         * @return a <code>int</code>
         */
        public function getResourceRoleId():Number {
            return this._resourceRoleId;
        }

        /**
         * Gets the asset version id constant.
         *
         * @return a <code>Number</code>
         */
        public function getAssetVersionId():Number {
            return this._assetVersionId;
        }

        /**
         * Gets the asset version number constant.
         *
         * @return a <code>Number</code>
         */
        public function getAssetVersionNumber():Number {
            return this._assetVersionNumber;
        }

        /**
         * Gets the asset version text.
         *
         * @return a <code>String</code>
         */
        public function getAssetVersionText():String {
            return this._assetVersionText;
        }

        /**
         * Get default dummy / mock phases.
         *
         * @return a <code>SoftwareProjectPhases</code>
         */
        public function getDefaultPhases():SoftwareProjectPhases {
            var projectPhases:SoftwareProjectPhases=new SoftwareProjectPhases();
            projectPhases.startDate=new Date();

            return projectPhases;
        }

        /**
         * Gets default project status.
         *
         * @return a <code>SoftwareProjectStatus</code>
         */
        public function getDefaultProjectStatus():SoftwareProjectStatus {
            var status:SoftwareProjectStatus=new SoftwareProjectStatus();
            status.id=1;
            status.name="ACTIVE";

            return status;
        }

        /**
         * Gets the unset id - id of the dto which is unset.
         *
         * @return a <code>Number</code>
         */
        public function getUnsetId():Number {
            return this._unsetId;
        }

        /**
         * Gets the resource role property.
         */
        public function getResourceRoleProperty(userName:String):MapEntry {
            var entry:MapEntry=new MapEntry();
            entry.key=this._resourceRoleKey;
            entry.value=userName;

            return entry;
        }

        /**
         * Constants defining project info type ids and their default values (if any)
         * Names are self explanatory.
         */
        // CONSTANTS STARTS HERE
        private static var PROJECT_INFO_TYPE_VERSION_ID_KEY:String="Version ID";
        
        private static var PROJECT_INFO_TYPE_ROOT_CATALOG_ID_KEY:String="Root Catalog ID";

        private static var PROJECT_INFO_TYPE_NAME_KEY:String="Project Name";

        private static var PROJECT_INFO_TYPE_VERSION_KEY:String="Project Version";

        private static var PROJECT_INFO_TYPE_AUTOPILOT_OPTION_KEY:String="Autopilot Option";
        private static var PROJECT_INFO_TYPE_AUTOPILOT_OPTION_VALUE:String="Off";

        private static var PROJECT_INFO_TYPE_STATUS_NOTIFICATION_KEY:String="Status Notification";
        private static var PROJECT_INFO_TYPE_STATUS_NOTIFICATION_VALUE:String="On";

        private static var PROJECT_INFO_TYPE_TIMELINE_NOTIFICATION_KEY:String="Timeline Notification";
        private static var PROJECT_INFO_TYPE_TIMELINE_NOTIFICATION_VALUE:String="On";

        private static var PROJECT_INFO_TYPE_PUBLIC_KEY:String="Public";
        private static var PROJECT_INFO_TYPE_PUBLIC_VALUE:String="Yes";

        private static var PROJECT_INFO_TYPE_RATED_KEY:String="Rated";
        private static var PROJECT_INFO_TYPE_RATED_VALUE:String="Yes";

        private static var PROJECT_INFO_TYPE_ELIGIBILITY_KEY:String="Eligibility";
        private static var PROJECT_INFO_TYPE_ELIGIBILITY_VALUE:String="Open";

        private static var PROJECT_INFO_TYPE_PAYMENT_KEY:String="Payments";

        private static var PROJECT_INFO_TYPE_DR_FLAG_KEY:String="Digital Run Flag";
        private static var PROJECT_INFO_TYPE_DR_FLAG_VALUE:String="On";

        private static var PROJECT_INFO_TYPE_DR_POINTS_KEY:String="DR points";

        private static var PROJECT_INFO_TYPE_ADMIN_FEE_KEY:String="Admin Fee";

        // CONSTANTS ENDS HERE

        /**
         * Sets the default properties for the project header.
         */
        public function getProjectDefaultProperties():Array {
            /*
               ==================================================================
               DEFAULT / DYNAMIC --> SET WHERE --> PROJECT INFO TYPE ID --> VALUE
               ==================================================================
               DYNAMIC --> SERVER --> 2 (Component ID) --> value - this is the id from create asset in catalog services
               DYNAMIC --> SERVER --> 3 (Version ID) --> value - this from asset dto version

               DYNAMIC --> CLIENT --> 5 (Root Catalog ID) --> value - from asset root category in catalog services
               DYNAMIC --> CLIENT --> 6 (Project Name) --> value - contset name

               DEFAULT --> CLIENT --> 7 (Project Version) --> value - from asset dto version
               DEFAULT --> CLIENT --> 9 (Autopilot Option) --> value - 'On'
               DEFAULT --> CLIENT --> 10 (Status Nofitifcaion) --> value - 'On'
               DEFAULT --> CLIENT --> 11 (Timeline Nofification) --> value - 'On'
               DEFAULT --> CLIENT --> 12 (Public) --> value - 'Yes'
               DEFAULT --> CLIENT --> 13 (Rated) --> value - 'Yes'
               DEFAULT --> CLIENT --> 14 (Eligibility) --> value - 'Open'

               DYNAMIC --> CLIENT --> 16 (Payments) --> value - from user input (user puts 1st, we add 2nd 1/2 of 1st)

               DEFAULT --> CLIENT --> 26 (Digital Run Flag) --> value - 'On'

               DYNAMIC --> CLIENT --> 30 (DR points) --> value - calculated based on payment (20% of total payment)
               DYNAMIC --> CLIENT --> 31 (Admin Fee) --> value - 20% of total prize
             */
             
            var entry:MapEntry;
            var ret:Array=new Array();
            
            // 3
            entry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_VERSION_ID_KEY;
            entry.value=getAssetVersionNumber().toString();
            ret.push(entry);

            // 7
            entry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_VERSION_KEY;
            entry.value=getAssetVersionText().toString();
            ret.push(entry);

            // 9
            entry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_AUTOPILOT_OPTION_KEY;
            entry.value=PROJECT_INFO_TYPE_AUTOPILOT_OPTION_VALUE;
            ret.push(entry);

            // 10
            entry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_TIMELINE_NOTIFICATION_KEY;
            entry.value=PROJECT_INFO_TYPE_TIMELINE_NOTIFICATION_VALUE;
            ret.push(entry);

            // 11
            entry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_TIMELINE_NOTIFICATION_KEY;
            entry.value=PROJECT_INFO_TYPE_TIMELINE_NOTIFICATION_VALUE;
            ret.push(entry);

            // 12
            entry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_PUBLIC_KEY;
            entry.value=PROJECT_INFO_TYPE_PUBLIC_VALUE;
            ret.push(entry);

            // 13
            entry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_RATED_KEY;
            entry.value=PROJECT_INFO_TYPE_RATED_VALUE;
            ret.push(entry);

            // 14
            entry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_ELIGIBILITY_KEY;
            entry.value=PROJECT_INFO_TYPE_ELIGIBILITY_VALUE;
            ret.push(entry);

            // 26
            entry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_DR_FLAG_KEY;
            entry.value=PROJECT_INFO_TYPE_DR_FLAG_VALUE;
            ret.push(entry);

            return ret;
        }

        /**
         * Adds the root catalog id property to project header of the specified competition.
         *
         * @param softwareCompetition specified competition
         * @param rootCatalogId id of the root catalog.
         */
        public function addRootCatalogIdProp(softwareCompetition:SoftwareCompetition, rootCatalogId:Number):void {
            var entry:MapEntry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_ROOT_CATALOG_ID_KEY;
            entry.value=rootCatalogId.toString();

            softwareCompetition.projectHeader.properties.push(entry);
        }

        /**
         * Adds the project name property to project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @param projectName name of the project.
         */
        public function addProjectNameProp(softwareCompetition:SoftwareCompetition, projectName:String):void {
            var entry:MapEntry=new MapEntry();
            entry.key=PROJECT_INFO_TYPE_NAME_KEY;
            entry.value=projectName;

            softwareCompetition.projectHeader.properties.push(entry);
        }

        /**
         * Adds the dr points property to project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @param totalPrize total prize of the competition.
         */
        private function addDrPointsProp(softwareCompetition:SoftwareCompetition, totalPrize:Number):void {
            var entry:MapEntry=new MapEntry();

            var drPoints:Number=0.20 * totalPrize;

            entry.key=PROJECT_INFO_TYPE_DR_POINTS_KEY;
            entry.value=drPoints.toFixed(0);

            softwareCompetition.projectHeader.properties.push(entry);
        }

        /**
         * Adds the admin fee proeprty to project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @param totalPrize total prize of the competition.
         */
        private function addAdminFeeProp(softwareCompetition:SoftwareCompetition, totalPrize:Number):void {
            var entry:MapEntry=new MapEntry();

            var adminFee:Number=0.20 * totalPrize;

            entry.key=PROJECT_INFO_TYPE_ADMIN_FEE_KEY;
            entry.value=adminFee.toFixed(2);

            softwareCompetition.projectHeader.properties.push(entry);
        }

        /**
         * Adds the total payment property to project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @param totalPrize total prize of the competition.
         */
        private function addPaymentProp(softwareCompetition:SoftwareCompetition, totalPrize:Number):void {
            var entry:MapEntry=new MapEntry();

            entry.key=PROJECT_INFO_TYPE_PAYMENT_KEY;
            entry.value=totalPrize.toFixed(0);

            softwareCompetition.projectHeader.properties.push(entry);
        }

        /**
         * Adds the prize related properties to project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @param prizes array of prizes.
         */
        public function addPrizeProps(softwareCompetition:SoftwareCompetition, prizes:Array):void {
            var totalPrize:Number=0;
            for (var i:int=0; i < prizes.length; i++) {
                var p:Number=prizes[i] as Number;
                totalPrize+=p;
            }

            addPaymentProp(softwareCompetition, totalPrize);
            addAdminFeeProp(softwareCompetition, totalPrize);
            addDrPointsProp(softwareCompetition, totalPrize);
        }

    }
}
