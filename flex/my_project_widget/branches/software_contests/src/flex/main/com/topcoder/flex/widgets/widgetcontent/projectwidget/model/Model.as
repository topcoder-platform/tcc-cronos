/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectwidget.model {
    import com.topcoder.flex.widgets.widgetcontent.projectwidget.ProjectWidget;
    import com.topcoder.flex.widgets.widgetcontent.projectwidget.vo.Contest;
    import com.topcoder.flex.widgets.widgetcontent.projectwidget.vo.Project;

    import flash.events.Event;
    import flash.net.URLLoader;
    import flash.net.URLLoaderDataFormat;
    import flash.net.URLRequest;

    import mx.collections.ArrayCollection;
    import mx.events.CollectionEvent;
    import mx.events.CollectionEventKind;
    import mx.formatters.DateFormatter;

    /**
     * <p>
     * This is the data model class for this my project widget.
     * </p>
     * 
     * <p>
     * Updated for Cockpit Release Assembly 2 [BUGR-1830]
     *    - Removed the singleton nature of this class.
     * </p>
     * 
     * Version 1.0.1 (Cockpit Release Assembly 4) Change Notes:
     *    - commented variable index, that is no more used.
     * 
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author TCSDEVELOPER, TCSASSEMBLER
     * @version 1.0.1
     * @since My Project Overhaul Assembly.
     */
    [Bindable]
    public class Model {
        /**
         * If widget is in max mode.
         */
        public var isMax:Boolean=true;

        //
        // Commented for Version 1.0.1
        //     - Unused
        //
        ///**
        // * The currently selected project in the widget.
        // */
        //public var selectedProject:Project;

        /**
         * The contest of the current selected project.
         */
        public var contest:ArrayCollection;

        /**
         * True if arrow can be shown otherwise false.
         */
        public var canShowArrow:Boolean;

        /**
         * Drag source index.
         */
        public var dragSourceIdx:int;

        /**
         * Indicates if drag is started.
         */
        public var doDrag:Boolean=false;

        /**
         * Date formatter for formatting the display of date.
         */
        public var dateFormatter:DateFormatter;

        /**
         * True if all contests groups are closed otherwise false.
         */
        public var closeAll:Boolean;

        /**
         * Indicates if drag has been stopped.
         */
        public var stopDrag:Boolean;

          //
          // Commented for Version 1.0.1 - no more needed, new implementation does it in different way.
          //
          //        /**
          //         *
          //         */
          //        public var index:int=-1;

        /**
         * Current project of this widget.
         */
        public var currentProj:Project;

        /**
         * Project list of this widget.
         */
        public var proList:ArrayCollection;

        /**
         * True if project is changed else false.
         */
        public var changeProj:Boolean;

        /**
         * True if date chooseer is shown else false.
         */
        public var showDateChooser:Boolean=false;

        /**
         * Start date of the date chooser.
         */
        private static var s:Date;

        /**
         * End date of the date chooser.
         */
        private static var e:Date;

        /**
         * A simple constructor that initializes date format etc.
         */
        public function Model() {
            var tmp:Array=["1", "2", "3", "4"];
            contest=new ArrayCollection(tmp);
            dateFormatter=new DateFormatter();
            dateFormatter.formatString="YYYY-MM-DD JJ:NN:SS";
        }

        /**
         * Gets the row index of the given target object in contest collection.
         */
        public function getListIndex(target:Object):int {
            if (contest.contains(target)) {
                return contest.getItemIndex(target);
            }
            return -1;
        }

        /**
         * Event handler for adjusting (ordering) the contest list.
         *
         * @param source the source which initiated this event.
         * @param target the target object.
         * @param before true if to be added before else false.
         */
        public function adjustList(source:Object, target:Object=null, before:Boolean=true):void {
            if (source && contest.contains(source)) {
                var event:CollectionEvent=new CollectionEvent(CollectionEvent.COLLECTION_CHANGE);
                event.kind=CollectionEventKind.MOVE;
                var i:int=contest.getItemIndex(source);
                event.oldLocation=i;
                contest.removeItemAt(i);
                if (target && contest.contains(target)) {
                    var index:int=contest.getItemIndex(target);
                    if (before) {
                        contest.addItemAt(source, index);
                        event.location=index;
                    } else {
                        contest.addItemAt(source, index + 1);
                        event.location=index + 1;
                    }
                } else {
                    contest.addItem(source);
                    event.location=contest.length - 1;
                }
                contest.dispatchEvent(event);
            }
        }

        /**
         * Sets the date filter for the current project.
         *
         * @param start state date of the project.
         * @param end date of the project.
         */
        public function setDateFilter(start:Date, end:Date):void {
            if (!start || !end) {
                s=null;
                e=null;
                currentProj.startDateFilter="";
                currentProj.endDateFilter="";
            } else {
                s=new Date(start);
                e=new Date(end);
                currentProj.startDateFilter=s.getFullYear().toString() + '-' + (s.getMonth() + 1).toString() + '-' + s.getDate();
                currentProj.endDateFilter=e.getFullYear().toString() + '-' + (e.getMonth() + 1).toString() + '-' + e.getDate();
            }
            currentProj.contests.refresh();
            changeProj=!changeProj;
        }

        /**
         * Callback function for project contests filters.
         *
         * @param obj contest to filter.
         */
        public static function filterDate(obj:Object):Boolean {
            if (!s || !e) {
                return true;
            }
            var cont:Contest=obj as Contest;
            trace(cont.start.toString() + ", " + cont.end.toString());
            trace(s.toString() + ", " + e.toString());
            if (cont && (cont.start >= s && cont.start <= e) || (s >= cont.start && s <= cont.end)) {
                return true;
            }
            return false;
        }
    }
}
