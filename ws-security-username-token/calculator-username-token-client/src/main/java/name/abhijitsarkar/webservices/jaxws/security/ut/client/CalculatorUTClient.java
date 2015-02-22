package name.abhijitsarkar.webservices.jaxws.security.ut.client;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

import name.abhijitsarkar.webservices.jaxws.security.ut.client.generated.CalculatorUT;
import name.abhijitsarkar.webservices.jaxws.security.ut.client.generated.CalculatorUTService;

public class CalculatorUTClient {
    @WebServiceRef
    private CalculatorUTService service;

    public int add(int augend, int addend, String username) {
	CalculatorUT proxy = service.getCalculatorUT();
	((BindingProvider) proxy).getRequestContext().put(
		"ws-security.username", username);
	((BindingProvider) proxy).getRequestContext().put(
		"ws-security.callback-handler",
		new CalculatorUTPasswordCallback());
	
	return proxy.add(augend, addend);
    }
}
