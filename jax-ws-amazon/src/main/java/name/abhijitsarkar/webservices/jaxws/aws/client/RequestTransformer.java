package name.abhijitsarkar.webservices.jaxws.aws.client;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestTransformer {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(RequestSignatureHandler.class);

    private static final String SECRET_ACCESS_KEY = "QR7NLNessixFbDIJJDWbXZiM9WJ1OVsziwbo2BTh";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String TIMEZONE = "UTC";

    private static final SignatureGenerator sigGen = new SignatureGenerator();
    private static final String XSL_TEMPLATE = "/aws-request.xsl";
    private static Source xsl;

    public RequestTransformer() {
	xsl = new StreamSource(
		RequestTransformer.class.getResourceAsStream(XSL_TEMPLATE));
    }

    protected Result transform(Source source) {
	Result result = new StreamResult(new StringWriter());
	String timestamp = getTimestamp();
	String signature = sigGen.getSignature("ItemSearch", timestamp,
		SECRET_ACCESS_KEY);
	LOGGER.debug("Signature: {}.", signature);
	TransformerFactory factory = TransformerFactory.newInstance();
	try {
	    Transformer transformer = factory.newTransformer(xsl);
	    transformer.setParameter("signature", signature);
	    transformer.setParameter("timestamp", timestamp);

	    transformer.transform(source, result);
	} catch (Exception e) {
	    LOGGER.error("Unable to sign the outgoing request", e);
	    throw new WebServiceException(
		    "Unable to sign the outgoing request", e);
	}
	return result;
    }

    private String getTimestamp() {
	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
	return dateFormat.format(calendar.getTime());
    }
}
