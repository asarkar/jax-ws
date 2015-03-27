package name.abhijitsarkar.webservices.jaxws.security.ejb;

import static org.junit.Assert.assertEquals;

import java.io.File;

import name.abhijitsarkar.webservices.jaxws.security.ejb.client.CalculatorEJBClient;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CalculatorEJBIT {
    private static final String WEB_APP_PATH = "src/main/webapp";

    private CalculatorEJBClient client = new CalculatorEJBClient();

    /*
     * For error "Could not invoke deployment method", check if all artifacts
     * that're being added, exist. Arquillian doesn't say anything useful.
     */
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
	WebArchive server = ShrinkWrap
		.create(WebArchive.class, "jax-ws-ejb-security.war")
		.addPackage(CalculatorEJB.class.getPackage())
		.addAsWebInfResource(new File(WEB_APP_PATH, "WEB-INF/web.xml"))
		.addAsWebInfResource(
			new File(WEB_APP_PATH, "WEB-INF/jboss-ejb3.xml"));

	System.out.println(server.toString(true));

	return server;
    }

    @Test
    public void testAdd() {
	assertEquals(3, client.add(1, 2, "abhijit", "abhijit"));
    }
}
