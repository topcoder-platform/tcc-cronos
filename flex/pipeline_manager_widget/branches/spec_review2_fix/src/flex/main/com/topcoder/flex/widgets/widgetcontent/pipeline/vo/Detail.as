/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.vo {
    
    import com.topcoder.flex.model.IWidgetFramework;
    
    /**
     * <p>
     * This is the data class that defines info related to pipeline details.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    [Bindable]
    public class Detail {
        public var uid:String;
        
        public var pipelineInfoId:Number;
        
        public var contestId:Number;
        
        public var name:String;
        
        public var projectId:Number;
        
        public var project:String;
        
        public var type:String;
        
        public var category:String;
        
        public var status:String;
        
        public var client:String;
        
        public var date:Date;
        
        public var duration:int;
        
        public var prize:int;
        
        public var dr:int;
        
        public var fee:int;
        
        public var review:int;
        
        public var spec:int;
        
        public var manager:String
        
        public var arch:String
        
        public var reviewer:String
        
        public var specer:String
        
        public var notes:String;
        
        public var aclient:Boolean;
        
        public var apricing:Boolean;
        
        public var vspec:Boolean;
        
        public var dependent:Boolean;
        
        public var repost:Boolean;
        
        public var hasWikiSpec:Boolean;
        
        public var desc:String;
        
        public var long:String;
        
        public var short:String;
        
        public var version:String;
        
        public var eligibity:String;
        
        public var added:Date;
        
        public var changed:Date;
        
        public var pperm:String;
        
        public var cperm:String;
        
        public var widgetFramework:IWidgetFramework;
        
        public var cpname:String;
        
        public function Detail() {
            super();
        }
    }
}
