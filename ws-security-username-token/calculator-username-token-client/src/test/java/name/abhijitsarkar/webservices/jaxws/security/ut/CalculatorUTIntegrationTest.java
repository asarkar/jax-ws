package name.abhijitsarkar.webservices.jaxws.security.ut;

import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import name.abhijitsarkar.webservices.jaxws.security.ut.client.CalculatorUTClientResource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CalculatorUTIntegrationTest {
    private static final String CALCULATOR_SERVICE_PATH = "../calculator-username-token-service";
    private static final String RESOURCES_PATH = "src/main/resources";
    private static final String WEB_APP_PATH = "src/main/webapp";
    private static final String TEST_RESOURCES_PATH = "src/test/resources";

    Client client;
    WebTarget root;

    @Deployment(testable = false)
    public static EnterpriseArchive createDeployment() {
	WebArchive server = ShrinkWrap.createFromZipFile(WebArchive.class,
		new File(CALCULATOR_SERVICE_PATH,
			"target/calculator-username-token-service.war"));

	WebArchive client = ShrinkWrap
		.create(WebArchive.class,
			"calculator-username-token-client.war")
		.addPackages(true,
			CalculatorUTClientResource.class.getPackage())
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
		.target("http://localhost:8080/calculator-username-token-client");
    }

    @Test
    public void testAdd() {
	Builder reqBuilder = buildRequest(1, 2, "abhijit");
	Response response = reqBuilder.get();

	assertEquals(200, response.getStatus());
	assertEquals(Integer.valueOf(3), response.readEntity(Integer.class));
    }

    @Test
    public void testAddWithWrongUsername() {
	Builder reqBuilder = buildRequest(1, 2, "junk");
	Response response = reqBuilder.get();

	assertEquals(500, response.getStatus());
    }

    private Builder buildRequest(int augend, int addend, String username) {
	return root.queryParam("augend", augend).queryParam("addend", addend)
		.queryParam("username", username).request()
		.accept(MediaType.TEXT_PLAIN);
    }
}
