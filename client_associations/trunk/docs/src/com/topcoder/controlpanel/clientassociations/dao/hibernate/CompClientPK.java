package com.topcoder.controlpanel.clientassociations.dao.hibernate;
// Generated 2007-12-8 12:51:43 by Hibernate Tools 3.2.0.CR1



/**
 * CompClientPK generated by hbm2java
 */
public class CompClientPK  implements java.io.Serializable {


     private Long componentId;
     private Integer clientId;

    public CompClientPK() {
    }

    public CompClientPK(Long componentId, Integer clientId) {
       this.componentId = componentId;
       this.clientId = clientId;
    }
   
    public Long getComponentId() {
        return this.componentId;
    }
    
    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }
    public Integer getClientId() {
        return this.clientId;
    }
    
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }




}


