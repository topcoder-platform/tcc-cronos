package com.topcoder.widgets.bridge;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * First cut for a Generics based service locator.  What we're missing is the ability
 * to do local calls.
 *
 * @author dok, cucu
= */
public abstract class EJB3Locator<T> implements ServiceLocator<T> {

	private Log log = LogManager.getLog("com.topcoder.widget.bridge.AjaxBridgeServlet");
    
    /**
     * The proxy returned to all getService calls.
     */
    private final T proxiedServices;

    /**
     * Indicates if a new instance of the service must be obtained.
     */
    private volatile boolean mustReload = true;

    /**
     * The real service where to delegate calls.
     */
    private T services;

    /**
     * Creates a new Service locator.
     * <p/>
     * With this constructor, the jndiname is defaulted to use the EJB3 default interface+Bean+/remote
     * <p/>
     * So, if you pass in com.topcoder.myinterface, you'll be looking up myinterfaceBean/remote
     *
     * @param contextURL The initial context URL.
     */
    @SuppressWarnings("unchecked")
	public EJB3Locator(String contextURL) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.log(Level.DEBUG, "class name: "+ clazz.getName());
        
        this.proxiedServices = (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz}, new ServiceFailureDetection());
    }

    protected abstract String getJndiName();
    
    /**
     * Returns the service instance.
     *
     * @return The service
     * @throws javax.naming.NamingException
     * @throws java.rmi.RemoteException
     * @throws javax.ejb.CreateException
     */
    public T getService() throws Exception {
        checkServiceLoaded();
        return proxiedServices;
    }

    private void checkServiceLoaded() throws Exception {
        if (mustReload || services == null) {
            createServiceInstance();
        }
    }

    private synchronized void createServiceInstance() throws Exception {
        if (mustReload || services == null) {
            try {
                services = getServices();
                mustReload = false;
            } catch (Exception e) {
                log.log(Level.INFO, "Error getting services: " + e);
            }
        }
    }

    private T getServices() throws NamingException {
        InitialContext ctx = null;
        try {
        	String name = getJndiName();
            log.log(Level.INFO, "Creating new instance of " + name);

            T ret = null;
            ctx = getContext();
            log.log(Level.DEBUG, "lookup remote " + name);

            ret = (T) ctx.lookup(name);
            if (ret != null) {
            	log.log(Level.DEBUG, "Found remotely");
            } else {
            	log.log(Level.DEBUG, "Not found remotely");
            }
            return ret;
        } catch (NamingException e) {
            throw e;
        } 
    }


    /**
     * Returns the initial context to use for finding the Home
     *
     * @return The context
     * @throws NamingException
     */
    protected InitialContext getContext() throws NamingException {
    	return new InitialContext();
    	
    }

    private class ServiceFailureDetection implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                checkServiceLoaded();
                return services.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(services, args);
            } catch (InvocationTargetException e) {
            	StringWriter buf = new StringWriter();
            	e.printStackTrace(new PrintWriter(buf));
            	
                if (e.getTargetException() instanceof RemoteException) {
                    log.log(Level.WARN, e.getTargetException().getClass().getName() + " when calling proxied method. home=" + getJndiName() + "\n" + buf );
                    mustReload = true;
                } else {
                }
                throw e.getTargetException();
            } catch (Throwable e) {
            	StringWriter buf = new StringWriter();
            	e.printStackTrace(new PrintWriter(buf));
            	throw e;
			}
        }
    }
    
    
}


