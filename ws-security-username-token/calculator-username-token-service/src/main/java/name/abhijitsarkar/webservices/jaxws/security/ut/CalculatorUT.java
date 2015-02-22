package name.abhijitsarkar.webservices.jaxws.security.ut;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jboss.ws.api.annotation.EndpointConfig;

@WebService(portName = "CalculatorUT", name = "CalculatorUT", serviceName = "CalculatorUTService", targetNamespace = "http://abhijitsarkar.name/webservices/jaxws/security/CalculatorUT/", wsdlLocation = "WEB-INF/calculator-ut.wsdl")
@HandlerChain(file = "jaxws-handler-chains.xml")
@EndpointConfig(configFile = "WEB-INF/jaxws-endpoint-config.xml", configName = "ws-security-ut")
public class CalculatorUT {

    @WebMethod(operationName = "add")
    public int add(@WebParam(name = "i") final int i,
	    @WebParam(name = "j") final int j) {
	return i + j;
    }
}
