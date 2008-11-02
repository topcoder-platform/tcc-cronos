/**
 * RemoteContentPermissionSet.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans;

public class RemoteContentPermissionSet  implements java.io.Serializable {
    private java.lang.String type;
    private com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteContentPermission[] contentPermissions;

    public RemoteContentPermissionSet() {
    }

    public RemoteContentPermissionSet(
           java.lang.String type,
           com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteContentPermission[] contentPermissions) {
           this.type = type;
           this.contentPermissions = contentPermissions;
    }


    /**
     * Gets the type value for this RemoteContentPermissionSet.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this RemoteContentPermissionSet.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the contentPermissions value for this RemoteContentPermissionSet.
     * 
     * @return contentPermissions
     */
    public com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteContentPermission[] getContentPermissions() {
        return contentPermissions;
    }


    /**
     * Sets the contentPermissions value for this RemoteContentPermissionSet.
     * 
     * @param contentPermissions
     */
    public void setContentPermissions(com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemoteContentPermission[] contentPermissions) {
        this.contentPermissions = contentPermissions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RemoteContentPermissionSet)) return false;
        RemoteContentPermissionSet other = (RemoteContentPermissionSet) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.contentPermissions==null && other.getContentPermissions()==null) || 
             (this.contentPermissions!=null &&
              java.util.Arrays.equals(this.contentPermissions, other.getContentPermissions())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getContentPermissions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContentPermissions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContentPermissions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RemoteContentPermissionSet.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.atlassian.com/package/com.atlassian.confluence.rpc.soap.beans/", "RemoteContentPermissionSet"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentPermissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contentPermissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.atlassian.com/package/com.atlassian.confluence.rpc.soap.beans/", "RemoteContentPermission"));
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
