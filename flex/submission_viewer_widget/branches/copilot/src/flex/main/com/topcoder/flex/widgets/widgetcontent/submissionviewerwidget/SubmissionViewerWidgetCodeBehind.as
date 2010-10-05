/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget {
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.util.date.DateUtil;
    import com.topcoder.flex.widgets.model.IWidget;
    import com.topcoder.flex.widgets.model.IWidgetContainer;
    import com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.webservices.CompletedContestData;
    import com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.webservices.CreditCardPaymentData;
    import com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.webservices.SubmissionPaymentData;
    import com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.webservices.TcPurhcaseOrderPaymentData;
    import com.topcoder.flex.util.progress.ProgressWindowManager;

    import flash.events.Event;
    import flash.utils.Dictionary;

    import mx.binding.utils.ChangeWatcher;
    import mx.collections.ArrayCollection;
    import mx.collections.Sort;
    import mx.containers.VBox;
    import mx.controls.Alert;
    import mx.core.Application;
    import mx.formatters.NumberFormatter;
    import mx.rpc.AbstractOperation;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.soap.SOAPHeader;
    import mx.rpc.soap.mxml.WebService;
    import mx.utils.ObjectProxy;
    import mx.utils.ObjectUtil;

    /**
     * <p>
     * This is the code behind mxml widget component for the submission viewer widget. It implements the IWidget interface.
     * It extends from the VBox.
     *
     * Version 1.0.1 (Cockpit Release Assembly 4 v1.0) Change notes:
     *    - introduced new state variable _isMaxView to correctly capture the view state of the widget.
     *
     * Version 1.0.2 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI) Change notes:
     *    - Added support for multi round contests, including:
     *          - Multi round information load.
     *          - Separate checkout submission list.
     *          - Milestone prizes rankings.
     *          - Submission entity changes to support milestone prizes.
     *          - Milestone prizes payment.
     *    - All contests the user has access to are now shown.
     *
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author shailendra_80, pulky
     * @version 1.0.2
     * @since Flex Submission Viewer Overhaul
     */
    [Bindable]
    public class SubmissionViewerWidgetCodeBehind extends VBox implements IWidget {
        private static const WSSE_SECURITY:QName=new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");

        /**
         * The name of the widget.
         */
        private var _name:String="Submission Viewer";

        /**
         * The reference to IWidgetFramework.
         */
        private var _widgetFramework:IWidgetFramework=null;

        /**
         * The container for this widget.
         */
        private var _container:IWidgetContainer;

        /**
         * The allowclose flag.
         */
        private var _allowclose:Boolean=true;

        /**
         * Callback function on widget maximize.
         */
        private var _maximizeFn:Function;

        /**
         * Callback function on widget restore.
         */
        private var _restoreFn:Function;

        /**
         * Contest id of the only contest that should be shown in drop down.
         */
        private var _tobeLoadedContestId:Number;

        /**
         * Reference to contest service facade webservice.
         */
        private var _contestServiceFacadeWS:WebService=null;

        /**
         * Reference to project service facade webservice.
         */
        private var _projectServiceFacadeWS:WebService=null;

        /**
         * Id of the project for which contests should be downloaded from webservice.
         */
        private var _pid:String=null;

        /**
         * The default contest id.
         */
        private var _defaultContestId:int=0;

        /**
         * Prefix address of the image urls.
         *
         * Server address is retrieved from application parameters.
         */
        private var _imageAddress:String="http://" + Application.application.parameters.hostAddress + "/direct/cockpit/impersonation/cockpitStudio.do?&sbmid=";
        /**
         * Array collection of all contests currently available in this widget.
         */
        private var _contestInfoList:ArrayCollection=new ArrayCollection;

        /**
         * Array collection of all contests currently selectable in this widget. This list gets created after filtering.
         */
        private var _contestList:ArrayCollection=new ArrayCollection();

        /**
         * Array collection of submissions in their rank order.
         */
        private var _rankList:ArrayCollection=new ArrayCollection();

        /**
         * Array collection of all submissions for the current contest.
         */
        private var _submissionList:ArrayCollection=new ArrayCollection();

        /**
         * Total purchased submissions.
         */
        private var _purchaseCount:int=0;

        /**
         * Id of the currently selected contest.
         */
        private var _selectedContestId:int;

        /**
         * Total purchase amount in displayable string format.
         */
        private var _purchaseMoney:String="0.00";

        /**
         * Status of the currently selected contest.
         */
        private var _selectedContestStatus:String="";

        /**
         * Status Id of the currently selected contest.
         */
        private var _selectedContestStatusId:int;

        /**
         * Array collection of the to be purchased submissions.
         */
        private var _purchaseList:ArrayCollection=new ArrayCollection();

        /**
         * Status type dictionary (status id --> status)
         */
        private var _statusTypeDictionary:Dictionary=new Dictionary();

        /**
         * Contest info dictionary (contest id --> contest info)
         */
        private var _contestInfoDictionary:Dictionary=new Dictionary();

        /**
         * Dictionary of active contest status ids.
         */
        private var _activeContestTypeIds:Dictionary=new Dictionary();

        /**
         * Dictionary of past contest status ids.
         */
        private var _pastContestTypeIds:Dictionary=new Dictionary();

        /**
         * Id of the action required contest status.
         */
        private var _actionRequiredContestTypeId:int;

        /**
         * Id of the completed contest status.
         */
        private var _completedContestTypeId:int;

        /**
         * Id of the no winner chosen contest status.
         */
        private var _noWinnerChosenContestTypeId:int;

        /**
         * Id of the in danger contest status.
         */
        private var _inDangerContestTypeId:int;

        /**
         * Id of the terminated contest status.
         */
        private var _terminatedContestTypeId:int;

        /**
         * Id of the draft contest status.
         */
        private var _draftContestTypeId:int;

        /**
         * Id of the scheduled contest status.
         */
        private var _scheduledContestTypeId:int;

        /**
         * Id of the abandoned contest status.
         *
         * @since Cockpit Release Assembly 2
         */
        private var _abandonedContestTypeId:int;

        /**
         * Total purchase amount in number.
         */
        private var _totalPurchaseAmount:Number=0;

        /**
         * Payment reference number of successful payment.
         */
        private var _paymentReferenceNumber:String;

        /**
         * Array collection of to be downloaded submissions.
         */
        private var _downloadList:ArrayCollection=new ArrayCollection();

        /**
         * Array collection of to be checkout submissions.
         *
         * @since 1.0.2
         */
        private var _checkoutList:ArrayCollection=new ArrayCollection();

        /**
         * Array collection of client project names item for purchase order.
         */
        private var _clientProjectNames:ArrayCollection=new ArrayCollection();

        /**
         * Current user.
         */
        private var _username:String=Application.application.parameters.username;

        /**
         * Password of the current user.
         */
        private var _password:String="";

        /**
         * Instance of the money formatter to format the amount.
         */
        private var _moneyFormatter:NumberFormatter;

        /**
         * Callback function to filter contests.
         */
        private var _contestFilterFn:Function;

        /**
         * True if this widget is running in local testing mode.
         * In local testing mode, widget doesn't make any webservice call.
         */
        public var _isLocalTesting:Boolean=false;

        /**
         * Instance of data model class for thumbnail gallery view.
         */
        public var model:ThumbnailGalleryDataModel;
        /**
        * Reference to air viewer - this class provides method to launch a url in external air viewer.
        *
        * @since Cockpit Submission Viewer Widget Enhancement Part 1.
        */
        private var _airViewer:AirViewer;

        /**
         * Introduced new state variable to correctly capture the state of the widget.
         *
         * @since 1.0.1
         */
        private var _isMaxView:Boolean=false;

        /**
         * Flag representing whether milestone ranking is being performed.
         *
         * @since 1.0.2
         */
        private var _isRankingMilestone:Boolean=false;

        /**
         * SubmissionViewerWidgetCodeBehind constructor.
         */
        public function SubmissionViewerWidgetCodeBehind() {
            super();
            this.init();
        }

        /**
         * Initializes this class.
     * Updated for Cockpit Release Assembly 2 [BUGR-1940]
     *    - to correctly handle abandoned contest.
         */
        private function init():void {
            // add list of active contest types here.
            // Active Contests should show contests with a status of:
            //        Active-Public = 2,
            //        Active = 5,
            //        Action Required = 6,
            //        In Danger = 10
            //        or Extended = 12,
            _activeContestTypeIds[2]=true;
            _activeContestTypeIds[5]=true;
            _activeContestTypeIds[6]=true;
            _activeContestTypeIds[10]=true;
            _activeContestTypeIds[12]=true;

            // add list of past contest types here.
            // Past Contests should show contests with a status of:
            //        No Winner Chosen = 7,
            //        Completed = 8,
            //        Insufficient Submissions - Rerun Possible = 11,
            //        or Insufficient Submissions = 13
            //      Abandoned = 14
            _pastContestTypeIds[7]=true;
            _pastContestTypeIds[8]=true;
            _pastContestTypeIds[11]=true;
            _pastContestTypeIds[13]=true;
            _pastContestTypeIds[14]=true;

            // add list of action required contest types here.
            _actionRequiredContestTypeId=6;
            _completedContestTypeId=8;
            _noWinnerChosenContestTypeId=7;
            _inDangerContestTypeId=10;
            _terminatedContestTypeId=17;
            _draftContestTypeId=15;
            _scheduledContestTypeId=9;
            _abandonedContestTypeId=14;

            this._moneyFormatter=new NumberFormatter();
            this._moneyFormatter.precision=2;
            this._moneyFormatter.useThousandsSeparator=true;
            this._moneyFormatter.useNegativeSign=true;

            this.model=new ThumbnailGalleryDataModel();

            //
            // Create new instance of AirViewer.
            //
            // since Cockpit Submission Viewer Widget Enhancement Part 1.
            //
            this._airViewer=new AirViewer();
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

        /**
         * Gets the user name.
         *
         * @return the current user name.
         */
        public function get username():String {
            return this._username;
        }

        /**
         * Sets the user name.
         *
         * @param name the user name.
         */
        public function set username(name:String):void {
            this._username=name;
        }

        /**
         * Gets the password.
         *
         * @return the password.
         */
        public function get password():String {
            return this._password;
        }

        /**
         * Sets the password.
         *
         * @param pwd the password.
         */
        public function set password(pwd:String):void {
            this._password=pwd;
        }

        public function get pid():String {
            return this._pid;
        }

        public function set pid(id:String):void {
            this._pid=id;
        }

        /**
         * Simple setter for the name of this widget.
         *
         * @param name the name of this widget.
         */
        override public function set name(name:String):void {
            this._name=name;
        }

        /**
         * Simple getter for the name of this widget.
         *
         * @return the current set name fo this widget. Could be null if not set.
         */
        override public function get name():String {
            return this._name;
        }

        /**
         * Simple setter for the framework of this widget.
         *
         * @param framework the framework of this widget.
         */
        public function set widgetFramework(framework:IWidgetFramework):void {
            this._widgetFramework=framework;
        }

        /**
         * Simple getter for the framework of this widget.
         *
         * @return the current set framework fo this widget. Could be null if not set.
         */
        public function get widgetFramework():IWidgetFramework {
            return this._widgetFramework;
        }

        /**
         * Simple setter for the allowclose of this widget.
         *
         * @param allow the flag allowclose of this widget.
         */
        public function set allowclose(allow:Boolean):void {
            this._allowclose=allow;
        }

        /**
         * Simple getter for the name of this widget.
         *
         * @return the allowclose flag fo this widget. Could be null if not set.
         */
        public function get allowclose():Boolean {
            return this._allowclose;
        }

        /**
         * Simple setter for the container of this widget.
         *
         * @param container of this widget.
         */
        public function set container(container:IWidgetContainer):void {
            this._container=container;
        }

        /**
         * Simple getter for the container of this widget.
         *
         * @return the container this widget. Could be null if not set.
         */
        public function get container():IWidgetContainer {
            return this._container;
        }

        /**
         * Simple getter for the call back function on widget maximize.
         *
         * @return the call back function on widget maximize.
         */
        public function get maximizeFn():Function {
            return this._maximizeFn;
        }

        /**
         * Simple setter for the call back function on widget maximize.
         *
         * @param fn the call back function on widget maximize.
         */
        public function set maximizeFn(fn:Function):void {
            this._maximizeFn=fn;
        }

        /**
         * Simple getter for the call back function on widget restore.
         *
         * @return the call back function on widget restore.
         */
        public function get restoreFn():Function {
            return this._restoreFn;
        }

        /**
         * Simple setter for the call back function on widget restore.
         *
         * @param fn the call back function on widget restore.
         */
        public function set restoreFn(fn:Function):void {
            this._restoreFn=fn;
        }

        /**
         * Simple getter for default contest id.
         *
         * @return the default contest id.
         */
        public function get defaultContestId():int {
            return _defaultContestId;
        }

        /**
         * Simple setter for the default contest id.
         *
         * @param id the default contest id.
         */
        public function set defaultContestId(id:int):void {
            this._defaultContestId=id;
        }

        /**
         * Simple getter for to be loaded contest id - the only contest that should be shown in widget.
         *
         * @return the to be loaded contest id - the only contest that should be shown in widget.
         */
        public function get tobeLoadedContestId():Number {
            return this._tobeLoadedContestId;
        }

        /**
         * Simple setter for to be loaded contest id - the only contest that should be shown in widget.
         *
         * @param id the to be loaded contest id - the only contest that should be shown in widget.
         */
        public function set tobeLoadedContestId(id:Number):void {
            this._tobeLoadedContestId=id;
        }

        /**
         * Simple getter for the contest service facade webservice.
         *
         * @return the contest service facade webservice.
         */
        public function get contestServiceFacadeWS():WebService {
            return this._contestServiceFacadeWS;
        }

        /**
         * Simple setter for the contest service facade webservice.
         *
         * @param ws the contest service facade webservice.
         */
        public function set contestServiceFacadeWS(ws:WebService):void {
            this._contestServiceFacadeWS=ws;
        }

        /**
         * Simple getter for the project service facade webservice.
         *
         * @return the project service facade webservice.
         */
        public function get projectServiceFacadeWS():WebService {
            return this._projectServiceFacadeWS;
        }

        /**
         * Simple setter for the project service facade webservice.
         *
         * @param ws the project service facade webservice.
         */
        public function set projectServiceFacadeWS(ws:WebService):void {
            this._projectServiceFacadeWS=ws;
        }

        /**
         * Simple getter for the payment reference number - this gets set once the contest/submission has been purchased.
         *
         * @return the payment reference number - this gets set once the contest/submission has been purchased.
         */
        public function get paymentReferenceNumber():String {
            return this._paymentReferenceNumber;
        }

        /**
         * Simple setter for the payment reference number - this gets set once the contest/submission has been purchased.
         *
         * @param number the payment reference number - this gets set once the contest/submission has been purchased.
         */
        public function set paymentReferenceNumber(number:String):void {
            this._paymentReferenceNumber=number;
        }

        /**
         * Simple getter for the contest info list - array collection of all contests that are available in this widget.
         *
         * @return the contest info list - array collection of all contests that are available in this widget.
         */
        public function get contestInfoList():ArrayCollection {
            return this._contestInfoList;
        }

        /**
         * Simple getter for the contest list - array collection of all contests that are visible in this widget.
         *
         * @return the contest info list - array collection of all contests that are visible in this widget.
         */
        public function get contestList():ArrayCollection {
            return this._contestList;
        }

        /**
         * Simple setter for the contest list - array collection of all contests that are visible in this widget.
         *
         * @param list the contest info list - array collection of all contests that are visible in this widget.
         */
        public function set contestList(list:ArrayCollection):void {
            this._contestList=list;
        }

        /**
         * Simple getter for the client project names - client project names items used for purchase order combo box.
         *
         * @return the client project names - client project names items used for purchase order combo box.
         */
        public function get clientProjectNames():ArrayCollection {
            return this._clientProjectNames;
        }

        /**
         * Simple setter for the client project names - client project names items used for purchase order combo box.
         *
         * @param list the client project names - client project names items used for purchase order combo box.
         */
        public function set clientProjectNames(list:ArrayCollection):void {
            this._clientProjectNames=list;
        }

        /**
         * Simple getter for the array collection of to be downloaded submissions.
         *
         * @return the array collection of to be downloaded submissions.
         */
        public function get downloadList():ArrayCollection {
            return this._downloadList;
        }

        /**
         * Simple setter for the array collection of to be downloaded submissions.
         *
         * @param list the array collection of to be downloaded submissions.
         */
        public function set downloadList(list:ArrayCollection):void {
            this._downloadList=list;
        }

        /**
         * Simple getter for the array collection of to be checkout submissions.
         *
         * @return the array collection of to be checkout submissions.
         *
         * @since 1.0.2
         */
        public function get checkoutList():ArrayCollection {
            return this._checkoutList;
        }

        /**
         * Simple setter for the array collection of to be checkout submissions.
         *
         * @param list the array collection of to be checkout submissions.
         *
         * @since 1.0.2
         */
        public function set checkoutList(list:ArrayCollection):void {
            this._checkoutList=list;
        }

        /**
         * Simple getter for the array collection of to be puchased submissions.
         *
         * @return the array collection of to be puchased submissions.
         */
        public function get purchaseList():ArrayCollection {
            return this._purchaseList;
        }

        /**
         * Simple setter for the array collection of to be puchased submissions.
         *
         * @param list the array collection of to be puchased submissions.
         */
        public function set purchaseList(list:ArrayCollection):void {
            this._purchaseList=list;
        }

        /**
         * Simple getter for the array collection of all submissions for the current contest.
         *
         * @return the array collection of all submissions for the current contest.
         */
        public function get submissionList():ArrayCollection {
            return this._submissionList;
        }

        /**
         * Simple setter for the array collection of all submissions for the current contest.
         *
         * @param list the array collection of all submissions for the current contest.
         */
        public function set submissionList(list:ArrayCollection):void {
            this._submissionList=list;
        }

        /**
         * Simple getter for the status name of the current contest.
         *
         * @return the status name of the current contest.
         */
        public function get selectedContestStatus():String {
            return this._selectedContestStatus;
        }

        /**
         * Simple setter for the status name of the current contest.
         *
         * @param status the status name of the current contest.
         */
        public function set selectedContestStatus(status:String):void {
            this._selectedContestStatus=status;
        }

        /**
         * Simple getter for the callback function on contest data load.
         *
         * @return the callback function on contest data load.
         */
        public function get contestFilterFn():Function {
            return this._contestFilterFn;
        }

        /**
         * Simple setter for the callback function on contest data load.
         *
         * @param fn the callback function on contest data load.
         */
        public function set contestFilterFn(fn:Function):void {
            this._contestFilterFn=fn;
        }

        /////

        /**
         * Simple getter for the array collection of currently ranked submissions, in their rank order.
         *
         * @return the array collection of currently ranked submissions, in their rank order.
         */
        public function get rankList():ArrayCollection {
            return this._rankList;
        }

        /**
         * Simple setter for the array collection of currently ranked submissions, in their rank order.
         *
         * @param list the array collection of currently ranked submissions, in their rank order.
         */
        public function set rankList(list:ArrayCollection):void {
            this._rankList=list;
        }

        /**
         * Simple getter for the current selected contest id.
         *
         * @return the current selected contest id
         */
        public function get selectedContestId():int {
            return this._selectedContestId;
        }

        /**
         * Simple setter for the current selected contest id.
         *
         * @param id the current selected contest id
         */
        public function set selectedContestId(id:int):void {
            this._selectedContestId=id;
        }

        /**
         * Simple getter for the status id of the current selected contest.
         *
         * @return the status id of the current selected contest.
         */
        public function get selectedContestStatusId():int {
            return this._selectedContestStatusId;
        }

        /**
         * Simple setter for the status id of the current selected contest.
         *
         * @param id the status id of the current selected contest.
         */
        public function set selectedContestStatusId(id:int):void {
            this._selectedContestStatusId=id;
        }

        /**
         * Simple getter for the status id to status info dictionary.
         *
         * @return the status id to status info dictionary
         */
        public function get statusTypeDictionary():Dictionary {
            return this._statusTypeDictionary;
        }

        /**
         * Simple getter for contest id to contest info dictionary.
         *
         * @return the contest id to contest info dictionary.
         */
        public function get contestInfoDictionary():Dictionary {
            return this._contestInfoDictionary;
        }

        /**
         * Simple getter for the dictionary of active contest status ids.
         *
         * @return the dictionary of active contest status ids.
         */
        public function get activeContestTypeIds():Dictionary {
            return this._activeContestTypeIds;
        }

        /**
         * Simple getter for the dictionary of past contest status ids.
         *
         * @return the dictionary of past contest status ids.
         */
        public function get pastContestTypeIds():Dictionary {
            return this._pastContestTypeIds;
        }

        /**
         * Simple getter for the status id of the action required contest type.
         *
         * @return the status id of the action required contest type.
         */
        public function get actionRequiredContestTypeId():int {
            return this._actionRequiredContestTypeId;
        }

        /**
         * Simple getter for the status id of the completed contest type.
         *
         * @return the status id of the completed contest type.
         */
        public function get completedContestTypeId():int {
            return this._completedContestTypeId;
        }

        /**
         * Simple getter for the status id of the no winner chosen contest type.
         *
         * @return the status id of the no winner chosen contest type.
         */
        public function get noWinnerChosenContestTypeId():int {
            return this._noWinnerChosenContestTypeId;
        }

        /**
         * Simple getter for the status id of the no winner chosen contest type.
         *
         * @return the status id of the no winner chosen contest type.
         * @since Cockpit Release Assembly 2 [BUGR-1940]
         */
        public function get abandonedContestTypeId():int {
            return this._abandonedContestTypeId;
        }

        /**
         * Simple getter for the status id of the in danger contest type.
         *
         * @return the status id of the in danger contest type.
         */
        public function get inDangerContestTypeId():int {
            return this._inDangerContestTypeId;
        }

        /**
         * Simple getter for the status id of the terminated contest type.
         *
         * @return the status id of the terminated contest type.
         */
        public function get terminatedContestTypeId():int {
            return this._terminatedContestTypeId;
        }

        /**
         * Simple getter for the status id of the draft contest type.
         *
         * @return the status id of the draft contest type.
         */
        public function get draftContestTypeId():int {
            return this._draftContestTypeId;
        }

        /**
         * Simple getter for the status id of the scheduled contest type.
         *
         * @return the status id of the scheduled contest type.
         */
        public function get scheduledContestTypeId():int {
            return this._scheduledContestTypeId;
        }

        /**
         * Simple getter for the count of total purchased submissions.
         *
         * @return the count of total purchased submissions.
         */
        public function get purchaseCount():int {
            return this._purchaseCount;
        }

        /**
         * Simple setter for the count of total purchased submissions.
         *
         * @oaram total the count of total purchased submissions.
         */
        public function set purchaseCount(total:int):void {
            this._purchaseCount=total;
        }

        /**
         * Simple getter for the displayable total purchase amount.
         *
         * @return the displayable total purchase amount
         */
        public function get purchaseMoney():String {
            return this._purchaseMoney;
        }

        /**
         * Simple setter for the displayable total purchase amount.
         *
         * @param money the displayable total purchase amount
         */
        public function set purchaseMoney(money:String):void {
            this._purchaseMoney=money;
        }

        /**
         * Simple getter for the total purchase amount.
         *
         * @return the total purchase amount
         */
        public function get totalPurchaseAmount():Number {
            return this._totalPurchaseAmount;
        }

        /**
         * Simple setter for the total purchase amount.
         *
         * @param amount the total purchase amount
         */
        public function set totalPurchaseAmount(amount:Number):void {
            this._totalPurchaseAmount=amount;
        }

        /**
         * Simple getter for the air viewer.
         *
         * @return the air viewer.
         *
         * @since Cockpit Submission Viewer Widget Enhancement Part 1.
         */
        public function get airViewer():AirViewer {
            return this._airViewer;
        }

        /**
         * Simple setter for the air viewer.
         *
         * @param viewer the air viewer.
         *
         * @since Cockpit Submission Viewer Widget Enhancement Part 1.
         */
        public function set airViewer(viewer:AirViewer):void {
            this._airViewer=viewer;
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
         * This will tell the caller if the widget is in minimzie mode or not.
         *
         * Updated for Version 1.0.1
         *    - returning value as per state variable '_isMaxView'
         *
         * @return true if the widget is minimized, false otherwise.
         */
        public function isMinimized():Boolean {
            return !this._isMaxView;
        }

        /**
         * This will tell the caller if the widget is in maximize mode or not.
         *
         * Updated for Version 1.0.1
         *    - returning value as per state variable '_isMaxView'
         *
         * @return true if the widget is maximized, false otherwise.
         */
        public function isMaximized():Boolean {
            return this._isMaxView;
        }

        /**
         * Simple getter for the ranking milestone flag
         *
         * @return the ranking milestone flag
         *
         * @since 1.0.2
         */
        public function get rankingMilestone():Boolean {
            return this._isRankingMilestone;
        }

        /**
         * Simple setter for the ranking milestone flag
         *
         * @param value the ranking milestone flag value to set
         *
         * @since 1.0.2
         */
         public function set rankingMilestone(value:Boolean):void {
            this._isRankingMilestone=value;
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
         * Set the attributes for this widget based on the given Map.
         *
         * @param map the attributes for this widget. Cannot be null.
         *
         * @throws ArgumentError if the input is null.
         */
        public function setAttributes(map:Dictionary):void {
            if (map["contestid"]) {
                this.defaultContestId=map["contestid"];
            }
            if (map.hasOwnProperty("pid")) {
                this.pid=map["pid"];
                trace("-------------------------------------------------- Setting pid: " + this.pid);
            }

            // Module Cockpit My Projects Release Assembly 1
            // 1.1.7
            // Submission Viewer widget should only have the current contest in the drop down.
            if (map.hasOwnProperty("toBeLoadedContestId")) {
                this.tobeLoadedContestId=map["toBeLoadedContestId"];
                trace("-------------------------------------------------- Setting tobeLoadedContestId: " + this.tobeLoadedContestId);
            }
            if (map.hasOwnProperty("reload")) {
                showLoadingProgress();

                // reload the widget data.
                if (pid) {
                    this.contestServiceFacadeWS.getContestDataOnlyByPID(pid);
                } else {
                    this.contestServiceFacadeWS.getContestDataOnly();
                }
            }
        }

        /**
         * This action will reload this widget.
         */
        public function reload():void {
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
         *
         * Updated for Version 1.0.1
         *    - setting variable '_isMaxView' to true.
         */
        public function minimize():void {
            this._isMaxView=false;
        }

        /**
         * This action will restpre this widget (for example from a menu bar).
         *
         * Updated for Version 1.0.1
         *    - setting variable '_isMaxView' to true.
         */
        public function restore():void {
            this._isMaxView=false;
            if (this.restoreFn != null) {
                this.restoreFn();
            }
        }

        /**
         * This action will maximize this widget.
         *
         * Updated for Version 1.0.1
         *    - setting variable '_isMaxView' to true.
         */
        public function maximize():void {
            this._isMaxView=true;
            if (this.maximizeFn != null) {
                this.maximizeFn();
            }
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
         * Shows the loading progress window for this widget.
         *
         * Loading progress window is a modal window that hides the widget through transparent layer
         * and it keeps showing animation.
         *
         * Loading progress window gets cancelled after some time automatically.
         */
        public function showLoadingProgress():void {
            ProgressWindowManager.showProgressWindow(this.container);
        }

        /**
         * Hides the loading progress window for this widget.
         *
         * Loading progress window is a modal window that hides the widget through transparent layer
         * and it keeps showing animation.
         *
         * Loading progress window gets cancelled after some time automatically.
         */
        public function hideLoadingProgress():void {
            ProgressWindowManager.hideProgressWindow(this.container);
        }

        /**
         * Webservice Callback function for handling the contest load.
         *
         * @param event webservice result event.
         */
        public function handleResult(event:ResultEvent=null):void {
            if (event != null && event.result != null) {

                this.contestInfoList.removeAll();

                var contests:ArrayCollection=new ArrayCollection();

                if (event.result is ArrayCollection) {
                    contests=event.result as ArrayCollection;
                } else {
                    contests=new ArrayCollection();
                    contests.addItem(event.result);
                }

                if (!contests.sort) {
                    var sort:Sort=new Sort();
                    sort.compareFunction=compareName;
                    contests.sort=sort;
                }

                contests.refresh();

                var selectIdx:int=-1;
                for (var i:int=0; i < contests.length; i++) {
                    var cinfo:Object=new Object();
                    var contestInfo:ObjectProxy=new ObjectProxy(cinfo);
                    var item:*=contests[i];

                    contestInfo.name=item.name;
                    contestInfo.id=item.contestId;
                    contestInfo.statusId=item.statusId;
                    contestInfo.permission=item.permission;

                    // add multi round information
                    contestInfo.multiRound=item.multiRound;
                    contestInfo.milestonePrizeAmount=item.milestonePrizeAmount;
                    contestInfo.numberOfMilestonePrizes=item.numberOfMilestonePrizes;
                    contestInfo.milestoneDate=DateUtil.parseFromString(item.milestoneDate);

                    // add prizes to contest info.
                    contestInfo.prizes=new ArrayCollection();

                    if (item.prizes && item.prizes is ArrayCollection) {
                        var prizes:ArrayCollection=item.prizes as ArrayCollection;

                        for (var j:int=0; j < prizes.length; j++) {
                            trace("Adding prize: " + prizes[j] + " to: " + contestInfo.name);
                            contestInfo.prizes.addItem(prizes[j] as Number);
                        }
                    }

                    // Module Cockpit My Projects Release Assembly 1
                    // 1.1.7
                    // show only the toBeLoadedContestId, as passed from my project widget.
                    if (this.activeContestTypeIds[contestInfo.statusId] == true || this.pastContestTypeIds[contestInfo.statusId] == true) {
                        if (!this.tobeLoadedContestId || contestInfo.id == this.tobeLoadedContestId) {

                            this.contestInfoList.addItem(contestInfo);
                            this.contestInfoDictionary[contestInfo.id]=contestInfo;
                        }
                    } else if (!this.tobeLoadedContestId || contestInfo.id == this.tobeLoadedContestId) {
                        this.contestInfoList.addItem(contestInfo);
                        this.contestInfoDictionary[contestInfo.id]=contestInfo;
                    }
                }

                if (this.contestFilterFn != null) {
                    this.contestFilterFn();
                }
            }
        }

        /**
         * Webservice Callback function for handling the status list load.
         *
         * Updated for Version 1.0.1
         *    - if status list is loaded later then contest status id is set here.
         *
         * @param event webservice result event.
         */
        public function handleStatusList(event:ResultEvent=null):void {
            if (event != null && event.result != null) {
                for each (var item:*in event.result as ArrayCollection) {
                    this.statusTypeDictionary[item.statusId as int]=item;
                }
            }

            if (this.selectedContestId) {
                if (this.contestInfoDictionary) {
                    var statusId:int=this.contestInfoDictionary[this.selectedContestId].statusId;
                    this.selectedContestStatusId=statusId;

                    if (this.statusTypeDictionary) {
                        this.selectedContestStatus=this.statusTypeDictionary[statusId].name;
                    }
                }
            }
        }

        /**
         * Webservice Callback function for handling the submissions load.
         *
         * @param event webservice result event.
         */
        public function handleSubResult(event:ResultEvent=null):void {
            hideLoadingProgress();

            // clear rank list first.
            this.rankList.removeAll();

            for (var j:int=0; j < 5; j++) {
                this.rankList.addItem(new Object());
            }

            this.submissionList=new ArrayCollection();


            var sort:Sort=new Sort();
            sort.compareFunction=compareNumber;
            this.submissionList.sort=sort;
            if (event && event.result != null) {
                var submissions:ArrayCollection=new ArrayCollection();
                if (event.result is ArrayCollection) {
                    submissions=event.result as ArrayCollection;
                } else {
                    submissions.addItem(event.result);
                }

                for (var i:int=0; i < submissions.length; i++) {
                    var sub:Object=new Object();

                    sub.parentModel=this.model;

                    sub.id=submissions[i].submissionId;
                    sub.submitterId=submissions[i].submitterId;
                    sub.passedScreening=submissions[i].passedScreening;
                    sub.submittedDate=DateUtil.parseFromString(submissions[i].submittedDate);
                    sub.awardMilestonePrize=submissions[i].awardMilestonePrize;

                    trace("---------- this.selectedContestId: " + this.selectedContestId);

                    // placement in submission data is mapped to rank.
                    if (this.activeContestTypeIds[this.contestInfoDictionary[this.selectedContestId].statusId] == true) {
                        if (submissions[i].userRank > 0) {
                            sub.rank=submissions[i].userRank;
                        } else {
                            sub.rank="";
                        }
                    } else {
                        if (submissions[i].placement > 0) {
                            sub.rank=submissions[i].placement;
                        } else {
                            sub.rank="";
                        }
                    }

                    var thumbVal:int=0;
                    if (submissions[i].feedbackThumb) {
                        thumbVal=submissions[i].feedbackThumb;
                    }
                    sub.upDown=thumbVal > 0 ? "up" : (thumbVal < 0 ? "down" : "");

                    sub.feedback=submissions[i].feedbackText;

                    //
                    // Represents the artifact count of submissions.
                    // In multi image submission artifact count > 1
                    //
                    // since: Cockpit Submission Viewer Widget Enhancement Part 1.
                    //
                    sub.artifactCount=submissions[i].artifactCount;

                    //
                    // A submission having more than 1 artifact is considered as multi image submission.
                    //
                    // since: Cockpit Submission Viewer Widget Enhancement Part 1.
                    //
                    if (sub.artifactCount > 1) {
                        sub.multi=true;
                    } else {
                        sub.multi=false;
                    }

                    //
                    // Represents the submission url of submission.
                    // A submission which is openable in Air Viewer would have valid submissionUrl.
                    //
                    // since: Cockpit Submission Viewer Widget Enhancement Part 1.
                    //
                    if (submissions[i].hasOwnProperty("submissionUrl") && submissions[i].submissionUrl && submissions[i].submissionUrl != "") {
                        sub.submissionUrl=submissions[i].submissionUrl;
                    }

                    sub.thumbnail=this._imageAddress + sub.id + "&sbt=thumb";

                    // even in case of multi image submission, this is valid
                    // and this would point to first (0th index) image of the submission.
                    sub.fullsizepreview=this._imageAddress + sub.id + "&sbt=full";

                    trace("For submission: " + sub.id + ", Image full path: " + sub.fullsizepreview);

                    //
                    // In case of multi image submission we also populate 'fullsizepreviewList'
                    //
                    // since: Cockpit Submission Viewer Widget Enhancement Part 1.
                    //
                    if (sub.multi) {
                        sub.fullsizepreviewList=new ArrayCollection();
                        for (var k:int=0; k<sub.artifactCount; k++) {
                            //
                            // - Updated the submission image index parameter name that matches the url on production - 'sfi'
                            // - Image index on production is 1 based.
                            // since: Complex Submission Viewer Assembly - Part 2
                            //
                            // image index is 1 based.
                            var path:String=this._imageAddress + sub.id + "&sbt=full" + "&sfi=" + (k+1);
                            sub.fullsizepreviewList.addItem(path);
                            trace("For submission: " + sub.id + ", Image full path: " + path);
                        }
                    }

                    sub.submissionContent=submissions[i].submissionContent;

                    // BUGR-1169: separate db price from app price.
                    sub.savedPrice=submissions[i].price;

                    sub.markedForPurchase=Boolean(submissions[i].markedForPurchase);

                    sub.paidFor=Boolean(submissions[i].paidFor);

                    var contestInfo:*=this.contestInfoDictionary[this.selectedContestId];

                    trace("ContestInfo: " + contestInfo + ", selectedContestId: " + this.selectedContestId);

                    var prizes:ArrayCollection=contestInfo.prizes as ArrayCollection;

                    trace("Prizes: " + prizes + ", selectedContestId: " + this.selectedContestId);

                    // BUGR-1169: give more priority to savedPrice (db price).
                    if (sub.savedPrice && sub.savedPrice > 0 && (sub.markedForPurchase || sub.paidFor)) {
                        sub.price=sub.savedPrice;
                    } else {
                        if (prizes) {
                            if (sub.rank && sub.rank > 0) {
                    // retrieve the price from contest info's prizeList.
                    if (prizes.length < sub.rank) {
                     if (sub.markedForPurchase) {
                         // if prizes are not there, then use the 2nd place prize

                         if (prizes.length > 1 && prizes[1] > 0) {
                         sub.price=prizes[1];
                         }
                    } else {

                         // do nothing here.
                    }
                    } else if (prizes[sub.rank - 1] > 0) {
                     // here I override the earlier set markedForPurchase field.
                     // since prize list is there for the current submission
                     // i do not need markedForPurchase field here.
                     sub.markedForPurchase=false;

                     sub.price=prizes[sub.rank - 1];
                     } else  if (sub.markedForPurchase) {
                         // if prizes are not there, then use the 2nd place prize

                         if (prizes.length > 1 && prizes[1] > 0) {
                         sub.price=prizes[1];
                         }
                    }
                 } else if (sub.markedForPurchase) {
                    // if submission is not ranked and markedForPurchase is there,

                    // then price must be equal to 2nd place prize.
                    if (prizes.length > 1 && prizes[1] > 0) {
                    sub.price=prizes[1];
                    }
                 }
                        }
                    }

                    if (prizes) {
                        sub.mustPurchase=sub.rank && sub.rank > 0 && sub.rank <= prizes.length && sub.price && sub.price > 0;
                    }
                    sub.purchased=sub.markedForPurchase || sub.mustPurchase || sub.paidFor;

                    var subProxy:ObjectProxy=new ObjectProxy(sub);

                    var thumbChangeWatcherFn:Function = function(e:Event):void {
                        if (e && e.target) {
                            trace("thumbChangeWatcherFn --- SubProxy: " + e.target.id);
                            saveSubmissionsFeedback(e.target);
                        }
                    }
                    // initialize a change watcher for the upDown property.
                    var thumbChangeWatcher:ChangeWatcher=ChangeWatcher.watch(subProxy, "upDown", thumbChangeWatcherFn);

                    var rankChangeWatcherFn:Function = function(e:Event):void {
                        if (e && e.target) {
                            trace("rankChangeWatcherFn --- SubProxy: " + e.target.id);
                            saveSubmissionRank(e.target);
                        }
                    }
                    // initialize a change watcher for the upDown property.
                    var rankChangeWatcher:ChangeWatcher=ChangeWatcher.watch(subProxy, "rank", rankChangeWatcherFn);

                    // add a watcher for the milestone awards also
                    ChangeWatcher.watch(subProxy, "awardMilestonePrize", rankChangeWatcherFn);

                    this.submissionList.addItem(subProxy);

                    if ((sub.rank && sub.rank > 0 && !rankingMilestone) || (rankingMilestone && sub.awardMilestonePrize)) {
                        updateRankList(subProxy);
                    }

                    trace("Added submission to list: {" + sub.id + "," + sub.price + "," + sub.rank + "," + sub.markedForPurchase + "," + sub.purchased + "}");
                }
            }

        }

        /**
         * Webservice Fault Callback function for handling the contest load.
         *
         * @param event webservice result event.
         */
        public function handleFail(event:FaultEvent):void {
            hideLoadingProgress();
            handleResult();
        }

        /**
         * Webservice Fault Callback function for handling the submission load.
         *
         * @param event webservice result event.
         */
        public function handleSubFail(event:FaultEvent):void {
            hideLoadingProgress();
            handleSubResult();
        }

        /**
         * Alphabetically compares name attribute of the given object.
         *
         * @param a object one.
         * @param b object two.
         * @param fields currently ignored field.
         */
        private function compareName(a:Object, b:Object, fields:Array=null):int {
            return ObjectUtil.stringCompare(a.name, b.name, true);
        }

        /**
         * Numerically compares rank attribute of the given object.
         *
         * @param a object one.
         * @param b object two.
         * @param fields currently ignored field.
         */
        private function compareNumber(a:Object, b:Object, fields:Array=null):int {
            var rank1:String=a.rank;
            var rank2:String=b.rank;
            if (!rank1 && !rank2) {
                return 0;
            } else if (!rank1) {
                return 1;
            } else if (!rank2) {
                return -1;
            } else {
                var r1:int=parseInt(rank1);
                var r2:int=parseInt(rank2);
                if (r1 > r2) {
                    return 1;
                } else if (r2 > r1) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }

        /**
         * Updates the rank list with rank of specified item.
         *
         * @param item the submission item for which rank list need to be updated.
         */
        public function updateRankList(item:Object):void {
            if (!item || !item.id) {
                return;
            }

            // The concept of ranking is not exactly the same for milestone prizes. The ranking really doesn't matter
            // for now, so the submission is assigned to the first available spot.
            var emptySpaceIndex:int=0;
            if (this.rankingMilestone) {
                if (item.awardMilestonePrize) {
                    // add it to the first available rank
                    for (; emptySpaceIndex < 5 && this.rankList.getItemAt(emptySpaceIndex) &&
                        this.rankList.getItemAt(emptySpaceIndex).hasOwnProperty("id"); emptySpaceIndex++) {
                    }

                    this.rankList.setItemAt(item, emptySpaceIndex);
                } else {
                    removeFromRankList(item);
                }
            } else {
                if (item.rank && (item.rank is Number || item.rank.length > 0)) {
                    var index:int=parseInt(item.rank) - 1;
                    //trace("---------------------------------- Submission Viewer RANK: " + item.id + "," + item.rank + "," + index);
                    if (index > 4 || index < 0) {
                        // placement values can be more than 5 also.
                        return;
                    }

                    // get if there is already an object with the given rank.
                    var obj:Object=this.rankList.getItemAt(index);
                    if (obj && obj.hasOwnProperty("id") && obj.hasOwnProperty("rank") && item.id != obj.id) {
                        obj.rank="";
                        obj.mustPurchase=false;
                        obj.purchased=obj.markedForPurchase || obj.mustPurchase;
                        obj.price=0;
                    }

                    //trace("---------------------------------- Setting item at: " + item.id + "," + item.rank + "," + index);
                    this.rankList.setItemAt(item, index);
                } else {
                    removeFromRankList(item);
                }
            }
        }

        /**
         * Removes the item from rank list.
         *
         * @param item the submission item for which rank list need to be updated.
         */
        public function removeFromRankList(item:Object):void {
            if (!item || !item.id) {
                return;
            }

            var i:int = 0;
            for each (var o:Object in this.rankList) {
                if (o && o.id == item.id) {
                    //trace("Setting item NULL at i: " + i);
                    this.rankList.setItemAt(new Object(), i);
                    break;
                }

                i++;
            }
        }

        /**
         * Loads the client project names from webservice.
         */
        private function getClientProjectsByUser():void {
            // add an empty item.
            var item:Object=null;

            item=new Object();
            item.label="";
            item.data=null;
            this.clientProjectNames.addItem(item);

            // get client projects by user.
            var header:SOAPHeader=getHeader(this.username, this.password);
            this.projectServiceFacadeWS.clearHeaders();
            this.projectServiceFacadeWS.addHeader(header);
            var getClientProjectsByUserOp:AbstractOperation=this.projectServiceFacadeWS.getOperation("getClientProjectsByUser");
            if (getClientProjectsByUserOp) {
                getClientProjectsByUserOp.addEventListener("result", getClientProjectsHandler);
                getClientProjectsByUserOp.send();
            }
        }

        /**
         * Webservice Callback function for handling the load of client projects.
         *
         * @param event webservice result event.
         */
        private function getClientProjectsHandler(e:ResultEvent):void {
            trace("getClientProjectsHandler: " + e + ", " + e.result);
            if (e && e.result) {

                if (e.result is ArrayCollection) {
                    var results:ArrayCollection=e.result as ArrayCollection;
                    for (var i:int=0; i < results.length; i++) {
                        var result1:*=results[i];
                        // add client project name
                        var item1:Object=new Object();
                        var id1:Number = result1.id;
                        var name1:String = result1.name;
                        var poNumber1:String = result1.pOBoxNumber;
                        var clientId1:Number = result1.client.id;
                        var clientName1:String = result1.client.name;
                        item1.label=name1;
                        item1.data = new Object();
                item1.data.poNumber = poNumber1;
                item1.data.projectName = name1;
                item1.data.projectId = id1;
                item1.data.clientId = clientId1;
                item1.data.clientName = clientName1;
                        this.clientProjectNames.addItem(item1);
                    }
                } else {
                    var result2:*=e.result;
                    // add client project name
                    var item2:Object=new Object();
                    var id2:Number = result2.id;
                    var name2:String = result2.name;
                    var poNumber2:String = result2.pOBoxNumber;
                    var clientId2:Number = result2.client.id;
                    var clientName2:String = result2.client.name;
                    item2.label=name2;
                    item2.data = new Object();
            item2.data.poNumber = poNumber2;
            item2.data.projectName = name2;
            item2.data.projectId = id2;
            item2.data.clientId = clientId2;
            item2.data.clientName = clientName2;
                    this.clientProjectNames.addItem(item2);
                }
            }
        }

        /**
         * This updates the purchase amount, purchase count, purchase list, download list and checkout list.
         *
         * It simply iterates over all submissions and recalculates purchase amount, purchase count, purchase list, download list.
         * A submission is supposed to be purchased if its 'purchased' property is set to 'true'
         */
        public function updatePurchase():void {
            //trace("Updating purchase info");
            this.purchaseCount=0;
            var totalMoney:Number=0;

            this.purchaseList=new ArrayCollection();
            this.downloadList=new ArrayCollection();
            var checkoutList2:ArrayCollection=new ArrayCollection();

            for each (var item:Object in this.submissionList) {
                //trace("Purchase item: {" + item.id + "," + item.rank + "," + item.markedForPurchase + "," + item.price + "," + item.purchased + "}");
                var addedToDownloadList:Boolean=false;

                if ((item.rank && item.rank > 0) || item.purchased) {
                    this.purchaseList.addItem(item);
                }

                if (item.purchased && item.purchased == true) {
                    this.purchaseCount++;
                    totalMoney+=item.price;
                    this.downloadList.addItem(item);
                    checkoutList2.addItem(item);
                    addedToDownloadList=true;
                }

                if (item.awardMilestonePrize) {
                    if (item.passedScreening) {
                        var milestonePrize:Object=new Object();
                        milestonePrize.id=item.id;
                        milestonePrize.rank=0;
                        milestonePrize.awardMilestonePrize=true;
                        milestonePrize.price = this.contestInfoDictionary[this.selectedContestId].milestonePrizeAmount;
                        checkoutList2.addItem(milestonePrize);
                        this.purchaseList.addItem(milestonePrize);
                    }

                    totalMoney+=this.contestInfoDictionary[this.selectedContestId].milestonePrizeAmount;
                }
            }

            this.purchaseMoney=_moneyFormatter.format(totalMoney);
            this.totalPurchaseAmount=totalMoney;
            var sort:Sort=new Sort();
            sort.compareFunction=compareNumber;
            this.downloadList.sort=sort;
            this.downloadList.refresh();

            checkoutList2.sort=sort;
            checkoutList2.refresh();
            this.checkoutList = checkoutList2;

            this.purchaseList.sort=sort;
            this.purchaseList.refresh();
        }

        /**
         * Updates contest abandon to webservice.
         */
        public function updateAbandonToWS():void {
            this.contestInfoDictionary[this.selectedContestId].statusId=this.noWinnerChosenContestTypeId;

            //trace("Updating contest status for: " + this.selectedContestId + " to statusId: " + this.noWinnerChosenContestTypeId);

            this.contestServiceFacadeWS.updateContestStatus(this.selectedContestId, this.noWinnerChosenContestTypeId);
        }

        /**
         * Function that decides if the milestone prize should be awarded for a particular submission.
         *
         * The submitter should have submitted a passing screening submission after the milestone date to be
         * eligible for the prize.
         *
         * @param originalSubmitterId the submitter id
         * @return true if the milestone prize should be awarded, false otherwise.
         */
 /*       private function shouldAwardMilestonePrize(originalSubmitterId:int):Boolean {
            var found:Boolean=false;
            for (var i:int; i < this.submissionList.length && !found; i++) {
                var item:Object=this.submissionList[i];

                if (item.submitterId == originalSubmitterId && item.passedScreening &&
                    item.submittedDate > this.contestInfoDictionary[this.selectedContestId].milestoneDate) {
                    found = true;
                }
            }
            return found;
        }*/

        /**
         * Updates contest / submisison purchase to websrvice.
         *
         * @param checkoutPage the ui page from where to read the purchase data.
         */
        public function updatePurchaseToWS(checkoutPage:SubmissionsCheckoutPage):void {


            var header:SOAPHeader=getHeader(this.username, this.password);

            var completedContestData:CompletedContestData=new CompletedContestData();
            completedContestData.contestId=this.selectedContestId;

            completedContestData.submissions=new Array();

            for (var i:int; i < this.submissionList.length; i++) {
                var item:Object=this.submissionList[i];
                var shouldAdd:Boolean=false;
                var submissionPaymentData:SubmissionPaymentData=new SubmissionPaymentData();
                submissionPaymentData.id=item.id;
                if (item.rank && item.rank > 0) {
                    submissionPaymentData.rank=item.rank;
                    shouldAdd=true;
                }

                if (item.awardMilestonePrize) {
                    // only add if there is there is a submission after milestone date from this same submitter that passed screening.
                    if (item.passedScreening) {
                        submissionPaymentData.awardMilestonePrize=true;
                        shouldAdd=true;
                    }
                }

                if (item.purchased) {
                    submissionPaymentData.amount=item.price;
                    shouldAdd=true;
                }

                if (shouldAdd) {
                    completedContestData.submissions.push(submissionPaymentData);
                }
            }

            this.contestServiceFacadeWS.clearHeaders();
            this.contestServiceFacadeWS.addHeader(header);
            if (checkoutPage.pay.selectedIndex == 1) {
                var creditCardPaymentData:CreditCardPaymentData=new CreditCardPaymentData();
                creditCardPaymentData.type="PayPalCreditCard";
                creditCardPaymentData.cardNumber=checkoutPage.wf1.cardNum.text;
                creditCardPaymentData.cardType=checkoutPage.wf1.cardTypeCombo.selectedItem.data;
                creditCardPaymentData.cardExpiryMonth=checkoutPage.wf1.month.selectedItem.data;
                creditCardPaymentData.cardExpiryYear=checkoutPage.wf1.year.selectedItem.data;
                creditCardPaymentData.firstName=checkoutPage.wf1.cardname.text;
                creditCardPaymentData.lastName=checkoutPage.wf1.cardname.text;
                creditCardPaymentData.address=checkoutPage.wf1.address.text;
                creditCardPaymentData.city=checkoutPage.wf1.city.text;
                creditCardPaymentData.state=checkoutPage.wf1.state.text;
                creditCardPaymentData.zipCode=checkoutPage.wf1.code.text;
                creditCardPaymentData.country=checkoutPage.wf1.country.text;
                creditCardPaymentData.phone=checkoutPage.wf1.phone.text;
                creditCardPaymentData.email=checkoutPage.wf1.email.text;

                creditCardPaymentData.ipAddress="10.10.10.10";
                creditCardPaymentData.sessionId="";

                // set the amount.
                creditCardPaymentData.amount=this.totalPurchaseAmount.toString();

                this.contestServiceFacadeWS.processSubmissionCreditCardPayment(completedContestData, creditCardPaymentData);
            } else if (checkoutPage.pay.selectedIndex == 2) {
                var purchaseOrderPaymentData:TcPurhcaseOrderPaymentData=new TcPurhcaseOrderPaymentData();
                purchaseOrderPaymentData.type="TCPurchaseOrder";
                purchaseOrderPaymentData.poNumber=checkoutPage.wf1.order.selectedItem.data.poNumber;
                purchaseOrderPaymentData.projectId=checkoutPage.wf1.order.selectedItem.data.projectId;
                purchaseOrderPaymentData.projectName=checkoutPage.wf1.order.selectedItem.data.projectName;
                purchaseOrderPaymentData.clientId=checkoutPage.wf1.order.selectedItem.data.clientId;
                purchaseOrderPaymentData.clientName=checkoutPage.wf1.order.selectedItem.data.clientName;

                this.contestServiceFacadeWS.processSubmissionPurchaseOrderPayment(completedContestData, purchaseOrderPaymentData);
            }

            showLoadingProgress();
        }

        /**
         * Gets the webservice security header for given user name and password.
         *
         * @param username the user name.
         * @param password the password.
         */
        public function getHeader(username:String, password:String):SOAPHeader {
            var userToken:String="UsernameToken-" + Math.round(Math.random() * 999999).toString();
            var headerXML:XML=<wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                   <wsse:UsernameToken wsu:Id={userToken} xmlns:wsu='http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd'>
                   <wsse:Username>{username}</wsse:Username>
                   <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest">{password}</wsse:Password>
                   </wsse:UsernameToken>
                 </wsse:Security>;
            var header:SOAPHeader=new SOAPHeader(WSSE_SECURITY, headerXML);
            return header;
        }

        /**
         * Loads the contest data through webservice.
         */
        public function loadData():void {
            if (!_isLocalTesting) {
                showLoadingProgress();

                var header:SOAPHeader=getHeader(username, password);
                this.contestServiceFacadeWS.clearHeaders();
                this.contestServiceFacadeWS.addHeader(header);

                this.contestServiceFacadeWS.getStatusList();
                this.contestServiceFacadeWS.getContestDataOnly();

                getClientProjectsByUser();
            } else {

                var statuses:ArrayCollection=new ArrayCollection();
                var status:Object=new Object();
                status.name="Action Required"
                status.statusId=6;
                statuses.addItem(status);

                status=new Object();
                status.name="No winner chosen"
                status.statusId=7;
                statuses.addItem(status);

                status=new Object();
                status.name="Completed"
                status.statusId=8;
                statuses.addItem(status);

                status=new Object();
                status.name="Abandoned"
                status.statusId=14;
                statuses.addItem(status);

                handleStatusList(new ResultEvent("STATUS_TYPES", false, true, statuses));

                var contests:ArrayCollection=new ArrayCollection();
                var contest:Object=new Object();
                contest.name="Active Contest 1";
                contest.contestId=12345;
                contest.statusId=6;

                contest.prizes=new ArrayCollection();
                contest.prizes.addItem(1000);
                contest.prizes.addItem(500);
                contest.prizes.addItem(0);
                contest.prizes.addItem(0);
                contest.prizes.addItem(0);
                contests.addItem(contest);

                contest=new Object();
                contest.name="Active Contest 2";
                contest.contestId=12346;
                contest.statusId=6;
                contest.prizes=new ArrayCollection();
                contest.prizes.addItem(1000);
                contest.prizes.addItem(500);
                contest.prizes.addItem(0);
                contest.prizes.addItem(0);
                contest.prizes.addItem(0);
                contests.addItem(contest);

                contest=new Object();
                contest.name="Abandoned Contest 1";
                contest.contestId=12347;
                contest.statusId=14;
                contest.prizes=new ArrayCollection();
                contest.prizes.addItem(1000);
                contest.prizes.addItem(500);
                contest.prizes.addItem(0);
                contest.prizes.addItem(0);
                contest.prizes.addItem(0);
                contests.addItem(contest);

                contest=new Object();
                contest.name="Completed Contest 1";
                contest.contestId=12348;
                contest.statusId=8;
                contest.prizes=new ArrayCollection();
                contest.prizes.addItem(1000);
                contest.prizes.addItem(500);
                contest.prizes.addItem(0);
                contest.prizes.addItem(0);
                contest.prizes.addItem(0);
                contests.addItem(contest);

                handleResult(new ResultEvent("CONTESTS", false, true, contests));
            }
        }

        /**
         * Loads the submission data through webservice.
         *
         * @param callbackFn the callback function to be called on submission data load.
         */
        public function loadSubmissions(callbackFn:Function):void {
            if (this.submissionList && this.submissionList.length > 0) {
                this.submissionList.removeAll();
            }

            if (this.contestList && this.contestList.length > 0) {
                var wrapperFn:Function=function(event:ResultEvent):void {
                    handleSubResult(event);
                    callbackFn();
                }

                if (!_isLocalTesting) {
                    showLoadingProgress();

                    var header:SOAPHeader=getHeader(username, password);
                    this.contestServiceFacadeWS.clearHeaders();
                    this.contestServiceFacadeWS.addHeader(header);
                    var retrieveSubmissionsForContest:AbstractOperation=this.contestServiceFacadeWS.getOperation("retrieveSubmissionsForContest");
                    if (retrieveSubmissionsForContest) {
                        retrieveSubmissionsForContest.addEventListener("result", wrapperFn);
                        retrieveSubmissionsForContest.send(this.selectedContestId);
                    }
                } else {
                    var submissionArray:ArrayCollection=new ArrayCollection();
                    var sub:Object;

                    //
                    // Setting test data.
                    //
                    // since Cockpit Submission Viewer Widget Enhancement Part 1.
                    //
                    sub=new Object();
                    sub.submissionId=24053;
                    sub.placement=2;
                    sub.feedbackText="It's feedback text for 24053. Thumb is up.";
                    sub.feedbackThumb=1;
                    sub.artifactCount=10;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24054;
                    sub.feedbackText="It's feedback text for 24054. Thumb is down.";
                    sub.feedbackThumb=-1;
                    sub.placement=1;
                    sub.submissionUrl="http://www.google.com";
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24055;
                    sub.placement=3;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24056;
                    sub.placement=4;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24057;
                    sub.placement=5;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24058;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24059;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24060;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24061;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24062;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24063;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24064;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24065;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24066;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24067;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24068;
                    submissionArray.addItem(sub);

                    sub=new Object();
                    sub.submissionId=24069;
                    submissionArray.addItem(sub);

                    wrapperFn(new ResultEvent("SUBMISSIONS", false, true, submissionArray));
                }
            }
        }

        /**
         * Determines if all required submissions has been ranked or not.
         *
         * If submission has 5 or more submissions than all atleast 5 submissions should be ranked.
         *
         * @return true if all required submissions has been ranked, else false.
         */
        public function areProperRanking():Boolean {
            var submissionsCount:int=this.submissionList.length;
            var requiredRatingCount:int=Math.min(5, submissionsCount);
            var rankingCnt:int=0;
            var rankBitmap:int=0;


            for (var i:int=0; i < this.rankList.length; i++) {
                if (this.rankList[i] && this.rankList[i].rank && this.rankList[i].rank > 0) {
                    rankBitmap |= Math.pow(2, this.rankList[i].rank - 1);
                    rankingCnt++;
                }
            }

            // check there are no "holes" in the ranklist
            if (rankBitmap != Math.pow(2, rankingCnt) -1) {
             return false;
            }

            if (rankingCnt == requiredRatingCount) {
                return true;
            }

            return false;
        }


        /**
         * Callback function on sorting by rank.
         */
        private function compareRank(a:Object, b:Object, fields:Array=null):int {
            if (!a.rank && !b.rank)
            {
                return 0;
            }
            if (!a.rank)
            {
                return 1;
            }
            if (!b.rank)
            {
                return -1;
            }

            return ObjectUtil.numericCompare(a.rank, b.rank);
        }

        /**
         * Saves given submission rank to webservice.
         */
        public function saveSubmissionRank(item:Object):void {
            var header:SOAPHeader=getHeader(this.username, this.password);
            this.contestServiceFacadeWS.clearHeaders();
            this.contestServiceFacadeWS.addHeader(header);
            var rankSubmissionsOp:AbstractOperation=this.contestServiceFacadeWS.getOperation("updateSubmissionUserRank");
            if (rankSubmissionsOp) {
                //trace("Ranking submissions: " + submissionList);
                var rank:Number=0;
                if (item.rank && item.rank != "" && item.rank is Number) {
                    rank=item.rank as Number;
                }

                trace("------------------------- Saving rank: " + rank + ", for submission: " + item.id);

                if (rankingMilestone) {
                    // the underlying service uses rank, so 0 non't award while 1 will
                    if (item.awardMilestonePrize) {
                        rank = 1;
                    } else {
                        rank = 0;
                    }
                }
                rankSubmissionsOp.send(item.id, rank, rankingMilestone);
            }
        }

        /**
         * Saves given submisison feedback and thumb to the webservice.
         *
         * @param item submission item
         */
        public function saveSubmissionsFeedback(item:Object):void {
            if (_isLocalTesting) {
                Alert.show("Setting feedback: " + item);
                return;
            }
            var feedbacks:ArrayCollection=new ArrayCollection();
            var feedback:Object=new Object();

            if (item.hasOwnProperty("id")) {
                feedback.submissionId=item.id;
            }

            if (item.hasOwnProperty("feedback")) {
                feedback.feedbackText=item.feedback;
            }

            if (item.hasOwnProperty("upDown")) {
                var thumb:int=0;
                if (item.upDown) {
                    if (item.upDown == "up") {
                        thumb=1;
                    } else if (item.upDown == "down") {
                        thumb=-1;
                    }
                }

                feedback.feedbackThumb=thumb;
            }

            if (feedback.hasOwnProperty("submissionId")) {
                //trace("--------------------------------- feedback: " + feedback.submissionId + "," + feedback.feedbackText + "," + feedback.feedbackThumb);

                feedbacks.addItem(feedback);
            }

            //trace("--------------------------------- FEEDBACKS: " + feedbacks);

            //this.showLoadingProgress();

            var header:SOAPHeader=getHeader(this.username, this.password);
            this.contestServiceFacadeWS.clearHeaders();
            this.contestServiceFacadeWS.addHeader(header);
            var updateSubmissionsFeedbackOp:AbstractOperation=this.contestServiceFacadeWS.getOperation("updateSubmissionsFeedback");
            if (updateSubmissionsFeedbackOp) {
                updateSubmissionsFeedbackOp.send(feedbacks);
            }
        }
    }
}
