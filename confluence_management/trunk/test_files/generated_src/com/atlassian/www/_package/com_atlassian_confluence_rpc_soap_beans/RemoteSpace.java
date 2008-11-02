/**
 * RemoteSpace.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans;

public class RemoteSpace  extends com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteSpaceSummary  implements java.io.Serializable {
    private java.lang.String description;
    private java.lang.String spaceGroup;
    private long homePage;

    public RemoteSpace() {
    }

    public RemoteSpace(
           java.lang.String description,
           java.lang.String spaceGroup,
           long homePage) {
           this.description = description;
           this.spaceGroup = spaceGroup;
           this.homePage = homePage;
    }


    /**
     * Gets the description value for this RemoteSpace.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this RemoteSpace.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the spaceGroup value for this RemoteSpace.
     * 
     * @return spaceGroup
     */
    public java.lang.String getSpaceGroup() {
        return spaceGroup;
    }


    /**
     * Sets the spaceGroup value for this RemoteSpace.
     * 
     * @param spaceGroup
     */
    public void setSpaceGroup(java.lang.String spaceGroup) {
        this.spaceGroup = spaceGroup;
    }


    /**
     * Gets the homePage value for this RemoteSpace.
     * 
     * @return homePage
     */
    public long getHomePage() {
        return homePage;
    }


    /**
     * Sets the homePage value for this RemoteSpace.
     * 
     * @param homePage
     */
    public void setHomePage(long homePage) {
        this.homePage = homePage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RemoteSpace)) return false;
        RemoteSpace other = (RemoteSpace) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.spaceGroup==null && other.getSpaceGroup()==null) || 
             (this.spaceGroup!=null &&
              this.spaceGroup.equals(other.getSpaceGroup()))) &&
            this.homePage == other.getHomePage();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getSpaceGroup() != null) {
            _hashCode += getSpaceGroup().hashCode();
        }
        _hashCode += new Long(getHomePage()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RemoteSpace.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.atlassian.com/package/com.atlassian.confluence.rpc.soap.beans/", "RemoteSpace"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spaceGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "spaceGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("homePage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "homePage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
