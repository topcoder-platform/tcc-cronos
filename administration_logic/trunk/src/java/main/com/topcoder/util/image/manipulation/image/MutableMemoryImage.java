
package com.topcoder.util.image.manipulation.image;

import java.awt.image.BufferedImage;

/**
 * Class BufferedImage <p>Purpose: This class implements the Image interface using java.awt.image.BufferedImage to perform the actual image manipulation. A BufferedImage is already a WriteableRaster and hence provides pixel setting and getting capabilities. It is also an instance of RenderedImage and thus conversion to RenderedImage also does not pose problems. By using BufferedImage, this class already provides the needed support for transparent colors.</p> <p>To ensure decoding support for GIF, JPEG and PNG (at minimum), this class use the JAI &quot;fileload&quot; operator which has support for these and many more file formats. It thus more than fulfills the requirements with respect to decoding and color models.</p> <p>Implementation: The class maintains a reference to an instance of BufferedImage. This reference is used to set and get pixel colors and also to set and get image height. The constructors of the class all create the appropriate BufferedImage insatnce. The file and stream load constructors, use the JAI fileload operator to get a RenderedOp object and from there a BufferedImage. In the case of the empty image, the appropriate BufferedImage constructor is used.</p> <p>Thread-Safety: This class is not thread safe as it allows setting of pixel colors which changes its state. It would be very expensive in the usual course of action if we were to make the set/get methods synchronized. Therefore, applications will have to synchronize their access on this object if they wish to achieve thread-safety. Alternatively, if this image would be used in a read-only fashion without any setPixel operations, no synchronization is required.</p> <p></p>
 * 
 */
public class MutableMemoryImage implements com.topcoder.util.image.manipulation.Image {


/**
 * <p>+DEFAULT_TYPE:int=java.awt.image.BufferedImage.TYPE_INT_ARGB</p> <p>Purpose: Stores the type of the default BufferedImage created.</p> <p>Implementation: This variable is created statically and finally.</p> <p>Usage: It is used by the BufferedImage(width,height) constructor to deicde the type of the udnerlying buffered image.</p> <p></p>
 * 
 */
    private static final int DEFAULT_TYPE = BufferedImage.TYPE_INT_ARGB;

/**
 * <p>Purpose: Creates a buffered image that is of the given height and width.</p> <p>Args:</p> <p>height - The height of the image. Must not be negative.</p> <p>width - The width of the image. Must not be negative.</p> <p>Implementation:</p> <p>Create a new java.awt.image.BufferedImage with the given height and width. The type of the image will be the default type.</p> <p>Exceptions:</p> <p>IllegalArgumentException - If either height or width is illegal.</p> <p></p>
 * 
 * 
 * @param width 
 * @param height 
 */
    public  MutableMemoryImage(int width, int height) {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object from an existing buffered image.</p> <p>Args:</p> <p>bufferedImage - The existing bufefred image to be used. Must not be null.</p> <p>Implementation:</p> <p>Simply assign a copy of the argument to the member variable.</p> <p>Exceptions:</p> <p>IllegalArgumentException - If the given image is null.</p> <p></p>
 * 
 * 
 * @param bufferedImage 
 */
    public  MutableMemoryImage(BufferedImage bufferedImage) {
        // your code here
    } 

/**
 * <p>getWidth():int</p> <p>Purpose: Returns the width of this image.</p> <p>Args: None.</p> <p>Implementation:</p> <p>Simply return bufferedImage.getWidth()</p> <p>Returns: The width of this image.</p> <p>Exceptions: None.</p> <p></p>
 * 
 * 
 * @return 
 */
    public int getWidth() {        
        // your code here
        return 0;
    } 

/**
 * <p>getHeight():int</p> <p>Purpose: Returns the height of this image.</p> <p>Args: None.</p> <p>Implementation:</p> <p>Simply return bufferedImage.getHeight()</p> <p>Returns: The height of this image.</p> <p>Exceptions: None.</p> <p></p>
 * 
 * 
 * @return 
 */
    public int getHeight() {        
        // your code here
        return 0;
    } 

/**
 * <p>getPixel(x:int, y:int):Color</p> <p>Purpose: Returns the color of the pixel at co-ordinate (x,y).</p> <p>Args:</p> <p>x - The x-co-ordinate of the pixel. It must satisfy 0&lt;=x&lt;width</p> <p>y - The y-co-ordinate of the pixel. It muts satisfy 0&lt;=y&lt;height</p> <p>Implementation:</p> <p>Create and return a java.awt.Color object from the sRGB color returned by bufferedImage.getRGB(x,y)</p> <p>Returns: The color of the pixel at co-ordinate (x,y).</p> <p>Exceptions:</p> <p>IllegalArgumentException - if either x or y is out of range.</p> <p></p>
 * 
 * 
 * @param x 
 * @param y 
 * @return 
 */
    public java.awt.Color getPixel(int x, int y) {        
        // your code here
        return null;
    } 

/**
 * <p>setPixel(x:int, y:int, color:Color)</p> <p>Purpose: Sets the color of the pixel at co-ordinate (x,y).</p> <p>Args:</p> <p>x - The x-co-ordinate of the pixel. It must satisfy 0&lt;=x&lt;width</p> <p>y - The y-co-ordinate of the pixel. It muts satisfy 0&lt;=y&lt;height</p> <p>color - The desired color of the pixel. Must not be null.</p> <p>Implementation:</p> <p>Get the ARGB value from the Color object. Call bufferedImage.setRGB(x,y,rgb);</p> <p>Exceptions:</p> <p>IllegalArgumentException - if either x or y is out of range or color is null.</p> <p></p>
 * 
 * 
 * @param x 
 * @param y 
 * @param color 
 */
    public void setPixel(int x, int y, java.awt.Color color) {        
        // your code here
    }

/**
 * <p>getAsRenderedImage():RenderedImage</p> <p>Purpose: Returns the image represented by this object as an instance of the RenderedImage interface.</p> <p>Args: None.</p> <p>Implementation:</p> <p>Make a copy of the underlying BufferedImage (possibly using getSubImage()) and return it.</p> <p>Returns: An instance of RenderedImage that represents the image represented by this object.</p> <p>Exceptions: None.</p> <p></p>
 *
 *
 * @return
 */
    public com.topcoder.util.image.manipulation.RenderedImage getAsRenderedImage() {
        // your code here
        return null;
    }
/**
 *
 *
 */
    public java.awt.image.BufferedImage bufferedImage;
 }
