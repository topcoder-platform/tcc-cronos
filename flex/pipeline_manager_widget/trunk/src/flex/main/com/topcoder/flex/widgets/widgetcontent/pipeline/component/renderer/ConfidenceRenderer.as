/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.pipeline.component.renderer {
    
    /**
     * <p>
     * A renderer for confidence.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     */
    public class ConfidenceRenderer extends RendererBase {
        override protected function renderLabel():void {
            this.content.text=detail.confidence;
        }
        
        override protected function doFilter():void {
            if (model) {
                model.filter.confidence=detail.confidence;
                model.filter.co=[1, 0, 0];
                model.filterDetail();
            }
        }
    }
}