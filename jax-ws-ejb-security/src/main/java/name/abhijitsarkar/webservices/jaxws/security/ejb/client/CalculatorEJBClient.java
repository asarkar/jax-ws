package name.abhijitsarkar.webservices.jaxws.security.ejb.client;

import javax.xml.ws.BindingProvider;

import name.abhijitsarkar.webservices.jaxws.security.ejb.client.generated.CalculatorEJB;
import name.abhijitsarkar.webservices.jaxws.security.ejb.client.generated.CalculatorEJBService;

public class CalculatorEJBClient {
    public int add(int augend, int addend, String username, String password) {
	CalculatorEJB proxy = new CalculatorEJBService().getCalculatorEJBPort();

	((BindingProvider) proxy).getRequestContext().put(
		BindingProvider.USERNAME_PROPERTY, username);
	((BindingProvider) proxy).getRequestContext().put(
		BindingProvider.PASSWORD_PROPERTY, password);

	return proxy.add(augend, addend);
    }
}
