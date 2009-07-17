/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget {
    import mx.controls.PopUpButton;
    import mx.managers.PopUpManager;
    import mx.utils.Base64Encoder;
    import flash.events.MouseEvent;
    import flash.display.Loader;
    import flash.system.LoaderContext;
    import flash.net.URLRequest;
    import flash.system.ApplicationDomain;
    import mx.core.Application;
    import flash.events.Event;

    /**
     * <p>
     * This is the code to install / launch air viewer for submission.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     * @since Cockpit Submission Viewer Widget Enhancement Part 1.
     */
    public class AirViewer {

        /**
         * Reference to air.swf file.
         * This provides method to control Air application.
         */
        private var _airSWF:Object;

        /**
         * App Id of the air viewer application.
         */
        private const AppID:String="com.topcoder.air.submissionviewer.SubmissionViewer";

        /**
         * Publish Id of the air viewer application.
         */
        private const PubID:String="3F72361E8C8820A04453F808F74A577BB83C7B11.1";

        /**
         * Init air viewer callback function.
         */
        private var _initAirViewerCallbackFn:Function=null;

        /**
         * True if initialization of air viewer has started else false.
         */
        private var _isInitializationStarted:Boolean=false;

        /**
         * True if air viewer is initialized else false.
         */
        private var _isInitialized:Boolean=false;

        /**
         * True if version of air viewer app has been successfully detected.
         */
        private var _isVersionDetected:Boolean=false;

        /**
         * Reference to self - needed for few anonymous functions.
         */
        private var _self:AirViewer=null;

        /**
         * Reference to air viewer installer screen.
         */
        private var _installerScreen:AirViewerInstallerScreen=null;

        /**
         * Reference to air viewer launch screen.
         */
        private var _launchScreen:AirViewerLaunchScreen=null;

        /**
         * Reference to the parent submission viewer widget.
         */
        [Bindable]
        private var _subViewer:SubmissionViewerWidget;

        /**
         * Simple constructor for air viewer.
         * This initializes air viewer.
         */
        public function AirViewer() {
            this.init();
        }


        /**
         * Gets the reference to the parent submission viewer widget.
         *
         * @return the parent submission viewer widget.
         */
        [Bindable]
        public function get subViewer():SubmissionViewerWidget {
            return this._subViewer;
        }

        /**
         * Sets the reference of the parent submission viewer widget.
         *
         * @param viewer the parent submission viewer widget.
         */
        public function set subViewer(viewer:SubmissionViewerWidget):void {
            this._subViewer=viewer;
        }

        /**
         * Initializer for air viewer.
         */
        private function init():void {
            trace("---------------------------- Calling init");
            _self=this;
            _isInitializationStarted=true;
            var airSWFLoader:Loader=new Loader(); // Used to load the SWF
            var loaderContext:LoaderContext=new LoaderContext(); // Used to set the application domain
            loaderContext.applicationDomain=ApplicationDomain.currentDomain;
            airSWFLoader.contentLoaderInfo.addEventListener(Event.INIT, onInit);
            airSWFLoader.load(new URLRequest("http://airdownload.adobe.com/air/browserapi/air.swf"), loaderContext);
        }

        /**
         * Callback function on air.swf load init.
         */
        private function onInit(e:Event):void {
            trace("---------------------------- Calling on init");
            _isInitialized=true;
            this._airSWF=e.target.content;
            if (_initAirViewerCallbackFn != null) {
                trace("---------------------- Calling air viewer init callback");
                _initAirViewerCallbackFn();
            }
        }

        /**
         * Gets the callback function for detecting version of air viewer app.
         * If version is not detected already, before launching air viewer we always detect version.
         * This is the way to know if air viewer app is installed already or not.
         *
         * On succesful version detection, specified callbackFn for opening air viewer app is called.
         * Otherwise, installer screen is presented to user.
         *
         * @param callbackFn callbackFn for opening air viewer app.
         */
        private function getVersionDetectFn(callbackFn:Function):Function {
            return function(version:String):void {
                if (version == null) {
                    trace("------------- no version detected");
                    _subViewer.hideLoadingProgress();
                    _installerScreen=AirViewerInstallerScreen(PopUpManager.createPopUp(_subViewer, AirViewerInstallerScreen, true));
                    _installerScreen.installAir=installAir;
                    _installerScreen.cancelInstall=cancelInstall;
                    PopUpManager.centerPopUp(_installerScreen);
                } else {
                    _isVersionDetected=true;
                    trace("------------- calling callback function for open submission");
                    callbackFn();
                    _subViewer.hideLoadingProgress();
                }
            }
        }

        /**
         * Handler for installing air viewer app.
         *
         * @param e mouse event which initiated this handler.
         */
        private function installAir(e:MouseEvent):void {
            if (_installerScreen) {
                PopUpManager.removePopUp(_installerScreen);
                _installerScreen=null;
            }

            var url:String="http://" + Application.application.parameters.hostAddress + "/i/cockpit/subviewer/SubmissionViewer.air";
            var runtimeVersion:String="1.1";
            var arguments:Array=["launchFromBrowser"];
            this._airSWF.installApplication(url, runtimeVersion, arguments);
        }

        /**
         * Handler for cancelling air viewer app installation.
         *
         * @param e mouse event which initiated this handler.
         */
        private function cancelInstall(e:MouseEvent):void {
            if (_installerScreen) {
                PopUpManager.removePopUp(_installerScreen);
                _installerScreen=null;
            }
        }

        /**
         * This opens given url with given title in air viewer app.
         * If this air viewer is already not initilized then it initializes it.
         * Otherwise if air viewer app version is not detected, then it detects the app.
         * Otherwise it simply launches the air viewer app.
         *
         * @param url url to open
         * @param title tab title.
         */
        public function openSubmission(url:String, title:String):void {
            if (!_isInitialized) {
                trace("------------------- air viewer is not initialized");
                _initAirViewerCallbackFn=function():void {
                    trace("------------- firing version detect");
                    _airSWF.getApplicationVersion(AppID, PubID, getVersionDetectFn(function():void {
                            openSubmissionInternal(url, title);
                        }));
                    _initAirViewerCallbackFn=null;
                };

                this._subViewer.showLoadingProgress();

                if (!_isInitializationStarted) {
                    init();
                }
            } else if (!_isVersionDetected) {
                trace("------------------- air viewer version is not detected");

                this._subViewer.showLoadingProgress();

                this._airSWF.getApplicationVersion(AppID, PubID, getVersionDetectFn(function():void {
                        openSubmissionInternal(url, title);
                    }));

                this._subViewer.hideLoadingProgress();
            } else {
                trace("------------------- opening air viewer for url: " + url + ", title: " + title);
                this._subViewer.showLoadingProgress();
                openSubmissionInternal(url, title);
                this._subViewer.hideLoadingProgress();
            }
        }

        /**
         * open submission internal - this simply presents the user confirmation screen for opening
         * air viewer. this is needed because air viewer can not be opened without any user event (like mouse event).
         *
         * @param url url to open
         * @param title tab title.
         */
        private function openSubmissionInternal(url:String, title:String):void {
            trace("------------------- opening air viewer for url: " + url + ", title: " + title + ", airSWF: " + _airSWF);
            _launchScreen=AirViewerLaunchScreen(PopUpManager.createPopUp(_subViewer, AirViewerLaunchScreen, true));
            _launchScreen.launchApp=function():void {
                var args:Array=["addTab", encodeBase64String(url), encodeBase64String(title)];
                _airSWF.launchApplication(AppID, PubID, args);
                PopUpManager.removePopUp(_launchScreen);
            };
            _launchScreen.cancelLaunchApp=function():void {
                PopUpManager.removePopUp(_launchScreen);
            };

            PopUpManager.centerPopUp(_launchScreen);
        }

        /**
         * Encodes the given string to base 64.
         *
         * @param s string to encode.
         * @retrun encoded string.
         */
        private function encodeBase64String(s:String):String {
            var enc:Base64Encoder=new Base64Encoder();
            enc.insertNewLines=false;
            enc.encode(s);
            return enc.flush();
        }

    }
}