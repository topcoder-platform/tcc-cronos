package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.skins
{
	import mx.skins.ProgrammaticSkin;
	
	public class PurchaseLogoSkin extends ProgrammaticSkin
	{
		public function PurchaseLogoSkin()
		{
		}
		
		override protected function updateDisplayList( unscaledWidth:Number,
                                                       unscaledHeight:Number ):void
        {
            var backgroundColor:Number = 0x15B846;
            
            graphics.clear();
            
            graphics.beginFill(backgroundColor);
            graphics.drawCircle(10, 10, 10);
            graphics.endFill();
        }
	}
}