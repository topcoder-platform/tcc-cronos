/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.vo {
    import mx.collections.ArrayCollection;
    
    /**
     * <p>
     * This is the data class that holds pipeline summary
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    [Bindable]
    public class Summary {
        public function Summary(week:Date) {
            this.week=week;
            cost=0;
            fee=0;
            actualA=0;
            actualB=0;
            isTotal=false;
            details=new ArrayCollection();
        }
        
        public var week:Date;
        
        public var cost:int;
        
        public var fee:int;
        
        public var actualA:int;
        
        public var actualB:int;
        
        public var uid:String;
        
        public var details:ArrayCollection;
        
        public var isTotal:Boolean;
    
    }
}