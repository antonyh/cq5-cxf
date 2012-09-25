package cxf_example.impl;

import java.util.Dictionary;

import cxf_example.api.CxfExampleService;
import cxf_example.util.JaxWsClientFactory;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.OsgiUtil;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.webservicex.*;


@Component(label = "Antony's CXF Service", immediate = true, metatype = true)
@Service(CxfExampleService.class)
public class CxfExampleServiceImpl implements CxfExampleService {

	public String getSomething() {
		StockQuoteSoap sqs = this.getInstance();
	    String val = sqs.getQuote("AAPL");
		return "AAPL = " + val;
	}

	@Property(label = "Webservice URL", value = CxfExampleServiceImpl.DEFAULT_PORTURL)
	private static final String PROPERTY_PORTURL = "portUrl";
	private static final String DEFAULT_PORTURL = "http://www.webservicex.net/stockquote.asmx";

	private String portUrl;
	private StockQuoteSoap soapInstance;

	private static final Logger log = LoggerFactory
			.getLogger(CxfExampleService.class);

	@Activate
	protected final void activate(final ComponentContext context) {
		
		try {
			Dictionary properties = context.getProperties();

			// get port url from OSGI config
			portUrl = OsgiUtil.toString(properties.get(PROPERTY_PORTURL),
					DEFAULT_PORTURL);

			log.info("portUrl=" + portUrl);

			// instantiate SOAP client proxy
			soapInstance = JaxWsClientFactory.create(StockQuoteSoap.class,
					portUrl);

		} catch (RuntimeException ex) {
			log.error("Error with SOAP service proxy client.", ex);
		}
	}

	/**
	 * @return Greeter SOAP client proxy instance
	 */
	public StockQuoteSoap getInstance() {
		return soapInstance;
	}

	/**
	 * @return Configured port url
	 */
	public String getPortUrl() {
		return portUrl;
	}

}
