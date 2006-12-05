
package com.topcoder.util.image.manipulation;
import java.awt.Color;
import java.awt.image.RenderedImage;
/**
 * Interface Image <p>Purpose: This is the main interface of the compoennt and gives the contract for an image. The interface is made very simple to use and defines an image as a rectangular array of pixels with each pixel having a specific color. The color of a pixel is a java.awt.Color object. The contract allows for setting and getting of the color of individual pixels. The contract also requires that the underlying image be returned as a RenderableImage. This allows the image to be easily integrated into other Java applications.</p> <p>Implementation: Since the contract forces the image to be a rectangular array of pixels, implementations would likely use a raster underneath to perform the operations. A raster would simplify the implementation of set/getPixel as also allow convertibility to RenderedImage. It is also desired that implementations have a constructor whose signatures is as described below.</p> <p>Constructor(width:int, height:int) - This would construct an image of the given height and width.</p> <p>Thread Safety: Since the setting of pixel colors is allowed, thread safety is not required. Any multi-threaded applications should synchronize access to the Image object. Making the methods of this contract synchronized would be very expensive for normal operations.</p>
 * 
 */
public interface Image {
/**
 * <p>getWidth():int</p> <p>Purpose: Returns the width of this image.</p> <p>Args: None.</p> <p>Returns: The width of this image.</p> <p>Exceptions: None.</p> <p></p>
 * 
 * 
 * @return 
 */
    public int getWidth();
/**
 * <p>getHeight():int</p> <p>Purpose: Returns the height of this image.</p> <p>Args: None.</p> <p>Returns: The height of this image.</p> <p>Exceptions: None.</p> <p></p>
 * 
 * 
 * @return 
 */
    public int getHeight();
/**
 * <p>getPixel(x:int, y:int):Color</p> <p>Purpose: Returns the color of the pixel at co-ordinate (x,y).</p> <p>Args:</p> <p>x - The x-co-ordinate of the pixel. It must satisfy 0&lt;=x&lt;width</p> <p>y - The y-co-ordinate of the pixel. It muts satisfy 0&lt;=y&lt;height</p> <p>Returns: The color of the pixel at co-ordinate (x,y).</p> <p>Exceptions:</p> <p>IllegalArgumentException - if either x or y is out of range.</p> <p></p>
 * 
 * 
 * @param x 
 * @param y 
 * @return 
 */
    public java.awt.Color getPixel(int x, int y);
/**
 * <p>setPixel(x:int, y:int, color:Color)</p> <p>Purpose: Sets the color of the pixel at co-ordinate (x,y).</p> <p>Args:</p> <p>x - The x-co-ordinate of the pixel. It must satisfy 0&lt;=x&lt;width</p> <p>y - The y-co-ordinate of the pixel. It muts satisfy 0&lt;=y&lt;height</p> <p>color - The desired color of the pixel. Must not be null.</p> <p>Exceptions:</p> <p>IllegalArgumentException - if either x or y is out of range or color is null.</p> <p></p>
 * 
 * 
 * @param x 
 * @param y 
 * @param color 
 */
    public void setPixel(int x, int y, java.awt.Color color);
/**
 * <p>getAsRenderedImage():RenderedImage</p> <p>Purpose: Returns the image represented by this object as an instance of the RenderedImage interface.</p> <p>Args: None.</p> <p>Returns: An instance of RenderedImage that represents the image represented by this object.</p> <p>Exceptions: ImageConversionException - If the imaage cannot be converted into a RenderedImage.</p> <p></p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.image.manipulation.RenderedImage getAsRenderedImage();
}


