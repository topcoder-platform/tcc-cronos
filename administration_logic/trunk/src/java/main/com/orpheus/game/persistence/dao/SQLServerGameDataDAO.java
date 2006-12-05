
package com.orpheus.game.persistence.dao;
import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DuplicateEntryException;
import com.orpheus.game.persistence.EntryNotFoundException;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.SlotCompletion;

/**
 * Implements GameDataDAO. Works with SQL Server database and most tables defined in RS 1.2.3. Most of the
 * beans used in the methods have a mostly 1-1 correspondence with the tables. It uses ConfigManager and
 * Object Factory to configure the connection factory, random string image, and the length of key text to
 * generate as well as the media type. The last three are used in the recording of the host slot completion
 * It is expected that the connection factory will use a JNDI connection provider so the Datasource is obtained
 * from the application server.
 * <p><strong>Thread Safety</strong></p>
 * <p>This class is mutable and thread-safe</p>
 * 
 */
public class SQLServerGameDataDAO implements com.orpheus.game.persistence.GameDataDAO {

/**
 * <p>Represents the connection factory that is used for performing CRUD operations. It should be backed by a JNDI connection producer, which simply eases the obtaining of a connection from a datasource via JNDI.&nbsp; This is created in the constructor, will not be null, and willl never change.</p>
 * 
 */
    private final com.topcoder.db.connectionfactory.DBConnectionFactory connectionFactory;

/**
 * <p>Represents the name of the connection to obtain from the connection factory that is used for performing CRUD operations.&nbsp; This is created in the constructor. This value is optional, and empty will be set to null, and willl never change. If null, then the default connection will be obtained.</p>
 * 
 */
    private final String connectionName;


/**
 * <p>Represents the length of text for the random string image generator to generate for hosting slot completion. This is created in the constructor, will not be null, and willl never change.</p>
 * 
 */
    private final int keyLength;

/**
 * <p>Represents the media type to plug into the download object&nbsp; for hosting slot completion. This is created in the constructor, will not be null/empty, and willl never change.</p>
 * 
 */
    private final String mediaType;

/**
 * <p>Instantiates new SQLServerGameDataDAO instance from the given namespace. It will use ConfigManager and ObjectFactory to instantiate a new DBConnectionFactory and RandomStringImage objects, and use the ConfigManager to get the optional connection name, the length of key text and media type.</p>
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>Instantiate a new ObjectFactory with a ConfogManagerSpecificationFactory with a namespace obtained from ConfigManager</li>
 * <li>Obtain the keys for the connection factory and random string image from ConfgManager and use them to obtain a new DBConnectionFactory and RandomStringImage object from ObjectFactory.</li>
 * <li>Use the ConfigManager to get the optional connection name, key text length, and media type</li>
 * </ul>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>ObjectInstantiationException If there is an error with construction</li>
 * <li>IllegalArgumentException If namespace is null or empty.</li>
 * </ul>
 * 
 * 
 * @param namespace configuration namespace
 */
    public  SQLServerGameDataDAO(String namespace) {        
        this.connectionFactory =null;
        this.connectionName = null;
        this.keyLength = 1;
        this.mediaType=null;
    } 

/**
 * Creates a new game entity in the persistent store, along with associated hosting blocks. Any game or block IDs that are null will be automatically assigned acceptable values. No hosting slots are created for the game at this time. The returned Game object will represent the persisted data, including any IDs assigned to the game and blocks.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- If id is not null, create a Statement to check if id already exists in the game table</li>
 * <li>&nbsp; - If yes, treat as DuplicateEntry scenario</li>
 * <li>- Create a Statement to check if ballColor.id already exists in the ball_color table</li>
 * <li>&nbsp; - If not, treat as EntryNotFound scenario</li>
 * <li>- Prepare a CallableStatement to insert the Game into the game table: Specifically, it will insert the id, ballColor.id, keyCount, and startDate, and register the id as an out parameter. A stored procedure needs to be written.</li>
 * <li>- Create a Statement to get the last sequence_number used in hosting_block table. if none in the table yet, use 0, so next will be 1.</li>
 * <li>- For each HostingBlock:</li>
 * <li>&nbsp; - Prepare a CallableStatement to insert into hosting_block values of next sequence_number, gameId, and the slotMaxHostingTime value, and register the id as the out parameter. A stored procedure needs to be written.</li>
 * <li>&nbsp; - Create a new HostingBlockImpl with these values</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- Package the created HostingBlocks in an array into a new Game</li>
 * <li>- Return this game</li>
 * </ul>
 * 
 * 
 * @return the game with the id
 * @param game the game
 * @throws EntryNotFoundException If game.ballColor.id is not found in persistence
 * @throws DuplicateEntryException If game.id is not null but already exists in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If game is null
 */
    public Game createGame(Game game) {        
 return null; 
    } 

/**
 * Creates hosting slots associates with the specified Bid IDs in the specified hosting block
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to check if blockId already exists in the hosting_block table</li>
 * <li>&nbsp; - If not, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get the last sequence_number used in hosting_slot table. if none in the table yet, use 0, so next will be 1.</li>
 * <li>- For each bidId:</li>
 * <li>&nbsp; - Create a Statement to check if bidId already exists in the bid table</li>
 * <li>&nbsp;&nbsp;&nbsp; - If not, treat as EntryNotFound scenario</li>
 * <li>&nbsp;&nbsp;&nbsp; - if bid.auctionId != blockId, treat as IllegalEntry scenario</li>
 * <li>&nbsp; - Create a Statement to obtain image record with the imageId from the bid table</li>
 * <li>&nbsp; - Call this.getDomain(domainId):Domain using the domainId from the image record</li>
 * <li>&nbsp; - Create a Statement to obtain effective_bid record with the bidId to obtain current_amount</li>
 * <li>&nbsp; - Create new HostingSlotImpl with this Domain, imageId, empty long[] of brainTeaserIds, null puzzleId, next sequence_number, empty DomainTarget[] of domainTargets, winning bid equal to current_amount, and null hosting start and end dates</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- Package the created HostingSlots in an array and return them</li>
 * </ul>
 * 
 * 
 * @return array of hosting slots
 * @param blockId the block id
 * @param bidIds the bid ids
 * @throws EntryNotFoundException If blockId or any bidId doesn't exist in the persistence
 * @throws IllegalEntryException If any bidId does not belong to the blockId
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If bidIds is null
 */
    public HostingSlot[] createSlots(long blockId, long[] bidIds) {        
 return null; 
    } 

/**
 * Creates a new persistent domain representation with the data from the provided Domain object and its nested ImageInfo objects. Any null Domain or ImageIndo IDs are assigned appropriate values. The returned Domain will reflect the persistent representation, including any automatically assigned IDs.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- If id is not null, create a Statement to check if id already exists in the domain table</li>
 * <li>&nbsp; - If yes, treat as DuplicateEntry scenario</li>
 * <li>- Prepare a CallableStatement to insert the Domain into the domain table: Register the id as an out parameter. A stored procedure needs to be written.</li>
 * <li>- For each ImageInfo</li>
 * <li>&nbsp; - If id is not null, create a Statement to check if id already exists in the image table</li>
 * <li>&nbsp;&nbsp;&nbsp; - If yes, treat as DuplicateEntry scenario&nbsp; </li>
 * <li>&nbsp; - Create a Statement to check if downloadId already exists in the target table</li>
 * <li>&nbsp;&nbsp;&nbsp; - If not, treat as EntryNotFound scenario</li>
 * <li>&nbsp;&nbsp;&nbsp; - Prepare a CallableStatement to insert the ImageInfo into the image table: Register the id as an out parameter. A stored procedure needs to be written.</li>
 * <li>&nbsp; - Create new ImageInfoImpl with the data</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- Create new DomainImpl with the data and the new ImageInfo[] and return it</li>
 * </ul>
 * 
 * 
 * @return the domain with id
 * @param domain the domain
 * @throws EntryNotFoundException If imageInfo.downloadId doesn't exist in persistence
 * @throws DuplicateEntryException If id or imageInfo.id is not null but already exists in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If domain is null
 */
    public Domain createDomain(Domain domain) {        
 return null; 
    } 

/**
 * Creates a new, persistent hosting block for the specified game. The block will having an auto-assigned ID, the next available sequence number after those of the game's existing blocks (or 1 if there are no other blocks), no hosting slots, and the specified maximum hosting time per slot. It returns a HostingBlock object representing the new block.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get game record with the gameId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get the last sequence_number used in hosting_block table. if none in the table yet, use 0, so next will be 1.</li>
 * <li>- Prepare a CallableStatement to insert into hosting_block values of next sequence_number, gameId, and the slotMaxHostingTime value passed, and register the id as the out parameter. A stored procedure needs to be written.</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- Create a new HostingBlockImpl with these values</li>
 * <li>- return this HostingBlock</li>
 * </ul>
 * 
 * 
 * @return the hosting block
 * @param gameId the game id
 * @param slotMaxHostingTime the max hosting time for the slot
 * @throws EntryNotFoundException If gameId is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public HostingBlock addBlock(long gameId, int slotMaxHostingTime) {        
 return null; 
    } 

/**
 * Retrieves a Game object representing the Game having the specified ID
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get game record with the gameId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get ball_color record with the above game's ball_color_id as the id</li>
 * <li>&nbsp; - Create a BallColor with this data.</li>
 * <li>- Create a Statement to get all hosting_block_ids form hosting_block table that have game_id = gameId</li>
 * <li>- FOR EACH hosting_block_id</li>
 * <li>&nbsp; - Obtain HostingBlock by calling this.getBlock(hosting_block_id)</li>
 * <li>- Create new GameImpl with the info from the game record, the BallColor, and HostingBlock[]</li>
 * <li>- Close connection: connection.close()</li>
 * </ul>
 * 
 * 
 * @return the game
 * @param gameId the game id
 * @throws EntryNotFoundException If gameId is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public Game getGame(long gameId) {        
 return null; 
    } 

/**
 * Retrieves a HostingBlock object representing the hosting block having the specified ID
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get hosting_block record with the blockId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get all hosting_slot.ids FROM hosting_slot, bid WHERE hosting_slot.bid_id = bid.id AND bid.auction_id = blockId</li>
 * <li>- FOR EACH hosting_slot.id</li>
 * <li>&nbsp; - Obtain HostingSlot by calling this.getSlot(hosting_slot.id)</li>
 * <li>- Create new HostingBlockImpl with the info from the hosting_block record, and the HostingSlot[]</li>
 * <li>- Close connection: connection.close()</li>
 * </ul>
 * 
 * 
 * @return the block
 * @param blockId the block id
 * @throws EntryNotFoundException If blockId is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public HostingBlock getBlock(long blockId) {        
 return null; 
    } 

/**
 * Retrieves a HostingSlot object representing the slot having the specified ID
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get hosting_slot record with the slotId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get image.id, image.domain_id FROM image, bid, hosting_slot WHERE hosting_slot.bid_id = bid.id AND bid.image_id = image.id</li>
 * <li>- Get Domain with this.getDomain(domainId)</li>
 * <li>- Get winningBid: Create a Statement to get current_amount FROM effective_bid, bid, hosting_slot WHERE hosting_slot.bid_id = bid.id AND effective_bid.bid_id = bid.id. If not found, winningBid = 0.</li>
 * <li>- Get brainTeaserIds: Create a Statement to get brn_tsr_for_slot.puzzle_id FROM brn_tsr_for_slot WHERE brn_tsr_for_slot.hosting_slot_id = slotId ORDER BY sequence_number</li>
 * <li>- Get puzzleId: Create a Statement to get puzzle_for_slot.puzzle_id FROM puzzle_for_slot WHERE puzzle_for_slot.hosting_slot_id = slotId</li>
 * <li>- Get domain targets: Create a Statement to get target FROM target_object WHERE target_object.hosting_slot_id = slotId ORDER BY sequence_number</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- FOR EACH target: Create a DomainTargetImpl with the target info</li>
 * <li>- Create a HostingSlotImpl with all this data and return it</li>
 * </ul>
 * 
 * 
 * @return the slot
 * @param slotId the slot id
 * @throws EntryNotFoundException If slotId is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public HostingSlot getSlot(long slotId) {        
 return null; 
    } 

/**
 * Retrieves the DownloadData object corresponding to the specified ID
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get download_obj record with the id. Get content as Blob.</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a new&nbsp; DownloadDataImpl with the blob's byte[].</li>
 * <li>- Close connection: connection.close()</li>
 * </ul>
 * 
 * 
 * @return the download data
 * @param id the download id
 * @throws EntryNotFoundException If id is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public com.topcoder.web.frontcontroller.results.DownloadData getDownloadData(long id) {        
 return null; 
    } 

/**
 * Retrieves a Domain object representing the domain corresponding to the specified ID
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get domain record with the domainId.</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Get image infos: Create a Statement to get image table data WHERE image.domain_id = domainId. and create ImageInfo with each one.</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- Create a new DomainImpl with this data and return it.</li>
 * </ul>
 * 
 * 
 * @return the domain
 * @param domainId the domain id
 * @throws EntryNotFoundException If domainId is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public Domain getDomain(long domainId) {        
 return null; 
    } 

/**
 * Returns the key text for the specified player's completions of the specified slots. The length of the returned array is the same as the length of the slotIds argument, and their elements correspond: each string in the returned array is the key text associated with the slot completion by the specified player of the slot whose ID appears at the same index in the input slotIds. If the specified player has not completed any particular slot specified among the slot IDs then the corresponding element or the returned array is null.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get player record with the playerId.</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get hosting_slot record with each slotId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get plyr_compltd_slot.key_text using the playerId and each slotId</li>
 * <li>- package all results into String[] with nulls where no plyr_compltd_slot record exists for a slotId</li>
 * <li>- Close connection: connection.close()</li>
 * </ul>
 * 
 * 
 * @return the player's keys
 * @param playerId the player id
 * @param slotIds the slot ids
 * @throws EntryNotFoundException If playerId or any slotId is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If slotIds is null
 */
    public String[] getKeysForPlayer(long playerId, long[] slotIds) {        
 return null; 
    } 

/**
 * Retrieves the PuzzleData associated with the specified puzzle ID.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get puzzle record with the puzzleId.</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get puzzle_attribute records with the puzzleId</li>
 * <li>&nbsp; - This will be the attributes Map</li>
 * <li>- Create a Statement to get all fields FROM outer left join of puzzle_resource and download_obj WHERE records have the passed puzzleId </li>
 * <li>&nbsp; - This will be the resources map: Each entry value will be a GeneralPuzzleResource </li>
 * <li>- Close connection: connection.close()</li>
 * <li>- Create a new GeneralPuzzleData with these maps.</li>
 * </ul>
 * 
 * 
 * @return the puzzle data
 * @param puzzleId the puzzle id
 * @throws EntryNotFoundException If puzzleId is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public com.topcoder.util.puzzle.PuzzleData getPuzzle(long puzzleId) {        
 return null; 
    } 

/**
 * Increments the download count for the plugin identified by the specified name
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get plugin_downloads record with the pluginName</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to update the plugin_downloads record with the pluginName so its count is incremented by 1</li>
 * <li>- Close connection: connection.close()</li>
 * </ul>
 * 
 * 
 * @param pluginName the plugin name
 * @throws EntryNotFoundException If pluginName is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If pluginName is null/empty
 */
    public void recordPluginDownload(String pluginName) {        
 return; 
    } 

/**
 * Records the specified player's registration for the specified game
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get player record with the playerId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get game record with the gameId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get plyr_regstrd_game record with the playerId and gameId</li>
 * <li>&nbsp; - If found, treat as DuplicateEntry scenario</li>
 * <li>- Create a Statement to insert a new record into plyr_regstrd_game game record with the playerId and gameId</li>
 * <li>- Close connection: connection.close()</li>
 * </ul>
 * 
 * 
 * @param playerId the player id
 * @param gameId the game id
 * @throws EntryNotFoundException If playerID or gameId is not in persistence
 * @throws DuplicateEntryException If the player has been already registered for this game
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public void recordRegistration(long playerId, long gameId) {        
 return; 
    } 

/**
 * Records the completion of the specified slot by the specified player at the specified date and time, and generates a key for the player to associate with the completion.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get player record with the playerId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get slot record with the slotId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create new stream:ByteArrayOutputStream();</li>
 * <li>- Create keyText and fill stream: Call randomStringImage.generateRandom(textLength,textLength,stream):String</li>
 * <li>- Obtain bytes from stream: byte[] content = stream.toByteArray()</li>
 * <li>- Call recordBinaryObject(String name, String mediaType, byte[] content);</li>
 * <li>- Create a Statement to insert a new record into plyr_compltd_slot</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- CReate a new SlotCompletionImpl with the data and return it.</li>
 * </ul>
 * 
 * 
 * @return the slot completion entity
 * @param playerId the player id
 * @param slotId the slot id
 * @param date date of completion
 * @throws EntryNotFoundException If playerID or slotId is not in persistence
 * @throws DuplicateEntryException If the slot has already been completed for this player
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If date is null
 */
    public SlotCompletion recordSlotCompletion(long playerId, long slotId, java.util.Date date) {        
 return null; 
    } 

/**
 * Records the fact that the specified player has completed the specified game. Whether or not such a player actually wins the game depends on whether others have already completed the game, and on administrative establishment of winner eligibility.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get player record with the playerId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get game record with the gameId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get plyr_compltd_game record with the playerId and gameId</li>
 * <li>&nbsp; - If found, treat as DuplicateEntry scenario</li>
 * <li>- Create a Statement to insert a new record into plyr_compltd_game game record with the playerId and gameId</li>
 * <li>- Close connection: connection.close()</li>
 * </ul>
 * 
 * 
 * @param playerId the player id
 * @param gameId the game id
 * @throws EntryNotFoundException If playerID or gameId is not in persistence
 * @throws DuplicateEntryException If the player has been already registered for this game
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public void recordGameCompletion(long playerId, long gameId) {        
 return; 
    } 

/**
 * Records a binary object in the database, such as might later be retrieved by the custom DownloadSource. The ID assigned to the binary object is returned.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Prepare a CallableStatement to insert the data into the download_obj table: Specifically, it will register the id as an out parameter. A stored procedure needs to be written.</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- return the id</li>
 * </ul>
 * 
 * 
 * @return the binary object
 * @param name the name of the object
 * @param mediaType the media type
 * @param content the content as a byte array
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If name or mediaType is null/empty, or content is null
 */
    public long recordBinaryObject(String name, String mediaType, byte[] content) {        
 return 1; 
    } 

/**
 * Updates the persistent hosting slot information for the existing slots represented by the specified HostingSlot objects, so that the persistent representation matches the provided objects. Nested DomainTarget objects may or may not already be recorded in persistence; the component assumes that DomainTarget's with null IDs are new, and that others already exist in the database. The component will assign IDs for new DomainTargets as needed. This method will also update the following additional HostingSlot properties (only): sequence number, hosting start, hosting end, brain teaser IDs, puzzle ID. It will return an array containing the revised hosting slots.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get hosting_slot record for each HostingSlot.id</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- FOR EACH slot</li>
 * <li>&nbsp; - Create a Statement to update the hosting_slot table: specifically update the sequencNumber, and hostingStartDate, hostingEndDate.</li>
 * <li>&nbsp; - Create a Statement to insert new record in puzzle_for_slot with the HostingSlot.puzzle_id unless it already exists</li>
 * <li>&nbsp; - Create a Statement to insert new record in brn_tsr_for_slot with each HostingSlot.brainTeaserId unless it already exists</li>
 * <li>&nbsp; - FOR EACH DomainTarget: </li>
 * <li>&nbsp;&nbsp;&nbsp; - IF it has an id, simply update target_object record with that id. If the record does not exist, treat as EntryNotFound scenario</li>
 * <li>&nbsp;&nbsp;&nbsp; - ELSE Prepare a CallableStatement to insert the data into the target_object table: Specifically, it will register the id as an out parameter. A stored procedure needs to be written. </li>
 * <li>&nbsp;&nbsp;&nbsp; - Use that id (if new) to create a new DomaintargetImpl object.</li>
 * <li>&nbsp; - Create a new HostingSlotImpl with all this data.</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- package all HostingSlots into array and return</li>
 * </ul>
 * 
 * 
 * @return the updated hosting slots
 * @param slots the hosting slots to update
 * @throws EntryNotFoundException If HsotingSlot.id or DomainTarget.id, if not null, is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If slots is null
 */
    public HostingSlot[] updateSlots(HostingSlot[] slots) {        
 return null; 
    } 

/**
 * Updates the persistent domain information for the specified Domain object to match the Domain object, where the appropriate persistent record is identified by the Domain's ID
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get domain record for Domain.id</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to update the domain record</li>
 * <li></li>
 * <li>- FOR EACH ImageInfo:</li>
 * <li>&nbsp; - IF it has an id, simply update image record with that id. If the record does not exist, treat as EntryNotFound scenario</li>
 * <li>&nbsp; - ELSE Create a Statement to insert the data into the image table</li>
 * <li>- Close connection: connection.close()</li>
 * </ul>
 * 
 * 
 * @param domain domain to update
 * @throws EntryNotFoundException If Domain.id or ImageInfo.id, if not null, is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If domain is null
 */
    public void updateDomain(Domain domain) {        
 return; 
    } 

/**
 * Deletes the hosting slot having the specified ID
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get all plyr_compltd_slot records where plyr_compltd_slot.slot_id= slotId</li>
 * <li>&nbsp; - FOR each retrieved record, use its key_image_id to delete a record in download_obj (on its id).</li>
 * <li>- Create a Statement to delete all plyr_compltd_slot records where plyr_compltd_slot.slot_id= slotId</li>
 * <li>- Create a Statement to delete hosting_slot record with the given slotId.</li>
 * <li>- Close connection: connection.close()</li>
 * </ul>
 * 
 * 
 * @param slotId slot id
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public void deleteSlot(long slotId) {        
 return; 
    } 

/**
 * Looks up all distinct domains hosting any slot in any active game, and returns an array of Domain objects representing the results
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to select all records from hosting_slot, game where hosting_slot.game_id = game.id and hosting_slot.hosting_start is not null, and filter them of any record that has an entry in plyr_won_game via game.id = plyr_won_game.game_id.</li>
 * <li>- From above, obtain all distinct domain_ids</li>
 * <li>- FOR EACH domainId, call this.getDomain(domainId)</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- package Domains into array and return</li>
 * </ul>
 * 
 * 
 * @return array of active domains
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public Domain[] findActiveDomains() {        
 return null; 
    } 

/**
 * Looks up all ongoing games in which a domain matching the specified string is a host in a slot that the specified player has not yet completed, and returns an array of all such games
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get domain record where domain.base_url = domainParam</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get player record where player.id = playerId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to select all from game, hosting_block, bid, hosting_slot, domain WHERE bid.image_id = image.id AND</li>
 * <li>image.domain_id = domain.id AND hosting_slot.hosting_start != null AND domain.base_url = domainParam AND hosting_slot.bid_id = bid.id AND bid.auction_id = hosting_block.id AND hosting_block.game_id = game.id AND game.start_date != null. </li>
 * <li>&nbsp; - Remove all records above where hosting_slot has:</li>
 * <li>&nbsp;&nbsp;&nbsp; - entry in plyr_compltd_slot where plyr_compltd_slot.hosting_slot_id = hosting_slot.id AND plyr_compltd_slot.player_id = playerId</li>
 * <li>&nbsp;&nbsp;&nbsp; - entry in plyr_won_game where plyr_won_game.game_id = game.id</li>
 * <li>- Get Game for each distinct game_id from above using this.getGame(gameId)</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- return the Game[]</li>
 * </ul>
 * 
 * 
 * @return array of games
 * @param domain the domain
 * @param playerId the player id
 * @throws EntryNotFoundException If there is no domain with the given name, or no player with such an id in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If domain is null/empty
 */
    public Game[] findGamesByDomain(String domain, long playerId) {        
 return null; 
    } 

/**
 * Looks up all hosting slots completed by any player in the specified game, and returns the results as an array of HostingSlot objects. Returned slots are in ascending order by first completion time, or equivalently, in ascending order by hosting block sequence number and hosting slot sequence number.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get game record where game.id = gameId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to select DISTINCT hosting_slot.id FROM game, hosting_block, bid, hosting_slot, plyr_compltd_slot WHERE hosting_slot.bid_id = bid.id AND bid.auction_id = hosting_block.id AND hosting_block.game_id = game.id AND plyr_compltd_slot.hosting_slot_id = hosting_slot.id AND game.id = gameId</li>
 * <li>- Get HostingSlot for each hosting_slot.id from above using this.getSlot(hosting_slot.id)</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- Sort HostingSlot[] by hosting block sequence number and hosting slot sequence return it</li>
 * </ul>
 * 
 * 
 * @return array of hosting slots
 * @param gameId the game id
 * @throws EntryNotFoundException If gameId is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public HostingSlot[] findCompletedSlots(long gameId) {        
 return null; 
    } 

/**
 * Looks up all recorded completion events that have the specified hosting slot in the specified game
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get game record where game.id = gameId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get hosting_slot record where hosting_slot.id = slotId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to select hosting_slot.id FROM game, hosting_block, bid, hosting_slot WHERE hosting_slot.bid_id = bid.id AND bid.auction_id = hosting_block.id AND hosting_block.game_id = game.id AND game.id = gameId AND hosting_slot.id = slotId</li>
 * <li>&nbsp; - If not found, treat as IllegalEntry scenario</li>
 * <li>- Create a Statement to select all plyr_compltd_slot fields FROM game, hosting_block, bid, hosting_slot, plyr_compltd_slot WHERE hosting_slot.bid_id = bid.id AND bid.auction_id = hosting_block.id AND hosting_block.game_id = game.id AND game.id = gameId AND hosting_slot.id = slotId AND plyr_compltd_slot.hosting_slot_id = hosting_slot.id</li>
 * <li>- Create a SlotCompletionImpl for each record</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- Package as SlotCompletion[] and return</li>
 * </ul>
 * 
 * 
 * @return array of slot competition events
 * @param gameId the game id
 * @param slotId the slot id
 * @throws EntryNotFoundException If gameId or slotId is not in persistence
 * @throws IllegalEntryException If slotId is not part of the game indicated by gameId
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public SlotCompletion[] findSlotCompletions(long gameId, long slotId) {        
 return null; 
    } 

/**
 * Retrieves game information for games meeting the specified game status criteria
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>
 * - We have seven possible combinations: Create a Statement with the applicable pseudo sqlString
 * <ul type="disc">
 * <li>&nbsp; 1. isStarted = null and isEnded = null: SELECT gameId FROM game</li>
 * <li>&nbsp; 2. isStarted = null and isEnded = true: SELECT gameId FROM game, plyr_won_game WHERE plyr_won_game.game_id = game.id</li>
 * <li>&nbsp; 3. isStarted = null and isEnded = false: SELECT gameId FROM game LEFT JOIN plyr_won_game ON plyr_won_game.game_id = game.id WHERE plyr_won_game.game_id = null</li>
 * <li>&nbsp; 4. isStarted = true and isEnded = null: SELECT gameId FROM game WHERE game.start_date != null</li>
 * <li>&nbsp; 5. isStarted = true and isEnded = true: SELECT gameId FROM game, plyr_won_game WHERE plyr_won_game.game_id = game.id AND game.start_date != null</li>
 * <li>&nbsp; 6. isStarted = true and isEnded = false: SELECT gameId FROM game LEFT JOIN plyr_won_game ON plyr_won_game.game_id = game.id WHERE plyr_won_game.game_id = null AND game.start_date != null</li>
 * <li>&nbsp; 7. isStarted = false and isEnded = null/false: SELECT gameId FROM game WHERE game.start_date == null</li>
 * </ul>
 * </li>
 * <li>- Get Game for each game_id from above using this.getGame(gameId)</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- return the Game[]</li>
 * </ul>
 * 
 * 
 * @return an array of Game objects representing the games found
 * @param isStarted a Boolean having value true to restrict to
 * games that have started or false to restrict to games that
 * have not yet started; null to ignore whether games have
 * started
 * @param isEnded a Boolean having value true to restrict to
 * games that have ended or false to restrict to games that
 * have not yet ended; null to ignore whether games have ended
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If isStarted == false and isEnded == true (impossible combo)
 */
    public Game[] findGames(Boolean isStarted, Boolean isEnded) {        
 return null; 
    } 

/**
 * Looks up all the games for which the specified player is registered, and returns an array of their IDs
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get player record where player.id = playerId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to select all gam_id from plyr_regstrd_game where plyr_regstrd_game.player_id = playerId</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- return all gameIds as long[], or empty if no gameIds rerieved</li>
 * </ul>
 * 
 * 
 * @return the array of game ids
 * @param playerId the player id
 * @throws EntryNotFoundException If playerId is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public long[] findGameRegistrations(long playerId) {        
 return null; 
    } 

/**
 * Looks up all domains associated with the specified sponsor and returns an array of Domain objects representing them
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get sponsor record where sponsor.id = sponsorId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to select domain.id from domain where domain.sponsor_id = sponsorId</li>
 * <li>- FOR EACH retrieved domainId, get Domain using this.getDomain(domainId)</li>
 * <li>- Close connection: connection.close()</li>
 * <li>- return all Domains as Domain[], or empty if no Domains rerieved</li>
 * </ul>
 * 
 * 
 * @return array of domains
 * @param sponsorId the sponsor id
 * @throws EntryNotFoundException If sponsorId is not in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public Domain[] findDomainsForSponsor(long sponsorId) {        
 return null; 
    } 

/**
 * Finds the first HostingSlot in the hosting sequence for the specified game that is assigned the specified domain and has not yet been completed by the specified player.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>- Obtain connection from factory: If connectionName is null, use default connection</li>
 * <li>- Create a Statement to get domain record where domain.base_url = domainParam</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get player record where player.id = playerId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to get game record where game.id = gameId</li>
 * <li>&nbsp; - If not found, treat as EntryNotFound scenario</li>
 * <li>- Create a Statement to select DISTINCT hosting_slot.id from game, hosting_block, bid, image, hosting_slot, domain WHERE domain.base_url = domainParam AND game.id = gameId AND hosting_slot.bid_id = bid.id AND bid.image_id = image.id AND image.domain_id = domain.id AND bid.auction_id = hosting_block.id ORDER BY hosting_block.sequence_number, hosting_slot.sequence_number</li>
 * <li>- Using EACH hosting_slot.id in the order given, Create a Statement to select from plyr_compltd_slot with this hosting_slot.id and playerId. If no result, this is our hosting_slot.id. Else, try with next one.</li>
 * <li>- If we have a desired hosting_slot.id, get HostingSlot by calling this.getSlot(hosting_slot.id) and return it, else return null.</li>
 * <li>- Close connection: connection.close()</li>
 * </ul>
 * 
 * 
 * @return hosting slot
 * @param gameId the game id
 * @param playerId the player id
 * @param domain the domain
 * @throws EntryNotFoundException If there is no domain with the given name, or no player or game with such an id in persistence
 * @throws PersistenceException If there is any problem in the persistence layer.
 * @throws IllegalArgumentException If domain is null/empty
 */
    public HostingSlot findSlotForDomain(long gameId, long playerId, String domain) {        
 return null; 
    } 

/**
 * Provides information about all ball colors available to the
 * application.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>XXX</li>
 * </ul>
 * 
 * 
 * @return array of ball colors
 * @throws PersistenceException If there is any problem in the persistence layer.
 */
    public BallColor[] findAllBallColors() {        
 return null; 
    } 
 }
