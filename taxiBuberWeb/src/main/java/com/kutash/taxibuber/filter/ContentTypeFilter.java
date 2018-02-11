package com.kutash.taxibuber.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * The type Content type filter.
 */
@WebFilter(urlPatterns = { "/ajaxController" }, initParams = {@WebInitParam(name = "contentType", value = "application/json")})
public class ContentTypeFilter implements Filter {

    private String contentType;

    public void init(FilterConfig fConfig) throws ServletException {
        contentType = fConfig.getInitParameter("contentType");
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setContentType(contentType);
        chain.doFilter(request, response);
    }

    public void destroy() {
        contentType = null;
    }
}
