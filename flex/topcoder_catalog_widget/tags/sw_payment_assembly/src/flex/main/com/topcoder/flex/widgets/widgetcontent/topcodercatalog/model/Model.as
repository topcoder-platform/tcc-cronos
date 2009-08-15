/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.topcodercatalog.model {
    import com.adobe.serialization.json.JSON;
    import com.adobe.utils.StringUtil;
    import com.topcoder.flex.util.date.DateUtil;
    import com.topcoder.flex.widgets.widgetcontent.topcodercatalog.renderer.ComponentRenderer;
    import com.topcoder.flex.widgets.widgetcontent.topcodercatalog.vo.TCComponent;

    import flash.net.URLRequest;
    import flash.net.navigateToURL;

    import mx.collections.ArrayCollection;
    import mx.collections.Sort;
    import mx.events.EffectEvent;
    import mx.preloaders.DownloadProgressBar;
    import mx.rpc.events.ResultEvent;
    import mx.utils.ObjectUtil;

    /**
     * <p>
     * This is the data model class for this catalog widget.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     * @since Cockpit Catalog Widget Integration
     */
    [Bindable]
    public class Model {
        /**
         * Singleton instance of this model class.
         */
        private static var _instance:Model;

        /**
         * Gets the singleton instance of this model class.
         *
         * @return the singleton instance of this model class.
         */
        public static function get instance():Model {
            if (!_instance) {
                _instance=new Model();
            }
            return _instance;
        }

        /**
         * Static constant for top download count component rows to be shown.
         */
        public static var TopMaxCount:int=3;

        /**
         * Static constant for most recent released component rows to be shown.
         */
        public static var RecentMaxCount:int=3;

        /**
         * The current filtered category. By default it is "All"
         */
        public var currentCategory:String="All";

        /**
         * The current searched keywords. By default it is empty string.
         */
        public var keyWords:String="";

        /**
         * The full list of categories.
         */
        public var categoryList:ArrayCollection;

        /**
         * True if view is locked and further actions won't happen else false.
         */
        public var lock:Boolean=false;

        /**
         * True if view is locked and further actions won't happen else false.
         */
        public var lockScreen:Boolean=false;

        /**
         * Current component renderer. This is used for mouse over effect on the component rows.
         */
        public var currentCR:ComponentRenderer;

        /**
         * Previous component renderer. This is used for mouse over effect on the component rows.
         */
        public var lastCR:ComponentRenderer;

        /**
         * The current mode of the widget.
         */
        private var _currentMode:String;

        /**
         * The last/previous mode of the widget.
         */
        public var lastMode:String;

        /**
         * The current component in the detail view.
         */
        public var currentComponent:TCComponent;

        /**
         * The full component list.
         */
        public var componentList:ArrayCollection=new ArrayCollection();

        /**
         * The searched component list.
         */
        public var searchedComponentList:ArrayCollection;

        /**
         * The filtered component list.
         */
        public var filteredComponentList:ArrayCollection;

        /**
         * Most recent released component list.
         */
        public var mostRecentReleaseList:ArrayCollection;

        /**
         * Most downloaded component list.
         */
        public var topDownloadList:ArrayCollection;

        /**
         * A simple constructor. It sets the current mode to "main" at initialization.
         */
        public function Model() {
            currentMode="main";
        }

        /**
         * Handler for loading data from service url.
         *
         * @param e the result event.
         */
        public function handleCatalogLoad(e:ResultEvent):void {
            trace("Result: " + e);

	    componentList.removeAll();

            if (e && e.result) {
                var jsonStr:String=e.result as String;
                var obj:Object=JSON.decode(jsonStr);

                var tempCategoryList:ArrayCollection=new ArrayCollection();

                tempCategoryList.addItemAt("All", 0);

                var rows:Array=obj.data as Array;
                for each (var r:Object in rows) {
                    var component:TCComponent=new TCComponent();
                    component.componentName=r.component_name;
                    component.categoryType=r.category_name;
                    component.categories=r.category_list;
                    component.releaseDate=parseFromString(r.release_date);
                    component.longTime=component.releaseDate.time;
                    component.componentProgressStatus="complete";
                    component.shortDescription=r.short_desc;
                    component.longDescription=r.long_desc;
                    component.moreInfoURL=r.url;
                    component.versionDownloadCount=r.version_downloads;
                    component.componentDownloadCount=r.component_downloads;
                    component.downloadURL=r.download_url;
                    component.versionText=r.version_text;

                    if (component.categories && component.categories != "") {
                        var categories:Array=component.categories.split(",");
                        for each (var c:String in categories) {
                            c=StringUtil.trim(c);
                            if (!tempCategoryList.contains(c)) {
                                tempCategoryList.addItem(c);
                            }
                        }
                    }

                    componentList.addItem(component);
                }

                sort(componentList, compareComponentName);
                sort(tempCategoryList, compareString);

                categoryList=tempCategoryList;

                generateMostRecentReleaseList();
                generateTopDownloadList();
            }
        }

        /**
         * Sorts the given collection by name
         *
         * @param coll specified array collection to be sorted.
         */
        private function sort(coll:ArrayCollection, compareFunction:Function):void {
            if (!coll.sort) {
                var sort:Sort=new Sort();
                sort.compareFunction=compareFunction;
                coll.sort=sort;
            }

            coll.refresh();
        }

        /**
         * Callback function on sorting by component name.
         */
        private function compareComponentName(a:Object, b:Object, fields:Array=null):int {
            return ObjectUtil.stringCompare(a.componentName, b.componentName, true);
        }

        /**
         * Callback function on sorting by component name.
         */
        private function compareString(a:Object, b:Object, fields:Array=null):int {
            return ObjectUtil.stringCompare(a as String, b as String, true);
        }

        /**
         * Generates the top download list.
         */
        private function generateTopDownloadList():void {
            var temp:Array=componentList.toArray();
            temp=temp.sortOn(["versionDownloadCount", "componentName"], [Array.NUMERIC | Array.DESCENDING, Array.CASEINSENSITIVE]);
            var tempTopDownloadList:ArrayCollection=new ArrayCollection();
            for (var i:int=0; i < TopMaxCount; i++) {
                trace("Adding item to topDownList: " + temp[i] + " = " + temp[i].versionDownloadCount);
                tempTopDownloadList.addItem(temp[i]);
            }

            topDownloadList=tempTopDownloadList;
        }

        /**
         * Generates the most recent released list.
         */
        private function generateMostRecentReleaseList():void {
            var temp:Array=componentList.toArray();
            temp=temp.sortOn(["longTime", "componentName"], [Array.NUMERIC | Array.DESCENDING, Array.CASEINSENSITIVE]);
            var tempMostRecentReleaseList:ArrayCollection=new ArrayCollection();
            for (var i:int=0; i < RecentMaxCount; i++) {
                trace("Adding item to componentRecRelease: " + temp[i] + " = " + temp[i].releaseDate + ", " + temp[i].longTime);
                tempMostRecentReleaseList.addItem(temp[i]);
            }

            mostRecentReleaseList=tempMostRecentReleaseList;
        }

        /**
         * Closes/opens the links corresponding to given component renderer.
         *
         * @param cr the component renderer.
         */
        public function closeLastLink(cr:ComponentRenderer):void {
            if (lockScreen) {
                return;
            }
            if (lock) {
                currentCR=cr;
                return;
            }
            if (currentCR) {
                if (currentCR == cr) {
                    return;
                }
                lastCR=currentCR;
                currentCR=cr;
                lock=true;
                lastCR.closeLink(openCurrentLink);
            } else {
                currentCR=cr;
                openCurrentLink();
            }
        }

        /**
         * Applies search and filters and calculates the searched & filtered component list.
         *
         * @return the searched and filtered component list.
         */
        private function applySearchAndFilter():ArrayCollection {
            var targetList:ArrayCollection=componentList;

            var array:ArrayCollection=new ArrayCollection();
            for each (var coms:TCComponent in targetList) {
                if ((!this.currentCategory || this.currentCategory == "All" || coms.categories == this.currentCategory) && (!this.keyWords || this.keyWords == "" || coms.componentName.toLowerCase().indexOf(keyWords.toLowerCase()) >= 0)) {
                    array.addItem(coms);
                }
            }

            return array;
        }

        /**
         * Handler to go the the filter view for given category.
         *
         * It simply filters the results and changes the view stack to show filtered view.
         *
         * @param cate the category for which results to be filtered.
         */
        public function gotoFilter(cate:String):void {
            this.currentCategory=cate;
            filteredComponentList=applySearchAndFilter();
            currentMode="filter";
        }

        /**
         * Handler to go the the search view
         *
         * It simply search the results and changes the view stack to show search view.
         */
        public function gotoSearch(key:String):void {
            this.keyWords=key;
            searchedComponentList=applySearchAndFilter();
            currentMode="search";
        }

        /**
         * Effect handler for open/close of the current link.
         *
         * @param event the effect event.
         */
        public function openCurrentLink(event:EffectEvent=null):void {
            lock=false;
            if (lastCR) {
                lastCR.overContent.visible=false;
            }
            if (currentCR) {
                currentCR.showLink();
            }
        }

        /**
         * Opens the download link of specified component in new browser window/tab.
         *
         * @param coms the tc component.
         */
        public function openDownload(coms:TCComponent):void {
            navigateToURL(new URLRequest(coms.downloadURL), "_blank");
        }

        /**
         * Sets the current mode of the widget.
         *
         * @param value the given mode.
         */
        public function set currentMode(value:String):void {
            lastMode=_currentMode;
            _currentMode=value;
        }

        /**
         * Gets the current mode of the widget.
         *
         * @return the current mode of the widget.
         */
        public function get currentMode():String {
            return _currentMode;
        }

        /**
         * Parses the given date string and creates a date object out of it.
         * If given string is null or empty, then it returns today's date.
         */
        private function parseFromString(str:String):Date {
            if (!str || str == '') {
                return new Date();
            }

            var year:Number=Number(str.substr(0, 4));
            var month:Number=Number(str.substr(5, 2)) - 1;
            var date:Number=Number(str.substr(8, 2));

            var hh:Number=Number(str.substr(11, 2));
            var mm:Number=Number(str.substr(14, 2));
            var ss:Number=Number(str.substr(17, 2));

            return new Date(year, month, date, hh, mm, ss);
        }
    }
}