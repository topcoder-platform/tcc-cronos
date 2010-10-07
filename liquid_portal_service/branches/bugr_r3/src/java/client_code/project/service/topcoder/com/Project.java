/**
 * Project.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package project.service.topcoder.com;

public class Project  extends com.liquid.portal.service.ProjectData  implements java.io.Serializable {
    private com.liquid.portal.service.Competition[] competitions;

    private java.util.Calendar createDate;

    private java.util.Calendar modifyDate;

    private long userId;

    public Project() {
    }

    public Project(
           java.lang.String description,
           java.lang.String name,
           java.lang.Long projectId,
           com.liquid.portal.service.Competition[] competitions,
           java.util.Calendar createDate,
           java.util.Calendar modifyDate,
           long userId) {
        super(
            description,
            name,
            projectId);
        this.competitions = competitions;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.userId = userId;
    }


    /**
     * Gets the competitions value for this Project.
     * 
     * @return competitions
     */
    public com.liquid.portal.service.Competition[] getCompetitions() {
        return competitions;
    }


    /**
     * Sets the competitions value for this Project.
     * 
     * @param competitions
     */
    public void setCompetitions(com.liquid.portal.service.Competition[] competitions) {
        this.competitions = competitions;
    }

    public com.liquid.portal.service.Competition getCompetitions(int i) {
        return this.competitions[i];
    }

    public void setCompetitions(int i, com.liquid.portal.service.Competition _value) {
        this.competitions[i] = _value;
    }


    /**
     * Gets the createDate value for this Project.
     * 
     * @return createDate
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this Project.
     * 
     * @param createDate
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the modifyDate value for this Project.
     * 
     * @return modifyDate
     */
    public java.util.Calendar getModifyDate() {
        return modifyDate;
    }


    /**
     * Sets the modifyDate value for this Project.
     * 
     * @param modifyDate
     */
    public void setModifyDate(java.util.Calendar modifyDate) {
        this.modifyDate = modifyDate;
    }


    /**
     * Gets the userId value for this Project.
     * 
     * @return userId
     */
    public long getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this Project.
     * 
     * @param userId
     */
    public void setUserId(long userId) {
        this.userId = userId;
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
            ((this.competitions==null && other.getCompetitions()==null) || 
             (this.competitions!=null &&
              java.util.Arrays.equals(this.competitions, other.getCompetitions()))) &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            ((this.modifyDate==null && other.getModifyDate()==null) || 
             (this.modifyDate!=null &&
              this.modifyDate.equals(other.getModifyDate()))) &&
            this.userId == other.getUserId();
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
        if (getCompetitions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCompetitions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCompetitions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        if (getModifyDate() != null) {
            _hashCode += getModifyDate().hashCode();
        }
        _hashCode += new Long(getUserId()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Project.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("com.topcoder.service.project", "project"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("competitions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "competitions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.portal.liquid.com/", "competition"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifyDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modifyDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
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
