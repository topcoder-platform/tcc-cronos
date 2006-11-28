
package com.topcoder.util.rssgenerator.impl.rss20;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: Extends the BaseRSSObjectDecorator class to provide an RSS 2.0 cloud <p>Implementation: We decorate over some more properties to provide a cloud object.</p> <p>Thread Safety: This class is not thread safe as its base class is not thread safe either.</p>
 * 
 */
public class RSS20Cloud extends com.topcoder.util.rssgenerator.impl.BaseRSSObjectDecorator {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of this class will result in an IllegalStateException being thrown.</p> <p>Args: None.</p> <p>Implementation: Simply call super().</p> <p>Exceptions: None.</p>
 * 
 */
    public  RSS20Cloud() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p> <p>Args: object - The RSSObject to be decorated over. Must not be null.</p> <p>Implementation: Simply call super(object).</p> <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * 
 * @param object 
 */
    public  RSS20Cloud(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: Gets the domain of the cloud.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:domain&quot;</p> <p>Returns: The domain of this cloud.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @return 
 */
    public java.net.URI getDomain() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Gets the path of the cloud.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:path&quot;</p> <p>Returns: The path of this cloud.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @return 
 */
    public String getPath() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Gets the port of the cloud.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:port&quot;</p> <p>Returns: The port of this cloud.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @return 
 */
    public Integer getPort() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Gets the register procedure of the cloud.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:registerProcedure&quot;</p> <p>Returns: The register procedure of this cloud.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p></p>
 * 
 * 
 * @return 
 */
    public String getRegisterProcedure() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Gets the protocol of the cloud.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:protocol&quot;</p> <p>Returns: The protocol of this cloud.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @return 
 */
    public String getProtocol() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Sets the domain of the cloud.</p> <p>Args: domain - the domain to be set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:domain&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @param domain 
 */
    public void setDomain(java.net.URI domain) {        
        // your code here
    } 

/**
 * <p>Purpose: Sets the path of the cloud.</p> <p>Args: path - the path to be set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:path&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p></p>
 * 
 * 
 * @param path 
 */
    public void setPath(String path) {        
        // your code here
    } 

/**
 * <p>Purpose: Sets the port of the cloud.</p> <p>Args: port - the port to be set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:port&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @param port 
 */
    public void setPort(Integer port) {        
        // your code here
    } 

/**
 * <p>Purpose: Sets the register procedure of the cloud.</p> <p>Args: registerProcedure - the register procedure to be set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:registerProcedure&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @param procedure 
 */
    public void setRegisterProcedure(String procedure) {        
        // your code here
    } 

/**
 * <p>Purpose: Sets the protocol of the cloud.</p> <p>Args: protocol - the protocol to be set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:protocol&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @param protocol 
 */
    public void setProtocol(String protocol) {        
        // your code here
    } 
 }
