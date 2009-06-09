/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget {
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
    
    import flash.display.DisplayObject;
    import flash.display.DisplayObjectContainer;
    import flash.net.URLRequest;
    import flash.net.navigateToURL;
    import flash.utils.Dictionary;
    
    import mx.collections.ArrayCollection;
    import mx.containers.VBox;
    import mx.core.Application;
    import mx.events.FlexEvent;
    import mx.rpc.AbstractOperation;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.soap.mxml.WebService;
    import mx.rpc.xml.SchemaTypeRegistry;

    /**
     * <p>
     * This is the code behind script part for the launch a contest widget. It implements the IWidget interface.
     * It extends from the Panel.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     * 
     * @author TCSDEVELOPER
     * @version 1.0
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
            
        /**
        * @since BUGR-1737
        */     
        public function get isAdmin():Boolean {
        	return _isAdmin;
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
        private var _userid:Number=Application.application.parameters.userid;
        
        public var initWidgetCallbackFn:Function=null;
        
        public var isEditMode:Boolean=false;
        
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
        
        private function reloadInternal(isEmptyStart:Boolean, map:Dictionary):void {
            trace("IN RELOAD OF LAUNCH WIDGET:: START");
            if (!container.contents.isMaximized()) {
                container.startMaximize();
            }
            var f:IWidgetFramework=widgetFramework;
            container.contents=new LaunchWidget();
            // Now with changes in WidgetContainer, widget.parent != container.
            // so this need to be set.
            (container.contents as LaunchWidget).container=container;
            container.contents.widgetFramework=f;
            container.contents.name=name;
            
            (container.contents as LaunchWidget).initWidgetCallbackFn=function():void {
                if (map) {
                    (container.contents as LaunchWidget).isEditMode=true;
                    (container.contents as LaunchWidget).contestid=map["contestid"];
                    (container.contents as LaunchWidget).competitionType=map["contestType"].toLocaleUpperCase();
                    (container.contents as LaunchWidget).tcDirectProjectId=map["projectid"];
                    (container.contents as LaunchWidget).tcDirectProjectName=map["projectName"];
                    (container.contents as LaunchWidget).studioContestType=(container.contents as LaunchWidget).competitionType=="STUDIO";
                    
                    (container.contents as LaunchWidget).contestSelect.initData();
                }
                
                trace("IN RELOAD OF LAUNCH WIDGET:: competitionType: " + (container.contents as LaunchWidget).competitionType);
                
                if (!isEmptyStart) {
                    trace("IN RELOAD OF LAUNCH WIDGET:: OPEN OVERVIEW PAGE");
                    (container.contents as LaunchWidget).openOverViewPage();
                    //_emptyStart=true;
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
                
                showLoadingProgress();
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
                (container.contents as LaunchWidget).currentState="ContestSelectionState";
                (container.contents as LaunchWidget).onCreateComplete(2);
            }
        }
        
        /**
         * Handler for s/w competition retrieval.
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
                (container.contents as LaunchWidget).currentState="ContestSelectionState";
                (container.contents as LaunchWidget).onCreateComplete(2);
            }
        }

        public function submitPurchase(type:String, eventHandler:Function, faultEventHandler:Function):void {
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
                purchaseOrderPaymentData.poNumber=Model.instance.purchaseOrder;
                
                if (studioContestType) { // BUGR-1682
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
            //var type:CompetionType = new CompetionType();
            if (this.competitionType == "STUDIO") {
                var competionType:String="STUDIO";
                this.competition.type=competionType;
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

	private function prepareSoftwareContest():void {
		// set dynamic properties before save.
            var prizes:Array=new Array();
            for (var i:int=0; i < this.softwareCompetition.prizes.length; i++) {
                var p:Number=PrizeData(this.softwareCompetition.prizes[i]).amount;
                prizes.push(p);
            }

	    // we need a reason for update
	    this.softwareCompetition.projectHeaderReason = "user update";

	    SoftwareCompetitionUtils.instance().addAdminFeeProp(this.softwareCompetition, this.softwareCompetition.adminFee);
            SoftwareCompetitionUtils.instance().addPrizeProps(this.softwareCompetition, prizes);
            SoftwareCompetitionUtils.instance().addProjectNameProp(this.softwareCompetition, this.softwareCompetition.assetDTO.name);
            SoftwareCompetitionUtils.instance().addRootCatalogIdProp(this.softwareCompetition, this.softwareCompetition.assetDTO.rootCategory.id);

	}

        /**
         * Handler for software contest create.
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
                
                // BUGR-1470 - mark refresh of my project.
            	notifyMyProjectWidget();

                Helper.showAlertMessage("Contest created successfully!");
            }
        }
        
        /**
         * Updates software contest.
         *
         * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
         */
        private function updateSoftwareContest():void {

            // set dynamic properties before save.
            var prizes:Array=new Array();
            for (var i:int=0; i < this.softwareCompetition.prizes.length; i++) {
                var p:Number=PrizeData(this.softwareCompetition.prizes[i]).amount;
                prizes.push(p);
            }

	    // we need a reason for update
	    this.softwareCompetition.projectHeaderReason = "user update";

	    SoftwareCompetitionUtils.instance().addAdminFeeProp(this.softwareCompetition, this.softwareCompetition.adminFee);
            SoftwareCompetitionUtils.instance().addPrizeProps(this.softwareCompetition, prizes);
            SoftwareCompetitionUtils.instance().addProjectNameProp(this.softwareCompetition, this.softwareCompetition.assetDTO.name);
            SoftwareCompetitionUtils.instance().addRootCatalogIdProp(this.softwareCompetition, this.softwareCompetition.assetDTO.rootCategory.id);
            
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

	    competition.contestData.statusId= CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED; //inactived
            competition.contestData.detailedStatusId= CONTEST_DETAILED_STATUS_DRAFT;
            
            createContestOp.addEventListener("result", createStudioContestHandler);
            createContestOp.send(competition, competition.contestData.tcDirectProjectId);
            
            showLoadingProgress();
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
         */
        private function updateStudioContest():void {

	    if (getExtraContestFee() > 0) 
	    {
		competition.contestData.statusId= CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED; //inactived
		competition.contestData.detailedStatusId= CONTEST_DETAILED_STATUS_DRAFT;
		
	    }

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
                return 0;
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
			return 0;
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
		
		[Bindable]
		public function get tcDirectProjectId():String {
            return _tcDirectProjectId;
        }
    }
}
