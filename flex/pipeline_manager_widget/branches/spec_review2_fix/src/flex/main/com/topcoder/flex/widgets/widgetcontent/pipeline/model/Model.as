/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.model {
    import com.topcoder.flex.model.IWidgetFramework;
    import com.topcoder.flex.util.date.DateUtil;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.PipelineManagerWidget;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.log.TCLog;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.BreakDown;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.ChangeDate;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.ChangePrice;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.Client;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.Detail;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.FilterCriteria;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.NumbericFilter;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.Project;
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.Summary;
    
    import flash.events.Event;
    import flash.events.EventDispatcher;
    import flash.utils.Dictionary;
    
    import mx.collections.ArrayCollection;
    import mx.collections.Sort;
    import mx.core.Application;
    import mx.rpc.events.ResultEvent;
    import mx.utils.StringUtil;

    
    /**
     * <p>
     * This is the data model class for this widget.
     * </p>
     * 
     * Version 1.0.1 (Pipeline Conversion Cockpit Integration Assembly 2 v1.0) Change Notes:
     *    - introduced new variable to hold whether overdue contests are included in loaded data or not.
     *    - added methods & variables to construct and retrieve ICAL and RSS feed urls.
     * <p>
     * Version 1.0.2(Cockpit Pipeline Manager Widget Release Assembly V1.0) Change Notes:
     * - Add logic to filter 'excluded status'
     * - Change the logic of contest status for studio contests
     * </p>
     *
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01, TCSASSEMBLER
     * @since Pipeline Conversion Cockpit Integration Assembly 1 v1.0
     * @version 1.0.2
     */
    [Bindable]
    public class Model extends EventDispatcher {
        /**
         * A default constructor.
         * Initializes the mode to MAIN view.
         */ 
        public function Model() {
            mode="MAIN";
        }
        
        /**
         * Holds the singleton instance of this data model class.
         */  
        private static var _instance:Dictionary;
        
        /**
         * Gets the singleton instance of this data model class for given uid.
         */  
        public static function getInstance(uid:String):Model {
            if (!_instance) {
                _instance=new Dictionary();
            }
            if (_instance[uid] != null) {
                return _instance[uid] as Model;
            } else {
                var model:Model=new Model();
                _instance[uid]=model;
                model.uid=uid;
                return model;
            }
        }
        
        public var widgetFramework:IWidgetFramework;
        
        /**
         * The parent widget ui id.
         */ 
        public var uid:String;
        
        /**
         * The left hand side list.
         */ 
        public var list:ArrayCollection;
        
        /**
         * The start date for the loaded pipeline details.
         */ 
        public var startDate:Date;
        
        /**
         * The end date for the loaded pipeline details.
         */ 
        public var endDate:Date;
        
        /**
         * The confidence list from the loaded pipeline details.
         */ 
        public var confidenceList:Array;
        
        /**
         * The client list from the loaded pipeline details.
         */
        public var clientList:Array;
        
        /**
         * The field list.
         */
        public var fieldList:Array=["Prize", "Second Place Prize", "DR Points", "Contest Fee", "Review Cost", "Spec Review Cost", "Duration"];
        
        /**
         * Pipleline info Items visible in ui.
         */ 
        public var itemList:Array=["prize", "seprize", "dr", "fee", "review", "spec", "duration"];
        
        [Embed(source="../assets/image/winMax.png")]
        public var winMax:Class;
        
        [Embed(source="../assets/image/winMin.png")]
        public var winMin:Class;
        
        /**
         * The contest type labels in left hand navigation bar.
         */
        public var typeLabels:Array=["All Contests", "Studio", "Conceptualization", "Software Specification", "Architecture", "Component Design", "Component Development", "RIA Component", "RIA Build", "UI Prototype", "Software Assembly", "Test Suites", "Test Scenarios"];
        
        /**
         * The contest type values for left hand navigation bar.
         */
        public var typeValues:Array=["All Contests", "STUDIO", "CONCEPTUALIZATION", "SPECIFICATION", "ARCHITECTURE", "DESIGN", "DEVELOPMENT", "RIACOMPONENT", "RIABUILD", "UIPROTOTYPE", "ASSEMBLY", "TESTSUITES", "TESTSCENARIOS"];
        
        /**
         * The contest group.
         */
        private var cate:String;
        
        /**
         * The current filter criteria.
         */
        public var filter:FilterCriteria=new FilterCriteria();
        
        /**
         * The reference to the widget class.
         */
        public var widget:PipelineManagerWidget;
        
        /**
         * Indicates whether overdue contests are included in current loaded data in the widget.
         * 
         * @since 1.0.1
         */ 
        public var overdueIncluded:Boolean
        
        /**
         * ICAL Feed URL. Read from application params.
         * 
         * @since 1.0.1
         */ 
        private var icalFeedURL:String=Application.application.parameters.icalFeedURL;
        
        /**
        * RSS Feed URL. Read from application params.
        * 
        * @since 1.0.1
        */
        private var rssFeedURL:String=Application.application.parameters.rssFeedURL;

        /**
        * host address, used for rss feed
        * 
        * @since 1.0.1
        */
        private var hostAddress:String=Application.application.parameters.hostAddress;
        
        /**
         * Holds the map of contestId to pipeline details.
         * 
         * @since 1.0.1
         */ 
        private var detailsByContestId:Dictionary=null;
        
        /**
         * Loads the contest data to the widget ui data objects.
         * 
         * Updated for Version 1.0.1
         *    - detailsByContestId is filled.
         * 
         * @param the XMLList of the contest data.
         */ 
        public function loadData(contests:XMLList):void {
            //TCLog.instance.timeStampLog("Load data", this.uid);
            //trace(contests.toXMLString());
            //TCLog.instance.debug("contests.children.length ---------> " + contests.children().length());
            var start:Date = new Date();
            clientList=new Array();
            confidenceList=new Array();
            detailsByContestId=new Dictionary();
            list=new ArrayCollection();
            for each(var o:XML in contests.children()) {
                
                var p:Detail=new Detail();
                p.widgetFramework=widgetFramework;
                p.pipelineInfoId=o.pipelineInfoId ? o.pipelineInfoId[0] : 0;
                p.contestId=o.contestId ? o.contestId[0] : 0;
                p.name=o.cname ? o.cname[0] : "";
                p.version=o.cversion ? o.cversion[0] : "";
                p.projectId=o.projectId ? o.projectId[0] : 0;
                p.project=o.pname ? o.pname[0] : "";
                
                p.type=o.contestType ? o.contestType[0] : "";
                p.category=o.contestCategory ? o.contestCategory[0] : "";
               
                p.status=o.sname ? o.sname[0] : "";
                
                
                p.client=o.clientName[0];
                
                p.date=o.startDate ? DateUtil.parseFromString(o.startDate[0]) : null;
                
                p.duration=o.durationInHrs ? o.durationInHrs[0] : 0;
                
                p.prize=o.totalPrize ? o.totalPrize[0] : 0;
                p.dr=o.dr ? o.dr[0] : 0;
                p.fee=o.contestFee ? o.contestFee[0] : 0;
                p.review=o.reviewPayment ? o.reviewPayment[0] : 0;
                p.spec=o.specReviewPayment ? o.specReviewPayment[0] : 0;
                
                p.aclient=o.clientApproval ? o.clientApproval[0] : false;
                p.apricing=o.pricingApproval ? o.pricingApproval[0] : false;
                p.vspec=o.passedSpecReview ? o.passedSpecReview[0] : false;
                p.dependent=o.hasDependentCompetitions ? o.hasDependentCompetitions[0] : false;
                
                if (o.wasReposted && o.wasReposted[0] && o.wasReposted[0] == true)
                {
                    p.repost=true;
                }

                p.hasWikiSpec=o.hasWikiSpecification ? o.hasWikiSpecification[0] : false;
                
                p.notes=o.notes ? o.notes[0] : "";
                
                p.eligibity=o.eligibility ? o.eligibility[0] : "";
                p.short=o.shortDesc ? o.shortDesc[0] : "";
                p.desc=o.longDesc ? o.longDesc[0] : "";
                p.long=p.desc;
                
                p.added=o.createTime ? DateUtil.parseFromString(o.createTime[0]) : null;
                p.changed=o.modifyTime ? DateUtil.parseFromString(o.modifyTime[0]) : null;
                
                p.pperm=o.pperm ? o.pperm[0] : "";
                p.cperm=o.cperm ? o.cperm[0] : "";
                
                p.reviewer=o.reviewer ? o.reviewer[0] : "";
                p.manager=o.manager ? o.manager[0] : "";
                p.arch=o.architect ? o.architect[0] : "";
                p.specer=o.salesPerson ? o.salesPerson[0] : "";
                
                p.uid=uid;
                p.cpname = o.cpname ? o.cpname[0] : "";
                // add client
                var clFound:Boolean=false;
                for each (var cl:String in clientList) {
                    if (cl == p.client) {
                        clFound=true;
                        break;
                    }
                }
                
                if (!clFound) {
                    clientList.push(p.client);
                }
                
                list.addItem(p);
                
                // store the details by contest id.
                detailsByContestId[p.contestId]=p;
            }
            
            cate=typeValues[0];
            filterDetail();
            TCLog.instance.timeStampLog("Calculate Data", this.uid);
            var end:Date = new Date();
            //TCLog.instance.debug("Time used in paring in Model.loadData: " + (end.getTime()-start.getTime())/1000 + " seconds");
        }
        
        /**
         * Filter the pipeline detail view.
         */ 
        public function filterDetail(clearAdd:Boolean=true):void {
            if (clearAdd) {
                filter.startAdd=null;
                filter.endAdd=null;
            }
            
            trace("Before applying filter: " + (list ? list.length : -1));

            if (!list) {
                isNoResult=true;
            } else {
                list.filterFunction=doFilter;
                list.refresh();
                
                trace("After applying filter: " + list.length);
                
                if (list.length <= 0) {
                    isNoResult=true;
                    widget.showHideNoResultPanel(true);
                } else {
                    //var previousNoResult:Boolean=isNoResult;
                    //isNoResult=false;
                
                    if (mode == "MAIN") {
                        updateSummary();
                    } else if (mode == "BREAK") {
                        updateBreak();
                    } else if (mode == "PRICE" || mode == "DATE") {
                        updateHistory();
                    }
                    
                    //if (previousNoResult) {
                    //    widget.showHideNoResultPanel(false);
                    //}
                }
            }
            
            if (filter.type == typeValues[0]) {
                dispatchEvent(new Event("clearType"));
            }
            dispatchEvent(new Event("updated"));
        }
        
        /**
         * Locks the ui
         */ 
        public function lock():void {
            dispatchEvent(new Event("lock"));
        }
        
        /**
         * The filter handler.
         */
        public function doFilter(obj:Object):Boolean {
            var detail:Detail=obj as Detail;
     
            if (detail) {
                if (filter.start)
                {
                    filter.start.hours=0;
                    filter.start.minutes=0;
                    filter.start.seconds=0;
                    filter.start.milliseconds=0;
                	
                }
                if (filter.end) 
                {
                    filter.end.hours=0;
                    filter.end.minutes=0;
                    filter.end.seconds=0;
                    filter.end.milliseconds=0;
       
                }
                if (filter.startAdd)
                {
                    filter.startAdd.hours=0;
                    filter.startAdd.minutes=0;
                    filter.startAdd.seconds=0;
                    filter.startAdd.milliseconds=0;
  
                }
                if (filter.endAdd) 
                {
                    filter.endAdd.hours=0;
                    filter.endAdd.minutes=0;
                    filter.endAdd.seconds=0;
                    filter.endAdd.milliseconds=0;
  
                }
                
                if (filter.type && filter.type != typeValues[0]) {
                    if (filter.type && detail.category && filter.type.toLocaleLowerCase() != detail.category.toLocaleLowerCase()) {
                        return false;
                    }
                }
                if (filter.start && filter.start > detail.date) {
                    return false;
                }
                if (filter.startAdd && filter.startAdd > detail.added) {
                    return false;
                }
                if (filter.end) {
                    var e:Date=new Date();
                    e.time=filter.end.time + 1000 * 3600 * 24 - 1000;
                    if (e < detail.date) {
                        return false;
                    }
                }
                if (filter.endAdd) {
                    var e2:Date=new Date();
                    e2.time=filter.endAdd.time + 1000 * 3600 * 24 - 1000;
                    if (e2 < detail.added) {
                        return false;
                    }
                }
                var i:int=0;
                /*if (filter.confidence && filter.confidence.length > 0) {
                    var result:Boolean=false;
                    
                    for (; i < filter.co.length; i++) {
                        if (filter.co[i] == 1) {
                            switch (i) {
                                case 0:
                                    if (detail.confidence == filter.confidence) {
                                        result=true;
                                    }
                                    break;
                                case 1:
                                    if (detail.confidence < filter.confidence) {
                                        result=true;
                                    }
                                    break;
                                case 2:
                                    if (detail.confidence > filter.confidence) {
                                        result=true;
                                    }
                                    break;
                            }
                        }
                    }
                    if (!result) {
                        return false;
                    }
                }*/
                if (filter.excludeClients.length > 0) {
                    for each (var client:String in filter.excludeClients) {
                        if (client == detail.client) {
                            return false;
                        }
                    }
                }
                if (filter.client && filter.client.length > 0) {
                    if (filter.client != detail.client) {
                        return false;
                    }
                }
                if (filter.resourceItem && filter.resourceItem.length > 0 && filter.resourceName && filter.resourceName.length > 0 && detail.hasOwnProperty(filter.resourceItem)) {
                    if (detail[filter.resourceItem] != filter.resourceName) {
                        return false;
                    }
                }
                if (filter.status && filter.status.length > 0) {
                    if (filter.status != detail.status) {
                        return false;
                    }
                }
                //exclude the status
                if (filter.excludedStatus && filter.excludedStatus.length > 0) {
                	for each(var s:String in filter.excludedStatus) {
                		if (detail.status && s.toLocaleLowerCase() == StringUtil.trim(detail.status.toLocaleLowerCase())) {
                			return false;
                		}
                	}
                }
                if (filter.category && filter.category.length > 0) {
                    if (filter.category != detail.category) {
                        return false;
                    }
                }
                if (filter.name && filter.name.length > 0) {
                    if (filter.name != detail.name) {
                        return false;
                    }
                }
                if (filter.cpname && filter.cpname.length > 0) {
                    if (filter.cpname != detail.cpname) {
                        return false;
                    }
                }

                if (filter.project && filter.project.length > 0) {
                    if (filter.project != detail.project) {
                        return false;
                    }
                }
                if (filter.numFilter.length > 0) {
                    for each (var f:NumbericFilter in filter.numFilter) {
                        if (!detail.hasOwnProperty(f.field)) {
                            return false;
                        }
                        var v:Number=detail[f.field];
                        if (f.minValue >= 0 && f.minValue > v) {
                            return false;
                        }
                        if (f.maxValue >= 0 && f.maxValue < v) {
                            return false;
                        }
                    }
                }
                if (!checkBooleanValue(filter.ca, detail, "aclient")) {
                    return false;
                }
                if (!checkBooleanValue(filter.pa, detail, "apricing")) {
                    return false;
                }
                if (!checkBooleanValue(filter.psr, detail, "vspec")) {
                    return false;
                }
                if (!checkBooleanValue(filter.dc, detail, "dependent")) {
                    return false;
                }
                if (!checkBooleanValue(filter.repost, detail, "repost")) {
                    return false;
                }
                /*if (filter.ws != null && filter.ws.length > 0) {
                    switch (filter.ws) {
                        case "N/A":
                            return true;
                        case "YES":
                            return (detail.wiki && detail.wiki.length > 0);
                        case "NO":
                            return !(detail.wiki && detail.wiki.length > 0);
                    }
                }*/
                return true;
                
            }
            return false;
        }
        
        /**
         * Used by filter handler to apply filter on boolean values.
         */
        public function checkBooleanValue(op:String, detail:Detail, field:String):Boolean {
            if (op != null && op.length > 0) {
                switch (op) {
                    case "N/A":
                        return true;
                    case "YES":
                        return (detail[field] == true);
                    case "NO":
                        return (detail[field] == false);
                }
            }
            return true;
        }
        
        /**
         * The current ui mode.
         */ 
        public var mode:String;
        
        /**
         * Represents if there are no results on search or filter.
         */ 
        public var isNoResult:Boolean=false;
        
        /**
         * The last ui mode.
         */
        private var lastMode:String;
        
        /**
         * The date change history list.
         */
        public var dateList:ArrayCollection;
        
        /**
         * The prize change history list.
         */
        public var priceList:ArrayCollection;
        
        /**
         * Go back to the main view.
         */
        public function goBack():void {
            navigateToMode("MAIN");
        }
        
        /**
         * Navigates to the specified mode.
         */
        public function navigateToMode(value:String):void {
            if (value && value.length > 0 && value != mode) {
                
                lastMode=mode;
                mode=value;
                if (mode == "MAIN") {
                    //updateSummary();
                } else if (mode == "BREAK") {
                    breakIdx=0;
                    breakField=breakArray[0];
                    //updateBreak();
                } else if (mode == "PRICE" || mode == "DATE") {
                    //updateHistory();
                }
                dispatchEvent(new Event("modeChange"));
            }
            dispatchEvent(new Event("updated"));
        }
        
        /**
         * The client list.
         */ 
        public var cList:ArrayCollection;
        
        /**
         * The person list.
         */
        public var pList:ArrayCollection;
        
        /**
         * The week list.
         */
        public var weekList:ArrayCollection;
        
        /**
         * The current active role.
         */
        public var role:String="manager";
        
        /**
         * Updates the ui to specified role.
         */
        public function updateRole(r:String):void {
            role=r;
            if (mode == "MAIN") {
                var tmp:Dictionary=new Dictionary();
                var tmp2:ArrayCollection=new ArrayCollection();
                for each (var detail:Detail in list) {
                    if (tmp[detail[role]] == null) {
                        var pro:Project=new Project();
                        pro.uid=uid;
                        tmp2.addItem(pro);
                        tmp[detail[role]]=pro;
                        pro.name=detail[role];
                    }
                    var p:Project=tmp[detail[role]] as Project;
                    p.scheduled++;
                    p.posted+=(detail.status != "Draft" && detail.status != "Scheduled") ? 1 : 0;
                }
                var sort:Sort=new Sort();
                sort.compareFunction=comparePersonContests;
                tmp2.sort = sort;
                tmp2.refresh();  
                this.pList=tmp2;
            }
        }
        
        /**
         * Gets the latest sunday for specified date input.
         */
        public function generateSundayTime(input:Date):Date {
            var monday:Date=new Date(input);
            monday.hours=0;
            monday.minutes=0;
            monday.seconds=0;
            monday.milliseconds=0;
            monday.date=monday.date - monday.day;
            return monday;
        }
        
        /**
         * The breakdown list.
         */
        public var breakList:ArrayCollection;
        
        /**
         * The current breakdown view index.
         */
        public var breakIdx:int=0;
        
        /**
         * Various types of breakdown view.
         */
        public var breakArray:Array=["category", "client", "project", "status", "type"];
        
        /**
         * The descriptive name for the breakdown views.
         */
        public var breakCate:Array=["Category Breakdown", "Client Breakdown", "Project Breakdown", "Status Breakdown", "Type Breakdown"];
        
        /**
         * Various type of history views.
         */ 
        public var hist:Array=["DATE", "PRICE"];
        
        /**
         * The current active breakdown field
         */ 
        public var breakField:String=breakArray[0];
        
        /**
         * Updates the breakdown view of the ui.
         */
        public function updateBreak():void {
            var tmp:ArrayCollection=new ArrayCollection();
            var tmp2:Dictionary=new Dictionary();
            if (breakField && breakField.length > 0) {
                for each (var detail:Detail in list) {
                    if (detail.hasOwnProperty(breakField)) {
                        var value:String=detail[breakField];
                        if (tmp2[value] == null) {
                            var brk:BreakDown=new BreakDown();
                            tmp2[value]=brk;
                            brk.name=value;
                            brk.totalContest=list.length;
                            tmp.addItem(brk);
                        }
                        var bk:BreakDown=tmp2[value] as BreakDown;
                        bk.contest+=1;
                    }
                }
            }
            
            if (!tmp || tmp.length <= 0) {
                widget.showHideNoResultPanel(true);
            } else {
                widget.showHideNoResultPanel(false);
            }
            
            breakList=tmp;
        }
        
        /**
         * Updates the history view of the ui.
         */
        public function updateHistory():void {
            var ids:ArrayCollection=new ArrayCollection();
            var ts:ArrayCollection=new ArrayCollection();
            
            for each (var detail:Detail in list) {
                ids.addItem(detail.contestId);
                ts.addItem(detail.type);
            }
            
            TCLog.instance.debug("updateHistory: " + ids + "," + ts);
            if (mode == "PRICE") {
                widget.loadPrizeChangeHistory(ids, ts, loadPrizeChangeHistory);
            } else if (mode == "DATE") {
                widget.loadDateChangeHistory(ids, ts, loadDateChangeHistory);
            }
        }
        
        /**
         * Webservice result event handler for loading the date change history.
         * 
         * Updated for Version 1.0.1
         *    - to get the contest name instead of id.
         * 
         * @param event the result event.
         */
        public function loadDateChangeHistory(event:ResultEvent):void {
            if (event && event.result) {
                
                var changeHistoryList:ArrayCollection = new ArrayCollection();
                TCLog.instance.debug("loadPrizeChangeHistory(1): " + event.result);
                var history:XMLList=event.result as XMLList;
                
                for each (var ch:XML in history.children()) {
                    var cd:ChangeDate = new ChangeDate();
                   
                    var contestId:Number=ch.contestId[0];
                    var detail:Detail=detailsByContestId[contestId] as Detail;
                    if (detail) {
                        cd.name=detail.name;
                    } else {
                        cd.name=contestId.toString();
                    }
                    cd.change=ch.changeType[0];
                    cd.oldDate=convertToDate(ch.oldData[0]);
                    cd.newDate=convertToDate(ch.newData[0]);
                    cd.init=convertToDate(ch.initialData[0]);
                    cd.times=1;
                    cd.manager=ch.manager[0];
                    cd.client=ch.client[0];
                    cd.type=ch.type[0];
                    
                    changeHistoryList.addItem(cd);
                }    
                
                if (!changeHistoryList || changeHistoryList.length <= 0) {
                    widget.showHideNoResultPanel(true);
                } else {
                    widget.showHideNoResultPanel(false);
                }
                
                this.dateList=changeHistoryList;
            } else {
                widget.showHideNoResultPanel(true);
            }
        }
        
        /**
         * Converts the specified string to date.
         * 
         * @param s the string date convert
         * @return the converted date object.
         */ 
        private function convertToDate(s:String):Date {
            var flds:Array=s.split("-");
            var y:Number=new Number(flds[0]);
            var m:Number=new Number(flds[1]) - 1;
            var d:Number=new Number(flds[2]);
            
            var ret:Date=new Date(y, m, d);
            return ret;
        }
        
        /**
         * Webservice result event handler for loading the prize change history.
         * 
         * Updated for Version 1.0.1
         *    - to get the contest name instead of id.
         * 
         * @param event the result event.
         */ 
        public function loadPrizeChangeHistory(event:ResultEvent):void {
            if (event && event.result) {
                var changeHistoryList:ArrayCollection = new ArrayCollection();
                TCLog.instance.debug("loadPrizeChangeHistory(1): " + event.result);
                var history:XMLList=event.result as XMLList;
                
                trace("loadPrizeChangeHistory::history.children(): " + history.children());
                
                for each (var ch:XML in history.children()) {
                    var cd:ChangePrice = new ChangePrice();
                   
                    var contestId:Number=ch.contestId[0];
                    trace("loadPrizeChangeHistory::ContestId: " + contestId);
                    var detail:Detail=detailsByContestId[contestId] as Detail;
                    if (detail) {
                        cd.name=detail.name;
                    } else {
                        cd.name=contestId.toString();
                    }
                    cd.change=ch.changeType[0];
                    cd.oldPrice=ch.oldData[0];
                    cd.newPrice=ch.newData[0];
                    cd.init=ch.initialData[0];
                    cd.times=1;
                    cd.manager=ch.manager[0];
                    cd.client=ch.client[0];
                    cd.type=ch.type[0];
                    
                    trace("loadPrizeChangeHistory::cd.name: " + cd.name);
                    trace("loadPrizeChangeHistory::cd.oldPrice: " + cd.oldPrice);
                    
                    changeHistoryList.addItem(cd);
                }    
                
                if (!changeHistoryList || changeHistoryList.length <= 0) {
                    widget.showHideNoResultPanel(true);
                } else {
                    widget.showHideNoResultPanel(false);
                }
                
                this.priceList=changeHistoryList;
            } else {
                widget.showHideNoResultPanel(true);
            }
        }
        
        /**
         * Updates the summary view. 
         */
        public function updateSummary():void {
            if (!list || list.length <= 0) {
                widget.showHideNoResultPanel(true);
            } else {
                widget.showHideNoResultPanel(false);
            }
            var tmps:ArrayCollection=new ArrayCollection();
            var tmp:Dictionary=new Dictionary();
            var tmp4:Dictionary=new Dictionary();
            var tmp2:ArrayCollection=new ArrayCollection();
            for each (var detail:Detail in list) {
                var monday:Date=generateSundayTime(detail.date);
                if (tmp[monday.time] == null) {
                    var wk:Summary=new Summary(monday);
                    wk.uid=this.uid;
                    tmp[monday.time]=wk;
                    tmps.addItem(wk);
                    
                }
                var week:Summary=tmp[monday.time] as Summary;
                week.fee+=detail.fee;
                week.cost+=detail.dr + detail.prize + /*detail.seprize + */detail.review + detail.spec;
                week.actualA+=(detail.status != "Draft" && detail.status != "Scheduled") ? 1 : 0;
                week.actualB++;
                week.isTotal=false;
                week.details.addItem(detail);
                if (tmp4[detail.client] == null) {
                    var client:Client=new Client();
                    client.uid=this.uid;
                    tmp2.addItem(client);
                    tmp4[detail.client]=client;
                }
                
                var c:Client=tmp4[detail.client] as Client;
                c.client=detail.client;
                c.contests++;
                c.launched+= (detail.status != "Draft" && detail.status != "Scheduled" && detail.status != "Cancelled") ? 1 : 0;
                
            }
            updateRole(role);
            weekList=tmps;

            var sort:Sort=new Sort();
            sort.compareFunction=compareClientContests;
            tmp2.sort = sort;
            tmp2.refresh();  
            cList=tmp2; 
        }

        /**
         * sort client by contests
         *
         */
        private function compareClientContests(a:Object, b:Object, fields:Array=null):int {
            var ca:Client = a as Client;
            var cb:Client = b as Client;
            if (a.contests > b.contests)
              return -1;
            if (a.contests < b.contests)
              return 1;
            else return 0;
        }

        /**
         * sort client by launched 
         *
         */
        private function comparePersonContests(a:Object, b:Object, fields:Array=null):int {
            var ca:Project = a as Project;
            var cb:Project = b as Project;
            if (a.posted > b.posted)
              return -1;
            if (a.posted < b.posted)
              return 1;
            else return 0;
        }
        
        
        /**
         * Creates and returns a new rss feed url that has filters applied.
         * 
         * @return  a new rss feed url that has filters applied.
         * @since 1.0.1
         */
        public function createRssFeedURL():String {
            //return rssFeedURL; // + "&" + getFilterParams();
            var url:String = "http://" + hostAddress + "/tc?module=BasicRSS&t=new_report&c=rss_Pipeline&dsid=28";

            return url;
        }
       
        
        /**
         * Returns the url param string from the filter criteria.
         * 
         * @return the url param string from the filter criteria. 
         * @since 1.0.1 
         */
        private function getFilterParams():String {
            var params:ArrayCollection=new ArrayCollection();
            
            if (filter.type && filter.type != typeValues[0]) {
                params.addItem("type="+filter.type);
            }
            if (filter.start) {
                params.addItem("start="+filter.start);
            }
            if (filter.startAdd) {
                params.addItem("startAdd="+filter.startAdd);
            }
            if (filter.end) {
                var e:Date=new Date();
                e.time=filter.end.time + 1000 * 3600 * 24;
                params.addItem("end="+e.time);
            }
            if (filter.endAdd) {
                var e2:Date=new Date();
                e2.time=filter.endAdd.time + 1000 * 3600 * 24;
                params.addItem("endAdd="+e2.time);
            }
            
            var i:int=0;
            if (filter.confidence && filter.confidence.length > 0) {
                var result:Boolean=false;
                
                for (; i < filter.co.length; i++) {
                    if (filter.co[i] == 1) {
                        params.addItem("co"+i+"="+filter.co[i]);
                    }
                }
            }
            
            i=0;
            if (filter.excludeClients.length > 0) {
                for each (var client:String in filter.excludeClients) {
                    params.addItem("excludeClient"+i+"="+client);
                    i++;
                }
            }
            if (isValidFilter(filter.client)) {
                params.addItem("client="+filter.client);
            }
            if (filter.resourceItem && filter.resourceItem.length > 0 && filter.resourceName && filter.resourceName.length > 0) {
                params.addItem("resourceName="+filter.resourceName);
            }
            if (isValidFilter(filter.status)) {
                params.addItem("status="+filter.status);
            }
            if (isValidFilter(filter.category)) {
                params.addItem("category="+filter.category);
            }
            if (isValidFilter(filter.name)) {
                params.addItem("name="+filter.name);
            }
            if (isValidFilter(filter.project)) {
                params.addItem("project="+filter.project);
            }
            if (filter.numFilter.length > 0) {
                for each (var f:NumbericFilter in filter.numFilter) {
                    params.addItem(f.field+"_min"+f.minValue);
                    params.addItem(f.field+"_max"+f.maxValue);
                }
            }
            if (isValidFilter(filter.ca)) {
                params.addItem("aclient="+filter.ca);
            }
            if (isValidFilter(filter.pa)) {
                params.addItem("apricing="+filter.pa);
            }
            if (isValidFilter(filter.psr)) {
                params.addItem("vspec="+filter.psr);
            }
            if (isValidFilter(filter.dc)) {
                params.addItem("dependent="+filter.dc);
            }
            if (isValidFilter(filter.repost)) {
                params.addItem("repost="+filter.repost);
            }
            if (isValidFilter(filter.ws)) {
                params.addItem("wiki="+filter.ws);
            }
            
            var paramString:String="";
            for (var j:int=0; j < params.length; j++) {
                if (j > 0) {
                    paramString=paramString + "&";
                }
                paramString=paramString + params[j];
            }
            
            return paramString;
        }
        
        /**
         * Checks if given field name has valid value.
         * 
         * @since 1.0.1
         */
        private function isValidFilter(op:String):Boolean {
            if (op != null && op.length > 0) {
                return true;
            }
            
            return false;
        }
    }
}
