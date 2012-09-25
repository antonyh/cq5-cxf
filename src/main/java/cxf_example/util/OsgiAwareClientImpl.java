package cxf_example.util;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.endpoint.ClientImpl;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointImplFactory;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.Conduit;

/**
 * Enhances {@link ClientImpl} by ensuring that each webservice method invoke is
 * called within the context of an OSGI aware classloader.
 */
class OsgiAwareClientImpl extends ClientImpl {

    public OsgiAwareClientImpl(Bus pB, Endpoint pE, Conduit pC) {
        super(pB, pE, pC);
    }

    public OsgiAwareClientImpl(Bus pB, Endpoint pE, ConduitSelector pSc) {
        super(pB, pE, pSc);
    }

    public OsgiAwareClientImpl(Bus pB, Endpoint pE) {
        super(pB, pE);
    }

    public OsgiAwareClientImpl(Bus pBus, URL pWsdlUrl, QName pService, QName pPort,
            EndpointImplFactory pEndpointImplFactory) {
        super(pBus, pWsdlUrl, pService, pPort, pEndpointImplFactory);
    }

    public OsgiAwareClientImpl(Bus pBus, URL pWsdlUrl, QName pService, QName pPort) {
        super(pBus, pWsdlUrl, pService, pPort);
    }

    public OsgiAwareClientImpl(URL pWsdlUrl, QName pPort) {
        super(pWsdlUrl, pPort);
    }

    public OsgiAwareClientImpl(URL pWsdlUrl) {
        super(pWsdlUrl);
    }

    @Override
    public Object[] invoke(BindingOperationInfo pOi, Object[] pParams, Map<String, Object> pContext, Exchange pExchange)
            throws Exception {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            // set classloader to CXF bundle class loader to avoid OSGI classloader problems
            Thread.currentThread().setContextClassLoader(BusFactory.class.getClassLoader());

            return super.invoke(pOi, pParams, pContext, pExchange);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

}
