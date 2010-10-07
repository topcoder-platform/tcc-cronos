/**
 * ContestSaleData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class ContestSaleData  implements java.io.Serializable {
    private java.lang.Long contestId;

    private long contestSaleId;

    private java.util.Calendar createDate;

    private java.lang.String paypalOrderId;

    private java.lang.Double price;

    private java.lang.String saleReferenceId;

    private java.lang.Long saleStatusId;

    private java.lang.Long saleTypeId;

    public ContestSaleData() {
    }

    public ContestSaleData(
           java.lang.Long contestId,
           long contestSaleId,
           java.util.Calendar createDate,
           java.lang.String paypalOrderId,
           java.lang.Double price,
           java.lang.String saleReferenceId,
           java.lang.Long saleStatusId,
           java.lang.Long saleTypeId) {
           this.contestId = contestId;
           this.contestSaleId = contestSaleId;
           this.createDate = createDate;
           this.paypalOrderId = paypalOrderId;
           this.price = price;
           this.saleReferenceId = saleReferenceId;
           this.saleStatusId = saleStatusId;
           this.saleTypeId = saleTypeId;
    }


    /**
     * Gets the contestId value for this ContestSaleData.
     * 
     * @return contestId
     */
    public java.lang.Long getContestId() {
        return contestId;
    }


    /**
     * Sets the contestId value for this ContestSaleData.
     * 
     * @param contestId
     */
    public void setContestId(java.lang.Long contestId) {
        this.contestId = contestId;
    }


    /**
     * Gets the contestSaleId value for this ContestSaleData.
     * 
     * @return contestSaleId
     */
    public long getContestSaleId() {
        return contestSaleId;
    }


    /**
     * Sets the contestSaleId value for this ContestSaleData.
     * 
     * @param contestSaleId
     */
    public void setContestSaleId(long contestSaleId) {
        this.contestSaleId = contestSaleId;
    }


    /**
     * Gets the createDate value for this ContestSaleData.
     * 
     * @return createDate
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this ContestSaleData.
     * 
     * @param createDate
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the paypalOrderId value for this ContestSaleData.
     * 
     * @return paypalOrderId
     */
    public java.lang.String getPaypalOrderId() {
        return paypalOrderId;
    }


    /**
     * Sets the paypalOrderId value for this ContestSaleData.
     * 
     * @param paypalOrderId
     */
    public void setPaypalOrderId(java.lang.String paypalOrderId) {
        this.paypalOrderId = paypalOrderId;
    }


    /**
     * Gets the price value for this ContestSaleData.
     * 
     * @return price
     */
    public java.lang.Double getPrice() {
        return price;
    }


    /**
     * Sets the price value for this ContestSaleData.
     * 
     * @param price
     */
    public void setPrice(java.lang.Double price) {
        this.price = price;
    }


    /**
     * Gets the saleReferenceId value for this ContestSaleData.
     * 
     * @return saleReferenceId
     */
    public java.lang.String getSaleReferenceId() {
        return saleReferenceId;
    }


    /**
     * Sets the saleReferenceId value for this ContestSaleData.
     * 
     * @param saleReferenceId
     */
    public void setSaleReferenceId(java.lang.String saleReferenceId) {
        this.saleReferenceId = saleReferenceId;
    }


    /**
     * Gets the saleStatusId value for this ContestSaleData.
     * 
     * @return saleStatusId
     */
    public java.lang.Long getSaleStatusId() {
        return saleStatusId;
    }


    /**
     * Sets the saleStatusId value for this ContestSaleData.
     * 
     * @param saleStatusId
     */
    public void setSaleStatusId(java.lang.Long saleStatusId) {
        this.saleStatusId = saleStatusId;
    }


    /**
     * Gets the saleTypeId value for this ContestSaleData.
     * 
     * @return saleTypeId
     */
    public java.lang.Long getSaleTypeId() {
        return saleTypeId;
    }


    /**
     * Sets the saleTypeId value for this ContestSaleData.
     * 
     * @param saleTypeId
     */
    public void setSaleTypeId(java.lang.Long saleTypeId) {
        this.saleTypeId = saleTypeId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContestSaleData)) return false;
        ContestSaleData other = (ContestSaleData) obj;
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
            this.contestSaleId == other.getContestSaleId() &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            ((this.paypalOrderId==null && other.getPaypalOrderId()==null) || 
             (this.paypalOrderId!=null &&
              this.paypalOrderId.equals(other.getPaypalOrderId()))) &&
            ((this.price==null && other.getPrice()==null) || 
             (this.price!=null &&
              this.price.equals(other.getPrice()))) &&
            ((this.saleReferenceId==null && other.getSaleReferenceId()==null) || 
             (this.saleReferenceId!=null &&
              this.saleReferenceId.equals(other.getSaleReferenceId()))) &&
            ((this.saleStatusId==null && other.getSaleStatusId()==null) || 
             (this.saleStatusId!=null &&
              this.saleStatusId.equals(other.getSaleStatusId()))) &&
            ((this.saleTypeId==null && other.getSaleTypeId()==null) || 
             (this.saleTypeId!=null &&
              this.saleTypeId.equals(other.getSaleTypeId())));
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
        _hashCode += new Long(getContestSaleId()).hashCode();
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        if (getPaypalOrderId() != null) {
            _hashCode += getPaypalOrderId().hashCode();
        }
        if (getPrice() != null) {
            _hashCode += getPrice().hashCode();
        }
        if (getSaleReferenceId() != null) {
            _hashCode += getSaleReferenceId().hashCode();
        }
        if (getSaleStatusId() != null) {
            _hashCode += getSaleStatusId().hashCode();
        }
        if (getSaleTypeId() != null) {
            _hashCode += getSaleTypeId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContestSaleData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestSaleData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestSaleId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestSaleId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
        elemField.setFieldName("paypalOrderId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paypalOrderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("saleReferenceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saleReferenceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saleStatusId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saleStatusId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saleTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saleTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
