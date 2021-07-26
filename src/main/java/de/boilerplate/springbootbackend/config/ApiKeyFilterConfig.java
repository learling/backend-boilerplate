package de.boilerplate.springbootbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class ApiKeyFilterConfig extends GenericFilterBean {

    @Value("${springboot-backend.http.auth-token-header-name}")
    private String principalRequestHeader;

    @Value("${springboot-backend.http.auth-token}")
    private String principalRequestValue;

    private void returnNoAPIKeyError(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String error = "Nonexistent or invalid API KEY";
        httpResponse.reset();
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentLength(error .length());
        response.getWriter().write(error);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        /*
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000, https://reactstrap-ts.netlify.app");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpResponse.setHeader("Access-Control-Expose-Headers", "*");
         */
        String apiKeyHeaderValue = httpRequest.getHeader(principalRequestHeader);
        if (principalRequestValue.equals(apiKeyHeaderValue)) {
            chain.doFilter(httpRequest, httpResponse);
        } else {
            returnNoAPIKeyError(httpResponse);
        }
    }
}
