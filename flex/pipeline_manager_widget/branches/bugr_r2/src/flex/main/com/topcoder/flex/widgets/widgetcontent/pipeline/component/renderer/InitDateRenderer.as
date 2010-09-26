/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.component.renderer {
    import com.topcoder.flex.widgets.widgetcontent.pipeline.vo.ChangeDate;
    
    /**
     * <p>
     * A renderer for initial date.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class InitDateRenderer extends DateRendererBase {
        public function InitDateRenderer() {
            super();
        }
        
        override protected function renderLabel(cds:ChangeDate):String {
            if (cds && cds.init) {
                return formatter.format(cds.init);
            }
            return "";
        }
    
    }
}