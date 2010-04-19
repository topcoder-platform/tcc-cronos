/*
 * Copyright (c) 2008-2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget {
    import com.topcoder.flex.util.date.DateUtil;
    import com.topcoder.flex.Helper;
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.util.progress.ProgressWindowManager;
    import com.topcoder.flex.widgets.model.IWidget;
    import com.topcoder.flex.widgets.model.IWidgetContainer;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.com.SoftwareCompetitionUtils;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.qs.Model;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.utils.ObjectTranslatorUtils;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.CompetionType;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.CompetitionPrize;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.ContestPaymentData;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.CreditCardPaymentData;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.PaymentType;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.PrizeData;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.StudioCompetition;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.TcPurhcaseOrderPaymentData;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.SoftwareCompetition;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.catalog.SoftwareCategory;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.catalog.SoftwareTechnology;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project.SoftwareProjectCategory;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project.SoftwareProjectHeader;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.software.project.SoftwareProjectSaleData;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.specreview.SpecReview;
    
    import flash.display.DisplayObject;
    import flash.display.DisplayObjectContainer;
    import flash.net.URLRequest;
    import flash.net.navigateToURL;
    import flash.utils.Dictionary;
    import flash.utils.ByteArray;
    
    import mx.collections.ArrayCollection;
    import mx.collections.Sort;
    import mx.containers.VBox;
    import mx.core.Application;
    import mx.events.FlexEvent;
    import mx.rpc.AbstractOperation;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.soap.mxml.WebService;
    import mx.rpc.xml.SchemaTypeRegistry;
    import mx.utils.ObjectUtil;
    import mx.events.CloseEvent;
    import mx.controls.Alert;

    /**
     * <p>
     * This is the code behind script part for the launch a contest widget. It implements the IWidget interface.
     * It extends from the Panel.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     * 
     * Version 1.0.1 (Cockpit Release Assembly 5 v1.0) Change Notes:
     *    - Added field to represent whether current contest is invoiced project or not and to hold invoiced project id.
     * 
     * 
     * Version 1.0.2 (Spec Reviews Finishing Touches v1.0) Change Notes:
     *    - changed the type of specReviews to SpecReview from ArrayCollection.
     *    - corresponding update in webservice handler method.
     * 
     * Version 1.0.3 (Cockpit Release Assembly 6) Change Notes:
     *    - introduced new boolean property 'enforceCCA' to capture whether CCA is enforced or not.
     *    - the property is preserved to contest properties during contest save.
     *    - during edit the property is read from contest properties. 
     *
     * Version 1.0.4 (Cockpit Release Assembly 4 v1.0) Change Notes:
     *    - Introduced methods to check whether the contest is a paid contest or not.
     *    - henceforth updated for BUGR-1983
     *
     * Version 1.0.5 (Cockpit Pipeline Release Assembly 2 - Capacity) Change notes:
     *    - Added web service support for pipeline service facade bean
     *
     * Version 1.0.6 (Cockpit Software Contest Payments v1.0) Change Notes:
     *    - Removed redundant calls for property add.
     *    - Used the new api for getting various costs.
     * 
     * Version 1.0.7 (Cockpit Release Assembly 7 v1.0) Change Notes:
     *    - added field to represent whether to use manualPrizeSetting. 
     *
     * Version 1.0.8 (Cockpit Release Assembly 8 v1.0) Change Notes:
     *    
     * Version 1.0.9 (Cockpit Release Assembly 11 v1.0) Change Notes:
     *    - Create corresponding develop competition on request.

     * Version 1.0.10 (Cockpit Contest Eligibility) Change Notes:
     *    - handle the creation of private contest
     *
     * Version 1.0.11 (Cockpit Upload Attachment) Change Notes:
     *    - Added attachment page refresh call after software contest is created or updated
     *
     * Version 1.0.12 (Cockpit Spec Review) change Notes:
     *    - Will not retrieve the getSpecReviews for software contests now.
     *
     * @author snow01, pulky
     * @version 1.0.12
     * @since 1.0
     */
     public class LaunchWidgetCodeBehind extends VBox implements IWidget {
        /**
         * The name of the widget.
         */
        private var _name:String = "Launch Contest";


        private const CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED:Number = 1;
         private const CONTEST_STATUS_ACTIVE_PUBLIC:Number = 2 ;
         private const CONTEST_DETAILED_STATUS_DRAFT:Number =15 ;
         private const CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC:Number =2 ;
         private const CONTEST_DETAILED_STATUS_SCHEDULED:Number =9 ;

        // Project Service Facade.
        public var _pws:WebService;
        
        // Contest Service Facade.
        public var _csws:WebService;

        /**
         * Pipeline Service Facade web service
         *
         * @since 1.0.2
         */
        public var _psws:WebService;

        // schema type registry.
        public var _launchContestSchemaTypeRegistry:SchemaTypeRegistry;
        
        public var contestid:String=null;
        
    	/**
    	 * The framework of the widget.
    	 */
    	private var _framework:IWidgetFramework = null;
    
    	/**
    	 * The container for this widget.
    	 */
    	private var _container:IWidgetContainer;
    
    	/**
    	 * The allowclose flag.
    	 */
    	private var _allowclose:Boolean=true;
    	
    	private var _maximized:Boolean=false;
    	
    	[Bindable] private var _emptyStart:Boolean = true;
        
        /**
         * The data for the widget.
         */
        [Bindable] private var _result:XML = <competition><contestData></contestData></competition>;
        
        [Bindable] private var _competition:StudioCompetition;
        
        private var _configurableText:XML;
        
        [Bindable]
        public var clientProjectNames:ArrayCollection = new ArrayCollection();
        
        /**
         * Variable that holds 'software competition' related data.
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        [Bindable]
        private var _softwareCompetition:SoftwareCompetition;

        /**
         * Variable that holds competition
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        [Bindable]
        private var _competitionType:String;

        /**
         * Placed here instead of LaunchWidget
         * @since BUGR-1737
         */
        [Bindable]
        protected var _isAdmin:Boolean;
        
        private var alert:Alert;
            
        /**
        * @since BUGR-1737
        */     
        [Bindable]
        public function get isAdmin():Boolean {
        	return _isAdmin;
        }    
        public function set isAdmin(value:Boolean):void {
        	_isAdmin = value;
        }    

        /**
         * If user is software admin
         * @since BUGR-1737
         */
        [Bindable]
        protected var _isSoftwareAdmin:Boolean;
        
        [Bindable]
        private var _tcDirectProjectId:String;

        [Bindable]
        private var _tcDirectProjectName:String=""; // BUGR-1757

        /**
        * Current user id.
        */
	    [Bindable]
        private var _userid:Number=Application.application.parameters.userid;
        
        public var initWidgetCallbackFn:Function=null;
        
        public var isEditMode:Boolean=false;
        
        /**
         * Indicates if this launch widget is to be operated in read only mode or normal editable mode.
         * In read only mode following things happen
         *    - user can only view the contest information.
         *    - user can not update any thing.
         *    - next button that goes to payment screen would also not be shown.
         * 
         * @since Cockpit Release Assembly 3 [RS: 1.1.2]
         */  
        [Bindable]
        public var isReadOnlyMode:Boolean=false;

         /**
         * Represents the spec reviews for the current contest.
         * 
         * Updated for Version 1.0.1
         *    - changed the type to SpecReview from ArrayCollection. 
         *  
         * since Cockpit Launch Contest - Inline Spec Reviews Part 2
         */ 
        [Bindable]
        public var specReviews:SpecReview=null;
        
        public var contestCreateUser:String=null;
        
        /**
         * Represents whether the current contest is invoiced or not.
         * 
         * @since 1.0.1
         */
        [Bindable]
        private var _isInvoicedContest:Boolean=false;
        
        /**
         * Represents the project id of the invoicing project.
         * 
         * @since 1.0.1
         */
        [Bindable] 
        private var _invoicedProjectId:Number=0;
        
        /**
         * Represents the data of the invoicing project.
         * 
         * @since 1.0.1
         */
        [Bindable] 
        private var _invoicedProjectData:Object=null;
        
        /**
         * Captures whether enforce cca is on or not.
         * 
         * @since 1.0.3
         */ 
        [Bindable]
        public var enforcedCCA:Boolean=false;

	 
        /**
         * Represents whether to allow manual prize setting or not.
         * 
         * Default value is false - that is manual prize setting is not allowed.
         * 
         * @since 1.0.4 
         */
        [Bindable]
        private var _manualPrizeSetting:Boolean=false;
        
        public var studioContestSubTypeId:Number=-1;
        
        [Bindable]
        public var studioContestSubTypeLabel:String="";
        
        //
        // Stores master technology list as retrieved from web-service.
        // This is loaded only one time.
        // @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
        //
        public static var masterTechnologies:ArrayCollection;

        //
        // Stores list of categories as retrieved from web-service.
        // This is loaded one time.
        // @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
        //
        public static var categories:ArrayCollection;

        //
        // Stores catalogs / root category.
        // @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
        //
        [Bindable]
        public static var catalogs:ArrayCollection;
	
        //The number respresents one hour
        //@since 1.0.9
        private static const OneHour:Number=1000 * 60 * 60;
        //
        // Map of catalog id to categories list.
        // @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
        //
        public static var catalogToCategoriesMap:Dictionary=new Dictionary();
        
        /**
         * Stores the reference to application catalog.
         * This is the default catalog for non design & non development contest.
         * 
         * @since Cockpit Release Assembly 1 v1.0 [BUGR-1850]
         */ 
        public static var applicationCatalog:SoftwareCategory = null;
        
        /**
         * Stores the reference to 'Business Layer' category.
         * This is the default category for non design & non development contest.
         * 
         * @since Cockpit Release Assembly 1 v1.0 [BUGR-1850]
         */
        public static var businessLayerApplicationCategory:SoftwareCategory = null;
        
        /**
         * Stores the reference to not set catalog.
         * This is the default catalog for design & development contest.
         */ 
        public static var notSetCatalog:SoftwareCategory = null;
        
        /**
         * Stores the reference to 'Not Set' category.
         * This is the default category for design & development contest.
         */
        public static var notSetCategory:SoftwareCategory = null;
        
        public var detailsConfig:XML=null;
        
        private var bolckSaveAsDraft:Boolean = false;
        
        /**
        * @since BUGR-1737
        */     
        public function get isSoftwareAdmin():Boolean {
        	return _isSoftwareAdmin;
        }  
                    
        /**
         * ProjectWidgetCodeBehind constructor.
         */
        public function LaunchWidgetCodeBehind() {
            super();
           
        }
        
        /**
         * goBack is intended to act as a "Back Button" only for the context of
         * the single widget.
         *
         * <p>For example, I may have 5 widgets open on my page. One of those
         * widgets contains a wizard style interface. Suppose I'm on screen 2 out
         * of 4 screens in the wizard. The "Back" button in the widget would take
         * me back to the previous screen of the widget. Essentially, this acts
         * like a browser's "Back" button but it is specific to each widget.</p>
         */
        public function goBack():void {
            
        }
        
        [Bindable]public function get configurableText():XML {
            return this._configurableText;
        }
        
        public function set configurableText(value:XML):void {
            this._configurableText = value;
        }
        
        /**
         * The active contents.
         */
        [Bindable]public function get result():XML {
            return this._result;
        }
        /**
         * Sets the data for the widget.
         */
        public function set result(value:XML):void {
            this._result = value;
        }
        
        [Bindable]public function get emptyStart():Boolean {
            return this._emptyStart;
        }
        
        public function set emptyStart(value:Boolean):void {
            this._emptyStart = value;
        }
        
        /**
         * The active contents.
         */
        [Bindable]public function get competition():StudioCompetition {
            return this._competition;
        }
        /**
         * Sets the data for the widget.
         */
        public function set competition(comp:StudioCompetition):void {
            this._competition = comp;
        }

        /**
         * Gets the software competition data.
         *
         * @return <code>SoftwareCompetition</code>
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        [Bindable]
        public function get softwareCompetition():SoftwareCompetition {
            return this._softwareCompetition;
        }

        /**
         * Sets the software competition data.
         *
         * @param comp a <code>SoftwareCompetition</code>
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        public function set softwareCompetition(comp:SoftwareCompetition):void {
            this._softwareCompetition=comp;
        }

        /**
         * Gets the competition type.
         *
         * @return <code>String</code>
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        [Bindable]
        public function get competitionType():String {
            return this._competitionType;
        }

        /**
         * Sets the competition type.
         *
         * @param a <code>String</code> competition type.
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        public function set competitionType(s:String):void {
            this._competitionType=s;
        }
        
        public function resetWidget(isEmptyStart:Boolean):void {
            reloadInternal(isEmptyStart,null);
        }

        /**
         * This action will reload this widget.
         */
        public function reload():void {
            reloadInternal(true,null);
        }
        
        /**
         * Reloads the widget data.
         *
         * <p>
         * Updated for Cockpit Release Assembly 3 [RS: 1.1.2]
         *    - updated to consider read only mode indicator from my project widget.
         *    - in read only mode, we disable mouse/tab actions on the main panel.
         * </p>
         *
         * @param isEmptyStart true if it is new contest start else if edit contest start then false.
         * @param map the various key-value parameters that usually gets passed during edit from another widget (My Project Widget)
         */
        private function reloadInternal(isEmptyStart:Boolean, map:Dictionary):void {
            if (!container.contents.isMaximized()) {
                container.startMaximize();
            }

            var admin:Boolean=this.isAdmin;
            var f:IWidgetFramework=widgetFramework;
            container.contents=new LaunchWidget();
            // Now with changes in WidgetContainer, widget.parent != container.
            // so this need to be set.
            (container.contents as LaunchWidget).container=container;
            container.contents.widgetFramework=f;
            container.contents.name=name;
            (container.contents as LaunchWidget).isAdmin=admin;

        if (map) {
                    (container.contents as LaunchWidget).isEditMode=true;
                    (container.contents as LaunchWidget).contestid=map["contestid"];
                    //(container.contents as LaunchWidget).competitionType=map["contestType"].toLocaleUpperCase();
                    (container.contents as LaunchWidget).tcDirectProjectId=map["projectid"];
                    (container.contents as LaunchWidget).tcDirectProjectName=map["projectName"];
                    (container.contents as LaunchWidget).studioContestType=(map["contestType"].toLocaleUpperCase()=="STUDIO");
                    if ((container.contents as LaunchWidget).studioContestType) {
                        (container.contents as LaunchWidget).competitionType="STUDIO";
                    }
            }
            
            (container.contents as LaunchWidget).initWidgetCallbackFn=function():void {
                var isReviewScreen:Boolean=false;
                if (map) {
                    (container.contents as LaunchWidget).isEditMode=true;
                    (container.contents as LaunchWidget).contestid=map["contestid"];
                    //(container.contents as LaunchWidget).competitionType=map["contestType"].toLocaleUpperCase();
                    (container.contents as LaunchWidget).tcDirectProjectId=map["projectid"];
                    (container.contents as LaunchWidget).tcDirectProjectName=map["projectName"];
                    (container.contents as LaunchWidget).studioContestType=(map["contestType"].toLocaleUpperCase()=="STUDIO");
                    //(container.contents as LaunchWidget).studioContestType=(container.contents as LaunchWidget).competitionType=="STUDIO";
                    if ((container.contents as LaunchWidget).studioContestType) {
                        (container.contents as LaunchWidget).competitionType="STUDIO";
                    }
                    
                    var editMode:String=map["mode"];
                    
                    trace("Edit mode: " + editMode);
                    
                    if (editMode && editMode == "READ") {
                        (container.contents as LaunchWidget).isReadOnlyMode=true;
                    } else {
                        (container.contents as LaunchWidget).isReadOnlyMode=false;
                    }
                    
                    isReviewScreen=(map["screen"]=="Review");
                    
                    if ((container.contents as LaunchWidget).isReadOnlyMode && !isReviewScreen) {
                        (container.contents as LaunchWidget).mainPanel.mouseEnabled=false;
                        (container.contents as LaunchWidget).mainPanel.mouseChildren=false;
                        (container.contents as LaunchWidget).mainPanel.tabEnabled=false;
                        (container.contents as LaunchWidget).mainPanel.tabChildren=false;
                    } else {
                        (container.contents as LaunchWidget).mainPanel.mouseEnabled=true;
                        (container.contents as LaunchWidget).mainPanel.mouseChildren=true;
                        (container.contents as LaunchWidget).mainPanel.tabEnabled=true;
                        (container.contents as LaunchWidget).mainPanel.tabChildren=true;
                    }
                    
                    (container.contents as LaunchWidget).contestSelect.initData();
                }
                
                trace("IN RELOAD OF LAUNCH WIDGET:: competitionType: " + (container.contents as LaunchWidget).competitionType);
                
                if (!isEmptyStart) {
                    if (isReviewScreen) {
                        (container.contents as LaunchWidget).openReviewPage();
                    } else {
                        trace("IN RELOAD OF LAUNCH WIDGET:: OPEN OVERVIEW PAGE");
                        (container.contents as LaunchWidget).openOverViewPage();
                    }
                }
                
                container.startRestore();
            }
        }
        
        /**
         * This action will show the user the configuration xml for this widget.
         */
        public function showConfiguration():void {
            
        }

        /**
         * This action signals that the widget should show the user help
         * information.
         */
        public function showHelp():void {
            
        }

        /**
         * This action will close this widget (i.e. most probably remove it
         * completely).
         */
        public function close():void {
            
        }

        /**
         * This action will minimize this widget.
         */
        public function minimize():void {
            
        }

        /**
         * This action will restpre this widget (for example from a menu bar).
         */
        public function restore():void {
            _maximized=false;
        }

        /**
         * This action will maximize this widget.
         */
        public function maximize():void {
            this.percentWidth=100;
            _maximized=true;
        }

        /**
         * Set a specific attribute value for the given key.
         *
         * @param attributeKey the key for the attribute. Cannot be null.
         * @param value the value for the specified key. Cannot be null.
         *
         * @throws ArgumentError if either input is null.
         */
        public function setAttributeValue(attributeKey:String, value:Object):void {         
        }

        /**
         * Get a specific attribute value for the given key. If not found,
         * returns null.
         *
         * @param attributeKey the key for the attribute. Cannot be null.
         *
         * @return the value for this key, or null if there is no mapping.
         *
         * @throws ArgumentError if the input is null.
         */
        public function getAttributeValue(attributeKey:String):Object {
            return null;
        }

        /**
         * Set the configuration for this widget based on the given xml.
         *
         * @param config the xml configuration data for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function configure(config:XML):void {            
        }

        /**
         * This will tell the caller if the widget is in minimzie mode or not.
         *
         * @return true if the widget is minimized, false otherwise.
         */
        public function isMinimized():Boolean {
            return false;
        }

        /**
         * This will tell the caller if the widget is in maximize mode or not.
         *
         * @return true if the widget is maximized, false otherwise.
         */
        public function isMaximized():Boolean {
            return _maximized;
        }

        /**
         * Get this widget's configuration xml data.
         *
         * @return the xml configuration data for this widget.
         */
        public function getConfiguration():XML {
            return null;
        }

        /**
         * Simple setter for the name of this widget.
         *
         * @param name the name of this widget.
         */
        override public function set name(name:String):void {
            this._name = name;
        }

        /**
         * Simple getter for the name of this widget.
         *
         * @return the current set name fo this widget. Could be null if not set.
         */
        override public function get name():String {
            return _name;
        }

	 /**
         * Simple setter for the framework of this widget.
         *
         * @param framework the framework of this widget.
         */
        public function set widgetFramework(framework:IWidgetFramework):void {
            this._framework = framework;
        }

        /**
         * Simple getter for the framework of this widget.
         *
         * @return the current set framework fo this widget. Could be null if not set.
         */
        public function get widgetFramework():IWidgetFramework {
        	return _framework;
        }
        
        /**
         * Set the attributes for this widget based on the given Map.
         *
         * Updated for Cockpit Release Assembly 1 v1.0
         *    - gets the contest type attribute.
         *    - based on contest type it determines which version to load - software or studio.
         * 
         * Updated for Cockpit Launch Contest - Inline Spec Reviews Part 2
         *    - we fetch the spec reviews during project edit.
         *
         * @param map the attributes for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function setAttributes(map:Dictionary):void {
            if (map["contestid"]) {
                contestid=map["contestid"];
                var contestType:String=map["contestType"];
                trace("@@@@ To get contest for: " + contestid + ", type: " + contestType);
                
                //_emptyStart=false;
                reloadInternal(false,map);
                
                if (contestType.toLocaleLowerCase() == "studio") {
                    var getStudioContestOp:AbstractOperation=_csws.getOperation("getContest");
                    getStudioContestOp.addEventListener("result", getStudioContestHandler);
                    getStudioContestOp.send(parseInt(contestid));
                } else {
                    var getSWContestOp:AbstractOperation=_csws.getOperation("getSoftwareContestByProjectId");
                    getSWContestOp.addEventListener("result", getSoftwareContestHandler);
                    getSWContestOp.send(parseInt(contestid));
                }
                
                //
                // we also fetch the review specs.
                //
                // since Cockpit Launch Contest - Inline Spec Reviews Part 2
                //
                // for Cockpit Spec Review - Stage 2, remove the 'review/comment' function in this widget
                // for software contest, so only retrieve the spec reviews for studio
                if (contestType.toLocaleLowerCase() == "studio") {
                    var getSpecReviewsOp:AbstractOperation = _csws.getOperation("getSpecReviews");
                    getSpecReviewsOp.addEventListener("result", getSpecReviewsHandler);
                    getSpecReviewsOp.send(parseInt(contestid), true);
                }
                showLoadingProgress();
            }
        }
        
        /**
         * Handles the webservice fetch of list of spec reviews. 
         * 
         * Updated for Version 1.0.1
         *    - Changed the type of specReviews to SpecReview from earlier ArrayCollection.
         * 
         * @since Cockpit Launch Contest - Inline Spec Reviews Part 2
         */
        private function getSpecReviewsHandler(e:ResultEvent):void {
            hideLoadingProgress();
            //trace("getSpecReviewsHandler: " + e + ", " + e.result);
            if (e && e.result) {
                var srs:SpecReview=ObjectTranslatorUtils.translate(e.result, SpecReview) as SpecReview;
                trace("getSpecReviewsHandler:: srs: " + srs);
                (container.contents as LaunchWidget).specReviews=srs;
            }
        }
        
        /**
         * Updated for Cockpit Release Assembly 1 v1.0 [BUGR-1847]
         * Name changed from getContestHandler to getStudioContestHandler, as the purpose.
         */
        private function getStudioContestHandler(e:ResultEvent):void {
            hideLoadingProgress();
            trace("getStudioContestHandler: " + e + ", " + e.result);
            if (e && e.result) {
                var c:StudioCompetition=ObjectTranslatorUtils.translate(e.result, StudioCompetition) as StudioCompetition;
                trace("getStudioContestHandler:: c: " + c);
                (container.contents as LaunchWidget).softwareCompetition=null;
                (container.contents as LaunchWidget).competition=c;
                (container.contents as LaunchWidget).contestCreateUser=c.creatorUserId.toString();
                (container.contents as LaunchWidget).competitionType="STUDIO";
                (container.contents as LaunchWidget).studioContestType=true;
                (container.contents as LaunchWidget).onCreateComplete(2);
                (container.contents as LaunchWidget).contestSelect.selectBillingProject(c.contestData.billingProject);

                (container.contents as LaunchWidget).tcDirectProjectId = c.contestData.tcDirectProjectId.toString();
                
                (container.contents as LaunchWidget).contestSelect.selectTCProject((container.contents as LaunchWidget).tcDirectProjectId);
            }
        }
        
        /**
         * Handler for s/w competition retrieval.
         * 
         * Updated for 1.0.3 to set the enforceCCA property during edit.
         * 
         * @since Cockpit Release Assembly 1 v1.0 [BUGR-1847]
         */
        private function getSoftwareContestHandler(e:ResultEvent):void {
            hideLoadingProgress();
            trace("getSoftwareContestHandler: " + e + ", " + e.result);
            if (e && e.result) {
                var c:SoftwareCompetition=ObjectTranslatorUtils.translate(e.result, SoftwareCompetition) as SoftwareCompetition;
                trace("getSoftwareContestHandler:: c: " + c);
                (container.contents as LaunchWidget).competition=null;
                (container.contents as LaunchWidget).softwareCompetition=c;
                (container.contents as LaunchWidget).competitionType=SoftwareCompetitionUtils.instance().getCompetitionType(
                    c.projectHeader.projectCategory.id);
                trace("Project Category Id: " + c.projectHeader.projectCategory.id + ", competitionType: " + (container.contents as LaunchWidget).competitionType);
                (container.contents as LaunchWidget).studioContestType=false;
                (container.contents as LaunchWidget).contestCreateUser=c.projectHeader.creationUser;
                (container.contents as LaunchWidget).onCreateComplete(2);
                var billingAccount:String=SoftwareCompetitionUtils.instance().getBillingProjectProp(c);
    	        if (billingAccount) {
    	            (container.contents as LaunchWidget).contestSelect.selectBillingProject(new Number(billingAccount));
    	        }

                (container.contents as LaunchWidget).tcDirectProjectId = c.projectHeader.tcDirectProjectId.toString();
                (container.contents as LaunchWidget).contestSelect.selectTCProject((container.contents as LaunchWidget).tcDirectProjectId);
                (container.contents as LaunchWidget).enforcedCCA=!(SoftwareCompetitionUtils.instance().getConfidentialityTypeProp(c)==SoftwareCompetitionUtils.CONFIDENTIALITY_TYPE_PUBLIC);
            }
        }
        
        private var _type:String;
        private var _eventHandler:Function;
        private var _faultEventHandler:Function;

        private function handleSubmitPurchase(event:CloseEvent):void {
            if (event && event.detail) {
                if (event.detail == Alert.YES) {
                    addDevelopmentData();
                }
            }
            _submitPurchase(_type, _eventHandler, _faultEventHandler);
        }

        /**
         * Updated for Cockpit Release Assembly 3
         *    - Added the billing project property.
         */ 
        public function submitPurchase(type:String, eventHandler:Function, faultEventHandler:Function):void {
            if (this.competitionType == "DESIGN" &&
                (isNaN(this.softwareCompetition.projectHeader.id) || this.softwareCompetition.projectHeader.id <= 0)) {
                Alert.yesLabel = "Yes";
                Alert.noLabel = "No";
                bolckSaveAsDraft = true;
                alert = Alert.show("Would you like to create the corresponding Component Development contest?",
                    "", 3, this, handleSubmitPurchase);
                alert.height = 150;
                _type = type;
                _eventHandler = eventHandler;
                _faultEventHandler = faultEventHandler;
                return;
            }
            _submitPurchase(type, eventHandler, faultEventHandler);
        }
        
        public function _submitPurchase(type:String, eventHandler:Function, faultEventHandler:Function):void {
            var studioContestType:Boolean = (this.container.contents as LaunchWidget).studioContestType; // BUGR-1682

            if(studioContestType) { // BUGR-1682
                competition._id=competition.id;
                competition._type=competition.type;
                competition.contestData.statusId=CONTEST_STATUS_ACTIVE_PUBLIC;
                competition.contestData.detailedStatusId=CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC;
            }
            //
            // Module: Flex Cockpit Launch Contest - Integrate Software Contests v1.0 
            // Updated to avoid 'duplicate variable definition' warning
            //
            var processContestPaymentOp:AbstractOperation=null;
            if (type == "PayPalCreditCard") {
                var creditCardPaymentData:CreditCardPaymentData=new CreditCardPaymentData();

                creditCardPaymentData.type=new PaymentType();
                creditCardPaymentData.type.paymentType="PayPalCreditCard";

                creditCardPaymentData.cardNumber=Model.instance.cardNum;
                creditCardPaymentData.cardType=Model.instance.cardType;
                creditCardPaymentData.cardExpiryMonth=Model.instance.cardExpMonth;
                creditCardPaymentData.cardExpiryYear=Model.instance.cardExpYear;
                creditCardPaymentData.firstName=Model.instance.firstName;
                creditCardPaymentData.lastName=Model.instance.lastName;
                creditCardPaymentData.address=Model.instance.address;
                creditCardPaymentData.city=Model.instance.city;
                creditCardPaymentData.state=Model.instance.state;
                creditCardPaymentData.zipCode=Model.instance.code;
                creditCardPaymentData.country=Model.instance.country;
                creditCardPaymentData.phone=Model.instance.phone;
                creditCardPaymentData.email=Model.instance.email;
                creditCardPaymentData.ipAddress="10.10.10.10";
                creditCardPaymentData.sessionId="";
                creditCardPaymentData.csc=Model.instance.csc; // BUGR-1398
                
                if (studioContestType) { // BUGR-1682
                    prepareStudioContestSave(false);
                    processContestPaymentOp=_csws.getOperation("processContestCreditCardPayment");
                    processContestPaymentOp.addEventListener("result", eventHandler);
                    processContestPaymentOp.addEventListener("fault", faultEventHandler);
                    processContestPaymentOp.send(competition, creditCardPaymentData);
                } else {
                    
                    prepareSoftwareContest();
                    
                    //
                    // Updated for Cockpit Release Assembly 1 v1.0 [BUGR-1816]
                    // for credit card based order, set comments to user id.
                    //
                    softwareCompetition.assetDTO.compComments=userid.toString();
                    
                    processContestPaymentOp=_csws.getOperation("processContestCreditCardSale");
                    processContestPaymentOp.addEventListener("result", eventHandler);
                    processContestPaymentOp.addEventListener("fault", faultEventHandler);
                    processContestPaymentOp.send(softwareCompetition, creditCardPaymentData);
                }
            } else {
                var purchaseOrderPaymentData:TcPurhcaseOrderPaymentData=new TcPurhcaseOrderPaymentData();
                
                purchaseOrderPaymentData.type=new PaymentType();
                purchaseOrderPaymentData.type.paymentType="TCPurchaseOrder";
                purchaseOrderPaymentData.poNumber=Model.instance.purchaseOrder.poNumber;
                purchaseOrderPaymentData.projectId=Model.instance.purchaseOrder.projectId;
                purchaseOrderPaymentData.projectName=Model.instance.purchaseOrder.projectName;
                purchaseOrderPaymentData.clientId=Model.instance.purchaseOrder.clientId;
                purchaseOrderPaymentData.clientName=Model.instance.purchaseOrder.clientName;
                
                if (studioContestType) { // BUGR-1682
                    prepareStudioContestSave(false);
                    competition.contestData.billingProject = this.invoicedProjectId;
                    
                    processContestPaymentOp=_csws.getOperation("processContestPurchaseOrderPayment");
                    processContestPaymentOp.addEventListener("result", eventHandler);
                    processContestPaymentOp.addEventListener("fault", faultEventHandler);
                    processContestPaymentOp.send(competition, purchaseOrderPaymentData);
                } else {
                    prepareSoftwareContest();
                    
                    //
                    // Updated for Cockpit Release Assembly 1 v1.0 [BUGR-1816]
                    // for purchase order, set comments to po number.
                    //
                    softwareCompetition.assetDTO.compComments=purchaseOrderPaymentData.poNumber;

                    
                    processContestPaymentOp=_csws.getOperation("processContestPurchaseOrderSale");
                    processContestPaymentOp.addEventListener("result", eventHandler);
                    processContestPaymentOp.addEventListener("fault", faultEventHandler);
                    processContestPaymentOp.send(softwareCompetition, purchaseOrderPaymentData);
                }
            }
            
            showLoadingProgress();
        }

        /**
         * Updated to support save of software contests too.
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        public function saveAsDraft():void {
            if (this.competitionType == "DESIGN" &&
                (isNaN(this.softwareCompetition.projectHeader.id) || this.softwareCompetition.projectHeader.id <= 0)) {
                Alert.yesLabel = "Yes";
                Alert.noLabel = "No";
                bolckSaveAsDraft = true;
                alert = Alert.show("Would you like to create the corresponding Component Development contest?",
                    "", 3, this, handleSaveAsDraft);
                alert.height = 150;
                return;
            }
            _saveAsDraft();
        }
        
        private function handleSaveAsDraft(event:CloseEvent):void {
            if (event && event.detail) {
                if (event.detail == Alert.YES) {
                    addDevelopmentData();
                }
            }
            _saveAsDraft();
        }
        
        public function _saveAsDraft():void {
            //var type:CompetionType = new CompetionType();
            if (this.competitionType == "STUDIO") {
                var competionType:String="STUDIO";
                this.competition.type=competionType;

                this.competition.contestData.billingProject = this.invoicedProjectId;

                if (isNaN(competition.contestData.contestId) || competition.contestData.contestId <= 0) {
                    createStudioContest();
                } else {
                    updateStudioContest();
                }
            } else {
                if (isNaN(this.softwareCompetition.projectHeader.id) || this.softwareCompetition.projectHeader.id <= 0) {
                    createSoftwareContest();
                } else {
                    // implementing update is out of scope of this assembly.
                    updateSoftwareContest();
                }
            }

	    showLoadingProgress();

        
        }

        /**
         * Creates software contest.
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        private function createSoftwareContest():void {

            prepareSoftwareContest();
            
            var createContestOp:AbstractOperation=_csws.getOperation("createSoftwareContest");
            
            createContestOp.addEventListener("result", createSoftwareContestHandler);
            createContestOp.send(softwareCompetition, softwareCompetition.projectHeader.tcDirectProjectId);
        }

        /**
            *Handle the alert pop up box of create development contest.
            */
        private function addDevelopmentData():void {
            var tempCompetition:SoftwareCompetition = new SoftwareCompetition();
            tempCompetition.projectHeader = new SoftwareProjectHeader();
            tempCompetition.projectHeader.properties = new Array();
            var me:LaunchWidget = container.contents as LaunchWidget;
            var developmentAdminFee:Number=me.overView.contestFees.contestType.(@contestTypeId == 2).contestFee;
            var selCostData:Dictionary=null;
            var level:String=SoftwareCompetitionUtils.instance().getCostLevelProp(softwareCompetition);
            if (level==SoftwareCompetitionUtils.COST_LEVEL_A) {
                me.overView.loadCostLevelData(
                    me.overView.contestFees.contestType.(@contestTypeId == 2).contestCost.billingLevel.(@id == 'low')[0] as XML,
                    selCostData);
            } else if (level==SoftwareCompetitionUtils.COST_LEVEL_B) {
                me.overView.loadCostLevelData(
                    me.overView.contestFees.contestType.(@contestTypeId == 2).contestCost.billingLevel.(@id == 'medium')[0] as XML,
                    selCostData);
            } else if (level==SoftwareCompetitionUtils.COST_LEVEL_C) {
                me.overView.loadCostLevelData(
                    me.overView.contestFees.contestType.(@contestTypeId == 2).contestCost.billingLevel.(@id == 'high')[0] as XML,
                    selCostData);
            }
            
            if (selCostData) {
                
                SoftwareCompetitionUtils.instance().addCostLevelProp(tempCompetition, level);
                
                var firstPlaceCost:Number=selCostData["FirstPlace"] as Number;
                var secondPlaceCost:Number=selCostData["SecondPlace"] as Number;
                var reviewBoardCost:Number=selCostData["ReviewBoard"] as Number;
                var reliabilityBonus:Number=selCostData["ReliabilityBonus"] as Number;
                var drCost:Number=selCostData["DR"] as Number;
                var milestoneBonus:Number=selCostData["MilestoneBonus"] as Number;
                
                SoftwareCompetitionUtils.instance().addCostProp(tempCompetition, firstPlaceCost, 
                    SoftwareCompetitionUtils.PROJECT_INFO_TYPE_FIRST_PLACE_COST_KEY);    
                SoftwareCompetitionUtils.instance().addCostProp(tempCompetition, secondPlaceCost, 
                    SoftwareCompetitionUtils.PROJECT_INFO_TYPE_SECOND_PLACE_COST_KEY);
                SoftwareCompetitionUtils.instance().addCostProp(tempCompetition, reviewBoardCost, 
                    SoftwareCompetitionUtils.PROJECT_INFO_TYPE_REVIEW_COST_KEY);
                SoftwareCompetitionUtils.instance().addCostProp(tempCompetition, reliabilityBonus, 
                    SoftwareCompetitionUtils.PROJECT_INFO_TYPE_RELIABILITY_BONUS_COST_KEY);
                SoftwareCompetitionUtils.instance().addCostProp(tempCompetition, drCost, 
                    SoftwareCompetitionUtils.PROJECT_INFO_TYPE_DR_POINTS_KEY);
                SoftwareCompetitionUtils.instance().addCostProp(tempCompetition, milestoneBonus, 
                    SoftwareCompetitionUtils.PROJECT_INFO_TYPE_MILESTONE_BONUS_COST_KEY);                
                    
                // TODO: ideally in payments property we should be storing total sw cost.
                // but not sure how does TC / OR works - so for now storing first place cost.
                SoftwareCompetitionUtils.instance().addCostProp(tempCompetition, firstPlaceCost/*getTotalSWCost(selCostData)*/, 
                    SoftwareCompetitionUtils.PROJECT_INFO_TYPE_PAYMENT_KEY); 
            }
            SoftwareCompetitionUtils.instance().addCostProp(tempCompetition, 
                developmentAdminFee, 
                SoftwareCompetitionUtils.PROJECT_INFO_TYPE_ADMIN_FEE_KEY);                
            this.softwareCompetition.developmentProjectHeader = tempCompetition.projectHeader;
        }


        /**
         * Updated for Cockpit Release Assembly 3
         *    - Add the billing project property.
         * 
         * Updated for Version 1.0.3
         *    - on save Confidentiality type property is saved.
         *    - Properties are getting inserted during Overview page, so not needed here.
         */ 
	    private function prepareSoftwareContest():void {
		    // set dynamic properties before save.
            
            if (!this.softwareCompetition.assetDTO.detailedDescription || this.softwareCompetition.assetDTO.detailedDescription == "") {
                this.softwareCompetition.assetDTO.detailedDescription="NA";
            }
            
            if (!this.softwareCompetition.assetDTO.shortDescription || this.softwareCompetition.assetDTO.shortDescription == "") {
                this.softwareCompetition.assetDTO.shortDescription="NA";
            }
                
    	    // we need a reason for update
    	    this.softwareCompetition.projectHeaderReason = "user update";
            this.softwareCompetition.projectHeader.tcDirectProjectName=tcDirectProjectName;
            this.softwareCompetition.projectHeader.tcDirectProjectId= new Number(tcDirectProjectId);
       
            if (enforcedCCA) {
                SoftwareCompetitionUtils.instance().addConfidentialityTypeProp(this.softwareCompetition, SoftwareCompetitionUtils.CONFIDENTIALITY_TYPE_CCA);
            } else {
                SoftwareCompetitionUtils.instance().addConfidentialityTypeProp(this.softwareCompetition, SoftwareCompetitionUtils.CONFIDENTIALITY_TYPE_PUBLIC);
            }

            SoftwareCompetitionUtils.instance().addProjectNameProp(this.softwareCompetition, this.softwareCompetition.assetDTO.name);
            SoftwareCompetitionUtils.instance().addRootCatalogIdProp(this.softwareCompetition, this.softwareCompetition.assetDTO.rootCategory.id);

            SoftwareCompetitionUtils.instance().addBillingProjectProp(this.softwareCompetition, this.invoicedProjectId.toString());
	    }

        /**
         * Handler for software contest create.
         * 
         * Updated for 1.0.3
         *    - getting first place and second place from project header properties.
         * 
         * @param e a <code>ResultEvent</code>
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        private function createSoftwareContestHandler(e:ResultEvent):void {
            trace("createSoftwareContestHandler: " + e + ", " + e.result);
            hideLoadingProgress();

            if (e && e.result) {
                this.softwareCompetition=ObjectTranslatorUtils.translate(e.result, SoftwareCompetition) as SoftwareCompetition;
                trace("createSoftwareContestHandler:: this.competition: " + this.softwareCompetition);
                this.contestid=this.softwareCompetition.projectHeader.id.toFixed(0);

                // set prize and admin fee from properties
                this.softwareCompetition.adminFee = SoftwareCompetitionUtils.instance().getCostProp(this.softwareCompetition, SoftwareCompetitionUtils.PROJECT_INFO_TYPE_ADMIN_FEE_KEY);
                this.softwareCompetition.prizes[0] = new PrizeData();
                this.softwareCompetition.prizes[1] = new PrizeData();
                PrizeData(this.softwareCompetition.prizes[0]).amount = SoftwareCompetitionUtils.instance().getCostProp(this.softwareCompetition, SoftwareCompetitionUtils.PROJECT_INFO_TYPE_FIRST_PLACE_COST_KEY);
                PrizeData(this.softwareCompetition.prizes[1]).amount = SoftwareCompetitionUtils.instance().getCostProp(this.softwareCompetition, SoftwareCompetitionUtils.PROJECT_INFO_TYPE_SECOND_PLACE_COST_KEY);

                // call initData in attachment page so that the attachment information is refreshed.
                (container.contents as LaunchWidget).attch.initData();

                // BUGR-1470 - mark refresh of my project.
            	notifyMyProjectWidget();

                Helper.showAlertMessage("Contest created successfully!");
            }
        }
        
        /**
         * Updates software contest.
         * 
         * Updated for Version 1.0.3
         *    - Properties are getting inserted during Overview page, so not needed here.
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        private function updateSoftwareContest():void {

            prepareSoftwareContest();

            
            // we need a reason for update
            this.softwareCompetition.projectHeaderReason = "user update";
            
            var updateContestOp:AbstractOperation=_csws.getOperation("updateSoftwareContest");
            
            updateContestOp.addEventListener("result", updateSoftwareContestHandler);
            updateContestOp.send(softwareCompetition, softwareCompetition.projectHeader.tcDirectProjectId);

	    showLoadingProgress();
        }

        /**
         * Handler for software contest update.
         * 
         * @param e a <code>ResultEvent</code>
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        private function updateSoftwareContestHandler(e:ResultEvent):void {
            trace("updateSoftwareContestHandler: " + e + ", " + e.result);
            hideLoadingProgress();
            if (e && e.result) {


		this.softwareCompetition=ObjectTranslatorUtils.translate(e.result, SoftwareCompetition) as SoftwareCompetition;
                trace("createSoftwareContestHandler:: this.competition: " + this.softwareCompetition);

                // set prize and admin fee from properties
                this.softwareCompetition.adminFee = SoftwareCompetitionUtils.instance().getCostProp(this.softwareCompetition, SoftwareCompetitionUtils.PROJECT_INFO_TYPE_ADMIN_FEE_KEY);
                this.softwareCompetition.prizes[0] = new PrizeData();
                this.softwareCompetition.prizes[1] = new PrizeData();
                PrizeData(this.softwareCompetition.prizes[0]).amount = SoftwareCompetitionUtils.instance().getCostProp(this.softwareCompetition, SoftwareCompetitionUtils.PROJECT_INFO_TYPE_FIRST_PLACE_COST_KEY);
                PrizeData(this.softwareCompetition.prizes[1]).amount = SoftwareCompetitionUtils.instance().getCostProp(this.softwareCompetition, SoftwareCompetitionUtils.PROJECT_INFO_TYPE_SECOND_PLACE_COST_KEY);

                // call initData in attachment page so that the attachment information is refreshed.
                (container.contents as LaunchWidget).attch.initData();

                // BUGR-1470 - mark refresh of my project.
            	notifyMyProjectWidget();
            	
                Helper.showAlertMessage("Contest updated successfully!");
            }
        }

        /**
         * Creates studio contest.
         * 
         * Just name has been updated in this version
         */
        private function createStudioContest():void {
            var createContestOp:AbstractOperation=_csws.getOperation("createContest");

            competition._id=competition.id;
            competition._type=competition.type;

            prepareStudioContestSave(true);
                        
            createContestOp.addEventListener("result", createStudioContestHandler);
            createContestOp.send(competition, competition.contestData.tcDirectProjectId);
            
            showLoadingProgress();
        }
        
        private function prepareStudioContestSave(inactive:Boolean):void {
            competition.contestData.contestTypeId = studioContestSubTypeId;
            competition.contestData.tcDirectProjectId = new Number(tcDirectProjectId);
            
            if (inactive) {
                competition.contestData.statusId=CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED;
                competition.contestData.detailedStatusId=CONTEST_DETAILED_STATUS_DRAFT;
            }
        }

        /**
         * Handler for studio contest create.
         * 
         * @param e a <code>ResultEvent</code>
         *
         * Just name has been updated in this version
         */
        private function createStudioContestHandler(e:ResultEvent):void {
            trace("createContestHandler: " + e + ", " + e.result);
            hideLoadingProgress();
            if (e && e.result) {
                this.competition=ObjectTranslatorUtils.translate(e.result, StudioCompetition) as StudioCompetition;
                trace("createContestHandler:: this.competition: " + this.competition);
                this.contestid=this.competition.contestData.contestId.toFixed(0);
                

                //var type:CompetionType = new CompetionType();
                var competitionType:String="STUDIO";
                this.competition.type=competitionType;

                //Helper.showAlertMessage("Contest created successfully!");
                (container.contents as LaunchWidget).sched.initData(); // BUGR-1445

		// BUGR-1470 - mark refresh of my project.
            	notifyMyProjectWidget();

		 Helper.showAlertMessage("Contest created successfully!");
            	
            }
        }

        /**
         * Updates studio contest.
         * 
         * Just name has been updated in this version
         * 
         * Updated for Version 1.0.1
         *    - unpaid contests are considered inactive.
         */
        private function updateStudioContest():void {
	        prepareStudioContestSave(!isPaidContest() || getExtraContestFee() > 0);

            var updateContestOp:AbstractOperation=_csws.getOperation("updateContest");

            updateContestOp.addEventListener("result", updateStudioContestHandler);
            updateContestOp.send(competition);

	    showLoadingProgress();
        }

        /**
         * Handler for studio contest update.
         * 
         * @param e a <code>ResultEvent</code>
         *
         * Just name has been updated in this version
         */
        private function updateStudioContestHandler(event:ResultEvent):void {

           hideLoadingProgress();
			
	   // BUGR-1470 - mark refresh of my project.
	   notifyMyProjectWidget();
			
           Helper.showAlertMessage("Contest updated successfully!");
        }

	/**
         * Simple setter for the allowclose of this widget.
         *
         * @param allow the flag allowclose of this widget.
         */
        public function set allowclose(allow:Boolean):void {
            _allowclose=allow;
        }

        /**
         * Simple getter for the name of this widget.
         *
         * @return the allowclose flag fo this widget. Could be null if not set.
         */
        public function get allowclose():Boolean {
            return _allowclose;
        }

	/**
         * Simple setter for the container of this widget.
         *
         * @param container of this widget.
         */
        public function set container(container:IWidgetContainer):void {
            _container=container;
        }

        /**
         * Simple getter for the container of this widget.
         *
         * @return the container this widget. Could be null if not set.
         */
        public function get container():IWidgetContainer {
            if (_container == null) {
                _container=parent as IWidgetContainer;
            }
            return _container;
        }
        
        public function previewContest():void {
            var url:String;
            if (this.competitionType == "STUDIO") {
                
                if (isNaN(competition.contestData.contestId) || competition.contestData.contestId <= 0) {
                    Helper.showAlertMessage("You must 'Save as Draft' before you can preview your contest.");
                } else {
                    //url="http://" + Application.application.parameters.studioAddress + "/?module=ViewContestDetails&ct=" + competition.contestData.contestId;
                    url="http://" + Application.application.parameters.studioAddress + "/direct/cockpit/impersonation/cockpitStudio.do?module=ViewContestDetails&ct=" + competition.contestData.contestId;
                    
                    navigateToURL(new URLRequest(url), "_blank");
                }
            } else {
                if (isNaN(this.softwareCompetition.projectHeader.id) || this.softwareCompetition.projectHeader.id <= 0) {
                    Helper.showAlertMessage("You must 'Save as Draft' before you can preview your contest.");
                } else {
                    url="http://" + Application.application.parameters.hostAddress + "/tc?module=ProjectDetail&pj=" + softwareCompetition.projectHeader.id;
                    navigateToURL(new URLRequest(url), "_blank");
                }
           
            }
        }
        
        // BUGR-1470
        public function notifyMyProjectWidget():void {
            var widget:IWidget=this.widgetFramework.getWidget("My Projects", "My Projects");
            
            var dict:Dictionary = new Dictionary();
            dict["ContestUpdated"]=true;
            widget.setAttributes(dict);
        }
        
        // BUGR-1393
        public function showLoadingProgress():void {
            ProgressWindowManager.showProgressWindow(this.container);
        }
        
        // BUGR-1393
        public function hideLoadingProgress():void {
        	ProgressWindowManager.hideProgressWindow(this.container);
        }
        
        /**
         * Determines whether the current contest is a paid contest or unpaid.
         * It returns true if current contest is a paid contest otherwise returns false.
         * 
         * @return true if current contest is a paid contest otherwise returns false.
         * @since 1.0.1
         */ 
        public function isPaidContest():Boolean {
            if (this.competitionType == "STUDIO") {
                if (!competition || !competition.contestData || !competition.contestData.payments 
                         || competition.contestData.payments.length <= 0) {
                    return false;
                }
                
                trace("STUDIO isPaidContest: " + competition.contestData.payments);
                
                return true;
            } else {
                if (!softwareCompetition || !softwareCompetition.projectData 
                         || !softwareCompetition.projectData.contestSales 
                         || softwareCompetition.projectData.contestSales.length <= 0) {
                    return false;
                }
                
                trace("SOFTWARE isPaidContest: " + softwareCompetition.projectData.contestSales);
                
                return true;
            }
        }

	    // BUGR-1363
        public function getPaidContestFee():Number {
            if (this.competitionType == "STUDIO") {
                if (!competition.contestData.payments) {
                    return 0;
                }
                var paidFee:Number=0;
                for (var i:int=0; i < competition.contestData.payments.length; i++) {
                    paidFee+=(competition.contestData.payments[i] as ContestPaymentData).price;
                }
                return new Number(paidFee.toFixed(2));
            } else {
                if (!softwareCompetition || !softwareCompetition.projectData || !softwareCompetition.projectData.contestSales) {
                    return 0;
                }
                
                var paidAmount:Number=0;
                for (var j:int=0; j < softwareCompetition.projectData.contestSales.length; j++) {
                    paidAmount+=(softwareCompetition.projectData.contestSales[i] as SoftwareProjectSaleData).price;
                }
                
                return new Number(paidAmount.toFixed(2));
            }
        }
        
        // BUGR-1363
        public function getExtraContestFee():Number {
            if (this.competitionType == "STUDIO")
            {
                    var me:LaunchWidget = container.contents as LaunchWidget;
                    var result:Number = new Number(me.overView.adminf.text) - getPaidContestFee();
                    return new Number(result.toFixed(2));
            }
            else
            {
                var me:LaunchWidget = container.contents as LaunchWidget;
                var result:Number = new Number(me.overView.ns_cntstTtl.text) - getPaidContestFee();
                return new Number(result.toFixed(2));
            }
        }
        
         /**
         * Gets the user id.
         *
         * @return the current user id.
         */
        public function get userid():Number {
            return this._userid;
        }

        /**
         * Sets the user id.
         *
         * @param name the user id.
         */
        public function set userid(id:Number):void {
            this._userid=id;
        }
        
        [Bindable]
        public function get tcDirectProjectId():String {
            return _tcDirectProjectId;
        }

        public function set tcDirectProjectId(projectID:String):void {
            _tcDirectProjectId=projectID;
        }

        [Bindable]
        public function get tcDirectProjectName():String {
            return _tcDirectProjectName;
        }

        public function set tcDirectProjectName(projectName:String):void {
            _tcDirectProjectName = projectName;
        }

        
        
        /**
         * Gets whether the current contest is invoiced or not
         * 
         * @return whether the current contest is invoiced or not
         * @since 1.0.1 
         */
        public function get isInvoicedContest():Boolean {
            return _isInvoicedContest;
        }
        
        /**
         * Sets whether the current contest is invoiced or not.
         * 
         * @param b whether the current contest is invoiced or not.
         * @since 1.0.1
         */ 
        public function set isInvoicedContest(b:Boolean):void {
            this._isInvoicedContest=b;
        }
        
        /**
         * Gets the invoiced billing project id.
         * 
         * @return the invoiced billing project id.
         * @since 1.0.1
         */
        public function get invoicedProjectId():Number {
            return this._invoicedProjectId;
        }
        
        /**
         * Sets the invoiced billing project id.
         * 
         * @param n the invoiced billing project id.
         * @since 1.0.1
         */ 
        public function set invoicedProjectId(n:Number):void {
            this._invoicedProjectId=n;
        }
        
        /**
         * Gets the invoiced billing project data.
         * 
         * @return the invoiced billing project data.
         * @since 1.0.1
         */
        public function get invoicedProjectData():Object {
            return this._invoicedProjectData;
        }
        
        /**
         * Sets the invoiced billing project data.
         * 
         * @param n the invoiced billing project data.
         * @since 1.0.1
         */ 
        public function set invoicedProjectData(n:Object):void {
            this._invoicedProjectData=n;
        }
        
        /**
         * Gets whether manual prize setting is allowed or not.
         * 
         * @return true if manual prize setting is allowed else false.
         * @since 1.0.4
         */
        public function get manualPrizeSetting():Boolean {
            return this._manualPrizeSetting;
        }
        
        /**
         * Sets whether manual prize setting is allowed or not.
         * 
         * @param b whether manual prize setting is allowed or not.
         * @since 1.0.4
         */ 
        public function set manualPrizeSetting(b:Boolean):void {
            this._manualPrizeSetting=b;
        }
        
        //
        // load master technologies from web-service.
        // @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
        //
        public function loadMasterTechnologies():void {
            if (!_csws) {
                return;
            }
            var getActiveTechnologiesOp:AbstractOperation=_csws.getOperation("getActiveTechnologies");
            getActiveTechnologiesOp.addEventListener("result", handleMasterTechnologiesLoad);
            getActiveTechnologiesOp.send();
        }

        //
        // handle successful load of master technologies from web-service.
        // @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
        //
        private function handleMasterTechnologiesLoad(e:ResultEvent):void {
            trace("handleMasterTechnologiesLoad: " + e + ", " + e.result);
            if (e && e.result) {
                var technologies:ArrayCollection=ObjectTranslatorUtils.translateCollection(e.result, SoftwareTechnology) as ArrayCollection;

                masterTechnologies=new ArrayCollection();
                for (var i:int=0; i < technologies.length; i++) {
                    var t:SoftwareTechnology=technologies.getItemAt(i) as SoftwareTechnology;
                    var o:Object=new Object();
                    o.label=t.name;
                    o.data=t;
                    masterTechnologies.addItem(o);
                }

                if (!masterTechnologies.sort) {
                    var sort:Sort=new Sort();
                    sort.compareFunction=compareListItem;
                    masterTechnologies.sort=sort;
                }

                masterTechnologies.refresh();
            }
        }
        
        //
        // load active categories from web-service.
        // @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
        //
        public function loadCategories():void { // BUGR-1678
            if (!_csws) {
                return;
            }
            var getActiveCategoriesOp:AbstractOperation=_csws.getOperation("getActiveCategories");
            getActiveCategoriesOp.addEventListener("result", handleCategoriesLoad);
            getActiveCategoriesOp.send();
        }

        // BUGR-1678
        public function isDesOrDevContestType():Boolean {
            return (softwareCompetition &&
                        (softwareCompetition.type == "DEVELOPMENT" ||
                                    softwareCompetition.type == "DESIGN")) 
                   ||
                    (softwareCompetition && softwareCompetition.projectHeader 
                          && softwareCompetition.projectHeader.projectCategory
                          &&
                            (softwareCompetition.projectHeader.projectCategory.id == SoftwareProjectCategory.PROJECT_CATEGORY_DESIGN
                                    || softwareCompetition.projectHeader.projectCategory.id == SoftwareProjectCategory.PROJECT_CATEGORY_DEV));
        }
        
        //
        // handled for load of active categories from web-service.
        // this method identifies the child / parent relationship as mentioned in 1.1.4
        // and groups the categories into root and child categories.
        // root categories are recognized as catalogs.
        // while for child categories catalog-id to list of child categories map is created.
        // @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
        //
        private function handleCategoriesLoad(e:ResultEvent):void {
            trace("handleCategoriesLoad: " + e + ", " + e.result);
            const APPLICATION_DESCR:String = "Application"; // BUGR-1678
            
            //
            // Cockpit Release Assembly 1 v1.0 [BUGR-1850]
            //
            const BUSINESS_LAYER_DESCR:String = "Business Layer";
            
            const NOT_SET_DESCR:String = "Not Set";
            
            catalogToCategoriesMap = new Dictionary();  // BUGR-1678 
            if (e && e.result) {
                categories=ObjectTranslatorUtils.translateCollection(e.result, SoftwareCategory) as ArrayCollection;

                catalogs=new ArrayCollection();
                var oEmpty:Object=new Object();
                oEmpty.label="";
                oEmpty.data=null;
                catalogs.addItem(oEmpty);

                for (var i:int=0; i < categories.length; i++) {
                    var t:SoftwareCategory=categories.getItemAt(i) as SoftwareCategory;
                    
                    //
                    // For non admin user just consider the custom catalogs.
                    // @since Cockpit Release Assembly 1 v1.0 [BUGR-1850]
                    //
                    if (!isAdmin && t.viewable) {
                        continue;
                    }

                    
                    if (t.catalogName) {
                        // it is the root category.
                        // add this catalog name to list of catalog names.
                        var o:Object=new Object();
                        o.label=t.name;
                        o.data=t;

                        // we only show catalog for dev/design, and dont show Application and Not Set
                        if (t.name != APPLICATION_DESCR && t.name != NOT_SET_DESCR) {
                            catalogs.addItem(o);
                        }
                        
                        //
                        // Stores the reference of application catalog to the variable 'applicationCatalog'
                        // @since Cockpit Release Assembly 1 v1.0 [BUGR-1850]
                        //
                        if (o.label == APPLICATION_DESCR) {
                            applicationCatalog = o.data;
                        } else if (o.label == NOT_SET_DESCR) {
                            notSetCatalog = o.data;
                        }
                    } else if (t.parentCategory) {
                        var categoryObj:Object=new Object();
                        categoryObj.label=t.name;
                        categoryObj.data=t;

                        var rootCategoryId:Number=t.parentCategory.id;
                        var categoriesList:ArrayCollection=null;
                        if (!catalogToCategoriesMap[rootCategoryId]) {
                            categoriesList=new ArrayCollection();
                            catalogToCategoriesMap[rootCategoryId]=categoriesList;

                            if (!categoriesList.sort) {
                                var srt:Sort=new Sort();
                                srt.compareFunction=compareListItem;
                                categoriesList.sort=srt;
                            }

                            categoriesList.addItem(categoryObj);
                            categoriesList.refresh();

                        } else {
                            categoriesList=catalogToCategoriesMap[rootCategoryId] as ArrayCollection;

                            categoriesList.addItem(categoryObj);
                            categoriesList.refresh();
                        }
                        
                        //
                        // Stores the reference of business layer category to the variable 'businessLayerApplicationCategory'
                        // @since Cockpit Release Assembly 1 v1.0 [BUGR-1850]
                        //
                        if (categoryObj.label == BUSINESS_LAYER_DESCR && categoryObj.data.parentCategory.name == APPLICATION_DESCR) {
                            businessLayerApplicationCategory = categoryObj.data;
                        } else if (categoryObj.label == NOT_SET_DESCR && categoryObj.data.parentCategory.name == NOT_SET_DESCR) {
                            notSetCategory = categoryObj.data;
                        }
                    }
                }

                if (!catalogs.sort) {
                    var sort:Sort=new Sort();
                    sort.compareFunction=compareListItem;
                    catalogs.sort=sort;
                }

                catalogs.refresh();
            }
        }
        
        //
        // utility function to compare list item by label.
        // @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
        //
        public function compareListItem(a:Object, b:Object, fields:Array=null):int {
            return ObjectUtil.stringCompare(a.label, b.label, true);
        }
    }
}
