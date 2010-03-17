/**
 * StudioCompetition.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class StudioCompetition  extends com.liquid.portal.service.Competition  implements java.io.Serializable {
    private long id;

    private com.liquid.portal.service.ContestData contestData;

    private com.liquid.portal.service.CompetionType type;

    private com.liquid.portal.service.Resource[] resources;

    private boolean clientApproval;

    private java.lang.String clientName;

    private double reviewPayment;

    private double specificationReviewPayment;

    private double contestFee;

    private com.liquid.portal.service.ContestStatusData status;

    private java.lang.String category;

    private int confidence;

    private boolean pricingApproval;

    private boolean hasWikiSpecification;

    private boolean passedSpecReview;

    private boolean hasDependentCompetitions;

    private boolean wasReposted;

    private java.lang.String notes;

    public StudioCompetition() {
    }

    public StudioCompetition(
           java.lang.Double adminFee,
           boolean _clientApproval,
           java.lang.String _clientName,
           java.lang.Long competitionId,
           int _confidence,
           double _contestFee,
           long creatorUserId,
           double drPoints,
           java.lang.String eligibility,
           java.lang.String endTime,
           boolean _hasDependentCompetitions,
           boolean _hasWikiSpecification,
           long _id,
           java.lang.String _notes,
           boolean _passedSpecReview,
           boolean _pricingApproval,
           com.liquid.portal.service.CompetitionPrize[] prizes,
           project.service.topcoder.com.Project project,
           com.liquid.portal.service.Resource[] _resources,
           double _reviewPayment,
           java.lang.String shortSummary,
           double _specificationReviewPayment,
           java.lang.String startTime,
           com.liquid.portal.service.CompetionType _type,
           boolean _wasReposted,
           long id,
           com.liquid.portal.service.ContestData contestData,
           com.liquid.portal.service.CompetionType type,
           com.liquid.portal.service.Resource[] resources,
           boolean clientApproval,
           java.lang.String clientName,
           double reviewPayment,
           double specificationReviewPayment,
           double contestFee,
           com.liquid.portal.service.ContestStatusData status,
           java.lang.String category,
           int confidence,
           boolean pricingApproval,
           boolean hasWikiSpecification,
           boolean passedSpecReview,
           boolean hasDependentCompetitions,
           boolean wasReposted,
           java.lang.String notes) {
        super(
            adminFee,
            _clientApproval,
            _clientName,
            competitionId,
            _confidence,
            _contestFee,
            creatorUserId,
            drPoints,
            eligibility,
            endTime,
            _hasDependentCompetitions,
            _hasWikiSpecification,
            _id,
            _notes,
            _passedSpecReview,
            _pricingApproval,
            prizes,
            project,
            _resources,
            _reviewPayment,
            shortSummary,
            _specificationReviewPayment,
            startTime,
            _type,
            _wasReposted);
        this.id = id;
        this.contestData = contestData;
        this.type = type;
        this.resources = resources;
        this.clientApproval = clientApproval;
        this.clientName = clientName;
        this.reviewPayment = reviewPayment;
        this.specificationReviewPayment = specificationReviewPayment;
        this.contestFee = contestFee;
        this.status = status;
        this.category = category;
        this.confidence = confidence;
        this.pricingApproval = pricingApproval;
        this.hasWikiSpecification = hasWikiSpecification;
        this.passedSpecReview = passedSpecReview;
        this.hasDependentCompetitions = hasDependentCompetitions;
        this.wasReposted = wasReposted;
        this.notes = notes;
    }


    /**
     * Gets the id value for this StudioCompetition.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this StudioCompetition.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the contestData value for this StudioCompetition.
     * 
     * @return contestData
     */
    public com.liquid.portal.service.ContestData getContestData() {
        return contestData;
    }


    /**
     * Sets the contestData value for this StudioCompetition.
     * 
     * @param contestData
     */
    public void setContestData(com.liquid.portal.service.ContestData contestData) {
        this.contestData = contestData;
    }


    /**
     * Gets the type value for this StudioCompetition.
     * 
     * @return type
     */
    public com.liquid.portal.service.CompetionType getType() {
        return type;
    }


    /**
     * Sets the type value for this StudioCompetition.
     * 
     * @param type
     */
    public void setType(com.liquid.portal.service.CompetionType type) {
        this.type = type;
    }


    /**
     * Gets the resources value for this StudioCompetition.
     * 
     * @return resources
     */
    public com.liquid.portal.service.Resource[] getResources() {
        return resources;
    }


    /**
     * Sets the resources value for this StudioCompetition.
     * 
     * @param resources
     */
    public void setResources(com.liquid.portal.service.Resource[] resources) {
        this.resources = resources;
    }

    public com.liquid.portal.service.Resource getResources(int i) {
        return this.resources[i];
    }

    public void setResources(int i, com.liquid.portal.service.Resource _value) {
        this.resources[i] = _value;
    }


    /**
     * Gets the clientApproval value for this StudioCompetition.
     * 
     * @return clientApproval
     */
    public boolean isClientApproval() {
        return clientApproval;
    }


    /**
     * Sets the clientApproval value for this StudioCompetition.
     * 
     * @param clientApproval
     */
    public void setClientApproval(boolean clientApproval) {
        this.clientApproval = clientApproval;
    }


    /**
     * Gets the clientName value for this StudioCompetition.
     * 
     * @return clientName
     */
    public java.lang.String getClientName() {
        return clientName;
    }


    /**
     * Sets the clientName value for this StudioCompetition.
     * 
     * @param clientName
     */
    public void setClientName(java.lang.String clientName) {
        this.clientName = clientName;
    }


    /**
     * Gets the reviewPayment value for this StudioCompetition.
     * 
     * @return reviewPayment
     */
    public double getReviewPayment() {
        return reviewPayment;
    }


    /**
     * Sets the reviewPayment value for this StudioCompetition.
     * 
     * @param reviewPayment
     */
    public void setReviewPayment(double reviewPayment) {
        this.reviewPayment = reviewPayment;
    }


    /**
     * Gets the specificationReviewPayment value for this StudioCompetition.
     * 
     * @return specificationReviewPayment
     */
    public double getSpecificationReviewPayment() {
        return specificationReviewPayment;
    }


    /**
     * Sets the specificationReviewPayment value for this StudioCompetition.
     * 
     * @param specificationReviewPayment
     */
    public void setSpecificationReviewPayment(double specificationReviewPayment) {
        this.specificationReviewPayment = specificationReviewPayment;
    }


    /**
     * Gets the contestFee value for this StudioCompetition.
     * 
     * @return contestFee
     */
    public double getContestFee() {
        return contestFee;
    }


    /**
     * Sets the contestFee value for this StudioCompetition.
     * 
     * @param contestFee
     */
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }


    /**
     * Gets the status value for this StudioCompetition.
     * 
     * @return status
     */
    public com.liquid.portal.service.ContestStatusData getStatus() {
        return status;
    }


    /**
     * Sets the status value for this StudioCompetition.
     * 
     * @param status
     */
    public void setStatus(com.liquid.portal.service.ContestStatusData status) {
        this.status = status;
    }


    /**
     * Gets the category value for this StudioCompetition.
     * 
     * @return category
     */
    public java.lang.String getCategory() {
        return category;
    }


    /**
     * Sets the category value for this StudioCompetition.
     * 
     * @param category
     */
    public void setCategory(java.lang.String category) {
        this.category = category;
    }


    /**
     * Gets the confidence value for this StudioCompetition.
     * 
     * @return confidence
     */
    public int getConfidence() {
        return confidence;
    }


    /**
     * Sets the confidence value for this StudioCompetition.
     * 
     * @param confidence
     */
    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }


    /**
     * Gets the pricingApproval value for this StudioCompetition.
     * 
     * @return pricingApproval
     */
    public boolean isPricingApproval() {
        return pricingApproval;
    }


    /**
     * Sets the pricingApproval value for this StudioCompetition.
     * 
     * @param pricingApproval
     */
    public void setPricingApproval(boolean pricingApproval) {
        this.pricingApproval = pricingApproval;
    }


    /**
     * Gets the hasWikiSpecification value for this StudioCompetition.
     * 
     * @return hasWikiSpecification
     */
    public boolean isHasWikiSpecification() {
        return hasWikiSpecification;
    }


    /**
     * Sets the hasWikiSpecification value for this StudioCompetition.
     * 
     * @param hasWikiSpecification
     */
    public void setHasWikiSpecification(boolean hasWikiSpecification) {
        this.hasWikiSpecification = hasWikiSpecification;
    }


    /**
     * Gets the passedSpecReview value for this StudioCompetition.
     * 
     * @return passedSpecReview
     */
    public boolean isPassedSpecReview() {
        return passedSpecReview;
    }


    /**
     * Sets the passedSpecReview value for this StudioCompetition.
     * 
     * @param passedSpecReview
     */
    public void setPassedSpecReview(boolean passedSpecReview) {
        this.passedSpecReview = passedSpecReview;
    }


    /**
     * Gets the hasDependentCompetitions value for this StudioCompetition.
     * 
     * @return hasDependentCompetitions
     */
    public boolean isHasDependentCompetitions() {
        return hasDependentCompetitions;
    }


    /**
     * Sets the hasDependentCompetitions value for this StudioCompetition.
     * 
     * @param hasDependentCompetitions
     */
    public void setHasDependentCompetitions(boolean hasDependentCompetitions) {
        this.hasDependentCompetitions = hasDependentCompetitions;
    }


    /**
     * Gets the wasReposted value for this StudioCompetition.
     * 
     * @return wasReposted
     */
    public boolean isWasReposted() {
        return wasReposted;
    }


    /**
     * Sets the wasReposted value for this StudioCompetition.
     * 
     * @param wasReposted
     */
    public void setWasReposted(boolean wasReposted) {
        this.wasReposted = wasReposted;
    }


    /**
     * Gets the notes value for this StudioCompetition.
     * 
     * @return notes
     */
    public java.lang.String getNotes() {
        return notes;
    }


    /**
     * Sets the notes value for this StudioCompetition.
     * 
     * @param notes
     */
    public void setNotes(java.lang.String notes) {
        this.notes = notes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StudioCompetition)) return false;
        StudioCompetition other = (StudioCompetition) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.id == other.getId() &&
            ((this.contestData==null && other.getContestData()==null) || 
             (this.contestData!=null &&
              this.contestData.equals(other.getContestData()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.resources==null && other.getResources()==null) || 
             (this.resources!=null &&
              java.util.Arrays.equals(this.resources, other.getResources()))) &&
            this.clientApproval == other.isClientApproval() &&
            ((this.clientName==null && other.getClientName()==null) || 
             (this.clientName!=null &&
              this.clientName.equals(other.getClientName()))) &&
            this.reviewPayment == other.getReviewPayment() &&
            this.specificationReviewPayment == other.getSpecificationReviewPayment() &&
            this.contestFee == other.getContestFee() &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory()))) &&
            this.confidence == other.getConfidence() &&
            this.pricingApproval == other.isPricingApproval() &&
            this.hasWikiSpecification == other.isHasWikiSpecification() &&
            this.passedSpecReview == other.isPassedSpecReview() &&
            this.hasDependentCompetitions == other.isHasDependentCompetitions() &&
            this.wasReposted == other.isWasReposted() &&
            ((this.notes==null && other.getNotes()==null) || 
             (this.notes!=null &&
              this.notes.equals(other.getNotes())));
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
        _hashCode += new Long(getId()).hashCode();
        if (getContestData() != null) {
            _hashCode += getContestData().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getResources() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResources());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResources(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isClientApproval() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getClientName() != null) {
            _hashCode += getClientName().hashCode();
        }
        _hashCode += new Double(getReviewPayment()).hashCode();
        _hashCode += new Double(getSpecificationReviewPayment()).hashCode();
        _hashCode += new Double(getContestFee()).hashCode();
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        _hashCode += getConfidence();
        _hashCode += (isPricingApproval() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isHasWikiSpecification() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isPassedSpecReview() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isHasDependentCompetitions() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isWasReposted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getNotes() != null) {
            _hashCode += getNotes().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StudioCompetition.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "studioCompetition"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competionType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resources");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resources"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "resource"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientApproval");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientApproval"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reviewPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reviewPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specificationReviewPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specificationReviewPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestFee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestFee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "contestStatusData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("category");
        elemField.setXmlName(new javax.xml.namespace.QName("", "category"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("confidence");
        elemField.setXmlName(new javax.xml.namespace.QName("", "confidence"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pricingApproval");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pricingApproval"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasWikiSpecification");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasWikiSpecification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passedSpecReview");
        elemField.setXmlName(new javax.xml.namespace.QName("", "passedSpecReview"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasDependentCompetitions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasDependentCompetitions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wasReposted");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wasReposted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notes"));
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
