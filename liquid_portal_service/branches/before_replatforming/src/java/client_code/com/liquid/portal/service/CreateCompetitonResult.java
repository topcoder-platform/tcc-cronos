/**
 * CreateCompetitonResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class CreateCompetitonResult  implements java.io.Serializable {
    private com.liquid.portal.service.Competition competition;

    private com.liquid.portal.service.Warning[] result;  // attribute

    public CreateCompetitonResult() {
    }

    public CreateCompetitonResult(
           com.liquid.portal.service.Warning[] param1,
           com.liquid.portal.service.Competition competition) {
        this.param1 = param1;
        this.competition = competition;
    }


    /**
     * Gets the competition value for this CreateCompetitonResult.
     * 
     * @return competition
     */
    public com.liquid.portal.service.Competition getCompetition() {
        return competition;
    }


    /**
     * Sets the competition value for this CreateCompetitonResult.
     * 
     * @param competition
     */
    public void setCompetition(com.liquid.portal.service.Competition competition) {
        this.competition = competition;
    }


    /**
     * Gets the result value for this CreateCompetitonResult.
     * 
     * @return result
     */
    public com.liquid.portal.service.Warning[] getResult() {
        return result;
    }


    /**
     * Sets the result value for this CreateCompetitonResult.
     * 
     * @param result
     */
    public void setResult(com.liquid.portal.service.Warning[] result) {
        this.result = result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateCompetitonResult)) return false;
        CreateCompetitonResult other = (CreateCompetitonResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.competition==null && other.getCompetition()==null) || 
             (this.competition!=null &&
              this.competition.equals(other.getCompetition()))) &&
            ((this.result==null && other.getResult()==null) || 
             (this.result!=null &&
              java.util.Arrays.equals(this.result, other.getResult())));
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
        if (getCompetition() != null) {
            _hashCode += getCompetition().hashCode();
        }
        if (getResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResult(), i);
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
        new org.apache.axis.description.TypeDesc(CreateCompetitonResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "createCompetitonResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("competition");
        elemField.setXmlName(new javax.xml.namespace.QName("", "competition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competition"));
        elemField.setMinOccurs(0);
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
