package name.abhijitsarkar.webservices.jaxws.security.sym;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class CalculatorSymPasswordCallback implements CallbackHandler {
    public void handle(Callback[] callbacks) throws IOException,
	    UnsupportedCallbackException {
	WSPasswordCallback pc = null;

	for (Callback c : callbacks) {
	    pc = (WSPasswordCallback) c;

	    /*
	     * This is the encryption username used by the client. If the client
	     * had used the server's public key (it better) to encrypt the
	     * request, now the server needs to retrieve it's private key to
	     * decrypt it and also to encrypt the response. This matches the
	     * keystore alias defined in the service.properties. This password
	     * needs to match the one that was used when generating the key.
	     */
	    if ("calcsym-service-key".equals(pc.getIdentifier())) {
		pc.setPassword("password");

		break;
	    }
	}
    }
}
