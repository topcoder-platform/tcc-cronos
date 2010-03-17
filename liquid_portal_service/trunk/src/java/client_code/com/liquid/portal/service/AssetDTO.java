/**
 * AssetDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liquid.portal.service;

public class AssetDTO  implements java.io.Serializable {
    private java.lang.Long id;

    private java.lang.String name;

    private java.lang.Long[] clientIds;

    private java.lang.String versionText;

    private java.lang.Long versionNumber;

    private java.lang.String shortDescription;

    private java.lang.String detailedDescription;

    private java.lang.String functionalDescription;

    private com.liquid.portal.service.Category rootCategory;

    private com.liquid.portal.service.Category[] categories;

    private com.liquid.portal.service.Technology[] technologies;

    private java.lang.String productionDate;

    private boolean isCurrentVersionAlsoLatestVersion;

    private java.lang.Long compVersionId;

    private java.lang.Long[] userIds;

    private boolean informationComplete;

    private com.liquid.portal.service.CompDocumentation[] documentation;

    private com.liquid.portal.service.CompUploadedFile[] compUploadedFiles;

    private java.lang.String compComments;

    private java.lang.String phase;

    private java.lang.Long[] dependencies;

    private java.lang.Long version;

    private java.lang.Boolean toCreateMinorVersion;

    public AssetDTO() {
    }

    public AssetDTO(
           java.lang.Long id,
           java.lang.String name,
           java.lang.Long[] clientIds,
           java.lang.String versionText,
           java.lang.Long versionNumber,
           java.lang.String shortDescription,
           java.lang.String detailedDescription,
           java.lang.String functionalDescription,
           com.liquid.portal.service.Category rootCategory,
           com.liquid.portal.service.Category[] categories,
           com.liquid.portal.service.Technology[] technologies,
           java.lang.String productionDate,
           boolean isCurrentVersionAlsoLatestVersion,
           java.lang.Long compVersionId,
           java.lang.Long[] userIds,
           boolean informationComplete,
           com.liquid.portal.service.CompDocumentation[] documentation,
           com.liquid.portal.service.CompUploadedFile[] compUploadedFiles,
           java.lang.String compComments,
           java.lang.String phase,
           java.lang.Long[] dependencies,
           java.lang.Long version,
           java.lang.Boolean toCreateMinorVersion) {
           this.id = id;
           this.name = name;
           this.clientIds = clientIds;
           this.versionText = versionText;
           this.versionNumber = versionNumber;
           this.shortDescription = shortDescription;
           this.detailedDescription = detailedDescription;
           this.functionalDescription = functionalDescription;
           this.rootCategory = rootCategory;
           this.categories = categories;
           this.technologies = technologies;
           this.productionDate = productionDate;
           this.isCurrentVersionAlsoLatestVersion = isCurrentVersionAlsoLatestVersion;
           this.compVersionId = compVersionId;
           this.userIds = userIds;
           this.informationComplete = informationComplete;
           this.documentation = documentation;
           this.compUploadedFiles = compUploadedFiles;
           this.compComments = compComments;
           this.phase = phase;
           this.dependencies = dependencies;
           this.version = version;
           this.toCreateMinorVersion = toCreateMinorVersion;
    }


    /**
     * Gets the id value for this AssetDTO.
     * 
     * @return id
     */
    public java.lang.Long getId() {
        return id;
    }


    /**
     * Sets the id value for this AssetDTO.
     * 
     * @param id
     */
    public void setId(java.lang.Long id) {
        this.id = id;
    }


    /**
     * Gets the name value for this AssetDTO.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this AssetDTO.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the clientIds value for this AssetDTO.
     * 
     * @return clientIds
     */
    public java.lang.Long[] getClientIds() {
        return clientIds;
    }


    /**
     * Sets the clientIds value for this AssetDTO.
     * 
     * @param clientIds
     */
    public void setClientIds(java.lang.Long[] clientIds) {
        this.clientIds = clientIds;
    }

    public java.lang.Long getClientIds(int i) {
        return this.clientIds[i];
    }

    public void setClientIds(int i, java.lang.Long _value) {
        this.clientIds[i] = _value;
    }


    /**
     * Gets the versionText value for this AssetDTO.
     * 
     * @return versionText
     */
    public java.lang.String getVersionText() {
        return versionText;
    }


    /**
     * Sets the versionText value for this AssetDTO.
     * 
     * @param versionText
     */
    public void setVersionText(java.lang.String versionText) {
        this.versionText = versionText;
    }


    /**
     * Gets the versionNumber value for this AssetDTO.
     * 
     * @return versionNumber
     */
    public java.lang.Long getVersionNumber() {
        return versionNumber;
    }


    /**
     * Sets the versionNumber value for this AssetDTO.
     * 
     * @param versionNumber
     */
    public void setVersionNumber(java.lang.Long versionNumber) {
        this.versionNumber = versionNumber;
    }


    /**
     * Gets the shortDescription value for this AssetDTO.
     * 
     * @return shortDescription
     */
    public java.lang.String getShortDescription() {
        return shortDescription;
    }


    /**
     * Sets the shortDescription value for this AssetDTO.
     * 
     * @param shortDescription
     */
    public void setShortDescription(java.lang.String shortDescription) {
        this.shortDescription = shortDescription;
    }


    /**
     * Gets the detailedDescription value for this AssetDTO.
     * 
     * @return detailedDescription
     */
    public java.lang.String getDetailedDescription() {
        return detailedDescription;
    }


    /**
     * Sets the detailedDescription value for this AssetDTO.
     * 
     * @param detailedDescription
     */
    public void setDetailedDescription(java.lang.String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }


    /**
     * Gets the functionalDescription value for this AssetDTO.
     * 
     * @return functionalDescription
     */
    public java.lang.String getFunctionalDescription() {
        return functionalDescription;
    }


    /**
     * Sets the functionalDescription value for this AssetDTO.
     * 
     * @param functionalDescription
     */
    public void setFunctionalDescription(java.lang.String functionalDescription) {
        this.functionalDescription = functionalDescription;
    }


    /**
     * Gets the rootCategory value for this AssetDTO.
     * 
     * @return rootCategory
     */
    public com.liquid.portal.service.Category getRootCategory() {
        return rootCategory;
    }


    /**
     * Sets the rootCategory value for this AssetDTO.
     * 
     * @param rootCategory
     */
    public void setRootCategory(com.liquid.portal.service.Category rootCategory) {
        this.rootCategory = rootCategory;
    }


    /**
     * Gets the categories value for this AssetDTO.
     * 
     * @return categories
     */
    public com.liquid.portal.service.Category[] getCategories() {
        return categories;
    }


    /**
     * Sets the categories value for this AssetDTO.
     * 
     * @param categories
     */
    public void setCategories(com.liquid.portal.service.Category[] categories) {
        this.categories = categories;
    }

    public com.liquid.portal.service.Category getCategories(int i) {
        return this.categories[i];
    }

    public void setCategories(int i, com.liquid.portal.service.Category _value) {
        this.categories[i] = _value;
    }


    /**
     * Gets the technologies value for this AssetDTO.
     * 
     * @return technologies
     */
    public com.liquid.portal.service.Technology[] getTechnologies() {
        return technologies;
    }


    /**
     * Sets the technologies value for this AssetDTO.
     * 
     * @param technologies
     */
    public void setTechnologies(com.liquid.portal.service.Technology[] technologies) {
        this.technologies = technologies;
    }

    public com.liquid.portal.service.Technology getTechnologies(int i) {
        return this.technologies[i];
    }

    public void setTechnologies(int i, com.liquid.portal.service.Technology _value) {
        this.technologies[i] = _value;
    }


    /**
     * Gets the productionDate value for this AssetDTO.
     * 
     * @return productionDate
     */
    public java.lang.String getProductionDate() {
        return productionDate;
    }


    /**
     * Sets the productionDate value for this AssetDTO.
     * 
     * @param productionDate
     */
    public void setProductionDate(java.lang.String productionDate) {
        this.productionDate = productionDate;
    }


    /**
     * Gets the isCurrentVersionAlsoLatestVersion value for this AssetDTO.
     * 
     * @return isCurrentVersionAlsoLatestVersion
     */
    public boolean isIsCurrentVersionAlsoLatestVersion() {
        return isCurrentVersionAlsoLatestVersion;
    }


    /**
     * Sets the isCurrentVersionAlsoLatestVersion value for this AssetDTO.
     * 
     * @param isCurrentVersionAlsoLatestVersion
     */
    public void setIsCurrentVersionAlsoLatestVersion(boolean isCurrentVersionAlsoLatestVersion) {
        this.isCurrentVersionAlsoLatestVersion = isCurrentVersionAlsoLatestVersion;
    }


    /**
     * Gets the compVersionId value for this AssetDTO.
     * 
     * @return compVersionId
     */
    public java.lang.Long getCompVersionId() {
        return compVersionId;
    }


    /**
     * Sets the compVersionId value for this AssetDTO.
     * 
     * @param compVersionId
     */
    public void setCompVersionId(java.lang.Long compVersionId) {
        this.compVersionId = compVersionId;
    }


    /**
     * Gets the userIds value for this AssetDTO.
     * 
     * @return userIds
     */
    public java.lang.Long[] getUserIds() {
        return userIds;
    }


    /**
     * Sets the userIds value for this AssetDTO.
     * 
     * @param userIds
     */
    public void setUserIds(java.lang.Long[] userIds) {
        this.userIds = userIds;
    }

    public java.lang.Long getUserIds(int i) {
        return this.userIds[i];
    }

    public void setUserIds(int i, java.lang.Long _value) {
        this.userIds[i] = _value;
    }


    /**
     * Gets the informationComplete value for this AssetDTO.
     * 
     * @return informationComplete
     */
    public boolean isInformationComplete() {
        return informationComplete;
    }


    /**
     * Sets the informationComplete value for this AssetDTO.
     * 
     * @param informationComplete
     */
    public void setInformationComplete(boolean informationComplete) {
        this.informationComplete = informationComplete;
    }


    /**
     * Gets the documentation value for this AssetDTO.
     * 
     * @return documentation
     */
    public com.liquid.portal.service.CompDocumentation[] getDocumentation() {
        return documentation;
    }


    /**
     * Sets the documentation value for this AssetDTO.
     * 
     * @param documentation
     */
    public void setDocumentation(com.liquid.portal.service.CompDocumentation[] documentation) {
        this.documentation = documentation;
    }

    public com.liquid.portal.service.CompDocumentation getDocumentation(int i) {
        return this.documentation[i];
    }

    public void setDocumentation(int i, com.liquid.portal.service.CompDocumentation _value) {
        this.documentation[i] = _value;
    }


    /**
     * Gets the compUploadedFiles value for this AssetDTO.
     * 
     * @return compUploadedFiles
     */
    public com.liquid.portal.service.CompUploadedFile[] getCompUploadedFiles() {
        return compUploadedFiles;
    }


    /**
     * Sets the compUploadedFiles value for this AssetDTO.
     * 
     * @param compUploadedFiles
     */
    public void setCompUploadedFiles(com.liquid.portal.service.CompUploadedFile[] compUploadedFiles) {
        this.compUploadedFiles = compUploadedFiles;
    }

    public com.liquid.portal.service.CompUploadedFile getCompUploadedFiles(int i) {
        return this.compUploadedFiles[i];
    }

    public void setCompUploadedFiles(int i, com.liquid.portal.service.CompUploadedFile _value) {
        this.compUploadedFiles[i] = _value;
    }


    /**
     * Gets the compComments value for this AssetDTO.
     * 
     * @return compComments
     */
    public java.lang.String getCompComments() {
        return compComments;
    }


    /**
     * Sets the compComments value for this AssetDTO.
     * 
     * @param compComments
     */
    public void setCompComments(java.lang.String compComments) {
        this.compComments = compComments;
    }


    /**
     * Gets the phase value for this AssetDTO.
     * 
     * @return phase
     */
    public java.lang.String getPhase() {
        return phase;
    }


    /**
     * Sets the phase value for this AssetDTO.
     * 
     * @param phase
     */
    public void setPhase(java.lang.String phase) {
        this.phase = phase;
    }


    /**
     * Gets the dependencies value for this AssetDTO.
     * 
     * @return dependencies
     */
    public java.lang.Long[] getDependencies() {
        return dependencies;
    }


    /**
     * Sets the dependencies value for this AssetDTO.
     * 
     * @param dependencies
     */
    public void setDependencies(java.lang.Long[] dependencies) {
        this.dependencies = dependencies;
    }

    public java.lang.Long getDependencies(int i) {
        return this.dependencies[i];
    }

    public void setDependencies(int i, java.lang.Long _value) {
        this.dependencies[i] = _value;
    }


    /**
     * Gets the version value for this AssetDTO.
     * 
     * @return version
     */
    public java.lang.Long getVersion() {
        return version;
    }


    /**
     * Sets the version value for this AssetDTO.
     * 
     * @param version
     */
    public void setVersion(java.lang.Long version) {
        this.version = version;
    }


    /**
     * Gets the toCreateMinorVersion value for this AssetDTO.
     * 
     * @return toCreateMinorVersion
     */
    public java.lang.Boolean getToCreateMinorVersion() {
        return toCreateMinorVersion;
    }


    /**
     * Sets the toCreateMinorVersion value for this AssetDTO.
     * 
     * @param toCreateMinorVersion
     */
    public void setToCreateMinorVersion(java.lang.Boolean toCreateMinorVersion) {
        this.toCreateMinorVersion = toCreateMinorVersion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AssetDTO)) return false;
        AssetDTO other = (AssetDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.clientIds==null && other.getClientIds()==null) || 
             (this.clientIds!=null &&
              java.util.Arrays.equals(this.clientIds, other.getClientIds()))) &&
            ((this.versionText==null && other.getVersionText()==null) || 
             (this.versionText!=null &&
              this.versionText.equals(other.getVersionText()))) &&
            ((this.versionNumber==null && other.getVersionNumber()==null) || 
             (this.versionNumber!=null &&
              this.versionNumber.equals(other.getVersionNumber()))) &&
            ((this.shortDescription==null && other.getShortDescription()==null) || 
             (this.shortDescription!=null &&
              this.shortDescription.equals(other.getShortDescription()))) &&
            ((this.detailedDescription==null && other.getDetailedDescription()==null) || 
             (this.detailedDescription!=null &&
              this.detailedDescription.equals(other.getDetailedDescription()))) &&
            ((this.functionalDescription==null && other.getFunctionalDescription()==null) || 
             (this.functionalDescription!=null &&
              this.functionalDescription.equals(other.getFunctionalDescription()))) &&
            ((this.rootCategory==null && other.getRootCategory()==null) || 
             (this.rootCategory!=null &&
              this.rootCategory.equals(other.getRootCategory()))) &&
            ((this.categories==null && other.getCategories()==null) || 
             (this.categories!=null &&
              java.util.Arrays.equals(this.categories, other.getCategories()))) &&
            ((this.technologies==null && other.getTechnologies()==null) || 
             (this.technologies!=null &&
              java.util.Arrays.equals(this.technologies, other.getTechnologies()))) &&
            ((this.productionDate==null && other.getProductionDate()==null) || 
             (this.productionDate!=null &&
              this.productionDate.equals(other.getProductionDate()))) &&
            this.isCurrentVersionAlsoLatestVersion == other.isIsCurrentVersionAlsoLatestVersion() &&
            ((this.compVersionId==null && other.getCompVersionId()==null) || 
             (this.compVersionId!=null &&
              this.compVersionId.equals(other.getCompVersionId()))) &&
            ((this.userIds==null && other.getUserIds()==null) || 
             (this.userIds!=null &&
              java.util.Arrays.equals(this.userIds, other.getUserIds()))) &&
            this.informationComplete == other.isInformationComplete() &&
            ((this.documentation==null && other.getDocumentation()==null) || 
             (this.documentation!=null &&
              java.util.Arrays.equals(this.documentation, other.getDocumentation()))) &&
            ((this.compUploadedFiles==null && other.getCompUploadedFiles()==null) || 
             (this.compUploadedFiles!=null &&
              java.util.Arrays.equals(this.compUploadedFiles, other.getCompUploadedFiles()))) &&
            ((this.compComments==null && other.getCompComments()==null) || 
             (this.compComments!=null &&
              this.compComments.equals(other.getCompComments()))) &&
            ((this.phase==null && other.getPhase()==null) || 
             (this.phase!=null &&
              this.phase.equals(other.getPhase()))) &&
            ((this.dependencies==null && other.getDependencies()==null) || 
             (this.dependencies!=null &&
              java.util.Arrays.equals(this.dependencies, other.getDependencies()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.toCreateMinorVersion==null && other.getToCreateMinorVersion()==null) || 
             (this.toCreateMinorVersion!=null &&
              this.toCreateMinorVersion.equals(other.getToCreateMinorVersion())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getClientIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClientIds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClientIds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVersionText() != null) {
            _hashCode += getVersionText().hashCode();
        }
        if (getVersionNumber() != null) {
            _hashCode += getVersionNumber().hashCode();
        }
        if (getShortDescription() != null) {
            _hashCode += getShortDescription().hashCode();
        }
        if (getDetailedDescription() != null) {
            _hashCode += getDetailedDescription().hashCode();
        }
        if (getFunctionalDescription() != null) {
            _hashCode += getFunctionalDescription().hashCode();
        }
        if (getRootCategory() != null) {
            _hashCode += getRootCategory().hashCode();
        }
        if (getCategories() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCategories());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCategories(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTechnologies() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTechnologies());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTechnologies(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProductionDate() != null) {
            _hashCode += getProductionDate().hashCode();
        }
        _hashCode += (isIsCurrentVersionAlsoLatestVersion() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCompVersionId() != null) {
            _hashCode += getCompVersionId().hashCode();
        }
        if (getUserIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUserIds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUserIds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isInformationComplete() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getDocumentation() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentation());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentation(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCompUploadedFiles() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCompUploadedFiles());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCompUploadedFiles(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCompComments() != null) {
            _hashCode += getCompComments().hashCode();
        }
        if (getPhase() != null) {
            _hashCode += getPhase().hashCode();
        }
        if (getDependencies() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDependencies());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDependencies(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getToCreateMinorVersion() != null) {
            _hashCode += getToCreateMinorVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AssetDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "assetDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientIds");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientIds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versionText");
        elemField.setXmlName(new javax.xml.namespace.QName("", "versionText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versionNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "versionNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shortDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detailedDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "detailedDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("functionalDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "functionalDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rootCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rootCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "category"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categories");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "category"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("technologies");
        elemField.setXmlName(new javax.xml.namespace.QName("", "technologies"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "technology"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isCurrentVersionAlsoLatestVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isCurrentVersionAlsoLatestVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("compVersionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "compVersionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userIds");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userIds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informationComplete");
        elemField.setXmlName(new javax.xml.namespace.QName("", "informationComplete"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "documentation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "compDocumentation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("compUploadedFiles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "compUploadedFiles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "compUploadedFile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("compComments");
        elemField.setXmlName(new javax.xml.namespace.QName("", "compComments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phase");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dependencies");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dependencies"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("", "version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toCreateMinorVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "toCreateMinorVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
