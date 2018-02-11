package com.kutash.taxibuber.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
 * The type Aggregate encoding filter.
 */
@WebFilter(urlPatterns = { "/*" }, initParams = {@WebInitParam(name = "encoding", value = "utf-8", description = "Encoding Param")})
public class AggregateEncodingFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();
    private String code;

    public void init(FilterConfig fConfig) throws ServletException {
        code = fConfig.getInitParameter("encoding");
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.log(Level.INFO,"Encoding filter");
        String codeRequest= request.getCharacterEncoding();
        if(code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        code= null;
    }
}
