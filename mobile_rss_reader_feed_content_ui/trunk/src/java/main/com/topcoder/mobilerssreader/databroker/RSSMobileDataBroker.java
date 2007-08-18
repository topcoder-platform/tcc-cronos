
package com.topcoder.mobilerssreader.databroker;
import java.util.Hashtable;

import javax.microedition.rms.RecordStore;

import com.topcoder.mobilerssreader.databroker.entity.Entity;
import com.topcoder.mobilerssreader.databroker.entity.EntityId;

/**
 * <p>This class is the main class, which implements MobileDataBroker interface, and deals with actual operations between Entity and RecordStore. It takes role to persist Entity instance and associations between Entities.</p>
 * <p>It works in this way:<br/>
 *  1. create instance of MobileDataBroker with registered entity factories[Only entity that its factory is registered into the MobileDataBroker can be stored/loaded/deleted].</p>
 * <p>2. user do some persistence operations on Entity and associations between Entities.</p>
 * <p>Considering the number of associations between Entities may be more than the number of Entities, this class has another cache way to deal with this situation. This way consume more memory, but have faster response speed</p>
 * <p>1. create instance of MobileDataBroker with registered entity factories.</p>
 * <p>2, Call openCache() to open cache mechanism.</p>
 * <p>3. user do some persistence operations on Entity and associations between Entities.</p>
 * <p>4. Call closeCache() to write cached associations into related RecordStore.[the name of RecordStore is the typeid plus &quot;association&quot;]</p>
 * <p>This class also provides ways to store/load application configuration data by calling &quot;storeConfigData&quot;/&quot;loadConfigData&quot;. The configuration data is stored as key-value string pairs.</p>
 * <p></p>
 * <p>Thread safety: all methods should be synchronized on modified member to keep only one thread modified the same RecordStore. This class manage some hashtables, it can synchronize operations on the data structure, but it can't control hashtable's element modification outside. Hence, it is not thread safe.</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm68ef]
 */
public class RSSMobileDataBroker implements com.topcoder.mobilerssreader.databroker.MobileDataBroker {

/**
 * <p>Represents registered factories in this broker, which are used to create related Entity objects from record in RMS storage.</p>
 * <p>It is initialized as new empty Hashtable in constructor. Its key is typeId defined in every Entity sub class, and its value is instance of EntityFactory sub class. Both key and value should not be null. typeId should not be empty.</p>
 * <p>A new entity factory can be registered by &quot;registerEntityFactory&quot; method, and unregistered by &quot;unregisterEntityFactory&quot; method.</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm683b]
 */
    private final Hashtable entityFactories = new Hashtable();

/**
 * <p>Represents whether Mobile Data Broker use cache mechanism or not. If isCached is true, all operations related to association between entities are done in member &quot;associations&quot; hashtable member, then write associations into RMS storage in &quot;closeCache&quot; method. Otherwise, all operations related to association between entities communicate with RMS storage directly.</p>
 * <p>It is private and mutable member, and initialized to be false in constructor. It is set true in &quot;openCache&quot; and false in &quot;closeCache&quot;.</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm67af]
 */
    private boolean isCached = false;

/**
 * <p>Represent cache of associations. It is used when member &quot;isCached&quot; set true in all operations related to association.</p>
 * <p>It is immutable hashtable and initialized to be new empty hashtable in constructor. Its element's key is EntityId, and its element's value is a Vector of associated EntityId(s). The key should not be null. The value should not be null and its element should not be null.</p>
 * <p>In &quot;get&quot; association operations, if member &quot;associations&quot; contains it, return directly. Otherwise, read from RMS storage, add it into member &quot;associations&quot;, and return.</p>
 * <p>In &quot;add&quot; association operation, if member &quot;associations&quot; contains it, add it to related vector and write into RMS storage. Otherwise, create new element, put it into member &quot;associations&quot;, and write into RMS storage.</p>
 * <p>In &quot;delete&quot; association operation, if member &quot;associations&quot; contains it, delete it from related vector, if vector's size is 0, remove it, then reset into RMS storage. Otherwise, throw exception.</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm677c]
 */
    private final java.util.Hashtable associations = new Hashtable();

/**
 * <p>Represents general application configuration data. It is a hashtable.The key is the configuration data's key string, the value is the Integer value of index of RecordStore named &quot;config&quot;</p>
 * <p>It is initialized in constructor by loading all records from &quot;config&quot; RecordStore. Its key is String value, should not be null or empty. Its value is Integer value, should not be null</p>
 * <p>A new config data is stored by calling method &quot;storeConfigData&quot;. To load specify config data is to call &quot;loadConfigData&quot; method.</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm50db]
 */
    private final java.util.Hashtable configuration = new Hashtable();

/**
 * <p>Constructor with specified hashtable of &lt;type id,entity factories&gt; pairs.</p>
 * <p>Implementation:</p>
 * <p>1. For every pair of type id and factory, call entityFactories.put(typeIds[i], factories[i]).</p>
 * <p>2. &nbsp;Load all records from &quot;config&quot; RecordStore, and put their &lt;key, recordIndex&gt; pair into configuration member.</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6812]
 * @param entityFactories hashtable that contains type id and its related entity factory instance. Its element should not be null
 * @throws IllegalArgumentException if entityFactories is null, its element is null.
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public  RSSMobileDataBroker(java.util.Hashtable entityFactories) {        
        // your code here
    } 

/**
 * <p>Default constructor. It will add RSSSubscriptionFactory, RSSFeedContentFactory, TagFactory into entityFactories.</p>
 * <p>Implementation:</p>
 * <p>1. create new instance of RSSFeedContentFactory, add it into entityFactories. [entityFactories.put(RSSFeedContent.TYPEID, new RSSFeedContentFactory())]</p>
 * <p>2. create new instance of RSSSubscriptionFactory, add it into entityFactories. [entityFactories.put(RSSSubscription.TYPEID, new RSSSubscriptionFactory())]</p>
 * <p>3. create new instance of TagFactory, add it into entityFactories. [entityFactories.put(Tag.TYPEID, new TagFactory())]</p>
 * <p>4. load all records from &quot;config&quot; RecordStore, and put their &lt;key, recordIndex&gt; pair into configuration member.</p>
 * <p>.</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6778]
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public  RSSMobileDataBroker() {        
        // your code here
    } 

/**
 * <p>Register new entity factory into this broker. If typeId exists before, the value will be replaced by new one.</p>
 * <p>Implementation:</p>
 * <p>1. synchronized on entityFactories, call entityFactories.put(typeId, factory)</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6756]
 * @param typeId specified type id of new entity factory, which should not be null or empty
 * @param factory specified entity factory to create new entity of this type, which should not be null
 * @throws IllegalArgumentException if typeId is null or empty, factory is null
 */
    public void registerEntityFactory(String typeId, com.topcoder.mobilerssreader.databroker.factory.EntityFactory factory) {        
        // your code here
    } 

/**
 * <p>Unregister entity factory from this broker. If the specified type id is not in the broker, it does nothing.</p>
 * <p>Implementation:</p>
 * <p>1. synchronized on entityFactories, call entityFactories.remove(typeId).</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm66b6]
 * @param typeId specified type id to remove entity factory in this broker, which should not be null or empty
 * @throws IllegalArgumentException if typeId is null or empty.
 */
    public void unregisterEntityFactory(String typeId) {        
        // your code here
    } 

/**
 * <p>Store Tag into RMS storage. The new instance of Tag will be stored into its RecordStore according to its type id. </p>
 * <p>It is defined as a synchronized method to make only one thread can access at a time.</p>
 * <p>Implementation:</p>
 * <p>1. Call storeEntity((Entity)instance).</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6648]
 * @param instance the new Tag instance to be stored into this broker, which should not be null
 * @throws IllegalArgumentException if instance is null
 * @throws InvalidEntityException if objectId is not valid for this RecordStore, which is out of range
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public synchronized void storeEntity(com.topcoder.mobilerssreader.databroker.entity.Tag instance) {        
        // your code here
    } 

/**
 * <p>Store RSSSubscription into RMS storage. The new instance of&nbsp; RSSSubscription will be stored into its RecordStore according to its type id.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1. Call storeEntity((Entity)instance).</p>
 * <p></p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm65ea]
 * @param instance the new RSSSubscription instance to be stored into this broker, which should not be null
 * @throws IllegalArgumentException if instance is null
 * @throws InvalidEntityException if objectId is not valid for this RecordStore, which is out of range
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public synchronized void storeEntity(com.topcoder.mobilerssreader.databroker.entity.RSSSubscription instance) {        
        // your code here
    } 

/**
 * <p>Store RSSFeedContent into RMS storage. The new instance of&nbsp; RSSFeedContent will be stored into its RecordStore according to its type id.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1. Call storeEntity((Entity)instance).</p>
 * <p></p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm65a5]
 * @param instance the new RSSFeedContent instance to be stored into this broker, which should not be null
 * @throws IllegalArgumentException if instance is null
 * @throws InvalidEntityException if objectId is not valid for this RecordStore, which is out of range
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public synchronized void storeEntity(com.topcoder.mobilerssreader.databroker.entity.RSSFeedContent instance) {        
        // your code here
    } 

/**
 * <p>Store entity into its related RMS storage according to its type id.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1. Open RecordStore by type id of this entity. If it is the first time to store entity of this type, create new RecordStore. [RecordStore rs = RecordStore.openRecordStore(instance. getTypeId()), true]</p>
 * <p>2. Get byte array of this entity instance. [byte[] content = instance.getBytes()].</p>
 * <p>3. Get object id from this entity. [String objectId = instance.getObjectId().]</p>
 * <p>3. If objectId is null, that is new instance, get the next record index and set instance's objectId to be the index. Then get byte array of this entity instance. [byte[] content = instance.getBytes()]. At last, add this instance into the end of rs.[rs.addRecord(content, 0, content.length())]</p>
 * <p>4. Otherwise if objectId is between 1 and current max index of the rs, set this instance into this rs at its objectId position.. <br/>
 *  [byte[] content = instance.getBytes(); rs.setRecord(objectId, content, 0, content.length);]. If objectId is out of such bounds, throw InvalidEntityException.</p>
 * <p>5. Close rs.</p>
 * <p>Note: in this method, any exception related with storage should be wrapped in StoreFailedException.</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm6560]
 * @param instance the entity instance to be stored into this broker, which should not be null
 * @throws IllegalArgumentException if instance is null
 * @throws InvalidEntityException if objectId is not valid for this RecordStore, which is out of range
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    protected synchronized void storeEntity(com.topcoder.mobilerssreader.databroker.entity.Entity instance) {        
        // your code here
    } 

/**
 * <p>Load Entity instance by its EntityId. It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1.If id.getTypeId() doesn't exist in entityFactories, throw EntityTypeNotFoundException</p>
 * <p>2. Open RecordStore by typeId.[RecordStore rs = RecordStore.openRecordStore(id.getTypeId(), true)].</p>
 * <p>3. Get entity factory according to type id.[AbstractFactory factory = entityFactories.get(id.getTypeId());]</p>
 * <p>4. Get record from RecordStore according to id.getObjectId(). If the return is not null, create Entity instance from factory.<br/>
 *  [byte[] record = rs.getRecord(id.getObjectId()); <br/>
 *  Entity loadedEntity = factory.createEntity( id.getObjectId(), record);]</p>
 * <p>5. return loadEntity</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm651b]
 * @param id the EntityId instance to use to load specified Entity, which should not be null
 * @return the loaded Entity from RecordStore, or null if there is no such Entity stored in RecordStore
 * @throws IllegalArgumentException if id is null
 * @throws EntityTypeNotFoundException if type id doesn't exist in entityFactories
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public synchronized com.topcoder.mobilerssreader.databroker.entity.Entity loadEntity(com.topcoder.mobilerssreader.databroker.entity.EntityId id) {        
        // your code here
        return null;
    } 

/**
 * <p>Load a list of Entity. These entities start from startIndex. If its range is 0,&nbsp; less than 0 or exceed the total record number in RecordStore, it will return the entire set.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>0. If typeId doesn't exist in entityFactories, throw EntityTypeNotFoundException</p>
 * <p>1. Open RecordStore by typeId.[RecordStore rs = RecordStore.openRecordStore(typeId, true)]</p>
 * <p>2. If range is 0, less than 0, or exceed the total record number in RecordStore, set it to be the total record number in RecordStore.</p>
 * <p>3. Get entity factory according to type id.[AbstractFactory factory = entityFactories.get(typeId);]</p>
 * <p>4. Start from startIndex, and count until range. Get each record i from RecordStore and create Entity instance from factory.<br/>
 *  [byte[] record = rs.getRecord(i); <br/>
 *  Entity loadedEntity = factory.createEntity(i, record);]</p>
 * <p>5. Put these loadedEntity into an array, return it.</p>
 * 
 * @poseidon-object-id [I7ff0f7d9m1138469cc34mm64d6]
 * @param typeId specified type id of loaded entities should be, which should not be null or empty, and should not be the unregistered type id
 * @param startIndex the start index of record in RecordStore, which should be between 1 and tha last index of RecordStore
 * @param range the number of entities to be loaded, if it is 0, less than 0 or exceed the total record number, it will be set to the total record number
 * @return all satisfied Entity from RecordStore according to typeId
 * @throws IllegalArgumentException, if typeId is null or empty
 * @throws IndexOutOfBoundsException if startIndex is out of bounds
 * @throws EntityTypeNotFoundException if typeId doesn't exist in entityFactories
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public synchronized Entity[] loadEntitiesOfType(String typeId, int startIndex, int range) {        
        // your code here
        return null;
    } 

/**
 * <p>Delete Entity by this EntityId.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1.If id.getTypeId() doesn't exist in entityFactories, throw EntityTypeNotFoundException</p>
 * <p>2. Open RecordStore by typeId.[RecordStore rs = RecordStore.openRecordStore(id.getTypeId(), true)].</p>
 * <p>3. If id.getObjectId is between 1 and the next record id, call rs.deleteRecord(id.getObjectId). Otherwise, throw EntityNotFoundException.</p>
 * <p>4. Close rs.</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im24c05902m1138bc1c825mm678e]
 * @param id the specified entity id used to delete entity, which should not be null
 * @throws EntityTypeNotFoundException if typeId doesn't exist in entityFactories
 * @throws EntityNotFoundException if no specified entity found according to this entity id, and also includes other internal exceptions
 * @throws IllegalArgumentException if id is null
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public synchronized void deleteEntity(com.topcoder.mobilerssreader.databroker.entity.EntityId id) {        
        // your code here
    } 

/**
 * <p>Look up all entity ids associated with the specified entity. If no associated entity ids found, return empty array of EntityId.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1. Create new EntityId from instance. [EntityId entityId = new EntityId(instance.getObjectId(), instance.getTypeId())]</p>
 * <p>2. If cache is open, retrieve associations by entityId.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 2.a &nbsp;If entityId.getObjectId is null or entityId is not any key of &quot;associations&quot;, return empty EntityId array.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 2.b If entityId is contained in keys of member &quot;associations&quot;, get vector of associated EntityId(s). Return these EntityId(s) in an EntityId array.</p>
 * <p>3. If cache is not open,&nbsp;it will be fetched from RMS storage directly and return.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.a Open RecordStore by entityId.getTypeId[RecordStore rs = RecordStore.openRecordStore(entityId.getTypeId()+ &quot;association&quot;, true)]</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.b If objectId of entityId is null or not in 1 and next record id, set empty EntityId array. Otherwise, Get byte array of entityId and associateId from record, parse these associateId into EntityId array.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.c close rs.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.d return EntityId array</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6afd]
 * @param instance the Entity instance that has associated entity id, which should not be null
 * @return all entities ids associated with the specified entity
 * @throws IllegalArgumentException if instance is null
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 * @throws EntityNotFoundException if entity is not found by the instance
 */
    public synchronized EntityId[] getEntityAssociationIds(com.topcoder.mobilerssreader.databroker.entity.Entity instance) {        
        // your code here
        return null;
    } 

/**
 * <p>Look up all entities associated with the specified entity. If no associated entities found, return empty array of EntityId.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1. Call EntityId[] entityIds = getEntityAssociationIds(instance);</p>
 * <p>2. &nbsp;If entityIds is empty, return empty Entity array. Iterate entity id in entityIds, call loadEntity(id). Then return this Entity array</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6aab]
 * @param instance the Entity instance that has associated entity id, which should not be null
 * @return all entity associated with the specified entity. The return array may contain null value, means that this entity is not stored in RecordStore
 * @throws IllegalArgumentException if instance is null
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 * @throws EntityNotFoundException if entity is not found by the instance
 */
    public synchronized Entity[] getEntityAssociations(com.topcoder.mobilerssreader.databroker.entity.Entity instance) {        
        // your code here
        return null;
    } 

/**
 * <p>Look up ids of all entities which have an association on the specified entity.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>1. Create new EntityId from instance. [EntityId entityId = new EntityId(instance.getObjectId(), instance.getTypeId())]</p>
 * <p>2. If cache is open, iterate all associations in member &quot;associations&quot;. For each association,&nbsp;if its value vector contains entityId, put the key into return EntityId array. At last, return EntityId array..</p>
 * <p>3. If cache is not open,&nbsp;fetch all associations from all RecordStore, which are refered by registered entity type in &quot;entityFactories&quot;. For each RecordStore.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.a Open RecordStore by entityId.getTypeId[RecordStore rs = RecordStore.openRecordStore(typeId + &quot;association&quot;, true)]</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.b Iterate all records in this RecordStore. Get byte array of entityId and associateId(s) from record and parse these associateId(s) into EntityId objects. If the &quot;input entityId&quot; is one of them, put the &quot;entityId of record&quot; into the return EntityId array.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.c close rs.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.d return EntityId array</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6a86]
 * @param instance the Entity instance that is associated by other entity id, which should not be null
 * @return all entity ids that has association with the specified entity
 * @throws IllegalArgumentException if instance is null
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 * @throws new value
 */
    public synchronized EntityId[] getAssociationEntityIds(com.topcoder.mobilerssreader.databroker.entity.Entity instance) {        
        // your code here
        return null;
    } 

/**
 * <p>Look up all entities which have an association on the specified entity.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1. Call EntityId[] entityIds = getAssociationEntityIds(instance);</p>
 * <p>2. &nbsp;If entityIds is empty, return empty Entity array. Iterate entity id in entityIds, call loadEntity(id). Then return this Entity array</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6a61]
 * @param instance the Entity instance that is associated by other entity id, which should not be null
 * @return all entities that has association with the specified entity
 * @throws IllegalArgumentException if instance is null
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 * @throws new value
 */
    public synchronized Entity[] getAssociationEntities(com.topcoder.mobilerssreader.databroker.entity.Entity instance) {        
        // your code here
        return null;
    } 

/**
 * <p>Add association into mobile data broker.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1. Create new EntityId from instance. [EntityId entityId = new EntityId(instance.getObjectId(), instance.getTypeId())]</p>
 * <p>2. If cache is open, the association will be cached first and return.</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.a If entityId.getObjectId is null or entityId is not any key of &quot;associations&quot;, put the new association into associations.</p>
 * <p>&nbsp;&nbsp; 2.b Otherwise, there has been associations with entityId. Hence, retrieve vector of this entityId and put new associatedId into the vector.</p>
 * <p>&nbsp;&nbsp; 2.c return.</p>
 * <p>3. If cache is not open, it will be written into RMS storage directly and return.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.a Open RecordStore by entityId.getTypeId[RecordStore rs = RecordStore.openRecordStore(entityId.getTypeId() +&quot;association&quot;, true)]</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.b If objectId of entityId is null or not in 1 and next record id, set objectId to the next record id. Get byte array of entityId and associatedId[like: entityId.getBytes()+associatedId.getBytes()], add it to the end of RecordStore.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.c Otherwise, the entityId has been associated with other entityId(s). Get record byte size, append associatedId.getBytes to the end of this record.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.d close rs.</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm697d]
 * @param instance the Entity instance that has associated entity id, which should not be null
 * @param associateId the associated entity id, which should not be null
 * @throws IllegalArgumentException if instance or associatedId is null
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 * @throws new value
 */
    public synchronized void addAssociation(com.topcoder.mobilerssreader.databroker.entity.Entity instance, com.topcoder.mobilerssreader.databroker.entity.EntityId associateId) {        
        // your code here
    } 

/**
 * <p>Delete one specified association from data broker. If there is no association of this entity, remove this record in cache and RecordStore.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1. Create new EntityId from instance. [EntityId entityId = new EntityId(instance.getObjectId(), instance.getTypeId())]</p>
 * <p>2. If cache is open, the association will be deleted from cache first, and then remove from RecordStore.</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.a If entityId.getObjectId is null or entityId is not any key of &quot;associations&quot;, return directly.</p>
 * <p>&nbsp;&nbsp; 2.b Otherwise, there has been associations with entityId. Hence, retrieve vector of this entityId and delete associatedId from the vector.</p>
 * <p>3. Delete it from RecordStore.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.a Open RecordStore by entityId.getTypeId[RecordStore rs = RecordStore.openRecordStore(entityId.getTypeId() +&quot;association&quot;, true)]</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.b If objectId of entityId is null or not in 1 and next record id, close rs and return.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.c Otherwise, the entityId has been associated with other entityId(s). load record bytes, and parse it into entityId+associateId[0]+...associateId[n-1]. remove associateId from it. If there is still associate id, rewrite the record into RecordStore at entityId.getObjectId index. Otherwise, delete this record.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.d close rs.</p>
 * <p></p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm68f3]
 * @param instance the Entity instance that has associated entity id, which should not be null
 * @param associateId the associated entity id, which should not be null
 * @throws IllegalArgumentException if instance or associatedId is null
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 * @throws EntityNotFoundException if entity is not found by the instance
 */
    public synchronized void deleteAssociation(com.topcoder.mobilerssreader.databroker.entity.Entity instance, com.topcoder.mobilerssreader.databroker.entity.EntityId associateId) {        
        // your code here
    } 

/**
 * <p>Delete all associations of specified entity from data broker. If there is no association of this entity, remove this record in cache and RecordStore.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1. Create new EntityId from instance. [EntityId entityId = new EntityId(instance.getObjectId(), instance.getTypeId())]</p>
 * <p>2. If cache is open, the association will be deleted from cache first, and then remove from RecordStore.</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.a If entityId.getObjectId is null or entityId is not any key of &quot;associations&quot;, return directly.</p>
 * <p>&nbsp;&nbsp; 2.b Otherwise, there has been association with entityId. Hence, delete this association.</p>
 * <p>3. Delete it from RecordStore.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.a Open RecordStore by entityId.getTypeId[RecordStore rs = RecordStore.openRecordStore(entityId.getTypeId() +&quot;association&quot;, true)]</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.b If objectId of entityId is null or not in 1 and next record id, close rs and return.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.c Otherwise, the entityId has been associated with other entityId(s). Delete this record.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.d close rs.</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6869]
 * @param instance the Entity instance that has associated entity id, which should not be null
 * @throws IllegalArgumentException if instance is null
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 * @throws new value
 */
    public synchronized void deleteAllAssociations(com.topcoder.mobilerssreader.databroker.entity.Entity instance) {        
        // your code here
    } 

/**
 * <p>Break all associations on to this entity. It will interate all associations in cache(if it is open) and RecordStore to break all associations on to this entity.</p>
 * <p>It is defined as a synchronized method to make only one thread can access at a tim</p>
 * <p>Implementation:</p>
 * <p>1. Create new EntityId from instance. [EntityId entityId = new EntityId(instance.getObjectId(), instance.getTypeId())]</p>
 * <p>2. If cache is open, iterate associations in cache first, and then in RecordStore.</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.a Iterate all associations from cache. If retrieved vector contains this entityId, remove it from the vector.</p>
 * <p>3. Delete it from RecordStore. Open all RecordStore according to registered typeId in member &quot;entityFactories&quot;. For each RecordStore:</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.a Open RecordStore by entityId.getTypeId[RecordStore rs = RecordStore.openRecordStore(typeId + &quot;association&quot;, true)]</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.b&nbsp; Iterate all records in this RecordStore. load record bytes, and parse it into entityId+associateId[0]+...associateId[n-1]. remove equal associateId from it. If there is still associateId in record bytes, rewrite the record into RecordStore at entityId.getObjectId index. Otherwise, delete this record.</p>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3.d close rs.</p>
 * <p></p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm6824]
 * @param instance the Entity instance that is associated by other entity id, which should not be null
 * @throws IllegalArgumentException if instance is null
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 * @throws EntityNotFoundException if entity is not found by the instance
 */
    public synchronized void disassociateEntity(com.topcoder.mobilerssreader.databroker.entity.Entity instance) {        
        // your code here
    } 

/**
 * <p>Open cache mechanism&nbsp; for this mobile data broker.</p>
 * <p>It is recommended to open cache when mobile memory is large and response speed is important.</p>
 * <p>After all operations on relations, user should call closeCache to write cache into RMS storage.</p>
 * <p>Implementation:</p>
 * <p>1. this.isCached = true;</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm67df]
 */
    public void openCache() {        
        // your code here
    } 

/**
 * <p>Close cache mechanism. It will set isCached false and write elements of member &quot;associations&quot; into RMS storage.</p>
 * <p>It MUST be called after calling openCache method to make consentience between cache and RMS storage.</p>
 * <p>Implementation:</p>
 * <p>1. isCached = false;</p>
 * <p>2. write all elements of &quot;associations&quot; into RMS storage.</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.1 get the ith element from &quot;associations&quot;, key is entityId(EntityId instance), value is associatedEntityIds(vector of EntityId instance.)</p>
 * <p>&nbsp;&nbsp; 2.2 open RecordStore according to entity's type id.[RecordStore rs = RecordStore.openRecordStore(entityId.getTypeId() +&quot;association&quot;, true) ]</p>
 * <p>&nbsp;&nbsp; 2.3 Get byte array of entityId, combined with every entityId instance in vector.[byte array like: entityId.getBytes()+ associatedEntityIds[0].getBytes()+,.... associatedEntityIds[n-1].getBytes()]</p>
 * <p>&nbsp;&nbsp; 2.4 If entityId.getObjectId is null, that is new entityId and association, add byte array into the end of the rs. Otherwise, set byte array into the rs according to the int value of entityId.getObjectId</p>
 * <p>&nbsp;&nbsp; 2.4 Close rs.</p>
 * <p></p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm67c7]
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public void closeCache() {        
        // your code here
    } 

/**
 * <p>Store new config data into this broker. If key exists in configuration member, the new value is reset. At last, the key-value pair is stored into &quot;config&quot; RecordStore</p>
 * <p>Implementation:</p>
 * <p>1. Call configuration.get(key). If return is null, that is no such configuration stored before. do the following step:</p>
 * <p>&nbsp;&nbsp;&nbsp; 1.a. Open a RecordStore by &quot;config&quot; name.</p>
 * <p>&nbsp;&nbsp;&nbsp; 1.b. Write length of byte array of key,&nbsp; byte array of key, length of byte array of value and length fo byte array of value into RecordStore.</p>
 * <p>&nbsp;&nbsp;&nbsp; 1.c. Put key-recordIndex into configuration hashtable. Call configuration.put(key, new Integer(recordIndex)).</p>
 * <p>&nbsp;&nbsp;&nbsp; 1.d. Close RecordStore.</p>
 * <p>2. Otherwise, get the recordIndex of the key,. set the new value into RecordStore at the recordIndex.&nbsp;</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.a. Open a RecordStore by &quot;config&quot; name.</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.b. Set length of byte array of key,&nbsp; byte array of key, length of byte array of value and length fo byte array of value into RecordStore at recordIndex position.</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.c.Close RecordStore.</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm51ef]
 * @param key the key string of config data, which should not be null or empty
 * @param value the value string of config data, which should not be null or empty
 * @throws IllegalArgumentException if key is null or empty, value is null or empty
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public void storeConfigData(String key, String value) {        
        // your code here
    } 

/**
 * <p>Load specified config value by its key from RecordStore.</p>
 * <p>Implementation:</p>
 * <p>1. Get recordIndex from member configuration by &quot;key&quot;. If the return is null, return null directly.</p>
 * <p>2. Otherwise, load value from RecordStore at specified recordIndex.</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.a Open a RecordStore by &quot;config&quot; name.</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.b Read and parse record[format: one integer value, a char array, one integer value, a char array] at recordIndex.</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.c Get the value</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.d Close the RecordStore</p>
 * <p>&nbsp;&nbsp;&nbsp; 2.e Return value</p>
 * 
 * @poseidon-object-id [I134f7188m113896f2eecmm5136]
 * @param key the key string to fetch config data, which should not be null or empty
 * @return the config data value, or null if there is no such value
 * @throws IllegalArgumentException if key is null or empty.
 * @throws StoreFailedException if any exception related with storage, it should be used to wrap this exception.
 */
    public String loadConfigData(String key) {        
        // your code here
        return null;
    } 

 }
