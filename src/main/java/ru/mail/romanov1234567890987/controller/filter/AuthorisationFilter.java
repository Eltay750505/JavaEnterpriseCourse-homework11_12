package ru.mail.romanov1234567890987.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class AuthorisationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
