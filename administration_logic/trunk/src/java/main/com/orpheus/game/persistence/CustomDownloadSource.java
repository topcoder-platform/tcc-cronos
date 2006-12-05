
package com.orpheus.game.persistence;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.results.DownloadData;
import com.topcoder.web.frontcontroller.results.DownloadSource;

/**
 * This is the download source client to the EJB layer. It implements the DonwnloadSource. It is built to work with EJBs, and this class leaves it to implementations to specify the EJBs. This is purpose of the abstract ejbXXX methods. The public methods defer to these for actual persistence calls.
 * <p><strong>Thread Safety</strong></p>
 * <p>This class is immutable and thread-safe.</p>
 * 
 */
public abstract class CustomDownloadSource implements DownloadSource {

/**
 * Empty constructor.
 * 
 */
    protected  CustomDownloadSource() {        
        // your code here
    } 

/**
 * Determines and returns the download data and metadata associated with the specified ID string, or returns null if it cannot associate a download with the specified ID.
 * <p><strong>Implementation Notes</strong></p>
 * <p>Simply calls and returns getDownloadData(id)</p>
 * 
 * 
 * @return DownloadData The download data for the id
 * @param id The id
 * @throws HandlerExecutionException if this method fails because
 * of an internal checked exception
 */
    public DownloadData getDownloadData(String id) {        
 return null; 
    } 

/**
 * Determines and returns the download data and metadata
 * associated with the specified ID string from persistence using the applicable EJB, or returns null if it
 * cannot associate a download with the specified ID.
 * 
 * 
 * @return DownloadData The download data for the id
 * @param id The id
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    protected abstract DownloadData ejbGetDownloadData(String id);
 }
