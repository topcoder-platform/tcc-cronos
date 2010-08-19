/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectadminwidget.qs.vo {
    import mx.collections.ArrayCollection;

    /**
     * <p>
     * This class is for storing user data.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     * @since Cockpit Share Submission Integration
     */
    [Bindable]
    public class User {
        /**
         * Id of the user.
         */
        public var id:int;

        /**
         * Name of the user.
         */
        public var name:String;

        /**
         * Collection of projects for the user.
         */
        public var projects:ArrayCollection=new ArrayCollection();
        
        
        private var _projLen:int=0;

        /**
         * An empty default constructor.
         */
        public function User() {
            super();
        }

        /**
         * Gets the project count for this user.
         *
         * @return project count.
         */
        public function get projLen():int {
            return _projLen;//projects.length;
        }
        public function set projLen(v:int):void{
             _projLen=v;//projects.length;
        }

        /**
         * Gets the project for the given project id.
         *
         * @param pid given project id.
         * @return the found project, null if none is found.
         */
        public function getProject(pid:int):Project {
            for each (var p:Project in projects) {
                trace("p.id: " + p.id + ", pid: " + pid);
                if (p.id == pid) {
                    return p;
                }
            }

            return null;
        }

        /**
         * Gets the contest for the given project id and contest id.
         *
         * @param pid given project id.
         * @param cid given contest id.
         * @return the found contest, null if none is found.
         */
        public function getContest(pid:int, cid:int):Contest {
            var p:Project=getProject(pid);
            if (!p) {
                return null;
            }

            for each (var c:Contest in p.contests) {
                if (c.id == cid) {
                    trace("c.id: " + p.id + ", cid: " + cid);
                    return c;
                }
            }

            return null;
        }

        /**
         * Forms the xml tree that need to be rendered for the given user.
         *
         * @return xml tree to be rendered.
         */
        public function getTreeXML():XMLList {
            var str:String="";

            var i:int=0;
            //BUGR-1925
            var quotPattern:RegExp =  new RegExp("\"", "g");
            var lABPattern:RegExp =  new RegExp("<", "g");
            var rABPattern:RegExp = new RegExp(">", "g");
            for each (var p:Project in projects) {
                var nodeStr:String;
                var poutput:String=p.name;
                //BUGR-1925
                poutput = poutput.replace(quotPattern, "&quot;");
                poutput = poutput.replace(lABPattern, "&lt;");
                poutput = poutput.replace(rABPattern, "&gt;");
                if (p.access.toLowerCase().indexOf("read") >= 0) {
                    poutput+="[Read]";
                }
                if (p.access.toLowerCase().indexOf("write") >= 0) {
                    poutput+="[Read][Write]";
                }
                if (p.access.toLowerCase().indexOf("full") >= 0) {
                    poutput+="[Read][Write][Full]";
                }
                if (i >= projects.length - 1) {
                    nodeStr="<node label=\"" + poutput + "\" id=\"" + p.id + "\" type=\"project\" last=\"true\">";
                } else {
                    nodeStr="<node label=\"" + poutput + "\" id=\"" + p.id + "\" type=\"project\" last=\"false\">";
                }

                var j:int=0;
                for each (var c:Contest in p.contests) {
                    var output:String=c.name;
                    //BUGR-1925
                    output = output.replace(quotPattern, "&quot;");
                    output = output.replace(lABPattern, "&lt;");
                    output = output.replace(rABPattern, "&gt;");
                    if (c.access.toLowerCase().indexOf("read") >= 0) {
                        output+="[Read]";
                    }
                    if (c.access.toLowerCase().indexOf("write") >= 0) {
                        output+="[Read][Write]";
                    }
                    if (c.access.toLowerCase().indexOf("full") >= 0) {
                        output+="[Read][Write][Full]";
                    }
                    if (j >= p.contests.length - 1) {
                        nodeStr+="<node label=\"" + output + "\" id=\"" + c.id + "\" pid=\"" + p.id + "\" type=\"contest\" last=\"true\" />";
                    } else {
                        nodeStr+="<node label=\"" + output + "\" id=\"" + c.id + "\" pid=\"" + p.id + "\" type=\"contest\" last=\"false\" />";
                    }

                    j++;
                }

                nodeStr+="</node>";
                str+=nodeStr;
                i++;
            }

            return new XMLList(str);
        }
    }
}
