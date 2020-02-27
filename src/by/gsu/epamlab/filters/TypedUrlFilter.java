package by.gsu.epamlab.filters;

import by.gsu.epamlab.model.utils.Loggers;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static by.gsu.epamlab.model.utils.Constants.MSG_FILTER;
import static by.gsu.epamlab.model.utils.ConstantsJSP.*;

@WebFilter({URL_ACTIONS, URL_MAIN_HTML, URL_ACTION_HTML})
public class TypedUrlFilter implements Filter {
	private static final Logger LOGGER = Loggers.init(TypedUrlFilter.class.getName());

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request; 
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
		String refferer = httpRequest.getHeader(REFERER_NAME);
		LOGGER.log(Level.INFO, MSG_FILTER + refferer);
		if (refferer == null) {
			httpResponse.sendRedirect(httpRequest.getContextPath());
			return;
		}
		chain.doFilter(request, response);
	}
}
