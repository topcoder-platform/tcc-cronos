package com.topcoder.mobilerssreader.databroker;

import com.topcoder.mobilerssreader.databroker.entity.*;

/**
 * This interface defines methods that persist Entity instances and their associations into persistence layer
 * <p>
 * Thread safety: all methods are synchronized to make there is only one thread to access.
 * </p>
 * 
 * @poseidon-object-id [I45d30951m113c2397497mm5981]
 */
public interface MobileDataBroker {
    /**
     * <p>
     * Store Tag into persistence layer
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm5919]
     * @param instance
     *            the new Tag instance to be stored into this broker, which should not be null
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws InvalidEntityException
     *             if objectId is not valid for this RecordStore, which is out of range
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     */
    public void storeEntity(com.topcoder.mobilerssreader.databroker.entity.Tag instance);

    /**
     * <p>
     * Store RSSSubscription into persistence layer
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm58d4]
     * @param instance
     *            the new RSSSubscription instance to be stored into this broker, which should not be null
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws InvalidEntityException
     *             if objectId is not valid for this RecordStore, which is out of range
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     */
    public void storeEntity(com.topcoder.mobilerssreader.databroker.entity.RSSSubscription instance);

    /**
     * <p>
     * Store RSSFeedContent into persistence layer
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm588f]
     * @param instance
     *            the new RSSFeedContent instance to be stored into this broker, which should not be null
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws InvalidEntityException
     *             if objectId is not valid for this RecordStore, which is out of range
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     */
    public void storeEntity(com.topcoder.mobilerssreader.databroker.entity.RSSFeedContent instance);

    /**
     * <p>
     * Load Entity instance by its EntityId.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm584a]
     * @param id
     *            the EntityId instance to use to load specified Entity, which should not be null
     * @return the loaded Entity from RecordStore, or null if there is no such Entity stored in RecordStore
     * @throws IllegalArgumentException
     *             if id is null
     * @throws EntityTypeNotFoundException
     *             if type id doesn't exist in entityFactories
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     */
    public com.topcoder.mobilerssreader.databroker.entity.Entity loadEntity(
            com.topcoder.mobilerssreader.databroker.entity.EntityId id);

    /**
     * <p>
     * Load a list of Entity. These entities start from startIndex. If its range is 0,&nbsp; less than 0 or exceed the
     * total record number in RecordStore, it will return the entire set.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm57ef]
     * @param typeId
     *            specified type id of loaded entities should be, which should not be null or empty, and should not be
     *            the unregistered type id
     * @param startIndex
     *            the start index of record in RecordStore, which should be between 1 and tha last index of RecordStore
     * @param range
     *            the number of entities to be loaded, if it is 0, less than 0 or exceed the total record number, it
     *            will be set to the total record number
     * @return all satisfied Entity from RecordStore according to typeId
     * @throws IllegalArgumentException,
     *             if typeId is null or empty
     * @throws IndexOutOfBoundsException
     *             if startIndex is out of bounds
     * @throws EntityTypeNotFoundException
     *             if typeId doesn't exist in entityFactories
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     */
    public Entity[] loadEntitiesOfType(String typeId, int startIndex, int range);

    /**
     * <p>
     * Delete Entity by this EntityId.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm574b]
     * @param id
     *            the specified entity id used to delete entity, which should not be null
     * @throws EntityTypeNotFoundException
     *             if typeId doesn't exist in entityFactories
     * @throws EntityNotFoundException
     *             if no specified entity found according to this entity id, and also includes other internal exceptions
     * @throws IllegalArgumentException
     *             if id is null
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     */
    public void deleteEntity(com.topcoder.mobilerssreader.databroker.entity.EntityId id);

    /**
     * <p>
     * Look up all entity ids associated with the specified entity. If no associated entity ids found, return empty
     * array of EntityId.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm5706]
     * @param instance
     *            the Entity instance that has associated entity id, which should not be null
     * @return all entities ids associated with the specified entity
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     * @throws EntityNotFoundException
     *             if entity is not found by the instance
     */
    public EntityId[] getEntityAssociationIds(com.topcoder.mobilerssreader.databroker.entity.Entity instance);

    /**
     * <p>
     * Look up all entities associated with the specified entity. If no associated entities found, return empty array of
     * EntityId.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm56e1]
     * @param instance
     *            the Entity instance that has associated entity id, which should not be null
     * @return all entity associated with the specified entity. The return array may contain null value, means that this
     *         entity is not stored in RecordStore
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     * @throws EntityNotFoundException
     *             if entity is not found by the instance
     */
    public Entity[] getEntityAssociations(com.topcoder.mobilerssreader.databroker.entity.Entity instance);

    /**
     * <p>
     * Look up ids of all entities which have an association on the specified entity.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm5658]
     * @param instance
     *            the Entity instance that is associated by other entity id, which should not be null
     * @return all entity ids that has association with the specified entity
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     */
    public EntityId[] getAssociationEntityIds(com.topcoder.mobilerssreader.databroker.entity.Entity instance);

    /**
     * <p>
     * Look up all entities which have an association on the specified entity.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm55fc]
     * @param instance
     *            the Entity instance that is associated by other entity id, which should not be null
     * @return all entities that has association with the specified entity
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     */
    public Entity[] getAssociationEntities(com.topcoder.mobilerssreader.databroker.entity.Entity instance);

    /**
     * <p>
     * Add association into mobile data broker.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm55aa]
     * @param instance
     *            the Entity instance that has associated entity id, which should not be null
     * @param associatedId
     *            the associated entity id, which should not be null
     * @throws IllegalArgumentException
     *             if instance or associatedId is null
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     */
    public void addAssociation(com.topcoder.mobilerssreader.databroker.entity.Entity instance,
            com.topcoder.mobilerssreader.databroker.entity.EntityId associatedId);

    /**
     * <p>
     * Delete one specified association from data broker. If there is no association of this entity, remove this record
     * in cache and RecordStore.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm5520]
     * @param instance
     *            the Entity instance that has associated entity id, which should not be null
     * @param associateId
     *            the associated entity id, which should not be null
     * @throws IllegalArgumentException
     *             if instance or associatedId is null
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     * @throws EntityNotFoundException
     *             if entity is not found by the instance
     */
    public void deleteAssociation(com.topcoder.mobilerssreader.databroker.entity.Entity instance,
            com.topcoder.mobilerssreader.databroker.entity.EntityId associateId);

    /**
     * <p>
     * Delete all associations of specified entity from data broker. If there is no association of this entity, remove
     * this record in cache and RecordStore.
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm5496]
     * @param instance
     *            the Entity instance that has associated entity id, which should not be null
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     */
    public void deleteAllAssociations(com.topcoder.mobilerssreader.databroker.entity.Entity instance);

    /**
     * <p>
     * Break all associations on to this entity
     * </p>
     * 
     * @poseidon-object-id [I45d30951m113c2397497mm5451]
     * @param instance
     *            the Entity instance that is associated by other entity id, which should not be null
     * @throws IllegalArgumentException
     *             if instance is null
     * @throws StoreFailedException
     *             if any exception related with storage, it should be used to wrap this exception.
     * @throws EntityNotFoundException
     *             if entity is not found by the instance
     */
    public void disassociateEntity(com.topcoder.mobilerssreader.databroker.entity.Entity instance);
}
