/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.flex.widgets.widgetcontent.projectadminwidget.com.renderer {
    import com.topcoder.flex.widgets.widgetcontent.projectadminwidget.ProjectAdminWidget;
    import com.topcoder.flex.widgets.widgetcontent.projectadminwidget.qs.model.Model;

    import flash.display.GradientType;
    import flash.display.InterpolationMethod;
    import flash.display.SpreadMethod;
    import flash.geom.Matrix;

    import mx.controls.DataGrid;
    import mx.controls.dataGridClasses.DataGridColumn;
    import mx.skins.ProgrammaticSkin;

    /**
     * <p>
     * This is the programmatic skin class for rendering data grid header.
     * </p>
     * <p>Thread Safety: ActionScript 3 only executes in a single thread so thread
     * safety is not an issue.</p>
     *
     * @author snow01
     * @version 1.0
     * @since Cockpit Share Submission Integration
     */
    public class DGHeaderSkin extends ProgrammaticSkin {

        /**
         * Default empty constructor.
         */
        public function DGHeaderSkin() {
            super();
        }

        /**
         * Over-rides updated display list method to implement custom ui rendering.
         *
         * @param unscaledWidth unscaled width
         * @param unscaledHeight unscaled height.
         */
        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {

            var myMatrix:Matrix=new Matrix();
            //trace(myMatrix.toString()); // (a=1, b=0, c=0, d=1, tx=0, ty=0)
            myMatrix.createGradientBox(20, unscaledHeight, Math.PI / 2, 0, 0);
            //trace(myMatrix.toString()); // (a=0.1220703125, b=0, c=0, d=0.1220703125, tx=150, ty=150)
            var colors:Array=[0xECECEC, 0xD3D3D3];
            var alphas:Array=[100, 100];
            var ratios:Array=[0, 0xFF];

            this.graphics.beginGradientFill(GradientType.LINEAR, colors, alphas, ratios, myMatrix, SpreadMethod.PAD, InterpolationMethod.LINEAR_RGB);
            this.graphics.drawRect(0, 0, unscaledWidth, unscaledHeight);
            this.graphics.endFill();
            var headerRenderer:DataGrid=this.parent.parent.parent as DataGrid;
            if (headerRenderer && headerRenderer.columns) {
                var x:Number=0;
                for (var i:int=0; i < headerRenderer.columnCount; i++) {
                    var column:DataGridColumn=headerRenderer.columns[i] as DataGridColumn;
                    if (i == Model.instance.sortIndex) {
                        colors=[0x920000, 0xD64B4D];
                        this.graphics.beginGradientFill(GradientType.LINEAR, colors, alphas, ratios, myMatrix, SpreadMethod.PAD, InterpolationMethod.LINEAR_RGB);
                        this.graphics.drawRect(x, 0, column.width, unscaledHeight);
                        this.graphics.endFill();
                        return;
                    }
                    x+=column.width;
                }
            }
        }

    }
}