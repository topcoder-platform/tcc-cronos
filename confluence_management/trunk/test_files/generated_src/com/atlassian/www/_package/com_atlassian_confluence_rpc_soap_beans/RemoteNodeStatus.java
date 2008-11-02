/**
 * RemoteNodeStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans;

public class RemoteNodeStatus  implements java.io.Serializable {
    private int nodeId;
    private com.atlassian.www._package.java_util.Map jvmStats;
    private com.atlassian.www._package.java_util.Map props;
    private com.atlassian.www._package.java_util.Map buildStats;

    public RemoteNodeStatus() {
    }

    public RemoteNodeStatus(
           int nodeId,
           com.atlassian.www._package.java_util.Map jvmStats,
           com.atlassian.www._package.java_util.Map props,
           com.atlassian.www._package.java_util.Map buildStats) {
           this.nodeId = nodeId;
           this.jvmStats = jvmStats;
           this.props = props;
           this.buildStats = buildStats;
    }


    /**
     * Gets the nodeId value for this RemoteNodeStatus.
     * 
     * @return nodeId
     */
    public int getNodeId() {
        return nodeId;
    }


    /**
     * Sets the nodeId value for this RemoteNodeStatus.
     * 
     * @param nodeId
     */
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }


    /**
     * Gets the jvmStats value for this RemoteNodeStatus.
     * 
     * @return jvmStats
     */
    public com.atlassian.www._package.java_util.Map getJvmStats() {
        return jvmStats;
    }


    /**
     * Sets the jvmStats value for this RemoteNodeStatus.
     * 
     * @param jvmStats
     */
    public void setJvmStats(com.atlassian.www._package.java_util.Map jvmStats) {
        this.jvmStats = jvmStats;
    }


    /**
     * Gets the props value for this RemoteNodeStatus.
     * 
     * @return props
     */
    public com.atlassian.www._package.java_util.Map getProps() {
        return props;
    }


    /**
     * Sets the props value for this RemoteNodeStatus.
     * 
     * @param props
     */
    public void setProps(com.atlassian.www._package.java_util.Map props) {
        this.props = props;
    }


    /**
     * Gets the buildStats value for this RemoteNodeStatus.
     * 
     * @return buildStats
     */
    public com.atlassian.www._package.java_util.Map getBuildStats() {
        return buildStats;
    }


    /**
     * Sets the buildStats value for this RemoteNodeStatus.
     * 
     * @param buildStats
     */
    public void setBuildStats(com.atlassian.www._package.java_util.Map buildStats) {
        this.buildStats = buildStats;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RemoteNodeStatus)) return false;
        RemoteNodeStatus other = (RemoteNodeStatus) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.nodeId == other.getNodeId() &&
            ((this.jvmStats==null && other.getJvmStats()==null) || 
             (this.jvmStats!=null &&
              this.jvmStats.equals(other.getJvmStats()))) &&
            ((this.props==null && other.getProps()==null) || 
             (this.props!=null &&
              this.props.equals(other.getProps()))) &&
            ((this.buildStats==null && other.getBuildStats()==null) || 
             (this.buildStats!=null &&
              this.buildStats.equals(other.getBuildStats())));
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
        _hashCode += getNodeId();
        if (getJvmStats() != null) {
            _hashCode += getJvmStats().hashCode();
        }
        if (getProps() != null) {
            _hashCode += getProps().hashCode();
        }
        if (getBuildStats() != null) {
            _hashCode += getBuildStats().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RemoteNodeStatus.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.atlassian.com/package/com.atlassian.confluence.rpc.soap.beans/", "RemoteNodeStatus"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nodeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nodeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jvmStats");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jvmStats"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.atlassian.com/package/java.util/", "Map"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("props");
        elemField.setXmlName(new javax.xml.namespace.QName("", "props"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.atlassian.com/package/java.util/", "Map"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buildStats");
        elemField.setXmlName(new javax.xml.namespace.QName("", "buildStats"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.atlassian.com/package/java.util/", "Map"));
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
