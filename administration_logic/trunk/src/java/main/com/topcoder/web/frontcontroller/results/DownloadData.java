
package com.topcoder.web.frontcontroller.results;
import com.topcoder.util.log.*;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import java.io.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.w3c.dom.*;
/**
 * <p>
 * This interface abstracts the behavior of a Downloadable data. This represents a complete, downloadable entity with associated media
 * type and (possibly) suggested file name. DownloadData encapsulates both the metadata and the actual contents of the data can be downloaded.
 * It defines methods to retrieve the metadata like the media-type and suggested name for the download data and also methods to retrieve the 
 * actual contents of the DownloadData.
 * The implementations of this interface are required to be thread-safe.
 * </p>
 * 
 */
public interface DownloadData {
/**
 * <p>
 * Returns the contents of the DownloadData as an InputStream.
 * The implementation of DownloadDats is free to decide upon the mechanism for Content Retrieval.
 * </p>
 * 
 * 
 * @return the contents of DownloadData as an InputStream
 * @throws ContentRetrievalException: if there are problems in the retrieval of contents
 */
    public java.io.InputStream getContent() throws ContentRetrievalException;
/**
 * <p>
 * Returns the Media type/MIME type for the DownloadData.
 * </p>
 * 
 * 
 * @return the Media type/MIME type for the DownloadData.
 */
    public String getMediaType();
/**
 * <p>
 * Returns the suggested name for the DownloadData. The suggested name can be null indicating that the user agent
 * should not prompt for saving the contents but instead display the contents on the screen.
 * </p>
 * 
 * 
 * @return the suggested name for the DownloadData. 
 */
    public String getSuggestedName();
}


