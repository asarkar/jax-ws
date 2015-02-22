package name.abhijitsarkar.webservices.jaxws.security.ut.client;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class CalculatorUTClientResource {
    @Inject
    CalculatorUTClient client;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int add(@QueryParam(value = "augend") int augend,
	    @QueryParam(value = "addend") int addend,
	    @QueryParam(value = "username") String username) {
	return client.add(augend, addend, username);
    }
}
