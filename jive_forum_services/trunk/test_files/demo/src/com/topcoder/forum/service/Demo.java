/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import com.topcoder.forum.service.ejb.JiveForumServiceRemote;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;


/**
 * <p>
 * Demo of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo {
    /**
     * <p>
     * Demo of this component.
     * </p>
     *
     * @param args the command line arguments
     * @throws Exception to JUnit.
     */
    public static void main(String[] args) throws Exception {
        /*
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        props.setProperty("java.naming.provider.url", "localhost:1099");
        props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
        
        InitialContext ctx  = new InitialContext(props);
        */
        InitialContext ctx = new InitialContext();

        JiveForumServiceRemote bean = (JiveForumServiceRemote) ctx.lookup(
                "JiveForumDemo/JiveForumService/remote");

        long userId = 1;
        long entityId = 1;

        // The user (identified by userId) watches the forum (identified by entityId)
        bean.watch(userId, entityId, EntityType.FORUM);

        // check whether the user is watching the forum or not. True should be returned
        boolean watched = bean.isWatched(userId, entityId, EntityType.FORUM);
        System.out.println("Watched: " + watched);

        long categoryId = 1;

        // set moderator role to the user (identified by userId) for the
        // category (identified by categoryId)
        bean.setUserRole(userId, categoryId, UserRole.MODERATOR);

        // get user role. UserRole.MODERATOR is expected.
        UserRole role = bean.getUserRole(userId, categoryId);
        System.out.println("The role: " + role);

        // create CategoryConfiguration with templateCategoryId set
        CategoryConfiguration categoryConfig = new CategoryConfiguration();
        categoryConfig.setName("Test");
        categoryConfig.setDescription("Test Desc");
        categoryConfig.setRootCategoryId(1);
        categoryConfig.setComponentId(1);
        categoryConfig.setVersionText("v1.0");
        categoryConfig.setVersionId(1);
        categoryConfig.setTemplateCategoryId(3);

        // create category. Contents from template category will be copied into it
        long newCatId = bean.createCategory(categoryConfig);

        // change CategoryConfiguration to use the templateCategoryType
        categoryConfig.setTemplateCategoryId(-1);
        categoryConfig.setTemplateCategoryType(CategoryType.COMPONENT);

        // create category. Contents from the component template category will
        // be copied into it.
        newCatId = bean.createCategory(categoryConfig);

        // Use JiveForumManager to operate the service
        Map<CategoryType, Long> categoryTemplateIds = new HashMap<CategoryType, Long>();
        categoryTemplateIds.put(CategoryType.APPLICATION, new Long(3));
        categoryTemplateIds.put(CategoryType.COMPONENT, new Long(3));

        long adminUserId = 1;
        JiveForumManager manager = new JiveForumManager(adminUserId,
                categoryTemplateIds);

        //Create the category
        categoryConfig.setTemplateCategoryType(CategoryType.APPLICATION);
        newCatId = manager.createCategory(categoryConfig);
    }
}
