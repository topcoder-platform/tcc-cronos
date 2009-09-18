package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.skins
{
	import mx.skins.ProgrammaticSkin;
	
	public class RankLogoSkin extends ProgrammaticSkin
	{
		public function RankLogoSkin()
		{
		}
		
		override protected function updateDisplayList( unscaledWidth:Number,
                                                       unscaledHeight:Number ):void
        {
            var backgroundColor:Number = 0xF4000A;
            
            graphics.clear();
            
            graphics.beginFill(backgroundColor);
            graphics.drawCircle(12, 12, 10);
            graphics.endFill();
        }


	}
}