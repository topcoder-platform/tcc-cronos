/**
 * Competition.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public abstract class Competition  implements java.io.Serializable {
    private java.lang.Double adminFee;

    private boolean clientApproval;

    private java.lang.String clientName;

    private java.lang.Long competitionId;

    private int confidence;

    private double contestFee;

    private long creatorUserId;

    private double drPoints;

    private java.lang.String eligibility;

    private java.lang.String endTime;

    private boolean hasDependentCompetitions;

    private boolean hasWikiSpecification;

    private long id;

    private java.lang.String notes;

    private boolean passedSpecReview;

    private boolean pricingApproval;

    private com.liquid.portal.service.CompetitionPrize[] prizes;

    private project.service.topcoder.com.Project project;

    private com.liquid.portal.service.Resource[] resources;

    private double reviewPayment;

    private java.lang.String shortSummary;

    private double specificationReviewPayment;

    private java.lang.String startTime;

    private com.liquid.portal.service.CompetionType type;

    private boolean wasReposted;

    public Competition() {
    }

    public Competition(
           java.lang.Double adminFee,
           boolean clientApproval,
           java.lang.String clientName,
           java.lang.Long competitionId,
           int confidence,
           double contestFee,
           long creatorUserId,
           double drPoints,
           java.lang.String eligibility,
           java.lang.String endTime,
           boolean hasDependentCompetitions,
           boolean hasWikiSpecification,
           long id,
           java.lang.String notes,
           boolean passedSpecReview,
           boolean pricingApproval,
           com.liquid.portal.service.CompetitionPrize[] prizes,
           project.service.topcoder.com.Project project,
           com.liquid.portal.service.Resource[] resources,
           double reviewPayment,
           java.lang.String shortSummary,
           double specificationReviewPayment,
           java.lang.String startTime,
           com.liquid.portal.service.CompetionType type,
           boolean wasReposted) {
           this.adminFee = adminFee;
           this.clientApproval = clientApproval;
           this.clientName = clientName;
           this.competitionId = competitionId;
           this.confidence = confidence;
           this.contestFee = contestFee;
           this.creatorUserId = creatorUserId;
           this.drPoints = drPoints;
           this.eligibility = eligibility;
           this.endTime = endTime;
           this.hasDependentCompetitions = hasDependentCompetitions;
           this.hasWikiSpecification = hasWikiSpecification;
           this.id = id;
           this.notes = notes;
           this.passedSpecReview = passedSpecReview;
           this.pricingApproval = pricingApproval;
           this.prizes = prizes;
           this.project = project;
           this.resources = resources;
           this.reviewPayment = reviewPayment;
           this.shortSummary = shortSummary;
           this.specificationReviewPayment = specificationReviewPayment;
           this.startTime = startTime;
           this.type = type;
           this.wasReposted = wasReposted;
    }


    /**
     * Gets the adminFee value for this Competition.
     * 
     * @return adminFee
     */
    public java.lang.Double getAdminFee() {
        return adminFee;
    }


    /**
     * Sets the adminFee value for this Competition.
     * 
     * @param adminFee
     */
    public void setAdminFee(java.lang.Double adminFee) {
        this.adminFee = adminFee;
    }


    /**
     * Gets the clientApproval value for this Competition.
     * 
     * @return clientApproval
     */
    public boolean isClientApproval() {
        return clientApproval;
    }


    /**
     * Sets the clientApproval value for this Competition.
     * 
     * @param clientApproval
     */
    public void setClientApproval(boolean clientApproval) {
        this.clientApproval = clientApproval;
    }


    /**
     * Gets the clientName value for this Competition.
     * 
     * @return clientName
     */
    public java.lang.String getClientName() {
        return clientName;
    }


    /**
     * Sets the clientName value for this Competition.
     * 
     * @param clientName
     */
    public void setClientName(java.lang.String clientName) {
        this.clientName = clientName;
    }


    /**
     * Gets the competitionId value for this Competition.
     * 
     * @return competitionId
     */
    public java.lang.Long getCompetitionId() {
        return competitionId;
    }


    /**
     * Sets the competitionId value for this Competition.
     * 
     * @param competitionId
     */
    public void setCompetitionId(java.lang.Long competitionId) {
        this.competitionId = competitionId;
    }


    /**
     * Gets the confidence value for this Competition.
     * 
     * @return confidence
     */
    public int getConfidence() {
        return confidence;
    }


    /**
     * Sets the confidence value for this Competition.
     * 
     * @param confidence
     */
    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }


    /**
     * Gets the contestFee value for this Competition.
     * 
     * @return contestFee
     */
    public double getContestFee() {
        return contestFee;
    }


    /**
     * Sets the contestFee value for this Competition.
     * 
     * @param contestFee
     */
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }


    /**
     * Gets the creatorUserId value for this Competition.
     * 
     * @return creatorUserId
     */
    public long getCreatorUserId() {
        return creatorUserId;
    }


    /**
     * Sets the creatorUserId value for this Competition.
     * 
     * @param creatorUserId
     */
    public void setCreatorUserId(long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }


    /**
     * Gets the drPoints value for this Competition.
     * 
     * @return drPoints
     */
    public double getDrPoints() {
        return drPoints;
    }


    /**
     * Sets the drPoints value for this Competition.
     * 
     * @param drPoints
     */
    public void setDrPoints(double drPoints) {
        this.drPoints = drPoints;
    }


    /**
     * Gets the eligibility value for this Competition.
     * 
     * @return eligibility
     */
    public java.lang.String getEligibility() {
        return eligibility;
    }


    /**
     * Sets the eligibility value for this Competition.
     * 
     * @param eligibility
     */
    public void setEligibility(java.lang.String eligibility) {
        this.eligibility = eligibility;
    }


    /**
     * Gets the endTime value for this Competition.
     * 
     * @return endTime
     */
    public java.lang.String getEndTime() {
        return endTime;
    }


    /**
     * Sets the endTime value for this Competition.
     * 
     * @param endTime
     */
    public void setEndTime(java.lang.String endTime) {
        this.endTime = endTime;
    }


    /**
     * Gets the hasDependentCompetitions value for this Competition.
     * 
     * @return hasDependentCompetitions
     */
    public boolean isHasDependentCompetitions() {
        return hasDependentCompetitions;
    }


    /**
     * Sets the hasDependentCompetitions value for this Competition.
     * 
     * @param hasDependentCompetitions
     */
    public void setHasDependentCompetitions(boolean hasDependentCompetitions) {
        this.hasDependentCompetitions = hasDependentCompetitions;
    }


    /**
     * Gets the hasWikiSpecification value for this Competition.
     * 
     * @return hasWikiSpecification
     */
    public boolean isHasWikiSpecification() {
        return hasWikiSpecification;
    }


    /**
     * Sets the hasWikiSpecification value for this Competition.
     * 
     * @param hasWikiSpecification
     */
    public void setHasWikiSpecification(boolean hasWikiSpecification) {
        this.hasWikiSpecification = hasWikiSpecification;
    }


    /**
     * Gets the id value for this Competition.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this Competition.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the notes value for this Competition.
     * 
     * @return notes
     */
    public java.lang.String getNotes() {
        return notes;
    }


    /**
     * Sets the notes value for this Competition.
     * 
     * @param notes
     */
    public void setNotes(java.lang.String notes) {
        this.notes = notes;
    }


    /**
     * Gets the passedSpecReview value for this Competition.
     * 
     * @return passedSpecReview
     */
    public boolean isPassedSpecReview() {
        return passedSpecReview;
    }


    /**
     * Sets the passedSpecReview value for this Competition.
     * 
     * @param passedSpecReview
     */
    public void setPassedSpecReview(boolean passedSpecReview) {
        this.passedSpecReview = passedSpecReview;
    }


    /**
     * Gets the pricingApproval value for this Competition.
     * 
     * @return pricingApproval
     */
    public boolean isPricingApproval() {
        return pricingApproval;
    }


    /**
     * Sets the pricingApproval value for this Competition.
     * 
     * @param pricingApproval
     */
    public void setPricingApproval(boolean pricingApproval) {
        this.pricingApproval = pricingApproval;
    }


    /**
     * Gets the prizes value for this Competition.
     * 
     * @return prizes
     */
    public com.liquid.portal.service.CompetitionPrize[] getPrizes() {
        return prizes;
    }


    /**
     * Sets the prizes value for this Competition.
     * 
     * @param prizes
     */
    public void setPrizes(com.liquid.portal.service.CompetitionPrize[] prizes) {
        this.prizes = prizes;
    }

    public com.liquid.portal.service.CompetitionPrize getPrizes(int i) {
        return this.prizes[i];
    }

    public void setPrizes(int i, com.liquid.portal.service.CompetitionPrize _value) {
        this.prizes[i] = _value;
    }


    /**
     * Gets the project value for this Competition.
     * 
     * @return project
     */
    public project.service.topcoder.com.Project getProject() {
        return project;
    }


    /**
     * Sets the project value for this Competition.
     * 
     * @param project
     */
    public void setProject(project.service.topcoder.com.Project project) {
        this.project = project;
    }


    /**
     * Gets the resources value for this Competition.
     * 
     * @return resources
     */
    public com.liquid.portal.service.Resource[] getResources() {
        return resources;
    }


    /**
     * Sets the resources value for this Competition.
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
     * Gets the reviewPayment value for this Competition.
     * 
     * @return reviewPayment
     */
    public double getReviewPayment() {
        return reviewPayment;
    }


    /**
     * Sets the reviewPayment value for this Competition.
     * 
     * @param reviewPayment
     */
    public void setReviewPayment(double reviewPayment) {
        this.reviewPayment = reviewPayment;
    }


    /**
     * Gets the shortSummary value for this Competition.
     * 
     * @return shortSummary
     */
    public java.lang.String getShortSummary() {
        return shortSummary;
    }


    /**
     * Sets the shortSummary value for this Competition.
     * 
     * @param shortSummary
     */
    public void setShortSummary(java.lang.String shortSummary) {
        this.shortSummary = shortSummary;
    }


    /**
     * Gets the specificationReviewPayment value for this Competition.
     * 
     * @return specificationReviewPayment
     */
    public double getSpecificationReviewPayment() {
        return specificationReviewPayment;
    }


    /**
     * Sets the specificationReviewPayment value for this Competition.
     * 
     * @param specificationReviewPayment
     */
    public void setSpecificationReviewPayment(double specificationReviewPayment) {
        this.specificationReviewPayment = specificationReviewPayment;
    }


    /**
     * Gets the startTime value for this Competition.
     * 
     * @return startTime
     */
    public java.lang.String getStartTime() {
        return startTime;
    }


    /**
     * Sets the startTime value for this Competition.
     * 
     * @param startTime
     */
    public void setStartTime(java.lang.String startTime) {
        this.startTime = startTime;
    }


    /**
     * Gets the type value for this Competition.
     * 
     * @return type
     */
    public com.liquid.portal.service.CompetionType getType() {
        return type;
    }


    /**
     * Sets the type value for this Competition.
     * 
     * @param type
     */
    public void setType(com.liquid.portal.service.CompetionType type) {
        this.type = type;
    }


    /**
     * Gets the wasReposted value for this Competition.
     * 
     * @return wasReposted
     */
    public boolean isWasReposted() {
        return wasReposted;
    }


    /**
     * Sets the wasReposted value for this Competition.
     * 
     * @param wasReposted
     */
    public void setWasReposted(boolean wasReposted) {
        this.wasReposted = wasReposted;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Competition)) return false;
        Competition other = (Competition) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.adminFee==null && other.getAdminFee()==null) || 
             (this.adminFee!=null &&
              this.adminFee.equals(other.getAdminFee()))) &&
            this.clientApproval == other.isClientApproval() &&
            ((this.clientName==null && other.getClientName()==null) || 
             (this.clientName!=null &&
              this.clientName.equals(other.getClientName()))) &&
            ((this.competitionId==null && other.getCompetitionId()==null) || 
             (this.competitionId!=null &&
              this.competitionId.equals(other.getCompetitionId()))) &&
            this.confidence == other.getConfidence() &&
            this.contestFee == other.getContestFee() &&
            this.creatorUserId == other.getCreatorUserId() &&
            this.drPoints == other.getDrPoints() &&
            ((this.eligibility==null && other.getEligibility()==null) || 
             (this.eligibility!=null &&
              this.eligibility.equals(other.getEligibility()))) &&
            ((this.endTime==null && other.getEndTime()==null) || 
             (this.endTime!=null &&
              this.endTime.equals(other.getEndTime()))) &&
            this.hasDependentCompetitions == other.isHasDependentCompetitions() &&
            this.hasWikiSpecification == other.isHasWikiSpecification() &&
            this.id == other.getId() &&
            ((this.notes==null && other.getNotes()==null) || 
             (this.notes!=null &&
              this.notes.equals(other.getNotes()))) &&
            this.passedSpecReview == other.isPassedSpecReview() &&
            this.pricingApproval == other.isPricingApproval() &&
            ((this.prizes==null && other.getPrizes()==null) || 
             (this.prizes!=null &&
              java.util.Arrays.equals(this.prizes, other.getPrizes()))) &&
            ((this.project==null && other.getProject()==null) || 
             (this.project!=null &&
              this.project.equals(other.getProject()))) &&
            ((this.resources==null && other.getResources()==null) || 
             (this.resources!=null &&
              java.util.Arrays.equals(this.resources, other.getResources()))) &&
            this.reviewPayment == other.getReviewPayment() &&
            ((this.shortSummary==null && other.getShortSummary()==null) || 
             (this.shortSummary!=null &&
              this.shortSummary.equals(other.getShortSummary()))) &&
            this.specificationReviewPayment == other.getSpecificationReviewPayment() &&
            ((this.startTime==null && other.getStartTime()==null) || 
             (this.startTime!=null &&
              this.startTime.equals(other.getStartTime()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            this.wasReposted == other.isWasReposted();
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
        if (getAdminFee() != null) {
            _hashCode += getAdminFee().hashCode();
        }
        _hashCode += (isClientApproval() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getClientName() != null) {
            _hashCode += getClientName().hashCode();
        }
        if (getCompetitionId() != null) {
            _hashCode += getCompetitionId().hashCode();
        }
        _hashCode += getConfidence();
        _hashCode += new Double(getContestFee()).hashCode();
        _hashCode += new Long(getCreatorUserId()).hashCode();
        _hashCode += new Double(getDrPoints()).hashCode();
        if (getEligibility() != null) {
            _hashCode += getEligibility().hashCode();
        }
        if (getEndTime() != null) {
            _hashCode += getEndTime().hashCode();
        }
        _hashCode += (isHasDependentCompetitions() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isHasWikiSpecification() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += new Long(getId()).hashCode();
        if (getNotes() != null) {
            _hashCode += getNotes().hashCode();
        }
        _hashCode += (isPassedSpecReview() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isPricingApproval() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPrizes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPrizes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPrizes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProject() != null) {
            _hashCode += getProject().hashCode();
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
        _hashCode += new Double(getReviewPayment()).hashCode();
        if (getShortSummary() != null) {
            _hashCode += getShortSummary().hashCode();
        }
        _hashCode += new Double(getSpecificationReviewPayment()).hashCode();
        if (getStartTime() != null) {
            _hashCode += getStartTime().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        _hashCode += (isWasReposted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Competition.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competition"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("competitionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "competitionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
        elemField.setFieldName("contestFee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestFee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creatorUserId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creatorUserId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drPoints");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drPoints"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eligibility");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eligibility"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasDependentCompetitions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasDependentCompetitions"));
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
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passedSpecReview");
        elemField.setXmlName(new javax.xml.namespace.QName("", "passedSpecReview"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pricingApproval");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pricingApproval"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prizes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prizes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competitionPrize"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("project");
        elemField.setXmlName(new javax.xml.namespace.QName("", "project"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.service.project", "project"));
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
        elemField.setFieldName("reviewPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reviewPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortSummary");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shortSummary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specificationReviewPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specificationReviewPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
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
        elemField.setFieldName("wasReposted");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wasReposted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
