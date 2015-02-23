package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		Class<?>[] rootConfigClasses = new Class<?>[] { AppConfig.class };

		return rootConfigClasses;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		Class<?>[] servletConfigClasses = new Class<?>[] { DispatcherConfig.class };

		return servletConfigClasses;
	}

	@Override
	protected String[] getServletMappings() {
		String[] servletMappings = new String[] { "/client/*" };

		return servletMappings;
	}

	@Override
	protected void registerContextLoaderListener(ServletContext servletContext) {
		super.registerContextLoaderListener(servletContext);

		/*
		 * CXF looks for a file /WEB-INF/cxf-servlet.xml by default or whatever
		 * file path is set against the key "config-location". In this project,
		 * we set the servlet mapping programmatically.
		 */
	}

	@Override
	protected String getServletName() {
		return "dispatcher";
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		super.onStartup(servletContext);

		registerJAXWSServlet(servletContext);
	}

	private void registerJAXWSServlet(ServletContext servletContext) {
		ServletRegistration.Dynamic jaxWsServlet = servletContext.addServlet(
				"cxf", new CXFServlet());
		jaxWsServlet.addMapping("/*");
	}
}
