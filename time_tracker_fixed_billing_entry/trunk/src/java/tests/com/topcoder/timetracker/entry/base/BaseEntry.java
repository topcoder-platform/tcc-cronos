/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.entry.base;
import com.topcoder.timetracker.common.*;
import java.util.*;

/**
 * <strong>Purpose:</strong> <p style='margin-left:.5in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>This is an abstraction of an entry. It is extended from the TimeTrackerBean and thus inherits the following:</p> <blockquote> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> Entry ID &ndash; the unique entry ID number</p> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> Creation Date &ndash; the date the entry was created</p> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> Creation User &ndash; the username that created the entry</p> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> Modification Date &ndash; the date the entry was modified</p> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> Modification User &ndash; the username that modified the entry</p> </blockquote> <p style="margin-top:0in; margin-right:0in; margin-bottom:1.0E-4pt; margin-left:0.5in; font-size:10pt; font-family:Arial; ">What this class adds are the following:</p> <blockquote> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>Company ID &ndash; the company Id associated with the entry</p> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> Description &ndash; a brief description of the entry</p> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> Entry Date &ndash; the date for the entry</p> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> Reject Reasons &ndash; the reasons why the entry was rejected.</p> </blockquote> <p style='margin-left:.5in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>Please note that this class follows the JavaBeans standard as far as the following is concerned:</p> <blockquote> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> The class is serializable (it is assumed that this is already taken care of in the base class &ndash; TimeTrackerBean)</p> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> The class has a no-argument constructor</p> <p style='margin-left:1.0in;text-indent:-.25in;margin:0in; margin-bottom:1.0E-4pt; font-size:12pt; font-family:"Times New Roman"; '>&bull;<span style="font-style: normal; font-weight: normal; font-variant: normal; font-size: 7pt; line-height: normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> The class properties are accessed through get, set, is methods.&nbsp; i.e. All properties will have get&lt;PropertyName&gt;() and set&lt;PropertyName&gt;().&nbsp; Boolean properties will have the additional is&lt;PropertyName&gt;().</p> </blockquote> This class is not thread-safe as it is mutable. <p></p>
 * 
 */
public abstract class BaseEntry extends TimeTrackerBean{

/**
 * This is a date/time representation of this entry (i.e. when it is valid/entered) This is initialized/set/get through the appropriate getter/setter it can not be null once set.
 * 
 */
    private Date date;

/**
 * This is a description of this entry. This is initialized/set/get through the appropriate getter/setter it can not be null once set. Cannot be an empty string.
 * 
 */
    private String description;

/**
 * this is a (possibly empty) map of reject reasons. please note thet neiuther keys not values can be empty. 
 * This is initialized/set/get through the appropriate getter/setter it can not be null once set but can be empty. 
 * <p>The only elements allowed in this map are of the type RejectReason. The setter will enforce this.</p>
 * 
 */
    private Map rejectReasons;

/**
 * This is a unique company id that identifies this entry 
 * (i.e. which company this belongs to) This is initialized/set/get through the appropriate getter/setter. 
 * Generally the user should not change this value once it has been set.
 * 
 */
    private long companyId;

/**
 * Return the company id for this entry. This is important in identifying the cot off time for a particular entry based on the company settings. Simpley return this.companyId
 * 
 * 
 * @return company id
 */
    public long getCompanyId() {        
 return companyId; 
    } 

/**
 * Return the date for this entry. This is used to identiofya the date of this entry and is used when 
 * deciding if cut off time has been reached Simply return this.date
 * 
 * 
 * @return entry date
 */
    public Date getDate() {        
 return date; 
    } 

/**
 * Return the map of reject reasons for this entry. This is used to track teh reasons why the entry has bene rejected. Here we will return a shallow copy of the this.rejectReasons Map.
 * 
 * 
 * @return reject reasons (possibly empty)
 */
    public Map getRejectReasons() {        
 return rejectReasons; 
    } 

/**
 * Return the description of this entry Simpley return this.description.
 * 
 * 
 * @return description
 */
    public String getDescription() {        
 return description; 
    } 

/**
 * Set the company id for this entry. This is important in identifying the cut off 
 * time for a particular entry based on the company settings. Simply assign the parameter to this.companyId
 * 
 * 
 * @param companyId company id
 */
    public void setCompanyId(long companyId) {       
    	setChanged(true);
    	this.companyId = companyId;
    } 

/**
 * Set the date for this entry. This is used to identiofy the date of this entry and is
 *  used when deciding if cut off time has been reached Simply assign the parameter to this.date
 * 
 * 
 * @param date entry date
 * @throws IllegalArgumentException if the parameter is null.
 */
    public void setDate(Date date) {   
    	setChanged(true);
    	this.date = date;
    } 

/**
 * Set the map of reject reasons for this entry. This is used to track teh reasons why the entry has bene rejected. Here we will assign a shallow copy of the input to this.rejectReasons Map. <p>We must check that the type of the elements (i.e. values)&nbsp; in the Map are indeed of the type RejectReason. If not we throw the approriate exception (IllegalArgumentException)</p>
 * 
 * 
 * @param rejectReasons 
 * @throws IllegalArgumentException if the parameter is null or is a map with null entryies (either for key or value) or of the eontry (i.e. the value) is not of RejectReason type
 */
    public void setRejectReasons(Map rejectReasons) {     
    	setChanged(true);
    	this.rejectReasons = rejectReasons;
    } 

/**
 * Set the description of this entry Simply set this.description.
 * 
 * 
 * @param description 
 * @throws IllegalArgumentException if the parameter is null or an empty string
 */
    public void setDescription(String description) {        
    	setChanged(true);
    	this.description = description;
    } 

/**
 * Empty constructor.
 * 
 */
    protected  BaseEntry() {        
        // your code here
    } /** lock-begin */

 }
