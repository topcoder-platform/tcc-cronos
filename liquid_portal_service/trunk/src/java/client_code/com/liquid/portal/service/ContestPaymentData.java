/**
 * ContestPaymentData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class ContestPaymentData  implements java.io.Serializable {
    private java.lang.Long contestId;

    private java.lang.Long paymentStatusId;

    private java.lang.Double price;

    private java.lang.String paypalOrderId;

    private java.util.Calendar createDate;

    private java.lang.Long paymentTypeId;

    private java.lang.String paymentReferenceId;

    public ContestPaymentData() {
    }

    public ContestPaymentData(
           java.lang.Long contestId,
           java.lang.Long paymentStatusId,
           java.lang.Double price,
           java.lang.String paypalOrderId,
           java.util.Calendar createDate,
           java.lang.Long paymentTypeId,
           java.lang.String paymentReferenceId) {
           this.contestId = contestId;
           this.paymentStatusId = paymentStatusId;
           this.price = price;
           this.paypalOrderId = paypalOrderId;
           this.createDate = createDate;
           this.paymentTypeId = paymentTypeId;
           this.paymentReferenceId = paymentReferenceId;
    }


    /**
     * Gets the contestId value for this ContestPaymentData.
     * 
     * @return contestId
     */
    public java.lang.Long getContestId() {
        return contestId;
    }


    /**
     * Sets the contestId value for this ContestPaymentData.
     * 
     * @param contestId
     */
    public void setContestId(java.lang.Long contestId) {
        this.contestId = contestId;
    }


    /**
     * Gets the paymentStatusId value for this ContestPaymentData.
     * 
     * @return paymentStatusId
     */
    public java.lang.Long getPaymentStatusId() {
        return paymentStatusId;
    }


    /**
     * Sets the paymentStatusId value for this ContestPaymentData.
     * 
     * @param paymentStatusId
     */
    public void setPaymentStatusId(java.lang.Long paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }


    /**
     * Gets the price value for this ContestPaymentData.
     * 
     * @return price
     */
    public java.lang.Double getPrice() {
        return price;
    }


    /**
     * Sets the price value for this ContestPaymentData.
     * 
     * @param price
     */
    public void setPrice(java.lang.Double price) {
        this.price = price;
    }


    /**
     * Gets the paypalOrderId value for this ContestPaymentData.
     * 
     * @return paypalOrderId
     */
    public java.lang.String getPaypalOrderId() {
        return paypalOrderId;
    }


    /**
     * Sets the paypalOrderId value for this ContestPaymentData.
     * 
     * @param paypalOrderId
     */
    public void setPaypalOrderId(java.lang.String paypalOrderId) {
        this.paypalOrderId = paypalOrderId;
    }


    /**
     * Gets the createDate value for this ContestPaymentData.
     * 
     * @return createDate
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this ContestPaymentData.
     * 
     * @param createDate
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the paymentTypeId value for this ContestPaymentData.
     * 
     * @return paymentTypeId
     */
    public java.lang.Long getPaymentTypeId() {
        return paymentTypeId;
    }


    /**
     * Sets the paymentTypeId value for this ContestPaymentData.
     * 
     * @param paymentTypeId
     */
    public void setPaymentTypeId(java.lang.Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }


    /**
     * Gets the paymentReferenceId value for this ContestPaymentData.
     * 
     * @return paymentReferenceId
     */
    public java.lang.String getPaymentReferenceId() {
        return paymentReferenceId;
    }


    /**
     * Sets the paymentReferenceId value for this ContestPaymentData.
     * 
     * @param paymentReferenceId
     */
    public void setPaymentReferenceId(java.lang.String paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContestPaymentData)) return false;
        ContestPaymentData other = (ContestPaymentData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.contestId==null && other.getContestId()==null) || 
             (this.contestId!=null &&
              this.contestId.equals(other.getContestId()))) &&
            ((this.paymentStatusId==null && other.getPaymentStatusId()==null) || 
             (this.paymentStatusId!=null &&
              this.paymentStatusId.equals(other.getPaymentStatusId()))) &&
            ((this.price==null && other.getPrice()==null) || 
             (this.price!=null &&
              this.price.equals(other.getPrice()))) &&
            ((this.paypalOrderId==null && other.getPaypalOrderId()==null) || 
             (this.paypalOrderId!=null &&
              this.paypalOrderId.equals(other.getPaypalOrderId()))) &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            ((this.paymentTypeId==null && other.getPaymentTypeId()==null) || 
             (this.paymentTypeId!=null &&
              this.paymentTypeId.equals(other.getPaymentTypeId()))) &&
            ((this.paymentReferenceId==null && other.getPaymentReferenceId()==null) || 
             (this.paymentReferenceId!=null &&
              this.paymentReferenceId.equals(other.getPaymentReferenceId())));
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
        if (getContestId() != null) {
            _hashCode += getContestId().hashCode();
        }
        if (getPaymentStatusId() != null) {
            _hashCode += getPaymentStatusId().hashCode();
        }
        if (getPrice() != null) {
            _hashCode += getPrice().hashCode();
        }
        if (getPaypalOrderId() != null) {
            _hashCode += getPaypalOrderId().hashCode();
        }
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        if (getPaymentTypeId() != null) {
            _hashCode += getPaymentTypeId().hashCode();
        }
        if (getPaymentReferenceId() != null) {
            _hashCode += getPaymentReferenceId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContestPaymentData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestPaymentData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentStatusId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentStatusId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("", "price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paypalOrderId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paypalOrderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentReferenceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentReferenceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
