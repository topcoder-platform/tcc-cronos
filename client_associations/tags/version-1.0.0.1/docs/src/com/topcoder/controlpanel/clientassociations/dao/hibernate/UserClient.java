package com.topcoder.controlpanel.clientassociations.dao.hibernate;
// Generated 2007-12-8 12:51:43 by Hibernate Tools 3.2.0.CR1



/**
 * UserClient generated by hbm2java
 */
public class UserClient  implements java.io.Serializable {


     private UserClientPK comp_id;
     private Integer adminInd;
     private Client client;

    public UserClient() {
    }

	
    public UserClient(UserClientPK comp_id) {
        this.comp_id = comp_id;
    }
    public UserClient(UserClientPK comp_id, Integer adminInd, Client client) {
       this.comp_id = comp_id;
       this.adminInd = adminInd;
       this.client = client;
    }
   
    public UserClientPK getComp_id() {
        return this.comp_id;
    }
    
    public void setComp_id(UserClientPK comp_id) {
        this.comp_id = comp_id;
    }
    public Integer getAdminInd() {
        return this.adminInd;
    }
    
    public void setAdminInd(Integer adminInd) {
        this.adminInd = adminInd;
    }
    public Client getClient() {
        return this.client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }




}


