/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.usersync {
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.util.progress.ProgressWindowManager;
    import com.topcoder.flex.widgets.model.IWidget;
    import com.topcoder.flex.widgets.model.IWidgetContainer;
    
    import flash.utils.Dictionary;
    
    import mx.containers.Canvas;
    import mx.containers.VBox;
    import mx.core.Application;
    import mx.rpc.soap.SOAPHeader;
    import mx.rpc.soap.WebService;

    /**
     * <p>
     * This is the code behind mxml widget component for the User Sync Widget.
     * It implements the IWidget interface. It extends from the VBox.
     * 
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     * @since Jira and Confluence User Sync Widget 1.0
     */
    [Bindable]
    public class UserSyncWidgetCodeBehind extends VBox implements IWidget {
        
        /**
         * The WSSE Security QName.
         */
        private static const WSSE_SECURITY:QName=new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
        
        /**
         * The name of the widget.
         */
        private var _name:String="User Sync Widget";

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
         * Currently logged in username.
         */
        protected var username:String=Application.application.parameters.username;

        /**
         * Currently logged in user password.
         */
        protected var password:String="";
        
        /**
         * Reference to the user service facade bean web-service.
         */
        private var _userServiceFacadeBean:WebService;
        
        private var _isMax:Boolean=false;

        /**
         * A default empty constructor.
         */
        public function UserSyncWidgetCodeBehind() {
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
            return !_isMax;
        }

        /**
         * This will tell the caller if the widget is in maximize mode or not.
         *
         * @return true if the widget is maximized, false otherwise.
         */
        public function isMaximized():Boolean {
            return _isMax;
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
         */
        public function minimize():void {
            this._isMax=false;
        }

        /**
         * This action will restpre this widget (for example from a menu bar).
         */
        public function restore():void {
            this._isMax=false;
        }

        /**
         * This action will maximize this widget.
         */
        public function maximize():void {
            this.percentWidth=100;
            this._isMax=true;
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
         * Getter for the user service facade bean service reference.
         *
         * @return the user sync service bean reference.
         */
        public function get userServiceFacadeBean():WebService {
            return _userServiceFacadeBean;
        }

        /**
         * A simple setter for user service facade bean service reference.
         *
         * @param w the webservice to be set.
         */
        public function set userServiceFacadeBean(w:WebService):void {
            _userServiceFacadeBean=w;
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
    }
}