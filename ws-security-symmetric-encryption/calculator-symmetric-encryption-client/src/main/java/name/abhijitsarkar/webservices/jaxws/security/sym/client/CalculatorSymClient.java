package name.abhijitsarkar.webservices.jaxws.security.sym.client;

import java.net.URL;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

import name.abhijitsarkar.webservices.jaxws.security.sym.client.generated.CalculatorSym;
import name.abhijitsarkar.webservices.jaxws.security.sym.client.generated.CalculatorSymService;

public class CalculatorSymClient {
    @WebServiceRef
    private CalculatorSymService service;

    public int add(int augend, int addend, String encryptionUsername) {
	URL clientProperties = getClass().getResource("/client.properties");

	CalculatorSym proxy = service.getCalculatorSym();

	/*
	 * The client doesn't need a callback because the server doesn't send
	 * anything using the client's public key. A callback is only required
	 * to retrieve the private key. The client uses the server's public key
	 * to encrypt the request and the same key to decrypt the response.
	 */
	((BindingProvider) proxy).getRequestContext().put(
		"ws-security.encryption.properties", clientProperties);
	/*
	 * This username is used to look up a certificate that is used to
	 * encrypt the request and decrypt the response
	 */
	((BindingProvider) proxy).getRequestContext().put(
		"ws-security.encryption.username", encryptionUsername);

	return proxy.add(augend, addend);
    }
}
