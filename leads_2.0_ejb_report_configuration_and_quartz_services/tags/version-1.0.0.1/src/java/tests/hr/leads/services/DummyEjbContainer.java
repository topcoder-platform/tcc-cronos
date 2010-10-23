/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * <p>
 * A mock up class represents the ejb resource.
 * </p>
 *
 * @author TCSDEVERLOPER
 * @version 1.0
 */
class EjbResource {
    /**
     * <p>
     * Represents the resource name.
     * </p>
     */
    private String resourceName;

    /**
     * <p>
     * Represents the resource type.
     * </p>
     */
    private String resourceType;

    /**
     * <p>
     * Represents the resource value.
     * </p>
     */
    private String resourceValue;

    /**
     * <p>
     * Creates an instance of ejb resource.
     * </p>
     */
    public EjbResource() {
        // do nothing
    }

    /**
     * <p>
     * Gets the resource name.
     * </p>
     *
     * @return the resource name.
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * <p>
     * Sets the resource name.
     * </p>
     * @param resourceName the resource name.
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * <p>
     * Gets the resource type.
     * </p>
     * @return the resource type.
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * <p>
     * Sets the resource type.
     * </p>
     *
     * @param resourceType the resource type.
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * <p>
     * Gets the resource value.
     * </p>
     * @return the resource value.
     */
    public String getResourceValue() {
        return resourceValue;
    }

    /**
     * <p>
     * Sets the the resource value.
     * </p>
     * @param resourceValue the resource value.
     */
    public void setResourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
    }
}

/**
 * <p>
 * A class to represents a ejb that other ejb references to.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
class EjbReference {

    /**
     * <p>
     * Represents to the ejb name.
     * </p>
     */
    private String ejbName;

    /**
     * <p>
     * Creates the ejb reference.
     * </p>
     */
    public EjbReference() {
        // do nothing
    }
    /**
     * <p>
     * Gets the ejb name.
     * </p>
     * @return the ejb name.
     */
    public String getEjbName() {
        return ejbName;
    }

    /**
     * <p>
     * Sets the ejb name.
     * </p>
     * @param ejbName the ejb name to set.
     */
    public void setEjbName(String ejbName) {
        this.ejbName = ejbName;
    }
}

/**
 * <p>
 * A class represents the configuration of a ejb.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
class EjbInfo {

    /**
     * <p>
     * Represents the name of a ejb.
     * </p>
     */
    private String ejbName;

    /**
     * <p>
     * Represents the name of the ejb class.
     * </p>
     */
    private String ejbClass;

    /**
     * <p>
     * Represents a list of ejb resources.
     * </p>
     */
    private List<EjbResource> ejbResources;

    /**
     * <p>
     * Represents a list of ejb references.
     * </p>
     */
    private List<EjbReference> ejbReferences;

    /**
     * <p>
     * Creates an instance of ejb info.
     * </p>
     */
    public EjbInfo() {
       // do nothing
    }

    /**
     * <p>
     * Gets the name of the ejb.
     * </p>
     * @return the name of the ejb.
     */
    public String getEjbName() {
        return ejbName;
    }

    /**
     * <p>
     * Sets the name of the ejb.
     * </p>
     * @param ejbName the name of ejb.
     */
    public void setEjbName(String ejbName) {
        this.ejbName = ejbName;
    }

    /**
     * <p>
     * Gets the name of the ejb class.
     * </p>
     *
     * @return the name of the ejb class.
     */
    public String getEjbClass() {
        return ejbClass;
    }

    /**
     * <p>
     * Sets the name of the ejb class.
     * </p>
     *
     * @param ejbClass the name of the ejb class.
     */
    public void setEjbClass(String ejbClass) {
        this.ejbClass = ejbClass;
    }

    /**
     * <p>
     * Gets the ejb resources.
     * </p>
     *
     * @return the ejb resources.
     */
    public List<EjbResource> getEjbResources() {
        return ejbResources;
    }

    /**
     * <p>
     * Sets the ejb resources.
     * </p>
     * @param ejbResources the ejb resources to set.
     */
    public void setEjbResources(List<EjbResource> ejbResources) {
        this.ejbResources = ejbResources;
    }

    /**
     * <p>
     * Gets the ejb references.
     * </p>
     *
     * @return the ejb references.
     */
    public List<EjbReference> getEjbReferences() {
        return ejbReferences;
    }

    /**
     * <p>
     * Gets the ejb references.
     * </p>
     * @param ejbReferences the ejb references.
     */
    public void setEjbReferences(List<EjbReference> ejbReferences) {
        this.ejbReferences = ejbReferences;
    }
}

/**
 * <p>
 * A class represents a dummy ejb container for unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class DummyEjbContainer {

    /**
     * <p>
     * Represents the mapping from the name to the configuration.
     * </p>
     */
    private Map<String, EjbInfo> ejbMap;

    /**
     * <p>
     * Represents the entity manager factory.
     * </p>
     */
    private EntityManagerFactory entityManagerFactory = null;

    /**
     * <p>
     * Represents the entity manager.
     * </p>
     */
    private EntityManager entityManager = null;

    /**
     * <p>
     * Represents the mapping from the name to the instance.
     * </p>
     */
    private Map<String, Object> instanceMap;

    /**
     * <p>
     * Creates a instance of dummy ejb container.
     * </p>
     */
    public DummyEjbContainer() {
        ejbMap = new HashMap<String, EjbInfo>();
        instanceMap = new HashMap<String, Object>();
    }

    /**
     * <p>
     * Gets the entity manager.
     * </p>
     * @return the entity manager.
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * <p>
     * Gets the ejb via its name.
     * </p>
     * @param ejbName the name of the ejb.
     * @return the object of the name.
     *
     * @throws Exception to JUnit.
     */
    public Object getEjb(String ejbName) throws Exception {

        EjbInfo info = ejbMap.get(ejbName);

        Object instance = instanceMap.get(ejbName);
        if (instance == null) {
            instance = Class.forName(info.getEjbClass()).newInstance();
            instanceMap.put(ejbName, instance);
        }

        // inject from set methods
        Method[] methods = getAllMethods(instance.getClass());
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getName().equals(
                        "javax.annotation.Resource")) {
                    String resouceName = ((javax.annotation.Resource) annotation)
                            .name();
                    for (EjbResource resource : info.getEjbResources()) {
                        if (resource.getResourceName().equals(resouceName)) {
                            String resourceType = resource.getResourceType();
                            String resourceValue = resource.getResourceValue();
                            Object obj = Class.forName(resourceType)
                                    .getConstructor(String.class).newInstance(
                                            new Object[] {resourceValue});

                            // inject the resource
                            method.invoke(instance, new Object[] {obj});
                        }
                    }
                } else if (annotation.annotationType().getName().equals(
                        "javax.persistence.PersistenceContext")) {
                    method
                            .invoke(instance,
                                    new Object[] {getEntityManager()});
                } else if (annotation.annotationType().getName().equals(
                        "javax.ejb.EJB")) {
                    // inject the ejb
                    Object obj = instanceMap.get(((javax.ejb.EJB) annotation).name());
                    if (obj == null) {
                        obj = getEjb(((javax.ejb.EJB) annotation).name());
                    }
                    method.invoke(instance, new Object[] {obj});
                }
            }
        }

        Field[] fields = getAllFields(instance.getClass());

        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getName().equals(
                        "javax.annotation.Resource")) {
                    String resouceName = ((javax.annotation.Resource) annotation)
                            .name();
                    if (resouceName == null || resouceName.trim().length() == 0) {
                        resouceName = field.getName();
                    }

                    for (EjbResource resource : info.getEjbResources()) {
                        if (resource.getResourceName().equals(resouceName)) {
                            String resourceType = resource.getResourceType();
                            String resourceValue = resource.getResourceValue();
                            Object obj = Class.forName(resourceType)
                                    .getConstructor(String.class).newInstance(
                                            new Object[] {resourceValue});

                            // inject the resource
                            // method.invoke(instance, new Object[] {obj});
                            field.setAccessible(true);
                            field.set(instance, obj);
                        }
                    }
                } else if (annotation.annotationType().getName().equals(
                        "javax.persistence.PersistenceContext")) {
                    // method.invoke(instance, new Object[]
                    // {getEntityManager()});
                    field.setAccessible(true);
                    field.set(instance, getEntityManager());
                } else if (annotation.annotationType().getName().equals(
                        "javax.ejb.EJB")) {
                    // inject the ejb
                    Object obj = instanceMap.get(((javax.ejb.EJB) annotation)
                            .name());
                    if (obj == null) {
                        obj = getEjb(((javax.ejb.EJB) annotation).name());
                    }
                    // method.invoke(instance, new Object[] { obj });
                    field.setAccessible(true);
                    field.set(instance, obj);
                }
            }
        }


        // invoke the post construct method
        Method[] mymethods = instance.getClass().getDeclaredMethods();
        for (Method method : mymethods) {
            if (method.getAnnotation(PostConstruct.class) != null) {
                method.setAccessible(true);
                method.invoke(instance, new Object[] {});
            }
        }
        return instance;
    }

    /**
     * <p>
     * Sets the configuration map to container.
     * </p>
     * @param ejbMap the configuration map.
     * @throws Exception to JUnit.
     */
    public void setEjbMap(Map<String, EjbInfo> ejbMap) throws Exception {
        this.ejbMap = ejbMap;
    }


    /**
     * <p>
     * Gets all of the declared methods of a class.
     * </p>
     * @param clazz the class type.
     * @return all of the declared methods of a class.
     */
    @SuppressWarnings("unchecked")
    private Method[] getAllMethods(Class clazz) {
        List<Method> methods = new ArrayList<Method>();
        while (clazz != null) {
            Method[] currentMethods = clazz.getDeclaredMethods();
            for (Method method : currentMethods) {
                methods.add(method);
            }
            clazz = clazz.getSuperclass();
        }
        return (Method[]) methods.toArray(new Method[0]);
    }

    /**
     * <p>
     * Gets all declared fields of a class.
     * </p>
     * @param clazz the class type.
     * @return all of the declared fields of a class.
     */
    @SuppressWarnings("unchecked")
    private Field[] getAllFields(Class clazz) {
        List<Field> fields = new ArrayList<Field>();
        while (clazz != null) {
            Field[] currentFields = clazz.getDeclaredFields();
            for (Field field : currentFields) {
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return (Field[]) fields.toArray(new Field[0]);
    }

    /**
     * <p>
     * Opens a new session with a persistent unit name.
     * </p>
     * @param unitName the persistent unit name.
     */
    public void openSession(String unitName) {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(unitName);
        }
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * <p>
     * Close the current session.
     * </p>
     */
    public void closeSession() {

        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            entityManager = null;
        }

    }
}
