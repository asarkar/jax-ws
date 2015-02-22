package name.abhijitsarkar.webservices.jaxws.security.sym.client;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class CalculatorSymClientResource {
    @Inject
    CalculatorSymClient client;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int add(@QueryParam(value = "augend") int augend,
	    @QueryParam(value = "addend") int addend,
	    @QueryParam(value = "encryption-username") String encryptionUsername) {
	/*
	 * In reality, there should be check for exceptions and if unauthorized,
	 * status code 401 returned with a WWW-Authenticate header field
	 */
	return client.add(augend, addend, encryptionUsername);
    }
}
