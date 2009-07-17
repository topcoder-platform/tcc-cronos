package com.topcoder.flex.widgets.widgetcontent.submissionviewerwidget.skins
{
	import flash.geom.ColorTransform;
	
	import mx.skins.ProgrammaticSkin;
	
	public class RankButtonSelectedSkin extends ProgrammaticSkin
	{
		public function RankButtonSelectedSkin()
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