/**
 * RemoteClusterInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans;

public class RemoteClusterInformation  implements java.io.Serializable {
    private boolean isRunning;
    private java.lang.String name;
    private java.lang.String multicastPort;
    private java.lang.String description;
    private int memberCount;
    private java.lang.String multicastAddress;

    public RemoteClusterInformation() {
    }

    public RemoteClusterInformation(
           boolean isRunning,
           java.lang.String name,
           java.lang.String multicastPort,
           java.lang.String description,
           int memberCount,
           java.lang.String multicastAddress) {
           this.isRunning = isRunning;
           this.name = name;
           this.multicastPort = multicastPort;
           this.description = description;
           this.memberCount = memberCount;
           this.multicastAddress = multicastAddress;
    }


    /**
     * Gets the isRunning value for this RemoteClusterInformation.
     * 
     * @return isRunning
     */
    public boolean isIsRunning() {
        return isRunning;
    }


    /**
     * Sets the isRunning value for this RemoteClusterInformation.
     * 
     * @param isRunning
     */
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }


    /**
     * Gets the name value for this RemoteClusterInformation.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this RemoteClusterInformation.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the multicastPort value for this RemoteClusterInformation.
     * 
     * @return multicastPort
     */
    public java.lang.String getMulticastPort() {
        return multicastPort;
    }


    /**
     * Sets the multicastPort value for this RemoteClusterInformation.
     * 
     * @param multicastPort
     */
    public void setMulticastPort(java.lang.String multicastPort) {
        this.multicastPort = multicastPort;
    }


    /**
     * Gets the description value for this RemoteClusterInformation.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this RemoteClusterInformation.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the memberCount value for this RemoteClusterInformation.
     * 
     * @return memberCount
     */
    public int getMemberCount() {
        return memberCount;
    }


    /**
     * Sets the memberCount value for this RemoteClusterInformation.
     * 
     * @param memberCount
     */
    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }


    /**
     * Gets the multicastAddress value for this RemoteClusterInformation.
     * 
     * @return multicastAddress
     */
    public java.lang.String getMulticastAddress() {
        return multicastAddress;
    }


    /**
     * Sets the multicastAddress value for this RemoteClusterInformation.
     * 
     * @param multicastAddress
     */
    public void setMulticastAddress(java.lang.String multicastAddress) {
        this.multicastAddress = multicastAddress;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RemoteClusterInformation)) return false;
        RemoteClusterInformation other = (RemoteClusterInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.isRunning == other.isIsRunning() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.multicastPort==null && other.getMulticastPort()==null) || 
             (this.multicastPort!=null &&
              this.multicastPort.equals(other.getMulticastPort()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            this.memberCount == other.getMemberCount() &&
            ((this.multicastAddress==null && other.getMulticastAddress()==null) || 
             (this.multicastAddress!=null &&
              this.multicastAddress.equals(other.getMulticastAddress())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += (isIsRunning() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getMulticastPort() != null) {
            _hashCode += getMulticastPort().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        _hashCode += getMemberCount();
        if (getMulticastAddress() != null) {
            _hashCode += getMulticastAddress().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RemoteClusterInformation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.atlassian.com/package/com.atlassian.confluence.rpc.soap.beans/", "RemoteClusterInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isRunning");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isRunning"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multicastPort");
        elemField.setXmlName(new javax.xml.namespace.QName("", "multicastPort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("memberCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "memberCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multicastAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "multicastAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
