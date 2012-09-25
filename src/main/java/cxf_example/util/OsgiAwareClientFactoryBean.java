package cxf_example.util;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.JaxWsClientFactoryBean;

/**
 * Factory bean that creates {@link OsgiAwareClientImpl} objects instead of
 * {@link org.apache.cxf.endpoint.ClientImpl} objects to ensure correct
 * classloader usage in OSGI context.
 */
class OsgiAwareClientFactoryBean extends JaxWsClientFactoryBean {

    @Override
    protected Client createClient(Endpoint ep) {
        // use osgi-aware client impl instead of {@link
        // org.apache.cxf.endpoint.ClientImpl}
        return new OsgiAwareClientImpl(getBus(), ep, getConduitSelector());
    }

}
