/**
 * Project.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class Project  extends com.liquid.portal.service.AuditableEntity  implements java.io.Serializable {
    private com.liquid.portal.service.Company company;

    private boolean active;

    private double salesTax;

    private java.lang.String pOBoxNumber;

    private long paymentTermsId;

    private java.lang.String description;

    private com.topcoder.clients.clientsmodel.ProjectStatus projectStatus;

    private com.liquid.portal.service.Client client;

    private boolean manualPrizeSetting;

    private com.liquid.portal.service.Project[] childProjects;

    private java.lang.Long parentProjectId;

    public Project() {
    }

    public Project(
           long id,
           java.lang.String createUsername,
           java.util.Calendar createDate,
           java.lang.String modifyUsername,
           java.util.Calendar modifyDate,
           java.lang.String name,
           java.lang.Boolean deleted,
           com.liquid.portal.service.Company company,
           boolean active,
           double salesTax,
           java.lang.String pOBoxNumber,
           long paymentTermsId,
           java.lang.String description,
           com.topcoder.clients.clientsmodel.ProjectStatus projectStatus,
           com.liquid.portal.service.Client client,
           boolean manualPrizeSetting,
           com.liquid.portal.service.Project[] childProjects,
           java.lang.Long parentProjectId) {
        super(
            id,
            createUsername,
            createDate,
            modifyUsername,
            modifyDate,
            name,
            deleted);
        this.company = company;
        this.active = active;
        this.salesTax = salesTax;
        this.pOBoxNumber = pOBoxNumber;
        this.paymentTermsId = paymentTermsId;
        this.description = description;
        this.projectStatus = projectStatus;
        this.client = client;
        this.manualPrizeSetting = manualPrizeSetting;
        this.childProjects = childProjects;
        this.parentProjectId = parentProjectId;
    }


    /**
     * Gets the company value for this Project.
     * 
     * @return company
     */
    public com.liquid.portal.service.Company getCompany() {
        return company;
    }


    /**
     * Sets the company value for this Project.
     * 
     * @param company
     */
    public void setCompany(com.liquid.portal.service.Company company) {
        this.company = company;
    }


    /**
     * Gets the active value for this Project.
     * 
     * @return active
     */
    public boolean isActive() {
        return active;
    }


    /**
     * Sets the active value for this Project.
     * 
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }


    /**
     * Gets the salesTax value for this Project.
     * 
     * @return salesTax
     */
    public double getSalesTax() {
        return salesTax;
    }


    /**
     * Sets the salesTax value for this Project.
     * 
     * @param salesTax
     */
    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }


    /**
     * Gets the pOBoxNumber value for this Project.
     * 
     * @return pOBoxNumber
     */
    public java.lang.String getPOBoxNumber() {
        return pOBoxNumber;
    }


    /**
     * Sets the pOBoxNumber value for this Project.
     * 
     * @param pOBoxNumber
     */
    public void setPOBoxNumber(java.lang.String pOBoxNumber) {
        this.pOBoxNumber = pOBoxNumber;
    }


    /**
     * Gets the paymentTermsId value for this Project.
     * 
     * @return paymentTermsId
     */
    public long getPaymentTermsId() {
        return paymentTermsId;
    }


    /**
     * Sets the paymentTermsId value for this Project.
     * 
     * @param paymentTermsId
     */
    public void setPaymentTermsId(long paymentTermsId) {
        this.paymentTermsId = paymentTermsId;
    }


    /**
     * Gets the description value for this Project.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Project.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the projectStatus value for this Project.
     * 
     * @return projectStatus
     */
    public com.topcoder.clients.clientsmodel.ProjectStatus getProjectStatus() {
        return projectStatus;
    }


    /**
     * Sets the projectStatus value for this Project.
     * 
     * @param projectStatus
     */
    public void setProjectStatus(com.topcoder.clients.clientsmodel.ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }


    /**
     * Gets the client value for this Project.
     * 
     * @return client
     */
    public com.liquid.portal.service.Client getClient() {
        return client;
    }


    /**
     * Sets the client value for this Project.
     * 
     * @param client
     */
    public void setClient(com.liquid.portal.service.Client client) {
        this.client = client;
    }


    /**
     * Gets the manualPrizeSetting value for this Project.
     * 
     * @return manualPrizeSetting
     */
    public boolean isManualPrizeSetting() {
        return manualPrizeSetting;
    }


    /**
     * Sets the manualPrizeSetting value for this Project.
     * 
     * @param manualPrizeSetting
     */
    public void setManualPrizeSetting(boolean manualPrizeSetting) {
        this.manualPrizeSetting = manualPrizeSetting;
    }


    /**
     * Gets the childProjects value for this Project.
     * 
     * @return childProjects
     */
    public com.liquid.portal.service.Project[] getChildProjects() {
        return childProjects;
    }


    /**
     * Sets the childProjects value for this Project.
     * 
     * @param childProjects
     */
    public void setChildProjects(com.liquid.portal.service.Project[] childProjects) {
        this.childProjects = childProjects;
    }

    public com.liquid.portal.service.Project getChildProjects(int i) {
        return this.childProjects[i];
    }

    public void setChildProjects(int i, com.liquid.portal.service.Project _value) {
        this.childProjects[i] = _value;
    }


    /**
     * Gets the parentProjectId value for this Project.
     * 
     * @return parentProjectId
     */
    public java.lang.Long getParentProjectId() {
        return parentProjectId;
    }


    /**
     * Sets the parentProjectId value for this Project.
     * 
     * @param parentProjectId
     */
    public void setParentProjectId(java.lang.Long parentProjectId) {
        this.parentProjectId = parentProjectId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Project)) return false;
        Project other = (Project) obj;
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
            this.active == other.isActive() &&
            this.salesTax == other.getSalesTax() &&
            ((this.pOBoxNumber==null && other.getPOBoxNumber()==null) || 
             (this.pOBoxNumber!=null &&
              this.pOBoxNumber.equals(other.getPOBoxNumber()))) &&
            this.paymentTermsId == other.getPaymentTermsId() &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.projectStatus==null && other.getProjectStatus()==null) || 
             (this.projectStatus!=null &&
              this.projectStatus.equals(other.getProjectStatus()))) &&
            ((this.client==null && other.getClient()==null) || 
             (this.client!=null &&
              this.client.equals(other.getClient()))) &&
            this.manualPrizeSetting == other.isManualPrizeSetting() &&
            ((this.childProjects==null && other.getChildProjects()==null) || 
             (this.childProjects!=null &&
              java.util.Arrays.equals(this.childProjects, other.getChildProjects()))) &&
            ((this.parentProjectId==null && other.getParentProjectId()==null) || 
             (this.parentProjectId!=null &&
              this.parentProjectId.equals(other.getParentProjectId())));
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
        _hashCode += (isActive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += new Double(getSalesTax()).hashCode();
        if (getPOBoxNumber() != null) {
            _hashCode += getPOBoxNumber().hashCode();
        }
        _hashCode += new Long(getPaymentTermsId()).hashCode();
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getProjectStatus() != null) {
            _hashCode += getProjectStatus().hashCode();
        }
        if (getClient() != null) {
            _hashCode += getClient().hashCode();
        }
        _hashCode += (isManualPrizeSetting() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getChildProjects() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChildProjects());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChildProjects(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getParentProjectId() != null) {
            _hashCode += getParentProjectId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Project.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "project"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("company");
        elemField.setXmlName(new javax.xml.namespace.QName("", "company"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "company"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("active");
        elemField.setXmlName(new javax.xml.namespace.QName("", "active"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salesTax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "salesTax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("POBoxNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pOBoxNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("clientsmodel.clients.topcoder.com", "projectStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("client");
        elemField.setXmlName(new javax.xml.namespace.QName("", "client"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "client"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manualPrizeSetting");
        elemField.setXmlName(new javax.xml.namespace.QName("", "manualPrizeSetting"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("childProjects");
        elemField.setXmlName(new javax.xml.namespace.QName("", "childProjects"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "project"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentProjectId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parentProjectId"));
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
