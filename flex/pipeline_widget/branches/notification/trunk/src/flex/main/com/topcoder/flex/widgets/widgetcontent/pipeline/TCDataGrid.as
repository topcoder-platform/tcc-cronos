package com.topcoder.flex.widgets.widgetcontent.pipeline
{
    import flash.display.GradientType;
    import flash.display.Sprite;
    import flash.events.MouseEvent;
    import flash.geom.Matrix;

    import mx.controls.DataGrid;
    import mx.controls.dataGridClasses.DataGridHeader;
    import mx.controls.dataGridClasses.DataGridItemRenderer;
    import mx.controls.listClasses.IListItemRenderer;
    import mx.core.FlexSprite;
    import mx.core.UIComponent;

    [Style(name="sortedHeaderColors",type="Array",format="Color",inherit="no")]
    [Style(name="rowSelectionColors",type="Array",format="Color",inherit="no")]

    public class TCDataGrid extends DataGrid
    {
        private var currentHeaderRenderer:DataGridItemRenderer = null;

        private var previousHeaderRenderer:DataGridItemRenderer = null;

        public function TCDataGrid() {
            super();
        }

        override protected function childrenCreated():void {
            super.childrenCreated();
            header.setStyle("paddingTop", "5");

        }

        override protected function drawHighlightIndicator(
                                indicator:Sprite, xx:Number, yy:Number,
                                ignoredWidth:Number, h:Number, color:uint,
                                ignoredRenderer:IListItemRenderer):void {
            var w:int = unscaledWidth - viewMetrics.left - viewMetrics.right;
            drawGradient(indicator, width, rowHeight, getStyle("rowSelectionColors"));
            indicator.x = xx;
            indicator.y = yy;
        }

        override protected function drawSelectionIndicator(
                                indicator:Sprite, xx:Number, yy:Number,
                                ignoredWidth:Number, h:Number, color:uint,
                                ignoredRenderer:IListItemRenderer):void {
            var w:int = unscaledWidth - viewMetrics.left - viewMetrics.right;
            drawGradient(indicator, width, rowHeight, getStyle("rowSelectionColors"));
            indicator.x = xx;
            indicator.y = yy;
        }

        override protected function mouseUpHandler(event:MouseEvent):void
        {
            super.mouseUpHandler(event);
            drawHeaderGradient(event);
        }

        override protected function mouseDownHandler(event:MouseEvent):void
        {
            super.mouseDownHandler(event);
            drawHeaderGradient(event);
        }

        override protected function mouseOverHandler(event:MouseEvent):void
        {
            super.mouseOverHandler(event);
            drawHeaderGradient(event);
        }

        override protected function mouseOutHandler(event:MouseEvent):void
        {
            super.mouseOutHandler(event);
            drawHeaderGradient(event);
        }

        private function drawHeaderGradient(event:MouseEvent):void {
            var spriteName:String = "sortHeaderCollection";
            var s:Sprite = Sprite(header.getChildByName(spriteName));
            var arrow:FlexSprite = FlexSprite(header.getChildByName("sortArrowHitArea"));

            if(!s) {
                s = new FlexSprite();
                s.name = spriteName;
                if (event.type == MouseEvent.MOUSE_DOWN) {
                    header.addChildAt(s, 2);
                }
            }

            if (event.target is DataGridItemRenderer) {

                var itemRenderer:DataGridItemRenderer = event.target as DataGridItemRenderer;

                if (itemRenderer.parent is DataGridHeader) {
                    var dgHeader:DataGridHeader = itemRenderer.parent as DataGridHeader;
                    previousHeaderRenderer = currentHeaderRenderer;
                    currentHeaderRenderer = itemRenderer;

                    var spriteWidth:int = itemRenderer.width;

                    if (arrow != null) {
                        var nextItemRenderer:DataGridItemRenderer =
                            dgHeader.getChildAt(dgHeader.getChildIndex(itemRenderer)+1)
                            as DataGridItemRenderer;
                        if (nextItemRenderer != null) {
                            if ((spriteWidth + arrow.width) <= (nextItemRenderer.x - itemRenderer.x)) {
                                spriteWidth += arrow.width;
                            }
                        } else {
                            // This code deals with the width of the final column header
                            var nextItem:UIComponent = dgHeader.getChildAt(dgHeader.getChildIndex(itemRenderer)+1)
                                as UIComponent;
                            var lastSeparator:Sprite = nextItem.getChildAt(nextItem.numChildren - 1) as Sprite;
                            if (lastSeparator.x > itemRenderer.x) {
                                if ( arrow.x > itemRenderer.x &&
                                    ((itemRenderer.x + itemRenderer.width) <= lastSeparator.x) ) {
                                    spriteWidth += arrow.width;
                                }
                            } else if (arrow.x > itemRenderer.x &&
                                       ((itemRenderer.x + itemRenderer.width) < (this.x + this.width)) ) {
                                spriteWidth += arrow.width;
                            }
                        }
                    }

                    if (itemRenderer.width < spriteWidth) {
                        itemRenderer.width = spriteWidth;
                    }
                    /*if (itemRenderer.width != spriteWidth) {
                        itemRenderer.x += arrow.width;
                    }*/

                    if (event.type == MouseEvent.MOUSE_DOWN) {
                        drawGradient(s, spriteWidth, headerHeight, getStyle("sortedHeaderColors"));
                        s.x = itemRenderer.x-1;
                        s.y = itemRenderer.parent.y - 1;
                        itemRenderer.styleName = "SortedDataGridHeader";
                    }
                }

                if (currentHeaderRenderer != null && (currentHeaderRenderer.x - 1 == s.x)) {
                    currentHeaderRenderer.styleName = "SortedDataGridHeader";
                }

                if (event.type == MouseEvent.MOUSE_DOWN) {
                    if (previousHeaderRenderer != null && currentHeaderRenderer != previousHeaderRenderer) {
                        if (previousHeaderRenderer.styleName == "SortedDataGridHeader") {
                            previousHeaderRenderer.styleName = "DataGridHeader";
                        }
                    }
                }
            }
        }

        private function drawGradient(target:Sprite, width:int, height:int, colors:Array):void {
            var gMatrix:Matrix = new Matrix();
            gMatrix.createGradientBox(width, height, 90, 0, 0);

            target.graphics.clear();
            target.graphics.beginGradientFill(
                GradientType.LINEAR, colors, [1.0, 1.0], [0, 255], gMatrix);

            target.graphics.drawRect(0, 0, width, height);
        }
    }
}
