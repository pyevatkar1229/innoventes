
package com.innoventes.test.app.exception;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class WebsiteURLValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("POST")) {
            // Get the request payload and inspect the webSiteURL field
            String webSiteURL = request.getParameter("webSiteURL");

            // Check if the URL is valid
            if (!isValidURL(webSiteURL)) {
                // Set the webSiteURL field to null
                request.setAttribute("webSiteURL", null);
            }
        }
        return true;
    }

    private boolean isValidURL(String url) {
        // Implement URL validation logic here
        // Example: You can use regex or a library like Apache Commons Validator
        // For demonstration, let's assume any non-null URL is considered valid
        return url != null;
    }
}
