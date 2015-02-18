package name.abhijitsarkar.webservices.jaxws.handler;

import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SOAPMessageLoggingHandler extends AbstractSOAPHandler {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SOAPMessageLoggingHandler.class);

    @Override
    public boolean handleMessage(SOAPMessageContext messageContext) {
	boolean outbound = (Boolean) messageContext
		.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

	if (outbound) {
	    LOGGER.debug("Outbound SOAP message");
	} else {
	    LOGGER.debug("Inbound SOAP message");
	}

	handlerUtil.prettyPrintSOAPMessage(messageContext.getMessage());

	return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext messageContext) {
	handlerUtil.prettyPrintSOAPMessage(messageContext.getMessage());

	return true;
    }
}
