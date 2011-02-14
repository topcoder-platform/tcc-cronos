/**
 * ProjectSpec.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class ProjectSpec  extends com.liquid.portal.service.AuditableObject  implements java.io.Serializable {
    private java.lang.String detailedRequirements;

    private java.lang.String environmentSetupInstructions;

    private java.lang.String finalSubmissionGuidelines;

    private long projectId;

    private long projectSpecId;

    private java.lang.String submissionDeliverables;

    private long version;

    public ProjectSpec() {
    }

    public ProjectSpec(
           java.util.Calendar creationTimestamp,
           java.lang.String creationUser,
           java.util.Calendar modificationTimestamp,
           java.lang.String modificationUser,
           java.lang.String detailedRequirements,
           java.lang.String environmentSetupInstructions,
           java.lang.String finalSubmissionGuidelines,
           long projectId,
           long projectSpecId,
           java.lang.String submissionDeliverables,
           long version) {
        super(
            creationTimestamp,
            creationUser,
            modificationTimestamp,
            modificationUser);
        this.detailedRequirements = detailedRequirements;
        this.environmentSetupInstructions = environmentSetupInstructions;
        this.finalSubmissionGuidelines = finalSubmissionGuidelines;
        this.projectId = projectId;
        this.projectSpecId = projectSpecId;
        this.submissionDeliverables = submissionDeliverables;
        this.version = version;
    }


    /**
     * Gets the detailedRequirements value for this ProjectSpec.
     * 
     * @return detailedRequirements
     */
    public java.lang.String getDetailedRequirements() {
        return detailedRequirements;
    }


    /**
     * Sets the detailedRequirements value for this ProjectSpec.
     * 
     * @param detailedRequirements
     */
    public void setDetailedRequirements(java.lang.String detailedRequirements) {
        this.detailedRequirements = detailedRequirements;
    }


    /**
     * Gets the environmentSetupInstructions value for this ProjectSpec.
     * 
     * @return environmentSetupInstructions
     */
    public java.lang.String getEnvironmentSetupInstructions() {
        return environmentSetupInstructions;
    }


    /**
     * Sets the environmentSetupInstructions value for this ProjectSpec.
     * 
     * @param environmentSetupInstructions
     */
    public void setEnvironmentSetupInstructions(java.lang.String environmentSetupInstructions) {
        this.environmentSetupInstructions = environmentSetupInstructions;
    }


    /**
     * Gets the finalSubmissionGuidelines value for this ProjectSpec.
     * 
     * @return finalSubmissionGuidelines
     */
    public java.lang.String getFinalSubmissionGuidelines() {
        return finalSubmissionGuidelines;
    }


    /**
     * Sets the finalSubmissionGuidelines value for this ProjectSpec.
     * 
     * @param finalSubmissionGuidelines
     */
    public void setFinalSubmissionGuidelines(java.lang.String finalSubmissionGuidelines) {
        this.finalSubmissionGuidelines = finalSubmissionGuidelines;
    }


    /**
     * Gets the projectId value for this ProjectSpec.
     * 
     * @return projectId
     */
    public long getProjectId() {
        return projectId;
    }


    /**
     * Sets the projectId value for this ProjectSpec.
     * 
     * @param projectId
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    /**
     * Gets the projectSpecId value for this ProjectSpec.
     * 
     * @return projectSpecId
     */
    public long getProjectSpecId() {
        return projectSpecId;
    }


    /**
     * Sets the projectSpecId value for this ProjectSpec.
     * 
     * @param projectSpecId
     */
    public void setProjectSpecId(long projectSpecId) {
        this.projectSpecId = projectSpecId;
    }


    /**
     * Gets the submissionDeliverables value for this ProjectSpec.
     * 
     * @return submissionDeliverables
     */
    public java.lang.String getSubmissionDeliverables() {
        return submissionDeliverables;
    }


    /**
     * Sets the submissionDeliverables value for this ProjectSpec.
     * 
     * @param submissionDeliverables
     */
    public void setSubmissionDeliverables(java.lang.String submissionDeliverables) {
        this.submissionDeliverables = submissionDeliverables;
    }


    /**
     * Gets the version value for this ProjectSpec.
     * 
     * @return version
     */
    public long getVersion() {
        return version;
    }


    /**
     * Sets the version value for this ProjectSpec.
     * 
     * @param version
     */
    public void setVersion(long version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProjectSpec)) return false;
        ProjectSpec other = (ProjectSpec) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.detailedRequirements==null && other.getDetailedRequirements()==null) || 
             (this.detailedRequirements!=null &&
              this.detailedRequirements.equals(other.getDetailedRequirements()))) &&
            ((this.environmentSetupInstructions==null && other.getEnvironmentSetupInstructions()==null) || 
             (this.environmentSetupInstructions!=null &&
              this.environmentSetupInstructions.equals(other.getEnvironmentSetupInstructions()))) &&
            ((this.finalSubmissionGuidelines==null && other.getFinalSubmissionGuidelines()==null) || 
             (this.finalSubmissionGuidelines!=null &&
              this.finalSubmissionGuidelines.equals(other.getFinalSubmissionGuidelines()))) &&
            this.projectId == other.getProjectId() &&
            this.projectSpecId == other.getProjectSpecId() &&
            ((this.submissionDeliverables==null && other.getSubmissionDeliverables()==null) || 
             (this.submissionDeliverables!=null &&
              this.submissionDeliverables.equals(other.getSubmissionDeliverables()))) &&
            this.version == other.getVersion();
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
        if (getDetailedRequirements() != null) {
            _hashCode += getDetailedRequirements().hashCode();
        }
        if (getEnvironmentSetupInstructions() != null) {
            _hashCode += getEnvironmentSetupInstructions().hashCode();
        }
        if (getFinalSubmissionGuidelines() != null) {
            _hashCode += getFinalSubmissionGuidelines().hashCode();
        }
        _hashCode += new Long(getProjectId()).hashCode();
        _hashCode += new Long(getProjectSpecId()).hashCode();
        if (getSubmissionDeliverables() != null) {
            _hashCode += getSubmissionDeliverables().hashCode();
        }
        _hashCode += new Long(getVersion()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProjectSpec.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "projectSpec"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailedRequirements");
        elemField.setXmlName(new javax.xml.namespace.QName("", "detailedRequirements"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("environmentSetupInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "environmentSetupInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finalSubmissionGuidelines");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finalSubmissionGuidelines"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectSpecId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectSpecId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submissionDeliverables");
        elemField.setXmlName(new javax.xml.namespace.QName("", "submissionDeliverables"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("", "version"));
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
