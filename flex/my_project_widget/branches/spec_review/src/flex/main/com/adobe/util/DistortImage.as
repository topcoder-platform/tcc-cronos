package com.adobe.util
{
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.display.Graphics;
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.geom.Matrix;
	import flash.geom.Rectangle;

	public class DistortImage
	{
		public var smooth : Boolean;
		// -- texture to distort
		public var texture:BitmapData;
		// -- container Sprite or Shape : the display object containing the distorted picture drawn via graphics.

		private var _container:Object;
		public function get container() : Object
		{
			return _container;
		}

		public function set container( value : Object ) : void
		{
			if( ( value is Shape ) || ( value is Sprite ) )
			{
				_container = value;
			}
			else
			{
				throw new Error('container must be flash.display.Shape or flash.display.Sprite');
			}
		}

		// -- target Object : either a BitmapData or a sprite
		public var target:Object;
		// -- arrays of differents datas types
		public var points:Array;

		/////////////////////////
		/// PRIVATE PROPERTIES //
		/////////////////////////
		private var offsetRect:Rectangle;
		private var _w:Number;
		private var _h:Number;
		private var _xMin:Number;
		private var _xMax:Number;
		private var _yMin:Number
		private var _yMax:Number;
		// -- picture segmentation properties
		private var _hseg:Number;
		private var _vseg:Number;
		private var _hsLen:Number;
		private var _vsLen:Number;
		private var _tri:Array;
		private var _aMcs:Array;

		public function DistortImage()
		{
			smooth = true;
		}

		/*
		* @param vseg Number : the vertical precision
		* @param hseg Number : the horizontal precision
		* @param offsetRect Rectangle : optional, the real bounds to use.
		* @throws: An error if target property isn't a BitmapData or a DisplayObject
		*/
		public function initialize( vseg: Number, hseg: Number, offsetRect: Rectangle = null ) : void
		{
			if( target is BitmapData )
			{
				texture = target as BitmapData;
				_w = texture.width;
				_h = texture.height;
			}
			else if( target is DisplayObject )
			{
				renderVector( offsetRect );
			}
			else
			{
				throw new Error('target must be flash.display.BitmapData or flash.display.DisplayObject');
			}
			_vseg = vseg || 0;
			_hseg = hseg || 0;

			// --
			_aMcs 	= new Array();
			points 		= new Array();
			_tri 	= new Array();
			// --
			__init();
		}

		public function render() : void
		{
			__render();
		}

		/**
		* setTransform
		*
		* @param x0 Number the horizontal coordinate of the first point
		* @param y0 Number the vertical coordinate of the first point
		* @param x1 Number the horizontal coordinate of the second point
		* @param y1 Number the vertical coordinate of the second point
		* @param x2 Number the horizontal coordinate of the third point
		* @param y2 Number the vertical coordinate of the third point
		* @param x3 Number the horizontal coordinate of the fourth point
		* @param y3 Number the vertical coordinate of the fourth point
		*
		* @description : Distort the bitmap to adjust it to those points.
		*/
		public function setTransform( x0:Number , y0:Number ,
										x1:Number , y1:Number ,
										x2:Number , y2:Number ,
										x3:Number , y3:Number ): void
		{
		    var w:Number = _w, h:Number = _h, dx30:Number = x3 - x0, dy30:Number = y3 - y0, dx21:Number = x2 - x1, dy21:Number = y2 - y1, l:int = points.length;
			while( --l > -1 )
			{
				var point:SandyPoint = points[ l ], gx:Number = ( point.x - _xMin ) / w, gy:Number = ( point.y - _yMin ) / h, bx:Number = x0 + gy * ( dx30 ), by:Number = y0 + gy * ( dy30 );

				point.sx = bx + gx * ( ( x1 + gy * ( dx21 ) ) - bx );
				point.sy = by + gx * ( ( y1 + gy * ( dy21 ) ) - by );
			}
			__render();
		}



		/////////////////////////
		///  PRIVATE METHODS  ///
		/////////////////////////
		private function renderVector( offsetRect: Rectangle = null ) : void
		{
			var vector : DisplayObject = target as DisplayObject;

			{
				texture = new BitmapData( vector.width , vector.height, true, 0x00000000 );
				offsetRect = new Rectangle( 0, 0, texture.width, texture.height );
			}

			var m : Matrix = new Matrix();
			m.translate( offsetRect.x * -1, offsetRect.y * -1 );
			texture.draw( vector, m );
			container.transform.matrix.translate( vector.transform.matrix.tx, vector.transform.matrix.ty );
			_w = offsetRect.width;
			_h = offsetRect.height;
		}

		private function __init(): void
		{
			points = new Array();
			_tri = new Array();
			var ix:int, iy:int, w2: Number = _w >> 1, h2: Number = _h >> 1;
			_xMin = _yMin = 0;
			_xMax = _w; _yMax = _h;
			_hsLen = _w / ( _hseg + 1 );
			_vsLen = _h / ( _vseg + 1 );
			var x:Number, y:Number, p0:SandyPoint, p1:SandyPoint, p2:SandyPoint;

			// -- we create the points
			for ( ix = 0 ; ix < _hseg + 2 ; ix++ )
			{
				for ( iy = 0 ; iy < _vseg + 2 ; iy++ )
				{
					x = ix * _hsLen;
					y = iy * _vsLen;
					points.push( new SandyPoint( x, y, x, y ) );
				}
			}
			// -- we create the triangles
			for ( ix = 0 ; ix < _vseg + 1 ; ix++ )
			{
				for ( iy = 0 ; iy < _hseg + 1 ; iy++ )
				{
					p0 = points[ iy + ix * ( _hseg + 2 ) ];
					p1 = points[ iy + ix * ( _hseg + 2 ) + 1 ];
					p2 = points[ iy + ( ix + 1 ) * ( _hseg + 2 ) ];
					__addTriangle( p0, p1, p2 );
					// --
					p0 = points[ iy + ( ix + 1 ) * ( _vseg + 2 ) + 1 ];
					p1 = points[ iy + ( ix + 1 ) * ( _vseg + 2 ) ];
					p2 = points[ iy + ix * ( _vseg + 2 ) + 1 ];
					__addTriangle( p0, p1, p2 );
				}
			}
		}

		private function __addTriangle( p0:SandyPoint, p1:SandyPoint, p2:SandyPoint ):void
		{
			var u0:Number, v0:Number, u1:Number, v1:Number, u2:Number, v2:Number, tMat:Matrix = new Matrix();
			// --
			u0 = p0.x; v0 = p0.y;
			u1 = p1.x; v1 = p1.y;
			u2 = p2.x; v2 = p2.y;
			tMat.tx = -v0*(_w / (v1 - v0));
			tMat.ty = -u0*(_h / (u2 - u0));
			tMat.a = tMat.d = 0;
			tMat.b = _h / (u2 - u0);
			tMat.c = _w / (v1 - v0);
			// --
			_tri.push( new Triangle( p0, p1, p2, tMat ) );
		}

		private function __render(): void
		{
			var vertices: Array, p0:SandyPoint, p1:SandyPoint, p2:SandyPoint, x0:Number, y0:Number, ih:Number = 1/_h, iw:Number = 1/_w;
			var c : Graphics = Graphics( container.graphics );
			c.clear();

			var a:Triangle, sM:Matrix = new Matrix(), tM:Matrix = new Matrix(), l:int = _tri.length;
			while( --l > -1 )
			{
				a 	= Triangle( _tri[ l ] );
				p0 	= a.p0;
				p1 	= a.p1;
				p2 	= a.p2;
				tM = a.tMat;
				// --
				sM.a = ( p1.sx - ( x0 = p0.sx ) ) * iw;
				sM.b = ( p1.sy - ( y0 = p0.sy ) ) * iw;
				sM.c = ( p2.sx - x0 ) * ih;
				sM.d = ( p2.sy - y0 ) * ih;
				sM.tx = x0;
				sM.ty = y0;
				// --
				sM = __concat( sM, tM );

				c.beginBitmapFill( texture, sM, false, smooth );
				c.moveTo( x0, y0 );
				c.lineTo( p1.sx, p1.sy );
				c.lineTo( p2.sx, p2.sy );
				c.endFill();
			}
		}

		private function __concat( m1:Matrix, m2:Matrix ):Matrix
		{
			//Relies on the original triangles being right angled with p0 being the right angle.
			//Therefore a = d = zero (before and after invert)
			var mat : Matrix = new Matrix();
			mat.a  = m1.c * m2.b;
			mat.b  = m1.d * m2.b;
			mat.c  = m1.a * m2.c;
			mat.d  = m1.b * m2.c;
			mat.tx = m1.a * m2.tx + m1.c * m2.ty + m1.tx;
			mat.ty = m1.b * m2.tx + m1.d * m2.ty + m1.ty;
			return mat;
		}
	}
}
