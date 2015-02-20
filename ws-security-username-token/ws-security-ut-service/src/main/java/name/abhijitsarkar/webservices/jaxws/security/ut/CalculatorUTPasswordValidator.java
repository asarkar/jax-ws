package name.abhijitsarkar.webservices.jaxws.security.ut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PasswordValidationException;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PasswordValidator;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PlainTextPasswordRequest;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.Request;

public class CalculatorUTPasswordValidator implements PasswordValidator {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(CalculatorUTPasswordValidator.class);

    @Override
    public boolean validate(Request request) throws PasswordValidationException {
	if (!(request instanceof PlainTextPasswordRequest)) {
	    LOGGER.error("Expected PlainTextPasswordRequest, got {}.", request
		    .getClass().getName());
	    throw new PasswordValidationException(
		    "Expected PlainTextPasswordRequest, got "
			    + request.getClass().getName());
	}

	PlainTextPasswordRequest plainTextRequest = (PlainTextPasswordRequest) request;

	if ("abhijit".equals(plainTextRequest.getUsername())
		&& "password".equals(plainTextRequest.getPassword())) {
	    LOGGER.debug("Username and password match found!");
	    return true;
	}

	LOGGER.error("Invalid credentials.");

	throw new PasswordValidationException("Invalid credentials.");
    }
}
