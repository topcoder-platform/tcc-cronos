package com.topcoder.chat.message.pool;

/**
 * The class containing a result of the searching algorithm. It is used for the
 * all searches. The search result contains information about user pool and
 * session pool identification numbers and the access time. Which pool was found
 * is represented by the isUserPool field. And the type of pool (user or
 * session) described by the isPushed variable.
 * <p>
 * Thread safety. This class is immutable and thread-safe.
 * </p>
 *
 */
public class SearchResult {

	/**
	 * The user pool identification number. Can be any value. Initialized in the
	 * constructor and never changed later.
	 *
	 */
	private final long user;

	/**
	 * The session pool identification number. Can be any value. Initialized in
	 * the constructor and never changed later.
	 *
	 */
	private final long session;

	/**
	 * The last access time to the pool. Can be any value. Initialized in the
	 * constructor and never changed later.
	 *
	 */
	private final long accessTime;

	/**
	 * The flag describing which pool was found. true - user pool, false -
	 * session pool. Can be any value. Initialized in the constructor and never
	 * changed later.
	 *
	 */
	private final boolean isUserPool;

	/**
	 * The flag, which identifies the type of access to the pool. true - push
	 * operation, false - pull operation. Can be any value. Initialized in the
	 * constructor and never changed later.
	 *
	 */
	private final boolean isPushed;

	/**
	 * A simple initializing constructor. Simply put the all arguments to the
	 * related fields. Any values are accepted.
	 * <p>
	 * Exception handling. None.
	 * </p>
	 *
	 *
	 * @param user
	 *            The iser pool identification number. Can be any value.
	 * @param session
	 *            The session pool identification number. Can be any value.
	 * @param accessTime
	 *            The time of the last access to the pool. Can be any value.
	 * @param isUserPool
	 *            The flag describing which pool was found. true - user pool,
	 *            false - session pool. Can be any value.
	 * @param isPushed
	 *            The flag describing whether the last action was push (true)<
	 *            or pull (false). Can be any value.
	 */
	public SearchResult(long user, long session, long accessTime,
			boolean isUserPool, boolean isPushed) {
		this.user = user;
		this.session = session;
		this.accessTime = accessTime;
		this.isUserPool = isUserPool;
		this.isPushed = isPushed;
	}

	/**
	 * A getter method for the related field. Simply return the user field
	 * value.
	 * <p>
	 * Exception handling. None.
	 * </p>
	 *
	 *
	 * @return The value of the user field. Can be any value.
	 */
	public long getUser() {
		return this.user;
	}

	/**
	 * A getter method for the related field. Simply return the session field
	 * value.
	 * <p>
	 * Exception handling. None.
	 * </p>
	 *
	 *
	 * @return The value of the session field. Can be any value.
	 */
	public long getSession() {
		return this.session;
	}

	/**
	 * A getter method for the related field. Simply return the access field
	 * value.
	 * <p>
	 * Exception handling. None.
	 * </p>
	 *
	 *
	 * @return The value of the accessTime field. Can be any value.
	 */
	public long getAccessTime() {
		return this.accessTime;
	}

	/**
	 * A getter method for the related field. Simply return the isUserPool field
	 * value.
	 * <p>
	 * Exception handling. None.
	 * </p>
	 *
	 *
	 * @return The value of the isUserPool field. Can be any value.
	 */
	public boolean isUserPool() {
		return this.isUserPool;
	}

	/**
	 * A getter method for the related field. Simply return the isPushed field
	 * value.
	 * <p>
	 * Exception handling. None.
	 * </p>
	 *
	 *
	 * @return The value of the isPushed field. Can be any value.
	 */
	public boolean isPushed() {
		return this.isPushed;
	}
}
