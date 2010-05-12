/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.component.skin {
    import mx.skins.ProgrammaticSkin;
    import flash.display.Graphics;
    
    /**
     * <p>
     * This defines a null css skin.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class NullSkin extends ProgrammaticSkin {
        public function NullSkin() {
            super();
        }
        
        override public function get measuredWidth():Number {
            return 0;
        }
        
        override public function get measuredHeight():Number {
            return 0;
        }
        
        override protected function updateDisplayList(w:Number, h:Number):void {
        }
    }
}