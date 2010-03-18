/**
 * Client.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class Client  extends com.liquid.portal.service.AuditableEntity  implements java.io.Serializable {
    private com.liquid.portal.service.Company company;

    private long paymentTermsId;

    private com.liquid.portal.service.ClientStatus clientStatus;

    private double salesTax;

    private java.util.Calendar startDate;

    private java.util.Calendar endDate;

    private java.lang.String codeName;

    public Client() {
    }

    public Client(
           long id,
           java.lang.String createUsername,
           java.util.Calendar createDate,
           java.lang.String modifyUsername,
           java.util.Calendar modifyDate,
           java.lang.String name,
           java.lang.Boolean deleted,
           com.liquid.portal.service.Company company,
           long paymentTermsId,
           com.liquid.portal.service.ClientStatus clientStatus,
           double salesTax,
           java.util.Calendar startDate,
           java.util.Calendar endDate,
           java.lang.String codeName) {
        super(
            id,
            createUsername,
            createDate,
            modifyUsername,
            modifyDate,
            name,
            deleted);
        this.company = company;
        this.paymentTermsId = paymentTermsId;
        this.clientStatus = clientStatus;
        this.salesTax = salesTax;
        this.startDate = startDate;
        this.endDate = endDate;
        this.codeName = codeName;
    }


    /**
     * Gets the company value for this Client.
     * 
     * @return company
     */
    public com.liquid.portal.service.Company getCompany() {
        return company;
    }


    /**
     * Sets the company value for this Client.
     * 
     * @param company
     */
    public void setCompany(com.liquid.portal.service.Company company) {
        this.company = company;
    }


    /**
     * Gets the paymentTermsId value for this Client.
     * 
     * @return paymentTermsId
     */
    public long getPaymentTermsId() {
        return paymentTermsId;
    }


    /**
     * Sets the paymentTermsId value for this Client.
     * 
     * @param paymentTermsId
     */
    public void setPaymentTermsId(long paymentTermsId) {
        this.paymentTermsId = paymentTermsId;
    }


    /**
     * Gets the clientStatus value for this Client.
     * 
     * @return clientStatus
     */
    public com.liquid.portal.service.ClientStatus getClientStatus() {
        return clientStatus;
    }


    /**
     * Sets the clientStatus value for this Client.
     * 
     * @param clientStatus
     */
    public void setClientStatus(com.liquid.portal.service.ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }


    /**
     * Gets the salesTax value for this Client.
     * 
     * @return salesTax
     */
    public double getSalesTax() {
        return salesTax;
    }


    /**
     * Sets the salesTax value for this Client.
     * 
     * @param salesTax
     */
    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }


    /**
     * Gets the startDate value for this Client.
     * 
     * @return startDate
     */
    public java.util.Calendar getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this Client.
     * 
     * @param startDate
     */
    public void setStartDate(java.util.Calendar startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the endDate value for this Client.
     * 
     * @return endDate
     */
    public java.util.Calendar getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this Client.
     * 
     * @param endDate
     */
    public void setEndDate(java.util.Calendar endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the codeName value for this Client.
     * 
     * @return codeName
     */
    public java.lang.String getCodeName() {
        return codeName;
    }


    /**
     * Sets the codeName value for this Client.
     * 
     * @param codeName
     */
    public void setCodeName(java.lang.String codeName) {
        this.codeName = codeName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Client)) return false;
        Client other = (Client) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.company==null && other.getCompany()==null) || 
             (this.company!=null &&
              this.company.equals(other.getCompany()))) &&
            this.paymentTermsId == other.getPaymentTermsId() &&
            ((this.clientStatus==null && other.getClientStatus()==null) || 
             (this.clientStatus!=null &&
              this.clientStatus.equals(other.getClientStatus()))) &&
            this.salesTax == other.getSalesTax() &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.codeName==null && other.getCodeName()==null) || 
             (this.codeName!=null &&
              this.codeName.equals(other.getCodeName())));
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
        if (getCompany() != null) {
            _hashCode += getCompany().hashCode();
        }
        _hashCode += new Long(getPaymentTermsId()).hashCode();
        if (getClientStatus() != null) {
            _hashCode += getClientStatus().hashCode();
        }
        _hashCode += new Double(getSalesTax()).hashCode();
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getCodeName() != null) {
            _hashCode += getCodeName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Client.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "client"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("company");
        elemField.setXmlName(new javax.xml.namespace.QName("", "company"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "company"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentTermsId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentTermsId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "clientStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salesTax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "salesTax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codeName"));
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
