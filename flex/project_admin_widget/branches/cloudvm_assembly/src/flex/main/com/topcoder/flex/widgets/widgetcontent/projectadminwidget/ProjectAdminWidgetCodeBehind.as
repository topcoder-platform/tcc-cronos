/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectadminwidget {
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.util.progress.ProgressWindowManager;
    import com.topcoder.flex.widgets.model.IWidget;
    import com.topcoder.flex.widgets.model.IWidgetContainer;
    import com.topcoder.flex.widgets.widgetcontent.projectadminwidget.qs.model.Model;
    
    import flash.utils.Dictionary;
    
    import mx.collections.ArrayCollection;
    import mx.containers.VBox;
    import mx.core.Application;
    import mx.rpc.AbstractOperation;
    import mx.rpc.soap.SOAPHeader;
    import mx.rpc.soap.mxml.WebService;

    /**
     * <p>
     * This is the code behind mxml widget component for the project admin widget.
     * It implements the IWidget interface. It extends from the VBox.
     * </p>
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.4]
     *    - reload should refresh the permissions for the current user.
     * </p>
     * 
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01, TCSASSEMBLER
     * @version 1.0
     * @since Cockpit Share Submission Integration
     */
    [Bindable]
    public class ProjectAdminWidgetCodeBehind extends VBox implements IWidget {
        /**
         * Web service security qualified name space mapping.
         */
        private static const WSSE_SECURITY:QName=new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");

        /**
         * The name of the widget.
         */
        private var _name:String="Project Admin";

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
         * Reference to contest service facade webservice.
         */
        private var _contestServiceFacadeWS:WebService=null;

        /**
         * Current user.
         */
        private var _username:String=Application.application.parameters.username;
        
        /**
        * Current user id.
        */
        private var _userid:Number=Application.application.parameters.userid;

        /**
         * Password of the current user.
         */
        private var _password:String="";

        /**
         * Instance of the data model class for this widget.
         */
        private var _model:Model=Model.instance;
        
        /**
         * All available project ids and contestIds, for fast lookup.
         * 
         * @since Cockpit Release Assembly 3
         */
        private var _pcIds:Dictionary=null;
        
        /**
         * List of current projects as loaded in this widget.
         * 
         * @since Cockpit Release Assembly 3 
         */ 
        private var _currentProjList:ArrayCollection=null;

        public function ProjectAdminWidgetCodeBehind() {
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
            return false;
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
        }

        /**
         * This action will reload this widget.
         * 
         * <p>
         * Updated for Cockpit Release Assembly 3 [RS: 1.1.4]
         *    - permissions are reloaded on refresh button click.
         * </p>
         */
        public function reload():void {
            var getCommonProjectPermissionDataForUser:AbstractOperation=this.contestServiceFacadeWS.getOperation("getCommonProjectPermissionDataForUser");
            
            model.keyWords="";
            model.clearSearch();
            model.userList=null;
            this.pcIds=null;
            this.currentProjList=null;
            model.currentUser=null;
            model.refresh=!model.refresh;
             
            model.selectedIndex=0;
                
            if (getCommonProjectPermissionDataForUser) {
        	    showLoadingProgress();
        	    trace("Reloading for userid: " + this.userid);
                getCommonProjectPermissionDataForUser.send(-1); /*this.userid - refer ProjectAdminWidget initComponent() method, it does same.*/
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
        }

        /**
         * This action will maximize this widget.
         */
        public function maximize():void {
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
            if (this.container) {
                ProgressWindowManager.showProgressWindow(this.container);
            }
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
            if (this.container) {
                ProgressWindowManager.hideProgressWindow(this.container);
            }
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
         * Gets the password.
         *
         * @return the password.
         */
        public function get model():Model {
            return this._model;
        }
        
        /**
         * Gets the password.
         *
         * @return the password.
         */
        public function set model(m:Model):void {
            this._model=m;
        }
        
        /**
         * Gets the project & contest id lookup map.
         * 
         * @return the project & contest id lookup map.
         * 
         * @since Cockpit Release Assembly 3.
         */
        public function get pcIds():Dictionary {
            return this._pcIds;
        }
        
        /**
         * Sets the project & contest id lookup map.
         * 
         * @param p the project & contest id lookup map.
         * 
         * @since Cockpit Release Assembly 3.
         */
        public function set pcIds(p:Dictionary):void {
            this._pcIds=p;
        }
        
        /**
         * Gets the current project list as loaded in this widget.
         * 
         * @return the current project list as loaded in this widget.
         * 
         * @since Cockpit Release Assembly 3
         */
        public function get currentProjList():ArrayCollection {
            return this._currentProjList;
        }
        
        /**
         * Sets the current project list as loaded in this widget.
         * 
         * @param pList the current project list as loaded in this widget.
         * 
         * @since Cockpit Release Assembly 3
         */
        public function set currentProjList(pList:ArrayCollection):void {
            this._currentProjList=pList;
        }
    }
}
