/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget {
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.widgets.model.IWidget;
    import com.topcoder.flex.widgets.model.IWidgetContainer;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.qs.Model;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.CompetionType;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.CreditCardPaymentData;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.PaymentType;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.StudioCompetition;
    import com.topcoder.flex.widgets.widgetcontent.LaunchAContestWidget.webservice.data.TcPurhcaseOrderPaymentData;
    
    import flash.utils.Dictionary;
    
    import mx.containers.VBox;

	import com.topcoder.flex.Helper;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import mx.core.Application;
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
        
        /**
         * The data for the widget.
         */
        [Bindable] private var _result:XML = <competition><contestData></contestData></competition>;
        
        [Bindable] private var _competition:StudioCompetition;
        
        private var _configurableText:XML;
        
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
        
        public function resetWidget():void{
    		reload();
    	}
                
        /**
         * This action will reload this widget.
         */
        public function reload():void {
            if(!container.contents.isMaximized())
            {
            		container.startMaximize();
            }
            var f:IWidgetFramework=widgetFramework;
        	container.contents=new  LaunchWidget();
        	container.contents.widgetFramework=f;
        	container.contents.name=name;
        	container.startRestore();
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
         * @param map the attributes for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function setAttributes(map:Dictionary):void {
        	if(map["contestid"])
        	{
        		contestid=map["contestid"];
        		
        		var getContestOp:AbstractOperation = _csws.getOperation("getContest");
                getContestOp.addEventListener("result", getContestHandler);
                getContestOp.send(parseInt(contestid));
        	}
        }
        
        private function getContestHandler(e:ResultEvent):void
        {
        	if(e && e.result)
        	{
        		resetWidget();
        		(container.contents as LaunchWidget).competition=e.result as StudioCompetition;
        	}
        }
        
        public function submitPurchase(type:String, eventHandler:Function):void
        {
            competition._id=competition.id;
            competition._type=competition.type;
            competition.contestData.statusId=CONTEST_STATUS_ACTIVE_PUBLIC;
            competition.contestData.detailedStatusId=CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC;
            
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
                
                var processContestPaymentOp:AbstractOperation = _csws.getOperation("processContestCreditCardPayment");
            
                processContestPaymentOp.addEventListener("result", eventHandler);
                processContestPaymentOp.send(competition, creditCardPaymentData);
            }
            else {
                var purchaseOrderPaymentData:TcPurhcaseOrderPaymentData=new TcPurhcaseOrderPaymentData();

                purchaseOrderPaymentData.type=new PaymentType();
                purchaseOrderPaymentData.type.paymentType="TCPurchaseOrder";
                purchaseOrderPaymentData.poNumber=Model.instance.purchaseOrder;
                
                var processContestPaymentOp:AbstractOperation = _csws.getOperation("processContestPurchaseOrderPayment");
            
                processContestPaymentOp.addEventListener("result", eventHandler);
                processContestPaymentOp.send(competition, purchaseOrderPaymentData);
            }
        }
        
        public function saveAsDraft():void{
        	competition.contestData.statusId= CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED; //inactived
        	competition.contestData.detailedStatusId= CONTEST_DETAILED_STATUS_DRAFT;
        	saveContest();
        }
        
        private function saveContest():void{
        	var type:CompetionType = new CompetionType();
        	type.competionType = "STUDIO";
        	this.competition.type = type;
        	if (isNaN(competition.id) || competition.id < 0){
        		createContest();
        	} else {
        		updateContest();
        	}
        }
        
         private function createContest():void
        {
            var createContestOp:AbstractOperation = _csws.getOperation("createContest");
            
            competition._id=competition.id;
            competition._type=competition.type;
            
            createContestOp.addEventListener("result", createContestHandler);
            createContestOp.send(competition, competition.contestData.tcDirectProjectId);
        }
        
        // TCCC-1023
        private function createContestHandler(event:ResultEvent):void {
            if (event && event.result) {
            	this.competition = event.result as StudioCompetition;
            	
            	trace("createContestHandler:: StudioCompetition -- " + this.competition);
            	
            	var type:CompetionType = new CompetionType();
            	type.competionType = "STUDIO";
            	this.competition.type = type;
    
    		    Helper.showAlertMessage("Contest created successfully!");
            }
        }
        
        // TCCC-1023
        private function updateContest():void{
            var updateContestOp:AbstractOperation = _csws.getOperation("updateContest");
            
            updateContestOp.addEventListener("result", updateContestHandler);
            updateContestOp.send(competition);
        }
        
        // TCCC-1023
        private function updateContestHandler(event:ResultEvent):void{
        	Helper.showAlertMessage("Contest updated successfully!");
	    }

	/**
         * Simple setter for the allowclose of this widget.
         *
         * @param allow the flag allowclose of this widget.
         */
        public function set allowclose(allow:Boolean):void
        {
        	_allowclose=allow;
        }

        /**
         * Simple getter for the name of this widget.
         *
         * @return the allowclose flag fo this widget. Could be null if not set.
         */
        public function get allowclose():Boolean
        {
        	return _allowclose;
        }

	/**
         * Simple setter for the container of this widget.
         *
         * @param container of this widget.
         */
        public function set container(container:IWidgetContainer):void
        {
        	_container=container;
        }

        /**
         * Simple getter for the container of this widget.
         *
         * @return the container this widget. Could be null if not set.
         */
        public function get container():IWidgetContainer
        {
        	if(_container==null)
        	{
        		_container=parent as IWidgetContainer;
        	}
        	return _container;
        }
        
        public function previewContest():void {
        	var url:String;
        	
        	if (isNaN(competition.contestData.contestId) || competition.contestData.contestId < 0){
            		Helper.showAlertMessage("You must 'Save as Draft' before you can preview your contest.");
        	} else {
			url = "http://"+Application.application.parameters.studioAddress+"/?module=ViewContestDetails&ct=" + competition.contestData.contestId;

			navigateToURL(new URLRequest(url), "_blank");
        	}
        }
    }
}
