/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.log {
    
    /**
     * <p>
     * This is the convenience logger class.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class TCLog {
        private static var _instance:TCLog;
        
        public static function get instance():TCLog {
            if (!_instance) {
                _instance=new TCLog();
                
            }
            
            return _instance;
        }
        
        public function TCLog() {
            super();
        }
        
        public function debug(res:Object):void {
            trace(res);
        }
        
        private var last:Date;
        
        private var lastAction:String;
        
        private var uid:String;
        
        public function timeStampLog(res:Object, uid:String):void {
            if (this.uid) {
                if (this.uid != uid) {
                    return;
                }
            } else {
                this.uid=uid;
            }
            var current:Date=new Date();
            var time:Number;
            if (last) {
                time=(current.getTime() - last.getTime()) / 1000;
            } else {
                time=0;
            }
            last=current;
            trace("[Time span:" + time.toString() + "]" + res);
        }
    
    }
}