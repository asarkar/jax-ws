package name.abhijitsarkar.webservices.jaxws.security.sym;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jboss.ws.api.annotation.EndpointConfig;

@WebService(portName = "CalculatorSym", name = "CalculatorSym", serviceName = "CalculatorSymService", targetNamespace = "http://abhijitsarkar.name/webservices/jaxws/security/CalculatorSym/", wsdlLocation = "WEB-INF/calculator-sym.wsdl")
@HandlerChain(file = "jaxws-handler-chains.xml")
 @EndpointConfig(configFile = "WEB-INF/jaxws-endpoint-config.xml", configName
 = "ws-security-sym")
public class CalculatorSym {
    @WebMethod(operationName = "add")
    public int add(@WebParam(name = "i") final int i,
	    @WebParam(name = "j") final int j) {
	return i + j;
    }
}
