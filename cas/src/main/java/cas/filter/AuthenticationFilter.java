package cas.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import cas.custom.component.DefaultRedisHttpSession;
import cas.models.User;
import cas.utils.JsonUtils;

public class AuthenticationFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest req = (HttpServletRequest)request;  
	     HttpServletResponse resp = (HttpServletResponse)response;
	     String token = req.getParameter("token");
	     if (StringUtils.isNotBlank(token)) {
	    	 HttpSession session = new DefaultRedisHttpSession(req.getSession(), token);
	    	 User user = (User) session.getAttribute("user");
	    	 if (user != null) {
	    		 resp.getWriter().write(JsonUtils.toJSONString(user));
	    	 }
	     }
	}

	@Override
	public void destroy() {
		
	}

}