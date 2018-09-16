package app.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@WebFilter(urlPatterns = {"/*"})
public class RenderPageLoggerFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException {

    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        req.setAttribute("requestTime", new Date().getTime());
        chain.doFilter(request, response);
    }
    public void destroy() {
    }
}
