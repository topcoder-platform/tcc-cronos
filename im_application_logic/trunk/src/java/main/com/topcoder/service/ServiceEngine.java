package com.topcoder.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is the core class of this component. It defines a model of service
 * provision based on category and queueing system.
 * <p>
 * Services are categoried by Category class. Each service category would hold a
 * requester queue and a responder queue. When both requester and responder
 * belonging to same category are available, service will be started.
 * </p>
 * <p>
 * There are two phases of service state. One is to-be-serviced state, and the
 * other is the actual serviced state. Only one service in the same category can
 * be in to-be-serviced state. But many services can be in serviced at the same
 * time to run in different threads. When a service is entering or exiting any
 * phase, a service event will be trigger. User can register the one he/she is
 * insterested in.
 * </p>
 * <p>
 * When both the requesters queue and responders queue of same category are not
 * empty, a pluggable service element selector is used to select the next
 * requester/responder to be serviced. After the requester/responder was
 * selecting out, a pluggable service linker will be used to link the two
 * element, and maybe remove them from the queue. The actual service logic will
 * be done in pluaggable service handler.
 * </p>
 * The status of service engine will be persisted in the pluggable
 * ServiceEnginePersistence, and requesters and responders will be persisted by
 * ServiceElementPersistence, which is also pluggable.
 * <p>
 * This class should be thread safe. IMPLEMENTATION NOTES: All the methods
 * should be synchronized.
 * </p>
 *
 */
public class ServiceEngine {

	/**
	 * Represents the requester persistence. It will be used to share requester
	 * attributes among multiple JVM.
	 * <p>
	 * It is initialized in ctor as non null value, and could never be changed
	 * later.
	 * </p>
	 * Please NOTE, this persistence is only used to store the requester object.
	 * And it could generate an unique id for each requester object.
	 *
	 */
	private ServiceElementPersistence requesterPersistence;

	/**
	 * Represents the requester persistence. It will be used to share responder
	 * attributes among multiple JVM.
	 * <p>
	 * It is initialized in ctor as non null value, and could never be changed
	 * later.
	 * </p>
	 * Please NOTE, this persistence is only used to store the respnder object.
	 * And it could generate an unique id for each requester object.
	 *
	 */
	private ServiceElementPersistence responderPersistence;

	/**
	 * Represents the ServiceElementSelector used to select next
	 * requester/responder to be serviced.
	 * <p>
	 * It is initialized in ctor, and never changed later. Not null.
	 * </p>
	 *
	 */
	private com.topcoder.service.ServiceElementSelector elementSelector;

	/**
	 * Represents all the ServiceHanlders used in this engine. The service
	 * handlers contain all the service logic, and will be used when both
	 * requester and reponder in same category are available.
	 * <p>
	 * Key of map is Category instance, and Value of map is corresponding
	 * ServiceHandler for the category. Both of them shouldn't be null.
	 * </p>
	 * <p>
	 * It is initialized and filled in ctor. The map reference is not changed
	 * later, but its key/value could be changed later.
	 * </p>
	 *
	 */
	private java.util.Map serviceHandlers = new HashMap();

	/**
	 * Represents the ServiceLinker used this engine.
	 * <p>
	 * It is used to link requester and responder before actual service logic
	 * begins. When requester and repsonder are selected out to do service, they
	 * may not available to do another service temporary. So serviceLinker is
	 * used to link these two elements, and after linking, the two elemnts are
	 * removed from queues according to the concrete ServiceLInker
	 * implementation. E.g. After linking, requester and responder will be
	 * removed from the queue.
	 * </p>
	 * <p>
	 * It is initialized in ctor, and never changed later. Not null.
	 * </p>
	 *
	 */
	private com.topcoder.service.ServiceLinker serviceLinker;

	/**
	 * Represents the persistence used to store all the state of this
	 * ServiceEngine.
	 * <p>
	 * It can persist requester, responder waiting queue, and also the locked
	 * categories. But please NOTE, only requester/responder/category IDs are
	 * persisted in this peristence. The requester/responder object is persisted
	 * in requesterPersistence and responderPersistence. Categories are
	 * predefined.
	 * </p>
	 *
	 */
	private ServiceEnginePersistence serviceEnginePersistence;

	/**
	 * Represents all the registered listeners to listen to service event. All
	 * the elements of list are non-null ServiceListener instances.
	 * <p>
	 * Listeners could be added/removed/fired in the corresponding methods.
	 * </p>
	 * <p>
	 * It is initizlied as an empty list in ctor, and its content can be changed
	 * later.
	 * </p>
	 *
	 */
	private List listeners = new ArrayList();

	/**
	 * <p>
	 * Represents ...
	 * </p>
	 *
	 */
	public static final String DEFAULT_NAMESPACE = "com.topcoder.service.ServiceEngine";

	/**
	 * Create a ServiceEngine with default namespace configuration.
	 * <p>
	 * Simply call this(DEFAULT_NAMESPACE);
	 * </p>
	 *
	 *
	 * @throws ConfigurationException
	 *             if any error occurs while loading configuration
	 */
	public ServiceEngine() {
	}

	/**
	 * Create a service engine. Load pre-configured values.
	 *
	 * <pre>
	 *   1) Get &quot;object_factory&quot; property, and use the value to create an ObjectFactor instance.
	 *   2) Get &quot;service_linker&quot; property, and use the value to create a ServiceLinker from ObjectFactory.
	 *   3) Get &quot;element_selector&quot; property, and use the value to create a ServiceElementSelector from ObjectFactory.
	 *   4) Get &quot;service_handler&quot; property, and use the value to create a ServiceHandler from ObjectFactory.
	 *   5) Get &quot;categories&quot; property, and use the values array to create a list of Category instances from ObjectFactory.
	 *   6) Get &quot;service_engine_persistence&quot; property, and use the value to create a ServiceEnginePersistence from ObjectFactory.
	 *   7) Get &quot;requester_persistence&quot; property, and use the value to create a ServiceElementPersistence from ObjectFactory.
	 *   8) Get &quot;responder_persistence&quot; property, and use the value to create a ServiceElementPersistence from ObjectFactory.
	 * </pre>
	 *
	 *
	 * @param namespace
	 *            the configuration namespace
	 * @throws IllegalArgumentException
	 *             if namespace is null or empty
	 * @throws ConfigurationException
	 *             if any other error occurs while loading configuraiton.
	 */
	public ServiceEngine(String namespace) {
	}

	/**
	 * Remove reqeuster from all the categories.
	 *
	 * <pre>
	 *   Impl Notes:
	 *   if (requester.id == -1) {
	 *   // if id is not set, we get the id by the requester attributes.
	 *   requester.id = requesterPersistence.getElementId(requester);
	 *   }
	 *   serviceEnginePersistence.removeRequeste(requester.id);
	 * </pre>
	 *
	 *
	 * @param requester
	 *            the requester to be removed
	 * @throws IllegalArgumentException
	 *             if given arg is null.
	 */
	public synchronized void removeRequester(
			com.topcoder.service.ServiceElement requester) {
		// if category is locked, throw exception.
	}

	/**
	 * Remove responder from all the categories.
	 *
	 * <pre>
	 *   Impl Notes:
	 *   if (responder.id == -1) {
	 *   // if id is not set, we get the id by the requester attributes.
	 *   responder.id = responderPersistence.getElementId(responder);
	 *   }
	 *   serviceEnginePersistence.removeResponder(responder.id);
	 * </pre>
	 *
	 *
	 * @param responder
	 *            the responder to be removed
	 * @throws IllegalArgumentException
	 *             if given arg is null.
	 */
	public synchronized void removeResponder(
			com.topcoder.service.ServiceElement responder) {
		// if category is locked, throw exception
	}

	/**
	 * Get all the requesters belonging to specific category.
	 *
	 * <pre>
	 * long[] ids = serviceEnginePersistence.getRequesters(category.id);
	 * return requesterPerstence.getElements(ids);
	 * </pre>
	 *
	 *
	 * @param category
	 *            the category instance
	 * @return all the requesteres belonging to the category.
	 * @throws IllegalArgumentException
	 *             if category is null, or isn't contained.
	 */
	public synchronized com.topcoder.service.ServiceElement[] getRequesters(
			com.topcoder.service.Category category) {
		return null;
	}

	/**
	 * Get all the responders belonging to specific category.
	 *
	 * <pre>
	 *   See namesake methods of requester
	 * </pre>
	 *
	 *
	 * @param category
	 *            the category instance
	 * @return all the responders belonging to the category.
	 * @throws IllegalArgumentException
	 *             if category is null, or isn't contained.
	 */
	public synchronized com.topcoder.service.ServiceElement[] getResponders(
			com.topcoder.service.Category category) {
		return null;
	}

	/**
	 * Get all the registered categories of this service engine.
	 *
	 *
	 * @return an array of categories.
	 */
	public synchronized Category[] getCategories() {
		return new Category[0];
	}

	/**
	 * Determine whether specifc category is supported by this enginge.
	 *
	 *
	 * @return whether the category is contained.
	 * @param category
	 *            the category to find
	 * @throws IllegalArgumentException
	 *             if any arg is null
	 */
	public synchronized boolean containsCategory(Category category) {
		return false;
	}

	/**
	 * Get all categories containing given requester.
	 *
	 * <pre>
	 *   Impl Notes,
	 *   if (requester.getId() == -1) {
	 *   requester.id = requesterPersistence.getElementId(requester);
	 *   }
	 *   long[] ids = serviceEnginePersistence.getCategoriesForRequester(requester.id);
	 *   // construct category array from ids.
	 * </pre>
	 *
	 *
	 * @param requester
	 *            the requester to find.
	 * @return an arry of categories (or an empty array, if none found).
	 * @throws IllegalArgumentException
	 *             if any arg is null.
	 */
	public synchronized com.topcoder.service.Category[] getCategoriesForRequester(
			com.topcoder.service.ServiceElement requester) {
		return null;
	}

	/**
	 * Get all categories containing given responder.
	 *
	 * <pre>
	 *   Impl Notes,
	 *   See namesake method for requester.
	 * </pre>
	 *
	 *
	 * @param responder
	 *            the responder to find.
	 * @return an arry of categories (or an empty array, if none found).
	 * @throws IllegalArgumentException
	 *             if any arg is null.
	 */
	public synchronized com.topcoder.service.Category[] getCategoriesForResponder(
			com.topcoder.service.ServiceElement responder) {
		return null;
	}

	/**
	 * Get the position of first requester in specific category queue.
	 *
	 * <pre>
	 * if (requester.getId() == -1) {
	 * 	requester.id = requesterPersistence.getElementId(requester);
	 * }
	 * return serviceEngine.indexOfRequester(requester.id, category.id);
	 * </pre>
	 *
	 *
	 * @param requester
	 *            the requester to be found
	 * @param category
	 *            the category containing requester.
	 * @return the position of requester in given category queue.
	 * @throws IllegalArgumentException
	 *             if any arg is null, or category is unregistered.
	 */
	public synchronized int indexOfRequester(
			com.topcoder.service.ServiceElement requester,
			com.topcoder.service.Category category) {
		return -1;
	}

	/**
	 * Get the position of first responder in specific category queue.
	 *
	 * <pre>
	 *   See namesake method for requester.
	 * </pre>
	 *
	 *
	 * @param responder
	 *            the responder to be found
	 * @param category
	 *            the category containing responder.
	 * @return the position of responder in given category queue.
	 * @throws IllegalArgumentException
	 *             if any arg is null, or category is unregistered.
	 */
	public synchronized int indexOfResponder(
			com.topcoder.service.ServiceElement responder,
			com.topcoder.service.Category category) {
		return -1;
	}

	/**
	 * Add a requester to specific category. (to the tail of list) If reponder
	 * in this category is also availabe, and the category is not locked,
	 * service will be started immediately.
	 *
	 * <pre>
	 *   Impl Notes:
	 *   requesterPersistence.createElement(requester);
	 *   serviceEnginePersistence.addRequester(requester.id, category.id);
	 * </pre>
	 *
	 *
	 * @param requester
	 *            the requester instance
	 * @param category
	 *            the category to add the requester to
	 * @throws IllegalArgumentException
	 *             if any arg is null, or no such category.
	 * @throws ServiceException
	 *             if any error occurs while trying to start service.
	 */
	public synchronized void addRequester(
			com.topcoder.service.ServiceElement requester,
			com.topcoder.service.Category category) {
	}

	/**
	 * Add a responder to specific category. (to the tail of list) If requester
	 * in this category is also availabe, and the category is not locked,
	 * service will be started immediately.
	 *
	 * <pre>
	 *   Impl Notes:
	 *   See namesake method for requester.
	 * </pre>
	 *
	 *
	 * @param responder
	 *            the responder instance
	 * @param category
	 *            the category to add the responder to
	 * @throws IllegalArgumentException
	 *             if any arg is null, or no such category.
	 * @throws ServiceException
	 *             if any error occurs while trying to start service.
	 */
	public synchronized void addResponder(
			com.topcoder.service.ServiceElement responder,
			com.topcoder.service.Category category) {
	}

	/**
	 * Insert requester to specific position in given category queue. Requesters
	 * can be duplicated.
	 *
	 * <pre>
	 *   Impl Notes,
	 *   requesterPersistence.createElement(requester);
	 *   serviceEnginePersistence.addRequester(requester.id, category.id, index);
	 * </pre>
	 *
	 *
	 * @param requester
	 *            the requster instance.
	 * @param category
	 *            the queue category
	 * @param index
	 *            position to insert to list
	 * @throws IllegalArgumentException
	 *             if any arg is null, or category in unregistered
	 * @throws IndexOutOfBoundsException
	 *             if index is out of bounds.
	 * @throws ServiceException
	 *             if any error occurs while trying to start service.
	 */
	public synchronized void addRequester(
			com.topcoder.service.ServiceElement requester,
			com.topcoder.service.Category category, int index) {
		// if the category if locked, throw exception
	}

	/**
	 * Insert responder to specific position in given category queue. Responders
	 * can be duplicated in same list.
	 *
	 * <pre>
	 *   Impl Notes,
	 *   See namesake method for requester.
	 * </pre>
	 *
	 *
	 * @param responder
	 *            the responder instance.
	 * @param category
	 *            the queue category
	 * @param index
	 *            position to insert to list
	 * @throws IllegalArgumentException
	 *             if any arg is null, or category is unregistered.
	 * @throws IndexOutOfBoundsException
	 *             if index is out of bounds.
	 * @throws ServiceException
	 *             if any error occurs while trying to start service.
	 */
	public synchronized void addResponder(
			com.topcoder.service.ServiceElement responder,
			com.topcoder.service.Category category, int index) {
		// if the category is locked, throw exception
	}

	/**
	 * Remove a requester from given category queue. Note, only the first
	 * occurrence should be removed.
	 *
	 * <pre>
	 *   Impl Notes:
	 *   if (requester.id == -1) {
	 *   // if id is not set, we get the id by the requester attributes.
	 *   requester.id = requesterPersistence.getElementId(requester);
	 *   }
	 *   serviceEnginePersistence.removeRequeste(requester.id, category.id);
	 * </pre>
	 *
	 *
	 * @param requester
	 *            the requester to be removed
	 * @param category
	 *            the category queue.
	 * @return whether the requester is removed successfully.
	 * @throws IllegalArgumentException
	 *             if any arg is null, or the category is unregistered.
	 */
	public synchronized boolean removeRequester(
			com.topcoder.service.ServiceElement requester,
			com.topcoder.service.Category category) {
		return false;
	}

	/**
	 * Remove a responder from given category queue. Note, only the first
	 * occurrence should be removed.
	 *
	 * <pre>
	 *   Impl Notes:
	 *   See namesake method for requester.
	 * </pre>
	 *
	 *
	 * @param responder
	 *            the responder to be removed
	 * @param category
	 *            the category queue.
	 * @return whether the responder is removed successfully.
	 * @throws IllegalArgumentException
	 *             if any arg is null, or the category is unregistered.
	 */
	public synchronized boolean removeResponder(
			com.topcoder.service.ServiceElement responder,
			com.topcoder.service.Category category) {
		return false;
	}

	/**
	 * delegate to the namesake methods in serviceEnginePersistence.
	 *
	 *
	 * @return number of requesters belonging to the category
	 * @param category
	 *            the category value
	 * @throws IllegalArgumentException
	 *             if category is null or unregistered.
	 */
	public synchronized int countRequesters(Category category) {
		return -1;
	}

	/**
	 * delegate to the namesake methods in serviceEnginePersistence.
	 *
	 *
	 * @return number of responders belonging to the category
	 * @param category
	 *            the category value
	 * @throws IllegalArgumentException
	 *             if category is null or unregistered.
	 */
	public synchronized int countResponders(Category category) {
		// ok
		return -1;
	}

	/**
	 * Clear the requesters queue of specific category.
	 *
	 * <pre>
	 *   Impl Notes.
	 *   delegate to the namesake methods in serviceEnginePersistence.
	 * </pre>
	 *
	 *
	 * @param category
	 *            the category value
	 * @throws IllegalArgumentException
	 *             if any arg is null, or category is unregistered.
	 */
	public synchronized void clearRequesters(Category category) {

	}

	/**
	 * Clear the responders queue of specific category.
	 *
	 * <pre>
	 *   Impl Notes.
	 *   delegate to the namesake methods in serviceEnginePersistence.
	 * </pre>
	 *
	 *
	 * @param category
	 *            the category value
	 * @throws IllegalArgumentException
	 *             if any arg is null, or category is unregistered.
	 */
	public synchronized void clearResponders(Category category) {

	}

	/**
	 * Start service beween given requester and responder in specific category.
	 * Basically, this method start serviceHandler.onServiced method in a new
	 * thread. And service STARTED event, and service FINISHED event will also
	 * be triggerred in that thread. This method will be called by caller after
	 * requester and responder's acknowlegement. And the requester, responder
	 * may not be the original linked elements, so it should be given explicitly
	 * by caller.
	 * <p>
	 * As per this thread:
	 * https://software.topcoder.com/forum/c_forum_message.jsp?f=26414189&r=26418429
	 * </p>
	 *
	 * <pre>
	 *   Impl Notes:
	 *   DESIGN CHANGES!!!!
	 * <br />
	 *   1) requesterId = serviceEnginePersistence.getLockedRequester(category.id);
	 * <br />
	 *   if (requesterId == -1) throw IllegalStateException as the category is not locked
	 * <br />
	 *   2) Check whether the requester is the original one&lt;br/&gt;
	 *   givenId = requesterPersistence.getElementId(requester);
	 * <br />
	 *   if (givenId != requesterId) throw ServiceException
	 *   3) Start a thread to run serviceing method:
	 *   Thread thread = new Thread() {
	 *   public void run() {
	 *   this.fireServiceEnvet(STARTED event);
	 *   serviceHandler.onServiced(requester, responder, category);
	 *   this.fireServiceEvent(FINISHED event);
	 *   }
	 *   };
	 *   5) unlock the category:
	 *   this.lockedCategories.remove(category);
	 *   6) Call this.prepareService(category) to try to find any requester/responder is available.
	 * </pre>
	 *
	 *
	 * @param requester
	 * @param responder
	 *            responder side of service
	 * @param category
	 *            the service category
	 * @throws IllegalArgumentException
	 *             if any arg is null
	 * @throws IllegalStateException
	 *             if given cateogry is not in to-be-serviced state.
	 * @throws ServiceException
	 *             if any other error occurs.
	 */
	public synchronized void startService(
			com.topcoder.service.ServiceElement requester,
			com.topcoder.service.ServiceElement responder,
			com.topcoder.service.Category category) {
		// start a thread to call
		// serviceHandler.onServiced(requester, responder, category);
		// Thread thread = new Thread() {
		// public void run() {
		// serviceHandler.onServiced(requester, responder, category);
		// }
		// };

		// unlock the category
	}

	/**
	 * Try to prepare service for the specific category.
	 * <p>
	 * If requester or responder for the category is not avaible, or if the
	 * category is locked, this method returns false directly. Otherwise, servie
	 * elements will be linked by serviceLink, and
	 * serviceHandler.onToBeServiceed will be called to enter to-be-serviced
	 * status. The category will be locked at last. An event will be triggered
	 * to tell user a requester/responder/category pair is to-be-serviced.
	 * </p>
	 * <p>
	 *
	 * <pre>
	 *   1) call requesterSelector/responderSelector to select the next resquester/responder.
	 *   If any return value is null, return false directly.
	 *   2) if the category is is locked, return false directly.
	 *   3) Call serviceLinker.linkService(requester, responder, category);
	 *   4) lock category: serviceEnginePersistence.lockCategory(category);
	 *   5) Start a new thread to call serviceHandler#onToBeServiced method, and fire an event.
	 *   Thread thread = new Thread() {
	 *   void run() {
	 *   // catch exception in this method, and print stacktrace
	 *   (5.1) Call servicehandler.onToBeServiced(requester, responder, category);
	 *   (5.2) trigger event: fireServiceEvent(PREPARED event);
	 *   }
	 *   }
	 * </pre>
	 *
	 * </p>
	 *
	 *
	 * @return
	 * @param category
	 *            the category to be checked whether service is available.
	 * @throws IllegalArgumentException
	 *             if category is null or unregistered.
	 * @throws ServiceException
	 *             if any other error occurs.
	 */
	protected synchronized boolean prepareService(Category category) {

		return true;
	}

	/**
	 * Add a service listener. Duplicated listener will be added.
	 * <p>
	 * Impl Notes. listeners.add(listener);
	 * </p>
	 *
	 *
	 * @param listener
	 *            the service listener
	 * @throws IllegalArgumentException
	 *             if any arg is null.
	 */
	public synchronized void addServiceListener(ServiceListener listener) {

	}

	/**
	 * Remove a service listener. Only the first occurrence will be removed.
	 * <p>
	 * Impl Notes. listeners.remove(listener);
	 * </p>
	 *
	 *
	 * @param listener
	 *            the service listener to be removed
	 * @throws IllegalArgumentException
	 *             if any arg is null.
	 */
	public synchronized void removeServiceListener(ServiceListener listener) {

	}

	/**
	 * Get all the registered service listener. Return this.listener.toArray();
	 *
	 *
	 * @return all the registered service listener.
	 */
	public synchronized ServiceListener[] getServiceListeners() {
		return null;
	}

	/**
	 * Fire the service event.
	 *
	 * <pre>
	 *   foreach listener in listeners list
	 *   swith (e.getStatus()) {
	 *   case ServiceStatus.STARTED:
	 *   listener.serviceStarted(e);
	 *   case ServiceStatus.FINISHED:
	 *   listener.serviceFinished(e);
	 *   case ServiceStatus.PREPARED:
	 *   listener.service.Prepared(e);
	 *   }
	 * </pre>
	 *
	 *
	 * @param e
	 *            the service event to be triggered
	 * @throws IllegalArgumentException
	 *             if e is null.
	 */
	protected synchronized void fireServiceEvent(ServiceEvent e) {
		// start a thread to call each listener
	}

	/**
	 * <p>
	 * Does ...
	 * </p>
	 *
	 *
	 * @return
	 */
	public com.topcoder.service.ServiceElementPersistence getRequesterPersistence() {
		return null;
	}

	/**
	 * <p>
	 * Does ...
	 * </p>
	 *
	 *
	 * @return
	 */
	public com.topcoder.service.ServiceElementPersistence getResponderPersistence() {
		return null;
	}

	/**
	 * <p>
	 * Does ...
	 * </p>
	 *
	 *
	 * @return
	 */
	public com.topcoder.service.ServiceEnginePersistence getServiceEnginePersistence() {
		return null;
	}
}
