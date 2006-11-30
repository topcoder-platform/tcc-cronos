/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

/**
 * <p>
 * The interface for classes that are responsible for converting a value object
 * into a corresponding serializable data transfer object, and vice-versa. The
 * value object is usually used outside the component, while the data transfer
 * object is used within the component to transfer information between EJB
 * clients and the DAO classes. The data types of the value and data transfer
 * objects supported depends entirely on the implementation class.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br>
 * It is recommended that implementations be made thread-safe. However, this is
 * not required, as the component will still use it in a thread-safe manner.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 */
public interface ObjectTranslator {

    /**
     * <p>
     * Assembles and returns a value object corresponding to the given data
     * transfer object. The value object is used outside this component, while
     * the data transfer object is a serializable representation of the value
     * object that is used to transfer information between the EJB client and
     * the DAO classes.
     * </p>
     * <p>
     * If the data type of the data transfer object is not supported by the
     * <code>ObjectTranslator</code> implementation, an
     * <code>IllegalArgumentException</code> is thrown. If assembling the
     * value object from the data transfer object fails, a
     * <code>TranslationException</code> is thrown.
     * </p>
     *
     * @param dataTransferObject the data transfer object from which to assemble
     *        the corresponding value object
     * @return the value object corresponding to the given data transfer object
     * @throws IllegalArgumentException if the given data transfer object is
     *         <code>null</code>, or is not of the type supported by the
     *         <code>ObjectTranslator</code> implementation
     * @throws TranslationException if assembling the value object from the
     *         given data transfer object fails
     * @see #assembleDTO(Object)
     */
    public Object assembleVO(Object dataTransferObject) throws TranslationException;

    /**
     * <p>
     * Assembles and returns a data transfer object corresponding to the given
     * value object. The value object is used outside this component, while the
     * data transfer object is a serializable representation of the value object
     * that is used to transfer information between the EJB client and the DAO
     * classes.
     * </p>
     * <p>
     * If the data type of the value object is not supported by the
     * <code>ObjectTranslator</code> implementation, an
     * <code>IllegalArgumentException</code> is thrown. If assembling the data
     * transfer object from the value object fails, a
     * <code>TranslationException</code> is thrown.
     * </p>
     *
     * @param valueObject the value object from which to assemble the
     *        corresponding data transfer object
     * @return the data transfer object corresponding to the given value object
     * @throws IllegalArgumentException if the given value object is
     *         <code>null</code>, or is not of the type supported by the
     *         <code>ObjectTranslator</code> implementation
     * @throws TranslationException if assembling the data transfer object from
     *         the given value object fails
     * @see #assembleVO(Object)
     */
    public Object assembleDTO(Object valueObject) throws TranslationException;

}
