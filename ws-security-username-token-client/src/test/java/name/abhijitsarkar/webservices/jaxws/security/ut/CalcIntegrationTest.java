package name.abhijitsarkar.webservices.jaxws.security.ut;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import name.abhijitsarkar.webservices.jaxws.security.ut.client.CalcResource;
import name.abhijitsarkar.webservices.jaxws.security.ut.gen.CalculatorUTService;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CalcIntegrationTest {
    private static final String CALCULATOR_SERVICE_NAME = "ws-security-username-token";
    private static final String RESOURCES_PATH = "src/main/resources";
    private static final String WEB_APP_PATH = "src/main/webapp";
    private static final String TEST_RESOURCES_PATH = "src/test/resources";

    Client client;
    WebTarget root;

    /*
     * For error "Could not invoke deployment method", check if all artifacts
     * that're being added, exist. Arquillian doesn't say anything useful.
     */
    @Deployment(testable = false)
    public static EnterpriseArchive createDeployment() {
	WebArchive server = ShrinkWrap.createFromZipFile(WebArchive.class,
		new File("../" + CALCULATOR_SERVICE_NAME + "/target/"
			+ CALCULATOR_SERVICE_NAME + ".war"));

	WebArchive client = ShrinkWrap
		.create(WebArchive.class,
			"ws-security-username-token-client.war")
		.addPackages(true, CalcResource.class.getPackage())
		.addPackages(true, CalculatorUTService.class.getPackage())
		.addAsResource(
			new File(RESOURCES_PATH, "wsdl/calculator-ut.wsdl"),
			"wsdl/calculator-ut.wsdl")
		.addAsWebInfResource(
			new File(WEB_APP_PATH, "WEB-INF/beans.xml"))
		.addAsManifestResource(
			new File(TEST_RESOURCES_PATH, "META-INF/MANIFEST.MF"));

	EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class,
		"ws-security-username-token.ear").addAsModules(server, client);

	System.out.println(ear.toString(true));

	return ear;
    }

    @Before
    public void initClient() {
	client = ClientBuilder.newClient();
	root = this.client
		.target("http://localhost:8080/ws-security-username-token-client");
    }

    @Test
    public void testAdd() {
	Builder reqBuilder = buildRequest(1, 2, "abhijit");
	Response response = reqBuilder.get();

	assertEquals(OK.getStatusCode(), response.getStatus());
	assertEquals(Integer.valueOf(3), response.readEntity(Integer.class));
    }

    @Test
    public void testAddWithWrongUsername() {
	Builder reqBuilder = buildRequest(1, 2, "junk");
	Response response = reqBuilder.get();

	/*
	 * Ideally this should be a 401 UNAUTHORIZED but we're really not
	 * testing the resource here.
	 */
	assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(),
		response.getStatus());
    }

    private Builder buildRequest(int augend, int addend, String username) {
	return root.queryParam("augend", augend).queryParam("addend", addend)
		.queryParam("username", username).request().accept(TEXT_PLAIN);
    }
}
