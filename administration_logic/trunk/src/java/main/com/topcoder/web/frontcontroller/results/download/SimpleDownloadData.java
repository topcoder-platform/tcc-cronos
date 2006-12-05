
package com.topcoder.web.frontcontroller.results.download;
import com.topcoder.web.frontcontroller.results.ContentRetrievalException;
import java.io.*;

/**
 * <p>
 * This class is an implementation of the DownloadData interface and provides a simple implementation of this
 * interface and defines method that allows to get the content of a downloadable data and the metadata for the
 * downloadable data. This implementation caches the metadata part of the downloadable data, but the content
 * will be fetched on demand and returned. As a result the method for fetching the content has been synchronized.
 * The instance members of this class are initialized in the constructor and are immutable later on. Also the 
 * method for fetching the content has been synchronized and hence the class as such is thread-safe.
 * </p>
 * 
 */
public class SimpleDownloadData implements com.topcoder.web.frontcontroller.results.DownloadData {

/**
 * <p>Represents the URL of the resource from which the content data needs to be fetched for this DownloadData.
 * This instance member is initialized in the constructor and will contain a non-null value and is immutable 
 * later on.
 * </p>
 * 
 */
    private final java.net.URL resourceURL;

/**
 * <p>Represents the media type of the download data, in MIME format. An example of value for this member, it will
 * be something like: application/jpg, video/mpg etc. This instance member will be initialized with a non-null
 * non-empty String value in the constructor and is immutable later on.
 * </p>
 * 
 */
    private final String mediaType;

/**
 * <p>Represents the suggested name to be used for the DownloadData. This name will be the name that will
 * be prompted on the User Agent asking the user to store the downloaded data in the specific file.
 * This instance member can have a null value or an empty String value indicating that the downloadData needs to be directly displayed
 * on the user agent without prompting the user for saving it.
 * This instance member is set in the constructor and not modified later on.
 * </p>
 * 
 */
    private final String suggestedName;

/**
 * <p>
 * Constructor. Simply assigns the value of the passed parameters to the corresponding instance members.
 * For the instance member "resourceURL", a URL object from the passed String "resourceURL" is created.
 * </p>
 * 
 * 
 * @param resourceURL the URL for the resource corresponding to the Download Data
 * @param mediaType the MIME type for the Download Data
 * @param suggestedName the suggested name to be prompted when saving the file to the user agent
 * @throws IllegalArgumentException: if the parameter "resourceURL" or "mediaType" is null or "mediaType" is an empty String on trimming.
 */
    public  SimpleDownloadData(String resourceURL, String mediaType, String suggestedName) {        
       this.resourceURL=null;
       this.mediaType=null
       ;
       this.suggestedName =null;
    } 

/**
 * <p>
 * Returns the contents of the DownloadData as an InputStream by fetching the contents from the "resourceURL" instance member.
 * This method is synchronized so that the possibility if IOException is reduced.
 * 
 * Implementation Hint:
 * --------------------------------
 * a. InputStream stream = resourceURL.getConnection().getInputStream();
 * b. Read the content stream and store them in a ByteArrayOutputStream created locally.
 * c. Close the stream.
 * d. Create a ByteArrayInputStream from the byte contents in the ByteArrayOutputStream and return this
 * ByteArrayInputStream.
 * </p>
 * 
 * 
 * @return the contents of the DownloadData
 * @throws ContentRetrievalException: if there IOException during the retrieval of contents or any other problems during the retrieval of contents.
 */
    public synchronized java.io.InputStream getContent() throws com.topcoder.web.frontcontroller.results.ContentRetrievalException {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Returns the Media type/MIME type for the DownloadData.
 * Simply returns the value of the instance member "mediaType".
 * </p>
 * 
 * 
 * @return the Media type/MIME type for the DownloadData.
 */
    public String getMediaType() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Returns the suggested name for the DownloadData. Simply returns the value of the instance member "suggestedName".
 * </p>
 * 
 * 
 * @return the suggested name for the DownloadData.
 */
    public String getSuggestedName() {        
        // your code here
        return null;
    } 

 }
