package name.abhijitsarkar.webservices.jaxws.aws.client;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureGenerator {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SignatureGenerator.class);

    private static final String DEFAULT_ALGORITHM = "HmacSHA256";
    private static final String DEFAULT_CHARSET = "UTF-8";

    private String algorithm;
    private String charset;

    public SignatureGenerator() {
	this(DEFAULT_ALGORITHM, DEFAULT_CHARSET);
    }

    public SignatureGenerator(String algorithm, String charset) {
	this.algorithm = algorithm;
	this.charset = charset;
    }

    protected String getSignature(String operation, String timestamp,
	    String secretAccessKey) {
	String signature = null;
	try {
	    String toSign = operation + timestamp;
	    byte[] toSignBytes = toSign.getBytes(charset);

	    Mac signer = Mac.getInstance(algorithm);
	    SecretKeySpec keySpec = new SecretKeySpec(
		    secretAccessKey.getBytes(charset), algorithm);

	    signer.init(keySpec);
	    signer.update(toSignBytes);
	    byte[] signBytes = signer.doFinal();

	    signature = DatatypeConverter.printBase64Binary(signBytes);
	} catch (Exception e) {
	    LOGGER.error("Unable to create the signature", e);
	    throw new WebServiceException("Unable to create the signature", e);
	}
	return signature;
    }
}