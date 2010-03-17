/**
 * Project.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package project.management.topcoder.com;

public class Project  extends com.liquid.portal.service.AuditableObject  implements java.io.Serializable {
    private long id;

    private com.liquid.portal.service.ProjectCategory projectCategory;

    private com.liquid.portal.service.ProjectSpec projectSpec;

    private com.liquid.portal.service.ProjectStatus projectStatus;

    private project.management.topcoder.com.ProjectPropertiesEntry[] properties;

    private long tcDirectProjectId;

    private java.lang.String tcDirectProjectName;

    public Project() {
    }

    public Project(
           java.util.Calendar creationTimestamp,
           java.lang.String creationUser,
           java.util.Calendar modificationTimestamp,
           java.lang.String modificationUser,
           long id,
           com.liquid.portal.service.ProjectCategory projectCategory,
           com.liquid.portal.service.ProjectSpec projectSpec,
           com.liquid.portal.service.ProjectStatus projectStatus,
           project.management.topcoder.com.ProjectPropertiesEntry[] properties,
           long tcDirectProjectId,
           java.lang.String tcDirectProjectName) {
        super(
            creationTimestamp,
            creationUser,
            modificationTimestamp,
            modificationUser);
        this.id = id;
        this.projectCategory = projectCategory;
        this.projectSpec = projectSpec;
        this.projectStatus = projectStatus;
        this.properties = properties;
        this.tcDirectProjectId = tcDirectProjectId;
        this.tcDirectProjectName = tcDirectProjectName;
    }


    /**
     * Gets the id value for this Project.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this Project.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the projectCategory value for this Project.
     * 
     * @return projectCategory
     */
    public com.liquid.portal.service.ProjectCategory getProjectCategory() {
        return projectCategory;
    }


    /**
     * Sets the projectCategory value for this Project.
     * 
     * @param projectCategory
     */
    public void setProjectCategory(com.liquid.portal.service.ProjectCategory projectCategory) {
        this.projectCategory = projectCategory;
    }


    /**
     * Gets the projectSpec value for this Project.
     * 
     * @return projectSpec
     */
    public com.liquid.portal.service.ProjectSpec getProjectSpec() {
        return projectSpec;
    }


    /**
     * Sets the projectSpec value for this Project.
     * 
     * @param projectSpec
     */
    public void setProjectSpec(com.liquid.portal.service.ProjectSpec projectSpec) {
        this.projectSpec = projectSpec;
    }


    /**
     * Gets the projectStatus value for this Project.
     * 
     * @return projectStatus
     */
    public com.liquid.portal.service.ProjectStatus getProjectStatus() {
        return projectStatus;
    }


    /**
     * Sets the projectStatus value for this Project.
     * 
     * @param projectStatus
     */
    public void setProjectStatus(com.liquid.portal.service.ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }


    /**
     * Gets the properties value for this Project.
     * 
     * @return properties
     */
    public project.management.topcoder.com.ProjectPropertiesEntry[] getProperties() {
        return properties;
    }


    /**
     * Sets the properties value for this Project.
     * 
     * @param properties
     */
    public void setProperties(project.management.topcoder.com.ProjectPropertiesEntry[] properties) {
        this.properties = properties;
    }


    /**
     * Gets the tcDirectProjectId value for this Project.
     * 
     * @return tcDirectProjectId
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }


    /**
     * Sets the tcDirectProjectId value for this Project.
     * 
     * @param tcDirectProjectId
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }


    /**
     * Gets the tcDirectProjectName value for this Project.
     * 
     * @return tcDirectProjectName
     */
    public java.lang.String getTcDirectProjectName() {
        return tcDirectProjectName;
    }


    /**
     * Sets the tcDirectProjectName value for this Project.
     * 
     * @param tcDirectProjectName
     */
    public void setTcDirectProjectName(java.lang.String tcDirectProjectName) {
        this.tcDirectProjectName = tcDirectProjectName;
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
            this.id == other.getId() &&
            ((this.projectCategory==null && other.getProjectCategory()==null) || 
             (this.projectCategory!=null &&
              this.projectCategory.equals(other.getProjectCategory()))) &&
            ((this.projectSpec==null && other.getProjectSpec()==null) || 
             (this.projectSpec!=null &&
              this.projectSpec.equals(other.getProjectSpec()))) &&
            ((this.projectStatus==null && other.getProjectStatus()==null) || 
             (this.projectStatus!=null &&
              this.projectStatus.equals(other.getProjectStatus()))) &&
            ((this.properties==null && other.getProperties()==null) || 
             (this.properties!=null &&
              java.util.Arrays.equals(this.properties, other.getProperties()))) &&
            this.tcDirectProjectId == other.getTcDirectProjectId() &&
            ((this.tcDirectProjectName==null && other.getTcDirectProjectName()==null) || 
             (this.tcDirectProjectName!=null &&
              this.tcDirectProjectName.equals(other.getTcDirectProjectName())));
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
        if (getProjectCategory() != null) {
            _hashCode += getProjectCategory().hashCode();
        }
        if (getProjectSpec() != null) {
            _hashCode += getProjectSpec().hashCode();
        }
        if (getProjectStatus() != null) {
            _hashCode += getProjectStatus().hashCode();
        }
        if (getProperties() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProperties());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProperties(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Long(getTcDirectProjectId()).hashCode();
        if (getTcDirectProjectName() != null) {
            _hashCode += getTcDirectProjectName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Project.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("com.topcoder.management.project", "project"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "projectCategory"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectSpec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectSpec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "projectSpec"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "projectStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "projectStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("properties");
        elemField.setXmlName(new javax.xml.namespace.QName("", "properties"));
        elemField.setXmlType(new javax.xml.namespace.QName("com.topcoder.management.project", ">>project>properties>entry"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "entry"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tcDirectProjectId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tcDirectProjectId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tcDirectProjectName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tcDirectProjectName"));
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
