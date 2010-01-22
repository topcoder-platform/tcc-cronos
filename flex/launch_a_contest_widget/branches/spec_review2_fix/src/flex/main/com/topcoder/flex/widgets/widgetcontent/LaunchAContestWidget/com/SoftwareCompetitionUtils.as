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
     * Version 1.0.1 (Cockpit Software Contest Payments v1.0) Change Notes:
     *    - Added consolidated methods to add / get / check cost properties.
     *    - Added new cost property keys.
     * 
     * Version 1.0.2 (Cockpit Release Assembly 7 v1.0) Change Notes:
     *    - Added methods to calculate various costs from formulae.
     *
     * @author snow01
     * @version 1.0.2
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
        
        private var _idToCompetitionType:Dictionary=new Dictionary();

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
	    _idToCompetitionType[23]="CONCEPTUALIZATION";

            _competitionTypes.addItem("SPECIFICATION");
            _projectTypeIds["SPECIFICATION"]=2;
            _projectCategoryIds["SPECIFICATION"]=6;
	    _projectTypeNames["SPECIFICATION"]="Application";
	    _idToCompetitionType[6]="SPECIFICATION";

            _competitionTypes.addItem("ARCHITECTURE");
            _projectTypeIds["ARCHITECTURE"]=2;
            _projectCategoryIds["ARCHITECTURE"]=7;
	    _projectTypeNames["ARCHITECTURE"]="Application";
	    _idToCompetitionType[7]="ARCHITECTURE";

            _competitionTypes.addItem("DESIGN");
            _projectTypeIds["DESIGN"]=1;
            _projectCategoryIds["DESIGN"]=1;
	    _projectTypeNames["DESIGN"]="Component";
	    _idToCompetitionType[1]="DESIGN";

            _competitionTypes.addItem("DEVELOPMENT");
            _projectTypeIds["DEVELOPMENT"]=1;
            _projectCategoryIds["DEVELOPMENT"]=2;
	    _projectTypeNames["DEVELOPMENT"]="Component";
	    _idToCompetitionType[2]="DEVELOPMENT";

            _competitionTypes.addItem("ASSEMBLY");
            _projectTypeIds["ASSEMBLY"]=2;
            _projectCategoryIds["ASSEMBLY"]=14;
	    _projectTypeNames["ASSEMBLY"]="Application";
	    _idToCompetitionType[14]="ASSEMBLY";

            _competitionTypes.addItem("RIACOMPONENT");
            _projectTypeIds["RIACOMPONENT"]=2;
            _projectCategoryIds["RIACOMPONENT"]=25;
	    _projectTypeNames["RIACOMPONENT"]="Application";
	    _idToCompetitionType[25]="RIACOMPONENT";

            _competitionTypes.addItem("RIABUILD");
            _projectTypeIds["RIABUILD"]=2;
            _projectCategoryIds["RIABUILD"]=24;
	    _projectTypeNames["RIABUILD"]="Application";
	    _idToCompetitionType[24]="RIABUILD";

            _competitionTypes.addItem("UIPROTOTYPE");
            _projectTypeIds["UIPROTOTYPE"]=2;
            _projectCategoryIds["UIPROTOTYPE"]=19;
	    _projectTypeNames["UIPROTOTYPE"]="Application";
	    _idToCompetitionType[19]="UIPROTOTYPE";

            _competitionTypes.addItem("TESTSUITES");
            _projectTypeIds["TESTSUITES"]=2;
            _projectCategoryIds["TESTSUITES"]=13;
	    _projectTypeNames["TESTSUITES"]="Application";
	    _idToCompetitionType[13]="TESTSUITES";

	    _competitionTypes.addItem("TESTSCENARIOS");
            _projectTypeIds["TESTSCENARIOS"]=2;
            _projectCategoryIds["TESTSCENARIOS"]=26;
	    _projectTypeNames["TESTSCENARIOS"]="Application";
	    _idToCompetitionType[26]="TESTSCENARIOS";
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
            //projectPhases.startDate=new Date();

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

        public static var PROJECT_INFO_TYPE_PAYMENT_KEY:String="Payments";

        private static var PROJECT_INFO_TYPE_DR_FLAG_KEY:String="Digital Run Flag";
        private static var PROJECT_INFO_TYPE_DR_FLAG_VALUE:String="On";

        public static var PROJECT_INFO_TYPE_DR_POINTS_KEY:String="DR points";

        public static var PROJECT_INFO_TYPE_ADMIN_FEE_KEY:String="Admin Fee";
        public static var PROJECT_INFO_TYPE_SPEC_REVIEW_COST_KEY:String="Spec Review Cost";

        public static var PROJECT_INFO_TYPE_REVIEW_COST_KEY:String="Review Cost";
        
        /**
         * Confidentiality Type project property
         * 
         * @since 1.0.1
         */ 
        public static var PROJECT_INFO_TYPE_CONFIDENTIALITY_TYPE_KEY:String="Confidentiality Type";
        
        /**
         * Public Confidentiality Type.
         * 
         * @since 1.0.1
         */
        public static var CONFIDENTIALITY_TYPE_PUBLIC:String="public";
        
        /**
         * Standard CCA Confidentiality Type value.
         * 
         * @since 1.0.1
         */
        public static var CONFIDENTIALITY_TYPE_CCA:String="standard_cca";
        
        /**
         * Represents the billing project for the project.
         * 
         * @since Cockpit Release Assembly 3
         */ 
        public static var PROJECT_INFO_TYPE_BILLING_PROJECT_KEY:String="Billing Project";

	/**
         * Represents the first place cost for the project.
         * 
         * @since 1.0.1
         */
        public static var PROJECT_INFO_TYPE_FIRST_PLACE_COST_KEY:String = "First Place Cost";
        
        /**
         * Represents the second place cost for the project.
         * 
         * @since 1.0.1
         */
        public static var PROJECT_INFO_TYPE_SECOND_PLACE_COST_KEY:String = "Second Place Cost";
        
        /**
         * Represents the reliability bonus cost for the project.
         * 
         * @since 1.0.1
         */
        public static var PROJECT_INFO_TYPE_RELIABILITY_BONUS_COST_KEY:String = "Reliability Bonus Cost";
        
        /**
         * Represents the milestone bonus cost for the project.
         * 
         * @since 1.0.1
         */
        public static var PROJECT_INFO_TYPE_MILESTONE_BONUS_COST_KEY:String = "Milestone Bonus Cost";
        
        /**
         * Represents the cost level property key.
         * 
         * @since 1.0.1
         */
        public static var PROJECT_INFO_TYPE_COST_LEVEL_KEY:String = "Cost Level";
        
        /**
         * Represents the cost level A (current maps to low).
         * 
         * @since 1.0.1
         */
        public static var COST_LEVEL_A:String = "A";
        
        /**
         * Represents the cost level B (current maps to medium).
         * 
         * @since 1.0.1
         */
        public static var COST_LEVEL_B:String = "B";
        
        /**
         * Represents the cost level C (current maps to high).
         * 
         * @since 1.0.1
         */
        public static var COST_LEVEL_C:String = "C";
       

        /**
         * Represents the cost level M (current maps to manual).
         *
         * @since 1.0.1
         */
        public static var COST_LEVEL_M:String = "M";

        /**
         * Standard submission count
         * 
         * @since 1.0.2
         */ 
        public static const STANDARD_SUBMISSION_COUNT:Number=3;
        
        /**
         * Standard passed screening count
         * 
         * @since 1.0.2
         */
        public static const STANDARD_PASSED_SCREENING_COUNT:Number=3;

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
            entry.key=PROJECT_INFO_TYPE_STATUS_NOTIFICATION_KEY;
            entry.value=PROJECT_INFO_TYPE_STATUS_NOTIFICATION_VALUE;
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
         * Adds the billing project property to project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @param project the billing project property.
         * 
         * @since Cockpit Release Assembly 3.
         */
        public function addBillingProjectProp(softwareCompetition:SoftwareCompetition, project:String):void {
            for each (var e:MapEntry in softwareCompetition.projectHeader.properties) {
                if (e.key == PROJECT_INFO_TYPE_BILLING_PROJECT_KEY) {
                    e.value=project;
                    return;
                }
            }
            
            var entry:MapEntry=new MapEntry();

            entry.key=PROJECT_INFO_TYPE_BILLING_PROJECT_KEY;
            entry.value=project;

            softwareCompetition.projectHeader.properties.push(entry);
        }
        
        /**
         * Gets the billing project property from project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @return the billing project proeprty.
         * 
         * @since Cockpit Release Assembly 3.
         */
        public function getBillingProjectProp(softwareCompetition:SoftwareCompetition):String {
            for each (var e:MapEntry in softwareCompetition.projectHeader.properties) {
                if (e.key == PROJECT_INFO_TYPE_BILLING_PROJECT_KEY) {
                    return e.value;
                }
            }
            
            return "0";
        }
        
        /**
         * Adds the confidentiality type property to project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @param type the confidentiality type.
         * 
         * @since 1.0.1
         */
        public function addConfidentialityTypeProp(softwareCompetition:SoftwareCompetition, type:String):void {
            for each (var e:MapEntry in softwareCompetition.projectHeader.properties) {
                if (e.key == PROJECT_INFO_TYPE_CONFIDENTIALITY_TYPE_KEY) {
                    e.value=type;
                    return;
                }
            }
            
            var entry:MapEntry=new MapEntry();

            entry.key=PROJECT_INFO_TYPE_CONFIDENTIALITY_TYPE_KEY;
            entry.value=type;

            softwareCompetition.projectHeader.properties.push(entry);
        }
        
        /**
         * Gets the confidentiality type property from project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @return the confidentiality type value.
         * 
         * @since 1.0.1
         */
        public function getConfidentialityTypeProp(softwareCompetition:SoftwareCompetition):String {
            for each (var e:MapEntry in softwareCompetition.projectHeader.properties) {
                if (e.key == PROJECT_INFO_TYPE_CONFIDENTIALITY_TYPE_KEY) {
                    return e.value;
                }
            }
            
            // default to Public.
            return CONFIDENTIALITY_TYPE_PUBLIC;
        }

	/**
         * Adds the specified costName property cost to specified software competition.
         * 
         * @param softwareCompetition the software competition.
         * @param cost the cost value.
         * @param costName the cost property name.
         * @since 1.0.1
         */ 
        public function addCostProp(softwareCompetition:SoftwareCompetition, cost:Number, costName:String):void {
            for each (var e:MapEntry in softwareCompetition.projectHeader.properties) {
                if (e.key == costName) {
                    e.value=cost.toFixed(2);
                    return;
                }
            }
            
            var entry:MapEntry=new MapEntry();

            entry.key=costName;
            entry.value=cost.toFixed(2);

            softwareCompetition.projectHeader.properties.push(entry);
        }
        
        /**
         * Gets whether the specified software competition has specified costName property.
         * 
         * @param softwareCompetition the software competition.
         * @param costName the cost property name.
         * @return whether the specified software competition has specified costName property.
         * @since 1.0.1 
         */ 
        public function hasCostProp(softwareCompetition:SoftwareCompetition, costName:String):Boolean {
            for each (var e:MapEntry in softwareCompetition.projectHeader.properties) {
                if (e.key == costName) {
                    return true;
                }
            }
            
            return false;
        }
        
        /**
         * Gets the cost value from the specified software competition for specified costName property.
         * 
         * @param softwareCompetition the software competition.
         * @param costName the cost property name.
         * @return the cost value from the specified software competition for specified costName property.
         * @since 1.0.1 
         */ 
        public function getCostProp(softwareCompetition:SoftwareCompetition, costName:String):Number {
            for each (var e:MapEntry in softwareCompetition.projectHeader.properties) {
                if (e.key == costName) {
                    return new Number(e.value);
                }
            }
            
            return 0;
        }

	/**
         * Adds the cost level property to project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @param cost level the billing project property.
         * 
         * @since 1.0.1
         */
        public function addCostLevelProp(softwareCompetition:SoftwareCompetition, costLevel:String):void {
            for each (var e:MapEntry in softwareCompetition.projectHeader.properties) {
                if (e.key == PROJECT_INFO_TYPE_COST_LEVEL_KEY) {
                    e.value=costLevel;
                    return;
                }
            }
            
            var entry:MapEntry=new MapEntry();

            entry.key=PROJECT_INFO_TYPE_COST_LEVEL_KEY;
            entry.value=costLevel;

            softwareCompetition.projectHeader.properties.push(entry);
        }
        
        /**
         * Gets the cost level property from project header of the specified competition
         *
         * @param softwareCompetition specified competition
         * @return the cost level value.
         * 
         * @since 1.0.1
         */
        public function getCostLevelProp(softwareCompetition:SoftwareCompetition):String {
            for each (var e:MapEntry in softwareCompetition.projectHeader.properties) {
                if (e.key == PROJECT_INFO_TYPE_COST_LEVEL_KEY) {
                    return e.value;
                }
            }
            
            // by default return B (that is medium).
            return COST_LEVEL_B;
        }
        
        /**
         * Gets the end date for unclosed submission phase for specified competition.
         * 
         * @param softwareCompetition the software competition.
         * @since 1.0.1
         */
        public function getUnclosedSubmissionPhaseEndDate(softwareCompetition:SoftwareCompetition):Date {
            if (softwareCompetition.projectPhases && softwareCompetition.projectPhases.phases) {
                for each (var p:SoftwarePhase in softwareCompetition.projectPhases.phases) {
                    if (p.phaseType.name=="Submission" && p.phaseStatus.name != "Closed") {
                        return p.scheduledEndDate;
                    }     
                }
            }
            
            return null;
        }
        
        /**
         * Calculates the 2nd place prize from first place prize.
         * 
         * @param firstPlacePrize the first palce prize
         * @return the calculated value of 2nd place prize.
         * 
         * @since 1.0.2
         */
        public function calculateSecondPlacePrize(firstPlacePrize:Number):Number {
            var prize:Number=firstPlacePrize * 0.5;
            return prize;
        }
        
        /**
         * Calculates the reliability prize from first place prize, 2nd place prize and for specified competition category.
         * 
         * @param firstPlacePrize the first palce prize
         * @param secondPlacePrize the second place prize
         * @param categoryId the software competition category id
         * @return the calculated value of reliability prize.
         * 
         * @since 1.0.2
         */
        public function calculateReliabilityPrize(firstPlacePrize:Number, secondPlacePrize:Number, categoryId:Number, reliabilityBonusXML:XML):Number {
            if (!reliabilityBonusXML) {
                return 0;
            }
            
            var bonusPercentage:String=reliabilityBonusXML.projectCategory.(@category == categoryId.toFixed(0)).reliabilityAbove.(@reliability == ".95")[0];
            if (!bonusPercentage || bonusPercentage == "") {
                bonusPercentage=reliabilityBonusXML.projectCategory.(@category == "0").reliabilityAbove.(@reliability == ".95")[0];
            } 
            
            if (bonusPercentage && !isNaN(Number(bonusPercentage))) {
                return (firstPlacePrize + secondPlacePrize) * Number(bonusPercentage);
            } else {
                return 0;
            }
        }
        
        /**
         * Calculates the review prize from first place prize for specified competition category.
         * 
         * @param firstPlacePrize the first palce prize
         * @param categoryId the software competition category id
         * @return the calculated value of review prize.
         * 
         * @since 1.0.2
         */
        public function calculateReviewCost(firstPlacePrize:Number, categoryId:Number):Number {
            if (categoryId == _projectCategoryIds['DEVELOPMENT'] 
                    || categoryId == _projectCategoryIds['DESIGN']) {
                // calculate as per component reviewer calculator.
                return getComponentReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT);
            } else if (categoryId == _projectCategoryIds['ASSEMBLY']) {
                // calculate as per assembly reviewer calculator.
                return getApplicationReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT) * 1.5;                                
            } else if (categoryId == _projectCategoryIds['ARCHITECTURE']) {
                return getArchitectureReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT);
            } else if (categoryId == _projectCategoryIds['CONCEPTUALIZATION'] 
                            || categoryId == _projectCategoryIds['SPECIFICATION']
                            || categoryId == _projectCategoryIds['TESTSUITES']
                            || categoryId == _projectCategoryIds['TESTSCENARIOS']
                            || categoryId == _projectCategoryIds['RIACOMPONENT'] 
                            || categoryId == _projectCategoryIds['RIABUILD']
                            || categoryId == _projectCategoryIds['UIPROTOTYPE']) {
                // calculate as per application reviewer calculator logic.
                return getApplicationReviewCost(firstPlacePrize, STANDARD_SUBMISSION_COUNT, STANDARD_PASSED_SCREENING_COUNT);
            } else {
                return 0;
            }
            
            return firstPlacePrize;
        }
        
        /**
         * Calculates the component review cost
         * 
         * @param firstPlacePrize the first place prize.
         * @param submissionCount the count of submission.
         * @param passedScreeningCount the passed screening count.
         * @return the component review cost
         * 
         * @since 1.0.2
         */
        private function getComponentReviewCost(firstPlacePrize:Number, submissionCount:Number, passedScreeningCount:Number):Number {
            var prizePurse:Number=1.5 * firstPlacePrize;
            var initialPurse:Number=0.40 * prizePurse;
            var incrementalPurse:Number=0.15 * prizePurse;
            var screeningCost:Number=(0.86 * initialPurse + incrementalPurse * (submissionCount - 1)) * 0.10;
            var reviewCost:Number=(0.86 * initialPurse + incrementalPurse * (passedScreeningCount - 1)) * 0.9 / 3.0;
            var aggregationCost:Number=0.04 * initialPurse;
            var finalReviewerCost:Number=0.10 * initialPurse;
            
            return screeningCost + 3 * reviewCost + aggregationCost + finalReviewerCost;
        }
        
        /**
         * Calculates the application review cost
         * 
         * @param firstPlacePrize the first place prize.
         * @param submissionCount the count of submission.
         * @param passedScreeningCount the passed screening count.
         * @return the application review cost
         * 
         * @since 1.0.2
         */
        private function getApplicationReviewCost(firstPlacePrize:Number, submissionCount:Number, passedScreeningCount:Number):Number {
            var standardPrize:Number = 750;
            var calculatedBaseRate:Number=15 + (firstPlacePrize - standardPrize) * 0.01;
            var actualBaseRate:Number=calculatedBaseRate;
            var calculatedReviewCost:Number=26 * calculatedBaseRate;
            
            var screeningCost:Number=actualBaseRate * 0.5 * submissionCount;
            var reviewCost:Number=(Math.max(0, submissionCount + 1 - passedScreeningCount) * 1.5 + 2 * passedScreeningCount) * actualBaseRate;
            var aggregationCost:Number=2 * actualBaseRate * 0.25;
            var finalReviewerCost:Number=2 * actualBaseRate * 0.75;
            
            return screeningCost + 3 * reviewCost + aggregationCost + finalReviewerCost;
        }


        /**
         * Calculates the arch review cost
         * 
         * @param firstPlacePrize the first place prize.
         * @param submissionCount the count of submission.
         * @param passedScreeningCount the passed screening count.
         * @return the application review cost
         */
        private function getArchitectureReviewCost(firstPlacePrize:Number, submissionCount:Number, passedScreeningCount:Number):Number {

            var multiplier = 1.5;
            var standardPrize:Number = 750;
            var calculatedBaseRate:Number=15 + (firstPlacePrize - standardPrize) * 0.01;
            var actualBaseRate:Number=calculatedBaseRate;
            var calculatedReviewCost:Number=26 * calculatedBaseRate;
            
            var screeningCost:Number=actualBaseRate * 0.5 * submissionCount;
            var reviewCost:Number=(Math.max(0, submissionCount + 1 - passedScreeningCount) * 1.5 + 2 * passedScreeningCount) * actualBaseRate;
            var aggregationCost:Number=2 * actualBaseRate * 0.25;
            var finalReviewerCost:Number=2 * actualBaseRate * 0.75;
            
            return screeningCost * multiplier + 3 * reviewCost * multiplier + aggregationCost * multiplier + finalReviewerCost * multiplier;
        }
        
        /**
         * Calculates the dr point from first place prize, 2nd place prize, reliability prize.
         * 
         * @param firstPlacePrize the first palce prize
         * @param secondPlacePrize the second place prize
         * @param reliabilityPrize the reliability prize. 
         * @return the calculated value of dr point
         * 
         * @since 1.0.2
         */
        public function calculateDRPoint(firstPlacePrize:Number, secondPlacePrize:Number, reliabilityPrize:Number):Number {
            return (firstPlacePrize + secondPlacePrize + reliabilityPrize) * 0.25;
        }
        
        public function getCompetitionType(id:Number):String {
            return _idToCompetitionType[id];
        }
    }
}
