package name.abhijitsarkar.webservices.jaxws.deploydesc.client;

import java.io.File;

import name.abhijitsarkar.webservices.jaxws.deploydesc.CalculatorImpl;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CalculatorIntegrationTest {
    /**
     * The location of the WebApp source folder so we know where to find the
     * web.xml when deploying using Arquillian.
     */
    private static final String WEBAPP_SRC = "src/main/webapp";
    /**
     * The name of the WAR Archive that will be used by Arquillian to deploy the
     * application.
     */
    private static final String APP_NAME = "jax-ws-deploy-descriptor";

    private final CalculatorClient client = new CalculatorClient();

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
	return ShrinkWrap
		.create(WebArchive.class, APP_NAME + ".war")
		.addPackage(CalculatorImpl.class.getPackage())
		.addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
		.addAsWebInfResource(
			new File(WEBAPP_SRC, "WEB-INF/webservices.xml"));
    }

    @Test
    public void testAdd() throws Exception {
	Assert.assertEquals(3, client.add(1, 2));
    }
}
