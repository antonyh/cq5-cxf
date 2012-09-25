package cxf_example.util;

import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * Helper methods for initializing webservice clients.
 */
public class JaxWsClientFactory {

    private JaxWsClientFactory() {
        // static methods only
    }

    /**
     * Create webservice port via JAXWS proxy factory.
     * 
     * This method fixes numerous problems with 3rdparty libs used by CXF and
     * CXF itself and classloader issues with OSGI. Using this method the
     * initialization phase of JAXB mapping is wrapped in an OSGI-aware
     * classloader. Furthermore each client instances is wrapped in an
     * OSGI-aware subclass (see {@link OsgiAwareClientImpl}), which ensures that
     * each invoke call on a webservice method is itself executed within an
     * OSGI-aware classloader context.
     * 
     * @param <T> Port class
     * @param pClass Port class
     * @param pPortUrl Port url (this is not the WSDL location)
     * @return Port object
     */
    public static <T> T create(Class<T> pClass, String pPortUrl) {
        return JaxWsClientFactory.create(pClass, pPortUrl, null, null);
    }
    
    /**
     * Create webservice port via JAXWS proxy factory.
     * 
     * This method fixes numerous problems with 3rdparty libs used by CXF and
     * CXF itself and classloader issues with OSGI. Using this method the
     * initialization phase of JAXB mapping is wrapped in an OSGI-aware
     * classloader. Furthermore each client instances is wrapped in an
     * OSGI-aware subclass (see {@link OsgiAwareClientImpl}), which ensures that
     * each invoke call on a webservice method is itself executed within an
     * OSGI-aware classloader context.
     * 
     * @param <T> Port class
     * @param pClass Port class
     * @param pPortUrl Port url (this is not the WSDL location)
     * @param pUsername Username for port authentication
     * @param pPassword Password for port authentication
     * @return Port object
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> pClass, String pPortUrl, String pUsername, String pPassword) {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            // set classloader to CXF bundle class loader to avoid OSGI classloader problems
            Thread.currentThread().setContextClassLoader(BusFactory.class.getClassLoader());

            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean(new OsgiAwareClientFactoryBean());
            factory.setServiceClass(pClass);
            factory.setAddress(pPortUrl);
            factory.setUsername(pUsername);
            factory.setPassword(pPassword);
            return (T) factory.create();
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

}
