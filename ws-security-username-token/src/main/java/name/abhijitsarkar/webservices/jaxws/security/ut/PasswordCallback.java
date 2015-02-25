package name.abhijitsarkar.webservices.jaxws.security.ut;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class PasswordCallback implements CallbackHandler {
    public void handle(Callback[] callbacks) throws IOException,
	    UnsupportedCallbackException {
	WSPasswordCallback pc = null;

	for (Callback c : callbacks) {
	    pc = (WSPasswordCallback) c;

	    if ("abhijit".equals(pc.getIdentifier())) {
		pc.setPassword("password");

		break;
	    }
	}
    }
}
