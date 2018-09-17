package app.filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebFilter(urlPatterns = {"/*"})
    public class AuthenticationFilter implements Filter {

        public void init(FilterConfig fConfig) throws ServletException {

        }

        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String ps = req.getRequestURI();
            Pattern p = Pattern.compile("(css)|(js)$");
            Matcher m = p.matcher(ps);
            Cookie[] cookies = req.getCookies();
            Cookie authCookie = null;
            if (cookies != null){
                authCookie = Arrays.stream( req.getCookies()).filter(x -> x.getName().equals("userSession")).findFirst().orElse(null);
            }
            if (req.getRequestURI().equals("/") || authCookie != null || m.find()) {
                chain.doFilter(request, response);
            }
            else {
                res.sendRedirect("/");
            }
        }

        public void destroy() {
        }
}
