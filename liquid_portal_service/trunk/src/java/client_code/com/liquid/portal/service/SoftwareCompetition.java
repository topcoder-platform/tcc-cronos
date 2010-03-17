/**
 * SoftwareCompetition.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class SoftwareCompetition  extends com.liquid.portal.service.Competition  implements java.io.Serializable {
    private long id;

    private com.liquid.portal.service.AssetDTO assetDTO;

    private project.management.topcoder.com.Project projectHeader;

    private project.management.topcoder.com.Project developmentProjectHeader;

    private java.lang.String projectHeaderReason;

    private phases.project.topcoder.com.Project projectPhases;

    private com.liquid.portal.service.Resource[] projectResources;

    private com.liquid.portal.service.FullProjectData projectData;

    private com.liquid.portal.service.CompetionType type;

    private java.lang.Double adminFee;

    private boolean clientApproval;

    private java.lang.String clientName;

    private double reviewPayment;

    private double specificationReviewPayment;

    private double contestFee;

    private com.liquid.portal.service.Status status;

    private com.liquid.portal.service.Category category;

    private int confidence;

    private boolean pricingApproval;

    private boolean hasWikiSpecification;

    private boolean passedSpecReview;

    private boolean hasDependentCompetitions;

    private boolean wasReposted;

    private java.lang.String notes;

    public SoftwareCompetition() {
    }

    public SoftwareCompetition(
           java.lang.Double _adminFee,
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
           com.liquid.portal.service.Resource[] resources,
           double _reviewPayment,
           java.lang.String shortSummary,
           double _specificationReviewPayment,
           java.lang.String startTime,
           com.liquid.portal.service.CompetionType _type,
           boolean _wasReposted,
           long id,
           com.liquid.portal.service.AssetDTO assetDTO,
           project.management.topcoder.com.Project projectHeader,
           project.management.topcoder.com.Project developmentProjectHeader,
           java.lang.String projectHeaderReason,
           phases.project.topcoder.com.Project projectPhases,
           com.liquid.portal.service.Resource[] projectResources,
           com.liquid.portal.service.FullProjectData projectData,
           com.liquid.portal.service.CompetionType type,
           java.lang.Double adminFee,
           boolean clientApproval,
           java.lang.String clientName,
           double reviewPayment,
           double specificationReviewPayment,
           double contestFee,
           com.liquid.portal.service.Status status,
           com.liquid.portal.service.Category category,
           int confidence,
           boolean pricingApproval,
           boolean hasWikiSpecification,
           boolean passedSpecReview,
           boolean hasDependentCompetitions,
           boolean wasReposted,
           java.lang.String notes) {
        super(
            _adminFee,
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
            resources,
            _reviewPayment,
            shortSummary,
            _specificationReviewPayment,
            startTime,
            _type,
            _wasReposted);
        this.id = id;
        this.assetDTO = assetDTO;
        this.projectHeader = projectHeader;
        this.developmentProjectHeader = developmentProjectHeader;
        this.projectHeaderReason = projectHeaderReason;
        this.projectPhases = projectPhases;
        this.projectResources = projectResources;
        this.projectData = projectData;
        this.type = type;
        this.adminFee = adminFee;
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
     * Gets the id value for this SoftwareCompetition.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this SoftwareCompetition.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the assetDTO value for this SoftwareCompetition.
     * 
     * @return assetDTO
     */
    public com.liquid.portal.service.AssetDTO getAssetDTO() {
        return assetDTO;
    }


    /**
     * Sets the assetDTO value for this SoftwareCompetition.
     * 
     * @param assetDTO
     */
    public void setAssetDTO(com.liquid.portal.service.AssetDTO assetDTO) {
        this.assetDTO = assetDTO;
    }


    /**
     * Gets the projectHeader value for this SoftwareCompetition.
     * 
     * @return projectHeader
     */
    public project.management.topcoder.com.Project getProjectHeader() {
        return projectHeader;
    }


    /**
     * Sets the projectHeader value for this SoftwareCompetition.
     * 
     * @param projectHeader
     */
    public void setProjectHeader(project.management.topcoder.com.Project projectHeader) {
        this.projectHeader = projectHeader;
    }


    /**
     * Gets the developmentProjectHeader value for this SoftwareCompetition.
     * 
     * @return developmentProjectHeader
     */
    public project.management.topcoder.com.Project getDevelopmentProjectHeader() {
        return developmentProjectHeader;
    }


    /**
     * Sets the developmentProjectHeader value for this SoftwareCompetition.
     * 
     * @param developmentProjectHeader
     */
    public void setDevelopmentProjectHeader(project.management.topcoder.com.Project developmentProjectHeader) {
        this.developmentProjectHeader = developmentProjectHeader;
    }


    /**
     * Gets the projectHeaderReason value for this SoftwareCompetition.
     * 
     * @return projectHeaderReason
     */
    public java.lang.String getProjectHeaderReason() {
        return projectHeaderReason;
    }


    /**
     * Sets the projectHeaderReason value for this SoftwareCompetition.
     * 
     * @param projectHeaderReason
     */
    public void setProjectHeaderReason(java.lang.String projectHeaderReason) {
        this.projectHeaderReason = projectHeaderReason;
    }


    /**
     * Gets the projectPhases value for this SoftwareCompetition.
     * 
     * @return projectPhases
     */
    public phases.project.topcoder.com.Project getProjectPhases() {
        return projectPhases;
    }


    /**
     * Sets the projectPhases value for this SoftwareCompetition.
     * 
     * @param projectPhases
     */
    public void setProjectPhases(phases.project.topcoder.com.Project projectPhases) {
        this.projectPhases = projectPhases;
    }


    /**
     * Gets the projectResources value for this SoftwareCompetition.
     * 
     * @return projectResources
     */
    public com.liquid.portal.service.Resource[] getProjectResources() {
        return projectResources;
    }


    /**
     * Sets the projectResources value for this SoftwareCompetition.
     * 
     * @param projectResources
     */
    public void setProjectResources(com.liquid.portal.service.Resource[] projectResources) {
        this.projectResources = projectResources;
    }

    public com.liquid.portal.service.Resource getProjectResources(int i) {
        return this.projectResources[i];
    }

    public void setProjectResources(int i, com.liquid.portal.service.Resource _value) {
        this.projectResources[i] = _value;
    }


    /**
     * Gets the projectData value for this SoftwareCompetition.
     * 
     * @return projectData
     */
    public com.liquid.portal.service.FullProjectData getProjectData() {
        return projectData;
    }


    /**
     * Sets the projectData value for this SoftwareCompetition.
     * 
     * @param projectData
     */
    public void setProjectData(com.liquid.portal.service.FullProjectData projectData) {
        this.projectData = projectData;
    }


    /**
     * Gets the type value for this SoftwareCompetition.
     * 
     * @return type
     */
    public com.liquid.portal.service.CompetionType getType() {
        return type;
    }


    /**
     * Sets the type value for this SoftwareCompetition.
     * 
     * @param type
     */
    public void setType(com.liquid.portal.service.CompetionType type) {
        this.type = type;
    }


    /**
     * Gets the adminFee value for this SoftwareCompetition.
     * 
     * @return adminFee
     */
    public java.lang.Double getAdminFee() {
        return adminFee;
    }


    /**
     * Sets the adminFee value for this SoftwareCompetition.
     * 
     * @param adminFee
     */
    public void setAdminFee(java.lang.Double adminFee) {
        this.adminFee = adminFee;
    }


    /**
     * Gets the clientApproval value for this SoftwareCompetition.
     * 
     * @return clientApproval
     */
    public boolean isClientApproval() {
        return clientApproval;
    }


    /**
     * Sets the clientApproval value for this SoftwareCompetition.
     * 
     * @param clientApproval
     */
    public void setClientApproval(boolean clientApproval) {
        this.clientApproval = clientApproval;
    }


    /**
     * Gets the clientName value for this SoftwareCompetition.
     * 
     * @return clientName
     */
    public java.lang.String getClientName() {
        return clientName;
    }


    /**
     * Sets the clientName value for this SoftwareCompetition.
     * 
     * @param clientName
     */
    public void setClientName(java.lang.String clientName) {
        this.clientName = clientName;
    }


    /**
     * Gets the reviewPayment value for this SoftwareCompetition.
     * 
     * @return reviewPayment
     */
    public double getReviewPayment() {
        return reviewPayment;
    }


    /**
     * Sets the reviewPayment value for this SoftwareCompetition.
     * 
     * @param reviewPayment
     */
    public void setReviewPayment(double reviewPayment) {
        this.reviewPayment = reviewPayment;
    }


    /**
     * Gets the specificationReviewPayment value for this SoftwareCompetition.
     * 
     * @return specificationReviewPayment
     */
    public double getSpecificationReviewPayment() {
        return specificationReviewPayment;
    }


    /**
     * Sets the specificationReviewPayment value for this SoftwareCompetition.
     * 
     * @param specificationReviewPayment
     */
    public void setSpecificationReviewPayment(double specificationReviewPayment) {
        this.specificationReviewPayment = specificationReviewPayment;
    }


    /**
     * Gets the contestFee value for this SoftwareCompetition.
     * 
     * @return contestFee
     */
    public double getContestFee() {
        return contestFee;
    }


    /**
     * Sets the contestFee value for this SoftwareCompetition.
     * 
     * @param contestFee
     */
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }


    /**
     * Gets the status value for this SoftwareCompetition.
     * 
     * @return status
     */
    public com.liquid.portal.service.Status getStatus() {
        return status;
    }


    /**
     * Sets the status value for this SoftwareCompetition.
     * 
     * @param status
     */
    public void setStatus(com.liquid.portal.service.Status status) {
        this.status = status;
    }


    /**
     * Gets the category value for this SoftwareCompetition.
     * 
     * @return category
     */
    public com.liquid.portal.service.Category getCategory() {
        return category;
    }


    /**
     * Sets the category value for this SoftwareCompetition.
     * 
     * @param category
     */
    public void setCategory(com.liquid.portal.service.Category category) {
        this.category = category;
    }


    /**
     * Gets the confidence value for this SoftwareCompetition.
     * 
     * @return confidence
     */
    public int getConfidence() {
        return confidence;
    }


    /**
     * Sets the confidence value for this SoftwareCompetition.
     * 
     * @param confidence
     */
    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }


    /**
     * Gets the pricingApproval value for this SoftwareCompetition.
     * 
     * @return pricingApproval
     */
    public boolean isPricingApproval() {
        return pricingApproval;
    }


    /**
     * Sets the pricingApproval value for this SoftwareCompetition.
     * 
     * @param pricingApproval
     */
    public void setPricingApproval(boolean pricingApproval) {
        this.pricingApproval = pricingApproval;
    }


    /**
     * Gets the hasWikiSpecification value for this SoftwareCompetition.
     * 
     * @return hasWikiSpecification
     */
    public boolean isHasWikiSpecification() {
        return hasWikiSpecification;
    }


    /**
     * Sets the hasWikiSpecification value for this SoftwareCompetition.
     * 
     * @param hasWikiSpecification
     */
    public void setHasWikiSpecification(boolean hasWikiSpecification) {
        this.hasWikiSpecification = hasWikiSpecification;
    }


    /**
     * Gets the passedSpecReview value for this SoftwareCompetition.
     * 
     * @return passedSpecReview
     */
    public boolean isPassedSpecReview() {
        return passedSpecReview;
    }


    /**
     * Sets the passedSpecReview value for this SoftwareCompetition.
     * 
     * @param passedSpecReview
     */
    public void setPassedSpecReview(boolean passedSpecReview) {
        this.passedSpecReview = passedSpecReview;
    }


    /**
     * Gets the hasDependentCompetitions value for this SoftwareCompetition.
     * 
     * @return hasDependentCompetitions
     */
    public boolean isHasDependentCompetitions() {
        return hasDependentCompetitions;
    }


    /**
     * Sets the hasDependentCompetitions value for this SoftwareCompetition.
     * 
     * @param hasDependentCompetitions
     */
    public void setHasDependentCompetitions(boolean hasDependentCompetitions) {
        this.hasDependentCompetitions = hasDependentCompetitions;
    }


    /**
     * Gets the wasReposted value for this SoftwareCompetition.
     * 
     * @return wasReposted
     */
    public boolean isWasReposted() {
        return wasReposted;
    }


    /**
     * Sets the wasReposted value for this SoftwareCompetition.
     * 
     * @param wasReposted
     */
    public void setWasReposted(boolean wasReposted) {
        this.wasReposted = wasReposted;
    }


    /**
     * Gets the notes value for this SoftwareCompetition.
     * 
     * @return notes
     */
    public java.lang.String getNotes() {
        return notes;
    }


    /**
     * Sets the notes value for this SoftwareCompetition.
     * 
     * @param notes
     */
    public void setNotes(java.lang.String notes) {
        this.notes = notes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SoftwareCompetition)) return false;
        SoftwareCompetition other = (SoftwareCompetition) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.id == other.getId() &&
            ((this.assetDTO==null && other.getAssetDTO()==null) || 
             (this.assetDTO!=null &&
              this.assetDTO.equals(other.getAssetDTO()))) &&
            ((this.projectHeader==null && other.getProjectHeader()==null) || 
             (this.projectHeader!=null &&
              this.projectHeader.equals(other.getProjectHeader()))) &&
            ((this.developmentProjectHeader==null && other.getDevelopmentProjectHeader()==null) || 
             (this.developmentProjectHeader!=null &&
              this.developmentProjectHeader.equals(other.getDevelopmentProjectHeader()))) &&
            ((this.projectHeaderReason==null && other.getProjectHeaderReason()==null) || 
             (this.projectHeaderReason!=null &&
              this.projectHeaderReason.equals(other.getProjectHeaderReason()))) &&
            ((this.projectPhases==null && other.getProjectPhases()==null) || 
             (this.projectPhases!=null &&
              this.projectPhases.equals(other.getProjectPhases()))) &&
            ((this.projectResources==null && other.getProjectResources()==null) || 
             (this.projectResources!=null &&
              java.util.Arrays.equals(this.projectResources, other.getProjectResources()))) &&
            ((this.projectData==null && other.getProjectData()==null) || 
             (this.projectData!=null &&
              this.projectData.equals(other.getProjectData()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.adminFee==null && other.getAdminFee()==null) || 
             (this.adminFee!=null &&
              this.adminFee.equals(other.getAdminFee()))) &&
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
        if (getAssetDTO() != null) {
            _hashCode += getAssetDTO().hashCode();
        }
        if (getProjectHeader() != null) {
            _hashCode += getProjectHeader().hashCode();
        }
        if (getDevelopmentProjectHeader() != null) {
            _hashCode += getDevelopmentProjectHeader().hashCode();
        }
        if (getProjectHeaderReason() != null) {
            _hashCode += getProjectHeaderReason().hashCode();
        }
        if (getProjectPhases() != null) {
            _hashCode += getProjectPhases().hashCode();
        }
        if (getProjectResources() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProjectResources());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProjectResources(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProjectData() != null) {
            _hashCode += getProjectData().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getAdminFee() != null) {
            _hashCode += getAdminFee().hashCode();
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
        new org.apache.axis.description.TypeDesc(SoftwareCompetition.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "softwareCompetition"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assetDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "assetDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "assetDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectHeader");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectHeader"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.management.project", "project"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("developmentProjectHeader");
        elemField.setXmlName(new javax.xml.namespace.QName("", "developmentProjectHeader"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.management.project", "project"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectHeaderReason");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectHeaderReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectPhases");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectPhases"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.project.phases", "project"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectResources");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectResources"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "resource"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "fullProjectData"));
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
        elemField.setFieldName("adminFee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adminFee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "status"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("category");
        elemField.setXmlName(new javax.xml.namespace.QName("", "category"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "category"));
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
