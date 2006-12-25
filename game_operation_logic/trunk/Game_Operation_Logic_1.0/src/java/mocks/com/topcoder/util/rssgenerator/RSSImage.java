
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface extends the RSSObject contract to represent an image. It adds get/set methods for 6 properties of an&nbsp; image - the source where it may be found, the title, the URI to which the image links, the width and height of the image and a description of it. Maps to an RSS 2.0 image element. There is no Atom 1.0 analogue but atom:logo and atom:icon can be mapped.
 * <p>Implementation: Implementations may provide the properties via member variables or by decoration.</p>
 * <p>Thread Safety: Implementations are not required to be thread safe.</p>
 * 
 * @poseidon-object-id [Im36376badm10e13f61c4dmm7e7b]
 */
public interface RSSImage extends com.topcoder.util.rssgenerator.RSSObject {
/**
 * <p>Purpose: This method returns the URI where the image may be found.</p>
 * <p>Args: None.</p>
 * <p>Returns: The URI where the image may be found.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm39bb]
 * @return 
 */
    public java.net.URI getSource();
/**
 * <p>Purpose: This method returns the title of this image.</p>
 * <p>Args: None.</p>
 * <p>Returns: The title of this image.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3996]
 * @return 
 */
    public String getTitle();
/**
 * <p>Purpose: This method returns the link to which this image links.</p>
 * <p>Args: None.</p>
 * <p>Returns: The link to which this image links.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3964]
 * @return 
 */
    public java.net.URI getLink();
/**
 * <p>Purpose: This method returns the width of this image.</p>
 * <p>Args: None.</p>
 * <p>Returns: The width of this image.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm393f]
 * @return 
 */
    public Integer getWidth();
/**
 * <p>Purpose: This method returns the height of this image.</p>
 * <p>Args: None.</p>
 * <p>Returns: The height of this image.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm391a]
 * @return 
 */
    public Integer getHeight();
/**
 * <p>Purpose: This method returns the description of this image.</p>
 * <p>Args: None.</p>
 * <p>Returns: The description of this image.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm38f5]
 * @return 
 */
    public String getDescription();
/**
 * <p>Purpose: This method sets the source of this image.</p>
 * <p>Args: source - The source URI of this image. Possibly null indicating no source.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm38c3]
 * @param source 
 */
    public void setSource(java.net.URI source);
/**
 * <p>Purpose: This method sets the title of this image.</p>
 * <p>Args: title - The title of this image. Possibly null indicating no title or empty.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3883]
 * @param title 
 */
    public void setTitle(String title);
/**
 * <p>Purpose: This method sets the link to which this image links.</p>
 * <p>Args: link - The link to which this image links. Possibly null indicating no such link.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3843]
 * @param link 
 */
    public void setLink(java.net.URI link);
/**
 * <p>Purpose: This method sets the width of this image.</p>
 * <p>Args: width - The width of this image. Possibly null indicating no width.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3803]
 * @param width 
 */
    public void setWidth(Integer width);
/**
 * <p>Purpose: This method sets the height of this image.</p>
 * <p>Args: height - The height of this image. Possibly null indicating no height.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm37c3]
 * @param height 
 */
    public void setHeight(Integer height);
/**
 * <p>Purpose: This method sets the description of this image.</p>
 * <p>Args: description - The description of this image. Possibly null indicating no description or empty.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm351f]
 * @param description 
 */
    public void setDescription(String description);
}


